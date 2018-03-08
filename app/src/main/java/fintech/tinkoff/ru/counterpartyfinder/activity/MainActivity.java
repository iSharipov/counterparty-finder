package fintech.tinkoff.ru.counterpartyfinder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.listener.AsyncTaskCompleteListener;
import fintech.tinkoff.ru.counterpartyfinder.model.DataSuggestion;
import fintech.tinkoff.ru.counterpartyfinder.watcher.SearchTextWatcher;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_view)
    RecyclerView mainView;
    @BindView(R.id.search)
    EditText search;
    private static int counter = 10;

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
            System.out.println(result);
        }
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