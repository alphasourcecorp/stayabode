package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class CategoryView {

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    List<Category> categoryList = new ArrayList<>();
}
