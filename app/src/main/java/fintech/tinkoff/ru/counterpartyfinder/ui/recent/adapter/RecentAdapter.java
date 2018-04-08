package fintech.tinkoff.ru.counterpartyfinder.ui.recent.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.data.network.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.recent.holder.RecentListViewHolder;

/**
 * 31.03.2018.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentListViewHolder> implements Filterable {

    private List<PreviewDto> previewDtos;
    private List<PreviewDto> previewDtosFiltered;
    private final RecyclerViewClickListener clickListener;

    public RecentAdapter(List<PreviewDto> previewDtos, RecyclerViewClickListener clickListener) {
        this.previewDtos = previewDtos;
        this.previewDtosFiltered = previewDtos;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_layout_row, parent, false);
        return new RecentListViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentListViewHolder holder, int position) {
        PreviewDto previewDto = previewDtosFiltered.get(position);
        holder.getCounterpartyName().setText(previewDto.getCounterpartyName());
        holder.getInn().setText(previewDto.getInn());
        holder.getAddress().setText(previewDto.getAddress());
        holder.itemView.setBackgroundColor(previewDto.getIsFavorite() ? Color.YELLOW : Color.WHITE);
//        holder.getIsFavorite().setImageResource();
    }

    @Override
    public int getItemCount() {
        return previewDtosFiltered.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    previewDtosFiltered = previewDtos;
                } else {
                    List<PreviewDto> filteredList = new ArrayList<>();
                    for (PreviewDto row : previewDtos) {
                        if (row.getInn().contains(charString.toLowerCase())
                                || row.getCounterpartyName().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    previewDtosFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = previewDtosFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                previewDtosFiltered = (List<PreviewDto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}