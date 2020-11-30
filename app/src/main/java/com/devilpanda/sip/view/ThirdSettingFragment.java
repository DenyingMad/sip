package com.devilpanda.sip.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.devilpanda.sip.R;
import com.devilpanda.sip.model.User;
import com.devilpanda.sip.viewmodel.HomeViewModel;

public class ThirdSettingFragment extends Fragment {

    private static final String TAG = "ThirdSettingFragment";

    private Spinner spinner;
    private Button next;
    private int spinnerPosition;
    private NavController navController;

    private HomeViewModel viewModel;

    public ThirdSettingFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinner = view.findViewById(R.id.physics_setting_spinner);
        next = view.findViewById(R.id.physics_setting_next_btn);

        navController = Navigation.findNavController(view);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSpinnerPosition(position);
                Log.d(TAG, "onItemSelected: spinner element selected: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setSpinnerPosition(0);
                Log.d(TAG, "onNothingSelected: 0");
            }
        });

        next.setOnClickListener(v -> {
            User user = User.getInstance();
            user.selectPhysicalActivity(spinnerPosition);
            Log.d(TAG, "onViewCreated: setup physical activity:" + user);
            setFirstLaunch();
            Log.d(TAG, "onViewCreated: create user in db");
            viewModel.createUser(user);
            navController.navigate(R.id.action_thirdSettingFragment_to_homeFragment);
        });
    }

    private void setFirstLaunch() {
        Log.d(TAG, "setFirstLaunch: edit shared preferences");
        SharedPreferences sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IsFirstLaunch", "Not First");
        editor.apply();
    }

    private void setSpinnerPosition(int spinnerPosition) {
        this.spinnerPosition = spinnerPosition;
    }
}