package com.rosol.listofdancers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rosol.listofdancers.database.TaskEntry;

import java.util.List;

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
                   callPhoneNumber(dialToPhoneNumberThatShow);
                }
            });

            editButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.OnItemClickListener(elementId);


        }
    }



    private void callPhoneNumber(String number) {
        try{
            if (Build.VERSION.SDK_INT >22){
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CALL_PHONE},101);
                    return;
                }
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse(number));
                context.startActivity(callIntent);
            }
            else {
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse(number));
                context.startActivity(callIntent);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
