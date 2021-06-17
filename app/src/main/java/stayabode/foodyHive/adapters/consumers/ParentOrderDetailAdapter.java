package stayabode.foodyHive.adapters.consumers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.ParentItemOrderDetail;

import java.util.List;

public class ParentOrderDetailAdapter extends RecyclerView.Adapter<ParentOrderDetailAdapter.ParentViewHolder> {

    // An object of RecyclerView.RecycledViewPool
    // is created to share the Views
    // between the child and
    // the parent RecyclerViews
    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    private List<ParentItemOrderDetail> itemList;

    public ParentOrderDetailAdapter(List<ParentItemOrderDetail> itemList)
    {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the parent item
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.order_detail_recycler_view_item,
                        viewGroup, false);

        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ParentViewHolder parentViewHolder,
            int position)
    {

        // Create an instance of the ParentItem
        // class for the given position
        ParentItemOrderDetail parentItem = itemList.get(position);

        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        parentViewHolder.chefname.setText(parentItem.getChefname());
        parentViewHolder.orderId.setText(parentItem.getOrderId());
        parentViewHolder.orderStatus.setText(parentItem.getOrderStatus());
        parentViewHolder.orderamount.setText(parentItem.getOrderamount());

       // Glide.with(parentViewHolder.itemView.getContext()).load(parentItem.getChefImage()).placeholder(R.drawable.user_icon_consumer).into(parentViewHolder.chefImage);


        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder
                        .ChildRecyclerView
                        .getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager
                .setInitialPrefetchItemCount(
                        parentItem
                                .getChildItemList()
                                .size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        ChildItemOrderDetailAdapter childItemAdapter = new ChildItemOrderDetailAdapter(parentItem.getChildItemList());
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    // This method returns the number
    // of items we have added in the
    // ParentItemList i.e. the number
    // of instances we have created
    // of the ParentItemList
    @Override
    public int getItemCount()
    {

        return itemList.size();
    }

    // This class is to initialize
    // the Views present in
    // the parent RecyclerView
    class ParentViewHolder
            extends RecyclerView.ViewHolder {

        private ImageView chefImage;
        private TextView chefname;
        private TextView orderId;
        private TextView orderStatus;
        private TextView orderamount;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView)
        {
            super(itemView);

            chefImage = itemView.findViewById(R.id.chefImage);
            chefname = itemView.findViewById(R.id.chefname);
            orderId = itemView.findViewById(R.id.orderId);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderamount = itemView.findViewById(R.id.orderamount);

            ChildRecyclerView = itemView.findViewById(R.id.dish_recycleview);
        }
    }
}


