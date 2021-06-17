package stayabode.foodyHive.corporatemenurange;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAdapterRange;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.CorporateDish;
import stayabode.foodyHive.models.CorporateMenuModel;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stayabode.foodyHive.activities.consumers.AboutUsAndGetHelpWebViewActivity;

public class CorporateMenuWithNavigatioin extends AppCompatActivity {

    ListView navLV;
    ImageView close_navigation_drawer;
    TextView menu_drawer_open_id;
    private static final String TAG = CorporateMenuWithNavigatioin.class.getName();

    CorporateExpandableListAdapterRange listAdapter;
    ExpandableListView expListView;
//    List<String> listDataHeader;
//    HashMap<String, List<String>> listDataChild;

    List<CorporateMenuModel> items = new ArrayList<>();
    List<CorporateDish> CorporateDish_list = new ArrayList<>();
    List<CorporateDish.MenuDetail> MenuDetail_list=new ArrayList<>();

    Button view_basket_id;
    TextView whatsapp_id;
    public static CorporateMenuWithNavigatioin corporateMenuWithNavigatioin;
    ImageView menu_id_menu;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String jsonurl = "https://foodyhivestorage.blob.core.windows.net/assets/data/subscription-requests.json";

    DrawerLayout drawer;
    private ProgressDialog pDialog;
    TextView cartTotalCountText;
    Button logout_id;
    Button home_id,about_id;

    ImageView cartIcon,mylist_id;

    LinearLayout adminhide;
    TextView all_id,breakfast_id,lunch_id,dinner_id,snacks_id;
    //String  mealType="All";

    androidx.constraintlayout.widget.ConstraintLayout cart_con;
    Boolean clickall=false;


    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_corporate_menu_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        corporateMenuWithNavigatioin=this;

        drawer= findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        GetscheduleCount();

        home_id=findViewById(R.id.home_id);
        about_id=findViewById(R.id.about_id);


        mylist_id = findViewById(R.id.mylist_id);
        cart_con = findViewById(R.id.cart_con);

        all_id = findViewById(R.id.all_id);
        breakfast_id = findViewById(R.id.breakfast_id);
        lunch_id = findViewById(R.id.lunch_id);
        dinner_id = findViewById(R.id.dinner_id);
        snacks_id = findViewById(R.id.snacks_id);





        home_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globaluse.frommenu="home";

                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, ColiveAndStayabodeHomeActivity.class);
                startActivity(intent);
            }
        });

        about_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globaluse.frommenu="about";
                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, ColiveAndStayabodeHomeActivity.class);
                startActivity(intent);
            }
        });


        all_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final int sdk = android.os.Build.VERSION.SDK_INT;
//                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                } else {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                }
//                all_id.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.corporate_menu_bg_yellow, null));
//                breakfast_id.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.corporate_menu_bg_yellow, null));
//                lunch_id.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.corporate_menu_bg_yellow, null));
//                dinner_id.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.corporate_menu_bg_yellow, null));
//                snacks_id.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.corporate_menu_bg_yellow, null));

                all_id.setTextColor(Color.parseColor("#F1102F"));
                breakfast_id.setTextColor(Color.parseColor("#000000"));
                lunch_id.setTextColor(Color.parseColor("#000000"));
                dinner_id.setTextColor(Color.parseColor("#000000"));
                snacks_id.setTextColor(Color.parseColor("#000000"));

                //all_id.setPadding(20,10,20,10);
                    Globaluse.Filtertype="All";

                    clickall=true;
                //sendAndRequestResponse();
//
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);

                //finishAffinity();
                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateMenuWithNavigatioin.class);
                overridePendingTransition(0, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
        breakfast_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int sdk = android.os.Build.VERSION.SDK_INT;
//                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                } else {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                }



                all_id.setTextColor(Color.parseColor("#000000"));
                breakfast_id.setTextColor(Color.parseColor("#F1102F"));
                lunch_id.setTextColor(Color.parseColor("#000000"));
                dinner_id.setTextColor(Color.parseColor("#000000"));
                snacks_id.setTextColor(Color.parseColor("#000000"));
                Globaluse.Filtertype="Breakfast";
                //sendAndRequestResponse();

//finishAffinity();
                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateMenuWithNavigatioin.class);
                overridePendingTransition(0, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
            }
        });

        lunch_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final int sdk = android.os.Build.VERSION.SDK_INT;
//                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                } else {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                }


                all_id.setTextColor(Color.parseColor("#000000"));
                breakfast_id.setTextColor(Color.parseColor("#000000"));
                lunch_id.setTextColor(Color.parseColor("#F1102F"));
                dinner_id.setTextColor(Color.parseColor("#000000"));
                snacks_id.setTextColor(Color.parseColor("#000000"));
                Globaluse.Filtertype="Lunch";
               // sendAndRequestResponse();

