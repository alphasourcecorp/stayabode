package stayabode.foodyHive.corporatemenurange;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.applandeo.materialcalendarview.CalendarView;
import stayabode.applandeo.materialcalendarview.EventDay;
import stayabode.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;

import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAdapterRangeForSchedul;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class CorporateScheduleEditFromApi extends AppCompatActivity {




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
    ImageView cartIcon;
    String deliveryType="";
    CheckBox breakfast_chk_id,lunch_chk_id,dinner_chk_id;
    LinearLayout hide_layout_id,show_hide_id;
    Boolean show_hide_id_boo=false;
    Boolean show_hide_id_notes=false;
    LinearLayout notes_id,hide_notes_id;
    ImageView back_id;

    static CheckBox sunday_chk_id,monday_chk_id,tuesday_chk_id,wednesday_chk_id,thursday_chk_id,friday_chk_id,saturday_chk_id;
    JSONArray exclusionDatesArray;

    CorporateExpandableListAdapterRangeForSchedul listAdapter;
    private ProgressDialog pDialog;
    String scheduleID="";

    ImageView addmore_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_schedule);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        GetscheduleCount();
        addmore_id=findViewById(R.id.addmore_id);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();




        arraylist = new ArrayList<HashMap<String, String>>();

        calendarView = findViewById(R.id.calendarView);
        suubmit_id = findViewById(R.id.suubmit_id);
        cartTotalCountText = findViewById(R.id.cartTotalCountText);
        cartIcon = findViewById(R.id.cartIcon);
        breakfast_chk_id = findViewById(R.id.breakfast_chk_id);
        lunch_chk_id = findViewById(R.id.lunch_chk_id);
        dinner_chk_id = findViewById(R.id.dinner_chk_id);
        hide_layout_id = findViewById(R.id.hide_layout_id);
        show_hide_id = findViewById(R.id.show_hide_id);
        notes_id = findViewById(R.id.notes_id);
        hide_notes_id = findViewById(R.id.hide_notes_id);

        back_id = findViewById(R.id.back_id);
        back_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        sunday_chk_id = findViewById(R.id.sunday_chk_id);
        monday_chk_id = findViewById(R.id.monday_chk_id);
        tuesday_chk_id = findViewById(R.id.tuesday_chk_id);
        wednesday_chk_id = findViewById(R.id.wednesday_chk_id);
        thursday_chk_id = findViewById(R.id.thursday_chk_id);
        friday_chk_id = findViewById(R.id.friday_chk_id);
        saturday_chk_id = findViewById(R.id.saturday_chk_id);



        cartTotalCountText.setText(""+ Globaluse.schedule_data_array.size());

        if(show_hide_id_boo)
        {
            hide_layout_id.setVisibility(View.GONE);
        }

        // Calendar calendar = Calendar.getInstance();
        //calendar.set(2020, 3, 19);
//
//
//        try {
//            calendarView.setDate(calendar);
//        } catch (OutOfDateRangeException e) {
//            e.printStackTrace();
//        }
//
//
//        Calendar min = Calendar.getInstance();
//        calendar.set(2021, 3, 6);
//        events.add(new EventDay(calendar, R.drawable.corporate_no_need));
//        calendarView.setMinimumDate(min);
//        events.add(new EventDay(calendar, new Drawable() {
//            @Override
//            public void draw(@NonNull Canvas canvas) {
//
//            }
//
//            @Override
//            public void setAlpha(int alpha) {
//
//            }
//
//            @Override
//            public void setColorFilter(@Nullable ColorFilter colorFilter) {
//
//            }
//
//            @Override
//            public int getOpacity() {
//                return 0;
//            }
//        }));
////or if you want to specify event label color
//        events.add(new EventDay(calendar, R.drawable.corporate_no_need, Color.parseColor("#228B22")));
//        calendarView.setEvents(events);






        //  Calendar calendar = Calendar.getInstance();

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                int  click_date_MILLISECOND_calender = clickedDayCalendar.get(Calendar.MILLISECOND);
                int month = clickedDayCalendar.get((Calendar.MONTH));
                int day = clickedDayCalendar.get(Calendar.DAY_OF_MONTH);
                int YEAR = clickedDayCalendar.get(Calendar.YEAR);
                String month_str="";
                String day_str="";

                if(month < 10){

                    month_str = "0" + month;
                }else
                {
                    month_str=""+ month;
                }
                if(day < 10){

                    day_str  = "0" + day ;
                }else
                {
                    day_str  =""+day ;
                }
                String concateDate=day_str+"/"+month_str+"/"+YEAR;
                //String concateDate=day+"/"+month+"/"+YEAR;

                for (int listPosition=0;listPosition<arraylist.size();listPosition++)
                {
                    String compareDate=  arraylist.get(listPosition).get("calenderDate");
                    if(compareDate.equals(concateDate))
                    {

                        if(arraylist.get(listPosition).get("deliveryStatus").equals("1"))
                        {
                            events.set(Integer.parseInt(arraylist.get(listPosition).get("id")),new EventDay(clickedDayCalendar, R.drawable.corporate_dish_disable2));
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id", String.valueOf(listPosition));
                            map.put("deliveryStatus","3");
                            map.put("calenderDate", compareDate);
                            arraylist.set(listPosition,map);

                        }else
                        {
                            events.set(Integer.parseInt(arraylist.get(listPosition).get("id")),new EventDay(clickedDayCalendar, R.drawable.corporate_dish_book2));
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id", String.valueOf(listPosition));
                            map.put("deliveryStatus","1");
                            map.put("calenderDate", compareDate);
                            arraylist.set(listPosition,map);
                        }

                    }

                }



                // events.add(new EventDay(clickedDayCalendar, R.drawable.corporate_dish_deliver2));
                // events.add(new EventDay(clickedDayCalendar, R.drawable.corporate_dish_book2));
//                events.add(new EventDay(clickedDayCalendar, R.drawable.corporate_dish_disable2));
               /* if(incre==0)
                {
                    events.add(new EventDay(clickedDayCalendar, R.drawable.corporate_dish_book2));

                    incre++;
                }else
                {
                    //events.remove(0);
                   // events.add(new EventDay(clickedDayCalendar, R.drawable.corporate_dish_not_able_deliver));
                    events.add(2,new EventDay(clickedDayCalendar, R.drawable.corporate_dish_not_able_deliver));

                }*/
                //events.remove(2);



                // events.set(0,new EventDay(clickedDayCalendar, R.drawable.corporate_dish_not_able_deliver));


                // events.add(new EventDay(clickedDayCalendar, R.drawable.corporate_dish_book2));
                //events.add(new EventDay(clickedDayCalendar, R.drawable.corporate_dish_book2));
                calendarView.setEvents(events);


//                 Calendar calendar = Calendar.getInstance();
//                //calendar.set(2020, 3, 19);
//                events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                calendarView.setEvents(events);

                //Calendar clickedDayCalendar = eventDay.getCalendar();


            }
        });



        startdate_button_id=findViewById(R.id.startdate_button_id);;
        enddate_button_id=findViewById(R.id.enddate_button_id);;
        start_date_id=(TextView) findViewById(R.id.start_date_id);
        end_date_id=(TextView)findViewById(R.id.end_date_id);

