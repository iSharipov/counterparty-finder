package fintech.tinkoff.ru.counterpartyfinder.watcher;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.Timer;
import java.util.TimerTask;

import fintech.tinkoff.ru.counterpartyfinder.listener.AsyncTaskCompleteListener;
import fintech.tinkoff.ru.counterpartyfinder.model.DataSuggestion;
import fintech.tinkoff.ru.counterpartyfinder.rest.DaDataBody;
import fintech.tinkoff.ru.counterpartyfinder.rest.DaDataRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08.03.2018.
 */

public class SearchTextWatcher implements TextWatcher {

    private Timer timer = new Timer();
    private final int counter;
    private AsyncTaskCompleteListener<DataSuggestion> completeListener;

    public SearchTextWatcher(int counter, AsyncTaskCompleteListener<DataSuggestion> completeListener) {
        this.counter = counter;
        this.completeListener = completeListener;
    }

    @Override
    public void afterTextChanged(final Editable s) {
        timer.cancel();
        timer = new Timer();
        long delay = 500;
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               DaDataRestClient.getInstance().suggestAsync(new DaDataBody(s.toString(), counter), new Callback<DataSuggestion>() {
                                   @Override
                                   public void onResponse(Call<DataSuggestion> call, Response<DataSuggestion> response) {
                                       completeListener.onTaskComplete(response.body());
                                   }

                                   @Override
                                   public void onFailure(Call<DataSuggestion> call, Throwable t) {
                                       System.out.println(t);
                                   }
                               });
                           }
                       },
                delay);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
