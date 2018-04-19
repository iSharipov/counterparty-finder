package com.isharipov.counterpartyfinder.ui.main.watcher;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Timer;
import java.util.TimerTask;

import com.isharipov.counterpartyfinder.data.network.DaDataBody;
import com.isharipov.counterpartyfinder.data.network.DaDataRestClient;
import com.isharipov.counterpartyfinder.data.network.model.DataSuggestion;
import com.isharipov.counterpartyfinder.ui.main.listener.AsyncTaskCompleteListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * 08.03.2018.
 */

public class SearchTextWatcher implements TextWatcher {

    private Timer timer = new Timer();
    private final int counter;
    private final AsyncTaskCompleteListener<DataSuggestion> completeListener;

    public SearchTextWatcher(int counter, AsyncTaskCompleteListener<DataSuggestion> completeListener) {
        this.counter = counter;
        this.completeListener = completeListener;
    }

    @Override
    public void afterTextChanged(final Editable s) {
        makeQuery(s);
    }

    private void makeQuery(Editable s) {
        final Handler handler = new Handler();
        timer.cancel();
        timer = new Timer();
        long delay = 1000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(completeListener::startProgress);
                DaDataBody daDataBody = new DaDataBody(s.toString(), counter);
                DaDataRestClient.getInstance().suggestAsync(daDataBody, new Callback<DataSuggestion>() {
                    @Override
                    public void onResponse(@NonNull Call<DataSuggestion> call, @NonNull Response<DataSuggestion> response) {
                        completeListener.onTaskComplete(response.body());
                        completeListener.stopProgress();
                    }

                    @Override
                    public void onFailure(@NonNull Call<DataSuggestion> call, @NonNull Throwable t) {
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