package stayabode.foodyHive.activities.chefs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import  stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.activities.consumers.IntroScreenActivity;
import stayabode.foodyHive.activities.consumers.LocationSelectionActivity;
import stayabode.foodyHive.adapters.chefs.ChefLeftMenuAdapter;
import stayabode.foodyHive.authentication.AuthStateManager;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.fragments.chefs.ChefHomeFragment;
import stayabode.foodyHive.fragments.chefs.ChefNotificationFragment;
import stayabode.foodyHive.fragments.chefs.ChefsMenuFragment;
import stayabode.foodyHive.fragments.chefs.ChefsOrderFragment;
import stayabode.foodyHive.models.MainMenu;
import stayabode.foodyHive.models.SubMenu;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.BottomNavigationTextFont;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

import net.openid.appauth.AuthState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ChefsMainActivity extends AppCompatActivity {


    public static Fragment chefMainfragment = null;
    public static Class cheffragmentClass = null;
    public static String chefbackStateName = null;
    public static boolean selected = false;
    public static FragmentManager cheffragmentManager;
    public static Toolbar cheftoolbar;
    public static DrawerLayout chefDrawer;
    public static BottomNavigationView chefnavigation;
    public static NavigationView chefnavigationView;
    public static LinearLayout searchLayout;
    public static String chefNamefromAPI = "";
    public static String chefIDfromAPI = "";
    boolean doubleBackToExitPressedOnce = false;
    String[] searchNames;
    Spinner searchSpinner;
    LinearLayout arrowImage;
    String[] searchIds;
    private static AuthStateManager mStateManager;
    public static ChefsMainActivity chefsMainActivity;
    public static LinearLayout mainBottomLayout;


    private long timerTime = TimeUnit.SECONDS.toMillis(300);
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                //Sync data to and fro every 300 seconds
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                timerHandler.postDelayed(this, timerTime);
            }
        }
    };

    /**
     * Bottom Navigation Menu View On Click to Change Each Respective Fragments
     **/
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            cheffragmentManager = getSupportFragmentManager();


            switch (item.getItemId()) {
                case R.id.navigation_my_dashboard:
                    dashboardView.setVisibility(View.VISIBLE);
                    ordersView.setVisibility(View.INVISIBLE);
                    franchiseView.setVisibility(View.INVISIBLE);
                    reportsView.setVisibility(View.INVISIBLE);

                    cheffragmentClass = ChefHomeFragment.class;
                    try {
                        chefMainfragment = (Fragment) cheffragmentClass.newInstance();
                        chefbackStateName = chefMainfragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate(chefbackStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = cheffragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if (currentFragment != null && !currentFragment.getClass().equals(chefMainfragment.getClass())) {
                                cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                // return true;

                case R.id.navigation_orders:
                    dashboardView.setVisibility(View.INVISIBLE);
                    ordersView.setVisibility(View.VISIBLE);
                    franchiseView.setVisibility(View.INVISIBLE);
                    reportsView.setVisibility(View.INVISIBLE);
                    cheffragmentClass = ChefsOrderFragment.class;
                    try {
                        chefMainfragment = (Fragment) cheffragmentClass.newInstance();
                        chefbackStateName = chefMainfragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate(chefbackStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = cheffragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if (currentFragment != null && !currentFragment.getClass().equals(chefMainfragment.getClass())) {
                                cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).commit();
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    selected = true;
                    break;

                case R.id.navigation_menu:
                    dashboardView.setVisibility(View.INVISIBLE);
                    ordersView.setVisibility(View.INVISIBLE);
                    franchiseView.setVisibility(View.VISIBLE);
                    reportsView.setVisibility(View.INVISIBLE);
                    cheffragmentClass = ChefsMenuFragment.class;
                    try {
                        chefMainfragment = (Fragment) cheffragmentClass.newInstance();
                        chefbackStateName = chefMainfragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate(chefbackStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = cheffragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if (currentFragment != null && !currentFragment.getClass().equals(chefMainfragment.getClass())) {
                                cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).commit();
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    selected = true;
                    break;


                case R.id.navigation_notification:
                    dashboardView.setVisibility(View.INVISIBLE);
                    ordersView.setVisibility(View.INVISIBLE);
                    franchiseView.setVisibility(View.INVISIBLE);
                    reportsView.setVisibility(View.VISIBLE);

                    cheffragmentClass = ChefNotificationFragment.class;
                    try {
                        chefMainfragment = (Fragment) cheffragmentClass.newInstance();
                        chefbackStateName = chefMainfragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate(chefbackStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = cheffragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if (currentFragment != null && !currentFragment.getClass().equals(chefMainfragment.getClass())) {
                                cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).commit();
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    selected = true;
                    break;
                //  return true;

            }
            return selected;
        }
    };

    RecyclerView sideMenuRecyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    List<MainMenu> mainMenuList = new ArrayList<>();
    Typeface font1;
    Typeface poppinsMedium;
    ImageView user_profile_image;
    TextView name;
    TextView location;
    TextView logoutFontAwesome;
    TextView logoutMenu;
    Switch availableSwitch,consumerSwitch;
    TextView placeName;
    public static LinearLayout dashboardView;
    public static LinearLayout ordersView;
    public static LinearLayout franchiseView;
    public static LinearLayout reportsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timerHandler.postDelayed(timerRunnable, 0);
        mStateManager = AuthStateManager.getInstance(this);
        setContentView(R.layout.activity_chef_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorGreen));
        }
        chefsMainActivity = this;
        cheftoolbar = (Toolbar) findViewById(R.id.toolbar);
        searchLayout = findViewById(R.id.searchLayout);
        searchSpinner = findViewById(R.id.searchSpinner);
        arrowImage = findViewById(R.id.arrowImage);
        availableSwitch = findViewById(R.id.availableSwitch);
        consumerSwitch = findViewById(R.id.consumerSwitch);
        placeName = findViewById(R.id.placeName);
        availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    try {
                        setActiveorDeactiveStatus(true, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    try {
                        setActiveorDeactiveStatus(true, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        consumerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {


                //chef
                    Intent verifOTPIntent = new Intent(chefsMainActivity, ChefsMainActivity.class);
                    verifOTPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    verifOTPIntent.putExtra("Role","Chef");
                    chefsMainActivity.startActivity(verifOTPIntent);
                    chefsMainActivity.finish();

                } else {
                //consumer

                    SaveSharedPreference.saveChef(chefsMainActivity,"chef");

                    if(SaveSharedPreference.getLoggedInUserAddress(chefsMainActivity).equals(""))
                    {
                        Intent intent = new Intent(chefsMainActivity, LocationSelectionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        chefsMainActivity.startActivity(intent);
                        chefsMainActivity.finish();
                    }
                    else
                    {
                        Intent intent = new Intent(chefsMainActivity, ConsumerMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        chefsMainActivity.startActivity(intent);
                        chefsMainActivity.finish();
                    }
                }
            }
        });



        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchSpinner.performClick();
            }
        });
        setSupportActionBar(cheftoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        chefDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, chefDrawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, chefDrawer,
                null, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                sideMenuRecyclerView.setVisibility(View.GONE);
                getSideMenus("First");
                getUserInfo();
            }
        };
        chefDrawer.setDrawerListener(toggle);
        cheftoolbar.setNavigationIcon(R.drawable.foodi_logo_left_image);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.foodi_logo_left_image);
        toggle.syncState();

        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chefDrawer.openDrawer(Gravity.LEFT);
            }
        });


        chefnavigation = (BottomNavigationView) findViewById(R.id.navigation);
        chefnavigationView = findViewById(R.id.nav_view);

        chefnavigationView.setItemIconTintList(null);

        cheffragmentManager = getSupportFragmentManager();


        BottomNavigationTextFont.persian_iran_font(getApplicationContext(), chefnavigation);
        chefnavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Class fragmentClass = null;

        fragmentClass = ChefHomeFragment.class;


        try {
            chefMainfragment = (Fragment) fragmentClass.newInstance();
            chefbackStateName = chefMainfragment.getClass().getName();

            FragmentManager manager = getSupportFragmentManager();


            //fragment not in back stack, create it.


            // Return if the class are the same

            cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).commit();


        } catch (Exception e) {
            e.printStackTrace();
        }

        View hView = chefnavigationView.getHeaderView(0);
        sideMenuRecyclerView = hView.findViewById(R.id.recyclerView);
        logoutFontAwesome = hView.findViewById(R.id.logoutFontAwesome);
        logoutMenu = hView.findViewById(R.id.logoutMenu);


        logoutFontAwesome.setText(Html.fromHtml("&#xf08b;"));

        logoutMenu.setTypeface(poppinsMedium);

        shimmerFrameLayout = hView.findViewById(R.id.shimmerLayout);
        user_profile_image = hView.findViewById(R.id.userProfileImage);
        name = hView.findViewById(R.id.name);
        location = hView.findViewById(R.id.location);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        sideMenuRecyclerView.setVisibility(View.GONE);
        sideMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBottomLayout = findViewById(R.id.mainBottomLayout);
        mainBottomLayout.setVisibility(View.VISIBLE);
        dashboardView = findViewById(R.id.dashboardView);
        ordersView = findViewById(R.id.ordersView);
        franchiseView = findViewById(R.id.franchiseView);
        reportsView = findViewById(R.id.reportsView);

        dashboardView.setVisibility(View.VISIBLE);
        ordersView.setVisibility(View.INVISIBLE);
        franchiseView.setVisibility(View.INVISIBLE);
        reportsView.setVisibility(View.INVISIBLE);

        font1 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");

        getUserInfo();


        if (SaveSharedPreference.getLoggedInUserRole(chefsMainActivity).equals("")) {

        } else {
            try {
                updatedDeviceToken();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * On Back Pressed Build In Method
     **/
    @Override
    public void onBackPressed() {
        if (cheffragmentManager.getBackStackEntryCount() > 0) {

            cheffragmentManager.popBackStack();


        } else {

            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            SuperActivityToast.create(ChefsMainActivity.this)
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
     * To Get the Chefs Side Menu's From API Call(GET)
     **/
    public void getSideMenus(String from) {
        String url = APIBaseURL.chefSideMenu;

        if (from.equals("First")) {
            shimmerFrameLayout.startShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            sideMenuRecyclerView.setVisibility(View.GONE);

        }


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (from.equals("First")) {
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    sideMenuRecyclerView.setVisibility(View.VISIBLE);
                }

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    mainMenuList.clear();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.setName(dataObject.optString("title"));
                        mainMenu.setId(dataObject.optString("id"));
                        mainMenu.setCount(dataObject.optString("count"));
                        mainMenu.setImage(dataObject.optString("icon").replaceAll(" ", ""));

                        JSONArray subMenuChildrenArray = dataObject.getJSONArray("children");

                        List<SubMenu> subMenuList = new ArrayList<>();

                        for (int j = 0; j < subMenuChildrenArray.length(); j++) {
                            JSONObject subMenuObject = subMenuChildrenArray.getJSONObject(j);
                            SubMenu subMenu = new SubMenu();
                            subMenu.setName(subMenuObject.optString("title"));
                            subMenu.setId(subMenuObject.optString("id"));
                            subMenu.setImage(/*"\\u"+*/subMenuObject.optString("icon").replaceAll(" ", "")/*+";"*/);
                            subMenu.setImageClass(subMenuObject.optString("iconclass"));
                            subMenuList.add(subMenu);
                            mainMenu.setSubMenuList(subMenuList);
                        }

                        mainMenuList.add(mainMenu);
                    }
                    sideMenuRecyclerView.setAdapter(new ChefLeftMenuAdapter(ChefsMainActivity.this, mainMenuList, font1, poppinsMedium, 0));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ChefsMainActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ChefsMainActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChefsMainActivity.this);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(ChefsMainActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, ChefsMainActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "menu_taq");
    }

    /**
     * To Get the Logged in Chefs Profile  From API Call(GET)
     **/
    public void getUserInfo() {

        String url = APIBaseURL.chefProfile + SaveSharedPreference.getLoggedInWorkFlowID(ChefsMainActivity.this);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataObject = jsonObject.getJSONArray("data");
                    JSONObject arrayData = new JSONObject();
                    arrayData = dataObject.getJSONObject(0);
                    name.setText("Hi " + arrayData.optString("name"));
                    chefNamefromAPI = arrayData.optString("name");
                    chefIDfromAPI = arrayData.optString("id");
                    JSONObject locationObject = new JSONObject();
                    if (arrayData.has("location")) {
                        locationObject = arrayData.getJSONObject("location");
                    }
                    location.setText(locationObject.optString("name"));
                    placeName.setText(locationObject.optString("name"));

                    Glide.with(ChefsMainActivity.this).load(arrayData.optString("uploadLogo")).placeholder(R.drawable.customer_user_profile_left_menu).into(user_profile_image);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ChefsMainActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ChefsMainActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChefsMainActivity.this);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(ChefsMainActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, ChefsMainActivity.this);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");
    }

    /**
     * To make the Chefs Cooked Dishes to be active or Inactive(POST)
     **/

    private void setActiveorDeactiveStatus(Boolean status, Boolean dishType) throws JSONException {

        String url = APIBaseURL.activeOrDeactiveStatus;

        JSONObject inputObject = new JSONObject();
        inputObject.put("chefId", SaveSharedPreference.getLoggedInWorkFlowID(ChefsMainActivity.this));
        inputObject.put("dishId", "");
        inputObject.put("forAllDishes", status);
        inputObject.put("status", dishType);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(ChefsMainActivity.this, response.optString("errorMessage"), Toast.LENGTH_SHORT).show();
                try {
                    //getChefMenus();
                    ChefsMenuFragment.index=0;
                    ChefsMenuFragment.getAllChefMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ChefsMainActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ChefsMainActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChefsMainActivity.this);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(ChefsMainActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(chefsMainActivity));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    /**
     * To logout from Azure and go to intro page
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

        SharedPreferences mySPrefs = SaveSharedPreference.getPreferences(chefsMainActivity);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.clear();
        editor.apply();


        Intent mainIntent = new Intent(chefsMainActivity, IntroScreenActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        chefsMainActivity.startActivity(mainIntent);
        chefsMainActivity.finish();

    }


    /**
     * update Firebase Token in API for Push Notification(FCM)
     **/
    public void updatedDeviceToken() throws JSONException {
        JSONObject inputObject = new JSONObject();
        inputObject.put("emailid", SaveSharedPreference.getLoggedInUserEmail(chefsMainActivity));
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

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(chefsMainActivity));

                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

}
