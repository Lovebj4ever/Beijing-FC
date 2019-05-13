package com.example.dell.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class test_month extends AppCompatActivity {
    TextView ti;
    TextView ti2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<CostBean> monthlydata = (List<CostBean>) getIntent().getSerializableExtra("mlist");
        String c= getIntent().getStringExtra("mtotal");
        String n= getIntent().getStringExtra("mday");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_month);
        ti=findViewById(R.id.textView2);
        ti2=findViewById(R.id.textView3);
        int C= Integer.parseInt(c);
        int N=Integer.parseInt(n);
        int daily=C/N;
        ti.setText("当月记录总消费为："+C);
        ti2.setText("当月日均消费为："+daily);


    }
}
