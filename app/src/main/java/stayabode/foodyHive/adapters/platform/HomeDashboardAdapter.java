package stayabode.foodyHive.adapters.platform;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.fragments.platforms.FragmentOrdersByWeek;
import stayabode.foodyHive.fragments.platforms.FragmentRevenues;
import stayabode.foodyHive.fragments.platforms.RevenueByFranchisees;
import stayabode.foodyHive.models.BarChartData;
import stayabode.foodyHive.models.BubbleChartDataModel;
import stayabode.foodyHive.models.FranchiseList;
import stayabode.foodyHive.models.NearByCloudKitchenList;
import stayabode.foodyHive.models.PieChartData;
import stayabode.foodyHive.models.SubscriptionList;
import stayabode.foodyHive.models.TopFranchise;
import stayabode.foodyHive.models.TotalRevenues;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.github.mikephil.charting.animation.Easing;
import stayabode.github.mikephil.charting.charts.BarChart;
import stayabode.github.mikephil.charting.charts.BubbleChart;
import stayabode.github.mikephil.charting.charts.PieChart;
import stayabode.github.mikephil.charting.components.Legend;
import stayabode.github.mikephil.charting.components.XAxis;
import stayabode.github.mikephil.charting.components.YAxis;
import stayabode.github.mikephil.charting.data.BarData;
import stayabode.github.mikephil.charting.data.BarDataSet;
import stayabode.github.mikephil.charting.data.BarEntry;
import stayabode.github.mikephil.charting.data.BubbleData;
import stayabode.github.mikephil.charting.data.BubbleDataSet;
import stayabode.github.mikephil.charting.data.BubbleEntry;
import stayabode.github.mikephil.charting.data.PieData;
import stayabode.github.mikephil.charting.data.PieDataSet;
import stayabode.github.mikephil.charting.data.PieEntry;
import stayabode.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import stayabode.github.mikephil.charting.formatter.LargeValueFormatter;
import stayabode.github.mikephil.charting.formatter.PercentFormatter;
import stayabode.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import stayabode.github.mikephil.charting.utils.MPPointF;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HomeDashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> objectList = new ArrayList<>();
    Context context;
    Typeface fontBold;
    Typeface fontRegular;
    ArrayList<LatLng> markerDataArrayList;

    public HomeDashboardAdapter(Context context,List<Object> objectList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.objectList = objectList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(viewType,parent,false);

        if(viewType == R.layout.home_total_revenues_adapter_item)
        {
            return new HomeDashboardsCountsItemViewHolder(view);
        }
        if(viewType == R.layout.top_franchise_adapter_item)
        {
            return new HomeTopFranchiseItemsViewHolder(view);
        }
        else if(viewType == R.layout.dashboard_home_pie_chart)
        {
            return new PieChartItemsViewHolder(view);
        }
        else if(viewType == R.layout.dashboard_home_revenue_bar_chart)
        {
            return new BarChartViewHolder(view);
        }
        else if(viewType == R.layout.home_orders_item)
        {
            return new BubbleChartItemViewHolder(view);
        }
        else if(viewType == R.layout.home_nearby_cluod_kitchens)
        {
            return new NearByCloudKitchenItemViewHolder(view);
        }
        else if(viewType == R.layout.franchise_adapter_item)
        {
            return new NewFranchiseeItemViewHolder(view);
        }
        else if(viewType == R.layout.subscriptions_adapter_item)
        {
            return new SubscriptionsViewHolder(view);
        }
        else
        {
            return new NewFranchiseeItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(objectList.get(position) instanceof TotalRevenues)
        {
            HomeDashboardsCountsItemViewHolder homeDashboardsCountsItemViewHolder = (HomeDashboardsCountsItemViewHolder)holder;

            TotalRevenues totalRevenues = (TotalRevenues)objectList.get(position);
            homeDashboardsCountsItemViewHolder.franchises.setTypeface(fontBold);
            homeDashboardsCountsItemViewHolder.franchisesHeader.setTypeface(fontBold);
            homeDashboardsCountsItemViewHolder.revenue.setTypeface(fontBold);
            homeDashboardsCountsItemViewHolder.revenueHeader.setTypeface(fontBold);
            homeDashboardsCountsItemViewHolder.revenueMonthHeader.setTypeface(fontRegular);
            homeDashboardsCountsItemViewHolder.invoice.setTypeface(fontBold);
            homeDashboardsCountsItemViewHolder.invoiceHeader.setTypeface(fontBold);
            homeDashboardsCountsItemViewHolder.quoted.setTypeface(fontBold);
            homeDashboardsCountsItemViewHolder.quotedHeader.setTypeface(fontBold);
            homeDashboardsCountsItemViewHolder.quotedMonth.setTypeface(fontRegular);
            homeDashboardsCountsItemViewHolder.outstandingMonth.setTypeface(fontRegular);





            if(MainActivity.Role.equals("Franchisee"))
            {
                homeDashboardsCountsItemViewHolder.franchisesHeader.setText("Chefs");
                homeDashboardsCountsItemViewHolder.quotedHeader.setText("Today's Orders");
                homeDashboardsCountsItemViewHolder.quotedMonth.setText(totalRevenues.getCompletedOrders() +"% Completed");
                homeDashboardsCountsItemViewHolder.franchises.setText("200");
                homeDashboardsCountsItemViewHolder.invoice.setText(totalRevenues.getTotalInvoices());
                homeDashboardsCountsItemViewHolder.revenue.setText(totalRevenues.getTotalRevenuesThisMonth());
                homeDashboardsCountsItemViewHolder.quoted.setText(totalRevenues.getQuotedThisMonth());
                SimpleDateFormat currentFormater = new SimpleDateFormat("MMMM, yyyy");
                homeDashboardsCountsItemViewHolder.outstandingMonth.setText(currentFormater.format(Calendar.getInstance().getTime()));
                homeDashboardsCountsItemViewHolder.revenueMonthHeader.setText(currentFormater.format(Calendar.getInstance().getTime()));
            }
            else
            {
                homeDashboardsCountsItemViewHolder.franchisesHeader.setText("Franchisees");
                homeDashboardsCountsItemViewHolder.quotedHeader.setText("Quote");
                homeDashboardsCountsItemViewHolder.quotedMonth.setText("80% Completed");
                homeDashboardsCountsItemViewHolder.franchises.setText(totalRevenues.getTotalFranchiese());
                homeDashboardsCountsItemViewHolder.invoice.setText(totalRevenues.getTotalInvoices());
                homeDashboardsCountsItemViewHolder.revenue.setText(totalRevenues.getTotalRevenuesThisMonth());
                homeDashboardsCountsItemViewHolder.quoted.setText(totalRevenues.getQuotedThisMonth());
                SimpleDateFormat currentFormater = new SimpleDateFormat("MMMM, yyyy");
                homeDashboardsCountsItemViewHolder.outstandingMonth.setText(currentFormater.format(Calendar.getInstance().getTime()));
                homeDashboardsCountsItemViewHolder.quotedMonth.setText(currentFormater.format(Calendar.getInstance().getTime()));
                homeDashboardsCountsItemViewHolder.revenueMonthHeader.setText(currentFormater.format(Calendar.getInstance().getTime()));

            }


        }
        else if(objectList.get(position) instanceof TopFranchise)
        {
            TopFranchise topFranchise = (TopFranchise)objectList.get(position);

            HomeTopFranchiseItemsViewHolder homeTopFranchiseItemsViewHolder = (HomeTopFranchiseItemsViewHolder)holder;

            homeTopFranchiseItemsViewHolder.header.setTypeface(fontBold);
            homeTopFranchiseItemsViewHolder.percentageText.setTypeface(fontBold);
            homeTopFranchiseItemsViewHolder.franchiseName.setTypeface(fontBold);
            homeTopFranchiseItemsViewHolder.location.setTypeface(fontRegular);
            homeTopFranchiseItemsViewHolder.cloudKitchenText.setTypeface(fontRegular);
            homeTopFranchiseItemsViewHolder.quotesText.setTypeface(fontRegular);

            if(MainActivity.Role.equals("Franchisee"))
            {
                homeTopFranchiseItemsViewHolder.header.setText("Top Chefs");
                homeTopFranchiseItemsViewHolder.cloudKitchenText.setText("Chef Name");
                homeTopFranchiseItemsViewHolder.quotesText.setText("Location");
                homeTopFranchiseItemsViewHolder.franchiseName.setVisibility(View.GONE);
                homeTopFranchiseItemsViewHolder.location.setVisibility(View.GONE);
                homeTopFranchiseItemsViewHolder.ratingBar.setVisibility(View.VISIBLE);
                homeTopFranchiseItemsViewHolder.cloudKitchenText.setText(topFranchise.getFranchiseName());
                homeTopFranchiseItemsViewHolder.quotesText.setText(topFranchise.getFranchiseLocation());
            }
            else
            {
                homeTopFranchiseItemsViewHolder.header.setText("Top Franchisee");
                homeTopFranchiseItemsViewHolder.franchiseName.setVisibility(View.VISIBLE);
                homeTopFranchiseItemsViewHolder.location.setVisibility(View.VISIBLE);
                homeTopFranchiseItemsViewHolder.ratingBar.setVisibility(View.GONE);


                homeTopFranchiseItemsViewHolder.cloudKitchenText.setText(topFranchise.getCloudKitchenCount());
                homeTopFranchiseItemsViewHolder.quotesText.setText(topFranchise.getQuoteCount());
                homeTopFranchiseItemsViewHolder.franchiseName.setText(topFranchise.getFranchiseName() + ",");
                homeTopFranchiseItemsViewHolder.location.setText(topFranchise.getFranchiseLocation());

                homeTopFranchiseItemsViewHolder.circularProgressBar.setProgress(Integer.parseInt(String.valueOf(topFranchise.getRevenuePercent()).split("\\.")[0]));

                homeTopFranchiseItemsViewHolder.percentageText.setText(Integer.parseInt(String.valueOf(topFranchise.getRevenuePercent()).split("\\.")[0]) + " %");
            }

        }
        else if(objectList.get(position) instanceof PieChartData)
        {
            PieChartItemsViewHolder pieChartItemsViewHolder = (PieChartItemsViewHolder)holder;
            pieChartItemsViewHolder.header.setTypeface(fontBold);

            if(MainActivity.Role.equals("Franchisee"))
            {
                pieChartItemsViewHolder.header.setText("Revenue by Chefs");
            }
            else
            {
                pieChartItemsViewHolder.header.setText("Revenue by Franchisees");
            }

           pieChartItemsViewHolder. chart.setUsePercentValues(true);
            //chart.setDrawSliceText(false);
            pieChartItemsViewHolder. chart.getDescription().setEnabled(false);
            pieChartItemsViewHolder. chart.setExtraOffsets(5, 10, 5, 5);

            pieChartItemsViewHolder. chart.setDragDecelerationFrictionCoef(0.95f);

            //chart.setCenterTextTypeface(tfLight);
            //chart.setCenterText(generateCenterSpannableText());

            pieChartItemsViewHolder. chart.setDrawHoleEnabled(true);
            pieChartItemsViewHolder.chart.setTouchEnabled(true);
            pieChartItemsViewHolder. chart.setHoleColor(Color.WHITE);

            pieChartItemsViewHolder. chart.setTransparentCircleColor(Color.WHITE);
            pieChartItemsViewHolder. chart.setTransparentCircleAlpha(110);

            pieChartItemsViewHolder. chart.setHoleRadius(58f);
            pieChartItemsViewHolder. chart.setTransparentCircleRadius(61f);

            pieChartItemsViewHolder. chart.setDrawCenterText(true);

            pieChartItemsViewHolder. chart.setRotationAngle(0);
            // enable rotation of the chart by touch
            pieChartItemsViewHolder. chart.setRotationEnabled(false);
            pieChartItemsViewHolder. chart.setHighlightPerTapEnabled(false);

//             chart.setUnit(" €");
//             pieChartItemsViewHolder.chart.setDrawUnitsInChart(true);

            // add a selection listener
           // pieChartItemsViewHolder. chart.setOnChartValueSelectedListener(this);



             pieChartItemsViewHolder.chart.animateY(1400, Easing.EaseInOutQuad);
//             pieChartItemsViewHolder.chart.spin(2000, 0, 360);

//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

            // entry label styling
            pieChartItemsViewHolder. chart.setEntryLabelColor(Color.WHITE);
            pieChartItemsViewHolder. chart.getLegend().setEnabled(false);
            //pieChartItemsViewHolder. chart.setEntryLabelTypeface(tfRegular);
            pieChartItemsViewHolder. chart.setEntryLabelTextSize(10f);

            pieChartItemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RevenueByFranchisees fragment = new RevenueByFranchisees();
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });

//            setPieChartData(pieChartItemsViewHolder.chart);
            setPieChartDataStatic(pieChartItemsViewHolder.chart);

        }
        else if(objectList.get(position) instanceof BarChartData)
        {
            BarChartViewHolder barChartViewHolder = (BarChartViewHolder)holder;
            //barChartViewHolder.chart.setOnChartValueSelectedListener(this);
            barChartViewHolder.chart.getDescription().setEnabled(false);
            barChartViewHolder.header.setTypeface(fontBold);
            barChartViewHolder.year.setTypeface(fontRegular);
            barChartViewHolder.chart.animateY(5000);

//        chart.setDrawBorders(true);

            // scaling can now only be done on x- and y-axis separately
            barChartViewHolder.chart.setPinchZoom(false);

            barChartViewHolder.chart.setDrawBarShadow(false);
            barChartViewHolder.chart.setTouchEnabled(true);

            barChartViewHolder.chart.setDrawGridBackground(false);


            Legend l = barChartViewHolder.chart.getLegend();
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

            barChartViewHolder.chart.setGridBackgroundColor(128);
            barChartViewHolder.chart.setBorderColor(255);
            barChartViewHolder.chart.getAxisRight().setEnabled(false);
            YAxis leftAxis = barChartViewHolder.chart.getAxisLeft();
            leftAxis.setEnabled(false);
            barChartViewHolder.chart.setDrawGridBackground(true);
            barChartViewHolder.chart.getAxisRight().setDrawLabels(false);
            barChartViewHolder.chart.getAxisLeft().setDrawLabels(false);
            barChartViewHolder.chart.getLegend().setEnabled(false);
            barChartViewHolder.chart.setPinchZoom(false);
            //  chart.setDescription("");
            barChartViewHolder.chart.setTouchEnabled(false);
            barChartViewHolder.chart.setDoubleTapToZoomEnabled(false);
            barChartViewHolder.chart.getXAxis().setEnabled(true);
            barChartViewHolder.chart.setDrawGridBackground(true);//enable this too
            barChartViewHolder.chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            barChartViewHolder.chart.getXAxis().setDrawGridLines(true);//enable for grid line
            //  chart.get().setDrawGridLines(false);//disable vertical line
            barChartViewHolder.chart.getXAxis().setDrawAxisLine(false);
            barChartViewHolder.chart.invalidate();

            barChartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentRevenues fragment = new FragmentRevenues();
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });

