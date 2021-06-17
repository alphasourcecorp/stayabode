
package stayabode.foodyHive.corporatemenurange;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.applandeo.materialcalendarview.CalendarView;
import stayabode.applandeo.materialcalendarview.EventDay;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAfterSchdule;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.FulllistItemdisplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class CorporateAfterRequestScheduleApi extends AppCompatActivity {



    private static final String TAG = "CorporateAfterScheduleApi";
    static CalendarView calendarView;
    static List<EventDay> events = new ArrayList<>();
     int incre = 0;
     LinearLayout startdate_button_id,enddate_button_id;
     static TextView start_date_id;
    static TextView end_date_id;
    static Calendar min = Calendar.getInstance();
    static Calendar max = Calendar.getInstance();
    static int start_day=0;
    static int start_month=0;
    static int  start_year=0;
    static int end_day=0;
    static int end_month=0;
    static int  end_year=0;
  //  int End_date_MILLISECOND_calender=0;
   // int click_date_MILLISECOND_calender=0;

    static ArrayList<HashMap<String, String>> arraylist;
    Button suubmit_id;

    ListView navLV;
    TextView cartTotalCountText;
    ExpandableListView expListView;

    CorporateExpandableListAfterSchdule listAdapter;

    Context context= CorporateAfterRequestScheduleApi.this;
    Button subscribe_id;
    ImageView back_id;
    private ProgressDialog pDialog;
    JSONArray datajsonarray;
    String requestId="",companyID="";
    TextView titlelist;

    String isActive ="";
   String isDeleted ="";
   String partitionKey ="";
   String timestamp ="";
   String emailFlag ="";
   String eTag ="";
   String subscriptionDate ="";
   String rowKey ="";

    String addressLine1="",addressLine2="",city="",state="",postalCode="",country="",notes="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_after_schedule);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
       // GetscheduleCount();
        navLV = findViewById(R.id.navLV);
        navLV.setSmoothScrollbarEnabled(true);


        titlelist = findViewById(R.id.titlelist);
        titlelist.setText("Update Schedule List");

        back_id = findViewById(R.id.back_id);
        back_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        // get the listview
//        expListView = (ExpandableListView) findViewById(R.id.lvExp);
//        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//
//                return false;
//            }
//        });
//
//        // Listview Group expanded listener
//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//
//
//            }
//        });
//
//        // Listview Group collasped listener
//        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//
//            }
//        });
//
//        // Listview on child click listener
//        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                // TODO Auto-generated method stub
//
//
//                return false;
//            }
//        });
//
//
////        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
////        {
////            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId)
////            {
////                expListView.expandGroup(itemPosition);
////                return true;
////            }
////        });
//
//        listAdapter = new CorporateExpandableListAfterSchdule(this,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
//        expListView.setAdapter(listAdapter);


        subscribe_id = findViewById(R.id.subscribe_id);
        subscribe_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(CorporateAfterRequestScheduleApi.this,CorporatebussinessInformationUpdateSubmit.class);
//                startActivity(intent);


                Intent intent = new Intent(CorporateAfterRequestScheduleApi.this,CorporatebussinessInformationUpdateSubmit.class);
                intent.putExtra("schedullingInfo", String.valueOf(Globaluse.scheduleList));
                intent.putExtra("requestId", requestId);
                intent.putExtra("companyId", companyID);
                intent.putExtra("addressLine1", addressLine1);
                intent.putExtra("addressLine2", addressLine2);
                intent.putExtra("city", city);
                intent.putExtra("state", state);
                intent.putExtra("postalCode", postalCode);
                intent.putExtra("country", country);
                intent.putExtra("subscriptionDate", subscriptionDate);
                intent.putExtra("isActive", isActive);
                intent.putExtra("isDeleted", isDeleted);
                intent.putExtra("partitionKey", partitionKey);
                intent.putExtra("rowKey", rowKey);
                intent.putExtra("timestamp", timestamp);
                intent.putExtra("eTag", eTag);
                intent.putExtra("emailFlag", "N");
                intent.putExtra("notes", notes);
                startActivity(intent);

            }
        });
        subscribe_id.setText("Update now");



        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            String schedullingInfo_str =((String) b.get("schedullingInfo"));
            requestId =((String) b.get("requestId"));
            companyID =((String) b.get("companyId"));
            addressLine1 =((String) b.get("addressLine1"));
            addressLine2 =((String) b.get("addressLine2"));
            city =((String) b.get("city"));
            state =((String) b.get("state"));
            postalCode =((String) b.get("postalCode"));
            country =((String) b.get("country"));
            isActive =((String) b.get("isActive"));
            isDeleted =((String) b.get("isDeleted"));
            partitionKey =((String) b.get("partitionKey"));
            timestamp =((String) b.get("timestamp"));
            emailFlag =((String) b.get("emailFlag"));
            eTag =((String) b.get("eTag"));
            subscriptionDate =((String) b.get("subscriptionDate"));
            rowKey =((String) b.get("rowKey"));
            notes =((String) b.get("notes"));



