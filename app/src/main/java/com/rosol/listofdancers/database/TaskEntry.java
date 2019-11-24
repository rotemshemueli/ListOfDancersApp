package com.rosol.listofdancers.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task")
public class TaskEntry {

    private String name, phoneNumber, dancerId;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String gender;
    private String birthDate;

    @Ignore
    public TaskEntry(String name, String gender, String dancerId, String birthDate, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.dancerId = dancerId;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public TaskEntry(int id, String name, String gender, String dancerId, String birthDate, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dancerId = dancerId;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDancerId() {
        return dancerId;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDancerId(String dancerId) {
        this.dancerId = dancerId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
