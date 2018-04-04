package fintech.tinkoff.ru.counterpartyfinder.ui.recent;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.BaseDao;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import fintech.tinkoff.ru.counterpartyfinder.data.network.dto.PreviewDto;
import fintech.tinkoff.ru.counterpartyfinder.mapper.DataAnswerDtoToPreviewDtoMapper;
import fintech.tinkoff.ru.counterpartyfinder.ui.detail.DetailActivity;
import fintech.tinkoff.ru.counterpartyfinder.ui.detail.swipe.SwipeController;
import fintech.tinkoff.ru.counterpartyfinder.ui.detail.swipe.SwipeControllerActions;
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.recent.adapter.RecentAdapter;
import io.realm.Realm;
import io.realm.Sort;

import static io.realm.Sort.DESCENDING;

/**
 * 31.03.2018.
 */
public class RecentActivity extends AppCompatActivity {

    private Realm realm;
    private List<DataAnswerDto> allByFieldsSorted;
    private String[] fields = new String[]{"isFavorite", "tapDate"};
    private Sort[] sorts = new Sort[]{DESCENDING, DESCENDING};
    private RecentAdapter recentAdapter;
    private SearchView searchView;

    @BindView(R.id.recent_view)
    RecyclerView recentView;
    @BindView(R.id.recent_toolbar)
    Toolbar toolbar;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, RecentActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.recent_title);
        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                BaseDao.remove(realm, DataAnswerDto.class, recentAdapter.getPreviewDtosFiltered().get(position).getHid());
                recentAdapter.getPreviewDtosFiltered().remove(position);
                recentAdapter.notifyItemRemoved(position);
                recentAdapter.notifyItemRangeChanged(position, recentAdapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recentView);
        realm = Realm.getDefaultInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recentView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recentView.getContext(),
                linearLayoutManager.getOrientation()
        );
        recentView.addItemDecoration(dividerItemDecoration);
        recentView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        allByFieldsSorted = BaseDao.getAllByFieldsSorted(realm, DataAnswerDto.class, fields, sorts);
        List<PreviewDto> previews = DataAnswerDtoToPreviewDtoMapper.INSTANCE.map(allByFieldsSorted);
        recentAdapter = new RecentAdapter(previews, new RecentListClickListener());
        recentView.setAdapter(recentAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        recentView.setAdapter(null);
    }

    class RecentListClickListener implements RecyclerViewClickListener {

        @Override
        public void onClick(View view, int position) {
            DataAnswerDto dataAnswerDto = allByFieldsSorted.get(position);
            if (dataAnswerDto != null) {
                DetailActivity.start(RecentActivity.this, dataAnswerDto.getHid());
            } else {
                Toast.makeText(RecentActivity.this, "Details must not be null", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recent, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.recent_search).getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recentAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                recentAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.recent_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }
}