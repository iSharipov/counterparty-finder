package fintech.tinkoff.ru.counterpartyfinder.data.db.repository;

import android.os.AsyncTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;

/**
 * 09.03.2018.
 */

public final class BaseDao {
    private static final Map<Class<? extends RealmModel>, String> primaryKeyMap = new ConcurrentHashMap<>();

    public static <T extends RealmModel> void add(T t) {
        new AsyncModelAdder<>().execute(t);
    }

    public static <T extends RealmModel> T get(Realm realm, Class<T> clazz, String key) {
        return findByKey(realm, clazz, key);
    }

    public static <T extends RealmModel> T get(Realm realm, Class<T> clazz, Long key) {
        return findByKey(realm, clazz, key);
    }

    public static <T extends RealmModel> List<T> getAll(Realm realm, Class<T> clazz) {
        return realm.copyFromRealm(realm.where(clazz).findAllAsync());
    }

    private static <T extends RealmModel, Tkey> T findByKey(Realm realm, Class<T> clazz, Tkey key) {
        String primaryKey = getPrimaryKeyName(realm, clazz);
        if (primaryKey == null)
            return null;
        if (key instanceof String)
            return realm.where(clazz).equalTo(primaryKey, (String) key).findFirst();
        else
            return realm.where(clazz).equalTo(primaryKey, (Long) key).findFirst();
    }

    private static String getPrimaryKeyName(Realm realm, Class<? extends RealmModel> clazz) {
        String primaryKey = primaryKeyMap.get(clazz);
        if (primaryKey != null)
            return primaryKey;
        RealmObjectSchema schema = realm.getSchema().get(clazz.getSimpleName());
        if (schema == null || !schema.hasPrimaryKey()) {
            return null;
        }
        primaryKey = schema.getPrimaryKey();
        primaryKeyMap.put(clazz, primaryKey);
        return primaryKey;
    }

    private static class AsyncModelAdder<T extends RealmModel> extends AsyncTask<T, Void, Void> {
        @SafeVarargs
        protected final Void doInBackground(T... params) {
            try (Realm realm = Realm.getDefaultInstance()) {
                realm.executeTransaction(realm1 -> realm1.insertOrUpdate(params[0]));
            }
            return null;
        }
    }
}