//            setBarChartData(barChartViewHolder.chart);
//            getBarChartDat(barChartViewHolder.chart);
            getBarChartDataStatic(barChartViewHolder.chart);
        }

        else if(objectList.get(position) instanceof BubbleChartDataModel)
        {
            BubbleChartItemViewHolder bubbleChartItemViewHolder = (BubbleChartItemViewHolder)holder;
            bubbleChartItemViewHolder.header.setTypeface(fontBold);
            bubbleChartItemViewHolder.weekheader.setTypeface(fontRegular);
            bubbleChartItemViewHolder.chart.getDescription().setEnabled(false);

//            bubbleChartItemViewHolder.chart.setOnChartValueSelectedListener(this);

            bubbleChartItemViewHolder.chart.setDrawGridBackground(false);

            bubbleChartItemViewHolder.chart.setTouchEnabled(true);

            // enable scaling and dragging
            bubbleChartItemViewHolder.chart.setDragEnabled(true);
            bubbleChartItemViewHolder.chart.setScaleEnabled(true);

            bubbleChartItemViewHolder.chart.setMaxVisibleValueCount(200);
            bubbleChartItemViewHolder.chart.setPinchZoom(true);

            Legend l = bubbleChartItemViewHolder.chart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.TOP);
//            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
          //  l.setTypeface(tfLight);

            YAxis yl = bubbleChartItemViewHolder.chart.getAxisLeft();
         //   yl.setTypeface(tfLight);
            yl.setSpaceTop(30f);
            yl.setSpaceBottom(30f);
            yl.setDrawZeroLine(false);

            bubbleChartItemViewHolder.chart.getAxisRight().setEnabled(false);

            XAxis xl = bubbleChartItemViewHolder.chart.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);

            bubbleChartItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentOrdersByWeek fragment = new FragmentOrdersByWeek();
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });

