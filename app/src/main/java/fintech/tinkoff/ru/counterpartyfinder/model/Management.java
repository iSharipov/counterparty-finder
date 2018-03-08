package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.EqualsAndHashCode;

/**
 * 07.03.2018.
 */
@EqualsAndHashCode(callSuper = true)
@lombok.Data
public class Management extends RealmObject implements Serializable {

    private String name;
    private String post;
}
