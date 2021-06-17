package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.Category;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.categoryList;

public class MainSortAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<Category> selectedSortCostsItemsLists = new ArrayList<>();
    Context context;
    Typeface poppinsMedium;
    int selectedPosition = -1;
    int lastSelectedPosition = 0;

    public MainSortAdapters(Context context, List<Category> categorySortTypes,Typeface poppinsMedium,int lastSelectedPosition)
    {
        this.context = context;
        this.selectedSortCostsItemsLists = categorySortTypes;
        this.poppinsMedium =poppinsMedium;
        this.lastSelectedPosition = lastSelectedPosition;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_sort_item, parent, false);
        return new CategoryTypesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryTypesItemViewHolder categoryTypesItemViewHolder = (CategoryTypesItemViewHolder)holder;
        categoryTypesItemViewHolder.name.setText(selectedSortCostsItemsLists.get(position).getName());
        categoryTypesItemViewHolder.name.setTypeface(poppinsMedium);


        if(position == selectedPosition)
        {

            if(!categoryTypesItemViewHolder.radioButton.isChecked())
            {

                categoryTypesItemViewHolder.radioButton.setChecked(false);
                selectedSortCostsItemsLists.get(position).setSelected(false);
                selectedSortCostsItemsLists.get(position).setNamewithTitle("Sort: "+selectedSortCostsItemsLists.get(position).getName());
                for (int i=0;i < FilteredItemsAdapter.objectList.size();i++)
                {

                    if(FilteredItemsAdapter.objectList.get(i).getNamewithTitle().contains("Sort"))
                    {
                        if(FilteredItemsAdapter.objectList.get(i).getId().equals(selectedSortCostsItemsLists.get(position).getId()))
                        {
                            FilteredItemsAdapter.objectList.get(i).setSelected(false);
                            FilteredItemsAdapter.objectList.remove(i);
                        }
                    }


                }
                categoryList.remove(selectedSortCostsItemsLists.get(position));

            }
            else
            {

                categoryTypesItemViewHolder.radioButton.setChecked(true);
                selectedSortCostsItemsLists.get(position).setSelected(true);
                selectedSortCostsItemsLists.get(position).setNamewithTitle("Sort: "+selectedSortCostsItemsLists.get(position).getName());
                if(categoryList.contains(selectedSortCostsItemsLists.get(position)))
                {

                }
                else
                {

                    if(categoryList.size()!=0)
                    {
                        for (int i=0;i < categoryList.size();i++)
                        {

                            if(categoryList.get(i).getId().equals(selectedSortCostsItemsLists.get(position).getId()))
                            {

                            }
                            else
                            {

                                categoryList.add(selectedSortCostsItemsLists.get(position));
                            }
                        }
                    }
                    else
                    {
                        categoryList.add(selectedSortCostsItemsLists.get(position));
                    }


                }
            }


        }
        else
        {

            if(selectedSortCostsItemsLists.get(position).getSelected() && !categoryTypesItemViewHolder.radioButton.isChecked())
            {

                categoryTypesItemViewHolder.radioButton.setChecked(true);
                selectedSortCostsItemsLists.get(position).setNamewithTitle("Sort: "+selectedSortCostsItemsLists.get(position).getName());
                if(categoryList.contains(selectedSortCostsItemsLists.get(position)))
                {

                }
                else
                {

                }

            }
            else
            {


                categoryTypesItemViewHolder.radioButton.setChecked(false);
                selectedSortCostsItemsLists.get(position).setSelected(false);
                selectedSortCostsItemsLists.get(position).setNamewithTitle("Sort: "+selectedSortCostsItemsLists.get(position).getName());
                for (int i=0;i < FilteredItemsAdapter.objectList.size();i++)
                {

                    if(FilteredItemsAdapter.objectList.get(i).getNamewithTitle().contains("Sort"))
                    {
                        if(FilteredItemsAdapter.objectList.get(i).getId().equals(selectedSortCostsItemsLists.get(position).getId()))
                        {
                            FilteredItemsAdapter.objectList.get(i).setSelected(false);
                            FilteredItemsAdapter.objectList.remove(i);
                        }
                    }


                }
                categoryList.remove(selectedSortCostsItemsLists.get(position));
            }

        }







        categoryTypesItemViewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

        categoryTypesItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryTypesItemViewHolder.radioButton.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedSortCostsItemsLists.size();
    }

    public class CategoryTypesItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        RadioButton radioButton;

        public CategoryTypesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            radioButton = itemView.findViewById(R.id.sortIdRB);
        }
    }
}
