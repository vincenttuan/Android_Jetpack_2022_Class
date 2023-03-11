package com.example.app_stock_ui_fastapi.util;

import com.example.app_stock_ui_fastapi.model.Stock;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class Utils {
    public OkHttpClient getUntrustOkHttpClient() {
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
        try {
            // 建立一個 SSLContext 環境
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .hostnameVerifier((hostname, session) -> true) // 讓主機名稱永遠通過
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]) // 配置 SSL Socket
                    .build();
            return okHttpClient;
        } catch(Exception e) {

        }
        return null;
    }

    public String buySellWAIT(Stock stock) {
        if(stock.getBest_buy_1() + stock.getBest_buy_2() + stock.getBest_buy_3() + stock.getBest_buy_4() > stock.getBest_sell_1() + stock.getBest_sell_2() + stock.getBest_sell_3() + stock.getBest_sell_4()) {
            return "BUY";
        } else if(stock.getBest_buy_1() + stock.getBest_buy_2() + stock.getBest_buy_3() + stock.getBest_buy_4() < stock.getBest_sell_1() + stock.getBest_sell_2() + stock.getBest_sell_3() + stock.getBest_sell_4()) {
            return "SELL";
        }
        return "WAIT";

    }
}
