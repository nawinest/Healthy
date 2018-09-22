package com.nawinc27.mac.healthy.Weight;

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

public class WeightAdapter extends ArrayAdapter<Weight> {
    List<Weight> weights = new ArrayList<Weight>();
    Context context;

    public WeightAdapter(Context context, int resource, List<Weight> object) {
        super(context, resource,object);
        this.weights = object;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View weightItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_weight_item,
                parent,
                false
        );
        TextView date = (TextView) weightItem.findViewById(R.id.weightItemdate);
        TextView weight = (TextView) weightItem.findViewById(R.id.weightItemWeight);
        TextView status =  weightItem.findViewById(R.id.weightItemStatus);

        date.setText(weights.get(position).getDate());
        weight.setText(weights.get(position).getWeight()+"");
        status.setText(weights.get(position).getStatus());


        return weightItem;
    }
}
