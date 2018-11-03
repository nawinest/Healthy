package com.nawinc27.mac.healthy;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SQLiteDatabase myDB = openOrCreateDatabase("my.db", MODE_PRIVATE,null);
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
