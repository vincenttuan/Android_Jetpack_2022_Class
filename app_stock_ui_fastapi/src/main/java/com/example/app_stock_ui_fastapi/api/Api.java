package com.example.app_stock_ui_fastapi.api;


import com.example.app_stock_ui_fastapi.model.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("bfp/get/{symbol}")
    Call<Stock> getSymbol(@Path("symbol") String symbol);

    @GET("bfp/query/all")
    Call<List<Stock>> queryAll();

    @GET("chatgpt/get/twii")
    Call<String> getTWII();

}
