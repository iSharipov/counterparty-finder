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
public class Name extends RealmObject implements Serializable {
    @SerializedName("full_with_opf")
    private String fullWithOpf;
    @SerializedName("short_with_opf")
    private String shortWithOpf;
    private String latin;
    private String full;
    @SerializedName("short")
    private String shortName;
}
