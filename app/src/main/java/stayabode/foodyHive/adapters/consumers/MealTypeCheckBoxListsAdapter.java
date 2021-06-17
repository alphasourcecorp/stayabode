package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;

import stayabode.foodyHive.models.Category;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.categoryList;


public class MealTypeCheckBoxListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<Category> selectedMealsSortTypesList = new ArrayList<>();
    Context context;
    Typeface poppinsMedium;

    public MealTypeCheckBoxListsAdapter(Context context, List<Category> categorySortTypes,Typeface poppinsMedium)
    {
        this.context = context;
        this.selectedMealsSortTypesList = categorySortTypes;
        this.poppinsMedium= poppinsMedium;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_box_sub_category_items, parent, false);
        return new CategoryTypesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryTypesItemViewHolder categoryTypesItemViewHolder = (CategoryTypesItemViewHolder)holder;
        categoryTypesItemViewHolder.name.setText(selectedMealsSortTypesList.get(position).getName());
        categoryTypesItemViewHolder.name.setTypeface(poppinsMedium);

        if(selectedMealsSortTypesList.get(position).getSelected())
        {
            categoryTypesItemViewHolder.checkbox.setChecked(true);
        }
        else
        {
            categoryTypesItemViewHolder.checkbox.setChecked(false);
        }



        categoryTypesItemViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isChecked = categoryTypesItemViewHolder.checkbox.isChecked();
                for (int i=0; i<selectedMealsSortTypesList.size();i++) {
                    if (isChecked) {
                    } else {
                    }
                }
                if(isChecked)
                {
                    selectedMealsSortTypesList.get(position).setSelected(true);
                    selectedMealsSortTypesList.get(position).setNamewithTitle("Meal: "+selectedMealsSortTypesList.get(position).getName());
                    categoryList.add(selectedMealsSortTypesList.get(position));
                }
                else
                {
                    selectedMealsSortTypesList.get(position).setSelected(false);
                    for (int i=0;i < FilteredItemsAdapter.objectList.size();i++)
                    {

                        if(FilteredItemsAdapter.objectList.get(i).getNamewithTitle().contains("Meal"))
                        {
                            if(FilteredItemsAdapter.objectList.get(i).getId().equals(selectedMealsSortTypesList.get(position).getId()))
                            {
                                FilteredItemsAdapter.objectList.get(i).setSelected(false);
                                FilteredItemsAdapter.objectList.remove(i);
                            }
                        }


                    }
                    categoryList.remove(selectedMealsSortTypesList.get(position));
                }
            }
        });

        categoryTypesItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryTypesItemViewHolder.checkbox.performClick();
            }
        });

    }

    @Override
    public int getItemCount() {
        return selectedMealsSortTypesList.size();
    }

    public class CategoryTypesItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        CheckBox checkbox;

        public CategoryTypesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
