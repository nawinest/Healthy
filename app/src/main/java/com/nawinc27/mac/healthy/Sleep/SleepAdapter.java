package com.nawinc27.mac.healthy.Sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.nawinc27.mac.healthy.R;
import java.util.ArrayList;
import java.util.List;

public class SleepAdapter extends ArrayAdapter<Sleep_info> {
    Context context;
    ArrayList<Sleep_info> sleeps = new ArrayList<Sleep_info>();


    public SleepAdapter(@NonNull Context context, int resource, @NonNull List<Sleep_info> objects) {
        super(context, resource, objects);
        this.context = context;
        this.sleeps = (ArrayList<Sleep_info>) objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View sleep_item;
        sleep_item = LayoutInflater.from(context).inflate(R.layout.fragment_sleepitem, parent,false);
        TextView date = (TextView) sleep_item.findViewById(R.id.date_sleep_item);
        TextView sleeptime = (TextView) sleep_item.findViewById(R.id.sleeptime_item);
        TextView durationtime = (TextView) sleep_item.findViewById(R.id.durationtime_item);
        Sleep_info item = sleeps.get(position);
        date.setText(item.getDate());
        sleeptime.setText(item.getTime());
        durationtime.setText(item.getSleep_time());
        return sleep_item;
    }
}
