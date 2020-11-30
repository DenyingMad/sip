package com.devilpanda.sip.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.devilpanda.sip.R;
import com.devilpanda.sip.database.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    public static MainActivity instance;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: database build");


        instance = this;
        if (database == null) {
            database = Room.databaseBuilder(this, AppDatabase.class, "database")
                    .build();
            Log.d(TAG, "onCreate: built");
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        setUpNavController();
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    void setUpNavController() {

        Log.d(TAG, "setUpNavController: navigation setup");

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.startFragment
                    || destination.getId() == R.id.firstSettingFragment
                    || destination.getId() == R.id.secondSettingFragment
                    || destination.getId() == R.id.thirdSettingFragment) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
    }
}