package com.isharipov.counterpartyfinder.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 07.03.2018.
 */
@lombok.Data
public class Opf implements Serializable {
    private String type;
    private String code;
    private String full;
    @SerializedName("short")
    private String shortName;
}
