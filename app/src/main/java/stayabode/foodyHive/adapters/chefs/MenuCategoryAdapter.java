package stayabode.foodyHive.adapters.chefs;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.ChefRewards;
import stayabode.foodyHive.models.DishTags;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.MenuCategory;
import stayabode.foodyHive.models.MenuCuisine;
import stayabode.foodyHive.models.MenuDishType;
import stayabode.foodyHive.models.WeekDays;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.fragments.chefs.AddChefMenuFragment.selectedMenuCategoriesforAdd;
import static stayabode.foodyHive.fragments.chefs.AddChefMenuFragment.selectedMenuCuisineforAdd;
import static stayabode.foodyHive.fragments.chefs.AddChefMenuFragment.selectedMenuDishTypeforAdd;
import static stayabode.foodyHive.fragments.chefs.AddChefMenuFragment.selectedWeekDaysforAdd;
import static stayabode.foodyHive.fragments.chefs.EditProfileFragment.selectedWeekDaysforAddInEditProfile;

public class MenuCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    String from = "";

    int selectedPositionMenu = -1;
    int selectedPositionDish = -1;
    int selectedPositionCuisine = -1;
    int selectedPositionWeek = -1;

    public MenuCategoryAdapter(Context context,List<Object> objectList,Typeface fontBold,Typeface fontRegular,String from)
    {
        this.context = context;
        this.objectList = objectList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
        this.from = from;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(viewType,parent,false);

        if(viewType == R.layout.menu_category_item)
        {
            return new CategoryItemViewHolder(view);
        }
        else if(viewType == R.layout.menu_dish_item)
        {
            return new DishesItemViewHolder(view);
        }
        else if(viewType == R.layout.menu_cuisine_item)
        {
            return new CuisinesItemViewHolder(view);
        }
        else if(viewType == R.layout.chef_order_food_items)
        {
            return new FoodItemsViewHolder(view);
        }
        else if(viewType == R.layout.chef_reward_item)
        {
            return new ChefRewardsItemViewHolder(view);
        }
        else if(viewType == R.layout.menu_tags_item)
        {
            return new TagsCategoryItemsViewHolder(view);
        }
        else
        {
            return new WeekDaysItemViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(objectList.get(position) instanceof MenuCategory)
        {
            CategoryItemViewHolder categoryItemViewHolder = (CategoryItemViewHolder)holder;
            MenuCategory menuCategory = (MenuCategory)objectList.get(position);
            categoryItemViewHolder.name.setTypeface(fontRegular);
            categoryItemViewHolder.name.setText(menuCategory.getName());

            if(menuCategory.getSelected())
            {

                categoryItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                categoryItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                categoryItemViewHolder.itemView.setSelected(true);
                menuCategory.setSelected(true);

            }
            else
            {
                categoryItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                categoryItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                categoryItemViewHolder.itemView.setSelected(false);
                menuCategory.setSelected(false);

            }

            categoryItemViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!menuCategory.getSelected())
                    {
                        categoryItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                        categoryItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                        categoryItemViewHolder.itemView.setSelected(true);
                        ((MenuCategory) objectList.get(position)).setSelected(true);
                        menuCategory.setName(menuCategory.getName());
                        selectedMenuCategoriesforAdd.add(menuCategory);

                    }
                    else
                    {

                        categoryItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                        categoryItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                        categoryItemViewHolder.itemView.setSelected(false);
                        ((MenuCategory) objectList.get(position)).setSelected(false);
                        for (int i=0;i < selectedMenuCategoriesforAdd.size();i++)
                        {
                            if(selectedMenuCategoriesforAdd.get(i).getId().equals(((MenuCategory) objectList.get(position)).getId()))
                            {
                                selectedMenuCategoriesforAdd.get(i).setSelected(false);
                            }

                        }
                        selectedMenuCategoriesforAdd.remove(((MenuCategory) objectList.get(position)));
                    }
                }
            });
        }
        else if(objectList.get(position) instanceof MenuDishType)
        {
            DishesItemViewHolder dishesItemViewHolder = (DishesItemViewHolder)holder;
            MenuDishType menuDishType = (MenuDishType)objectList.get(position);
            dishesItemViewHolder.name.setTypeface(fontRegular);
            dishesItemViewHolder.name.setText(menuDishType.getName());

            if(menuDishType.getSelected())
            {
                dishesItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                dishesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                dishesItemViewHolder.itemView.setSelected(true);
                menuDishType.setSelected(true);
            }
            else
            {
                dishesItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                dishesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                dishesItemViewHolder.itemView.setSelected(false);
                menuDishType.setSelected(false);

            }

            dishesItemViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!menuDishType.getSelected())
                    {
                        dishesItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                        dishesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                        dishesItemViewHolder.itemView.setSelected(true);
                        ((MenuDishType) objectList.get(position)).setSelected(true);
                        menuDishType.setName(menuDishType.getName());
                        selectedMenuDishTypeforAdd.add(menuDishType);

                    }
                    else
                    {
                        dishesItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                        dishesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                        dishesItemViewHolder.itemView.setSelected(false);

                        ((MenuDishType) objectList.get(position)).setSelected(false);
                        for (int i=0;i < selectedMenuDishTypeforAdd.size();i++)
                        {
                            if(selectedMenuDishTypeforAdd.get(i).getId().equals(((MenuDishType) objectList.get(position)).getId()))
                            {
                                selectedMenuDishTypeforAdd.get(i).setSelected(false);
                            }

                        }
                        selectedMenuDishTypeforAdd.remove(((MenuDishType) objectList.get(position)));
                    }
                }
            });
        }
        else if(objectList.get(position) instanceof MenuCuisine)
        {
            CuisinesItemViewHolder cuisinesItemViewHolder = (CuisinesItemViewHolder)holder;
            MenuCuisine menuCuisine = (MenuCuisine)objectList.get(position);
            cuisinesItemViewHolder.name.setTypeface(fontRegular);
            cuisinesItemViewHolder.name.setText(menuCuisine.getName());


            if(menuCuisine.getSelected())
            {
                cuisinesItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                cuisinesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                cuisinesItemViewHolder.itemView.setSelected(true);
                menuCuisine.setSelected(true);
            }
            else
            {
                cuisinesItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                cuisinesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                cuisinesItemViewHolder.itemView.setSelected(false);
                menuCuisine.setSelected(false);
            }

            cuisinesItemViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!menuCuisine.getSelected())
                    {
                        cuisinesItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                        cuisinesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                        cuisinesItemViewHolder.itemView.setSelected(true);
                        ((MenuCuisine) objectList.get(position)).setSelected(true);
                        menuCuisine.setName(menuCuisine.getName());
                        selectedMenuCuisineforAdd.add(menuCuisine);

                    }
                    else
                    {
                        cuisinesItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                        cuisinesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                        cuisinesItemViewHolder.itemView.setSelected(false);

                        ((MenuCuisine) objectList.get(position)).setSelected(false);
                        for (int i=0;i < selectedMenuCuisineforAdd.size();i++)
                        {
                            if(selectedMenuCuisineforAdd.get(i).getId().equals(((MenuCuisine) objectList.get(position)).getId()))
                            {
                                selectedMenuCuisineforAdd.get(i).setSelected(false);
                            }

                        }
                        selectedMenuCuisineforAdd.remove(((MenuCuisine) objectList.get(position)));
                    }
                }
            });
        }
        else if(objectList.get(position) instanceof DishTags)
        {
            TagsCategoryItemsViewHolder tagsCategoryItemsViewHolder = (TagsCategoryItemsViewHolder)holder;
            DishTags dishTags = (DishTags)objectList.get(position);
            tagsCategoryItemsViewHolder.name.setTypeface(fontRegular);
            tagsCategoryItemsViewHolder.name.setText(dishTags.getName());


            if(position == 0)
            {
                tagsCategoryItemsViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                tagsCategoryItemsViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                tagsCategoryItemsViewHolder.itemView.setSelected(true);
                dishTags.setSelected(true);
            }
            else
            {
                tagsCategoryItemsViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                tagsCategoryItemsViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                tagsCategoryItemsViewHolder.itemView.setSelected(false);
                dishTags.setSelected(false);
            }


        }
        else if(objectList.get(position) instanceof FoodItem)
        {
            FoodItemsViewHolder foodItemsViewHolder = (FoodItemsViewHolder)holder;
            FoodItem  foodItem = (FoodItem)objectList.get(position);
            foodItemsViewHolder.itemName.setTypeface(fontRegular);
            foodItemsViewHolder.qty.setTypeface(fontRegular);
            foodItemsViewHolder.qty.setVisibility(View.GONE);
            foodItemsViewHolder.itemName.setText(foodItem.getFoodName());
            foodItemsViewHolder.qty.setText(foodItem.getCartAddedQuantity() + " nos");
        }
        else if(objectList.get(position) instanceof ChefRewards)
        {
            ChefRewardsItemViewHolder chefRewardsItemViewHolder = (ChefRewardsItemViewHolder)holder;
            ChefRewards chefRewards = (ChefRewards)objectList.get(position);
            chefRewardsItemViewHolder.rewardText.setText(chefRewards.getName());
            chefRewardsItemViewHolder.rewardText.setTypeface(fontBold);

        }
        else if(objectList.get(position) instanceof WeekDays)
        {
            WeekDaysItemViewHolder weekDaysItemViewHolder = (WeekDaysItemViewHolder)holder;
            WeekDays weekDays = (WeekDays)objectList.get(position);
            weekDaysItemViewHolder.name.setTypeface(fontRegular);
            weekDaysItemViewHolder.name.setText(weekDays.getWeekDayName());


            if(weekDays.getSelected())
            {
                weekDaysItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                weekDaysItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                weekDaysItemViewHolder.itemView.setSelected(true);
                weekDays.setSelected(true);
            }
            else
            {
                weekDaysItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                weekDaysItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                weekDaysItemViewHolder.itemView.setSelected(false);
                weekDays.setSelected(false);
            }




            weekDaysItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(from.equals("ViewChef"))
                    {

                    }
                    else
                    {
                        if(from.equals("EditChef"))
                        {
                            if(!weekDays.getSelected())
                            {
                                weekDaysItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                                weekDaysItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                                weekDaysItemViewHolder.itemView.setSelected(true);
                                ((WeekDays) objectList.get(position)).setSelected(true);
                                weekDays.setWeekDayName(weekDays.getWeekDayName());
                                selectedWeekDaysforAddInEditProfile.add(weekDays);
                            }
                            else
                            {
                                weekDaysItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                                weekDaysItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                                weekDaysItemViewHolder.itemView.setSelected(false);
                                ((WeekDays) objectList.get(position)).setSelected(false);
                                for (int i=0;i < selectedWeekDaysforAddInEditProfile.size();i++)
                                {
                                    if(selectedWeekDaysforAddInEditProfile.get(i).getId().equals(((WeekDays) objectList.get(position)).getId()))
                                    {
                                        selectedWeekDaysforAddInEditProfile.get(i).setSelected(false);
                                    }

                                }
                                selectedWeekDaysforAddInEditProfile.remove(((WeekDays) objectList.get(position)));
                            }
                        }
                        else
                        {
                            if(!weekDays.getSelected())
                            {
                                weekDaysItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                                weekDaysItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                                weekDaysItemViewHolder.itemView.setSelected(true);
                                ((WeekDays) objectList.get(position)).setSelected(true);
                                weekDays.setWeekDayName(weekDays.getWeekDayName());
                                selectedWeekDaysforAdd.add(weekDays);
                            }
                            else
                            {
                                weekDaysItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                                weekDaysItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                                weekDaysItemViewHolder.itemView.setSelected(false);
                                ((WeekDays) objectList.get(position)).setSelected(false);
                                for (int i=0;i < selectedWeekDaysforAdd.size();i++)
                                {
                                    if(selectedWeekDaysforAdd.get(i).getId().equals(((WeekDays) objectList.get(position)).getId()))
                                    {
                                        selectedWeekDaysforAdd.get(i).setSelected(false);
                                    }

                                }
                                selectedWeekDaysforAdd.remove(((WeekDays) objectList.get(position)));
                            }
                        }

                    }

                }
            });



        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(objectList.get(position)instanceof MenuCategory)
        {
            return R.layout.menu_category_item;
        }
        else  if(objectList.get(position)instanceof MenuDishType)
        {
            return R.layout.menu_dish_item;
        }
        else  if(objectList.get(position)instanceof MenuCuisine)
        {
            return R.layout.menu_cuisine_item;
        }
        else  if(objectList.get(position)instanceof WeekDays)
        {
            return R.layout.menu_week_day_item;
        }
        else if(objectList.get(position) instanceof FoodItem)
        {
            return R.layout.chef_order_food_items;
        }
        else if (objectList.get(position) instanceof ChefRewards)
        {
            return R.layout.chef_reward_item;
        }
        else if (objectList.get(position) instanceof DishTags)
        {
            return R.layout.menu_tags_item;
        }
        else
        {

        }
        return super.getItemViewType(position);
    }

    public class CategoryItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        LinearLayout itemLayout;

        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class DishesItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        LinearLayout itemLayout;

        public DishesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class CuisinesItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        LinearLayout itemLayout;

        public CuisinesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class TagsCategoryItemsViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        LinearLayout itemLayout;

        public TagsCategoryItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class WeekDaysItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        LinearLayout itemLayout;

        public WeekDaysItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class FoodItemsViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemName;
        TextView qty;

        public FoodItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            qty = itemView.findViewById(R.id.qty);
        }
    }

    public class ChefRewardsItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView rewardText;
        ImageView rewardImage;

        public ChefRewardsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardText = itemView.findViewById(R.id.rewardText);
            rewardImage = itemView.findViewById(R.id.rewardImage);
        }
    }
}
