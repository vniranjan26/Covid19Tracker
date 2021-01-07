package com.example.covid19tracker;

public class Statelist {
    String state,updateTime,deltaconfirm,deltarecover,deltadeath;
    int confirm,recover,death,active;

    public Statelist(String state, String updateTime, String deltaconfirm, String deltarecover, String deltadeath, int confirm, int recover, int death) {
        this.state = state;
        this.updateTime = updateTime;
        this.deltaconfirm = deltaconfirm;
        this.deltarecover = deltarecover;
        this.deltadeath = deltadeath;
        this.confirm = confirm;
        this.recover = recover;
        this.death = death;
        active=confirm-(recover+death);
    }

    public String getState() {
        return state;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getDeltaconfirm() {
        return deltaconfirm;
    }

    public String getDeltarecover() {
        return deltarecover;
    }

    public String getDeltadeath() {
        return deltadeath;
    }

    public int getConfirm() {
        return confirm;
    }

    public int getRecover() {
        return recover;
    }

    public int getDeath() {
        return death;
    }

    public int getActive() {
        return active;
    }
}
