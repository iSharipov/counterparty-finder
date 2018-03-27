package fintech.tinkoff.ru.counterpartyfinder.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import fintech.tinkoff.ru.counterpartyfinder.R;
import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.model.DataAnswerDto;

public class DetailActivity extends AppCompatActivity {

    private static String EXTRA_INFO = "extra_info";

    @BindView(R.id.inn_detail)
    public TextView innDetail;
    @BindView(R.id.name_detail)
    public TextView nameDetail;
    @BindView(R.id.address_detail)
    public TextView addressDetail;
    @BindView(R.id.kpp_value)
    public TextView kppValue;
    @BindView(R.id.ogrn_value)
    public TextView ogrnValue;
    @BindView(R.id.okved_code_value)
    public TextView okvedCodeValue;
    @BindView(R.id.okved_type_value)
    public TextView okvedTypeValue;
    @BindView(R.id.management_fio_value)
    public TextView managementFioValue;
    @BindView(R.id.management_post_value)
    public TextView managementPostValue;
    @BindView(R.id.branch_type_value)
    public TextView branchTypeValue;

    public static void start(Activity activity, DataAnswerDto dataAnswerDto) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_INFO, dataAnswerDto);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        DataAnswerDto dataAnswerDto = (DataAnswerDto) getIntent().getSerializableExtra(EXTRA_INFO);
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
}