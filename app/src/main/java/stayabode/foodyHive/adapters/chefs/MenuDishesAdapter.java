package stayabode.foodyHive.adapters.chefs;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;

import stayabode.foodyHive.models.MenuDishType;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.fragments.chefs.AddChefMenuFragment.selectedMenuDishTypeforAdd;



public class MenuDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Object> menuDishTypeList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    String from;
    int selectedPositionDish = -1;


    public MenuDishesAdapter(Context context, List<Object> menuDishTypeList,Typeface fontBold,Typeface fontRegular,String from)
    {
        this.context = context;
        this.menuDishTypeList = menuDishTypeList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
        this.from = from;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_dish_item,parent,false);
        return new DishesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DishesItemViewHolder dishesItemViewHolder = (DishesItemViewHolder)holder;
        MenuDishType menuDishType = (MenuDishType)menuDishTypeList.get(position);
        dishesItemViewHolder.name.setTypeface(fontRegular);
        dishesItemViewHolder.name.setText(menuDishType.getName());




        if(position == selectedPositionDish)
        {
            if(dishesItemViewHolder.itemView.isSelected())
            {
                dishesItemViewHolder.itemView.setSelected(false);
                ((MenuDishType) menuDishTypeList.get(position)).setSelected(false);
                dishesItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                dishesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                for (int i=0;i < selectedMenuDishTypeforAdd.size();i++)
                {


                        if(selectedMenuDishTypeforAdd.get(i).getId().equals(((MenuDishType) menuDishTypeList.get(position)).getId()))
                        {
                            selectedMenuDishTypeforAdd.get(i).setSelected(false);
                            selectedMenuDishTypeforAdd.remove(i);
                        }



                }
                selectedMenuDishTypeforAdd.remove((MenuDishType) menuDishTypeList.get(position));

            }
            else
            {

                dishesItemViewHolder.itemView.setSelected(true);
                ((MenuDishType)  menuDishTypeList.get(position)).setSelected(true);
                dishesItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                dishesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                if(selectedMenuDishTypeforAdd.contains((MenuDishType) menuDishTypeList.get(position)))
                {

                }
                else
                {
                    selectedMenuDishTypeforAdd.add((MenuDishType) menuDishTypeList.get(position));
                }
            }


        }
        else
        {

            if(((MenuDishType) menuDishTypeList.get(position)).getSelected() && !dishesItemViewHolder.itemView.isSelected())
            {

                dishesItemViewHolder.itemView.setSelected(true);
//                ((MenuDishType) menuDishTypeList.get(position)).setSelected(false);
                dishesItemViewHolder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.franchise_image_bg));
                dishesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                if(selectedMenuDishTypeforAdd.contains((MenuDishType) menuDishTypeList.get(position)))
                {

                }
                else
                {
                    selectedMenuDishTypeforAdd.add((MenuDishType) menuDishTypeList.get(position));
                }

            }
            else
            {


                dishesItemViewHolder.itemView.setSelected(false);
//                ((MenuDishType) menuDishTypeList.get(position)).setSelected(false);
                dishesItemViewHolder.itemLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                dishesItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                for (int i=0;i < selectedMenuDishTypeforAdd.size();i++)
                {


                    if(selectedMenuDishTypeforAdd.get(i).getId().equals(((MenuDishType) menuDishTypeList.get(position)).getId()))
                    {
                        selectedMenuDishTypeforAdd.get(i).setSelected(false);
                        selectedMenuDishTypeforAdd.remove(i);
                    }



                }
                selectedMenuDishTypeforAdd.remove((MenuDishType) menuDishTypeList.get(position));
            }


        }


        dishesItemViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPositionDish = position;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return menuDishTypeList.size();
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
}
