package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity;
import stayabode.foodyHive.activities.consumers.TrackOrderActivity;
import stayabode.foodyHive.models.Chef;

import java.util.ArrayList;
import java.util.List;

public class InvoiceOrdersCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Chef> chefList = new ArrayList<>();
    AlertDialog alertDialog;

    public InvoiceOrdersCardAdapter(Context context,List<Chef> chefList,AlertDialog alertDialog)
    {
        this.context = context;
        this.chefList = chefList;
        this.alertDialog = alertDialog;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_orders,parent,false);
        return new InvoiceOrdersItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InvoiceOrdersItemsViewHolder invoiceOrdersItemsViewHolder = (InvoiceOrdersItemsViewHolder)holder;

        invoiceOrdersItemsViewHolder.chefName.setText(chefList.get(position).getName());
        invoiceOrdersItemsViewHolder.orderId.setText(getOrderNoWithDashes(chefList.get(position).getSeperateOrderNo(),"-",4));

        invoiceOrdersItemsViewHolder.trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(context, TrackOrderActivity.class);
                intent.putExtra("OrderID",chefList.get(position).getSeperateOrderNo());
                intent.putExtra("Status",chefList.get(position).getActiveStatus());
                intent.putExtra("dateOrdered",chefList.get(position).getStartDate());
                intent.putExtra("From","Checkout");
                context.startActivity(intent);
                ((ConsumerCheckOutActivity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chefList.size();
    }


    public class InvoiceOrdersItemsViewHolder extends RecyclerView.ViewHolder
    {
        TextView chefName;
        TextView orderId;
        TextView trackOrder;

        public InvoiceOrdersItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            chefName = itemView.findViewById(R.id.chefName);
            orderId = itemView.findViewById(R.id.orderId);
            trackOrder = itemView.findViewById(R.id.trackOrder);
        }
    }


    /**
     get order number with dashes for every 4 digits..
     **/

    public String getOrderNoWithDashes(String orderNo, String insert, int period) {
        StringBuilder builder = new StringBuilder(
                orderNo.length() + insert.length() * (orderNo.length() / period) + 1);

        int index = 0;
        String prefix = "";
        while (index < orderNo.length()) {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            prefix = insert;
            builder.append(orderNo.substring(index,
                    Math.min(index + period, orderNo.length())));
            index += period;
        }
        return builder.toString();
    }
}
