package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

/**
 * 07.03.2018.
 */

public class DataType implements Serializable {
    private String dataType;

    public Type getType() {
        return Type.valueOf(dataType);
    }

    public void setBranchType(Type type) {
        this.dataType = type.toString();
    }
}

