package com.nawinc27.mac.healthy.Sleep;

public class Sleep_info {
    int id;
    String time ;
    String sleep_time ;
    String date;



    public Sleep_info(int id, String date, String time , String duration){
        this.id = id;
        this.date = date;
        this.time = time;
        this.sleep_time = duration;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(String sleep_time) {
        this.sleep_time = sleep_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
