package com.nawinc27.mac.healthy.Sleep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.nawinc27.mac.healthy.MenuFragment;
import com.nawinc27.mac.healthy.R;
import java.util.ArrayList;
import java.util.Comparator;

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


        //get database
        final SQLiteDatabase db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        //query from database by table sleep_table [cursor]
        Cursor pointer_query = db.rawQuery("select * from sleep_table", null);
        while (pointer_query.moveToNext()){
            int id = pointer_query.getInt(0);
            String date = pointer_query.getString(1);
            String sleeptime = pointer_query.getString(2);
            String wakeuptime = pointer_query.getString(3);
            String duration  = pointer_query.getString(4);
            String sleeptowake = sleeptime + " - " + wakeuptime;
            sleeps.add(new Sleep_info(id, date, sleeptowake , duration)); // add to sleeps [ array of sleep_info]
        }


        //sort arraylist by use .sort from arrayadapter to sort by date
        adapter.sort(new Comparator<Sleep_info>() {
            @Override
            public int compare(Sleep_info o1, Sleep_info o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        //set event listener for update user's history sleep time
        sleep_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sleep_info sleep_item = (Sleep_info) parent.getItemAtPosition(position);
                //create bundle for pass argument to next Fragment
                Bundle bundle = new Bundle();
                bundle.putInt("id", sleep_item.getId());
                SleepFormFragment sleepform = new SleepFormFragment();
                sleepform.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, sleepform)
                        .addToBackStack(null)
                        .commit();
                Log.d("Sleep Fragment", "item click and send bundle");

            }
        });
        adapter.notifyDataSetChanged(); //change list view automatic


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

        Button backbtn = getView().findViewById(R.id.back_sleep);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        // optional :: clear history from local (SQLite) and Arraylist (sleep_list)
        Button clr_history = getView().findViewById(R.id.clear_database);
        clr_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("delete from sleep_table");
                sleeps.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
