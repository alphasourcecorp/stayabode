package stayabode.foodyHive.models;

public class CorporateMenuModel {

    private String id;
    private String CategoryTitle;
    private String CategorySubTitle;
    private String count;
    private String postion;






    public CorporateMenuModel(String id, String CategoryTitle,String CategorySubTitle,String count,String postion) {
        this.id = id;
        this.CategoryTitle = CategoryTitle;
        this.CategorySubTitle = CategorySubTitle;
        this.count = count;
        this.postion = postion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getCategorySubTitle() {
        return CategorySubTitle;
    }

    public void setCategorySubTitle(String categorySubTitle) {
        CategorySubTitle = categorySubTitle;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }




}
