package com.rosol.listofdancers;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rosol.listofdancers.database.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    final private ItemClickListener mItemClickListener;

    EditText date;
    private static final int REQUEST_CALL = 1;

    private List<TaskEntry> mTaskEntries;
    private Context context;


    public TaskAdapter(Context context, ItemClickListener mItemClickListener) {
        this.context = context;
        this.mItemClickListener = mItemClickListener;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the item_to_show layout to a view
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_to_show, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        TaskEntry taskEntry = mTaskEntries.get(position);
        String name = taskEntry.getName();
        final String phoneNumber = taskEntry.getPhoneNumber();
        String dancerId = taskEntry.getDancerId();
        String gender = taskEntry.getGender();
        String date = taskEntry.getBirthDate();

        //set values
        holder.nameToShow.setText(name);
        String genderString = "" + gender;
        holder.genderToShow.setText(genderString);
        holder.idToShow.setText(dancerId);
        holder.dateToShow.setText(date);
        holder.phoneToShow.setText(phoneNumber);


    }

    private int getGenderColor(int gender) {
        int genderColor = 0;
        switch (gender) {
            case 1:
                genderColor = ContextCompat.getColor(context, R.color.male);
                break;
            case 2:
                genderColor = ContextCompat.getColor(context, R.color.female);
                break;
            default:
                break;

        }
        return genderColor;

    }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<TaskEntry> getTasks() {
        return mTaskEntries;
    }


    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void OnItemClickListener(int itemId);
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        TextView nameToShow, genderToShow, phoneToShow, idToShow, dateToShow;
        Button editButton;
        ImageView imagePhoneCall;

        public TaskViewHolder(View itemView) {
            super(itemView);

            nameToShow = itemView.findViewById(R.id.name_to_show);
            genderToShow = itemView.findViewById(R.id.gender_to_show);
            idToShow = itemView.findViewById(R.id.id_to_show);
            phoneToShow = itemView.findViewById(R.id.number_to_show);
            editButton = itemView.findViewById(R.id.edit_button);
            dateToShow = itemView.findViewById(R.id.date_to_show);
            imagePhoneCall = itemView.findViewById(R.id.button_to_call);

            final String dialToPhoneNumberThatShow = String.valueOf(phoneToShow);
            imagePhoneCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(dialToPhoneNumberThatShow));
                    dial.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(dial);
                }
            });



            dateToShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            //    itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.OnItemClickListener(elementId);


        }
    }



}