//            setBubbleChartData(bubbleChartItemViewHolder.chart);
            setBubbleChartDataStatic(bubbleChartItemViewHolder.chart);
          //  xl.setTypeface(tfLight);
        }


        else if(objectList.get(position) instanceof NearByCloudKitchenList)
        {
            NearByCloudKitchenList nearByCloudKitchenList = (NearByCloudKitchenList)objectList.get(position);
            NearByCloudKitchenItemViewHolder nearByCloudKitchenItemViewHolder  = (NearByCloudKitchenItemViewHolder)holder;
            nearByCloudKitchenItemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            nearByCloudKitchenItemViewHolder.header.setTypeface(fontBold);
            nearByCloudKitchenItemViewHolder.recentlyAddedHeader.setTypeface(fontBold);
            nearByCloudKitchenItemViewHolder.recyclerView.setAdapter(new NearByCloudKitchenListAdapter(context,nearByCloudKitchenList.getCloudKitchenList(),fontBold,fontRegular));
        }



        else if(objectList.get(position) instanceof FranchiseList)
        {
            FranchiseList franchiseList = (FranchiseList)objectList.get(position);

            NewFranchiseeItemViewHolder newFranchiseeItemViewHolder = (NewFranchiseeItemViewHolder)holder;

            newFranchiseeItemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            newFranchiseeItemViewHolder.amountHeader.setTypeface(fontBold);
            newFranchiseeItemViewHolder.dateHeader.setTypeface(fontBold);
            newFranchiseeItemViewHolder.franchiseHeader.setTypeface(fontBold);
            newFranchiseeItemViewHolder.header.setTypeface(fontBold);

            newFranchiseeItemViewHolder.recyclerView.setAdapter(new NewFranchisesHomeSubAdapter(context,franchiseList.getFranchiseList(),fontBold,fontRegular));

        }
        else if(objectList.get(position) instanceof SubscriptionList)
        {
            SubscriptionList subscriptionList = (SubscriptionList)objectList.get(position);

            SubscriptionsViewHolder subscriptionsViewHolder = (SubscriptionsViewHolder)holder;
            subscriptionsViewHolder.clientHeader.setTypeface(fontBold);
            subscriptionsViewHolder.header.setTypeface(fontBold);
            subscriptionsViewHolder.dateHeader.setTypeface(fontBold);
            subscriptionsViewHolder.statusHeader.setTypeface(fontBold);

            subscriptionsViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            subscriptionsViewHolder.recyclerView.setAdapter(new SubscriptionsHomeSubAdapter(context,subscriptionList.getSubscriptionsList(),fontBold,fontRegular));


        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(objectList.get(position) instanceof TotalRevenues)
        {
            return R.layout.home_total_revenues_adapter_item;
        }
        else if(objectList.get(position) instanceof TopFranchise)
        {
            return R.layout.top_franchise_adapter_item;
        }
        else if(objectList.get(position) instanceof PieChartData)
        {
            return R.layout.dashboard_home_pie_chart;
        }
        else if(objectList.get(position) instanceof BarChartData)
        {
            return R.layout.dashboard_home_revenue_bar_chart;
        }
        else if(objectList.get(position) instanceof BubbleChartDataModel)
        {
            return R.layout.home_orders_item;
        }
        else if(objectList.get(position) instanceof NearByCloudKitchenList)
        {
            return R.layout.home_nearby_cluod_kitchens;
        }
        else if(objectList.get(position) instanceof FranchiseList)
        {
            return R.layout.franchise_adapter_item;
        }
        else if(objectList.get(position) instanceof SubscriptionList)
        {
            return R.layout.subscriptions_adapter_item;
        }
        else
        {

        }
        return super.getItemViewType(position);
    }



    public class HomeDashboardsCountsItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView franchisesHeader;
        TextView quotedHeader;
        TextView revenueHeader;
        TextView revenueMonthHeader;
        TextView invoiceHeader;
        TextView franchises;
        TextView outstandingMonth;
        TextView quotedMonth;
        TextView quoted;
        TextView revenue;
        TextView invoice;
        public HomeDashboardsCountsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            franchisesHeader = itemView.findViewById(R.id.franchisesHeader);
            quotedHeader = itemView.findViewById(R.id.quotedHeader);
            franchises = itemView.findViewById(R.id.franchises);
            quoted = itemView.findViewById(R.id.quoted);
            revenueHeader = itemView.findViewById(R.id.revenueHeader);
            outstandingMonth = itemView.findViewById(R.id.outstandingMonth);
            quotedMonth = itemView.findViewById(R.id.quotedMonth);
            revenue = itemView.findViewById(R.id.revenue);
            revenueMonthHeader = itemView.findViewById(R.id.revenueMonthHeader);
            invoiceHeader = itemView.findViewById(R.id.invoiceHeader);
            invoice = itemView.findViewById(R.id.invoice);
        }
    }

    public class HomeTopFranchiseItemsViewHolder extends RecyclerView.ViewHolder
    {

        TextView header;
        TextView franchiseName;
        TextView percentageText;
        TextView cloudKitchenText;
        TextView location;
        TextView quotesText;
        RatingBar ratingBar;
        ProgressBar circularProgressBar;

        public HomeTopFranchiseItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            franchiseName = itemView.findViewById(R.id.franchiseName);
            circularProgressBar = itemView.findViewById(R.id.circularProgressBar);
            percentageText = itemView.findViewById(R.id.percentageText);
            cloudKitchenText = itemView.findViewById(R.id.cloudKitchenText);
            location = itemView.findViewById(R.id.location);
            quotesText = itemView.findViewById(R.id.quotesText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    public class PieChartItemsViewHolder extends RecyclerView.ViewHolder
    {
        private PieChart chart;
        TextView header;
        public PieChartItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            chart = itemView.findViewById(R.id.pieChart);
            header = itemView.findViewById(R.id.header);
        }
    }

    public class BarChartViewHolder extends RecyclerView.ViewHolder
    {

        private BarChart chart;
        TextView header;
        TextView year;

        public BarChartViewHolder(@NonNull View itemView) {
            super(itemView);
            chart = itemView.findViewById(R.id.barChart);
            header = itemView.findViewById(R.id.header);
            year = itemView.findViewById(R.id.year);
        }
    }

    public class BubbleChartItemViewHolder extends RecyclerView.ViewHolder
    {
        private BubbleChart chart;
        private TextView weekheader;
        private TextView header;

        public BubbleChartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            chart = itemView.findViewById(R.id.bubbleChart);
            weekheader = itemView.findViewById(R.id.weekheader);
            header = itemView.findViewById(R.id.header);
        }
    }

    public class NewFranchiseeItemViewHolder extends RecyclerView.ViewHolder
    {
        RecyclerView recyclerView;
        TextView header;
        TextView dateHeader;
        TextView franchiseHeader;
        TextView amountHeader;

        public NewFranchiseeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
            dateHeader = itemView.findViewById(R.id.dateHeader);
            franchiseHeader = itemView.findViewById(R.id.franchiseHeader);
            amountHeader = itemView.findViewById(R.id.amountHeader);
        }
    }

    public class SubscriptionsViewHolder extends RecyclerView.ViewHolder
    {

        RecyclerView recyclerView;
        TextView header;
        TextView dateHeader;
        TextView clientHeader;
        TextView statusHeader;

        public SubscriptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
            dateHeader = itemView.findViewById(R.id.dateHeader);
            clientHeader = itemView.findViewById(R.id.clientHeader);
            statusHeader = itemView.findViewById(R.id.statusHeader);
        }
    }

    public class NearByCloudKitchenItemViewHolder extends RecyclerView.ViewHolder /*implements OnMapReadyCallback*/
    {

        RecyclerView recyclerView;
        TextView header;
        TextView recentlyAddedHeader;
        SupportMapFragment mapFragment;

        public NearByCloudKitchenItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
            recentlyAddedHeader=itemView.findViewById(R.id.recentlyAddedHeader);
        }



        public SupportMapFragment getMapFragmentAndCallback(OnMapReadyCallback callback) {
            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
                mapFragment.getMapAsync(callback);
            }

            // for fragment
            // FragmentManager fragmentManager = getChildFragmentManager();
            // for activity
            FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.map, mapFragment).commitNowAllowingStateLoss();
            return mapFragment;
        }


        public void removeMapFragment() {
            if (mapFragment != null) {
                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(mapFragment).commitAllowingStateLoss();
                mapFragment = null;
            }
        }
    }


    private void setPieChartDataStatic(PieChart chart)
    {

        ArrayList<PieEntry> entries = new ArrayList<>();


        entries.add(new PieEntry(10,
                /*parties[i % parties.length]*/ "",
                context.getResources().getDrawable(R.drawable.rating_star)));

        entries.add(new PieEntry(25,
                /*parties[i % parties.length]*/ "",
                context.getResources().getDrawable(R.drawable.rating_star)));


        entries.add(new PieEntry(20,
                /*parties[i % parties.length]*/ "",
                context.getResources().getDrawable(R.drawable.rating_star)));


        entries.add(new PieEntry(5,
                /*parties[i % parties.length]*/ "",
                context.getResources().getDrawable(R.drawable.rating_star)));


        entries.add(new PieEntry(10,
                /*parties[i % parties.length]*/ "",
                context.getResources().getDrawable(R.drawable.rating_star)));


        entries.add(new PieEntry(30,
                /*parties[i % parties.length]*/ "",
                context.getResources().getDrawable(R.drawable.rating_star)));

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        // dataSet.setSliceSpace(1f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

//        ArrayList<Integer> colors = new ArrayList<>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);

        final int[] MY_COLORS = {Color.rgb(246,67,67), Color.rgb(13,201,45), Color.rgb(14,131,187),
                Color.rgb(236,163,17), Color.rgb(246,246,67), Color.rgb(13,201,45)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);

//        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        //data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        // data.setValueTypeface(tfLight);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    private void setPieChartData(PieChart chart) {


        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
//        for (int i = 0; i < count ; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
//                    /*parties[i % parties.length]*/ "",
//                    context.getResources().getDrawable(R.drawable.rating_star)));
//        }


        String url = "";

        if(MainActivity.Role.equals("Franchisee"))
        {
            url = APIBaseURL.franchiseGetRevenueByChef +"5ece8c07a18aea3628105f88";
        }
        else
        {
            url = APIBaseURL.getrevenueByFranchisee;
        }


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    ArrayList<PieEntry> entries = new ArrayList<>();
                    for (int i=0; i <dataArray.length();i++)
                    {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        if(dataObject.optString("revenuePercent").equals(""))
                        {

                        }
                        entries.add(new PieEntry(Float.parseFloat(dataObject.optString("revenuePercent")),
                                /*parties[i % parties.length]*/ /*dataObject.optString("franchiseName")*/"",
                                context.getResources().getDrawable(R.drawable.rating_star)));
                    }

                    PieDataSet dataSet = new PieDataSet(entries, "");

                    dataSet.setDrawIcons(false);

                    // dataSet.setSliceSpace(1f);
                    dataSet.setIconsOffset(new MPPointF(0, 40));
                    dataSet.setSelectionShift(5f);



                    final int[] MY_COLORS = {Color.rgb(14,131,187),Color.rgb(236,163,17), Color.rgb(246,246,67), Color.rgb(13,201,45),Color.rgb(246,67,67), Color.rgb(13,201,45),
                            };
                    ArrayList<Integer> colors = new ArrayList<Integer>();

                    for(int c: MY_COLORS) colors.add(c);


                    dataSet.setColors(colors);
                    dataSet.setSelectionShift(0f);

                    PieData data = new PieData(dataSet);
                    data.setValueFormatter(new PercentFormatter(chart));
                    data.setValueTextSize(11f);
                    data.setValueTextColor(Color.WHITE);
//                     data.setValueTypeface(tfLight);
                    chart.setData(data);

                    // undo all highlights
//                    chart.highlightValues(null);
//                    chart.getTooltip().setFontColor("orange");
//                    chart.getTooltip().getTitle()
//                            .setFontColor("lightgrey")
//                            .setFontFamily("Calibri")
//                            .setFontDecoration(Decoration.UNDERLINE)
//                            .setFontWeight(400);
//                    chart.getTooltip().getBackground()
//                            .setFill("#663399 0.8")
//                            .setStroke("green")
//                            .setCornerType(BackgroundCornersType.ROUND_INNER)
//                            .setCorners(5, 15, 5, 5);


                    chart.invalidate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutErrorPie");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthErrorPie");
