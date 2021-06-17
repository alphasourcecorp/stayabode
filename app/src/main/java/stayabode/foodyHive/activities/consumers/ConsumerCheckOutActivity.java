package stayabode.foodyHive.activities.consumers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import stayabode.foodyHive.DirectionPointListener;
import stayabode.foodyHive.GetPathFromLocation;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.ConsumerHomeAdapters;
import stayabode.foodyHive.adapters.consumers.InvoiceOrdersCardAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.models.Consumer;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.ItemAddOns;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ConsumerCheckOutActivity extends AppCompatActivity implements OnMapReadyCallback {

    static Typeface poppinsMedium;
    static Typeface poppinsSemiBold;
    static Typeface poppinsBold;
    static Typeface RobotoRegular;
    static Typeface RobotoBold;
    static Typeface poppinsLight;
    static Typeface RobotoMedium;

    public static TextView subTotal;
    public static TextView taxCost;
    public static TextView packagingCost;
    public static TextView referralCost;
    TextView promoCodeTitle;
    TextView addressHeader;
    TextView reSymbol;
    public static TextView price;
    EditText promoCode;
    Button checkout;

    public static RecyclerView recyclerViewFoodItem;
    public static RecyclerView recyclerViewAddress;
    public static List<Object> objectsList = new ArrayList<>();
    public static List<Object> addressList = new ArrayList<>();

    Boolean isOrderPlacedAPI = false;
    String orderNumber = "";
    CardView totalExpand;
    LinearLayout subTotalLayout;
    public static TextView totalCostinPop;
    public static TextView mealCost;
    public static TextView deliveryCost;

    public static String selectedAddress = "";
    static Button addAddressButton;
    static Button editAddressButton;
    public static String selectedAddressLineOne = "";
    public static String selectedAddressLineTwo = "";
    public static String selectedUserName = "";
    public static String selectedUserPhoneNumber = "";
    public static String selectedAddressID = "";
    public static String selectedEmailID = "";
    public static String selectedPinCode = "";
    public static String selectedLandMark = "";
    public static Boolean checkAddressRadius = false;

    public static String selectedlatitude = "";
    public static String selectedlongtitude = "";
    public static  ArrayList<HashMap<String, String>>  center_name_arraylist ;



    public static ConsumerCheckOutActivity consumerCheckOutActivity;

    public static List<FoodItem> cartsItemsListss = new ArrayList<>();
    public static List<Chef> cartDetailsListssArray = new ArrayList<>();

    public static TextView soldOutTextinCart;
    LinearLayout referralrootLayout;
    static CheckBox checkReferral;

    public static boolean stateReferralAppliedorNot = false;

    Button continebutton;
    FrameLayout framelayout_id;
    private Marker mCurrLocationMarker;
    private GoogleMap mMap;
    LinearLayout latePayment,offline_id_layout;
    CheckBox offlineDelivery, offlinePayment;
    boolean chk_status=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_checkout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        selectedAddress = "";
        checkAddressRadius = false;
        consumerCheckOutActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Checkout");
        isOrderPlacedAPI = false;
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        RobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        RobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        RobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        recyclerViewFoodItem = findViewById(R.id.recyclerViewFoodList);
        referralrootLayout = findViewById(R.id.referralrootLayout);
        checkReferral = findViewById(R.id.checkReferral);
        recyclerViewAddress = findViewById(R.id.recyclerViewAddress);
        subTotalLayout = findViewById(R.id.subTotalLayout);
        totalExpand = findViewById(R.id.totalExpand);
        subTotal = findViewById(R.id.subTotal);
        taxCost = findViewById(R.id.taxCost);
        packagingCost = findViewById(R.id.packagingCost);
        referralCost = findViewById(R.id.referralCost);
        promoCodeTitle = findViewById(R.id.promoCodeTitle);
        addressHeader = findViewById(R.id.addressHeader);
        promoCode = findViewById(R.id.promoCode);
        checkout = findViewById(R.id.checkOutButton);
        reSymbol = findViewById(R.id.reSymbol);
        price = findViewById(R.id.price);
        totalCostinPop = findViewById(R.id.totalCostinPop);
        addAddressButton = findViewById(R.id.addAddressButton);
        editAddressButton = findViewById(R.id.editAddressButton);
        mealCost = findViewById(R.id.mealCost);
        deliveryCost = findViewById(R.id.deliveryCost);
        soldOutTextinCart = findViewById(R.id.soldOutTextinCart);

        recyclerViewFoodItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this));
        subTotal.setTypeface(poppinsMedium);
        promoCodeTitle.setTypeface(poppinsMedium);
        addressHeader.setTypeface(poppinsMedium);
        checkout.setTypeface(poppinsBold);
        addAddressButton.setTypeface(poppinsBold);
        editAddressButton.setTypeface(poppinsBold);
        reSymbol.setTypeface(RobotoMedium);
        price.setTypeface(poppinsMedium);

        offlineDelivery=(CheckBox)findViewById(R.id.offlineDelivery);
        offlinePayment=(CheckBox)findViewById(R.id.offlinePayment);
        latePayment=findViewById(R.id.latePayment);
        offline_id_layout=findViewById(R.id.offline_id_layout);

        continebutton=findViewById(R.id.continebutton);
        framelayout_id=findViewById(R.id.framelayout_id);

       // placeOrder();//sat

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkAvailability = isAllItemsAreNotSoldOut(cartDetailsListssArray);
                if (checkAvailability) {
                    new AlertDialog.Builder(ConsumerCheckOutActivity.this)
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

                } else {
                    if (recyclerViewAddress.getAdapter().getItemCount() == 0) {
                        addAddressDialog();
                    } else if (selectedAddress.equals("")) {
                        Toast.makeText(ConsumerCheckOutActivity.this, "Please select Delivery Address", Toast.LENGTH_SHORT).show();
                    } else {
                        if (checkAddressRadius) {


                            if((offlineDelivery.isChecked())&&(offlinePayment.isChecked()))
                            {
                               // placeOrder();
                                OfflineplaceOrder();

                            }else
                            {
                                placeOrder();
                            }




                        } else {

                            //Toast.makeText(ConsumerCheckOutActivity.this, "Apologies, our services are limited to serviceable areas with in the city only", Toast.LENGTH_SHORT).show();


                            if((offlineDelivery.isChecked())&&(offlinePayment.isChecked()))
                            {
                                //placeOrder();
                                OfflineplaceOrder();
                            }else
                            {
                                Toast.makeText(ConsumerCheckOutActivity.this, "Apologies, our services are limited to serviceable areas with in the city only", Toast.LENGTH_SHORT).show();

                            }





                        }

                    }
                }

            }
        });

        editAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAddress.equals("")) {
                    Toast.makeText(ConsumerCheckOutActivity.this, "Please Choose Address to Edit", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ConsumerCheckOutActivity.this, Address.class);
//                    context.startActivity(intent);
                    intent.putExtra("AddorEdit", "Edit");
                    intent.putExtra("AddressLine1", selectedAddressLineOne);
                    intent.putExtra("AddressLine2", selectedAddressLineTwo);
                    intent.putExtra("Name", selectedUserName);
                    intent.putExtra("ContactNumber", selectedUserPhoneNumber);
                    intent.putExtra("Id", selectedAddressID);
                    intent.putExtra("emailId", selectedEmailID);
                    intent.putExtra("pinCode", selectedPinCode);
                    intent.putExtra("landmark", selectedLandMark);
                    intent.putExtra("latitude", selectedlatitude);
                    intent.putExtra("longtitude", selectedlongtitude);
                    startActivityForResult(intent, 102);
                }
            }
        });

        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumerCheckOutActivity.this, Address.class);
                intent.putExtra("AddorEdit", "Add");
                intent.putExtra("emailId", SaveSharedPreference.getLoggedInUserEmail(ConsumerCheckOutActivity.this));
                startActivityForResult(intent, 101);
            }
        });

        subTotalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalExpand.getVisibility() == View.GONE) {
                    totalExpand.setVisibility(View.VISIBLE);
                } else {
                    totalExpand.setVisibility(View.GONE);
                }
            }
        });

        totalExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalExpand.setVisibility(View.GONE);
            }
        });

        getAvailableDeliveryAddresses(1);


        checkReferral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean checkAvailability = isAllItemsAreNotSoldOut(cartDetailsListssArray);
                if (checkAvailability) {
                    checkReferral.setChecked(false);
                    new AlertDialog.Builder(ConsumerCheckOutActivity.this)
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
                    if (b) {
                        stateReferralAppliedorNot = true;
                        getCartCheckOutItems(1, 2,true);

                    } else {
                        stateReferralAppliedorNot = false;
                        getCartCheckOutItems(1, 2,false);
                    }
                }



            }
        });





        continebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chk_status)
                {

                    if((offlineDelivery.isChecked()))
                    {
                        if((offlineDelivery.isChecked())&&(offlinePayment.isChecked()))
                        {
                            gotoMap();

                        }else
                        {
                            Toast.makeText(ConsumerCheckOutActivity.this, "Please check offline delivery and offline payment", Toast.LENGTH_SHORT).show();
                        }

                    }else
                    {
                        gotoMap();
                    }

                    //if((offlineDelivery.isChecked())&&(offlinePayment.isChecked()))


                }else
                {
                    gotoMap();
                }


            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        //offlinePrivilege();//late implementation

        offlineDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                           if(isChecked)
                                                           {
                                                               offline_id_layout.setVisibility(View.VISIBLE);
                                                               Globaluse.offlinedeliver=true;

                                                           }
                                                           else
                                                           {
                                                               offline_id_layout.setVisibility(View.GONE);
                                                               offlinePayment.setChecked(false);
                                                               Globaluse.offlinedeliver=false;

                                                           }

                                                       }
                                                   }
        );
    }



    @Override
    protected void onResume() {
        super.onResume();
        checkApplyReferralCanBeAppliedOrNot();

        if (isOrderPlacedAPI) {

        }

    }

    /**
     * To Check referral can be applied or Not by points and wallets(GET)
     **/

    public void checkApplyReferralCanBeAppliedOrNot() {
        String url = APIBaseURL.checkHavingReferralOrNot + SaveSharedPreference.getLoggedInUserEmail(ConsumerCheckOutActivity.this);

        CustomVolleyRequest customVolleyRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                {
//                    "isSuccess": true,
//                        "errorMessage": "",
//                        "data": {
//                    "isHavingReffrral": false
//                },
//                    "count": 0
//                }
                Log.d("ReferralApplyResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        JSONObject dataObject = new JSONObject();
                        if (jsonObject.has("data")) {
                            dataObject = jsonObject.getJSONObject("data");
                        }

                        Boolean isHavingReffrral = dataObject.optBoolean("isHavingReffrral");

                        if (isHavingReffrral) {
                            referralrootLayout.setVisibility(View.VISIBLE);

                            Boolean isRefferalAppliedorNot = dataObject.optBoolean("isRefferalApplied");

                            if (isRefferalAppliedorNot) {
                                checkReferral.setChecked(true);

                                stateReferralAppliedorNot = true;
                            } else {
                                checkReferral.setChecked(false);

                                stateReferralAppliedorNot = false;
                            }
                        } else {
                            referralrootLayout.setVisibility(View.GONE);
                        }


                        getCartCheckOutItems(1, 1,true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stateReferralAppliedorNot = false;
                getCartCheckOutItems(1, 1,true);
            }
        }, consumerCheckOutActivity);
        ApplicationController.getInstance().addToRequestQueue(customVolleyRequest, "having_referral_or_not");


    }


    /**
     * Custom method to implement int array contains
     **/
    private static boolean isAllItemsAreNotSoldOut(List<Chef> foodItemList) {
        boolean result = false;
        for (int i = 0; i < foodItemList.size(); i++) {
            for (int j = 0; j < foodItemList.get(i).getFoodItemList().size(); j++) {
                if (foodItemList.get(i).getFoodItemList().get(j).getAvailQty() == 0 || !foodItemList.get(i).getFoodItemList().get(j).getAvailable()) {
                    result = true;
                }
            }
        }
        return result;
    }


    /**
     * To Get the Cart Items Lists(GET)
     **/
    public static void getCartCheckOutItems(int from, int checkbox,boolean voluntaryuncheck) {
        String url = APIBaseURL.getCartsList + SaveSharedPreference.getLoggedInUserEmail(consumerCheckOutActivity) + "?Referal=" + stateReferralAppliedorNot;
        Log.v("cartUrl", url);
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("cartResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataArray = jsonObject.getJSONObject("data");



                    JSONArray cartDetailsArray = new JSONArray();
                    JSONArray totalMenuItemsArray = new JSONArray();
                    if (dataArray.has("cartDetails")) {
                        cartDetailsArray = dataArray.getJSONArray("cartDetails");
                    }
                    double sum = 0;
                    double mealPriceSum = 0;
                    objectsList = new ArrayList<>();
                    cartDetailsListssArray = new ArrayList<>();
                    for (int i = 0; i < cartDetailsArray.length(); i++) {
                        JSONObject menuObject = cartDetailsArray.getJSONObject(i);
                        Chef chef = new Chef();
                        chef.setId(menuObject.optString("chefId"));
                        chef.setName(menuObject.optString("chefName"));

                        JSONArray menuDetailsArray = new JSONArray();

                        if (menuObject.has("menuDetails")) {
                            menuDetailsArray = menuObject.getJSONArray("menuDetails");
                            totalMenuItemsArray = menuObject.getJSONArray("menuDetails");
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
                        objectsList.add(chef);
                        cartDetailsListssArray.add(chef);
                    }
                    JSONObject orderReportSummaryObject = new JSONObject();

                    if (dataArray.has("orderReportSummary")) {
                        orderReportSummaryObject = dataArray.getJSONObject("orderReportSummary");
                    }

                    subTotal.setText("Sub Total (" + jsonObject.optString("count") + " Items)");
                    price.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                    totalCostinPop.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                    taxCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("totalTax"))));
                    packagingCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("totalPackingCharges"))));

                    if (checkbox == 2) {
                        if (!dataArray.optBoolean("isRefferalApplied") && stateReferralAppliedorNot) {
                            checkReferral.setChecked(false);
                            stateReferralAppliedorNot = false;
                            new AlertDialog.Builder(consumerCheckOutActivity)
                                    .setMessage("Referral cannot be applied as 25% of bill amount is less than one time Referral code amount")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(R.mipmap.ic_launcher_round)
                                    .show();
                        } else if (orderReportSummaryObject.optInt("referalAmount") != 0 && dataArray.optInt("totalPointused") != 0) {
                            //Toast.makeText(consumerCheckOutActivity, "Referral code has been applied successfully and " + dataArray.optInt("totalPointused") + " points have been consumed", Toast.LENGTH_SHORT).show();
                        }

                    }

