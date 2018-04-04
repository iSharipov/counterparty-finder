package fintech.tinkoff.ru.counterpartyfinder.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.BaseDao;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import io.realm.Realm;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    private static String EXTRA_INFO = "extra_info";
    private DataAnswerDto dataAnswerDto;
    private Map<Boolean, Integer> bookmarkIds;
    private Realm realm;
    @BindView(R.id.bookmark)
    ImageView bookmark;
    @BindView(R.id.inn_detail)
    TextView innDetail;
    @BindView(R.id.name_detail)
    TextView nameDetail;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.kpp_value)
    TextView kppValue;
    @BindView(R.id.ogrn_value)
    TextView ogrnValue;
    @BindView(R.id.okved_code_value)
    TextView okvedCodeValue;
    @BindView(R.id.okved_type_value)
    TextView okvedTypeValue;
    @BindView(R.id.management_fio_value)
    TextView managementFioValue;
    @BindView(R.id.management_post_value)
    TextView managementPostValue;
    @BindView(R.id.branch_type_value)
    TextView branchTypeValue;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    public DetailActivity() {
        bookmarkIds = new HashMap<>();
        bookmarkIds.put(Boolean.TRUE, R.drawable.ic_bookmark_black_18dp);
        bookmarkIds.put(Boolean.FALSE, R.drawable.ic_bookmark_border_black_18dp);
    }

    public static void start(Activity activity, String hid) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_INFO, hid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        String hid = (String) getIntent().getSerializableExtra(EXTRA_INFO);
        dataAnswerDto = BaseDao.get(realm, DataAnswerDto.class, hid);
        bookmark.setImageResource(bookmarkIds.get(dataAnswerDto.getIsFavorite()));
        innDetail.setText(dataAnswerDto.getInn());
        branchTypeValue.setText(StringUtils.capitalize(dataAnswerDto.getBranchType()));
        nameDetail.setText(dataAnswerDto.getValue());
        addressDetail.setText(dataAnswerDto.getAddressValue());
        kppValue.setText(dataAnswerDto.getKpp());
        ogrnValue.setText(dataAnswerDto.getOgrn());
        okvedCodeValue.setText(dataAnswerDto.getOkved());
        okvedTypeValue.setText(dataAnswerDto.getOkvedType());
        managementFioValue.setText(dataAnswerDto.getManagementName());
        managementPostValue.setText(dataAnswerDto.getManagementPost());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookmark.setOnClickListener(v -> {
            ImageView imageView = (ImageView) v;
            realm.executeTransaction(realm1 -> dataAnswerDto.setIsFavorite(!dataAnswerDto.getIsFavorite()));
            BaseDao.add(realm, dataAnswerDto);
            imageView.setImageResource(bookmarkIds.get(dataAnswerDto.getIsFavorite()));
            DataAnswerDto dataAnswerDto2 = BaseDao.get(realm, DataAnswerDto.class, dataAnswerDto.getHid());

            Timber.i("Ответ от realm %s", dataAnswerDto2);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        bookmark.setOnClickListener(null);
    }
}