package com.example.orientation.Attendance;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.orientation.model.SubjectData;

import java.util.List;

@Dao
public interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SubjectData subjectData);

    @Query("SELECT * from Attendance ORDER BY SubName ASC")
    LiveData<List<SubjectData>> getDataLive();

    @Query("SELECT TotClass from Attendance ORDER BY SubName ASC")
    List<Integer> getTot();

    @Query("SELECT AttendCount from Attendance ORDER BY SubName ASC")
    List<Integer> getNum();

   @Delete
   void delete(SubjectData data);

    @Update
    void update(SubjectData subjectData);

}
