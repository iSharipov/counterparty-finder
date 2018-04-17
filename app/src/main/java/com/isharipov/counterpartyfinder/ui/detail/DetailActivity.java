package com.isharipov.counterpartyfinder.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.isharipov.counterpartyfinder.R;
import com.isharipov.counterpartyfinder.data.db.repository.BaseDao;
import com.isharipov.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import com.isharipov.counterpartyfinder.data.network.model.Location;
import com.isharipov.counterpartyfinder.ui.map.MapActivity;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class DetailActivity extends AppCompatActivity {

    private static String EXTRA_INFO = "extra_info";
    private DataAnswerDto dataAnswerDto;
    public static Map<Boolean, Integer> bookmarkIds;
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
    @BindView(R.id.registration_date_value)
    TextView registrationDateValue;
    @BindView(R.id.organization_status_value)
    TextView organizationStatusValue;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    static {
        bookmarkIds = new HashMap<>();
        bookmarkIds.put(Boolean.TRUE, R.drawable.ic_bookmark_red_24dp);
        bookmarkIds.put(Boolean.FALSE, R.drawable.ic_bookmark_border_gray_24dp);
    }

    public DetailActivity() {

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
        initToolbar();
        initRealm();
        initDataAnswerDto();
        populateUi();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.detail_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
    }

    private void initDataAnswerDto() {
        String hid = (String) getIntent().getSerializableExtra(EXTRA_INFO);
        dataAnswerDto = BaseDao.get(realm, DataAnswerDto.class, hid);
    }

    private void populateUi() {
        bookmark.setImageResource(bookmarkIds.get(dataAnswerDto.getIsFavorite()));
        innDetail.setText(dataAnswerDto.getInn());
        branchTypeValue.setText(capitalize(dataAnswerDto.getBranchType()));
        nameDetail.setText(dataAnswerDto.getValue());
        addressDetail.setText(dataAnswerDto.getAddressValue());
        kppValue.setText(dataAnswerDto.getKpp());
        ogrnValue.setText(dataAnswerDto.getOgrn());
        okvedCodeValue.setText(dataAnswerDto.getOkved());
        okvedTypeValue.setText(dataAnswerDto.getOkvedType());
        managementFioValue.setText(dataAnswerDto.getManagementName());
        managementPostValue.setText(dataAnswerDto.getManagementPost());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        registrationDateValue.setText(sdf.format(dataAnswerDto.getStateRegistrationDate()));
        organizationStatusValue.setText(capitalize(dataAnswerDto.getStateStatus()));
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
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        bookmark.setOnClickListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        addMapMenuItem(menu);
        return true;
    }

    private void addMapMenuItem(Menu menu) {
        if (isMapEnabled()) {
            MenuItem menuItem = menu.findItem(R.id.action_detail_map);
            menuItem.setVisible(true);
        }
    }

    private boolean isMapEnabled() {
        Location location = dataAnswerDto.getLocation();
        return location != null && location.getGeoLat() != null && location.getGeoLon() != null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_detail_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Детальная информация");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, createShareMessage(dataAnswerDto));
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;
            case R.id.action_detail_map:
                MapActivity.start(this, dataAnswerDto.getHid());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String createShareMessage(DataAnswerDto dataAnswerDto) {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        sb.append("ИНН:");
        sb.append(dataAnswerDto.getInn());
        sb.append(lineSeparator);
        return sb.toString();
    }
}