package com.devilpanda.sip.model;

public class User {
    private static User instance;

    private boolean sex;
    private Double weight;
    private Double physicalActivity;

    private Integer waterTotal;
    private Integer waterDrank;
    private Integer waterLeft;
    private Double percent;

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }


}
