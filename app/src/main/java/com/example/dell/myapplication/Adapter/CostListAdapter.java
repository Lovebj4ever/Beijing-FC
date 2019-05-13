package com.example.dell.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.myapplication.CostBean;
import com.example.dell.myapplication.R;

import java.util.List;

public class CostListAdapter extends BaseAdapter {
    private List<CostBean> mList;
    private Context mcontext;
    private LayoutInflater mLayoutInflater;
    public CostListAdapter(Context context, List<CostBean> list){
        mList=list;
        mcontext=context;
        mLayoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=mLayoutInflater.inflate(R.layout.list_item,null);
            viewHolder.mTvcostdate=(TextView)convertView.findViewById(R.id.tv_date);
            viewHolder.mTvcosttittle=(TextView)convertView.findViewById(R.id.tv_tittle);
            viewHolder.mTvcostmoney=(TextView)convertView.findViewById(R.id.tv_cost);
            convertView.setTag(viewHolder);


        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        CostBean bean=mList.get(position);
        viewHolder.mTvcostdate.setText(bean.costDate);
        viewHolder.mTvcosttittle.setText(bean.costTittle);
        viewHolder.mTvcostmoney.setText(bean.costMoney);
        return convertView;
    }
    private static class ViewHolder{
        public TextView mTvcosttittle;
        public TextView mTvcostdate;
        public TextView mTvcostmoney;
    }
}
