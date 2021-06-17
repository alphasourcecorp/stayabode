package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class ReviewsHeader {
    List<Reviews> reviewsList = new ArrayList<>();

    public List<Reviews> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }
    Reviews reviews = new Reviews();

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }
}
