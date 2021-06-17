package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class NearByCloudKitchenList {
    List<CloudKitchen>  cloudKitchenList=new ArrayList<>();

    public void setCloudKitchenList(List<CloudKitchen> cloudKitchenList) {
        this.cloudKitchenList = cloudKitchenList;
    }

    public List<CloudKitchen> getCloudKitchenList() {
        return cloudKitchenList;
    }
}
