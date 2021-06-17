package stayabode.foodyHive.fragments.chefs;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.MenuCategoryAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.ChefRewards;
import stayabode.foodyHive.models.WeekDays;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import stayabode.github.mikephil.charting.charts.BarChart;
import stayabode.github.mikephil.charting.charts.CombinedChart;
import stayabode.github.mikephil.charting.components.XAxis;
import stayabode.github.mikephil.charting.components.YAxis;
import stayabode.github.mikephil.charting.data.BarData;
import stayabode.github.mikephil.charting.data.BarDataSet;
import stayabode.github.mikephil.charting.data.BarEntry;
import stayabode.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import stayabode.github.mikephil.charting.formatter.LargeValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class ChefProfileFragment extends Fragment {

    public static Typeface fontBold;
    public static Typeface fontRegular;
    public static Typeface raleWayFontBold;
    public static Typeface ralewayFontRegular;

    public ChefProfileFragment chefProfileFragment;

    TextView chefName;
    TextView cityname;
    TextView ratingCount;
    TextView status;
    TextView subscriptionHeader;
    TextView subscriptionValue;
    TextView orderHeader;
    TextView orderValue;
    TextView header_accolades;
    TextView professionalDetailHeader;
    TextView dayAvailableHeader;
    TextView workingTimeHeader;
    TextView workingHoursValue;
    TextView workingTimeValue;
    TextView timeOftheDayHeader;
    TextView startDateHeader;
    TextView startDateValue;
    TextView franchiseeHeader;
    TextView franchiseeValue;
    TextView gstHeader;
    TextView gstValue;
    TextView panHeader;
    TextView panValue;
    TextView fssaiHeader;
    TextView fssaiValue;
    TextView renewalDateHeader;
    TextView renewalValue;
    TextView kitchenTypeeHeader;
    TextView kitchenTypeValue;
    TextView aboutUsValue;
    TextView aboutUs;
    RatingBar ratingBar;
    ImageView editProfileBtn;
    ImageView profileImageView;
    ImageView chefCapImage;
    ImageView editImageView;
    RecyclerView rewardsRecyclerView;
    RecyclerView daysRecyclerView;

    Context context;

    View chefRevenueCard;
    CombinedChart combinedChart;
    BarChart barChart;

    protected final String[] months = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    List<Object> daysListObject = new ArrayList<>();


    List<WeekDays> daysListArray = new ArrayList<>();


    public static List<WeekDays> selectedWeekDaysforAddInViewProfile = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chef_profile_detail, container, false);
        chefProfileFragment=this;
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        raleWayFontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");
        ralewayFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
        ChefsMainActivity.searchLayout.setVisibility(View.GONE);
        ChefsMainActivity.chefnavigation.setVisibility(View.GONE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.GONE);
        cheftoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        chefName = rootView.findViewById(R.id.chefName);
        cityname = rootView.findViewById(R.id.cityname);
        ratingBar = rootView.findViewById(R.id.ratingBar);
        ratingCount = rootView.findViewById(R.id.ratingCount);
        editProfileBtn = rootView.findViewById(R.id.editProfileBtn);
        profileImageView = rootView.findViewById(R.id.profileImageView);
        chefCapImage = rootView.findViewById(R.id.chefCapImage);
        status = rootView.findViewById(R.id.status);
        subscriptionHeader = rootView.findViewById(R.id.subscriptionHeader);
        subscriptionValue = rootView.findViewById(R.id.subscriptionValue);
        orderHeader = rootView.findViewById(R.id.orderHeader);
        orderValue = rootView.findViewById(R.id.orderValue);
        header_accolades = rootView.findViewById(R.id.header_accolades);
        professionalDetailHeader = rootView.findViewById(R.id.professionalDetailHeader);
        rewardsRecyclerView = rootView.findViewById(R.id.rewardsRecyclerView);
        rewardsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        editImageView = rootView.findViewById(R.id.editImageView);
        dayAvailableHeader = rootView.findViewById(R.id.dayAvailableHeader);
        daysRecyclerView = rootView.findViewById(R.id.daysRecyclerView);
        daysRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        workingTimeHeader = rootView.findViewById(R.id.workingTimeHeader);
        workingHoursValue = rootView.findViewById(R.id.workingHoursValue);
        timeOftheDayHeader = rootView.findViewById(R.id.timeOftheDayHeader);
        workingTimeValue = rootView.findViewById(R.id.workingTimeValue);
        startDateHeader = rootView.findViewById(R.id.startDateHeader);
        startDateValue = rootView.findViewById(R.id.startDateValue);
        franchiseeHeader = rootView.findViewById(R.id.franchiseeHeader);
        franchiseeValue = rootView.findViewById(R.id.franchiseeValue);
        gstHeader = rootView.findViewById(R.id.gstHeader);
        gstValue = rootView.findViewById(R.id.gstValue);
        panHeader = rootView.findViewById(R.id.panHeader);
        panValue = rootView.findViewById(R.id.panValue);
        fssaiHeader = rootView.findViewById(R.id.fssaiHeader);
        fssaiValue = rootView.findViewById(R.id.fssaiValue);
        renewalDateHeader = rootView.findViewById(R.id.renewalDateHeader);
        renewalValue = rootView.findViewById(R.id.renewalValue);
        kitchenTypeeHeader = rootView.findViewById(R.id.kitchenTypeeHeader);
        kitchenTypeValue = rootView.findViewById(R.id.kitchenTypeValue);
        aboutUs = rootView.findViewById(R.id.aboutUs);
        aboutUsValue = rootView.findViewById(R.id.aboutUsValue);

        chefRevenueCard = rootView.findViewById(R.id.chef_revenue_card);
        chefRevenueCard.setElevation(0f);

        combinedChart = rootView.findViewById(R.id.chart);
        barChart = rootView.findViewById(R.id.barChart);


        chefName.setTypeface(fontBold);
        cityname.setTypeface(fontRegular);
        ratingCount.setTypeface(fontRegular);
        status.setTypeface(fontRegular);
        subscriptionHeader.setTypeface(fontRegular);
        subscriptionValue.setTypeface(fontRegular);
        orderHeader.setTypeface(fontRegular);
        orderValue.setTypeface(fontRegular);
        header_accolades.setTypeface(raleWayFontBold);
        professionalDetailHeader.setTypeface(raleWayFontBold);
        dayAvailableHeader.setTypeface(fontBold);

        workingTimeHeader.setTypeface(fontBold);
        timeOftheDayHeader.setTypeface(fontBold);
        startDateHeader.setTypeface(fontBold);
        franchiseeHeader.setTypeface(fontBold);
        gstHeader.setTypeface(fontBold);
        panHeader.setTypeface(fontBold);
        fssaiHeader.setTypeface(fontBold);
        aboutUs.setTypeface(fontBold);
        renewalDateHeader.setTypeface(fontBold);
        kitchenTypeeHeader.setTypeface(fontBold);

        workingHoursValue.setTypeface(fontRegular);
        workingTimeValue.setTypeface(fontRegular);
        aboutUsValue.setTypeface(fontRegular);
        startDateValue.setTypeface(fontRegular);
        franchiseeValue.setTypeface(fontRegular);
        gstValue.setTypeface(fontRegular);
        panValue.setTypeface(fontRegular);
        fssaiValue.setTypeface(fontRegular);
        renewalValue.setTypeface(fontRegular);
        kitchenTypeValue.setTypeface(fontRegular);


        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileFragment fragment = new EditProfileFragment();
                FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                ft.commit();
            }
        });

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileFragment fragment = new EditProfileFragment();
                FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                ft.commit();
            }
        });
        getDishOfValues();

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public void onBackPressed() {
        FragmentManager fm = cheffragmentManager;
        fm.popBackStack();
    }

    /**
     To Get an Chefs Dishes available week days by using this API (GET)
     **/

    public void getDishOfValues()
    {
        String url = APIBaseURL.searchOptions;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray daysArray  = new JSONArray();
                    JSONArray dishCategoriesArray = new JSONArray();
                    JSONArray dishCuisinesArray = new JSONArray();
                    JSONArray dishTypesArray = new JSONArray();

                    if(jsonObject.has("dishTypes"))
                    {
                        dishTypesArray = jsonObject.getJSONArray("dishTypes");
                    }

                    if(jsonObject.has("days"))
                    {
                        daysArray = jsonObject.getJSONArray("days");
                    }

                    if(jsonObject.has("dishCategories"))
                    {
                        dishCategoriesArray = jsonObject.getJSONArray("dishCategories");
                    }

                    if(jsonObject.has("dishCuisines"))
                    {
                        dishCuisinesArray = jsonObject.getJSONArray("dishCuisines");
                    }


                    daysListArray = new ArrayList<>();
                    for (int i=0;i < daysArray.length();i++)
                    {
                        JSONObject daysObject = daysArray.getJSONObject(i);
                        WeekDays weekDays = new WeekDays();
                        weekDays.setWeekDayName(daysObject.optString("label"));
                        weekDays.setId(daysObject.optString("value"));
                        weekDays.setIdForCheck(daysObject.optInt("value"));
                        weekDays.setSelected(false);
                        daysListObject.add(weekDays);
                        daysListArray.add(weekDays);

                    }



                    getChefProfileFromAPI();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"get_dish_taqs");
    }

    /**
     To Get Chefs  Bar Chart Revenues from this API  (GET)
     **/
    public void getBarChartDat(BarChart barChart)
    {



        float barWidth = 0.30f;
        float barSpace = 0.07f;
        float groupSpace = 0.25f;


        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String url = APIBaseURL.getChefsDashboard+""+ SaveSharedPreference.getLoggedInWorkFlowID(context)+"&currentdate="+formattedDate;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataArray = jsonObject.getJSONObject("data");
                    JSONArray annualRevenueAndOrderArray = new JSONArray();
                    if(dataArray.has("annualRevenueAndOrder"))
                    {
                        annualRevenueAndOrderArray = dataArray.getJSONArray("annualRevenueAndOrder");
                    }



                    List<String> xAxisValues =new  ArrayList<String>();
                    xAxisValues.add("Jan");
                    xAxisValues.add("Feb");
                    xAxisValues.add("Mar");
                    xAxisValues.add("Apr");
                    xAxisValues.add("May");
                    xAxisValues.add("June");
                    xAxisValues.add("Jul");
                    xAxisValues.add("Aug");
                    xAxisValues.add("Sep");
                    xAxisValues.add("Oct");
                    xAxisValues.add("Nov");
                    xAxisValues.add("Dec");

                    List<BarEntry> yValueGroup1 = new ArrayList<BarEntry>();
                    List<BarEntry> yValueGroup2 = new ArrayList<BarEntry>();

                    // draw the graph
                    BarDataSet barDataSet1 = new BarDataSet(yValueGroup1,"2019");
                    BarDataSet barDataSet2 = new  BarDataSet(yValueGroup2,"2020");

                    JSONObject dataObjectFirst = annualRevenueAndOrderArray.getJSONObject(0);
                    JSONArray nineteenMonthArray = dataObjectFirst.getJSONArray("monthlyOrderDetails");
                    for (int i=0;i < nineteenMonthArray.length();i++)
                    {
                        JSONObject nineteenMonthObject = nineteenMonthArray.getJSONObject(i);
                        if(nineteenMonthObject.optString("totalOrder").equals(""))
                        {

                        }
                        else
                        {
                            yValueGroup1.add(new BarEntry(Float.parseFloat(nineteenMonthObject.optString("totalOrder")), Float.parseFloat(nineteenMonthObject.optString("totalOrder"))));
                        }

                    }


                    JSONObject dataObjectSecond = annualRevenueAndOrderArray.getJSONObject(1);
                    JSONArray twentyyMonthArray = dataObjectSecond.getJSONArray("monthlyOrderDetails");
                    for (int i=0;i < twentyyMonthArray.length();i++)
                    {
                        JSONObject twentyMonthObject = twentyyMonthArray.getJSONObject(i);
                        if(twentyMonthObject.optString("totalOrder").equals(""))
                        {

                        }
                        else
                        {
                            yValueGroup2.add(new BarEntry(Float.parseFloat(twentyMonthObject.optString("totalOrder")), Float.parseFloat(twentyMonthObject.optString("totalOrder"))));
                        }

                    }






                    barDataSet1 = new BarDataSet(yValueGroup1, "");
                    barDataSet1.setColors(context.getResources().getColor(R.color.colorGreen));
                    barDataSet1.setLabel(annualRevenueAndOrderArray.getJSONObject(1).optString("year"));
                    barDataSet1.setDrawIcons(false);
                    barDataSet1.setDrawValues(false);



                    barDataSet2 = new BarDataSet(yValueGroup2, "");
                    barDataSet2.setLabel(annualRevenueAndOrderArray.getJSONObject(0).optString("year"));
                    barDataSet2.setColors(context.getResources().getColor(R.color.colorPrimaryDark));
                    barDataSet2.setDrawIcons(false);
                    barDataSet2.setDrawValues(false);

                    BarData barData = new  BarData(barDataSet1, barDataSet2);

                    barChart.getDescription().setEnabled(false);
                    barChart.getDescription().setTextSize(0f);
                    barData.setValueFormatter(new LargeValueFormatter());
                    barChart.setData(barData);
                    barChart.getBarData().setBarWidth(barWidth);
                    barChart.getXAxis().setAxisMinimum(0f);
                    barChart.getXAxis().setAxisMaximum(12f);
                    barChart.groupBars(0f, groupSpace, barSpace);
                    barChart.setFitBars(true);
                    barChart.getData().setHighlightEnabled(false);
                    barChart.invalidate();



                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setDrawGridLines(false);


                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

                    xAxis.setLabelCount(12);
                    xAxis.mAxisMaximum = 12f;
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setAvoidFirstLastClipping(true);

                    barChart.setVisibleXRangeMaximum(12f);
                    barChart.setVisibleXRangeMinimum(12f);
                    barChart.setDragEnabled(true);

                    //Y-axis
                    barChart.getAxisRight().setEnabled(false);
                    barChart.setScaleEnabled(true);

                    YAxis leftAxis = barChart.getAxisLeft();
                    leftAxis.setValueFormatter(new LargeValueFormatter());
                    leftAxis.setDrawGridLines(false);
                    leftAxis.setSpaceTop(1f);
                    leftAxis.setAxisMinimum(0f);


                    barChart.setData(barData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"home_bar_data");



    }


    /**
     To Get Chef Profile Detalis by using this API (GET)
     **/
    public void getChefProfileFromAPI()
    {
        String url = APIBaseURL.chefProfile+ SaveSharedPreference.getLoggedInWorkFlowID(context);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    getBarChartDat(barChart);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = new JSONArray();
                    if(jsonObject.has("data"))
                    {
                        dataArray = jsonObject.getJSONArray("data");
                    }
                    JSONObject dataObject = dataArray.getJSONObject(0);
                    try {
                        Glide.with(context).load(dataObject.optString("uploadLogo")).placeholder(R.drawable.customer_user_profile_left_menu).into(profileImageView);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    chefName.setText(dataObject.optString("name"));
                    JSONObject locationObject = new JSONObject();
                    if(dataObject.has("location"))
                    {
                        locationObject = dataObject.getJSONObject("location");
                    }
                    cityname.setText(locationObject.optString("name"));

                    workingHoursValue.setText(dataObject.optString("workingHours"));
                    workingTimeValue.setText(dataObject.optString("timeOfTheDay"));
                    orderValue.setText(dataObject.optString("totalOrders"));
                    subscriptionValue.setText(dataObject.optString("subscriptions"));
                    ratingBar.setRating(Float.parseFloat(dataObject.optString("rating")));
                    ratingCount.setText("("+dataObject.optString("ratingsCount")+")");

                    fssaiValue.setText(dataObject.optString("fssaiNumber"));
                    panValue.setText(dataObject.optString("panNumber"));
                    gstValue.setText(dataObject.optString("gstNumber"));
                    aboutUsValue.setText(dataObject.optString("aboutYou"));

                    JSONArray daysOfTheWeekArray = new JSONArray();
                    if(dataObject.has("daysOfTheWeek"))
                    {
                        daysOfTheWeekArray = dataObject.getJSONArray("daysOfTheWeek");
                    }


                    JSONArray accoladesArray = new JSONArray();
                    if(dataObject.has("accolades"))
                    {
                        accoladesArray = dataObject.getJSONArray("accolades");
                    }
                    List<Object> rewardsLists = new ArrayList<>();
                    for (int i=0;i < accoladesArray.length();i++)
                    {
                        JSONObject accoladesObject = accoladesArray.getJSONObject(i);
                        ChefRewards chefRewards = new ChefRewards();
                        chefRewards.setName(accoladesObject.optString("title"));
                        chefRewards.setDescription(accoladesObject.optString("accoladeDescription"));
                        rewardsLists.add(chefRewards);

                        rewardsRecyclerView.setAdapter(new MenuCategoryAdapter(context, rewardsLists, fontBold, fontRegular,"ViewChef"));
                    }

                    if(daysOfTheWeekArray.length()!=0)
                    {
                        ArrayList<String> stringList = new ArrayList<>();

                        for (int i=0;i < daysOfTheWeekArray.length();i++)
                        {

                            Object getValuesforAvailableDaysTypes = daysOfTheWeekArray.get(i);

                            for (int j=0;j < daysListArray.size();j++)
                            {
                                if(getValuesforAvailableDaysTypes.equals(daysListArray.get(j).getIdForCheck()))
                                {
                                    String finalResultNamesForDishTypes = daysListArray.get(j).getWeekDayName();
                                    stringList.add(finalResultNamesForDishTypes);

                                }

                            }


                        }
                        List<Object> dishTypesLists = new ArrayList<>();

                        for (int j=0;j < daysListArray.size();j++) {
                            WeekDays category5 = new WeekDays();
                            category5.setIdForCheck(daysListArray.get(j).getIdForCheck());
                            category5.setId(daysListArray.get(j).getId());
                            category5.setWeekDayName(daysListArray.get(j).getWeekDayName());
                            if(stringList.contains(daysListArray.get(j).getWeekDayName()))
                            {
                                category5.setSelected(true);
                            }
                            else
                            {
                                category5.setSelected(false);
                            }

                            selectedWeekDaysforAddInViewProfile.add(category5);
                            dishTypesLists.add(category5);

                        }


                        daysRecyclerView.setAdapter(new MenuCategoryAdapter(context,dishTypesLists,fontBold,fontRegular,"ViewChef"));

                    }
                    else
                    {
                        daysRecyclerView.setAdapter(new MenuCategoryAdapter(context,daysListObject,fontBold,fontRegular,"ViewChef"));
                    }

                    Date newDate = null;
                    Date newDate1 = null;
                    Date newDate2 = null;
                    try {
                        newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataObject.optString("dateOfBirth"));
                        newDate1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataObject.optString("startDate"));
                        newDate2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataObject.optString("renewalDate"));
                    } catch (ParseException e) {

                        newDate1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dataObject.optString("startDate"));
                        newDate2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dataObject.optString("renewalDate"));
                        e.printStackTrace();
                    }
                    try {
                        String startDate = new SimpleDateFormat("dd MMM yyyy").format(newDate1);
                        String renewalDate = new SimpleDateFormat("dd MMM yyyy").format(newDate2);
                        startDateValue.setText(startDate);
                        renewalValue.setText(renewalDate);
                    }
                    catch (Exception e)
                    {
                        startDateValue.setText("");
                        renewalValue.setText("");
                        e.printStackTrace();
                    }



                    JSONObject franchiseObject = new JSONObject();
                    if(dataObject.has("franchise"))
                    {
                        franchiseObject = dataObject.getJSONObject("franchise");
                    }

                    franchiseeValue.setText(franchiseObject.optString("franchiseName"));
                    JSONObject cloudKitchenObject = new JSONObject();
                    if(dataObject.has("cloudKitchen"))
                    {
                        cloudKitchenObject = dataObject.getJSONObject("cloudKitchen");
                    }
                    if(dataObject.optBoolean("isCloudKitchen"))
                    {
                        kitchenTypeValue.setText(cloudKitchenObject.optString("cloudKitchenName"));
                    }
                    else
                    {
                        kitchenTypeValue.setText(dataObject.optString("kitchenName"));

                    }



                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"chef_profile_page");
    }
}
