package com.nawinc27.mac.healthy;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //open database or create for use app at first time
        SQLiteDatabase myDB = openOrCreateDatabase("my.db", MODE_PRIVATE,null);

        //execute command to create table "sleep_table"
        //this table[_id, date, sleep time , wakeup time , duration]
        myDB.execSQL("CREATE TABLE IF NOT EXISTS sleep_table(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date VARCHAR(255), " +
                "sleeptime VARCHAR(255)," +
                "wakeuptime VARCHAR(255), " +
                "duration VARCHAR(255))");

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .commit();
        }
    }
}
