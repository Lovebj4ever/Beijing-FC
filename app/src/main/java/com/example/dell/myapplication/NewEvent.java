package com.example.dell.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewEvent extends AppCompatActivity {

    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    EditText theme,content;
    RadioButton layout3_button;
    database1 specialday_database;
    TextView layout3_text;
    TextView layout5_text;
    TextView Layout2_text;


    class RadioListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Toast.makeText(NewEvent.this, "选择"+checkedId, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.calcu:
                SQLiteDatabase db = specialday_database.getWritableDatabase();
                ContentValues values = new ContentValues();
                //将数值存入数据库
                values.put("name",theme.getText().toString());
                values.put("content",content.getText().toString());
                values.put("date",theme.getText().toString());
                values.put("time",layout5_text.getText().toString());
                values.put("type",layout3_text.getText().toString());



                Cursor cursor = db.query("event",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        //遍历对象
                        String name = cursor.getString(cursor.getColumnIndex("theme"));
                        String content = cursor.getString(cursor.getColumnIndex("content"));
                        String type = cursor.getString(cursor.getColumnIndex("type"));
                        String date = cursor.getString(cursor.getColumnIndex("date"));
                        String time = cursor.getString(cursor.getColumnIndex("time"));

                        System.out.println("你好"+name);
                        Log.d("NewEvent",content);
                        Log.d("NewEvent",type);
                        Log.d("NewEvent",date);
                        Log.d("NewEvent",time);


                    }while(cursor.moveToNext());
                }
                cursor.close();


                //返回主界面
                Intent intent1 = new Intent(NewEvent.this, SpecialDay.class);
                startActivity(intent1);




                break;
            default:
        }
        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.topbar,menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        layout1 = (LinearLayout) findViewById(R.id.Layout1);
        layout2 = (LinearLayout) findViewById(R.id.Layout2);
        layout3 = (LinearLayout) findViewById(R.id.Layout3);
        layout4 = (LinearLayout) findViewById(R.id.Layout4);
        layout5 = (LinearLayout) findViewById(R.id.Layout5);
        layout3_text = (TextView) findViewById(R.id.layout3_textView);
        layout5_text = (TextView) findViewById(R.id.layout5_textView);
        Layout2_text = (TextView) findViewById(R.id.Layout2_text);

        theme = (EditText) findViewById(R.id.theme);
        content = (EditText) findViewById(R.id.content);
        Toolbar topbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topbar);
        //创建数据库
        specialday_database = new database1(this,"SpecialDay.db",null,1);




        layout2.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(NewEvent.this);
                localBuilder.setTitle("选择日期");


                final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.activity_datepick, null);
                localBuilder.setView(layout_alert);

                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        DatePicker datepicker1= (DatePicker) layout_alert.findViewById(R.id.datepick);

                        int layout_y=datepicker1.getYear();
                        int layout_m=datepicker1.getMonth()+1;
                        int layout_d=datepicker1.getDayOfMonth();
                        System.out.println("y:"+layout_y+" m:"+layout_m+" d:"+layout_d);
                        Layout2_text.setText(layout_y+"-"+layout_m+"-"+layout_d); //  获取时间


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {

                    }
                }).create().show();
            }

        });

        layout3.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(NewEvent.this);
                localBuilder.setTitle("选择类型");

                final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.radio_button, null);
                localBuilder.setView(layout_alert);


                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        RadioGroup day_type = (RadioGroup) layout_alert.findViewById(R.id.daytype);
                        day_type.setOnCheckedChangeListener(new RadioListener());
                        int id = day_type.getCheckedRadioButtonId();
                        System.out.println(id+"is");
                        layout3_button = (RadioButton) layout_alert.findViewById(id);


                        //获取文本到文本框
                        String str = layout3_button.getText().toString();
                        System.out.println(str);
                        layout3_text.setText(str);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {

                    }
                }).create().show();
            }
        });

        layout4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

        layout5.setOnClickListener(new View.OnClickListener(){



                @Override
                public void onClick(View v) {
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(NewEvent.this);
                    localBuilder.setTitle("选择时间");


                    final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.time_pick, null);
                    localBuilder.setView(layout_alert);

                    localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                        {
                            TimePicker timepicker1= (TimePicker) layout_alert.findViewById(R.id.timepicker);

                            int layout_b = timepicker1.getBaseline();
                            int layout_h = 0;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                layout_h = timepicker1.getHour();
                            }
                            int layout_mi = 0;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                layout_mi = timepicker1.getMinute();
                            }

                            //System.out.println("y:"+layout_b+" m:"+layout_h+" d:"+layout_mi);
                            //  获取时间
                            if(layout_h<10 && layout_mi<10){
                                layout5_text.setText("0"+layout_h+":0"+layout_mi);
                            }
                            else if(layout_h<10 && layout_mi>=10){
                                layout5_text.setText("0"+layout_h+":"+layout_mi);
                            }
                            else if(layout_h>=10 && layout_mi<10){
                                layout5_text.setText(layout_h+":0"+layout_mi);
                            }
                            else{
                                layout5_text.setText(layout_h+":"+layout_mi);

                            }




                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                        {

                        }
                    }).create().show();
            }
        });



    }

}
