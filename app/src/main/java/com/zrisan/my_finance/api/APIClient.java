package com.zrisan.my_finance.api;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIClient {
    private static final String BASE_URL = "https://backend-app-money-production.up.railway.app/api/v1/";
    private static APIService apiService;

    public static APIService getApiService(@Nullable Context context) {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(getOkHttpClient(context))
                    .build();

            apiService = retrofit.create(APIService.class);
        }
        return apiService;
    }

    private static OkHttpClient getOkHttpClient(@Nullable Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(chain -> {
            Request originalRequest = chain.request();

            String token = getTokenFromPreferences(context);  // Obtiene el token actualizado de las preferencias

            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClientBuilder.build();
    }

    private static String getTokenFromPreferences(@Nullable Context context) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
            return preferences.getString("token", null);
        } else {
            return null;
        }
    }
}
