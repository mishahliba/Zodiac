package com.zodiac.client.model;

/**
 * Class represents validated prerequisites for retrieving zodiac sign.
 */
public class ValidInput {

    private int day;
    private int month;

    public ValidInput(int day, int month) {
        this.day = day;
        this.month = month;
    }

    public int getDay() {

        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
