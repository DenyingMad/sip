package com.devilpanda.sip.adapters;

import android.content.res.Resources;
import android.content.res.loader.ResourcesLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devilpanda.sip.R;
import com.devilpanda.sip.model.UserHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private List<UserHistory> userHistoryList;

    public HistoryRecyclerViewAdapter(List<UserHistory> userHistoryList) {
        this.userHistoryList = userHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.action.setText(userHistoryList.get(position).getAction());
        holder.amount.setText(String.valueOf(userHistoryList.get(position).getAmount()));
        holder.time.setText(userHistoryList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return userHistoryList == null ? 0 : userHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView action, amount, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.action = itemView.findViewById(R.id.history_action);
            this.amount = itemView.findViewById(R.id.history_amount);
            this.time = itemView.findViewById(R.id.history_time);
        }
    }
}
