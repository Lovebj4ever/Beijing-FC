package com.example.dell.myapplication.Util;

import android.text.TextUtils;

import com.example.dell.myapplication.model.City;
import com.example.dell.myapplication.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    public static boolean provinceResponse(String response){
        if(TextUtils.isEmpty(response)){
            try {
                JSONArray provinces=new JSONArray(response);
                for(int i=0;i<provinces.length();i++){
                    JSONObject object=provinces.getJSONObject(i);
                    Province pro=new Province();
                    pro.setProvinceName(object.getString("name"));
                    pro.setProvinceCode(object.getInt("id"));
                    pro.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean cityResponse(String response, int provinceId){
        if(TextUtils.isEmpty(response)){
            try {
                JSONArray cities=new JSONArray(response);
                for(int i=0;i<cities.length();i++){
                    JSONObject object=cities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(object.getString("name"));
                    city.setCityCode(object.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