//        start_date_id.setText(Globaluse.startdate);
//        end_date_id.setText(Globaluse.enddate);
        startdate_button_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isWekely=Globaluse.CheckWeeekly(Globaluse.corporatedishlistnew);
                if(isWekely)
                {
                    //Toast.makeText(CorporateScheduleEditFromApi.this, "Only Weekly Selection", Toast.LENGTH_SHORT).show();
                    enddate_button_id.setEnabled(false);
                }
                else
                {
                   // Toast.makeText(CorporateScheduleEditFromApi.this, "Day selection", Toast.LENGTH_SHORT).show();
                    enddate_button_id.setEnabled(true);
                }

                showTruitonDatePickerDialog(v);
            }
        });



        enddate_button_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(start_year==0))
                {
                    showToDatePickerDialog(v);
                }
                else
                {
                    Toast.makeText(CorporateScheduleEditFromApi.this, "Start Date is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        suubmit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dish_dont_want_date="";
                String selected_dish="";
                String Sdate=start_date_id.getText().toString().trim();
                String Edate=end_date_id.getText().toString().trim();
                if(Edate.equals("End Date"))
                {
                    Edate="";
                }
                if(!(Edate.equals("")))
                {

                    if(breakfast_chk_id.isChecked()|lunch_chk_id.isChecked()|dinner_chk_id.isChecked())
                    {

                    JSONArray no_need_deliver_that_day = new JSONArray();
                    for (int listPosition=0;listPosition<arraylist.size();listPosition++) {

                        String deliveryStatus = arraylist.get(listPosition).get("deliveryStatus");
                        if(deliveryStatus.equals("3"))
                        {
                            String currentString = arraylist.get(listPosition).get("calenderDate");
                            String[] separated = currentString.split("/");
                            String date_str=separated[0];
                            int month_str= Integer.parseInt(separated[1])+1;
                            String year_str=separated[2];
                            String fulldate_str=date_str+"/"+month_str+"/"+year_str;

                            no_need_deliver_that_day.put(fulldate_str);

                            dish_dont_want_date+="\n"+fulldate_str;
                        }

                    }

                    ArrayList<HashMap<String, String>> schedule_data = new ArrayList<HashMap<String, String>>();
                    for (int k=0;k<Globaluse.corporatedishlistnew.size();k++) {

                        try {
                            JSONObject jsonObject = new JSONObject(Globaluse.corporatedishlistnew.get(k));
                            if(jsonObject.getBoolean("checkStatus_schedule"))
                            {
                                String fulldate_str=jsonObject.getString("dishItemName");
                                selected_dish+="\n"+fulldate_str;

                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("id", "FH"+k);
                                map.put("dishItemName", jsonObject.getString("dishItemName"));
                                map.put("description", jsonObject.getString("description"));
                                map.put("cost", jsonObject.getString("cost"));
                                map.put("noOfUnits", jsonObject.getString("noOfUnits"));
                                map.put("dishImage", jsonObject.getString("dishImage"));
                                map.put("taxValue", jsonObject.getString("taxValue"));
                                map.put("packingCharges", jsonObject.getString("packingCharges"));
                                map.put("deliveryCharges", jsonObject.getString("deliveryCharges"));
                                map.put("buttondisplay", jsonObject.getString("buttondisplay"));
                                map.put("quantity", jsonObject.getString("quantity"));
                                map.put("checkStatus", String.valueOf(jsonObject.getBoolean("checkStatus")));
                                map.put("checkStatus_schedule", String.valueOf(jsonObject.getBoolean("checkStatus_schedule")));
                                map.put("startDate", Sdate);
                                map.put("endDate", Edate);
                                map.put("deliveryType", deliveryType);//1-lunch,2-dinner,3-both
                                map.put("breakfast",""+breakfast_chk_id.isChecked());
                                map.put("lunch", ""+lunch_chk_id.isChecked());
                                map.put("dinner",""+dinner_chk_id.isChecked());
                                map.put("noNeedDeliver", String.valueOf(no_need_deliver_that_day));

                                schedule_data.add(map);









                            }else
                            {
                                //  Toast.makeText(CorporateSchedule.this, "Please Check any one dish", Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //  Toast.makeText(CorporateSchedule.this, "Start Date :"+Sdate + "\n" +"End Date :"+Edate+ "\n"+"Don't want dish : "+dish_dont_want_date+"\n"+"Selected dish : "+selected_dish, Toast.LENGTH_SHORT).show();


                    }

                    if(!(schedule_data.size()==0))
                    {


                        final Dialog dialog = new Dialog(CorporateScheduleEditFromApi.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.corporate_schedule_popup);

                        ImageView close_id = (ImageView) dialog.findViewById(R.id.close_id);
                        ListView  navLVp = (ListView) dialog.findViewById(R.id.navLV);
                        RecyclerView  horizontal_recycler_view = (RecyclerView) dialog.findViewById(R.id.my_recycler_view);
                        TextView  start_date_id = (TextView) dialog.findViewById(R.id.start_date_id);
                        TextView  end_date_id = (TextView) dialog.findViewById(R.id.end_date_id);
                        TextView  breakfast_id = (TextView) dialog.findViewById(R.id.breakfast_id);
                        TextView  lunch_id = (TextView) dialog.findViewById(R.id.lunch_id);
                        TextView  dinner_id = (TextView) dialog.findViewById(R.id.dinner_id);
                        View  viewid1 = (View) dialog.findViewById(R.id.viewid1);
                        View  viewid2 = (View) dialog.findViewById(R.id.viewid2);
                        Button  suubmit_id = (Button) dialog.findViewById(R.id.suubmit_id);
                        start_date_id.setText(Sdate);
                        end_date_id.setText(Edate);

                        if(breakfast_chk_id.isChecked())
                        {
                            breakfast_id.setVisibility(View.VISIBLE);
                            viewid1.setVisibility(View.VISIBLE);
                        }else
                        {
                            breakfast_id.setVisibility(View.GONE);
                            viewid1.setVisibility(View.GONE);
                        }
                        if(lunch_chk_id.isChecked())
                        {
                            lunch_id.setVisibility(View.VISIBLE);
                            viewid2.setVisibility(View.VISIBLE);
                        }else
                        {
                            lunch_id.setVisibility(View.GONE);
                            viewid2.setVisibility(View.GONE);
                        }
                        if(dinner_chk_id.isChecked())
                        {
                            dinner_id.setVisibility(View.VISIBLE);
                        }else
                        {
                            dinner_id.setVisibility(View.GONE);
                        }


                        ArrayList<String> listdata = new ArrayList<String>();
                       // JSONArray jArray = (JSONArray)jsonObject;
                        if (no_need_deliver_that_day != null) {
                            for (int i=0;i<no_need_deliver_that_day.length();i++){
                                try {

                                    listdata.add(no_need_deliver_that_day.getString(i));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                       // ArrayList<String> horizontalList;
                        CustomAdapterRecycle horizontalAdapter;
                        horizontalAdapter=new CustomAdapterRecycle(listdata);
                        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(CorporateScheduleEditFromApi.this, LinearLayoutManager.HORIZONTAL, false);
                        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                        horizontal_recycler_view.setAdapter(horizontalAdapter);


                        CustomAdapter2 adapter = new CustomAdapter2(CorporateScheduleEditFromApi.this,  schedule_data);
                        navLVp.setAdapter(adapter);
                        FulllistItemdisplay.setListViewHeightBasedOnItems(navLVp);

                        close_id.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        String finalEdate = Edate;
                        String finalEdate1 = Edate;
                        suubmit_id.setText("UPDATE schedule");

                        suubmit_id.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                Globaluse.schedule_data_array.add(schedule_data);
                                pDialog = ProgressDialog.show(CorporateScheduleEditFromApi.this, "", "Please wait...", true,false);

                                Addschedule();

                            }
                        });

                        dialog.show();




                    }
                    }else
                    {
                        Toast.makeText(CorporateScheduleEditFromApi.this, "Please select  meals type", Toast.LENGTH_SHORT).show();
                    }



                }else
                {
                    Toast.makeText(CorporateScheduleEditFromApi.this, "Please select  date", Toast.LENGTH_SHORT).show();

                }





            }
        });

        navLV = findViewById(R.id.navLV);









        for (int k=0;k<Globaluse.corporatedishlistnew.size();k++) {
            try {
                JSONObject dishDetail = new JSONObject();
                JSONObject jsonObject = new JSONObject(Globaluse.corporatedishlistnew.get(k));
                if(jsonObject.getBoolean("checkStatus_schedule"))
                {
                    String dishItemName=jsonObject.getString("dishItemName");
                    for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
                    {
                        for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
                        {
                            final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
                            JSONObject jsonObject1 = null;
                            try {
                                jsonObject1 = new JSONObject(childText);
                                if(jsonObject1.getString("dishName").equals(dishItemName))
                                {
                                    jsonObject1.put("checkStatus_schedule", true);

//                                            if(checkStatus_id.isChecked())
//                                            {
//                                                jsonObject.put("checkStatus_schedule", true);
//                                            }else
//                                            {
//                                                jsonObject.put("checkStatus_schedule", false);
//                                            }
                                    //  updatejson(c,t,String.valueOf(jsonObject1));


                                    List<String> dishlistdata = new ArrayList<String>();
                                    for (int m = 0; m < Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size(); m++) {

                                        if(t==m)
                                        {
                                            dishlistdata.add(String.valueOf(jsonObject1));
                                        }else
                                        {
                                            dishlistdata.add(String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(m)));
                                        }

                                    }
                                    Globaluse._listDataChild.put(Globaluse._listDataHeader.get(c),dishlistdata);
                                    Globaluse._listDataHeader=new ArrayList<String>(Globaluse._listDataHeader);
                                    Globaluse._listDataChild=new HashMap<String, List<String>>(Globaluse._listDataChild);

                                    //}
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else
                {
                    //  Toast.makeText(CorporateSchedule.this, "Please Check any one dish", Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        Globaluse.corporatedishlistnew=new  ArrayList<String>();
        for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
        {
            for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
            {
                final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
                JSONObject jsonObject11 = null;
                try {
                    jsonObject11 = new JSONObject(childText);
                    if(jsonObject11.getBoolean("checkStatus"))
                    {
                        JSONObject dishDetail11 = new JSONObject();
                        try {
                            dishDetail11.put("dishItemName", jsonObject11.getString("dishName"));
                            dishDetail11.put("description", jsonObject11.getString("dishDescription"));
                            dishDetail11.put("cost", jsonObject11.getString("saleAmount"));
                            dishDetail11.put("noOfUnits", jsonObject11.getString("quantity"));
                            dishDetail11.put("dishImage", jsonObject11.getString("dishImage"));
                            dishDetail11.put("taxValue", 0);
                            dishDetail11.put("packingCharges", 0);
                            dishDetail11.put("deliveryCharges", 0);
//                            dishDetail.put("buttondisplay", false);
//                            dishDetail.put("quantity", 0);
//                            dishDetail.put("checkStatus", false);

                            dishDetail11.put("buttondisplay", jsonObject11.getString("buttondisplay"));
                            dishDetail11.put("quantity", jsonObject11.getString("quantity"));
                            dishDetail11.put("checkStatus", jsonObject11.getBoolean("checkStatus"));
                            dishDetail11.put("checkStatus_schedule", jsonObject11.getBoolean("checkStatus_schedule"));


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Globaluse.corporatedishlistnew.add(String.valueOf(dishDetail11));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        CustomAdapter adapter = new CustomAdapter(CorporateScheduleEditFromApi.this,  Globaluse.corporatedishlistnew);
        navLV.setAdapter(adapter);




//        CustomAdapter adapter = new CustomAdapter(this,  Globaluse.corporatedishlistnew);
//        navLV.setAdapter(adapter);
        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);

        //calendarView.setMaximumDate(max);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CorporateScheduleEditFromApi.this, CorporateAfterScheduleApi.class);
                startActivity(intent);
            }
        });

        addmore_id.setVisibility(View.VISIBLE);
        addmore_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Globaluse.fromscheduleEdit="yes";
                Intent intent = new Intent(CorporateScheduleEditFromApi.this, CorporateAddmoredish.class);
                startActivity(intent);
            }
        });



        breakfast_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               // Globaluse.brekfast=isChecked;

//                if((dinner_chk_id.isChecked())&&(lunch_chk_id.isChecked()))
//                {
//                    deliveryType="3";
//
//                }else
//                {
//                    if(lunch_chk_id.isChecked())
//                    {
//                        deliveryType="1";
//                    }
//                    else
//                    {
//                        if(dinner_chk_id.isChecked())
//                        {
//                            deliveryType="2";
//                        }else
//                        {
//                            deliveryType="";
//                        }
//
//                    }
//                }


                Toast.makeText(CorporateScheduleEditFromApi.this,"Delivery type :"+deliveryType,Toast.LENGTH_SHORT).show();

            }
        });





        dinner_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


               // Globaluse.dinner=isChecked;


//               if((dinner_chk_id.isChecked())&&(lunch_chk_id.isChecked()))
//               {
//                   deliveryType="3";
//
//               }else
//               {
//                   if(lunch_chk_id.isChecked())
//                   {
//                       deliveryType="1";
//                   }
//                   else
//                   {
//                       if(dinner_chk_id.isChecked())
//                       {
//                           deliveryType="2";
//                       }else
//                       {
//                           deliveryType="";
//                       }
//
//                   }
//               }


                Toast.makeText(CorporateScheduleEditFromApi.this,"Delivery type :"+deliveryType,Toast.LENGTH_SHORT).show();

            }
        });


        lunch_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               // Globaluse.lunch=isChecked;


