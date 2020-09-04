package com.example.orientation.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "Attendance")
public class SubjectData {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "SubName")
    private String subjectName;

    @ColumnInfo(name = "TotClass")
    private int totalClass;

    @ColumnInfo(name = "AttendCount")
    private int attendnum;

    @ColumnInfo(name = "Dates")
    ArrayList<String> dates;

    @ColumnInfo(name = "Attended")
    ArrayList<String> attend;

    public SubjectData(@NonNull String subjectName, int totalClass, int attendnum, ArrayList<String> dates,ArrayList<String> attend) {
        this.subjectName = subjectName;
        this.totalClass = totalClass;
        this.attendnum = attendnum;
        this.dates = dates;
        this.attend = attend;
    }

    @NonNull
    public String getSubjectName() {
        return subjectName;
    }

    public int getTotalClass() {
        return totalClass;
    }

    public int getAttendnum() {
        return attendnum;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public ArrayList<String> getAttend() {
        return attend;
    }
}
