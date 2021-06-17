package stayabode.foodyHive.corporatemenurange;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import stayabode.foodyHive.R;

import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAdapterRange;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.CorporateDish;
import stayabode.foodyHive.models.CorporateMenuModel;
import stayabode.foodyHive.models.RequestBtoBItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.SqlDateTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import stayabode.foodyHive.activities.consumers.AboutUsAndGetHelpWebViewActivity;

public class CorporateMenuWithNavigatioinAdmin extends AppCompatActivity {

    ListView navLV;
    ImageView close_navigation_drawer;
    TextView menu_drawer_open_id;
    private static final String TAG = CorporateMenuWithNavigatioinAdmin.class.getName();

    CorporateExpandableListAdapterRange listAdapter;
    ExpandableListView expListView;
//    List<String> listDataHeader;
//    HashMap<String, List<String>> listDataChild;

    List<CorporateMenuModel> items = new ArrayList<>();
    List<CorporateDish> CorporateDish_list = new ArrayList<>();
    List<CorporateDish.MenuDetail> MenuDetail_list=new ArrayList<>();
    List <String> stringList = new ArrayList<String>();

    Button view_basket_id;
    TextView whatsapp_id;
    public static CorporateMenuWithNavigatioinAdmin corporateMenuWithNavigatioin;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String jsonurl = "https://foodyhivestorage.blob.core.windows.net/assets/data/subscription-requests.json";

    DrawerLayout drawer;
    private ProgressDialog pDialog;
    TextView logout_id;
    ImageView menu_id_menu;

    ViewPager viewPager;
    TabLayout tabLayoutViewAll;
    Spinner spinner;
    ImageView addclient;
    int pageindex=0;
    String status="All";
    String edit_search_str="";
    LinearLayout startDateIcon,date_layout,endDateIcon;
    EditText startDateEditText,endDateEditText;
    DatePickerDialog picker;



    private RecyclerView schedule_list;
    ArrayList<RequestBtoBItem> near_data= new ArrayList<>();
    NearAdapter adapter;

    int firstVisibleItem, visibleItemCount, totalItemCount,count=0;
    protected int m_PreviousTotalCount;
    RecyclerViewPositionHelper mRecyclerViewHelper;

    public AutoCompleteTextView edit_search ;

//    String rstartDate="2019-04-11";
//    String rendDate="2019-04-11";
    String rstartDate=null;
    String rendDate=null;

    ImageView clearid;
    long startTime=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_corporate_menu_navigation_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        corporateMenuWithNavigatioin=this;
        drawer= findViewById(R.id.drawer_layout);
        near_data= new ArrayList<>();
        addclient= findViewById(R.id.addclient);
        stringList.add(" ");
        clearid= findViewById(R.id.clearid);
        clearid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  spinner.setSelection(0);
                status="All";
                pageindex=0;
                edit_search.setText("");
                edit_search_str="";
                rstartDate=null;
                rendDate=null;
                startDateEditText.setText("");
                endDateEditText.setText("");

                near_data= new ArrayList<>();
                count=0;
                GetRequestList();
            }
        });

        edit_search = (AutoCompleteTextView) findViewById(R.id.edit_search);
        addclient.setVisibility(View.GONE);
        addclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CorporateMenuWithNavigatioinAdmin.this, CorporateRegisterActivity.class);
                startActivity(intent);

            }
        });
        adapter = new NearAdapter(CorporateMenuWithNavigatioinAdmin.this, near_data);

        viewPager=findViewById(R.id.viewPager);
        tabLayoutViewAll=findViewById(R.id.tabLayoutViewAll);
        tabLayoutViewAll.setVisibility(View.GONE);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add("Request");
//        arrayList.add("Accept");
//        arrayList.add("Reject");
//        arrayList.add("Dinner");
//        arrayList.add("Other");
       // prepareViewager(viewPager,arrayList);
        tabLayoutViewAll.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        GetCompanySignUp();
        //GetRequestList();
