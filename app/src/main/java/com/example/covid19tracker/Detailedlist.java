package com.example.covid19tracker;

public class Detailedlist {
    String date;
    int total,today;

    public Detailedlist(String date, int total, int today) {
        this.date = date;
        this.total = total;
        this.today = today;
    }

    public String getDate() {
        return date;
    }

    public int getTotal() {
        return total;
    }

    public int getToday() {
        return today;
    }
}
