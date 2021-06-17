package stayabode.foodyHive.fragments.chefs;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.HomeAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.ChefCard;
import stayabode.foodyHive.models.ChefMostOrderedDish;
import stayabode.foodyHive.models.ChefRevenues;
import stayabode.foodyHive.models.ChefTopRatedDish;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.models.PieChartData;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefDrawer;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class ChefHomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();

    Typeface fontBold;
    Typeface fontRegular;
    Typeface raleWayFontBold;
    Typeface ralewayFontRegular;
    Typeface nunitoRegular;

    TextView chefName;
    TextView date;
    TextView cost;

    ProgressBar progressBar;

    ChefCard chefCard = new ChefCard();
    ChefRevenues chefRevenues = new ChefRevenues();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chef_home,container,false);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        raleWayFontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");
        ralewayFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
        nunitoRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Nunito-Regular.ttf");
        cheftoolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chefName=rootView.findViewById(R.id.chefName);
        date=rootView.findViewById(R.id.date);
        cost=rootView.findViewById(R.id.cost);
        chefName.setTypeface(fontBold);
        date.setTypeface(fontRegular);
        cost.setTypeface(fontRegular);

        ChefsMainActivity.chefnavigation.setVisibility(View.VISIBLE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        progressBar = rootView.findViewById(R.id.progressBar);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chefDrawer.openDrawer(Gravity.LEFT);
            }
        });


        getUserInfo();

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        date.setText(formattedDate);
        getChefsHomeDashboardCardDetails();
        return rootView;
    }


    /**
     Get Dashboard Details and Revenues from this API(GET)
     **/
    public void getChefsHomeDashboardCardDetails()
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String url = APIBaseURL.getChefsDashboard+""+ SaveSharedPreference.getLoggedInWorkFlowID(getContext())+"&currentdate="+formattedDate;



        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ChefHomeResponse",response);
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    objectList = new ArrayList<>();

                    objectList.clear();

                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if(isSuccess)
                    {
                        JSONObject dataObject = new JSONObject();
                        if(jsonObject.has("data"))
                        {
                            dataObject = jsonObject.getJSONObject("data");
                        }
                        chefCard = new ChefCard();
                        chefCard.setCurrentOrdersCount(dataObject.optString("currentOrderCount"));
                        chefCard.setNewOrdersCount(dataObject.optString("newOrderCount"));
                        chefCard.setSubscriptionsCount(dataObject.optString("cancelledOrderCount"));



                        JSONArray annualRevenueAndOrderArray = new JSONArray();
                        if(dataObject.has("annualRevenueAndOrder"))
                        {
                            annualRevenueAndOrderArray = dataObject.getJSONArray("annualRevenueAndOrder");
                        }
                        JSONObject annualRevenueAndOrderObject = annualRevenueAndOrderArray.getJSONObject(0);
                        chefRevenues = new ChefRevenues();
                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);

                        SimpleDateFormat df = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);

                        chefRevenues.setMonth(formattedDate);
                        chefRevenues.setAmount("₹"+formatChange(annualRevenueAndOrderObject.optDouble("totalRevenue")));
                        SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        String formattedDate1 = df1.format(c);
                        date.setText(formattedDate1);
                        cost.setText("₹"+formatChange(annualRevenueAndOrderObject.optDouble("totalRevenue")));


                        JSONArray mostOrderedDishArray = new JSONArray();
                        JSONArray topRateddataArray = new JSONArray();
                        if(dataObject.has("mostOrderedDish"))
                        {
                            mostOrderedDishArray = dataObject.getJSONArray("mostOrderedDish");
                        }
                        if(dataObject.has("topratedDish"))
                        {
                            topRateddataArray = dataObject.getJSONArray("topratedDish");
                        }
                        ChefMostOrderedDish chefMostOrderedDish = new ChefMostOrderedDish();
                        ChefTopRatedDish chefTopRatedDish = new ChefTopRatedDish();
                        List<FoodItem> mostOrderedDishItemsList = new ArrayList<>();
                        List<FoodItem> topRatedDishLists = new ArrayList<>();
                        for (int i=0;i < mostOrderedDishArray.length();i++)
                        {
                            JSONObject dishesObject = mostOrderedDishArray.getJSONObject(i);
                            FoodItem foodItem = new FoodItem();
                            foodItem.setFoodId(dishesObject.optString("dishId"));
                            foodItem.setChefId(dishesObject.optString("chefId"));
                            foodItem.setAvailableStatus("On");
                            foodItem.setFoodName(dishesObject.optString("name"));
                            foodItem.setFoodRatings(dishesObject.optString("ratingAverage"));
                            foodItem.setRatingAverage(Float.parseFloat(dishesObject.optString("ratingAverage")));
                            if(dishesObject.getJSONArray("dishImagePath").length()!=0)
                            {
                                foodItem.setFoodImage(dishesObject.getJSONArray("dishImagePath").get(0).toString());
                            }

                            foodItem.setTime(dishesObject.optString("deliveryTime") +" - "+ dishesObject.optString("deliveryExpectedTime")+" mins");
                            foodItem.setRatingCount(dishesObject.optString("ratingsCount"));
                            foodItem.setReviewsCount(dishesObject.optString("reviewCount"));
                            foodItem.setPrice(dishesObject.optString("price"));
                            foodItem.setDiscountedPrice(dishesObject.optString("discountedPrice"));
                            foodItem.setActive(dishesObject.optBoolean("isActive"));
                            foodItem.setApproved(dishesObject.optBoolean("isApproved"));
                            foodItem.setAutoAcceptOrder(dishesObject.optBoolean("autoAcceptOrder"));
                            foodItem.setShortDescription(dishesObject.optString("shortDescription"));

                            mostOrderedDishItemsList.add(foodItem);
                        }

                        for (int i=0;i < topRateddataArray.length();i++)
                        {
                            JSONObject dishesObject = topRateddataArray.getJSONObject(i);
                            FoodItem foodItem = new FoodItem();
                            foodItem.setFoodId(dishesObject.optString("dishId"));
                            foodItem.setChefId(dishesObject.optString("chefId"));
                            foodItem.setAvailableStatus("On");
                            foodItem.setFoodName(dishesObject.optString("name"));
                            foodItem.setRatingAverage(Float.parseFloat(dishesObject.optString("ratingAverage")));
                            if(dishesObject.getJSONArray("dishImagePath").length()!=0)
                            {
                                foodItem.setFoodImage(dishesObject.getJSONArray("dishImagePath").get(0).toString());
                            }

                            foodItem.setTime(dishesObject.optString("deliveryTime") +" - "+ dishesObject.optString("deliveryExpectedTime")+" mins");
                            foodItem.setRatingCount(dishesObject.optString("ratingsCount"));
                            foodItem.setReviewsCount(dishesObject.optString("reviewCount"));
                            foodItem.setPrice(dishesObject.optString("price"));
                            foodItem.setDiscountedPrice(dishesObject.optString("discountedPrice"));
                            foodItem.setActive(dishesObject.optBoolean("isActive"));
                            foodItem.setApproved(dishesObject.optBoolean("isApproved"));
                            foodItem.setAutoAcceptOrder(dishesObject.optBoolean("autoAcceptOrder"));
                            foodItem.setShortDescription(dishesObject.optString("shortDescription"));

                            topRatedDishLists.add(foodItem);
                        }

                        objectList.add(chefCard);
                        objectList.add(chefRevenues);


                        chefMostOrderedDish.setFoodItemList(mostOrderedDishItemsList);
                        objectList.add(chefMostOrderedDish);

                        chefTopRatedDish.setFoodItemList(topRatedDishLists);
                        objectList.add(chefTopRatedDish);


                        Orders orders = new Orders();
                        objectList.add(orders);


                        PieChartData pieChart = new PieChartData();
                        objectList.add(pieChart);



                        recyclerView.setAdapter(new HomeAdapter(getContext(),objectList,fontBold,fontRegular,raleWayFontBold,ralewayFontRegular));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }

                NetworkResponse response = error.networkResponse;
                if (response != null && response.statusCode == 404) {
//                    Toast.makeText(getContext(), "No Records found", Toast.LENGTH_SHORT).show();
                }

            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"chef_cards_taq");


    }

    /**
     To Change the Date Time Format
     **/
    public String formatChange(Double value)
    {

        if (value == null) {
            return "";
        }
        Double InrRSOut = value;
        InrRSOut = Double.valueOf(Math.round(InrRSOut));
        String RV = "";
        if (InrRSOut > 0 && InrRSOut < 1000) {
            RV = InrRSOut.toString();
            Log.d("RV",RV + " ");
        }
        else if (InrRSOut >= 1000 && InrRSOut < 10000) {
            String f1 = InrRSOut.toString().substring(0, 1);
            String f2 = InrRSOut.toString().substring(1, 4);
            RV = f1+ "," + f2;
        }
        else if (InrRSOut >= 10000 && InrRSOut < 100000) {
            String f1 = InrRSOut.toString().substring(0, 2);
            String  f2 = InrRSOut.toString().substring(2, 5);
//                if(k) {
            RV = f1 + "k";
//                } else {
//                    RV = f1 + "," + f2;
//                }

        }
        else if (InrRSOut >= 100000 && InrRSOut < 1000000) {
            String f1 = InrRSOut.toString().substring(0, 1);
            String  f2 = InrRSOut.toString().substring(1, 3);
            if (f2 == "00") {
                RV = f1 + " L";
            }
            else {
                RV = f1 + "." + f2 + " L";
            }
        }
        else if (InrRSOut >= 1000000 && InrRSOut < 10000000) {
            String f1 = InrRSOut.toString().substring(0, 2);
            String f2 = InrRSOut.toString().substring(2, 4);
            if (f2 == "00") {
                RV = f1 + " L";
            }
            else {
                RV = f1 + "." + f2 + " L";
            }
        }
        else if (InrRSOut >= 10000000 && InrRSOut < 100000000) {
            String f1 = InrRSOut.toString().substring(0, 1);
            String f2 = InrRSOut.toString().substring(1, 3);
            if (f2 == "00") {
                RV = f1 + " Cr";
            }
            else {
                RV = f1 + "." + f2 + " Cr";
            }
        }
        else if (InrRSOut >= 100000000 && InrRSOut < 1000000000) {
            String f1 = InrRSOut.toString().substring(0, 2);
            String  f2 = InrRSOut.toString().substring(2, 4);
            if (f2 == "00") {
                RV = f1 + " Cr";
            }
            else {
                RV = f1 + "." + f2 + " Cr";
            }
        }
//            else if (InrRSOut >= 1000000000 && InrRSOut < 10000000000) {
//                String f1 = InrRSOut.toString().substring(0, 3);
//                String f2 = InrRSOut.toString().substring(3, 5);
//                if (f2 == "00") {
//                    RV = f1 + " Cr";
//                }
//                else {
//                    RV = f1 + "." + f2 + " Cr";
//                }
//            }
//            else if (InrRSOut >= 10000000000) {
//                String f1 = InrRSOut.toString().substring(0, 4);
//                String f2 = InrRSOut.toString().substring(6, 8);
//                if (f2 == "00") {
//                    RV = f1 + " Cr";
//                }
//                else {
//                    RV = f1 + "." + f2 + " Cr";
//                }
//            }
        else {
            RV = InrRSOut.toString();
        }
        Log.d("RVEnd",RV.replaceAll("[.0]+$", "") + " ");
        Log.d("RVEndReplace",RV.replaceAll(".0", "") + " ");
        if(InrRSOut > 0 && InrRSOut < 1000)
        {
            return  RV.replace(".0", "");
        }
        else
        return  RV.replaceAll("[.0]+$", "");
//        }

//        return value;
    }


    /**
     Get the Logged in User Profiel Details (GET)
     **/
    public void getUserInfo()
    {

        String url = APIBaseURL.chefProfile + SaveSharedPreference.getLoggedInWorkFlowID(getContext());

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("chefLog",response);
                progressBar.setVisibility(View.GONE);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataObject = jsonObject.getJSONArray("data");
                    JSONObject arrayData = new JSONObject();
                    arrayData = dataObject.getJSONObject(0);
                    chefName.setText("Hi " + arrayData.optString("name"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"userInfoTaq");
    }


}
