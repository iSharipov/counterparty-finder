package fintech.tinkoff.ru.counterpartyfinder.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 07.03.2018.
 */

public class BranchType implements Serializable {
    private String branchType;

    public Branch getBranchType() {
        return Branch.valueOf(branchType);
    }

    public void setBranchType(Branch branch) {
        this.branchType = branch.toString();
    }
}

