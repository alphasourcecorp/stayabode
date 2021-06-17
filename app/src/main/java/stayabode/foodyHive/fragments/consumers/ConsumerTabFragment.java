package stayabode.foodyHive.fragments.consumers;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.adapters.consumers.InfoListsAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.CancelledOrdersHeader;
import stayabode.foodyHive.models.ClosedOrderHeader;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.models.OrdersHeader;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerTabFragment extends Fragment {

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
    private int size = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consumer_tab, container, false);
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
        progressBar.setVisibility(View.VISIBLE);
        itemsLoader.setVisibility(View.GONE);
        objectList = new ArrayList<>();
        cancelledOrdersList = new ArrayList<>();
        ordersList = new ArrayList<>();
        closedOrdersList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);


        recyclerView.setAdapter(new InfoListsAdapter(getContext(), objectList, recyclerView, poppinsSemiBold, poppinsLight, poppinsMedium, poppinsBold, robotoBold, robotoRegular));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sTitle.equalsIgnoreCase("open")) {
            try {
                getOrders(openOrdersId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //perform pagination
            nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        openIndex++;
                        try {
                            getOrders(openOrdersId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        } else if (sTitle.equalsIgnoreCase("closed")) {
            try {
                getOrders(closedOrdersId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //perform pagination
            nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        closedIndex++;
                        try {
                            getOrders(closedOrdersId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        } else if (sTitle.equalsIgnoreCase("cancelled")) {

            try {
                getOrders(completedOrdersId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //perform pagination
            nestedScrollViewAll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                            completedIndex++;
                            try {
                                getOrders(completedOrdersId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

        }
        recyclerView.getAdapter().

    notifyDataSetChanged();

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
}