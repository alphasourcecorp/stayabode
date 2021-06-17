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
import stayabode.foodyHive.models.Franchise;

import java.util.ArrayList;
import java.util.List;

public class NewFranchisesHomeSubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Franchise> franchiseList = new ArrayList<>();
    Context context;
    Typeface fontBold;
    Typeface fontRegular;

    public NewFranchisesHomeSubAdapter(Context context,List<Franchise> franchiseList,Typeface fontBold,Typeface fontRegular)
    {
        this.franchiseList = franchiseList;
        this.context = context;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_franchises_list_item,parent,false);
        return new FranchiseeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FranchiseeItemViewHolder franchiseeItemViewHolder = (FranchiseeItemViewHolder)holder;
        franchiseeItemViewHolder.amount.setTypeface(fontRegular);
        franchiseeItemViewHolder.dateText.setTypeface(fontRegular);
        franchiseeItemViewHolder.franchiseName.setTypeface(fontRegular);
        franchiseeItemViewHolder.location.setTypeface(fontRegular);
        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            franchiseeItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
        }
        else
        {
            franchiseeItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            // Set the background color for alternate row/item
        }
        franchiseeItemViewHolder.amount.setText(franchiseList.get(position).getAmount());
        franchiseeItemViewHolder.franchiseName.setText(franchiseList.get(position).getName());
        franchiseeItemViewHolder.dateText.setText(franchiseList.get(position).getDate());
        franchiseeItemViewHolder.location.setText(franchiseList.get(position).getLocation());


    }

    @Override
    public int getItemCount() {
        return franchiseList.size();
    }

    public class FranchiseeItemViewHolder extends RecyclerView.ViewHolder
    {

        TextView dateText;
        TextView franchiseName;
        TextView location;
        TextView amount;

        public FranchiseeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            franchiseName = itemView.findViewById(R.id.franchiseName);
            location = itemView.findViewById(R.id.location);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
