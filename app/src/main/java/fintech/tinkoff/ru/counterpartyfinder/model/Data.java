package fintech.tinkoff.ru.counterpartyfinder.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.EqualsAndHashCode;

//import io.realm.RealmObject;

/**
 * 27.02.2018.
 */
@EqualsAndHashCode(callSuper = true)
@lombok.Data
public class Data extends RealmObject implements Serializable {

    @PrimaryKey
    private String uuid;

    private String kpp;
    private String capital;
    private Management management;
    @SerializedName("branch_type")
    private String branchType;
    @SerializedName("branch_count")
    private Long branchCount;
    private String source;
    private String qc;
    private String hid;
    private String type;
    private State state;
    private Opf opf;
    private Name name;
    private String inn;
    private String ogrn;
    private String okpo;
    private String okved;
    private RealmList<String> okveds = new RealmList<>();
    private RealmList<String> authoroties = new RealmList<>();
    private RealmList<String> documents = new RealmList<>();
    private RealmList<String> licenses = new RealmList<>();
    private Address address;
    private RealmList<String> phones = new RealmList<>();
    private RealmList<String> emails = new RealmList<>();
    @SerializedName("ogrn_date")
    private Long ogrnDate;
    @SerializedName("okved_type")
    private String okvedType;
}