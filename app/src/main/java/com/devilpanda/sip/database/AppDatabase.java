package com.devilpanda.sip.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.devilpanda.sip.model.GenderConverter;
import com.devilpanda.sip.model.User;
import com.devilpanda.sip.model.UserHistory;

@Database(entities = {User.class, UserHistory.class}, version = 1)
@TypeConverters({ GenderConverter.class })
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract HistoryDao historyDao();
}
