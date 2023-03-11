package com.example.app_stock_ui_fastapi.api;

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

        // 若 https 不是安全的, 可以使用一個不安全的 TrustManager，該 TrustManager 將信任所有證書，從而允許您訪問不安全的 HTTPS 網站
        TrustManager[] trustAllCerts = new TrustManager[] {
                // 這個 X509TrustManager 實例的目的是覆蓋默認的安全檢查，使得這個 OkHttpClient 可以信任所有的 HTTPS 服務器證書，從而建立一個不安全的 HTTPS 連接。
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        // 空實作
                    }
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        // 空實作
                    }
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        // 建立一個 SSLContext 環境
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true) // 讓主機名稱永遠通過
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]) // 配置 SSL Socket
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss") // 字串轉 Date (2023-02-25 07:38:39)
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
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
