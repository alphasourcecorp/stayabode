package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.HomeDashboardAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.BarChartData;
import stayabode.foodyHive.models.BubbleChartDataModel;
import stayabode.foodyHive.models.CloudKitchen;
import stayabode.foodyHive.models.Franchise;
import stayabode.foodyHive.models.FranchiseList;
import stayabode.foodyHive.models.NearByCloudKitchenList;
import stayabode.foodyHive.models.PieChartData;
import stayabode.foodyHive.models.SubscriptionList;
import stayabode.foodyHive.models.Subscriptions;
import stayabode.foodyHive.models.TopFranchise;
import stayabode.foodyHive.models.TotalRevenues;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    List<Object> objectList = new ArrayList<>();
    HomeDashboardAdapter homeDashboardAdapter;
    RecyclerView recyclerView;
    Typeface fontBold;
    Typeface fontRegular;
    TextView pagetitle;

    ProgressBar progressBar;
    Spinner searchSpinner;
    String[] searchNames;
    String[] searchIds;
    ImageView searchIv;
    LinearLayout dashBoardSearch;
    LinearLayout arrowImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment,container,false);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");
        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_dehaze_black);
        MainActivity.customIcon.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.active = this;
        pagetitle = rootView.findViewById(R.id.pagetitle);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = rootView.findViewById(R.id.progressBar);
        searchSpinner = rootView.findViewById(R.id.searchSpinner);
        dashBoardSearch = rootView.findViewById(R.id.dashBoardSearch);
        arrowImage = rootView.findViewById(R.id.arrowImage);
        searchIv = rootView.findViewById(R.id.searchIv);
//        progressBar.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);


        pagetitle.setText("Dashboard");
        pagetitle.setTypeface(fontBold);


        objectList = new ArrayList<>();


        dashBoardSearch.setVisibility(View.GONE);

        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardSearch.setVisibility(View.VISIBLE);
            }
        });





        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(searchSpinner.getSelectedItem() == null) { // user selected nothing...
                    searchSpinner.performClick();
//                }
            }
        });

        if(MainActivity.Role.equals("Franchisee"))
        {
//            getFranchiseeDashboardCardDetails();
            getCardDetailsForFranchisee();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            getDashboardCardDetails();
//            getCardDetailsForPlatform();
        }


