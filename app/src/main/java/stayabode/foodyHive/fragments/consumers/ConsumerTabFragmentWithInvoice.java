package stayabode.foodyHive.fragments.consumers;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.adapters.consumers.ExpandableListAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.CancelledOrdersHeader;
import stayabode.foodyHive.models.ClosedOrderHeader;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.models.OrdersHeader;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ConsumerTabFragmentWithInvoice extends Fragment {

    TextView title;
    TextView header;
    String sTitle;
    Typeface poppinsMedium;
    Typeface poppinsBold;
    Typeface poppinsLight;
    Typeface poppinsSemiBold;
    Typeface robotoRegular;
    Typeface robotoBold;
    ProgressBar progressBar;
    ProgressBar itemsLoader;
    NestedScrollView nestedScrollViewAll;

    List<Object> objectList = new ArrayList<>();
    List<Orders> cancelledOrdersList;
    List<Orders> ordersList ;
    List<Orders> closedOrdersList ;
    RecyclerView recyclerView;
    RecyclerView recyclerViewAll;

    final int openOrdersId = 12;
    final int closedOrdersId = 13;
    final int completedOrdersId = 100;

    private int completedIndex = 0;
    private int openIndex = 0;
    private int closedIndex = 0;
    private int size = 500;




    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String searchString="";
    long lastFifteen=0;
    long lastThirty=0;
    long dateStart=0;
    long dateEnd=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consumer_tab_invoice, container, false);
        poppinsMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");
        poppinsSemiBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        robotoBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        robotoRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollViewAll = view.findViewById(R.id.nestedScrollViewAll);
        recyclerViewAll = view.findViewById(R.id.recyclerViewAll);
        itemsLoader = view.findViewById(R.id.itemsLoader);
        header = view.findViewById(R.id.header);

        recyclerViewAll.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recyclerView);

        sTitle = getArguments().getString("title");
        // lastFifteen= Long.parseLong(getArguments().getString("lastFifteen"));

        progressBar.setVisibility(View.VISIBLE);
        itemsLoader.setVisibility(View.GONE);
        objectList = new ArrayList<>();
        cancelledOrdersList = new ArrayList<>();
        ordersList = new ArrayList<>();
        closedOrdersList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);


        // recyclerView.setAdapter(new InfoListsAdapterInvoice(getContext(), objectList, recyclerView, poppinsSemiBold, poppinsLight, poppinsMedium, poppinsBold, robotoBold, robotoRegular));



        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);


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
//                Toast.makeText(getActivity(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getActivity(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getActivity(),
//                        listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // preparing list data
        //prepareListData();
        getUserOrders();


        //getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();

//        searchString = getArguments().getString("searchString");
//        lastFifteen = Long.parseLong(getArguments().getString("lastFifteen"));
//        lastThirty = Long.parseLong(getArguments().getString("lastThirty"));
//        dateStart = Long.parseLong(getArguments().getString("dateStart"));
//        dateEnd = Long.parseLong(getArguments().getString("dateEnd"));


