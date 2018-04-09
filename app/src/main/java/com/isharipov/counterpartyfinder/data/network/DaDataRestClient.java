package com.isharipov.counterpartyfinder.data.network;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import com.isharipov.counterpartyfinder.BuildConfig;
import com.isharipov.counterpartyfinder.data.network.model.DataSuggestion;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 08.03.2018.
 */

public class DaDataRestClient {
    private static final String BASE_URL = "https://suggestions.dadata.ru";

    private static volatile DaDataRestClient instance;

    private DaDataService apiService;

    private DaDataRestClient() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
            Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Token ".concat(BuildConfig.DADATA_API_KEY))
                    .build();
            return chain.proceed(request);
        }).addInterceptor(loggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(DaDataService.class);
    }

    /**
     * Get default instance of {@code DaDataRestClient}.
     *
     * @return an instance of DaDataRestClient.
     */
    public static DaDataRestClient getInstance() {
        DaDataRestClient localInstance = instance;
        if (localInstance == null) {
            synchronized (DaDataRestClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DaDataRestClient();
                }
            }
        }
        return localInstance;
    }

    /**
     * Get suggestion asynchronously.
     *
     * @param body     an object that need to be passed in the body of the request.
     * @param callback a Retrofit callback.
     */
    public void suggestAsync(DaDataBody body, Callback<DataSuggestion> callback) {
        Call<DataSuggestion> suggestionSync = apiService.getSuggestion(body);
        suggestionSync.enqueue(callback);
    }

    /**
     * Get suggestion synchronously.
     *
     * @param body an object that need to be passed in the body of the request.
     * @return
     */
    public DataSuggestion suggestSync(DaDataBody body) throws IOException {
        Call<DataSuggestion> suggestion = apiService.getSuggestion(body);
        return suggestion.execute().body();
    }
}
