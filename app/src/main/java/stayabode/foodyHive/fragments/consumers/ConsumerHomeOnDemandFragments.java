package stayabode.foodyHive.fragments.consumers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
//import com.cunoraz.tagview.Tag;
//import com.cunoraz.tagview.TagView;
import com.facebook.shimmer.ShimmerFrameLayout;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerBecomeChefActivity;
import stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.activities.consumers.ViewAllSubItemsActivity;
import stayabode.foodyHive.adapters.consumers.CategoryAdapter;
import stayabode.foodyHive.adapters.consumers.FilteredItemsAdapter;
import stayabode.foodyHive.adapters.consumers.FoodItemAdapter;
import stayabode.foodyHive.adapters.consumers.NeighbourHoodItemsAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Category;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomRequest;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.cartTotalCountText;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.consumertoolbar;

public class ConsumerHomeOnDemandFragments extends Fragment {

    public static Typeface poppinsSemiBold;
    public static Typeface poppinsBold;
    public static Typeface poppinsMedium;
    public static Typeface poppinsLight;
    public static Typeface robotoFontBold;
    public static Typeface robotoFontRegular;


    RecyclerView categoryRecyclerView;
    public static RecyclerView topOfferrecyclerView;
    public static RecyclerView chefsrecyclerView;
    public static RecyclerView popularrecyclerView;
    public static RecyclerView recentlyrecyclerView;
    public static RecyclerView filtersrecyclerView;

    public static LinearLayout cartLayout;
    public static TextView totalAmount;
    public static TextView cost;
    public static TextView checkoutText;
    TextView searchText;
    ImageView filterIcon;
    CardView searchBar;

    public static String TAG = "ConsumerHomeOnDemandFragment";

    public static ConsumerHomeOnDemandFragments consumerHomeOnDemandFragments;

    ShimmerFrameLayout categoryshimmerLayout;
    public static ShimmerFrameLayout offersshimmerLayout;
    public static ShimmerFrameLayout popularshimmerLayout;
    public static ShimmerFrameLayout recentlyshimmerLayout;
    public static ShimmerFrameLayout chefsshimmerLayout;
    public static TextView header;
    public static TextView chefheader;
    public static TextView wishHeader;
    public static TextView popularviewAll;
    public static TextView recentheader;
    public static TextView popularChoiceheader;
    public static LinearLayout joinUs;
    LinearLayout popularLay;
    public static LinearLayout recentViewAllLay;
    public static LinearLayout offersViewAll;


    // For Filters Section

    public static int minPriceSelectedFilter = 0;
    public static int maxPriceSelectedFilter = 0;
    public static int selectedCuisinesId= 0;
    public static int selectedMealTypesId= 0;
    public static int selectedQuickFilterTypesID= 0;
    public static String selectedSortFilterArray = "";
    public static ArrayList<Integer> quickFiltersArrayIntes = new ArrayList<Integer>();
    public static ArrayList<Integer> mealTypesAddayIntes = new ArrayList<Integer>();
    public static ArrayList<Integer> cuisinesTypesAddayIntes = new ArrayList<Integer>();
    public static ArrayList<Integer> ratingsTypesAddayIntes = new ArrayList<Integer>();

    public static ArrayList<String> quickFiltersArrayString = new ArrayList<String>();
    public static ArrayList<String> mealTypesAddayStringI = new ArrayList<String>();
    public static ArrayList<String> cuisinesTypesAddayString = new ArrayList<String>();
    public static ArrayList<String> ratingsTypesAddayString = new ArrayList<String>();


    public static List<Category> selectedcategorySortTypesList = new ArrayList<>();
    public static List<Category> selectedMealSortTypesList = new ArrayList<>();
    public static List<Category> selectedCuisinesSortTypesList = new ArrayList<>();
    public static TextView jointext;

    public static List<Object> filteredobjectList = new ArrayList<>();
    public static ArrayList<String> selectedStringsList = new ArrayList<String>();


     public static List<Category> categoryList = new ArrayList<>();
     public static List<Category> noRepeatCategorysLists = new ArrayList<>();
     public static List<Category> freshcategoryList = new ArrayList<>();
     public static TextView noRecordsFound;
    CategoryAdapter categoryAdapter;
    public static FoodItemAdapter topFoodItesmAdapter;
    public static FoodItemAdapter popularFoodItesmAdapter;
    public static FoodItemAdapter recentlyFoodItesmAdapter;
    NeighbourHoodItemsAdapter neighbourHoodItemsAdapter;
    public static List<FoodItem> topOffersfoodItemList = new ArrayList<>();
    public static List<FoodItem> popularChoicesfoodItemList = new ArrayList<>();
    public static List<FoodItem> recentlyViewedfoodItemList = new ArrayList<>();
    public static List<Chef> chefList = new ArrayList<>();
    public static TextView categoryheader;

