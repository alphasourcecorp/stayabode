package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;

import stayabode.foodyHive.models.AboutInfoChef;
import stayabode.foodyHive.models.AccountInfoHeaders;
import stayabode.foodyHive.models.CancelledOrdersHeader;
import stayabode.foodyHive.models.ChangePasswordHeaders;
import stayabode.foodyHive.models.ClosedOrderHeader;
import stayabode.foodyHive.models.NotificationsLists;
import stayabode.foodyHive.models.OrdersHeader;
import stayabode.foodyHive.models.Reviews;
import stayabode.foodyHive.models.ReviewsHeader;
import stayabode.foodyHive.models.TodaysChefMenu;
import stayabode.foodyHive.models.TodaysChefsPopularMenu;

import java.util.ArrayList;
import java.util.List;

public class InfoListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> objectList = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    Typeface poppinsSemibold;
    Typeface poppinsLight;
    Typeface poppinsBold;
    Typeface poppinsMedium;
    Typeface robotoRegular;
    Typeface robotoBold;


    public InfoListsAdapter(Context context, List<Object> objectList, RecyclerView recyclerView, Typeface poppinsSemibold, Typeface poppinsLight, Typeface poppinsMedium, Typeface poppinsBold, Typeface robotoBold, Typeface robotoRegular) {
        this.context = context;
        this.objectList = objectList;
        this.recyclerView = recyclerView;
        this.poppinsLight = poppinsLight;
        this.poppinsMedium = poppinsMedium;
        this.poppinsSemibold = poppinsSemibold;
        this.poppinsBold = poppinsBold;
        this.robotoBold = robotoBold;
        this.robotoRegular = robotoRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(viewType, parent, false);

        if (viewType == R.layout.chef_reviews_items) {
            return new RatingsViewHolder(view);
        } else if (viewType == R.layout.chefs_menu_items) {
            return new MenuItemsViewHolder(view);
        }
        else if (viewType == R.layout.chef_popular_menu_items) {
            return new PopularMenuItemsViewHolder(view);
        }
        else if(viewType==R.layout.consumer_your_order_all_list_opened){
            return new OpenOrderItemViewHolder(view);
        }
        else if(viewType==R.layout.consumer_your_order_all_list_closed){
            return new ClosedOrderItemsViewHolder(view);
        }
        else if(viewType==R.layout.consumer_your_order_all_list_cancelled){
            return new CancelledOrdersItemsViewHolder(view);
        }

        else {
            return new MenuItemsViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (objectList.get(position) instanceof ReviewsHeader) {
            RatingsViewHolder ratingsViewHolder = (RatingsViewHolder) holder;
            ReviewsHeader reviewsHeader = (ReviewsHeader)objectList.get(position);
            ratingsViewHolder.header.setTypeface(poppinsSemibold);
            ratingsViewHolder.mealHeader.setTypeface(poppinsMedium);
            ratingsViewHolder.mealPrice.setTypeface(poppinsMedium);
            ratingsViewHolder.taxHeader.setTypeface(poppinsMedium);
            ratingsViewHolder.taxPrice.setTypeface(poppinsMedium);
            ratingsViewHolder.deliveryChargeHeader.setTypeface(poppinsMedium);
            ratingsViewHolder.deliveryPrice.setTypeface(poppinsMedium);
            ratingsViewHolder.subTotalHeader.setTypeface(poppinsMedium);
            ratingsViewHolder.subTotalPrice.setTypeface(poppinsMedium);

            List<Object> objectList = new ArrayList<>();

            for (int i=0;i < reviewsHeader.getReviewsList().size();i++)
            {
                Reviews reviews = reviewsHeader.getReviewsList().get(i);
                reviews.setDate(reviews.getDate());
                reviews.setUserName(reviews.getUserName());
                reviews.setImage(reviews.getImage());
                reviews.setReviewsDescription(reviews.getReviewsDescription());
                reviews.setRatingCount(reviews.getRatingCount());
                objectList.add(reviews);
            }



            ratingsViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            ratingsViewHolder.recyclerView.setAdapter(new ConsumerHomeAdapters(context, objectList, null, null, null, poppinsBold, poppinsLight, poppinsMedium,1));

        } else if (objectList.get(position) instanceof TodaysChefMenu) {
            MenuItemsViewHolder menuItemsViewHolder = (MenuItemsViewHolder) holder;
            TodaysChefMenu todaysChefMenu = (TodaysChefMenu)objectList.get(position);

            menuItemsViewHolder.header.setTypeface(poppinsBold);
            menuItemsViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            menuItemsViewHolder.recyclerView.setAdapter(new ChefMenuAdapter(context, todaysChefMenu.getFoodItemList(), poppinsSemibold, poppinsLight, robotoBold, robotoRegular));
        }
        else if (objectList.get(position) instanceof TodaysChefsPopularMenu) {
            PopularMenuItemsViewHolder menuItemsViewHolder = (PopularMenuItemsViewHolder) holder;
            TodaysChefsPopularMenu todaysChefMenu = (TodaysChefsPopularMenu)objectList.get(position);

            menuItemsViewHolder.subHeader.setTypeface(poppinsBold);
            menuItemsViewHolder.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(context));
            menuItemsViewHolder.recyclerViewPopular.setAdapter(new ChefMenuAdapter(context, todaysChefMenu.getFoodItemLists(), poppinsSemibold, poppinsLight, robotoBold, robotoRegular));
        }

        else if(objectList.get(position) instanceof OrdersHeader){
            OpenOrderItemViewHolder orderItemsViewHolder = (OpenOrderItemViewHolder) holder;
            OrdersHeader ordersHeader = (OrdersHeader)objectList.get(position);

            orderItemsViewHolder.openOrderText.setTypeface(poppinsSemibold);
            orderItemsViewHolder.openOrderRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            orderItemsViewHolder.openOrderRecyclerView.setAdapter(new ConsumerYouOrderAdapter(context,ordersHeader.getOrdersList(),poppinsSemibold,poppinsLight,poppinsMedium,poppinsBold,robotoBold,robotoRegular,"Open"));
        }
        else if(objectList.get(position) instanceof ClosedOrderHeader){
            ClosedOrderItemsViewHolder orderItemsViewHolder = (ClosedOrderItemsViewHolder) holder;
            ClosedOrderHeader ordersHeader = (ClosedOrderHeader) objectList.get(position);


            orderItemsViewHolder.openOrderText.setTypeface(poppinsSemibold);

            orderItemsViewHolder.openOrderRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            orderItemsViewHolder.openOrderRecyclerView.setAdapter(new ConsumerYouOrderAdapter(context,ordersHeader.getOrdersList(),poppinsSemibold,poppinsLight,poppinsMedium,poppinsBold,robotoBold,robotoRegular,"Closed"));
        }
        else if(objectList.get(position) instanceof CancelledOrdersHeader){
            CancelledOrdersItemsViewHolder orderItemsViewHolder = (CancelledOrdersItemsViewHolder) holder;
            CancelledOrdersHeader ordersHeader = (CancelledOrdersHeader) objectList.get(position);

            orderItemsViewHolder.openOrderText.setTypeface(poppinsSemibold);
            orderItemsViewHolder.openOrderRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            orderItemsViewHolder.openOrderRecyclerView.setAdapter(new ConsumerYouOrderAdapter(context,ordersHeader.getOrdersList(),poppinsSemibold,poppinsLight,poppinsMedium,poppinsBold,robotoBold,robotoRegular,"Cancelled"));
        }


    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof AboutInfoChef) {
            return R.layout.about_recycler_view_item;
        } else if (objectList.get(position) instanceof ReviewsHeader) {
            return R.layout.chef_reviews_items;
        } else if (objectList.get(position) instanceof TodaysChefMenu) {
            return R.layout.chefs_menu_items;
        } else if (objectList.get(position) instanceof TodaysChefsPopularMenu) {
            return R.layout.chef_popular_menu_items;
        } else if (objectList.get(position) instanceof OrdersHeader) {
            return R.layout.consumer_your_order_all_list_opened;
        } else if (objectList.get(position) instanceof ClosedOrderHeader) {
            return R.layout.consumer_your_order_all_list_closed;
        } else if (objectList.get(position) instanceof CancelledOrdersHeader) {
            return R.layout.consumer_your_order_all_list_cancelled;
        } else if (objectList.get(position) instanceof AccountInfoHeaders) {
            return R.layout.account_info_layout;
        } else if (objectList.get(position) instanceof ChangePasswordHeaders) {
            return R.layout.change_password_layout;
        } else if (objectList.get(position) instanceof NotificationsLists) {
            return R.layout.notification_on_off_item;
        }

        else {

        }
        return super.getItemViewType(position);
    }

    public class AboutInfoViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;

        public AboutInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }

    public class RatingsViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        TextView mealHeader;
        TextView taxHeader;
        TextView taxPrice;
        TextView mealPrice;
        TextView deliveryChargeHeader;
        TextView subTotalHeader;
        TextView subTotalPrice;
        TextView deliveryPrice;
        RecyclerView recyclerView;

        public RatingsViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            mealHeader = itemView.findViewById(R.id.mealHeader);
            taxHeader = itemView.findViewById(R.id.taxHeader);
            mealPrice = itemView.findViewById(R.id.mealPrice);
            taxPrice = itemView.findViewById(R.id.taxPrice);
            deliveryChargeHeader = itemView.findViewById(R.id.deliveryChargeHeader);
            subTotalHeader = itemView.findViewById(R.id.subTotalHeader);
            deliveryPrice = itemView.findViewById(R.id.deliveryPrice);
            subTotalPrice = itemView.findViewById(R.id.subTotalPrice);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }

    public class MenuItemsViewHolder extends RecyclerView.ViewHolder {

        TextView header;
        RecyclerView recyclerView;

        public MenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }

    public class PopularMenuItemsViewHolder extends RecyclerView.ViewHolder {

        TextView subHeader;
        RecyclerView recyclerViewPopular;

        public PopularMenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            subHeader = itemView.findViewById(R.id.subHeader);
            recyclerViewPopular = itemView.findViewById(R.id.recyclerViewPopular);
        }
    }

    public class OpenOrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView openOrderText;
        RecyclerView openOrderRecyclerView;

        public OpenOrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            openOrderRecyclerView = itemView.findViewById(R.id.openOrderRecyclerView);

            openOrderText = itemView.findViewById(R.id.openOrderText);
        }
    }

    public class ClosedOrderItemsViewHolder extends RecyclerView.ViewHolder
    {
        TextView openOrderText;
        RecyclerView openOrderRecyclerView;

        public ClosedOrderItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            openOrderRecyclerView = itemView.findViewById(R.id.openOrderRecyclerView);

            openOrderText = itemView.findViewById(R.id.openOrderText);
        }
    }


    public class CancelledOrdersItemsViewHolder extends RecyclerView.ViewHolder
    {
        TextView openOrderText;
        RecyclerView openOrderRecyclerView;
        public CancelledOrdersItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            openOrderRecyclerView = itemView.findViewById(R.id.openOrderRecyclerView);

            openOrderText = itemView.findViewById(R.id.openOrderText);
        }
    }



}
