package com.devilpanda.sip.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.devilpanda.sip.model.UserHistory;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insert(UserHistory history);

    @Query("select * from user_history")
    List<UserHistory> getAllHistory();
}
