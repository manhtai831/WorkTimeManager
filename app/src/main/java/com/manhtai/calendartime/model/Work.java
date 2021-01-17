package com.manhtai.calendartime.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "work")
public class Work {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameEvent;
    private String date;
    private boolean punish;
    private String moneyPunish;
    private String shifts;
    private String numberOfTime;
    private String note;

    public Work(String nameEvent, String date, boolean punish, String moneyPunish, String shifts, String numberOfTime, String note) {
        this.nameEvent = nameEvent;
        this.date = date;
        this.punish = punish;
        this.moneyPunish = moneyPunish;
        this.shifts = shifts;
        this.numberOfTime = numberOfTime;
        this.note = note;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPunish() {
        return punish;
    }

    public void setPunish(boolean punish) {
        this.punish = punish;
    }

    public String getMoneyPunish() {
        return moneyPunish;
    }

    public void setMoneyPunish(String moneyPunish) {
        this.moneyPunish = moneyPunish;
    }

    public String getShifts() {
        return shifts;
    }

    public void setShifts(String shifts) {
        this.shifts = shifts;
    }

    public String getNumberOfTime() {
        return numberOfTime;
    }

    public void setNumberOfTime(String numberOfTime) {
        this.numberOfTime = numberOfTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", nameEvent='" + nameEvent + '\'' +
                ", date='" + date + '\'' +
                ", punish=" + punish +
                ", moneyPunish='" + moneyPunish + '\'' +
                ", shifts='" + shifts + '\'' +
                ", numberOfTime='" + numberOfTime + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}


