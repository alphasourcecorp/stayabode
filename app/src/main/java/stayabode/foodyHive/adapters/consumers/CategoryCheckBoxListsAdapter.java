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


public class CategoryCheckBoxListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<Category> selectedcategorySortTypesList = new ArrayList<>();
    Context context;
    Typeface poppinsMedium;


    public CategoryCheckBoxListsAdapter(Context context,List<Category> categorySortTypes,Typeface poppinsMedium)
    {
        this.context = context;
        this.selectedcategorySortTypesList = categorySortTypes;
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
        categoryTypesItemViewHolder.name.setText(selectedcategorySortTypesList.get(position).getName());
        categoryTypesItemViewHolder.name.setTypeface(poppinsMedium);

        if(selectedcategorySortTypesList.get(position).getSelected())
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
                for (int i=0; i<selectedcategorySortTypesList.size();i++) {
                    if (isChecked) {

                    } else {
                    }
                }
                if(isChecked || !selectedcategorySortTypesList.get(position).getSelected())
                {
                    selectedcategorySortTypesList.get(position).setSelected(true);
                    selectedcategorySortTypesList.get(position).setNamewithTitle("Category: "+selectedcategorySortTypesList.get(position).getName());
                    categoryList.add(selectedcategorySortTypesList.get(position));

                }
                else
                {
                    selectedcategorySortTypesList.get(position).setSelected(false);

                    for (int i=0;i < FilteredItemsAdapter.objectList.size();i++)
                    {

                        if(FilteredItemsAdapter.objectList.get(i).getNamewithTitle().contains("Category"))
                        {
                            if(FilteredItemsAdapter.objectList.get(i).getId().equals(selectedcategorySortTypesList.get(position).getId()))
                            {
                                FilteredItemsAdapter.objectList.get(i).setSelected(false);
                                FilteredItemsAdapter.objectList.remove(i);
                            }
                        }


                    }
                    categoryList.remove(selectedcategorySortTypesList.get(position));
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
        return selectedcategorySortTypesList.size();
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
