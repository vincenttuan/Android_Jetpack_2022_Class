package com.example.app_stock_fastapi.service;

import android.util.Log;

import com.example.app_stock_fastapi.api.Api;
import com.example.app_stock_fastapi.api.RetrofitClient;
import com.example.app_stock_fastapi.model.Stock;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockService {

    private Api api;

    public StockService() {
        api = RetrofitClient.getInstance().getApi();
    }

    public void getSymbol(String symbol, Consumer<Stock> consumer) {
        api.getSymbol(symbol).enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                consumer.accept(response.body());
                Log.i("stock", response.body().toString());
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                Log.i("stock", t.toString());
            }
        });
    }

    public void queryAll(Consumer<List<Stock>> consumer) {
        api.queryAll().enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                consumer.accept(response.body());
                Log.i("stock", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                Log.i("stock", t.toString());
            }
        });
    }

}