//        searchPages();




        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);

    }

    public void getDashboardCardDetails()
    {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        String url = APIBaseURL.getDashboardCardDetails;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CardResponse",response);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optBoolean("isSuccess"))
                    {
                        JSONObject dataObject = jsonObject.getJSONObject("data");

                        TotalRevenues totalRevenues = new TotalRevenues();
                        totalRevenues.setQuotedThisMonth("\u20B9"+formatChange(dataObject.optInt("quotedThisMonth")));
                        totalRevenues.setTotalRevenuesThisMonth("\u20B9"+formatChange(dataObject.optInt("totalRevenue")));
                        totalRevenues.setTotalFranchiese(dataObject.optString("totalFranchisees"));
                        totalRevenues.setTotalInvoices("\u20B9"+dataObject.optString("outstandingInvoice"));

                        objectList.add(totalRevenues);

                        TopFranchise topFranchise = new TopFranchise();
                        JSONObject topOneFranchiseeObject = new JSONObject();
                        if(dataObject.has("topOneFranchisee"))
                        {
                            topOneFranchiseeObject = dataObject.getJSONObject("topOneFranchisee");
                        }
                        topFranchise.setFranchiseName(topOneFranchiseeObject.optString("name"));
                        topFranchise.setFranchiseLocation(topOneFranchiseeObject.optString("location"));
                        topFranchise.setCloudKitchenCount(topOneFranchiseeObject.optString("cloudKitchenCount")+" Cloud Kitchen");
                        topFranchise.setQuoteCount(topOneFranchiseeObject.optString("quoteCount")+" Quotes");
                        topFranchise.setRevenuePercent(topOneFranchiseeObject.optString("revenuePercent"));
                        objectList.add(topFranchise);
                    }



                        PieChartData pieChartData = new PieChartData();
                        objectList.add(pieChartData);

                        BarChartData barChartData = new BarChartData();
                        objectList.add(barChartData);


                        homeDashboardAdapter = new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
                        recyclerView.setAdapter(homeDashboardAdapter);

                        getTopFranchisesList();




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.VISIBLE);
//                parseVolleyError(error);
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutErrorHome");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthErrorHome");
//                } else if (error instanceof ServerError) {
//                    //TODO
//                    Log.d("Error","ServerErrorHome");
//                    Log.d("Error",error.getMessage() + "  Check Error");
//
//                        PieChartData pieChartData = new PieChartData();
//                        objectList.add(pieChartData);
//
//                        BarChartData barChartData = new BarChartData();
//                        objectList.add(barChartData);
//
//
//                        homeDashboardAdapter = new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
//                        recyclerView.setAdapter(homeDashboardAdapter);
//
//                        getTopFranchisesList();
//
//
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error","Network ErrorHome");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error","ParseErrorHome");
//                }
            }
        },getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"home_card_details");
    }
    public void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            JSONArray errors = data.getJSONArray("errors");
            JSONObject jsonMessage = errors.getJSONObject(0);
            String message = jsonMessage.getString("message");
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
        } catch (UnsupportedEncodingException errorr) {
        }
    }

    public void getTopFranchisesList()
    {
        String url = APIBaseURL.getTopFranchiseesList;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.optBoolean("isSuccess"))
                    {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        FranchiseList mainFranchisesList = new FranchiseList();

                        List<Franchise> franchiseList = new ArrayList<>();
                        for (int i=0;i < dataArray.length();i++)
                        {
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            Franchise franchise = new Franchise();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            DateFormat startDate = new SimpleDateFormat("dd MMMM");

                            Date startdate = null;


                            try {
                                startdate = sdf.parse(dataObject.optString("date"));

                                franchise.setDate(startDate.format(startdate));

                            } catch (ParseException e) {
                                e.printStackTrace();

                                Date startdate1 = null;
                                try {
                                    startdate1 = sdf1.parse(dataObject.optString("date"));
                                    franchise.setDate(startDate.format(startdate1));
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }




                            }





                            franchise.setName(dataObject.optString("franchiseName"));
                            franchise.setLocation(dataObject.optString("location"));
                            franchise.setAmount("\u20B9"+formatChange(dataObject.optInt("revenue")));
                            franchiseList.add(franchise);

                        }
                        mainFranchisesList.setFranchiseList(franchiseList);
                        objectList.add(mainFranchisesList);
                        homeDashboardAdapter = new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
                        recyclerView.setAdapter(homeDashboardAdapter);
                    }

                    getSubscriptions();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutErrorHome");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthErrorHome");
