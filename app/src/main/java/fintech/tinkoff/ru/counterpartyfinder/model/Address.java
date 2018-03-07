package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 07.03.2018.
 */
@lombok.Data
public class Address extends RealmObject implements Serializable {
    private String value;
    private String unrestricted_value;
    private Data data;
}
