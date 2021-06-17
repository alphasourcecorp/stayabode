package stayabode.foodyHive.models;

import java.util.List;

public class ParentItemOrderDetail {
    // Declaration of the variables

    private String chefImage;
    private String chefname;
    private String orderId;
    private String orderStatus;
    private String orderamount;

    public String getChefImage() {
        return chefImage;
    }

    public void setChefImage(String chefImage) {
        this.chefImage = chefImage;
    }

    public String getChefname() {
        return chefname;
    }

    public void setChefname(String chefname) {
        this.chefname = chefname;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }



    private List<ChildItemOrderDetail> ChildItemList;

    // Constructor of the class
    // to initialize the variables
//    public ParentItemOrderDetail(String chefImage,String chefname, String orderId,String orderStatus,String orderamount,List<ChildItemOrderDetail> ChildItemList)
//    {
//
//        this.chefImage = chefImage;
//        this.chefname = chefname;
//        this.orderId = orderId;
//        this.orderStatus = orderStatus;
//        this.orderamount = orderamount;
//        this.ChildItemList = ChildItemList;
//    }

    public ParentItemOrderDetail(String chefname, String orderId,String orderStatus,String orderamount,List<ChildItemOrderDetail> ChildItemList)
    {

        this.chefImage = chefImage;
        this.chefname = chefname;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderamount = orderamount;
        this.ChildItemList = ChildItemList;
    }

    // Getter and Setter methods
    // for each parameter


    public List<ChildItemOrderDetail> getChildItemList()
    {
        return ChildItemList;
    }

    public void setChildItemList(List<ChildItemOrderDetail> childItemList)
    {
        ChildItemList = childItemList;
    }



}
