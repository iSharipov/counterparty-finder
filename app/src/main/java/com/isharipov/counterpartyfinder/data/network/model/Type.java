package com.isharipov.counterpartyfinder.data.network.model;

public enum Type {
    LEGAL("юридическое лицо"),
    INDIVIDUAL("индивидуальный предприниматель");

    private String description;

    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
