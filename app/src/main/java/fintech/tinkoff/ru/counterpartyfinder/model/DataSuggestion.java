package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;
import java.util.List;

import io.realm.annotations.PrimaryKey;

/**
 * 08.03.2018.
 */
@lombok.Data
public class DataSuggestion implements Serializable {
    @PrimaryKey
    private String uuid;

    private List<DataAnswer> suggestions;
}
