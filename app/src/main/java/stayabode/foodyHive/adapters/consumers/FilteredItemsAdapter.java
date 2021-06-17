package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.Category;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.getHomePageFilteredAPI;

public class FilteredItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<Category> objectList = new ArrayList<>();
    Context context;

    public FilteredItemsAdapter(Context context,List<Category> objectList )
    {
        this.context = context;
        this.objectList = objectList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filtered_items,parent,false);
        return new FilteresItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilteresItemsViewHolder filteresItemsViewHolder = (FilteresItemsViewHolder)holder;
                filteresItemsViewHolder.name.setText(objectList.get(position).getNamewithTitle());


        filteresItemsViewHolder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(objectList.size() !=0)
                    {
                        if(objectList.get(position).getNamewithTitle().contains("Category"))
                        {
                            for (int i=0;i < CategoryCheckBoxListsAdapter.selectedcategorySortTypesList.size();i++)
                            {

                                if(CategoryCheckBoxListsAdapter.selectedcategorySortTypesList.get(i).getId().equals(objectList.get(position).getId()))
                                {
                                    CategoryCheckBoxListsAdapter.selectedcategorySortTypesList.get(i).setSelected(false);
                                }

                            }

                        }
                        else if(objectList.get(position).getNamewithTitle().contains("Sort"))
                        {

                            for (int i=0;i < MainSortAdapters.selectedSortCostsItemsLists.size();i++)
                            {
                                if(MainSortAdapters.selectedSortCostsItemsLists.get(i).getId().equals(objectList.get(position).getId()))
                                {
                                    MainSortAdapters.selectedSortCostsItemsLists.get(i).setSelected(false);
                                }

                            }
                        }
                        else if(objectList.get(position).getNamewithTitle().contains("Cuisine"))
                        {

                            for (int i=0;i < CuisineCheckBoxListsAdapter.selectedCuisinesSortTypeLists.size();i++)
                            {
                                if(CuisineCheckBoxListsAdapter.selectedCuisinesSortTypeLists.get(i).getId().equals(objectList.get(position).getId()))
                                {
                                    CuisineCheckBoxListsAdapter.selectedCuisinesSortTypeLists.get(i).setSelected(false);
                                }

                            }
                        }
                        else if(objectList.get(position).getNamewithTitle().contains("Meal"))
                        {
                            for (int i=0;i < MealTypeCheckBoxListsAdapter.selectedMealsSortTypesList.size();i++)
                            {
                                if(MealTypeCheckBoxListsAdapter.selectedMealsSortTypesList.get(i).getId().equals(objectList.get(position).getId()))
                                {
                                    MealTypeCheckBoxListsAdapter.selectedMealsSortTypesList.get(i).setSelected(false);
                                }

                            }
                        }
                        else if(objectList.get(position).getNamewithTitle().contains("Rating"))
                        {
                            for (int i=0;i < RatingItemSortsAdapter.selectedRatingsSortTypes.size();i++)
                            {
                                if(RatingItemSortsAdapter.selectedRatingsSortTypes.get(i).getId().equals(objectList.get(position).getId()))
                                {
                                    RatingItemSortsAdapter.selectedRatingsSortTypes.get(i).setSelected(false);
                                }

                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    objectList.remove(position);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    getHomePageFilteredAPI(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class FilteresItemsViewHolder extends RecyclerView.ViewHolder
    {

        TextView name;
        ImageView close;

        public FilteresItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            close = itemView.findViewById(R.id.close);
        }
    }
}
