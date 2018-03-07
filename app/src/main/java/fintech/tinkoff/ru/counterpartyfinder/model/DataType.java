package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 07.03.2018.
 */

public class DataType extends RealmObject implements Serializable {
    private String dataType;

    public Type getType() {
        return Type.valueOf(dataType);
    }

    public void setBranchType(Type type) {
        this.dataType = type.toString();
    }
}

enum Type {
    LEGAL("юридическое лицо"),
    INDIVIDUAL("индивидуальный предприниматель");

    private String description;

    Type(String description) {
        this.description = description;
    }
}
