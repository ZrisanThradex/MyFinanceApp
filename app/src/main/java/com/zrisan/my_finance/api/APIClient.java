package com.zrisan.my_finance.api;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
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
                    .client(getOkHttpClient(context))  // Agrega el OkHttpClient con el interceptor
                    .build();

            apiService = retrofit.create(APIService.class);
        }
        return apiService;
    }

    private static OkHttpClient getOkHttpClient(@Nullable Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (context != null) {
            String token = getTokenFromPreferences(context);  // Obtiene el token de tus preferencias

            // Crea el interceptor con el token
            AuthInterceptor authInterceptor = new AuthInterceptor(token);

            httpClientBuilder.addInterceptor(authInterceptor);  // Agrega el interceptor al cliente
        }

        return httpClientBuilder.build();
    }

    private static String getTokenFromPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
        return preferences.getString("token", null);
    }
}
