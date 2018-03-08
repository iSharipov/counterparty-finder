package fintech.tinkoff.ru.counterpartyfinder.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.EqualsAndHashCode;

/**
 * 08.03.2018.
 */
@EqualsAndHashCode(callSuper = true)
@lombok.Data
public class DataAnswer extends RealmObject {
    @PrimaryKey
    private String uuid;

    private String value;
    @SerializedName("unrestricted_value")
    private String unrestrictedValue;
    private Data data;
}
