package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

//import io.realm.RealmObject;

/**
 * 27.02.2018.
 */

public class Data extends RealmObject implements Serializable {

    @PrimaryKey
    private String uuid;
}
