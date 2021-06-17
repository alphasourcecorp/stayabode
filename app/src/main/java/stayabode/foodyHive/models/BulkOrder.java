package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class BulkOrder {
    List<OrderByMonth> orderByMonthList = new ArrayList<>();

    public List<OrderByMonth> getOrderByMonthList() {
        return orderByMonthList;
    }

    public void setOrderByMonthList(List<OrderByMonth> orderByMonthList) {
        this.orderByMonthList = orderByMonthList;
    }
}
