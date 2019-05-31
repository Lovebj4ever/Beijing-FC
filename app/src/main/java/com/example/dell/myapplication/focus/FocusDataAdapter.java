package com.example.dell.myapplication.focus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class FocusDataAdapter extends ArrayAdapter<FocusData> {
    private int resourceId;
    public FocusDataAdapter(Context context, int textViewResourceId, List<FocusData> objects){
        super(context, textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        FocusData data = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView focusTitle = (TextView)view.findViewById(R.id.focus_title);
        TextView focusTime = (TextView)view.findViewById(R.id.focus_time);
        TextView focusDate = (TextView)view.findViewById(R.id.focus_date);
        focusTitle.setText(data.getTitle());
        String year = String.valueOf(data.getYear());
        String month = String.valueOf(data.getMonth());
        String day = String.valueOf(data.getDay());
        String hour = data.getHour();
        String min = data.getMin();
        String sec = data.getSec();
        focusTime.setText(hour+"-"+min+"-"+sec);
        focusDate.setText(year+"-"+month+"-"+day);
        return view;
    }
}
