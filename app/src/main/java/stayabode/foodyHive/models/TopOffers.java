package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class TopOffers {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    List<FoodItem> foodItemList = new ArrayList<>();

    public List<FoodItem> getFoodItemList() {
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
    }
}
