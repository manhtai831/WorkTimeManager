package com.manhtai.calendartime.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.manhtai.calendartime.model.Work;

import java.util.List;

@Dao
public interface WorkDao {
    @Query("select * from work")
    List<Work> getAll();

    @Query("select * from work order by id desc")
    List<Work> getAllOrdered();


    @Query("select * from work where id = :id")
    Work getWorkById(int id);

    @Insert
    void insertWork(Work... works);

    @Update
    void updateWork(Work work);

    @Delete
    void deleteWork(Work works);
}
