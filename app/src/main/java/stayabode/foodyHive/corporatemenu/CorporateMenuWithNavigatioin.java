package stayabode.foodyHive.corporatemenu;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import stayabode.foodyHive.R;

import stayabode.foodyHive.activities.consumers.AboutUsAndGetHelpWebViewActivity;
import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAdapterNew;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.CorporateDish;
import stayabode.foodyHive.models.CorporateMenuModel;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CorporateMenuWithNavigatioin extends AppCompatActivity {

    ListView navLV;
    ImageView close_navigation_drawer;
    TextView menu_drawer_open_id;
    private static final String TAG = CorporateMenuWithNavigatioin.class.getName();

    CorporateExpandableListAdapterNew listAdapter;
    ExpandableListView expListView;
//    List<String> listDataHeader;
//    HashMap<String, List<String>> listDataChild;

    List<CorporateMenuModel> items = new ArrayList<>();
    List<CorporateDish> CorporateDish_list = new ArrayList<>();
    List<CorporateDish.MenuDetail> MenuDetail_list=new ArrayList<>();

    Button view_basket_id;
    TextView whatsapp_id;
    public static CorporateMenuWithNavigatioin corporateMenuWithNavigatioin;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String jsonurl = "https://foodyhivestorage.blob.core.windows.net/assets/data/subscription-requests.json";

    DrawerLayout drawer;
    private ProgressDialog pDialog;
    TextView logout_id;

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
                finishAffinity();
                Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateHomeActivity.class);
                startActivity(intent);
                
            }
        });



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


                            if(jsonObject.getBoolean("buttondisplay"))
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


                    corporateshared();


                    Intent intent = new Intent(CorporateMenuWithNavigatioin.this, CorporateOrderSummery.class);
                    startActivity(intent);
                }else
                {
                    Globaluse.corporatedishlistnew=new  ArrayList<String>();
                    Toast.makeText(getApplicationContext(), "Basket is Empty", Toast.LENGTH_SHORT).show();
                }


            }
        });


        navLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), items.get(position).getCategoryTitle() + " Clicked", Toast.LENGTH_SHORT).show();

                drawer.closeDrawer(GravityCompat.START);
                expListView.post(new Runnable() {

                    @Override
                    public void run() {

                        expListView.setSelection(Integer.parseInt(items.get(position).getPostion()));

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
            super.onBackPressed();
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
            listAdapter = new CorporateExpandableListAdapterNew(this,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
            expListView.setAdapter(listAdapter);
        }
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

                                    listAdapter = new CorporateExpandableListAdapterNew(corporateMenuWithNavigatioin,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
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


        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, jsonurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
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

                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        dishlistdata.add(String.valueOf(childDetail));
                                    }

                                    Globaluse._listDataChild.put(Globaluse._listDataHeader.get(adddataTo), dishlistdata);

                                    adddataTo++;

                                    listAdapter = new CorporateExpandableListAdapterNew(corporateMenuWithNavigatioin,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
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




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
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



}
