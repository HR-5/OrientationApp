package com.example.orientation.Attendance;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.orientation.model.SubjectData;

public class Async extends AsyncTask<Void,Void, SubjectData> {

    String subname;
    Application application;
    Context context;
    AttDialog dialogClass;

    public Async(String subname, Application application, Context context) {
        this.subname = subname;
        this.application = application;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogClass = new AttDialog(context);
    }

    @Override
    protected SubjectData doInBackground(Void... voids) {
        AttendanceDb db = AttendanceDb.getDatabase(application);
        AttendanceDao attendanceDao = db.attendanceDao();
        return attendanceDao.getSub(subname);
    }

    @Override
    protected void onPostExecute(SubjectData subjectData) {
        super.onPostExecute(subjectData);
        dialogClass.setSubjectData(subjectData);
        dialogClass.show();
    }
}