    public static int getSelectedCategoryID = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_consumer_on_demand,container,false);
        consumerHomeOnDemandFragments = this;
        poppinsSemiBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");
        robotoFontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        poppinsMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Medium.ttf");
        robotoFontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        consumertoolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze);
        cartLayout = rootView.findViewById(R.id.cartLayout);
        totalAmount = rootView.findViewById(R.id.totalAmount);
        cost = rootView.findViewById(R.id.cost);
        checkoutText = rootView.findViewById(R.id.checkoutText);
        noRecordsFound = rootView.findViewById(R.id.noRecordsFound);
        noRecordsFound.setVisibility(View.GONE);

        searchText = rootView.findViewById(R.id.searchText);
        filterIcon = rootView.findViewById(R.id.filterIcon);
        searchBar = rootView.findViewById(R.id.searchBar);
        searchText.setTypeface(poppinsMedium);
        header = rootView.findViewById(R.id.topOfferheader);
        chefheader = rootView.findViewById(R.id.chefheader);
        wishHeader = rootView.findViewById(R.id.wishHeader);
        popularviewAll = rootView.findViewById(R.id.popularviewAll);
        recentheader = rootView.findViewById(R.id.recentheader);
        joinUs = rootView.findViewById(R.id.joinUs);
        popularLay = rootView.findViewById(R.id.popularLay);
        recentViewAllLay = rootView.findViewById(R.id.recentViewAllLay);
        popularChoiceheader = rootView.findViewById(R.id.popularChoiceheader);
        offersViewAll = rootView.findViewById(R.id.offersViewAll);
        categoryRecyclerView = rootView.findViewById(R.id.categoryRecyclerView);
        topOfferrecyclerView = rootView.findViewById(R.id.topOfferrecyclerView);
        chefsrecyclerView = rootView.findViewById(R.id.chefsrecyclerView);
        popularrecyclerView = rootView.findViewById(R.id.popularrecyclerView);
        recentlyrecyclerView = rootView.findViewById(R.id.recentlyrecyclerView);
        filtersrecyclerView = rootView.findViewById(R.id.filtersrecyclerView);
        categoryshimmerLayout = rootView.findViewById(R.id.categoryshimmerLayout);
        offersshimmerLayout = rootView.findViewById(R.id.offersshimmerLayout);
        popularshimmerLayout = rootView.findViewById(R.id.popularshimmerLayout);
        recentlyshimmerLayout = rootView.findViewById(R.id.recentlyshimmerLayout);
        chefsshimmerLayout = rootView.findViewById(R.id.chefsshimmerLayout);
        jointext = rootView.findViewById(R.id.jointext);
        categoryheader = rootView.findViewById(R.id.categoryheader);


        categoryshimmerLayout.startShimmerAnimation();
        categoryshimmerLayout.setVisibility(View.VISIBLE);
        offersshimmerLayout.startShimmerAnimation();
        offersshimmerLayout.setVisibility(View.VISIBLE);
        chefsshimmerLayout.startShimmerAnimation();
        chefsshimmerLayout.setVisibility(View.VISIBLE);
        popularshimmerLayout.startShimmerAnimation();
        popularshimmerLayout.setVisibility(View.VISIBLE);
        recentlyshimmerLayout.startShimmerAnimation();
        recentlyshimmerLayout.setVisibility(View.VISIBLE);


        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        topOfferrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        chefsrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        popularrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recentlyrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        filtersrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        categoryAdapter = new CategoryAdapter(getContext(), freshcategoryList, poppinsMedium,0,poppinsBold,poppinsLight,robotoFontBold,robotoFontRegular,poppinsSemiBold,null,null,null);
        categoryRecyclerView.setAdapter(categoryAdapter);

        topFoodItesmAdapter = new FoodItemAdapter(getContext(), topOffersfoodItemList, poppinsSemiBold, poppinsLight, robotoFontBold, robotoFontRegular,"Top Offers");
        topOfferrecyclerView.setAdapter(topFoodItesmAdapter);

        neighbourHoodItemsAdapter = new NeighbourHoodItemsAdapter(getContext(), chefList, poppinsMedium, poppinsLight);
        chefsrecyclerView.setAdapter(neighbourHoodItemsAdapter);

        popularFoodItesmAdapter = new FoodItemAdapter(getContext(), popularChoicesfoodItemList, poppinsSemiBold, poppinsLight, robotoFontBold, robotoFontRegular,"Popular");
        popularrecyclerView.setAdapter(popularFoodItesmAdapter);

        recentlyFoodItesmAdapter = new FoodItemAdapter(getContext(), recentlyViewedfoodItemList, poppinsSemiBold, poppinsLight, robotoFontBold, robotoFontRegular,"Recently Viewed");
        recentlyrecyclerView.setAdapter(recentlyFoodItesmAdapter);


        getHomePageCategories();
        getSelectedCategoryID = 1;
        try {
            getHomePageAPI(1,"Top Offers");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            getNearByChefsAtYourLocation(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            getPopularChoicesItems();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            getRecentlyViewedItems();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        offersViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewAllSubItemsActivity.class);
                startActivity(intent);
            }
        });


        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConsumerCheckOutActivity.class);
                startActivity(intent);
            }
        });



        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                DialogFragment newFragment = ConsumerMainActivity.MyDialogFragment.newInstance();
                newFragment.show(ft, "sort_dialog");
            }
        });

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                DialogFragment newFragment = ConsumerMainActivity.MyDialogFragment.newInstance();
                newFragment.show(ft, "sort_dialog");
            }
        });

        jointext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ConsumerBecomeChefActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }


    /**
     Get Categories(GET)
     **/
    public void getHomePageCategories() {

        categoryshimmerLayout.startShimmerAnimation();
        categoryshimmerLayout.setVisibility(View.VISIBLE);

        String url = APIBaseURL.getHomePageCategories;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                categoryshimmerLayout.stopShimmerAnimation();
                categoryshimmerLayout.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dishCategoriesArray = jsonObject.getJSONArray("data");
                    freshcategoryList.clear();
                    for (int i = 0; i < dishCategoriesArray.length(); i++) {
                        JSONObject dishCategoriesObject = dishCategoriesArray.getJSONObject(i);

                        Category category = new Category();
                        category.setName(dishCategoriesObject.optString("title"));
                        category.setSetImage(dishCategoriesObject.optString("icon"));
                        category.setId(dishCategoriesObject.optString("id"));
                        category.setTopColor(dishCategoriesObject.optString("topColor"));
                        category.setBottomColor(dishCategoriesObject.optString("bottomColor"));

                        if(category.getName().equals("Calories") || category.getName().equals("Protein") || category.getName().equals("Carbs") || category.getName().equals("Fibre") || category.getName().equals("Self Pickup"))
                        {

                        }
                        else
                        {
                            freshcategoryList.add(category);
                        }

                    }
                    categoryRecyclerView.getAdapter().notifyDataSetChanged();

                    if(categoryRecyclerView.getAdapter().getItemCount() == 0)
                    {
                        categoryheader.setVisibility(View.GONE);
                    }
                    else
                    {
                        categoryheader.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                categoryshimmerLayout.stopShimmerAnimation();
                categoryshimmerLayout.setVisibility(View.GONE);

                if(categoryRecyclerView.getAdapter().getItemCount() == 0)
                {
                    categoryheader.setVisibility(View.GONE);
                }
                else
                {
                    categoryheader.setVisibility(View.VISIBLE);
                }
            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "get_category_taq");
    }



    /**
     Get Top Offers and Other Stuffs(POST)
     **/
    public static void getHomePageAPI(int selectedID,String selectedName) throws JSONException {
        offersshimmerLayout.startShimmerAnimation();
        offersshimmerLayout.setVisibility(View.VISIBLE);
        topOfferrecyclerView.setVisibility(View.GONE);
        noRecordsFound.setVisibility(View.GONE);
        String url = APIBaseURL.consumerHomePage;
        header.setText(selectedName);
        if(header.getText().toString().equalsIgnoreCase("top offers")){
            offersViewAll.setVisibility(View.VISIBLE);
        }else offersViewAll.setVisibility(View.GONE);

        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        locationObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("location",locationObject);
        inputObject.put("customerId", SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", 10);
        pageObject.put("index", 0);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(selectedID);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        inputObject.put("typeOfDish", typeOfDishArray);
        inputObject.put("priceRangeFrom", 0);
        inputObject.put("priceRangeTo", 0);
        JSONArray ratingArray = new JSONArray();
        ratingArray.put(0);
        inputObject.put("rating", ratingArray);
        inputObject.put("healthCalMin", 0);
        inputObject.put("healthCalMax", 0);
        inputObject.put("healthProtienMax", 0);
        inputObject.put("healthProtienMin", 0);
        inputObject.put("healthCarbsMin", 0);
        inputObject.put("healthCarbsMax", 0);
        inputObject.put("healthFiberMin", 0);
        inputObject.put("healthFiberMax", 0);

        Log.d("TopOffersinputObject",inputObject + "");

        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                offersshimmerLayout.stopShimmerAnimation();
                offersshimmerLayout.setVisibility(View.GONE);
                topOfferrecyclerView.setVisibility(View.VISIBLE);
                noRecordsFound.setVisibility(View.GONE);
                Log.d("HomePageResponse", response.toString());
                try {

                    topOffersfoodItemList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - "  + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setDeliveryTime(menuObject.optString("deliveryTime") + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");
                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();

                        if (menuObject.has("quickInfo")) {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if (quickInfoObject.has("nutrition")) {
                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
                            }
                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
                            foodItem.setFatCount(nutritionObject.optInt("fat"));
                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                        }
                        JSONObject chefQuickInfoObject = new JSONObject();

                        if (menuObject.has("chefQuickInfo")) {
                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                        }
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));

                        JSONArray typeOfDishArray = new JSONArray();
                        if(quickInfoObject.has("typeOfDish"))
                        {
                            typeOfDishArray = quickInfoObject.getJSONArray("typeOfDish");

                            if(typeOfDishArray.length()!=0)
                            {
                                if(typeOfDishArray.get(0).equals(1))
                                {
                                    foodItem.setTypeOfDish("VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(2))
                                {
                                    foodItem.setTypeOfDish("NON-VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(3))
                                {
                                    foodItem.setTypeOfDish("EGGETARIAN");
                                }
                                else if(typeOfDishArray.get(0).equals(4))
                                {
                                    foodItem.setTypeOfDish("VEGAN");
                                }
                            }


                        }


                        topOffersfoodItemList.add(foodItem);
                    }
                   topOfferrecyclerView.getAdapter().notifyDataSetChanged();
                    if(topOfferrecyclerView.getAdapter().getItemCount() == 0)
                    {
                        header.setVisibility(View.GONE);
                        offersViewAll.setVisibility(View.GONE);
                    }
                    else
                    {
                        header.setVisibility(View.VISIBLE);
                        offersViewAll.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                offersshimmerLayout.stopShimmerAnimation();
                offersshimmerLayout.setVisibility(View.GONE);
                noRecordsFound.setVisibility(View.VISIBLE);
                if(topOfferrecyclerView.getAdapter().getItemCount() == 0)
                {
                    header.setVisibility(View.GONE);
                    offersViewAll.setVisibility(View.GONE);
                }
                else
                {
                    header.setVisibility(View.VISIBLE);
                    offersViewAll.setVisibility(View.VISIBLE);
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    };



    /**
     Get Top Offers and Other Stuffs(POST)
     **/
    public static void updateOtherThanTopOFfers(int selectedID,String selectedName) throws JSONException {


        String url = APIBaseURL.consumerHomePage;
        header.setText(selectedName);
        if(header.getText().toString().equalsIgnoreCase("top offers")){
            offersViewAll.setVisibility(View.VISIBLE);
        }else offersViewAll.setVisibility(View.GONE);

        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        locationObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("location",locationObject);
        inputObject.put("customerId", SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", 10);
        pageObject.put("index", 0);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(selectedID);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        inputObject.put("typeOfDish", typeOfDishArray);
        inputObject.put("priceRangeFrom", 0);
        inputObject.put("priceRangeTo", 0);
        JSONArray ratingArray = new JSONArray();
        ratingArray.put(0);
        inputObject.put("rating", ratingArray);
        inputObject.put("healthCalMin", 0);
        inputObject.put("healthCalMax", 0);
        inputObject.put("healthProtienMax", 0);
        inputObject.put("healthProtienMin", 0);
        inputObject.put("healthCarbsMin", 0);
        inputObject.put("healthCarbsMax", 0);
        inputObject.put("healthFiberMin", 0);
        inputObject.put("healthFiberMax", 0);

        Log.d("HomeInputObject", inputObject.toString());

        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    topOffersfoodItemList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - "  + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setDeliveryTime(menuObject.optString("deliveryTime") + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");
                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();

                        if (menuObject.has("quickInfo")) {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if (quickInfoObject.has("nutrition")) {
                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
                            }
                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
                            foodItem.setFatCount(nutritionObject.optInt("fat"));
                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                        }
                        JSONObject chefQuickInfoObject = new JSONObject();

                        if (menuObject.has("chefQuickInfo")) {
                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                        }
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        JSONArray typeOfDishArray = new JSONArray();
                        if(quickInfoObject.has("typeOfDish"))
                        {
                            typeOfDishArray = quickInfoObject.getJSONArray("typeOfDish");
                            if(typeOfDishArray.length()!=0)
                            {
                                if(typeOfDishArray.get(0).equals(1))
                                {
                                    foodItem.setTypeOfDish("VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(2))
                                {
                                    foodItem.setTypeOfDish("NON-VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(3))
                                {
                                    foodItem.setTypeOfDish("EGGETARIAN");
                                }
                                else if(typeOfDishArray.get(0).equals(4))
                                {
                                    foodItem.setTypeOfDish("VEGAN");
                                }
                            }


                        }


                        topOffersfoodItemList.add(foodItem);
                    }
                    topOfferrecyclerView.getAdapter().notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                offersshimmerLayout.stopShimmerAnimation();
                offersshimmerLayout.setVisibility(View.GONE);
                noRecordsFound.setVisibility(View.VISIBLE);
                if(topOfferrecyclerView.getAdapter().getItemCount() == 0)
                {
                    header.setVisibility(View.GONE);
                    offersViewAll.setVisibility(View.GONE);
                }
                else
                {
                    header.setVisibility(View.VISIBLE);
                    offersViewAll.setVisibility(View.VISIBLE);
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    };

    /**
     Get Near Chefs(POST)
     **/
    public static void getNearByChefsAtYourLocation(int refresh) throws JSONException {
        if(refresh == 1)
        {
            chefsshimmerLayout.startShimmerAnimation();
            chefsshimmerLayout.setVisibility(View.VISIBLE);
            chefsrecyclerView.setVisibility(View.GONE);
        }

        String url = APIBaseURL.getChefsLocation;

        JSONObject inputObject = new JSONObject();
        inputObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                chefsshimmerLayout.stopShimmerAnimation();
                chefsshimmerLayout.setVisibility(View.GONE);
                chefsrecyclerView.setVisibility(View.VISIBLE);

                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    chefList.clear();
                    for (int i=0;i < dataArray.length();i++)
                    {
                        JSONObject dataObject = dataArray.getJSONObject(i);

                        Chef chef = new Chef();
                        chef.setName(dataObject.optString("name"));
                        chef.setId(dataObject.optString("id"));
                        JSONObject locationObject = new JSONObject();
                        if(dataObject.has("location"))
                        {
                            locationObject = dataObject.getJSONObject("location");
                        }
                        chef.setLocation(locationObject.optString("name"));
                        chef.setImage(dataObject.optString("uploadLogo"));
                        if(dataObject.optBoolean("isApproved")){
                            chefList.add(chef);
                        }


                    }
                   chefsrecyclerView.getAdapter().notifyDataSetChanged();
                    if(chefsrecyclerView.getAdapter().getItemCount() == 0)
                    {
                        chefheader.setVisibility(View.GONE);
                        wishHeader.setVisibility(View.GONE);
                        jointext.setVisibility(View.GONE);
                        joinUs.setVisibility(View.GONE);
                    }
                    else
                    {
                        chefheader.setVisibility(View.VISIBLE);
                        wishHeader.setVisibility(View.VISIBLE);
                        jointext.setVisibility(View.VISIBLE);
                        joinUs.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                chefsshimmerLayout.stopShimmerAnimation();
                chefsshimmerLayout.setVisibility(View.GONE);
                try {
                    chefheader.setVisibility(View.GONE);
                    wishHeader.setVisibility(View.GONE);
                    jointext.setVisibility(View.GONE);
                    joinUs.setVisibility(View.GONE);
                    chefsrecyclerView.setVisibility(View.GONE);
                }
                catch (Exception e)
                {
                    chefsrecyclerView.setVisibility(View.GONE);
                    chefheader.setVisibility(View.GONE);
                    wishHeader.setVisibility(View.GONE);
                    jointext.setVisibility(View.GONE);
                    joinUs.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                chefsshimmerLayout.stopShimmerAnimation();
                chefsshimmerLayout.setVisibility(View.GONE);

                if(chefsrecyclerView.getAdapter().getItemCount() == 0)
                {
                    chefheader.setVisibility(View.GONE);
                    jointext.setVisibility(View.GONE);
                    joinUs.setVisibility(View.GONE);
                }
                else
                {
                    chefheader.setVisibility(View.VISIBLE);
                    jointext.setVisibility(View.VISIBLE);
                    joinUs.setVisibility(View.VISIBLE);
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"near_chefs_taq");
    }


    /**
     get popular food items(POST)
     **/
    public void getPopularChoicesItems() throws JSONException {

        popularshimmerLayout.startShimmerAnimation();
        popularshimmerLayout.setVisibility(View.VISIBLE);
        String url = APIBaseURL.consumerHomePage;


        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        locationObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("location",locationObject);
        inputObject.put("customerId",  SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", 10);
        pageObject.put("index", 0);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(0);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        JSONArray sortFilterArray = new JSONArray();
        typeOfDishArray.put(0);
        sortFilterArray.put(4);
        inputObject.put("typeOfDish", typeOfDishArray);
        inputObject.put("sortFilter", sortFilterArray);
        inputObject.put("priceRangeFrom", 0);
        inputObject.put("priceRangeTo", 0);
        JSONArray ratingArray = new JSONArray();
        ratingArray.put(0);
        inputObject.put("rating", ratingArray);
        inputObject.put("healthCalMin", 0);
        inputObject.put("healthCalMax", 0);
        inputObject.put("healthProtienMax", 0);
        inputObject.put("healthProtienMin", 0);
        inputObject.put("healthCarbsMin", 0);
        inputObject.put("healthCarbsMax", 0);
        inputObject.put("healthFiberMin", 0);
        inputObject.put("healthFiberMax", 0);


        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                popularshimmerLayout.stopShimmerAnimation();
                popularshimmerLayout.setVisibility(View.GONE);
                try {

                    popularChoicesfoodItemList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - "  + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setDeliveryTime(menuObject.optString("deliveryTime") + " mins");
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();



                        if (menuObject.has("quickInfo")) {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if (quickInfoObject.has("nutrition")) {
                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
                            }
                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
                            foodItem.setFatCount(nutritionObject.optInt("fat"));
                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                        }
                        JSONObject chefQuickInfoObject = new JSONObject();

                        if (menuObject.has("chefQuickInfo")) {
                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                        }
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        JSONArray typeOfDishArray = new JSONArray();
                        if(quickInfoObject.has("typeOfDish"))
                        {
                            typeOfDishArray = quickInfoObject.getJSONArray("typeOfDish");
                            if(typeOfDishArray.length()!=0)
                            {
                                if(typeOfDishArray.get(0).equals(1))
                                {
                                    foodItem.setTypeOfDish("VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(2))
                                {
                                    foodItem.setTypeOfDish("NON-VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(3))
                                {
                                    foodItem.setTypeOfDish("EGGETARIAN");
                                }
                                else if(typeOfDishArray.get(0).equals(4))
                                {
                                    foodItem.setTypeOfDish("VEGAN");
                                }
                            }


                        }


                        popularChoicesfoodItemList.add(foodItem);
                    }
                    popularrecyclerView.getAdapter().notifyDataSetChanged();
                    if(popularrecyclerView.getAdapter().getItemCount() == 0)
                    {
                        popularChoiceheader.setVisibility(View.GONE);
                        popularviewAll.setVisibility(View.GONE);
                    }
                    else {
                        popularChoiceheader.setVisibility(View.VISIBLE);
                        popularviewAll.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                popularshimmerLayout.stopShimmerAnimation();
                popularshimmerLayout.setVisibility(View.GONE);
                if(popularrecyclerView.getAdapter().getItemCount() == 0)
                {
                    popularChoiceheader.setVisibility(View.GONE);
                    popularviewAll.setVisibility(View.GONE);
                }
                else {
                    popularChoiceheader.setVisibility(View.VISIBLE);
                    popularviewAll.setVisibility(View.VISIBLE);
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    };


    /**
     get recently viewed items list(POST)
     **/
    public void getRecentlyViewedItems() throws JSONException {
        recentlyshimmerLayout.startShimmerAnimation();
        recentlyshimmerLayout.setVisibility(View.VISIBLE);

        String url = APIBaseURL.consumerHomePage;


        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        locationObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("location",locationObject);
        inputObject.put("customerId",  SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", 10);
        pageObject.put("index", 0);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(13);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        inputObject.put("typeOfDish", typeOfDishArray);
        inputObject.put("priceRangeFrom", 0);
        inputObject.put("priceRangeTo", 0);
        JSONArray ratingArray = new JSONArray();
        ratingArray.put(0);
        inputObject.put("rating", ratingArray);
        inputObject.put("healthCalMin", 0);
        inputObject.put("healthCalMax", 0);
        inputObject.put("healthProtienMax", 0);
        inputObject.put("healthProtienMin", 0);
        inputObject.put("healthCarbsMin", 0);
        inputObject.put("healthCarbsMax", 0);
        inputObject.put("healthFiberMin", 0);
        inputObject.put("healthFiberMax", 0);


        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                recentlyshimmerLayout.stopShimmerAnimation();
                recentlyshimmerLayout.setVisibility(View.GONE);


                try {

                    recentlyViewedfoodItemList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");

                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - "  + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setDeliveryTime(menuObject.optString("deliveryTime") + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();

                        if (menuObject.has("quickInfo")) {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if (quickInfoObject.has("nutrition")) {
                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
                            }
                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
                            foodItem.setFatCount(nutritionObject.optInt("fat"));
                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                        }
                        JSONObject chefQuickInfoObject = new JSONObject();

                        if (menuObject.has("chefQuickInfo")) {
                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                        }
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        JSONArray typeOfDishArray = new JSONArray();
                        if(quickInfoObject.has("typeOfDish"))
                        {
                            typeOfDishArray = quickInfoObject.getJSONArray("typeOfDish");

                            if(typeOfDishArray.length()!=0)
                            {
                                if(typeOfDishArray.get(0).equals(1))
                                {
                                    foodItem.setTypeOfDish("VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(2))
                                {
                                    foodItem.setTypeOfDish("NON-VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(3))
                                {
                                    foodItem.setTypeOfDish("EGGETARIAN");
                                }
                                else if(typeOfDishArray.get(0).equals(4))
                                {
                                    foodItem.setTypeOfDish("VEGAN");
                                }
                            }

                        }


                        recentlyViewedfoodItemList.add(foodItem);
                    }

                    recentlyrecyclerView.getAdapter().notifyDataSetChanged();
                    if(recentlyrecyclerView.getAdapter().getItemCount() == 0)
                    {
                        recentheader.setVisibility(View.GONE);
                        recentViewAllLay.setVisibility(View.GONE);
                    }
                    else {
                        recentheader.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                recentlyshimmerLayout.stopShimmerAnimation();
                recentlyshimmerLayout.setVisibility(View.GONE);
                if(recentlyrecyclerView.getAdapter().getItemCount() == 0)
                {
                    recentheader.setVisibility(View.GONE);
                    recentViewAllLay.setVisibility(View.GONE);
                }
                else {
                    recentheader.setVisibility(View.VISIBLE);
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    };


    /**
     Check CartCounts(GET)
     **/
    public static void getCartLists() {
        String url = APIBaseURL.getCartsList+ SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext());

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataArray = jsonObject.getJSONObject("data");

                    if(dataArray.length()!=0)
                    {
                        SaveSharedPreference.saveCartID(consumerHomeOnDemandFragments.getContext(),dataArray.optString("id"));
                    }


                    JSONArray cartDetailsArray = new JSONArray();
                    JSONArray menuDetailsArray = new JSONArray();
                    if(dataArray.has("cartDetails"))
                    {
                        cartDetailsArray = dataArray.getJSONArray("cartDetails");
                    }
                    List<Chef> chefList = new ArrayList<>();
                    for (int i=0;i < cartDetailsArray.length();i++)
                    {
                        JSONObject menuObject = cartDetailsArray.getJSONObject(i);
                        Chef chef = new Chef();
                        chef.setId(menuObject.optString("chefId"));
                        chef.setName(menuObject.optString("chefName"));

                        chefList.add(chef);

                        SaveSharedPreference.setList(chefList,consumerHomeOnDemandFragments.getContext());
                    }



                    if (jsonObject.optInt("count") == 0) {
                        cartLayout.setVisibility(View.GONE);
                    } else {
                        cartLayout.setVisibility(View.VISIBLE);
                    }
                    double sum = 0;
                    for (int i=0;i < cartDetailsArray.length();i++)
                    {
                        JSONObject dataObject = cartDetailsArray.getJSONObject(i);
                        JSONObject footerTotalObject = new JSONObject();
                        if(dataObject.has("footer"))
                        {
                            footerTotalObject = dataObject.getJSONObject("footer");
                        }

                        if(dataObject.has("menuDetails"))
                        {
                            menuDetailsArray = dataObject.getJSONArray("menuDetails");
                        }
                    }
                    try {
                        JSONObject orderReportSummaryObject = new JSONObject();

                        if(dataArray.has("orderReportSummary"))
                        {
                            orderReportSummaryObject = dataArray.getJSONObject("orderReportSummary");
                        }

                        cartTotalCountText.setText(String.valueOf(jsonObject.optString("count")));
                        totalAmount.setText("("+(String.valueOf(jsonObject.optString("count")))+") ITEMS Subtotal:");
                        cost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cartLayout.setVisibility(View.GONE);
                cartTotalCountText.setText(String.valueOf(0));
                SaveSharedPreference.saveCartID(consumerHomeOnDemandFragments.getContext(),"");

                try {
                    List<Chef> chefList = new ArrayList<>();
                    SaveSharedPreference.setList(chefList,consumerHomeOnDemandFragments.getContext());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        },consumerHomeOnDemandFragments.getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "cart_list_taq");
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getCartLists();
            if(categoryList.size()!=0 || minPriceSelectedFilter != 0 && maxPriceSelectedFilter != 0)
            {
                getHomePageFilteredAPI(2);
            }
            else
            {
                if(ConsumerHomeOnDemandFragments.header.getText().toString().equals("Top Offers")){
                    updateTopOFFersItems();
                }
                else
                {
                    updateOtherThanTopOFfers(ConsumerHomeOnDemandFragments.getSelectedCategoryID,ConsumerHomeOnDemandFragments.header.getText().toString());
                }

            }
            updatePopularChoices();
            updateRecentlyViewedItems();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /**
     Sort & Filter Page Based on user selecting filters Results({POST})
     **/
    public static void getHomePageFilteredAPI(int from) throws JSONException {

        if(from == 1)
        {
            topOfferrecyclerView.setVisibility(View.GONE);
            offersshimmerLayout.startShimmerAnimation();
            offersshimmerLayout.setVisibility(View.VISIBLE);
        }



        String url = APIBaseURL.consumerHomePage;
        header.setText("Top Offers");




        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        locationObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("location",locationObject);
        inputObject.put("customerId",  SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", 10);
        pageObject.put("index", 0);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);



        JSONArray sortFilterArray = new JSONArray();
        for (int i=0;i < categoryList.size();i++)
        {
            if(categoryList.get(i).getNamewithTitle().contains("Sort"))
            {
                sortFilterArray.put(Integer.parseInt(categoryList.get(i).getId()));

            }

        }
        inputObject.put("sortFilter", sortFilterArray);




        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(1);
        inputObject.put("quickFilter",quickFilterArray);

            JSONArray cuicineFilterrArray = new JSONArray();
            for (int i=0;i < categoryList.size();i++)
            {
                if(categoryList.get(i).getNamewithTitle().contains("Cuisine"))
                {
                    if(categoryList.get(i).getSelected())
                    {
                        cuicineFilterrArray.put(Integer.parseInt(categoryList.get(i).getId()));
                    }


                }

            }
            inputObject.put("dishCuisineFilter", cuicineFilterrArray);
            JSONArray dishCategoryFilterArray = new JSONArray();

            for (int i=0;i < categoryList.size();i++)
            {

                if(categoryList.get(i).getNamewithTitle().contains("Category"))
                {
                    if(categoryList.get(i).getSelected())
                    {
                        dishCategoryFilterArray.put(Integer.parseInt(categoryList.get(i).getId()));
                    }

                }

            }
            inputObject.put("dishCategoryFilter", dishCategoryFilterArray);

            JSONArray typeOfDishArray = new JSONArray();
            for (int i=0;i < categoryList.size();i++)
            {
                if(categoryList.get(i).getNamewithTitle().contains("Meal"))
                {
                    if(categoryList.get(i).getSelected())
                    {
                        typeOfDishArray.put(Integer.parseInt(categoryList.get(i).getId()));
                    }



                }

            }
            inputObject.put("typeOfDish", typeOfDishArray);


        inputObject.put("priceRangeFrom", minPriceSelectedFilter);
        inputObject.put("priceRangeTo", maxPriceSelectedFilter);


            JSONArray ratingArray = new JSONArray();
            for (int i=0;i < categoryList.size();i++)
            {
                if(categoryList.get(i).getNamewithTitle().contains("Rating"))
                {
                    if(categoryList.get(i).getSelected())
                    {
                        ratingArray.put(Integer.parseInt(categoryList.get(i).getId()));
                    }


                }

            }
            inputObject.put("ratingArray", ratingArray);


        inputObject.put("healthCalMin", 0);
        inputObject.put("healthCalMax", 0);
        inputObject.put("healthProtienMax", 0);
        inputObject.put("healthProtienMin", 0);
        inputObject.put("healthCarbsMin", 0);
        inputObject.put("healthCarbsMax", 0);
        inputObject.put("healthFiberMin", 0);
        inputObject.put("healthFiberMax", 0);



        noRecordsFound.setVisibility(View.GONE);



        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                offersshimmerLayout.stopShimmerAnimation();
                offersshimmerLayout.setVisibility(View.GONE);
                topOfferrecyclerView.setVisibility(View.VISIBLE);
                noRecordsFound.setVisibility(View.GONE);

                try {

                    topOffersfoodItemList .clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - "  + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setDeliveryTime(menuObject.optString("deliveryTime") + " mins");
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();

                        if (menuObject.has("quickInfo")) {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if (quickInfoObject.has("nutrition")) {
                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
                            }

                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
                            foodItem.setFatCount(nutritionObject.optInt("fat"));
                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));

                        }
                        JSONObject chefQuickInfoObject = new JSONObject();

                        if (menuObject.has("chefQuickInfo")) {
                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                        }
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        JSONArray typeOfDishArray = new JSONArray();
                        if(quickInfoObject.has("typeOfDish"))
                        {
                            typeOfDishArray = quickInfoObject.getJSONArray("typeOfDish");
                            if(typeOfDishArray.length()!=0)
                            {
                                if(typeOfDishArray.get(0).equals(1))
                                {
                                    foodItem.setTypeOfDish("VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(2))
                                {
                                    foodItem.setTypeOfDish("NON-VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(3))
                                {
                                    foodItem.setTypeOfDish("EGGETARIAN");
                                }
                                else if(typeOfDishArray.get(0).equals(4))
                                {
                                    foodItem.setTypeOfDish("VEGAN");
                                }
                            }


                        }


                        topOffersfoodItemList.add(foodItem);
                    }

                    topOfferrecyclerView.getAdapter().notifyDataSetChanged();

                    filtersrecyclerView.setAdapter(new FilteredItemsAdapter(consumerHomeOnDemandFragments.getContext(),categoryList));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                offersshimmerLayout.stopShimmerAnimation();
                offersshimmerLayout.setVisibility(View.GONE);
                noRecordsFound.setVisibility(View.VISIBLE);
                filtersrecyclerView.setAdapter(new FilteredItemsAdapter(consumerHomeOnDemandFragments.getContext(),categoryList));
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    }


    /**
     Notify Dataset Changed For Popular Choices({POST})
     **/
    public static void updatePopularChoices() throws JSONException {

        String url = APIBaseURL.consumerHomePage;
        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        locationObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("location",locationObject);
        inputObject.put("customerId",  SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", 10);
        pageObject.put("index", 0);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(0);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        JSONArray sortFilterArray = new JSONArray();
        typeOfDishArray.put(0);
        sortFilterArray.put(4);
        inputObject.put("typeOfDish", typeOfDishArray);
        inputObject.put("sortFilter", sortFilterArray);
        inputObject.put("priceRangeFrom", 0);
        inputObject.put("priceRangeTo", 0);
        JSONArray ratingArray = new JSONArray();
        ratingArray.put(0);
        inputObject.put("rating", ratingArray);
        inputObject.put("healthCalMin", 0);
        inputObject.put("healthCalMax", 0);
        inputObject.put("healthProtienMax", 0);
        inputObject.put("healthProtienMin", 0);
        inputObject.put("healthCarbsMin", 0);
        inputObject.put("healthCarbsMax", 0);
        inputObject.put("healthFiberMin", 0);
        inputObject.put("healthFiberMax", 0);



        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    popularChoicesfoodItemList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - "  + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setDeliveryTime(menuObject.optString("deliveryTime") + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");
                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();



                        if (menuObject.has("quickInfo")) {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if (quickInfoObject.has("nutrition")) {
                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
                            }
                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
                            foodItem.setFatCount(nutritionObject.optInt("fat"));
                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                        }
                        JSONObject chefQuickInfoObject = new JSONObject();

                        if (menuObject.has("chefQuickInfo")) {
                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                        }
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        JSONArray typeOfDishArray = new JSONArray();
                        if(quickInfoObject.has("typeOfDish"))
                        {
                            typeOfDishArray = quickInfoObject.getJSONArray("typeOfDish");
                            if(typeOfDishArray.length()!=0)
                            {
                                if(typeOfDishArray.get(0).equals(1))
                                {
                                    foodItem.setTypeOfDish("VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(2))
                                {
                                    foodItem.setTypeOfDish("NON-VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(3))
                                {
                                    foodItem.setTypeOfDish("EGGETARIAN");
                                }
                                else if(typeOfDishArray.get(0).equals(4))
                                {
                                    foodItem.setTypeOfDish("VEGAN");
                                }
                            }


                        }


                        popularChoicesfoodItemList.add(foodItem);
                    }
                    popularrecyclerView.getAdapter().notifyDataSetChanged();
                    if(popularrecyclerView.getAdapter().getItemCount() == 0)
                    {
                        popularChoiceheader.setVisibility(View.GONE);
                        popularviewAll.setVisibility(View.GONE);
                        popularrecyclerView.setVisibility(View.GONE);
                    }
                    else {
                        popularChoiceheader.setVisibility(View.VISIBLE);
                        popularrecyclerView.setVisibility(View.VISIBLE);
                        popularviewAll.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    popularrecyclerView.setVisibility(View.GONE);
                    popularChoiceheader.setVisibility(View.GONE);
                    popularviewAll.setVisibility(View.GONE);
                }
                catch (Exception e)
                {
                    popularrecyclerView.setVisibility(View.GONE);
                    popularChoiceheader.setVisibility(View.GONE);
                    popularviewAll.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");

    }

    /**
     Notify Dataset Changed For Top Offers({POST})
     **/
    public static void  updateTopOFFersItems() throws JSONException {

        String url = APIBaseURL.consumerHomePage;

        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        locationObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("location",locationObject);
        inputObject.put("customerId",  SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", 10);
        pageObject.put("index", 0);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(1);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        inputObject.put("typeOfDish", typeOfDishArray);
        inputObject.put("priceRangeFrom", 0);
        inputObject.put("priceRangeTo", 0);
        JSONArray ratingArray = new JSONArray();
        ratingArray.put(0);
        inputObject.put("rating", ratingArray);
        inputObject.put("healthCalMin", 0);
        inputObject.put("healthCalMax", 0);
        inputObject.put("healthProtienMax", 0);
        inputObject.put("healthProtienMin", 0);
        inputObject.put("healthCarbsMin", 0);
        inputObject.put("healthCarbsMax", 0);
        inputObject.put("healthFiberMin", 0);
        inputObject.put("healthFiberMax", 0);



        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                try {

                    topOffersfoodItemList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - "  + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setDeliveryTime(menuObject.optString("deliveryTime") + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");

                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();

                        if (menuObject.has("quickInfo")) {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if (quickInfoObject.has("nutrition")) {
                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
                            }
                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
                            foodItem.setFatCount(nutritionObject.optInt("fat"));
                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                        }
                        JSONObject chefQuickInfoObject = new JSONObject();

                        if (menuObject.has("chefQuickInfo")) {
                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                        }
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        JSONArray typeOfDishArray = new JSONArray();
                        if(quickInfoObject.has("typeOfDish"))
                        {
                            typeOfDishArray = quickInfoObject.getJSONArray("typeOfDish");
                            if(typeOfDishArray.length()!=0)
                            {
                                if(typeOfDishArray.get(0).equals(1))
                                {
                                    foodItem.setTypeOfDish("VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(2))
                                {
                                    foodItem.setTypeOfDish("NON-VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(3))
                                {
                                    foodItem.setTypeOfDish("EGGETARIAN");
                                }
                                else if(typeOfDishArray.get(0).equals(4))
                                {
                                    foodItem.setTypeOfDish("VEGAN");
                                }
                            }


                        }


                        topOffersfoodItemList.add(foodItem);
                    }
                    topOfferrecyclerView.getAdapter().notifyDataSetChanged();
                    if(topOfferrecyclerView.getAdapter().getItemCount() == 0)
                    {
                        header.setVisibility(View.GONE);
                        offersViewAll.setVisibility(View.GONE);
                        topOfferrecyclerView.setVisibility(View.GONE);
                    }
                    else
                    {
                        header.setVisibility(View.VISIBLE);
                        offersViewAll.setVisibility(View.VISIBLE);
                        topOfferrecyclerView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    topOfferrecyclerView.setVisibility(View.GONE);
                    header.setVisibility(View.VISIBLE);
                    offersViewAll.setVisibility(View.VISIBLE);
                }
                catch (Exception e)
                {
                    topOfferrecyclerView.setVisibility(View.GONE);
                    header.setVisibility(View.VISIBLE);
                    offersViewAll.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }


            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    }

    /**
     Notify Dataset Changed For Recently Viewed Items({POST})
     **/
    public static void updateRecentlyViewedItems() throws JSONException {

        String url = APIBaseURL.consumerHomePage;


        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(consumerHomeOnDemandFragments.getContext())));
        locationObject.put("longitude",Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(consumerHomeOnDemandFragments.getContext())));
        inputObject.put("location",locationObject);
        inputObject.put("customerId",  SaveSharedPreference.getLoggedInUserEmail(consumerHomeOnDemandFragments.getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", 10);
        pageObject.put("index", 0);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(13);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        inputObject.put("typeOfDish", typeOfDishArray);
        inputObject.put("priceRangeFrom", 0);
        inputObject.put("priceRangeTo", 0);
        JSONArray ratingArray = new JSONArray();
        ratingArray.put(0);
        inputObject.put("rating", ratingArray);
        inputObject.put("healthCalMin", 0);
        inputObject.put("healthCalMax", 0);
        inputObject.put("healthProtienMax", 0);
        inputObject.put("healthProtienMin", 0);
        inputObject.put("healthCarbsMin", 0);
        inputObject.put("healthCarbsMax", 0);
        inputObject.put("healthFiberMin", 0);
        inputObject.put("healthFiberMax", 0);



        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                try {

                    recentlyViewedfoodItemList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - " + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setDeliveryTime(menuObject.optString("deliveryTime") + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");

                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();

                        if (menuObject.has("quickInfo")) {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if (quickInfoObject.has("nutrition")) {
                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
                            }
                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
                            foodItem.setFatCount(nutritionObject.optInt("fat"));
                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                        }
                        JSONObject chefQuickInfoObject = new JSONObject();

                        if (menuObject.has("chefQuickInfo")) {
                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                        }
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        JSONArray typeOfDishArray = new JSONArray();
                        if(quickInfoObject.has("typeOfDish"))
                        {
                            typeOfDishArray = quickInfoObject.getJSONArray("typeOfDish");

                            if(typeOfDishArray.length()!=0)
                            {
                                if(typeOfDishArray.get(0).equals(1))
                                {
                                    foodItem.setTypeOfDish("VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(2))
                                {
                                    foodItem.setTypeOfDish("NON-VEG");
                                }
                                else if(typeOfDishArray.get(0).equals(3))
                                {
                                    foodItem.setTypeOfDish("EGGETARIAN");
                                }
                                else if(typeOfDishArray.get(0).equals(4))
                                {
                                    foodItem.setTypeOfDish("VEGAN");
                                }
                            }


                        }


                        recentlyViewedfoodItemList.add(foodItem);
                    }

                    recentlyrecyclerView.getAdapter().notifyDataSetChanged();
                    if(recentlyrecyclerView.getAdapter().getItemCount() == 0)
                    {
                        recentheader.setVisibility(View.GONE);
                        recentlyrecyclerView.setVisibility(View.GONE);
                        recentViewAllLay.setVisibility(View.GONE);
                    }
                    else {
                        recentheader.setVisibility(View.VISIBLE);
                        recentlyrecyclerView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    recentlyrecyclerView.setVisibility(View.GONE);
                    recentlyrecyclerView.setVisibility(View.GONE);
                    recentheader.setVisibility(View.GONE);
                    recentViewAllLay.setVisibility(View.GONE);
                }
                catch (Exception e)
                {
                    recentlyrecyclerView.setVisibility(View.GONE);
                    recentheader.setVisibility(View.GONE);
                    recentViewAllLay.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    };
}
