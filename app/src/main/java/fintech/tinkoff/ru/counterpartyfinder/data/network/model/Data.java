package fintech.tinkoff.ru.counterpartyfinder.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.PrimaryKey;

//import io.realm.RealmObject;

/**
 * 27.02.2018.
 */
@lombok.Data
public class Data implements Serializable {

    private String kpp;
    private String capital;
    private Management management;
    @SerializedName("branch_type")
    private String branchType;
    @SerializedName("branch_count")
    private Long branchCount;
    private String source;
    private String qc;
    @PrimaryKey
    private String hid;
    private String type;
    private State state;
    private Opf opf;
    private Name name;
    private String inn;
    private String ogrn;
    private String okpo;
    private String okved;
    private List<String> okveds = new ArrayList<>();
    private List<String> authorities = new ArrayList<>();
    private List<String> documents = new ArrayList<>();
    private List<String> licenses = new ArrayList<>();
    private Address address;
    private List<String> phones = new ArrayList<>();
    private List<String> emails = new ArrayList<>();
    @SerializedName("ogrn_date")
    private Long ogrnDate;
    @SerializedName("okved_type")
    private String okvedType;
}