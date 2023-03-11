package com.example.app_stock_ui_fastapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.app_stock_ui_fastapi.R;
import com.example.app_stock_ui_fastapi.model.Stock;

import java.util.List;

public class StockAdapter extends BaseAdapter {

    private List<Stock> stockList;
    private Context context;

    public StockAdapter(Context context) {
        this.context = context;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public int getCount() {
        return (stockList == null)?0:stockList.size();
    }

    @Override
    public Object getItem(int i) {
        return (stockList == null)?null:stockList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return stockList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item, null);
            holder.chartImageView = (AppCompatImageView) convertView.findViewById(R.id.chartImageView);
            holder.textTitle = (TextView) convertView.findViewById(R.id.textTitle);
            holder.textlocation = (TextView) convertView.findViewById(R.id.textlocation);
            holder.textTime = (TextView) convertView.findViewById(R.id.textTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Stock stock = stockList.get(position);
        holder.textTitle.setText(stock.getSymbol());
        if(stock.getBest_buy_1() + stock.getBest_buy_2() + stock.getBest_buy_3() + stock.getBest_buy_4() > stock.getBest_sell_1() + stock.getBest_sell_2() + stock.getBest_sell_3() + stock.getBest_sell_4()) {
            holder.textlocation.setText("BUY");
        } else if(stock.getBest_buy_1() + stock.getBest_buy_2() + stock.getBest_buy_3() + stock.getBest_buy_4() < stock.getBest_sell_1() + stock.getBest_sell_2() + stock.getBest_sell_3() + stock.getBest_sell_4()) {
            holder.textlocation.setText("SELL");
        } else {
            holder.textlocation.setText("WAIT");
        }
        holder.textTime.setText("$" + stock.getPrices().get(stock.getPrices().size()-1));
        return convertView;
    }

    static class ViewHolder {
        AppCompatImageView chartImageView;
        TextView textTitle;
        TextView textlocation;
        TextView textTime;
    }
}