//                try {
//                    datajsonarray = new JSONArray(schedullingInfo_str);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


        }


        CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterRequestScheduleApi.this,  Globaluse.scheduleList);
        navLV.setAdapter(adapter);
        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);

    }



    class CustomAdapter2 extends BaseAdapter {

        Context context;
        JSONArray items;

        public CustomAdapter2(Context context, JSONArray items) {
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
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_schedule_item_new, null);

            ImageView dropdown_id = v.findViewById(R.id.dropdown_id);
            LinearLayout displaylist = v.findViewById(R.id.displaylist);
            TextView s_date_id = v.findViewById(R.id.s_date_id);
            TextView s_month_id = v.findViewById(R.id.s_month_id);
            TextView s_year_id = v.findViewById(R.id.s_year_id);
            TextView breakfast_id = v.findViewById(R.id.breakfast_id);
            TextView lunch_id = v.findViewById(R.id.lunch_id);
            TextView dinner_id = v.findViewById(R.id.dinner_id);
            ImageView edit_id = v.findViewById(R.id.edit_id);
            ImageView delete_id = v.findViewById(R.id.delete_id);
            TextView e_date_id = v.findViewById(R.id.e_date_id);
            TextView e_month_id = v.findViewById(R.id.e_month_id);
            TextView e_year_id = v.findViewById(R.id.e_year_id);
            RecyclerView dish_recycler_view = v.findViewById(R.id.dish_recycler_view);
            RecyclerView excludes_recycler_view = v.findViewById(R.id.excludes_recycler_view);
            LinearLayout exclude_linear_id = v.findViewById(R.id.exclude_linear_id);
            TextView total_id = v.findViewById(R.id.total_id);

            dropdown_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(displaylist.isShown())
                    {
                        displaylist.setVisibility(View.GONE);
                    }else
                    {
                        displaylist.setVisibility(View.VISIBLE);
                    }


                }
            });



            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(items.get(i)));
                String scheduleID=jsonObject.getString("scheduleID");
                String comapnyID=jsonObject.getString("companyID");





                edit_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
                        {
                            for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
                            {
                                final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(childText);
                                    jsonObject.put("buttondisplay", false);
                                    jsonObject.put("quantity", 0);
                                    jsonObject.put("checkStatus", false);
                                    jsonObject.put("checkStatus_schedule", false);

                                    updatejson(c,t,String.valueOf(jsonObject));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
//


                        try {
                            JSONArray dishesItems_jsonarray = new JSONArray(jsonObject.getString("dishesItems"));
                            Globaluse.corporatedishlistnew=new ArrayList<String>();
                            for(int k=0;k<dishesItems_jsonarray.length();k++)
                            {
                                dishesItems_jsonarray.getJSONObject(k).getString("noOfUnits");

                                JSONObject dishDetail = new JSONObject();
                                try {
                                    dishDetail.put("dishItemName", dishesItems_jsonarray.getJSONObject(k).getString("dishItemName"));
                                    dishDetail.put("description", dishesItems_jsonarray.getJSONObject(k).getString("description"));
                                    dishDetail.put("cost", dishesItems_jsonarray.getJSONObject(k).getString("cost"));
                                    dishDetail.put("noOfUnits", dishesItems_jsonarray.getJSONObject(k).getString("noOfUnits"));
                                    dishDetail.put("taxValue", dishesItems_jsonarray.getJSONObject(k).getString("taxValue"));
                                    dishDetail.put("packingCharges", dishesItems_jsonarray.getJSONObject(k).getString("packingCharges"));
                                    dishDetail.put("deliveryCharges", dishesItems_jsonarray.getJSONObject(k).getString("deliveryCharges"));
                                    dishDetail.put("buttondisplay", true);
                                    dishDetail.put("quantity", dishesItems_jsonarray.getJSONObject(k).getString("noOfUnits"));
                                    dishDetail.put("checkStatus", true);
                                    dishDetail.put("checkStatus_schedule", true);
                                    dishDetail.put("dishImage", dishesItems_jsonarray.getJSONObject(k).getString("dishImageUrl"));

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                Globaluse.corporatedishlistnew.add(String.valueOf(dishDetail));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                        
                        for(int l=0;l<Globaluse.corporatedishlistnew.size();l++)
                        {
                        try {

                            JSONObject jsonObject_old = null;
                            jsonObject_old = new JSONObject(Globaluse.corporatedishlistnew.get(l));
                            String dishItemName=jsonObject_old.getString("dishItemName");

                            for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
                            {
                                for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
                                {
                                    final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(childText);

                                        if(jsonObject.getString("dishName").equals(dishItemName))
                                        {
                                            //if(jsonObject.getString("saleAmount").equals(dishItemName))
                                            //{
                                            try {
                                               // jsonObject.put("quantity", qut);

                                                jsonObject.put("buttondisplay", true);
                                                jsonObject.put("quantity", jsonObject_old.getString("quantity"));
                                                jsonObject.put("checkStatus", true);
                                                jsonObject.put("checkStatus_schedule", true);
                                                
                                                updatejson(c,t,String.valueOf(jsonObject));
                                                
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            
                                            

                                            //}

                                        }




                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        }


                        try {

                            String[] separated = (jsonObject.getString("startDate").split("T"));
                            String[] separated2 = (jsonObject.getString("endDate").split("T"));


                            String string = dateFormat(separated[0]);
                            String string1 = dateFormat(separated2[0]);

//                            String string = separated[0];
//                            String string1 = separated2[0];



                        Intent intent = new Intent(CorporateAfterRequestScheduleApi.this,CorporateRequestScheduleEditFromApi.class);
                        intent.putExtra("sdate", string);
                        intent.putExtra("endate", string1);
                        intent.putExtra("isBreakfast", jsonObject.getBoolean("isBreakfast"));
                        intent.putExtra("isLunch", jsonObject.getBoolean("isLunch"));
                        intent.putExtra("isDinner", jsonObject.getBoolean("isDinner"));
                        intent.putExtra("exclusionWeeks", String.valueOf(jsonObject.getJSONArray("exclusionWeeks")));
                        intent.putExtra("exclusionDates", String.valueOf(jsonObject.getJSONArray("exclusionDates")));
                        intent.putExtra("scheduleID", (jsonObject.getString("scheduleID")));
                        intent.putExtra("companyID", (jsonObject.getString("companyID")));
                        intent.putExtra("updatepostion", i+"");
                           // Globaluse.scheduleList.remove(i);
                        startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


               // pDialog.dismiss();

                JSONArray dishesItems_jsonarray = new JSONArray(jsonObject.getString("dishesItems"));
                CustomAdapterRecycleDish horizontalAdapter;
                horizontalAdapter=new CustomAdapterRecycleDish(dishesItems_jsonarray);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(CorporateAfterRequestScheduleApi.this, LinearLayoutManager.VERTICAL, false);
                dish_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                dish_recycler_view.setAdapter(horizontalAdapter);
                dish_recycler_view.setNestedScrollingEnabled(false);

                JSONArray exclusionDates_jsonarray = new JSONArray(jsonObject.getString("exclusionDates"));
                CustomAdapterRecycle horizontalAdapter2;
                horizontalAdapter2=new CustomAdapterRecycle(exclusionDates_jsonarray);
                LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(CorporateAfterRequestScheduleApi.this, LinearLayoutManager.HORIZONTAL, false);
                excludes_recycler_view.setLayoutManager(horizontalLayoutManagaer2);
                excludes_recycler_view.setAdapter(horizontalAdapter2);
                excludes_recycler_view.setNestedScrollingEnabled(false);


                String[] separated = (jsonObject.getString("startDate").split("T"));


                String string = dateFormat(separated[0]);


                String[] parts = string.split("/");
                String part1 = parts[0]; // 004
                String part2 = parts[1];
                String part3 = parts[2];
                String input_date=string;
                SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");

                Date dt1= null;
                try {
                    dt1 = format1.parse(input_date);
                    DateFormat format2=new SimpleDateFormat("MMM");
                    String finalDay=format2.format(dt1);
                    s_month_id.setText(finalDay);
                    s_date_id.setText(part1);
                    s_year_id.setText(part3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String[] separated2 = (jsonObject.getString("endDate").split("T"));

                String string1 = dateFormat(separated2[0]);
                String[] parts1 = string1.split("/");
                String part11 = parts1[0]; // 004
                String part21 = parts1[1];
                String part31= parts1[2];
                String input_date1=string1;
                SimpleDateFormat format11=new SimpleDateFormat("dd/MM/yyyy");
                Date dt11= null;
                try {
                    dt11 = format11.parse(input_date1);
                    DateFormat format2=new SimpleDateFormat("MMM");
                    String finalDay=format2.format(dt11);
                    e_month_id.setText(finalDay);
                    e_date_id.setText(part11);
                    e_year_id.setText(part31);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int totalmeal=0;
                        if(jsonObject.getBoolean("isBreakfast"))
                            {
                                breakfast_id.setVisibility(View.VISIBLE);
                                totalmeal++;
                            }

                            if(jsonObject.getBoolean("isLunch"))
                            {
                                lunch_id.setVisibility(View.VISIBLE);
                                totalmeal++;
                            }
                            if(jsonObject.getBoolean("isDinner"))
                            {
                                dinner_id.setVisibility(View.VISIBLE);
                                totalmeal++;
                            }



                if(Globaluse.scheduleList.length()==1)
                {
                    delete_id.setVisibility(View.INVISIBLE);

                }else
                {
                    delete_id.setVisibility(View.VISIBLE);
                }


                delete_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // removeCart(comapnyID, scheduleID);

//                        if(Globaluse.scheduleList.length()==1)
//                        {
//
//
////                            AlertDialog alertDialog = new AlertDialog.Builder(CorporateAfterRequestScheduleApi.this).create();
////                            alertDialog.setTitle("Notes:");
////                            alertDialog.setMessage("You have only one schedule ,you want to delete the request also delete");
////                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
////                                    new DialogInterface.OnClickListener() {
////                                        public void onClick(DialogInterface dialog, int which) {
////                                            dialog.dismiss();
////                                        }
////                                    });
////                            alertDialog.show();
//
//                        }else
//                        {
//                            Globaluse.scheduleList.remove(i);
//                            CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterRequestScheduleApi.this,  Globaluse.scheduleList);
//                            navLV.setAdapter(adapter);
//                            FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);
//                        }

                        Globaluse.scheduleList.remove(i);
                        CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterRequestScheduleApi.this,  Globaluse.scheduleList);
                        navLV.setAdapter(adapter);
                        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);



                    }
                });


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate start = LocalDate.parse(string,formatter);
                LocalDate end = LocalDate.parse(string1,formatter);

                System.out.println(ChronoUnit.DAYS.between(start, end));



//                double noOfUnits= Double.parseDouble(dataSet.getJSONObject(listPosition).getString("noOfUnits"));
//                double cost= Double.parseDouble(dataSet.getJSONObject(listPosition).getString("cost"));
//                double totalval=((noOfUnits)*(cost));

                double dishtotal=0.0;
                for(int t=0;t<dishesItems_jsonarray.length();t++)
                {
                    double noOfUnits= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("noOfUnits"));
                    double cost= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("cost"));
                    double totalval=((noOfUnits)*(cost));
                    Log.i(TAG, "noOfUnits ( * ) cost" + noOfUnits +" * "+cost+" = "+totalval);
                    dishtotal+=totalval;
                    Log.i(TAG, "dishtotal" + dishtotal);
                }

                double selectmeal=(dishtotal * totalmeal);

                Log.i(TAG, "dishTotal ( * ) totalmeal" + selectmeal);

                long numberOfDays=((ChronoUnit.DAYS.between(start, end))-(exclusionDates_jsonarray.length()))+1;

                Log.i(TAG, "numberOfDays : start date & end date "+""+ChronoUnit.DAYS.between(start, end)+ " - "+"exclusionDates length : "+exclusionDates_jsonarray.length()+" = "+numberOfDays);

                double alltotal=0.0;
                JSONArray dishesItems_jsonarray2 = new JSONArray(jsonObject.getString("dishesItems"));
                boolean isWekely=Globaluse.CheckWeeeklyUsingJson(dishesItems_jsonarray2);
                if(isWekely)
                {
                    // Toast.makeText(CorporateAfterScheduleApi.this, "Only Weekly Selection", Toast.LENGTH_SHORT).show();
                    alltotal=(selectmeal * 1);
                }
                else
                {
                    //Toast.makeText(CorporateAfterScheduleApi.this, "Day selection", Toast.LENGTH_SHORT).show();
                    alltotal=(selectmeal * numberOfDays);
                }


               // double alltotal=(selectmeal * numberOfDays);



                Log.i(TAG, "all total" + alltotal);
                total_id.setText(""+alltotal);
               // s_date_id.setText(scheduleID);

            } catch (JSONException e) {
                e.printStackTrace();
            }



//            CustomAdapterRecycleDish horizontalAdapter;
//            ArrayList<String> listdata = new ArrayList<String>();
//            ArrayList<HashMap<String, String>> schedule_data = new ArrayList<HashMap<String, String>>();
//
//
//            double dishtotal=0.0;
//            Globaluse.corporatedishlistnewEdit=new ArrayList<String>();
//
//
//                for(int dishitem=0;dishitem<(items.get(i)).size();dishitem++)
//                {
//                       HashMap<String, String> itr = new HashMap<String, String>();
//                         itr=items.get(i).get(dishitem);
//                        schedule_data.add(itr);
//
//
////                    HashMap<String, String> map = new HashMap<String, String>();
////                    map.put("id", "FH"+k);
////                    map.put("dishItemName", jsonObject.getString("dishItemName"));
////                    map.put("description", jsonObject.getString("description"));
////                    map.put("cost", jsonObject.getString("cost"));
////                    map.put("noOfUnits", jsonObject.getString("noOfUnits"));
////                    map.put("dishImage", jsonObject.getString("dishImage"));
////                    map.put("taxValue", jsonObject.getString("taxValue"));
////                    map.put("packingCharges", jsonObject.getString("packingCharges"));
////                    map.put("deliveryCharges", jsonObject.getString("deliveryCharges"));
////                    map.put("buttondisplay", jsonObject.getString("buttondisplay"));
////                    map.put("quantity", jsonObject.getString("quantity"));
////                    map.put("checkStatus", String.valueOf(jsonObject.getBoolean("checkStatus")));
////                    map.put("checkStatus_schedule", String.valueOf(jsonObject.getBoolean("checkStatus_schedule")));
////                    map.put("startDate", Sdate);
////                    map.put("endDate", Edate);
////                    map.put("deliveryType", deliveryType);//1-lunch,2-dinner,3-both
////                    map.put("breakfast",""+breakfast_chk_id.isChecked());
////                    map.put("lunch", ""+lunch_chk_id.isChecked());
////                    map.put("dinner",""+dinner_chk_id.isChecked());
////                    map.put("noNeedDeliver", String.valueOf(no_need_deliver_that_day));
////
//
//
//
//                    JSONObject dishDetail = new JSONObject();
//                    try {
//                        dishDetail.put("dishItemName", items.get(i).get(dishitem).get("dishItemName"));
//                        dishDetail.put("description", items.get(i).get(dishitem).get("description"));
//                        dishDetail.put("cost", items.get(i).get(dishitem).get("cost"));
//                        dishDetail.put("noOfUnits", items.get(i).get(dishitem).get("noOfUnits"));
//                        dishDetail.put("taxValue", items.get(i).get(dishitem).get("taxValue"));
//                        dishDetail.put("packingCharges", items.get(i).get(dishitem).get("packingCharges"));
//                        dishDetail.put("deliveryCharges", items.get(i).get(dishitem).get("deliveryCharges"));
//                        dishDetail.put("buttondisplay", items.get(i).get(dishitem).get("buttondisplay"));
//                        dishDetail.put("quantity", items.get(i).get(dishitem).get("quantity"));
//                        dishDetail.put("checkStatus", items.get(i).get(dishitem).get("checkStatus"));
//                        dishDetail.put("checkStatus_schedule", items.get(i).get(dishitem).get("checkStatus_schedule"));
//                        dishDetail.put("dishImage", items.get(i).get(dishitem).get("dishImage"));
//
//                    } catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    Globaluse.corporatedishlistnewEdit.add(String.valueOf(dishDetail));
//
//
//
//                    double noOfUnits= Double.parseDouble(items.get(i).get(dishitem).get("noOfUnits"));
//                    double cost= Double.parseDouble(items.get(i).get(dishitem).get("cost"));
//                    double totalval=((noOfUnits)*(cost));
//                    dishtotal+=totalval;
//
//                        if(dishitem==0)
//                        {
//
//                            String string = (items.get(i).get(dishitem).get("startDate"));
//                            String[] parts = string.split("/");
//                            String part1 = parts[0]; // 004
//                            String part2 = parts[1];
//                            String part3 = parts[2];
//                            String input_date=string;
//                            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
//                            Date dt1= null;
//                            try {
//                                dt1 = format1.parse(input_date);
//                                DateFormat format2=new SimpleDateFormat("MMM");
//                                String finalDay=format2.format(dt1);
//                                s_month_id.setText(finalDay);
//                                s_date_id.setText(part1);
//                                s_year_id.setText(part3);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                            String string1 = (items.get(i).get(dishitem).get("endDate"));
//                            String[] parts1 = string1.split("/");
//                            String part11 = parts1[0]; // 004
//                            String part21 = parts1[1];
//                            String part31= parts1[2];
//                            String input_date1=string1;
//                            SimpleDateFormat format11=new SimpleDateFormat("dd/MM/yyyy");
//                            Date dt11= null;
//                            try {
//                                dt11 = format11.parse(input_date1);
//                                DateFormat format2=new SimpleDateFormat("MMM");
//                                String finalDay=format2.format(dt11);
//                                e_month_id.setText(finalDay);
//                                e_date_id.setText(part11);
//                                e_year_id.setText(part31);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            if(items.get(i).get(dishitem).get("breakfast").equals("true"))
//                            {
//                                breakfast_id.setVisibility(View.VISIBLE);
//                            }
//                            if(items.get(i).get(dishitem).get("lunch").equals("true"))
//                            {
//                                lunch_id.setVisibility(View.VISIBLE);
//                            }
//                            if(items.get(i).get(dishitem).get("dinner").equals("true"))
//                            {
//                                dinner_id.setVisibility(View.VISIBLE);
//                            }
//
//
//                            JSONArray jArray = null;
//                            try {
//                                jArray = new JSONArray(items.get(i).get(dishitem).get("noNeedDeliver"));
//                                if (jArray != null) {
//                                    for (int l=0;l<jArray.length();l++){
//                                        listdata.add(jArray.getString(l));
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                           // listdata=items.get(i).get(dishitem).get("noNeedDeliver");
//                            // JSONArray jArray = (JSONArray)jsonObject;
////                            if (items.get(i).get(dishitem).get("dinner") != null) {
//
////                                for (int k=0;k<items.get(i).get(dishitem).get("noNeedDeliver").length();k++){
//////                                    try {
//////
//////                                     //   listdata.add(no_need_deliver_that_day.getString(k));
//////
//////                                    } catch (JSONException e) {
//////                                        e.printStackTrace();
//////                                    }
////                                }
//
//
//
//                           // }
//
//                        }
//
//
//                }
//
//            total_id.setText(""+dishtotal);
//
//                if(listdata.isEmpty())
//                {
//                    exclude_linear_id.setVisibility(View.GONE);
//                }else
//                {
//                    exclude_linear_id.setVisibility(View.VISIBLE);
//                }
//
//
//            CustomAdapterRecycle horizontalAdapter2;
//            horizontalAdapter2=new CustomAdapterRecycle(listdata);
//            LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(CorporateAfterScheduleApi.this, LinearLayoutManager.HORIZONTAL, false);
//            excludes_recycler_view.setLayoutManager(horizontalLayoutManagaer2);
//            excludes_recycler_view.setAdapter(horizontalAdapter2);
//
//            excludes_recycler_view.setNestedScrollingEnabled(false);
//           // FulllistItemdisplay.setListViewHeightBasedOnItems(excludes_recycler_view);
//
//            horizontalAdapter=new CustomAdapterRecycleDish(schedule_data);
//            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(CorporateAfterScheduleApi.this, LinearLayoutManager.VERTICAL, false);
//            dish_recycler_view.setLayoutManager(horizontalLayoutManagaer);
//            dish_recycler_view.setAdapter(horizontalAdapter);
//            dish_recycler_view.setNestedScrollingEnabled(false);
//
////                Glide.with(CorporateAfterSchedule.this).load(items.get(i).get("dishImage")).into(img_id);
////                dish_multipleprice.setText("Rs. "+jsonObject.getString("cost"));
////                dish_id.setText(items.get(i).get("noOfUnits")+" X "+jsonObject.getString("dishItemName"));
//
////            }catch (JSONException err){
////                Log.d("Error", err.toString());
////            }
//
//
//
//            delete_id.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Globaluse.schedule_data_array
//                    Globaluse.schedule_data_array.remove(items.get(i));
//                    notifyDataSetChanged();
////                    CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterSchedule.this,  Globaluse.schedule_data_array);
////                    navLV.setAdapter(adapter);
////                    FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);
//
//                }
//            });
//
//
//
//            edit_id.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                    Globaluse.corporatedishlistnewEdit=new ArrayList<String>();
//                    for(int dishitem=0;dishitem<(items.get(i)).size();dishitem++) {
//
//
////                    HashMap<String, String> map = new HashMap<String, String>();
////                    map.put("id", "FH"+k);
////                    map.put("dishItemName", jsonObject.getString("dishItemName"));
////                    map.put("description", jsonObject.getString("description"));
////                    map.put("cost", jsonObject.getString("cost"));
////                    map.put("noOfUnits", jsonObject.getString("noOfUnits"));
////                    map.put("dishImage", jsonObject.getString("dishImage"));
////                    map.put("taxValue", jsonObject.getString("taxValue"));
////                    map.put("packingCharges", jsonObject.getString("packingCharges"));
////                    map.put("deliveryCharges", jsonObject.getString("deliveryCharges"));
////                    map.put("buttondisplay", jsonObject.getString("buttondisplay"));
////                    map.put("quantity", jsonObject.getString("quantity"));
////                    map.put("checkStatus", String.valueOf(jsonObject.getBoolean("checkStatus")));
////                    map.put("checkStatus_schedule", String.valueOf(jsonObject.getBoolean("checkStatus_schedule")));
////                    map.put("startDate", Sdate);
////                    map.put("endDate", Edate);
////                    map.put("deliveryType", deliveryType);//1-lunch,2-dinner,3-both
////                    map.put("breakfast",""+breakfast_chk_id.isChecked());
////                    map.put("lunch", ""+lunch_chk_id.isChecked());
////                    map.put("dinner",""+dinner_chk_id.isChecked());
////                    map.put("noNeedDeliver", String.valueOf(no_need_deliver_that_day));
////
//
//
//                        JSONObject dishDetail = new JSONObject();
//                        try {
//                            dishDetail.put("dishItemName", items.get(i).get(dishitem).get("dishItemName"));
//                            dishDetail.put("description", items.get(i).get(dishitem).get("description"));
//                            dishDetail.put("cost", items.get(i).get(dishitem).get("cost"));
//                            dishDetail.put("noOfUnits", items.get(i).get(dishitem).get("noOfUnits"));
//                            dishDetail.put("taxValue", items.get(i).get(dishitem).get("taxValue"));
//                            dishDetail.put("packingCharges", items.get(i).get(dishitem).get("packingCharges"));
//                            dishDetail.put("deliveryCharges", items.get(i).get(dishitem).get("deliveryCharges"));
//                            dishDetail.put("buttondisplay", items.get(i).get(dishitem).get("buttondisplay"));
//                            dishDetail.put("quantity", items.get(i).get(dishitem).get("quantity"));
//                            dishDetail.put("checkStatus", items.get(i).get(dishitem).get("checkStatus"));
//                            dishDetail.put("checkStatus_schedule", items.get(i).get(dishitem).get("checkStatus_schedule"));
//                            dishDetail.put("dishImage", items.get(i).get(dishitem).get("dishImage"));
//
//                        } catch (JSONException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        Globaluse.corporatedishlistnewEdit.add(String.valueOf(dishDetail));
//                    }
//
//
//
//                    Intent intent = new Intent(CorporateAfterScheduleApi.this, CorporateScheduleEdit.class);
//                    startActivity(intent);
//
//                }
//            });



            return v;
        }
    }






    public class CustomAdapterRecycleDish extends RecyclerView.Adapter<CustomAdapterRecycleDish.MyViewHolder> {

        private JSONArray dataSet;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView dish_id,dish_price,dish_multipleprice;
            ImageView img_id;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.dish_id = (TextView) itemView.findViewById(R.id.dish_id);
                this.dish_price = (TextView) itemView.findViewById(R.id.dish_price);
                this.dish_multipleprice = (TextView) itemView.findViewById(R.id.dish_multipleprice);
                //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
                this.img_id = (ImageView) itemView.findViewById(R.id.img_id);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                    }
                });
            }
        }

        public CustomAdapterRecycleDish(JSONArray data) {
            this.dataSet = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_schedule_my_dish_item, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView dish_id = holder.dish_id;
            TextView dish_price = holder.dish_price;
            TextView dish_multipleprice = holder.dish_multipleprice;
            ImageView img_id = holder.img_id;

            try {

                //dataSet.getJSONObject(listPosition).getString("dishItemName")
                dish_id.setText(dataSet.getJSONObject(listPosition).getString("noOfUnits")+" X "+dataSet.getJSONObject(listPosition).getString("dishItemName"));
                //dish_price.setText("Rs. "+dataSet.getJSONObject(listPosition).getString("cost")+" /-");
                double noOfUnits= Double.parseDouble(dataSet.getJSONObject(listPosition).getString("noOfUnits"));
                double cost= Double.parseDouble(dataSet.getJSONObject(listPosition).getString("cost"));
                double totalval=((noOfUnits)*(cost));

               // dish_multipleprice.setText(""+totalval);

                dish_multipleprice.setText("₹ "+totalval);
                if(dataSet.getJSONObject(listPosition).getString("cost").equals("0"))
                {
                    //price_id.setText("Rs. "+" TBD ");
                    dish_price.setText("₹ "+" TBD ");
                }else
                {
                    dish_price.setText("₹ "+dataSet.getJSONObject(listPosition).getString("cost")+"/-");
                }




                Glide.with(CorporateAfterRequestScheduleApi.this).load(dataSet.getJSONObject(listPosition).getString("dishImageUrl")).into(img_id);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //textViewVersion.setText(dataSet.get(listPosition).getVersion());
            //imageView.setImageResource(dataSet.get(listPosition).getImage());
        }

        @Override
        public int getItemCount() {
            return dataSet.length();
        }
    }




    class CustomAdapterRecycle extends RecyclerView.Adapter<CustomAdapterRecycle.MyViewHolder> {

        private JSONArray dataSet;

        public  class MyViewHolder extends RecyclerView.ViewHolder {

            TextView month_id,day_id,year_id;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.month_id = (TextView) itemView.findViewById(R.id.month_id);
                this.day_id = (TextView) itemView.findViewById(R.id.day_id);
                this.year_id = (TextView) itemView.findViewById(R.id.year_id);
                //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
//                this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        if (getPosition()==0)
//                        {
//                            Toast.makeText(v.getContext(), " On CLick one", Toast.LENGTH_SHORT).show();
//
//                        } if (getPosition()==1)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Two", Toast.LENGTH_SHORT).show();
//
//                    } if (getPosition()==2)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Three", Toast.LENGTH_SHORT).show();
//
//                    } if (getPosition()==3)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Fore", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    }
//                });
            }
        }

        public CustomAdapterRecycle(JSONArray data) {
            this.dataSet = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                      int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_schedule_my_item, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView month_id = holder.month_id;
            TextView day_id = holder.day_id;
            TextView year_id = holder.year_id;


            try {
                String[] separated = (dataSet.getString(listPosition).split("T"));

                String  string = dateFormat(separated[0]);


                String[] parts = string.split("/");
                String part1 = parts[0]; // 004
                String part2 = parts[1];
                String part3 = parts[2];
                String input_date=string;
                SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");

                Date dt1= null;
                try {
                    dt1 = format1.parse(input_date);
                    DateFormat format2=new SimpleDateFormat("MMM");
                    String finalDay=format2.format(dt1);
                    month_id.setText(finalDay);
                    day_id.setText(part1);
                    year_id.setText(part3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }






        }

        @Override
        public int getItemCount() {
            return dataSet.length();
        }}



    @Override
    public void onRestart()
    {
        super.onRestart();

        CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterRequestScheduleApi.this,  Globaluse.scheduleList);
        navLV.setAdapter(adapter);
        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);

       // GetscheduleCount();
    }


    public void  GetscheduleCount()
    {
      //  pDialog = ProgressDialog.show(CorporateAfterScheduleApi.this, "", "Please wait...", true,false);

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
                        JSONArray datajsonarray = jsonObject.getJSONArray("data");

//                        for (int i=0;i<datajsonarray.length();i++)
//                        {
//
//
//
//                        }



                        //JSONArray myReferralCode = dataObject.getJSONArray("dishesItems");

                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                // change UI elements here
                               // cartTotalCountText.setText(""+dataObject.length());
//pDialog.dismiss();
                                CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterRequestScheduleApi.this,  datajsonarray);
                                navLV.setAdapter(adapter);
                                FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);
                            }
                        });

