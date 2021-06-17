package stayabode.foodyHive.adapters.chefs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.applandeo.materialcalendarview.CalendarView;
import stayabode.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.fragments.chefs.ChefItemDetailFragment;
import stayabode.foodyHive.fragments.chefs.ChefProfileFragment;
import stayabode.foodyHive.models.BulkOrder;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.models.ChefCard;
import stayabode.foodyHive.models.ChefMostOrderedDish;
import stayabode.foodyHive.models.ChefRevenues;
import stayabode.foodyHive.models.ChefTopRatedDish;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.OrderByMonth;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.models.PieChartData;
import stayabode.foodyHive.models.PromoCodes;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import stayabode.github.mikephil.charting.animation.Easing;
import stayabode.github.mikephil.charting.charts.CombinedChart;
import stayabode.github.mikephil.charting.charts.LineChart;
import stayabode.github.mikephil.charting.charts.PieChart;
import stayabode.github.mikephil.charting.components.Legend;
import stayabode.github.mikephil.charting.components.XAxis;
import stayabode.github.mikephil.charting.components.YAxis;
import stayabode.github.mikephil.charting.data.Entry;
import stayabode.github.mikephil.charting.data.LineData;
import stayabode.github.mikephil.charting.data.LineDataSet;
import stayabode.github.mikephil.charting.data.PieData;
import stayabode.github.mikephil.charting.data.PieDataSet;
import stayabode.github.mikephil.charting.data.PieEntry;
import stayabode.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import stayabode.github.mikephil.charting.formatter.PercentFormatter;
import stayabode.github.mikephil.charting.utils.ColorTemplate;
import stayabode.github.mikephil.charting.utils.MPPointF;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefMainfragment;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefbackStateName;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    Typeface ralewayFontBold;
    Typeface ralewayFontRegular;

    ArrayList pieEntries;

    protected final String[] months = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    public HomeAdapter(Context context, List<Object> objectList, Typeface fontBold, Typeface fontRegular, Typeface ralewayFontBold, Typeface ralewayFontRegular) {
        this.context = context;
        this.objectList = objectList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
        this.ralewayFontBold = ralewayFontBold;
        this.ralewayFontRegular = ralewayFontRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(viewType, parent, false);

        if (viewType == R.layout.home_chefs_card_item) {
            return new CardsItemViewHolder(view);
        } else if (viewType == R.layout.home_chefs_revenue_item) {
            return new RevenuesCardItemViewHolder(view);
        } else if (viewType == R.layout.home_chefs_most_orders_dish_item) {
            return new MostOrderedDishItemViewHolder(view);
        } else if (viewType == R.layout.home_chef_top_rated_dish_item) {
            return new TopRatefddDishesItemViewHolder(view);
        } else if (viewType == R.layout.home_chef_orders_item) {
            return new OrdersItemViewHolder(view);
        } else if (viewType == R.layout.dashboard_home_pie_chart) {
            return new RevenueChartDishesItemViewHolder(view);
        } else if (viewType == R.layout.chefs_promo_codes_list_item) {
            return new PromoCodesChefItemViewHolder(view);
        } else if (viewType == R.layout.chef_menu_dish_list_item) {
            return new MenuItemsViewHolder(view);
        } else if (viewType == R.layout.chef_home_bulk_order_item) {
            return new BulkOrderItemViewHolder(view);
        } else if (viewType == R.layout.chef_list_item) {
            return new ChefItemViewHolder(view);
        } else {
            return new MostOrderedDishItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (objectList.get(position) instanceof ChefCard) {
            CardsItemViewHolder cardsItemViewHolder = (CardsItemViewHolder) holder;
            ChefCard chefCard = (ChefCard) objectList.get(position);
            cardsItemViewHolder.currentOrderHeader.setTypeface(fontBold);
            cardsItemViewHolder.newOrderHeader.setTypeface(fontBold);
            cardsItemViewHolder.subscriptionHeader.setTypeface(fontBold);
            cardsItemViewHolder.subscriptionCount.setTypeface(fontRegular);
            cardsItemViewHolder.currentOrderCount.setTypeface(fontRegular);
            cardsItemViewHolder.newOrderCount.setTypeface(fontRegular);
            cardsItemViewHolder.currentOrderCount.setText(chefCard.getCurrentOrdersCount());
            cardsItemViewHolder.newOrderCount.setText(chefCard.getNewOrdersCount());
            cardsItemViewHolder.subscriptionCount.setText(chefCard.getSubscriptionsCount());
        } else if (objectList.get(position) instanceof ChefRevenues) {
            RevenuesCardItemViewHolder revenuesCardItemViewHolder = (RevenuesCardItemViewHolder) holder;
            ChefRevenues chefRevenues = (ChefRevenues) objectList.get(position);
            revenuesCardItemViewHolder.revenueHeader.setTypeface(fontBold);
            revenuesCardItemViewHolder.revenueMonth.setTypeface(fontRegular);
            revenuesCardItemViewHolder.revenueamount.setTypeface(fontRegular);
            revenuesCardItemViewHolder.revenueamount.setText(chefRevenues.getAmount());
            revenuesCardItemViewHolder.revenueMonth.setText(chefRevenues.getMonth());
        } else if (objectList.get(position) instanceof ChefMostOrderedDish) {
            MostOrderedDishItemViewHolder mostOrderedDishItemViewHolder = (MostOrderedDishItemViewHolder) holder;
            mostOrderedDishItemViewHolder.header.setTypeface(fontBold);
            ChefMostOrderedDish chefMostOrderedDish = (ChefMostOrderedDish) objectList.get(position);
            mostOrderedDishItemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mostOrderedDishItemViewHolder.recyclerView.setAdapter(new FoodAdapter(context, chefMostOrderedDish.getFoodItemList(), ralewayFontBold, ralewayFontRegular));
        } else if (objectList.get(position) instanceof ChefTopRatedDish) {
            TopRatefddDishesItemViewHolder topRatefddDishesItemViewHolder = (TopRatefddDishesItemViewHolder) holder;
            topRatefddDishesItemViewHolder.header.setTypeface(fontBold);
            ChefTopRatedDish chefTopRatedDish = (ChefTopRatedDish) objectList.get(position);
            topRatefddDishesItemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            topRatefddDishesItemViewHolder.recyclerView.setAdapter(new FoodAdapter(context, chefTopRatedDish.getFoodItemList(), ralewayFontBold, ralewayFontRegular));
        } else if (objectList.get(position) instanceof Orders) {
            OrdersItemViewHolder ordersItemViewHolder = (OrdersItemViewHolder) holder;
            ordersItemViewHolder.header.setTypeface(fontBold);

            // no description text
            ordersItemViewHolder.lineChart.getDescription().setEnabled(false);

            // enable touch gestures
            ordersItemViewHolder.lineChart.setTouchEnabled(true);

            ordersItemViewHolder.lineChart.setDragDecelerationFrictionCoef(0.9f);

            // enable scaling and dragging
            ordersItemViewHolder.lineChart.setDragEnabled(true);
            ordersItemViewHolder.lineChart.setScaleEnabled(true);
            ordersItemViewHolder.lineChart.setDrawGridBackground(false);
            ordersItemViewHolder.lineChart.setHighlightPerDragEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            ordersItemViewHolder.lineChart.setPinchZoom(true);

            // set an alternative background color
            ordersItemViewHolder.lineChart.setBackgroundColor(Color.parseColor("#F8F8F8"));
            XAxis xaxis=ordersItemViewHolder.lineChart.getXAxis();
            xaxis.setEnabled(true);

            ordersItemViewHolder.lineChart.animateX(1500);

            // get the legend (only possible after setting data)
            Legend l = ordersItemViewHolder.lineChart.getLegend();

            // modify the legend ...
            l.setForm(Legend.LegendForm.LINE);
            //   l.setTypeface(tfLight);
            l.setTextSize(11f);
            l.setTextColor(Color.BLACK);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            //l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setDrawInside(false);
//        l.setYOffset(11f);

            XAxis xAxis = ordersItemViewHolder.lineChart.getXAxis();
            XAxis.XAxisPosition positionBottom = XAxis.XAxisPosition.BOTTOM;
            xAxis.setPosition(positionBottom);
            //xAxis.setTypeface(tfLight);
            xAxis.setTextSize(11f);
            xAxis.setTextColor(Color.parseColor("#6A6A6A"));
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            final ArrayList<String> xLabel = new ArrayList<>();
            xLabel.add("Jan");
            xLabel.add("Feb");
            xLabel.add("Mar");
            xLabel.add("Apr");
            xLabel.add("May");
            xLabel.add("Jun");
            xLabel.add("Jul");
            xLabel.add("Aug");
            xLabel.add("Sep");
            xLabel.add("Oct");
            xLabel.add("Nov");
            xLabel.add("Dec");
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabel));


            // or use some other logic to save your data in list. For ex.


            YAxis leftAxis = ordersItemViewHolder.lineChart.getAxisLeft();
            leftAxis.setEnabled(false);
//            leftAxis.setTextColor(ColorTemplate.getHoloBlue());
//            leftAxis.setAxisMaximum(200f);
//            leftAxis.setAxisMinimum(0f);
//            leftAxis.setDrawGridLines(true);
//            leftAxis.setGranularityEnabled(true);

            YAxis rightAxis = ordersItemViewHolder.lineChart.getAxisRight();
            rightAxis.setEnabled(false);
//            rightAxis.setTextColor(Color.RED);
//            rightAxis.setAxisMaximum(900);
//            rightAxis.setAxisMinimum(-200);
//            rightAxis.setDrawGridLines(false);
//            rightAxis.setDrawZeroLine(false);
//            rightAxis.setGranularityEnabled(false);


            getOrdersLineChart(ordersItemViewHolder.lineChart);
        } else if (objectList.get(position) instanceof PieChartData) {
            RevenueChartDishesItemViewHolder revenueChartDishesItemViewHolder = (RevenueChartDishesItemViewHolder) holder;
            revenueChartDishesItemViewHolder.pieChartCard.setCardElevation(0f);

            revenueChartDishesItemViewHolder.header.setTypeface(fontBold);


            revenueChartDishesItemViewHolder.chart.setUsePercentValues(true);
            //chart.setDrawSliceText(false);
            revenueChartDishesItemViewHolder.chart.getDescription().setEnabled(false);
            revenueChartDishesItemViewHolder.chart.setExtraOffsets(5, 10, 5, 5);

            revenueChartDishesItemViewHolder.chart.setDragDecelerationFrictionCoef(0.95f);

            //chart.setCenterTextTypeface(tfLight);
            //chart.setCenterText(generateCenterSpannableText());

            revenueChartDishesItemViewHolder.chart.setDrawHoleEnabled(true);
            revenueChartDishesItemViewHolder.chart.setTouchEnabled(true);
            revenueChartDishesItemViewHolder.chart.setHoleColor(Color.WHITE);

            revenueChartDishesItemViewHolder.chart.setTransparentCircleColor(Color.WHITE);
            revenueChartDishesItemViewHolder.chart.setTransparentCircleAlpha(110);

            revenueChartDishesItemViewHolder.chart.setHoleRadius(58f);
            revenueChartDishesItemViewHolder.chart.setTransparentCircleRadius(61f);

            revenueChartDishesItemViewHolder.chart.setDrawCenterText(true);

            revenueChartDishesItemViewHolder.chart.setRotationAngle(0);
            // enable rotation of the chart by touch
            revenueChartDishesItemViewHolder.chart.setRotationEnabled(false);
            revenueChartDishesItemViewHolder.chart.setHighlightPerTapEnabled(true);

//             chart.setUnit(" €");
//             pieChartItemsViewHolder.chart.setDrawUnitsInChart(true);

            // add a selection listener
            // pieChartItemsViewHolder. chart.setOnChartValueSelectedListener(this);


            revenueChartDishesItemViewHolder.chart.animateY(1400, Easing.EaseInOutQuad);
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
            revenueChartDishesItemViewHolder.chart.setEntryLabelColor(Color.WHITE);
            revenueChartDishesItemViewHolder.chart.getLegend().setEnabled(false);
            //pieChartItemsViewHolder. chart.setEntryLabelTypeface(tfRegular);
            revenueChartDishesItemViewHolder.chart.setEntryLabelTextSize(10f);


            getPieChartDatas(revenueChartDishesItemViewHolder.chart);

        } else if (objectList.get(position) instanceof PromoCodes) {
            PromoCodesChefItemViewHolder promoCodesChefItemViewHolder = (PromoCodesChefItemViewHolder) holder;
            promoCodesChefItemViewHolder.header.setTypeface(fontBold);
            promoCodesChefItemViewHolder.startDateHeader.setTypeface(fontBold);
            promoCodesChefItemViewHolder.endDateHeader.setTypeface(fontBold);
            promoCodesChefItemViewHolder.discountHeader.setTypeface(fontBold);
            promoCodesChefItemViewHolder.forOrderHeader.setTypeface(fontBold);
            promoCodesChefItemViewHolder.promoValue.setTypeface(fontRegular);
            promoCodesChefItemViewHolder.startDateValue.setTypeface(fontRegular);
            promoCodesChefItemViewHolder.endDateValue.setTypeface(fontRegular);
            promoCodesChefItemViewHolder.discountValue.setTypeface(fontRegular);
            promoCodesChefItemViewHolder.retail.setTypeface(fontRegular);
        } else if (objectList.get(position) instanceof FoodItem) {
            MenuItemsViewHolder menuItemsViewHolder = (MenuItemsViewHolder) holder;
            menuItemsViewHolder.name.setTypeface(fontBold);
            menuItemsViewHolder.itemDescription.setTypeface(fontRegular);
            menuItemsViewHolder.availableSwitch.setTypeface(fontRegular);
            menuItemsViewHolder.price.setTypeface(ralewayFontRegular);
            menuItemsViewHolder.availableSwitch.setTypeface(fontRegular);
            menuItemsViewHolder.count.setTypeface(ralewayFontRegular);
            menuItemsViewHolder.mins.setTypeface(ralewayFontRegular);
            menuItemsViewHolder.actualPrice.setTypeface(ralewayFontRegular);
            FoodItem foodItem = (FoodItem) objectList.get(position);
            menuItemsViewHolder.ratingBar.setRating(foodItem.getRatingAverage());
            if(foodItem.getDiscountedPercentage().equals("0")){
                menuItemsViewHolder.discountPercent.setVisibility(View.GONE);
                menuItemsViewHolder.actualPrice.setVisibility(View.GONE);
            }else{
                menuItemsViewHolder.discountPercent.setVisibility(View.VISIBLE);
                menuItemsViewHolder.actualPrice.setVisibility(View.VISIBLE);
                menuItemsViewHolder.actualPrice.setBackground(context.getResources().getDrawable(R.drawable.strike_through));
                menuItemsViewHolder.actualPrice.setText(foodItem.getPrice());
                menuItemsViewHolder.discountPercent.setText(foodItem.getDiscountedPercentage());
            }
            menuItemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (foodItem.getApproved()) {
                        ChefItemDetailFragment fragment = new ChefItemDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", foodItem.getFoodId());
                        bundle.putString("ChefId", foodItem.getChefId());
                        fragment.setArguments(bundle);
                        FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.content, fragment).addToBackStack(null);

                        ft.commit();
                    }

                }
            });

            menuItemsViewHolder.name.setText(foodItem.getFoodName());
            menuItemsViewHolder.mins.setText(foodItem.getTime());
            menuItemsViewHolder.count.setText("("+foodItem.getRatingCount()+")");
            menuItemsViewHolder.itemDescription.setText(foodItem.getShortDescription());

            menuItemsViewHolder.price.setText("₹"+String.format("%.2f",Double.valueOf(foodItem.getDiscountedPrice())));
            Glide.with(context).load(foodItem.getFoodImage()).placeholder(R.drawable.foodi_logo_left_image).into(menuItemsViewHolder.imageView);
            menuItemsViewHolder.availableSwitch.setChecked(foodItem.getAutoAcceptOrder());
            if (foodItem.getApproved()) {
                menuItemsViewHolder.viewNotApproved.setVisibility(View.GONE);
            } else {
                menuItemsViewHolder.viewNotApproved.setVisibility(View.VISIBLE);
            }
            menuItemsViewHolder.availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Log.d("Status", "True");
                        try {
                            setActiveorDeactiveStatus(foodItem.getChefId(), foodItem.getFoodId(), true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("Status", "False");
                        try {
                            setActiveorDeactiveStatus(foodItem.getChefId(), foodItem.getFoodId(), false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        } else if (objectList.get(position) instanceof BulkOrder) {
            BulkOrderItemViewHolder bulkOrderItemViewHolder = (BulkOrderItemViewHolder) holder;
            BulkOrder bulkOrder = (BulkOrder) objectList.get(position);
            bulkOrderItemViewHolder.pagetitle.setTypeface(fontBold);
            //bulkOrderItemViewHolder.header.setTypeface(fontRegular);
            bulkOrderItemViewHolder.monthText.setTypeface(fontRegular);
            bulkOrderItemViewHolder.thisMonth.setTypeface(fontBold);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2020, 3, 19);
            bulkOrderItemViewHolder.rootLayout.setVisibility(View.GONE);

            try {
                bulkOrderItemViewHolder.calendarView.setDate(calendar);
            } catch (OutOfDateRangeException e) {
                e.printStackTrace();
            }

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            List<Object> orderList = new ArrayList<>();


            for (int i = 0; i < bulkOrder.getOrderByMonthList().size(); i++) {
                OrderByMonth orderByMonth = new OrderByMonth();
                orderByMonth.setDate(bulkOrder.getOrderByMonthList().get(i).getDate());
                orderByMonth.setQuantity("Qty - " + bulkOrder.getOrderByMonthList().get(i).getQuantity());
                orderByMonth.setOrderNumber(bulkOrder.getOrderByMonthList().get(i).getOrderNumber());
                orderList.add(orderByMonth);
            }


            bulkOrderItemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            bulkOrderItemViewHolder.recyclerView.setAdapter(new ChefsOrdersAdapter(context, orderList, fontBold, fontRegular));
        }

        else if (objectList.get(position) instanceof Chef) {
            ChefItemViewHolder chefItemViewHolder=(ChefItemViewHolder) holder;
            chefItemViewHolder.chefName.setTypeface(fontBold);
            chefItemViewHolder.locationText.setTypeface(fontRegular);

            chefItemViewHolder.chefName.setText(((Chef) objectList.get(position)).getName());
            chefItemViewHolder.locationText.setText(((Chef) objectList.get(position)).getLocation());
            Glide.with(context).load(((Chef) objectList.get(position)).getImage()).placeholder(R.drawable.foodi_logo_left_image).into(chefItemViewHolder.imageView);
            chefItemViewHolder.ratingBar.setRating(((Chef) objectList.get(position)).getRatingAverage());
            chefItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChefsMainActivity.cheffragmentClass = ChefProfileFragment.class;
                    try {
                        chefMainfragment = (Fragment) ChefsMainActivity.cheffragmentClass.newInstance();
                        chefbackStateName = chefMainfragment.getClass().getName();

                        FragmentManager manager = cheffragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate(chefbackStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = cheffragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if (currentFragment != null && !currentFragment.getClass().equals(chefMainfragment.getClass())) {
                                cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).addToBackStack(chefbackStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof ChefCard) {
            return R.layout.home_chefs_card_item;
        } else if (objectList.get(position) instanceof ChefRevenues) {
            return R.layout.home_chefs_revenue_item;
        } else if (objectList.get(position) instanceof ChefMostOrderedDish) {
            return R.layout.home_chefs_most_orders_dish_item;
        } else if (objectList.get(position) instanceof ChefTopRatedDish) {
            return R.layout.home_chef_top_rated_dish_item;
        } else if (objectList.get(position) instanceof Orders) {
            return R.layout.home_chef_orders_item;
        } else if (objectList.get(position) instanceof PieChartData) {
            return R.layout.dashboard_home_pie_chart;
        } else if (objectList.get(position) instanceof PromoCodes) {
            return R.layout.chefs_promo_codes_list_item;
        } else if (objectList.get(position) instanceof FoodItem) {
            return R.layout.chef_menu_dish_list_item;
        } else if (objectList.get(position) instanceof BulkOrder) {
            return R.layout.chef_home_bulk_order_item;
        } else if (objectList.get(position) instanceof Chef) {
            return R.layout.chef_list_item;
        } else {

        }
        return super.getItemViewType(position);
    }

    public class ChefItemViewHolder extends RecyclerView.ViewHolder {
        TextView chefName;
        TextView locationText;
        RatingBar ratingBar;
        ImageView imageView;
        ConstraintLayout chefItemView;

        public ChefItemViewHolder(@NonNull View itemView) {
            super(itemView);
            chefName = itemView.findViewById(R.id.chefName);
            locationText = itemView.findViewById(R.id.locationText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageView = itemView.findViewById(R.id.imageView);
            chefItemView = itemView.findViewById(R.id.chefItemView);

        }
    }


    public class CardsItemViewHolder extends RecyclerView.ViewHolder {
        TextView newOrderCount;
        TextView newOrderHeader;
        TextView currentOrderCount;
        TextView currentOrderHeader;
        TextView subscriptionCount;
        TextView subscriptionHeader;

        public CardsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            newOrderCount = itemView.findViewById(R.id.newOrderCount);
            newOrderHeader = itemView.findViewById(R.id.newOrderHeader);
            currentOrderCount = itemView.findViewById(R.id.currentOrderCount);
            currentOrderHeader = itemView.findViewById(R.id.currentOrderHeader);
            subscriptionCount = itemView.findViewById(R.id.subscriptionCount);
            subscriptionHeader = itemView.findViewById(R.id.subscriptionHeader);
        }
    }

    public class RevenuesCardItemViewHolder extends RecyclerView.ViewHolder {
        TextView revenueHeader;
        TextView revenueMonth;
        TextView revenueamount;

        public RevenuesCardItemViewHolder(@NonNull View itemView) {
            super(itemView);
            revenueHeader = itemView.findViewById(R.id.revenueHeader);
            revenueMonth = itemView.findViewById(R.id.revenueMonth);
            revenueamount = itemView.findViewById(R.id.revenueamount);
        }
    }

    public class MostOrderedDishItemViewHolder extends RecyclerView.ViewHolder {

        TextView header;
        RecyclerView recyclerView;

        public MostOrderedDishItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
        }
    }

    public class TopRatefddDishesItemViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        RecyclerView recyclerView;

        public TopRatefddDishesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            header = itemView.findViewById(R.id.header);
        }
    }

    public class OrdersItemViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        TextView year;
        CombinedChart chart;
        LineChart lineChart;

        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            year = itemView.findViewById(R.id.year);
            chart = itemView.findViewById(R.id.chart);
            lineChart = itemView.findViewById(R.id.linechart);
        }
    }

    public class RevenueChartDishesItemViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        PieChart chart;
        CardView pieChartCard;

        public RevenueChartDishesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            chart = itemView.findViewById(R.id.pieChart);
            pieChartCard = itemView.findViewById(R.id.pieChartCard);
        }
    }

    public class BulkOrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView pagetitle;
        TextView header;
        TextView monthText;
        CalendarView calendarView;
        RecyclerView recyclerView;
        TextView thisMonth;
        RelativeLayout rootLayout;

        public BulkOrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            pagetitle = itemView.findViewById(R.id.pagetitle);
            //header = itemView.findViewById(R.id.header);
            monthText = itemView.findViewById(R.id.monthText);
            calendarView = itemView.findViewById(R.id.calendarView);
            recyclerView = itemView.findViewById(R.id.recyclerViewBulkOrder);
            thisMonth = itemView.findViewById(R.id.thisMonth);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }


    public class PromoCodesChefItemViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        TextView startDateHeader;
        TextView discountHeader;
        TextView promoValue;
        TextView startDateValue;
        TextView discountValue;
        TextView endDateHeader;
        TextView endDateValue;
        TextView forOrderHeader;
        TextView retail;

        public PromoCodesChefItemViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            startDateHeader = itemView.findViewById(R.id.startDateHeader);
            promoValue = itemView.findViewById(R.id.promoValue);
            startDateValue = itemView.findViewById(R.id.startDateValue);
            discountHeader = itemView.findViewById(R.id.discountHeader);
            discountValue = itemView.findViewById(R.id.discountValue);
            endDateHeader = itemView.findViewById(R.id.endDateHeader);
            endDateValue = itemView.findViewById(R.id.endDateValue);
            forOrderHeader = itemView.findViewById(R.id.forOrderHeader);
            retail = itemView.findViewById(R.id.retail);
        }
    }

    public class MenuItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView itemDescription;
        TextView price;
        Switch availableSwitch;
        RatingBar ratingBar;
        TextView count;
        TextView mins;
        TextView discountPercent;
        TextView actualPrice;
        RelativeLayout viewNotApproved;


        public MenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            count = itemView.findViewById(R.id.count);
            mins = itemView.findViewById(R.id.mins);
            availableSwitch = itemView.findViewById(R.id.availableSwitch);
            viewNotApproved = itemView.findViewById(R.id.viewNotApproved);
            discountPercent = itemView.findViewById(R.id.discountPercent);
            actualPrice = itemView.findViewById(R.id.actualPrice);
        }
    }




    /**
     Order Line Chart Dynamic Data
     **/
    public void getOrdersLineChart(LineChart chart) {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String url = APIBaseURL.getChefsDashboard + "" + SaveSharedPreference.getLoggedInWorkFlowID(context) + "&currentdate=" + formattedDate;

        Log.d("ChefDasURL", url);
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ChefLineGraph", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        JSONObject dataObject = new JSONObject();
                        if (jsonObject.has("data")) {
                            dataObject = jsonObject.getJSONObject("data");
                        }

                        JSONArray annualRevenueAndOrderArray = new JSONArray();
                        if (dataObject.has("annualRevenueAndOrder")) {
                            annualRevenueAndOrderArray = dataObject.getJSONArray("annualRevenueAndOrder");
                        }


                        JSONObject firstMonthObject = annualRevenueAndOrderArray.getJSONObject(0);
                        JSONObject secondMonthObject = new JSONObject();
                        Log.v("OrderLineLenght",annualRevenueAndOrderArray.length() + "");
                        if(annualRevenueAndOrderArray.length() > 1)
                        {
                             secondMonthObject = annualRevenueAndOrderArray.getJSONObject(1);
                        }


                        JSONArray monthArray = new JSONArray();
                        if (firstMonthObject.has("monthlyOrderDetails")) {
                            monthArray = firstMonthObject.getJSONArray("monthlyOrderDetails");
                        }

                        ArrayList<Entry> values1 = new ArrayList<>();
                        for (int index = 0; index < monthArray.length(); index++) {
                            JSONObject firstmonthObjectValue = monthArray.getJSONObject(index);
                            values1.add(new Entry(index, (float) firstmonthObjectValue.getDouble("totalOrder")));
                        }


                        JSONArray monthArray2 = new JSONArray();
                        if (secondMonthObject.has("monthlyOrderDetails")) {
                            monthArray2 = secondMonthObject.getJSONArray("monthlyOrderDetails");
                        }

                        ArrayList<Entry> values2 = new ArrayList<>();

                        for (int index = 0; index < monthArray2.length(); index++) {
                            JSONObject firstmonthObjectValue = monthArray2.getJSONObject(index);
                            if (!firstmonthObjectValue.optString("totalOrder").equals("")) {
                                values2.add(new Entry(index, (float) firstmonthObjectValue.getDouble("totalOrder")));
                            }

                        }


                        LineDataSet set1, set2;

                        if (chart.getData() != null &&
                                chart.getData().getDataSetCount() > 0) {
                            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
                            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
                            set1.setValues(values1);
                            set2.setValues(values2);
                            chart.getData().notifyDataChanged();
                            chart.notifyDataSetChanged();
                        } else {
                            // create a dataset and give it a type
                            set1 = new LineDataSet(values2, "");

//                            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                            set1.setDrawCircleHole(true);
                            set1.setColor(ColorTemplate.getHoloBlue());
                            set1.setCircleColor(ColorTemplate.getHoloBlue());
                            set1.setLineWidth(2f);
                            set1.setCircleRadius(3f);
                            set1.setFillAlpha(65);
                            set1.setFillColor(ColorTemplate.getHoloBlue());
                            set1.setHighLightColor(Color.rgb(244, 117, 117));
                            //set1.setFillFormatter(new MyFillFormatter(0f));
                            //set1.setDrawHorizontalHighlightIndicator(false);
                            //set1.setVisible(false);
                            //set1.setCircleHoleColor(Color.WHITE);

                            // create a dataset and give it a type
                            set2 = new LineDataSet(values1, "");
                            set2.setDrawCircleHole(true);
//                            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
                            set2.setColor(Color.RED);
                            set2.setCircleColor(Color.RED);
                            set2.setLineWidth(2f);
                            set2.setCircleRadius(3f);
                            set2.setFillAlpha(65);
                            set2.setFillColor(Color.RED);
                            set2.setHighLightColor(Color.rgb(244, 117, 117));
                            //set2.setFillFormatter(new MyFillFormatter(900f));
                            // create a data object with the data sets
                            LineData data = new LineData(set1, set2);
                            data.setValueTextColor(Color.BLACK);
                            data.setValueTextSize(9f);

                            // set data
                            chart.setData(data);

                            // redraw
                            chart.invalidate();
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
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "orders_taq");
    }



    /**
     Pie Chart Dynamic Data
     **/
    public void getPieChartDatas(PieChart chart) {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String url = APIBaseURL.getChefsDashboard + "" + SaveSharedPreference.getLoggedInWorkFlowID(context) + "&currentdate=" + formattedDate;

        Log.d("ChefDasURL", url);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    objectList = new ArrayList<>();
//
//                    objectList.clear();

                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        JSONObject dataObject = new JSONObject();
                        if (jsonObject.has("data")) {
                            dataObject = jsonObject.getJSONObject("data");
                        }

                        JSONObject monthlyRevenueByDishDetailsArray = new JSONObject();
                        if (dataObject.has("monthlyRevenueByDishDetails")) {
                            monthlyRevenueByDishDetailsArray = dataObject.getJSONObject("monthlyRevenueByDishDetails");
                        }

                        JSONArray revenueByDishesArray = new JSONArray();
                        if (monthlyRevenueByDishDetailsArray.has("revenueByDishes")) {
                            revenueByDishesArray = monthlyRevenueByDishDetailsArray.getJSONArray("revenueByDishes");
                        }
                        pieEntries = new ArrayList<>();
                        for (int i = 0; i < revenueByDishesArray.length(); i++) {
                            JSONObject revenueByDishesObject = revenueByDishesArray.getJSONObject(i);

                            pieEntries.add(new PieEntry(Float.parseFloat(revenueByDishesObject.optString("contribution")), revenueByDishesObject.optString("name"), i));
                        }


                        PieDataSet dataSet = new PieDataSet(pieEntries, "");

                        dataSet.setDrawIcons(false);

                        // dataSet.setSliceSpace(1f);
                        dataSet.setIconsOffset(new MPPointF(0, 40));
                        dataSet.setSelectionShift(5f);


                        final int[] MY_COLORS = {Color.rgb(14, 131, 187), Color.rgb(236, 163, 17), Color.rgb(246, 246, 67), Color.rgb(13, 201, 45), Color.rgb(246, 67, 67), Color.rgb(13, 201, 45),
                        };
                        ArrayList<Integer> colors = new ArrayList<Integer>();

                        for (int c : MY_COLORS) colors.add(c);


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

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_cards_taq");

    }
    /**
     Static
     **/
    private void setData(int count, float range, LineChart chart) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            float val = (float) (Math.random() * (range / 2f)) + 50;
            values1.add(new Entry(i, val));
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 450;
            values2.add(new Entry(i, val));
        }

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 500;
            values3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "DataSet 2");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(values3, "DataSet 3");
            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setColor(Color.YELLOW);
            set3.setCircleColor(Color.WHITE);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
    }

    /**
     To Make any Dish to be active or Deactive(POST)
     **/
    private void setActiveorDeactiveStatus(String chefID, String dishId, boolean status) throws JSONException {


        String url = APIBaseURL.activeOrDeactiveStatus;

        JSONObject inputObject = new JSONObject();
        inputObject.put("chefId", chefID);
        inputObject.put("dishId", dishId);
        inputObject.put("forAllDishes", false);
        inputObject.put("status", status);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.optString("errorMessage"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(context));

                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

}
