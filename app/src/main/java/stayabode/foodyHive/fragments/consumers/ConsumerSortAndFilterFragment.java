package stayabode.foodyHive.fragments.consumers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;

import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.getSortByDishesSearchOptions;

public class ConsumerSortAndFilterFragment  extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    int ratingId=1;
    int sortById=2;
    int priceId=3;
    int categoryId=4;
    int cuisineId=5;
    int mealTypeId=6;
    String sTitle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_tab, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewAll);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        sTitle = getArguments().getString("title");

        if (sTitle.equalsIgnoreCase("Rating")){
            getSortByDishesSearchOptions(recyclerView,ratingId);
        }
        if (sTitle.equalsIgnoreCase("Sort By")){
            getSortByDishesSearchOptions(recyclerView,sortById);
        }
        if (sTitle.equalsIgnoreCase("Price")){
            getSortByDishesSearchOptions(recyclerView,priceId);
        }
        if (sTitle.equalsIgnoreCase("Category")){
            getSortByDishesSearchOptions(recyclerView,categoryId);
        }
        if (sTitle.equalsIgnoreCase("Cuisine")){
            getSortByDishesSearchOptions(recyclerView,cuisineId);
        }
        if (sTitle.equalsIgnoreCase("Meal Type")){
            getSortByDishesSearchOptions(recyclerView,mealTypeId);
        }

        return view;
    }
}
