package com.devilpanda.sip.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.List;

@Entity(tableName = "user")
public class User {
    @Ignore
    public static final int[] hoursActivity =  {1, 3, 5, 7};
    @Ignore
    private static User instance;

    @PrimaryKey(autoGenerate = true)
    private long id;

    private Gender gender;
    @ColumnInfo(name = "weight")
    private Integer weight;
    @ColumnInfo(name = "activity")
    private Integer physicalActivity;

    @ColumnInfo(name = "water_drank_today")
    private Integer waterDrankToday = 0;
    @ColumnInfo(name = "water_total")
    private Integer waterTotal = 0;

    private Integer waterLeft = 0;
    private Double percent = 0.0;

    public User(){}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(Integer physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public void setWaterTotal(Integer waterTotal) {
        this.waterTotal = waterTotal;
    }

    public void countDailyWaterAmount() {
        if (gender == Gender.MAN)
            this.waterTotal = Math.round((weight * 0.03f + physicalActivity * 0.5f) * 1000);
        if (gender == Gender.WOMAN)
            this.waterTotal = Math.round((weight * 0.025f + physicalActivity * 0.4f) * 1000);
    }

    public void addDrankWater(int ml) {
        this.waterDrankToday += ml;
        countWaterLeft();
    }

    public void removeDrankWater(int ml) {
        if (waterDrankToday - ml >= 0) {
            this.waterDrankToday -= ml;
            countWaterLeft();
        } else {
            this.waterDrankToday = 0;
            countWaterLeft();
        }
    }

    public void countWaterLeft() {
        this.waterLeft = waterTotal - waterDrankToday;
        this.percent = (double) (waterDrankToday * 100.0d / waterTotal);
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void selectPhysicalActivity(int pos) {
        this.physicalActivity = hoursActivity[pos];
    }

    public Integer getWaterDrankToday() {
        return this.waterDrankToday;
    }

    public void setWaterDrankToday(Integer waterDrankToday) {
        this.waterDrankToday = waterDrankToday;
    }

    public Integer getWaterTotal() {
        return waterTotal;
    }

    public Integer getWaterLeft() {
        return waterLeft;
    }

    public Double getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return "User{" +
                "gender=" + gender +
                ", weight=" + weight +
                ", physicalActivity=" + physicalActivity +
                ", waterTotal=" + waterTotal +
                ", waterDrank=" + waterDrankToday +
                ", waterLeft=" + waterLeft +
                ", percent=" + percent +
                '}';
    }

    public void setWaterLeft(Integer waterLeft) {
        this.waterLeft = waterLeft;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

}