//                } else if (error instanceof ServerError) {
//                    //TODO
//                    Log.d("Error","ServerErrorPie");
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error","Network ErrorPie");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error","ParseErrorPie");
//                }
            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"pie_chart_taq");
//        entries.add(new PieEntry(10,
//                /*parties[i % parties.length]*/ "",
//                context.getResources().getDrawable(R.drawable.rating_star)));
//
//        entries.add(new PieEntry(25,
//                /*parties[i % parties.length]*/ "",
//                context.getResources().getDrawable(R.drawable.rating_star)));
//
//
//        entries.add(new PieEntry(20,
//                /*parties[i % parties.length]*/ "",
//                context.getResources().getDrawable(R.drawable.rating_star)));
//
//
//        entries.add(new PieEntry(5,
//                /*parties[i % parties.length]*/ "",
//                context.getResources().getDrawable(R.drawable.rating_star)));
//
//
//        entries.add(new PieEntry(10,
//                /*parties[i % parties.length]*/ "",
//                context.getResources().getDrawable(R.drawable.rating_star)));
//
//
//        entries.add(new PieEntry(30,
//                /*parties[i % parties.length]*/ "",
//                context.getResources().getDrawable(R.drawable.rating_star)));
//
//        PieDataSet dataSet = new PieDataSet(entries, "");
//
//        dataSet.setDrawIcons(false);
//
//        // dataSet.setSliceSpace(1f);
//        dataSet.setIconsOffset(new MPPointF(0, 40));
//        dataSet.setSelectionShift(5f);
//
//        // add a lot of colors
//
////        ArrayList<Integer> colors = new ArrayList<>();
////
////        for (int c : ColorTemplate.VORDIPLOM_COLORS)
////            colors.add(c);
////
////        for (int c : ColorTemplate.JOYFUL_COLORS)
////            colors.add(c);
////
////        for (int c : ColorTemplate.COLORFUL_COLORS)
////            colors.add(c);
////
////        for (int c : ColorTemplate.LIBERTY_COLORS)
////            colors.add(c);
////
////        for (int c : ColorTemplate.PASTEL_COLORS)
////            colors.add(c);
//
//        final int[] MY_COLORS = {Color.rgb(246,67,67), Color.rgb(13,201,45), Color.rgb(14,131,187),
//                Color.rgb(236,163,17), Color.rgb(246,246,67), Color.rgb(13,201,45)};
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for(int c: MY_COLORS) colors.add(c);
//
////        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//        //dataSet.setSelectionShift(0f);
//
//        PieData data = new PieData(dataSet);
//        //data.setValueFormatter(new PercentFormatter(chart));
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.WHITE);
//       // data.setValueTypeface(tfLight);
//        chart.setData(data);
//
//        // undo all highlights
//        chart.highlightValues(null);
//
//        chart.invalidate();
    }

    private void setBarChartData(BarChart chart)
    {
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

    public void getBarChartDataStatic(BarChart barChart)
    {
        float barWidth = 0.30f;
        float barSpace = 0.07f;
        float groupSpace = 0.25f;

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

        yValueGroup1.add(new BarEntry(0f, Float.parseFloat(String.valueOf(9)), Float.parseFloat(String.valueOf(3))));
        yValueGroup2.add(new BarEntry(0f, Float.parseFloat(String.valueOf(15)), Float.parseFloat(String.valueOf(7))));



        yValueGroup1.add(new BarEntry(1f, Float.parseFloat(String.valueOf(9)), Float.parseFloat(String.valueOf(3))));
        yValueGroup2.add(new BarEntry(1f, Float.parseFloat(String.valueOf(2)), Float.parseFloat(String.valueOf(7))));


        yValueGroup1.add(new BarEntry(2f, Float.parseFloat(String.valueOf(3)), Float.parseFloat(String.valueOf(3))));
        yValueGroup2.add(new BarEntry(2f, Float.parseFloat(String.valueOf(4)), Float.parseFloat(String.valueOf(15))));

        yValueGroup1.add(new BarEntry(3f, Float.parseFloat(String.valueOf(3)), Float.parseFloat(String.valueOf(3))));
        yValueGroup2.add(new BarEntry(3f, Float.parseFloat(String.valueOf(4)), Float.parseFloat(String.valueOf(15))));

        yValueGroup1.add(new BarEntry(4f, Float.parseFloat(String.valueOf(3)), Float.parseFloat(String.valueOf(3))));
        //  yValueGroup2.add(new BarEntry(4f, Float.parseFloat(String.valueOf(4)), Float.parseFloat(String.valueOf(15))));


        yValueGroup1.add(new BarEntry(5f, Float.parseFloat(String.valueOf(9)), Float.parseFloat(String.valueOf(4))));
        //  yValueGroup2.add(new BarEntry(5f, Float.parseFloat(String.valueOf(10)), Float.parseFloat(String.valueOf(6))));

        yValueGroup1.add(new BarEntry(6f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(1))));
        // yValueGroup2.add(new BarEntry(6f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(2))));


        yValueGroup1.add(new BarEntry(7f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(7))));
        // yValueGroup2.add(new BarEntry(7f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(12))));


        yValueGroup1.add(new BarEntry(8f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(9))));
        // yValueGroup2.add(new BarEntry(8f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(8))));


        yValueGroup1.add(new BarEntry(9f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(13))));
        // yValueGroup2.add(new BarEntry(9f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(12))));

        yValueGroup1.add(new BarEntry(10f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(2))));
        // yValueGroup2.add(new BarEntry(10f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(7))));

        yValueGroup1.add(new BarEntry(11f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(6))));
        //yValueGroup2.add(new BarEntry(11f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(5))));

