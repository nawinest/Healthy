package com.nawinc27.mac.healthy.Weight;

public class Weight {
    float weight;
    String date;
    String status;
    public Weight(){

    }
    public Weight(String date,float weight, String status) {
        this.weight = weight;
        this.date = date;
        this.status = status;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
