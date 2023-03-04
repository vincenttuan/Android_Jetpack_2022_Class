package com.example.app_stock_ui_fastapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.app_stock_ui_fastapi.api.Api;
import com.example.app_stock_ui_fastapi.api.RetrofitClient;
import com.example.app_stock_ui_fastapi.model.Stock;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tvHolder, tvSell, tvBuy;
    private Context context;
    private Api api;
    private List<Stock> stocks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        init();
    }

    public void init() {
        api = RetrofitClient.getInstance().getApi();
        tvHolder = findViewById(R.id.tv_holder);
        tvSell = findViewById(R.id.tv_sell);
        tvBuy = findViewById(R.id.tv_buy);
         // 初始抓取觀望,賣出與買進的股票數量
        api.queryAll().enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                stocks = response.body();
                if(stocks.size() > 0) {
                    // 觀望的股票資料
                    List<Stock> holders = stocks.stream()
                            .filter(s -> s.getBest_buy_1() + s.getBest_buy_2() + s.getBest_buy_3() + s.getBest_buy_4() == s.getBest_sell_1() + s.getBest_sell_2() + s.getBest_sell_3() + s.getBest_sell_4())
                            .collect(Collectors.toList());
                    // 賣出的股票資料
                    List<Stock> sells = stocks.stream()
                            .filter(s -> s.getBest_buy_1() + s.getBest_buy_2() + s.getBest_buy_3() + s.getBest_buy_4() < s.getBest_sell_1() + s.getBest_sell_2() + s.getBest_sell_3() + s.getBest_sell_4())
                            .collect(Collectors.toList());
                    // 買進的股票資料
                    List<Stock> buys = stocks.stream()
                            .filter(s -> s.getBest_buy_1() + s.getBest_buy_2() + s.getBest_buy_3() + s.getBest_buy_4() > s.getBest_sell_1() + s.getBest_sell_2() + s.getBest_sell_3() + s.getBest_sell_4())
                            .collect(Collectors.toList());

                    tvHolder.setText(holders.size() + "");
                    tvSell.setText(sells.size() + "");
                    tvBuy.setText(buys.size() + "");
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });
    }
}