//        yValueGroup1.add(new BarEntry(12f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(2))));
//        yValueGroup2.add(new BarEntry(12f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(3))));


        barDataSet1 = new BarDataSet(yValueGroup1, "");
        barDataSet1.setColors(context.getResources().getColor(R.color.colorGreen));
        barDataSet1.setLabel("2019");
        barDataSet1.setDrawIcons(false);
        barDataSet1.setDrawValues(false);



        barDataSet2 = new BarDataSet(yValueGroup2, "");

        barDataSet2.setLabel("2020");
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

        // set bar label
//        Legend legend = new barChart.getLegend();
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setDrawInside(false);

//        List<LegendEntry> legenedEntries = new ArrayList<>();
//
//        legenedEntries.add(LegendEntry("2016", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.RED));
//        legenedEntries.add(LegendEntry("2017", Legend.LegendForm.SQUARE, 8f, 8f, null, Color.YELLOW));
//
//        legend.setCustom(legenedEntries);

//        legend.setYOffset(2f);
//        legend.setXOffset(2f);
//        legend.setYEntrySpace(0f);
//        legend.setTextSize(5f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        //xAxis.textSize = 9f

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        xAxis.setLabelCount(12);
        xAxis.mAxisMaximum = 12f;
        xAxis.setCenterAxisLabels(true);
        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.spaceMin = 4f
