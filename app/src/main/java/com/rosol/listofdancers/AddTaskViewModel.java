package com.rosol.listofdancers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rosol.listofdancers.database.AppDatabase;
import com.rosol.listofdancers.database.TaskEntry;

public class AddTaskViewModel extends ViewModel {
    private LiveData<TaskEntry> task;

    public AddTaskViewModel(AppDatabase database,int taskId){
        task=database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask(){
        return task;
    }
}
