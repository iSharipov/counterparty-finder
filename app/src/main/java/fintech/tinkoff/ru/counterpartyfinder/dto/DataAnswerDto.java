package fintech.tinkoff.ru.counterpartyfinder.dto;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 09.03.2018.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataAnswerDto extends RealmObject {
    @PrimaryKey
    private String hid;
    private String kpp;
    private String capital;
    private String managementName;
    private String managementPost;
    private String branchType;
    private Long branchCount;
    private String source;
    private String qc;
    private String type;
    private String stateStatus;
    private String stateActualityDate;
    private String stateRegistrationDate;
    private String stateLiquidationDate;
    private String opfType;
    private String opfCode;
    private String opfFull;
    private String opfShortName;
    private String nameFullWithOpf;
    private String nameShortWithOpf;
    private String nameLatin;
    private String nameFull;
    private String nameShortName;
    private String inn;
    private String ogrn;
    private String okpo;
    private String okved;
    private RealmList<String> okveds = new RealmList<>();
    private RealmList<String> authorities = new RealmList<>();
    private RealmList<String> documents = new RealmList<>();
    private RealmList<String> licenses = new RealmList<>();
    private String addressValue;
    private String addressUnrestrictedValue;
    private RealmList<String> phones = new RealmList<>();
    private RealmList<String> emails = new RealmList<>();
    private String ogrnDate;
    private String okvedType;
    private String tapDate = new Date().toString();
    private Boolean isFavorite = Boolean.FALSE;
}