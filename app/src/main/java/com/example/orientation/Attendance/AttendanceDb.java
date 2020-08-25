package com.example.orientation.Attendance;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.orientation.model.SubjectData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SubjectData.class}, version = 1,exportSchema = false)
public abstract class AttendanceDb extends RoomDatabase {

    abstract AttendanceDao attendanceDao();

    private static volatile AttendanceDb INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AttendanceDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AttendanceDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AttendanceDb.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                AttendanceDao dao = INSTANCE.attendanceDao();

            });
        }
    };
}
