package com.example.app_stock_ui_fastapi.model;

import java.util.Date;
import java.util.List;

public class Stock {
    private Integer id; // 16,
    private String symbol; // 2330
    private Integer best_buy_1; // 1,
    private Integer best_buy_2; // 0,
    private Integer best_buy_3; // 0,
    private Integer best_buy_4; // 1,
    private Integer best_sell_1; // 0,
    private Integer best_sell_2; // 0,
    private Integer best_sell_3; // 0,
    private Integer best_sell_4; // 0,
    private Date create_time; // "2023-02-11 06:06:25"
    private List<String> transaction_time;
    private List<String> prices;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getBest_buy_1() {
        return best_buy_1;
    }

    public void setBest_buy_1(Integer best_buy_1) {
        this.best_buy_1 = best_buy_1;
    }

    public Integer getBest_buy_2() {
        return best_buy_2;
    }

    public void setBest_buy_2(Integer best_buy_2) {
        this.best_buy_2 = best_buy_2;
    }

    public Integer getBest_buy_3() {
        return best_buy_3;
    }

    public void setBest_buy_3(Integer best_buy_3) {
        this.best_buy_3 = best_buy_3;
    }

    public Integer getBest_buy_4() {
        return best_buy_4;
    }

    public void setBest_buy_4(Integer best_buy_4) {
        this.best_buy_4 = best_buy_4;
    }

    public Integer getBest_sell_1() {
        return best_sell_1;
    }

    public void setBest_sell_1(Integer best_sell_1) {
        this.best_sell_1 = best_sell_1;
    }

    public Integer getBest_sell_2() {
        return best_sell_2;
    }

    public void setBest_sell_2(Integer best_sell_2) {
        this.best_sell_2 = best_sell_2;
    }

    public Integer getBest_sell_3() {
        return best_sell_3;
    }

    public void setBest_sell_3(Integer best_sell_3) {
        this.best_sell_3 = best_sell_3;
    }

    public Integer getBest_sell_4() {
        return best_sell_4;
    }

    public void setBest_sell_4(Integer best_sell_4) {
        this.best_sell_4 = best_sell_4;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public List<String> getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(List<String> transaction_time) {
        this.transaction_time = transaction_time;
    }

    public List<String> getPrices() {
        return prices;
    }

    public void setPrices(List<String> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", best_buy_1=" + best_buy_1 +
                ", best_buy_2=" + best_buy_2 +
                ", best_buy_3=" + best_buy_3 +
                ", best_buy_4=" + best_buy_4 +
                ", best_sell_1=" + best_sell_1 +
                ", best_sell_2=" + best_sell_2 +
                ", best_sell_3=" + best_sell_3 +
                ", best_sell_4=" + best_sell_4 +
                ", create_time=" + create_time +
                ", transaction_time=" + transaction_time +
                ", prices=" + prices +
                '}';
    }
}
