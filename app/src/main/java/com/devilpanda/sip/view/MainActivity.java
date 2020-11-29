package com.devilpanda.sip.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.devilpanda.sip.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        setUpNavController();
        checkFirstLaunch();
    }

    void setUpNavController() {
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

    /**
     * Функция проверяет, запущено ли приложение впервые
     * Если в SharedPreferences лежит значение "First",
     * то запускается фрагмент {@link StartFragment}, который начинает цепочку фрагментов для ввода
     * настроек пользователя.
     * {@link ThirdSettingFragment} записывает в SharedPreferences значение "Not First"
     *
     * Если в SharedPreferences лежит значение "Not First",
     * то запускается фрагмент {@link HomeFragment}, используются настройки, полученные при
     * первом запуске.
     */
    void checkFirstLaunch() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String isFirstLaunch = sharedPreferences.getString("Not First", "First");
        if (isFirstLaunch.equals("Not First")) {
            navController.navigate(R.id.action_thirdSettingFragment_to_homeFragment);
        }
    }
}