package fintech.tinkoff.ru.counterpartyfinder.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * 08.03.2018.
 */
@lombok.Data
public class DataAnswer {

    private String value;
    @SerializedName("unrestricted_value")
    private String unrestrictedValue;
    private Data data;
}
