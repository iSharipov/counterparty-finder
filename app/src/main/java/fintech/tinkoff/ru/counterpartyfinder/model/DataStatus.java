package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 07.03.2018.
 */

public class DataStatus implements Serializable {
    private String status;

    public Status getStatus() {
        return Status.valueOf(status);
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

}

