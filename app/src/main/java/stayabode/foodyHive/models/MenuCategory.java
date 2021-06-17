package stayabode.foodyHive.models;

public class MenuCategory {
    String id;
    String name;
    Boolean isSelected;

    int idForCheck;

    public int getIdForCheck() {
        return idForCheck;
    }

    public void setIdForCheck(int idForCheck) {
        this.idForCheck = idForCheck;
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
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
