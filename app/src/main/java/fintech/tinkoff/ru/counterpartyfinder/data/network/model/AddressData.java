package fintech.tinkoff.ru.counterpartyfinder.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 09.04.2018.
 */
@lombok.Data
public class AddressData implements Serializable {
    @SerializedName("postal_code")
    private String postalCode;
    private String country;
    @SerializedName("region_fias_id")
    private String regionFiasId;
    @SerializedName("region_kladr_id")
    private String regionKladrId;
    @SerializedName("region_with_type")
    private String regionWithType;
    @SerializedName("street_fias_id")
    private String streetFiasId;
    @SerializedName("street_kladr_id")
    private String streetKladrId;
    @SerializedName("street_with_type")
    private String streetWithType;

    @SerializedName("geo_lat")
    private Double geoLat;
    @SerializedName("geo_lon")
    private Double geoLon;
}