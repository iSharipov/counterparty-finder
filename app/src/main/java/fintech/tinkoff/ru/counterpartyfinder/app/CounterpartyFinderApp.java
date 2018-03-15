package fintech.tinkoff.ru.counterpartyfinder.app;

import android.app.Application;

import fintech.tinkoff.ru.counterpartyfinder.BuildConfig;
import io.realm.Realm;
import timber.log.Timber;

/**
 * 07.03.2018.
 */

public class CounterpartyFinderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            //TODO plant your Production Tree
        }
        Realm.init(this);
    }
}
