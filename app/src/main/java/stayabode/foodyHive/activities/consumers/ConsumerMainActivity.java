package stayabode.foodyHive.activities.consumers;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnyThread;
import androidx.annotation.ColorRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.consumers.CategoryCheckBoxListsAdapter;
import stayabode.foodyHive.adapters.consumers.ConsumerSideMenuAdapter;
import stayabode.foodyHive.adapters.consumers.ConsumerSortSubMenuItemsAdapter;
import stayabode.foodyHive.adapters.consumers.ConsumerTabAdapter;
import stayabode.foodyHive.adapters.consumers.CuisineCheckBoxListsAdapter;
import stayabode.foodyHive.adapters.consumers.MainSortAdapters;
import stayabode.foodyHive.adapters.consumers.MealTypeCheckBoxListsAdapter;
import stayabode.foodyHive.adapters.consumers.RatingItemSortsAdapter;
import stayabode.foodyHive.authentication.AuthStateManager;
import stayabode.foodyHive.authentication.BrowserSelectionAdapter;
import stayabode.foodyHive.authentication.Configuration;
import stayabode.foodyHive.authentication.TokenActivity;
import stayabode.foodyHive.constants.APIBaseURL;

import stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments;
import stayabode.foodyHive.fragments.consumers.ConsumerSavedItemsFragment;
import stayabode.foodyHive.fragments.consumers.ConsumerNotificationFragment;
import stayabode.foodyHive.fragments.consumers.ConsumerSortAndFilterFragment;
import stayabode.foodyHive.fragments.consumers.ConsumerYouOrdersFragment;
import stayabode.foodyHive.models.Category;
import stayabode.foodyHive.models.CategorySort;
import stayabode.foodyHive.models.CuisineSort;
import stayabode.foodyHive.models.MainMenu;
import stayabode.foodyHive.models.MainSort;
import stayabode.foodyHive.models.MealTypeSort;
import stayabode.foodyHive.models.PriceSort;
import stayabode.foodyHive.models.SortRating;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.BottomNavigationTextFont;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class ConsumerMainActivity extends AppCompatActivity {

    public static Fragment consumerMainfragment = null;
    public static Class consumerfragmentClass = null;
    public static String consumerbackStateName = null;
    public static boolean selected = false;
    public static FragmentManager consumerfragmentManager;
    public static Toolbar consumertoolbar;
    public static DrawerLayout consumerDrawer;
    public static BottomNavigationView consumernavigation;
    public static NavigationView consumernavigationView;

    TextView logoLeftText;
    TextView logoRightText;
    TextView logoBottomText;
    TextView userNameText;
    ImageView userProfileImage;
    TextView placeText;
    TextView deliverTo;
    TextView deliverAddress;
    static TextView favCounter;
    static TextView orderCounter;
    public static Typeface poppinsMedium;
    public static Typeface poppinsLight;
    Typeface fontb;
    Typeface font;
    public static RecyclerView recyclerView;
    RecyclerView sorttopRecyclerView;
    RecyclerView sortBottomRecyclerView;
    public static Boolean scrolled = false;

    public static Boolean enableScroll = true;

    public static int selectedposition = -1;
    public static Typeface poppinsSemiBold;
    public static Typeface poppinsBold;
    public static ConsumerMainActivity consumerMainActivity;
    ShimmerFrameLayout shimmerFrameLayout;
    Typeface font1;
    public static TextView cartTotalCountText;
    boolean doubleBackToExitPressedOnce = false;

    FragmentManager fragmentManager = getSupportFragmentManager();
    public static Fragment consumerOnDemandFragment = new ConsumerHomeOnDemandFragments();
    public static Fragment consumerSavedItemsFragment = new ConsumerSavedItemsFragment();
    public static Fragment consumerYouOrdersFragment = new ConsumerYouOrdersFragment();
    public static Fragment consumerNotificationFragment = new ConsumerNotificationFragment();
    public static Fragment active = consumerOnDemandFragment;


    public static String userName = "";
    public static String opendOrdersCount = "";
    public static String favouritesCount = "";


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
    Switch chefSwitch;

    @NonNull
    private BrowserMatcher mBrowserMatcher = AnyBrowserMatcher.INSTANCE;
    /**
     * Bottom Navigation View Menu On Each Menu Click to Redirect to respective fragment
     **/
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            consumerfragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_my_dashboard:
                    if(ConsumerHomeOnDemandFragments.categoryList.size()!=0 || ConsumerHomeOnDemandFragments.minPriceSelectedFilter != 0 && ConsumerHomeOnDemandFragments.maxPriceSelectedFilter != 0)
                    {
                        try {
                            ConsumerHomeOnDemandFragments.getHomePageFilteredAPI(2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        if(ConsumerHomeOnDemandFragments.header.getText().toString().equals("Top Offers")){
                            try {
                                ConsumerHomeOnDemandFragments.updateTopOFFersItems();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            try {
                                ConsumerHomeOnDemandFragments.updateOtherThanTopOFfers(ConsumerHomeOnDemandFragments.getSelectedCategoryID,ConsumerHomeOnDemandFragments.header.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    try {
                        ConsumerHomeOnDemandFragments.getNearByChefsAtYourLocation(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        ConsumerHomeOnDemandFragments.updatePopularChoices();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        ConsumerHomeOnDemandFragments.updateRecentlyViewedItems();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    consumerfragmentManager.beginTransaction().hide(active).show(consumerOnDemandFragment).commit();
                    active = consumerOnDemandFragment;
                    return true;

                case R.id.navigation_saved:
                    try {
                        ConsumerSavedItemsFragment.getSavedOrders();
                        ConsumerSavedItemsFragment.recyclerView.getAdapter().notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    consumerfragmentManager.beginTransaction().hide(active).show(consumerSavedItemsFragment).commit();
                    active = consumerSavedItemsFragment;
                    return true;

                case R.id.navigation_notification:
                    ConsumerNotificationFragment.referralWalletindex = 0;
                    //getWalletHistory();sat cmd
                    ConsumerNotificationFragment.getReferralscore();
                    consumerfragmentManager.beginTransaction().hide(active).show(consumerNotificationFragment).commit();
                    active = consumerNotificationFragment;
                    return true;

                case R.id.navigation_orders:
                    try {
                        ConsumerYouOrdersFragment.viewPager.setCurrentItem(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    consumerfragmentManager.beginTransaction().hide(active).show(consumerYouOrdersFragment).commit();
                    active = consumerYouOrdersFragment;
                    return true;

            }

            return false;
        }
    };
    private static AuthStateManager mStateManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExecutor = Executors.newSingleThreadExecutor();
        mAuthStateManager = AuthStateManager.getInstance(this);
        mStateManager = AuthStateManager.getInstance(this);
        mConfiguration = Configuration.getInstance(this);

        setContentView(R.layout.activity_consumer_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarColor));
        }
        consumerMainActivity = this;

        consumertoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(consumertoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");

        chefSwitch = findViewById(R.id.chefSwitch);

        if((SaveSharedPreference.getsaveChef(consumerMainActivity)).equals("chef"))
        {
            chefSwitch.setVisibility(View.VISIBLE);
        }else
        {
            chefSwitch.setVisibility(View.INVISIBLE);
        }

        chefSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {


                    //consumer

                    if(SaveSharedPreference.getLoggedInUserAddress(consumerMainActivity).equals(""))
                    {
                        Intent intent = new Intent(consumerMainActivity, LocationSelectionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        consumerMainActivity.startActivity(intent);
                        consumerMainActivity.finish();
                    }
                    else
                    {
                        Intent intent = new Intent(consumerMainActivity, ConsumerMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        consumerMainActivity.startActivity(intent);
                        consumerMainActivity.finish();
                    }



                } else {


                    //chef
                    Intent verifOTPIntent = new Intent(consumerMainActivity, ChefsMainActivity.class);
                    verifOTPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    verifOTPIntent.putExtra("Role","Chef");
                    consumerMainActivity.startActivity(verifOTPIntent);
                    consumerMainActivity.finish();

                }
            }
        });

        deliverTo = consumertoolbar.findViewById(R.id.deliverToText);
        deliverAddress = consumertoolbar.findViewById(R.id.deliverAddress);
        deliverAddress.setText(SaveSharedPreference.getLoggedInUserAddress(getApplicationContext()));
        deliverAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsumerMainActivity.this, ChangeLocationActivity.class);
                startActivityForResult(intent, 102);
            }
        });
        deliverTo.setTypeface(poppinsLight);
        deliverAddress.setTypeface(poppinsMedium);

        ImageView cartIcon = consumertoolbar.findViewById(R.id.cartIcon);
        cartTotalCountText = consumertoolbar.findViewById(R.id.cartTotalCountText);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumerMainActivity.this, ConsumerMyBasketActivity.class);
                startActivity(intent);
            }
        });

        consumerDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, consumerDrawer,
                null, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSideMenuCounts();
                getUserInfo();


            }
        };
        consumerDrawer.setDrawerListener(toggle);
        consumertoolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.foodi_logo_left_image);
        toggle.syncState();

        consumertoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumerDrawer.openDrawer(Gravity.LEFT);
            }
        });


        consumernavigation = (BottomNavigationView) findViewById(R.id.navigation);
        consumernavigationView = findViewById(R.id.nav_view);

        View hView = consumernavigationView.getHeaderView(0);

        recyclerView = hView.findViewById(R.id.recyclerView);
        LinearLayout accountlayout = hView.findViewById(R.id.accountlayout);
        accountlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumerDrawer.closeDrawers();
                if(SaveSharedPreference.getLoggedInUserRole(ConsumerMainActivity.this).equals(""))
                {
                    ViewGroup viewGroup = v.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerMainActivity.this).inflate(R.layout.sign_in_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerMainActivity.this);

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ConsumerMainActivity.startAuth();

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
                }
                else
                {
                    Intent intent = new Intent(ConsumerMainActivity.this, ProfileInfoActivity.class);
                    startActivity(intent);
                }

            }
        });

        ImageView settingsIcon = hView.findViewById(R.id.settingsIcon);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consumerDrawer.closeDrawers();
                if(SaveSharedPreference.getLoggedInUserRole(ConsumerMainActivity.this).equals(""))
                {
                    ViewGroup viewGroup = view.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerMainActivity.this).inflate(R.layout.sign_in_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerMainActivity.this);

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ConsumerMainActivity.startAuth();

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
                }
                else
                {
                    Intent intent = new Intent(ConsumerMainActivity.this, ProfileInfoActivity.class);
                    startActivity(intent);
                }
            }
        });

        shimmerFrameLayout = hView.findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        logoLeftText = hView.findViewById(R.id.logoLeftText);
        logoRightText = hView.findViewById(R.id.logoRightText);
        logoBottomText = hView.findViewById(R.id.logoBottomText);
        userNameText = hView.findViewById(R.id.userNameText);
        userProfileImage = hView.findViewById(R.id.userProfileImage);
        placeText = hView.findViewById(R.id.placeText);
        font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        fontb = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        logoLeftText.setTypeface(font);
        logoRightText.setTypeface(font);
        logoBottomText.setTypeface(font);
        userNameText.setTypeface(fontb);
        placeText.setTypeface(fontb);

        consumerfragmentManager = getSupportFragmentManager();
        consumerfragmentManager.beginTransaction().add(R.id.content, consumerYouOrdersFragment, "4").hide(consumerYouOrdersFragment).commit();
        consumerfragmentManager.beginTransaction().add(R.id.content, consumerNotificationFragment, "3").hide(consumerNotificationFragment).commit();
        consumerfragmentManager.beginTransaction().add(R.id.content, consumerSavedItemsFragment, "2").hide(consumerSavedItemsFragment).commit();
        consumerfragmentManager.beginTransaction().add(R.id.content, consumerOnDemandFragment, "1").commit();

        consumernavigationView.setItemIconTintList(null);


        BottomNavigationTextFont.persian_iran_font(getApplicationContext(), consumernavigation);
        consumernavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        BottomNavigationMenuView favView = (BottomNavigationMenuView) consumernavigation.getChildAt(0);
        BottomNavigationItemView favItemView = (BottomNavigationItemView) favView.getChildAt(1);
        View favBadge = LayoutInflater.from(this).inflate(R.layout.badge_layout, favView, false);
        favCounter = favBadge.findViewById(R.id.counter_badge);
        favItemView.addView(favBadge);

        BottomNavigationMenuView ordersView = (BottomNavigationMenuView) consumernavigation.getChildAt(0);
        BottomNavigationItemView orderItemView = (BottomNavigationItemView) ordersView.getChildAt(3);
        View orderBadge = LayoutInflater.from(this).inflate(R.layout.badge_layout, ordersView, false);
        orderCounter = orderBadge.findViewById(R.id.counter_badge);
        orderItemView.addView(orderBadge);


        font1 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");

        getUserInfo();
        getSideMenuCounts();
        getSideMenus();

        if (SaveSharedPreference.getLoggedInUserRole(consumerMainActivity).equals("")) {

        } else {
            try {
                updatedDeviceToken();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        configureBrowserSelector();
        if (mConfiguration.hasConfigurationChanged()) {
            // discard any existing authorization state due to the change of configuration
            mAuthStateManager.replace(new AuthState());
            mConfiguration.acceptConfiguration();
        }

        if (getIntent().getBooleanExtra(EXTRA_FAILED, false)) {
            displayAuthCancelled();
        }

        displayLoading("Initializing");
        mExecutor.submit(this::initializeAppAuth);

    }

    // on BackPress
    @Override
    public void onBackPressed() {
        if (consumerfragmentManager.getBackStackEntryCount() > 0) {

            consumerfragmentManager.popBackStack();


        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            SuperActivityToast.create(ConsumerMainActivity.this)
                    .setProgressBarColor(Color.WHITE)
                    .setText("Press again to exit")
                    .setDuration(Style.DURATION_SHORT)
                    .setFrame(Style.FRAME_KITKAT)
                    .setColor(getResources().getColor(R.color.colorWhite))
                    .setTextColor(getResources().getColor(R.color.colorBlack))
                    .setAnimations(Style.ANIMATIONS_FLY).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    /**
     * To Logout From Azure and redirect to Intro page
     **/
    public static void logout() {

// discard the authorization and token state, but retain the configuration and
// dynamic client registration (if applicable), to save from retrieving them again.

        AuthState currentState = mStateManager.getCurrent();
        AuthState clearedState =
                new AuthState(currentState.getAuthorizationServiceConfiguration());
        if (currentState.getLastRegistrationResponse() != null) {
            clearedState.update(currentState.getLastRegistrationResponse());
        }
        mStateManager.replace(clearedState);

        SharedPreferences mySPrefs = SaveSharedPreference.getPreferences(consumerMainActivity);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.clear();
        editor.apply();


        Intent mainIntent = new Intent(consumerMainActivity, IntroScreenActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        consumerMainActivity.startActivity(mainIntent);
        consumerMainActivity.finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            ConsumerHomeOnDemandFragments.getCartLists();
            getSideMenuCounts();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * To Get the Consumers Side Order and Favourites Menu's Count From API(GET)
     **/
    public static void getSideMenuCounts() {
        String url = APIBaseURL.getSideMenuCounts + SaveSharedPreference.getLoggedInUserEmail(consumerMainActivity);


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        opendOrdersCount = dataObject.optString("openOrderCount");
                        favouritesCount = dataObject.optString("favouriteDishCount");
                    } else {
                        opendOrdersCount = "";
                        favouritesCount = "";
                    }

                    try {
                        // get menu from navigationView
                        Menu menu = consumernavigation.getMenu();

                        // find MenuItem you want to change
                        MenuItem nav_camara = menu.findItem(R.id.navigation_saved);

                        // set new title to the MenuItem
                        //  nav_camara.setTitle("Favourites"+"("+favouritesCount+")");
                        if (favouritesCount.equals("0")) {
                            favCounter.setVisibility(View.GONE);
                        } else {
                            favCounter.setVisibility(View.VISIBLE);
                            favCounter.setText(favouritesCount);
                        }
                        // do the same for other MenuItems
                        MenuItem nav_gallery = menu.findItem(R.id.navigation_orders);
//                        nav_gallery.setTitle("Your Orders" + "(" + opendOrdersCount + ")");
                        if (opendOrdersCount.equals("0")) {
                            orderCounter.setVisibility(View.GONE);
                        } else {
                            orderCounter.setVisibility(View.VISIBLE);
                            orderCounter.setText(opendOrdersCount);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        recyclerView.getAdapter().notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                opendOrdersCount = "";
                favouritesCount = "";
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerMainActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerMainActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerMainActivity);

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
                    Toast.makeText(consumerMainActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerMainActivity);

        ApplicationController.getInstance().addToRequestQueue(stringRequest, "side_menu_count");
    }


    /**
     * To Get the Consumers Side Menu's  From API(GET)
     **/
    public void getSideMenus() {
        String url = APIBaseURL.consumerLeftMenu;
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    List<MainMenu> mainMenuList = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.setName(dataObject.optString("title"));
                        mainMenu.setId(dataObject.optString("id"));
                        mainMenu.setCount(dataObject.optString("count"));
                        mainMenu.setImage(dataObject.optString("icon").replaceAll(" ", ""));
                        mainMenuList.add(mainMenu);
                    }
                    recyclerView.setAdapter(new ConsumerSideMenuAdapter(ConsumerMainActivity.this, mainMenuList, font1, poppinsMedium));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerMainActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerMainActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerMainActivity);

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
                    Toast.makeText(ConsumerMainActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, ConsumerMainActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "menu_taq");
    }


    /**
     * To Filter & Sort the Items by using Option get the Values from API Call(GET)
     **/
    public static void getSortByDishesSearchOptions(RecyclerView sortBottomRecyclerView, int sortId) {
        String url = APIBaseURL.searchOptions;


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int mainSortSelectedPosition = 12;
                try {
                    JSONObject jsonObject = new JSONObject(response);

                   /* List<SortHeader> sortHeaderList = new ArrayList<>();

                    SortHeader sortHeader = new SortHeader();
                    sortHeader.setTitle("Sort By");

                    SortHeader pricesortHeader = new SortHeader();
                    pricesortHeader.setTitle("Price");

                    SortHeader categorysortHeader = new SortHeader();
                    categorysortHeader.setTitle("Category");

                    SortHeader cuisinesortHeader = new SortHeader();
                    cuisinesortHeader.setTitle("Cuisine");

                    SortHeader mealsortHeader = new SortHeader();
                    mealsortHeader.setTitle("Meal Type");

                    SortHeader ratingHeader = new SortHeader();
                    ratingHeader.setTitle("Rating");

                    sortHeaderList.add(sortHeader);
                    sortHeaderList.add(pricesortHeader);
                    sortHeaderList.add(categorysortHeader);
                    sortHeaderList.add(cuisinesortHeader);
                    sortHeaderList.add(mealsortHeader);
                    sortHeaderList.add(ratingHeader);*/

                    List<Object> objectList = new ArrayList<>();
                    if(sortId==2) {
                        if (jsonObject.has("sortTypes")) {

                            JSONArray sortTypeFiltersArray = jsonObject.getJSONArray("sortTypes");
                            if (sortTypeFiltersArray.length() != 0) {
                                MainSort mainSort = new MainSort();
                                List<Category> mainSortItemsList = new ArrayList<>();

                                Boolean isSelected = false;
                                for (int i = 0; i < sortTypeFiltersArray.length(); i++) {
                                    JSONObject sortTypeFilterObjects = sortTypeFiltersArray.getJSONObject(i);
                                    Category mainSortItems = new Category();
                                    mainSortItems.setName(sortTypeFilterObjects.optString("label"));
                                    mainSortItems.setId(sortTypeFilterObjects.optString("value"));

                                    if (MainSortAdapters.selectedSortCostsItemsLists.size() != 0) {
                                        if (MainSortAdapters.selectedSortCostsItemsLists.get(i).getSelected()) {
                                            mainSortItems.setSelected(true);
                                            mainSortItems.setLastselectedPosition(i);
                                            mainSortSelectedPosition = 15;
                                        } else {
                                            mainSortItems.setSelected(false);
                                            mainSortItems.setLastselectedPosition(12);
                                            mainSortSelectedPosition = 12;
                                        }
                                    } else {
                                        mainSortItems.setSelected(false);
                                        mainSortItems.setLastselectedPosition(12);
                                        mainSortSelectedPosition = 12;
                                    }
                                    mainSortItemsList.add(mainSortItems);
                                    mainSort.setCategoryList(mainSortItemsList);
                                }

                                objectList.add(mainSort);
                            }
                        }
                    }

                    else if(sortId==3){
                        PriceSort priceSort = new PriceSort();
                        objectList.add(priceSort);
                    }

                    else if(sortId==4) {
                        if (jsonObject.has("dishCategories")) {
                            JSONArray dishTypesFiltersArray = jsonObject.getJSONArray("dishCategories");
                            if (dishTypesFiltersArray.length() != 0) {
                                CategorySort categorySort = new CategorySort();
                                categorySort.setName("Category");

                                List<Category> categorySortTypesList = new ArrayList<>();

                                Boolean check = false;
                                for (int i = 0; i < dishTypesFiltersArray.length(); i++) {
                                    JSONObject dishTypesFilterObject = dishTypesFiltersArray.getJSONObject(i);
                                    Category categorySortTypes = new Category();
                                    categorySortTypes.setName(dishTypesFilterObject.optString("label"));
                                    categorySortTypes.setId(dishTypesFilterObject.optString("value"));
                                    if (CategoryCheckBoxListsAdapter.selectedcategorySortTypesList.size() != 0) {
                                        if (CategoryCheckBoxListsAdapter.selectedcategorySortTypesList.get(i).getSelected()) {
                                            categorySortTypes.setSelected(true);
                                        } else {
                                            categorySortTypes.setSelected(false);
                                        }
                                    } else {
                                        categorySortTypes.setSelected(false);
                                    }
                                    categorySortTypesList.add(categorySortTypes);
                                    categorySort.setCategoryList(categorySortTypesList);
                                }
                                objectList.add(categorySort);
                            }
                        }
                    }

                    if(sortId==5) {
                        if (jsonObject.has("dishCuisines")) {
                            JSONArray dishQuickFiltersArray = jsonObject.getJSONArray("dishCuisines");
                            if (dishQuickFiltersArray.length() != 0) {
                                CuisineSort cuisineSort = new CuisineSort();

                                List<Category> cuisineSortTypesList = new ArrayList<>();

                                Boolean check = false;
                                for (int i = 0; i < dishQuickFiltersArray.length(); i++) {
                                    JSONObject dishQuickFilterObject = dishQuickFiltersArray.getJSONObject(i);
                                    Category cuisineSortTypes = new Category();
                                    cuisineSortTypes.setName(dishQuickFilterObject.optString("label"));
                                    cuisineSortTypes.setId(dishQuickFilterObject.optString("value"));
                                    if (CuisineCheckBoxListsAdapter.selectedCuisinesSortTypeLists.size() != 0) {
                                        if (CuisineCheckBoxListsAdapter.selectedCuisinesSortTypeLists.get(i).getSelected()) {
                                            cuisineSortTypes.setSelected(true);
                                        } else {
                                            cuisineSortTypes.setSelected(false);
                                        }
                                    } else {
                                        cuisineSortTypes.setSelected(false);
                                    }


                                    cuisineSortTypesList.add(cuisineSortTypes);
                                    cuisineSort.setCategoryList(cuisineSortTypesList);


                                }
                                objectList.add(cuisineSort);

                            }

                        }
                    }

                    if(sortId==6) {
                        if (jsonObject.has("dishTypes")) {
                            JSONArray dishTypesArray = jsonObject.getJSONArray("dishTypes");
                            if (dishTypesArray.length() != 0) {
                                MealTypeSort mealTypeSort = new MealTypeSort();

                                List<Category> mealTypeSortTypesList = new ArrayList<>();

                                Boolean check = false;
                                for (int i = 0; i < dishTypesArray.length(); i++) {
                                    JSONObject dishQuickFilterObject = dishTypesArray.getJSONObject(i);

                                    Category mealTypeSortTypes = new Category();
                                    mealTypeSortTypes.setName(dishQuickFilterObject.optString("label"));
                                    mealTypeSortTypes.setId(dishQuickFilterObject.optString("value"));
                                    if (MealTypeCheckBoxListsAdapter.selectedMealsSortTypesList.size() != 0) {
                                        if (MealTypeCheckBoxListsAdapter.selectedMealsSortTypesList.get(i).getSelected()) {
                                            mealTypeSortTypes.setSelected(true);
                                        } else {
                                            mealTypeSortTypes.setSelected(false);
                                        }
                                    } else {
                                        mealTypeSortTypes.setSelected(false);
                                    }
                                    mealTypeSortTypesList.add(mealTypeSortTypes);
                                    mealTypeSort.setCategoryList(mealTypeSortTypesList);
                                }
                                objectList.add(mealTypeSort);
                            }
                        }
                    }

                    if(sortId==1) {
                        List<Category> ratingSortTypesListForChecking = new ArrayList<>();
                        Category ratingSortTypes = new Category();
                        ratingSortTypes.setId("1");
                        ratingSortTypes.setRatingCount(5);

                        Category ratingSortTypes1 = new Category();
                        ratingSortTypes1.setId("2");
                        ratingSortTypes1.setRatingCount(4);

                        Category ratingSortTypes2 = new Category();
                        ratingSortTypes2.setId("3");
                        ratingSortTypes2.setRatingCount(3);

                        Category ratingSortTypes3 = new Category();
                        ratingSortTypes3.setId("4");
                        ratingSortTypes3.setRatingCount(2);

                        Category ratingSortTypes4 = new Category();
                        ratingSortTypes4.setId("5");
                        ratingSortTypes4.setRatingCount(1);

                        ratingSortTypesListForChecking.add(ratingSortTypes);
                        ratingSortTypesListForChecking.add(ratingSortTypes1);
                        ratingSortTypesListForChecking.add(ratingSortTypes2);
                        ratingSortTypesListForChecking.add(ratingSortTypes3);
                        ratingSortTypesListForChecking.add(ratingSortTypes4);


                        SortRating sortRating = new SortRating();

                        List<Category> ratingSortTypesList = new ArrayList<>();

                        for (int i = 0; i < ratingSortTypesListForChecking.size(); i++) {
                            Category ratingSortTypesFor = new Category();
                            ratingSortTypesFor.setId(ratingSortTypesListForChecking.get(i).getId());
                            ratingSortTypesFor.setRatingCount(ratingSortTypesListForChecking.get(i).getRatingCount());

                            if (RatingItemSortsAdapter.selectedRatingsSortTypes.size() != 0) {
                                if (RatingItemSortsAdapter.selectedRatingsSortTypes.get(i).getSelected()) {
                                    ratingSortTypesFor.setSelected(true);

                                } else {
                                    ratingSortTypesFor.setSelected(false);
                                }
                            } else {
                                ratingSortTypesFor.setSelected(false);
                            }

                            ratingSortTypesList.add(ratingSortTypesFor);
                            sortRating.setCategoryList(ratingSortTypesList);
                        }

                        objectList.add(sortRating);
                    }
                    sortBottomRecyclerView.setLayoutManager(new LinearLayoutManager(consumerMainActivity));

                    sortBottomRecyclerView.setAdapter(new ConsumerSortSubMenuItemsAdapter(consumerMainActivity, objectList, poppinsSemiBold, poppinsLight, poppinsMedium, poppinsBold, null, null, mainSortSelectedPosition));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = consumerMainActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(consumerMainActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(consumerMainActivity);

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
                    Toast.makeText(consumerMainActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, consumerMainActivity);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "menu_taq");

    }


    /**
     * Sort and Filter Page Dialog in Full Screen Mode
     **/
    public static class MyDialogFragment extends DialogFragment {

        TextView resetText;
        TextView title;
        Button applyFilter;
        TabLayout tabLayout;
        ViewPager viewPager;

        public static RecyclerView sorttopRecyclerView;
        public static RecyclerView sortBottomRecyclerView;
        public static ImageView backIcon;

        public static MyDialogFragment newInstance() {
            MyDialogFragment f = new MyDialogFragment();
            return f;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme);
            setStyle(DialogFragment.STYLE_NORMAL,
                    android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        }

        @Override
        public void onStart() {
            super.onStart();
            if (getDialog() == null) {
                return;
            }
            getDialog().getWindow().setWindowAnimations(R.style.AnimationWindow);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.consumer_sort_side_menu_navigation, container, false);

            //sorttopRecyclerView = v.findViewById(R.id.toprecyclerView);
            title = v.findViewById(R.id.title);
            resetText = v.findViewById(R.id.resetText);
            applyFilter = v.findViewById(R.id.applyFilter);
            backIcon = v.findViewById(R.id.backIcon);
            tabLayout = v.findViewById(R.id.tabLayout);
            viewPager=v.findViewById(R.id.viewPager);
            ArrayList<String> arrayList = new ArrayList<>();
            //sortBottomRecyclerView = v.findViewById(R.id.bottomRecyclerView);
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = getFragmentManager().findFragmentByTag("sort_dialog");
                    if (fragment != null) {
                        MyDialogFragment dialog = (MyDialogFragment) fragment;
                        dialog.dismiss();
                    }

                }
            });

            title.setTypeface(poppinsBold);
            resetText.setTypeface(poppinsMedium);
            applyFilter.setTypeface(poppinsBold);

            arrayList.add("Rating");
            arrayList.add("Sort By");
            arrayList.add("Price");
            arrayList.add("Category");
            arrayList.add("Cuisine");
            arrayList.add("Meal Type");
            prepareViewager(viewPager,arrayList);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setOffscreenPageLimit(6);



            resetText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        ConsumerHomeOnDemandFragments.minPriceSelectedFilter = 0;
                        ConsumerHomeOnDemandFragments.maxPriceSelectedFilter = 0;
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
                    ConsumerHomeOnDemandFragments.categoryList.clear();

                    //sortBottomRecyclerView.getAdapter().notifyDataSetChanged();

                    Toast.makeText(consumerMainActivity, "Your selected filter has cleared", Toast.LENGTH_SHORT).show();
                    Fragment fragment = getFragmentManager().findFragmentByTag("sort_dialog");
                    if (fragment != null) {
                        MyDialogFragment dialog = (MyDialogFragment) fragment;
                        dialog.dismiss();
                    }


                }
            });

            applyFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = getFragmentManager().findFragmentByTag("sort_dialog");
                    if (fragment != null) {
                        MyDialogFragment dialog = (MyDialogFragment) fragment;
                        dialog.dismiss();
                    }
                    try {
                        ConsumerHomeOnDemandFragments.getHomePageFilteredAPI(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });

            //getSortByDishesSearchOptions(sorttopRecyclerView,sortBottomRecyclerView);
            return v;
        }
        private void prepareViewager(ViewPager viewPager, ArrayList<String> arrayList) {
            ConsumerTabAdapter consumerTabAdapter=new ConsumerTabAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            ConsumerSortAndFilterFragment consumerSortAndFilterFragment=new ConsumerSortAndFilterFragment();
            for(int i=0;i<arrayList.size();i++){
                Bundle bundle=new Bundle();
                bundle.putString("title",arrayList.get(i));
                consumerSortAndFilterFragment.setArguments(bundle);
                consumerTabAdapter.createFragment(consumerSortAndFilterFragment,arrayList.get(i));
                consumerSortAndFilterFragment=new ConsumerSortAndFilterFragment();
            }
            viewPager.setAdapter(consumerTabAdapter);
        }

    }


    /**
     * Get Logged in User Profile Details from API(GET)
     **/
    public void getUserInfo() {
        String url = APIBaseURL.checkUserExistsOrNot + SaveSharedPreference.getLoggedInUserEmail(ConsumerMainActivity.this);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    userNameText.setText("Hi " + dataObject.optString("name"));
                    userName = dataObject.optString("name");
                    Glide.with(ConsumerMainActivity.this).load(dataObject.optString("profilePic")).placeholder(R.drawable.customer_user_profile_left_menu).into(userProfileImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, ConsumerMainActivity.this);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");
    }


    // on Activity Result Overridden Method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayAuthOptions();
        if (resultCode == 102) {
            deliverAddress.setText(SaveSharedPreference.getLoggedInUserAddress(getApplicationContext()));

            try {
                ConsumerHomeOnDemandFragments.updateTopOFFersItems();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                ConsumerHomeOnDemandFragments.getNearByChefsAtYourLocation(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ConsumerHomeOnDemandFragments.updatePopularChoices();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                ConsumerHomeOnDemandFragments.updateRecentlyViewedItems();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(resultCode == 200)
        {
            try {
                ConsumerHomeOnDemandFragments.updateTopOFFersItems();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                ConsumerHomeOnDemandFragments.getNearByChefsAtYourLocation(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ConsumerHomeOnDemandFragments.updatePopularChoices();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                ConsumerHomeOnDemandFragments.updateRecentlyViewedItems();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            consumerfragmentManager.beginTransaction().hide(active).show(consumerOnDemandFragment).commit();
            active = consumerOnDemandFragment;
        }
        else if (resultCode == RESULT_CANCELED) {
            displayAuthCancelled();
        } else {
            Intent intent = new Intent(this, TokenActivity.class);
            intent.putExtras(data.getExtras());
            startActivity(intent);
            finish();
        }
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


    @MainThread
    public static void startAuth() {

        // WrongThread inference is incorrect for lambdas
        // noinspection WrongThread
        mExecutor.submit(consumerMainActivity::doAuth);
    }

    /**
     * Initializes the authorization service configuration if necessary, either from the local
     * static values or by retrieving an OpenID discovery document.
     */
    @WorkerThread
    private void initializeAppAuth() {
        recreateAuthorizationService();

        if (mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration() != null) {
            // configuration is already created, skip to client initialization
            initializeClient();
            return;
        }

        // if we are not using discovery, build the authorization service configuration directly
        // from the static configuration values.
        if (mConfiguration.getDiscoveryUri() == null) {
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
            displayError("Failed to retrieve discovery document: " + ex.getMessage(), true);
            return;
        }

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
            // use a statically configured client ID
            mClientId.set(mConfiguration.getClientId());
            runOnUiThread(this::initializeAuthRequest);
            return;
        }

        RegistrationResponse lastResponse =
                mAuthStateManager.getCurrent().getLastRegistrationResponse();
        if (lastResponse != null) {
            // already dynamically registered a client ID
            mClientId.set(lastResponse.clientId);
            runOnUiThread(this::initializeAuthRequest);
            return;
        }

        // WrongThread inference is incorrect for lambdas
        // noinspection WrongThread
        runOnUiThread(() -> displayLoading("Dynamically registering client"));

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
            displayErrorLater("Failed to register client: " + ex.getMessage(), true);
            return;
        }

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
            mAuthService.dispose();
        }
        mAuthService = createAuthorizationService();
        mAuthRequest.set(null);
        mAuthIntent.set(null);
    }

    private AuthorizationService createAuthorizationService() {
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
            CustomTabsIntent.Builder intentBuilder =
                    mAuthService.createCustomTabsIntentBuilder(mAuthRequest.get().toUri());
            intentBuilder.setToolbarColor(getColorCompat(R.color.colorPrimary));
            mAuthIntent.set(intentBuilder.build());
            mAuthIntentLatch.countDown();
        });
    }

    private void createAuthRequest(@Nullable String loginHint) {
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


    /**
     * To update firebase device token from firebase to API (POST)
     **/
    public void updatedDeviceToken() throws JSONException {

        JSONObject inputObject = new JSONObject();
        inputObject.put("emailid", SaveSharedPreference.getLoggedInUserEmail(consumerMainActivity));
        inputObject.put("devicetype", "android");
        inputObject.put("devicetoken", FirebaseInstanceId.getInstance().getToken());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        inputObject.put("lastLoginTime", currentDateandTime);


        String url = APIBaseURL.saveUserDeviceToken;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(consumerMainActivity));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

}
