package stayabode.foodyHive.models;

public class MenuDishType {

    String id;
    String name;
    Boolean isSelected;
    int selectedPosition;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

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
