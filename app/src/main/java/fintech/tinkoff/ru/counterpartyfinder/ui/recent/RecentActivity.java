package fintech.tinkoff.ru.counterpartyfinder.ui.recent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import fintech.tinkoff.ru.counterpartyfinder.ui.main.listener.RecyclerViewClickListener;
import fintech.tinkoff.ru.counterpartyfinder.ui.recent.adapter.RecentAdapter;
import io.realm.Realm;

/**
 * 31.03.2018.
 */
public class RecentActivity extends AppCompatActivity {

    private Realm realm;
    private List<DataAnswerDto> dataAnswerDtos;

    @BindView(R.id.recent_view)
    public RecyclerView recentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        recentView.setHasFixedSize(true);
        recentView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<PreviewDto> previews = DataAnswerDtoToPreviewDtoMapper.INSTANCE.map(BaseDao.getAll(realm, DataAnswerDto.class));
        RecentAdapter adapter = new RecentAdapter(previews, new RecentListClickListener());
        recentView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class RecentListClickListener implements RecyclerViewClickListener {

        @Override
        public void onClick(View view, int position) {
            DataAnswerDto dataAnswerDto = dataAnswerDtos.get(position);
            if (dataAnswerDto != null) {
                DetailActivity.start(RecentActivity.this, dataAnswerDto.getHid());
            } else {
                Toast.makeText(RecentActivity.this, "Details must not be null", Toast.LENGTH_LONG).show();
            }
        }
    }
}