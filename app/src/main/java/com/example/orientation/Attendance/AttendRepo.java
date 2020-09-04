package com.example.orientation.Attendance;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.orientation.model.SubjectData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AttendRepo {
    private AttendanceDao attendanceDao;
    private LiveData<List<SubjectData>> data;

    AttendRepo(Application application) {
        AttendanceDb db = AttendanceDb.getDatabase(application);
        attendanceDao = db.attendanceDao();
        data = attendanceDao.getDataLive();
    }

    LiveData<List<SubjectData>> getAllWords() {
        return data;
    }

    void insert(SubjectData word) {
        AttendanceDb.databaseWriteExecutor.execute(() -> {
            attendanceDao.insert(word);
        });
    }

    void update(SubjectData word) {
        AttendanceDb.databaseWriteExecutor.execute(() -> {
            attendanceDao.update(word);
        });
    }

    int getTot(){
        final int[] sum = {0};
        AttendanceDb.databaseWriteExecutor.execute(() -> {
             List<Integer> tot = attendanceDao.getTot();
            for(int i=0;i<tot.size();i++){
                sum[0] +=tot.get(i);
            }
        });
        return sum[0];
    }
    int getAtt(){
        final int[] sum = {0};
        AttendanceDb.databaseWriteExecutor.execute(() -> {
            List<Integer> tot = attendanceDao.getNum();
            for(int i=0;i<tot.size();i++){
                sum[0] +=tot.get(i);
            }
        });
        return sum[0];
    }

    void delete(SubjectData word) {
        AttendanceDb.databaseWriteExecutor.execute(() -> {
            attendanceDao.delete(word);
        });
    }

    SubjectData getSu(String subname){
//        String query = "SELECT * from Attendance " + "WHERE SubName LIKE '%"+subname+"%'";
//        SimpleSQLiteQuery query1 = new SimpleSQLiteQuery(query);
//        return attendanceDao.getData(query1);
        return attendanceDao.getSub(subname);
    }

}