//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
               // finishAffinity();
                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateMenuWithNavigatioin.class);
                overridePendingTransition(0, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        dinner_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final int sdk = android.os.Build.VERSION.SDK_INT;
//                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                } else {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                }
               // finishAffinity();
                all_id.setTextColor(Color.parseColor("#000000"));
                breakfast_id.setTextColor(Color.parseColor("#000000"));
                lunch_id.setTextColor(Color.parseColor("#000000"));
                dinner_id.setTextColor(Color.parseColor("#F1102F"));
                snacks_id.setTextColor(Color.parseColor("#000000"));
                Globaluse.Filtertype="Dinner";
                //sendAndRequestResponse();

//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
                //finishAffinity();
                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateMenuWithNavigatioin.class);
                overridePendingTransition(0, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
        snacks_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int sdk = android.os.Build.VERSION.SDK_INT;
//                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                } else {
//                    all_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    breakfast_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    lunch_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    dinner_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_white) );
//                    snacks_id.setBackgroundDrawable(ContextCompat.getDrawable(CorporateMenuWithNavigatioin.this, R.drawable.corporate_menu_bg_yellow) );
//                }


                all_id.setTextColor(Color.parseColor("#000000"));
                breakfast_id.setTextColor(Color.parseColor("#000000"));
                lunch_id.setTextColor(Color.parseColor("#000000"));
                dinner_id.setTextColor(Color.parseColor("#000000"));
                snacks_id.setTextColor(Color.parseColor("#F1102F"));
                Globaluse.Filtertype="Snacks";
                //sendAndRequestResponse();

//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
               // finishAffinity();
                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateMenuWithNavigatioin.class);
                overridePendingTransition(0, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


        if(Globaluse.Filtertype.equalsIgnoreCase("All"))
        {
//            if(clickall)
//            {
//                drawer.openDrawer(GravityCompat.START);
//            }
//

            all_id.setTextColor(Color.parseColor("#F1102F"));
            breakfast_id.setTextColor(Color.parseColor("#000000"));
            lunch_id.setTextColor(Color.parseColor("#000000"));
            dinner_id.setTextColor(Color.parseColor("#000000"));
            snacks_id.setTextColor(Color.parseColor("#000000"));
        }
        if(Globaluse.Filtertype.equalsIgnoreCase("Breakfast"))
        {
            drawer.openDrawer(GravityCompat.START);
            all_id.setTextColor(Color.parseColor("#000000"));
            breakfast_id.setTextColor(Color.parseColor("#F1102F"));
            lunch_id.setTextColor(Color.parseColor("#000000"));
            dinner_id.setTextColor(Color.parseColor("#000000"));
            snacks_id.setTextColor(Color.parseColor("#000000"));
        }
        if(Globaluse.Filtertype.equalsIgnoreCase("Lunch"))
        {
            drawer.openDrawer(GravityCompat.START);
            all_id.setTextColor(Color.parseColor("#000000"));
            breakfast_id.setTextColor(Color.parseColor("#000000"));
            lunch_id.setTextColor(Color.parseColor("#F1102F"));
            dinner_id.setTextColor(Color.parseColor("#000000"));
            snacks_id.setTextColor(Color.parseColor("#000000"));
        }
        if(Globaluse.Filtertype.equalsIgnoreCase("Dinner"))
        {
            drawer.openDrawer(GravityCompat.START);
            all_id.setTextColor(Color.parseColor("#000000"));
            breakfast_id.setTextColor(Color.parseColor("#000000"));
            lunch_id.setTextColor(Color.parseColor("#000000"));
            dinner_id.setTextColor(Color.parseColor("#F1102F"));
            snacks_id.setTextColor(Color.parseColor("#000000"));
        }
        if(Globaluse.Filtertype.equalsIgnoreCase("Snacks"))
        {
            drawer.openDrawer(GravityCompat.START);
            all_id.setTextColor(Color.parseColor("#000000"));
            breakfast_id.setTextColor(Color.parseColor("#000000"));
            lunch_id.setTextColor(Color.parseColor("#000000"));
            dinner_id.setTextColor(Color.parseColor("#000000"));
            snacks_id.setTextColor(Color.parseColor("#F1102F"));
        }





        mylist_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateMyRequestList.class);
                startActivity(intent);
            }
        });
        cartIcon = findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateAfterScheduleApi.class);
                startActivity(intent);
            }
        });

        menu_id_menu=findViewById(R.id.menu_id_menu);
        menu_id_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(GravityCompat.START);
            }
        });
        cartTotalCountText=findViewById(R.id.cartTotalCountText);



        navLV = findViewById(R.id.navLV);

        close_navigation_drawer=findViewById(R.id.close_navigation_drawer);
        close_navigation_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        menu_drawer_open_id=findViewById(R.id.menu_drawer_open_id);
        menu_drawer_open_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(GravityCompat.START);
            }
        });

        whatsapp_id = (TextView) findViewById(R.id.whatsapp_id);
        whatsapp_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = "+91 6366225334"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = CorporateMenuWithNavigatioin.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(CorporateMenuWithNavigatioin.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }



//                boolean installed = appInstalledOrNot("com.whatsapp");
//                if (installed){
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+"8147189500" + "&text="+" "));
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(CorporateMenuWithNavigatioin.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
//                }
            }
        });






        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
//
//                Toast.makeText(CorporateMenuWithNavigatioin.this, " Collapsed",
//                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getActivity(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();


//                final Dialog dialog = new Dialog(CorporateMenuWithNavigatioin.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setCancelable(false);
//                dialog.setContentView(R.layout.corporate_list_detail);
//
////                    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
////                    text.setText(msg);
////
////                    Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
////                    dialogButton.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            dialog.dismiss();
////                        }
////                    });
//
//                dialog.show();

//                Toast.makeText(CorporateMenuWithNavigatioin.this, " Collapsed",
//                        Toast.LENGTH_SHORT).show();


            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getActivity(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();


//                Toast.makeText(CorporateMenuWithNavigatioin.this, " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getActivity(),
//                        listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT)
//                        .show();



//                    final Dialog dialog = new Dialog(CorporateMenuWithNavigatioin.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
//                    dialog.setContentView(R.layout.corporate_list_detail);
//
//                ImageView close_id = (ImageView) dialog.findViewById(R.id.close_id);
//                close_id.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.cancel();
//                    }
//                });
////
////                    Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
////                    dialogButton.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            dialog.dismiss();
////                        }
////                    });
//
//                    dialog.show();



//                Toast.makeText(CorporateMenuWithNavigatioin.this, " Collapsed",
//                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId)
            {
                expListView.expandGroup(itemPosition);
                return true;
            }
        });


        logout_id=findViewById(R.id.logout_id);
        logout_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globaluse.frommenu=" ";
                finishAffinity();
                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, ColiveAndStayabodeHomeActivity.class);
                startActivity(intent);

                SharedPreferences preferences =getSharedPreferences("corporateLogin",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                
            }
        });


        adminhide = findViewById(R.id.adminhide);
        adminhide.setVisibility(View.VISIBLE);



