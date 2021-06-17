package stayabode.foodyHive.fragments.consumers;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.MenuSubBottomItemsVerticalAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.TopOffers;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumerAllItemsFragment extends Fragment {
    Typeface poppinsMedium;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface RobotoRegular;
    Typeface RobotoBold;
    Typeface poppinsLight;

    TextView noMoreItemText;

    ProgressBar progressBar;
    ProgressBar itemsLoader;

    TopOffers topOffers = new TopOffers();
    List<TopOffers> topOffersList = new ArrayList<>();
    List<FoodItem> breakFastFoodItemList = new ArrayList<>();
    List<FoodItem> lunchFoodItemList = new ArrayList<>();
    List<FoodItem> snackFoodItemList = new ArrayList<>();
    List<FoodItem> dinnerFoodItemList = new ArrayList<>();
    List<FoodItem> otherFoodItemList = new ArrayList<>();

    JSONArray breakFastArray = new JSONArray();
    JSONArray LunchArray = new JSONArray();
    JSONArray dinnerArray = new JSONArray();
    JSONArray snackArray = new JSONArray();
    JSONArray othersArray = new JSONArray();


    RecyclerView recyclerView;
    TextView title;
    String sTitle;
    NestedScrollView nestedScrollViewAll;
    int breakfastIndex = 0;
    int lunchIndex = 0;
    int snacksIndex = 0;
    int dinnerIndex = 0;
    int othersIndex = 0;
    int size = 20;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_tab, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewAll);
        title = view.findViewById(R.id.header);
        progressBar = view.findViewById(R.id.progressBar);
        itemsLoader = view.findViewById(R.id.itemsLoader);
        nestedScrollViewAll = view.findViewById(R.id.nestedScrollViewAll);
        noMoreItemText = view.findViewById(R.id.noMoreItemText);

        poppinsSemiBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");
        RobotoBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        poppinsMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Medium.ttf");
        RobotoRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");

        sTitle = getArguments().getString("title");
        progressBar.setVisibility(View.VISIBLE);
        itemsLoader.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        topOffersList = new ArrayList<>();
        recyclerView.setAdapter(new MenuSubBottomItemsVerticalAdapter(getContext(), topOffersList, poppinsBold, null, poppinsLight, poppinsSemiBold, RobotoRegular, RobotoBold));

        return view;

    }

    /**
     get breakfast list (POST)
     **/
    private void getBreakFast() throws JSONException {
        String url = APIBaseURL.consumerHomePage;

        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(getContext())));
        locationObject.put("longitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(getContext())));
        inputObject.put("location", locationObject);
        inputObject.put("customerId", SaveSharedPreference.getLoggedInUserEmail(getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", size);
        pageObject.put("index", breakfastIndex);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(0);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        breakFastArray = new JSONArray();
        LunchArray = new JSONArray();
        dinnerArray = new JSONArray();
        snackArray = new JSONArray();
        othersArray = new JSONArray();
        breakFastArray.put(1);
        LunchArray.put(2);
        dinnerArray.put(3);
        snackArray.put(4);
        othersArray.put(5);
        inputObject.put("dishCategoryFilter", breakFastArray);
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

        Log.d("HomeInputObjectBF", inputObject.toString());

        if (breakfastIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }
        Log.v("BreakFastURL", url);
        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("HomePageResponse", response.toString());
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                try {
                    if (breakfastIndex == 0) {
                        topOffers = new TopOffers();
                        breakFastFoodItemList.clear();
                    }
                    topOffersList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + "-" + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
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
                        Log.v("ratingHere", String.valueOf(foodItem.getChefratingAverage()));
                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        breakFastFoodItemList.add(foodItem);

                    }
                    topOffers.setFoodItemList(breakFastFoodItemList);
                    topOffersList.add(topOffers);
                    recyclerView.getAdapter().notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);

                if (error instanceof ServerError) {
                    if(topOffersList.size()>20){
                        noMoreItemText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    }


    /**
     get lunch list (POST)
     **/

    private void getLunch() throws JSONException {
        String url = APIBaseURL.consumerHomePage;
        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(getContext())));
        locationObject.put("longitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(getContext())));
        inputObject.put("location", locationObject);
        inputObject.put("customerId", SaveSharedPreference.getLoggedInUserEmail(getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", size);
        pageObject.put("index", lunchIndex);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(0);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        breakFastArray = new JSONArray();
        LunchArray = new JSONArray();
        dinnerArray = new JSONArray();
        snackArray = new JSONArray();
        othersArray = new JSONArray();
        breakFastArray.put(2);
        LunchArray.put(2);
        dinnerArray.put(3);
        snackArray.put(4);
        othersArray.put(5);
        inputObject.put("dishCategoryFilter", breakFastArray);
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


        if (lunchIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }
        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                try {
                    if (lunchIndex == 0) {
                        topOffers = new TopOffers();
                        lunchFoodItemList.clear();
                    }
                    topOffersList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - " + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
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

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        lunchFoodItemList.add(foodItem);

                    }
                    topOffers.setFoodItemList(lunchFoodItemList);
                    topOffersList.add(topOffers);

                    recyclerView.getAdapter().notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                if (error instanceof ServerError) {
                    if(topOffersList.size()>20){
                        noMoreItemText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    }


    /**
     get snacks list (POST)
     **/
    private void getSnacks() throws JSONException {
        String url = APIBaseURL.consumerHomePage;


        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(getContext())));
        locationObject.put("longitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(getContext())));
        inputObject.put("location", locationObject);
        inputObject.put("customerId", SaveSharedPreference.getLoggedInUserEmail(getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", size);
        pageObject.put("index", snacksIndex);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(0);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        breakFastArray = new JSONArray();
        LunchArray = new JSONArray();
        dinnerArray = new JSONArray();
        snackArray = new JSONArray();
        othersArray = new JSONArray();
        breakFastArray.put(4);
        LunchArray.put(2);
        dinnerArray.put(3);
        snackArray.put(4);
        othersArray.put(5);
        inputObject.put("dishCategoryFilter", breakFastArray);
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



        if (snacksIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }
        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                try {
                    if (snacksIndex == 0) {
                        topOffers = new TopOffers();
                        snackFoodItemList.clear();
                    }
                    topOffersList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - " + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
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

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        snackFoodItemList.add(foodItem);

                    }
                    topOffers.setFoodItemList(snackFoodItemList);
                    topOffersList.add(topOffers);
                    recyclerView.getAdapter().notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                if (error instanceof ServerError) {
                    if(topOffersList.size()>20){
                        noMoreItemText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    }


    /**
     get dinners list (POST)
     **/
    private void getDinner() throws JSONException {
        String url = APIBaseURL.consumerHomePage;


        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(getContext())));
        locationObject.put("longitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(getContext())));
        inputObject.put("location", locationObject);
        inputObject.put("customerId", SaveSharedPreference.getLoggedInUserEmail(getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", size);
        pageObject.put("index", dinnerIndex);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(0);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        breakFastArray = new JSONArray();
        LunchArray = new JSONArray();
        dinnerArray = new JSONArray();
        snackArray = new JSONArray();
        othersArray = new JSONArray();
        breakFastArray.put(3);
        LunchArray.put(2);
        dinnerArray.put(3);
        snackArray.put(4);
        othersArray.put(5);
        inputObject.put("dishCategoryFilter", breakFastArray);
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


        if (dinnerIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }
        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                try {
                    if(dinnerIndex==0){
                        topOffers=new TopOffers();
                        dinnerFoodItemList.clear();
                    }
                    topOffersList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - " + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
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

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        dinnerFoodItemList.add(foodItem);

                    }
                    topOffers.setFoodItemList(dinnerFoodItemList);
                    topOffersList.add(topOffers);
                    recyclerView.getAdapter().notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                if (error instanceof ServerError) {
                    if(topOffersList.size()>20){
                        noMoreItemText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    }

    /**
     get other food item list(POST)
     **/
    private void getOther() throws JSONException {
        String url = APIBaseURL.consumerHomePage;


        JSONObject inputObject = new JSONObject();
        inputObject.put("location", "Kammanahalli");
        JSONObject locationObject = new JSONObject();
        locationObject.put("latitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLatitude(getContext())));
        locationObject.put("longitude", Double.valueOf(SaveSharedPreference.getLoggedInUserLongitude(getContext())));
        inputObject.put("location", locationObject);
        inputObject.put("customerId", SaveSharedPreference.getLoggedInUserEmail(getContext()));
        JSONObject pageObject = new JSONObject();
        pageObject.put("size", size);
        pageObject.put("index", othersIndex);
        inputObject.put("page", pageObject);
        inputObject.put("chefMenuType", 0);
        JSONArray quickFilterArray = new JSONArray();
        quickFilterArray.put(0);
        inputObject.put("quickFilter", quickFilterArray);
        JSONArray typeOfDishArray = new JSONArray();
        typeOfDishArray.put(0);
        breakFastArray = new JSONArray();
        LunchArray = new JSONArray();
        dinnerArray = new JSONArray();
        snackArray = new JSONArray();
        othersArray = new JSONArray();
        breakFastArray.put(5);
        LunchArray.put(2);
        dinnerArray.put(3);
        snackArray.put(4);
        othersArray.put(5);
        inputObject.put("dishCategoryFilter", breakFastArray);
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


        if (othersIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }
        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                try {
                    if(othersIndex==0){
                        topOffers=new TopOffers();
                        otherFoodItemList.clear();
                    }
                    topOffersList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject menuObject = response.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
                        foodItem.setTime(totalTimeForPreparation + " - " + totalTimeForPreparationPlusFiveAdded + " mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
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

                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
                        otherFoodItemList.add(foodItem);

                    }
                    topOffers.setFoodItemList(otherFoodItemList);
                    topOffersList.add(topOffers);
                    recyclerView.getAdapter().notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                if (error instanceof ServerError) {
                    if(topOffersList.size()>20){
                        noMoreItemText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (sTitle.equalsIgnoreCase("Breakfast")) {
                title.setText(sTitle);


                try {

                    breakfastIndex=0;
                    getBreakFast();
                    //perform pagination
                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                                breakfastIndex++;
                                try {
                                    getBreakFast();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (sTitle.equalsIgnoreCase("lunch")) {
                title.setText(sTitle);
                try {
                    lunchIndex=0;
                    getLunch();
                    //perform pagination

                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                                lunchIndex++;
                                try {
                                    getLunch();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (sTitle.equalsIgnoreCase("Snacks")) {
                title.setText(sTitle);
                try {
                    snacksIndex=0;
                    getSnacks();
                    //perform pagination

                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                                snacksIndex++;
                                try {
                                    getSnacks();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (sTitle.equalsIgnoreCase("Dinner")) {
                title.setText(sTitle);
                try {
                    dinnerIndex=0;
                    getDinner();
                    //perform pagination

                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                                dinnerIndex++;
                                try {
                                    getDinner();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (sTitle.equalsIgnoreCase("other")) {
                title.setText(sTitle);
                try {
                    othersIndex=0;
                    getOther();
                    //perform pagination

                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                                othersIndex++;
                                try {
                                    getOther();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerView.getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
