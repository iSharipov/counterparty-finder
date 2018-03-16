package fintech.tinkoff.ru.counterpartyfinder.ui.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.data.network.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.holder.SuggestionListViewHolder;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;

/**
 * 07.03.2018.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionListViewHolder> {

    private final List<PreviewDto> previewDtos;

    public SuggestionAdapter(List<PreviewDto> previewDtos) {
        this.previewDtos = previewDtos;
    }


    @NonNull
    @Override
    public SuggestionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion_layout_row, parent, false);
        return new SuggestionListViewHolder(v);
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
