package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class RecentlyViewed {

    List<FoodItem> foodItemList = new ArrayList<>();

    public List<FoodItem> getFoodItemList() {
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
    }
}
