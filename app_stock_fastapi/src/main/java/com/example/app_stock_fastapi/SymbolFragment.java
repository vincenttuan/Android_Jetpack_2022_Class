package com.example.app_stock_fastapi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app_stock_fastapi.api.Api;
import com.example.app_stock_fastapi.api.RetrofitClient;
import com.example.app_stock_fastapi.model.Stock;
import com.example.app_stock_fastapi.service.StockService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SymbolFragment extends Fragment {
    private Bundle bundle;
    private StockService stockService;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        stockService = new StockService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_symbol, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvSymbol = view.findViewById(R.id.tv_symbol);
        TextView info = view.findViewById(R.id.info);

        String symbol = bundle.getString("symbol");
        tvSymbol.setText(symbol);
        stockService.getSymbol(symbol, (stock) -> {
            info.setText(stock.toString());
        });
        Log.i("stock", "info: " + info.getText().toString());

    }
}