//                if((dinner_chk_id.isChecked())&&(lunch_chk_id.isChecked()))
//                {
//                    deliveryType="3";
//
//                }else
//                {
//                    if(lunch_chk_id.isChecked())
//                    {
//                        deliveryType="1";
//                    }
//                    else
//                    {
//                        if(dinner_chk_id.isChecked())
//                        {
//                            deliveryType="2";
//                        }else
//                        {
//                            deliveryType="";
//                        }
//
//                    }
//                }


                Toast.makeText(CorporateScheduleEditFromApi.this,"Delivery type :"+deliveryType,Toast.LENGTH_SHORT).show();

            }
        });

      //  static CheckBox sunday_chk_id,monday_chk_id,tuesday_chk_id,wednesday_chk_id,thursday_chk_id,friday_chk_id,saturday_chk_id;


//        sunday_chk_id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

        sunday_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEvents();
            }
        });


        monday_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEvents();
            }
        });
        tuesday_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEvents();
            }
        });
        wednesday_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEvents();
            }
        });
        thursday_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEvents();
            }
        });
        friday_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEvents();
            }
        });
        saturday_chk_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEvents();
            }
        });


//        breakfast_chk_id.setChecked(Globaluse.brekfast);
//        lunch_chk_id.setChecked(Globaluse.lunch);
//        dinner_chk_id.setChecked(Globaluse.dinner);


        show_hide_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide_layout_id.setVisibility(View.GONE);

                if(show_hide_id_boo)
                {
                    hide_layout_id.setVisibility(View.GONE);
                    show_hide_id_boo=false;
                }else
                {
                    hide_layout_id.setVisibility(View.VISIBLE);
                    show_hide_id_boo=true;
                }
            }
        });


        notes_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(show_hide_id_notes)
                {
                    show_hide_id_notes=false;
                    hide_notes_id.setVisibility(View.GONE);
                }else
                {
                    show_hide_id_notes=true;
                    hide_notes_id.setVisibility(View.VISIBLE);
                }

            }
        });





        if(b!=null)
        {
            String sdate_str =(String) b.get("sdate");
            String endate_str =(String) b.get("endate");
            Boolean isBreakfast_str =(Boolean) b.get("isBreakfast");
            Boolean isLunch_str =(Boolean) b.get("isLunch");
            Boolean isDinner_str =(Boolean) b.get("isDinner");
            String exclusionWeeks_str =((String) b.get("exclusionWeeks"));
            String exclusionDates_str =((String) b.get("exclusionDates"));
             scheduleID =((String) b.get("scheduleID"));
            String companyID =((String) b.get("companyID"));
            try {

                sunday_chk_id.setChecked(false);
                monday_chk_id.setChecked(false);
                tuesday_chk_id.setChecked(false);
                wednesday_chk_id.setChecked(false);
                thursday_chk_id.setChecked(false);
                friday_chk_id.setChecked(false);
                saturday_chk_id.setChecked(false);


                JSONArray jsonArray = new JSONArray(exclusionWeeks_str);
                 exclusionDatesArray = new JSONArray(exclusionDates_str);

                for (int r=0;r<jsonArray.length();r++)
                {
                    if(jsonArray.getString(r).equals("0"))
                    {
                        sunday_chk_id.setChecked(true);
                    }else if(jsonArray.getString(r).equals("1"))
                    {
                        monday_chk_id.setChecked(true);

                    }else if(jsonArray.getString(r).equals("2"))
                    {
                        tuesday_chk_id.setChecked(true);
                    }else if(jsonArray.getString(r).equals("3"))
                    {
                        wednesday_chk_id.setChecked(true);
                    } else if(jsonArray.getString(r).equals("4"))
                    {
                        thursday_chk_id.setChecked(true);
                    }else if(jsonArray.getString(r).equals("5"))
                    {
                        friday_chk_id.setChecked(true);
                    }else if(jsonArray.getString(r).equals("6"))
                    {
                        saturday_chk_id.setChecked(true);
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //JsonArray exclusionWeeks=exclusionWeeks_str;

            start_date_id.setText(sdate_str);
            end_date_id.setText(endate_str);
            breakfast_chk_id.setChecked(isBreakfast_str);
            lunch_chk_id.setChecked(isLunch_str);
            dinner_chk_id.setChecked(isDinner_str);

            String startD[] = sdate_str.split("/");
            String endD[] = endate_str.split("/");

            start_year= Integer.parseInt(startD[2]);
            start_month=((Integer.parseInt(startD[1])));
            start_day=Integer.parseInt(startD[0]);


            end_year= Integer.parseInt(endD[2]);
            end_month=((Integer.parseInt(endD[1])));
            end_day=Integer.parseInt(endD[0]);

            setEventsFromApi();

        }



    }




    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new ToDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year, month,day);
           // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

            return datePickerDialog;
        }



        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

