package fintech.tinkoff.ru.counterpartyfinder.data.db.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * 09.03.2018.
 */

public final class BaseDao {
    private static final Map<Class<? extends RealmModel>, String> primaryKeyMap = new ConcurrentHashMap<>();

    public static <T extends RealmModel> void add(Realm realm, T t) {
        realm.beginTransaction();
        realm.insertOrUpdate(t);
        realm.commitTransaction();
    }

    public static <T extends RealmModel> T get(Realm realm, Class<T> clazz, String key) {
        return findByKey(realm, clazz, key);
    }

    public static <T extends RealmModel> T get(Realm realm, Class<T> clazz, Long key) {
        return findByKey(realm, clazz, key);
    }

    public static <T extends RealmModel> List<T> getAll(Class<T> clazz) {
        try (Realm realm = Realm.getDefaultInstance()) {
            return getAll(realm, clazz);
        }
    }

    public static <T extends RealmModel> List<T> getAll(Realm realm, Class<T> clazz) {
        return realm.copyFromRealm(realm.where(clazz).findAllAsync());
    }

    public static <T extends RealmModel> List<T> getAllByFieldsSorted(Realm realm, Class<T> clazz, String[] fields, Sort[] sorts) {
        return realm.copyFromRealm(realm.where(clazz).sort(fields, sorts).findAllAsync());
    }

    public static <T extends RealmModel> List<T> getAllByFieldSorted(Realm realm, Class<T> clazz, String field, Sort sort) {
        return getAllByFieldsSorted(realm, clazz, new String[]{field}, new Sort[]{sort});
    }

    public static <T extends RealmModel> void remove(Realm realm, Class<T> clazz, String key) {
        realm.beginTransaction();
        String primaryKey = getPrimaryKeyName(realm, clazz);
        RealmResults<T> result = realm.where(clazz).equalTo(primaryKey, key).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    private static <T extends RealmModel, Tkey> T findByKey(Realm realm, Class<T> clazz, Tkey key) {
        String primaryKey = getPrimaryKeyName(realm, clazz);
        if (primaryKey == null)
            return null;
        if (key instanceof String) {
            return realm.where(clazz).equalTo(primaryKey, (String) key).findFirst();
        } else {
            return realm.where(clazz).equalTo(primaryKey, (Long) key).findFirst();
        }
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
}