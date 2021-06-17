package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.ItemAddOns;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.activities.consumers.ProfileInfoActivity.selectedFoodPreferencesList;

public class ConsumerPreferencesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ItemAddOns> consumerSelectedpreferencesesListss = new ArrayList<>();

    public ConsumerPreferencesAdapter(Context context,List<ItemAddOns> categoryList)
    {
        this.consumerSelectedpreferencesesListss = categoryList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_box_sub_category_items, parent, false);
        return new ConsumerPreferencesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConsumerPreferencesViewHolder categoryTypesItemViewHolder = (ConsumerPreferencesViewHolder)holder;
        categoryTypesItemViewHolder.name.setText(consumerSelectedpreferencesesListss.get(position).getName());

        if(consumerSelectedpreferencesesListss.get(position).getSelected())
        {
            categoryTypesItemViewHolder.checkbox.setChecked(true);
            consumerSelectedpreferencesesListss.get(position).setSelected(true);
        }
        else
        {
            categoryTypesItemViewHolder.checkbox.setChecked(false);
            consumerSelectedpreferencesesListss.get(position).setSelected(false);
        }

        categoryTypesItemViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final boolean isChecked = categoryTypesItemViewHolder.checkbox.isChecked();
                for (int i=0; i<consumerSelectedpreferencesesListss.size();i++) {
                    if (isChecked) {

                    } else {

                    }
                }
                if(isChecked || !consumerSelectedpreferencesesListss.get(position).getSelected())
                {
                    consumerSelectedpreferencesesListss.get(position).setSelected(true);
                    selectedFoodPreferencesList.add(consumerSelectedpreferencesesListss.get(position));

                }
                else
                {
                    consumerSelectedpreferencesesListss.get(position).setSelected(false);
                    for (int i=0;i < selectedFoodPreferencesList.size();i++)
                    {
                        if(selectedFoodPreferencesList.get(i).getId().equals(consumerSelectedpreferencesesListss.get(position).getId()))
                        {
                            selectedFoodPreferencesList.get(i).setSelected(false);
                        }

                    }
                    selectedFoodPreferencesList.remove(consumerSelectedpreferencesesListss.get(position));

                }
            }
        });

        categoryTypesItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryTypesItemViewHolder.checkbox.performClick();
            }
        });


    }

    @Override
    public int getItemCount() {
        return consumerSelectedpreferencesesListss.size();
    }

    public class ConsumerPreferencesViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        CheckBox checkbox;

        public ConsumerPreferencesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
