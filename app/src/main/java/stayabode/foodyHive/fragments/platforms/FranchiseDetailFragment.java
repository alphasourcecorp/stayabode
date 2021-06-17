package stayabode.foodyHive.fragments.platforms;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.adapters.platform.DeliveryPartnerFranchiseAvailableAdapters;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.DeliveryPartner;
import stayabode.foodyHive.models.UserContact;
import stayabode.foodyHive.models.UserEmail;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.github.mikephil.charting.charts.BarChart;
import stayabode.github.mikephil.charting.components.Legend;
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

import java.util.ArrayList;
import java.util.List;

public class FranchiseDetailFragment extends Fragment {

    ImageView profileImage;
    TextView franchiseeName;
    TextView city;
    TextView status;
    TextView quickStatsHeader;
    TextView chefsCount;
    TextView chefsHeader;
    TextView cloudKitchenCount;
    TextView cloudKitchenHeader;
    TextView franchiseDetailheader;
    TextView locationHeader;
    TextView locationValue;
    TextView addressHeader;
    TextView addressLine1Value;
    TextView addressLine2Value;
    TextView addressLine3Value;
    TextView pincodeHeader;
    TextView pinCodeValue;
    TextView stateHeader;
    TextView stateValue;
    TextView countryHeader;
    TextView countryValue;
    TextView phoneHeader;
    //TextView phoneValue;
    TextView emailHeader;
    //TextView emailValue;
    TextView pricingModelHeader;
    TextView memberShipStatusValue;
    TextView amountValue;
    TextView availableDeliveryHeader;
    TextView transactionGSTValue;
    RecyclerView recyclerView;
    RecyclerView recyclerViewPhone;
    RecyclerView recyclerViewEmail;


    Typeface fontBold;
    Typeface fontRegular;
    List<DeliveryPartner> deliveryPartnerList = new ArrayList<>();
    List<Object> contactList = new ArrayList<>();
    List<Object> emailList = new ArrayList<>();

    TextView pagetitle;
    private BarChart chart;
    TextView header;
    TextView back;
    NestedScrollView bodyLayout;

    ProgressBar progressBar;
    LinearLayout noInternetLayout;
    Button retryBtn;
    TextView text;
    ImageView noInternetImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.franchise_profile_details, container, false);
        fontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Regular.ttf");
