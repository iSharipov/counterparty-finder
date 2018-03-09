package fintech.tinkoff.ru.counterpartyfinder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.adapter.SuggestionAdapter;
import fintech.tinkoff.ru.counterpartyfinder.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.listener.AsyncTaskCompleteListener;
import fintech.tinkoff.ru.counterpartyfinder.listener.RecyclerViewClickListener;
import fintech.tinkoff.ru.counterpartyfinder.mapper.DataAnswerToPreviewDtoMapper;
import fintech.tinkoff.ru.counterpartyfinder.model.DataSuggestion;
import fintech.tinkoff.ru.counterpartyfinder.watcher.SearchTextWatcher;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_view)
    RecyclerView mainView;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.pb_loading_indicator)
    FrameLayout progressBar;

    private static int counter = 20;
    private DataSuggestion dataSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainView.setHasFixedSize(true);
        mainView.setLayoutManager(new LinearLayoutManager(this));
    }

    class SuggestionTaskCompleteListener implements AsyncTaskCompleteListener<DataSuggestion> {

        @Override
        public void onTaskComplete(DataSuggestion result) {
            dataSuggestion = result;
            populateUI();
        }

        @Override
        public void onTaskFailure(Throwable t) {
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void startProgress() {
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
        search.addTextChangedListener(new SearchTextWatcher(counter, new SuggestionTaskCompleteListener()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        search.addTextChangedListener(null);
    }
}