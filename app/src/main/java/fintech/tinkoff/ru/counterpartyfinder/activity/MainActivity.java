package fintech.tinkoff.ru.counterpartyfinder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import fintech.tinkoff.ru.counterpartyfinder.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipe_refresh_layout)
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
    }
}