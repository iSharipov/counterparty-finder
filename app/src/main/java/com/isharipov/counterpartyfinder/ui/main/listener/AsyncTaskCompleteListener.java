package com.isharipov.counterpartyfinder.ui.main.listener;

/**
 * 08.03.2018.
 */

public interface AsyncTaskCompleteListener<T> {
    void onTaskComplete(T result);

    void onTaskFailure(Throwable t);

    void startProgress();

    void stopProgress();
}
