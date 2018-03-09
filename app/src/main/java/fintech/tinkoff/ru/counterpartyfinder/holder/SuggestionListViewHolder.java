package fintech.tinkoff.ru.counterpartyfinder.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.listener.RecyclerViewClickListener;

/**
 * 09.03.2018.
 */

public class SuggestionListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.counterparty_name)
    TextView counterpartyName;
    @BindView(R.id.inn)
    TextView inn;
    @BindView(R.id.address)
    TextView address;

    private final RecyclerViewClickListener clickListener;

    public SuggestionListViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View view) {
        clickListener.onClick(view, getAdapterPosition());
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
