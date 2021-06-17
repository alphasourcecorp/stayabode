package stayabode.foodyHive.models;

public class OrderByWeek {
    String day;
    String bulkOrderCount;
    String subscription;
    String retail;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBulkOrderCount() {
        return bulkOrderCount;
    }

    public void setBulkOrderCount(String bulkOrderCount) {
        this.bulkOrderCount = bulkOrderCount;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getRetail() {
        return retail;
    }

    public void setRetail(String retail) {
        this.retail = retail;
    }

}
