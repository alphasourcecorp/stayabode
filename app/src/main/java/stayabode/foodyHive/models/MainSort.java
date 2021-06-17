package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class MainSort {
    public List<MainSortItems> getMainSortList() {
        return mainSortList;
    }

    public void setMainSortList(List<MainSortItems> mainSortList) {
        this.mainSortList = mainSortList;
    }

    List<MainSortItems> mainSortList = new ArrayList<>();

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    List<Category> categoryList = new ArrayList<>();
}
