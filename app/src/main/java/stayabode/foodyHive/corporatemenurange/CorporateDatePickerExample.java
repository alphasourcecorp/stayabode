package stayabode.foodyHive.corporatemenurange;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import stayabode.applandeo.materialcalendarview.CalendarView;
import stayabode.applandeo.materialcalendarview.EventDay;
import stayabode.applandeo.materialcalendarview.listeners.OnDayClickListener;
import stayabode.foodyHive.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CorporateDatePickerExample extends AppCompatActivity {




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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_date_picker_test);


        arraylist = new ArrayList<HashMap<String, String>>();

        calendarView = findViewById(R.id.calendarView);
        suubmit_id = findViewById(R.id.suubmit_id);

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
                    Toast.makeText(CorporateDatePickerExample.this, "Start Date is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        suubmit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dish_dont_want_date="";
                for (int listPosition=0;listPosition<arraylist.size();listPosition++) {

                    String deliveryStatus = arraylist.get(listPosition).get("deliveryStatus");
                    if(deliveryStatus.equals("3"))
                    {
                        String currentString = arraylist.get(listPosition).get("calenderDate");
                        String[] separated = currentString.split("/");
                        String date_str=separated[0]; // this will contain "Fruit"
                        int month_str= Integer.parseInt(separated[1])+1; // this will contain "Fruit"
                        String year_str=separated[2]; // this will contain "Fruit"
                        String fulldate_str=date_str+"/"+month_str+"/"+year_str;

                        dish_dont_want_date+="\n"+fulldate_str;
                    }

                }

                String Sdate=start_date_id.getText().toString().trim();
                String Edate=end_date_id.getText().toString().trim();

                Toast.makeText(CorporateDatePickerExample.this, "Start Date :"+Sdate + "\n" +"End Date :"+Edate+ "\n"+"Don't want dish : "+dish_dont_want_date, Toast.LENGTH_SHORT).show();
            }
        });

        //calendarView.setMaximumDate(max);

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
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year, month,day);
            return datePickerDialog;
        }



        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            start_date_id.setText(day + "/" + (month+1) + "/" + year);
//             Calendar calendar = Calendar.getInstance();
//             calendar.set(year, month, day-1);
//             events.add(new EventDay(calendar, R.drawable.corporate_dish_not_able_deliver));
            start_year=year;
            start_month=(month+1);
            start_day=day;
            min.set(year, month, day-1);
            calendarView.setMinimumDate(min);
            //calendarView.setEvents(events);
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
                    map.put("deliveryStatus","1");
                    map.put("calenderDate", concateDate);
                    arraylist.add(map);


                    events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));
                    calendarView.setEvents(events);

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
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day + 1);
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog2.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog2;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            end_date_id.setText(day + "/" + (month+1) + "/" + year);

            end_day=day;
            end_month=(month+1);
            end_year=year;

            max.set(year, month, day);
            events = new ArrayList<>();

            calendarView.setMaximumDate(max);
           // calendarView.setEvents(events);
            //calendarView.setEvents(events);
            List<Date> dates =getDates(start_year+"-"+""+start_month+"-"+""+(start_day), end_year+"-"+""+end_month+"-"+""+end_day);

            arraylist = new ArrayList<HashMap<String, String>>();

            //for(Date date2:dates)
            if(!(start_year==0)) {
                for (int i = 0; i < dates.size(); i++) {
                    String formateyear = new SimpleDateFormat("yyyy").format(dates.get(i));
                    String formatedate = new SimpleDateFormat("dd").format(dates.get(i));
                    int formatemonth = Integer.parseInt(new SimpleDateFormat("MM").format(dates.get(i))) - 1;
                    //Log.v("output date ",formateDate);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, Integer.parseInt(formateyear));
                    calendar.set(Calendar.MONTH, Integer.parseInt(String.valueOf((formatemonth))));
                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(formatedate));
                    System.out.println(dates.get(i));


                    String month_str = "";
                    String day_str = "";

                    if (formatemonth < 10) {

                        month_str = "0" + formatemonth;
                    } else {
                        month_str = "" + formatemonth;
                    }
//
//                int formatedate_int= Integer.parseInt(formatedate);
//                if(formatedate_int < 10){
//
//                    day_str  = "0" + formatedate ;
//                }else
//                {
//                    day_str  =""+formatedate ;
//                }
//
//
//                String concateDate=day_str+"/"+month_str+"/"+formateyear;

                    //String concateDate=formatedate+"/"+formatemonth+"/"+formateyear;
                    String concateDate = formatedate + "/" + month_str + "/" + formateyear;

                    //String concateDate=formatedate+"/"+formatemonth+"/"+formateyear;

                    // int  End_date_MILLISECOND_calender = calendar.get(Calendar.MILLISECOND);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", String.valueOf(i));
                    map.put("deliveryStatus", "1");
                    map.put("calenderDate", concateDate);
                    arraylist.add(map);


                    events.add(new EventDay(calendar, R.drawable.corporate_dish_book2));


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
            calendarView.setEvents(events);
                //events.add(new EventDay(date, R.drawable.corporate_dish_not_able_deliver));


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
}
