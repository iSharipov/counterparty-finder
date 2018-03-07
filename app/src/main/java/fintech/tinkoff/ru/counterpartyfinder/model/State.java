package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.*;

/**
 * 07.03.2018.
 */
@lombok.Data
public class State extends RealmObject implements Serializable {
    private DataStatus status;
    private Long actuality_date;
    private Long registration_date;
    private Long liquidation_date;
}
