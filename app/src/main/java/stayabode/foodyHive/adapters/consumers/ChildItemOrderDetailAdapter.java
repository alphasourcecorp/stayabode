package stayabode.foodyHive.adapters.consumers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.models.ChildItemOrderDetail;

import java.util.List;

public class ChildItemOrderDetailAdapter extends RecyclerView.Adapter<ChildItemOrderDetailAdapter.ChildViewHolder> {

    private List<ChildItemOrderDetail> ChildItemList;

    // Constuctor
    ChildItemOrderDetailAdapter(List<ChildItemOrderDetail> childItemList)
    {
        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.order_detail_dish_view_item, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChildViewHolder childViewHolder,
            int position)
    {

        // Create an instance of the ChildItem
        // class for the given position
        ChildItemOrderDetail childItem = ChildItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        childViewHolder.dishname.setText(childItem.getDishname());
        childViewHolder.qtyId.setText(childItem.getDishquantity());

        Glide.with(childViewHolder.itemView.getContext()).load(childItem.getDishImage()).placeholder(R.drawable.foodi_logo_left_image).into(childViewHolder.dish_image);

    }

    @Override
    public int getItemCount()
    {

        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return ChildItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ChildViewHolder
            extends RecyclerView.ViewHolder {

        ImageView dish_image;
        TextView dishname;
        TextView qtyId;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            dish_image = itemView.findViewById(R.id.dish_image);
            dishname = itemView.findViewById(R.id.dishname);
            qtyId = itemView.findViewById(R.id.qtyId);
        }
    }
}
