package stayabode.foodyHive.activities.consumers;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnyThread;
import androidx.annotation.ColorRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;

import stayabode.foodyHive.authentication.AuthStateManager;
import stayabode.foodyHive.authentication.BrowserSelectionAdapter;
import stayabode.foodyHive.authentication.Configuration;
import stayabode.foodyHive.authentication.TokenActivity;
import stayabode.foodyHive.constants.APIBaseURL;

import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.android.material.snackbar.Snackbar;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.RegistrationRequest;
import net.openid.appauth.RegistrationResponse;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.browser.AnyBrowserMatcher;
import net.openid.appauth.browser.BrowserMatcher;
import net.openid.appauth.browser.ExactBrowserMatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments;


public class ItemDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Typeface poppinsMedium;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface poppinsLight;
    Typeface robotoMedium;


    TextView itemName;
    TextView oldPrice;
    TextView savedPrice;
    TextView newPrice;
    TextView mealcontainsText;
    TextView minsText;
    TextView availableText;
    TextView protein;
    TextView carbs;
    TextView fiberText;
    TextView fatText;
    TextView mealTypeHeader;
    TextView qtyHeader;
    RadioGroup radioGroup;
    RadioButton smallRB;
    RadioButton mediumRB;
    RadioButton largeRB;
    TextView decrease;
    TextView increase;
    TextView itemCount;
    TextView totalAddedText;
    TextView speciaLRequestHeader;
    TextView preferencesHeader;
    EditText tabEnterEditText;
    TextView subTotalbreakUpHeader;
    TextView mealHeader;
    TextView taxHeader;
    TextView mealPrice;
    TextView taxPrice;
    TextView deliveryChargeHeader;
    TextView deliveryPrice;
    TextView subTotalHeader;
    TextView subTotalPrice;
    TextView toaltPriceText;
    TextView saveText;
    Button addItemsButton;
    Button checkOutButton;
    ProgressBar proteinprogressBar;
    ProgressBar carbsprogressBar;
    ProgressBar fibreprogressBar;
    ProgressBar fatprogressBar;
    TextView proteinCount;
    TextView carbsCount;
    TextView fibreCount;
    TextView fatCount;
    TextView preparedChefname;
    ImageView chefImage;
    TextView totalReviewCountText;
    TextView avgRating;
    TextView infoLinkText;
    TextView reviewsLink;
    TextView discountPercent;
    TextView oldPriceReSymbol;

    FragmentManager consumerItemReviewFragment;
    //    ShimmerFrameLayout shimmerFrameLayout;
//    NestedScrollView scrollLayout;
//    ShimmerFrameLayout shimmerLayoutForImage;
    ImageView iv_class_image;
