package com.nawinc27.mac.healthy.Sleep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.nawinc27.mac.healthy.R;
import java.util.ArrayList;

public class SleepFragment extends Fragment {
    ArrayList<Sleep_info> sleeps = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container , false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        final ListView sleep_list = getView().findViewById(R.id.sleeptime_list);
        final SleepAdapter adapter = new SleepAdapter(
                getActivity(),
                R.layout.fragment_sleepitem,
                sleeps
        );

        sleep_list.setAdapter(adapter);
        adapter.clear();

        SQLiteDatabase db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        Cursor pointer_query = db.rawQuery("select * from sleep_table", null);
        while (pointer_query.moveToNext()){
            String date = pointer_query.getString(1);
            String sleeptime = pointer_query.getString(2);
            String wakeuptime = pointer_query.getString(3);
            String duration  = pointer_query.getString(4);
            String sleeptowake = sleeptime + " - " + wakeuptime;

            sleeps.add(new Sleep_info(date, sleeptowake , duration));
        }

        adapter.notifyDataSetChanged();


        Button addbtn = getView().findViewById(R.id.add_sleep_btn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button backbtm = getView().findViewById(R.id.back_sleep);
    }
}
