package com.isharipov.counterpartyfinder.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 08.03.2018.
 */
@lombok.Data
public class DataAnswer implements Serializable{

    private String value;
    @SerializedName("unrestricted_value")
    private String unrestrictedValue;
    private Data data;
}
