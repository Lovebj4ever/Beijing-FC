package com.example.dell.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Display extends AppCompatActivity {
    database1 dbHelper = new database1(this,"SpecialDay.db",null,1);
    ArrayList<String> temp = new ArrayList<>();
    String[] key;
    String output1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        TextView t1 = (TextView) findViewById(R.id.t1);
        TextView t2 = (TextView) findViewById(R.id.t2);
        TextView t3 = (TextView) findViewById(R.id.t3);
        TextView t4 = (TextView) findViewById(R.id.t4);
        TextView t5 = (TextView) findViewById(R.id.t5);
        String output1 = intent.getStringExtra("name");
        String output2 = intent.getStringExtra("content");
        String output3 = intent.getStringExtra("type");
        String output4 = intent.getStringExtra("time");
        String output5 = intent.getStringExtra("date");
        t1.setText(output1);
        t2.setText(output2);
        t3.setText(output3);
        t4.setText(output4);
        t5.setText(output5);

    }
}
