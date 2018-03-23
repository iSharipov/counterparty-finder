package fintech.tinkoff.ru.counterpartyfinder.ui.main;

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
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.BaseDao;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import fintech.tinkoff.ru.counterpartyfinder.data.network.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.DataSuggestion;
import fintech.tinkoff.ru.counterpartyfinder.mapper.DataAnswerToDataAnswerDtoMapper;
import fintech.tinkoff.ru.counterpartyfinder.mapper.DataAnswerToPreviewDtoMapper;
import fintech.tinkoff.ru.counterpartyfinder.ui.detail.DetailActivity;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.adapter.SuggestionAdapter;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.AsyncTaskCompleteListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.SuggestionItemClickListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.watcher.SearchTextWatcher;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_view)
    public RecyclerView mainView;
    @BindView(R.id.search)
    public EditText search;
    @BindView(R.id.pb_loading_indicator)
    public FrameLayout progressBar;
    @BindView(R.id.error_layout)
    public View errorView;

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

    private void populateUI() {
        List<PreviewDto> previews = DataAnswerToPreviewDtoMapper.INSTANCE.map(dataSuggestion.getSuggestions());
        SuggestionAdapter adapter = new SuggestionAdapter(previews);
        mainView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        search.addTextChangedListener(new SearchTextWatcher(counter, new SuggestionTaskCompleteListener()));
        mainView.addOnItemTouchListener(new SuggestionItemClickListener(this, mainView, new RecyclerViewClickListener() {
            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public void onClick(View view, int position) {
                DataAnswerDto dataAnswerDto = DataAnswerToDataAnswerDtoMapper.INSTANCE.map(dataSuggestion.getSuggestions().get(position));
                if (dataAnswerDto != null) {
                    BaseDao.add(dataAnswerDto);
                    DetailActivity.start(MainActivity.this, dataAnswerDto);
                }
                Toast.makeText(MainActivity.this, "Details must not be null", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    protected void onPause() {
        super.onPause();
        search.addTextChangedListener(null);
        mainView.addOnItemTouchListener(null);
    }
}