package fintech.tinkoff.ru.counterpartyfinder.app;

import android.app.Application;

import io.realm.Realm;

/**
 * 07.03.2018.
 */

public class CounterpartyFinderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
