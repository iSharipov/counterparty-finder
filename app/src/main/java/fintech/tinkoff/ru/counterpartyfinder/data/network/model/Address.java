package fintech.tinkoff.ru.counterpartyfinder.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 07.03.2018.
 */
@lombok.Data
public class Address implements Serializable {
    private String value;
    @SerializedName("unrestricted_value")
    private String unrestrictedValue;
    private AddressData data;
}