//        xAxis.spaceMax = 4f

        barChart.setVisibleXRangeMaximum(12f);
        barChart.setVisibleXRangeMinimum(12f);
        barChart.setDragEnabled(true);

        //Y-axis
        barChart.getAxisRight().setEnabled(false);
        barChart.setScaleEnabled(true);

        YAxis  leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(1f);
        leftAxis.setAxisMinimum(0f);


        barChart.setData(barData);
        barChart.setVisibleXRange(1f, 12f);
    }

    public void getBarChartDat(BarChart barChart)
    {


//        Float barWidth = new  Float();
//        Float barSpace = new  Float();
//        Float groupSpace: Float
//        val groupCount = 12

        float barWidth = 0.30f;
        float barSpace = 0.07f;
        float groupSpace = 0.25f;


        String url = APIBaseURL.revenueByYear;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseBar",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");

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

                    JSONObject dataObjectFirst = dataArray.getJSONObject(0);
                    JSONArray nineteenMonthArray = dataObjectFirst.getJSONArray("month");
                    for (int i=0;i < nineteenMonthArray.length();i++)
                    {
                        JSONObject nineteenMonthObject = nineteenMonthArray.getJSONObject(i);
                        if(nineteenMonthObject.optString("Amount").equals(""))
                        {

                        }
                        else
                        {
                            yValueGroup1.add(new BarEntry(Float.parseFloat(nineteenMonthObject.optString("Amount")), Float.parseFloat(nineteenMonthObject.optString("Amount"))));
                        }

                    }


                    JSONObject dataObjectSecond = dataArray.getJSONObject(1);
                    JSONArray twentyyMonthArray = dataObjectSecond.getJSONArray("month");
                    for (int i=0;i < twentyyMonthArray.length();i++)
                    {
                        JSONObject twentyMonthObject = twentyyMonthArray.getJSONObject(i);
                        if(twentyMonthObject.optString("Amount").equals(""))
                        {

                        }
                        else
                        {
                            yValueGroup2.add(new BarEntry(Float.parseFloat(twentyMonthObject.optString("Amount")), Float.parseFloat(twentyMonthObject.optString("Amount"))));
                        }

                    }






                    barDataSet1 = new BarDataSet(yValueGroup1, "");
                    barDataSet1.setColors(context.getResources().getColor(R.color.colorGreen));
                    barDataSet1.setLabel(dataArray.getJSONObject(1).optString("year"));
                    barDataSet1.setDrawIcons(false);
                    barDataSet1.setDrawValues(false);



                    barDataSet2 = new BarDataSet(yValueGroup2, "");
                    barDataSet2.setLabel(dataArray.getJSONObject(1).optString("year"));
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

                    YAxis  leftAxis = barChart.getAxisLeft();
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
//                    Log.d("Error","TimeoutErrorBar");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthErrorBar");
//                } else if (error instanceof ServerError) {
//                    //TODO
//                    Log.d("Error","ServerErrorBar");
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error","Network ErrorBar");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error","ParseErrorBar");
//                }
            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"home_bar_data");


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
//        yValueGroup1.add(new BarEntry(0f, Float.parseFloat(String.valueOf(9)), Float.parseFloat(String.valueOf(3))));
//        yValueGroup2.add(new BarEntry(0f, Float.parseFloat(String.valueOf(15)), Float.parseFloat(String.valueOf(7))));
//
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
//      //  yValueGroup2.add(new BarEntry(4f, Float.parseFloat(String.valueOf(4)), Float.parseFloat(String.valueOf(15))));
//
//
//        yValueGroup1.add(new BarEntry(5f, Float.parseFloat(String.valueOf(9)), Float.parseFloat(String.valueOf(4))));
//      //  yValueGroup2.add(new BarEntry(5f, Float.parseFloat(String.valueOf(10)), Float.parseFloat(String.valueOf(6))));
//
//        yValueGroup1.add(new BarEntry(6f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(1))));
//       // yValueGroup2.add(new BarEntry(6f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(2))));
//
//
//        yValueGroup1.add(new BarEntry(7f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(7))));
//       // yValueGroup2.add(new BarEntry(7f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(12))));
//
//
//        yValueGroup1.add(new BarEntry(8f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(9))));
//       // yValueGroup2.add(new BarEntry(8f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(8))));
//
//
//        yValueGroup1.add(new BarEntry(9f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(13))));
//       // yValueGroup2.add(new BarEntry(9f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(12))));
//
//        yValueGroup1.add(new BarEntry(10f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(2))));
//       // yValueGroup2.add(new BarEntry(10f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(7))));
//
//        yValueGroup1.add(new BarEntry(11f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(6))));
//        //yValueGroup2.add(new BarEntry(11f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(5))));
//
////        yValueGroup1.add(new BarEntry(12f, Float.parseFloat(String.valueOf(11)), Float.parseFloat(String.valueOf(2))));
////        yValueGroup2.add(new BarEntry(12f, Float.parseFloat(String.valueOf(12)), Float.parseFloat(String.valueOf(3))));
//
//
//        barDataSet1 = new BarDataSet(yValueGroup1, "");
//        barDataSet1.setColors(context.getResources().getColor(R.color.colorGreen));
//        barDataSet1.setLabel("2019");
//        barDataSet1.setDrawIcons(false);
//        barDataSet1.setDrawValues(false);
//
//
//
//        barDataSet2 = new BarDataSet(yValueGroup2, "");
//
//        barDataSet2.setLabel("2020");
//        barDataSet2.setColors(context.getResources().getColor(R.color.colorPrimaryDark));
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
//        YAxis  leftAxis = barChart.getAxisLeft();
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setSpaceTop(1f);
//        leftAxis.setAxisMinimum(0f);
//
//
//        barChart.setData(barData);
//        barChart.setVisibleXRange(1f, 12f);
    }

    public void setBubbleChartDataStatic(BubbleChart chart)
    {
        ArrayList<BubbleEntry> values1 = new ArrayList<>();
        ArrayList<BubbleEntry> values2 = new ArrayList<>();
        ArrayList<BubbleEntry> values3 = new ArrayList<>();

        List<String> xAxisValues =new  ArrayList<String>();

        xAxisValues.add(0,"");
        xAxisValues.add(1,"Mon");
        xAxisValues.add(2,"Tue");
        xAxisValues.add(3,"Wed");
        xAxisValues.add(4,"Thu");
        xAxisValues.add(5,"Fri");
        xAxisValues.add(6,"Sat");
        xAxisValues.add(7,"Sun");

        List<Integer> days = Arrays.asList(
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.TUESDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY,
                Calendar.SUNDAY
        );


        int start = days.indexOf(0);

        for(int i = 1; i < 8; i++) {
            int key = days.get((start + i) % 7);
//            Button button = new Button(this);
//            button.setText(map.get(key));
//            dayLayout.addView(button);
            xAxisValues.add(String.valueOf(key));
        }


        for (int i = 1; i < 8; i++) {
            values1.add(new BubbleEntry(i, (float) (Math.random() * 10), 0.50f));
            values2.add(new BubbleEntry(i, (float) (Math.random() * 10), 0.60f));
            values3.add(new BubbleEntry(i, (float) (Math.random() * 10), 0.70f));
        }


        final int[] MY_COLORS = {Color.rgb(59,53,252), Color.rgb(23,48,245), Color.rgb(240,176,50)/*,*/
                /*Color.rgb(59,53,252), Color.rgb(23,48,245), Color.rgb(240,176,50)*/};
//        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for(int c: MY_COLORS) colors.add(c);

        // create a dataset and give it a type
        BubbleDataSet set1 = new BubbleDataSet(values1, "Bulk");
        set1.setDrawIcons(false);
        set1.setColor(MY_COLORS[0], 130);
        set1.setDrawValues(true);

        BubbleDataSet set2 = new BubbleDataSet(values2, "Subscription");
        set2.setDrawIcons(false);
        set2.setIconsOffset(new MPPointF(0, 15));
        set2.setColor(MY_COLORS[1], 130);
        set2.setDrawValues(true);

        BubbleDataSet set3 = new BubbleDataSet(values3, "Retail");
        set3.setColor(MY_COLORS[2], 130);
        set3.setDrawValues(true);

        ArrayList<IBubbleDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets
        dataSets.add(set2);
        dataSets.add(set3);

        // create a data object with the data sets
        BubbleData data = new BubbleData(dataSets);
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);


        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        xAxis.setLabelCount(8);
        xAxis.mAxisMaximum = 8f;
        xAxis.setCenterAxisLabels(true);
        xAxis.setAvoidFirstLastClipping(true);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);

