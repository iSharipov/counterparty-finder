package fintech.tinkoff.ru.counterpartyfinder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.model.DataSuggestion;
import fintech.tinkoff.ru.counterpartyfinder.rest.DaDataBody;
import fintech.tinkoff.ru.counterpartyfinder.rest.DaDataRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_view)
    RecyclerView mainView;
    @BindView(R.id.search)
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainView.setHasFixedSize(true);
        mainView.setLayoutManager(new LinearLayoutManager(this));
        DaDataRestClient.getInstance().suggestAsync(new DaDataBody("7", 10), new Callback<DataSuggestion>() {
            @Override
            public void onResponse(Call<DataSuggestion> call, Response<DataSuggestion> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<DataSuggestion> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}