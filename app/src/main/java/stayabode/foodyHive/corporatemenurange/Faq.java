package stayabode.foodyHive.corporatemenurange;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.ApplicationController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class Faq extends AppCompatActivity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.faq);


            // get the listview
            expListView = (ExpandableListView) findViewById(R.id.lvExp);

            // preparing list data
            prepareListData();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);

            // Listview Group click listener
            expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    // Toast.makeText(getApplicationContext(),
                    // "Group Clicked " + listDataHeader.get(groupPosition),
                    // Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            // Listview Group expanded listener
            expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
//                    Toast.makeText(getApplicationContext(),
//                            listDataHeader.get(groupPosition) + " Expanded",
//                            Toast.LENGTH_SHORT).show();
                }
            });

            // Listview Group collasped listener
            expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
//                    Toast.makeText(getApplicationContext(),
//                            listDataHeader.get(groupPosition) + " Collapsed",
//                            Toast.LENGTH_SHORT).show();

                }
            });

            // Listview on child click listener
            expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    // TODO Auto-generated method stub
//                    Toast.makeText(
//                            getApplicationContext(),
//                            listDataHeader.get(groupPosition)
//                                    + " : "
//                                    + listDataChild.get(
//                                    listDataHeader.get(groupPosition)).get(
//                                    childPosition), Toast.LENGTH_SHORT)
//                            .show();
                    return false;
                }
            });
        }

        /*
         * Preparing the list data
         */
        private void prepareListData() {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
            listDataHeader.add("Can we expect your services throughout Bengaluru ?");
            listDataHeader.add("Does FoodyHive similar to Swiggy, Zomato, UberEats etc ?");
            listDataHeader.add("Is there any minimum order quantity applies to the subscription plan ?");
            listDataHeader.add("What is the minimum subscription period ?");
            listDataHeader.add("I just want to make an one time order, can you process it ?");
            listDataHeader.add("Can we get the food immediately upon placing the order ?");
            listDataHeader.add("What cuisines are available with Foodyhive ?");
            listDataHeader.add("How long will it take to deliver the food to our location ?");
            listDataHeader.add("What are the provisions for the cancellation of order ?");
            listDataHeader.add("I have something else to inquire, what should I do ?");

            // Adding child data
            List<String> one = new ArrayList<String>();
            one.add("Yes, our services are available anywhere in Bengaluru.");
            List<String> two = new ArrayList<String>();
            two.add("Not really. At FoodyHive, we provide healthy and hygienic home made food supplied through our certified home chefs to the Corporates, PG Accommodations, Hospitals and related on a subscription basis.");
            List<String> three = new ArrayList<String>();
            three.add("Yes, we will be able to work on with a minimum of 10 orders.");
            List<String> four = new ArrayList<String>();
            four.add("Minimum subscription is 7 days");
            List<String> five = new ArrayList<String>();
            five.add("Yes, we can process with a minimum of 10 orders.");
            List<String> six = new ArrayList<String>();
            six.add("No, we accept only pre-orders; you are requested to place an order 24 hours prior to delivery.");
            List<String> seven = new ArrayList<String>();
            seven.add("We provide South Indian, North Indian, Chinese & Continental dishes as well. Available for both vegetarians and non-vegetarians. For more details please call us at (080) 4718 9418");
            List<String> eight = new ArrayList<String>();
            eight.add("The food will be delivered on time as per the mutual agreement.");
            List<String> nine = new ArrayList<String>();
            nine.add("Cancellation of an order is permitted with 16 to 24 hours prior notice; some penalty may be applicable in case short time notice less than 16 hours.");
            List<String> ten = new ArrayList<String>();
            ten.add("Feel free to contact our sales team: (080) 4718 9148");

            listDataChild.put(listDataHeader.get(0), one); // Header, Child data
            listDataChild.put(listDataHeader.get(1), two);
            listDataChild.put(listDataHeader.get(2), three);
            listDataChild.put(listDataHeader.get(3), four);
            listDataChild.put(listDataHeader.get(4), five);
            listDataChild.put(listDataHeader.get(5), six);
            listDataChild.put(listDataHeader.get(6), seven);
            listDataChild.put(listDataHeader.get(7), eight);
            listDataChild.put(listDataHeader.get(8), nine);
            listDataChild.put(listDataHeader.get(9), ten);
        }


}