//        chart.getBubbleData().setBarWidth(barWidth);
//        chart.getXAxis().setAxisMinimum(0f);
//        chart.getXAxis().setAxisMaximum(12f);
//        chart.groupBars(0f, groupSpace, barSpace);

        data.setDrawValues(false);
        //data.setValueTypeface(tfLight);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        data.setHighlightCircleWidth(1.5f);
        chart.getXAxis().setAxisMinValue(0f);
        chart.getXAxis().setAxisMaxValue(9f);

        chart.setData(data);
        chart.invalidate();
    }

    public void setBubbleChartData(BubbleChart chart)
    {
//        ArrayList<BubbleEntry> values1 = new ArrayList<>();
//        ArrayList<BubbleEntry> values2 = new ArrayList<>();
//        ArrayList<BubbleEntry> values3 = new ArrayList<>();
//
//        List<String> xAxisValues =new  ArrayList<String>();
//
//        xAxisValues.add(0,"");
//        xAxisValues.add(1,"Mon");
//        xAxisValues.add(2,"Tue");
//        xAxisValues.add(3,"Wed");
//        xAxisValues.add(4,"Thu");
//        xAxisValues.add(5,"Fri");
//        xAxisValues.add(6,"Sat");
//        xAxisValues.add(7,"Sun");
//
//        List<Integer> days = Arrays.asList(
//                Calendar.MONDAY,
//                Calendar.TUESDAY,
//                Calendar.WEDNESDAY,
//                Calendar.TUESDAY,
//                Calendar.FRIDAY,
//                Calendar.SATURDAY,
//                Calendar.SUNDAY
//        );
//
//
//        int start = days.indexOf(firstDayOfWeek);
//
//        for(int i = 1; i < 8; i++) {
//            int key = days.get((start + i) % 7);
////            Button button = new Button(this);
////            button.setText(map.get(key));
////            dayLayout.addView(button);
//            xAxisValues.add(String.valueOf(key));
//        }
//
//
//        for (int i = 1; i < 8; i++) {
//            values1.add(new BubbleEntry(i, (float) (Math.random() * 10), 0.50f));
//            values2.add(new BubbleEntry(i, (float) (Math.random() * 10), 0.60f));
//            values3.add(new BubbleEntry(i, (float) (Math.random() * 10), 0.70f));
//        }


        String url = APIBaseURL.franchiseeDashboardOrdersRevenue;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
//                    for (int i=0;i < dataArray.length();i++)
//                    {
                        JSONObject firstdataObject = dataArray.getJSONObject(0);
                        JSONObject seconddataObject = dataArray.getJSONObject(1);
                        JSONObject thirddataObject = dataArray.getJSONObject(2);

                        JSONArray firstItemArray = firstdataObject.getJSONArray("days");
                        JSONArray secondItemArray = seconddataObject.getJSONArray("days");
                        JSONArray thirdItemArray = thirddataObject.getJSONArray("days");

                        ArrayList<BubbleEntry> values1 = new ArrayList<>();
                        ArrayList<BubbleEntry> values2 = new ArrayList<>();
                        ArrayList<BubbleEntry> values3 = new ArrayList<>();


                        List<String> xAxisValues =new  ArrayList<String>();

                        xAxisValues.add(0,"  ");
                        xAxisValues.add(1,"Mon");
                        xAxisValues.add(2,"Tue");
                        xAxisValues.add(3,"Wed");
                        xAxisValues.add(4,"Thu");
                        xAxisValues.add(5,"Fri");
                        xAxisValues.add(6,"Sat");
                        xAxisValues.add(7,"Sun");
                        for (int i=0;i < firstItemArray.length();i++)
                        {
                            JSONObject firstItemObject = firstItemArray.getJSONObject(i);

                            values1.add(new BubbleEntry(i+1, Float.parseFloat(firstItemObject.optString("Amount")), 10f));
                        }

                        for (int i=0;i < secondItemArray.length();i++)
                        {
                            JSONObject secondItemObject = secondItemArray.getJSONObject(i);
                            values2.add(new BubbleEntry(i+1, Float.parseFloat(secondItemObject.optString("Amount")), 10f));
                        }

                        for (int i=0;i < thirdItemArray.length();i++)
                        {
                            JSONObject thirdItemObject = thirdItemArray.getJSONObject(i);
                            values3.add(new BubbleEntry(i+1, Float.parseFloat(thirdItemObject.optString("Amount")), 10f));
                        }

                    final int[] MY_COLORS = {Color.rgb(59,53,252), Color.rgb(232,48,245), Color.rgb(240,176,50)/*,*/
                            /*Color.rgb(59,53,252), Color.rgb(23,48,245), Color.rgb(240,176,50)*/};
//        ArrayList<Integer> colors = new ArrayList<Integer>();


                    // create a dataset and give it a type
                    BubbleDataSet set1 = new BubbleDataSet(values1, "Bulk");
                    set1.setDrawIcons(false);
                    set1.setColor(MY_COLORS[0], 180);
                    set1.setDrawValues(true);

                    BubbleDataSet set2 = new BubbleDataSet(values2, "Subscription");
                    set2.setDrawIcons(false);
                    set2.setIconsOffset(new MPPointF(0, 15));
                    set2.setColor(MY_COLORS[1], 180);
                    set2.setDrawValues(true);

                    BubbleDataSet set3 = new BubbleDataSet(values3, "Retail");
                    set3.setColor(MY_COLORS[2], 180);
                    set3.setDrawValues(true);

                    ArrayList<IBubbleDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1); // add the data sets
                    dataSets.add(set2);
                    dataSets.add(set3);

                    // create a data object with the data sets
                    BubbleData data = new BubbleData(dataSets);
                    XAxis xAxis = chart.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setDrawGridLines(false);


                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

                    xAxis.setLabelCount(8);
                    xAxis.mAxisMaximum = 8f;
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setAvoidFirstLastClipping(true);
                    chart.getAxisRight().setEnabled(false);
                    chart.getAxisLeft().setEnabled(false);

