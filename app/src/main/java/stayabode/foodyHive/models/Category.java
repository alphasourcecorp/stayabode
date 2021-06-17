package stayabode.foodyHive.models;

public class Category {
    String name;
    String id;
    String namewithTitle;

    public int getLastselectedPosition() {
        return lastselectedPosition;
    }

    public void setLastselectedPosition(int lastselectedPosition) {
        this.lastselectedPosition = lastselectedPosition;
    }

    int lastselectedPosition;

    public String getNamewithTitle() {
        return namewithTitle;
    }

    public void setNamewithTitle(String namewithTitle) {
        this.namewithTitle = namewithTitle;
    }

    int idforDish;

    public int getIdforDish() {
        return idforDish;
    }

    public void setIdforDish(int idforDish) {
        this.idforDish = idforDish;
    }

    String setImage;

    public String getSetImage() {
        return setImage;
    }

    public void setSetImage(String setImage) {
        this.setImage = setImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String topColor;

    public String getTopColor() {
        return topColor;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public String getBottomColor() {
        return bottomColor;
    }

    public void setBottomColor(String bottomColor) {
        this.bottomColor = bottomColor;
    }

    String bottomColor;
    Boolean isSelected;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    int ratingCount;


    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
