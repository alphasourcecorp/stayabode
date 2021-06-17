package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class RequestBtoB {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    List<RequestBtoBItem> RequestBtoBItemList = new ArrayList<>();

    public List<RequestBtoBItem> getFoodItemList() {
        return RequestBtoBItemList;
    }

    public void setFoodItemList(List<RequestBtoBItem> RequestBtoBItemList) {
        this.RequestBtoBItemList = RequestBtoBItemList;
    }
}
