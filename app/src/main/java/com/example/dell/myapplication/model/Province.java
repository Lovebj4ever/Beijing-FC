package com.example.dell.myapplication.model;

import org.litepal.crud.LitePalSupport;

public class Province extends LitePalSupport {
    private int Id;
    private int pCode;
    private String pName;
    public int getId(){
        return Id;
    }
    public void setId(int id){
        this.Id=id;
    }
    public int getProvinceCode(){
        return pCode;
    }
    public void setProvinceCode(int pc){
        this.pCode=pc;
    }
    public String getProvinceName(){
        return pName;
    }
    public void setProvinceName(String pn){
        this.pName=pn;
    }
}