//        if (sTitle.equalsIgnoreCase("open")) {
//            try {
//                getOrders(openOrdersId);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //perform pagination
//            nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                        openIndex++;
//                        try {
//                            getOrders(openOrdersId);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            });
//        } else if (sTitle.equalsIgnoreCase("closed")) {
//            try {
//                getOrders(closedOrdersId);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //perform pagination
//            nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                        closedIndex++;
//                        try {
//                            getOrders(closedOrdersId);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            });
//        } else if (sTitle.equalsIgnoreCase("cancelled")) {
//
//            try {
//                getOrders(completedOrdersId);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //perform pagination
//            nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                    @Override
//                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                            completedIndex++;
//                            try {
//                                getOrders(completedOrdersId);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }
//                });
//
//        }
//        recyclerView.getAdapter().
//
//    notifyDataSetChanged();

    }


    /**
     get open, closed and cancelled order list based on order ID passed(POST)
     **/
    public void getOrders(int id) throws JSONException {
        String url = APIBaseURL.getConsumersOrdersList;


        JSONObject inputObject = new JSONObject();
        inputObject.put("consumerEmailId", SaveSharedPreference.getLoggedInUserEmail(getContext()));
        inputObject.put("consumerOrderStatus", id);
        JSONObject pagaObject = new JSONObject();
        if (id == openOrdersId) {
            pagaObject.put("size", size);
            pagaObject.put("index", openIndex);
            inputObject.put("page", pagaObject);
            if(openIndex>0){
                itemsLoader.setVisibility(View.VISIBLE);
            }
        } else if (id == closedOrdersId) {
            pagaObject.put("size", size);
            pagaObject.put("index", closedIndex);
            inputObject.put("page", pagaObject);
            if(closedIndex>0){
                itemsLoader.setVisibility(View.VISIBLE);
            }
        } else if (id == completedOrdersId) {
            pagaObject.put("size", size);
            pagaObject.put("index", completedIndex);
            inputObject.put("page", pagaObject);
            if(completedIndex>0){
                itemsLoader.setVisibility(View.VISIBLE);
            }
        }



        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    itemsLoader.setVisibility(View.GONE);
                    JSONArray dataArray = response.getJSONArray("data");
                    objectList.clear();

                    OrdersHeader openOrdersHeader = new OrdersHeader();
                    ClosedOrderHeader closedOrderHeader = new ClosedOrderHeader();
                    CancelledOrdersHeader cancelledOrdersHeader = new CancelledOrdersHeader();
                    if(openIndex==0){

                        ordersList=new ArrayList<>();
                    } if(closedIndex==0){

                        closedOrdersList=new ArrayList<>();
                    }  if(completedIndex==0){

                        cancelledOrdersList=new ArrayList<>();
                    }
                    for (int i = 0; i < dataArray.length(); i++) {
                        Orders orders = new Orders();


                        JSONObject dataObject = dataArray.getJSONObject(i);
                        JSONArray orderMenuDetailsArray = new JSONArray();
                        if (dataObject.has("orderMenuDetails")) {
                            orderMenuDetailsArray = dataObject.getJSONArray("orderMenuDetails");
                        }

                        orders.setOrderID(dataObject.optString("orderId"));
                        orders.setChefName(dataObject.optString("chefName"));
                        orders.setOrderDate(dataObject.optString("orderDate"));
                        orders.setChefId(dataObject.optString("chefId"));
                        orders.setAmount(dataObject.optString("amount"));
                        orders.setPaymentmethod(dataObject.optString("paymentBy"));
                        orders.setChefImage(dataObject.optString("profilePic"));

                        List<FoodItem> foodItemList = new ArrayList<>();
                        List<FoodItem> passItemsfoodItemList = new ArrayList<>();
                        for (int j = 0; j < orderMenuDetailsArray.length(); j++) {
                            JSONObject orderMenuDetailsObject = orderMenuDetailsArray.getJSONObject(j);
                            FoodItem foodItem = new FoodItem();
                            if (orderMenuDetailsObject.getJSONArray("dishImagePath").length() != 0) {
                                foodItem.setFoodImage(orderMenuDetailsObject.getJSONArray("dishImagePath").get(0).toString());
                            }

                            foodItem.setFoodName(orderMenuDetailsObject.optString("menuName"));
                            foodItem.setPrice(String.format("%.2f", dataObject.optDouble("price")));
                            foodItem.setCartAddedQuantity(orderMenuDetailsObject.optInt("quantity"));
                            foodItem.setTime(dataObject.optString("orderDate"));
                            foodItem.setChefName(dataObject.optString("chefName"));
                            foodItem.setFoodId(orderMenuDetailsObject.optString("dishId"));
                            if (orderMenuDetailsArray.length() != 0) {
                                foodItemList.add(foodItem);
                                orders.setFoodItemList(foodItemList);
                            }


                        }

                        if (orderMenuDetailsArray.length() != 0) {
                            JSONObject singleObject = orderMenuDetailsArray.getJSONObject(0);

                            FoodItem foodItem = new FoodItem();
                            if (singleObject.getJSONArray("dishImagePath").length() != 0) {
                                foodItem.setFoodImage(singleObject.getJSONArray("dishImagePath").get(0).toString());
                            }
                            //foodItem.setFoodImage(singleObject.getJSONArray("dishImagePath").get(0).toString());
                            foodItem.setFoodName(singleObject.optString("menuName"));
                            foodItem.setPrice(dataObject.optString("amount"));
                            foodItem.setCartAddedQuantity(singleObject.optInt("quantity"));
                            foodItem.setTime(dataObject.optString("orderDate"));
                            foodItem.setChefName(dataObject.optString("chefName"));
                            if (dataObject.optInt("orderDetailStatus") >= 0 && dataObject.optInt("orderDetailStatus") <= 2) {
                                foodItem.setStatus("Accepted");
                            }
                            else if (dataObject.optInt("orderDetailStatus") >= 3 && dataObject.optInt("orderDetailStatus") <= 6) {
                                foodItem.setStatus("Preparing");
                            } else if (dataObject.optInt("orderDetailStatus") >= 7 && dataObject.optInt("orderDetailStatus") <= 11) {
                                foodItem.setStatus("On the way");
                            } else if (dataObject.optInt("orderDetailStatus") == 100) {
                                foodItem.setStatus("Cancelled");
                            } else if (dataObject.optInt("orderDetailStatus") >= 12 && dataObject.optInt("orderDetailStatus") <= 99) {
                                foodItem.setStatus("Delivered");
                            }
                            if (orderMenuDetailsArray.length() != 0) {
                                passItemsfoodItemList.add(foodItem);
                                orders.setSingleFoodItemList(passItemsfoodItemList);
                            }


                        }

                        if (orderMenuDetailsArray.length() != 0) {
                            ordersList.add(orders);
                        }




                        if (orderMenuDetailsArray.length() != 0) {
                            closedOrdersList.add(orders);
                        }



                        if (orderMenuDetailsArray.length() != 0) {
                            cancelledOrdersList.add(orders);
                        }



                    }
                    openOrdersHeader.setOrdersList(ordersList);
                    closedOrderHeader.setOrdersList(closedOrdersList);
                    cancelledOrdersHeader.setOrdersList(cancelledOrdersList);


                    if(ordersList.size()==0){
                        header.setVisibility(View.VISIBLE);
                    }if(closedOrdersList.size()==0) {
                        header.setVisibility(View.VISIBLE);
                    }if(cancelledOrdersList.size()==0){
                        header.setVisibility(View.VISIBLE);
                    }


                    if (id == openOrdersId) {
                        objectList.add(openOrdersHeader);
                    } else if (id == closedOrdersId) {
                        objectList.add(closedOrderHeader);
                    } else if (id == completedOrdersId) {
                        objectList.add(cancelledOrdersHeader);
                    }

                    recyclerView.getAdapter().notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                if (error instanceof AuthFailureError) {
                    if (!SaveSharedPreference.getLoggedInUserRole(getContext()).equals("")) {
                        //TODO
                        ViewGroup viewGroup = ((Activity) getContext()).findViewById(android.R.id.content);

                        //then we will inflate the custom alert dialog xml that we created
                        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                        Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                        ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                        //Now we need an AlertDialog.Builder object
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                        //setting the view of the builder to our custom view that we already inflated
                        builder.setView(dialogView);

                        //finally creating the alert dialog and displaying it
                        android.app.AlertDialog alertDialog = builder.create();


                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                                ConsumerMainActivity.logout();

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
                    }

                } else if (error instanceof NetworkError) {
                    Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "orders_taq");
    }



    private void prepareListData() {


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        progressBar.setVisibility(View.GONE);
        // Adding child data
        listDataHeader.add("FH0000000001");
        listDataHeader.add("FH0000000002");
        listDataHeader.add("FH0000000003");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");


        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("The Godfather");
        nowShowing.add("The Godfather: Part II");


        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);


    }



    public void getUserOrders() {
        String url = APIBaseURL.getConsumersOrdersfullList + SaveSharedPreference.getLoggedInUserEmail(getActivity());
        //String url = APIBaseURL.getConsumersOrdersfullList + "satcatbat@gmail.com";
        //String url = APIBaseURL.getConsumersOrdersfullList + "jd.ramkumar@gmail.com";



        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);

                try {

                    JSONObject  jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    listDataHeader = new ArrayList<String>();
                    listDataChild = new HashMap<String, List<String>>();

                    ArrayList<String> invoiceidBaseOnchef = new ArrayList<String>();

                    int adddataTo=0;
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject fullOrderData = dataArray.getJSONObject(i);

                        if(fullOrderData.optString("cartDetails")!=null && (fullOrderData.optString("cartDetails").length()>0)&& (!(fullOrderData.optString("cartDetails").equals("[]")))){
                            if(fullOrderData.optString("orderInvoiceNo") != null && !fullOrderData.optString("orderInvoiceNo") .isEmpty()&& !fullOrderData.optString("orderInvoiceNo").equals("null")) {
                                if(fullOrderData.optString("paymentMethod") != null && !fullOrderData.optString("paymentMethod") .isEmpty()&& !fullOrderData.optString("paymentMethod").equals("null"))
                                {

                                JSONArray cartDetailsArray = fullOrderData.getJSONArray("cartDetails");
                                List<String> comingSoon = new ArrayList<String>();
                                JSONObject imagelist = new JSONObject();

                                String open_order = "0";
                                String closed_order = "0";
                                String cancelled_order = "0";
                                long timeInMillis = 0;


                                for (int j = 0; j < cartDetailsArray.length(); j++) {
                                    JSONObject cartDetailsData = cartDetailsArray.getJSONObject(j);
                                    JSONObject listDetail = new JSONObject();
                                    try {

                                        int Allquantity = 0;

                                        if (cartDetailsData.optString("chefName").equals(Globaluse.searchString)) {
                                            invoiceidBaseOnchef.add(fullOrderData.optString("orderInvoiceNo"));
                                        }


                                        listDetail.put("orderNo", cartDetailsData.optString("orderNo"));

                                        String consumerOrderStatus_str = "";

                                        if (cartDetailsData.optInt("consumerOrderStatus") >= 0 && cartDetailsData.optInt("consumerOrderStatus") <= 2) {
                                            // foodItem.setStatus("Accepted");
                                            //open_order="1";
                                            consumerOrderStatus_str = "Accepted";
                                        } else if (cartDetailsData.optInt("consumerOrderStatus") >= 3 && cartDetailsData.optInt("consumerOrderStatus") <= 6) {
                                            //foodItem.setStatus("Preparing");
                                            //open_order="1";
                                            consumerOrderStatus_str = "Preparing";

                                        } else if (cartDetailsData.optInt("consumerOrderStatus") >= 7 && cartDetailsData.optInt("consumerOrderStatus") <= 11) {
                                            //foodItem.setStatus("On the way");

                                            //open_order="1";
                                            consumerOrderStatus_str = "Preparing";

                                        } else if (cartDetailsData.optInt("consumerOrderStatus") == 100) {
                                            //foodItem.setStatus("Cancelled");
                                            //cancelled_order="1";

                                            consumerOrderStatus_str = "Cancelled";
                                        } else if (cartDetailsData.optInt("consumerOrderStatus") >= 12 && cartDetailsData.optInt("consumerOrderStatus") <= 99) {
                                            // foodItem.setStatus("Delivered");
                                            //closed_order="1";
                                            consumerOrderStatus_str = "Delivered";
                                        }


                                        listDetail.put("consumerOrderStatus", consumerOrderStatus_str);


                                        // listDetail.put("consumerOrderStatus", cartDetailsData.optString("consumerOrderStatus"));


                                        listDetail.put("orderCreatedDate", dateFormat(cartDetailsData.optString("orderCreatedDate")));
                                        timeInMillis = GettingMiliSeconds(cartDetailsData.optString("orderCreatedDate"));

                                        listDetail.put("chefName", cartDetailsData.optString("chefName"));
                                        listDetail.put("paymentMethod", fullOrderData.optString("paymentMethod"));
                                        listDetail.put("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                                        if (cartDetailsData.optString("menuDetails") != null && (cartDetailsData.optString("menuDetails").length() > 0) && (!(cartDetailsData.optString("menuDetails").equals("[]")))) {

                                            JSONArray menuDetailsArray1 = cartDetailsData.getJSONArray("menuDetails");
                                            for (int m = 0; m < menuDetailsArray1.length(); m++) {
                                                JSONObject menuDetailsData = menuDetailsArray1.getJSONObject(m);
                                                Allquantity += menuDetailsData.optInt("quantity");
                                            }

                                            ArrayList<String> img_source = new ArrayList<>();
                                            JSONArray jarray = new JSONArray();
                                            //JSONObject jsonObjectImag = new JSONObject();
                                            for (int c = 0; c < cartDetailsArray.length(); c++) {
                                                JSONObject cartDetailsData2 = cartDetailsArray.getJSONObject(c);
                                                JSONArray menuDetailsArray = cartDetailsData2.getJSONArray("menuDetails");

                                                for (int m = 0; m < menuDetailsArray.length(); m++) {
                                                    JSONObject menuDetailsData = menuDetailsArray.getJSONObject(m);
                                                    JSONArray dishImageArray = menuDetailsData.getJSONArray("dishImage");
                                                  //  Allquantity = Allquantity + menuDetailsData.optInt("quantity");


                                                    if (menuDetailsData.optString("dishName").equals(Globaluse.searchString)) {
                                                        invoiceidBaseOnchef.add(fullOrderData.optString("orderInvoiceNo"));
                                                    }

                                                    for (int d = 0; d < dishImageArray.length(); d++) {
                                                        // img_source.add(String.valueOf(dishImageArray.get(d)));
                                                        if (d == 0) {
                                                            img_source.add(String.valueOf(dishImageArray.get(d)));
                                                            //jarray.put(dishImageArray.get(d));
                                                            jarray.put(dishImageArray.get(d));

                                                        }


                                                    }
                                                }

                                            }

                                            listDetail.put("quantity", Allquantity);
                                            listDetail.put("disImage", jarray);

                                        }

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                    //comingSoon.add(cartDetailsData.optString("chefName"));
                                    comingSoon.add(String.valueOf(listDetail));

                                    if (cartDetailsData.optInt("consumerOrderStatus") >= 12 && cartDetailsData.optInt("consumerOrderStatus") <= 99) {
                                        // foodItem.setStatus("Delivered");
                                        closed_order = "1";
                                    } else if (cartDetailsData.optInt("consumerOrderStatus") == 100) {
                                        //foodItem.setStatus("Cancelled");
                                        cancelled_order = "1";
                                    } else if (cartDetailsData.optInt("consumerOrderStatus") >= 7 && cartDetailsData.optInt("consumerOrderStatus") <= 11) {
                                        //foodItem.setStatus("On the way");

                                        open_order = "1";

                                    } else if (cartDetailsData.optInt("consumerOrderStatus") >= 3 && cartDetailsData.optInt("consumerOrderStatus") <= 6) {
                                        //foodItem.setStatus("Preparing");
                                        open_order = "1";

                                    } else if (cartDetailsData.optInt("consumerOrderStatus") >= 0 && cartDetailsData.optInt("consumerOrderStatus") <= 2) {
                                        // foodItem.setStatus("Accepted");
                                        open_order = "1";
                                    }

//                                    if(fullOrderData.optString("orderInvoiceNo").equals("FHINV637456211176800922"))
//                                    {
//
//                                        if(cartDetailsData.optString("orderNo").equals("FH637456211310404022"))
//                                        {
//                                            open_order="1";
//                                        }
//
//                                        if(cartDetailsData.optString("orderNo").equals("FH637456211310568528"))
//                                        {
//                                            closed_order="1";
//                                        }
//                                        if(cartDetailsData.optString("orderNo").equals("FH637456211310579910"))
//                                        {
//                                            cancelled_order="1";
//                                        }
//                                    }


                                    Log.v("Result_chefName", cartDetailsData.optString("chefName"));
                                }


                                JSONObject headerDetail = new JSONObject();
                                try {
                                    headerDetail.put("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                                    headerDetail.put("open", open_order);
                                    headerDetail.put("closed", closed_order);
                                    headerDetail.put("cancelled", cancelled_order);
                                    headerDetail.put("orderCreatedDate_timeMillseconds", timeInMillis);

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                listDataHeader.add(String.valueOf(headerDetail));


                                listDataChild.put(listDataHeader.get(adddataTo), comingSoon);


                                adddataTo++;
                            }
                            }
                        }

                    }





                   // ArrayList<String> values=new ArrayList<String>();
                    HashSet<String> hashSet = new HashSet<String>();
                    hashSet.addAll(invoiceidBaseOnchef);
                    invoiceidBaseOnchef.clear();
                    invoiceidBaseOnchef.addAll(hashSet);


                    List<String>   listDataHeaderNew = new ArrayList<String>();
                    HashMap<String, List<String>> listDataChildNew = new HashMap<String, List<String>>();

                    for(int nd=0;nd<listDataHeader.size();nd++)
                    {
                        JSONObject jsonObject2 = new JSONObject(listDataHeader.get(nd));
                        if (sTitle.equalsIgnoreCase("open"))
                        {


                            if(jsonObject2.getString("open").equals("1"))
                            {
                                long comparedate= Long.parseLong(jsonObject2.getString("orderCreatedDate_timeMillseconds"));

                                if(!(Globaluse.dateStart==0)&&!(Globaluse.dateEnd==0))
                                {
                                    if((Globaluse.dateStart<=comparedate)&&(Globaluse.dateEnd>=comparedate)) {

                                        if(invoiceidBaseOnchef.size()>0)
                                        {
                                            for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                            {
                                                if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                {
                                                    listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                    listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                }

                                            }
                                        }else
                                        {
                                            listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                            listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                        }
                                    }

                                }else
                                {

                                    if(!(Globaluse.lastFifteen==0))
                                    {
                                        if(Globaluse.lastFifteen<=comparedate)
                                        {
                                            if(invoiceidBaseOnchef.size()>0)
                                            {
                                                for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                {
                                                    if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                    {
                                                        listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                        listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                    }

                                                }
                                            }else
                                            {
                                                listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                            }
                                        }

                                    }else
                                    {
                                        if(!(Globaluse.lastThirty==0))
                                        {
                                            if(Globaluse.lastThirty<=comparedate)
                                            {
                                                if(invoiceidBaseOnchef.size()>0)
                                                {
                                                    for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                    {
                                                        if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                        {
                                                            listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                            listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                        }

                                                    }
                                                }else
                                                {
                                                    listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                    listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                }
                                            }

                                        }else
                                        {
                                            if(invoiceidBaseOnchef.size()>0)
                                            {
                                                for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                {
                                                    if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                    {
                                                        listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                        listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                    }

                                                }
                                            }else
                                            {
                                                listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                            }


                                        }
                                    }

                                }

                            }
                        }
                        if (sTitle.equalsIgnoreCase("closed"))
                        {
                            if(jsonObject2.getString("closed").equals("1"))
                            {
                                long comparedate= Long.parseLong(jsonObject2.getString("orderCreatedDate_timeMillseconds"));

                                if(!(Globaluse.dateStart==0)&&!(Globaluse.dateEnd==0))
                                {
                                    if((Globaluse.dateStart<=comparedate)&&(Globaluse.dateEnd>=comparedate)) {

                                        if(invoiceidBaseOnchef.size()>0)
                                        {
                                            for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                            {
                                                if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                {
                                                    listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                    listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                }

                                            }
                                        }else
                                        {
                                            listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                            listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                        }
                                    }

                                }else
                                {

                                    if(!(Globaluse.lastFifteen==0))
                                    {
                                        if(Globaluse.lastFifteen<=comparedate)
                                        {
                                            if(invoiceidBaseOnchef.size()>0)
                                            {
                                                for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                {
                                                    if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                    {
                                                        listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                        listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                    }

                                                }
                                            }else
                                            {
                                                listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                            }
                                        }

                                    }else
                                    {
                                        if(!(Globaluse.lastThirty==0))
                                        {
                                            if(Globaluse.lastThirty<=comparedate)
                                            {
                                                if(invoiceidBaseOnchef.size()>0)
                                                {
                                                    for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                    {
                                                        if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                        {
                                                            listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                            listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                        }

                                                    }
                                                }else
                                                {
                                                    listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                    listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                }
                                            }

                                        }else
                                        {


                                            if(invoiceidBaseOnchef.size()>0)
                                            {
                                                for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                {
                                                    if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                    {
                                                        listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                        listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                    }

                                                }
                                            }else
                                            {
                                                listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                            }


                                        }
                                    }

                                }

                            }
                        }
                        if (sTitle.equalsIgnoreCase("cancelled"))
                        {
                            if(jsonObject2.getString("cancelled").equals("1"))
                            {
                                long comparedate= Long.parseLong(jsonObject2.getString("orderCreatedDate_timeMillseconds"));

                                if(!(Globaluse.dateStart==0)&&!(Globaluse.dateEnd==0))
                                {
                                    if((Globaluse.dateStart<=comparedate)&&(Globaluse.dateEnd>=comparedate)) {

                                        if(invoiceidBaseOnchef.size()>0)
                                        {
                                            for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                            {
                                                if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                {
                                                    listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                    listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                }

                                            }
                                        }else
                                        {
                                            listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                            listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                        }
                                    }

                                }else
                                {

                                    if(!(Globaluse.lastFifteen==0))
                                    {
                                        if(Globaluse.lastFifteen<=comparedate)
                                        {
                                            if(invoiceidBaseOnchef.size()>0)
                                            {
                                                for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                {
                                                    if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                    {
                                                        listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                        listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                    }

                                                }
                                            }else
                                            {
                                                listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                            }
                                        }

                                    }else
                                    {
                                        if(!(Globaluse.lastThirty==0))
                                        {
                                            if(Globaluse.lastThirty<=comparedate)
                                            {
                                                if(invoiceidBaseOnchef.size()>0)
                                                {
                                                    for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                    {
                                                        if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                        {
                                                            listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                            listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                        }

                                                    }
                                                }else
                                                {
                                                    listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                    listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                }
                                            }

                                        }else
                                        {
                                            if(invoiceidBaseOnchef.size()>0)
                                            {
                                                for ( int i=0;i<invoiceidBaseOnchef.size();i++)
                                                {
                                                    if(jsonObject2.getString("orderInvoiceNo").equals(invoiceidBaseOnchef.get(i)))
                                                    {
                                                        listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                        listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                                    }

                                                }
                                            }else
                                            {
                                                listDataHeaderNew.add(String.valueOf(listDataHeader.get(nd)));
                                                listDataChildNew.put(listDataHeader.get(nd), listDataChild.get(listDataHeader.get(nd)));
                                            }
                                        }
                                    }

                                }


                            }
                        }
                    }






                    listAdapter = new ExpandableListAdapter(getActivity(), listDataHeaderNew, listDataChildNew);

                    expListView.setAdapter(listAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }




                ;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ConsumerMainActivity.logout();

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
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, getActivity());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");
    }


    private long GettingMiliSeconds(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        long formattedDate=0;
        try {
            serverDate = df.parse(date);
//            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm a");
//
//            outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = serverDate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }



    private String dateFormat(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        String formattedDate = null;
        try {
            serverDate = df.parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm");

            outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = outputFormat.format(serverDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

}