package stayabode.foodyHive.models;

public class ChildItemOrderDetail {

    private String dishImage;
    private String dishname;
    private String dishquantity;


    // Constructor of the class
    // to initialize the variable*
    public ChildItemOrderDetail(String dishImage,String dishname,String dishquantity)
    {
        this.dishImage = dishImage;
        this.dishname = dishname;
        this.dishquantity = dishquantity;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public String getDishquantity() {
        return dishquantity;
    }

    public void setDishquantity(String dishquantity) {
        this.dishquantity = dishquantity;
    }





}