//        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black);
//        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
            MainActivity.toolbar.setNavigationIcon(null);
            MainActivity.customIcon.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);
            MainActivity.toolbar_save.setText("< Back");
            MainActivity.toolbar_save.setTypeface(fontBold);
            MainActivity.customIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.active = this;
        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("Franchisee Profile");
        pagetitle.setTypeface(fontBold);

        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });


        recyclerViewPhone = rootView.findViewById(R.id.recyclerViewPhone);
        recyclerViewPhone.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEmail=rootView.findViewById(R.id.recyclerViewEmail);
        recyclerViewEmail.setLayoutManager(new LinearLayoutManager(getContext()));
        profileImage = rootView.findViewById(R.id.profileImage);
        chart = rootView.findViewById(R.id.barChart);
        header = rootView.findViewById(R.id.header);
        franchiseeName = rootView.findViewById(R.id.franchiseeName);
        city = rootView.findViewById(R.id.city);
        status = rootView.findViewById(R.id.status);
        quickStatsHeader = rootView.findViewById(R.id.quickStatsHeader);
        chefsCount = rootView.findViewById(R.id.chefsCount);
        chefsHeader = rootView.findViewById(R.id.chefsHeader);
        cloudKitchenCount = rootView.findViewById(R.id.cloudKitchenCount);
        cloudKitchenHeader = rootView.findViewById(R.id.cloudKitchenHeader);
        franchiseDetailheader = rootView.findViewById(R.id.franchiseDetailheader);
        locationHeader = rootView.findViewById(R.id.locationHeader);
        locationValue = rootView.findViewById(R.id.locationValue);
        addressHeader = rootView.findViewById(R.id.addressHeader);
        addressLine1Value = rootView.findViewById(R.id.addressLine1Value);
        addressLine2Value = rootView.findViewById(R.id.addressLine2Value);
        addressLine3Value = rootView.findViewById(R.id.addressLine3Value);
        pincodeHeader = rootView.findViewById(R.id.pincodeHeader);
        pinCodeValue = rootView.findViewById(R.id.pinCodeValue);
        stateHeader = rootView.findViewById(R.id.stateHeader);
        stateValue = rootView.findViewById(R.id.stateValue);
        countryHeader = rootView.findViewById(R.id.countryHeader);
        countryValue = rootView.findViewById(R.id.countryValue);
        phoneHeader = rootView.findViewById(R.id.phoneHeader);
        //phoneValue = rootView.findViewById(R.id.phoneValue);
        emailHeader = rootView.findViewById(R.id.emailHeader);
        //emailValue = rootView.findViewById(R.id.emailValue);
        pricingModelHeader = rootView.findViewById(R.id.pricingModelHeader);
        memberShipStatusValue = rootView.findViewById(R.id.memberShipStatusValue);
        amountValue = rootView.findViewById(R.id.amountValue);
        transactionGSTValue = rootView.findViewById(R.id.transactionGSTValue);
        transactionGSTValue.setText("< 50,000 Transactions\n25%\n18 % GST Extra");
        availableDeliveryHeader = rootView.findViewById(R.id.availableDeliveryHeader);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        bodyLayout = rootView.findViewById(R.id.bodyLayout);
        progressBar = rootView.findViewById(R.id.progressBar);
        bodyLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        noInternetLayout.setVisibility(View.GONE);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        text=rootView.findViewById(R.id.text);
        noInternetImage=rootView.findViewById(R.id.noInternetImage);

        franchiseDetailheader.setTypeface(fontBold);
        franchiseeName.setTypeface(fontBold);
        city.setTypeface(fontRegular);
        status.setTypeface(fontRegular);
        quickStatsHeader.setTypeface(fontBold);
        chefsCount.setTypeface(fontBold);
        chefsHeader.setTypeface(fontRegular);
        cloudKitchenCount.setTypeface(fontBold);
        cloudKitchenHeader.setTypeface(fontRegular);
        locationHeader.setTypeface(fontBold);
        addressHeader.setTypeface(fontBold);
        pincodeHeader.setTypeface(fontBold);
        stateHeader.setTypeface(fontBold);
        countryHeader.setTypeface(fontBold);
        phoneHeader.setTypeface(fontBold);
        emailHeader.setTypeface(fontBold);
        locationValue.setTypeface(fontRegular);
        addressLine1Value.setTypeface(fontRegular);
        addressLine2Value.setTypeface(fontRegular);
        addressLine3Value.setTypeface(fontRegular);
        pinCodeValue.setTypeface(fontRegular);
        stateValue.setTypeface(fontRegular);
        countryValue.setTypeface(fontRegular);
        //phoneValue.setTypeface(fontRegular);
        //emailValue.setTypeface(fontRegular);
        pricingModelHeader.setTypeface(fontBold);
        memberShipStatusValue.setTypeface(fontRegular);
        amountValue.setTypeface(fontBold);
        transactionGSTValue.setTypeface(fontRegular);
        availableDeliveryHeader.setTypeface(fontBold);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        chart.getDescription().setEnabled(false);
        header.setTypeface(fontBold);

        chart.animateY(5000);
//        chart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);

        chart.setDrawGridBackground(false);


        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        //l.setTypeface(tfLight);
//        l.setYOffset(0f);
//        l.setXOffset(10f);
//        l.setYEntrySpace(0f);
        l.setTextSize(8f);

