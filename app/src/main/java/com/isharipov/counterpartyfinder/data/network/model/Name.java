package com.isharipov.counterpartyfinder.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 07.03.2018.
 */
@lombok.Data
public class Name implements Serializable {
    @SerializedName("full_with_opf")
    private String fullWithOpf;
    @SerializedName("short_with_opf")
    private String shortWithOpf;
    private String latin;
    private String full;
    @SerializedName("short")
    private String shortName;
}
