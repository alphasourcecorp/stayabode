package stayabode.foodyHive.activities.platform;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.platform.MainMenuAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.fragments.chefs.ChefHomeFragment;
import stayabode.foodyHive.fragments.platforms.ChefFragment;
import stayabode.foodyHive.fragments.platforms.EventsListFragment;
import stayabode.foodyHive.fragments.platforms.FranchiseFragment;
import stayabode.foodyHive.fragments.platforms.HomeFragment;
import stayabode.foodyHive.fragments.platforms.MyProfileFragment;
import stayabode.foodyHive.fragments.platforms.NotificationFragment;
import stayabode.foodyHive.fragments.platforms.OrdersFragment;
import stayabode.foodyHive.fragments.platforms.ReportsFragment;
import stayabode.foodyHive.fragments.platforms.SettingsFragment;
import stayabode.foodyHive.models.MainMenu;
import stayabode.foodyHive.models.SubMenu;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.BottomNavigationTextFont;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    NavigationView profileNavigationView;
    public static DrawerLayout drawer;
    public static FragmentManager fragmentManager;

    public static HomeFragment homeFragment = new HomeFragment();
    public static FranchiseFragment franchiseFragment = new FranchiseFragment();
    public static NotificationFragment notificationFragment = new NotificationFragment();
    public static ReportsFragment reportsFragment = new ReportsFragment();
    public static OrdersFragment ordersFragment = new OrdersFragment();
//    public static CloudKitchenFragment cloudKitchenFragment = new CloudKitchenFragment();
//    public static ChefFragment chefFragment = new ChefFragment();
//    public static ConsumerFragment consumerFragment = new ConsumerFragment();
//    public static BulkOrderFragment bulkOrderFragment = new BulkOrderFragment();
//    public static SubscriptionFragment subscriptionFragment = new SubscriptionFragment();
//    public static DeliveryPartnerFragment deliveryPartnerFragment = new DeliveryPartnerFragment();
//    public static PaymentGatewayFragment paymentGatewayFragment = new PaymentGatewayFragment();
//    public static RevenueSharingFragment revenueSharingFragment = new RevenueSharingFragment();
//    public static MyProfileFragment myProfileFragment = new MyProfileFragment();
//    public static EventsListFragment eventsListFragment = new EventsListFragment();
    public static Fragment active = homeFragment;
    //CircleImageView profileImage;
    Typeface fontBold;
    Typeface fontRegular;

    LinearLayout settingsLayout;
    LinearLayout profileLayout;
    LinearLayout calendarLayout;
    LinearLayout languageLayout;
    LinearLayout currencyLayout;
    LinearLayout logoutLayout;

    TextView settings;
    TextView profile;
    TextView calendar;
    TextView language;
    TextView currency;
    TextView logout;
    TextView username;

    RecyclerView recyclerView;
    public static TextView toolbar_title;
    public static BottomNavigationView navigation;
    public static LinearLayout mainBottomLayout;
    //ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;

    public static Fragment nowActiveFragment = homeFragment;
    public static Fragment fragment = null;
    public static Class fragmentClass = null;
    public static String backStateName = null;
    public static boolean selected = false;
    public static Menu moreMenu=null;
