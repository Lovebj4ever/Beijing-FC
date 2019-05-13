package com.example.dell.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView ivEventsCounter;
    TextView tvEventsCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivEventsCounter= (ImageView) findViewById(R.id.ivEventsCounter);
        ivEventsCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SpecialDay.class);
                startActivity(intent);
            }
        });
        tvEventsCounter= (TextView) findViewById(R.id.tvEventsCounter);
        tvEventsCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SpecialDay.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.main_title1:

        }
    }
}
