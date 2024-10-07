package com.example.municipalityapp;

public class PopulationChangeData {

    private int year;
    private int populationChange;
    public PopulationChangeData(int year, int populationChange) {
        this.year = year;
        this.populationChange = populationChange;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getPopulationChange() {
        return populationChange;
    }
    public void setPopulationChange(int populationChange) {
        this.populationChange = populationChange;
    }
}