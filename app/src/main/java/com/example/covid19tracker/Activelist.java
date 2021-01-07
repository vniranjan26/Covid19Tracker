package com.example.covid19tracker;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Activelist {
    String date;
    int totalconfirmed,totaldeceased,totalrecovered,active_case,alive;

    public Activelist(String date, int totalconfirmed, int totaldeceased, int totalrecovered) {
        this.date = date;
        this.totalconfirmed = totalconfirmed;
        this.totaldeceased = totaldeceased;
        this.totalrecovered = totalrecovered;
        alive=(totaldeceased+totalrecovered);
        active_case = totalconfirmed-alive;
    }

    public String getDate() {
        return date;
    }

    public int getActive_case() {
        return active_case;
    }

    public Serializable getPercentage()
    {
        if(alive==0|active_case==0)
            return 0.00;
        else
            return  new DecimalFormat("#.##").format((double) (active_case*100)/totalconfirmed);
    }


}
