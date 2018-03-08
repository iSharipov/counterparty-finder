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
public class State extends RealmObject implements Serializable {
    private String status;
    @SerializedName("actuality_date")
    private Long actualityDate;
    @SerializedName("registration_date")
    private Long registrationDate;
    @SerializedName("liquidation_date")
    private Long liquidationDate;
}
