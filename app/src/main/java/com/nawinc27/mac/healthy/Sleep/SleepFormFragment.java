package com.nawinc27.mac.healthy.Sleep;

import android.app.DatePickerDialog;
import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nawinc27.mac.healthy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepFormFragment extends Fragment{
    EditText date;
    TextView dateField;
    Calendar mCurrentDate;
    int mYear, mMonth, mDay;
    String dayStr, monthStr;
    int id = 9999;
    SQLiteDatabase db;
    Sleep_info sl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleepform, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            id = bundle.getInt("id");
            Log.d("Sleep form fragment", "Id from bundle : " + Integer.toString(id));
            TextView sleep_date = ((TextView)getView().findViewById(R.id.date_sleep));
            EditText sleep_start = ((EditText)getView().findViewById(R.id.start_sleep));
            EditText sleep_wakeup = ((EditText)getView().findViewById(R.id.wakeup_time_sleep));

            db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
            String selectQuery = "SELECT * FROM sleep_table WHERE _id = " + id;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            //0: id , 1 :date , 2 :sleeptime ,3 : wakeuptime , 4 : duration
            sleep_date.setText(cursor.getString(1));
            sleep_start.setText(cursor.getString(2));
            sleep_wakeup.setText(cursor.getString(3));
        }



        dateField = getView().findViewById(R.id.date_sleep);
        mCurrentDate = Calendar.getInstance();
        mYear = mCurrentDate.get(Calendar.YEAR);
        mMonth = mCurrentDate.get(Calendar.MONTH);
        mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerPopup(dateField);
            }
        });

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
                String sleep_date = ((TextView)getView().findViewById(R.id.date_sleep)).getText().toString();
                String sleep_start = ((EditText)getView().findViewById(R.id.start_sleep)).getText().toString();
                String sleep_wakeup = ((EditText)getView().findViewById(R.id.wakeup_time_sleep)).getText().toString();
                Log.d("Sleep Form", "sleep_date : " + sleep_date);

                //create content
                ContentValues content = new ContentValues();
                content.put("date", sleep_date);
                content.put("sleeptime" , sleep_start );
                content.put("wakeuptime", sleep_wakeup);
                content.put("duration", getDurationTime(sleep_start,sleep_wakeup));
                Log.d("Sleep Form", "content values : " + content);


                //insert to SQLite database
                db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
                if (id == 9999){
                    db.insert("sleep_table",null,content);
                    Log.d("Sleep Form", "insert : " + db);
                }
                else{
                    db.update("sleep_table", content, "_id = " + id, null);
                    Log.d("Sleep Form", "update : " + db);
                }
                db.close();


                //if insert success replace fragment with SleepFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).commit();


            }
        });
    }


    public String getDurationTime(String time1 , String time2){
        int diff;
        String[] time_1 = time1.split(":");
        String[] time_2 = time2.split(":");
        int hour1 , minute1 , hour2 , minute2;
        hour1 = Integer.parseInt(time_1[0]);
        minute1 = Integer.parseInt(time_1[1]);

        hour2 = Integer.parseInt(time_2[0]);
        minute2 = Integer.parseInt(time_2[1]);

        int totaltime1 = hour1*60 + minute1;
        int totaltime2 = hour2*60 + minute2;

        String different = "";
        if(totaltime2 >= totaltime1) {
            diff = totaltime2 - totaltime1;
        }else{
            int day = 1440;
            diff = ((1440 - totaltime1) + totaltime2);
        }
        if(diff < 60){
            different = Math.round(diff%60) + "Minute";
        }else{
            different = Math.round(diff/60) + "." + Math.round(diff%60) + "Hour";
        }
        return  different;
    }

    private void datePickerPopup(final TextView field){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(month < 10){
                    monthStr = "0" + (month + 1);
                }else{
                    monthStr = (month + 1)+"";
                }
                if(dayOfMonth < 10){
                    dayStr  = "0" + dayOfMonth ;
                }else{
                    dayStr = dayOfMonth+"";
                }
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                field.setText(year+"-"+monthStr+"-"+dayStr);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