//        XAxis xAxis = chart.getXAxis();
//        xAxis.setTypeface(tfLight);
//        xAxis.setGranularity(1f);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return String.valueOf((int) value);
//            }
//        });
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setSpaceTop(35f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        //  chart.getAxisRight().setEnabled(false);
        //chart.getAxisLeft().setEnabled(false);
        //chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.setGridBackgroundColor(128);
        chart.setBorderColor(255);
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        chart.setDrawGridBackground(true);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.setPinchZoom(false);
        //  chart.setDescription("");
        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getXAxis().setEnabled(true);
        chart.setDrawGridBackground(true);//enable this too
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(true);//enable for grid line
        //  chart.get().setDrawGridLines(false);//disable vertical line
        chart.getXAxis().setDrawAxisLine(false);
        chart.invalidate();

//        setBarChartData(chart);
        getBarChartDat(chart);


        getFrachiseDetailAPI();
//        deliveryPartnerList = new ArrayList<>();
//
//        DeliveryPartner deliveryPartner = new DeliveryPartner();
//        deliveryPartner.setDistanceTime("3 Sec");
//        deliveryPartner.setName("Delivery Partner 1");
//        deliveryPartner.setTotalPersons("45 Persons");
//        deliveryPartner.setUnitKG("15 Kg");
//        deliveryPartnerList.add(deliveryPartner);
//        deliveryPartnerList.add(deliveryPartner);
//        deliveryPartnerList.add(deliveryPartner);
//        deliveryPartnerList.add(deliveryPartner);
//
//
//        recyclerView.setAdapter(new DeliveryPartnerFranchiseAvailableAdapters(getContext(),deliveryPartnerList,fontBold,fontRegular));
        return rootView;
    }

    private void setBarChartData(BarChart chart) {
        float groupSpace = 0.10f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.1f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        int groupCount = 12;
        int startYear = 1;
        int endYear = startYear + groupCount;


        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        for (int i = 1; i < 12; i++) {
            values1.add(new BarEntry(i, i));
            values2.add(new BarEntry(i, i));
        }

        BarDataSet set1, set2/*, set3, set4*/;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);

            set1.setValues(values1);
            set2.setValues(values2);

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "2019");
            set1.setColor(Color.rgb(12, 162, 38));
            set2 = new BarDataSet(values2, "2020");
            set2.setColor(Color.rgb(249, 186, 13));
//            set3 = new BarDataSet(values3, "");
//            set3.setColor(Color.rgb(242, 247, 158));
//            set4 = new BarDataSet(values4, "Company D");
//            set4.setColor(Color.rgb(255, 102, 0));

            BarData data = new BarData(set1, set2/*, set3, set4*/);
            // data.setValueFormatter(new LargeValueFormatter());
            //data.setValueTypeface(tfLight);

            chart.setData(data);
        }

        // specify the width each bar should have
        chart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        chart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();
    }

    public void getBarChartDat(BarChart barChart) {


//        Float barWidth = new  Float();
//        Float barSpace = new  Float();
//        Float groupSpace: Float
//        val groupCount = 12

        float barWidth = 0.18f;
        float barSpace = 0.07f;
        float groupSpace = 0.50f;

        String url = APIBaseURL.revenueByYear;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseBar", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    List<String> xAxisValues = new ArrayList<String>();
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
                    BarDataSet barDataSet1 = new BarDataSet(yValueGroup1, "2019");
                    BarDataSet barDataSet2 = new BarDataSet(yValueGroup2, "2020");

                    JSONObject dataObjectFirst = dataArray.getJSONObject(0);
                    JSONArray nineteenMonthArray = dataObjectFirst.getJSONArray("month");
                    for (int i = 0; i < nineteenMonthArray.length(); i++) {
                        JSONObject nineteenMonthObject = nineteenMonthArray.getJSONObject(i);
                        if (nineteenMonthObject.optString("Amount").equals("")) {

                        } else {
                            yValueGroup1.add(new BarEntry(Float.parseFloat(nineteenMonthObject.optString("Amount")), Float.parseFloat(nineteenMonthObject.optString("Amount"))));
                        }

                    }


                    JSONObject dataObjectSecond = dataArray.getJSONObject(1);
                    JSONArray twentyyMonthArray = dataObjectSecond.getJSONArray("month");
                    for (int i = 0; i < twentyyMonthArray.length(); i++) {
                        JSONObject twentyMonthObject = twentyyMonthArray.getJSONObject(i);
                        if (twentyMonthObject.optString("Amount").equals("")) {

                        } else {
                            yValueGroup2.add(new BarEntry(Float.parseFloat(twentyMonthObject.optString("Amount")), Float.parseFloat(twentyMonthObject.optString("Amount"))));
                        }

                    }


                    barDataSet1 = new BarDataSet(yValueGroup1, "");
                    barDataSet1.setColors(getContext().getResources().getColor(R.color.colorGreen));
                    barDataSet1.setLabel(dataArray.getJSONObject(1).optString("year"));
                    barDataSet1.setDrawIcons(false);
                    barDataSet1.setDrawValues(false);


                    barDataSet2 = new BarDataSet(yValueGroup2, "");
                    barDataSet2.setLabel(dataArray.getJSONObject(1).optString("year"));
                    barDataSet2.setColors(getContext().getResources().getColor(R.color.colorPrimaryDark));
                    barDataSet2.setDrawIcons(false);
                    barDataSet2.setDrawValues(false);

                    BarData barData = new BarData(barDataSet1, barDataSet2);

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
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error", "TimeoutErrorBar");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error", "AuthErrorBar");
//                } else if (error instanceof ServerError) {
//                    //TODO
//                    Log.d("Error", "ServerErrorBar");
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error", "Network ErrorBar");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error", "ParseErrorBar");
//                }
            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "home_bar_data");


