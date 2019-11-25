package com.rosol.listofdancers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rosol.listofdancers.database.AppDatabase;
import com.rosol.listofdancers.database.TaskEntry;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    //Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";

    //extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    //constant for gender
    public static final String GENDER_MALE = "זכר";
    public static final String GENDER_FEMALE = "נקבה";
    public static final String GENDER_FEFAULT="זכר/נקבה?";

    //constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;

    //constant for logging
    private static final String TAG = AddTaskActivity.class.getSimpleName();

    //fields for vies

    EditText mName, mNumber, mId;
    TextView mDate;
    RadioGroup mRadioGroup;
    Button mButton;
    DatePickerDialog datePickerDialog;


    private int mTaskId = DEFAULT_TASK_ID;

    //member variable for Database
    private AppDatabase mDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }


          Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText("עדכן");
            if (mTaskId == DEFAULT_TASK_ID) {
                //populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                AddTaskViewModelFactory factory=new AddTaskViewModelFactory(
                        mDb,mTaskId);
                final AddTaskViewModel viewModel= ViewModelProviders.of(
                        this,factory).get(AddTaskViewModel.class);
                viewModel.getTask().observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(TaskEntry taskEntry) {
                        populateUi(taskEntry);
                    }
                });

            }

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        mId = findViewById(R.id.edit_id);
        mName = findViewById(R.id.edit_name);
        mNumber = findViewById(R.id.edit_phone);
        mDate = findViewById(R.id.edit_birth_date);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                //date picker dialog
                datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        mRadioGroup = findViewById(R.id.radioGroup);
        mButton = findViewById(R.id.save_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });


    }

    private void populateUi(TaskEntry task) {
        if (task == null) {
            return;
        }
        mId.setText(task.getDancerId());
        mDate.setText(task.getBirthDate());
        mName.setText(task.getName());
        mNumber.setText(task.getPhoneNumber());
        setGenderInViews(task.getGender());

    }


    private void onSaveButtonClicked() {
        String name= mName.getText().toString();
        String phoneNumber=mNumber.getText().toString();
        String birthDate=mDate.getText().toString();
        String danceId=mId.getText().toString();
        String gender=getGenderFromViews();
        final TaskEntry taskEntry=new TaskEntry(name,gender,danceId,birthDate,phoneNumber);
        AppExecutors.getInstance().diskIo().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId==DEFAULT_TASK_ID){
                    mDb.taskDao().insertTask(taskEntry);
                }else {
                    taskEntry.setId(mTaskId);
                    mDb.taskDao().updateTask(taskEntry);
                }
                finish();
            }
        });

    }

    private String getGenderFromViews() {

        String gender = "נקבה";
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();

        switch (checkedId) {
            case R.id.radButtonMale:
                gender = GENDER_MALE;
                break;
            case R.id.radButtonFemale:
                gender = GENDER_FEMALE;
                break;
                default:
                    gender=GENDER_FEFAULT;
                    break;
        }
        return gender;
    }

    private void setGenderInViews(String gender) {
        switch (gender) {
            case GENDER_MALE:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButtonMale);
                break;
            case GENDER_FEMALE:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButtonFemale);
                break;
        }
    }
}




