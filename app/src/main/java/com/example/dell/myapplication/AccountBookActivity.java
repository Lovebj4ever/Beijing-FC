package com.example.dell.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.myapplication.database.DatabaseHelper;
import com.example.dell.myapplication.Adapter.CostListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountBookActivity extends AppCompatActivity {
    private List<CostBean>mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountbook);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper=new DatabaseHelper(this);

        mCostBeanList=new ArrayList<>();
        ListView costList=findViewById(R.id.lv_main);
        initCostDate();
        adapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AccountBookActivity.this);
                LayoutInflater inflater=LayoutInflater.from(AccountBookActivity.this);
                View viewDialog=inflater.inflate(R.layout.new_cost_data,null);
                final EditText title = viewDialog.findViewById(R.id.ed_cost_title);
                final EditText money = viewDialog.findViewById(R.id.ed_cost_money);
                final DatePicker datePicker=viewDialog.findViewById(R.id.dp_cost_date);
                builder.setView(viewDialog);
                builder.setTitle("新的消费记录~");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (title.getText().toString().equals("")||money.getText().toString().equals("")) {
                            Toast.makeText(AccountBookActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            CostBean costBean = new CostBean();
                        costBean.costTittle = title.getText().toString();
                        costBean.costMoney = money.getText().toString();
                        costBean.costDate = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" +
                                datePicker.getDayOfMonth();
                        mDatabaseHelper.insertcost(costBean);
                        mCostBeanList.add(costBean);
                        adapter.notifyDataSetChanged();
                    }
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
            }
        });
    }
    private void initCostDate() {
        Cursor cursor = mDatabaseHelper.getallcost();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CostBean costBean = new CostBean();
                costBean.costTittle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);

            }
            cursor.close();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_chart) {
            Intent intent=new Intent(AccountBookActivity.this,ChartActivity.class);
            intent.putExtra("cost_list",(Serializable)mCostBeanList);
            startActivity(intent);
            return true;

        }
        if (id == R.id.action_total) {
            Intent intent=new Intent(AccountBookActivity.this,totalCost.class);
            intent.putExtra("cost_list",(Serializable)mCostBeanList);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_monthly) {
            Intent intent=new Intent(AccountBookActivity.this,monthlycost.class);
            intent.putExtra("cost_list",(Serializable)mCostBeanList);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
