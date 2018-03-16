package fintech.tinkoff.ru.counterpartyfinder.ui.main.watcher;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import fintech.tinkoff.ru.counterpartyfinder.data.network.DaDataBody;
import fintech.tinkoff.ru.counterpartyfinder.data.network.DaDataRestClient;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.DataSuggestion;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.AsyncTaskCompleteListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * 08.03.2018.
 */

public class SearchTextWatcher implements TextWatcher {

    private Timer timer = new Timer();
    private final Gson gson;
    private final int counter;
    private final AsyncTaskCompleteListener<DataSuggestion> completeListener;

    public SearchTextWatcher(int counter, AsyncTaskCompleteListener<DataSuggestion> completeListener) {
        this.counter = counter;
        this.completeListener = completeListener;
        this.gson = new Gson();

    }

    @Override
    public void afterTextChanged(final Editable s) {
        final Handler handler = new Handler();
        timer.cancel();
        timer = new Timer();
        long delay = 400;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(completeListener::startProgress);
                DaDataBody daDataBody = new DaDataBody(s.toString(), counter);
                Timber.i("Start Dadata request with params: %s", gson.toJson(daDataBody));
                DaDataRestClient.getInstance().suggestAsync(daDataBody, new Callback<DataSuggestion>() {
                    @Override
                    public void onResponse(Call<DataSuggestion> call, Response<DataSuggestion> response) {
                        Timber.i("Response received: %s", gson.toJson(response));
                        completeListener.onTaskComplete(response.body());
                        completeListener.stopProgress();
                    }

                    @Override
                    public void onFailure(Call<DataSuggestion> call, Throwable t) {
                        Timber.e(t);
                        completeListener.onTaskFailure(t);
                        completeListener.stopProgress();
                    }
                });
            }
        }, delay);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}