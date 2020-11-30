package com.devilpanda.sip.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_history")
public class UserHistory {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "action")
    private String action;
    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "amount")
    private Integer amount;

    public UserHistory(String action, String time, Integer amount) {
        this.action = action;
        this.time = time;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