//    LinearLayout dashboardView;
//    LinearLayout ordersView;
//    LinearLayout franchiseView;
//    LinearLayout reportsView;
//    LinearLayout notificationView;





    public static String Role = "";
    public static Toolbar toolbar;
    public static LinearLayout customIcon;
    public static TextView toolbar_save;
    public static LinearLayout rightMenu;
    public static RelativeLayout logoutMenu;


    TextView reportsText;
    TextView settingsText;
    TextView myProfileText;
    TextView calendarText;
    TextView logoutText;
    TextView orderText;
    boolean doubleBackToExitPressedOnce = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        fragmentManager = getSupportFragmentManager();




            switch (item.getItemId()) {
                case R.id.navigation_my_dashboard:
                    rightMenu.setVisibility(View.GONE);

                    toolbar_title.setText("Dashboard");
//                    dashboardView.setVisibility(View.VISIBLE);
//                    ordersView.setVisibility(View.INVISIBLE);
//                    franchiseView.setVisibility(View.INVISIBLE);
//                    reportsView.setVisibility(View.INVISIBLE);
//                    notificationView.setVisibility(View.INVISIBLE);
//                    fragmentManager.beginTransaction().hide(active).show(homeFragment)./*setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).*/commit();
//                    active = homeFragment;
                    //fragmentManager.beginTransaction().replace(R.id.content,homeFragment).commit();
                    nowActiveFragment = homeFragment;
                    fragmentClass= HomeFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        backStateName = fragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(fragment.getClass())) {
                                fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                   // return true;

                case R.id.navigation_orders:

                        rightMenu.setVisibility(View.GONE);


                    toolbar_title.setText("Orders");
//                    dashboardView.setVisibility(View.INVISIBLE);
//                    ordersView.setVisibility(View.VISIBLE);
//                    franchiseView.setVisibility(View.INVISIBLE);
//                    reportsView.setVisibility(View.INVISIBLE);
//                    notificationView.setVisibility(View.INVISIBLE);
//                    fragmentManager.beginTransaction().hide(active).show(ordersFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//                    active = ordersFragment;
                    //fragmentManager.beginTransaction().replace(R.id.content,ordersFragment).commit();
                    nowActiveFragment = ordersFragment;
                    fragmentClass= OrdersFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        backStateName = fragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(fragment.getClass())) {
                                fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    selected = true;
                    break;
                  //  return true;
                case R.id.navigation_franchise:

                    rightMenu.setVisibility(View.GONE);

                    if(item.getTitle().equals("Chefs"))
                    {
                        toolbar_title.setText("Franchisee Status");
//                        dashboardView.setVisibility(View.INVISIBLE);
//                        ordersView.setVisibility(View.INVISIBLE);
//                        franchiseView.setVisibility(View.VISIBLE);
//                        reportsView.setVisibility(View.INVISIBLE);
//                        notificationView.setVisibility(View.INVISIBLE);
//                    fragmentManager.beginTransaction().hide(active).show(franchiseFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//                    active = franchiseFragment;
                        //fragmentManager.beginTransaction().replace(R.id.content,franchiseFragment).commit();
                        nowActiveFragment = franchiseFragment;
                        fragmentClass= ChefFragment.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                            backStateName = fragment.getClass().getName();

                            FragmentManager manager = getSupportFragmentManager();
                            boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                            if (!fragmentPopped) {

                                Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
                                // Return if the class are the same
                                if(currentFragment!=null&&!currentFragment.getClass().equals(fragment.getClass())) {
                                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        toolbar_title.setText("Franchisee Status");
//                        dashboardView.setVisibility(View.INVISIBLE);
//                        ordersView.setVisibility(View.INVISIBLE);
//                        franchiseView.setVisibility(View.VISIBLE);
//                        reportsView.setVisibility(View.INVISIBLE);
//                        notificationView.setVisibility(View.INVISIBLE);
//                    fragmentManager.beginTransaction().hide(active).show(franchiseFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//                    active = franchiseFragment;
                        //fragmentManager.beginTransaction().replace(R.id.content,franchiseFragment).commit();
                        nowActiveFragment = franchiseFragment;
                        fragmentClass= FranchiseFragment.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                            backStateName = fragment.getClass().getName();

                            FragmentManager manager = getSupportFragmentManager();
                            boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                            if (!fragmentPopped) {

                                Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
                                // Return if the class are the same
                                if(currentFragment!=null&&!currentFragment.getClass().equals(fragment.getClass())) {
                                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    selected = true;
                    break;
                   // return true;


                /*case R.id.navigation_reports:
                    toolbar_title.setText("Reports");
                    dashboardView.setVisibility(View.INVISIBLE);
                    ordersView.setVisibility(View.INVISIBLE);
                    franchiseView.setVisibility(View.INVISIBLE);
                    reportsView.setVisibility(View.VISIBLE);
                    notificationView.setVisibility(View.INVISIBLE);
                    fragmentManager.beginTransaction().hide(active).show(reportsFragment)*//*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*//*.commit();
                    active = reportsFragment;
                    fragmentManager.beginTransaction().replace(R.id.content,reportsFragment).commit();
                    nowActiveFragment = reportsFragment;
                    fragmentClass= ReportsFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        backStateName = fragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(fragment.getClass())) {
                                fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    selected = true;
                    break;*/
                   // return true;
                case R.id.navigation_more:
                    toolbar_title.setText("More");
//                    PopupMenu popup = new PopupMenu(MainActivity.this,navigation);
//                    //Inflating the Popup using xml file
//                    popup.getMenuInflater().inflate(R.menu.right_menus, popup.getMenu());
//                    popup.setGravity(Gravity.RIGHT);
//
//                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        public boolean onMenuItemClick(MenuItem item) {
////                            Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
//                            return true;
//                        }
//                    });
//
//                    popup.show();
                    // Inflate the popup_layout.xml

//                    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View layout = layoutInflater.inflate(R.layout.bottom_more_menu_layout, null);
//
//                    // Creating the PopupWindow
//                    PopupWindow changeStatusPopUp = new PopupWindow(MainActivity.this);
//                    changeStatusPopUp.setContentView(layout);
//                    changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
//                    changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//                    changeStatusPopUp.setFocusable(true);
//
//                    // Displaying the popup at the specified location, + offsets.
//                    changeStatusPopUp.showAtLocation(layout, Gravity.RIGHT, 0, 0);
                    if(rightMenu.getVisibility() == View.VISIBLE)
                    {
                        rightMenu.setVisibility(View.GONE);
                    }
                    else
                    {
                        rightMenu.setVisibility(View.VISIBLE);
                    }

                    break;


                case R.id.navigation_notification:
                    toolbar_title.setText("Notifications");
//                    dashboardView.setVisibility(View.INVISIBLE);
//                    ordersView.setVisibility(View.INVISIBLE);
//                    franchiseView.setVisibility(View.INVISIBLE);
//                    reportsView.setVisibility(View.INVISIBLE);
//                    notificationView.setVisibility(View.VISIBLE);
//                    fragmentManager.beginTransaction().hide(active).show(notificationFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//                    active = notificationFragment;
                    //fragmentManager.beginTransaction().replace(R.id.content,notificationFragment).addToBackStack(null).commit();
                    rightMenu.setVisibility(View.GONE);

                    nowActiveFragment = notificationFragment;
                    fragmentClass= NotificationFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        backStateName = fragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(fragment.getClass())) {
                                fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        fontBold = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Regular.ttf");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        setTitle("Dashboard");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        customIcon = toolbar.findViewById(R.id.customIcon);
        toolbar_save = toolbar.findViewById(R.id.toolbar_save);
        customIcon.setVisibility(View.GONE);
//        dashboardView = findViewById(R.id.dashboardView);
//        ordersView = findViewById(R.id.ordersView);
//        franchiseView = findViewById(R.id.franchiseView);
//        reportsView = findViewById(R.id.reportsView);
//        notificationView = findViewById(R.id.notificationView);
//
//        dashboardView.setVisibility(View.VISIBLE);
//        ordersView.setVisibility(View.INVISIBLE);
//        franchiseView.setVisibility(View.INVISIBLE);
//        reportsView.setVisibility(View.INVISIBLE);
//        notificationView.setVisibility(View.INVISIBLE);
        toolbar_title.setText("Dashboard");
        toolbar_title.setTypeface(fontBold);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setHomeAsUpIndicator(R.drawable.hamburger_icon);
        toggle.syncState();
      //  toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        navigationView =  findViewById(R.id.nav_view);
        profileNavigationView =  findViewById(R.id.profileNavigationView);
        reportsText =  findViewById(R.id.reportsText);
        settingsText =  findViewById(R.id.settingsText);
        myProfileText =  findViewById(R.id.myProfileText);
        calendarText =  findViewById(R.id.calendarText);
        logoutText =  findViewById(R.id.logoutText);
        orderText =  findViewById(R.id.orderText);
        reportsText.setTypeface(fontRegular);
        settingsText.setTypeface(fontRegular);
        myProfileText.setTypeface(fontRegular);
        calendarText.setTypeface(fontRegular);
        logoutText.setTypeface(fontRegular);
        orderText.setTypeface(fontRegular);
        settingsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenu.setVisibility(View.GONE);
            }
        });
        reportsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenu.setVisibility(View.GONE);
            }
        });
        myProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenu.setVisibility(View.GONE);
            }
        });
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenu.setVisibility(View.GONE);
//                SaveSharedPreference.setLoggedIn(getApplicationContext(), false,"","");
                logout();
            }
        });
        orderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenu.setVisibility(View.GONE);
            }
        });
        calendarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenu.setVisibility(View.GONE);
            }
        });
        settingsLayout =  profileNavigationView.findViewById(R.id.settingsLayout);
        profileLayout =  profileNavigationView.findViewById(R.id.profileLayout);
        calendarLayout =  profileNavigationView.findViewById(R.id.calendarLayout);
        languageLayout =  profileNavigationView.findViewById(R.id.languageLayout);
        currencyLayout =  profileNavigationView.findViewById(R.id.currencyLayout);
        logoutLayout =  profileNavigationView.findViewById(R.id.logoutLayout);
        View headerView = navigationView.getHeaderView(0);
        recyclerView =  headerView.findViewById(R.id.recyclerView);


        shimmerFrameLayout =  headerView.findViewById(R.id.shimmerLayout);
       // progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        settings =  profileNavigationView.findViewById(R.id.settings);
        profile =  profileNavigationView.findViewById(R.id.profile);
        calendar =  profileNavigationView.findViewById(R.id.calendar);
        language =  profileNavigationView.findViewById(R.id.language);
        currency =  profileNavigationView.findViewById(R.id.currency);
        logout =  profileNavigationView.findViewById(R.id.logout);
        username =  profileNavigationView.findViewById(R.id.username);
        rightMenu =  findViewById(R.id.right_more_menu);
        logoutMenu =  findViewById(R.id.logoutMenu);
        rightMenu.setVisibility(View.GONE);

        //profileImage =  findViewById(R.id.profileImage);

        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navigation.setVisibility(View.GONE);
                drawer.closeDrawers();

                SettingsFragment fragment = new SettingsFragment();
                FragmentManager fm = MainActivity.fragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });


        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,MyProfileActivity.class);
