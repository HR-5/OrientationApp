package com.example.orientation.model;

import java.util.ArrayList;

public class DetailResponse {
    public ArrayList<Schedule> schedule;
    public ArrayList<Food> food;
    public ArrayList<Department> department;
    public ArrayList<Sports> sports;

    public ArrayList<Food> getFood() {
        return food;
    }

    public void setFood(ArrayList<Food> food) {
        this.food = food;
    }

    public ArrayList<Department> getDepartment() {
        return department;
    }

    public void setDepartment(ArrayList<Department> department) {
        this.department = department;
    }

    public ArrayList<Sports> getSports() {
        return sports;
    }

    public void setSports(ArrayList<Sports> sports) {
        this.sports = sports;
    }

    public ArrayList<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Schedule> schedule) {
        this.schedule = schedule;
    }
}
