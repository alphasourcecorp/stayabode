package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class CancelledOrdersHeader {

    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    List<Orders> ordersList = new ArrayList<>();

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    List<FoodItem> foodItemList = new ArrayList<>();

    public List<FoodItem> getFoodItemList() {
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
    }
}
