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
import stayabode.foodyHive.models.TopOffers;

import java.util.ArrayList;
import java.util.List;

public class MenuSubBottomItemsVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<TopOffers> topOffersList = new ArrayList<>();
    Context context;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface RobotoRegular;
    Typeface RobotoBold;
    Typeface poppinsLight;
    int selectedposition ;
    RecyclerView recyclerView;


    public MenuSubBottomItemsVerticalAdapter(Context context,List<TopOffers> topOffersList,Typeface poppinsBold,RecyclerView recyclerView,Typeface poppinsLight,Typeface poppinsSemiBold,Typeface RobotoRegular,Typeface RobotoBold)
    {
        this.context = context;
        this.topOffersList = topOffersList;
        this.poppinsBold = poppinsBold;
        this.recyclerView = recyclerView;
        this.poppinsLight = poppinsLight;
        this.poppinsSemiBold = poppinsSemiBold;
        this.RobotoBold = RobotoBold;
        this.RobotoRegular = RobotoRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_food_menu_items,parent,false);
        return new SubItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SubItemsHolder subItemsHolder = (SubItemsHolder)holder;
        subItemsHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        subItemsHolder.name.setTypeface(poppinsBold);
        subItemsHolder.name.setText(topOffersList.get(position).getName());

        subItemsHolder.recyclerView.setAdapter(new FoodsListVerticalAdapter(context,topOffersList.get(position).getFoodItemList(),poppinsLight,poppinsSemiBold,RobotoRegular,RobotoBold));


    }

    @Override
    public int getItemCount() {
        return topOffersList.size();
    }


    public class  SubItemsHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        RecyclerView recyclerView;

        public SubItemsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
