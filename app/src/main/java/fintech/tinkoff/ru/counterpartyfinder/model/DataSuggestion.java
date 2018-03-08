package fintech.tinkoff.ru.counterpartyfinder.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.EqualsAndHashCode;

/**
 * 08.03.2018.
 */
@EqualsAndHashCode(callSuper = true)
@lombok.Data
public class DataSuggestion extends RealmObject {
    @PrimaryKey
    private String uuid;

    private RealmList<DataAnswer> suggestions;
}
