package fintech.tinkoff.ru.counterpartyfinder.model;

public enum Branch {

    MAIN("головная организация"),
    BRANCH("филиал");

    private String description;

    Branch(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