//                    if(!voluntaryuncheck){
//                        Toast.makeText(consumerCheckOutActivity,"Referral not applied",Toast.LENGTH_SHORT).show();
//                    }

//                    if(dataArray.optInt("totalPointused") == 0)
//                    {
//                        //referralCost.setText(" - "+String.format("%.2f",Double.valueOf("0")));
//                    }
//                    else
//                    {
                    try {
                        referralCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("referalAmount"))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    }
                    mealCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("orderSubTotal"))));
                    deliveryCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("totalDeliveryCharges"))));


                    if (from == 2) {
                        recyclerViewFoodItem.setAdapter(new ConsumerHomeAdapters(consumerCheckOutActivity, objectsList, RobotoBold, RobotoRegular, poppinsSemiBold, poppinsBold, poppinsLight, poppinsMedium, 1));

                    } else {
                        recyclerViewFoodItem.setAdapter(new ConsumerHomeAdapters(consumerCheckOutActivity, objectsList, RobotoBold, RobotoRegular, poppinsSemiBold, poppinsBold, poppinsLight, poppinsMedium, 1));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                boolean checkAvailability = isAllItemsAreNotSoldOut(cartDetailsListssArray);
                if (checkAvailability) {
                    soldOutTextinCart.setVisibility(View.VISIBLE);
                } else {
                    soldOutTextinCart.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerCheckOutActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerCheckOutActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerCheckOutActivity);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(consumerCheckOutActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerCheckOutActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "cart_list_taq");
    }


    /**
     * To Get the Cart Lists when increasing or decreasing or removing the item from cart(GET)
     **/
    public static void getCartCheckOutItemsTotalAmount(TextView total, TextView mealCost1, TextView totalTaxFromAPI, TextView deliveryChargeFromAPI, TextView packagingChargeFromAPI, int from, boolean applyreferral) {
        String url = APIBaseURL.getCartsList + SaveSharedPreference.getLoggedInUserEmail(consumerCheckOutActivity) + "?Referal=" + applyreferral;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.v("referalResponse", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataArray = jsonObject.getJSONObject("data");
                    JSONArray cartDetailsArray = dataArray.getJSONArray("cartDetails");
                    JSONArray totalMenuItemsArray = new JSONArray();
                    double sum = 0;
                    double mealPriceSum = 0;
                    objectsList.clear();
                    cartDetailsListssArray = new ArrayList<>();
                    for (int i = 0; i < cartDetailsArray.length(); i++) {
                        JSONObject menuObject = cartDetailsArray.getJSONObject(i);
                        Chef chef = new Chef();
                        chef.setId(menuObject.optString("chefId"));
                        chef.setName(menuObject.optString("chefName"));

                        JSONArray menuDetailsArray = new JSONArray();

                        if (menuObject.has("menuDetails")) {
                            menuDetailsArray = menuObject.getJSONArray("menuDetails");
                            totalMenuItemsArray = menuObject.getJSONArray("menuDetails");
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
                        total.setText(String.format("%.2f", Double.valueOf(footerObject.optString("total"))));
                        mealCost1.setText(String.format("%.2f", Double.valueOf(footerObject.optString("sumOfSaleAmount"))));
                        totalTaxFromAPI.setText(String.format("%.2f", Double.valueOf(footerObject.optString("sumTaxAmount"))));
                        deliveryChargeFromAPI.setText(String.format("%.2f", Double.valueOf(footerObject.optString("deliveryCharges"))));
                        packagingChargeFromAPI.setText(String.format("%.2f", Double.valueOf(footerObject.optString("packagingCharges"))));
                        Log.v("SingleMealPrice", footerObject.optString("sumOfSaleAmount"));
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
                        objectsList.add(chef);
                        cartDetailsListssArray.add(chef);
                        if (from == 2) {
                            recyclerViewFoodItem.getAdapter().notifyDataSetChanged();
                        } else {
                            recyclerViewFoodItem.getAdapter().notifyDataSetChanged();
                        }
                        if (objectsList.size() == 0) {
                            consumerCheckOutActivity.finish();

                        }
                    }
                    try {
                        JSONObject orderReportSummaryObject = new JSONObject();

                        if (dataArray.has("orderReportSummary")) {
                            orderReportSummaryObject = dataArray.getJSONObject("orderReportSummary");
                        }

                        subTotal.setText("Sub Total (" + jsonObject.optString("count") + " Items)");
                        price.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                        totalCostinPop.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                        taxCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("totalTax"))));
                        packagingCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("totalPackingCharges"))));


                        if (!dataArray.optBoolean("isRefferalApplied") || orderReportSummaryObject.optInt("referalAmount") == 0) {
                            // Toast.makeText(consumerCheckOutActivity, "Referral Cannot be applied", Toast.LENGTH_SHORT).show();
                            checkReferral.setChecked(false);
                            stateReferralAppliedorNot = false;
                            Log.v("checkBoxReferal", "false");
                        }

