package fintech.tinkoff.ru.counterpartyfinder.ui.main.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;

/**
 * 09.03.2018.
 */

public class SuggestionListViewHolder extends RecyclerView.ViewHolder {
    private final TextView counterpartyName;
    private final TextView inn;
    private final TextView address;

    public SuggestionListViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        this.counterpartyName = itemView.findViewById(R.id.counterparty_name);
        this.inn = itemView.findViewById(R.id.inn);
        this.address = itemView.findViewById(R.id.address);

        itemView.setOnClickListener(v -> clickListener.onClick(v, getAdapterPosition()));
    }


    public TextView getCounterpartyName() {
        return counterpartyName;
    }

    public TextView getInn() {
        return inn;
    }

    public TextView getAddress() {
        return address;
    }
}