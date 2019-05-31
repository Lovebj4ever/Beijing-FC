package com.example.dell.myapplication.focus;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dell.myapplication.DataAdapter;
import com.example.dell.myapplication.R;
import com.example.dell.myapplication.account.CostBean;
import com.example.dell.myapplication.database.database1;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class history_focus extends AppCompatActivity {
    FocusDataAdapter adapter;
    database1 dbHelper;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<FocusData> new_data = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> year = new ArrayList<>();
    ArrayList<String> month = new ArrayList<>();
    ArrayList<String> day = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_focus);
        dbHelper = new database1(this,"SpecialDay.db",null,1);
        SQLiteDatabase d =dbHelper.getWritableDatabase();
        Cursor cursor = d.query("focus",null,null,null,null,null,null);
        if(cursor!=null){
            while(cursor.moveToNext()){

                title.add(cursor.getString(cursor.getColumnIndex("title")));
                year.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("year"))));
                month.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("month"))));
                day.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("day"))));
                String temp = cursor.getString(cursor.getColumnIndex("sec"));
                time.add(cursor.getString(cursor.getColumnIndex("hour"))+" "+cursor.getString(cursor.getColumnIndex("min"))+" "+cursor.getString(cursor.getColumnIndex("sec")));
               /* mm.add( cursor.getString(cursor.getColumnIndex("min")));
                ss.add( cursor.getString(cursor.getColumnIndex("sec")));*/
                System.out.println(temp);
                data.add("Title: "+cursor.getString(cursor.getColumnIndex("title"))+" \n"+String.valueOf(cursor.getInt(cursor.getColumnIndex("year")))+"-"+String.valueOf(cursor.getInt(cursor.getColumnIndex("month")))+"-"+String.valueOf(cursor.getInt(cursor.getColumnIndex("day")))+"   "+cursor.getString(cursor.getColumnIndex("hour"))+":"+cursor.getString(cursor.getColumnIndex("min"))+":"+cursor.getString(cursor.getColumnIndex("sec")));
            }

        }
        cursor.close();
        /*adapter = new FocusDataAdapter(history_focus.this,R.layout.focus_data,new_data);
        ListView listview = (ListView) findViewById(R.id.history_list);
        listview.setAdapter(adapter);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(history_focus.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) findViewById(R.id.history_list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                String temp = data.remove(position);
                String t = title.remove(position);
                String[] args = {String.valueOf(t)};
                SQLiteDatabase db =dbHelper.getWritableDatabase();
                db.delete("focus","title=?",args);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }
}