//        chart.getBubbleData().setBarWidth(barWidth);
//        chart.getXAxis().setAxisMinimum(0f);
//        chart.getXAxis().setAxisMaximum(12f);
//        chart.groupBars(0f, groupSpace, barSpace);

                    data.setDrawValues(false);
                    //data.setValueTypeface(tfLight);
                    data.setValueTextSize(10f);
                    data.setValueTextColor(Color.WHITE);
                    data.setHighlightCircleWidth(1.5f);
                    chart.getXAxis().setAxisMinValue(0f);
                    chart.getXAxis().setAxisMaxValue(9f);

                    chart.setData(data);
                    chart.invalidate();

//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"bubble_chart_taq");

//        values1.add(new BubbleEntry(0, 1,0.21f));
//        values1.add(new BubbleEntry(1, 2,0.12f));
//        values1.add(new BubbleEntry(2, 3,0.20f));
//        values1.add(new BubbleEntry(2,4, 0.52f));
//        values1.add(new BubbleEntry(3, 5,0.29f));
//        values1.add(new BubbleEntry(4, 6,0.62f));
//
//
//        values2.add(new BubbleEntry(0, 1,0.30f));
//        values2.add(new BubbleEntry(1, 2,0.22f));
//        values2.add(new BubbleEntry(2, 3,0.30f));
//        values2.add(new BubbleEntry(2,4, 0.62f));
//        values2.add(new BubbleEntry(3, 5,0.39f));
//        values2.add(new BubbleEntry(4, 6,0.72f));
//
//
//
//        values3.add(new BubbleEntry(0, 1,0.40f));
//        values3.add(new BubbleEntry(1, 2,0.32f));
//        values3.add(new BubbleEntry(2, 3,0.40f));
//        values3.add(new BubbleEntry(2,4, 0.72f));
//        values3.add(new BubbleEntry(3, 5,0.49f));
//        values3.add(new BubbleEntry(4, 6,0.82f));


//        final int[] MY_COLORS = {Color.rgb(59,53,252), Color.rgb(23,48,245), Color.rgb(240,176,50)/*,*/
//                /*Color.rgb(59,53,252), Color.rgb(23,48,245), Color.rgb(240,176,50)*/};
////        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for(int c: MY_COLORS) colors.add(c);
//
//        // create a dataset and give it a type
//        BubbleDataSet set1 = new BubbleDataSet(values1, "Bulk");
//        set1.setDrawIcons(false);
//        set1.setColor(MY_COLORS[0], 130);
//        set1.setDrawValues(true);
//
//        BubbleDataSet set2 = new BubbleDataSet(values2, "Subscription");
//        set2.setDrawIcons(false);
//        set2.setIconsOffset(new MPPointF(0, 15));
//        set2.setColor(MY_COLORS[1], 130);
//        set2.setDrawValues(true);
//
//        BubbleDataSet set3 = new BubbleDataSet(values3, "Retail");
//        set3.setColor(MY_COLORS[2], 130);
//        set3.setDrawValues(true);
//
//        ArrayList<IBubbleDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set1); // add the data sets
//        dataSets.add(set2);
//        dataSets.add(set3);
//
//        // create a data object with the data sets
//        BubbleData data = new BubbleData(dataSets);
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setDrawGridLines(false);
//
//
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
//
//        xAxis.setLabelCount(8);
//        xAxis.mAxisMaximum = 8f;
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setAvoidFirstLastClipping(true);
//        chart.getAxisRight().setEnabled(false);
//        chart.getAxisLeft().setEnabled(false);
//
////        chart.getBubbleData().setBarWidth(barWidth);
////        chart.getXAxis().setAxisMinimum(0f);
////        chart.getXAxis().setAxisMaximum(12f);
////        chart.groupBars(0f, groupSpace, barSpace);
//
//        data.setDrawValues(false);
//        //data.setValueTypeface(tfLight);
//        data.setValueTextSize(10f);
//        data.setValueTextColor(Color.WHITE);
//        data.setHighlightCircleWidth(1.5f);
//        chart.getXAxis().setAxisMinValue(0f);
//        chart.getXAxis().setAxisMaxValue(9f);
//
//        chart.setData(data);
//        chart.invalidate();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        if(holder instanceof NearByCloudKitchenItemViewHolder)
        {



            NearByCloudKitchenItemViewHolder nearByCloudKitchenItemViewHolder = (NearByCloudKitchenItemViewHolder)holder;



                nearByCloudKitchenItemViewHolder.getMapFragmentAndCallback(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {

                        markerDataArrayList=new ArrayList<LatLng>();

//                        String url = APIBaseURL.franchiseeGetCloudKitchenByFranchiseID + "5ece8c07a18aea3628105f88";
//
//                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                    for (int i=0; i < jsonArray.length();i++)
//                                    {
//                                        JSONObject dataObject = jsonArray.getJSONObject(i);
//                                        JSONObject locationObject = new JSONObject();
//                                        if(dataObject.has("location"))
//                                        {
//                                            locationObject = dataObject.getJSONObject("location");
//
//                                            JSONObject positionObject = new JSONObject();
//                                            if(locationObject.has("position"))
//                                            {
//                                                positionObject = locationObject.getJSONObject("position");
//                                            }
//                                            markerDataArrayList.add(new LatLng(positionObject.optDouble("lat"), positionObject.optDouble("lon")));
//
//                                            for( LatLng location : markerDataArrayList){
//                                                googleMap.addMarker(new MarkerOptions().position(location));
//                                            }
//                                        }
//                                    }
//
//                                    try {
//                                        if(markerDataArrayList.size()!=0)
//                                        {
//                                            LatLng lastLatLng= markerDataArrayList.get(markerDataArrayList.size()-1);
//                                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(lastLatLng));
//                                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11.5f));
//                                        }
//                                    }
//                                    catch (Exception e)
//                                    {
//                                        e.printStackTrace();
//                                    }
//
//
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//                        ApplicationController.getInstance().addToRequestQueue(stringRequest,"map_marker_taq");
                        markerDataArrayList.add(new LatLng(13.012658215701101, 77.63940504634817));
                        markerDataArrayList.add(new LatLng(13.012658215701101, 77.61063729269978));
                        markerDataArrayList.add(new LatLng(13.026613193056955, 77.62848058293739));
                        markerDataArrayList.add(new LatLng(13.010765954886969, 77.67715513657875));
                        for( LatLng location : markerDataArrayList){
                            googleMap.addMarker(new MarkerOptions().position(location));
                        }
                        try {
                            if(markerDataArrayList.size()!=0)
                            {
                                LatLng lastLatLng= markerDataArrayList.get(markerDataArrayList.size()-1);
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(lastLatLng));
                                googleMap.animateCamera(CameraUpdateFactory.zoomTo(11.5f));
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//                        //Creating a list of markers
//                        List<MarkerOptions> markers = new ArrayList<>();
//
//                        //Creating my Icon from a drawable ressource
//                        Icon yellowIcon = IconFactory.getInstance(context).fromDrawable(ContextCompat.getDrawable(context, R.drawable.yellow_marker));

//                        for (int i = 0; i < numberOfMarkers; i++){
//                            markers.add(new MarkerOptions()
//                                    .position(lat,lng)
//                                    .title("blabla")
//                                    .snippet("blabla too")
//                                    .icon(yellowIcon)
//                            )
//                        }

                    }
                });


//            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);



        if (holder instanceof NearByCloudKitchenItemViewHolder ) {
            NearByCloudKitchenItemViewHolder nearByCloudKitchenItemViewHolder = (NearByCloudKitchenItemViewHolder)holder;
            nearByCloudKitchenItemViewHolder.removeMapFragment();

        }

    }


}