//            Globaluse.s_year=year;
//            Globaluse.s_month=month;
//            Globaluse.s_day=day;

            start_date_id.setText(day + "/" + ( month+1) + "/" + year);
            //start_date_id.setText(Globaluse.s_day + "/" + (Globaluse.s_month+1) + "/" + Globaluse.s_year);
           // Globaluse.startdate=start_date_id.getText().toString().trim();


//             Calendar calendar = Calendar.getInstance();
//             calendar.set(year, month, day-1);
//             events.add(new EventDay(calendar, R.drawable.corporate_dish_not_able_deliver));
            start_year=year;
            start_month=(month+1);
            start_day=day;
            min.set(year, month, day-1);
            calendarView.setMinimumDate(min);
            //calendarView.setEvents(events);

            boolean isWekely=Globaluse.CheckWeeekly(Globaluse.corporatedishlistnew);
            if(isWekely)
            {
                // Toast.makeText(CorporateScheduleFromApi.this, "Only Weekly Selection", Toast.LENGTH_SHORT).show();

                final Calendar calendar2 = Calendar.getInstance();
                calendar2.set(Calendar.MONTH, (month));
                calendar2.set(Calendar.DAY_OF_MONTH, day);
                calendar2.add(Calendar.DATE, 5);
                calendar2.set(Calendar.YEAR, year);



                //int endday=(day+6);
                end_date_id.setText(calendar2.get(Calendar.DAY_OF_MONTH) + "/" + ((calendar2.get(Calendar.MONTH))+1) + "/" + calendar2.get(Calendar.YEAR));
                end_day=calendar2.get(Calendar.DAY_OF_MONTH);
                end_month=((calendar2.get(Calendar.MONTH))+1);
                end_year=calendar2.get(Calendar.YEAR);
                //max.set(end_day, calendar2.get(Calendar.MONTH), end_year);
                events = new ArrayList<>();
                //calendarView.setMaximumDate(max);
                setEvents();

//                end_date_id.setText(day + "/" + (month+1) + "/" + year);
//                // Globaluse.enddate=end_date_id.getText().toString().trim();
//
//                end_day=day;
//                end_month=(month+1);
//                end_year=year;
//
//                max.set(year, month, day);
//                events = new ArrayList<>();
//
//                calendarView.setMaximumDate(max);

            }
            else
            {

                // Toast.makeText(CorporateScheduleFromApi.this, "Day selection", Toast.LENGTH_SHORT).show();
                setEvents();
            }

        }
    }

    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        // Calendar startDateCalendar=Calendar.getInstance();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            String getfromdate = start_date_id.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year, month, day;
            year = Integer.parseInt(getfrom[2]);
            month = (Integer.parseInt(getfrom[1]))-1;
            day = (Integer.parseInt(getfrom[0]));
