package fintech.tinkoff.ru.counterpartyfinder.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 07.03.2018.
 */
@lombok.Data
public class Address extends RealmObject implements Serializable {
    private String value;
    @SerializedName("unrestricted_value")
    private String unrestrictedValue;
    private Data data;
}