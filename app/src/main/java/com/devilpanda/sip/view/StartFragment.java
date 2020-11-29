package com.devilpanda.sip.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.devilpanda.sip.R;

public class StartFragment extends Fragment {

    public StartFragment() {
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
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavController navController = Navigation.findNavController(view);
        Button nextBtn = view.findViewById(R.id.start_settings_next_btn);
        nextBtn.setOnClickListener(view1 -> navController.navigate(R.id.action_startFragment_to_firstSetiingFragment));
    }
}