//            if(Globaluse.enddate.equals("End Date"))
//            {
//                year = Integer.parseInt(getfrom[2]);
//                month = (Integer.parseInt(getfrom[1]))-1;
//                day = (Integer.parseInt(getfrom[0]));
//            }else
//            {
//                year = Integer.parseInt(getfrom[2]);
//                month = (Integer.parseInt(getfrom[1]))-1;
//                day = (Integer.parseInt(getfrom[0]));
//            }



            final Calendar c = Calendar.getInstance();
           // c.set(year, month, day + 1);
            c.set(year, month, day);
           // c.set(Globaluse.e_year, Globaluse.e_month, Globaluse.e_day);
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog2.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog2;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {



//            Globaluse.e_year=year;
//            Globaluse.e_month=month;
//            Globaluse.e_day=day;

            //end_date_id.setText(Globaluse.e_day + "/" + (Globaluse.e_month+1) + "/" + Globaluse.e_year);
            end_date_id.setText(day + "/" + (month+1) + "/" + year);
           // Globaluse.enddate=end_date_id.getText().toString().trim();

            end_day=day;
            end_month=(month+1);
            end_year=year;

            max.set(year, month, day);
            events = new ArrayList<>();

            calendarView.setMaximumDate(max);
            setEvents();

//            // calendarView.setEvents(events);
//            //calendarView.setEvents(events);
//            List<Date> dates =getDates(start_year+"-"+""+start_month+"-"+""+(start_day), end_year+"-"+""+end_month+"-"+""+end_day);
//
//            arraylist = new ArrayList<HashMap<String, String>>();
//
//            //for(Date date2:dates)
//            if(!(start_year==0)) {
//                for (int i = 0; i < dates.size(); i++) {
//                    String formateyear = new SimpleDateFormat("yyyy").format(dates.get(i));
//                    String formatedate = new SimpleDateFormat("dd").format(dates.get(i));
//                    int formatemonth = Integer.parseInt(new SimpleDateFormat("MM").format(dates.get(i))) - 1;
//                    //Log.v("output date ",formateDate);
//
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(Calendar.YEAR, Integer.parseInt(formateyear));
//                    calendar.set(Calendar.MONTH, Integer.parseInt(String.valueOf((formatemonth))));
//                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(formatedate));
//                    System.out.println(dates.get(i));
//
//
//                    String month_str = "";
//                    String day_str = "";
//
//                    if (formatemonth < 10) {
//
//                        month_str = "0" + formatemonth;
//                    } else {
//                        month_str = "" + formatemonth;
//                    }
////
////                int formatedate_int= Integer.parseInt(formatedate);
////                if(formatedate_int < 10){
////
////                    day_str  = "0" + formatedate ;
////                }else
////                {
////                    day_str  =""+formatedate ;
////                }
////
////
////                String concateDate=day_str+"/"+month_str+"/"+formateyear;
//
//                    //String concateDate=formatedate+"/"+formatemonth+"/"+formateyear;
//                    String concateDate = formatedate + "/" + month_str + "/" + formateyear;
//
//                    //String concateDate=formatedate+"/"+formatemonth+"/"+formateyear;
//
//                    // int  End_date_MILLISECOND_calender = calendar.get(Calendar.MILLISECOND);
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put("id", String.valueOf(i));
//                    map.put("deliveryStatus", "1");
//                    map.put("calenderDate", concateDate);
//                    arraylist.add(map);
//
//
//                    events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//
//
//// access date fields
////                int year1 = date.getYear(); // 2010
////                int day1 = date.getDay(); // 2
////                Month  month1= Month.of(date.getMonth()); // JANUARY
////                int monthAsInt = month1.getValue();
//                    // Calendar calendar2 = Calendar.getInstance();
////                calendar2.set(year1, monthAsInt, day1);
//                    //calendar2.set(2021, 3, 19);
//
//
//                }
//            }
//            calendarView.setEvents(events);
//            //events.add(new EventDay(date, R.drawable.corporate_dish_not_able_deliver));


        }
    }




    class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> items;

        public CustomAdapter(Context context, ArrayList<String> items) {
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
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_schedulw_list_row, null);

            ImageView deleterecord = v.findViewById(R.id.deleterecord);
            TextView dish_id = v.findViewById(R.id.dish_id);
            TextView dish_multipleprice = v.findViewById(R.id.dish_multipleprice);
            ImageView img_id = v.findViewById(R.id.img_id);
            CheckBox checkStatus_id = v.findViewById(R.id.checkStatus_id);
            LinearLayout inc_dec_id = (LinearLayout) v.findViewById(R.id.inc_dec_id);
            LinearLayout add_linear_id = (LinearLayout) v.findViewById(R.id.add_linear_id);
            Button addbutton_id = (Button) v.findViewById(R.id.addbutton_id);
            EditText quantity_id = (EditText) v.findViewById(R.id.quantity_id);
            ImageView minus_id = (ImageView) v.findViewById(R.id.minus_id);
            ImageView plus_id = (ImageView) v.findViewById(R.id.plus_id);


            //expListView.setSelection(1);
            //notifyDataSetChanged();
            try {
                JSONObject jsonObject = new JSONObject(items.get(i));

                // dish_id.setText(jsonObject.getString("noOfUnits")+"X "+jsonObject.getString("dishItemName"));
                dish_id.setText(jsonObject.getString("dishItemName"));

                Glide.with(CorporateScheduleEditFromApi.this).load(jsonObject.getString("dishImage")).into(img_id);

                double noofUnits= Integer.parseInt(jsonObject.getString("noOfUnits"));
                double cost= Double.parseDouble(jsonObject.getString("cost"));

                double totalvalue=(cost * noofUnits);

                dish_multipleprice.setText(jsonObject.getString("noOfUnits")+"X "+jsonObject.getString("dishItemName"));

//                if(totalvalue==0.0)
//                {
//                    dish_multipleprice.setText("Rs. "+" TBD ");
//                }else
//                {
//                    dish_multipleprice.setText("Rs. "+totalvalue);
//                }
                // dish_multipleprice.setText("Rs. "+totalvalue);

               // dish_multipleprice.setText("Rs. "+cost);

                if(jsonObject.getString("cost").equals("0"))
                {
                    //price_id.setText("Rs. "+" TBD ");
                    dish_multipleprice.setText("₹ "+" TBD ");
                }else
                {
                    dish_multipleprice.setText("₹ "+jsonObject.getString("cost")+"/-");
                }
                quantity_id.setText(jsonObject.getString("noOfUnits"));



                checkStatus_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {
                            JSONObject jsonObject_old = null;
                            jsonObject_old = new JSONObject(items.get(i));
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



                                            if(checkStatus_id.isChecked())
                                            {
                                                jsonObject.put("checkStatus_schedule", true);
                                            }else
                                            {
                                                jsonObject.put("checkStatus_schedule", false);
                                            }


                                            updatejson(c,t,String.valueOf(jsonObject));
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
                });