//                        CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterScheduleApi.this,  datajsonarray);
//                        navLV.setAdapter(adapter);
//                        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);

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
                  //  pDialog.dismiss();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(CorporateAfterRequestScheduleApi.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporateAfterRequestScheduleApi.this){    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
               // params.put("content-type", "application/json");
                return params;
            }
        };


        ApplicationController.getInstance().addToRequestQueue(stringRequest, "scheduleList");
    }




    private String dateFormat(String date) {
        Log.i(TAG, "serverdate " + date);
       // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        String formattedDate = null;
        try {
            serverDate = df.parse(date);
            //SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            //outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = outputFormat.format(serverDate);
            Log.i(TAG, "converdate" + formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;



    }



    public void removeCart(String comapnyID, String scheduleID) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = APIBaseURL.BASEURLLINK_B2B_SCHEDULE_DELETE+""+comapnyID+"/"+scheduleID;
        Log.d("ReferralDelete",url);
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        Toast.makeText(context, jsonObject.optString("successMessage"), Toast.LENGTH_SHORT).show();

                        GetscheduleCount();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
//                    if (isSuccess) {
//                        Toast.makeText(context, jsonObject.optString("errorMessage"), Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, jsonObject.optString("isSuccess"), Toast.LENGTH_SHORT).show();

//                if (error instanceof AuthFailureError) {
//                    //TODO
////                    ViewGroup viewGroup = ((Activity) context).findViewById(android.R.id.content);
////
////                    //then we will inflate the custom alert dialog xml that we created
////                    View dialogView = LayoutInflater.from(context).inflate(R.layout.access_token_expired_dialog, viewGroup, false);
////
////                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
////                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
////                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);
////
////                    //Now we need an AlertDialog.Builder object
////                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
////
////                    //setting the view of the builder to our custom view that we already inflated
////                    builder.setView(dialogView);
////
////                    //finally creating the alert dialog and displaying it
////                    AlertDialog alertDialog = builder.create();
////
////
////                    buttonOk.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
////                            alertDialog.dismiss();
////                            ConsumerMainActivity.logout();
////
////                        }
////                    });
////
////                    closeBtn.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
////                            alertDialog.dismiss();
////
////                        }
////                    });
////
////                    buttonreset.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
////                            alertDialog.dismiss();
////                        }
////                    });
////                    alertDialog.setCanceledOnTouchOutside(false);
////                    alertDialog.show();
//                } else if (error instanceof NetworkError) {
//                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
//                }
            }
        }, context){    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
               // params.put("content-type", "application/json");
                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "delete_item");
    }







    public void updatejson(int groupPosition, int childPosititon,String updatejsondat) {

        List<String> dishlistdata = new ArrayList<String>();
        for (int m = 0; m < Globaluse._listDataChild.get(Globaluse._listDataHeader.get(groupPosition)).size(); m++) {

            if(childPosititon==m)
            {
                dishlistdata.add(updatejsondat);
            }else
            {
                dishlistdata.add(String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(groupPosition)).get(m)));
            }

        }
        Globaluse._listDataChild.put(Globaluse._listDataHeader.get(groupPosition),dishlistdata);

