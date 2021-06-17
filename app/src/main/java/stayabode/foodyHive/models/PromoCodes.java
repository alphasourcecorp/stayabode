package stayabode.foodyHive.models;

public class PromoCodes {
    String promoCodeText;
    String id;
    String status;
    Boolean isExpired;
    Boolean isUsed;

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public String getPromoCodeText() {
        return promoCodeText;
    }

    public void setPromoCodeText(String promoCodeText) {
        this.promoCodeText = promoCodeText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String startDate;
    String endDate;
    String discount;
    String retails;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRetails() {
        return retails;
    }

    public void setRetails(String retails) {
        this.retails = retails;
    }
}
