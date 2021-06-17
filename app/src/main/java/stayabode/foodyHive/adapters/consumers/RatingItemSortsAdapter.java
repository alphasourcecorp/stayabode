package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.models.Category;


import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.categoryList;


public class RatingItemSortsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<Category> selectedRatingsSortTypes = new ArrayList<>();
    Context context;
    Typeface poppinsMedium;


    public RatingItemSortsAdapter(Context context,List<Category> ratingSortTypesList,Typeface poppinsMedium)
    {
        this.context = context;
        this.selectedRatingsSortTypes = ratingSortTypesList;
        this.poppinsMedium= poppinsMedium;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_rating_items, parent, false);
        return new RatingsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RatingsItemViewHolder ratingsItemViewHolder = (RatingsItemViewHolder)holder;
        ratingsItemViewHolder.ratingBar.setIsIndicator(true);
        ratingsItemViewHolder.ratingBar.setFocusable(false);
        ratingsItemViewHolder.ratingBar.setRating(selectedRatingsSortTypes.get(position).getRatingCount());

        if(selectedRatingsSortTypes.get(position).getSelected())
        {
            ratingsItemViewHolder.checkbox.setChecked(true);
        }
        else
        {
            ratingsItemViewHolder.checkbox.setChecked(false);
        }

        ratingsItemViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isChecked = ratingsItemViewHolder.checkbox.isChecked();
                for (int i=0; i<selectedRatingsSortTypes.size();i++) {
                    if (isChecked) {
                    } else {
                    }
                }
                if(isChecked)
                {
                    selectedRatingsSortTypes.get(position).setSelected(true);
                    selectedRatingsSortTypes.get(position).setNamewithTitle("Rating: "+selectedRatingsSortTypes.get(position).getRatingCount());
                    categoryList.add(selectedRatingsSortTypes.get(position));
                }
                else
                {
                    selectedRatingsSortTypes.get(position).setSelected(false);
                    for (int i=0;i < FilteredItemsAdapter.objectList.size();i++)
                    {

                        if(FilteredItemsAdapter.objectList.get(i).getNamewithTitle().contains("Rating"))
                        {
                            if(FilteredItemsAdapter.objectList.get(i).getId().equals(selectedRatingsSortTypes.get(position).getId()))
                            {
                                FilteredItemsAdapter.objectList.get(i).setSelected(false);
                                FilteredItemsAdapter.objectList.remove(i);
                            }
                        }


                    }
                    categoryList.remove(selectedRatingsSortTypes.get(position));
                }
            }
        });

        ratingsItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingsItemViewHolder.checkbox.performClick();
            }
        });


    }

    @Override
    public int getItemCount() {
        return selectedRatingsSortTypes.size();
    }

    public class RatingsItemViewHolder extends RecyclerView.ViewHolder
    {

        RatingBar ratingBar;
        CheckBox checkbox;
        public RatingsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
