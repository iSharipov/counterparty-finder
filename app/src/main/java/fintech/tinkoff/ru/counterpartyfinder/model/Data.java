package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

//import io.realm.RealmObject;

/**
 * 27.02.2018.
 */
@lombok.Data
public class Data extends RealmObject implements Serializable {

    @PrimaryKey
    private String uuid;

    private String kpp;
    private String capital;
    private Management management;
    private BranchType branch_type;
    private Long branch_count;
    private String source;
    private String qc;
    private String hid;
    private DataType type;
    private State state;
    private Opf opf;
    private Name name;
    private String inn;
    private String ogrn;
    private String okpo;
    private String okved;
    private List<String> okveds;
    private List<String> authoroties;
    private List<String> documents;
    private List<String> licenses;
    private Address address;
    private List<String> phones;
    private List<String> emails;
    private Long ogrn_date;
    private String okved_type;
}