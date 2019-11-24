package com.rosol.listofdancers;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rosol.listofdancers.database.AppDatabase;
import com.rosol.listofdancers.database.TaskEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG=MainViewModel.class.getSimpleName();

    LiveData<List<TaskEntry>> tasks;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database=AppDatabase.getInstance(this.getApplication());
        Log.d(TAG,"Activity retrieve the tasks from the Database");
        tasks=database.taskDao().loadAllTasks();

    }
    public LiveData<List<TaskEntry>> getTasks(){
        return tasks;
    }
}