//        InputStream is = getResources().openRawResource(R.raw.subscription);
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try {
//            Reader reader = null;
//            try {
//                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            int n;
//            while (true) {
//                try {
//                    if (!((n = reader.read(buffer)) != -1)) break;
//                    writer.write(buffer, 0, n);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        String jsonObject1 = writer.toString();
//
//
//        JSONObject jsonObject = null;
//        try {
//
//            //  String data="{\"isSuccess\":true,\"errorMessage\":\"\",\"data\":[{\"id\":\"0001\",\"catname\":\"BRIYANI\",\"catimg\":\"https://cimavistorage.blob.core.windows.net/assets/55954582/Dark Chocolate Ice cream 2.png\",\"menucount\":\"3\",\"menuDetails\":[{\"dishId\":\"5fb71837f5cab8535c7820e6\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/55954582/Dark Chocolate Ice cream 2.png\"],\"dishName\":\"CHETTINAD CHICKEN BIRYANI\",\"saleAmount\":550,\"quantity\":1,\"mealPrice\":550,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":210,\"preparationTime\":1440,\"autoAcceptOrder\":true,\"tax\":2.93,\"isAvailable\":true},{\"dishId\":\"5fb71837f5cab8535c7820d8\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/697413609/Mini Pies.png\"],\"dishName\":\"CHETTINAD MUTTON BIRYANI\",\"saleAmount\":56.92,\"quantity\":1,\"mealPrice\":45,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":150,\"preparationTime\":90,\"autoAcceptOrder\":true,\"tax\":4.4,\"isAvailable\":true},{\"dishId\":\"5fb71837f5cab8535c7820d8\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/697413609/Mini Pies.png\"],\"dishName\":\"CHETTINAD EGG BIRYANI\",\"saleAmount\":56.92,\"quantity\":1,\"mealPrice\":45,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":150,\"preparationTime\":90,\"autoAcceptOrder\":true,\"tax\":4.4,\"isAvailable\":true}]},{\"id\":\"0001\",\"catname\":\"NON-VEG CURRIES\",\"catimg\":\"https://cimavistorage.blob.core.windows.net/assets/55954582/Dark Chocolate Ice cream 2.png\",\"menucount\":\"3\",\"menuDetails\":[{\"dishId\":\"5fb71837f5cab8535c7820e6\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/55954582/Dark Chocolate Ice cream 2.png\"],\"dishName\":\"CHETTINAD CHICKEN BIRYANI\",\"saleAmount\":550,\"quantity\":1,\"mealPrice\":550,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":210,\"preparationTime\":1440,\"autoAcceptOrder\":true,\"tax\":2.93,\"isAvailable\":true},{\"dishId\":\"5fb71837f5cab8535c7820d8\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/697413609/Mini Pies.png\"],\"dishName\":\"CHETTINAD MUTTON BIRYANI\",\"saleAmount\":56.92,\"quantity\":1,\"mealPrice\":45,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":150,\"preparationTime\":90,\"autoAcceptOrder\":true,\"tax\":4.4,\"isAvailable\":true},{\"dishId\":\"5fb71837f5cab8535c7820d8\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/697413609/Mini Pies.png\"],\"dishName\":\"CHETTINAD EGG BIRYANI\",\"saleAmount\":56.92,\"quantity\":1,\"mealPrice\":45,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":150,\"preparationTime\":90,\"autoAcceptOrder\":true,\"tax\":4.4,\"isAvailable\":true}]}]}";
//
//            jsonObject = new JSONObject(String.valueOf(jsonObject1));
//            JSONArray dataArray = jsonObject.getJSONArray("data");
//            for (int i = 0; i < dataArray.length(); i++) {
//                JSONObject fullOrderData = dataArray.getJSONObject(i);
//                JSONArray menuDetailsArray = fullOrderData.getJSONArray("menuDetails");
//                List<CorporateDish.MenuDetail> MenuDetail_list=new ArrayList<>();
//
//                for (int m = 0; m < menuDetailsArray.length(); m++) {
//                    JSONObject menuDetailsData = menuDetailsArray.getJSONObject(m);
//                    ArrayList<String> displayImage_listdata = new ArrayList<String>();
//                    if (menuDetailsData.optJSONArray("dishImage") != null) {
//                        for (int di=0;di<menuDetailsData.optJSONArray("dishImage").length();di++){
//                            displayImage_listdata.add(menuDetailsData.optJSONArray("dishImage").getString(di));
//                        }
//                    }
//                    ArrayList<String> suitableFor_listdata = new ArrayList<String>();
//                    if (menuDetailsData.optJSONArray("suitableFor") != null) {
//                        for (int sf=0;sf<menuDetailsData.optJSONArray("suitableFor").length();sf++){
//                            suitableFor_listdata.add(menuDetailsData.optJSONArray("suitableFor").getString(sf));
//                        }
//                    }
//                    double saleAmount= Double.parseDouble(String.valueOf(menuDetailsData.optInt("saleAmount")));
//                    double calories= Double.parseDouble(String.valueOf(menuDetailsData.optInt("calories")));
//                    double protine= Double.parseDouble(String.valueOf(menuDetailsData.optInt("protein")));
//                    double fat= Double.parseDouble(String.valueOf(menuDetailsData.optInt("fat")));
//                    double carbs= Double.parseDouble(String.valueOf(menuDetailsData.optInt("carbs")));
//                    double fiber= Double.parseDouble(String.valueOf(menuDetailsData.optInt("fiber")));
//                    double sugar= Double.parseDouble(String.valueOf(menuDetailsData.optInt("sugar")));
//
//
//                    MenuDetail_list.add(new CorporateDish.MenuDetail(
//                            menuDetailsData.optString("dishId"),
//                            displayImage_listdata,
//                            menuDetailsData.optString("chefName"),
//                            menuDetailsData.optString("dishName"),
//                            menuDetailsData.optString("dishSubName"),
//                            saleAmount,
//                            menuDetailsData.optString("dishDescription"),
//                            menuDetailsData.optString("saleKgMg"),
//                            calories,
//                            protine,
//                            fat,
//                            carbs,
//                            fiber,
//                            sugar,
//                            suitableFor_listdata,
//                            0,
//                            false
//                    ));
//                }
//                CorporateDish_list.add(new CorporateDish(fullOrderData.optString("id"),fullOrderData.optString("categoryTitle"),fullOrderData.optString("categorySubTitle"),fullOrderData.optString("categoryDescription"),fullOrderData.optString("categoryImg"),MenuDetail_list));
//
//
//            }
//
//
////            listDataHeader = new ArrayList<String>();
////            listDataChild = new HashMap<String, List<String>>();
//
//
////            if(Globaluse._listDataHeader.size()==0)
////            {
////                Globaluse._listDataHeader = new ArrayList<String>();
////                Globaluse._listDataChild = new HashMap<String, List<String>>();
////
////            }
//
//            Globaluse._listDataHeader = new ArrayList<String>();
//            Globaluse._listDataChild = new HashMap<String, List<String>>();
//
//
//            int listpostion=0;
//            int adddataTo=0;
//            for (int i = 0; i < CorporateDish_list.size(); i++) {
//                //JSONObject fullOrderData = CorporateDish_list.get(i);
//
//
//                JSONObject headerDetail = new JSONObject();
//                try {
//                    headerDetail.put("id", CorporateDish_list.get(i).id);
//                    headerDetail.put("categoryTitle", CorporateDish_list.get(i).categoryTitle);
//                    headerDetail.put("categorySubTitle", CorporateDish_list.get(i).categorySubTitle);
//                    headerDetail.put("categoryDescription", CorporateDish_list.get(i).categoryDescrption);
//                    headerDetail.put("categoryImg", CorporateDish_list.get(i).categoryimg);
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
////                if(Globaluse._listDataHeader.size()==0)
////                {
////                    Globaluse._listDataHeader.add(String.valueOf(headerDetail));
////
////                }
//                Globaluse._listDataHeader.add(String.valueOf(headerDetail));
//
//
//
//               // listDataHeader.add(CorporateDish_list.get(i).categoryTitle+" "+CorporateDish_list.get(i).categorySubTitle);
//                // JSONArray menuDetailsArray = fullOrderData.getJSONArray("menuDetails");
//                if(i==0)
//                {
//                    items.add(new CorporateMenuModel(""+i, CorporateDish_list.get(i).categoryTitle,CorporateDish_list.get(i).categorySubTitle,""+CorporateDish_list.get(i).menuDetails.size(),""+(listpostion)));
//                    listpostion=listpostion+(CorporateDish_list.get(i).menuDetails.size());
//                }else
//                {
//                    items.add(new CorporateMenuModel(""+i, CorporateDish_list.get(i).categoryTitle,CorporateDish_list.get(i).categorySubTitle,""+CorporateDish_list.get(i).menuDetails.size(),""+(listpostion)));
//                    listpostion=listpostion+(CorporateDish_list.get(i).menuDetails.size())+1;
//                }
//                List<String> dishlistdata = new ArrayList<String>();
//                for (int m = 0; m < CorporateDish_list.get(i).menuDetails.size(); m++) {
////                    JSONObject menuDetailsData = menuDetailsArray.getJSONObject(m);
////                    String dishname=menuDetailsData.optString("dishName");
//                   // dishlistdata.add(String.valueOf(CorporateDish_list.get(i).menuDetails.get(m).dishName));
//
//                    JSONObject childDetail = new JSONObject();
//                    try {
//                        childDetail.put("dishId", CorporateDish_list.get(i).menuDetails.get(m).dishId);
//
//
//                        String img_str="";
//                        if(!(CorporateDish_list.get(i).menuDetails.get(m).dishImage.size()==0))
//                        {
//                            for (int d = 0; d < CorporateDish_list.get(i).menuDetails.get(m).dishImage.size(); d++) {
//                                if (d == 0) {
//                                    img_str=CorporateDish_list.get(i).menuDetails.get(m).dishImage.get(d);
//                                }
//                            }
//                        }
//
//
//                        childDetail.put("dishImage", img_str);
//                        childDetail.put("chefName", CorporateDish_list.get(i).menuDetails.get(m).chefName);
//                        childDetail.put("dishName", CorporateDish_list.get(i).menuDetails.get(m).dishName);
//                        childDetail.put("dishSubName", CorporateDish_list.get(i).menuDetails.get(m).dishSubName);
//                        childDetail.put("saleAmount", CorporateDish_list.get(i).menuDetails.get(m).saleAmount);
//                        childDetail.put("dishDescription", CorporateDish_list.get(i).menuDetails.get(m).dishDescrption);
//                        childDetail.put("saleKgMg", CorporateDish_list.get(i).menuDetails.get(m).saleKgMg);
//                        childDetail.put("calories", CorporateDish_list.get(i).menuDetails.get(m).calories);
//                        childDetail.put("protein", CorporateDish_list.get(i).menuDetails.get(m).protine);
//                        childDetail.put("fat", CorporateDish_list.get(i).menuDetails.get(m).fat);
//                        childDetail.put("carbs", CorporateDish_list.get(i).menuDetails.get(m).carbs);
//                        childDetail.put("fiber", CorporateDish_list.get(i).menuDetails.get(m).fiber);
//                        childDetail.put("sugar", CorporateDish_list.get(i).menuDetails.get(m).sugar);
//                        childDetail.put("suitableFor", CorporateDish_list.get(i).menuDetails.get(m).suitableFor);
//                        childDetail.put("buttondisplay", false);
//                        childDetail.put("quantity", 0);
//
//                    } catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                    dishlistdata.add(String.valueOf(childDetail));
//                }
//
//
//
//
//
////                if(Globaluse._listDataHeader.size()==0)
////                {
////
////
////                }
//
//                Globaluse._listDataChild.put(Globaluse._listDataHeader.get(adddataTo), dishlistdata);
//
//                adddataTo++;
//
//                listAdapter = new CorporateExpandableListAdapterNew(this,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
//                expListView.setAdapter(listAdapter);
//
//
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//
//
////        final List<CorporateMenuModel> items = new ArrayList<>();
////        items.add(new CorporateMenuModel("001", "Briyani","12"));
////        items.add(new CorporateMenuModel("002", "Non-veg","30"));
////        items.add(new CorporateMenuModel("003", "Veg","40"));
//
//
//        navLV = findViewById(R.id.navLV);
//        CustomAdapter adapter = new CustomAdapter(this, items);
//        navLV.setAdapter(adapter);
//        navLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               // Toast.makeText(getApplicationContext(), items.get(position).getCategoryTitle() + " Clicked", Toast.LENGTH_SHORT).show();
//
//                drawer.closeDrawer(GravityCompat.START);
//                expListView.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        expListView.setSelection(Integer.parseInt(items.get(position).getPostion()));
//
//                    }
//                });
//
//                listAdapter.notifyDataSetChanged();
//
//                //listAdapter.notifyDataSetChanged();
//            }
//        });


       // getsubscriptionData();
        sendAndRequestResponse();

        view_basket_id=findViewById(R.id.view_basket_id);
        view_basket_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(Globaluse.fromscheduleEdit.equals(" "))
//                {
                    int checkbasket_count=0;
                Globaluse.corporatedishlistnew=new  ArrayList<String>();
                for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
                {

                    for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
                    {


                        final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));


                        JSONObject jsonObject = null;
                        try {

                            jsonObject = new JSONObject(childText);


                            if(jsonObject.getBoolean("checkStatus"))
                            {

                                    checkbasket_count++;



                                JSONObject dishDetail = new JSONObject();
                                try {
                                    dishDetail.put("dishItemName", jsonObject.getString("dishName"));
                                    dishDetail.put("description", jsonObject.getString("dishDescription"));
                                    dishDetail.put("cost", jsonObject.getString("saleAmount"));
                                    dishDetail.put("noOfUnits", jsonObject.getString("quantity"));
                                    dishDetail.put("taxValue", 0);
                                    dishDetail.put("packingCharges", 0);
                                    dishDetail.put("deliveryCharges", 0);
                                    dishDetail.put("buttondisplay", jsonObject.getString("buttondisplay"));
                                    dishDetail.put("quantity", jsonObject.getString("quantity"));
                                    dishDetail.put("checkStatus", jsonObject.getBoolean("checkStatus"));
                                    dishDetail.put("checkStatus_schedule", jsonObject.getBoolean("checkStatus_schedule"));
                                    dishDetail.put("dishImage", jsonObject.getString("dishImage"));

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                 Globaluse.corporatedishlistnew.add(String.valueOf(dishDetail));

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }


                }



                if(checkbasket_count>0)
                {
                   // Toast.makeText(getApplicationContext(), "hiiiiiiii", Toast.LENGTH_SHORT).show();
                    Log.d("ResponseRole","Success" + checkbasket_count);
                    corporateshared();
                    Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateScheduleFromApi.class);
                    startActivity(intent);

                }else
                {
                    Globaluse.corporatedishlistnew=new  ArrayList<String>();
                    Toast.makeText(getApplicationContext(), "Please add any one", Toast.LENGTH_SHORT).show();
                }







//                }
//                else
//                {
//                    finish();
//                }


            }
        });

        view_basket_id.setText("Add Schedule");

        navLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), items.get(position).getCategoryTitle() + " Clicked", Toast.LENGTH_SHORT).show();

                drawer.closeDrawer(GravityCompat.START);
                expListView.post(new Runnable() {

                    @Override
                    public void run() {

                        expListView.setSelection(Integer.parseInt(items.get(position).getPostion()));
                        //expListView.setSelection(position);
                        expListView.smoothScrollToPosition(Integer.parseInt(items.get(position).getPostion()));

                    }
                });

                listAdapter.notifyDataSetChanged();

                //listAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onBackPressed() {
       // DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

