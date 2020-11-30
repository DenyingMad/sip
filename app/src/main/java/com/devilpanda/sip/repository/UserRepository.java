package com.devilpanda.sip.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.devilpanda.sip.database.AppDatabase;
import com.devilpanda.sip.database.UserDao;
import com.devilpanda.sip.model.User;
import com.devilpanda.sip.view.MainActivity;

import java.util.List;

public class UserRepository {

    private static final String TAG = "UserRepository";

    private static UserRepository instance;

    AppDatabase db = MainActivity.getInstance().getDatabase();
    UserDao userDao = db.userDao();

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
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