//    LinearLayout bottomButton;

    String dishID = "";
    String dishName = "";
    String dishImage = "";
    int dishPrice = 0;
    String chefID = "";
    int quantity = 1;
    LinearLayout rootLayout;
    ProgressBar progressBar;
    LinearLayout bottomButton;


    int getServerCartQuantity = 0;

    int availableCount = 0;
    boolean hasQuantity=true;
    String ratingsCount="";

    private static final String TAG = "IntroActivity";

    private static final String EXTRA_FAILED = "failed";
    private static final int RC_AUTH = 100;

    private AuthorizationService mAuthService;
    private AuthStateManager mAuthStateManager;
    private Configuration mConfiguration;

    private final AtomicReference<String> mClientId = new AtomicReference<>();
    private final AtomicReference<AuthorizationRequest> mAuthRequest = new AtomicReference<>();
    private final AtomicReference<CustomTabsIntent> mAuthIntent = new AtomicReference<>();
    private CountDownLatch mAuthIntentLatch = new CountDownLatch(1);
    private static ExecutorService mExecutor;

    private static boolean mUsePendingIntents;

    @NonNull
    private BrowserMatcher mBrowserMatcher = AnyBrowserMatcher.INSTANCE;

    public static ItemDetailsActivity itemDetailsActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExecutor = Executors.newSingleThreadExecutor();
        mAuthStateManager = AuthStateManager.getInstance(this);
        mConfiguration = Configuration.getInstance(this);
        setContentView(R.layout.activity_item_details);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        itemDetailsActivity = this;
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        robotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_for_consmer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);

        iv_class_image = findViewById(R.id.iv_class_image);
        rootLayout = findViewById(R.id.rootLayout);
        progressBar = findViewById(R.id.progressBar);
        bottomButton = findViewById(R.id.bottomButton);
        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
        bottomButton.setVisibility(View.GONE);

        itemName = findViewById(R.id.itemName);
        oldPrice = findViewById(R.id.oldPrice);
        savedPrice = findViewById(R.id.savedPrice);
        newPrice = findViewById(R.id.newPrice);
        mealcontainsText = findViewById(R.id.mealcontainsText);
        minsText = findViewById(R.id.minsText);
        availableText = findViewById(R.id.availableText);
        protein = findViewById(R.id.protein);
        carbs = findViewById(R.id.carbs);
        fiberText = findViewById(R.id.fiberText);
        fatText = findViewById(R.id.fatText);
        mealTypeHeader = findViewById(R.id.mealTypeHeader);
        radioGroup = findViewById(R.id.radioGroup);
        smallRB = findViewById(R.id.smallRB);
        mediumRB = findViewById(R.id.mediumRB);
        largeRB = findViewById(R.id.largeRB);
        qtyHeader = findViewById(R.id.qtyHeader);
        decrease = findViewById(R.id.decrease);
        increase = findViewById(R.id.increase);
        itemCount = findViewById(R.id.itemCount);
        avgRating = findViewById(R.id.avgRating);

        totalAddedText = findViewById(R.id.totalAddedText);
        speciaLRequestHeader = findViewById(R.id.speciaLRequestHeader);
        tabEnterEditText = findViewById(R.id.tabEnterEditText);
        preferencesHeader = findViewById(R.id.preferencesHeader);
        subTotalbreakUpHeader = findViewById(R.id.subTotalbreakUpHeader);
        mealHeader = findViewById(R.id.mealHeader);
        taxHeader = findViewById(R.id.taxHeader);
        mealPrice = findViewById(R.id.mealPrice);
        taxPrice = findViewById(R.id.taxPrice);
        deliveryChargeHeader = findViewById(R.id.deliveryChargeHeader);
        deliveryPrice = findViewById(R.id.deliveryPrice);
        subTotalHeader = findViewById(R.id.subTotalHeader);
        subTotalPrice = findViewById(R.id.subTotalPrice);
        toaltPriceText = findViewById(R.id.toaltPriceText);

        addItemsButton = findViewById(R.id.addItemsButton);
        checkOutButton = findViewById(R.id.checkOutButton);
        proteinprogressBar = findViewById(R.id.proteinprogressBar);
        carbsprogressBar = findViewById(R.id.carbsprogressBar);
        fibreprogressBar = findViewById(R.id.fibreprogressBar);
        fatprogressBar = findViewById(R.id.fatprogressBar);
        proteinCount = findViewById(R.id.proteinCount);
        carbsCount = findViewById(R.id.carbsCount);
        fibreCount = findViewById(R.id.fibreCount);
        fatCount = findViewById(R.id.fatCount);
        infoLinkText = findViewById(R.id.infoLinkText);
        preparedChefname = findViewById(R.id.preparedChefname);
        chefImage = findViewById(R.id.chefImage);
        totalReviewCountText = findViewById(R.id.totalReviewCountText);
        reviewsLink = findViewById(R.id.reviewsLink);
        saveText = findViewById(R.id.saveText);
        discountPercent = findViewById(R.id.discountPercent);
        oldPriceReSymbol = findViewById(R.id.oldPriceReSymbol);

        mealcontainsText.setTypeface(poppinsBold);
        itemName.setTypeface(poppinsSemiBold);
        oldPrice.setTypeface(poppinsMedium);
        savedPrice.setTypeface(poppinsMedium);
        newPrice.setTypeface(poppinsMedium);
        availableText.setTypeface(poppinsLight);
        minsText.setTypeface(poppinsLight);
        protein.setTypeface(poppinsLight);
        carbs.setTypeface(poppinsLight);
        fiberText.setTypeface(poppinsLight);
        totalReviewCountText.setTypeface(poppinsLight);
        reviewsLink.setTypeface(poppinsLight);
        infoLinkText.setTypeface(poppinsLight);
        preparedChefname.setTypeface(poppinsLight);
        fatText.setTypeface(poppinsLight);
        proteinCount.setTypeface(poppinsSemiBold);
        carbsCount.setTypeface(poppinsSemiBold);
        fibreCount.setTypeface(poppinsSemiBold);
        fatCount.setTypeface(poppinsSemiBold);
        mealTypeHeader.setTypeface(poppinsLight);
        smallRB.setTypeface(poppinsMedium);
        mediumRB.setTypeface(poppinsMedium);
        largeRB.setTypeface(poppinsMedium);
        qtyHeader.setTypeface(poppinsLight);
        decrease.setTypeface(robotoMedium);
        increase.setTypeface(robotoMedium);
        itemCount.setTypeface(poppinsSemiBold);
        totalAddedText.setTypeface(poppinsLight);
        speciaLRequestHeader.setTypeface(poppinsLight);
        tabEnterEditText.setTypeface(poppinsLight);
        subTotalbreakUpHeader.setTypeface(poppinsLight);
        preferencesHeader.setTypeface(poppinsLight);
        mealHeader.setTypeface(poppinsMedium);
        mealPrice.setTypeface(poppinsMedium);
        taxHeader.setTypeface(poppinsMedium);
        taxPrice.setTypeface(poppinsMedium);
        deliveryChargeHeader.setTypeface(poppinsMedium);
        deliveryPrice.setTypeface(poppinsMedium);
        subTotalHeader.setTypeface(poppinsBold);
        subTotalPrice.setTypeface(poppinsBold);
        addItemsButton.setTypeface(poppinsBold);
        checkOutButton.setTypeface(poppinsBold);
        toaltPriceText.setTypeface(poppinsMedium);
        saveText.setTypeface(poppinsMedium);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));



        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SaveSharedPreference.getLoggedInUserRole(ItemDetailsActivity.this).equals("")) {
                    ViewGroup viewGroup = v.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ItemDetailsActivity.this).inflate(R.layout.sign_in_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsActivity.this);

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            startAuth();

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
                    alertDialog.show();

                } else {
                    if(availableCount==0){
                        Toast.makeText(ItemDetailsActivity.this,"Item sold out",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (getServerCartQuantity == 0) {
                            try {
                                addToCart(dishID, dishName, dishImage, chefID, dishPrice,"checkout");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                updateCartCount(dishID, dishPrice, itemCount, "update", dishName, dishImage, chefID,"checkout");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }
            }
        });

        addItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SaveSharedPreference.getLoggedInUserRole(ItemDetailsActivity.this).equals("")) {
                    ViewGroup viewGroup = v.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ItemDetailsActivity.this).inflate(R.layout.sign_in_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsActivity.this);

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            startAuth();

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
                    alertDialog.show();

                } else {
                    if(availableCount==0){
                        Toast.makeText(ItemDetailsActivity.this,"Item sold out",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (getServerCartQuantity == 0) {
                            try {
                                addToCart(dishID, dishName, dishImage, chefID, dishPrice,"add");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                updateCartCount(dishID, dishPrice, itemCount, "update", dishName, dishImage, chefID,"add");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }


            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(itemCount.getText().toString());
                qty++;
                if(availableCount==0) {
                    Toast.makeText(ItemDetailsActivity.this,"Item sold out",Toast.LENGTH_SHORT).show();
                }
               else if (qty > availableCount) {
                    Toast.makeText(ItemDetailsActivity.this, "Only quantity of "+availableCount+" available", Toast.LENGTH_SHORT).show();

                } else {
                    itemCount.setText(String.valueOf(qty));
                    // subTotalPrice.setText(String.valueOf(dishPrice * qty));
                    subTotalPrice.setText(String.format("%.2f", Double.valueOf(dishPrice) * qty));
                    totalAddedText.setText("(" + String.valueOf(itemCount.getText().toString()) + ")Items Added to your basket");
                }


            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(itemCount.getText().toString());
                if (qty > 1) {
                    qty--;
                    itemCount.setText(String.valueOf(qty));
                    //subTotalPrice.setText(String.valueOf(dishPrice * qty));
                    subTotalPrice.setText(String.format("%.2f", Double.valueOf(dishPrice) * qty));
                    totalAddedText.setText("(" + String.valueOf(itemCount.getText().toString()) + ")Items Added to your basket");
                } else {
                }
            }
        });

        infoLinkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, CookedChefItemDetailActivity.class);
                intent.putExtra("chefId", chefID);
                intent.putExtra("dishID", dishID);
                intent.putExtra("chefName", preparedChefname.getText().toString());
                startActivity(intent);
            }
        });

        getDishDetailsById();

        reviewsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, ConsumerItemReview.class);
                intent.putExtra("itemName", itemName.getText().toString());
                intent.putExtra("chefId", getIntent().getStringExtra("chefId"));
                intent.putExtra("dishId", getIntent().getStringExtra("Id"));
                intent.putExtra("ratingCount", getIntent().getStringExtra("ratingCount"));
                intent.putExtra("ratingsCount",ratingsCount );
                startActivity(intent);
            }
        });

        configureBrowserSelector();
        if (mConfiguration.hasConfigurationChanged()) {
            // discard any existing authorization state due to the change of configuration
            Log.i(TAG, "Configuration change detected, discarding old state");
            mAuthStateManager.replace(new AuthState());
            mConfiguration.acceptConfiguration();
        }

        if (getIntent().getBooleanExtra(EXTRA_FAILED, false)) {
            displayAuthCancelled();
        }

        displayLoading("Initializing");
        mExecutor.submit(this::initializeAppAuth);
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
     To Get the Dish Details make an GET  Call API(GET)
     **/
    public void getDishDetailsById() {



        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
        bottomButton.setVisibility(View.GONE);
        String url = APIBaseURL.getDishByFullId + getIntent().getStringExtra("Id") + "?userId=" + SaveSharedPreference.getLoggedInUserEmail(ItemDetailsActivity.this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                rootLayout.setVisibility(View.VISIBLE);
                bottomButton.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    int discountedPRice = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(dataObject.optString("price")) - Double.parseDouble(dataObject.optString("discountedPrice")))));
                    int qunatity = Integer.parseInt(dataObject.optString("cartQuantity"));


                    dishPrice = dataObject.optInt("discountedPrice");
                    try {
                        Glide.with(ItemDetailsActivity.this).load(dataObject.getJSONArray("dishImagePath").get(0).toString()).placeholder(R.drawable.foodi_logo_left_image).into(iv_class_image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    itemName.setText(dataObject.optString("dishName"));
                    dishID = dataObject.optString("dishId");
                    dishName = dataObject.optString("dishName");
                    mealPrice.setText(String.format("%.2f", Double.valueOf(dataObject.optString("discountedPrice"))));
                    oldPrice.setText(String.format("%.2f", dataObject.optDouble("price")));
                    oldPrice.setBackground(getDrawable(R.drawable.strike_through));

                    newPrice.setText(String.format("%.2f", Double.valueOf(dataObject.optString("discountedPrice"))));
                    mealcontainsText.setText("This meal " + dataObject.optString("weight") + "g Contain");
                    totalReviewCountText.setText("based on " + dataObject.optString("ratingsCount") + " reviews");
                    ratingsCount=dataObject.optString("ratingsCount");
                    avgRating.setText(dataObject.optString("ratingAverage"));
                    if(dataObject.optInt("discountedPercent")!=0) {
                        discountPercent.setVisibility(View.VISIBLE);
                        oldPrice.setVisibility(View.VISIBLE);
                        oldPriceReSymbol.setVisibility(View.VISIBLE);
                        discountPercent.setText(dataObject.optInt("discountedPercent") + "%\nOFF");
                    }else {
                        oldPrice.setVisibility(View.GONE);
                        discountPercent.setVisibility(View.GONE);
                        oldPriceReSymbol.setVisibility(View.GONE);
                    }

                    savedPrice.setText(String.format("%.2f", Double.valueOf(discountedPRice)));
                    dishImage = dataObject.getJSONArray("dishImagePath").get(0).toString();
                    chefID = dataObject.optString("chefId");

                    minsText.setText(dataObject.optString("deliveryTime") +"-"+dataObject.optString("deliveryExpectedTime")+" mins");

                    availableText.setText(getIntent().getStringExtra("availableCount"));
                    JSONObject nutritionObject = new JSONObject();
                    if (dataObject.has("nutritionPercentage")) {
                        nutritionObject = dataObject.getJSONObject("nutritionPercentage");
                    }

                    if (dataObject.has("nutrition")) {
                        nutritionObject = dataObject.getJSONObject("nutrition");
                    }
                    proteinCount.setText(nutritionObject.optString("protein") + "%");
                    proteinprogressBar.setProgress(nutritionObject.optInt("protein"));
                    fatCount.setText(nutritionObject.optString("fat") + "%");
                    fatprogressBar.setProgress(nutritionObject.optInt("fat"));
                    fibreCount.setText(nutritionObject.optString("fibre") + "%");
                    fibreprogressBar.setProgress(nutritionObject.optInt("fibre"));
                    carbsCount.setText(nutritionObject.optString("carbohydrates") + "%");
                    carbsprogressBar.setProgress(nutritionObject.optInt("carbohydrates"));

                    if (dataObject.optInt("cartQuantity") == 0) {
                        getServerCartQuantity = dataObject.optInt("cartQuantity");
                        itemCount.setText(String.valueOf(getServerCartQuantity + 1));
                        totalAddedText.setText("(" + (itemCount.getText().toString()) + ")Items Added to your basket");
                    } else {
                        getServerCartQuantity = dataObject.optInt("cartQuantity");
                        itemCount.setText(String.valueOf(getServerCartQuantity));
                        totalAddedText.setText("(" + (itemCount.getText().toString()) + ")Items Added to your basket");
                    }
                    availableCount = dataObject.optInt("availableCount");
                    if (qunatity == 0) {
                        qunatity = 1;
                        subTotalPrice.setText(String.format("%.2f", Double.valueOf(dataObject.optString("discountedPrice")) * qunatity));
                    } else
                        subTotalPrice.setText(String.format("%.2f", Double.valueOf(dataObject.optString("discountedPrice")) * qunatity));


                    preparedChefname.setText("Prepared By " + getIntent().getStringExtra("chefName"));

                    try {
                        Glide.with(ItemDetailsActivity.this).load(getIntent().getStringExtra("chefImage")).placeholder(R.drawable.foodi_logo_left_image).into(chefImage);

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
                progressBar.setVisibility(View.GONE);
                rootLayout.setVisibility(View.GONE);
                bottomButton.setVisibility(View.GONE);
                discountPercent.setVisibility(View.GONE);
                Toast.makeText(ItemDetailsActivity.this, "Oops something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "details_taq");
    }


    /**
     To Add the Dish To Cart make an POST  Call API(POST)
     **/
    public void addToCart(String dishID, String dishName, String dishImage, String chefID, int price,String from) throws JSONException {

        ProgressDialog progressDialog = new ProgressDialog(ItemDetailsActivity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        String url = APIBaseURL.addToCart;

        JSONObject inputObject = new JSONObject();
        inputObject.put("dishId", dishID);
        inputObject.put("userId", SaveSharedPreference.getLoggedInUserEmail(ItemDetailsActivity.this));
        inputObject.put("quantity", Integer.parseInt(itemCount.getText().toString()));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                Boolean isSuccess = response.optBoolean("isSuccess");
                try {

                    if (isSuccess) {
                        getServerCartQuantity = 1;
                        Toast.makeText(ItemDetailsActivity.this, "Successfully added to cart", Toast.LENGTH_SHORT).show();
                        if(from.equals("checkout"))
                        {
                            Intent intent = new Intent(ItemDetailsActivity.this, ConsumerCheckOutActivity.class);
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ItemDetailsActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ItemDetailsActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);
                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ItemDetailsActivity.this);

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
                    Toast.makeText(ItemDetailsActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(ItemDetailsActivity.this));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "add_to_cart_taq");
    }


    /**
     To Update the Dish To Cart make an PUT  Call API(PUT)
     **/
    public void updateCartCount(String dishID, int price, TextView quantityText, String check, String dishName, String dishImage, String chefID,String from) throws JSONException {
        String url = APIBaseURL.updateCart;

        ProgressDialog progressDialog = new ProgressDialog(ItemDetailsActivity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();


        JSONObject inputObject = new JSONObject();
        inputObject.put("dishId", dishID);
        inputObject.put("userId", SaveSharedPreference.getLoggedInUserEmail(ItemDetailsActivity.this));
        inputObject.put("quantity", Integer.parseInt(quantityText.getText().toString()));



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                ConsumerHomeOnDemandFragments.getCartLists();
                Boolean isSuccess = response.optBoolean("isSuccess");
                try {

                    if (isSuccess) {
                        Toast.makeText(ItemDetailsActivity.this, "Cart Updated Successfully", Toast.LENGTH_SHORT).show();
                        if(from.equals("checkout"))
                        {
                            Intent intent = new Intent(ItemDetailsActivity.this, ConsumerCheckOutActivity.class);
                            startActivity(intent);
                        }
                        if (check.equals("increase")) {
                        } else {

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ItemDetailsActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ItemDetailsActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ItemDetailsActivity.this);

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
                    Toast.makeText(ItemDetailsActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(ItemDetailsActivity.this));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "add_to_cart_taq");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mExecutor.shutdownNow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAuthService != null) {
            mAuthService.dispose();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayAuthOptions();
        if (resultCode == RESULT_CANCELED) {
            displayAuthCancelled();
        } else {
            Intent intent = new Intent(this, TokenActivity.class);
            intent.putExtras(data.getExtras());
            startActivity(intent);
            finish();
        }
    }

    @MainThread
    public static void startAuth() {

        // WrongThread inference is incorrect for lambdas
        // noinspection WrongThread
        mExecutor.submit(itemDetailsActivity::doAuth);
    }

    /**
     * Initializes the authorization service configuration if necessary, either from the local
     * static values or by retrieving an OpenID discovery document.
     */
    @WorkerThread
    private void initializeAppAuth() {
        Log.i(TAG, "Initializing AppAuth");
        recreateAuthorizationService();

        if (mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration() != null) {
            // configuration is already created, skip to client initialization
            Log.i(TAG, "auth config already established");
            initializeClient();
            return;
        }

        // if we are not using discovery, build the authorization service configuration directly
        // from the static configuration values.
        if (mConfiguration.getDiscoveryUri() == null) {
            Log.i(TAG, "Creating auth config from res/raw/auth_config.json");
            AuthorizationServiceConfiguration config = new AuthorizationServiceConfiguration(
                    mConfiguration.getAuthEndpointUri(),
                    mConfiguration.getTokenEndpointUri(),
                    mConfiguration.getRegistrationEndpointUri());

            mAuthStateManager.replace(new AuthState(config));
            initializeClient();
            return;
        }

        // WrongThread inference is incorrect for lambdas
        // noinspection WrongThread
        runOnUiThread(() -> displayLoading("Retrieving discovery document"));
        Log.i(TAG, "Retrieving OpenID discovery doc");
        AuthorizationServiceConfiguration.fetchFromUrl(
                mConfiguration.getDiscoveryUri(),
                this::handleConfigurationRetrievalResult,
                mConfiguration.getConnectionBuilder());
    }

    @MainThread
    private void handleConfigurationRetrievalResult(
            AuthorizationServiceConfiguration config,
            AuthorizationException ex) {
        if (config == null) {
            Log.i(TAG, "Failed to retrieve discovery document", ex);
            displayError("Failed to retrieve discovery document: " + ex.getMessage(), true);
            return;
        }

        Log.i(TAG, "Discovery document retrieved");
        mAuthStateManager.replace(new AuthState(config));
        mExecutor.submit(this::initializeClient);
    }

    /**
     * Initiates a dynamic registration request if a client ID is not provided by the static
     * configuration.
     */
    @WorkerThread
    private void initializeClient() {
        if (mConfiguration.getClientId() != null) {
            Log.i(TAG, "Using static client ID: " + mConfiguration.getClientId());
            // use a statically configured client ID
            mClientId.set(mConfiguration.getClientId());
            runOnUiThread(this::initializeAuthRequest);
            return;
        }

        RegistrationResponse lastResponse =
                mAuthStateManager.getCurrent().getLastRegistrationResponse();
        if (lastResponse != null) {
            Log.i(TAG, "Using dynamic client ID: " + lastResponse.clientId);
            // already dynamically registered a client ID
            mClientId.set(lastResponse.clientId);
            runOnUiThread(this::initializeAuthRequest);
            return;
        }

        // WrongThread inference is incorrect for lambdas
        // noinspection WrongThread
        runOnUiThread(() -> displayLoading("Dynamically registering client"));
        Log.i(TAG, "Dynamically registering client");

        RegistrationRequest registrationRequest = new RegistrationRequest.Builder(
                mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration(),
                Collections.singletonList(mConfiguration.getRedirectUri()))
                .setTokenEndpointAuthenticationMethod(ClientSecretBasic.NAME)
                .build();

        mAuthService.performRegistrationRequest(
                registrationRequest,
                this::handleRegistrationResponse);
    }

    @MainThread
    private void handleRegistrationResponse(
            RegistrationResponse response,
            AuthorizationException ex) {
        mAuthStateManager.updateAfterRegistration(response, ex);
        if (response == null) {
            Log.i(TAG, "Failed to dynamically register client", ex);
            displayErrorLater("Failed to register client: " + ex.getMessage(), true);
            return;
        }

        Log.i(TAG, "Dynamically registered client: " + response.clientId);
        mClientId.set(response.clientId);
        initializeAuthRequest();
    }

    /**
     * Enumerates the browsers installed on the device and populates a spinner, allowing the
     * demo user to easily test the authorization flow against different browser and custom
     * tab configurations.
     */
    @MainThread
    private void configureBrowserSelector() {


        Spinner spinner = (Spinner) findViewById(R.id.browser_selector);
        final BrowserSelectionAdapter adapter = new BrowserSelectionAdapter(this);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BrowserSelectionAdapter.BrowserInfo info = adapter.getItem(position);
                if (info == null) {
                    mBrowserMatcher = AnyBrowserMatcher.INSTANCE;
                    return;
                } else {
                    mBrowserMatcher = new ExactBrowserMatcher(info.mDescriptor);
                }

                recreateAuthorizationService();
                createAuthRequest(getLoginHint());
                warmUpBrowser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBrowserMatcher = AnyBrowserMatcher.INSTANCE;
            }
        });
    }

    /**
     * Performs the authorization request, using the browser selected in the spinner,
     * and a user-provided `login_hint` if available.
     */
    @WorkerThread
    private void doAuth() {
        try {
            mAuthIntentLatch.await();
        } catch (InterruptedException ex) {
            Log.w(TAG, "Interrupted while waiting for auth intent");
        }

        if (mUsePendingIntents) {
            Intent completionIntent = new Intent(this, TokenActivity.class);
            Intent cancelIntent = new Intent(this, TokenActivity.class);
            cancelIntent.putExtra(EXTRA_FAILED, true);
            cancelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            mAuthService.performAuthorizationRequest(
                    mAuthRequest.get(),
                    PendingIntent.getActivity(this, 0, completionIntent, 0),
                    PendingIntent.getActivity(this, 0, cancelIntent, 0),
                    mAuthIntent.get());
        } else {
            Intent intent = mAuthService.getAuthorizationRequestIntent(
                    mAuthRequest.get(),
                    mAuthIntent.get());
            startActivityForResult(intent, RC_AUTH);
        }
    }

    private void recreateAuthorizationService() {
        if (mAuthService != null) {
            Log.i(TAG, "Discarding existing AuthService instance");
            mAuthService.dispose();
        }
        mAuthService = createAuthorizationService();
        mAuthRequest.set(null);
        mAuthIntent.set(null);
    }

    private AuthorizationService createAuthorizationService() {
        Log.i(TAG, "Creating authorization service");
        AppAuthConfiguration.Builder builder = new AppAuthConfiguration.Builder();
        builder.setBrowserMatcher(mBrowserMatcher);
        builder.setConnectionBuilder(mConfiguration.getConnectionBuilder());

        return new AuthorizationService(this, builder.build());
    }

    @MainThread
    private static void displayLoading(String loadingMessage) {

    }

    @MainThread
    private void displayError(String error, boolean recoverable) {

    }

    // WrongThread inference is incorrect in this case
    @SuppressWarnings("WrongThread")
    @AnyThread
    private void displayErrorLater(final String error, final boolean recoverable) {
        runOnUiThread(() -> displayError(error, recoverable));
    }

    @MainThread
    private void initializeAuthRequest() {
        createAuthRequest(getLoginHint());
        warmUpBrowser();
        displayAuthOptions();
    }

    @MainThread
    private void displayAuthOptions() {
        findViewById(R.id.auth_container).setVisibility(View.GONE);
        findViewById(R.id.loading_container).setVisibility(View.GONE);
        findViewById(R.id.error_container).setVisibility(View.GONE);

        AuthState state = mAuthStateManager.getCurrent();
        AuthorizationServiceConfiguration config = state.getAuthorizationServiceConfiguration();

        String authEndpointStr;
        if (config.discoveryDoc != null) {
            authEndpointStr = "Discovered auth endpoint: \n";
        } else {
            authEndpointStr = "Static auth endpoint: \n";
        }
        authEndpointStr += config.authorizationEndpoint;
//        ((TextView)findViewById(R.id.auth_endpoint)).setText(authEndpointStr);

        String clientIdStr;
        if (state.getLastRegistrationResponse() != null) {
            clientIdStr = "Dynamic client ID: \n";
        } else {
            clientIdStr = "Static client ID: \n";
        }
        clientIdStr += mClientId;
        ((TextView) findViewById(R.id.client_id)).setText(clientIdStr);
    }

    private void displayAuthCancelled() {
        try {
            Snackbar.make(findViewById(R.id.coordinator),
                    "Authorization canceled",
                    Snackbar.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void warmUpBrowser() {
        mAuthIntentLatch = new CountDownLatch(1);
        mExecutor.execute(() -> {
            Log.i(TAG, "Warming up browser instance for auth request");
            CustomTabsIntent.Builder intentBuilder =
                    mAuthService.createCustomTabsIntentBuilder(mAuthRequest.get().toUri());
            intentBuilder.setToolbarColor(getColorCompat(R.color.colorPrimary));
            mAuthIntent.set(intentBuilder.build());
            mAuthIntentLatch.countDown();
        });
    }

    private void createAuthRequest(@Nullable String loginHint) {
        Log.i(TAG, "Creating auth request for login hint: " + loginHint);
        AuthorizationRequest.Builder authRequestBuilder = new AuthorizationRequest.Builder(
                mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration(),
                mClientId.get(),
                ResponseTypeValues.CODE,
                mConfiguration.getRedirectUri())
                .setPrompt("login")
                .setScope(mConfiguration.getScope());

        if (!TextUtils.isEmpty(loginHint)) {
            authRequestBuilder.setLoginHint(loginHint);
        }

        mAuthRequest.set(authRequestBuilder.build());
    }

    private String getLoginHint() {
        return ((EditText) findViewById(R.id.login_hint_value))
                .getText()
                .toString()
                .trim();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressWarnings("deprecation")
    private int getColorCompat(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getColor(color);
        } else {
            return getResources().getColor(color);
        }
    }

}
