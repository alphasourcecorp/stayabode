package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class MyBasket {

    String foodImage;
    String foodName;
    String foodId;
    String foodRatings;
    String time;
    String price;
    String availableStatus;
    String reviewsCount;
    String ratingCount;
    String availableQuantity;
    String chefImage;
    String chefId;
    String chefName;
    int itemRatingAverage;
    int proteintCount;
    int fatCount;
    int carbsCount;
    int fibreCount;
    String cartId;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

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

    public int getItemRatingAverage() {
        return itemRatingAverage;
    }

    public void setItemRatingAverage(int itemRatingAverage) {
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

    int cartQuantity;

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
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


    List<ItemAddOns> addOns = new ArrayList<>();

    public List<ItemAddOns> getAddOns() {
        return addOns;
    }

    public void setAddOns(List<ItemAddOns> addOns) {
        this.addOns = addOns;
    }

    int mealPrice;
    int total;
    int totalTax;
    int deliveryCharges;

    public int getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(int mealPrice) {
        this.mealPrice = mealPrice;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
    }

    public int getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(int deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    int subTotal;
    int availQty;

    public int getAvailQty() {
        return availQty;
    }

    public void setAvailQty(int availQty) {
        this.availQty = availQty;
    }
}
