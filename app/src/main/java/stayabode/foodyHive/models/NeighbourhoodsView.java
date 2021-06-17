package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class NeighbourhoodsView {

    List<Chef> chefList = new ArrayList<>();

    public List<Chef> getChefList() {
        return chefList;
    }

    public void setChefList(List<Chef> chefList) {
        this.chefList = chefList;
    }
}
