package com.isharipov.counterpartyfinder.data.network.model;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 09.04.2018.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Location extends RealmObject implements Serializable {
    private Double geoLat;
    private Double geoLon;
}
