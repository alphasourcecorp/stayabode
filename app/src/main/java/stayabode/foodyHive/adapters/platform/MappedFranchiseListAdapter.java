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
import stayabode.foodyHive.models.Franchise;

import java.util.ArrayList;
import java.util.List;

public class MappedFranchiseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Franchise> franchiseList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;

    public MappedFranchiseListAdapter(Context context,List<Franchise> franchiseList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.franchiseList = franchiseList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.franchise_mapped_list_item,parent,false);
        return new MappedFranchisesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MappedFranchisesListViewHolder mappedFranchisesListViewHolder = (MappedFranchisesListViewHolder)holder;
        mappedFranchisesListViewHolder.franchiseName.setTypeface(fontRegular);
        mappedFranchisesListViewHolder.location.setTypeface(fontRegular);
        mappedFranchisesListViewHolder.franchiseName.setText(franchiseList.get(position).getName());
        mappedFranchisesListViewHolder.location.setText(franchiseList.get(position).getLocation());
        Glide.with(context).load(franchiseList.get(position).getImage()).placeholder(R.drawable.foodi_logo_left_image).into(mappedFranchisesListViewHolder.imageView);

        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            mappedFranchisesListViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
        }
        else
        {
            mappedFranchisesListViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            // Set the background color for alternate row/item
        }
    }

    @Override
    public int getItemCount() {
        return franchiseList.size();
    }

    public class MappedFranchisesListViewHolder extends RecyclerView.ViewHolder
    {
        TextView franchiseName;
        TextView location;
        ImageView imageView;

        public MappedFranchisesListViewHolder(@NonNull View itemView) {
            super(itemView);
            franchiseName = itemView.findViewById(R.id.franchiseName);
            location = itemView.findViewById(R.id.location);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
