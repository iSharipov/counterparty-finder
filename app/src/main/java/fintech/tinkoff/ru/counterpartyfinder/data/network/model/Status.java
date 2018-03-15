package fintech.tinkoff.ru.counterpartyfinder.data.network.model;

public enum Status {
    ACTIVE("действующая"),
    LIQUIDATING("ликвидируется"),
    LIQUIDATED("ликвидирована");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
