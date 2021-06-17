package stayabode.foodyHive.fragments.consumers;

import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;

import stayabode.foodyHive.adapters.consumers.MenuSubBottomItemsVerticalAdapterAdmin;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.RequestBtoB;
import stayabode.foodyHive.models.RequestBtoBItem;
import stayabode.foodyHive.models.TopOffers;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.SqlDateTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

public class ConsumerAllItemsFragmentAdmin extends Fragment {
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

    RequestBtoB topOffersnew = new RequestBtoB();
    List<RequestBtoB> topOffersListnew = new ArrayList<>();
    List<RequestBtoBItem> breakFastFoodItemListnew = new ArrayList<>();


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
    String spinnerselection;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_tab_admin, container, false);
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
        //spinnerselection = getArguments().getString("spinnerselection");
        progressBar.setVisibility(View.VISIBLE);
        itemsLoader.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        topOffersList = new ArrayList<>();
        topOffersListnew = new ArrayList<>();

        recyclerView.setAdapter(new MenuSubBottomItemsVerticalAdapterAdmin(getContext(), topOffersListnew, poppinsBold, null, poppinsLight, poppinsSemiBold, RobotoRegular, RobotoBold, Globaluse.spinnerselection));

        return view;

    }

    /**
     get breakfast list (POST)
     **/
    private void getBreakFast() throws JSONException {



            String URL = APIBaseURL.BASEURLLINK_B2B_Subscription_list;

            JSONObject inputObject = new JSONObject();
            inputObject.put("companyId", "CMPNY31032021055750");
            inputObject.put("status", "All");
            inputObject.put("search", "");
            inputObject.put("startDate", "2020-04-11T10:21:54.996Z");
            inputObject.put("endDate", "2020-04-11T10:21:54.996Z");
            inputObject.put("pagesize", 20);
            inputObject.put("pageindex", 0);

        Log.d("HomeInputObjectBF", inputObject.toString());

        if (breakfastIndex > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    // jsonObject.getString("isSuccess");

                   // Gson gson = new Gson().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
                    SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(java.sql.Date.class, sqlAdapter)
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    try {

                        if(jsonObject.getString("isSuccess").equals("true")) {
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            JSONArray requestsList=dataObject.getJSONArray("requestsList");

                            if (breakfastIndex == 0) {
                                topOffersnew = new RequestBtoB();
                                breakFastFoodItemListnew.clear();
                            }
                            topOffersList.clear();

                            for(int i=0;i<requestsList.length();i++)
                            {
                                RequestBtoBItem foodItem = new RequestBtoBItem();
                                Log.v("BreakFastURL", String.valueOf(requestsList.get(i)));
                                foodItem = gson.fromJson(String.valueOf(requestsList.get(i)), RequestBtoBItem .class);
                                Log.v("BreakFastURL222", String.valueOf(foodItem));
                                breakFastFoodItemListnew.add(foodItem) ;
                            }

                            topOffersnew.setFoodItemList(breakFastFoodItemListnew);
                            topOffersListnew.add(topOffersnew);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                  //  recyclerView.setAdapter(new MenuSubBottomItemsVerticalAdapterAdmin(getContext(), topOffersListnew, poppinsBold, null, poppinsLight, poppinsSemiBold, RobotoRegular, RobotoBold, Globaluse.spinnerselection));



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
                    if(topOffersListnew.size()>20){
                        noMoreItemText.setVisibility(View.VISIBLE);
                    }
                }


            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getActivity().getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                params.put("content-type", "application/json");
                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);





























//        Log.v("BreakFastURL", url);
//        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.d("HomePageResponse", response.toString());
//                progressBar.setVisibility(View.GONE);
//                itemsLoader.setVisibility(View.GONE);
//
//
////                RequestBtoBItem topOffersnew = new RequestBtoBItem();
////                List<RequestBtoBItem> topOffersListnew = new ArrayList<>();
////                List<RequestBtoB> breakFastFoodItemListnew = new ArrayList<>();
//
//
//                Gson gson = new Gson();
//                try {
//                    JSONObject   jsonObject = new JSONObject(String.valueOf(response));
//                    if(jsonObject.getString("isSuccess").equals("true")) {
//                        JSONObject dataObject = jsonObject.getJSONObject("data");
//                        JSONArray requestsList=dataObject.getJSONArray("requestsList");
//
//                        if (breakfastIndex == 0) {
//                            topOffersnew = new RequestBtoB();
//                            breakFastFoodItemListnew.clear();
//                        }
//                        topOffersList.clear();
//
//                        for(int i=0;i<requestsList.length();i++)
//                        {
//                            RequestBtoBItem foodItem = new RequestBtoBItem();
//                            foodItem = gson.fromJson(String.valueOf(requestsList.get(i)), RequestBtoBItem .class);
//
//                            breakFastFoodItemListnew.add(foodItem) ;
//                        }
//
//                        topOffersnew.setFoodItemList(breakFastFoodItemListnew);
//                        topOffersListnew.add(topOffersnew);
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                recyclerView.setAdapter(new MenuSubBottomItemsVerticalAdapterAdmin(getContext(), topOffersListnew, poppinsBold, null, poppinsLight, poppinsSemiBold, RobotoRegular, RobotoBold, Globaluse.spinnerselection));
//
//


//                try {
//                    if (breakfastIndex == 0) {
//                        topOffers = new TopOffers();
//                        breakFastFoodItemList.clear();
//                    }
//                    topOffersList.clear();
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject menuObject = response.getJSONObject(i);
//
//                        FoodItem foodItem = new FoodItem();
//                        if (menuObject.getJSONArray("dishImagePath").length() != 0) {
//                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
//                        }
//                        foodItem.setFoodName(menuObject.optString("name"));
//                        foodItem.setFoodId(menuObject.optString("dishId"));
//                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
//                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
//                        foodItem.setAvailableQuantity("Available(" + menuObject.optString("availableCount") + ")");
//                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
//                        int totalTimeForPreparation = menuObject.optInt("deliveryTime")/* + menuObject.optInt("deliveryTime")*/;
//                        int totalTimeForPreparationPlusFiveAdded = /*menuObject.optInt("preparationTime") +*/ menuObject.optInt("deliveryExpectedTime") /*+ 5*/;
//                        foodItem.setTime(totalTimeForPreparation + "-" + totalTimeForPreparationPlusFiveAdded + " mins");
//                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage") + "(" + menuObject.optString("ratingsCount") + ")");
//                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
//                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
//                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
//                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent") + "%\nOFF");
//                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
//                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
//                        JSONObject quickInfoObject = new JSONObject();
//
//                        if (menuObject.has("quickInfo")) {
//                            quickInfoObject = menuObject.getJSONObject("quickInfo");
//
//
//                            JSONObject nutritionObject = new JSONObject();
//
//                            if (quickInfoObject.has("nutrition")) {
//                                nutritionObject = quickInfoObject.getJSONObject("nutrition");
//                            }
//                            foodItem.setGramsCount(quickInfoObject.optInt("weight"));
//                            foodItem.setCaloriesCount(nutritionObject.optInt("energyCalories"));
//                            foodItem.setProteintCount(nutritionObject.optInt("protein"));
//                            foodItem.setFatCount(nutritionObject.optInt("fat"));
//                            foodItem.setFibreCount(nutritionObject.optInt("fibre"));
//                            foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
//                        }
//                        JSONObject chefQuickInfoObject = new JSONObject();
//
//                        if (menuObject.has("chefQuickInfo")) {
//                            chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
//                        }
//
//                        foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
//                        foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
//                        foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
//                        foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
//                        foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
//                        Log.v("ratingHere", String.valueOf(foodItem.getChefratingAverage()));
//                        foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
//                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
//                        foodItem.setFavourite(menuObject.optBoolean("isFavourite"));
//                        foodItem.setCartAddedQuantity(menuObject.optInt("cartQuantity"));
//
//                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
//                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
//                        foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));
//                        breakFastFoodItemList.add(foodItem);
//
//                    }
//                    topOffers.setFoodItemList(breakFastFoodItemList);
//                    topOffersList.add(topOffers);
//                    recyclerView.getAdapter().notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
//                itemsLoader.setVisibility(View.GONE);
//
//                if (error instanceof ServerError) {
//                    if(topOffersListnew.size()>20){
//                        noMoreItemText.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        }){    //this is the part, that adds the header to the request
//            @Override
//            public Map<String, String> getHeaders() {
//                SharedPreferences sh = getActivity().getSharedPreferences("corporateLogin", MODE_APPEND);
//                String token = sh.getString("token", "");
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "Bearer " + token);
//                params.put("content-type", "application/json");
//                return params;
//            }
//        };
//        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "consumer_home_taq");
//

        //recyclerView.setAdapter(new MenuSubBottomItemsVerticalAdapterAdmin(getContext(), topOffersList, poppinsBold, null, poppinsLight, poppinsSemiBold, RobotoRegular, RobotoBold,"request"));

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

       // recyclerView.setAdapter(new MenuSubBottomItemsVerticalAdapterAdmin(getContext(), topOffersList, poppinsBold, null, poppinsLight, poppinsSemiBold, RobotoRegular, RobotoBold,"Accept"));

    }


    /**
     get snacks list (POST)
     **/
    private void getSnacks() throws JSONException {

        String url = APIBaseURL.BASEURLLINK_B2B_Subscription_list;

        JSONObject inputObject = new JSONObject();
        inputObject.put("companyId", "CMPNY31032021055750");
        inputObject.put("status", "All");
        inputObject.put("search", "");
        inputObject.put("startDate", "2020-04-11T10:21:54.996Z");
        inputObject.put("endDate", "2020-04-11T10:21:54.996Z");
        inputObject.put("pagesize", 20);
        inputObject.put("pageindex", 0);



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

        //recyclerView.setAdapter(new MenuSubBottomItemsVerticalAdapterAdmin(getContext(), topOffersList, poppinsBold, null, poppinsLight, poppinsSemiBold, RobotoRegular, RobotoBold,"reject"));

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
            if (sTitle.equalsIgnoreCase("Request")) {
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
//            if (sTitle.equalsIgnoreCase("Accept")) {
//                title.setText(sTitle);
//                try {
//                    lunchIndex=0;
//                    getLunch();
//                    //perform pagination
//
//                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                        @Override
//                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                                lunchIndex++;
//                                try {
//                                    getLunch();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (sTitle.equalsIgnoreCase("Reject")) {
//                title.setText(sTitle);
//                try {
//                    snacksIndex=0;
//                    getSnacks();
//                    //perform pagination
//
//                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                        @Override
//                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                                snacksIndex++;
//                                try {
//                                    getSnacks();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (sTitle.equalsIgnoreCase("Dinner")) {
//                title.setText(sTitle);
//                try {
//                    dinnerIndex=0;
//                    getDinner();
//                    //perform pagination
//
//                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                        @Override
//                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                                dinnerIndex++;
//                                try {
//                                    getDinner();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (sTitle.equalsIgnoreCase("other")) {
//                title.setText(sTitle);
//                try {
//                    othersIndex=0;
//                    getOther();
//                    //perform pagination
//
//                    nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                        @Override
//                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                                othersIndex++;
//                                try {
//                                    getOther();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
            recyclerView.getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