//                startActivity(intent);
//                navigation.setVisibility(View.GONE);
                drawer.closeDrawers();
//                fragmentManager.beginTransaction().hide(active).show(myProfileFragment).commit();
//                active = myProfileFragment;
//                MyProfileFragment fragment = new MyProfileFragment();
//                FragmentManager fm = MainActivity.fragmentManager;
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.content, fragment).addToBackStack(null);
//                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.commit();
                fragmentClass= MyProfileFragment.class;
                try {
                    fragment  = (Fragment) fragmentClass.newInstance();
                    backStateName = fragment.getClass().getName();

                    FragmentManager manager = fragmentManager;
                    boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                    if (!fragmentPopped) {

                        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
                        // Return if the class are the same
                        if(currentFragment!=null&&!currentFragment.getClass().equals(fragment.getClass())) {
                            fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(backStateName).commit();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,EventsListActivity.class);
//                startActivity(intent);
//                navigation.setVisibility(View.GONE);
                drawer.closeDrawers();
//                fragmentManager.beginTransaction().hide(active).show(eventsListFragment).commit();
//                active = eventsListFragment;
//                EventsListFragment fragment = new EventsListFragment();
//                FragmentManager fm = MainActivity.fragmentManager;
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.content, fragment).addToBackStack(null);
//               // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.commit();
                fragmentClass= EventsListFragment.class;
                try {
                    fragment  = (Fragment) fragmentClass.newInstance();
                    backStateName = fragment.getClass().getName();

                    FragmentManager manager = fragmentManager;
                    boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                    if (!fragmentPopped) {

                        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
                        // Return if the class are the same
                        if(currentFragment!=null&&!currentFragment.getClass().equals(fragment.getClass())) {
                            fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(backStateName).commit();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });

        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
//                SaveSharedPreference.setLoggedIn(getApplicationContext(), false,"","");
                 logout();

            }
        });

        logoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightMenu.setVisibility(View.GONE);
