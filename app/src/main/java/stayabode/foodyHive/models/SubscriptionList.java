package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionList {

    List<Subscriptions> subscriptionsList = new ArrayList<>();

    public List<Subscriptions> getSubscriptionsList() {
        return subscriptionsList;
    }

    public void setSubscriptionsList(List<Subscriptions> subscriptionsList) {
        this.subscriptionsList = subscriptionsList;
    }
}
