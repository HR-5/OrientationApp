package com.example.orientation.Attendance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.orientation.model.SubjectData;

import java.util.List;

public class AttendViewModel extends AndroidViewModel {

    private AttendRepo mRepository;
    private LiveData<List<SubjectData>> data;
    public Application application;

    public AttendViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AttendRepo(application);
        data = mRepository.getAllWords();
        this.application = application;
    }

    LiveData<List<SubjectData>> getAllWords() {
        return data;
    }

    void insert(SubjectData word) {
        mRepository.insert(word);
    }

    void update(SubjectData data){
        mRepository.update(data);
    }

    int tot(){
        return mRepository.getTot();
    }

    int num(){
        return mRepository.getAtt();
    }

    void delete(SubjectData data){
        mRepository.delete(data);
    }

    SubjectData getSub(String subname){return mRepository.getSu(subname);}
}