//        try {
//           // getOrdersStatus();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();




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
        menu_id_menu=findViewById(R.id.menu_id_menu);
        menu_id_menu.setOnClickListener(new View.OnClickListener() {
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
                    PackageManager pm = CorporateMenuWithNavigatioinAdmin.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
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
                finishAffinity();
                Intent intent = new Intent(CorporateMenuWithNavigatioinAdmin.this, ColiveAndStayabodeHomeActivity.class);
                startActivity(intent);
                SharedPreferences preferences =getSharedPreferences("corporateLogin",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                
            }
        });



        spinner = findViewById(R.id.filterSpinner);
        ArrayList<String> spinnerList = new ArrayList<>();
        //spinnerList.clear();
        spinnerList.add("All");
        spinnerList.add("Requested");
        spinnerList.add("Accepted");
        spinnerList.add("Rejected");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CorporateMenuWithNavigatioinAdmin.this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = spinner.getSelectedItemPosition();
                if(pos==0)
                {
                    Globaluse.spinnerselection="All";
                    status="All";
                    pageindex=0;
                    near_data= new ArrayList<>();
                    count=0;
                    GetRequestList();
                   // adapter.notifyDataSetChanged();
                }

                if(pos==1)
                {
                    Globaluse.spinnerselection="Requested";
                    status="Request";
                    pageindex=0;
                    near_data= new ArrayList<>();
                    count=0;
                    GetRequestList();
                    //adapter.notifyDataSetChanged();
                }

                if(pos==2)
                {
                    Globaluse.spinnerselection="Accepted";
                    status="Approved";
                    pageindex=0;
                    near_data= new ArrayList<>();
                    count=0;
                    GetRequestList();
                    adapter.notifyDataSetChanged();
                }

                if(pos==3)
                {
                    Globaluse.spinnerselection="Rejected";
                    status="Rejected";
                    pageindex=0;
                    near_data= new ArrayList<>();
                    count=0;
                    GetRequestList();
                    adapter.notifyDataSetChanged();
                }


              //  prepareViewager(viewPager,arrayList);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });




        schedule_list = (RecyclerView) findViewById(R.id.schedule_list);

        schedule_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mRecyclerViewHelper.getItemCount();
                firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();

                if (totalItemCount == 0 || adapter == null)
                    return;
                if (m_PreviousTotalCount == totalItemCount)
                {
                    return;
                }
                else
                {
                    boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
                    if (loadMore)
                    {
                        m_PreviousTotalCount = totalItemCount;
                        GetRequestList();
                    }
                }
            }
        });
      //  GetRequestList();

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
       // sendAndRequestResponse();




        startDateEditText = findViewById(R.id.startDateEditText);
        startDateIcon = findViewById(R.id.startDateIcon);
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CorporateMenuWithNavigatioinAdmin.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //  startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = format.parse(dateFormat);
                                    startDateEditText.setText(format.format(date));
                                    rstartDate=format2.format(date);
                                    //format2

//                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                    Date date2 = sdf.parse(format.format(date));
//                                    long millis = date2.getTime();
//                                    Globaluse.dateStart=millis;
//                                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this,""+millis,Toast.LENGTH_SHORT).show();
//                                    //dateStart=millis;
//
//                                    String search_txt = edit_search.getText().toString();
//                                    if((search_txt.isEmpty()))
//                                    {
//                                        Globaluse.searchString="";
//                                    }
//

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                    startTime = calendar.getTimeInMillis();


                                    //prepareViewager(viewPager,arrayList);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //isValidateDate();
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

                //picker.getDatePicker().setMinDate(System.currentTimeMillis());
                // cldr.add(Calendar.YEAR, 1);
                //picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();




            }
        });

        startDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateEditText.performClick();
            }
        });




        endDateEditText =findViewById(R.id.endDateEditText);
        endDateIcon = findViewById(R.id.endDateIcon);
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CorporateMenuWithNavigatioinAdmin.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //  startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = format.parse(dateFormat);
                                    endDateEditText.setText(format.format(date));
                                    rendDate=format2.format(date);
                                    // String myDate = "2014/10/29 18:10:45";
