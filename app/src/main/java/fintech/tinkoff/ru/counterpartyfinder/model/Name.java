package fintech.tinkoff.ru.counterpartyfinder.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 07.03.2018.
 */
@lombok.Data
public class Name extends RealmObject implements Serializable {
    private String full_with_opf;
    private String short_with_opf;
    private String latin;
    private String full;
    @SerializedName("short")
    private String shortName;
}
