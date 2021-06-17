package stayabode.foodyHive.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RequestBtoBItem implements Serializable {
    public String partitionKey;
    public String rowKey;
    public Date timestamp;
    public String eTag;
    public String requestId;
    public String companyId;
    public String companyName;
    public String subscriberName;
    public String emailId;
    public String phoneNumber;
    public List<DeliveryAddress> deliveryAddresses;
    public Date subscriptionDate;
    public List<SchedullingInfo> schedullingInfo;
    public String promoCode;
    public String notes;
    public int totalOrderValue;
    public String status;
    public String emailFlag;

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public List<SchedullingInfo> getSchedullingInfo() {
        return schedullingInfo;
    }

    public void setSchedullingInfo(List<SchedullingInfo> schedullingInfo) {
        this.schedullingInfo = schedullingInfo;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(int totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmailFlag() {
        return emailFlag;
    }

    public void setEmailFlag(String emailFlag) {
        this.emailFlag = emailFlag;
    }



    public class DeliveryAddress{
        public String addressLine1;
        public String addressLine2;
        public String city;
        public String state;
        public String country;
        public String postalCode;
    }

    class DishesItem{
        public String dishItemName;
        public String description;
        public String dishImageUrl;
        public Double cost;
        public int noOfUnits;
        public Double taxValue;
        public Double packingCharges;
        public Double deliveryCharges;
        public boolean isDishRequired;
    }

    class ItemDeliveryTime{
        public String lunch;
        public String breakfast;
        public String dinner;
        public String snacks;
    }

    class SchedullingInfo{
        public String partitionKey;
        public String rowKey;
        public Date timestamp;
        public String eTag;
        public String scheduleID;
        public String companyID;
        public List<DishesItem> dishesItems;
        public boolean isBreakfast;
        public boolean isLunch;
        public boolean isDinner;
        public Date startDate;
        public Date endDate;
        public String pointOfContact;
        public Date createdDate;
        public Date updatedDate;
        public List<Date> exclusionDates;
        public List<Integer> exclusionWeeks;
        public List<ItemDeliveryTime> itemDeliveryTime;
        public String exclusionReason;
        public boolean isActive;
    }


}





