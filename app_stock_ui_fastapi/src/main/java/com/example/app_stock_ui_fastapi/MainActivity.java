package com.example.app_stock_ui_fastapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_stock_ui_fastapi.adapter.StockAdapter;
import com.example.app_stock_ui_fastapi.api.Api;
import com.example.app_stock_ui_fastapi.api.RetrofitClient;
import com.example.app_stock_ui_fastapi.model.Stock;
import com.example.app_stock_ui_fastapi.util.Utils;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tvHolder, tvSell, tvBuy, tvChatGPT;
    private AppCompatImageButton searchImageBtn;
    private ListView listView;
    private Context context;
    private Api api;
    private List<Stock> stocks;
    private StockAdapter stockAdapter;
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
        tvChatGPT = findViewById(R.id.tv_chatgpt);
        listView = findViewById(R.id.list_view);
        searchImageBtn = findViewById(R.id.search_image_btn);

        // 配置適配器
        stockAdapter = new StockAdapter(context);
        listView.setAdapter(stockAdapter);

        // 事件註冊
        searchImageBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("請輸入股票代號");
            EditText input = new EditText(context);
            input.setHint("請輸入股票代號");
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setGravity(Gravity.CENTER);
            builder.setView(input);

            // 設定 button
            builder.setPositiveButton("OK", (dialog, which) -> {
                String symbol = input.getText().toString();
                api.addSymbol(symbol).enqueue(new Callback<Stock>() {
                    @Override
                    public void onResponse(Call<Stock> call, Response<Stock> response) {
                        Stock stock = response.body();
                        Toast.makeText(context, stock.getSymbol() + ": " + new Utils().buySellWAIT(stock), Toast.LENGTH_SHORT).show();
                        stockAdapter.setStockList(null);
                        stockAdapter.notifyDataSetChanged();
                        init();
                        //根據 buy, Sell, WAIT 自動按下對應 view 元件
                        switch (new Utils().buySellWAIT(stock)) {
                            case "BUY":
                                findViewById(R.id.tv_buy).performClick();
                                break;
                            case "SELL":
                                findViewById(R.id.tv_sell).performClick();
                                break;
                            case "WAIT":
                                findViewById(R.id.tv_holder).performClick();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Stock> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.setNeutralButton("Refresh", (dialog, which) -> {
                stockAdapter.setStockList(null);
                stockAdapter.notifyDataSetChanged();
                init();
            });

            builder.show();

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // 按下圖片
                view.findViewById(R.id.chartImageView).setOnClickListener(v -> {
                    Stock stock = stocks.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(stock.getSymbol() + "股價走勢圖");
                    ImageView imageView = new ImageView(context);
                    String path = "https://54.199.175.248/bfp/chart/" + stock.getSymbol();

                    Picasso picasso = new Picasso.Builder(context)
                            .downloader(new OkHttp3Downloader(new Utils().getUntrustOkHttpClient()))
                            .build();

                    picasso.load(path).into(imageView);
                    builder.setView(imageView);
                    builder.show();
                });
                // 自動按下(一定要加)
                view.findViewById(R.id.chartImageView).performClick();
            }
        });

        tvChatGPT.setOnClickListener(view -> {
            ProgressDialog progressDialog = new ProgressDialog(view.getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            api.getTWII().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressDialog.dismiss();
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("ChatGPT 明日大盤指數建議")
                            .setMessage(response.body())
                            .create();
                    dialog.show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        });

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

                    // 註冊事件
                    tvHolder.setOnClickListener((v) -> {
                        stockAdapter.setStockList(holders);
                        stockAdapter.notifyDataSetChanged();
                    });

                    tvSell.setOnClickListener((v) -> {
                        stockAdapter.setStockList(sells);
                        stockAdapter.notifyDataSetChanged();
                    });

                    tvBuy.setOnClickListener((v) -> {
                        stockAdapter.setStockList(buys);
                        stockAdapter.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });
    }
}