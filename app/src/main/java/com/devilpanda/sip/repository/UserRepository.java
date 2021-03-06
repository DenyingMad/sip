package com.devilpanda.sip.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.devilpanda.sip.database.AppDatabase;
import com.devilpanda.sip.database.HistoryDao;
import com.devilpanda.sip.database.UserDao;
import com.devilpanda.sip.model.User;
import com.devilpanda.sip.model.UserHistory;
import com.devilpanda.sip.view.MainActivity;

import java.util.List;

public class UserRepository {

    private static final String TAG = "UserRepository";

    private static UserRepository instance;

    AppDatabase db = MainActivity.getInstance().getDatabase();
    UserDao userDao = db.userDao();
    HistoryDao historyDao = db.historyDao();

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public MutableLiveData<List<UserHistory>> getAllUserHistory() {
        MutableLiveData<List<UserHistory>> data = new MutableLiveData<>();

        getAllHistoryTask getHistoryTask = new getAllHistoryTask(historyDao, data);
        getHistoryTask.execute();

        return data;
    }

    private static class getAllHistoryTask extends AsyncTask<Void, Void, List<UserHistory>> {

        private HistoryDao historyDao;
        private MutableLiveData<List<UserHistory>> data;

        public getAllHistoryTask(HistoryDao historyDao, MutableLiveData<List<UserHistory>> data) {
            this.historyDao = historyDao;
            this.data = data;
        }

        @Override
        protected List<UserHistory> doInBackground(Void... voids) {
            return historyDao.getAllHistory();
        }

        @Override
        protected void onPostExecute(List<UserHistory> userHistoryList) {
            data.postValue(userHistoryList);
        }
    }

    public void insertHistory(UserHistory history) {
        insertHistoryTask insertTask = new insertHistoryTask(historyDao);
        insertTask.execute(history);
    }

    private static class insertHistoryTask extends AsyncTask<UserHistory, Void, Void> {

        private final HistoryDao historyDao;

        public insertHistoryTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(UserHistory... userHistories) {
            historyDao.insert(userHistories[0]);
            return null;
        }
    }

    public void insertUser(User user) {
        new insertUserTask(userDao).execute(user);
    }

    public MutableLiveData<User> getUser() {
        final MutableLiveData<User> data = new MutableLiveData<>();

        getUserTask task = new getUserTask(userDao, data);
        task.execute();

        return data;
    }

    public void updateUser(User user) {
        Log.d(TAG, "updateUser: " + user);
        updateUserTask updateUserTask = new updateUserTask(userDao);
        updateUserTask.execute(user);
    }

    private static class updateUserTask extends AsyncTask<User, Void, Void> {

        private final UserDao userDao;

        public updateUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class getUserTask extends AsyncTask<Void, Void, User> {

        private final UserDao userDao;
        private final MutableLiveData<User> data;

        public getUserTask(UserDao userDao, MutableLiveData<User> data) {
            this.userDao = userDao;
            this.data = data;
        }

        @Override
        protected User doInBackground(Void... voids) {
            List<User> userList = userDao.getAll();
            Log.d(TAG, "getUser doInBackground: " + userList);
            return userList.get(0);
        }

        @Override
        protected void onPostExecute(User user) {
            Log.d(TAG, "getUser onPostExecute: " + user);
            User u = User.getInstance();
            u = user;
            data.postValue(user);
        }
    }

    private static class insertUserTask extends AsyncTask<User, Void, Void> {

        private final UserDao userDao;

        public insertUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            Log.d(TAG, "insert user doInBackground: " + users[0]);
            userDao.insert(users[0]);
            return null;
        }

    }
}
