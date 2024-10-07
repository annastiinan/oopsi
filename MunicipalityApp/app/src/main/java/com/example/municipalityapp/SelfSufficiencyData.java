package com.example.municipalityapp;

public class SelfSufficiencyData {

    private int year;
    private float selfSufficiency;

    public SelfSufficiencyData (int year, float selfSufficiency) {
        this.year = year;
        this.selfSufficiency = selfSufficiency;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getSelfSufficiency() {
        return selfSufficiency;
    }

    public void setSelfSufficiency(float selfSufficiency) {
        this.selfSufficiency = selfSufficiency;
    }
}
