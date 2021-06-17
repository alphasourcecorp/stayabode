package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import stayabode.foodyHive.R;
import stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments;
import stayabode.foodyHive.models.Category;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.filtersrecyclerView;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.getHomePageAPI;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.getSelectedCategoryID;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.maxPriceSelectedFilter;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.minPriceSelectedFilter;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<Category> categoryList = new ArrayList<>();
    Context context;
    Typeface poppinsMedium;
    int position;
    Typeface poppinsBold;
    Typeface poppinsLight;
    Typeface robotoFontBold;
    Typeface robotoFontRegular;
    Typeface poppinsSemiBold;
    RecyclerView recyclerView;
    TextView header;
    ShimmerFrameLayout shimmerFrameLayout;

    public static String selectedCategoryID = "";

    public CategoryAdapter(Context context, List<Category> categoryList, Typeface poppinsMedium, int position, Typeface poppinsBold, Typeface poppinsLight, Typeface robotoFontBold, Typeface robotoFontRegular,Typeface poppinsSemiBold,RecyclerView recyclerView,TextView header,ShimmerFrameLayout shimmerFrameLayout)
    {
        this.categoryList = categoryList;
        this.context = context;
        this.poppinsMedium = poppinsMedium;
        this.position = position;
        this.poppinsBold = poppinsBold;
        this.poppinsLight = poppinsLight;
        this.robotoFontBold = robotoFontBold;
        this.robotoFontRegular = robotoFontRegular;
        this.poppinsSemiBold = poppinsSemiBold;
        this.recyclerView = recyclerView;
        this.header = header;
        this.shimmerFrameLayout = shimmerFrameLayout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_category_item,parent,false);
        return new CategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryItemViewHolder categoryItemViewHolder = (CategoryItemViewHolder)holder;
        Category category = categoryList.get(position);
        categoryItemViewHolder.categoryName.setTypeface(poppinsMedium);
        categoryItemViewHolder.categoryName.setText(category.getName());
        Glide.with(context).load(category.getSetImage()).placeholder(R.drawable.foodi_logo_left_image).into(categoryItemViewHolder.categoryImage);

        int[] colors = {Color.parseColor(category.getTopColor()),Color.parseColor(category.getBottomColor())};

///create a new gradient color
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);

        gd.setCornerRadius(0f);
        //apply the button background to newly created drawable gradient
        categoryItemViewHolder.backgroundLayout.setBackground(gd);
        categoryItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    minPriceSelectedFilter = 0;
                    maxPriceSelectedFilter = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < MainSortAdapters.selectedSortCostsItemsLists.size(); i++) {
                    MainSortAdapters.selectedSortCostsItemsLists.get(i).setSelected(false);
                }

                for (int i = 0; i < CategoryCheckBoxListsAdapter.selectedcategorySortTypesList.size(); i++) {
                    CategoryCheckBoxListsAdapter.selectedcategorySortTypesList.get(i).setSelected(false);
                }

                for (int i = 0; i < CuisineCheckBoxListsAdapter.selectedCuisinesSortTypeLists.size(); i++) {
                    CuisineCheckBoxListsAdapter.selectedCuisinesSortTypeLists.get(i).setSelected(false);
                }

                for (int i = 0; i < MealTypeCheckBoxListsAdapter.selectedMealsSortTypesList.size(); i++) {
                    MealTypeCheckBoxListsAdapter.selectedMealsSortTypesList.get(i).setSelected(false);

                }

                for (int i = 0; i < RatingItemSortsAdapter.selectedRatingsSortTypes.size(); i++) {
                    RatingItemSortsAdapter.selectedRatingsSortTypes.get(i).setSelected(false);

                }


                try {
                    ConsumerHomeOnDemandFragments.categoryList.clear();
                    filtersrecyclerView.setAdapter(new FilteredItemsAdapter(context,ConsumerHomeOnDemandFragments.categoryList));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                selectedCategoryID = category.getId();
                getSelectedCategoryID  = Integer.parseInt(category.getId());
                try {
                    getHomePageAPI(Integer.parseInt(category.getId()),category.getName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView categoryImage;
        TextView categoryName;
        LinearLayout backgroundLayout;

        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            backgroundLayout = itemView.findViewById(R.id.backgroundLayout);


        }
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
