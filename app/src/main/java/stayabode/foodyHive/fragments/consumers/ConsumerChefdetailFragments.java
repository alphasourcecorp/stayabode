package stayabode.foodyHive.fragments.consumers;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.InfoListsAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.Reviews;
import stayabode.foodyHive.models.ReviewsHeader;
import stayabode.foodyHive.models.TodaysChefMenu;
import stayabode.foodyHive.models.TodaysChefsPopularMenu;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumerChefdetailFragments extends Fragment {

    RecyclerView recyclerView;
    TextView title;
    TextView description;
    TextView emptyMenuText;
    ProgressBar progressBar;
    ProgressBar itemsLoader;
    NestedScrollView nestedScrollViewAll;

    String sTitle;
    int size = 20;
    int menuIndex = 0;
    int popularMenuIndex = 0;
    int reviewIndex = 0;

    List<Object> objectList = new ArrayList<>();
    List<FoodItem> menuFoodItemList = new ArrayList<>();
    List<FoodItem> popularFoodItemList = new ArrayList<>();
    List<Reviews> reviewsList = new ArrayList<>();
    ReviewsHeader reviewsheader = new ReviewsHeader();
    TodaysChefMenu todaysChefMenu = new TodaysChefMenu();
    TodaysChefsPopularMenu todaysChefsPopularMenu = new TodaysChefsPopularMenu();


    Typeface poppinsMedium;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface poppinsLight;
    Typeface robotoMedium;
    Typeface RobotoRegular;
    Typeface RobotoBold;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_chef_detail_tab, container, false);

        poppinsMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsSemiBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Light.ttf");
        robotoMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
        RobotoBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        RobotoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");

        recyclerView = view.findViewById(R.id.recyclerView);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        progressBar = view.findViewById(R.id.progressBar);
        emptyMenuText = view.findViewById(R.id.emptyMenuText);
        nestedScrollViewAll = view.findViewById(R.id.nestedScrollViewAll);
        itemsLoader = view.findViewById(R.id.itemsLoader);

        progressBar.setVisibility(View.VISIBLE);
        itemsLoader.setVisibility(View.GONE);
        sTitle = getArguments().getString("title");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new InfoListsAdapter(getContext(), objectList, null, poppinsSemiBold, poppinsLight, poppinsMedium, poppinsBold, RobotoBold, RobotoRegular));


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (sTitle.equalsIgnoreCase("about")) {
                getChefDetailPage();
                title.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                emptyMenuText.setVisibility(View.GONE);
            }
            if (sTitle.equalsIgnoreCase("Reviews")) {
                getChefDetailPage();
                //perform pagination

                nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                            reviewIndex++;
                            getChefDetailPage();

                        }
                    }
                });
            }
            if (sTitle.equalsIgnoreCase("Menu")) {
                getChefsTodayMenus();
                //perform pagination

                nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                            menuIndex++;
                            getChefsTodayMenus();

                        }
                    }
                });
            }
            if (sTitle.equalsIgnoreCase("Popular")) {
                getChefsPopularMenus();
                //perform pagination

                nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                            popularMenuIndex++;
                            getChefsPopularMenus();

                        }
                    }
                });
            }
            recyclerView.getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     get details of the chef(GET) and the review list
     **/
    public void getChefDetailPage() {
        String url = APIBaseURL.getCookedChefProfile + "" + getActivity().getIntent().getStringExtra("chefId") + "&" + reviewIndex + "&" + size;
        progressBar.setVisibility(View.GONE);
        if (reviewIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);



                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray consumerReviewsArray = new JSONArray();
                    title.setText("About " + dataObject.optString("chefName"));

                    if (dataObject.has("consumerReviews")) {
                        consumerReviewsArray = dataObject.getJSONArray("consumerReviews");
                    }

                    description.setText(dataObject.optString("aboutYou"));


                    if (consumerReviewsArray.length() != 0) {
                        objectList.clear();
                        if (reviewIndex == 0) {
                            reviewsList.clear();
                        }
                        for (int i = 0; i < consumerReviewsArray.length(); i++) {
                            JSONObject consumerReviewsObject = consumerReviewsArray.getJSONObject(i);
                            Reviews reviews = new Reviews();
                            reviews.setDate(consumerReviewsObject.optString("createdDate"));
                            reviews.setUserName(consumerReviewsObject.optString("consumerName"));
                            reviews.setImage(consumerReviewsObject.optString("consumerImage"));
                            reviews.setReviewsDescription(consumerReviewsObject.optString("consumerComments"));
                            reviews.setRatingCount(consumerReviewsObject.optString("consumerRating"));
                            reviewsList.add(reviews);
                        }
                        reviewsheader.setReviewsList(reviewsList);
                        objectList.add(reviewsheader);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    } else {
                        if (consumerReviewsArray.length() == 0 && reviewIndex == 0) {
                            emptyMenuText.setText("No reviews yet!");
                            if (sTitle.equalsIgnoreCase("about")) {
                                emptyMenuText.setVisibility(View.GONE);
                            }else
                            emptyMenuText.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);

                Toast.makeText(getContext(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_detail_taq");
    }


    /**
     get today's food menu of chef(GET)
     **/
    public void getChefsTodayMenus() {

        String url = APIBaseURL.getCookedChefTodaysMenu + "" + getActivity().getIntent().getStringExtra("chefId") + "?userId=" + SaveSharedPreference.getLoggedInUserEmail(getContext()) + "&pagesize=" + size + "&pageindex=" + menuIndex;

        if (menuIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray dishesArray = new JSONArray();

                    if (dataObject.has("dishes")) {
                        dishesArray = dataObject.getJSONArray("dishes");
                    }

                    if (dishesArray.length() != 0) {

                        objectList.clear();
                        if (menuIndex == 0) {
                            menuFoodItemList.clear();
                        }
                        for (int i = 0; i < dishesArray.length(); i++) {
                            JSONObject menuObject = dishesArray.getJSONObject(i);

                            FoodItem foodItem = new FoodItem();
                            if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                                foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                            }
                            foodItem.setFoodName(menuObject.optString("name"));
                            foodItem.setFoodId(menuObject.optString("dishId"));
                            foodItem.setShortDescription(menuObject.optString("shortDescription"));
                            foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                            foodItem.setAvailQty(menuObject.optInt("availableCount"));
                            foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
                            foodItem.setTime(menuObject.optString("deliveryTime") + "-" + menuObject.optString("deliveryExpectedTime") + " mins");
                            foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                            foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");



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

                            foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                            foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                            foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                            foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                            foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                            foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                            foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                            foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                            foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));
                            menuFoodItemList.add(foodItem);
                            todaysChefMenu.setFoodItemList(menuFoodItemList);
                        }
                        objectList.add(todaysChefMenu);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    } else if (dishesArray.length() == 0 && menuIndex == 0) {
                        emptyMenuText.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);


                emptyMenuText.setVisibility(View.VISIBLE);
            }
        }, getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_menu_taq");
    }


    /**
     get poplar food menu of chef(GET)
     **/
    public void getChefsPopularMenus() {

        String url = APIBaseURL.getChefsPopularMenus + getActivity().getIntent().getStringExtra("chefId") + "?userId=" + SaveSharedPreference.getLoggedInUserEmail(getContext()) + "&pagesize=" + size + "&pageindex=" + popularMenuIndex;

        if (popularMenuIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray dishesArray = dataObject.getJSONArray("dishes");
                    if (dishesArray.length() != 0) {
                        objectList.clear();
                        if (popularMenuIndex == 0) {
                            popularFoodItemList.clear();
                        }
                        for (int i = 0; i < dishesArray.length(); i++) {


                            JSONObject menuObject = dishesArray.getJSONObject(i);

                            FoodItem foodItem = new FoodItem();
                            if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                                foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                            }
                            foodItem.setFoodName(menuObject.optString("name"));
                            foodItem.setFoodId(menuObject.optString("dishId"));
                            foodItem.setShortDescription(menuObject.optString("shortDescription"));
                            foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                            foodItem.setAvailQty(menuObject.optInt("availableCount"));
                            foodItem.setTime(menuObject.optString("deliveryTime") + "-" + menuObject.optString("deliveryExpectedTime") + " mins");
                            foodItem.setPrice(menuObject.optString("mealPrice"));
                            foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                            foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
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

                            foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                            foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                            foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                            foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                            foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                            foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                            foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                            foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                            foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));
                            popularFoodItemList.add(foodItem);
                            todaysChefsPopularMenu.setFoodItemLists(popularFoodItemList);
                        }
                        objectList.add(todaysChefsPopularMenu);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    } else if (dishesArray.length() == 0 && popularMenuIndex == 0) {
                        emptyMenuText.setText("No popular menu available.");
                        emptyMenuText.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                emptyMenuText.setText("No popular menu available.");
                emptyMenuText.setVisibility(View.VISIBLE);

            }
        }, getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_popular_menu_taq");
    }

}