//        Globaluse._listDataHeader=new ArrayList<String>();
//        Globaluse._listDataChild=new HashMap<String, List<String>>();
        Globaluse._listDataHeader=new ArrayList<String>(Globaluse._listDataHeader);
        Globaluse._listDataChild=new HashMap<String, List<String>>(Globaluse._listDataChild);


//        Globaluse.corporatedishlistnew=new  ArrayList<String>();
//        for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
//        {
//            for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
//            {
//                final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(childText);
//                    if(jsonObject.getBoolean("checkStatus"))
//                    {
//                        JSONObject dishDetail = new JSONObject();
//                        try {
//                            dishDetail.put("dishItemName", jsonObject.getString("dishName"));
//                            dishDetail.put("description", jsonObject.getString("dishDescription"));
//                            dishDetail.put("cost", jsonObject.getString("saleAmount"));
//                            dishDetail.put("noOfUnits", jsonObject.getString("quantity"));
//                            dishDetail.put("dishImage", jsonObject.getString("dishImage"));
//                            dishDetail.put("taxValue", 0);
//                            dishDetail.put("packingCharges", 0);
//                            dishDetail.put("deliveryCharges", 0);
////                            dishDetail.put("buttondisplay", false);
////                            dishDetail.put("quantity", 0);
////                            dishDetail.put("checkStatus", false);
//
//                            dishDetail.put("buttondisplay", jsonObject.getString("buttondisplay"));
//                            dishDetail.put("quantity", jsonObject.getString("quantity"));
//                            dishDetail.put("checkStatus", jsonObject.getBoolean("checkStatus"));
//                            dishDetail.put("checkStatus_schedule", jsonObject.getBoolean("checkStatus_schedule"));
//
//
//                        } catch (JSONException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        Globaluse.corporatedishlistnew.add(String.valueOf(dishDetail));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }



//        finish();
//        overridePendingTransition( 0, 0);
//        startActivity(getIntent());
//        overridePendingTransition( 0, 0);

    }

    
    
}
