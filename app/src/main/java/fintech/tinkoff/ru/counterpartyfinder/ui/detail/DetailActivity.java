package fintech.tinkoff.ru.counterpartyfinder.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.model.DataAnswerDto;

public class DetailActivity extends AppCompatActivity {

    private static String EXTRA_INFO = "extra_info";

    public static void start(Activity activity, DataAnswerDto dataAnswerDto) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_INFO, dataAnswerDto);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DataAnswerDto dataAnswerDto = (DataAnswerDto) getIntent().getSerializableExtra(EXTRA_INFO);
    }
}