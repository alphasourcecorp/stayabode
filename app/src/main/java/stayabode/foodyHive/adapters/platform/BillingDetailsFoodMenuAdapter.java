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

public class BillingDetailsFoodMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<FoodMenu> foodMenuList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;

    public BillingDetailsFoodMenuAdapter(Context context,List<FoodMenu> foodMenuList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.foodMenuList = foodMenuList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_food_menu_items,parent,false);
        return new BillingDeailsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BillingDeailsItemViewHolder billingDeailsItemViewHolder = (BillingDeailsItemViewHolder)holder;
        billingDeailsItemViewHolder.foodItem.setTypeface(fontRegular);
        billingDeailsItemViewHolder.price.setTypeface(fontRegular);
        billingDeailsItemViewHolder.qty.setTypeface(fontRegular);
        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            billingDeailsItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
        }
        else
        {
            billingDeailsItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            // Set the background color for alternate row/item
        }
    }

    @Override
    public int getItemCount() {
        return foodMenuList.size();
    }

    public class BillingDeailsItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView foodItem;
        TextView qty;
        TextView price;

        public BillingDeailsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItem = itemView.findViewById(R.id.foodItem);
            qty = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.price);
        }
    }
}
