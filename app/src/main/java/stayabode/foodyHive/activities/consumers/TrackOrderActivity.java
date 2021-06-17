package stayabode.foodyHive.activities.consumers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.OrderFoodsItemsListsAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackOrderActivity extends AppCompatActivity {


    StateProgressBar stateProgressBar;
    String[] descriptionData = {"Received", "Preparing", "On the way", "Delivered"};
    RecyclerView recyclerView;
    TextView orderReceivedVertical;
    TextView foodPrepareddVertical;
    TextView foodonwayVertical;
    TextView fooddeliveredVertical;
    TextView orderID;
    TextView deliveredTimeField;
    TextView acceptedTimefield;
    TextView preparingTimefield;
    Button exploreFoodButton;

    TextView statusTwo;
    TextView statusThree;
    TextView statusFour;
    TextView statusOne;
    TextView etaTime;
    TextView etaTimeinMins;


    View viewverticallinethree;
    View viewverticallinetwo;
    View viewverticallineOne;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Order Status");

        stateProgressBar=findViewById(R.id.stateProgress);
        stateProgressBar.setStateDescriptionData(descriptionData);
        recyclerView = findViewById(R.id.recyclerView);
        orderReceivedVertical = findViewById(R.id.orderReceivedVertical);
        statusTwo = findViewById(R.id.statusTwo);
        statusFour = findViewById(R.id.statusFour);
        statusThree = findViewById(R.id.statusThree);
        statusOne = findViewById(R.id.statusOne);
        etaTime = findViewById(R.id.etaTime);
        etaTimeinMins = findViewById(R.id.etaTimeinMins);
        foodPrepareddVertical = findViewById(R.id.foodPrepareddVertical);
        foodonwayVertical = findViewById(R.id.foodonwayVertical);
        fooddeliveredVertical = findViewById(R.id.fooddeliveredVertical);
        exploreFoodButton=findViewById(R.id.exploreFoodButton);
        deliveredTimeField=findViewById(R.id.deliveredTimeField);
        acceptedTimefield=findViewById(R.id.acceptedTimefield);
        preparingTimefield=findViewById(R.id.preparingTimefield);
        viewverticallinethree=findViewById(R.id.viewverticallinethree);
        viewverticallinetwo=findViewById(R.id.viewverticallinetwo);
        viewverticallineOne=findViewById(R.id.viewverticallineOne);

        orderID = findViewById(R.id.orderID);
        orderID.setText("Order ID : "+getOrderNoWithDashes(getIntent().getStringExtra("OrderID"),"-",4));
        deliveredTimeField.setText(getIntent().getStringExtra("dateOrdered"));
        acceptedTimefield.setText(getIntent().getStringExtra("dateOrdered"));
        preparingTimefield.setText(getIntent().getStringExtra("dateOrdered"));
        etaTime.setText(getIntent().getStringExtra("dateOrdered"));
        stateProgressBar.setStateDescriptionTypeface("fonts/Poppins-Medium.ttf");
        stateProgressBar.setStateNumberTypeface("fonts/Poppins-Medium.ttf");

        exploreFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("From").equals("Checkout"))
                {
                    Intent intent = new Intent(TrackOrderActivity.this, ConsumerMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                {
//                    Intent intent = new Intent(TrackOrderActivity.this, ConsumerMainActivity.class);
//                    //startActivityForResult(intent, 200);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
                    finish();
                }
            }
        });

        try {
            getOrdersStatus();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     get order number with dashes for every 4 digits..
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
    // Back to home screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                if(getIntent().getStringExtra("From").equals("Checkout"))
                {
                    Intent intent = new Intent(TrackOrderActivity.this, ConsumerMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    finish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Back to home screen
    @Override
    public void onBackPressed() {
        if(getIntent().getStringExtra("From").equals("Checkout"))
        {
            Intent intent = new Intent(TrackOrderActivity.this, ConsumerMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else
        {
          finish();
        }

    }

    /**
     To Get Order in Each Status Make an GET API Call(GET)
     **/
    public void getOrdersStatus() throws JSONException {
        String url = APIBaseURL.getConsumersOrdersList;

        JSONObject inputObject = new JSONObject();
        inputObject.put("consumerEmailId", SaveSharedPreference.getLoggedInUserEmail(TrackOrderActivity.this));
        inputObject.put("consumerOrderStatus",12);
        JSONObject pagaObject = new JSONObject();
        pagaObject.put("size",20);
        pagaObject.put("index",0);
        inputObject.put("page",pagaObject);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray dataArray = response.getJSONArray("data");

                    JSONObject dataSingleArray = dataArray.getJSONObject(0);
                    for (int i=0;i < dataArray.length();i++)
                    {
                        JSONObject dataObject = dataArray.getJSONObject(i);


                        if(dataObject.optString("orderId").equals(getIntent().getStringExtra("OrderID")))
                        {

                            if(dataObject.optInt("orderDetailStatus")>=0 && dataObject.optInt("orderDetailStatus")<=2)
                            {
                                Toast.makeText(TrackOrderActivity.this, "Order Accepted", Toast.LENGTH_SHORT).show();
                                statusOne.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                orderReceivedVertical.setTextColor(Color.parseColor("#F7B917"));
                                statusOne.setTextColor(Color.parseColor("#F7B917"));
                                statusTwo.setTextColor(Color.parseColor("#112132"));
                                foodonwayVertical.setTextColor(Color.parseColor("#112132"));
                                statusThree.setTextColor(Color.parseColor("#112132"));
                                statusFour.setTextColor(Color.parseColor("#112132"));
                                foodPrepareddVertical.setTextColor(Color.parseColor("#112132"));
                                fooddeliveredVertical.setTextColor(Color.parseColor("#112132"));
                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                                stateProgressBar.setCurrentStateDescriptionColor(Color.parseColor("#F7B917"));
                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
                              //  viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
                            }
                            else if(dataObject.optInt("orderDetailStatus")>=3 && dataObject.optInt("orderDetailStatus")<=6)
                            {
                                Toast.makeText(TrackOrderActivity.this, "Food is being prepared", Toast.LENGTH_SHORT).show();
                                foodPrepareddVertical.setTextColor(Color.parseColor("#F7B917"));
                                statusTwo.setTextColor(Color.parseColor("#F7B917"));
                                foodonwayVertical.setTextColor(Color.parseColor("#112132"));
                                statusOne.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                statusTwo.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                statusThree.setTextColor(Color.parseColor("#112132"));
                                statusFour.setTextColor(Color.parseColor("#112132"));
                                statusOne.setTextColor(Color.parseColor("#F7B917"));
                                orderReceivedVertical.setTextColor(Color.parseColor("#F7B917"));
                                fooddeliveredVertical.setTextColor(Color.parseColor("#112132"));
                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                                stateProgressBar.setCurrentStateDescriptionColor(Color.parseColor("#F7B917"));
                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
                                viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
                            }
                            else if(dataObject.optInt("orderDetailStatus")>=7 && dataObject.optInt("orderDetailStatus")<=11)
                            {
                                Toast.makeText(TrackOrderActivity.this, "Food is on the way", Toast.LENGTH_SHORT).show();
                                foodonwayVertical.setTextColor(Color.parseColor("#F7B917"));
                                statusThree.setTextColor(Color.parseColor("#F7B917"));
                                statusThree.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                foodPrepareddVertical.setTextColor(Color.parseColor("#F7B917"));
                                statusTwo.setTextColor(Color.parseColor("#F7B917"));
                                statusTwo.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                statusFour.setTextColor(Color.parseColor("#112132"));
                                statusOne.setTextColor(Color.parseColor("#F7B917"));
                                statusOne.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                orderReceivedVertical.setTextColor(Color.parseColor("#F7B917"));
                                fooddeliveredVertical.setTextColor(Color.parseColor("#112132"));
                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                                stateProgressBar.setCurrentStateDescriptionColor(Color.parseColor("#F7B917"));
                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
                                viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
                               // viewverticallinethree.setBackgroundColor(Color.parseColor("#F7B917"));
                                if(dataObject.optInt("orderTotalTime") == 0)
                                {

                                }
                                else
                                {
                                    //String text = "<font color=#000000>"+getIntent().getStringExtra("dateOrdered")+"</font> <font color=#23C706>"+"\nETA : "+dataObject.optString("orderTotalTime") + " mins"+"</font>";
                                  //  etaTime.setText(Html.fromHtml(text));
                                   etaTimeinMins.setText("ETA : "+dataObject.optString("orderTotalTime") + " mins");
                                   //etaTime.setText("ETA : "+dataObject.optString("orderTotalTime") + " mins");
                                }
                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
                                viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
                                viewverticallinethree.setBackgroundColor(Color.parseColor("#F7B917"));
                            }
                            else if(dataObject.optInt("orderDetailStatus")>=12)
                            {
                                statusOne.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                statusTwo.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                statusThree.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                statusFour.setBackground(getDrawable(R.drawable.track_order_rounded_border_yellow));
                                fooddeliveredVertical.setTextColor(Color.parseColor("#F7B917"));
                                statusFour.setTextColor(Color.parseColor("#F7B917"));
                                foodPrepareddVertical.setTextColor(Color.parseColor("#F7B917"));
                                statusTwo.setTextColor(Color.parseColor("#F7B917"));
                                statusThree.setTextColor(Color.parseColor("#F7B917"));
                                statusOne.setTextColor(Color.parseColor("#F7B917"));
                                orderReceivedVertical.setTextColor(Color.parseColor("#F7B917"));
                                foodonwayVertical.setTextColor(Color.parseColor("#F7B917"));
                                viewverticallineOne.setBackgroundColor(Color.parseColor("#F7B917"));
                                viewverticallinetwo.setBackgroundColor(Color.parseColor("#F7B917"));
                                viewverticallinethree.setBackgroundColor(Color.parseColor("#F7B917"));

                                Toast.makeText(TrackOrderActivity.this, "Food is Delivered", Toast.LENGTH_SHORT).show();
                                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                                stateProgressBar.setAllStatesCompleted(true);
                            }

                            JSONArray orderMenuDetailsArray = new JSONArray();
                            if(dataObject.has("orderMenuDetails"))
                            {
                                orderMenuDetailsArray = dataObject.getJSONArray("orderMenuDetails");
                            }
                            JSONObject orderMenuDetailsObjectSingle = orderMenuDetailsArray.getJSONObject(0);

                            List<FoodItem> foodItemList = new ArrayList<>();
                            for (int j=0;j < orderMenuDetailsArray.length();j++) {
                                JSONObject orderMenuDetailsObject = orderMenuDetailsArray.getJSONObject(j);
                                FoodItem foodItem = new FoodItem();
                                if(orderMenuDetailsObject.getJSONArray("dishImagePath").length()!=0)
                                {
                                    foodItem.setFoodImage(orderMenuDetailsObject.getJSONArray("dishImagePath").get(0).toString());
                                }
                                foodItem.setFoodName(orderMenuDetailsObject.optString("menuName"));
                                foodItem.setPrice(String.format("%.2f", dataObject.optDouble("price")));
                                foodItem.setCartAddedQuantity(orderMenuDetailsObject.optInt("quantity"));
                                foodItem.setTime(dataObject.optString("orderDate"));
                                foodItem.setChefName(dataObject.optString("chefName"));
                                foodItemList.add(foodItem);

                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(TrackOrderActivity.this));
                            recyclerView.setAdapter(new OrderFoodsItemsListsAdapter(TrackOrderActivity.this,foodItemList,"track",null,null,null));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = TrackOrderActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(TrackOrderActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TrackOrderActivity.this);

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
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(TrackOrderActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(TrackOrderActivity.this));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"get_taq");
    }
}
