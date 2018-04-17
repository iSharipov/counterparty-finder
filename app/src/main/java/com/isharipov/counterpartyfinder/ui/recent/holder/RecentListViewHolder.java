package com.isharipov.counterpartyfinder.ui.recent.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isharipov.counterpartyfinder.R;
import com.isharipov.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;

/**
 * 31.03.2018.
 */
public class RecentListViewHolder extends RecyclerView.ViewHolder {

    private final TextView counterpartyName;
    private final TextView inn;
    private final TextView address;
    private final ImageView isFavorite;
    private final RelativeLayout viewBackground, viewForeground;

    public RecentListViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);

        this.counterpartyName = itemView.findViewById(R.id.counterparty_name);
        this.inn = itemView.findViewById(R.id.inn);
        this.address = itemView.findViewById(R.id.address);
        this.isFavorite = itemView.findViewById(R.id.is_favorite);
        this.viewBackground = itemView.findViewById(R.id.view_background);
        this.viewForeground = itemView.findViewById(R.id.view_foreground);

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

    public ImageView getIsFavorite() {
        return isFavorite;
    }

    public RelativeLayout getViewBackground() {
        return viewBackground;
    }

    public RelativeLayout getViewForeground() {
        return viewForeground;
    }
}