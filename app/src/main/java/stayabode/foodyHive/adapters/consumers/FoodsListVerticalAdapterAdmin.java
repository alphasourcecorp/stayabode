package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;

import stayabode.foodyHive.corporatemenurange.CorporateOrderDetail;
import stayabode.foodyHive.models.RequestBtoBItem;

import java.util.ArrayList;
import java.util.List;


public class FoodsListVerticalAdapterAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<RequestBtoBItem> foodItemList = new ArrayList<>();
    Context context;
    Typeface poppinsSemiBold;
    Typeface RobotoRegular;
    Typeface RobotoBold;
    Typeface poppinsLight;
    String spinnerselection;

    public FoodsListVerticalAdapterAdmin(Context context, List<RequestBtoBItem> foodItemList, Typeface poppinsLight, Typeface poppinsSemiBold, Typeface RobotoRegular, Typeface RobotoBold,String spinnerselection) {
        this.foodItemList = foodItemList;
        this.context = context;
        this.poppinsLight = poppinsLight;
        this.poppinsSemiBold = poppinsSemiBold;
        this.RobotoBold = RobotoBold;
        this.RobotoRegular = RobotoRegular;
        this.spinnerselection = spinnerselection;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.corporate_admin_request_list_item, parent, false);
        return new FoodsItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodsItemsViewHolder foodsItemsViewHolder = (FoodsItemsViewHolder) holder;


        if(spinnerselection.equalsIgnoreCase("Request"))
        {
            foodsItemsViewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_yellow);
        }else if(spinnerselection.equalsIgnoreCase("Accept"))
        {
            foodsItemsViewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_green);
        }
        else if(spinnerselection.equalsIgnoreCase("Reject"))
        {
            foodsItemsViewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_red);
        }else
        {
            foodsItemsViewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_yellow);
        }

        foodsItemsViewHolder.view_detail_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CorporateOrderDetail.class);
                context.startActivity(intent);

            }
        });





    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public class FoodsItemsViewHolder extends RecyclerView.ViewHolder {

        Button view_detail_id;

        public FoodsItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            view_detail_id = itemView.findViewById(R.id.view_detail_id);
        }

    }



}