//            if(Globaluse.fromscheduleEdit.equals(" "))
//            {
//
//                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
//                {
//                    super.onBackPressed();
//                    return;
//                }
//                else { Toast.makeText(getBaseContext(), "Press again to exit.", Toast.LENGTH_SHORT).show(); }
//                mBackPressed = System.currentTimeMillis();
//
//            }
//        else
//            {
//                 finish();
//            }


            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
            {
                super.onBackPressed();
                return;
            }
            else { Toast.makeText(getBaseContext(), "Press again to exit.", Toast.LENGTH_SHORT).show(); }
            mBackPressed = System.currentTimeMillis();

           // super.onBackPressed();


        }
    }

    class CustomAdapter extends BaseAdapter {

        Context context;
        List<CorporateMenuModel> items;

        public CustomAdapter(Context context, List<CorporateMenuModel> items) {
            this.context = context;
            this.items = items;
        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_list_view_item_type, null);

            TextView text = v.findViewById(R.id.textView);


            //expListView.setSelection(1);
            //notifyDataSetChanged();

            text.setText(items.get(i).getCategoryTitle()+" "+items.get(i).getCategorySubTitle()+""+"("+items.get(i).getCount()+")");
            //event handling code can be done here
            return v;
        }
    }





    public void corporateshared()
    {
// Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("corporateShare",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putString("addcart", "yes");
        myEdit.putString("companyname", "");
        myEdit.putString("name", "");
        myEdit.putString("email", "");
        myEdit.putString("phone", "");
        myEdit.putString("fulladdress", "");
        myEdit.putString("city", "");
        myEdit.putString("postalcode", "");

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();
    }




