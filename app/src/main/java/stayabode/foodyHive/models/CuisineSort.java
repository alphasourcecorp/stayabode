package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class CuisineSort {

    String name;
//    List<CuisineSortTypes> cuisineSortTypesList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<CuisineSortTypes> getCuisineSortTypesList() {
//        return cuisineSortTypesList;
//    }
//
//    public void setCuisineSortTypesList(List<CuisineSortTypes> cuisineSortTypesList) {
//        this.cuisineSortTypesList = cuisineSortTypesList;
//    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    List<Category> categoryList = new ArrayList<>();
}
