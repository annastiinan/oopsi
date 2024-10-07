package com.example.municipalityapp;

public class EmploymentRateData {
    private int year;
    private float employmentRate;

    public EmploymentRateData (int year, float employmentRate) {
        this.year = year;
        this.employmentRate = employmentRate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getEmploymentRate() {
        return employmentRate;
    }

    public void setEmploymentRate(float employmentRate) {
        this.employmentRate = employmentRate;
    }
}