//                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                    Date date2 = sdf.parse(format.format(date));
//                                    long millis = date2.getTime();
//                                    Globaluse.dateEnd=millis;
//                                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this,""+millis,Toast.LENGTH_SHORT).show();
//                                    //dateEnd=millis;

//                                    String search_txt = edit_search.getText().toString();
//                                    if((search_txt.isEmpty()))
//                                    {
//                                        Globaluse.searchString="";
//                                    }

                                   // prepareViewager(viewPager,arrayList);



                                    pageindex=0;
                                    near_data= new ArrayList<>();
                                    count=0;
                                    GetRequestList();


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //isValidateDate();
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(startTime);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

                //picker.getDatePicker().setMinDate(System.currentTimeMillis());
                // cldr.add(Calendar.YEAR, 1);
                //picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();




            }
        });

        endDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDateEditText.performClick();
            }
        });





        view_basket_id=findViewById(R.id.view_basket_id);
        view_basket_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Intent intent = new Intent(CorporateMenuWithNavigatioinAdmin.this, CorporateSchedule.class);
                    startActivity(intent);

                }else
                {
                    Globaluse.corporatedishlistnew=new  ArrayList<String>();
                    Toast.makeText(getApplicationContext(), "Please add any one", Toast.LENGTH_SHORT).show();
                }


            }
        });

        view_basket_id.setText("Add Schedule");