//                checkStatus_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
//
//
//                            try {
//                                JSONObject jsonObject_old = null;
//                                jsonObject_old = new JSONObject(items.get(i));
//                                String dishItemName=jsonObject_old.getString("dishItemName");
//                                for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
//                                {
//                                    for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
//                                    {
//                                        final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
//                                        JSONObject jsonObject = null;
//                                        try {
//                                            jsonObject = new JSONObject(childText);
//
//                                            if(jsonObject.getString("dishName").equals(dishItemName))
//                                            {
//                                                //if(jsonObject.getString("saleAmount").equals(dishItemName))
//                                                //{
//                                                try {
//
//                                                    if(isChecked)
//                                                    {
//                                                        jsonObject.put("checkStatus_schedule", true);
//
//                                                    }else
//                                                    {
//                                                        jsonObject.put("checkStatus_schedule", false);
//                                                    }
//
//                                                    //updatejson(c,t,String.valueOf(jsonObject));
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//
//                                                //}
//
//                                            }
//
//
//
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//
//
//                    }
//                });




                if(jsonObject.getBoolean("checkStatus_schedule"))
                {
                    checkStatus_id.setChecked(true);
                }
                else {
                    checkStatus_id.setChecked(false);
                }

                if(jsonObject.getBoolean("buttondisplay"))
                {
                    inc_dec_id.setVisibility(View.VISIBLE);
                    add_linear_id.setVisibility(View.GONE);

                }
                else {

                    inc_dec_id.setVisibility(View.GONE);
                    add_linear_id.setVisibility(View.VISIBLE);

                }


                minus_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int qut= Integer.parseInt(quantity_id.getText().toString())- 1;
                        quantity_id.setText(""+qut);

                        try {
                            JSONObject jsonObject_old = null;
                            jsonObject_old = new JSONObject(items.get(i));
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
                                                if(qut==0)
                                                {
                                                    jsonObject.put("buttondisplay", false);
                                                    jsonObject.put("checkStatus", false);
                                                }
                                                jsonObject.put("quantity", qut);
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
                });

                plus_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int qut= Integer.parseInt(quantity_id.getText().toString())+ 1;
                        quantity_id.setText(""+qut);

                        try {

                            JSONObject jsonObject_old = null;
                            jsonObject_old = new JSONObject(items.get(i));
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
                                                jsonObject.put("quantity", qut);
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

//                        try {
//                            jsonObject.put("quantity", qut);
//                            updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }


                    }
                });

                addbutton_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            JSONObject jsonObject_old = null;
                            jsonObject_old = new JSONObject(items.get(i));
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
                                                jsonObject.put("buttondisplay", true);
                                                jsonObject.put("quantity", 1);
                                                jsonObject.put("checkStatus", true);
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
                        inc_dec_id.setVisibility(View.VISIBLE);
                        add_linear_id.setVisibility(View.GONE);

                    }
                });


            }catch (JSONException err){
                Log.d("Error", err.toString());
            }


            deleterecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    JSONObject jsonObject_old = null;
                    try {
                        jsonObject_old = new JSONObject(items.get(i));
                        String dishItemName=jsonObject_old.getString("dishItemName");
                        String cost=jsonObject_old.getString("cost");





                        Globaluse.corporatedishlistnew=new  ArrayList<String>();
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
                                            jsonObject.put("buttondisplay", false);
                                            jsonObject.put("quantity", 0);
                                            jsonObject.put("checkStatus", false);
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
            });

            //text.setText(items.get(i));
            //event handling code can be done here
            return v;
        }
    }






    class CustomAdapter2 extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String, String>> items;

        public CustomAdapter2(Context context, ArrayList<HashMap<String, String>> items) {
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
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_schedule_list_row_popup, null);
            TextView dish_multipleprice = v.findViewById(R.id.dish_multipleprice);
            TextView dish_id = v.findViewById(R.id.dish_id);
            ImageView img_id = v.findViewById(R.id.img_id);
            try {
                JSONObject jsonObject = new JSONObject(items.get(i));


                Glide.with(CorporateScheduleEditFromApi.this).load(items.get(i).get("dishImage")).into(img_id);


                if(jsonObject.getString("cost").equals("0"))
                {
                    //price_id.setText("Rs. "+" TBD ");
                    dish_multipleprice.setText("₹ "+" TBD ");
                }else
                {
                    dish_multipleprice.setText("₹ "+jsonObject.getString("cost")+"/-");
                }

                //dish_multipleprice.setText("Rs. "+jsonObject.getString("cost"));


                dish_id.setText(items.get(i).get("noOfUnits")+" X "+jsonObject.getString("dishItemName"));


            }catch (JSONException err){
                Log.d("Error", err.toString());
            }

            return v;
        }
    }





    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());


            cal1.add(Calendar.DATE, 1);
        }
        return dates;
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
                        JSONObject dishDetail = new JSONObject();
                        try {
                            dishDetail.put("dishItemName", jsonObject.getString("dishName"));
                            dishDetail.put("description", jsonObject.getString("dishDescription"));
                            dishDetail.put("cost", jsonObject.getString("saleAmount"));
                            dishDetail.put("noOfUnits", jsonObject.getString("quantity"));
                            dishDetail.put("dishImage", jsonObject.getString("dishImage"));
                            dishDetail.put("taxValue", 0);
                            dishDetail.put("packingCharges", 0);
                            dishDetail.put("deliveryCharges", 0);
//                            dishDetail.put("buttondisplay", false);
//                            dishDetail.put("quantity", 0);
//                            dishDetail.put("checkStatus", false);

                            dishDetail.put("buttondisplay", jsonObject.getString("buttondisplay"));
                            dishDetail.put("quantity", jsonObject.getString("quantity"));
                            dishDetail.put("checkStatus", jsonObject.getBoolean("checkStatus"));
                            dishDetail.put("checkStatus_schedule", jsonObject.getBoolean("checkStatus_schedule"));


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

        CustomAdapter adapter = new CustomAdapter(this,  Globaluse.corporatedishlistnew);
        navLV.setAdapter(adapter);

//        finish();
//        overridePendingTransition( 0, 0);
//        startActivity(getIntent());
//        overridePendingTransition( 0, 0);

    }




   static public  void setEvents()
    {

        events = new ArrayList<>();
        List<Date> dates =getDates(start_year+"-"+""+start_month+"-"+""+(start_day), end_year+"-"+""+end_month+"-"+""+end_day);
        arraylist = new ArrayList<HashMap<String, String>>();
        if(!(end_year==0))
        {
            for(int i=0;i<dates.size();i++)
            {
                String formateyear = new SimpleDateFormat("yyyy").format(dates.get(i));
                String formatedate = new SimpleDateFormat("dd").format(dates.get(i));
                int formatemonth = Integer.parseInt(new SimpleDateFormat("MM").format(dates.get(i)))-1;
                //Log.v("output date ",formateDate);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, Integer.parseInt(formateyear));
                calendar.set(Calendar.MONTH, Integer.parseInt(String.valueOf((formatemonth))));
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(formatedate));
                System.out.println(dates.get(i));

                String month_str="";
                String day_str="";

                if(formatemonth < 10){

                    month_str = "0" + formatemonth;
                }else
                {
                    month_str=""+ formatemonth;
                }
//
//                    int formatedate_int= Integer.parseInt(formatedate);
//                    if(formatedate_int < 10){
//
//                        day_str  = "0" + formatedate ;
//                    }else
//                    {
//                        day_str  =""+formatedate ;
//                    }

                //  String concateDate=day_str+"/"+month_str+"/"+formateyear;
                // String concateDate=formatedate+"/"+formatemonth+"/"+formateyear;
                String concateDate=formatedate+"/"+month_str+"/"+formateyear;

                // int  End_date_MILLISECOND_calender = calendar.get(Calendar.MILLISECOND);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(i));
                // CheckBox sunday_chk_id = null,monday_chk_id,tuesday_chk_id,wednesday_chk_id,thursday_chk_id,friday_chk_id,saturday_chk_id;

                int day2 = calendar.get(Calendar.DAY_OF_WEEK);
                switch (day2) {
                    case Calendar.SUNDAY:
                        // Current day is Sunday
                        if(sunday_chk_id.isChecked())
                        {

                            map.put("deliveryStatus","1");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                            calendarView.setEvents(events);

                        }else
                        {
                            map.put("deliveryStatus","3");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
                            calendarView.setEvents(events);
                        }

                        break;
                    case Calendar.MONDAY:
                        // Current day is Monday
                        if(monday_chk_id.isChecked())
                        {

                            map.put("deliveryStatus","1");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                            calendarView.setEvents(events);

                        }else
                        {
                            map.put("deliveryStatus","3");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
                            calendarView.setEvents(events);
                        }

                        break;
                    case Calendar.TUESDAY:
                        // etc.
                        if(tuesday_chk_id.isChecked())
                        {

                            map.put("deliveryStatus","1");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                            calendarView.setEvents(events);

                        }else
                        {
                            map.put("deliveryStatus","3");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
                            calendarView.setEvents(events);
                        }

                        break;
                    case Calendar.WEDNESDAY:

                        if(wednesday_chk_id.isChecked())
                        {

                            map.put("deliveryStatus","1");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                            calendarView.setEvents(events);

                        }else
                        {
                            map.put("deliveryStatus","3");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
                            calendarView.setEvents(events);
                        }
                        // etc.
                        break;
                    case Calendar.THURSDAY:

                        if(thursday_chk_id.isChecked())
                        {

                            map.put("deliveryStatus","1");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                            calendarView.setEvents(events);

                        }else
                        {
                            map.put("deliveryStatus","3");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
                            calendarView.setEvents(events);
                        }

                        // etc.
                        break;
                    case Calendar.FRIDAY:

                        if(friday_chk_id.isChecked())
                        {

                            map.put("deliveryStatus","1");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                            calendarView.setEvents(events);

                        }else
                        {
                            map.put("deliveryStatus","3");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
                            calendarView.setEvents(events);
                        }

                        // etc.
                        break;
                    case Calendar.SATURDAY:

                        if(saturday_chk_id.isChecked())
                        {

                            map.put("deliveryStatus","1");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                            calendarView.setEvents(events);

                        }else
                        {
                            map.put("deliveryStatus","3");
                            map.put("calenderDate", concateDate);
                            arraylist.add(map);
                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
                            calendarView.setEvents(events);
                        }

                        // etc.
                        break;
                }

//                    map.put("deliveryStatus","1");
//                    map.put("calenderDate", concateDate);
//                    arraylist.add(map);
//                    events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                    calendarView.setEvents(events);

// access date fields
//                int year1 = date.getYear(); // 2010
//                int day1 = date.getDay(); // 2
//                Month  month1= Month.of(date.getMonth()); // JANUARY
//                int monthAsInt = month1.getValue();
                // Calendar calendar2 = Calendar.getInstance();
//                calendar2.set(year1, monthAsInt, day1);
                //calendar2.set(2021, 3, 19);


            }
        }
        else
        {
            calendarView.setEvents(events);
        }

    }











    public  void setEventsFromApi()
    {

        events = new ArrayList<>();
        List<Date> dates =getDates(start_year+"-"+""+start_month+"-"+""+(start_day), end_year+"-"+""+end_month+"-"+""+end_day);
        arraylist = new ArrayList<HashMap<String, String>>();
        if(!(end_year==0))
        {
            for(int i=0;i<dates.size();i++)
            {
                String formateyear = new SimpleDateFormat("yyyy").format(dates.get(i));
                String formatedate = new SimpleDateFormat("dd").format(dates.get(i));
                int formatemonth = Integer.parseInt(new SimpleDateFormat("MM").format(dates.get(i)))-1;
                //Log.v("output date ",formateDate);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, Integer.parseInt(formateyear));
                calendar.set(Calendar.MONTH, Integer.parseInt(String.valueOf((formatemonth))));
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(formatedate));
                System.out.println(dates.get(i));

                String month_str="";
                String day_str="";

                if(formatemonth < 10){

                    month_str = "0" + formatemonth;
                }else
                {
                    month_str=""+ formatemonth;
                }

                int monthint= (Integer.parseInt(month_str)+1);
                String month_strn="";
                if(monthint < 10){

                    month_strn = "0" + monthint;
                }else
                {
                    month_strn=""+ monthint;
                }

                String concateDate=formatedate+"/"+month_str+"/"+formateyear;
                String newconcateDate=formatedate+"/"+month_strn+"/"+formateyear;

                // int  End_date_MILLISECOND_calender = calendar.get(Calendar.MILLISECOND);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(i));
                // CheckBox sunday_chk_id = null,monday_chk_id,tuesday_chk_id,wednesday_chk_id,thursday_chk_id,friday_chk_id,saturday_chk_id;


                Boolean match=false;
                for(int n=0;n<exclusionDatesArray.length();n++)
                {
                        try {
                            String cdate= String.valueOf((exclusionDatesArray.get(n)));
                            String[] separated = cdate.split("T");
                            String strings = dateFormat1(separated[0]);
                            if(newconcateDate.equals(strings))
                            {

                                match=true;
                                break;

                            }else
                            {
                                match=false;


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                }

                if(match)
                {
                    map.put("deliveryStatus","3");
                    map.put("calenderDate", concateDate);
                    arraylist.add(map);
                    events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
                    calendarView.setEvents(events);
                }else
                {
                    map.put("deliveryStatus","1");
                    map.put("calenderDate", concateDate);
                    arraylist.add(map);
                    events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                    calendarView.setEvents(events);
                }





//                int day2 = calendar.get(Calendar.DAY_OF_WEEK);
//                switch (day2) {
//                    case Calendar.SUNDAY:
//                        // Current day is Sunday
//                        if(sunday_chk_id.isChecked())
//                        {
//
//                            map.put("deliveryStatus","1");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                            calendarView.setEvents(events);
//
//                        }else
//                        {
//                            map.put("deliveryStatus","3");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
//                            calendarView.setEvents(events);
//                        }
//
//                        break;
//                    case Calendar.MONDAY:
//                        // Current day is Monday
//                        if(monday_chk_id.isChecked())
//                        {
//
//                            map.put("deliveryStatus","1");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                            calendarView.setEvents(events);
//
//                        }else
//                        {
//                            map.put("deliveryStatus","3");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
//                            calendarView.setEvents(events);
//                        }
//
//                        break;
//                    case Calendar.TUESDAY:
//                        // etc.
//                        if(tuesday_chk_id.isChecked())
//                        {
//
//                            map.put("deliveryStatus","1");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                            calendarView.setEvents(events);
//
//                        }else
//                        {
//                            map.put("deliveryStatus","3");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
//                            calendarView.setEvents(events);
//                        }
//
//                        break;
//                    case Calendar.WEDNESDAY:
//
//                        if(wednesday_chk_id.isChecked())
//                        {
//
//                            map.put("deliveryStatus","1");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                            calendarView.setEvents(events);
//
//                        }else
//                        {
//                            map.put("deliveryStatus","3");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
//                            calendarView.setEvents(events);
//                        }
//                        // etc.
//                        break;
//                    case Calendar.THURSDAY:
//
//                        if(thursday_chk_id.isChecked())
//                        {
//
//                            map.put("deliveryStatus","1");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                            calendarView.setEvents(events);
//
//                        }else
//                        {
//                            map.put("deliveryStatus","3");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
//                            calendarView.setEvents(events);
//                        }
//
//                        // etc.
//                        break;
//                    case Calendar.FRIDAY:
//
//                        if(friday_chk_id.isChecked())
//                        {
//
//                            map.put("deliveryStatus","1");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                            calendarView.setEvents(events);
//
//                        }else
//                        {
//                            map.put("deliveryStatus","3");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
//                            calendarView.setEvents(events);
//                        }
//
//                        // etc.
//                        break;
//                    case Calendar.SATURDAY:
//
//                        if(saturday_chk_id.isChecked())
//                        {
//
//                            map.put("deliveryStatus","1");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
//                            calendarView.setEvents(events);
//
//                        }else
//                        {
//                            map.put("deliveryStatus","3");
//                            map.put("calenderDate", concateDate);
//                            arraylist.add(map);
//                            events.add(new EventDay(calendar, R.drawable.corporate_dish_disable2));
//                            calendarView.setEvents(events);
//                        }
//
//                        // etc.
//                        break;
//                }

            }
        }
        else
        {
            calendarView.setEvents(events);
        }

    }









    class CustomAdapterRecycle extends RecyclerView.Adapter<CustomAdapterRecycle.MyViewHolder> {

        private ArrayList<String> dataSet;

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

        public CustomAdapterRecycle(ArrayList<String> data) {
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
            String string = (dataSet.get(listPosition));
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




        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }}




    @Override
    public void onRestart()
    {
        super.onRestart();
//        finish();
//        startActivity(getIntent());


       // Globaluse.schedule_data_array.add(schedule_data);
//        runOnUiThread(new Runnable(){
//            @Override
//            public void run(){
//                // change UI elements here
//                cartTotalCountText.setText(""+Globaluse.schedule_data_array.size());
//            }
//        });
        GetscheduleCount();

    }


    @Override
    public void onResume(){
        super.onResume();
        
        CustomAdapter adapter = new CustomAdapter(this,  Globaluse.corporatedishlistnew);
        navLV.setAdapter(adapter);
        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);

    }


    private String dateFormat(String date) {
//        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        Date serverDate = null;
//        String formattedDate = null;
//        try {
//
//            Calendar c = Calendar.getInstance();
//            c.setTime(df.parse(date));
//            date = df.format(c.getTime());
//            serverDate = df.parse(date);
//
//            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
//           // outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            outputFormat.setTimeZone(TimeZone.getDefault());
//
//            formattedDate = outputFormat.format(serverDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return formattedDate;
//


        // String date="Mar 10, 2016 6:30:00 PM";
        SimpleDateFormat spf=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date  newDate = spf.parse(date);


            //spf= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            // spf= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            spf= new SimpleDateFormat("yyyy-MM-dd");
            date = spf.format(newDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private String dateFormat1(String date) {

        SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date  newDate = spf.parse(date);


            //spf= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            // spf= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            spf= new SimpleDateFormat("dd/MM/yyyy");
            date = spf.format(newDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    public void  Addschedule()
    {

        String Sdate=start_date_id.getText().toString().trim();
        String Edate=end_date_id.getText().toString().trim();

        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");
        String subscriberName = sh.getString("subscriberName", "");
        JSONObject inputObject = new JSONObject();
        try {
            inputObject.put("companyID",companyId);
            inputObject.put("scheduleID",scheduleID);
            JSONArray dishItems_array = new JSONArray();
            JSONArray exclusionDates_array = new JSONArray();
            JSONArray exclusionWeeks_array = new JSONArray();
            JSONObject itemDeliveryTime_obj = new JSONObject();
            JSONArray itemDeliveryTime_array = new JSONArray();
            if(sunday_chk_id.isChecked()) { exclusionWeeks_array.put(0); }
            if(monday_chk_id.isChecked()) { exclusionWeeks_array.put(1); }
            if(tuesday_chk_id.isChecked()) { exclusionWeeks_array.put(2); }
            if(wednesday_chk_id.isChecked()) { exclusionWeeks_array.put(3); }
            if(thursday_chk_id.isChecked()) { exclusionWeeks_array.put(4); }
            if(friday_chk_id.isChecked()) { exclusionWeeks_array.put(5); }
            if(friday_chk_id.isChecked()) { exclusionWeeks_array.put(6); }
            for (int listPosition=0;listPosition<arraylist.size();listPosition++) {
                String deliveryStatus = arraylist.get(listPosition).get("deliveryStatus");
                if(deliveryStatus.equals("3"))
                {
                    String currentString = arraylist.get(listPosition).get("calenderDate");
                    String[] separated = currentString.split("/");
                    int date_str=Integer.parseInt(separated[0]);
                    int month_str= Integer.parseInt(separated[1])+1;
                    String year_str=separated[2];
                    String month_str_new="";
                    if(month_str < 10){
                        month_str_new = "0" + month_str;
                    }else
                    {
                        month_str_new=""+ month_str;
                    }
                    String date_str_new="";
                    if(date_str< 10){

                        date_str_new = "0" + date_str;
                    }else
                    {
                        date_str_new=""+ date_str;
                    }
                    String fulldate_str=date_str_new+"/"+month_str_new+"/"+year_str;
                    exclusionDates_array.put(dateFormat(fulldate_str));
                }
            }
            SimpleDateFormat mdyFormat = new SimpleDateFormat("d-M-yyyy");
            SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dmyFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            String createdDate_str = df.format(today);
            String updatedDate_str = df.format(today);
            for (int k=0;k<Globaluse.corporatedishlistnew.size();k++) {
                try {
                    JSONObject dishDetail = new JSONObject();
                    JSONObject jsonObject = new JSONObject(Globaluse.corporatedishlistnew.get(k));
                    if(jsonObject.getBoolean("checkStatus_schedule"))
                    {
                        dishDetail.put("dishItemName",jsonObject.getString("dishItemName"));
                        dishDetail.put("description",jsonObject.getString("description")+" ");
                        dishDetail.put("dishImageUrl",jsonObject.getString("dishImage"));
                        dishDetail.put("cost",Double.parseDouble(jsonObject.getString("cost")));
                        dishDetail.put("noOfUnits",Integer.parseInt(jsonObject.getString("noOfUnits")));
                        dishDetail.put("taxValue",Double.parseDouble(jsonObject.getString("taxValue")));
                        dishDetail.put("packingCharges",Double.parseDouble(jsonObject.getString("packingCharges")));
                        dishDetail.put("deliveryCharges",Double.parseDouble(jsonObject.getString("deliveryCharges")));
                        dishDetail.put("isDishRequired",true);
                        dishItems_array.put(dishDetail);

                    }
                    else
                    {
                        //  Toast.makeText(CorporateSchedule.this, "Please Check any one dish", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            inputObject.put("dishesItems",dishItems_array);
            inputObject.put("isBreakfast",breakfast_chk_id.isChecked());
            inputObject.put("isLunch",lunch_chk_id.isChecked());
            inputObject.put("isDinner",dinner_chk_id.isChecked());
            inputObject.put("startDate",dateFormat(Sdate));
            inputObject.put("endDate",dateFormat(Edate));
            inputObject.put("pointOfContact",subscriberName);
            inputObject.put("createdDate",createdDate_str);
            inputObject.put("updatedDate",updatedDate_str);
            inputObject.put("exclusionDates",exclusionDates_array);
            inputObject.put("exclusionWeeks",exclusionWeeks_array);
            itemDeliveryTime_obj.put("lunch"," ");
            itemDeliveryTime_obj.put("breakfast"," ");
            itemDeliveryTime_obj.put("dinner"," ");
            itemDeliveryTime_obj.put("snacks"," ");
            itemDeliveryTime_array.put(itemDeliveryTime_obj);
            inputObject.put("itemDeliveryTime",itemDeliveryTime_array);
            inputObject.put("exclusionReason"," ");
            inputObject.put("isActive",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }





        String URL = APIBaseURL.BASEURLLINK_B2B_UPDATE_SCHEDULE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // Toast.makeText(CorporatebussinessInformation.this, response.optString("errorMessage"), Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if(jsonObject.getString("isSuccess").equals("true"))
                    {
                        Toast.makeText(CorporateScheduleEditFromApi.this, jsonObject.getString("successMessage"), Toast.LENGTH_SHORT).show();
                        Globaluse.fromscheduleEdit=" ";


//                        Globaluse._listDataHeader=new ArrayList<String>();
//                        Globaluse._listDataChild=new HashMap<String, List<String>>();

                        finish();



                        //GetscheduleCount();


//                        //pDialog.dismiss();
//                        //corporateshared("no");
//                        final Dialog dialog = new Dialog(CorporateScheduleFromApi.this);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog.setCancelable(false);
//                        dialog.setContentView(R.layout.corporate_sucess);
//
//                        Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
//
//                        subscribe_id.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.cancel();
//                                finishAffinity();
//                                Intent intent = new Intent(CorporateScheduleFromApi.this, CorporateMenuWithNavigatioin.class);
//                                startActivity(intent);
//
//                            }
//                        });
//                        dialog.show();

                    }else
                    {
                        // pDialog.dismiss();
                        //corporateshared("yes");
                        final Dialog dialog = new Dialog(CorporateScheduleEditFromApi.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.corporate_fail);

                        Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                        TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                        errormessage_id.setText(jsonObject.getString("errorMessage"));

                        subscribe_id.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();

                            }
                        });
                        dialog.show();

                        TextView call_id = (TextView) dialog.findViewById(R.id.call_id);
                        call_id.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:+91 6366225334"));
                                startActivity(intent);
                            }
                        });
                    }



                } catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();


                //corporateshared("yes");
                final Dialog dialog = new Dialog(CorporateScheduleEditFromApi.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.corporate_fail);

                Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                errormessage_id.setText("sorry something went wrong..");

                subscribe_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });
                dialog.show();

                TextView call_id = (TextView) dialog.findViewById(R.id.call_id);
                call_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:+91 6366225334"));
                        startActivity(intent);
                    }
                });

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
//                    ViewGroup viewGroup = CorporateScheduleFromApi.this.findViewById(android.R.id.content);
//
//                    //then we will inflate the custom alert dialog xml that we created
//                    View dialogView = LayoutInflater.from(CorporateScheduleFromApi.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);
//
//                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
//                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
//                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);
//
//                    //Now we need an AlertDialog.Builder object
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CorporateScheduleFromApi.this);
//
//                    //setting the view of the builder to our custom view that we already inflated
//                    builder.setView(dialogView);
//
//                    //finally creating the alert dialog and displaying it
//                    android.app.AlertDialog alertDialog = builder.create();
//
//
//                    buttonOk.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//                            ConsumerMainActivity.logout();
//
//                        }
//                    });
//
//                    closeBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//
//                        }
//                    });
//
//                    buttonreset.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//                        }
//                    });
//                    alertDialog.setCanceledOnTouchOutside(false);
//                    alertDialog.show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(CorporateScheduleEditFromApi.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporateScheduleEditFromApi.this){    //this is the part, that adds the header to the request
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


        ApplicationController.getInstance().addToRequestQueue(stringRequest, "scheduleList");
    }




//    @Override
//    public void onRestart() {
//        super.onRestart();
//        CustomAdapter adapter = new CustomAdapter(this,  Globaluse.corporatedishlistnew);
//        navLV.setAdapter(adapter);
//        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);
//    }

}