//                SaveSharedPreference.setLoggedIn(getApplicationContext(), false,"","");
                logout();
            }
        });
        settings.setTypeface(fontRegular);
        profile.setTypeface(fontRegular);
        calendar.setTypeface(fontRegular);
        language.setTypeface(fontRegular);
        currency.setTypeface(fontRegular);
        logout.setTypeface(fontRegular);

        username.setTypeface(fontBold);


        /*profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });*/

        navigationView.setNavigationItemSelectedListener(this);

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if(toolbar.getChildAt(i) instanceof ImageButton){
                toolbar.getChildAt(i).setScaleX(1.5f);
                toolbar.getChildAt(i).setScaleY(1.5f);
            }
        }

        fragmentManager = getSupportFragmentManager();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mainBottomLayout =  findViewById(R.id.mainBottomLayout);
        BottomNavigationTextFont.persian_iran_font(getApplicationContext(), navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.navigation_my_dashboard);
        Menu menu = navigation.getMenu();
        Role = getIntent().getStringExtra("Role");
        if(getIntent().getStringExtra("Role").equals("Franchisee"))
        {
            // find MenuItem you want to change
            MenuItem nav_camara = menu.findItem(R.id.navigation_franchise);

            // set new title to the MenuItem
            nav_camara.setTitle("Chefs");
        }
        else
        {
            // find MenuItem you want to change
            MenuItem nav_camara = menu.findItem(R.id.navigation_franchise);

            // set new title to the MenuItem
            nav_camara.setTitle("Franchisees");

        }

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_count_text, itemView, true);


        //fragmentManager.beginTransaction().replace(R.id.content,homeFragment).commit();
                Class fragmentClass = null;
                String backStateName = null;
                boolean selected = false;
                if(Role.equals("Chef"))
                {
                    fragmentClass = ChefHomeFragment.class;
                }
                else
                {
                    fragmentClass = HomeFragment.class;
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    backStateName = fragment.getClass().getName();

                    FragmentManager manager = getSupportFragmentManager();


                    //fragment not in back stack, create it.


                    // Return if the class are the same

                    Log.d("currentFragment!=null","currentFragment!=null");
                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();


                } catch (Exception e) {
                    e.printStackTrace();
                }



        //fragmentManager.beginTransaction().add(R.id.content, homeFragment,"1")/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//        fragmentManager.beginTransaction().add(R.id.content,ordersFragment,"2").hide(ordersFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
