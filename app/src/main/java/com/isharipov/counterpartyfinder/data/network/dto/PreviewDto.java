package com.isharipov.counterpartyfinder.data.network.dto;

import lombok.Data;

/**
 * 08.03.2018.
 */
@Data
public class PreviewDto {
    private String hid;
    private String counterpartyName;
    private String inn;
    private String address;
    private Boolean isFavorite;
}
