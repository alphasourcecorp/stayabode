package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class CategorySort {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

//    List<CategorySortTypes> categorySortTypesList = new ArrayList<>();
//
//    public List<CategorySortTypes> getCategorySortTypesList() {
//        return categorySortTypesList;
//    }
//
//    public void setCategorySortTypesList(List<CategorySortTypes> categorySortTypesList) {
//        this.categorySortTypesList = categorySortTypesList;
//    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    List<Category> categoryList = new ArrayList<>();
}
