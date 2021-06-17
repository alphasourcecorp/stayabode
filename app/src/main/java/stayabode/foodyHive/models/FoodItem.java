package stayabode.foodyHive.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodItem implements Serializable {
    String foodImage;
    String foodName;
    String foodId;
    String foodRatings;
    String time;
    String price;
    String availableStatus;
    String reviewsCount;
    String ratingCount;
    float ratingAverage;
    String availableQuantity;
    String chefImage;
    String chefId;
    String chefName;
    String itemRatingAverage;
    int proteintCount;
    int fatCount;
    int carbsCount;
    Boolean isFavourite = false;
    String mealPrice;
    String cartId;
    int cartQuantity;
    int subTotal;
    String discountedPercentage;

    public String getDiscountedPercentage() {
        return discountedPercentage;
    }

    public void setDiscountedPercentage(String discountedPercentage) {
        this.discountedPercentage = discountedPercentage;
    }

    public float getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(float ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public String getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(String mealPrice) {
        this.mealPrice = mealPrice;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    List<Object> objectList=new ArrayList<>();

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    public int getCartAddedQuantity() {
        return cartAddedQuantity;
    }

    public void setCartAddedQuantity(int cartAddedQuantity) {
        this.cartAddedQuantity = cartAddedQuantity;
    }

    int cartAddedQuantity = 1;

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    int fibreCount;

    public int getProteintCount() {
        return proteintCount;
    }

    public void setProteintCount(int proteintCount) {
        this.proteintCount = proteintCount;
    }

    public int getFatCount() {
        return fatCount;
    }

    public void setFatCount(int fatCount) {
        this.fatCount = fatCount;
    }

    public int getCarbsCount() {
        return carbsCount;
    }

    public void setCarbsCount(int carbsCount) {
        this.carbsCount = carbsCount;
    }

    public int getFibreCount() {
        return fibreCount;
    }

    public void setFibreCount(int fibreCount) {
        this.fibreCount = fibreCount;
    }

    public String getItemRatingAverage() {
        return itemRatingAverage;
    }

    public void setItemRatingAverage(String itemRatingAverage) {
        this.itemRatingAverage = itemRatingAverage;
    }

    String chefprofession;
    int chefratingAverage;
    int chefratingCount;
    int chefsubscribersCount;

    public String getChefImage() {
        return chefImage;
    }

    public void setChefImage(String chefImage) {
        this.chefImage = chefImage;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getChefprofession() {
        return chefprofession;
    }

    public void setChefprofession(String chefprofession) {
        this.chefprofession = chefprofession;
    }

    public int getChefratingAverage() {
        return chefratingAverage;
    }

    public void setChefratingAverage(int chefratingAverage) {
        this.chefratingAverage = chefratingAverage;
    }

    public int getChefratingCount() {
        return chefratingCount;
    }

    public void setChefratingCount(int chefratingCount) {
        this.chefratingCount = chefratingCount;
    }

    public int getChefsubscribersCount() {
        return chefsubscribersCount;
    }

    public void setChefsubscribersCount(int chefsubscribersCount) {
        this.chefsubscribersCount = chefsubscribersCount;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Boolean getAutoAcceptOrder() {
        return autoAcceptOrder;
    }

    public void setAutoAcceptOrder(Boolean autoAcceptOrder) {
        this.autoAcceptOrder = autoAcceptOrder;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    String shortDescription;
    Boolean autoAcceptOrder;
    Boolean isActive;
    Boolean isApproved;
    String savedPrice;
    int availQty;
    Boolean isAvailable;

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public int getAvailQty() {
        return availQty;
    }

    public void setAvailQty(int availQty) {
        this.availQty = availQty;
    }

    public String getSavedPrice() {
        return savedPrice;
    }

    public void setSavedPrice(String savedPrice) {
        this.savedPrice = savedPrice;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    String discountedPrice;

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodRatings() {
        return foodRatings;
    }

    public void setFoodRatings(String foodRatings) {
        this.foodRatings = foodRatings;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String deliveryTime;

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(String availableStatus) {
        this.availableStatus = availableStatus;
    }

    public String getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(String reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getTypeOfDish() {
        return typeOfDish;
    }

    public void setTypeOfDish(String typeOfDish) {
        this.typeOfDish = typeOfDish;
    }

    String typeOfDish;

    int caloriesCount;
    int gramsCount;

    public int getCaloriesCount() {
        return caloriesCount;
    }

    public void setCaloriesCount(int caloriesCount) {
        this.caloriesCount = caloriesCount;
    }

    public int getGramsCount() {
        return gramsCount;
    }

    public void setGramsCount(int gramsCount) {
        this.gramsCount = gramsCount;
    }
}
