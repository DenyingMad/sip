package com.devilpanda.sip.view;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.devilpanda.sip.R;
import com.devilpanda.sip.model.User;
import com.google.android.material.textfield.TextInputLayout;

public class SecondSettingFragment extends Fragment {

    private static final String TAG = "SecondSettingFragment";

    private TextInputLayout inputLayout;
    private EditText weight_et;
    private Button nextBtn;

    public SecondSettingFragment() {
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
        return inflater.inflate(R.layout.fragment_second_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inputLayout = view.findViewById(R.id.weight_input_layout);
        weight_et = view.findViewById(R.id.weight_setting_edit_text);
        nextBtn = view.findViewById(R.id.weight_setting_next_btn);

        inputLayout.setHint(getString(R.string.enter_weight_hint));

        NavController navController = Navigation.findNavController(view);
        Log.d(TAG, "onViewCreated: " +
                "waiting for input");
        nextBtn.setOnClickListener(clickView -> {
            int weight = Integer.parseInt(weight_et.getText().toString());
            Log.d(TAG, "onViewCreated: weight: " + weight);
            if (isRightWeight(weight)) {
                User user = User.getInstance();
                user.setWeight(weight);
                Log.d(TAG, "onViewCreated: " + user);
                navController.navigate(R.id.action_secondSettingFragment_to_thirdSettingFragment);
            } else {
                inputLayout.setHint(getString(R.string.enter_weight_hint_error));
                inputLayout.setHintTextColor(ColorStateList
                        .valueOf(getResources().getColor(R.color.red)));
            }
        });
    }

    boolean isRightWeight(int weight) {
        return weight > 20 && weight < 200;
    }

}