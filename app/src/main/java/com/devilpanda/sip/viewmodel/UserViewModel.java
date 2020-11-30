package com.devilpanda.sip.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devilpanda.sip.model.User;
import com.devilpanda.sip.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private UserRepository userRepository;
    private MutableLiveData<User> userLiveData;

    public void init() {
        if (userRepository == null) {
            userRepository = UserRepository.getInstance();
        }
    }

    public void addDrankWater(Integer amount) {
        User user = userLiveData.getValue();
        user.addDrankWater(amount);
        updateUser(user);
    }

    public void removeDrankWater(Integer amount) {
        User user = userLiveData.getValue();
        user.removeDrankWater(amount);
        updateUser(user);
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
