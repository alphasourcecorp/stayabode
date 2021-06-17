package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentOrders {
    String id;
    String orderNo;
    String chefId;
    int orderStatusEnum;
    String orderCreatedDate;
    String minsBeforeCooking;
    String orderNoWithoutDash;

    public String getOrderNoWithoutDash() {
        return orderNoWithoutDash;
    }

    public void setOrderNoWithoutDash(String orderNoWithoutDash) {
        this.orderNoWithoutDash = orderNoWithoutDash;
    }

    public String getMinsBeforeCooking() {
        return minsBeforeCooking;
    }

    public void setMinsBeforeCooking(String minsBeforeCooking) {
        this.minsBeforeCooking = minsBeforeCooking;
    }

    public String getOrderCreatedDate() {
        return orderCreatedDate;
    }

    public void setOrderCreatedDate(String orderCreatedDate) {
        this.orderCreatedDate = orderCreatedDate;
    }

    public int getMinutesForPreparation() {
        return minutesForPreparation;
    }

    public void setMinutesForPreparation(int minutesForPreparation) {
        this.minutesForPreparation = minutesForPreparation;
    }

    public int getSecondsForPreparation() {
        return secondsForPreparation;
    }

    public void setSecondsForPreparation(int secondsForPreparation) {
        this.secondsForPreparation = secondsForPreparation;
    }

    public int getHoursForPreparation() {
        return hoursForPreparation;
    }

    public void setHoursForPreparation(int hoursForPreparation) {
        this.hoursForPreparation = hoursForPreparation;
    }

    public int getMinutesForCooking() {
        return minutesForCooking;
    }

    public void setMinutesForCooking(int minutesForCooking) {
        this.minutesForCooking = minutesForCooking;
    }

    public int getSecondsForCooking() {
        return secondsForCooking;
    }

    public void setSecondsForCooking(int secondsForCooking) {
        this.secondsForCooking = secondsForCooking;
    }

    public int getHoursForCooking() {
        return hoursForCooking;
    }

    public void setHoursForCooking(int hoursForCooking) {
        this.hoursForCooking = hoursForCooking;
    }

    int minutesForPreparation = 0;
    int secondsForPreparation = 0;
    int hoursForPreparation = 0;

    int minutesForCooking = 0;
    int secondsForCooking = 0;
    int hoursForCooking = 0;

    public int getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(int orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    List<FoodItem> foodItemList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public List<FoodItem> getFoodItemList() {
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    int cookingTime;

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getCancelledreason() {
        return cancelledreason;
    }

    public void setCancelledreason(String cancelledreason) {
        this.cancelledreason = cancelledreason;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    String totalAmount;
    int preparationTime;
    String orderStatus;
    String paymentmethod;
    String cancelledreason;
    String createddate;

    long maxDifferences;

    public long getEllapsedDifferences() {
        return ellapsedDifferences;
    }

    public void setEllapsedDifferences(long ellapsedDifferences) {
        this.ellapsedDifferences = ellapsedDifferences;
    }

    long ellapsedDifferences;

    public long getStartCookingTimeDifference() {
        return startCookingTimeDifference;
    }

    public void setStartCookingTimeDifference(long startCookingTimeDifference) {
        this.startCookingTimeDifference = startCookingTimeDifference;
    }

    long startCookingTimeDifference;

    public long getMaxDifferences() {
        return maxDifferences;
    }

    public void setMaxDifferences(long differences) {
        this.maxDifferences = differences;
    }

    long startTimeinMillis;
    long endTimeinMillis;


    int preparationTimeHours;
    int preparationTimeMinutes;
    int preparationTimeSeconds;

    public int getPreparationTimeHours() {
        return preparationTimeHours;
    }

    public void setPreparationTimeHours(int preparationTimeHours) {
        this.preparationTimeHours = preparationTimeHours;
    }

    public int getPreparationTimeMinutes() {
        return preparationTimeMinutes;
    }

    public void setPreparationTimeMinutes(int preparationTimeMinutes) {
        this.preparationTimeMinutes = preparationTimeMinutes;
    }

    public int getPreparationTimeSeconds() {
        return preparationTimeSeconds;
    }

    public void setPreparationTimeSeconds(int preparationTimeSeconds) {
        this.preparationTimeSeconds = preparationTimeSeconds;
    }

    public long getStartTimeinMillis() {
        return startTimeinMillis;
    }

    public void setStartTimeinMillis(long startTimeinMillis) {
        this.startTimeinMillis = startTimeinMillis;
    }

    public long getEndTimeinMillis() {
        return endTimeinMillis;
    }

    public void setEndTimeinMillis(long endTimeinMillis) {
        this.endTimeinMillis = endTimeinMillis;
    }

    Date startDateTime;

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    Date endDateTime;
}