//        List<String> xAxisValues =new  ArrayList<String>();
//        xAxisValues.add("Jan");
//        xAxisValues.add("Feb");
//        xAxisValues.add("Mar");
//        xAxisValues.add("Apr");
//        xAxisValues.add("May");
//        xAxisValues.add("June");
//        xAxisValues.add("Jul");
//        xAxisValues.add("Aug");
//        xAxisValues.add("Sep");
//        xAxisValues.add("Oct");
//        xAxisValues.add("Nov");
//        xAxisValues.add("Dec");
//
//        List<BarEntry> yValueGroup1 = new ArrayList<BarEntry>();
//        List<BarEntry> yValueGroup2 = new ArrayList<BarEntry>();
//
//        // draw the graph
//        BarDataSet barDataSet1 = new BarDataSet(yValueGroup1,"2019");
//        BarDataSet barDataSet2 = new  BarDataSet(yValueGroup2,"2020");
//
//
//        yValueGroup1.add(new BarEntry(1f, Float.parseFloat(String.valueOf(9)), Float.parseFloat(String.valueOf(3))));
//        yValueGroup2.add(new BarEntry(1f, Float.parseFloat(String.valueOf(2)), Float.parseFloat(String.valueOf(7))));
//
//
//        yValueGroup1.add(new BarEntry(2f, Float.parseFloat(String.valueOf(3)), Float.parseFloat(String.valueOf(3))));
//        yValueGroup2.add(new BarEntry(2f, Float.parseFloat(String.valueOf(4)), Float.parseFloat(String.valueOf(15))));
//
//        yValueGroup1.add(new BarEntry(3f, Float.parseFloat(String.valueOf(3)), Float.parseFloat(String.valueOf(3))));
//        yValueGroup2.add(new BarEntry(3f, Float.parseFloat(String.valueOf(4)), Float.parseFloat(String.valueOf(15))));
//
//        yValueGroup1.add(new BarEntry(4f, Float.parseFloat(String.valueOf(3)), Float.parseFloat(String.valueOf(3))));
//        // yValueGroup2.add(new BarEntry(4f, Float.parseFloat(String.valueOf(4)), Float.parseFloat(String.valueOf(15))));
//
//
//        yValueGroup1.add(new BarEntry(5f, Float.parseFloat(String.valueOf(9)), Float.parseFloat(String.valueOf(3))));
//        // yValueGroup2.add(new BarEntry(5f, Float.parseFloat(String.valueOf(10)), Float.parseFloat(String.valueOf(6))));
//
//        yValueGroup1.add(new BarEntry(6f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(1))));
//        //  yValueGroup2.add(new BarEntry(6f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(2))));
//
//
//        yValueGroup1.add(new BarEntry(7f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(7))));
//        // yValueGroup2.add(new BarEntry(7f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(12))));
//
//
//        yValueGroup1.add(new BarEntry(8f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(9))));
//        // yValueGroup2.add(new BarEntry(8f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(8))));
//
//
//        yValueGroup1.add(new BarEntry(9f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(13))));
//        //  yValueGroup2.add(new BarEntry(9f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(12))));
//
//        yValueGroup1.add(new BarEntry(10f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(2))));
//        // yValueGroup2.add(new BarEntry(10f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(7))));
//
//        yValueGroup1.add(new BarEntry(11f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(6))));
//        // yValueGroup2.add(new BarEntry(11f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(5))));
//
//        yValueGroup1.add(new BarEntry(12f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(2))));
//        // yValueGroup2.add(new BarEntry(12f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(3))));
//
//
//        barDataSet1 = new BarDataSet(yValueGroup1, "");
//        barDataSet1.setColors(getResources().getColor(R.color.colorGreen));
//        barDataSet1.setLabel("2019");
//        barDataSet1.setDrawIcons(false);
//        barDataSet1.setDrawValues(false);
//
//
//
//        barDataSet2 = new BarDataSet(yValueGroup2, "");
//
//        barDataSet2.setLabel("2020");
//        barDataSet2.setColors(getResources().getColor(R.color.colorPrimaryDark));
//
//        barDataSet2.setDrawIcons(false);
//        barDataSet2.setDrawValues(false);
//
//        BarData barData = new  BarData(barDataSet1, barDataSet2);
//
//        barChart.getDescription().setEnabled(false);
//        barChart.getDescription().setTextSize(0f);
//        barData.setValueFormatter(new LargeValueFormatter());
//        barChart.setData(barData);
//        barChart.getBarData().setBarWidth(barWidth);
//        barChart.getXAxis().setAxisMinimum(0f);
//        barChart.getXAxis().setAxisMaximum(12f);
//        barChart.groupBars(0f, groupSpace, barSpace);
//        barChart.setFitBars(true);
//        barChart.getData().setHighlightEnabled(false);
//        barChart.invalidate();
//
//        // set bar label
////        Legend legend = new barChart.getLegend();
////        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
////        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
////        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
////        legend.setDrawInside(false);
//
////        List<LegendEntry> legenedEntries = new ArrayList<>();
////
////        legenedEntries.add(LegendEntry("2016", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.RED));
////        legenedEntries.add(LegendEntry("2017", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.YELLOW));
////
////        legend.setCustom(legenedEntries);
//
////        legend.setYOffset(2f);
////        legend.setXOffset(2f);
////        legend.setYEntrySpace(0f);
////        legend.setTextSize(5f);
//
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setDrawGridLines(false);
//        //xAxis.textSize = 9f
//
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
//
//        xAxis.setLabelCount(12);
//        xAxis.mAxisMaximum = 12f;
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setAvoidFirstLastClipping(true);
////        xAxis.spaceMin = 4f
////        xAxis.spaceMax = 4f
//
//        barChart.setVisibleXRangeMaximum(12f);
//        barChart.setVisibleXRangeMinimum(12f);
//        barChart.setDragEnabled(true);
//
//        //Y-axis
//        barChart.getAxisRight().setEnabled(false);
//        barChart.setScaleEnabled(true);
//
//        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setSpaceTop(1f);
//        leftAxis.setAxisMinimum(0f);
//
//
//        barChart.setData(barData);
//        barChart.setVisibleXRange(1f, 12f);
    }

    public void onBackPressed() {
//        navigation.setVisibility(View.VISIBLE);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    public void getFrachiseDetailAPI() {

        bodyLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
        //recyclerView.setVisibility(View.GONE);
        String url = APIBaseURL.franchiseProfileID + getArguments().getString("Id");


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                bodyLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONObject franchiseObject = dataObject.getJSONObject("franchise");

                    franchiseeName.setText(franchiseObject.optString("name"));
                    Glide.with(getContext()).load(franchiseObject.optString("uploadLogo").replaceAll(" ", "%20")).placeholder(R.drawable.foodi_logo_left_image).into(profileImage);
                    city.setText(franchiseObject.optString("location"));

                    locationValue.setText(franchiseObject.optString("location"));
                    addressLine1Value.setText(franchiseObject.optString("addressLine1"));
                    addressLine2Value.setText(franchiseObject.optString("addressLine2"));
                    addressLine3Value.setText(franchiseObject.optString("addressLine2"));
                    pinCodeValue.setText(franchiseObject.optString("pinCode"));
                    stateValue.setText(franchiseObject.optString("state"));
                    countryValue.setText(franchiseObject.optString("country"));
//                    phoneValue.setText(franchiseObject.optString("contact"));


                    //emailValue.setText(franchiseObject.optString("email"));
                    chefsCount.setText(franchiseObject.optString("numberOfChefs"));
                    cloudKitchenCount.setText(franchiseObject.optString("numberOfCloudKitchens"));
                    if (franchiseObject.optString("isActive").equals("Active")) {
                        status.setText("Active");
                        status.setBackground(getResources().getDrawable(R.drawable.rounded_corner_border));
                    } else {
                        status.setText("Inactive");
                        status.setBackground(getResources().getDrawable(R.drawable.rounded_corner_border_inactive));
                    }

                    JSONArray deliveryPartnersArray = franchiseObject.getJSONArray("deliveryPartner");

                    deliveryPartnerList = new ArrayList<>();

                    for (int i = 0; i < deliveryPartnersArray.length(); i++) {
                        JSONObject deliveryPartnerObject = deliveryPartnersArray.getJSONObject(i);
                        DeliveryPartner deliveryPartner = new DeliveryPartner();
                        deliveryPartner.setDistanceTime(deliveryPartnerObject.optString("deliveryPartnerResponseTime") + " Sec");
                        deliveryPartner.setName(deliveryPartnerObject.optString("deliveryPartnerName"));
                        deliveryPartner.setTotalPersons(deliveryPartnerObject.optString("deliveryPartnerNumberOfDeliveryPersons") + " Persons");
                        deliveryPartner.setUnitKG(deliveryPartnerObject.optString("deliveryPartnerMaxWeight") + " Kg");
                        deliveryPartnerList.add(deliveryPartner);
                    }

                    JSONObject pricingModelObject = franchiseObject.getJSONObject("choosePricingModel");

                    amountValue.setText("\u20B9 " + pricingModelObject.optString("amount"));
                    memberShipStatusValue.setText(pricingModelObject.optString("pricing"));
                    transactionGSTValue.setText("< " + pricingModelObject.optString("transactions") + " Transactions\n" + pricingModelObject.optString("transactionPercentage") + "%\n" + pricingModelObject.optString("gstExtra") + " % GST Extra");
                    recyclerView.setAdapter(new DeliveryPartnerFranchiseAvailableAdapters(getContext(), deliveryPartnerList, fontBold, fontRegular));

                    JSONArray contactArray = franchiseObject.getJSONArray("contact");
                    contactList = new ArrayList<>();

                    for (int i = 0; i < contactArray.length(); i++) {
                        UserContact userContact = new UserContact();
                        userContact.setContactNumber(String.valueOf(contactArray.getString(i)));
                        contactList.add(userContact);
                    }
                    recyclerViewPhone.setAdapter(new AllListAdapter(getContext(), contactList, fontBold, fontRegular));

                    JSONArray emailArray = franchiseObject.getJSONArray("email");
                    emailList = new ArrayList<>();
                    for (int i = 0; i < emailArray.length(); i++) {
                        UserEmail userEmail=new UserEmail();
                        userEmail.setEmail(String.valueOf(emailArray.getString(i)));
                        emailList.add(userEmail);
                    }
                    recyclerViewEmail.setAdapter(new AllListAdapter(getContext(),emailList,fontBold,fontRegular));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bodyLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                noInternetLayout.setVisibility(View.VISIBLE);
                retryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noInternetLayout.setVisibility(View.GONE);
                        getFrachiseDetailAPI();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error", "TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error", "AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("Franchisee profile details not available!");
//                    retryBtn.setVisibility(View.GONE);
//                    //TODO
//                    Log.d("Error", "ServerError");
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error", "Network Error");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error", "ParseError");
//                }


            }
        },getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "detail_taq_franchise");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
    }
}
