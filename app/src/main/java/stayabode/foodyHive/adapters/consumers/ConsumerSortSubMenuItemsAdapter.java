package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appyvet.materialrangebar.RangeBar;
import stayabode.foodyHive.R;

import stayabode.foodyHive.models.CategorySort;
import stayabode.foodyHive.models.CuisineSort;
import stayabode.foodyHive.models.MainSort;
import stayabode.foodyHive.models.MealTypeSort;
import stayabode.foodyHive.models.PriceSort;
import stayabode.foodyHive.models.SortRating;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.maxPriceSelectedFilter;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.minPriceSelectedFilter;

public class ConsumerSortSubMenuItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> objectList = new ArrayList<>();
    Context context;
    Typeface poppinsSemibold;
    Typeface poppinsLight;
    Typeface poppinsBold;
    Typeface poppinsMedium;
    Typeface robotoRegular;
    Typeface robotoBold;
    int lastposition;

    public ConsumerSortSubMenuItemsAdapter(Context context, List<Object> objectList, Typeface poppinsSemibold, Typeface poppinsLight, Typeface poppinsMedium, Typeface poppinsBold, Typeface robotoBold, Typeface robotoRegular,int position) {
        this.context = context;
        this.objectList = objectList;
        this.poppinsLight = poppinsLight;
        this.poppinsMedium = poppinsMedium;
        this.poppinsSemibold = poppinsSemibold;
        this.poppinsBold = poppinsBold;
        this.robotoBold = robotoBold;
        this.robotoRegular = robotoRegular;
        this.lastposition = position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(viewType, parent, false);

        if (viewType == R.layout.consumer_sort_heading) {
            return new SortInfoViewHolder(view);
        } else if (viewType == R.layout.consumer_price_sort) {
            return new PriceCategoryInfoViewHolder(view);
        } else if (viewType == R.layout.consumer_category_sort_item) {
            return new CategorySortItemViewHolder(view);
        }
        else if (viewType == R.layout.consumer_cuisine_sort_item) {
            return new CuisineSortViewHolder(view);
        }
        else if (viewType == R.layout.consumer_meal_type_sort_item) {
            return new MealTypeItemsViewHolder(view);
        }
        else {
            return new RatingItemsViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (objectList.get(position) instanceof MainSort) {

            SortInfoViewHolder sortInfoViewHolder = (SortInfoViewHolder)holder;
            MainSort mainSort = (MainSort)objectList.get(position);
            sortInfoViewHolder.header.setTypeface(poppinsSemibold);
            sortInfoViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            sortInfoViewHolder.recyclerView.setAdapter(new MainSortAdapters(context,mainSort.getCategoryList(),poppinsMedium,lastposition));

        }
        else if(objectList.get(position) instanceof PriceSort)
        {
            PriceCategoryInfoViewHolder priceCategoryInfoViewHolder = (PriceCategoryInfoViewHolder)holder;
            priceCategoryInfoViewHolder.header.setTypeface(poppinsSemibold);
            priceCategoryInfoViewHolder.leftPinValue.setText("₹"+priceCategoryInfoViewHolder.rangeBar.getLeftPinValue());
            priceCategoryInfoViewHolder.rightPinValue.setText("₹"+priceCategoryInfoViewHolder.rangeBar.getRightPinValue());

            if(minPriceSelectedFilter == 0 && maxPriceSelectedFilter == 0)
            {
                priceCategoryInfoViewHolder.rangeBar.setRangePinsByValue(10,1000);
            }
            else
            {
                Log.d("PriceMin",minPriceSelectedFilter + "");
                Log.d("PriceMax",maxPriceSelectedFilter + "");
                priceCategoryInfoViewHolder.rangeBar.setRangePinsByValue(minPriceSelectedFilter,maxPriceSelectedFilter);

            }
            priceCategoryInfoViewHolder.rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                @Override
                public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                    priceCategoryInfoViewHolder.leftPinValue.setText("₹"+leftPinValue);
                    priceCategoryInfoViewHolder.rightPinValue.setText("₹"+rightPinValue);
                    minPriceSelectedFilter = Integer.parseInt(leftPinValue);
                    maxPriceSelectedFilter = Integer.parseInt(rightPinValue);
                }

                @Override
                public void onTouchStarted(RangeBar rangeBar) {

                }

                @Override
                public void onTouchEnded(RangeBar rangeBar) {

                }
            });
        }
        else if(objectList.get(position) instanceof CategorySort)
        {
            CategorySortItemViewHolder categorySortItemViewHolder = (CategorySortItemViewHolder)holder;
            categorySortItemViewHolder.header.setTypeface(poppinsSemibold);
            CategorySort categorySort = (CategorySort)objectList.get(position);
            categorySortItemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            categorySortItemViewHolder.recyclerView.setAdapter(new CategoryCheckBoxListsAdapter(context,categorySort.getCategoryList(),poppinsMedium));
        }
        else if(objectList.get(position) instanceof CuisineSort)
        {
            CuisineSortViewHolder cuisineSortViewHolder = (CuisineSortViewHolder)holder;
            cuisineSortViewHolder.header.setTypeface(poppinsSemibold);
            CuisineSort cuisineSort = (CuisineSort) objectList.get(position);
            cuisineSortViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            cuisineSortViewHolder.recyclerView.setAdapter(new CuisineCheckBoxListsAdapter(context,cuisineSort.getCategoryList(),poppinsMedium));
        }
        else if(objectList.get(position) instanceof MealTypeSort)
        {
            MealTypeItemsViewHolder mealTypeItemsViewHolder = (MealTypeItemsViewHolder)holder;
            mealTypeItemsViewHolder.header.setTypeface(poppinsSemibold);
            MealTypeSort mealTypeSort = (MealTypeSort) objectList.get(position);
            mealTypeItemsViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mealTypeItemsViewHolder.recyclerView.setAdapter(new MealTypeCheckBoxListsAdapter(context,mealTypeSort.getCategoryList(),poppinsMedium));
        }
        else if(objectList.get(position) instanceof SortRating)
        {
            RatingItemsViewHolder mealTypeItemsViewHolder = (RatingItemsViewHolder)holder;
            mealTypeItemsViewHolder.header.setTypeface(poppinsSemibold);
            SortRating mealTypeSort = (SortRating) objectList.get(position);
            mealTypeItemsViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            Log.d("CheckCount",mealTypeSort.getCategoryList().size() + " Empty");
            mealTypeItemsViewHolder.recyclerView.setAdapter(new RatingItemSortsAdapter(context,mealTypeSort.getCategoryList(),poppinsMedium));
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof MainSort) {
            return R.layout.consumer_sort_heading;
        } else if (objectList.get(position) instanceof PriceSort) {
            return R.layout.consumer_price_sort;
        } else if (objectList.get(position) instanceof CategorySort) {
            return R.layout.consumer_category_sort_item;
        }
        else if (objectList.get(position) instanceof CuisineSort) {
            return R.layout.consumer_cuisine_sort_item;
        }
        else if (objectList.get(position) instanceof MealTypeSort) {
            return R.layout.consumer_meal_type_sort_item;
        }
        else if (objectList.get(position) instanceof SortRating) {
            return R.layout.sort_rating_header_view;
        }
        else {

        }
        return super.getItemViewType(position);
    }

    public class SortInfoViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView header;
        public SortInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);

        }
    }

    public class PriceCategoryInfoViewHolder extends RecyclerView.ViewHolder {

        TextView header;
        TextView leftPinValue;
        TextView rightPinValue;
        RangeBar rangeBar;
        public PriceCategoryInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            rangeBar=itemView.findViewById(R.id.rangeBar);
            leftPinValue=itemView.findViewById(R.id.leftPinValue);
            rightPinValue=itemView.findViewById(R.id.rightPinValue);
        }
    }

    public class CategorySortItemViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView header;

        public CategorySortItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
        }
    }

    public class CuisineSortViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView header;
        public CuisineSortViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
        }
    }

    public class MealTypeItemsViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView header;
        public MealTypeItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
        }
    }


    public class RatingItemsViewHolder extends RecyclerView.ViewHolder
    {
        RecyclerView recyclerView;
        TextView header;
        public RatingItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
        }
    }

}
