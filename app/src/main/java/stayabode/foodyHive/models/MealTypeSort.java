package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class MealTypeSort {
    String name;
//    List<MealTypeSortTypes> mealTypeSortList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<MealTypeSortTypes> getMealTypeSortList() {
//        return mealTypeSortList;
//    }
//
//    public void setMealTypeSortList(List<MealTypeSortTypes> mealTypeSortList) {
//        this.mealTypeSortList = mealTypeSortList;
//    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    List<Category> categoryList = new ArrayList<>();
}
