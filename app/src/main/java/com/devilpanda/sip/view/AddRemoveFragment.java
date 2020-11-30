package com.devilpanda.sip.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.devilpanda.sip.R;
import com.devilpanda.sip.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddRemoveFragment extends Fragment {

    private static final String TAG = "AddRemoveFragment";

    private UserViewModel viewModel;

    public AddRemoveFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_remove, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        boolean changeType = getArguments().getBoolean("Change");
        Log.d(TAG, "onViewCreated: changeType: " + changeType);

        TextInputEditText editText = view.findViewById(R.id.change_edit_text);
        Button button = view.findViewById(R.id.change_next_btn);

        button.setOnClickListener((clickView) -> {
            Integer waterChange = Integer.parseInt(editText.getText().toString());
            if (changeType) {
                Log.d(TAG, "onViewCreated: add");
                viewModel.addDrankWater(waterChange);
            }
            else {
                Log.d(TAG, "onViewCreated: remove");
                viewModel.removeDrankWater(waterChange);
            }
            Navigation.findNavController(view).navigate(R.id.action_addRemoveFragment_to_homeFragment);
        });

    }
}