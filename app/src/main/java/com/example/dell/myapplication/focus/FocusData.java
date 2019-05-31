package com.example.dell.myapplication.focus;

public class FocusData {
    private String title;
    private int year;
    private int month;
    private int day;
    private String hour;
    private String min;
    private String sec;
    public FocusData(String title,int year,int month,int  day,String hour,String min,String sec){
        this.title=title;
        this.year=year;
        this.day=day;
        this.month = month;
        this.hour=hour;
        this.min=min;
        this.sec=sec;
    }
    public String getTitle(){
        return title;
    }

    public int getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMin() {
        return min;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getSec() {
        return sec;
    }
}
