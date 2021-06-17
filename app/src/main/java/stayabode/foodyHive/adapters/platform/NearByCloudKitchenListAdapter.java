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
import stayabode.foodyHive.models.CloudKitchen;

import java.util.ArrayList;
import java.util.List;

public class NearByCloudKitchenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<CloudKitchen> nearByCloudKitchenList=new ArrayList<>();
    Context context;
    Typeface fontBold;
    Typeface fontRegular;

    public NearByCloudKitchenListAdapter(Context context, List<CloudKitchen> nearByCloudKitchenList, Typeface fontBold, Typeface fontRegular){
        this.nearByCloudKitchenList = nearByCloudKitchenList;
        this.context=context;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_nearby_cloud_kitchens_list_item,parent,false);
        return new NearByCloudKitchenItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NearByCloudKitchenItemViewHolder nearByCloudKitchenItemViewHolder=(NearByCloudKitchenItemViewHolder)holder;
        nearByCloudKitchenItemViewHolder.locationHeader.setTypeface(fontRegular);
        nearByCloudKitchenItemViewHolder.cloudKitchenHeader.setTypeface(fontBold);
        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            nearByCloudKitchenItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
        }
        else
        {
            nearByCloudKitchenItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            // Set the background color for alternate row/item
        }

        CloudKitchen cloudKitchen = nearByCloudKitchenList.get(position);
        nearByCloudKitchenItemViewHolder.locationHeader.setText(cloudKitchen.getLocation());
        nearByCloudKitchenItemViewHolder.cloudKitchenHeader.setText(cloudKitchen.getName());
    }

    @Override
    public int getItemCount() {
        return nearByCloudKitchenList.size();
    }
    public class NearByCloudKitchenItemViewHolder extends RecyclerView.ViewHolder
    {

        TextView cloudKitchenHeader;
        TextView locationHeader;

        public NearByCloudKitchenItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cloudKitchenHeader=itemView.findViewById(R.id.cloudKitchenHeader);
            locationHeader=itemView.findViewById(R.id.locationHeader);
        }
    }
}
