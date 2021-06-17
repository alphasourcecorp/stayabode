package stayabode.foodyHive.models;

public class TotalRevenues {
    String totalFranchiese;
    String quotedThisMonth;

    public String getTotalFranchiese() {
        return totalFranchiese;
    }

    public void setTotalFranchiese(String totalFranchiese) {
        this.totalFranchiese = totalFranchiese;
    }

    public String getQuotedThisMonth() {
        return quotedThisMonth;
    }

    public void setQuotedThisMonth(String quotedThisMonth) {
        this.quotedThisMonth = quotedThisMonth;
    }

    public String getTotalRevenuesThisMonth() {
        return totalRevenuesThisMonth;
    }

    public void setTotalRevenuesThisMonth(String totalRevenuesThisMonth) {
        this.totalRevenuesThisMonth = totalRevenuesThisMonth;
    }

    public String getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(String totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    String totalRevenuesThisMonth;
    String totalInvoices;

    public String getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(String completedOrders) {
        this.completedOrders = completedOrders;
    }

    String completedOrders;
}
