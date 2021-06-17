package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class FranchiseList {

    public List<Franchise> getFranchiseList() {
        return franchiseList;
    }

    public void setFranchiseList(List<Franchise> franchiseList) {
        this.franchiseList = franchiseList;
    }

    List<Franchise> franchiseList = new ArrayList<>();
}
