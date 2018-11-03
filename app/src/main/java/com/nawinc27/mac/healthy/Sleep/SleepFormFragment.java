package com.nawinc27.mac.healthy.Sleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nawinc27.mac.healthy.R;

public class SleepFormFragment extends Fragment{
    private EditText date;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleepform, container , false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        Button backPage  = getView().findViewById(R.id.backBtnSleepForm);
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button savebtn = getView().findViewById(R.id.save_btn_sleepForm);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Sleep Form", "submitted information sleep");
                String sleep_date = ((EditText)getView().findViewById(R.id.date_sleep)).getText().toString();
                String sleep_start = ((EditText)getView().findViewById(R.id.start_sleep)).getText().toString();
                String sleep_wakeup = ((EditText)getView().findViewById(R.id.wakeup_time_sleep)).getText().toString();


                Log.d("Sleep Form", "sleep_date : " + sleep_date);

                //create content
                ContentValues content = new ContentValues();
                content.put("date", sleep_date);
                content.put("sleeptime" , sleep_start );
                content.put("wakeuptime", sleep_wakeup);
                content.put("duration", "9.00");
                Log.d("Sleep Form", "content values : " + content);


                //insert to SQLite database
                SQLiteDatabase db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
                db.insert("sleep_table",null,content);
                db.close();
                Log.d("Sleep Form", "insert : " + db);

                //if insert success replace fragment with SleepFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).commit();


            }
        });
    }
}
