package fintech.tinkoff.ru.counterpartyfinder.ui.recent.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.data.network.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.recent.holder.RecentListViewHolder;

/**
 * 31.03.2018.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentListViewHolder> {

    private final List<PreviewDto> previewDtos;
    private final RecyclerViewClickListener clickListener;

    public RecentAdapter(List<PreviewDto> previewDtos, RecyclerViewClickListener clickListener) {
        this.previewDtos = previewDtos;
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
        PreviewDto previewDto = previewDtos.get(position);
        holder.getCounterpartyName().setText(previewDto.getCounterpartyName());
        holder.getInn().setText(previewDto.getInn());
        holder.getAddress().setText(previewDto.getAddress());
//        holder.getIsFavorite().setImageResource();
    }

    @Override
    public int getItemCount() {
        return previewDtos.size();
    }
}
    