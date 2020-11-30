package com.devilpanda.sip.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.devilpanda.sip.model.UserHistory;

@Dao
public interface HistoryDao {
    @Insert
    void insert(UserHistory history);
}
