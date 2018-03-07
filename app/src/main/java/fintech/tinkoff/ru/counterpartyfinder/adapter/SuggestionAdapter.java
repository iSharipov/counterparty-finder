package fintech.tinkoff.ru.counterpartyfinder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.listener.SuggestionAdapterListener;

/**
 * 07.03.2018.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {

    private final List<PreviewDto> previewDtos;
    private final SuggestionAdapterListener suggestionAdapterListener;

    public SuggestionAdapter(List<PreviewDto> previewDtos, SuggestionAdapterListener suggestionAdapterListener) {
        this.previewDtos = previewDtos;
        this.suggestionAdapterListener = suggestionAdapterListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView counterpartyName, inn, address;

        ViewHolder(View itemView) {
            super(itemView);
            counterpartyName = itemView.findViewById(R.id.counterparty_name);
            inn = itemView.findViewById(R.id.inn);
            address = itemView.findViewById(R.id.address);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion_layout_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreviewDto previewDto = previewDtos.get(position);

        holder.counterpartyName.setText(previewDto.getCounterpartyName());
        holder.inn.setText(previewDto.getInn());
        holder.address.setText(previewDto.getAddress());
    }

    @Override
    public int getItemCount() {
        return previewDtos.size();
    }
}
