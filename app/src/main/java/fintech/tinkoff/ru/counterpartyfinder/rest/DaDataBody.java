package fintech.tinkoff.ru.counterpartyfinder.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 08.03.2018.
 */

@Data
@AllArgsConstructor
public class DaDataBody {
    private String query;
    private int count;
}