//        navLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Toast.makeText(getApplicationContext(), items.get(position).getCategoryTitle() + " Clicked", Toast.LENGTH_SHORT).show();
//
//                drawer.closeDrawer(GravityCompat.START);
//                expListView.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        //expListView.setSelection(Integer.parseInt(items.get(position).getPostion()));
//
//
//
//                    }
//                });
//
//                //listAdapter.notifyDataSetChanged();
//
//                //listAdapter.notifyDataSetChanged();
//            }
//        });

    }

    @Override
    public void onBackPressed() {
       // DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    class CustomAdapter extends BaseAdapter {

        Context context;
        JSONArray items;

        public CustomAdapter(CorporateMenuWithNavigatioinAdmin context,JSONArray items) {
            this.context = context;
            this.items = items;
        }



        @Override
        public int getCount() {
            return items.length();
        }

        @Override
        public Object getItem(int i) {
            try {
                return items.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_list_view_item_type, null);

            TextView text = v.findViewById(R.id.textView);


            try {
                JSONObject  jsonObject= new JSONObject(String.valueOf(items.get(i)));
                String companyName=jsonObject.getString("subscriberName");
                text.setText(companyName);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawer.closeDrawer(GravityCompat.START);
                        //status="All";
                        try {
                            edit_search_str=jsonObject.getString("subscriberName");
                            edit_search.setText(edit_search_str);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pageindex=0;
                        near_data= new ArrayList<>();
                        count=0;
                        GetRequestList();


                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //expListView.setSelection(1);
            //notifyDataSetChanged();

            //text.setText(items.get(i).getCategoryTitle()+" "+items.get(i).getCategorySubTitle()+""+"("+items.get(i).getCount()+")");
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
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }



//    //add viewAllTabs to the viewpager
//    private void prepareViewager(ViewPager viewPager, ArrayList<String> arrayList) {
//        ConsumerTabAdapter consumerTabAdapter=new ConsumerTabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        ConsumerAllItemsFragmentAdmin consumerAllItemsFragment =new ConsumerAllItemsFragmentAdmin();
//        for(int i=0;i<arrayList.size();i++){
//            Bundle bundle=new Bundle();
//            bundle.putString("title",arrayList.get(i));
//            bundle.putString("spinnerselection", Globaluse.spinnerselection);
//            consumerAllItemsFragment.setArguments(bundle);
//            consumerTabAdapter.createFragment(consumerAllItemsFragment,arrayList.get(i));
//            consumerAllItemsFragment=new ConsumerAllItemsFragmentAdmin();
//        }
//        viewPager.setAdapter(consumerTabAdapter);
//    }





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
//                            CustomAdapter adapter = new CustomAdapter(corporateMenuWithNavigatioin, items);
//                            navLV.setAdapter(adapter);













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
     //   pDialog = ProgressDialog.show(CorporateMenuWithNavigatioinAdmin.this, "", "Please wait...", true,false);


        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, jsonurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // pDialog.dismiss();
               // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen


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
                                            childDetail.put("checkStatus", false);
                                            childDetail.put("checkStatus_schedule", false);

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


//                            CustomAdapter adapter = new CustomAdapter(corporateMenuWithNavigatioin, items);
//                            navLV.setAdapter(adapter);
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

//                                            items.get(position).
//                                            afa
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




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  pDialog.dismiss();
                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
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







    public void  GetCompanySignUp()
    {
        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");
        String URL = APIBaseURL.BASEURLLINK_B2B_company_list;
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                //List <String> stringList = new ArrayList<String>();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if(jsonObject.getString("isSuccess").equals("true"))
                    {
                        JSONArray datajsonarray = jsonObject.getJSONArray("data");

                        for(int k=0;k<datajsonarray.length();k++)
                        {
                            //stringList.add("");
                            stringList.add(datajsonarray.getJSONObject(k).getString("companyName"));
                            stringList.add(datajsonarray.getJSONObject(k).getString("emailId"));
                            stringList.add(datajsonarray.getJSONObject(k).getString("phoneNumber"));
                            stringList.add(datajsonarray.getJSONObject(k).getString("subscriberName"));
                        }
                        GetAllRequestId();


                        CustomAdapter adapter = new CustomAdapter(corporateMenuWithNavigatioin, datajsonarray);
                        navLV.setAdapter(adapter);



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

                } else if (error instanceof NetworkError) {
                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporateMenuWithNavigatioinAdmin.this);
//        {    //this is the part, that adds the header to the request
//            @Override
//            public Map<String, String> getHeaders() {
//                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
//                String token = sh.getString("token", "");
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "Bearer " + token);
//                params.put("content-type", "application/json");
//                return params;
//            }
//        };

        ApplicationController.getInstance().addToRequestQueue(stringRequest, "companyList");
    }








    public void  GetAllRequestId()
    {
        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");
        String URL = APIBaseURL.BASEURLLINK_B2B_AddRequestId+companyId;
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if (jsonObject.getString("isSuccess").equals("true")) {
                        //JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray requestsList = jsonObject.getJSONArray("data");

                        for (int i = 0; i < requestsList.length(); i++) {
                            stringList.add(requestsList.getJSONObject(i).getString("requestId"));
                        }


                        Set<String> set = new HashSet<>(stringList);
                        stringList.clear();
                        stringList.addAll(set);

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(CorporateMenuWithNavigatioinAdmin.this, R.layout.mytextview, stringList);
                        edit_search.setAdapter(adapter2);
                        edit_search.setThreshold(1);
                        edit_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Toast.makeText(CorporateMenuWithNavigatioinAdmin.this, adapter2.getItem(position).toString(), Toast.LENGTH_SHORT).show();

//                                //searchString=adapter.getItem(position);
//                                Globaluse.searchString=adapter.getItem(position);
//                                prepareViewager(viewPager,arrayList);

                                edit_search_str=adapter2.getItem(position).toString();
                                pageindex=0;
                                near_data= new ArrayList<>();
                                count=0;
                                adapter.notifyDataSetChanged();
                                GetRequestList();


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
                if (error instanceof AuthFailureError) {
                    //TODO

                } else if (error instanceof NetworkError) {
                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporateMenuWithNavigatioinAdmin.this){    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                //params.put("content-type", "application/json");
                return params;
            }};
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "requestList");
    }







    public void  GetRequestList() {
        SharedPreferences sharedPreferences = getSharedPreferences("corporateLogin",MODE_PRIVATE);
        String companyId = sharedPreferences.getString("companyId", "");

        pDialog = ProgressDialog.show(CorporateMenuWithNavigatioinAdmin.this, "", "Please wait...", true,false);
        JSONObject inputObject = new JSONObject();
        String URL = APIBaseURL.BASEURLLINK_B2B_Subscription_list;
        try {


            inputObject = new JSONObject();
            inputObject.put("companyId", companyId);
            inputObject.put("status", status);
            inputObject.put("search", edit_search_str);
            inputObject.put("startDate", rstartDate);
            inputObject.put("endDate", rendDate);
            inputObject.put("pagesize", 50);
            inputObject.put("pageindex", pageindex);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();






//                try {
//                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
//
//                    // jsonObject.getString("isSuccess");
//
//                    Gson gson = new Gson();
//                    try {
//
//                        if(jsonObject.getString("isSuccess").equals("true")) {
//                            JSONObject dataObject = jsonObject.getJSONObject("data");
//                            JSONArray requestsList=dataObject.getJSONArray("requestsList");
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//
//                } catch (JSONException e) {
//
//                    e.printStackTrace();
//                }





                SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(java.sql.Date.class, sqlAdapter)
                        .setDateFormat("yyyy-MM-dd")
                        .create();

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if (jsonObject.getString("isSuccess").equals("true")) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray requestsList = dataObject.getJSONArray("requestsList");

                        //int forrequestsList=0;//var

                        for(int i=0;i<requestsList.length();i++)
                        {
                            RequestBtoBItem foodItem = new RequestBtoBItem();
                            Log.v("BreakFastURL", String.valueOf(requestsList.get(i)));
                            foodItem = gson.fromJson(String.valueOf(requestsList.get(i)), RequestBtoBItem .class);
                            Log.v("BreakFastURL222", String.valueOf(foodItem));

                          //  stringList.add(foodItem.getRequestId());//it can't returun all request id
//                            if(foodItem.getStatus().equalsIgnoreCase(status))//varr
//                            {
//                                near_data.add(foodItem);
//                                forrequestsList++;
//                            }
//                            else {
//                                if(status.equalsIgnoreCase("All"))
//                                {
//                                    near_data.add(foodItem);
//                                    forrequestsList++;
//                                }
//                            }
                            near_data.add(foodItem);
                        }


                    if (count == 0) //vara
                    {
                        adapter = new NearAdapter(CorporateMenuWithNavigatioinAdmin.this, near_data);
                        schedule_list.setAdapter(adapter);
                        schedule_list.setLayoutManager(new LinearLayoutManager(CorporateMenuWithNavigatioinAdmin.this));
                        pageindex++;
                    } else {
                        adapter.notifyDataSetChanged();
                        pageindex++;
                    }
//                    if (forrequestsList == 0) {
//                        count = 0;
//                    } else {
//                        count += forrequestsList;
//                    }


//
//                        if (count == 0) {
//                            adapter = new NearAdapter(CorporateMenuWithNavigatioinAdmin.this, near_data);
//                            schedule_list.setAdapter(adapter);
//                            schedule_list.setLayoutManager(new LinearLayoutManager(CorporateMenuWithNavigatioinAdmin.this));
//                        } else {
//                            adapter.notifyDataSetChanged();
//                        }
//                        if (requestsList.length() == 0) {
//                            count = 0;
//                        } else {
//                            count += requestsList.length();
//                        }
//                        pageindex++;
                }

//                    SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
//                    Gson gson = new GsonBuilder()
//                            .registerTypeAdapter(java.sql.Date.class, sqlAdapter)
//                            .setDateFormat("yyyy-MM-dd")
//                            .create();
//                    try {
//
//                        if(jsonObject.getString("isSuccess").equals("true")) {
//                            JSONObject dataObject = jsonObject.getJSONObject("data");
//                            JSONArray requestsList=dataObject.getJSONArray("requestsList");
//
//                            for(int i=0;i<requestsList.length();i++)
//                            {
//                                RequestBtoBItem foodItem = new RequestBtoBItem();
//                                Log.v("BreakFastURL", String.valueOf(requestsList.get(i)));
//                                foodItem = gson.fromJson(String.valueOf(requestsList.get(i)), RequestBtoBItem .class);
//                                Log.v("BreakFastURL222", String.valueOf(foodItem));
//
//                            }
//
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
                    //}


                } catch (JSONException e) {

                    e.printStackTrace();
                }


                pDialog.dismiss();




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                //params.put("content-type", "application/json");
                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);



    }



    public void getOrdersStatus() throws JSONException {
        String URL = APIBaseURL.BASEURLLINK_B2B_Subscription_list;

        JSONObject inputObject = new JSONObject();

        inputObject.put("companyId", "CMPNY31032021055750");
        inputObject.put("status", "All");
        inputObject.put("search", "");
        inputObject.put("startDate", "2020-04-11T10:21:54.996Z");
        inputObject.put("endDate", "2020-04-11T10:21:54.996Z");
        inputObject.put("pagesize", 20);
        inputObject.put("pageindex", 0);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL, inputObject,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray dataArray = response.getJSONArray("data");

                    JSONObject dataSingleArray = dataArray.getJSONObject(0);
                    for (int i=0;i < dataArray.length();i++)
                    {
//                        JSONObject dataObject = dataArray.getJSONObject(i);
//
//
//                        if(dataObject.optString("orderId").equals(getIntent().getStringExtra("OrderID")))
//                        {
//
//                            if(dataObject.optInt("orderDetailStatus")>=0 && dataObject.optInt("orderDetailStatus")<=2)
//                            {
//                                Toast.makeText(TrackOrderActivity.this, "Order Accepted", Toast.LENGTH_SHORT).show();
//                                statusOne.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                orderReceivedVertical.setTextColor(Color.parseColor("#F7B917"));
//                                statusOne.setTextColor(Color.parseColor("#F7B917"));
//                                statusTwo.setTextColor(Color.parseColor("#112132"));
//                                foodonwayVertical.setTextColor(Color.parseColor("#112132"));
//                                statusThree.setTextColor(Color.parseColor("#112132"));
//                                statusFour.setTextColor(Color.parseColor("#112132"));
//                                foodPrepareddVertical.setTextColor(Color.parseColor("#112132"));
//                                fooddeliveredVertical.setTextColor(Color.parseColor("#112132"));
//                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
//                                stateProgressBar.setCurrentStateDescriptionColor(Color.parseColor("#F7B917"));
//                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
//                                //  viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
//                            }
//                            else if(dataObject.optInt("orderDetailStatus")>=3 && dataObject.optInt("orderDetailStatus")<=6)
//                            {
//                                Toast.makeText(TrackOrderActivity.this, "Food is being prepared", Toast.LENGTH_SHORT).show();
//                                foodPrepareddVertical.setTextColor(Color.parseColor("#F7B917"));
//                                statusTwo.setTextColor(Color.parseColor("#F7B917"));
//                                foodonwayVertical.setTextColor(Color.parseColor("#112132"));
//                                statusOne.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                statusTwo.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                statusThree.setTextColor(Color.parseColor("#112132"));
//                                statusFour.setTextColor(Color.parseColor("#112132"));
//                                statusOne.setTextColor(Color.parseColor("#F7B917"));
//                                orderReceivedVertical.setTextColor(Color.parseColor("#F7B917"));
//                                fooddeliveredVertical.setTextColor(Color.parseColor("#112132"));
//                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
//                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
//                                stateProgressBar.setCurrentStateDescriptionColor(Color.parseColor("#F7B917"));
//                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
//                                viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
//                            }
//                            else if(dataObject.optInt("orderDetailStatus")>=7 && dataObject.optInt("orderDetailStatus")<=11)
//                            {
//                                Toast.makeText(TrackOrderActivity.this, "Food is on the way", Toast.LENGTH_SHORT).show();
//                                foodonwayVertical.setTextColor(Color.parseColor("#F7B917"));
//                                statusThree.setTextColor(Color.parseColor("#F7B917"));
//                                statusThree.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                foodPrepareddVertical.setTextColor(Color.parseColor("#F7B917"));
//                                statusTwo.setTextColor(Color.parseColor("#F7B917"));
//                                statusTwo.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                statusFour.setTextColor(Color.parseColor("#112132"));
//                                statusOne.setTextColor(Color.parseColor("#F7B917"));
//                                statusOne.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                orderReceivedVertical.setTextColor(Color.parseColor("#F7B917"));
//                                fooddeliveredVertical.setTextColor(Color.parseColor("#112132"));
//                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
//                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
//                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
//                                stateProgressBar.setCurrentStateDescriptionColor(Color.parseColor("#F7B917"));
//                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
//                                viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
//                                // viewverticallinethree.setBackgroundColor(Color.parseColor("#F7B917"));
//                                if(dataObject.optInt("orderTotalTime") == 0)
//                                {
//
//                                }
//                                else
//                                {
//                                    //String text = "<font color=#000000>"+getIntent().getStringExtra("dateOrdered")+"</font> <font color=#23C706>"+"\nETA : "+dataObject.optString("orderTotalTime") + " mins"+"</font>";
//                                    //  etaTime.setText(Html.fromHtml(text));
//                                    etaTimeinMins.setText("ETA : "+dataObject.optString("orderTotalTime") + " mins");
//                                    //etaTime.setText("ETA : "+dataObject.optString("orderTotalTime") + " mins");
//                                }
//                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
//                                viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
//                                viewverticallinethree.setBackgroundColor(Color.parseColor("#F7B917"));
//                            }
//                            else if(dataObject.optInt("orderDetailStatus")>=12)
//                            {
//                                statusOne.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                statusTwo.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                statusThree.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                statusFour.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
//                                fooddeliveredVertical.setTextColor(Color.parseColor("#F7B917"));
//                                statusFour.setTextColor(Color.parseColor("#F7B917"));
//                                foodPrepareddVertical.setTextColor(Color.parseColor("#F7B917"));
//                                statusTwo.setTextColor(Color.parseColor("#F7B917"));
//                                statusThree.setTextColor(Color.parseColor("#F7B917"));
//                                statusOne.setTextColor(Color.parseColor("#F7B917"));
//                                orderReceivedVertical.setTextColor(Color.parseColor("#F7B917"));
//                                foodonwayVertical.setTextColor(Color.parseColor("#F7B917"));
//                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
//                                viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
//                                viewverticallinethree.setBackgroundColor(Color.parseColor("#F7B917"));
//
//                                Toast.makeText(TrackOrderActivity.this, "Food is Delivered", Toast.LENGTH_SHORT).show();
//                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
//                                stateProgressBar.setAllStatesCompleted(true);
//                            }
//
//                            JSONArray orderMenuDetailsArray = new JSONArray();
//                            if(dataObject.has("orderMenuDetails"))
//                            {
//                                orderMenuDetailsArray = dataObject.getJSONArray("orderMenuDetails");
//                            }
//                            JSONObject orderMenuDetailsObjectSingle = orderMenuDetailsArray.getJSONObject(0);
//
//                            List<FoodItem> foodItemList = new ArrayList<>();
//                            for (int j=0;j < orderMenuDetailsArray.length();j++) {
//                                JSONObject orderMenuDetailsObject = orderMenuDetailsArray.getJSONObject(j);
//                                FoodItem foodItem = new FoodItem();
//                                if(orderMenuDetailsObject.getJSONArray("dishImagePath").length()!=0)
//                                {
//                                    foodItem.setFoodImage(orderMenuDetailsObject.getJSONArray("dishImagePath").get(0).toString());
//                                }
//                                foodItem.setFoodName(orderMenuDetailsObject.optString("menuName"));
//                                foodItem.setPrice(String.format("%.2f", dataObject.optDouble("price")));
//                                foodItem.setCartAddedQuantity(orderMenuDetailsObject.optInt("quantity"));
//                                foodItem.setTime(dataObject.optString("orderDate"));
//                                foodItem.setChefName(dataObject.optString("chefName"));
//                                foodItemList.add(foodItem);
//
//                            }
//                            recyclerView.setLayoutManager(new LinearLayoutManager(TrackOrderActivity.this));
//                            recyclerView.setAdapter(new OrderFoodsItemsListsAdapter(TrackOrderActivity.this,foodItemList,"track",null,null,null));
//                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {

                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this, "Error", Toast.LENGTH_SHORT).show();

                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                //params.put("content-type", "application/json");
                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"get_taq");
    }




    public class NearAdapter extends RecyclerView.Adapter<NearAdapter.ViewHolder> {
        private ArrayList<RequestBtoBItem> data;
        private Context context;
        public NearAdapter(Context context,ArrayList<RequestBtoBItem> data) {
            this.data = data;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.corporate_admin_request_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            viewHolder.company_id.setText(data.get(i).getSubscriberName());
            viewHolder.email_id.setText(data.get(i).getEmailId());
            viewHolder.mobile_id.setText(data.get(i).getPhoneNumber());

            if(data.get(i).getStatus().equalsIgnoreCase("Request"))
            {
                viewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_yellow);
            }else if(data.get(i).getStatus().equalsIgnoreCase("Approved"))
            {
                viewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_green);
            }
            else if(data.get(i).getStatus().equalsIgnoreCase("Rejected"))
            {
                viewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_red);
            }else
            {
                viewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_yellow);
            }


            viewHolder.view_detail_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CorporateMenuWithNavigatioinAdmin.this, CorporateOrderDetail.class);
                    intent.putExtra("requestId", data.get(i).getRequestId());
                    intent.putExtra("companyId", data.get(i).getCompanyId());
                    startActivity(intent);
                }
            });


