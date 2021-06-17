package stayabode.foodyHive.models;

public class WeekDays {
    String weekDayName;
    String id;


    int idForCheck;

    public int getIdForCheck() {
        return idForCheck;
    }

    public void setIdForCheck(int idForCheck) {
        this.idForCheck = idForCheck;
    }


    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    Boolean isSelected;

    public String getWeekDayName() {
        return weekDayName;
    }

    public void setWeekDayName(String weekDayName) {
        this.weekDayName = weekDayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
