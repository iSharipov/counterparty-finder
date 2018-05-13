package com.isharipov.counterpartyfinder.app;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.isharipov.counterpartyfinder.BuildConfig;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * 07.03.2018.
 */

public class CounterpartyFinderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            //TODO plant your Production Tree
        }
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