//        fragmentManager.beginTransaction().add(R.id.content,franchiseFragment,"3").hide(franchiseFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//        fragmentManager.beginTransaction().add(R.id.content,reportsFragment,"4").hide(reportsFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//        fragmentManager.beginTransaction().add(R.id.content,notificationFragment,"5").hide(notificationFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//        fragmentManager.beginTransaction().add(R.id.content,cloudKitchenFragment,"6").hide(cloudKitchenFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,chefFragment,"7").hide(chefFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,consumerFragment,"8").hide(consumerFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,bulkOrderFragment,"9").hide(bulkOrderFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,subscriptionFragment,"9").hide(subscriptionFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,deliveryPartnerFragment,"10").hide(deliveryPartnerFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,paymentGatewayFragment,"11").hide(paymentGatewayFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,revenueSharingFragment,"12").hide(revenueSharingFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,myProfileFragment,"13").hide(myProfileFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.content,eventsListFragment,"14").hide(eventsListFragment).commit();


//        navigation.setVisibility(View.VISIBLE);

//        getSideBarMenuItems();
        getSideMenus();


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Called","MainResume");
//        fragmentManager.beginTransaction().add(R.id.content, homeFragment,"1")/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//        fragmentManager.beginTransaction().add(R.id.content,ordersFragment,"2").hide(ordersFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
//        fragmentManager.beginTransaction().add(R.id.content,franchiseFragment,"3").hide(franchiseFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//        fragmentManager.beginTransaction().add(R.id.content,reportsFragment,"4").hide(reportsFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
//        fragmentManager.beginTransaction().add(R.id.content,notificationFragment,"5").hide(notificationFragment)/*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)*/.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.nav_dashboard)
        {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getSideMenus()
    {
        String url = APIBaseURL.LeftSideMenuURL;
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
                    for (int i=0;i < dataArray.length();i++)
                    {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.setName(dataObject.optString("title"));
                        mainMenu.setId(dataObject.optString("id"));
                        mainMenu.setImage(/*"\\u"+*/dataObject.optString("icon").replaceAll(" ","")/*+";"*/);
                        mainMenu.setImageClass(dataObject.optString("iconclass"));

                        JSONArray subMenuChildrenArray = dataObject.getJSONArray("children");

                        List<SubMenu> subMenuList = new ArrayList<>();

                        for (int j=0;j < subMenuChildrenArray.length();j++)
                        {
                            JSONObject subMenuObject = subMenuChildrenArray.getJSONObject(j);
                            SubMenu subMenu = new SubMenu();
                            subMenu.setName(subMenuObject.optString("title"));
                            subMenu.setId(subMenuObject.optString("id"));
                            subMenu.setImage(/*"\\u"+*/subMenuObject.optString("icon").replaceAll(" ","")/*+";"*/);
                            subMenu.setImageClass(subMenuObject.optString("iconclass"));
                            subMenuList.add(subMenu);
                            mainMenu.setSubMenuList(subMenuList);
                        }
                        mainMenuList.add(mainMenu);
                    }
                    recyclerView.setAdapter(new MainMenuAdapter(MainActivity.this,mainMenuList,fontBold,fontRegular));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error",error.getMessage() + " Timeout Error");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthError");
//                } else if (error instanceof ServerError) {
//                    //TODO
//                    Log.d("Error","ServerError");
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error","Network Error");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error","ParseError");
//                }


            }
        },MainActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"menu_taq");
    }

    @Override
    public void onBackPressed() {


//        if(active == homeFragment || active == ordersFragment || active == franchiseFragment || active == reportsFragment || active == notificationFragment)
//        {
//
//        }

        if (fragmentManager.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
//            if(navigation.getSelectedItemId() == R.id.navigation_orders || navigation.getSelectedItemId() == R.id.navigation_franchise)
//            {
//                navigation.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                navigation.setVisibility(View.GONE);
//            }
//            if(navigation.getSelectedItemId() == R.id.navigation_my_dashboard || navigation.getSelectedItemId() == R.id.navigation_orders || navigation.getSelectedItemId() == R.id.navigation_franchise || navigation.getSelectedItemId() == R.id.navigation_reports || navigation.getSelectedItemId() == R.id.navigation_notification)
//            {
//
//            }
//            else {
//
////            }
//            if(fragment instanceof HomeFragment || fragment instanceof OrdersFragment || fragment instanceof ReportsFragment || fragment instanceof NotificationFragment || fragment instanceof FranchiseFragment)
//            {
//                finish();
//            }
//            else
//            {
                fragmentManager.popBackStack();
//            }

        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
//            super.onBackPressed();
//            finish();
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            SuperActivityToast.create(MainActivity.this)
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
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }



//    @Override
//    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
//        if (menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod(
//                            "setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (Exception e) {
//                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
//                }
//            }
//        }
//        return super.onPrepareOptionsPanel(view, menu);
//    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                    Log.e("TAG", "onMenuOpened", e);
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public void logout()
    {
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
    }

}
