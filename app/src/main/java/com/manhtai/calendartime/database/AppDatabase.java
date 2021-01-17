package com.manhtai.calendartime.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.manhtai.calendartime.dao.WorkDao;
import com.manhtai.calendartime.model.Work;

@Database(entities = {Work.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    static String DATABASE_NAME = "work.db";

    static AppDatabase appDatabase;
    public abstract WorkDao workDao();
    public static AppDatabase getInstance(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context,AppDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();

        }
        return appDatabase;
    }
}
