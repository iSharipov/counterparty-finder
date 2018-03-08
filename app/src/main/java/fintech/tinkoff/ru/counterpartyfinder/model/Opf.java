package fintech.tinkoff.ru.counterpartyfinder.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.EqualsAndHashCode;

/**
 * 07.03.2018.
 */
@EqualsAndHashCode(callSuper = true)
@lombok.Data
public class Opf extends RealmObject implements Serializable {
    private String type;
    private String code;
    private String full;
    @SerializedName("short")
    private String shortName;
}
