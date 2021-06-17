package stayabode.foodyHive.corporatemenurange;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

import stayabode.applandeo.materialcalendarview.CalendarView;
import stayabode.applandeo.materialcalendarview.EventDay;
import stayabode.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAdapterRangeForSchedul;
import stayabode.foodyHive.constants.Globaluse;
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

public class CorporateScheduleEdit extends AppCompatActivity {




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

    static CheckBox sunday_chk_id,monday_chk_id,tuesday_chk_id,wednesday_chk_id,thursday_chk_id,friday_chk_id,saturday_chk_id;


    CorporateExpandableListAdapterRangeForSchedul listAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_schedule);


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
                    Toast.makeText(CorporateScheduleEdit.this, "Start Date is empty", Toast.LENGTH_SHORT).show();
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
                    for (int k=0;k<Globaluse.corporatedishlistnewEdit.size();k++) {

                        try {
                            JSONObject jsonObject = new JSONObject(Globaluse.corporatedishlistnewEdit.get(k));
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


                        final Dialog dialog = new Dialog(CorporateScheduleEdit.this);
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
                        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(CorporateScheduleEdit.this, LinearLayoutManager.HORIZONTAL, false);
                        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                        horizontal_recycler_view.setAdapter(horizontalAdapter);


                        CustomAdapter2 adapter = new CustomAdapter2(CorporateScheduleEdit.this,  schedule_data);
                        navLVp.setAdapter(adapter);
                        FulllistItemdisplay.setListViewHeightBasedOnItems(navLVp);

                        close_id.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        suubmit_id.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                Globaluse.schedule_data_array.add(schedule_data);
                                runOnUiThread(new Runnable(){
                                    @Override
                                    public void run(){
                                        // change UI elements here
                                        cartTotalCountText.setText(""+Globaluse.schedule_data_array.size());
                                    }
                                });

                               // Toast.makeText(CorporateSchedule.this, "Please select  date", Toast.LENGTH_SHORT).show();

                            }
                        });

                        dialog.show();




                    }
                    }else
                    {
                        Toast.makeText(CorporateScheduleEdit.this, "Please select  meals type", Toast.LENGTH_SHORT).show();
                    }



                }else
                {
                    Toast.makeText(CorporateScheduleEdit.this, "Please select  date", Toast.LENGTH_SHORT).show();

                }





            }
        });

        navLV = findViewById(R.id.navLV);

        CustomAdapter adapter = new CustomAdapter(this,  Globaluse.corporatedishlistnewEdit);
        navLV.setAdapter(adapter);
        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);

        //calendarView.setMaximumDate(max);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CorporateScheduleEdit.this, CorporateAfterSchedule.class);
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


                Toast.makeText(CorporateScheduleEdit.this,"Delivery type :"+deliveryType,Toast.LENGTH_SHORT).show();

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


                Toast.makeText(CorporateScheduleEdit.this,"Delivery type :"+deliveryType,Toast.LENGTH_SHORT).show();

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


                Toast.makeText(CorporateScheduleEdit.this,"Delivery type :"+deliveryType,Toast.LENGTH_SHORT).show();

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
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year, month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

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

            setEvents();

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

                Glide.with(CorporateScheduleEdit.this).load(jsonObject.getString("dishImage")).into(img_id);

                double noofUnits= Integer.parseInt(jsonObject.getString("noOfUnits"));
                double cost= Integer.parseInt(jsonObject.getString("cost"));

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
                dish_multipleprice.setText("Rs. "+cost);
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





                        Globaluse.corporatedishlistnewEdit=new  ArrayList<String>();
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


                Glide.with(CorporateScheduleEdit.this).load(items.get(i).get("dishImage")).into(img_id);
                dish_multipleprice.setText("Rs. "+jsonObject.getString("cost"));
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


        Globaluse.corporatedishlistnewEdit=new  ArrayList<String>();
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
                        Globaluse.corporatedishlistnewEdit.add(String.valueOf(dishDetail));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        CustomAdapter adapter = new CustomAdapter(this,  Globaluse.corporatedishlistnewEdit);
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
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                // change UI elements here
                cartTotalCountText.setText(""+Globaluse.schedule_data_array.size());
            }
        });

    }

}
