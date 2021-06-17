package stayabode.foodyHive.adapters.chefs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.ChefRewards;

import java.util.ArrayList;
import java.util.List;

public class AccoladesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ChefRewards> chefRewardsList = new ArrayList<>();
    Context context;

    public AccoladesAdapter(Context context,List<ChefRewards> chefRewardsList)
    {
        this.chefRewardsList = chefRewardsList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accolades_profile_item, parent, false);
        return new AccoladesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AccoladesViewHolder accoladesViewHolder = (AccoladesViewHolder)holder;

        accoladesViewHolder.description.setText(chefRewardsList.get(position).getDescription());
        accoladesViewHolder.title.setText(chefRewardsList.get(position).getName());


        accoladesViewHolder.deleteIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chefRewardsList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chefRewardsList.size();
    }

    public class AccoladesViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout accoladesLayout;
        LinearLayout deleteIconLayout;
        TextView title;
        TextView description;

        public AccoladesViewHolder(@NonNull View itemView) {
            super(itemView);
            accoladesLayout = itemView.findViewById(R.id.accoladesLayout);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            deleteIconLayout = itemView.findViewById(R.id.deleteIconLayout);
        }
    }

}
