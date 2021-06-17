package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.models.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class OrderFoodsItemsListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<FoodItem> foodItemList = new ArrayList<>();
    Context context;
    String from;
    String date;
    String chefName;
    String chefImage;


    public OrderFoodsItemsListsAdapter(Context context, List<FoodItem> foodItemList,String from,String date,String chefName,String chefImage) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.from = from;
        this.date=date;
        this.chefName=chefName;
        this.chefImage=chefImage;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_orderes_items, parent, false);
        return new OrderedItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderedItemsViewHolder orderedItemsViewHolder = (OrderedItemsViewHolder) holder;
        orderedItemsViewHolder.name.setText(foodItemList.get(position).getFoodName());
        if(from.equals("detail"))
        {
            orderedItemsViewHolder.qty.setText("Quantity : "+foodItemList.get(position).getCartAddedQuantity());
            orderedItemsViewHolder.qty.setVisibility(View.VISIBLE);
            orderedItemsViewHolder.deliveryDate.setVisibility(View.GONE);
            orderedItemsViewHolder.chefName.setVisibility(View.GONE);
            orderedItemsViewHolder.chefPic.setVisibility(View.GONE);
            orderedItemsViewHolder.viewLine.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(20, 0, 0, 0);
            orderedItemsViewHolder.nameLayout.setLayoutParams(params);
        }
        else if(from.equals("track")){
            orderedItemsViewHolder.imageView.setVisibility(View.GONE);
            orderedItemsViewHolder.qty.setText("Quantity : "+foodItemList.get(position).getCartAddedQuantity());
            orderedItemsViewHolder.qty.setVisibility(View.GONE);
            orderedItemsViewHolder.deliveryDate.setVisibility(View.GONE);
            orderedItemsViewHolder.chefName.setVisibility(View.GONE);
            orderedItemsViewHolder.chefPic.setVisibility(View.GONE);
            orderedItemsViewHolder.viewLine.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            orderedItemsViewHolder.nameLayout.setLayoutParams(params);
        }
        else
        {
            orderedItemsViewHolder.qty.setText("");
            orderedItemsViewHolder.qty.setVisibility(View.GONE);
            orderedItemsViewHolder.deliveryDate.setVisibility(View.VISIBLE);
            orderedItemsViewHolder.chefName.setVisibility(View.VISIBLE);
            orderedItemsViewHolder.status.setVisibility(View.VISIBLE);
            orderedItemsViewHolder.chefPic.setVisibility(View.VISIBLE);
            orderedItemsViewHolder.chefPic.setVisibility(View.VISIBLE);
            orderedItemsViewHolder.viewLine.setVisibility(View.VISIBLE);

            orderedItemsViewHolder.deliveryDate.setText(date);
            orderedItemsViewHolder.chefName.setText(chefName);
            orderedItemsViewHolder.status.setText(foodItemList.get(position).getStatus());
            Glide.with(context).load(chefImage).placeholder(R.drawable.foodi_logo_left_image).into(orderedItemsViewHolder.chefPic);
        }

        Glide.with(context).load(foodItemList.get(position).getFoodImage()).placeholder(R.drawable.foodi_logo_left_image).into(orderedItemsViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public class OrderedItemsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView chefPic;
        TextView status;
        TextView name;
        TextView qty;
        TextView deliveryDate;
        TextView chefName;
        View viewLine;
        LinearLayout rootLay;
        LinearLayout nameLayout;

        public OrderedItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            qty = itemView.findViewById(R.id.qty);
            deliveryDate=itemView.findViewById(R.id.deliveryDate);
            chefName=itemView.findViewById(R.id.chefName);
            chefPic=itemView.findViewById(R.id.chefImage);
            status=itemView.findViewById(R.id.status);
            viewLine=itemView.findViewById(R.id.viewLine);
            rootLay=itemView.findViewById(R.id.rootLay);
            nameLayout=itemView.findViewById(R.id.nameLayout);
        }
    }
}
