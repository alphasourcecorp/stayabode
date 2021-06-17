package stayabode.foodyHive.models;

public class Reviews {
    String userName;
    String reviewsDescription;
    String id;
    String image;
    String ratingCount;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReviewsDescription() {
        return reviewsDescription;
    }

    public void setReviewsDescription(String reviewsDescription) {
        this.reviewsDescription = reviewsDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