//                } else if (error instanceof ServerError) {
//                    //TODO
//                    Log.d("Error","ServerErrorHome");
//                    getSubscriptions();
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error","Network ErrorHome");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error","ParseErrorHome");
//                }
            }
        },getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"top_franchise_list");
    }

    public void getSubscriptions()
    {
        String url = APIBaseURL.subscriptionsURL;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.optBoolean("isSuccess"))
                    {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        SubscriptionList mainSubscriptionList = new SubscriptionList();
                        List<Subscriptions> subscriptionsList = new ArrayList<>();
                        for (int i=0;i < dataArray.length();i++)
                        {
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            Subscriptions subscriptions = new Subscriptions();
                            subscriptions.setClientName(dataObject.optString("clientName"));

                            subscriptions.setLocation(dataObject.optString("clientLocation"));
                            if (dataObject.optBoolean("status"))
                            {
                                subscriptions.setStatus("Complete");
                            }
                            else
                            {
                                subscriptions.setStatus("In progress");
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            DateFormat startDate = new SimpleDateFormat("dd MMMM");
                            DateFormat endDateFormat = new SimpleDateFormat("dd");


                            try {
                                Date startdate = null;
                                Date endDate = null;
                                try {
                                    startdate = sdf.parse(dataObject.optString("startDate"));
                                    endDate = sdf.parse(dataObject.optString("endDate"));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Log.d("StartDate", startDate.format(startdate));
                                Log.d("EndDate", endDateFormat.format(endDate));
                                if(startDate.equals(endDate))
                                {

                                }

                                subscriptions.setDate(startDate.format(startdate) /*+ " - " + endDateFormat.format(endDate)*/);

                            } catch (Exception e) {
                                Date startdate = null;
                                Date endDate = null;
                                try {
                                    startdate = sdf1.parse(dataObject.optString("startDate"));
                                    endDate = sdf1.parse(dataObject.optString("endDate"));
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                                Log.d("StartDate", startDate.format(startdate));
                                Log.d("EndDate", endDateFormat.format(endDate));
                                if(startDate.equals(endDate))
                                {

                                }

                                subscriptions.setDate(startDate.format(startdate) /*+ " - " + endDateFormat.format(endDate)*/);
                                e.printStackTrace();
                            }


                            subscriptionsList.add(subscriptions);
                        }
                        mainSubscriptionList.setSubscriptionsList(subscriptionsList);
                        objectList.add(mainSubscriptionList);

                        homeDashboardAdapter = new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
                        recyclerView.setAdapter(homeDashboardAdapter);
                    }

                } catch (JSONException /*| ParseException*/ e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"subscriptions_taq");
    }





    public String formatChange(Integer value)
    {
//        if (! isNaN(value)) {
//            String currencySymbol = String.valueOf('₹');
            if (value == null) {
                return "";
            }
            Integer InrRSOut = value;
            InrRSOut = Math.round(InrRSOut);
            String RV = "";
            if (InrRSOut > 0 && InrRSOut < 1000) {
                RV = InrRSOut.toString();
            }
            else if (InrRSOut >= 1000 && InrRSOut < 10000) {
         String f1 = InrRSOut.toString().substring(0, 1);
         String f2 = InrRSOut.toString().substring(1, 4);
                RV = f1+ "," + f2;
            }
            else if (InrRSOut >= 10000 && InrRSOut < 100000) {
                String f1 = InrRSOut.toString().substring(0, 2);
                String  f2 = InrRSOut.toString().substring(2, 5);
//                if(k) {
                    RV = f1 + "k";
//                } else {
//                    RV = f1 + "," + f2;
//                }

            }
            else if (InrRSOut >= 100000 && InrRSOut < 1000000) {
                String f1 = InrRSOut.toString().substring(0, 1);
                String  f2 = InrRSOut.toString().substring(1, 3);
                if (f2 == "00") {
                    RV = f1 + " L";
                }
                else {
                    RV = f1 + "." + f2 + " L";
                }
            }
            else if (InrRSOut >= 1000000 && InrRSOut < 10000000) {
                String f1 = InrRSOut.toString().substring(0, 2);
                String f2 = InrRSOut.toString().substring(2, 4);
                if (f2 == "00") {
                    RV = f1 + " L";
                }
                else {
                    RV = f1 + "." + f2 + " L";
                }
            }
            else if (InrRSOut >= 10000000 && InrRSOut < 100000000) {
                String f1 = InrRSOut.toString().substring(0, 1);
                String f2 = InrRSOut.toString().substring(1, 3);
                if (f2 == "00") {
                    RV = f1 + " Cr";
                }
                else {
                    RV = f1 + "." + f2 + " Cr";
                }
            }
            else if (InrRSOut >= 100000000 && InrRSOut < 1000000000) {
                String f1 = InrRSOut.toString().substring(0, 2);
                String  f2 = InrRSOut.toString().substring(2, 4);
                if (f2 == "00") {
                    RV = f1 + " Cr";
                }
                else {
                    RV = f1 + "." + f2 + " Cr";
                }
            }
//            else if (InrRSOut >= 1000000000 && InrRSOut < 10000000000) {
//                String f1 = InrRSOut.toString().substring(0, 3);
//                String f2 = InrRSOut.toString().substring(3, 5);
//                if (f2 == "00") {
//                    RV = f1 + " Cr";
//                }
//                else {
//                    RV = f1 + "." + f2 + " Cr";
//                }
//            }
//            else if (InrRSOut >= 10000000000) {
//                String f1 = InrRSOut.toString().substring(0, 4);
//                String f2 = InrRSOut.toString().substring(6, 8);
//                if (f2 == "00") {
//                    RV = f1 + " Cr";
//                }
//                else {
//                    RV = f1 + "." + f2 + " Cr";
//                }
//            }
            else {
                RV = InrRSOut.toString();
            }
            return  RV;
//        }

//        return value;
    }

    public void searchPages()
    {

        String url = APIBaseURL.search;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject  jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    searchNames = new String[dataArray.length() + 1];
                    searchIds = new String[dataArray.length() + 1];
                    searchIds[0] = "0000000000000";
                    searchNames[0] = "Search";

                    for (int i = 1; i <= dataArray.length(); i++) {
                        JSONObject officer = dataArray.getJSONObject(i - 1);
                        String officerType_Id = officer.optString("id");
                        String officerType_name = officer.optString("name");
                        searchIds[i] = officerType_Id;
                        searchNames[i] = officerType_name;
                    }

                    try {
                        ArrayAdapter<String> officerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, searchNames);
                        searchSpinner.setAdapter(officerAdapter);
                        searchSpinner.setFocusable(false);
                        searchSpinner.setClickable(false);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if(searchIds[position].equals("1001"))
                            {

                                MainActivity.fragmentClass= CloudKitchenFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            else if(searchIds[position].equals("1000"))
                            {

                                MainActivity.navigation.setSelectedItemId(R.id.navigation_franchise);

                                MainActivity.fragmentClass= FranchiseFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            else if(searchIds[position].equals("1002"))
                            {

                                MainActivity.fragmentClass= ChefFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(searchIds[position].equals("1003"))
                            {

                                MainActivity.fragmentClass= ConsumerFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(searchIds[position].equals("2000"))
                            {

                                MainActivity.fragmentClass= DeliveryPartnerFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(searchIds[position].equals("1004"))
                            {

                                MainActivity.fragmentClass= BulkOrderFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(searchIds[position].equals("1005"))
                            {

                                MainActivity.fragmentClass= SubscriptionFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(searchIds[position].equals("2001"))
                            {

                                MainActivity.fragmentClass= PaymentGatewayFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(searchIds[position].equals("5001"))
                            {

                                MainActivity.fragmentClass= RevenueSharingFragment.class;
                                try {
                                    MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                                    MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                                    FragmentManager manager = MainActivity.fragmentManager;
                                    boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                                    if (!fragmentPopped) {

                                        Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                                        // Return if the class are the same
                                        if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"search_taq");


    }

    public void getFranchiseeDashboardCardDetails()
    {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        String url = APIBaseURL.franchiseeDashboardCardDetails + "5ece8c07a18aea3628105f88";

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    if(jsonObject.optBoolean("isSuccess"))
                    {
                        TotalRevenues totalRevenues = new TotalRevenues();
                        JSONObject totalOrdersObject = new JSONObject();

                        try {
                            if(dataObject.has("totalOrders") || dataObject.optString("totalOrders") !=null)
                            {
                                totalOrdersObject = dataObject.getJSONObject("totalOrders");
                            }
                            totalRevenues.setQuotedThisMonth("\u20B9"+formatChange(totalOrdersObject.optInt("totalOrders")));
                            totalRevenues.setCompletedOrders(formatChange(totalOrdersObject.optInt("completed")));
                            totalRevenues.setTotalRevenuesThisMonth("\u20B9"+formatChange(dataObject.optInt("totalRevenue")));
                            totalRevenues.setTotalFranchiese(dataObject.optString("totalChefs"));
                            totalRevenues.setTotalInvoices("\u20B9"+dataObject.optString("outstandingInvoice"));

                            objectList.add(totalRevenues);

                            TopFranchise topFranchise = new TopFranchise();
                            JSONObject topOneFranchiseeObject = new JSONObject();
                            if(dataObject.has("topChef")  || dataObject.optString("topChef") !=null)
                            {
                                topOneFranchiseeObject = dataObject.getJSONObject("topChef");
                            }
                            topFranchise.setFranchiseName(topOneFranchiseeObject.optString("name"));
                            topFranchise.setFranchiseLocation(topOneFranchiseeObject.optString("location"));
                            topFranchise.setRatings(topOneFranchiseeObject.optString("rating"));
                            topFranchise.setRevenuePercent(topOneFranchiseeObject.optString("revenuePercent"));
                            objectList.add(topFranchise);


                            PieChartData pieChartData = new PieChartData();
                            objectList.add(pieChartData);

                            BarChartData barChartData = new BarChartData();
                            objectList.add(barChartData);

                            BubbleChartDataModel bubbleChart = new BubbleChartDataModel();
                            objectList.add(bubbleChart);


                            homeDashboardAdapter = new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
                            recyclerView.setAdapter(homeDashboardAdapter);


                            getFranchiseeDashboardNearByCloudKitchens();
                        }
                        catch (Exception e)
                        {
                            PieChartData pieChartData = new PieChartData();
                            objectList.add(pieChartData);

                            BarChartData barChartData = new BarChartData();
                            objectList.add(barChartData);

                            BubbleChartDataModel bubbleChart = new BubbleChartDataModel();
                            objectList.add(bubbleChart);


                            homeDashboardAdapter = new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
                            recyclerView.setAdapter(homeDashboardAdapter);


                            getFranchiseeDashboardNearByCloudKitchens();
                            e.printStackTrace();
                        }



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"franchisee_dashboard_taq");
    }

    public void getFranchiseeDashboardNearByCloudKitchens()
    {

        String url = APIBaseURL.franchiseeDashboardGetCloudKitchen+"5ece8c07a18aea3628105f88";

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    NearByCloudKitchenList mainNearByCloudKitchenList = new NearByCloudKitchenList();

                    List<CloudKitchen> nearByCloudKitchenList = new ArrayList<>();
                    for (int i=0;i < dataArray.length();i++)
                    {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        JSONObject cloudKitchenLocationObject = new JSONObject();

                        if(dataObject.has("cloudKitchenLocation"))
                        {
                            cloudKitchenLocationObject = dataObject.getJSONObject("cloudKitchenLocation");
                        }

                        CloudKitchen cloudKitchen=new CloudKitchen();
                        cloudKitchen.setName(dataObject.optString("cloudKitchenName"));
                        cloudKitchen.setLocation(cloudKitchenLocationObject.optString("name"));
                        cloudKitchen.setId(dataObject.optString("cloudKitchenId"));
                        nearByCloudKitchenList.add(cloudKitchen);

                    }

                    mainNearByCloudKitchenList.setCloudKitchenList(nearByCloudKitchenList);
                    objectList.add(mainNearByCloudKitchenList);

                    homeDashboardAdapter=new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
                    recyclerView.setAdapter(homeDashboardAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutErrorHome");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthErrorHome");
//                } else if (error instanceof ServerError) {
//                    //TODO
//                    Log.d("Error","ServerErrorHome");
//                    getSubscriptions();
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error","Network ErrorHome");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error","ParseErrorHome");
//                }
            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"near_cloud_kitchen_taq");
    }

    //Static Data For Franchisee And Platform

    public void getCardDetailsForPlatform()
    {
        TotalRevenues totalRevenues = new TotalRevenues();
        totalRevenues.setQuotedThisMonth("\u20B9"+ 20);
        totalRevenues.setTotalRevenuesThisMonth("\u20B9"+30);
        totalRevenues.setTotalFranchiese("60");
        totalRevenues.setTotalInvoices("\u20B9"+ 40);

        objectList.add(totalRevenues);

        TopFranchise topFranchise = new TopFranchise();

        topFranchise.setFranchiseName("Franchisee");
        topFranchise.setFranchiseLocation("Kammanahalli");
        topFranchise.setCloudKitchenCount(10 +" Cloud Kitchen");
        topFranchise.setQuoteCount(15+" Quotes");
        topFranchise.setRevenuePercent("30");
        objectList.add(topFranchise);


        PieChartData pieChartData = new PieChartData();
        objectList.add(pieChartData);

        BarChartData barChartData = new BarChartData();
        objectList.add(barChartData);


        FranchiseList mainFranchisesList = new FranchiseList();

        List<Franchise> franchiseList = new ArrayList<>();
        Franchise franchise = new Franchise();
        franchise.setName("Franchisee");
        franchise.setLocation("Kammanahalli");
        franchise.setDate("12 Jul 2020");
        franchise.setAmount("\u20B9"+30);
        franchiseList.add(franchise);
        franchiseList.add(franchise);
        franchiseList.add(franchise);
        franchiseList.add(franchise);

        mainFranchisesList.setFranchiseList(franchiseList);
        objectList.add(mainFranchisesList);

        SubscriptionList mainSubscriptionList = new SubscriptionList();
        List<Subscriptions> subscriptionsList = new ArrayList<>();


        Subscriptions subscriptions = new Subscriptions();
        subscriptions.setClientName("Client Name");
        subscriptions.setLocation("Kammanahalli");
        subscriptions.setDate("12 Jul 2020");
        subscriptions.setStatus("In progress");
        subscriptionsList.add(subscriptions);
        subscriptionsList.add(subscriptions);
        subscriptionsList.add(subscriptions);
        subscriptionsList.add(subscriptions);
        mainSubscriptionList.setSubscriptionsList(subscriptionsList);
        objectList.add(mainSubscriptionList);



        homeDashboardAdapter = new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
        recyclerView.setAdapter(homeDashboardAdapter);
    }


    public void getCardDetailsForFranchisee()
    {
        TotalRevenues totalRevenues = new TotalRevenues();

        totalRevenues.setQuotedThisMonth("\u20B9"+30);
        totalRevenues.setCompletedOrders("110");
        totalRevenues.setTotalRevenuesThisMonth("\u20B9"+50);
        totalRevenues.setTotalFranchiese("200");
        totalRevenues.setTotalInvoices("\u20B9"+180);

        objectList.add(totalRevenues);

        TopFranchise topFranchise = new TopFranchise();

        topFranchise.setFranchiseName("Franchisee");
        topFranchise.setFranchiseLocation("Kammanahalli");
        topFranchise.setRatings("5");
        topFranchise.setRevenuePercent("45");
        objectList.add(topFranchise);


        PieChartData pieChartData = new PieChartData();
        objectList.add(pieChartData);

        BarChartData barChartData = new BarChartData();
        objectList.add(barChartData);

        BubbleChartDataModel bubbleChart = new BubbleChartDataModel();
        objectList.add(bubbleChart);


        NearByCloudKitchenList mainNearByCloudKitchenList = new NearByCloudKitchenList();

        List<CloudKitchen> nearByCloudKitchenList = new ArrayList<>();

        CloudKitchen cloudKitchen=new CloudKitchen();
        cloudKitchen.setName("CloudKitchen");
        cloudKitchen.setLocation("Bangalore");
        cloudKitchen.setId("1");
        nearByCloudKitchenList.add(cloudKitchen);
        nearByCloudKitchenList.add(cloudKitchen);
        nearByCloudKitchenList.add(cloudKitchen);
        nearByCloudKitchenList.add(cloudKitchen);

        mainNearByCloudKitchenList.setCloudKitchenList(nearByCloudKitchenList);
        objectList.add(mainNearByCloudKitchenList);

        homeDashboardAdapter=new HomeDashboardAdapter(getActivity(),objectList,fontBold,fontRegular);
        recyclerView.setAdapter(homeDashboardAdapter);

    }

}
