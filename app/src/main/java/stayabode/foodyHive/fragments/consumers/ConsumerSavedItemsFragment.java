package stayabode.foodyHive.fragments.consumers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.adapters.consumers.ConsumerHomeAdapters;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumerSavedItemsFragment extends Fragment {


    public static Typeface poppinsSemiBold;
    public static Typeface poppinsBold;
    public static Typeface poppinsMedium;
    public static Typeface poppinsLight;
    public static Typeface robotoFontBold;
    public static Typeface robotoFontRegular;

    public static RecyclerView recyclerView;

    EditText searchText;
    public static List<Object> objectList = new ArrayList<>();

    public static String TAG = "ConsumerSavedItemsFragment";

    public static ConsumerSavedItemsFragment consumerSavedItemsFragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consumer_saved_items,container,false);

        consumerSavedItemsFragment = this;
        poppinsSemiBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");
        robotoFontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        poppinsMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Medium.ttf");
        robotoFontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchText = rootView.findViewById(R.id.searchText);
        searchText.setTypeface(poppinsMedium);

        recyclerView.setAdapter(new ConsumerHomeAdapters(consumerSavedItemsFragment.getContext(),objectList,null,robotoFontRegular,poppinsSemiBold,poppinsBold,poppinsLight,poppinsMedium,1));




        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        getSavedOrders();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    /**
     get favourite items list(GET)
     **/
    public static void getSavedOrders()
    {

        ApplicationController.getInstance().getRequestQueue().getCache().remove("get_saved_taq");
        ApplicationController.getInstance().getRequestQueue().getCache().invalidate("get_saved_taq", true);

        String url = APIBaseURL.getFavouritesForCustomers+ SaveSharedPreference.getLoggedInUserEmail(consumerSavedItemsFragment.getContext());


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    objectList.clear();
                    for (int i=0;i < jsonArray.length();i++)
                    {
                        JSONObject menuObject = jsonArray.getJSONObject(i);

                        FoodItem foodItem = new FoodItem();
                        if(menuObject.getJSONArray("dishImagePath").length()!=0)
                        {
                            foodItem.setFoodImage(menuObject.getJSONArray("dishImagePath").get(0).toString());
                        }
                        foodItem.setFoodName(menuObject.optString("name"));
                        foodItem.setFoodId(menuObject.optString("dishId"));
                        foodItem.setAvailable(menuObject.optBoolean("isAvailable"));
                        foodItem.setShortDescription(menuObject.optString("shortDescription"));
                        foodItem.setAvailableQuantity("Available("+menuObject.optString("availableCount")+")");
                        foodItem.setAvailQty(menuObject.optInt("availableCount"));
                        foodItem.setTime(menuObject.optString("deliveryTime")+"-"+menuObject.optString("deliveryExpectedTime")+" mins");
                        foodItem.setItemRatingAverage(menuObject.optString("ratingAverage")+ "(" + menuObject.optString("ratingsCount")+")");
                        foodItem.setRatingAverage(Float.parseFloat(menuObject.optString("ratingAverage")));
                        foodItem.setDiscountedPercentage(menuObject.optInt("discountedPercent")+"%\nOFF");
                        foodItem.setPrice(String.format("%.2f", menuObject.optDouble("price")));
                        foodItem.setDiscountedPrice(String.format("%.2f", menuObject.optDouble("discountedPrice")));
                        double savedPrice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(menuObject.optString("price")) - Double.parseDouble(menuObject.optString("discountedPrice")))));
                        foodItem.setSavedPrice("Save \u20B9" + String.format("%.2f", savedPrice));
                        JSONObject quickInfoObject = new JSONObject();

                        if(menuObject.has("quickInfo"))
                        {
                            quickInfoObject = menuObject.getJSONObject("quickInfo");


                            JSONObject nutritionObject = new JSONObject();

                            if(quickInfoObject.has("nutrition"))
                            {
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

                        if(menuObject.has("chefQuickInfo"))
                        {
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

                        objectList.add(foodItem);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FoodItem foodItem = new FoodItem();
                try {
                    objectList.clear();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)consumerSavedItemsFragment.getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerSavedItemsFragment.getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerSavedItemsFragment.getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ConsumerMainActivity.logout();

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
                    Toast.makeText(consumerSavedItemsFragment.getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }



            }
        },consumerSavedItemsFragment.getContext());

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"get_saved_taq");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
