package com.devilpanda.sip.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devilpanda.sip.model.User;
import com.devilpanda.sip.model.UserHistory;
import com.devilpanda.sip.repository.UserRepository;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private UserRepository userRepository;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<List<UserHistory>> userHistoryLiveData;

    public void init() {
        if (userRepository == null) {
            userRepository = UserRepository.getInstance();
        }
    }

    public LiveData<List<UserHistory>> getHistory() {
        userHistoryLiveData = userRepository.getAllUserHistory();
        return userHistoryLiveData;
    }

    public void insertHistory(UserHistory userHistory) {
        userRepository.insertHistory(userHistory);
    }

    public void addDrankWater(Integer amount) {
        User user = userLiveData.getValue();
        user.addDrankWater(amount);
        updateUser(user);
        // add to history
        addToHistory(amount, "add");
    }

    private void addToHistory(Integer amount, String action) {
        Date currentTime = Calendar.getInstance().getTime();
        String time = currentTime.getHours() + ":" + currentTime.getMinutes();
        UserHistory userHistory = new UserHistory(action, time, amount);
        insertHistory(userHistory);
    }

    public void removeDrankWater(Integer amount) {
        User user = userLiveData.getValue();
        user.removeDrankWater(amount);
        updateUser(user);

        addToHistory(amount, "remove");
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void createUser(User user) {
        Log.d(TAG, "createUser: insert user");
        userRepository.insertUser(user);
    }

    public LiveData<User> getUser() {
        userLiveData = userRepository.getUser();
        Log.d(TAG, "getUser: " + userLiveData.getValue());
        return userLiveData;
    }

}
