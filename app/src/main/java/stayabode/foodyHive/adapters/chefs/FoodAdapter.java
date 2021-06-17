package stayabode.foodyHive.adapters.chefs;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.fragments.chefs.ChefItemDetailFragment;
import stayabode.foodyHive.models.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<FoodItem> foodItemList = new ArrayList<>();
    Context context;
    Typeface fontBold;
    Typeface fontRegular;

    public FoodAdapter(Context context,List<FoodItem> foodItemList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.foodItemList = foodItemList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_ordered_dish_list_item,parent,false);
        return new FoodItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodItemsViewHolder foodItemsViewHolder = (FoodItemsViewHolder)holder;
        foodItemsViewHolder.name.setTypeface(fontBold);
        foodItemsViewHolder.availableSwitch.setTypeface(fontRegular);
        foodItemsViewHolder.price.setTypeface(fontRegular);
        foodItemsViewHolder.count.setTypeface(fontRegular);
        foodItemsViewHolder.mins.setTypeface(fontRegular);
        foodItemsViewHolder.availableSwitch.setVisibility(View.GONE);



        foodItemsViewHolder.name.setText(foodItemList.get(position).getFoodName());
        foodItemsViewHolder.mins.setText(foodItemList.get(position).getTime());
        foodItemsViewHolder.count.setText("("+foodItemList.get(position).getRatingCount()+")");
        foodItemsViewHolder.itemDescription.setText(foodItemList.get(position).getShortDescription());
        foodItemsViewHolder.price.setText("₹"+String.format("%.2f",Double.valueOf(foodItemList.get(position).getPrice())));
        Glide.with(context).load(foodItemList.get(position).getFoodImage()).placeholder(R.drawable.foodi_logo_left_image).into(foodItemsViewHolder.imageView);
        foodItemsViewHolder.availableSwitch.setChecked(foodItemList.get(position).getAutoAcceptOrder());
        foodItemsViewHolder.ratingBar.setRating(foodItemList.get(position).getRatingAverage());


        foodItemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChefItemDetailFragment fragment = new ChefItemDetailFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ID", foodItemList.get(position).getFoodId());
                bundle.putString("ChefId", foodItemList.get(position).getChefId());
                fragment.setArguments(bundle);
                FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public class FoodItemsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name;
        TextView price;
        TextView itemDescription;
        Switch availableSwitch;
        RatingBar ratingBar;
        TextView count;
        TextView mins;

        public FoodItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            count = itemView.findViewById(R.id.count);
            mins = itemView.findViewById(R.id.mins);
            availableSwitch =itemView.findViewById(R.id.availableSwitch);
            itemDescription =itemView.findViewById(R.id.itemDescription);
        }
    }
}
