package com.isharipov.counterpartyfinder.ui.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.isharipov.counterpartyfinder.R;
import com.isharipov.counterpartyfinder.data.network.dto.PreviewDto;
import com.isharipov.counterpartyfinder.ui.main.holder.SuggestionListViewHolder;
import com.isharipov.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;

/**
 * 07.03.2018.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionListViewHolder> {

    private final List<PreviewDto> previewDtos;
    private final RecyclerViewClickListener clickListener;

    public SuggestionAdapter(List<PreviewDto> previewDtos, RecyclerViewClickListener clickListener) {
        this.previewDtos = previewDtos;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public SuggestionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion_layout_row, parent, false);
        return new SuggestionListViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionListViewHolder holder, int position) {
        PreviewDto previewDto = previewDtos.get(position);
        holder.getCounterpartyName().setText(previewDto.getCounterpartyName());
        holder.getInn().setText(previewDto.getInn());
        holder.getAddress().setText(previewDto.getAddress());
    }

    @Override
    public int getItemCount() {
        return previewDtos.size();
    }
}
