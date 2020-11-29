package com.devilpanda.sip.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_history")
public class UserHistory {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "amount")
    private Integer amount;
    @ColumnInfo(name = "total")
    private Integer total;
    @ColumnInfo(name = "percent")
    private Double percent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
