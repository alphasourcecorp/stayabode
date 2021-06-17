package stayabode.foodyHive.adapters.platform;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.Subscriptions;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsHomeSubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Subscriptions> subscriptionsList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;

    public SubscriptionsHomeSubAdapter(Context context, List<Subscriptions> subscriptionsList,Typeface fontBold,Typeface fontRegular)
    {
        this.subscriptionsList = subscriptionsList;
        this.context = context;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_subscriptions_list_item,parent,false);
        return new SubscriptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SubscriptionsViewHolder subscriptionsViewHolder = (SubscriptionsViewHolder)holder;

        subscriptionsViewHolder.clientName.setTypeface(fontRegular);
        subscriptionsViewHolder.date.setTypeface(fontRegular);
        subscriptionsViewHolder.complete.setTypeface(fontRegular);
        subscriptionsViewHolder.location.setTypeface(fontRegular);
        subscriptionsViewHolder.complete.setText(subscriptionsList.get(position).getStatus());
        subscriptionsViewHolder.clientName.setText(subscriptionsList.get(position).getClientName());
        subscriptionsViewHolder.location.setText(subscriptionsList.get(position).getLocation());
        subscriptionsViewHolder.date.setText(subscriptionsList.get(position).getDate());
        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            subscriptionsViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
        }
        else
        {
            subscriptionsViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            // Set the background color for alternate row/item
        }
        if(subscriptionsViewHolder.complete.getText().equals("In progress"))
        {
            subscriptionsViewHolder.completeLayout.setBackgroundColor(Color.parseColor("#D3D3D3"));
            subscriptionsViewHolder.complete.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
        else
        {
            subscriptionsViewHolder.completeLayout.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            subscriptionsViewHolder.complete.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }


    }

    @Override
    public int getItemCount() {
        return subscriptionsList.size();
    }

    public class SubscriptionsViewHolder extends RecyclerView.ViewHolder
    {
        TextView date;
        TextView clientName;
        TextView location;
        TextView complete;
        LinearLayout completeLayout;

        public SubscriptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            clientName = itemView.findViewById(R.id.clientName);
            location = itemView.findViewById(R.id.location);
            complete = itemView.findViewById(R.id.complete);
            completeLayout = itemView.findViewById(R.id.completeLayout);
        }
    }
}
