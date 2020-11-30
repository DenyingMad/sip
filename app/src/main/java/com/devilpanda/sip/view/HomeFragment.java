package com.devilpanda.sip.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.devilpanda.sip.R;
import com.devilpanda.sip.model.User;
import com.devilpanda.sip.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private HomeViewModel viewModel;
    private NavController navController;

    private Button remove, add;
    private TextView drankWater, target, left, percent;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: viewmodel init");

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onViewCreated: checking first launch and init views");

        navController = Navigation.findNavController(view);

        initViews(view);

        checkFirstLaunch();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: observing viewmodel");

//        viewModel.updateUser().observe(this.requireActivity(), new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//                Log.d(TAG, "onChanged: " +
//                        user);
//                setupViews(user);
//            }
//        });

    }

    private void initViews(View view) {
        Log.d(TAG, "initViews");
        // Text Fields
        drankWater = view.findViewById(R.id.home_drank_water);
        target = view.findViewById(R.id.home_target_water);
        left = view.findViewById(R.id.home_left_water);
        percent = view.findViewById(R.id.home_percent);
        // Buttons
        remove = view.findViewById(R.id.home_remove_btn);
        add = view.findViewById(R.id.home_add_btn);
    }

    private void setupViews(User user) {
        drankWater.setText(String.valueOf(user.getWaterDrankToday()));
        target.setText(String.valueOf(user.getWaterTotal()));
        left.setText(String.valueOf(user.getWaterLeft()));
        percent.setText(String.valueOf(user.getPercent()));
    }

    /**
     * Функция проверяет, запущено ли приложение впервые
     * Если в SharedPreferences лежит значение "First" по ключу "IsFirstLaunch",
     * то запускается фрагмент {@link StartFragment}, который начинает цепочку фрагментов для ввода
     * настроек пользователя.
     * {@link ThirdSettingFragment} записывает в SharedPreferences значение "Not First"
     * по ключу "IsFirstLaunch"
     * <p>
     * Если в SharedPreferences лежит значение "Not First",
     * то запускается фрагмент {@link HomeFragment}, используются настройки, полученные при
     * первом запуске.
     */
    private void checkFirstLaunch() {
        Log.d(TAG, "checkFirstLaunch: checking");
        SharedPreferences sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String isFirstLaunch = sharedPreferences.getString("IsFirstLaunch", "First");
        if (isFirstLaunch.equals("First")) {
            Log.d(TAG, "checkFirstLaunch: first launch, go to start screen");
            navController.navigate(R.id.action_homeFragment_to_startFragment);
        } else if (isFirstLaunch.equals("Not First")) {
            Log.d(TAG, "checkFirstLaunch: not first launch");
            viewModel.getUser().observe(requireActivity(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    setupViews(user);
                }
            });
        }
    }

}