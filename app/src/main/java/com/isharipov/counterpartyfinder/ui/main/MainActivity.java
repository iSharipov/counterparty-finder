package com.isharipov.counterpartyfinder.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.isharipov.counterpartyfinder.R;
import com.isharipov.counterpartyfinder.data.db.repository.BaseDao;
import com.isharipov.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import com.isharipov.counterpartyfinder.data.network.dto.PreviewDto;
import com.isharipov.counterpartyfinder.data.network.model.DataAnswer;
import com.isharipov.counterpartyfinder.data.network.model.DataSuggestion;
import com.isharipov.counterpartyfinder.mapper.DataAnswerToDataAnswerDtoMapper;
import com.isharipov.counterpartyfinder.mapper.DataAnswerToPreviewDtoMapper;
import com.isharipov.counterpartyfinder.ui.detail.DetailActivity;
import com.isharipov.counterpartyfinder.ui.main.adapter.SuggestionAdapter;
import com.isharipov.counterpartyfinder.ui.main.listener.AsyncTaskCompleteListener;
import com.isharipov.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;
import com.isharipov.counterpartyfinder.ui.main.watcher.SearchTextWatcher;
import com.isharipov.counterpartyfinder.ui.pref.SettingsPrefActivity;
import com.isharipov.counterpartyfinder.ui.recent.RecentActivity;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private static final String DATA_SUGGESTION = "DATA_SUGGESTION";
    private static final String SEARCH = "SEARCH";
    @BindView(R.id.main_view)
    RecyclerView mainView;
    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    @BindView(R.id.error_layout)
    View errorView;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    private SharedPreferences sharedPref;
    private int suggestionPiecePref;
    private Realm realm;
    private DataSuggestion dataSuggestion;
    private SearchView.OnQueryTextListener textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initRealm();
        initMainView(savedInstanceState);
        initPreferences();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.search_title);
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
    }

    private void initMainView(Bundle savedInstanceState) {
        mainView.setHasFixedSize(true);
        mainView.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState != null) {
            search.setQuery(savedInstanceState.getString(SEARCH), true);
            dataSuggestion = (DataSuggestion) savedInstanceState.getSerializable(DATA_SUGGESTION);
            if (dataSuggestion != null && dataSuggestion.getSuggestions() != null && !dataSuggestion.getSuggestions().isEmpty()) {
                populateUI();
            }
        }
    }

    private void initPreferences() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        suggestionPiecePref = Integer.valueOf(sharedPref.getString(SettingsPrefActivity.KEY_PREF_SUGGESTION_PIECE, "10"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void populateUI() {
        List<PreviewDto> previews = DataAnswerToPreviewDtoMapper.INSTANCE.map(dataSuggestion.getSuggestions());
        SuggestionAdapter adapter = new SuggestionAdapter(previews, new SuggestionListClickListener());
        mainView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textWatcher = new SearchTextWatcher(suggestionPiecePref, new SuggestionTaskCompleteListener());
        search.setOnQueryTextListener(textWatcher);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DATA_SUGGESTION, dataSuggestion);
        outState.putString(SEARCH, search.getQuery().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_recent) {
            RecentActivity.start(this);
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsPrefActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class SuggestionTaskCompleteListener implements AsyncTaskCompleteListener<DataSuggestion> {

        @Override
        public void onTaskComplete(DataSuggestion result) {
            dataSuggestion = result;
            populateUI();
        }

        @Override
        public void onTaskFailure(Throwable t) {
            mainView.setAdapter(null);
            errorView.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void startProgress() {
            errorView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
//            enableSearchView(search, false);
        }

        @Override
        public void stopProgress() {
            progressBar.setVisibility(View.GONE);
//            enableSearchView(search, true);
        }
    }

//    private void enableSearchView(View view, boolean enabled) {
//        view.setEnabled(enabled);
//        view.setFocusable(true);
//        if (view instanceof ViewGroup) {
//            ViewGroup viewGroup = (ViewGroup) view;
//            for (int i = 0; i < viewGroup.getChildCount(); i++) {
//                View child = viewGroup.getChildAt(i);
//                enableSearchView(child, enabled);
//            }
//        }
//    }

    class SuggestionListClickListener implements RecyclerViewClickListener {

        @Override
        public void onClick(View view, int position) {

            DataAnswer dataAnswer = dataSuggestion.getSuggestions().get(position);
            if (dataAnswer != null) {
                DataAnswerDto dataAnswerDto = DataAnswerToDataAnswerDtoMapper.INSTANCE.map(dataSuggestion.getSuggestions().get(position));
                DataAnswerDto dataAnswerFromRealm = BaseDao.get(realm, DataAnswerDto.class, dataAnswerDto.getHid());
                if (dataAnswerFromRealm != null) {
                    realm.executeTransaction(realm1 -> dataAnswerFromRealm.setTapDate(new Date().toString()));
                } else {
                    BaseDao.add(realm, dataAnswerDto);
                }
                DetailActivity.start(MainActivity.this, dataAnswerDto.getHid());
            } else {
                Toast.makeText(MainActivity.this, "Details must not be null", Toast.LENGTH_LONG).show();
            }
        }
    }
}