package stayabode.foodyHive.models;

public class ItemAddOns {
    String id;
    int idForCheck;

    public int getIdForCheck() {
        return idForCheck;
    }

    public void setIdForCheck(int idForCheck) {
        this.idForCheck = idForCheck;
    }

    String name;
    Boolean selected;
    String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
