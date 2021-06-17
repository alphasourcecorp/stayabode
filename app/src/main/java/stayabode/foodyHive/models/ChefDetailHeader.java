package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class ChefDetailHeader {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    List<AboutInfoChef> aboutInfoChefs = new ArrayList<>();

    public List<AboutInfoChef> getAboutInfoChefs() {
        return aboutInfoChefs;
    }

    public void setAboutInfoChefs(List<AboutInfoChef> aboutInfoChefs) {
        this.aboutInfoChefs = aboutInfoChefs;
    }

    public List<Reviews> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public List<FoodItem> getFoodItemList() {
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
    }

    List<Reviews> reviewsList = new ArrayList<>();
    List<FoodItem> foodItemList = new ArrayList<>();
}
