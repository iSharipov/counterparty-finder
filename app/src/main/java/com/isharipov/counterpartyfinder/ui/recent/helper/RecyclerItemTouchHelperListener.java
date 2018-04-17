package com.isharipov.counterpartyfinder.ui.recent.helper;

import android.support.v7.widget.RecyclerView;

/**
 * 17.04.2018.
 */
public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
