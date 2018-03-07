package fintech.tinkoff.ru.counterpartyfinder.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 07.03.2018.
 */
@lombok.Data
public class Opf extends RealmObject implements Serializable {
    private String type;
    private String code;
    private String full;
    @SerializedName("short")
    private String shortName;
}