//                        if(dataArray.optInt("totalPointused") == 0)
//                        {
//                            //referralCost.setText(" - "+String.format("%.2f",Double.valueOf("0")));
//                        }
//                        else
//                        {
                        try {
                            referralCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("referalAmount"))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        }
                        mealCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("orderSubTotal"))));
                        deliveryCost.setText(String.format("%.2f", Double.valueOf(orderReportSummaryObject.optString("totalDeliveryCharges"))));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    boolean checkAvailability = isAllItemsAreNotSoldOut(cartDetailsListssArray);
                    if (checkAvailability) {
                        soldOutTextinCart.setVisibility(View.VISIBLE);
                    } else {
                        soldOutTextinCart.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
                        consumerCheckOutActivity.finish();
                        //  Toast.makeText(Address.this, "Address Field should not contain #,Invalid address", Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerCheckOutActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerCheckOutActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerCheckOutActivity);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(consumerCheckOutActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerCheckOutActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "cart_list_taq");
    }

//    // Toolbar backPresss Overridden Method
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // todo: goto back activity from here
//                finish();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    /**
     * To Get the Available Delivery Address Lists(GET)
     **/
    public static void getAvailableDeliveryAddresses(int from) {
        String url = APIBaseURL.getAvailableDeliveryAddress + SaveSharedPreference.getLoggedInUserEmail(consumerCheckOutActivity);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("availableAddressess", response);
                if (from == 2) {
                    checkAddressRadius = true;
                } else {
                    checkAddressRadius = false;
                }
                selectedAddress = "";
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    addressList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Consumer consumer = new Consumer();
                        consumer.setEmailId(jsonObject.optString("consumerEmailId"));
                        consumer.setName(jsonObject.optString("name"));
                        consumer.setAddressLineOne(jsonObject.optString("streetAddress1"));
                        consumer.setAddressLineTwo(jsonObject.optString("streetAddress2"));
                        consumer.setId(jsonObject.optString("id"));
                        consumer.setAddressId(jsonObject.optString("id"));
                        consumer.setAddressId(jsonObject.optString("id"));
                        //consumer.setAddressLineTwo(jsonObject.optString("city"));
                        consumer.setContactNumber(jsonObject.optString("mobileNumber"));
                        consumer.setPinCode(jsonObject.optString("pincode"));
                        consumer.setLandMark(jsonObject.optString("landmark"));

//                        if(Globaluse.offlinedeliver)//sat
//                        {
//                            consumer.setDeliveryHere(true);
//                        }else
//                        {
//                            consumer.setDeliveryHere(jsonObject.optBoolean("isDeliveryHere"));
//                        }

                        consumer.setDeliveryHere(jsonObject.optBoolean("isDeliveryHere"));

                        consumer.setLatitude(jsonObject.optString("latitude"));
                        consumer.setLongtitude(jsonObject.optString("longtitude"));
                        addressList.add(consumer);
                    }
                    if (addressList.isEmpty()) {

                    }
                    recyclerViewAddress.setAdapter(new ConsumerHomeAdapters(consumerCheckOutActivity, addressList, RobotoBold, RobotoRegular, poppinsSemiBold, poppinsBold, poppinsLight, poppinsMedium, from));

                    if (recyclerViewAddress.getAdapter().getItemCount() == 0) {
                        editAddressButton.setVisibility(View.GONE);
                    } else {
                        editAddressButton.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerCheckOutActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerCheckOutActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerCheckOutActivity);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(consumerCheckOutActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerCheckOutActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "delivery_address_taq");
    }


    /**
     * To Place an Order and Move to Payment Page(POST)
     **/
    public void placeOrder() {
        String url = APIBaseURL.placeOrder + SaveSharedPreference.getLoggedInUserEmail(ConsumerCheckOutActivity.this);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                isOrderPlacedAPI = true;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    orderNumber = dataObject.optString("orderId");

                    if((offlineDelivery.isChecked())&&(offlinePayment.isChecked()))
                    {
                        Globaluse.orderInvoiceNo_str=orderNumber;

                    }else
                    {
                        Intent intent = new Intent(ConsumerCheckOutActivity.this, CashFreePaymentWebActivity.class);
                        intent.putExtra("paymentLink", dataObject.optString("paymentLink"));
                        startActivityForResult(intent, 103);

                    }

                  //  Globaluse.orderInvoiceNo_str=orderNumber;


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ConsumerCheckOutActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerCheckOutActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConsumerCheckOutActivity.this);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(consumerCheckOutActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerCheckOutActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "place_order_taq");

    }








    /**
     * To Place an Order Offline Page(POST)
     **/
    public void OfflineplaceOrder() {
        String url = APIBaseURL.offlinepay+SaveSharedPreference.getLoggedInUserEmail(ConsumerCheckOutActivity.this)+"/"+true+"/"+true;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    offlineorderDialog();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ConsumerCheckOutActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerCheckOutActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConsumerCheckOutActivity.this);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(consumerCheckOutActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerCheckOutActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "place_offline_order");

    }








    // Override Method Onactivity Result Method For Add and Edit Address
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            getAvailableDeliveryAddresses(1);
        } else if (resultCode == 102) {
            getAvailableDeliveryAddresses(1);
        } else if (resultCode == 103) {
            assert data != null;
            String orderID = data.getStringExtra("OrderID");
            String status = data.getStringExtra("status");
            try {
                if (status.equals("SUCCESS")) {
                    SuperActivityToast.create(ConsumerCheckOutActivity.this)
                            .setProgressBarColor(Color.WHITE)
                            .setText("Your Order is received")
                            .setDuration(Style.DURATION_SHORT)
                            .setFrame(Style.FRAME_KITKAT)
                            .setColor(getResources().getColor(R.color.colorWhite))
                            .setTextColor(getResources().getColor(R.color.colorBlack))
                            .setAnimations(Style.ANIMATIONS_FLY).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerCheckOutActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.consumer_checkout_dialogue, null);
                    TextView header = dialogLayout.findViewById(R.id.header);
                    TextView subHeader = dialogLayout.findViewById(R.id.subHeader);
                    Button trackOrderButton = dialogLayout.findViewById(R.id.trackOrderButton);
                    trackOrderButton.setVisibility(View.GONE);
                    Button exploreFoodButton = dialogLayout.findViewById(R.id.exploreFoodButton);
                    RecyclerView recyclerView = dialogLayout.findViewById(R.id.recyclerView);
                    header.setTypeface(RobotoBold);
                    subHeader.setTypeface(poppinsMedium);
                    subHeader.setText("Invoice No: " + getOrderNoWithDashes(orderID, "-", 4));

                    trackOrderButton.setTypeface(poppinsBold);
                    exploreFoodButton.setTypeface(poppinsBold);

                    builder.setView(dialogLayout);
                    AlertDialog alertDialog = builder.create();
                    trackOrderButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();
                            Intent intent = new Intent(ConsumerCheckOutActivity.this, TrackOrderActivity.class);
                            intent.putExtra("OrderID", orderID);
                            intent.putExtra("From", "Checkout");
                            startActivity(intent);
                            finish();
                        }
                    });
                    exploreFoodButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            Intent intent = new Intent(ConsumerCheckOutActivity.this, ConsumerMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                    getOrdersListFromInvoiceNumber(orderID, recyclerView, alertDialog);
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                } else {
                    SuperActivityToast.create(ConsumerCheckOutActivity.this)
                            .setProgressBarColor(Color.WHITE)
                            .setText("Your Payment has beed failed")
                            .setDuration(Style.DURATION_SHORT)
                            .setFrame(Style.FRAME_KITKAT)
                            .setColor(getResources().getColor(R.color.colorWhite))
                            .setTextColor(getResources().getColor(R.color.colorBlack))
                            .setAnimations(Style.ANIMATIONS_FLY).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * If Delivery Address is Empty ,Then ask User to Add New Address by Showing Pop up Dialog
     **/
    private void addAddressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerCheckOutActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.consumer_checkout_dialogue, null);
        TextView header = dialogLayout.findViewById(R.id.header);
        TextView subHeader = dialogLayout.findViewById(R.id.subHeader);
        Button trackOrderButton = dialogLayout.findViewById(R.id.trackOrderButton);
        Button exploreFoodButton = dialogLayout.findViewById(R.id.exploreFoodButton);
        TextView okButton = dialogLayout.findViewById(R.id.okButton);
        ImageView tickIcon = dialogLayout.findViewById(R.id.tickIcon);
        header.setTypeface(poppinsBold);
        subHeader.setTypeface(poppinsLight);
        header.setTextSize(22);
        header.setText("Add Address");
        subHeader.setText("Please add your address to deliver on time");
        subHeader.setTextSize(16);
        tickIcon.setVisibility(View.GONE);
        okButton.setVisibility(View.VISIBLE);
        trackOrderButton.setVisibility(View.GONE);
        exploreFoodButton.setVisibility(View.GONE);
        builder.setView(dialogLayout);
        AlertDialog alertDialog = builder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumerCheckOutActivity.this, Address.class);
                intent.putExtra("AddorEdit", "Add");
                intent.putExtra("emailId", SaveSharedPreference.getLoggedInUserEmail(ConsumerCheckOutActivity.this));
                startActivityForResult(intent, 101);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    /**
     * After Payment Done get the Order Invoice Number and show popup
     **/
    public void getOrdersListFromInvoiceNumber(String invoiceNumber, RecyclerView recyclerView, AlertDialog alertDialog) {
        String url = APIBaseURL.getOrdersListFromInvoiceNumber + invoiceNumber;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("responseFromInvoice", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");

                        JSONArray cartDetailsArray = new JSONArray();
                        if (dataObject.has("cartDetails")) {
                            cartDetailsArray = dataObject.getJSONArray("cartDetails");
                        }
                        List<Chef> chefList = new ArrayList<>();
                        for (int i = 0; i < cartDetailsArray.length(); i++) {
                            JSONObject cartDetailsObject = cartDetailsArray.getJSONObject(i);
                            Chef chef = new Chef();
                            chef.setName(cartDetailsObject.optString("chefName"));
                            chef.setId(cartDetailsObject.optString("chefId"));
                            chef.setSeperateOrderNo(cartDetailsObject.optString("orderNo"));
                            chef.setStartDate(dateFormat(cartDetailsObject.optString("orderCreatedDate")));
                            chefList.add(chef);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(ConsumerCheckOutActivity.this));
                        recyclerView.setAdapter(new InvoiceOrdersCardAdapter(ConsumerCheckOutActivity.this, chefList, alertDialog));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ConsumerCheckOutActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerCheckOutActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConsumerCheckOutActivity.this);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(consumerCheckOutActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerCheckOutActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "order_with_invoice");

    }

    /**
     * get order number with dashes for every 4 digits..
     **/

    public String getOrderNoWithDashes(String orderNo, String insert, int period) {
        StringBuilder builder = new StringBuilder(
                orderNo.length() + insert.length() * (orderNo.length() / period) + 1);

        int index = 0;
        String prefix = "";
        while (index < orderNo.length()) {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            prefix = insert;
            builder.append(orderNo.substring(index,
                    Math.min(index + period, orderNo.length())));
            index += period;
        }
        return builder.toString();
    }

    /**
     * get date in desired format..
     **/
    private String dateFormat(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        String formattedDate = null;
        try {
            serverDate = df.parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm");

            outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = outputFormat.format(serverDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
//        //Initialize Google Play Services
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                buildGoogleApiClient();
//                mMap.setMyLocationEnabled(true);
//            }
//        } else {
//            buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                onBackPressed();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //Execute your code here
        if (framelayout_id.getVisibility() == View.VISIBLE) {
            // Its visible
            framelayout_id.setVisibility(View.GONE);
        } else {
            // Either gone or invisible
            finish();
        }

    }












    /**
     * To offline_privilege(Get)
     **/
    public void offlinePrivilege() {
        String url =APIBaseURL.getprivilege+SaveSharedPreference.getLoggedInUserEmail(ConsumerCheckOutActivity.this);
        // String url =APIBaseURL.getprivilege+"ramkumar@alphasource.in";
        // String url =APIBaseURL.getprivilege+"satcatbat@gmail.com";

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                chk_status= Boolean.parseBoolean(response);
                if (chk_status)
                {
                    latePayment.setVisibility(View.VISIBLE);
                }else
                {
                    latePayment.setVisibility(View.GONE);
                }
            }
//
//            @Override
//            public void onResponse(Boolean response) {
//
//                if (response)
//                {
//                    latePayment.setVisibility(View.VISIBLE);
//                }else
//                {
//                    latePayment.setVisibility(View.GONE);
//                }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ConsumerCheckOutActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerCheckOutActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConsumerCheckOutActivity.this);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(consumerCheckOutActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerCheckOutActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "privilege");



//        BooleanRequest booleanRequest = new BooleanRequest(1, url, new Response.Listener<Boolean>()
//            {
//            @Override
//            public void onResponse(Boolean response) {
//                Toast.makeText(ConsumerCheckOutActivity.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ConsumerCheckOutActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }




    public void offlineorderDialog() {

        String orderID = Globaluse.orderInvoiceNo_str;
        String status = "SUCCESS";
        try {
            if (status.equals("SUCCESS")) {
                SuperActivityToast.create(ConsumerCheckOutActivity.this)
                        .setProgressBarColor(Color.WHITE)
                        .setText("Your Order is received")
                        .setDuration(Style.DURATION_SHORT)
                        .setFrame(Style.FRAME_KITKAT)
                        .setColor(getResources().getColor(R.color.colorWhite))
                        .setTextColor(getResources().getColor(R.color.colorBlack))
                        .setAnimations(Style.ANIMATIONS_FLY).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerCheckOutActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.consumer_checkout_dialogue, null);
                TextView header = dialogLayout.findViewById(R.id.header);
                TextView subHeader = dialogLayout.findViewById(R.id.subHeader);
                Button trackOrderButton = dialogLayout.findViewById(R.id.trackOrderButton);
                trackOrderButton.setVisibility(View.GONE);
                Button exploreFoodButton = dialogLayout.findViewById(R.id.exploreFoodButton);
                RecyclerView recyclerView = dialogLayout.findViewById(R.id.recyclerView);
                header.setTypeface(RobotoBold);
                subHeader.setTypeface(poppinsMedium);
                subHeader.setText("Invoice No: " + getOrderNoWithDashes(orderID, "-", 4));

                trackOrderButton.setTypeface(poppinsBold);
                exploreFoodButton.setTypeface(poppinsBold);

                builder.setView(dialogLayout);
                AlertDialog alertDialog = builder.create();
                trackOrderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                        Intent intent = new Intent(ConsumerCheckOutActivity.this, TrackOrderActivity.class);
                        intent.putExtra("OrderID", orderID);
                        intent.putExtra("From", "Checkout");
                        startActivity(intent);
                        finish();
                    }
                });
                exploreFoodButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Intent intent = new Intent(ConsumerCheckOutActivity.this, ConsumerMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                getOrdersListFromInvoiceNumber(orderID, recyclerView, alertDialog);
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
            } else {
                SuperActivityToast.create(ConsumerCheckOutActivity.this)
                        .setProgressBarColor(Color.WHITE)
                        .setText("Your Payment has beed failed")
                        .setDuration(Style.DURATION_SHORT)
                        .setFrame(Style.FRAME_KITKAT)
                        .setColor(getResources().getColor(R.color.colorWhite))
                        .setTextColor(getResources().getColor(R.color.colorBlack))
                        .setAnimations(Style.ANIMATIONS_FLY).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






    public void gotoMap()
    {

        boolean checkAvailability = isAllItemsAreNotSoldOut(cartDetailsListssArray);
        if (checkAvailability) {
            new AlertDialog.Builder(ConsumerCheckOutActivity.this)
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

        } else {
            if (recyclerViewAddress.getAdapter().getItemCount() == 0) {
                addAddressDialog();
            } else if (selectedAddress.equals("")) {
                Toast.makeText(ConsumerCheckOutActivity.this, "Please select Delivery Address", Toast.LENGTH_SHORT).show();
            } else {


                //   Toast.makeText(ConsumerCheckOutActivity.this, "Apologies, our services are limited to serviceable areas with in the city only", Toast.LENGTH_SHORT).show();

//
//                if (checkAddressRadius) {
//
//
//                // placeOrder();
//
//                //selectedlatitude;
//                // selectedlongtitude;
//                framelayout_id.setVisibility(View.VISIBLE);
//                mMap.clear();
//                if (mCurrLocationMarker != null) {
//                    mCurrLocationMarker.remove();
//                }
//                // drawPolylines();
//                ArrayList<HashMap<String,String>> dataSet =  new ArrayList<HashMap<String,String>>(center_name_arraylist);
//                for(int i=0;i<dataSet.size();i++)
//                {
//
//                    String consumerEmailId= (dataSet.get(i).get("consumerEmailId"));
//                    String chef_name= (dataSet.get(i).get("chef_name"));
//                    String streetAddress1= (dataSet.get(i).get("streetAddress1"));
//                    String streetAddress2= (dataSet.get(i).get("streetAddress2"));
//                    String city= (dataSet.get(i).get("city"));
//                    String state= (dataSet.get(i).get("state"));
//                    String country= (dataSet.get(i).get("country"));
//                    String mobileNumber= (dataSet.get(i).get("mobileNumber"));
//                    String consumer_name= (dataSet.get(i).get("consumer_name"));
//                    double chef_latitude= Double.parseDouble((dataSet.get(i).get("chef_latitude")));
//                    double chef_longtitude= Double.parseDouble((dataSet.get(i).get("chef_longtitude")));
//                    double consumer_latitude= Double.parseDouble((dataSet.get(i).get("consumer_latitude")));
//                    double consumer_longtitude= Double.parseDouble((dataSet.get(i).get("consumer_longtitude")));
//                    String landmark= (dataSet.get(i).get("landmark"));
//
//                    LatLng source = new LatLng(chef_latitude, chef_longtitude);
//                    LatLng destination = new LatLng(consumer_latitude, consumer_longtitude);
//
//                    if(i==0)
//                    {
//                        new GetPathFromLocation(source, destination, new DirectionPointListener() {
//                            @Override
//                            public void onPath(PolylineOptions polyLine) {
//                                mMap.addPolyline(polyLine);
//                                mCurrLocationMarker = mMap.addMarker(new MarkerOptions().position(source)
//                                        .title(chef_name));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLng(source));
//                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//                                        .target(mMap.getCameraPosition().target)
//                                        .zoom(14)
//                                        .bearing(30)
//                                        .tilt(45)
//                                        .build()));
//
//                                MarkerOptions markerOptions = new MarkerOptions();
//                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                                mMap.addMarker(markerOptions.position(destination).title(consumer_name));
//
//                                //mCurrLocationMarker = mMap.addMarker(markerOptions);
//
////                                mMap.addMarker(new MarkerOptions().position(destination)
////                                        .title(consumer_name));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
//                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//                                        .target(mMap.getCameraPosition().target)
//                                        .zoom(14)
//                                        .bearing(30)
//                                        .tilt(45)
//                                        .build()));
//
//
//                            }
//                        }).execute();
//                    }else
//                    {
//                        new GetPathFromLocation(source, destination, new DirectionPointListener() {
//                            @Override
//                            public void onPath(PolylineOptions polyLine) {
//                                mMap.addPolyline(polyLine);
//                                mCurrLocationMarker =mMap.addMarker(new MarkerOptions().position(source)
//                                        .title(chef_name));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLng(source));
//                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//                                        .target(mMap.getCameraPosition().target)
//                                        .zoom(14)
//                                        .bearing(30)
//                                        .tilt(45)
//                                        .build()));
//
//                            }
//                        }).execute();
//                    }
//
//
//
//                }
//
//                } else {
//
//                    Toast.makeText(ConsumerCheckOutActivity.this, "Apologies, our services are limited to serviceable areas with in the city only", Toast.LENGTH_SHORT).show();
//
//                }





                if (checkAddressRadius) {
                    //gototomap();


                    if((offlineDelivery.isChecked()))
                    {
                        if((offlineDelivery.isChecked())&&(offlinePayment.isChecked()))
                        {
                            gototomap();

                        }else
                        {
                            Toast.makeText(ConsumerCheckOutActivity.this, "Please check offline delivery and offline payment", Toast.LENGTH_SHORT).show();
                        }

                    }else
                    {
                        gototomap();
                    }



                } else {




                    if((offlineDelivery.isChecked()))
                    {
                        if((offlineDelivery.isChecked())&&(offlinePayment.isChecked()))
                        {
                            gototomap();

                        }else
                        {
                            Toast.makeText(ConsumerCheckOutActivity.this, "Please check offline delivery and offline payment", Toast.LENGTH_SHORT).show();
                        }

                    }else
                    {
                        Toast.makeText(ConsumerCheckOutActivity.this, "Apologies, our services are limited to serviceable areas with in the city only", Toast.LENGTH_SHORT).show();

                    }

//                    if((offlineDelivery.isChecked())&&(offlinePayment.isChecked()))
//                    {
//                        gototomap();
//                    }else
//                    {
//                        Toast.makeText(ConsumerCheckOutActivity.this, "Apologies, our services are limited to serviceable areas with in the city only", Toast.LENGTH_SHORT).show();
//
//                    }


                }

            }
        }


    }






    public void gototomap() {
        // placeOrder();

        //selectedlatitude;
        // selectedlongtitude;
        framelayout_id.setVisibility(View.VISIBLE);
        mMap.clear();
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        // drawPolylines();
        ArrayList<HashMap<String,String>> dataSet =  new ArrayList<HashMap<String,String>>(center_name_arraylist);
        for(int i=0;i<dataSet.size();i++)
        {

            String consumerEmailId= (dataSet.get(i).get("consumerEmailId"));
            String chef_name= (dataSet.get(i).get("chef_name"));
            String streetAddress1= (dataSet.get(i).get("streetAddress1"));
            String streetAddress2= (dataSet.get(i).get("streetAddress2"));
            String city= (dataSet.get(i).get("city"));
            String state= (dataSet.get(i).get("state"));
            String country= (dataSet.get(i).get("country"));
            String mobileNumber= (dataSet.get(i).get("mobileNumber"));
            String consumer_name= (dataSet.get(i).get("consumer_name"));
            double chef_latitude= Double.parseDouble((dataSet.get(i).get("chef_latitude")));
            double chef_longtitude= Double.parseDouble((dataSet.get(i).get("chef_longtitude")));
            double consumer_latitude= Double.parseDouble((dataSet.get(i).get("consumer_latitude")));
            double consumer_longtitude= Double.parseDouble((dataSet.get(i).get("consumer_longtitude")));
            String landmark= (dataSet.get(i).get("landmark"));

            LatLng source = new LatLng(chef_latitude, chef_longtitude);
            LatLng destination = new LatLng(consumer_latitude, consumer_longtitude);

            if(i==0)
            {
                new GetPathFromLocation(source, destination, new DirectionPointListener() {
                    @Override
                    public void onPath(PolylineOptions polyLine) {
                        mMap.addPolyline(polyLine);
                        mCurrLocationMarker = mMap.addMarker(new MarkerOptions().position(source)
                                .title(chef_name));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(source));
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(mMap.getCameraPosition().target)
                                .zoom(14)
                                .bearing(30)
                                .tilt(45)
                                .build()));

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mMap.addMarker(markerOptions.position(destination).title(consumer_name));

                        //mCurrLocationMarker = mMap.addMarker(markerOptions);

//                                mMap.addMarker(new MarkerOptions().position(destination)
//                                        .title(consumer_name));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(mMap.getCameraPosition().target)
                                .zoom(14)
                                .bearing(30)
                                .tilt(45)
                                .build()));


                    }
                }).execute();
            }else
            {
                new GetPathFromLocation(source, destination, new DirectionPointListener() {
                    @Override
                    public void onPath(PolylineOptions polyLine) {
                        mMap.addPolyline(polyLine);
                        mCurrLocationMarker =mMap.addMarker(new MarkerOptions().position(source)
                                .title(chef_name));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(source));
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(mMap.getCameraPosition().target)
                                .zoom(14)
                                .bearing(30)
                                .tilt(45)
                                .build()));

                    }
                }).execute();
            }



        }
    }



    private class BooleanRequest extends Request<Boolean> {
        private final Response.Listener<Boolean> mListener;
        private final Response.ErrorListener mErrorListener;
        // private final String mRequestBody;

        private final String PROTOCOL_CHARSET = "utf-8";
        private final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);

        public BooleanRequest(int method, String url, Response.Listener<Boolean> listener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.mListener = listener;
            this.mErrorListener = errorListener;
            // this.mRequestBody = requestBody;
        }

        @Override
        protected Response<Boolean> parseNetworkResponse(NetworkResponse response) {
            Boolean parsed;
            try {
                parsed = Boolean.valueOf(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
            } catch (UnsupportedEncodingException e) {
                parsed = Boolean.valueOf(new String(response.data));
            }
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        }

        @Override
        protected VolleyError parseNetworkError(VolleyError volleyError) {
            return super.parseNetworkError(volleyError);
        }

        @Override
        protected void deliverResponse(Boolean response) {
            mListener.onResponse(response);
        }

        @Override
        public void deliverError(VolleyError error) {
            mErrorListener.onErrorResponse(error);
        }

        @Override
        public String getBodyContentType() {
            return PROTOCOL_CONTENT_TYPE;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();

            headers.put("Authorization","Bearer "+ SaveSharedPreference.getAzureToken(ConsumerCheckOutActivity.this));
            Log.v("accessToken",SaveSharedPreference.getAzureToken(ConsumerCheckOutActivity.this));
            return headers;
        }

//        @Override
//        public byte[] getBody() throws AuthFailureError {
////            try {
////                return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
////            } catch (UnsupportedEncodingException uee) {
////                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
////                        mRequestBody, PROTOCOL_CHARSET);
////                return null;
////            }
//        }
    }
}
