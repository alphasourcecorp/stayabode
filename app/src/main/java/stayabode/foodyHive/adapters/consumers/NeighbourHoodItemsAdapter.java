package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.content.Intent;
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
import stayabode.foodyHive.activities.consumers.CookedChefItemDetailActivity;
import stayabode.foodyHive.models.Chef;

import java.util.ArrayList;
import java.util.List;

public class NeighbourHoodItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Chef> chefList = new ArrayList<>();
    Context context;
    Typeface poppinsMedium;
    Typeface poppinsLight;

    public NeighbourHoodItemsAdapter(Context context,List<Chef> chefList,Typeface poppinsMedium,Typeface poppinsLight)
    {
        this.context = context;
        this.chefList = chefList;
        this.poppinsLight = poppinsLight;
        this.poppinsMedium = poppinsMedium;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_neighbourhoods_list_item,parent,false);
        return new NeighbourhoodItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NeighbourhoodItemsViewHolder neighbourhoodItemsViewHolder = (NeighbourhoodItemsViewHolder)holder;
        Chef chef = chefList.get(position);
        neighbourhoodItemsViewHolder.name.setTypeface(poppinsMedium);
        neighbourhoodItemsViewHolder.placeName.setTypeface(poppinsLight);
        neighbourhoodItemsViewHolder.name.setText(chefList.get(position).getName());
        neighbourhoodItemsViewHolder.placeName.setText(chefList.get(position).getLocation());
        Glide.with(context).load(chefList.get(position).getImage()).placeholder(R.drawable.foodi_logo_left_image).into(neighbourhoodItemsViewHolder.neighbourImage);

        neighbourhoodItemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CookedChefItemDetailActivity.class);
                intent.putExtra("chefId",chef.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chefList.size();
    }

    public class NeighbourhoodItemsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView neighbourImage;
        TextView name;
        TextView placeName;

        public NeighbourhoodItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            neighbourImage = itemView.findViewById(R.id.neighbourImage);
            name = itemView.findViewById(R.id.name);
            placeName = itemView.findViewById(R.id.placeName);
        }
    }
}
