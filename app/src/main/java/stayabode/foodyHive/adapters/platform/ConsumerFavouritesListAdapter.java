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
import stayabode.foodyHive.models.ConsumerFavourites;

import java.util.ArrayList;
import java.util.List;

public class ConsumerFavouritesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ConsumerFavourites> consumerFavouritesList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;

    public ConsumerFavouritesListAdapter(Context context,List<ConsumerFavourites> consumerFavouritesList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.consumerFavouritesList = consumerFavouritesList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_favourites_list_item,parent,false);
        return new ConsumerFavouritesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConsumerFavouritesItemViewHolder consumerFavouritesItemViewHolder = (ConsumerFavouritesItemViewHolder)holder;
        consumerFavouritesItemViewHolder.kitchenName.setTypeface(fontRegular);
        consumerFavouritesItemViewHolder.location.setTypeface(fontRegular);
        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            consumerFavouritesItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
        }
        else
        {
            consumerFavouritesItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            // Set the background color for alternate row/item
        }

    }

    @Override
    public int getItemCount() {
        return consumerFavouritesList.size();
    }

    public class ConsumerFavouritesItemViewHolder extends RecyclerView.ViewHolder
    {

        TextView kitchenName;
        TextView location;

        public ConsumerFavouritesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            kitchenName = itemView.findViewById(R.id.kitchenName);
            location = itemView.findViewById(R.id.location);

        }
    }
}
