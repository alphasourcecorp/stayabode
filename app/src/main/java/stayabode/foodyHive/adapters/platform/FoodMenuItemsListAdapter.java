package stayabode.foodyHive.adapters.platform;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.FoodMenu;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuItemsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<FoodMenu> foodMenuList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;

    public FoodMenuItemsListAdapter(Context context,List<FoodMenu> foodMenuList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.foodMenuList = foodMenuList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_menu_items,parent,false);
        return new FoodMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodMenuItemViewHolder foodMenuItemViewHolder = (FoodMenuItemViewHolder)holder;
        foodMenuItemViewHolder.itemName.setTypeface(fontRegular);
        foodMenuItemViewHolder.itemPrice.setTypeface(fontRegular);
        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            foodMenuItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
        }
        else
        {
            foodMenuItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            // Set the background color for alternate row/item
        }
    }

    @Override
    public int getItemCount() {
        return foodMenuList.size();
    }

    public class FoodMenuItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemName;
        TextView itemPrice;

        public FoodMenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
