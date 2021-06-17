package stayabode.foodyHive.constants;


import android.util.Log;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import stayabode.foodyHive.corporatemenurange.CorporateScheduleFromApi;

public class Globaluse {

    public static  String searchString ="";
    public static long lastFifteen = 0;
    public static long lastThirty = 0;
    public static  long dateStart = 0;
    public static  long dateEnd = 0;
    public static  String orderInvoiceNo_str = "";
    public static  boolean offlinedeliver = false;

    public static ArrayList<String> corporatedishlistnew=new ArrayList<String>();
    public static ArrayList<String> corporatedishlistnewEdit=new ArrayList<String>();

    public static List<String> _listDataHeader=new ArrayList<String>();;
    public static HashMap<String, List<String>> _listDataChild=new HashMap<String, List<String>>();

    public static List<String> _listDataHeader_for_schedule=new ArrayList<String>();;
    public static HashMap<String, List<String>> _listDataChild_for_schedule=new HashMap<String, List<String>>();


    public static ArrayList<HashMap<String, String>> schedule_data = new ArrayList<HashMap<String, String>>();
    public static ArrayList<ArrayList<HashMap<String, String>>> schedule_data_array = new ArrayList<ArrayList<HashMap<String, String>>>();

    public static  String only_Monthly_week ="";
    public static  String today_tomo ="Tomorrow";
    public static  String lunch_dinner ="LUNCH";
    public static  String deliver_time ="1.00";




    public static Calendar c = Calendar.getInstance();
    public static int s_year = c.get(Calendar.YEAR);
    public static int s_month = c.get(Calendar.MONTH);
    public static int s_day = c.get(Calendar.DAY_OF_MONTH);

    public static int e_year = c.get(Calendar.YEAR);
    public static int e_month = c.get(Calendar.MONTH);
    public static int e_day = c.get(Calendar.DAY_OF_MONTH);


    //public static  String startdate =day + "/" + (month+1) + "/" + year;
    public static  String startdate ="Start Date";
    public static  String enddate ="End Date";
    public static  Boolean brekfast =false;
    public static  Boolean lunch =false;
    public static  Boolean dinner =false;

    public static  String spinnerselection ="All";
    public static  String Filtertype ="All";

    public static JSONArray scheduleList =new JSONArray() ;

    public static  String fromscheduleEdit =" ";
    public static  String frommenu =" ";


    public static boolean CheckWeeekly(ArrayList<String>  schedule_data)
    {
        boolean isWeeekly=false;

        ArrayList<String> weekList = new ArrayList<>();
        weekList.add("SINGLE - Weekly Specials");
        weekList.add("COUPLES - Weekly Specials");
        weekList.add("FAMILIES - Weekly Specials");
        weekList.add("SINGLE HEALTHY MENU");
        weekList.add("COUPLES HEALTHY MENU");
        weekList.add("FAMILIES HEALTHY MENU");
        weekList.add("SINGLE - Weekly Specials Veg");
        weekList.add("SINGLE - Weekly Specials Non-Veg");
        weekList.add("COUPLES - Weekly Specials Veg");
        weekList.add("COUPLES - Weekly Specials Non-Veg");
        weekList.add("FAMILIES - Weekly Specials Veg");
        weekList.add("FAMILIES - Weekly Specials Non-veg");

        for(int i=0;i<schedule_data.size();i++)
        {
            try {
                JSONObject jsonObject = new JSONObject(schedule_data.get(i));

                if(jsonObject.getBoolean("checkStatus_schedule"))
                {
                    boolean ans = weekList.contains(jsonObject.getString("dishItemName"));
                    if (ans)
                    {
                        System.out.println("The list contains dishItemName");
                        isWeeekly=true;
                    }
                    else
                    {
                        System.out.println("The list does not contains dishItemName");
                        isWeeekly=false;
                        break;
                    }

                }


            }catch (JSONException err){
                Log.d("Error", err.toString());
            }
        }

        return isWeeekly;
    }


    public static boolean CheckWeeeklyUsingJson(JSONArray schedule_data)
    {
        boolean isWeeekly=false;

        ArrayList<String> weekList = new ArrayList<>();
        weekList.add("SINGLE - Weekly Specials");
        weekList.add("COUPLES - Weekly Specials");
        weekList.add("FAMILIES - Weekly Specials");
        weekList.add("SINGLE HEALTHY MENU");
        weekList.add("COUPLES HEALTHY MENU");
        weekList.add("FAMILIES HEALTHY MENU");
        weekList.add("SINGLE - Weekly Specials Veg");
        weekList.add("SINGLE - Weekly Specials Non-Veg");
        weekList.add("COUPLES - Weekly Specials Veg");
        weekList.add("COUPLES - Weekly Specials Non-Veg");
        weekList.add("FAMILIES - Weekly Specials Veg");
        weekList.add("FAMILIES - Weekly Specials Non-veg");

        for(int i=0;i<schedule_data.length();i++)
        {
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(schedule_data.get(i)));


                    boolean ans = weekList.contains(jsonObject.getString("dishItemName"));
                    if (ans)
                    {
                        System.out.println("The list contains dishItemName");
                        isWeeekly=true;
                    }
                    else
                    {
                        System.out.println("The list does not contains dishItemName");
                        isWeeekly=false;
                        break;
                    }




            }catch (JSONException err){
                Log.d("Error", err.toString());
            }
        }

        return isWeeekly;
    }

}