//            viewHolder.review.setText(data.get(i).getReview());
//            viewHolder.category.setText(data.get(i).getCategory_name());
//            viewHolder.address.setText(data.get(i).getAddress());
//            viewHolder.rat.setRating(Integer.parseInt(data.get(i).getRate()));
//            viewHolder.vi_click.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    GlobalVariable.position=i;
//                    Intent ik=new Intent(context,Restaurant_detail.class);
//                    ik.putExtra("rid",""+data.get(i).getId());
//                    startActivity(ik);
//                }
//            });
//            if(data.get(i).getImage_path().equals(""))
//            {
//                Picasso.with(context).load("abc").placeholder(R.drawable.load_240).into(viewHolder.img);
//            }
//            else
//            {
//                Picasso.with(context).load(data.get(i).getImage_path()).placeholder(R.drawable.load_240).into(viewHolder.img);
//            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
//            private TextView name,distince,review,category,address;
//            private ImageView img;
//            private RatingBar rat;
//            CardView vi_click;
            Button view_detail_id;
            TextView company_id,email_id,mobile_id;

            public ViewHolder(View view) {
                super(view);
                view_detail_id = (Button) view.findViewById(R.id.view_detail_id);
                company_id = (TextView) view.findViewById(R.id.company_id);
                email_id = (TextView) view.findViewById(R.id.email_id);
                mobile_id = (TextView) view.findViewById(R.id.mobile_id);




//                name = (TextView)view.findViewById(R.id.list_near_by_name);
//                distince = (TextView)view.findViewById(R.id.list_near_by_distince);
//                review = (TextView)view.findViewById(R.id.list_near_by_review);
//                category = (TextView)view.findViewById(R.id.list_near_by_category);
//                address = (TextView)view.findViewById(R.id.list_near_by_address);
//                img = (ImageView) view.findViewById(R.id.list_near_by_img);
//                rat = (RatingBar)view.findViewById(R.id.list_near_by_ratbar);
//                vi_click = (CardView)view.findViewById(R.id.list_near_card);
            }
        }
    }

}
