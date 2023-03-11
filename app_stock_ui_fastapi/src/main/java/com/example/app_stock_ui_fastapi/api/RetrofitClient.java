package com.example.app_stock_ui_fastapi.api;

import com.example.app_stock_ui_fastapi.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //private static final String BASE_URL = "http://10.0.2.2:8000";
    private static final String BASE_URL = "https://54.199.175.248";
    //private static final String BASE_URL = "http://54.199.175.248:8000";
    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    private RetrofitClient() throws Exception {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss") // 字串轉 Date (2023-02-25 07:38:39)
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new Utils().getUntrustOkHttpClient()) // 設定一個不安全的 OkHttpClient
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if(retrofitClient == null) {
            try {
                retrofitClient = new RetrofitClient();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return retrofitClient;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
