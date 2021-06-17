package stayabode.foodyHive.activities.consumers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.ConsumerHomeAdapters;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.ItemAddOns;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ConsumerMyBasketActivity extends AppCompatActivity {

    public static Typeface poppinsMedium;
    public static Typeface poppinsSemiBold;
    public static Typeface poppinsBold;
    public static Typeface RobotoRegular;
    public static Typeface RobotoBold;
    public static Typeface poppinsLight;
    public static Typeface RobotoItalic;

    TextView reSymbol;
    public static TextView totalCost;
    public static TextView totalCostinPop;
    public static TextView taxCost;
    public static TextView packagingCost;
    public static TextView mealCost;
    TextView totalAmount;
    static TextView cartIsEmptyText;
    Button addItemsButton;
    Button checkOutButton;

    public static RecyclerView recyclerView;
    public static List<Object> objectList = new ArrayList<>();
    public static RelativeLayout rootLayout;
    public static ProgressBar progressBar;
    static Button emptyCart;
    public static RelativeLayout checkoutlayout;
    public static ConsumerMyBasketActivity consumerMyBasketActivity;
    CardView totalExpand;
    public static LinearLayout totalAmountLayout;


    static String cartId = null;

    public static List<FoodItem> cartsItemsListss = new ArrayList<>();
    public static List<Chef> cartDetailsListssArray = new ArrayList<>();

    public static TextView soldOutTextinCart;
    public static String preperationTime="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_my_basket);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        consumerMyBasketActivity = this;
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        RobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        RobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        RobotoItalic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Italic.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("My Basket");

        checkoutlayout = findViewById(R.id.checkoutlayout);
        totalAmount = findViewById(R.id.totalAmount);
        emptyCart = findViewById(R.id.emptyCart);
        rootLayout = findViewById(R.id.rootLayout);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
        addItemsButton = findViewById(R.id.addItemsButton);
        checkOutButton = findViewById(R.id.checkOutButton);
        recyclerView = findViewById(R.id.recyclerView);
        totalCost = findViewById(R.id.totalCost);
        totalCostinPop = findViewById(R.id.totalCostinPop);
        taxCost = findViewById(R.id.taxCost);
        packagingCost = findViewById(R.id.packagingCost);
        mealCost = findViewById(R.id.mealCost);
        reSymbol = findViewById(R.id.reSymbol);
        totalExpand = findViewById(R.id.totalExpand);
        totalAmountLayout = findViewById(R.id.totalAmountLayout);
        cartIsEmptyText=findViewById(R.id.cartIsEmptyText);
        soldOutTextinCart=findViewById(R.id.soldOutTextinCart);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        totalAmount.setTypeface(poppinsMedium);
        addItemsButton.setTypeface(poppinsBold);
        checkOutButton.setTypeface(poppinsBold);
        totalCost.setTypeface(poppinsMedium);
        totalCostinPop.setTypeface(poppinsMedium);
        taxCost.setTypeface(poppinsMedium);
        packagingCost.setTypeface(poppinsMedium);
        reSymbol.setTypeface(RobotoItalic);



        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkAvailability = isAllItemsAreNotSoldOut(cartDetailsListssArray);
                if(checkAvailability)
                {
                    new AlertDialog.Builder(ConsumerMyBasketActivity.this)
                            .setTitle("Delete Item")
                            .setMessage("Oops! Some of the selected dishes are no longer available / sold out\nPlease delete them from cart in order to proceed")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    dialog.dismiss();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(R.mipmap.ic_launcher)
                            .show();

                }
                else
                {
                    Intent intent = new Intent(ConsumerMyBasketActivity.this, ConsumerCheckOutActivity.class);
                    startActivity(intent);
                }

            }
        });

        emptyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllCarts();
            }
        });



        addItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumerMyBasketActivity.super.onBackPressed();
            }
        });
        totalExpand.setVisibility(View.GONE);


    }



    /**
     Custom method to implement int array contains
     **/
    private static boolean isAllItemsAreNotSoldOut(List<Chef> foodItemList){
        boolean result = false;
        for (int i=0;i<foodItemList.size();i++){
            for (int j=0;j < foodItemList.get(i).getFoodItemList().size();j++)
            {
                if(foodItemList.get(i).getFoodItemList().get(j).getAvailQty() == 0 || !foodItemList.get(i).getFoodItemList().get(j).getAvailable()){
                    result = true;
                }
            }

        }
        return result;
    }


    @Override
    protected void onResume() {
        super.onResume();
        getCartListsinCheckoutPage();
        try {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     To get the Cart Items Lists From API(GET)
     **/
    public static void getCartListsinCheckoutPage() {
        String url = APIBaseURL.getCartsList+ SaveSharedPreference.getLoggedInUserEmail(consumerMyBasketActivity);
        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                rootLayout.setVisibility(View.VISIBLE);
                objectList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataArray = jsonObject.getJSONObject("data");
                    cartId = dataArray.optString("id");
                    JSONArray cartDetailsArray = new JSONArray();
                    if (dataArray.has("cartDetails")) {
                        cartDetailsArray = dataArray.getJSONArray("cartDetails");
                    }
                    double sum = 0;
                    double mealPriceSum = 0;
                    cartDetailsListssArray = new ArrayList<>();
                    for (int i = 0; i < cartDetailsArray.length(); i++) {
                        JSONObject menuObject = cartDetailsArray.getJSONObject(i);

                        Chef chef = new Chef();
                        chef.setId(menuObject.optString("chefId"));
                        chef.setName(menuObject.optString("chefName"));

                        JSONArray menuDetailsArray = new JSONArray();

                        if (menuObject.has("menuDetails")) {
                            menuDetailsArray = menuObject.getJSONArray("menuDetails");
                        }

                        JSONObject footerObject = new JSONObject();

                        if (menuObject.has("footer")) {
                            footerObject = menuObject.getJSONObject("footer");
                        }
                        chef.setSumOfSaleAmount(footerObject.optString("sumOfSaleAmount"));
                        chef.setSumTaxAmount(footerObject.optString("sumTaxAmount"));
                        chef.setDeliveryCharges(footerObject.optString("deliveryCharges"));
                        chef.setPackagingCharges(footerObject.optString("packagingCharges"));
                        chef.setTotal(footerObject.optString("total"));
                        List<FoodItem> foodItemList = new ArrayList<>();
                        cartsItemsListss = new ArrayList<>();
                        for (int j = 0; j < menuDetailsArray.length(); j++) {
                            JSONObject menuItemsObject = menuDetailsArray.getJSONObject(j);

                            FoodItem foodItem = new FoodItem();
                            if (menuItemsObject.getJSONArray("dishImage").length() != 0) {
                                foodItem.setFoodImage(menuItemsObject.getJSONArray("dishImage").get(0).toString());
                            }

                            foodItem.setFoodName(menuItemsObject.optString("dishName"));
                            foodItem.setShortDescription(menuItemsObject.optString("shortDescription"));
                            foodItem.setAvailableQuantity("Available(" + menuItemsObject.optString("availableCount") + ")");
                            foodItem.setAvailQty(menuItemsObject.optInt("availableCount"));
                            foodItem.setTime(menuItemsObject.optString("preparationTime"));
                            foodItem.setCartId(menuItemsObject.optString("id"));
                            foodItem.setFoodId(menuItemsObject.optString("dishId"));
                            foodItem.setCartQuantity(menuItemsObject.optInt("quantity"));
                            foodItem.setMealPrice(menuItemsObject.optString("mealPrice"));
                            foodItem.setPrice(menuItemsObject.optString("mealPrice"));
                            foodItem.setSubTotal(menuItemsObject.optInt("total"));
                            foodItem.setAvailable(menuItemsObject.optBoolean("isAvailable"));


                            JSONObject quickInfoObject = new JSONObject();

                            if (menuObject.has("quickInfo")) {
                                quickInfoObject = menuItemsObject.getJSONObject("quickInfo");


                                JSONObject nutritionObject = new JSONObject();

                                if (quickInfoObject.has("nutrition")) {
                                    nutritionObject = quickInfoObject.getJSONObject("nutrition");
                                }

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

                            List<ItemAddOns> itemAddOns = new ArrayList<>();
                            ItemAddOns addOns = new ItemAddOns();
                            addOns.setName("coconut");
                            // foodItem.setAddOns(itemAddOns);
                            Double subTotal = menuObject.optDouble("total");
                            sum = sum + subTotal;

                            Double mealPriceTotal = menuObject.optDouble("mealPrice");
                            mealPriceSum = mealPriceSum + mealPriceTotal;
                            foodItemList.add(foodItem);
                            cartsItemsListss.add(foodItem);
                            chef.setFoodItemList(foodItemList);

                        }
                        cartDetailsListssArray.add(chef);
                        objectList.add(chef);
                    }

                    JSONObject orderReportSummaryObject = new JSONObject();

                    if (dataArray.has("orderReportSummary")) {
                        orderReportSummaryObject = dataArray.getJSONObject("orderReportSummary");
                    }

                    totalCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));

                    totalCostinPop.setText(orderReportSummaryObject.optString("grandTotal"));
                    taxCost.setText(orderReportSummaryObject.optString("totalTax"));
                    packagingCost.setText(orderReportSummaryObject.optString("totalPackingCharges"));
                    mealCost.setText(orderReportSummaryObject.optString("orderSubTotal"));
                    recyclerView.setAdapter(new ConsumerHomeAdapters(consumerMyBasketActivity, objectList, RobotoBold, RobotoRegular, poppinsSemiBold, poppinsBold, poppinsLight, poppinsMedium,1));

                    if (recyclerView.getAdapter().getItemCount() == 0) {
                        Toast.makeText(consumerMyBasketActivity, "Your cart is Empty", Toast.LENGTH_SHORT).show();
                        checkoutlayout.setVisibility(View.GONE);
                        totalAmountLayout.setVisibility(View.GONE);
                    } else {
                        checkoutlayout.setVisibility(View.VISIBLE);
                        totalAmountLayout.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                boolean checkAvailability = isAllItemsAreNotSoldOut(cartDetailsListssArray);
                if(checkAvailability)
                {
                    soldOutTextinCart.setVisibility(View.VISIBLE);
                }
                else
                {
                    soldOutTextinCart.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                rootLayout.setVisibility(View.VISIBLE);
                emptyCart.setVisibility(View.GONE);
                cartIsEmptyText.setVisibility(View.VISIBLE);
                checkoutlayout.setVisibility(View.GONE);
                totalAmountLayout.setVisibility(View.GONE);
                objectList.clear();
                try {
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerMyBasketActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerMyBasketActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerMyBasketActivity);

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
                    Toast.makeText(consumerMyBasketActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        },consumerMyBasketActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "cart_list_taq");
    }



    /**
     To get the Carts From API  when increase or decrease or remove(GET)
     **/
    public static void getCartListsinCheckoutPageForTotal(int from) {
        String url = APIBaseURL.getCartsList+ SaveSharedPreference.getLoggedInUserEmail(consumerMyBasketActivity);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataArray = jsonObject.getJSONObject("data");
                    JSONArray cartDetailsArray = dataArray.getJSONArray("cartDetails");
                    double sum = 0;
                    double mealPriceSum = 0;
                    objectList.clear();
                    cartDetailsListssArray = new ArrayList<>();
                    for (int i = 0; i < cartDetailsArray.length(); i++) {
                        JSONObject menuObject = cartDetailsArray.getJSONObject(i);
                        Chef chef = new Chef();
                        chef.setId(menuObject.optString("chefId"));
                        chef.setName(menuObject.optString("chefName"));

                        JSONArray menuDetailsArray = new JSONArray();

                        if (menuObject.has("menuDetails")) {
                            menuDetailsArray = menuObject.getJSONArray("menuDetails");
                        }

                        JSONObject footerObject = new JSONObject();

                        if (menuObject.has("footer")) {
                            footerObject = menuObject.getJSONObject("footer");
                        }
                        chef.setSumOfSaleAmount(footerObject.optString("sumOfSaleAmount"));
                        chef.setSumTaxAmount(footerObject.optString("sumTaxAmount"));
                        chef.setDeliveryCharges(footerObject.optString("deliveryCharges"));
                        chef.setPackagingCharges(footerObject.optString("packagingCharges"));
                        chef.setTotal(footerObject.optString("total"));
                        List<FoodItem> foodItemList = new ArrayList<>();
                        cartsItemsListss = new ArrayList<>();
                        for (int j = 0; j < menuDetailsArray.length(); j++) {
                            JSONObject menuItemsObject = menuDetailsArray.getJSONObject(j);

                            FoodItem foodItem = new FoodItem();
                            if (menuItemsObject.getJSONArray("dishImage").length() != 0) {
                                foodItem.setFoodImage(menuItemsObject.getJSONArray("dishImage").get(0).toString());
                            }

                            foodItem.setFoodName(menuItemsObject.optString("dishName"));
                            foodItem.setShortDescription(menuItemsObject.optString("shortDescription"));
                            foodItem.setAvailableQuantity("Available(" + menuItemsObject.optString("availableCount") + ")");
                            foodItem.setAvailQty(menuItemsObject.optInt("availableCount"));
                            foodItem.setTime(menuItemsObject.optString("preparationTime"));
                            foodItem.setCartId(menuItemsObject.optString("id"));
                            foodItem.setFoodId(menuItemsObject.optString("dishId"));
                            foodItem.setCartQuantity(menuItemsObject.optInt("quantity"));
                            foodItem.setMealPrice(menuItemsObject.optString("mealPrice"));
                            foodItem.setPrice(menuItemsObject.optString("mealPrice"));
                            foodItem.setSubTotal(menuItemsObject.optInt("total"));
                            foodItem.setAvailable(menuItemsObject.optBoolean("isAvailable"));


                            JSONObject quickInfoObject = new JSONObject();

                            if (menuObject.has("quickInfo")) {
                                quickInfoObject = menuItemsObject.getJSONObject("quickInfo");


                                JSONObject nutritionObject = new JSONObject();

                                if (quickInfoObject.has("nutrition")) {
                                    nutritionObject = quickInfoObject.getJSONObject("nutrition");
                                }

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

                            List<ItemAddOns> itemAddOns = new ArrayList<>();
                            ItemAddOns addOns = new ItemAddOns();
                            addOns.setName("coconut");
                            // foodItem.setAddOns(itemAddOns);
                            Double subTotal = menuObject.optDouble("total");
                            sum = sum + subTotal;

                            Double mealPriceTotal = menuObject.optDouble("mealPrice");
                            mealPriceSum = mealPriceSum + mealPriceTotal;
                            foodItemList.add(foodItem);
                            cartsItemsListss.add(foodItem);
                            chef.setFoodItemList(foodItemList);

                        }
                        objectList.add(chef);
                        cartDetailsListssArray.add(chef);
                        if(from == 2)
                        {
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                        if(objectList.size() == 0)
                        {
                            consumerMyBasketActivity.finish();
                        }
                    }
                    try {
                        JSONObject orderReportSummaryObject = new JSONObject();

                        if (dataArray.has("orderReportSummary")) {
                            orderReportSummaryObject = dataArray.getJSONObject("orderReportSummary");
                        }

                        totalCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));

                        totalCostinPop.setText(orderReportSummaryObject.optString("grandTotal"));
                        taxCost.setText(orderReportSummaryObject.optString("totalTax"));
                        packagingCost.setText(orderReportSummaryObject.optString("totalPackingCharges"));
                        mealCost.setText(orderReportSummaryObject.optString("orderSubTotal"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                boolean checkAvailability = isAllItemsAreNotSoldOut(cartDetailsListssArray);
                if(checkAvailability)
                {
                    soldOutTextinCart.setVisibility(View.VISIBLE);
                }
                else
                {
                    soldOutTextinCart.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null && response.statusCode == 404) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        //use this json as you want
                        consumerMyBasketActivity.finish();
                      //  Toast.makeText(Address.this, "Address Field should not contain #,Invalid address", Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.GONE);
                rootLayout.setVisibility(View.VISIBLE);
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerMyBasketActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerMyBasketActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerMyBasketActivity);

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
                    Toast.makeText(consumerMyBasketActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        },consumerMyBasketActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "cart_list_taq");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     To Remove All Items from Cart from Cart Page(DELETE)
     **/
    public void deleteAllCarts() {
        String url = APIBaseURL.removeAllCarts + cartId;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(ConsumerMyBasketActivity.this, jsonObject.optString("data"), Toast.LENGTH_SHORT).show();
                    finish();
                    getCartListsinCheckoutPage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerMyBasketActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerMyBasketActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerMyBasketActivity);

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
                    Toast.makeText(consumerMyBasketActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        },consumerMyBasketActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "taq");
    }
}
