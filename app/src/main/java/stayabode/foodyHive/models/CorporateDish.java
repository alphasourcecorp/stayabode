package stayabode.foodyHive.models;

import java.util.List;

public class CorporateDish {
    public String id;
    public String categoryTitle;
    public String categorySubTitle;
    public String categoryDescrption;
    public String categoryimg;
    public List<MenuDetail> menuDetails;
    public static class MenuDetail{
        public String dishId;
        public List<String> dishImage;
        public String chefName;
        public String dishName;
        public String dishSubName;
        public double saleAmount;
        public String dishDescrption;
        public String saleKgMg;
        public double calories;
        public double protine;
        public double fat;
        public double carbs;
        public double fiber;
        public double sugar;

        public boolean Breakfast;
        public boolean Lunch;
        public boolean Dinner;
        public boolean Snacks;

        public boolean isBreakfast() {
            return Breakfast;
        }

        public void setBreakfast(boolean breakfast) {
            Breakfast = breakfast;
        }

        public boolean isLunch() {
            return Lunch;
        }

        public void setLunch(boolean lunch) {
            Lunch = lunch;
        }

        public boolean isDinner() {
            return Dinner;
        }

        public void setDinner(boolean dinner) {
            Dinner = dinner;
        }

        public boolean isSnacks() {
            return Snacks;
        }

        public void setSnacks(boolean snacks) {
            Snacks = snacks;
        }



        public List<String> suitableFor;
        public int quantity;
        public boolean displayAddbutton;

        public String getDishId() {
            return dishId;
        }

        public void setDishId(String dishId) {
            this.dishId = dishId;
        }

        public List<String> getDishImage() {
            return dishImage;
        }

        public void setDishImage(List<String> dishImage) {
            this.dishImage = dishImage;
        }

        public String getChefName() {
            return chefName;
        }

        public void setChefName(String chefName) {
            this.chefName = chefName;
        }

        public String getDishName() {
            return dishName;
        }

        public void setDishName(String dishName) {
            this.dishName = dishName;
        }

        public String getDishSubName() {
            return dishSubName;
        }

        public void setDishSubName(String dishSubName) {
            this.dishSubName = dishSubName;
        }

        public double getSaleAmount() {
            return saleAmount;
        }

        public void setSaleAmount(double saleAmount) {
            this.saleAmount = saleAmount;
        }

        public String getDishDescrption() {
            return dishDescrption;
        }

        public void setDishDescrption(String dishDescrption) {
            this.dishDescrption = dishDescrption;
        }

        public String getSaleKgMg() {
            return saleKgMg;
        }

        public void setSaleKgMg(String saleKgMg) {
            this.saleKgMg = saleKgMg;
        }

        public double getCalories() {
            return calories;
        }

        public void setCalories(double calories) {
            this.calories = calories;
        }

        public double getProtine() {
            return protine;
        }

        public void setProtine(double protine) {
            this.protine = protine;
        }

        public double getFat() {
            return fat;
        }

        public void setFat(double fat) {
            this.fat = fat;
        }

        public double getCarbs() {
            return carbs;
        }

        public void setCarbs(double carbs) {
            this.carbs = carbs;
        }

        public double getFiber() {
            return fiber;
        }

        public void setFiber(double fiber) {
            this.fiber = fiber;
        }

        public double getSugar() {
            return sugar;
        }

        public void setSugar(double sugar) {
            this.sugar = sugar;
        }

        public List<String> getSuitableFor() {
            return suitableFor;
        }

        public void setSuitableFor(List<String> suitableFor) {
            this.suitableFor = suitableFor;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public boolean isDisplayAddbutton() {
            return displayAddbutton;
        }

        public void setDisplayAddbutton(boolean displayAddbutton) {
            this.displayAddbutton = displayAddbutton;
        }


        public MenuDetail(String dishId, List<String> dishImage,String chefName,String dishName,String dishSubName,double saleAmount,String dishDescrption,String saleKgMg,double calories,double protine,double fat,double carbs,double fiber, double sugar,List<String> suitableFor,int quantity,boolean displayAddbutton,boolean Breakfast,boolean Lunch,boolean Dinner,boolean Snacks) {
            this.dishId = dishId;
            this.dishImage = dishImage;
            this.chefName = chefName;
            this.dishName = dishName;
            this.dishSubName = dishSubName;
            this.saleAmount = saleAmount;
            this.dishDescrption = dishDescrption;
            this.saleKgMg = saleKgMg;
            this.calories = calories;
            this.protine = protine;
            this.fat = fat;
            this.carbs = carbs;
            this.fiber = fiber;
            this.sugar = sugar;
            this.suitableFor = suitableFor;
            this.quantity = quantity;
            this.displayAddbutton = displayAddbutton;

            this.Breakfast = Breakfast;
            this.Lunch = Lunch;
            this.Dinner = Dinner;
            this.Snacks = Snacks;
        }


    }


    public CorporateDish(String id, String categoryTitle,String categorySubTitle,String categoryDescrption,String categoryimg,List<MenuDetail> menuDetails) {
        this.id = id;
        this.categoryTitle = categoryTitle;
        this.categorySubTitle = categorySubTitle;
        this.categoryDescrption = categoryDescrption;
        this.categoryimg = categoryimg;
        this.menuDetails = menuDetails;
    }


}





