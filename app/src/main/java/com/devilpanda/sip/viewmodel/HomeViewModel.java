package com.devilpanda.sip.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devilpanda.sip.model.User;
import com.devilpanda.sip.repository.UserRepository;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private UserRepository userRepository;
    private MutableLiveData<User> userLiveData;

    public void init() {
        if (userRepository == null) {
            userRepository = UserRepository.getInstance();
        }
    }


    // todo rewrite to update db
    public LiveData<User> updateUser() {
        Log.d(TAG, "updateUser: get user instance and recalculate params");
        User user = User.getInstance();
        Log.d(TAG, "updateUser: " + user);
        if (user.getWaterTotal() != null) {
            Log.d(TAG, "updateUser: computing");
            user.countDailyWaterAmount();
            user.countWaterLeft();
        }
        userLiveData.setValue(user);
        Log.d(TAG, "updateUser: " + userLiveData.getValue());
        return userLiveData;
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
