package fintech.tinkoff.ru.counterpartyfinder.data.network;

import fintech.tinkoff.ru.counterpartyfinder.data.network.model.DataSuggestion;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 08.03.2018.
 */

public interface DaDataService {
    @POST("/suggestions/api/4_1/rs/suggest/party")
    Call<DataSuggestion> getSuggestion(@Body DaDataBody body);
}
