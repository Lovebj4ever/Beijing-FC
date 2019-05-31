package com.example.dell.myapplication.focus;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.dell.myapplication.NewEvent;
import com.example.dell.myapplication.R;
import com.example.dell.myapplication.database.database1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class focus extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private String[] label={"Data Structure","Daydreaming"};
    FloatingActionButton new_label;
    Button start;
    TextView hour;
    TextView minute;
    TextView second;
    SeekBar bar;
    ImageView tree;
    TextView test1;
    int length;
    int same_length;
    database1 dbHelper;
    //    GifImageView loading;
    private boolean buttonText = false;
    private long longmillTime;
    int barlength = 0;
    boolean isStart = false;
    String h=null;
    String ih = null;
    String m=null;
    String im = null;
    String s=null;
    String is = null;
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> year = new ArrayList<>();
    ArrayList<String> month = new ArrayList<>();
    ArrayList<String> day = new ArrayList<>();
    ArrayList<String> hh = new ArrayList<>();
    ArrayList<String> mm = new ArrayList<>();
    ArrayList<String> ss = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();
    class RadioListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Toast.makeText(focus.this, "选择"+checkedId, Toast.LENGTH_SHORT);
        }
    }


    //通过handler更新时间
    private Handler uiHandle = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    deleteTimeUsed();
                    if(isStart == true){
                        upBar();
                        upDateTime();
                    }
                    changeImage();
                    uiHandle.sendEmptyMessageDelayed(2, 1000);
                    if(bar.getProgress() == 0 && isStart == true){

                        setButtonText();
                        isStart = false;
                        tree.setImageResource(R.mipmap.tree1);
                        bar.setOnTouchListener(new View.OnTouchListener(){
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        this.removeMessages(2);

                        AlertDialog.Builder localBuilder = new AlertDialog.Builder(focus.this);
                        localBuilder.setTitle("请选择专注标签");

                        final LinearLayout layout_alert= (LinearLayout) getLayoutInflater().inflate(R.layout.focus_label, null);
                        localBuilder.setView(layout_alert);
                        data.clear();
                        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                            {

                                dbHelper.getWritableDatabase();
                                SQLiteDatabase d = dbHelper.getReadableDatabase();

                                ContentValues values = new ContentValues();
                                Calendar calendar = Calendar.getInstance();
                                int year_now = calendar.get(Calendar.YEAR);
                                int month_now = calendar.get(Calendar.MONTH)+1;
                                int day_now = calendar.get(Calendar.DAY_OF_MONTH);
                                EditText e = layout_alert.findViewById(R.id.new_label);
                                String a = e.getText().toString();
                                Toast.makeText(focus.this ,a,Toast.LENGTH_SHORT).show();
                                values.put("title",a);
                                values.put("year",year_now);
                                values.put("month",month_now);
                                values.put("day",day_now);
                                values.put("hour",ih);
                                values.put("min",im);
                                values.put("sec",is);

                                d.insert("focus",null,values);
                                values.clear();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                            {

                            }
                        }).create().show();
                        SQLiteDatabase d = dbHelper.getWritableDatabase();
                        Cursor cursor = d.query("focus",null,null,null,null,null,null);
                        if(cursor!=null){
                            int i=0;
                            while(cursor.moveToNext()){
                                String temp =cursor.getString(cursor.getColumnIndex("title"));
                                if(i==0){
                                    data.add(temp);
                                    i++;
                                }
                                else{
                                    for(int j=i;j!=0;j--){
                                        String comp = data.get(j-1);
                                        if(comp.equals(temp)){
                                            break;
                                        }
                                        data.add(temp);
                                        i++;
                                    }
                                }
                            }
                        }
                        cursor.close();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(focus.this,android.R.layout.simple_list_item_1,data);
                        ListView listView = (ListView)layout_alert.findViewById(R.id.all_label);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String b = data.get(position);
                                System.out.println(b);

                                dbHelper.getWritableDatabase();
                                SQLiteDatabase d = dbHelper.getReadableDatabase();

                                ContentValues values = new ContentValues();
                                Calendar calendar = Calendar.getInstance();
                                int year_now = calendar.get(Calendar.YEAR);
                                int month_now = calendar.get(Calendar.MONTH)+1;
                                int day_now = calendar.get(Calendar.DAY_OF_MONTH);
                                values.put("title",b);
                                values.put("year",year_now);
                                values.put("month",month_now);
                                values.put("day",day_now);
                                values.put("hour",ih);
                                values.put("min",im);
                                values.put("sec",is);
                                System.out.println(is);
                                d.insert("focus",null,values);
                                values.clear();
                                Intent i = new Intent(focus.this,history_focus.class);
                                startActivity(i);
                                finish();
                            }
                        });

                    }

                    break;
                default:
                    break;
            }
        }
    };

    //改变按钮文字
    public void setButtonText(){
        if (buttonText) {
            start.setText("开始专注");
            buttonText = false;

        } else {//开始计时
            start.setText("停止专注");
            buttonText = true;
        }
    }

    //设置专注时间
    public void setFocusTime(){
        length = bar.getProgress();
        longmillTime = length*1000;//设置时间
        hour.setText(getHour());
        minute.setText(getMin());
        second.setText(getSce());
        h = getHour();
        m = getMin();
        s = getSce();
    }
    //更新时间
    public void upDateTime(){
        hour.setText(getHour());
        minute.setText(getMin());
        second.setText(getSce());
    }


    public void upBar(){
        if (longmillTime%1000 == 0){
            bar.setProgress(bar.getProgress()-1);
        }
//        else if(bar.getProgress() == 0 && isStart == true){
//                setButtonText();
//                isStart = false;
//                uiHandle.removeMessages(2);
//        }
    }

    //减少时间
    public void deleteTimeUsed() {
        if (longmillTime>=1000){
            longmillTime -= 1000;


        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.topbar,menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        Toolbar toolbar = findViewById(R.id.clock);
        setSupportActionBar(toolbar);
        dbHelper = new database1(this,"SpecialDay.db",null,1);
        test1 = findViewById(R.id.test1);
        start = findViewById(R.id.start);
        hour = findViewById(R.id.hourTv);
        minute = findViewById(R.id.minuteTv);
        second = findViewById(R.id.secondTv);
        bar = findViewById(R.id.bar);
        new_label = findViewById(R.id.labeled);
        tree = findViewById(R.id.tree);
       // SQLiteDatabase d = dbHelper.getWritableDatabase();
        System.out.println("1");
        //Cursor cursor = d.query("focus",null,null,null,null,null,null);
       /* if(cursor!=null){
            while(cursor.moveToNext()){

                title.add(cursor.getString(cursor.getColumnIndex("title")));
                year.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("year"))));
                month.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("month"))));
                day.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("day"))));
                String temp = cursor.getString(cursor.getColumnIndex("sec"));
                hh.add(cursor.getString(cursor.getColumnIndex("hour")));
                mm.add( cursor.getString(cursor.getColumnIndex("min")));
                ss.add( cursor.getString(cursor.getColumnIndex("sec")));
                System.out.println(temp);
                data.add(cursor.getString(cursor.getColumnIndex("title"))+" "+String.valueOf(cursor.getInt(cursor.getColumnIndex("year")))+" "+String.valueOf(cursor.getInt(cursor.getColumnIndex("month")))+" "+String.valueOf(cursor.getInt(cursor.getColumnIndex("day"))));
            }

        }
        cursor.close()*/
//        new_label.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder localBuilder = new AlertDialog.Builder(focus.this);
//                localBuilder.setTitle("选择标签");
//
//                final LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.label,null);
//                localBuilder.setView(layout);
//                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
//                    {
//                        RadioGroup day_type = (RadioGroup) layout.findViewById(R.id.labeltype);
//                        day_type.setOnCheckedChangeListener(new focus.RadioListener());
//                    }
//                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
//                    {
//
//                    }
//                }).create().show();
//            }
//        });

        bar.setOnSeekBarChangeListener(this);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!buttonText) {//开始计时
                    same_length = length;
                    ih = getHour();
                    im = getMin();
                    is = getSce();
                    setButtonText();
                    startTime();
                    bar.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    isStart = true;
                } else {//终止计时
                    setButtonText();
                    tree.setImageResource(R.mipmap.tree1);
                    bar.setProgress(0);
                    uiHandle.removeMessages(2);
                    longmillTime = 0;
                    hour.setText("00");
                    minute.setText("00");
                    second.setText("00");
                    bar.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    isStart = false;
                }

                //时间到弹出窗口记录
                if (hour.getText().equals("00") && minute.getText().equals("00") && second.getText().equals("00")) {

                }
            }
        });


