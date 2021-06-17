package stayabode.foodyHive.fragments.chefs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.ChefsOrdersAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;


public class ChefsCancelledOrderFragment extends Fragment {

    TextView menuTitle;
    TextView filterHeader;
    TextView weekHeader;
    TextView costHeader;
    TextView noMoreItem;
    RecyclerView recyclerView;
    LinearLayout currentOrderHeaderLayout;
    LinearLayout dashBoardSearch;
    List<Object> objectList = new ArrayList<>();
    public static Typeface fontBold;
    public static Typeface fontRegular;
    public static Typeface raleWayFontBold;
    public static Typeface ralewayFontRegular;
    ProgressBar progressBar;
    ProgressBar loader;
    TextView searchSpinner;

    NestedScrollView scrollView;
    NumberPicker numberPicker1;
    int index = 0;
    int size = 20;
    boolean isFiltered=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chef_cancelled_orders, container, false);
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
        menuTitle = rootView.findViewById(R.id.menuTitle);
        filterHeader = rootView.findViewById(R.id.filterHeader);
        weekHeader = rootView.findViewById(R.id.weekHeader);
        costHeader = rootView.findViewById(R.id.costHeader);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);
        loader = rootView.findViewById(R.id.loader);
        scrollView = rootView.findViewById(R.id.scrollView);
        noMoreItem = rootView.findViewById(R.id.noMoreItem);
        currentOrderHeaderLayout = rootView.findViewById(R.id.currentOrderHeaderLayout);
        dashBoardSearch = rootView.findViewById(R.id.dashBoardSearch);
        searchSpinner = rootView.findViewById(R.id.searchSpinner);
        progressBar.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        isFiltered=false;

        currentOrderHeaderLayout.setOnClickListener(new View.OnClickListener() {
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
                        index=0;
                        objectList=new ArrayList<>();
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
                        index=0;
                        objectList=new ArrayList<>();
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

        getCancelledOrdersFromAPI();
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    index++;
                    if(isFiltered){
                        try {
                            getFilteredOrders(numberPicker1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else getCancelledOrdersFromAPI();
                }
            }
        });
        return rootView;
    }

    public void getFilteredOrders(NumberPicker numberPicker) {

        progressBar.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);

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
            inputObject.put("chefid", SaveSharedPreference.getLoggedInWorkFlowID(getContext()));
            inputObject.put("orderstatus", 100);
            inputObject.put("pageno", index);
            inputObject.put("pagesize", size);
            JSONArray dishArray = new JSONArray();
            // dishArray.put("");
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

        if(index>0){
            loader.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else{
            objectList=new ArrayList<>();
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isFiltered=true;
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                try {
                    JSONObject dataObject = response.getJSONObject("data");
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
                            //orders.setTotalAmount(ordersListObject.optString("totalamount"));
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
                            objectList.add(orders);
                        }
                        recyclerView.setAdapter(new ChefsOrdersAdapter(getContext(), objectList, fontBold, fontRegular));
                    }else{
                        loader.setVisibility(View.GONE);
                        noMoreItem.setVisibility(View.VISIBLE);
                    }
                }catch (JSONException e) {
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

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()));

                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "open_orders_taq");


    }

    /**
     For Bottom Spinner Dropdown as Same as IOS
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
    Custom On Back Press
     **/
    public void onBackPressed() {
        FragmentManager fm = cheffragmentManager;
        fm.popBackStack();
    }


    /**
     Custom Method to add Order No with dash in every after four characters
     **/
    public String getOrderNoWithDashes(String orderNo, String insert, int period) {
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

    /**
     To Get Only the Cancelled Orders from API(GET)
     **/
    public void getCancelledOrdersFromAPI() {

        progressBar.setVisibility(View.VISIBLE);
        //recyclerView.setVisibility(View.GONE);
        String url = APIBaseURL.getChefsOrdersDetails + SaveSharedPreference.getLoggedInWorkFlowID(getContext()) + "&100&" + index + "&" + size;

        if (index > 0) {
            progressBar.setVisibility(View.GONE);
          //  recyclerView.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);

        }
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                isFiltered=false;
                progressBar.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
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
                            //orders.setTotalAmount(ordersListObject.optString("totalamount"));
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
                            objectList.add(orders);
                        }
                        recyclerView.setAdapter(new ChefsOrdersAdapter(getContext(), objectList, fontBold, fontRegular));
                    }else{
                        loader.setVisibility(View.GONE);
                        noMoreItem.setVisibility(View.VISIBLE);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);
            }
        }, getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "open_orders_taq");


    }
}
