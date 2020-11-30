package com.devilpanda.sip.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.devilpanda.sip.R;
import com.devilpanda.sip.model.Gender;
import com.devilpanda.sip.model.User;

public class FirstSettingFragment extends Fragment {

    private static final String TAG = "FirstSettingFragment";

    private Button man;
    private Button woman;

    public FirstSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        man = view.findViewById(R.id.man_button);
        woman = view.findViewById(R.id.woman_button);

        Log.d(TAG, "onViewCreated: choose gender");

        User user = User.getInstance();
        NavController navController = Navigation.findNavController(view);
        View.OnClickListener listener = clickView -> {
            int id = clickView.getId();
            if (id == R.id.man_button) {
                user.setGender(Gender.MAN);
                Log.d(TAG, "onViewCreated: " +
                        "MAN " + user);
            }
            else if (id == R.id.woman_button) {
                user.setGender(Gender.WOMAN);
                Log.d(TAG, "onViewCreated: " +
                        "WOMAN " + user);
            }
            navController.navigate(R.id.action_firstSettingFragment_to_secondSettingFragment);
        };

        man.setOnClickListener(listener);
        woman.setOnClickListener(listener);
    }
}