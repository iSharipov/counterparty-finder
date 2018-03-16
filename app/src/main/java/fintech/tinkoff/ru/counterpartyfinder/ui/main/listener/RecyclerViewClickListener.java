package fintech.tinkoff.ru.counterpartyfinder.ui.main.listener;

import android.view.View;

/**
 * 09.03.2018.
 */

public interface RecyclerViewClickListener{

    void onLongClick(View view, int position);

    void onClick(View view, int position);
}
