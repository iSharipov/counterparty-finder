package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

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
    private Object source;
    private Object qc;
    private String hid;
    private DataType type;
    private State state;
    private Opf opf;
    private Name name;
}
