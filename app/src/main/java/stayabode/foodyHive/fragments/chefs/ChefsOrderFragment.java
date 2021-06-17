package stayabode.foodyHive.fragments.chefs;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;

import stayabode.foodyHive.adapters.chefs.ChefsOrdersAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.CurrentOrders;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.models.UpcomingOrders;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
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

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefDrawer;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class ChefsOrderFragment extends Fragment {
    LinearLayout upcomingTab;
    LinearLayout currentOrderTab;
    LinearLayout completedOrderTab;
    LinearLayout filerLayout;
    LinearLayout completedOrderHeaderLayout;
    LinearLayout currentOrderHeaderLayout;
    static TextView upcoming_cart_badge;
    TextView upcomingText;
    TextView currentOrderText;

    static TextView noMoreItemTransit;
    static TextView noMoreItemOpen;

    NestedScrollView openScrollView;
    NestedScrollView transitScrollView;
    NestedScrollView completedScrollView;

    public static TextView current_cart_badge;
    TextView completedOrderText;
    FrameLayout upcomingBgFrame;
    FrameLayout completedBGFrame;
    FrameLayout currentBGFrame;

    public static Typeface fontBold;
    public static Typeface fontRegular;
    Typeface raleWayFontBold;
    Typeface ralewayFontRegular;

    public static List<Object> objectList = new ArrayList<>();
    public List<Object> transitOrderList = new ArrayList<>();
    public List<Object> completedOrderList = new ArrayList<>();
    public static RecyclerView openOrderRecyclerView;
    public static RecyclerView transitRecyclerView;
    public static RecyclerView completedRecyclerView;
    TextView cancelledOrders;
    TextView onSwitchHeader;
    TextView weekHeader;
    LinearLayout dashBoardSearch;

    NumberPicker numberPicker1;
    TextView flipBtnHeader;
    static TextView noMoreItemCompleted;
    LinearLayout acceptOrderLayout;
    public static RelativeLayout orderRootLayout;
    public static LinearLayout alphaPopBG;
    public static LinearLayout contentLayout;
    public static ProgressBar progressBar;
    static ProgressBar openloader;
    ProgressBar transitLoader;
    static ProgressBar completedloader;

    public static ChefsOrderFragment chefsOrderFragment;

    int transitIndex = 0;
    int transitSize = 20;
    static int openOrderIndex = 0;
    static int openOrderSize = 20;
    int completedIndex = 0;
    int completedSize = 20;
    TextView searchSpinner;


    public static Boolean completedBoolean = false;
    public static Boolean completedFilteredBoolean = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chef_orders, container, false);
        chefsOrderFragment = this;
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        raleWayFontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");
        ralewayFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
        cheftoolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze);
        ChefsMainActivity.chefnavigation.setVisibility(View.VISIBLE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chefDrawer.openDrawer(Gravity.LEFT);
            }
        });
        upcomingTab = rootView.findViewById(R.id.upcomingTab);
        orderRootLayout = rootView.findViewById(R.id.orderRootLayout);
        progressBar = rootView.findViewById(R.id.progressBar);
        alphaPopBG = rootView.findViewById(R.id.alphaPopBG);
        contentLayout = rootView.findViewById(R.id.contentLayout);
        currentOrderTab = rootView.findViewById(R.id.currentOrderTab);
        completedOrderTab = rootView.findViewById(R.id.completedOrderTab);
        filerLayout = rootView.findViewById(R.id.filerLayout);
        upcoming_cart_badge = rootView.findViewById(R.id.upcoming_cart_badge);
        completedOrderHeaderLayout = rootView.findViewById(R.id.completedOrderHeaderLayout);
        currentOrderHeaderLayout = rootView.findViewById(R.id.currentOrderHeaderLayout);
        cancelledOrders = rootView.findViewById(R.id.cancelledOrders);
        upcomingText = rootView.findViewById(R.id.upcomingText);
        currentOrderText = rootView.findViewById(R.id.currentOrderText);
        current_cart_badge = rootView.findViewById(R.id.current_cart_badge);
        completedOrderText = rootView.findViewById(R.id.completedOrderText);
        upcomingBgFrame = rootView.findViewById(R.id.upcomingBgFrame);
        completedBGFrame = rootView.findViewById(R.id.completedBGFrame);
        currentBGFrame = rootView.findViewById(R.id.currentBGFrame);
        flipBtnHeader = rootView.findViewById(R.id.flipBtnHeader);
        onSwitchHeader = rootView.findViewById(R.id.onSwitchHeader);
        openOrderRecyclerView = rootView.findViewById(R.id.openOrderRecyclerView);
        transitRecyclerView = rootView.findViewById(R.id.transitRecyclerView);
        completedRecyclerView = rootView.findViewById(R.id.completedRecyclerView);
        acceptOrderLayout = rootView.findViewById(R.id.acceptOrderLayout);
        openScrollView = rootView.findViewById(R.id.openScrollView);
        transitScrollView = rootView.findViewById(R.id.transitScrollView);
        completedScrollView = rootView.findViewById(R.id.completedScrollView);
        openloader = rootView.findViewById(R.id.openloader);
        transitLoader = rootView.findViewById(R.id.transitLoader);
        completedloader = rootView.findViewById(R.id.completedloader);
        noMoreItemCompleted = rootView.findViewById(R.id.noMoreItemCompleted);
        noMoreItemOpen = rootView.findViewById(R.id.noMoreItemOpen);
        noMoreItemTransit = rootView.findViewById(R.id.noMoreItemTransit);
        weekHeader = rootView.findViewById(R.id.weekHeader);
        searchSpinner = rootView.findViewById(R.id.searchSpinner);
        dashBoardSearch = rootView.findViewById(R.id.dashBoardSearch);

        acceptOrderLayout.setVisibility(View.GONE);
        openOrderRecyclerView.setVisibility(View.VISIBLE);

        openloader.setVisibility(View.GONE);
        transitLoader.setVisibility(View.GONE);
        completedloader.setVisibility(View.GONE);

        flipBtnHeader.setTypeface(fontRegular);
        onSwitchHeader.setTypeface(fontBold);
        openOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        completedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transitRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        currentOrderTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitScrollView.setVisibility(View.GONE);
                openScrollView.setVisibility(View.VISIBLE);
                completedScrollView.setVisibility(View.GONE);
                try {
                    completedIndex = 0;
                    transitIndex = 0;
                    noMoreItemCompleted.setVisibility(View.GONE);
                    noMoreItemOpen.setVisibility(View.GONE);
                    noMoreItemTransit.setVisibility(View.GONE);
                    transitLoader.setVisibility(View.GONE);
                    completedloader.setVisibility(View.GONE);
                    openloader.setVisibility(View.GONE);

                    completedRecyclerView.getAdapter().notifyDataSetChanged();
                    transitRecyclerView.getAdapter().notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentOrderHeaderLayout.setVisibility(View.GONE);
                completedOrderHeaderLayout.setVisibility(View.GONE);
                filerLayout.setVisibility(View.GONE);
                openOrderRecyclerView.setVisibility(View.VISIBLE);
                transitRecyclerView.setVisibility(View.GONE);
                completedRecyclerView.setVisibility(View.GONE);

                upcomingText.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
                currentOrderText.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
                completedOrderText.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
                upcomingTab.setBackground(getContext().getResources().getDrawable(R.drawable.edittext_border));
                currentOrderTab.setBackgroundColor(getContext().getResources().getColor(R.color.colorGreen));
                completedOrderTab.setBackground(getContext().getResources().getDrawable(R.drawable.edittext_border));
                objectList = new ArrayList<>();
                upcomingTab.setClickable(true);
                currentOrderTab.setClickable(false);
                completedOrderTab.setClickable(true);

                getOpenedOrders(1);
                getOpenedOrdersForCount();
                openScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        noMoreItemCompleted.setVisibility(View.GONE);
                        noMoreItemOpen.setVisibility(View.GONE);
                        noMoreItemTransit.setVisibility(View.GONE);
                        transitLoader.setVisibility(View.GONE);
                        completedloader.setVisibility(View.GONE);
                        openloader.setVisibility(View.GONE);
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {


                            openOrderIndex++;
                            getOpenedOrders(1);
                        }
                    }
                });
            }
        });


        upcomingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitScrollView.setVisibility(View.VISIBLE);
                openScrollView.setVisibility(View.GONE);
                completedScrollView.setVisibility(View.GONE);
                try {
                    completedIndex = 0;
                    openOrderIndex = 0;
                    noMoreItemCompleted.setVisibility(View.GONE);
                    noMoreItemOpen.setVisibility(View.GONE);
                    noMoreItemTransit.setVisibility(View.GONE);
                    transitLoader.setVisibility(View.GONE);
                    completedloader.setVisibility(View.GONE);
                    openloader.setVisibility(View.GONE);

                    completedRecyclerView.getAdapter().notifyDataSetChanged();
                    openOrderRecyclerView.getAdapter().notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentOrderHeaderLayout.setVisibility(View.GONE);
                filerLayout.setVisibility(View.GONE);
                completedOrderHeaderLayout.setVisibility(View.GONE);
                openOrderRecyclerView.setVisibility(View.GONE);
                transitRecyclerView.setVisibility(View.VISIBLE);
                completedRecyclerView.setVisibility(View.GONE);


                upcomingText.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
                currentOrderText.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
                completedOrderText.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
                upcomingTab.setBackgroundColor(getContext().getResources().getColor(R.color.colorGreen));
                currentOrderTab.setBackground(getContext().getResources().getDrawable(R.drawable.edittext_border));
                completedOrderTab.setBackground(getContext().getResources().getDrawable(R.drawable.edittext_border));
                transitOrderList = new ArrayList<>();
                upcomingTab.setClickable(false);
                currentOrderTab.setClickable(true);
                completedOrderTab.setClickable(true);

                getTransitOrders();
                transitScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                        noMoreItemCompleted.setVisibility(View.GONE);
                        noMoreItemOpen.setVisibility(View.GONE);
                        noMoreItemTransit.setVisibility(View.GONE);
                        transitLoader.setVisibility(View.GONE);
                        completedloader.setVisibility(View.GONE);
                        openloader.setVisibility(View.GONE);
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                            transitIndex++;
                            getTransitOrders();
                        }
                    }
                });
            }
        });

        completedOrderTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitScrollView.setVisibility(View.GONE);
                openScrollView.setVisibility(View.GONE);
                completedScrollView.setVisibility(View.VISIBLE);
                transitRecyclerView.setVisibility(View.GONE);
                noMoreItemTransit.setVisibility(View.GONE);
                transitLoader.setVisibility(View.GONE);

                try {
                    transitIndex = 0;
                    openOrderIndex = 0;
                    noMoreItemCompleted.setVisibility(View.GONE);
                    noMoreItemOpen.setVisibility(View.GONE);

                    completedloader.setVisibility(View.GONE);
                    openloader.setVisibility(View.GONE);

                    transitRecyclerView.getAdapter().notifyDataSetChanged();
                    openOrderRecyclerView.getAdapter().notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentOrderHeaderLayout.setVisibility(View.GONE);
                filerLayout.setVisibility(View.GONE);
                completedOrderHeaderLayout.setVisibility(View.VISIBLE);
                openOrderRecyclerView.setVisibility(View.GONE);


                completedRecyclerView.setVisibility(View.VISIBLE);
                upcomingText.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
                currentOrderText.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
                completedOrderText.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
                upcomingTab.setBackground(getContext().getResources().getDrawable(R.drawable.edittext_border));
                currentOrderTab.setBackground(getContext().getResources().getDrawable(R.drawable.edittext_border));
                completedOrderTab.setBackgroundColor(getContext().getResources().getColor(R.color.colorGreen));
                completedOrderList = new ArrayList<>();
                upcomingTab.setClickable(true);
                currentOrderTab.setClickable(true);
                completedOrderTab.setClickable(false);


                getCompletedOrdersfromAPI();
                completedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                        noMoreItemCompleted.setVisibility(View.GONE);
                        noMoreItemOpen.setVisibility(View.GONE);
                        noMoreItemTransit.setVisibility(View.GONE);
                        transitLoader.setVisibility(View.GONE);
                        completedloader.setVisibility(View.GONE);
                        openloader.setVisibility(View.GONE);
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {


                            completedIndex++;

                            if(completedBoolean)
                            {
                                getCompletedOrdersfromAPI();
                            }
                            else
                            {
                                try {
                                    getFilteredOrders(numberPicker1);
                                } catch (Exception e) {

                                }
                            }


                        }
                    }
                });
            }
        });


        progressBar.setVisibility(View.VISIBLE);
        try {
            objectList = new ArrayList<>();
            openOrderIndex = 0;
            getOpenedOrders(1);
            openOrderRecyclerView.getAdapter().notifyDataSetChanged();
            noMoreItemOpen.setVisibility(View.GONE);
            noMoreItemTransit.setVisibility(View.GONE);
            noMoreItemCompleted.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getOpenedOrdersForCount();

        openScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                noMoreItemCompleted.setVisibility(View.GONE);
                noMoreItemOpen.setVisibility(View.GONE);
                noMoreItemTransit.setVisibility(View.GONE);


                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {


                    openOrderIndex++;
                    getOpenedOrders(1);
                }
            }
        });

        cancelledOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChefsCancelledOrderFragment fragment = new ChefsCancelledOrderFragment();
                FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                ft.commit();
            }
        });

        filerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(R.layout.chef_drop_down_spinner_bottom);

                Button doneBtn = (Button) dialog.findViewById(R.id.button1);
                Button cancelBtn = (Button) dialog.findViewById(R.id.button2);
                numberPicker1 = dialog.findViewById(R.id.numberPicker1);

                doneBtn.setTypeface(fontBold);
                cancelBtn.setTypeface(fontBold);

                //Initializing a new string array with elements
                final String[] values = {/*"Sort By ",*/"1 Week ago", "2 Weeks ago", "1 Month ago", "3 Months ago", "6 Months ago"};


                numberPicker1.setMinValue(0);
                numberPicker1.setMaxValue(values.length - 1);

                //Specify the NumberPicker data source as array elements
                numberPicker1.setDisplayedValues(values);

                numberPicker1.setWrapSelectorWheel(false);
                numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                setNumberPickerDivider(numberPicker1);
                numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    }
                });

                doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        completedOrderList.clear();
                        getFilteredOrders(numberPicker1);
                        weekHeader.setText(values[numberPicker1.getValue()]);
                        searchSpinner.setText(values[numberPicker1.getValue()]);
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });


        dashBoardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(R.layout.chef_drop_down_spinner_bottom);

                Button doneBtn = (Button) dialog.findViewById(R.id.button1);
                Button cancelBtn = (Button) dialog.findViewById(R.id.button2);
                numberPicker1 = dialog.findViewById(R.id.numberPicker1);

                doneBtn.setTypeface(fontBold);
                cancelBtn.setTypeface(fontBold);

                //Initializing a new string array with elements
                final String[] values = {/*"Sort By ",*/"1 Week ago", "2 Weeks ago", "1 Month ago", "3 Months ago", "6 Months ago"};


                numberPicker1.setMinValue(0);
                numberPicker1.setMaxValue(values.length - 1);

                //Specify the NumberPicker data source as array elements
                numberPicker1.setDisplayedValues(values);

                numberPicker1.setWrapSelectorWheel(false);
                numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                setNumberPickerDivider(numberPicker1);
                numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    }
                });

                doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        completedOrderList.clear();
                        getFilteredOrders(numberPicker1);
                        weekHeader.setText(values[numberPicker1.getValue()]);
                        searchSpinner.setText(values[numberPicker1.getValue()]);
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        getCountsForTransites();
        return rootView;
    }
    /**
     Show Number Picker like in IOS spinner bottom view
     **/
    private void setNumberPickerDivider(NumberPicker picker) {
        Field[] fields = NumberPicker.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals("mSelectionDividerHeight")) {
                f.setAccessible(true);
                try {
                    f.set(picker, 1);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     Get Completed Orders Total from Filtering  based on user selection from this API(POST)
     **/
    public void getFilteredOrders(NumberPicker numberPicker) {
        progressBar.setVisibility(View.VISIBLE);
        completedRecyclerView.setVisibility(View.GONE);
        String url = APIBaseURL.getFilteredOrderDetails;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        String formattedDate = df.format(new Date());

        Date oneWeekBeforeDate = new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000); // 7 * 24 * 60 * 60 * 1000
        Date twoWeekBeforeDate = new Date(new Date().getTime() - 14 * 24 * 60 * 60 * 1000); // 7 * 24 * 60 * 60 * 1000
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        Calendar calendarthree = Calendar.getInstance();
        calendarthree.add(Calendar.MONTH, -3);

        Calendar calendarSix = Calendar.getInstance();
        calendarSix.add(Calendar.MONTH, -6);

        Date oneMonthBeforeDate = calendar.getTime();
        Date threeMonthBeforeDate = calendarthree.getTime();
        Date sixMonthBeforeDate = calendarSix.getTime();

        String oneWeekAgoDateFormat = df.format(oneWeekBeforeDate);
        String twoWeekBeforeDateFormat = df.format(twoWeekBeforeDate);
        String oneMonthBeforeDateFormat = df.format(oneMonthBeforeDate);
        String threeMonthBeforeDateFormat = df.format(threeMonthBeforeDate);
        String sixMonthBeforeDateFormat = df.format(sixMonthBeforeDate);

        JSONObject inputObject = new JSONObject();
        try {
            inputObject.put("chefid", SaveSharedPreference.getLoggedInWorkFlowID(chefsOrderFragment.getContext()));
            inputObject.put("orderstatus", 13);
            inputObject.put("pageno", completedIndex);
            inputObject.put("pagesize", completedSize);
            JSONArray dishArray = new JSONArray();
//            dishArray.put("");
            inputObject.put("dish", dishArray);
            if (numberPicker.getValue() == 0) {
                inputObject.put("startdate", oneWeekAgoDateFormat);
            } else if (numberPicker.getValue() == 1) {
                inputObject.put("startdate", twoWeekBeforeDateFormat);
            } else if (numberPicker.getValue() == 2) {
                inputObject.put("startdate", oneMonthBeforeDateFormat);
            } else if (numberPicker.getValue() == 3) {
                inputObject.put("startdate", threeMonthBeforeDateFormat);
            } else if (numberPicker.getValue() == 4) {
                inputObject.put("startdate", sixMonthBeforeDateFormat);
            }

            inputObject.put("enddate", formattedDate);
            inputObject.put("orderid", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        completedBoolean = false;
        completedFilteredBoolean = true;

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                completedRecyclerView.setVisibility(View.VISIBLE);
                completedBoolean = false;
                completedFilteredBoolean = true;
                try {
                    JSONObject dataObject = response.getJSONObject("data");
                    JSONArray orderlistArray = new JSONArray();
                    if (dataObject.has("orderlist")) {
                        orderlistArray = dataObject.getJSONArray("orderlist");
                    }
                    if (dataObject.length() > 0) {
                        for (int i = 0; i < orderlistArray.length(); i++) {
                            JSONObject ordersListObject = orderlistArray.getJSONObject(i);
                            Orders orders = new Orders();
                            orders.setChefId(ordersListObject.optString("chefid"));
                            orders.setOrderNo(ordersListObject.optString("orderno"));
                            orders.setOrderNo(getOrderNoWithDashes(orders.getOrderNo(), "-", 4));
                            orders.setId(ordersListObject.optString("id"));
                            // orders.setTotalAmount(ordersListObject.optString("totalamount"));
                            orders.setTotalAmount(String.format("%.2f", ordersListObject.optDouble("totalamount")));
                            orders.setPreparationTime(ordersListObject.optString("preparationtime"));
                            orders.setOrderStatus(ordersListObject.optString("orderstatus"));
                            orders.setCreateddate(ordersListObject.optString("createddate"));


                            orders.setPaymentmethod(ordersListObject.optString("paymentmethod"));
                            orders.setCancelledreason(ordersListObject.optString("cancelledreason"));

                            JSONArray menulistArray = new JSONArray();

                            if (ordersListObject.has("menulist")) {
                                menulistArray = ordersListObject.getJSONArray("menulist");
                            }
                            List<FoodItem> foodItemList = new ArrayList<>();

                            for (int j = 0; j < menulistArray.length(); j++) {
                                JSONObject menulistObject = menulistArray.getJSONObject(j);
                                FoodItem foodItem = new FoodItem();
                                foodItem.setFoodId(menulistObject.optString("menuid"));
                                foodItem.setFoodName(menulistObject.optString("menuname"));
                                foodItem.setPrice(menulistObject.optString("menuprice"));
                                foodItem.setCartAddedQuantity(menulistObject.optInt("quantity"));
                                foodItemList.add(foodItem);
                                orders.setFoodItemList(foodItemList);
                            }
                            completedOrderList.add(orders);
                        }
                    } else {

                        Toast.makeText(chefsOrderFragment.getContext(), "No more items!", Toast.LENGTH_SHORT).show();

                    }
                    completedRecyclerView.setAdapter(new ChefsOrdersAdapter(chefsOrderFragment.getContext(), completedOrderList, fontBold, fontRegular));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(chefsOrderFragment.getContext()));

                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "open_orders_taq");


    }
    /**
     Get Current Orders Total from this API(GET)
     **/
    public static void getOpenedOrders(int identity) {
        progressBar.setVisibility(View.VISIBLE);
        String url = "";
        try {
            if(identity==2){
                openOrderIndex=0;
            }
            url = APIBaseURL.getChefsOrdersDetails + SaveSharedPreference.getLoggedInWorkFlowID(chefsOrderFragment.getContext()) + "&2&" + openOrderIndex + "&" + openOrderSize;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        if (openOrderIndex > 0 && identity == 2) {
            progressBar.setVisibility(View.GONE);
            openloader.setVisibility(View.VISIBLE);
        } else {
            objectList = new ArrayList<>();
            objectList.clear();
        }

        noMoreItemCompleted.setVisibility(View.GONE);
        noMoreItemOpen.setVisibility(View.GONE);
        noMoreItemTransit.setVisibility(View.GONE);
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);
                openloader.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray orderlistArray = new JSONArray();
                    if (dataObject.has("orderlist")) {
                        orderlistArray = dataObject.getJSONArray("orderlist");
                    }

                    if (identity == 2) {
                        objectList.clear();
                    }


                    if (orderlistArray.length() > 0) {
                        noMoreItemOpen.setVisibility(View.GONE);
                        for (int i = 0; i < orderlistArray.length(); i++) {
                            JSONObject ordersListObject = orderlistArray.getJSONObject(i);
                            CurrentOrders orders = new CurrentOrders();
                            orders.setChefId(ordersListObject.optString("chefid"));
                            orders.setOrderNoWithoutDash(ordersListObject.optString("orderno"));
                            orders.setOrderNo(getOrderNoWithDashes(orders.getOrderNoWithoutDash(), "-", 4));
                            orders.setId(ordersListObject.optString("id"));
                            //orders.setTotalAmount(ordersListObject.optString("totalamount"));
                            orders.setTotalAmount(String.format("%.2f", ordersListObject.optDouble("totalamount")));
                            orders.setPreparationTime(ordersListObject.optInt("preparationtime"));
                            orders.setCookingTime(10);
                            orders.setOrderStatus(ordersListObject.optString("orderstatus"));
                            orders.setCreateddate(ordersListObject.optString("createddate"));
                            orders.setOrderCreatedDate(ordersListObject.optString("createddate"));
                            orders.setPaymentmethod(ordersListObject.optString("paymentmethod"));
                            orders.setCancelledreason(ordersListObject.optString("cancelledreason"));
                            orders.setOrderStatusEnum(ordersListObject.optInt("orderstatus"));

                            Date startDateTimeforCooking = null;
                            try {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                                startDateTimeforCooking = df.parse(ordersListObject.optString("createddate"));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm:ss");
                            outputFormat.setTimeZone(TimeZone.getDefault());
                            String formattedDate1 = outputFormat.format(startDateTimeforCooking);



                            int hours = ordersListObject.optInt("preparationtime") / 60; //since both are ints, you get an int
                            int minutes = ordersListObject.optInt("preparationtime") % 60;
                            int seconds = ordersListObject.optInt("preparationtime") % 60;

                            SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
                            df.setTimeZone(TimeZone.getDefault());
                            Date startDate = df.parse(formattedDate1);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(startDate);
                            cal.add(Calendar.MINUTE, ordersListObject.optInt("preparationtime"));
                            String endTime = df.format(cal.getTime());



                            Calendar calCookingTime = Calendar.getInstance();
                            calCookingTime.setTime(startDate);
                            calCookingTime.add(Calendar.MINUTE, 10);
                            String endCookTime = df.format(calCookingTime.getTime());



                            Date endDate = df.parse(endTime);
                            Date endCookDate = df.parse(endCookTime);


                            long maxCookingTimeDifference = endCookDate.getTime() - startDate.getTime();


                            long maxdifference = endDate.getTime() - startDate.getTime();


                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());


                            Date currentDate = sdf.parse(currentDateandTime);


                            long ellapsedDifference = currentDate.getTime() - startDate.getTime();


                            orders.setMaxDifferences(maxdifference);
                            orders.setStartCookingTimeDifference(maxCookingTimeDifference);
                            orders.setEllapsedDifferences(ellapsedDifference);
                            orders.setStartTimeinMillis(currentDate.getTime());
                            orders.setEndTimeinMillis(endDate.getTime());
                            orders.setStartDateTime(currentDate);
                            orders.setEndDateTime(endDate);
                            orders.setPreparationTimeHours(hours);
                            orders.setPreparationTimeMinutes(minutes);
                            orders.setPreparationTimeMinutes(seconds);
                            orders.setEndDateTime(endDate);



                            DateFormat formatter = new SimpleDateFormat("hh:mm:ss", Locale.US);
                            formatter.setTimeZone(TimeZone.getDefault());
                            String currentTimeFromMillis = formatter.format(new Date(currentDate.getTime()));
                            String startTimeinMillis = formatter.format(new Date(startDate.getTime()));
                            String endTimeinMillis = formatter.format(new Date(endDate.getTime()));


                            JSONArray menulistArray = new JSONArray();

                            if (ordersListObject.has("menulist")) {
                                menulistArray = ordersListObject.getJSONArray("menulist");
                            }
                            List<FoodItem> foodItemList = new ArrayList<>();

                            for (int j = 0; j < menulistArray.length(); j++) {
                                JSONObject menulistObject = menulistArray.getJSONObject(j);
                                FoodItem foodItem = new FoodItem();
                                foodItem.setFoodId(menulistObject.optString("menuid"));
                                foodItem.setFoodName(menulistObject.optString("menuname"));
                                foodItem.setPrice(menulistObject.optString("menuprice"));
                                foodItem.setCartAddedQuantity(menulistObject.optInt("quantity"));
                                foodItemList.add(foodItem);
                                orders.setFoodItemList(foodItemList);
                            }
                            objectList.add(orders);
                        }

                        openOrderRecyclerView.setAdapter(new ChefsOrdersAdapter(chefsOrderFragment.getContext(), objectList, fontBold, fontRegular));
                        openOrderRecyclerView.getAdapter().notifyDataSetChanged();
                    } else {
                        if (identity == 2) {
                            objectList.clear();
                            try {
                                openOrderRecyclerView.setAdapter(new ChefsOrdersAdapter(chefsOrderFragment.getContext(), objectList, fontBold, fontRegular));
                                openOrderRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                        openloader.setVisibility(View.GONE);


                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                openloader.setVisibility(View.GONE);

                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity) chefsOrderFragment.getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(chefsOrderFragment.getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(chefsOrderFragment.getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(chefsOrderFragment.getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError)
                {
                    objectList.clear();
                    openOrderRecyclerView.getAdapter().notifyDataSetChanged();
                    Toast.makeText(chefsOrderFragment.getContext(), "Server Error,Please try again later!", Toast.LENGTH_SHORT).show();
                }
            }
        }, chefsOrderFragment.getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "open_orders_taq");


    }

    /**
     Get Current Orders Total Count from this API(GET)
     **/
    public static void getOpenedOrdersForCount() {
        String url1 = "";

        try {
            url1 = APIBaseURL.getChefsOrdersDetails + SaveSharedPreference.getLoggedInWorkFlowID(chefsOrderFragment.getContext()) + "&2&0&100";
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray orderlistArray = new JSONArray();
                    if (dataObject.has("orderlist")) {
                        orderlistArray = dataObject.getJSONArray("orderlist");
                    }

                    current_cart_badge.setText(String.valueOf(orderlistArray.length()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                openloader.setVisibility(View.GONE);

                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity) chefsOrderFragment.getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(chefsOrderFragment.getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(chefsOrderFragment.getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(chefsOrderFragment.getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, chefsOrderFragment.getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "open_orders_taq");
    }

    /**
     Get Transit Orders Total from this API(GET)
     **/
    public void getTransitOrders() {
        progressBar.setVisibility(View.VISIBLE);
        String url = "";
        try {
            url = APIBaseURL.getChefsOrdersDetails + SaveSharedPreference.getLoggedInWorkFlowID(chefsOrderFragment.getContext()) + "&5&" + transitIndex + "&" + transitSize;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (transitIndex > 0) {
            progressBar.setVisibility(View.GONE);
            transitLoader.setVisibility(View.VISIBLE);
        }
        noMoreItemCompleted.setVisibility(View.GONE);
        noMoreItemOpen.setVisibility(View.GONE);
        noMoreItemTransit.setVisibility(View.GONE);
        completedloader.setVisibility(View.GONE);
        openloader.setVisibility(View.GONE);
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                transitLoader.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray orderlistArray = new JSONArray();
                    if (dataObject.has("orderlist")) {
                        orderlistArray = dataObject.getJSONArray("orderlist");
                    }

                    if (orderlistArray.length() > 0) {
                        for (int i = 0; i < orderlistArray.length(); i++) {
                            JSONObject ordersListObject = orderlistArray.getJSONObject(i);
                            UpcomingOrders orders = new UpcomingOrders();
                            orders.setChefId(ordersListObject.optString("chefid"));
                            orders.setOrderNo(ordersListObject.optString("orderno"));
                            orders.setOrderNo(getOrderNoWithDashes(orders.getOrderNo(), "-", 4));
                            orders.setId(ordersListObject.optString("id"));
                            orders.setTotalAmount(String.format("%.2f", ordersListObject.optDouble("totalamount")));
                            orders.setPreparationTime(ordersListObject.optString("preparationtime"));
                            orders.setCookingTime(10);
                            orders.setOrderStatus(ordersListObject.optString("orderstatus"));
                            orders.setCreateddate(ordersListObject.optString("createddate"));
                            orders.setPaymentmethod(ordersListObject.optString("paymentmethod"));
                            orders.setCancelledreason(ordersListObject.optString("cancelledreason"));


                            JSONArray menulistArray = new JSONArray();

                            if (ordersListObject.has("menulist")) {
                                menulistArray = ordersListObject.getJSONArray("menulist");
                            }
                            List<FoodItem> foodItemList = new ArrayList<>();

                            for (int j = 0; j < menulistArray.length(); j++) {
                                JSONObject menulistObject = menulistArray.getJSONObject(j);
                                FoodItem foodItem = new FoodItem();
                                foodItem.setFoodId(menulistObject.optString("menuid"));
                                foodItem.setFoodName(menulistObject.optString("menuname"));
                                foodItem.setPrice(menulistObject.optString("menuprice"));
                                foodItem.setCartAddedQuantity(menulistObject.optInt("quantity"));
                                foodItemList.add(foodItem);
                                orders.setFoodItemList(foodItemList);
                            }
                            transitOrderList.add(orders);
                        }
                        transitRecyclerView.setAdapter(new ChefsOrdersAdapter(getContext(), transitOrderList, fontBold, fontRegular));

                    } else {
                        transitLoader.setVisibility(View.GONE);
                        noMoreItemTransit.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                transitLoader.setVisibility(View.GONE);
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity) chefsOrderFragment.getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(chefsOrderFragment.getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(chefsOrderFragment.getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(chefsOrderFragment.getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, chefsOrderFragment.getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "open_orders_taq");


    }

    /**
     Get Completed Orders Total from this API(GET)
     **/
    public void getCompletedOrdersfromAPI() {
        String url = "";
        try {
            url = APIBaseURL.getChefsOrdersDetails + SaveSharedPreference.getLoggedInWorkFlowID(chefsOrderFragment.getContext()) + "&13&" + completedIndex + "&" + completedSize/* + "5efee122b428d30c0ce486e8&12&0&6"*/;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        progressBar.setVisibility(View.VISIBLE);
        if (completedIndex > 0) {
            progressBar.setVisibility(View.GONE);
            completedloader.setVisibility(View.VISIBLE);
        }
        noMoreItemCompleted.setVisibility(View.GONE);
        noMoreItemOpen.setVisibility(View.GONE);
        noMoreItemTransit.setVisibility(View.GONE);
        transitLoader.setVisibility(View.GONE);
        openloader.setVisibility(View.GONE);

        completedBoolean = true;
        completedFilteredBoolean = false;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                completedloader.setVisibility(View.GONE);
                completedBoolean = true;
                completedFilteredBoolean = false;

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray orderlistArray = new JSONArray();
                    if (dataObject.has("orderlist")) {
                        orderlistArray = dataObject.getJSONArray("orderlist");
                    }

                    if (orderlistArray.length() > 0) {
                        for (int i = 0; i < orderlistArray.length(); i++) {
                            JSONObject ordersListObject = orderlistArray.getJSONObject(i);
                            Orders orders = new Orders();
                            orders.setChefId(ordersListObject.optString("chefid"));
                            orders.setOrderNo(ordersListObject.optString("orderno"));
                            orders.setOrderNo(getOrderNoWithDashes(orders.getOrderNo(), "-", 4));
                            orders.setId(ordersListObject.optString("id"));
                            orders.setTotalAmount(String.format("%.2f", ordersListObject.optDouble("totalamount")));
                            orders.setPreparationTime(ordersListObject.optString("preparationtime"));
                            orders.setOrderStatus(ordersListObject.optString("orderstatus"));
                            orders.setCreateddate(ordersListObject.optString("createddate"));


                            orders.setPaymentmethod(ordersListObject.optString("paymentmethod"));
                            orders.setCancelledreason(ordersListObject.optString("cancelledreason"));

                            JSONArray menulistArray = new JSONArray();

                            if (ordersListObject.has("menulist")) {
                                menulistArray = ordersListObject.getJSONArray("menulist");
                            }
                            List<FoodItem> foodItemList = new ArrayList<>();

                            for (int j = 0; j < menulistArray.length(); j++) {
                                JSONObject menulistObject = menulistArray.getJSONObject(j);
                                FoodItem foodItem = new FoodItem();
                                foodItem.setFoodId(menulistObject.optString("menuid"));
                                foodItem.setFoodName(menulistObject.optString("menuname"));
                                foodItem.setPrice(menulistObject.optString("menuprice"));
                                foodItem.setCartAddedQuantity(menulistObject.optInt("quantity"));
                                foodItemList.add(foodItem);
                                orders.setFoodItemList(foodItemList);
                            }
                            completedOrderList.add(orders);
                        }

                        completedRecyclerView.setAdapter(new ChefsOrdersAdapter(getContext(), completedOrderList, fontBold, fontRegular));

                    } else {
                        completedloader.setVisibility(View.GONE);
                        noMoreItemCompleted.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity) chefsOrderFragment.getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(chefsOrderFragment.getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(chefsOrderFragment.getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(chefsOrderFragment.getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, chefsOrderFragment.getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "open_orders_taq");


    }

    /**
     Get Transit Orders Total Count from this API(GET)
     **/
    public static void getCountsForTransites() {

        String url = "";
        try {
            url = APIBaseURL.getChefsOrdersDetails + SaveSharedPreference.getLoggedInWorkFlowID(chefsOrderFragment.getContext()) + "&5&0&100";
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray orderlistArray = new JSONArray();
                    if (dataObject.has("orderlist")) {
                        orderlistArray = dataObject.getJSONArray("orderlist");
                    }

                    if (orderlistArray.length() != 0) {

                        upcoming_cart_badge.setText(String.valueOf(orderlistArray.length()));
                    } else {

                        upcoming_cart_badge.setText(String.valueOf(0));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity) chefsOrderFragment.getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(chefsOrderFragment.getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(chefsOrderFragment.getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(chefsOrderFragment.getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, chefsOrderFragment.getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "open_orders_taq");

    }

    /**
     Get  Orders Numbers with dash for after every 4 characters
     **/
    public static String getOrderNoWithDashes(String orderNo, String insert, int period) {
        StringBuilder builder = new StringBuilder(
                orderNo.length() + insert.length() * (orderNo.length() / period) + 1);

        int index = 0;
        String prefix = "";
        while (index < orderNo.length()) {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            prefix = insert;
            builder.append(orderNo.substring(index,
                    Math.min(index + period, orderNo.length())));
            index += period;
        }
        return builder.toString();
    }


}
