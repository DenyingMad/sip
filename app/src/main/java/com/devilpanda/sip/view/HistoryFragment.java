package com.devilpanda.sip.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devilpanda.sip.R;
import com.devilpanda.sip.adapters.HistoryRecyclerViewAdapter;
import com.devilpanda.sip.model.UserHistory;
import com.devilpanda.sip.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private UserViewModel viewModel;
    private HistoryRecyclerViewAdapter adapter;
    private List<UserHistory> userHistoryList = new ArrayList<>();

    public HistoryFragment() {
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = view.findViewById(R.id.history_recyclerview);
        TextView message = view.findViewById(R.id.empty_message);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));

        adapter = new HistoryRecyclerViewAdapter(userHistoryList);
        recyclerView.setAdapter(adapter);



        viewModel.getHistory().observe(requireActivity(), userHistory -> {
            userHistoryList.clear();
            userHistoryList.addAll(userHistory);

            if (userHistoryList.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                message.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                message.setVisibility(View.GONE);
            }

            adapter.notifyDataSetChanged();
        });
    }
}