//        new_label.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(un_colck.this, label_record.class);
//            }
//        });
    }

    private void startTime() {
        uiHandle.sendEmptyMessageDelayed(2, 1000);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setFocusTime();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**得到时*/
    public String getHour() {
        long min = (longmillTime) / 3600000;
        return min < 10 ? "0" + min : min + "";
    }
    /**得到分*/
    public String getMin() {
        long min = ((longmillTime) / 60000) % 60;
        return min < 10 ? "0" + min : min + "";
    }
    /**得到秒*/
    public String getSce() {
        long sec = (longmillTime / 1000) % 60;
        return sec < 10 ? "0" + sec : sec + "";
    }


    public void changeImage(){
        if(bar.getProgress() == same_length*5/6){
            tree.setImageResource(R.mipmap.tree2);
        }else if(bar.getProgress() == same_length*4/6){
            tree.setImageResource(R.mipmap.tree3);
        }else if(bar.getProgress() == same_length*3/6){
            tree.setImageResource(R.mipmap.tree4);
        }else if(bar.getProgress() == same_length*2/6){
            tree.setImageResource(R.mipmap.tree5);
        }else if(bar.getProgress() == same_length*1/6){
            tree.setImageResource(R.mipmap.tree6);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.calcu:
                Intent intent1 = new Intent(focus.this, Timer.class);
                startActivity(intent1);
                break;

            case R.id.history:

                Intent intent2 = new Intent(focus.this, history_focus.class);
                intent2.putExtra("data",data);

                startActivity(intent2);
                break;

            default:

        }
        return true;
    }

}