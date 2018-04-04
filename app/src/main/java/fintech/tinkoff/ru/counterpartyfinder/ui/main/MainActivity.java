package fintech.tinkoff.ru.counterpartyfinder.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.BaseDao;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import fintech.tinkoff.ru.counterpartyfinder.data.network.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.DataAnswer;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.DataSuggestion;
import fintech.tinkoff.ru.counterpartyfinder.mapper.DataAnswerToDataAnswerDtoMapper;
import fintech.tinkoff.ru.counterpartyfinder.mapper.DataAnswerToPreviewDtoMapper;
import fintech.tinkoff.ru.counterpartyfinder.ui.detail.DetailActivity;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.adapter.SuggestionAdapter;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.AsyncTaskCompleteListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.watcher.SearchTextWatcher;
import fintech.tinkoff.ru.counterpartyfinder.ui.recent.RecentActivity;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private static final String DATA_SUGGESTION = "DATA_SUGGESTION";
    private static final String SEARCH = "SEARCH";
    private static int counter = 20;
    private Realm realm;

    @BindView(R.id.main_view)
    public RecyclerView mainView;
    @BindView(R.id.search)
    public EditText search;
    @BindView(R.id.pb_loading_indicator)
    public FrameLayout progressBar;
    @BindView(R.id.error_layout)
    public View errorView;
    @BindView(R.id.main_toolbar)
    public Toolbar toolbar;

    private DataSuggestion dataSuggestion;
    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Поиск");
        realm = Realm.getDefaultInstance();
        mainView.setHasFixedSize(true);
        mainView.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState != null) {
            dataSuggestion = (DataSuggestion) savedInstanceState.getSerializable(DATA_SUGGESTION);
            search.setText(savedInstanceState.getString(SEARCH));
            populateUI();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    class SuggestionTaskCompleteListener implements AsyncTaskCompleteListener<DataSuggestion> {

        @Override
        public void onTaskComplete(DataSuggestion result) {
            dataSuggestion = result;
            populateUI();
        }

        @Override
        public void onTaskFailure(Throwable t) {
            errorView.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void startProgress() {
            errorView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void stopProgress() {
            progressBar.setVisibility(View.GONE);
        }
    }

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

    private void populateUI() {
        List<PreviewDto> previews = DataAnswerToPreviewDtoMapper.INSTANCE.map(dataSuggestion.getSuggestions());
        SuggestionAdapter adapter = new SuggestionAdapter(previews, new SuggestionListClickListener());
        mainView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textWatcher = new SearchTextWatcher(counter, new SuggestionTaskCompleteListener());
        search.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onPause() {
        super.onPause();
        search.removeTextChangedListener(textWatcher);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DATA_SUGGESTION, dataSuggestion);
        outState.putString(SEARCH, search.getText().toString());
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
        return super.onOptionsItemSelected(item);
    }

}