//    public void corporatesharedcheck()
//    {
//        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
//
//// The value will be default as empty string because for
//// the very first time when the app is opened, there is nothing to show
//        String addcart_share = sh.getString("addcart", "");
//        String companyname_share = sh.getString("companyname", "");
//        String name_share = sh.getString("name", "");
//        String email_share = sh.getString("email", "");
//        String phone_share = sh.getString("phone", "");
//        String fulladdress_share = sh.getString("fulladdress", "");
//        String city_share = sh.getString("city", "");
//        String postalcode_share = sh.getString("postalcode", "");
//
//        if(addcart_share.equals("yes"))
//        {
//            finish();
//            overridePendingTransition( 0, 0);
//            startActivity(getIntent());
//            overridePendingTransition( 0, 0);
//        }
//    }

    @Override
    public void onRestart() {
        super.onRestart();
        SharedPreferences sh = getSharedPreferences("corporateShare", MODE_APPEND);
        String addcart_share = sh.getString("addcart", "");
        if(addcart_share.equals("yes"))
        {
            listAdapter = new CorporateExpandableListAdapterRange(this,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
            expListView.setAdapter(listAdapter);

        }
        GetscheduleCount();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }











    public void getsubscriptionData()
    {

        //String url ="https://foodyhivestorage.blob.core.windows.net/assets/data/subscription-requests.json";
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, jsonurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseRole","Success" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);


                    if(jsonObject.has("data") )
                    {
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        if(dataArray.length() !=0)
                        {
                            try {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject fullOrderData = dataArray.getJSONObject(i);
                                    JSONArray menuDetailsArray = fullOrderData.getJSONArray("menuDetails");
                                    List<CorporateDish.MenuDetail> MenuDetail_list=new ArrayList<>();

                                    for (int m = 0; m < menuDetailsArray.length(); m++) {
                                        JSONObject menuDetailsData = menuDetailsArray.getJSONObject(m);
                                        ArrayList<String> displayImage_listdata = new ArrayList<String>();
                                        if (menuDetailsData.optJSONArray("dishImage") != null) {
                                            for (int di=0;di<menuDetailsData.optJSONArray("dishImage").length();di++){
                                                displayImage_listdata.add(menuDetailsData.optJSONArray("dishImage").getString(di));
                                            }
                                        }
                                        ArrayList<String> suitableFor_listdata = new ArrayList<String>();
                                        if (menuDetailsData.optJSONArray("suitableFor") != null) {
                                            for (int sf=0;sf<menuDetailsData.optJSONArray("suitableFor").length();sf++){
                                                suitableFor_listdata.add(menuDetailsData.optJSONArray("suitableFor").getString(sf));
                                            }
                                        }
                                        double saleAmount= Double.parseDouble(String.valueOf(menuDetailsData.optInt("saleAmount")));
                                        double calories= Double.parseDouble(String.valueOf(menuDetailsData.optInt("calories")));
                                        double protine= Double.parseDouble(String.valueOf(menuDetailsData.optInt("protein")));
                                        double fat= Double.parseDouble(String.valueOf(menuDetailsData.optInt("fat")));
                                        double carbs= Double.parseDouble(String.valueOf(menuDetailsData.optInt("carbs")));
                                        double fiber= Double.parseDouble(String.valueOf(menuDetailsData.optInt("fiber")));
                                        double sugar= Double.parseDouble(String.valueOf(menuDetailsData.optInt("sugar")));


                                        MenuDetail_list.add(new CorporateDish.MenuDetail(
                                                menuDetailsData.optString("dishId"),
                                                displayImage_listdata,
                                                menuDetailsData.optString("chefName"),
                                                menuDetailsData.optString("dishName"),
                                                menuDetailsData.optString("dishSubName"),
                                                saleAmount,
                                                menuDetailsData.optString("dishDescription"),
                                                menuDetailsData.optString("saleKgMg"),
                                                calories,
                                                protine,
                                                fat,
                                                carbs,
                                                fiber,
                                                sugar,
                                                suitableFor_listdata,
                                                0,
                                                false,
                                                menuDetailsData.optBoolean("Breakfast"),
                                                menuDetailsData.optBoolean("Lunch"),
                                                menuDetailsData.optBoolean("Dinner"),
                                                menuDetailsData.optBoolean("Snacks")
                                        ));
                                    }
                                    CorporateDish_list.add(new CorporateDish(fullOrderData.optString("id"),fullOrderData.optString("categoryTitle"),fullOrderData.optString("categorySubTitle"),fullOrderData.optString("categoryDescription"),fullOrderData.optString("categoryImg"),MenuDetail_list));
                                }


                                Globaluse._listDataHeader = new ArrayList<String>();
                                Globaluse._listDataChild = new HashMap<String, List<String>>();


                                int listpostion=0;
                                int adddataTo=0;
                                for (int i = 0; i < CorporateDish_list.size(); i++) {


                                    JSONObject headerDetail = new JSONObject();
                                    try {
                                        headerDetail.put("id", CorporateDish_list.get(i).id);
                                        headerDetail.put("categoryTitle", CorporateDish_list.get(i).categoryTitle);
                                        headerDetail.put("categorySubTitle", CorporateDish_list.get(i).categorySubTitle);
                                        headerDetail.put("categoryDescription", CorporateDish_list.get(i).categoryDescrption);
                                        headerDetail.put("categoryImg", CorporateDish_list.get(i).categoryimg);

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                    Globaluse._listDataHeader.add(String.valueOf(headerDetail));

                                    if(i==0)
                                    {
                                        items.add(new CorporateMenuModel(""+i, CorporateDish_list.get(i).categoryTitle,CorporateDish_list.get(i).categorySubTitle,""+CorporateDish_list.get(i).menuDetails.size(),""+(listpostion)));
                                        listpostion=listpostion+(CorporateDish_list.get(i).menuDetails.size());
                                    }else
                                    {
                                        items.add(new CorporateMenuModel(""+i, CorporateDish_list.get(i).categoryTitle,CorporateDish_list.get(i).categorySubTitle,""+CorporateDish_list.get(i).menuDetails.size(),""+(listpostion)));
                                        listpostion=listpostion+(CorporateDish_list.get(i).menuDetails.size())+1;
                                    }
                                    List<String> dishlistdata = new ArrayList<String>();
                                    for (int m = 0; m < CorporateDish_list.get(i).menuDetails.size(); m++) {

                                        JSONObject childDetail = new JSONObject();
                                        try {
                                            childDetail.put("dishId", CorporateDish_list.get(i).menuDetails.get(m).dishId);


                                            String img_str="";
                                            if(!(CorporateDish_list.get(i).menuDetails.get(m).dishImage.size()==0))
                                            {
                                                for (int d = 0; d < CorporateDish_list.get(i).menuDetails.get(m).dishImage.size(); d++) {
                                                    if (d == 0) {
                                                        img_str=CorporateDish_list.get(i).menuDetails.get(m).dishImage.get(d);
                                                    }
                                                }
                                            }


                                            childDetail.put("dishImage", img_str);
                                            childDetail.put("chefName", CorporateDish_list.get(i).menuDetails.get(m).chefName);
                                            childDetail.put("dishName", CorporateDish_list.get(i).menuDetails.get(m).dishName);
                                            childDetail.put("dishSubName", CorporateDish_list.get(i).menuDetails.get(m).dishSubName);
                                            childDetail.put("saleAmount", CorporateDish_list.get(i).menuDetails.get(m).saleAmount);
                                            childDetail.put("dishDescription", CorporateDish_list.get(i).menuDetails.get(m).dishDescrption);
                                            childDetail.put("saleKgMg", CorporateDish_list.get(i).menuDetails.get(m).saleKgMg);
                                            childDetail.put("calories", CorporateDish_list.get(i).menuDetails.get(m).calories);
                                            childDetail.put("protein", CorporateDish_list.get(i).menuDetails.get(m).protine);
                                            childDetail.put("fat", CorporateDish_list.get(i).menuDetails.get(m).fat);
                                            childDetail.put("carbs", CorporateDish_list.get(i).menuDetails.get(m).carbs);
                                            childDetail.put("fiber", CorporateDish_list.get(i).menuDetails.get(m).fiber);
                                            childDetail.put("sugar", CorporateDish_list.get(i).menuDetails.get(m).sugar);
                                            childDetail.put("suitableFor", CorporateDish_list.get(i).menuDetails.get(m).suitableFor);
                                            childDetail.put("buttondisplay", false);
                                            childDetail.put("quantity", 0);

                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        dishlistdata.add(String.valueOf(childDetail));
                                    }

                                    Globaluse._listDataChild.put(Globaluse._listDataHeader.get(adddataTo), dishlistdata);

                                    adddataTo++;

                                    listAdapter = new CorporateExpandableListAdapterRange(corporateMenuWithNavigatioin,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
                                    expListView.setAdapter(listAdapter);


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //navLV = findViewById(R.id.navLV);
                            CustomAdapter adapter = new CustomAdapter(corporateMenuWithNavigatioin, items);
                            navLV.setAdapter(adapter);













                        }
                        else
                        {
                            Toast.makeText(corporateMenuWithNavigatioin, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(corporateMenuWithNavigatioin, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AboutUsAndGetHelpWebViewActivity.parseVolleyError(error);
                Log.d("ResponseRole","Error" + error.getMessage());
                Toast.makeText(corporateMenuWithNavigatioin, "Error Json" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }, corporateMenuWithNavigatioin) ;
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"userdat");
    }



    private void sendAndRequestResponse() {
        pDialog = ProgressDialog.show(CorporateMenuWithNavigatioin.this, "", "Please wait...", true,false);
        try {
            InputStream is = getResources().openRawResource(R.raw.subscription);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int n;
            while (true) {
                try {
                    if (!((n = reader.read(buffer)) != -1)) break;
                    writer.write(buffer, 0, n);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String jsonObject1 = writer.toString();

            JSONObject jsonObject = new JSONObject(String.valueOf(jsonObject1));
            if(jsonObject.has("data") )
            {
                JSONArray dataArray = jsonObject.getJSONArray("data");

                if(dataArray.length() !=0)
                {
                    try {



                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject fullOrderData = dataArray.getJSONObject(i);
                            JSONArray menuDetailsArray = fullOrderData.getJSONArray("menuDetails");
                            List<CorporateDish.MenuDetail> MenuDetail_list=new ArrayList<>();

                            boolean all=false;
                            boolean bfa=false;
                            boolean lun=false;
                            boolean din=false;
                            boolean sna=false;



                            for (int m = 0; m < menuDetailsArray.length(); m++) {
                                JSONObject menuDetailsData = menuDetailsArray.getJSONObject(m);
                                ArrayList<String> displayImage_listdata = new ArrayList<String>();
                                if (menuDetailsData.optJSONArray("dishImage") != null) {
                                    for (int di=0;di<menuDetailsData.optJSONArray("dishImage").length();di++){
                                        displayImage_listdata.add(menuDetailsData.optJSONArray("dishImage").getString(di));
                                    }
                                }
                                ArrayList<String> suitableFor_listdata = new ArrayList<String>();
                                if (menuDetailsData.optJSONArray("suitableFor") != null) {
                                    for (int sf=0;sf<menuDetailsData.optJSONArray("suitableFor").length();sf++){
                                        suitableFor_listdata.add(menuDetailsData.optJSONArray("suitableFor").getString(sf));
                                    }
                                }
                                double saleAmount= Double.parseDouble(String.valueOf(menuDetailsData.optInt("saleAmount")));
                                double calories= Double.parseDouble(String.valueOf(menuDetailsData.optInt("calories")));
                                double protine= Double.parseDouble(String.valueOf(menuDetailsData.optInt("protein")));
                                double fat= Double.parseDouble(String.valueOf(menuDetailsData.optInt("fat")));
                                double carbs= Double.parseDouble(String.valueOf(menuDetailsData.optInt("carbs")));
                                double fiber= Double.parseDouble(String.valueOf(menuDetailsData.optInt("fiber")));
                                double sugar= Double.parseDouble(String.valueOf(menuDetailsData.optInt("sugar")));





                                if(Globaluse.Filtertype.equalsIgnoreCase("All"))
                                {
                                    all=true;
                                    MenuDetail_list.add(new CorporateDish.MenuDetail(
                                            menuDetailsData.optString("dishId"),
                                            displayImage_listdata,
                                            menuDetailsData.optString("chefName"),
                                            menuDetailsData.optString("dishName"),
                                            menuDetailsData.optString("dishSubName"),
                                            saleAmount,
                                            menuDetailsData.optString("dishDescription"),
                                            menuDetailsData.optString("saleKgMg"),
                                            calories,
                                            protine,
                                            fat,
                                            carbs,
                                            fiber,
                                            sugar,
                                            suitableFor_listdata,
                                            0,
                                            false,
                                            menuDetailsData.optBoolean("Breakfast"),
                                            menuDetailsData.optBoolean("Lunch"),
                                            menuDetailsData.optBoolean("Dinner"),
                                            menuDetailsData.optBoolean("Snacks")
                                    ));

                                }else if(Globaluse.Filtertype.equalsIgnoreCase("Breakfast"))
                                {

                                    if(menuDetailsData.optBoolean("Breakfast"))
                                    {
                                        bfa=true;

                                        MenuDetail_list.add(new CorporateDish.MenuDetail(
                                                menuDetailsData.optString("dishId"),
                                                displayImage_listdata,
                                                menuDetailsData.optString("chefName"),
                                                menuDetailsData.optString("dishName"),
                                                menuDetailsData.optString("dishSubName"),
                                                saleAmount,
                                                menuDetailsData.optString("dishDescription"),
                                                menuDetailsData.optString("saleKgMg"),
                                                calories,
                                                protine,
                                                fat,
                                                carbs,
                                                fiber,
                                                sugar,
                                                suitableFor_listdata,
                                                0,
                                                false,
                                                menuDetailsData.optBoolean("Breakfast"),
                                                menuDetailsData.optBoolean("Lunch"),
                                                menuDetailsData.optBoolean("Dinner"),
                                                menuDetailsData.optBoolean("Snacks")
                                        ));
                                    }


                                }else if(Globaluse.Filtertype.equalsIgnoreCase("Lunch"))
                                {

                                    if(menuDetailsData.optBoolean("Lunch"))
                                    {
                                        lun=true;

                                        MenuDetail_list.add(new CorporateDish.MenuDetail(
                                                menuDetailsData.optString("dishId"),
                                                displayImage_listdata,
                                                menuDetailsData.optString("chefName"),
                                                menuDetailsData.optString("dishName"),
                                                menuDetailsData.optString("dishSubName"),
                                                saleAmount,
                                                menuDetailsData.optString("dishDescription"),
                                                menuDetailsData.optString("saleKgMg"),
                                                calories,
                                                protine,
                                                fat,
                                                carbs,
                                                fiber,
                                                sugar,
                                                suitableFor_listdata,
                                                0,
                                                false,
                                                menuDetailsData.optBoolean("Breakfast"),
                                                menuDetailsData.optBoolean("Lunch"),
                                                menuDetailsData.optBoolean("Dinner"),
                                                menuDetailsData.optBoolean("Snacks")
                                        ));
                                    }

                                }else if(Globaluse.Filtertype.equalsIgnoreCase("Dinner"))
                                {
                                    if(menuDetailsData.optBoolean("Dinner"))
                                    {
                                        din=true;

                                        MenuDetail_list.add(new CorporateDish.MenuDetail(
                                                menuDetailsData.optString("dishId"),
                                                displayImage_listdata,
                                                menuDetailsData.optString("chefName"),
                                                menuDetailsData.optString("dishName"),
                                                menuDetailsData.optString("dishSubName"),
                                                saleAmount,
                                                menuDetailsData.optString("dishDescription"),
                                                menuDetailsData.optString("saleKgMg"),
                                                calories,
                                                protine,
                                                fat,
                                                carbs,
                                                fiber,
                                                sugar,
                                                suitableFor_listdata,
                                                0,
                                                false,
                                                menuDetailsData.optBoolean("Breakfast"),
                                                menuDetailsData.optBoolean("Lunch"),
                                                menuDetailsData.optBoolean("Dinner"),
                                                menuDetailsData.optBoolean("Snacks")
                                        ));
                                    }
                                }
                                else if(Globaluse.Filtertype.equalsIgnoreCase("Snacks"))
                                {
                                    if(menuDetailsData.optBoolean("Snacks"))
                                    {
                                        sna=true;
                                        MenuDetail_list.add(new CorporateDish.MenuDetail(
                                                menuDetailsData.optString("dishId"),
                                                displayImage_listdata,
                                                menuDetailsData.optString("chefName"),
                                                menuDetailsData.optString("dishName"),
                                                menuDetailsData.optString("dishSubName"),
                                                saleAmount,
                                                menuDetailsData.optString("dishDescription"),
                                                menuDetailsData.optString("saleKgMg"),
                                                calories,
                                                protine,
                                                fat,
                                                carbs,
                                                fiber,
                                                sugar,
                                                suitableFor_listdata,
                                                0,
                                                false,
                                                menuDetailsData.optBoolean("Breakfast"),
                                                menuDetailsData.optBoolean("Lunch"),
                                                menuDetailsData.optBoolean("Dinner"),
                                                menuDetailsData.optBoolean("Snacks")
                                        ));
                                    }
                                }




                            }









                            if(Globaluse.Filtertype.equalsIgnoreCase("All"))
                            {
                                if(all)
                                {
                                    CorporateDish_list.add(new CorporateDish(fullOrderData.optString("id"),fullOrderData.optString("categoryTitle"),fullOrderData.optString("categorySubTitle"),fullOrderData.optString("categoryDescription"),fullOrderData.optString("categoryImg"),MenuDetail_list));

                                }
                            }else if(Globaluse.Filtertype.equalsIgnoreCase("Breakfast"))
                            {


                                if(bfa)
                                {
                                    CorporateDish_list.add(new CorporateDish(fullOrderData.optString("id"),fullOrderData.optString("categoryTitle"),fullOrderData.optString("categorySubTitle"),fullOrderData.optString("categoryDescription"),fullOrderData.optString("categoryImg"),MenuDetail_list));

                                }


                            }else if(Globaluse.Filtertype.equalsIgnoreCase("Lunch"))
                            {

                                if(lun)
                                {
                                    CorporateDish_list.add(new CorporateDish(fullOrderData.optString("id"),fullOrderData.optString("categoryTitle"),fullOrderData.optString("categorySubTitle"),fullOrderData.optString("categoryDescription"),fullOrderData.optString("categoryImg"),MenuDetail_list));

                                }

                            }else if(Globaluse.Filtertype.equalsIgnoreCase("Dinner"))
                            {
                                if(din)
                                {
                                    CorporateDish_list.add(new CorporateDish(fullOrderData.optString("id"),fullOrderData.optString("categoryTitle"),fullOrderData.optString("categorySubTitle"),fullOrderData.optString("categoryDescription"),fullOrderData.optString("categoryImg"),MenuDetail_list));

                                }

                            }
                            else if(Globaluse.Filtertype.equalsIgnoreCase("Snacks"))
                            {

                                if(sna)
                                {
                                    CorporateDish_list.add(new CorporateDish(fullOrderData.optString("id"),fullOrderData.optString("categoryTitle"),fullOrderData.optString("categorySubTitle"),fullOrderData.optString("categoryDescription"),fullOrderData.optString("categoryImg"),MenuDetail_list));

                                }
                            }





                        }


                        Globaluse._listDataHeader = new ArrayList<String>();
                        Globaluse._listDataChild = new HashMap<String, List<String>>();


                        int listpostion=0;
                        int adddataTo=0;
                        for (int i = 0; i < CorporateDish_list.size(); i++) {


                            JSONObject headerDetail = new JSONObject();
                            try {
                                headerDetail.put("id", CorporateDish_list.get(i).id);
                                headerDetail.put("categoryTitle", CorporateDish_list.get(i).categoryTitle);
                                headerDetail.put("categorySubTitle", CorporateDish_list.get(i).categorySubTitle);
                                headerDetail.put("categoryDescription", CorporateDish_list.get(i).categoryDescrption);
                                headerDetail.put("categoryImg", CorporateDish_list.get(i).categoryimg);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            Globaluse._listDataHeader.add(String.valueOf(headerDetail));

                            if(i==0)
                            {
                                items.add(new CorporateMenuModel(""+i, CorporateDish_list.get(i).categoryTitle,CorporateDish_list.get(i).categorySubTitle,""+CorporateDish_list.get(i).menuDetails.size(),""+(listpostion)));
                                listpostion=listpostion+(CorporateDish_list.get(i).menuDetails.size());
                            }else
                            {
                                items.add(new CorporateMenuModel(""+i, CorporateDish_list.get(i).categoryTitle,CorporateDish_list.get(i).categorySubTitle,""+CorporateDish_list.get(i).menuDetails.size(),""+(listpostion)));
                                listpostion=listpostion+(CorporateDish_list.get(i).menuDetails.size())+1;
                            }
                            List<String> dishlistdata = new ArrayList<String>();
                            for (int m = 0; m < CorporateDish_list.get(i).menuDetails.size(); m++) {

                                JSONObject childDetail = new JSONObject();
                                try {
                                    childDetail.put("dishId", CorporateDish_list.get(i).menuDetails.get(m).dishId);


                                    String img_str="";
                                    if(!(CorporateDish_list.get(i).menuDetails.get(m).dishImage.size()==0))
                                    {
                                        for (int d = 0; d < CorporateDish_list.get(i).menuDetails.get(m).dishImage.size(); d++) {
                                            if (d == 0) {
                                                img_str=CorporateDish_list.get(i).menuDetails.get(m).dishImage.get(d);
                                            }
                                        }
                                    }


                                    childDetail.put("dishImage", img_str);
                                    childDetail.put("chefName", CorporateDish_list.get(i).menuDetails.get(m).chefName);
                                    childDetail.put("dishName", CorporateDish_list.get(i).menuDetails.get(m).dishName);
                                    childDetail.put("dishSubName", CorporateDish_list.get(i).menuDetails.get(m).dishSubName);
                                    childDetail.put("saleAmount", CorporateDish_list.get(i).menuDetails.get(m).saleAmount);
                                    childDetail.put("dishDescription", CorporateDish_list.get(i).menuDetails.get(m).dishDescrption);
                                    childDetail.put("saleKgMg", CorporateDish_list.get(i).menuDetails.get(m).saleKgMg);
                                    childDetail.put("calories", CorporateDish_list.get(i).menuDetails.get(m).calories);
                                    childDetail.put("protein", CorporateDish_list.get(i).menuDetails.get(m).protine);
                                    childDetail.put("fat", CorporateDish_list.get(i).menuDetails.get(m).fat);
                                    childDetail.put("carbs", CorporateDish_list.get(i).menuDetails.get(m).carbs);
                                    childDetail.put("fiber", CorporateDish_list.get(i).menuDetails.get(m).fiber);
                                    childDetail.put("sugar", CorporateDish_list.get(i).menuDetails.get(m).sugar);

                                    childDetail.put("Breakfast", CorporateDish_list.get(i).menuDetails.get(m).Breakfast);
                                    childDetail.put("Lunch", CorporateDish_list.get(i).menuDetails.get(m).Lunch);
                                    childDetail.put("Dinner", CorporateDish_list.get(i).menuDetails.get(m).Dinner);
                                    childDetail.put("Snacks", CorporateDish_list.get(i).menuDetails.get(m).Snacks);



                                    childDetail.put("suitableFor", CorporateDish_list.get(i).menuDetails.get(m).suitableFor);
                                    childDetail.put("buttondisplay", false);
                                    childDetail.put("quantity", 0);
                                    childDetail.put("checkStatus", false);
                                    childDetail.put("checkStatus_schedule", false);

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }


                                Boolean Breakfast_bo=CorporateDish_list.get(i).menuDetails.get(m).Breakfast;
                                Boolean Lunch_bo=CorporateDish_list.get(i).menuDetails.get(m).Lunch;
                                Boolean Dinner_bo=CorporateDish_list.get(i).menuDetails.get(m).Dinner;
                                Boolean Snacks_bo=CorporateDish_list.get(i).menuDetails.get(m).Snacks;

//                                        if(Breakfast_bo||Lunch_bo||Dinner_bo||Snacks_bo)
//                                        {
//
//                                        }

//                                                if(Globaluse.Filtertype.equalsIgnoreCase("All"))
//                                                {
//                                                    dishlistdata.add(String.valueOf(childDetail));
//
//                                                }else if(Globaluse.Filtertype.equalsIgnoreCase("Breakfast"))
//                                                {
//
//                                                    if(Breakfast_bo)
//                                                    {
//                                                        dishlistdata.add(String.valueOf(childDetail));
//                                                    }
//
//
//                                                }else if(Globaluse.Filtertype.equalsIgnoreCase("Lunch"))
//                                                {
//
//                                                    if(Lunch_bo)
//                                                    {
//                                                        dishlistdata.add(String.valueOf(childDetail));
//                                                    }
//
//                                                }else if(Globaluse.Filtertype.equalsIgnoreCase("Dinner"))
//                                                {
//                                                    if(Dinner_bo)
//                                                    {
//                                                        dishlistdata.add(String.valueOf(childDetail));
//                                                    }
//                                                }
//                                                else if(Globaluse.Filtertype.equalsIgnoreCase("Snacks"))
//                                                {
//                                                    if(Snacks_bo)
//                                                    {
//                                                        dishlistdata.add(String.valueOf(childDetail));
//                                                    }
//                                                }


                                dishlistdata.add(String.valueOf(childDetail));

                            }

                            Globaluse._listDataChild.put(Globaluse._listDataHeader.get(adddataTo), dishlistdata);

                            adddataTo++;

                            listAdapter = new CorporateExpandableListAdapterRange(corporateMenuWithNavigatioin,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
                            expListView.setAdapter(listAdapter);


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    CustomAdapter adapter = new CustomAdapter(corporateMenuWithNavigatioin, items);
                    navLV.setAdapter(adapter);
                    navLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Toast.makeText(getApplicationContext(), items.get(position).getCategoryTitle() + " Clicked", Toast.LENGTH_SHORT).show();

                            drawer.closeDrawer(GravityCompat.START);
                            expListView.post(new Runnable() {

                                @Override
                                public void run() {
                                    // expListView.requestFocusFromTouch();
                                    listAdapter.notifyDataSetChanged();
                                    expListView.setSelection(Integer.parseInt(items.get(position).getPostion()));
                                    //expListView.requestFocus();
                                    //listAdapter.notifyDataSetChanged();

                                }
                            });

                            //listAdapter.notifyDataSetChanged();


                        }
                    });












                }
                else
                {
                    Toast.makeText(corporateMenuWithNavigatioin, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(corporateMenuWithNavigatioin, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pDialog.dismiss();

    }




    //Create method appInstalledOrNot
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }

    public void  GetscheduleCount()
    {
        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");
        String URL = APIBaseURL.BASEURLLINK_B2B_SCHEDULE_LIST+""+companyId;
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if(jsonObject.getString("isSuccess").equals("true"))
                    {
                        JSONArray dataObject = jsonObject.getJSONArray("data");
                        //JSONArray myReferralCode = dataObject.getJSONArray("dishesItems");

                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                // change UI elements here
                                cartTotalCountText.setText(""+dataObject.length());

                                if(dataObject.length()>0)
                                {
                                    cart_con.setVisibility(View.VISIBLE);
                                }else
                                {
                                    cart_con.setVisibility(View.GONE);
                                }

                            }
                        });

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                cart_con.setVisibility(View.INVISIBLE);
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = CorporateMenuWithNavigatioin.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(CorporateMenuWithNavigatioin.this).inflate(R.layout.access_token_expired_dialog_corporate, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CorporateMenuWithNavigatioin.this);

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            finishAffinity();
                            Intent intent = new Intent(CorporateMenuWithNavigatioin.this, ColiveAndStayabodeHomeActivity.class);
                            startActivity(intent);

                            SharedPreferences preferences =getSharedPreferences("corporateLogin",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                            finish();

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
                    Toast.makeText(CorporateMenuWithNavigatioin.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporateMenuWithNavigatioin.this){    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                //params.put("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTk0NjMyMDAsImlzcyI6Imh0dHBzOi8vbG9jYWxob3N0OjQ0MzcyLyJ9.N_iY9Erlr-CeN76VPpPxzjTN2r_ytX0apvW8N9T0JlQ");
                //params.put("content-type", "application/json");
                return params;
            }
        };


        ApplicationController.getInstance().addToRequestQueue(stringRequest, "scheduleList");
    }



}
