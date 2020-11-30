package com.devilpanda.sip.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devilpanda.sip.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from user")
    List<User> getAll();

    @Insert
    void insert(User user);

    @Update
    void update(User user);
}
