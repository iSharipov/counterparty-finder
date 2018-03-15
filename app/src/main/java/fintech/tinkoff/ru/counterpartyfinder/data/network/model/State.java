package fintech.tinkoff.ru.counterpartyfinder.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 07.03.2018.
 */
@lombok.Data
public class State implements Serializable {
    private String status;
    @SerializedName("actuality_date")
    private Long actualityDate;
    @SerializedName("registration_date")
    private Long registrationDate;
    @SerializedName("liquidation_date")
    private Long liquidationDate;
}
