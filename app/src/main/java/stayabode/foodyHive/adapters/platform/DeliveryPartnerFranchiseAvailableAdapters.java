package stayabode.foodyHive.adapters.platform;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.models.DeliveryPartner;

import java.util.ArrayList;
import java.util.List;

public class DeliveryPartnerFranchiseAvailableAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<DeliveryPartner> deliveryPartnerList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;


    public DeliveryPartnerFranchiseAvailableAdapters(Context context,List<DeliveryPartner> deliveryPartnerList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.deliveryPartnerList = deliveryPartnerList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_partner_list_item,parent,false);
        return new DeliveryPartnersItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeliveryPartnersItemViewHolder deliveryPartnersItemViewHolder = (DeliveryPartnersItemViewHolder)holder;
        deliveryPartnersItemViewHolder.partnerName.setTypeface(fontRegular);
        deliveryPartnersItemViewHolder.seconds.setTypeface(fontRegular);
        deliveryPartnersItemViewHolder.totalPersons.setTypeface(fontRegular);
        deliveryPartnersItemViewHolder.unitKG.setTypeface(fontRegular);

        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            deliveryPartnersItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
        }
        else
        {
            deliveryPartnersItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            // Set the background color for alternate row/item
        }

        deliveryPartnersItemViewHolder.partnerName.setText(deliveryPartnerList.get(position).getName());
        deliveryPartnersItemViewHolder.seconds.setText(deliveryPartnerList.get(position).getDistanceTime());
        deliveryPartnersItemViewHolder.totalPersons.setText(deliveryPartnerList.get(position).getTotalPersons());
        deliveryPartnersItemViewHolder.unitKG.setText(deliveryPartnerList.get(position).getUnitKG());
        Glide.with(context).load(deliveryPartnerList.get(position).getUploadLogo()).placeholder(R.drawable.foodi_logo_left_image).into(deliveryPartnersItemViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return deliveryPartnerList.size();
    }

    public class DeliveryPartnersItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView partnerName;
        TextView seconds;
        TextView unitKG;
        TextView totalPersons;
        ImageView imageView;

        public DeliveryPartnersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            partnerName = itemView.findViewById(R.id.partnerName);
            seconds = itemView.findViewById(R.id.seconds);
            unitKG = itemView.findViewById(R.id.unitKG);
            totalPersons = itemView.findViewById(R.id.totalPersons);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
