package com.example.dell.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class totalCost extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int total=0;
        List<CostBean>alldata= (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalcost);
        String s=null;
        int costMoney=0;
        if(alldata!=null) {
            for (int i = 0; i < alldata.size(); i++) {
                CostBean costBean ;
                costBean = alldata.get(i);
                costMoney = Integer.parseInt(costBean.costMoney);
                total = total + costMoney;
            }
            s = Integer.toString(total);
            Toast.makeText(totalCost.this, s, Toast.LENGTH_SHORT).show();
        }
        tv=findViewById(R.id.txt);
        tv.setText("总消费"+s);
//        List<Date>dates=new ArrayList<>();
//        String d=null;
//        String m=null;
//        String y=null;
//        Date costdate=null;
//        if(alldata!=null) {
//            for (int i = 0; i < alldata.size(); i++) {
//                CostBean costBean ;
//                costBean = alldata.get(0);
//            try {
//                costdate=getDate(costBean.costDate);
//                dates.add(costdate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            d=Integer.toString(costdate.getYear()+1900);
//            d= Integer.toString(costdate.getMonth()+1);
//            d=Integer.toString(costdate.getDate());
//            getday返回是周几
//            Toast.makeText(totalCost.this, d, Toast.LENGTH_SHORT).show();
//            }
//
//        }

}

    public Date getDate(String str) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd"));
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(date.getTime());
    }
    public int getmonthlycost(int n,List<CostBean> alldata) throws ParseException {
        Date costdate;
        int total=0;
        ArrayList<CostBean> list=new  ArrayList<CostBean>();
        if(alldata!=null) {
            for (int i = 0; i < alldata.size(); i++) {
                CostBean costBean;
                costBean = alldata.get(i);
                costdate=getDate(costBean.costDate);
                if (costdate.getMonth()+1==n) {
                    total = total + Integer.parseInt(costBean.costMoney);
                }
//                Toast.makeText(monthlycost.this, d, Toast.LENGTH_SHORT).show();
            }

        }
        return total;
    }

}