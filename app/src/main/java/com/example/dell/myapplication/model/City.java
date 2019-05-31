package com.example.dell.myapplication.model;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport {
    private int Id;
    private int cityCode;
    private String cityName;
    private int provinceId;
    public int getId(){
        return Id;
    }
    public void setId(int id){
        this.Id=id;
    }
    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
