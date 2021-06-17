package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class TodaysChefMenu {
    List<FoodItem> foodItemList = new ArrayList<>();

    public List<FoodItem> getFoodItemList() {
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
    }

    public List<FoodItem> getFoodItemLists() {
        return foodItemLists;
    }

    public void setFoodItemLists(List<FoodItem> foodItemLists) {
        this.foodItemLists = foodItemLists;
    }

    List<FoodItem> foodItemLists = new ArrayList<>();
}
