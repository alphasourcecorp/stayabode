package stayabode.foodyHive.corporatemenurange;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.adapters.consumers.ParentOrderDetailAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.ChildItemOrderDetail;
import stayabode.foodyHive.models.ItemAddOns;
import stayabode.foodyHive.models.ParentItemOrderDetail;
import stayabode.foodyHive.models.RequestBtoBItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.FulllistItemdisplay;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.SqlDateTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class CorporateOrderDetailUser extends AppCompatActivity {

    Typeface poppinsMedium;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface RobotoRegular;
    Typeface RobotoBold;
    Typeface poppinsLight;
    RecyclerView topRecyclerView;
    RecyclerView bottomRecyclerView;
    Boolean scrolled = false;

    CardView searchBar;

    public Boolean enableScroll = true;

    public static int selectedposition = -1;
    double sub_totalvalue1=0;



    private static final String ROOT_URL = APIBaseURL.LoginUserInfoPOSTURL;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String filePath;
    EditText email;
    EditText name;
    EditText phoneNumber;
    ImageView userProfileImage;
    ImageView userProfileImageSlected;
    RecyclerView preferencesCheckRecyclerView;
    RadioGroup radioGroup;
    RadioButton male;
    RadioButton female;
    Button confirmBtn;
    String fileImageURLFromServer = "";
    ImageView cartIcon;
    public static TextView cartTotalCountText;
    String strMyImagePath = null;


    public static List<ItemAddOns> selectedFoodPreferencesList = new ArrayList<>();


    List<ItemAddOns> categoryList = new ArrayList<>();
    private static final String TAG = "CorporateOrderDetail";

    Object getValues;


    List<ItemAddOns> selectedPreviousesLists = new ArrayList<>();

    String selectedGenderTypes = "";
    File fileuploaded = null;
    File filetoUploadinServer = null;
    File mFolder = null;
    File output = null;
    private Bitmap bitmap;
    String send_location="";
    String send_lat="";
    String send_lng="";
    String send_dob="";
    String send_addressLine1="";
    String send_addressLine2="";
    String send_pinCode="";
    String send_state="";
    String send_country="";
    JSONArray cloneJSONArray=new JSONArray();
    JSONArray emailJSONArray=new JSONArray();
    String send_profilePic="";
    String send_roleId="";
    String send_roleName="";
    String send_createdDate="";
    String send_status="";
    JSONObject cloneJSONObject=new JSONObject();
    String orderInvoiceNo="";

    ParentOrderDetailAdapter parentItemAdapter ;
    RecyclerView ParentRecyclerViewItem;
    LinearLayoutManager layoutManager;
    TextView invoiceId,orderdate,totalQuantityId,paymentId,dishPriceId,totalTaxId,deliveryChargeId,packagingChargeId,TotalId;
    String ordercreateDate="";
    int totalQuantity=0;
    NonScrollListView navLV;
    Button suubmit_id;
    String requestId="",companyId="";
    TextView user_id,company_name,company_email,mobile_id,notes_id;
    EditText addr_edittext;
    Button accept_id,reject_id;
    String sendstatus="";
    TextView location_id;

    Toolbar toolbar;
    ImageView redit_id;

    JSONArray schedullingInfo;

    String subscriptionDate="",isActive="",isDeleted="",partitionKey="",rowKey="",timestamp="",eTag="";
    String addressLine1="",addressLine2="",city="",state="",postalCode="",country="",notes="";
    CustomAdapter22 adapter2;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.corporateorderdetailuser);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        navLV = findViewById(R.id.navLV);
        redit_id = findViewById(R.id.redit_id);
       // navLV.setSmoothScrollbarEnabled(true);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
//        if (Build.VERSION.SDK_INT > 22) {
//            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
//                    /*zzzzzzREQUEST_PERMISSIONS},*/ 1);
//        }

        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        RobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        RobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

//        cartIcon = findViewById(R.id.cartIcon);
//        cartTotalCountText = findViewById(R.id.cartTotalCountText);
//        cartTotalCountText.setVisibility(View.GONE);
//        cartIcon.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             //orderInvoiceNo = extras.getString("orderInvoiceNo");
            requestId = extras.getString("requestId");
            companyId = extras.getString("companyId");
            //The key argument here must match that used in the other activity
        }


         ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);
        // Initialise the Linear layout manager
         layoutManager = new LinearLayoutManager( CorporateOrderDetailUser.this);
        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()



        // Set the layout manager
        // and adapter for items
        // of the parent recyclerview


        location_id = (TextView) findViewById(R.id.location_id);

        invoiceId = (TextView) findViewById(R.id.invoiceId);
        orderdate = (TextView) findViewById(R.id.orderdate);
        totalQuantityId = (TextView) findViewById(R.id.totalQuantityId);
        paymentId = (TextView) findViewById(R.id.paymentId);
        dishPriceId = (TextView) findViewById(R.id.dishPriceId);
        totalTaxId = (TextView) findViewById(R.id.totalTaxId);
        deliveryChargeId = (TextView) findViewById(R.id.deliveryChargeId);
        packagingChargeId = (TextView) findViewById(R.id.packagingChargeId);
        TotalId = (TextView) findViewById(R.id.TotalId);

        user_id = (TextView) findViewById(R.id.user_id);
        company_name = (TextView) findViewById(R.id.company_name);
        company_email = (TextView) findViewById(R.id.company_email);
        mobile_id = (TextView) findViewById(R.id.mobile_id);
        notes_id = (TextView) findViewById(R.id.notes_id);

        accept_id = (Button) findViewById(R.id.accept_id);
        reject_id = (Button) findViewById(R.id.reject_id);


        accept_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendstatus="Approved";
                try {
                    updateRequeststatus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        reject_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendstatus="Rejected";
                try {
                    updateRequeststatus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        //  getUserOrders();
//        CustomAdapter2 adapter = new CustomAdapter2(CorporateOrderDetail.this,  Globaluse.schedule_data_array);
//        navLV.setAdapter(adapter);
//        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);

//        suubmit_id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(CorporateOrderDetail.this, CorporateMenuWithNavigatioin.class);
//                startActivity(intent);
//            }
//        });

        GetrequestDetail();



        redit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CorporateOrderDetailUser.this,CorporateAfterRequestScheduleApi.class);
                intent.putExtra("schedullingInfo", String.valueOf(schedullingInfo));
                intent.putExtra("requestId", requestId);
                intent.putExtra("companyId", companyId);

                intent.putExtra("addressLine1", addressLine1);
                intent.putExtra("addressLine2", addressLine2);
                intent.putExtra("city", city);
                intent.putExtra("state", state);
                intent.putExtra("postalCode", postalCode);
                intent.putExtra("country", country);


                intent.putExtra("subscriptionDate", subscriptionDate);
                intent.putExtra("isActive", isActive);
                intent.putExtra("isDeleted", isDeleted);
                intent.putExtra("partitionKey", partitionKey);
                intent.putExtra("rowKey", rowKey);
                intent.putExtra("timestamp", timestamp);
                intent.putExtra("eTag", eTag);
                intent.putExtra("emailFlag", "N");
                intent.putExtra("notes", notes);



                startActivity(intent);

            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void getUserOrders() {
        String url = APIBaseURL.getConsumersOrdersfullList + SaveSharedPreference.getLoggedInUserEmail(getApplicationContext());
        //String url = APIBaseURL.getConsumersOrdersfullList + "satcatbat@gmail.com";
        //String url = APIBaseURL.getConsumersOrdersfullList + "jd.ramkumar@gmail.com";



        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                List <String> stringList = new ArrayList<String>();
                try {

                    JSONObject  jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");




                    int adddataTo=0;
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject fullOrderData = dataArray.getJSONObject(i);

                        if(fullOrderData.optString("cartDetails")!=null && (fullOrderData.optString("cartDetails").length()>0)&& (!(fullOrderData.optString("cartDetails").equals("[]")))){
                            //Log.v("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                            // Log.v("Number of order", " "+fullOrderData.optString("orderInvoiceNo"));



                            if(fullOrderData.optString("orderInvoiceNo") != null && !fullOrderData.optString("orderInvoiceNo") .isEmpty()&& !fullOrderData.optString("orderInvoiceNo").equals("null"))
                            {
                                // Log.v("Result_orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                                // Log.v("Result_paymentMethod", fullOrderData.optString("paymentMethod"));

                                if(fullOrderData.optString("paymentMethod") != null && !fullOrderData.optString("paymentMethod") .isEmpty()&& !fullOrderData.optString("paymentMethod").equals("null"))
                                {


                                    if (orderInvoiceNo.equals(fullOrderData.optString("orderInvoiceNo"))) {
                                        JSONArray cartDetailsArray = fullOrderData.getJSONArray("cartDetails");
                                        List<String> comingSoon = new ArrayList<String>();
                                        JSONObject imagelist = new JSONObject();
                                        String open_order = "0";
                                        String closed_order = "0";
                                        String cancelled_order = "0";


                                        for (int j = 0; j < cartDetailsArray.length(); j++) {
                                            JSONObject cartDetailsData = cartDetailsArray.getJSONObject(j);
                                            JSONObject listDetail = new JSONObject();
                                            stringList.add(cartDetailsData.optString("chefName"));
                                            try {
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
                                                listDetail.put("orderCreatedDate", cartDetailsData.optString("orderCreatedDate"));
                                                //Allquantity+cartDetailsData.optInt("quantity");
                                                listDetail.put("chefName", cartDetailsData.optString("chefName"));
                                                listDetail.put("paymentMethod", fullOrderData.optString("paymentMethod"));
                                                listDetail.put("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                                                if (cartDetailsData.optString("menuDetails") != null && (cartDetailsData.optString("menuDetails").length() > 0) && (!(cartDetailsData.optString("menuDetails").equals("[]")))) {
                                                    ArrayList<String> img_source = new ArrayList<>();
                                                    JSONArray jarray = new JSONArray();
                                                    //JSONObject jsonObjectImag = new JSONObject();

                                                    JSONArray menuDetailsArray1 = cartDetailsData.getJSONArray("menuDetails");
                                                    for (int m = 0; m < menuDetailsArray1.length(); m++) {
                                                        JSONObject menuDetailsData = menuDetailsArray1.getJSONObject(m);
                                                        totalQuantity += menuDetailsData.optInt("quantity");
                                                    }


                                                    List<ParentItemOrderDetail> itemList = new ArrayList<>();

                                                    for (int c = 0; c < cartDetailsArray.length(); c++) {
                                                        JSONObject cartDetailsData2 = cartDetailsArray.getJSONObject(c);

                                                        String consumerOrderStatus_str2 = "";
                                                        if (cartDetailsData2.optInt("consumerOrderStatus") >= 0 && cartDetailsData2.optInt("consumerOrderStatus") <= 2) {
                                                            // foodItem.setStatus("Accepted");
                                                            //open_order="1";
                                                            consumerOrderStatus_str2 = "Accepted";
                                                        } else if (cartDetailsData2.optInt("consumerOrderStatus") >= 3 && cartDetailsData2.optInt("consumerOrderStatus") <= 6) {
                                                            //foodItem.setStatus("Preparing");
                                                            //open_order="1";
                                                            consumerOrderStatus_str2 = "Preparing";

                                                        } else if (cartDetailsData2.optInt("consumerOrderStatus") >= 7 && cartDetailsData2.optInt("consumerOrderStatus") <= 11) {
                                                            //foodItem.setStatus("On the way");
                                                            //open_order="1";
                                                            consumerOrderStatus_str2 = "Preparing";
                                                        } else if (cartDetailsData2.optInt("consumerOrderStatus") == 100) {
                                                            //foodItem.setStatus("Cancelled");
                                                            //cancelled_order="1";
                                                            consumerOrderStatus_str2 = "Cancelled";
                                                        } else if (cartDetailsData2.optInt("consumerOrderStatus") >= 12 && cartDetailsData2.optInt("consumerOrderStatus") <= 99) {
                                                            // foodItem.setStatus("Delivered");
                                                            //closed_order="1";
                                                            consumerOrderStatus_str2 = "Delivered";
                                                        }


                                                        JSONArray menuDetailsArray = cartDetailsData2.getJSONArray("menuDetails");
                                                        int Allquantity = 0;

                                                        List<ChildItemOrderDetail> ChildItemList = new ArrayList<>();

                                                        for (int m = 0; m < menuDetailsArray.length(); m++) {
                                                            JSONObject menuDetailsData = menuDetailsArray.getJSONObject(m);
                                                            JSONArray dishImageArray = menuDetailsData.getJSONArray("dishImage");
                                                            Allquantity = Allquantity + menuDetailsData.optInt("quantity");


//                                                    if(j==0)
//                                                    {
//                                                        totalQuantity+=menuDetailsData.optInt("quantity");
//                                                    }


                                                            stringList.add(menuDetailsData.optString("dishName"));
                                                            String dishimg = "";
                                                            for (int d = 0; d < dishImageArray.length(); d++) {
                                                                img_source.add(String.valueOf(dishImageArray.get(d)));
                                                                // jarray.put(dishImageArray.get(d));
                                                                jarray.put(dishImageArray.get(0));
                                                                dishimg = String.valueOf(dishImageArray.get(0));
                                                            }

                                                            ChildItemList.add(new ChildItemOrderDetail(dishimg, menuDetailsData.optString("dishName"), menuDetailsData.optString("quantity")));
                                                        }

                                                        JSONObject footerDeatail = cartDetailsData2.getJSONObject("footer");
                                                        //footerDeatail.getString("total");
                                                        double number1 = Double.parseDouble(footerDeatail.getString("total"));
                                                        double number2 = (int) (Math.round(number1 * 100)) / 100.0;

                                                        ParentItemOrderDetail item = new ParentItemOrderDetail(cartDetailsData2.optString("chefName"), cartDetailsData2.optString("orderNo"), consumerOrderStatus_str2, "" + number2, ChildItemList);
                                                        itemList.add(item);


                                                        listDetail.put("quantity", Allquantity);
                                                    }
                                                    listDetail.put("disImage", jarray);
                                                    parentItemAdapter = new ParentOrderDetailAdapter(itemList);
                                                }

                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                            //comingSoon.add(cartDetailsData.optString("chefName"));
                                            comingSoon.add(String.valueOf(listDetail));
                                            if (cartDetailsData.optInt("consumerOrderStatus") >= 0 && cartDetailsData.optInt("consumerOrderStatus") <= 2) {
                                                // foodItem.setStatus("Accepted");
                                                open_order = "1";
                                            } else if (cartDetailsData.optInt("consumerOrderStatus") >= 3 && cartDetailsData.optInt("consumerOrderStatus") <= 6) {
                                                //foodItem.setStatus("Preparing");
                                                open_order = "1";

                                            } else if (cartDetailsData.optInt("consumerOrderStatus") >= 7 && cartDetailsData.optInt("consumerOrderStatus") <= 11) {
                                                //foodItem.setStatus("On the way");

                                                open_order = "1";

                                            } else if (cartDetailsData.optInt("consumerOrderStatus") == 100) {
                                                //foodItem.setStatus("Cancelled");
                                                cancelled_order = "1";
                                            } else if (cartDetailsData.optInt("consumerOrderStatus") >= 12 && cartDetailsData.optInt("consumerOrderStatus") <= 99) {
                                                // foodItem.setStatus("Delivered");
                                                closed_order = "1";
                                            }

                                            Log.v("Result_chefName", cartDetailsData.optString("chefName"));


                                            ordercreateDate = dateFormat(cartDetailsData.optString("orderCreatedDate"));
                                        }


                                        JSONObject headerDetail = new JSONObject();
                                        try {
                                            headerDetail.put("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                                            headerDetail.put("open", open_order);
                                            headerDetail.put("closed", closed_order);
                                            headerDetail.put("cancelled", cancelled_order);

                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        adddataTo++;


                                        JSONObject orderReportSummary = fullOrderData.getJSONObject("orderReportSummary");
                                        double orderSubTotal_number = Double.parseDouble(orderReportSummary.getString("orderSubTotal"));
                                        double orderSubTotal_number2 = (int) (Math.round(orderSubTotal_number * 100)) / 100.0;

                                        double totalTax_number = Double.parseDouble(orderReportSummary.getString("totalTax"));
                                        double totalTax_number2 = (int) (Math.round(totalTax_number * 100)) / 100.0;

                                        double totalDeliveryCharges_number = Double.parseDouble(orderReportSummary.getString("totalDeliveryCharges"));
                                        double totalDeliveryCharges_number2 = (int) (Math.round(totalDeliveryCharges_number * 100)) / 100.0;

                                        double totalPackingCharges_number = Double.parseDouble(orderReportSummary.getString("totalPackingCharges"));
                                        double totalPackingCharges_number2 = (int) (Math.round(totalPackingCharges_number * 100)) / 100.0;

                                        double grandTotal_number = Double.parseDouble(orderReportSummary.getString("grandTotal"));
                                        double grandTotal_number2 = (int) (Math.round(grandTotal_number * 100)) / 100.0;

                                        invoiceId.setText(orderInvoiceNo);
                                        orderdate.setText(ordercreateDate);
                                        totalQuantityId.setText("" + totalQuantity);
                                        paymentId.setText(fullOrderData.optString("paymentMethod"));


//                                        dishPriceId.setText("" + orderSubTotal_number2);
//                                        totalTaxId.setText("" + totalTax_number2);
//                                        deliveryChargeId.setText("" + totalDeliveryCharges_number2);
//                                        packagingChargeId.setText("" + totalPackingCharges_number2);
//                                        TotalId.setText("" + grandTotal_number2);

                                    }


                                    // TextView invoiceId,orderdate,totalQuantityId,paymentId,dishPriceId,totalTaxId,deliveryChargeId,packagingChargeId,TotalId;


                                }

                            }
                        }


                        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
                        ParentRecyclerViewItem.setLayoutManager(layoutManager);

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
                    ViewGroup viewGroup = CorporateOrderDetailUser.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    AlertDialog alertDialog = builder.create();


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
                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet... Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, getApplicationContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");
    }





    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
//    private List<ChildItemOrderDetail> ChildItemList()
//    {
//        List<ChildItemOrderDetail> ChildItemList = new ArrayList<>();
//
//        ChildItemList.add(new ChildItem("Card 1"));
//        ChildItemList.add(new ChildItem("Card 2"));
//        ChildItemList.add(new ChildItem("Card 3"));
//        ChildItemList.add(new ChildItem("Card 4"));
//
//        return ChildItemList;
//    }
//
//
//
//    private List<ParentItemOrderDetail> ParentItemList()
//    {
//        List<ParentItemOrderDetail> itemList = new ArrayList<>();
//
//        ParentItem item = new ParentItem("Title 1", ChildItemList());
//        itemList.add(item);
//        ParentItem item1 = new ParentItem("Title 2", ChildItemList());
//        itemList.add(item1);
//        ParentItem item2 = new ParentItem("Title 3", ChildItemList());
//        itemList.add(item2);
//        ParentItem item3 = new ParentItem("Title 4", ChildItemList());
//        itemList.add(item3);
//
//
//        return itemList;
//    }


//    private String dateFormat(String date) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date serverDate = null;
//        String formattedDate = null;
//        try {
//            serverDate = df.parse(date);
//            //SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm");
//            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy");
//
//            outputFormat.setTimeZone(TimeZone.getDefault());
//
//            formattedDate = outputFormat.format(serverDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return formattedDate;
//    }

    private String dateFormat(String date) {
        Log.i(TAG, "serverdate " + date);
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        String formattedDate = null;
        try {
            serverDate = df.parse(date);
            //SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            //outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = outputFormat.format(serverDate);
            Log.i(TAG, "converdate" + formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;



    }




//    class CustomAdapter2 extends BaseAdapter {
//
//        Context context;
//        ArrayList<ArrayList<HashMap<String, String>>> items;
//
//        public CustomAdapter2(Context context, ArrayList<ArrayList<HashMap<String, String>>> items) {
//            this.context = context;
//            this.items = items;
//        }
//
//
//        @Override
//        public int getCount() {
//            return items.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return items.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            View v = LayoutInflater.from(context).inflate(R.layout.corporate_schedule_item_new, null);
//
//            TextView s_date_id = v.findViewById(R.id.s_date_id);
//            TextView s_month_id = v.findViewById(R.id.s_month_id);
//            TextView s_year_id = v.findViewById(R.id.s_year_id);
//            TextView breakfast_id = v.findViewById(R.id.breakfast_id);
//            TextView lunch_id = v.findViewById(R.id.lunch_id);
//            TextView dinner_id = v.findViewById(R.id.dinner_id);
//            ImageView edit_id = v.findViewById(R.id.edit_id);
//            ImageView delete_id = v.findViewById(R.id.delete_id);
//            TextView e_date_id = v.findViewById(R.id.e_date_id);
//            TextView e_month_id = v.findViewById(R.id.e_month_id);
//            TextView e_year_id = v.findViewById(R.id.e_year_id);
//            RecyclerView dish_recycler_view = v.findViewById(R.id.dish_recycler_view);
//            RecyclerView excludes_recycler_view = v.findViewById(R.id.excludes_recycler_view);
//            LinearLayout exclude_linear_id = v.findViewById(R.id.exclude_linear_id);
//            TextView total_id = v.findViewById(R.id.total_id);
//            edit_id.setVisibility(View.GONE);
//            delete_id.setVisibility(View.GONE);
//
//            // ArrayList<Arraylist> horizontalList;
//            CustomAdapterRecycleDish horizontalAdapter;
//
////            TextView dish_id = v.findViewById(R.id.dish_id);
////            ImageView img_id = v.findViewById(R.id.img_id);
//            ArrayList<String> listdata = new ArrayList<String>();
//            ArrayList<HashMap<String, String>> schedule_data = new ArrayList<HashMap<String, String>>();
////            try {
//            // JSONObject jsonObject = new JSONObject(String.valueOf(items.get(i)));
//            double dishtotal=0.0;
//            Globaluse.corporatedishlistnewEdit=new ArrayList<String>();
//            for(int dishitem=0;dishitem<(items.get(i)).size();dishitem++)
//            {
//                HashMap<String, String> itr = new HashMap<String, String>();
//                itr=items.get(i).get(dishitem);
//                schedule_data.add(itr);
//
//
////                    HashMap<String, String> map = new HashMap<String, String>();
////                    map.put("id", "FH"+k);
////                    map.put("dishItemName", jsonObject.getString("dishItemName"));
////                    map.put("description", jsonObject.getString("description"));
////                    map.put("cost", jsonObject.getString("cost"));
////                    map.put("noOfUnits", jsonObject.getString("noOfUnits"));
////                    map.put("dishImage", jsonObject.getString("dishImage"));
////                    map.put("taxValue", jsonObject.getString("taxValue"));
////                    map.put("packingCharges", jsonObject.getString("packingCharges"));
////                    map.put("deliveryCharges", jsonObject.getString("deliveryCharges"));
////                    map.put("buttondisplay", jsonObject.getString("buttondisplay"));
////                    map.put("quantity", jsonObject.getString("quantity"));
////                    map.put("checkStatus", String.valueOf(jsonObject.getBoolean("checkStatus")));
////                    map.put("checkStatus_schedule", String.valueOf(jsonObject.getBoolean("checkStatus_schedule")));
////                    map.put("startDate", Sdate);
////                    map.put("endDate", Edate);
////                    map.put("deliveryType", deliveryType);//1-lunch,2-dinner,3-both
////                    map.put("breakfast",""+breakfast_chk_id.isChecked());
////                    map.put("lunch", ""+lunch_chk_id.isChecked());
////                    map.put("dinner",""+dinner_chk_id.isChecked());
////                    map.put("noNeedDeliver", String.valueOf(no_need_deliver_that_day));
////
//
//
//
//                JSONObject dishDetail = new JSONObject();
//                try {
//                    dishDetail.put("dishItemName", items.get(i).get(dishitem).get("dishItemName"));
//                    dishDetail.put("description", items.get(i).get(dishitem).get("description"));
//                    dishDetail.put("cost", items.get(i).get(dishitem).get("cost"));
//                    dishDetail.put("noOfUnits", items.get(i).get(dishitem).get("noOfUnits"));
//                    dishDetail.put("taxValue", items.get(i).get(dishitem).get("taxValue"));
//                    dishDetail.put("packingCharges", items.get(i).get(dishitem).get("packingCharges"));
//                    dishDetail.put("deliveryCharges", items.get(i).get(dishitem).get("deliveryCharges"));
//                    dishDetail.put("buttondisplay", items.get(i).get(dishitem).get("buttondisplay"));
//                    dishDetail.put("quantity", items.get(i).get(dishitem).get("quantity"));
//                    dishDetail.put("checkStatus", items.get(i).get(dishitem).get("checkStatus"));
//                    dishDetail.put("checkStatus_schedule", items.get(i).get(dishitem).get("checkStatus_schedule"));
//                    dishDetail.put("dishImage", items.get(i).get(dishitem).get("dishImage"));
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                Globaluse.corporatedishlistnewEdit.add(String.valueOf(dishDetail));
//
//
//
//                double noOfUnits= Double.parseDouble(items.get(i).get(dishitem).get("noOfUnits"));
//                double cost= Double.parseDouble(items.get(i).get(dishitem).get("cost"));
//                double totalval=((noOfUnits)*(cost));
//                dishtotal+=totalval;
//
//                if(dishitem==0)
//                {
//
//                    String string = (items.get(i).get(dishitem).get("startDate"));
//                    String[] parts = string.split("/");
//                    String part1 = parts[0]; // 004
//                    String part2 = parts[1];
//                    String part3 = parts[2];
//                    String input_date=string;
//                    SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
//                    Date dt1= null;
//                    try {
//                        dt1 = format1.parse(input_date);
//                        DateFormat format2=new SimpleDateFormat("MMM");
//                        String finalDay=format2.format(dt1);
//                        s_month_id.setText(finalDay);
//                        s_date_id.setText(part1);
//                        s_year_id.setText(part3);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    String string1 = (items.get(i).get(dishitem).get("endDate"));
//                    String[] parts1 = string1.split("/");
//                    String part11 = parts1[0]; // 004
//                    String part21 = parts1[1];
//                    String part31= parts1[2];
//                    String input_date1=string1;
//                    SimpleDateFormat format11=new SimpleDateFormat("dd/MM/yyyy");
//                    Date dt11= null;
//                    try {
//                        dt11 = format11.parse(input_date1);
//                        DateFormat format2=new SimpleDateFormat("MMM");
//                        String finalDay=format2.format(dt11);
//                        e_month_id.setText(finalDay);
//                        e_date_id.setText(part11);
//                        e_year_id.setText(part31);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    if(items.get(i).get(dishitem).get("breakfast").equals("true"))
//                    {
//                        breakfast_id.setVisibility(View.VISIBLE);
//                    }
//                    if(items.get(i).get(dishitem).get("lunch").equals("true"))
//                    {
//                        lunch_id.setVisibility(View.VISIBLE);
//                    }
//                    if(items.get(i).get(dishitem).get("dinner").equals("true"))
//                    {
//                        dinner_id.setVisibility(View.VISIBLE);
//                    }
//
//
//                    JSONArray jArray = null;
//                    try {
//                        jArray = new JSONArray(items.get(i).get(dishitem).get("noNeedDeliver"));
//                        if (jArray != null) {
//                            for (int l=0;l<jArray.length();l++){
//                                listdata.add(jArray.getString(l));
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    // listdata=items.get(i).get(dishitem).get("noNeedDeliver");
//                    // JSONArray jArray = (JSONArray)jsonObject;
////                            if (items.get(i).get(dishitem).get("dinner") != null) {
//
////                                for (int k=0;k<items.get(i).get(dishitem).get("noNeedDeliver").length();k++){
//////                                    try {
//////
//////                                     //   listdata.add(no_need_deliver_that_day.getString(k));
//////
//////                                    } catch (JSONException e) {
//////                                        e.printStackTrace();
//////                                    }
////                                }
//
//
//
//                    // }
//
//                }
//
//
//            }
//
//            total_id.setText(""+dishtotal);
//
//            if(listdata.isEmpty())
//            {
//                exclude_linear_id.setVisibility(View.GONE);
//            }else
//            {
//                exclude_linear_id.setVisibility(View.VISIBLE);
//            }
//
//
//            CustomAdapterRecycle horizontalAdapter2;
//            horizontalAdapter2=new CustomAdapterRecycle(listdata);
//            LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(CorporateOrderDetail.this, LinearLayoutManager.HORIZONTAL, false);
//            excludes_recycler_view.setLayoutManager(horizontalLayoutManagaer2);
//            excludes_recycler_view.setAdapter(horizontalAdapter2);
//
//            excludes_recycler_view.setNestedScrollingEnabled(false);
//            // FulllistItemdisplay.setListViewHeightBasedOnItems(excludes_recycler_view);
//
//            horizontalAdapter=new CustomAdapterRecycleDish(schedule_data);
//            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(CorporateOrderDetail.this, LinearLayoutManager.VERTICAL, false);
//            dish_recycler_view.setLayoutManager(horizontalLayoutManagaer);
//            dish_recycler_view.setAdapter(horizontalAdapter);
//            dish_recycler_view.setNestedScrollingEnabled(false);
//
////                Glide.with(CorporateAfterSchedule.this).load(items.get(i).get("dishImage")).into(img_id);
////                dish_multipleprice.setText("Rs. "+jsonObject.getString("cost"));
////                dish_id.setText(items.get(i).get("noOfUnits")+" X "+jsonObject.getString("dishItemName"));
//
////            }catch (JSONException err){
////                Log.d("Error", err.toString());
////            }
//
//
//
//            delete_id.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Globaluse.schedule_data_array
//                    Globaluse.schedule_data_array.remove(items.get(i));
//                    notifyDataSetChanged();
////                    CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterSchedule.this,  Globaluse.schedule_data_array);
////                    navLV.setAdapter(adapter);
////                    FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);
//
//                }
//            });
//
//
//
//            edit_id.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                    Globaluse.corporatedishlistnewEdit=new ArrayList<String>();
//                    for(int dishitem=0;dishitem<(items.get(i)).size();dishitem++) {
//
//
////                    HashMap<String, String> map = new HashMap<String, String>();
////                    map.put("id", "FH"+k);
////                    map.put("dishItemName", jsonObject.getString("dishItemName"));
////                    map.put("description", jsonObject.getString("description"));
////                    map.put("cost", jsonObject.getString("cost"));
////                    map.put("noOfUnits", jsonObject.getString("noOfUnits"));
////                    map.put("dishImage", jsonObject.getString("dishImage"));
////                    map.put("taxValue", jsonObject.getString("taxValue"));
////                    map.put("packingCharges", jsonObject.getString("packingCharges"));
////                    map.put("deliveryCharges", jsonObject.getString("deliveryCharges"));
////                    map.put("buttondisplay", jsonObject.getString("buttondisplay"));
////                    map.put("quantity", jsonObject.getString("quantity"));
////                    map.put("checkStatus", String.valueOf(jsonObject.getBoolean("checkStatus")));
////                    map.put("checkStatus_schedule", String.valueOf(jsonObject.getBoolean("checkStatus_schedule")));
////                    map.put("startDate", Sdate);
////                    map.put("endDate", Edate);
////                    map.put("deliveryType", deliveryType);//1-lunch,2-dinner,3-both
////                    map.put("breakfast",""+breakfast_chk_id.isChecked());
////                    map.put("lunch", ""+lunch_chk_id.isChecked());
////                    map.put("dinner",""+dinner_chk_id.isChecked());
////                    map.put("noNeedDeliver", String.valueOf(no_need_deliver_that_day));
////
//
//
//                        JSONObject dishDetail = new JSONObject();
//                        try {
//                            dishDetail.put("dishItemName", items.get(i).get(dishitem).get("dishItemName"));
//                            dishDetail.put("description", items.get(i).get(dishitem).get("description"));
//                            dishDetail.put("cost", items.get(i).get(dishitem).get("cost"));
//                            dishDetail.put("noOfUnits", items.get(i).get(dishitem).get("noOfUnits"));
//                            dishDetail.put("taxValue", items.get(i).get(dishitem).get("taxValue"));
//                            dishDetail.put("packingCharges", items.get(i).get(dishitem).get("packingCharges"));
//                            dishDetail.put("deliveryCharges", items.get(i).get(dishitem).get("deliveryCharges"));
//                            dishDetail.put("buttondisplay", items.get(i).get(dishitem).get("buttondisplay"));
//                            dishDetail.put("quantity", items.get(i).get(dishitem).get("quantity"));
//                            dishDetail.put("checkStatus", items.get(i).get(dishitem).get("checkStatus"));
//                            dishDetail.put("checkStatus_schedule", items.get(i).get(dishitem).get("checkStatus_schedule"));
//                            dishDetail.put("dishImage", items.get(i).get(dishitem).get("dishImage"));
//
//                        } catch (JSONException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        Globaluse.corporatedishlistnewEdit.add(String.valueOf(dishDetail));
//                    }
//
//
//
//                    Intent intent = new Intent(CorporateOrderDetail.this, CorporateScheduleEdit.class);
//                    startActivity(intent);
//
//                }
//            });
//
//
//
//            return v;
//        }
//    }







//    public class CustomAdapterRecycleDish extends RecyclerView.Adapter<CustomAdapterRecycleDish.MyViewHolder> {
//
//        private ArrayList<HashMap<String, String>> dataSet;
//
//        public class MyViewHolder extends RecyclerView.ViewHolder {
//
//            TextView dish_id,dish_price,dish_multipleprice;
//            ImageView img_id;
//
//            public MyViewHolder(View itemView) {
//                super(itemView);
//                this.dish_id = (TextView) itemView.findViewById(R.id.dish_id);
//                this.dish_price = (TextView) itemView.findViewById(R.id.dish_price);
//                this.dish_multipleprice = (TextView) itemView.findViewById(R.id.dish_multipleprice);
//                //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
//                this.img_id = (ImageView) itemView.findViewById(R.id.img_id);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//
//                    }
//                });
//            }
//        }
//
//        public CustomAdapterRecycleDish(ArrayList<HashMap<String, String>> data) {
//            this.dataSet = data;
//        }
//
//        @Override
//        public CustomAdapterRecycleDish.MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                                                               int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_schedule_my_dish_item, parent, false);
//
//            //view.setOnClickListener(MainActivity.myOnClickListener);
//
//            CustomAdapterRecycleDish.MyViewHolder myViewHolder = new CustomAdapterRecycleDish.MyViewHolder(view);
//            return myViewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final CustomAdapterRecycleDish.MyViewHolder holder, final int listPosition) {
//
//            TextView dish_id = holder.dish_id;
//            TextView dish_price = holder.dish_price;
//            TextView dish_multipleprice = holder.dish_multipleprice;
//            ImageView img_id = holder.img_id;
//
//            dish_id.setText(dataSet.get(listPosition).get("noOfUnits")+" X "+dataSet.get(listPosition).get("dishItemName"));
//            dish_price.setText("Rs. "+dataSet.get(listPosition).get("cost")+" /-");
//            double noOfUnits= Double.parseDouble(dataSet.get(listPosition).get("noOfUnits"));
//            double cost= Double.parseDouble(dataSet.get(listPosition).get("cost"));
//            double totalval=((noOfUnits)*(cost));
//
//            dish_multipleprice.setText(""+totalval);
//
//            Glide.with(CorporateOrderDetail.this).load(dataSet.get(listPosition).get("dishImage")).into(img_id);
//
//            //textViewVersion.setText(dataSet.get(listPosition).getVersion());
//            //imageView.setImageResource(dataSet.get(listPosition).getImage());
//        }
//
//        @Override
//        public int getItemCount() {
//            return dataSet.size();
//        }
//    }




//    class CustomAdapterRecycle extends RecyclerView.Adapter<CustomAdapterRecycle.MyViewHolder> {
//
//        private ArrayList<String> dataSet;
//
//        public  class MyViewHolder extends RecyclerView.ViewHolder {
//
//            TextView month_id,day_id,year_id;
//            public MyViewHolder(View itemView) {
//                super(itemView);
//                this.month_id = (TextView) itemView.findViewById(R.id.month_id);
//                this.day_id = (TextView) itemView.findViewById(R.id.day_id);
//                this.year_id = (TextView) itemView.findViewById(R.id.year_id);
//                //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
////                this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image);
////                itemView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v)
////                    {
////                        if (getPosition()==0)
////                        {
////                            Toast.makeText(v.getContext(), " On CLick one", Toast.LENGTH_SHORT).show();
////
////                        } if (getPosition()==1)
////                    {
////                        Toast.makeText(v.getContext(), " On CLick Two", Toast.LENGTH_SHORT).show();
////
////                    } if (getPosition()==2)
////                    {
////                        Toast.makeText(v.getContext(), " On CLick Three", Toast.LENGTH_SHORT).show();
////
////                    } if (getPosition()==3)
////                    {
////                        Toast.makeText(v.getContext(), " On CLick Fore", Toast.LENGTH_SHORT).show();
////
////                    }
////
////                    }
////                });
//            }
//        }
//
//        public CustomAdapterRecycle(ArrayList<String> data) {
//            this.dataSet = data;
//        }
//
//        @Override
//        public CustomAdapterRecycle.MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                                                           int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_schedule_my_item, parent, false);
//
//            //view.setOnClickListener(MainActivity.myOnClickListener);
//
//            CustomAdapterRecycle.MyViewHolder myViewHolder = new CustomAdapterRecycle.MyViewHolder(view);
//            return myViewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final CustomAdapterRecycle.MyViewHolder holder, final int listPosition) {
//
//            TextView month_id = holder.month_id;
//            TextView day_id = holder.day_id;
//            TextView year_id = holder.year_id;
//            String string = (dataSet.get(listPosition));
//            String[] parts = string.split("/");
//            String part1 = parts[0]; // 004
//            String part2 = parts[1];
//            String part3 = parts[2];
//            String input_date=string;
//            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
//            Date dt1= null;
//            try {
//                dt1 = format1.parse(input_date);
//                DateFormat format2=new SimpleDateFormat("MMM");
//                String finalDay=format2.format(dt1);
//                month_id.setText(finalDay);
//                day_id.setText(part1);
//                year_id.setText(part3);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return dataSet.size();
//        }}





    public void  GetrequestDetail()
    {
        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");
        String URL = APIBaseURL.BASEURLLINK_B2B_Subscription_list_detail+""+requestId;
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(java.sql.Date.class, sqlAdapter)
                        .setDateFormat("yyyy-MM-dd")
                        .create();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if(jsonObject.getString("isSuccess").equals("true"))
                    {
                        JSONArray dataObject = jsonObject.getJSONArray("data");
                        RequestBtoBItem foodItem = new RequestBtoBItem();
                        foodItem = gson.fromJson(String.valueOf(dataObject.get(0)), RequestBtoBItem .class);
                        user_id.setText(foodItem.getSubscriberName());
                        company_name.setText(foodItem.getCompanyName());
                        company_email.setText(foodItem.getEmailId());
                        mobile_id.setText(foodItem.getPhoneNumber());
                        notes_id.setText(foodItem.getNotes());

                       // RequestBtoBItem.DeliveryAddress deliver_add_obj= foodItem.deliveryAddresses.get(0).addressLine1;
                        String deliver_add= foodItem.deliveryAddresses.get(0).addressLine1+","+ foodItem.deliveryAddresses.get(0).addressLine2+","+foodItem.deliveryAddresses.get(0).city+","+foodItem.deliveryAddresses.get(0).state+","+foodItem.deliveryAddresses.get(0).postalCode+","+foodItem.deliveryAddresses.get(0).country;

                        location_id.setText(deliver_add);

                        toolbar.setTitle(foodItem.status);

                        addressLine1=foodItem.deliveryAddresses.get(0).addressLine1;
                        addressLine2=foodItem.deliveryAddresses.get(0).addressLine2;
                        city=foodItem.deliveryAddresses.get(0).city;
                        state=foodItem.deliveryAddresses.get(0).state;
                        postalCode=foodItem.deliveryAddresses.get(0).postalCode;
                        country=foodItem.deliveryAddresses.get(0).country;


                        subscriptionDate=dataObject.getJSONObject(0).getString("subscriptionDate");
                        isActive=dataObject.getJSONObject(0).getString("isActive");
                        isDeleted=dataObject.getJSONObject(0).getString("isDeleted");
                        partitionKey=dataObject.getJSONObject(0).getString("partitionKey");
                        rowKey=dataObject.getJSONObject(0).getString("rowKey");
                        timestamp=dataObject.getJSONObject(0).getString("timestamp");
                        eTag=dataObject.getJSONObject(0).getString("eTag");
                        notes=dataObject.getJSONObject(0).getString("notes");


                        if(foodItem.status.equalsIgnoreCase("Request"))
                        {
                            redit_id.setVisibility(View.VISIBLE);
                        }else {
                            redit_id.setVisibility(View.GONE);
                        }
                       // redit_id.setVisibility(View.VISIBLE);
                         schedullingInfo = (dataObject.getJSONObject(0).getJSONArray("schedullingInfo"));

                        Globaluse.scheduleList=(dataObject.getJSONObject(0).getJSONArray("schedullingInfo"));

                    String Status_current=foodItem.status;
                        sub_totalvalue1=0.0;
                        adapter2 = new CustomAdapter22(CorporateOrderDetailUser.this,  Globaluse.scheduleList,Status_current);
                        navLV.setAdapter(adapter2);
                        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);
                        sub_totalvalue1=0.0;
                    }



                    for(int k=0;k<Globaluse.scheduleList.length();k++)
                    {

                        try {
                            JSONObject jsonObject2 = new JSONObject(String.valueOf(Globaluse.scheduleList.get(k)));
                            String scheduleID=jsonObject2.getString("scheduleID");
                            String comapnyID=jsonObject2.getString("companyID");
                            JSONArray dishesItems_jsonarray = new JSONArray(jsonObject2.getString("dishesItems"));

                            JSONArray exclusionDates_jsonarray = new JSONArray(jsonObject2.getString("exclusionDates"));

                            String[] separated = (jsonObject2.getString("startDate").split("T"));
                            String string = dateFormat(separated[0]);
                            String[] parts = string.split("/");
                            String part1 = parts[0]; // 004
                            String part2 = parts[1];
                            String part3 = parts[2];
                            String input_date=string;
                            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");

                            Date dt1= null;
                            try {
                                dt1 = format1.parse(input_date);
                                DateFormat format2=new SimpleDateFormat("MMM");
                                String finalDay=format2.format(dt1);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String[] separated2 = (jsonObject2.getString("endDate").split("T"));

                            String string1 = dateFormat(separated2[0]);
                            String[] parts1 = string1.split("/");
                            String part11 = parts1[0]; // 004
                            String part21 = parts1[1];
                            String part31= parts1[2];
                            String input_date1=string1;
                            SimpleDateFormat format11=new SimpleDateFormat("dd/MM/yyyy");
                            Date dt11= null;
                            try {
                                dt11 = format11.parse(input_date1);
                                DateFormat format2=new SimpleDateFormat("MMM");
                                String finalDay=format2.format(dt11);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            int totalmeal=0;
                            if(jsonObject2.getBoolean("isBreakfast"))
                            {

                                totalmeal++;
                            }

                            if(jsonObject2.getBoolean("isLunch"))
                            {

                                totalmeal++;
                            }
                            if(jsonObject2.getBoolean("isDinner"))
                            {

                                totalmeal++;
                            }
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate start = LocalDate.parse(string,formatter);
                            LocalDate end = LocalDate.parse(string1,formatter);
                            System.out.println(ChronoUnit.DAYS.between(start, end));
                            double dishtotal=0.0;
                            for(int t=0;t<dishesItems_jsonarray.length();t++)
                            {
                                double noOfUnits= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("noOfUnits"));
                                double cost= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("cost"));
                                double totalval=((noOfUnits)*(cost));
                                Log.i(TAG, "noOfUnits ( * ) cost" + noOfUnits +" * "+cost+" = "+totalval);
                                dishtotal+=totalval;
                                Log.i(TAG, "dishtotal" + dishtotal);
                            }
                            double selectmeal=(dishtotal * totalmeal);
                            Log.i(TAG, "dishTotal ( * ) totalmeal" + selectmeal);
                            long numberOfDays=(((ChronoUnit.DAYS.between(start, end)))-(exclusionDates_jsonarray.length()))+1;
                            Log.i(TAG, "numberOfDays : start date & end date "+""+ChronoUnit.DAYS.between(start, end)+ " - "+"exclusionDates length : "+exclusionDates_jsonarray.length()+" = "+numberOfDays);


                            double alltotal1=0.0;
                            JSONArray dishesItems_jsonarray2 = new JSONArray(jsonObject2.getString("dishesItems"));
                            boolean isWekely=Globaluse.CheckWeeeklyUsingJson(dishesItems_jsonarray2);
                            if(isWekely)
                            {
                                // Toast.makeText(CorporateAfterScheduleApi.this, "Only Weekly Selection", Toast.LENGTH_SHORT).show();
                                alltotal1=(selectmeal * 1);
                            }
                            else
                            {
                                //Toast.makeText(CorporateAfterScheduleApi.this, "Day selection", Toast.LENGTH_SHORT).show();
                                alltotal1=(selectmeal * numberOfDays);
                            }

                            //double alltotal1=(selectmeal * numberOfDays);



                            Log.i(TAG, "all total" + alltotal1);

                            // s_date_id.setText(scheduleID);
                            sub_totalvalue1+=alltotal1;

                        dishPriceId.setText(""+sub_totalvalue1);
                        TotalId.setText(""+sub_totalvalue1);

                        } catch (JSONException e) {
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
                if (error instanceof AuthFailureError) {
                    //TODO
//                    ViewGroup viewGroup = CorporateScheduleFromApi.this.findViewById(android.R.id.content);
//
//                    //then we will inflate the custom alert dialog xml that we created
//                    View dialogView = LayoutInflater.from(CorporateScheduleFromApi.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);
//
//                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
//                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
//                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);
//
//                    //Now we need an AlertDialog.Builder object
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CorporateScheduleFromApi.this);
//
//                    //setting the view of the builder to our custom view that we already inflated
//                    builder.setView(dialogView);
//
//                    //finally creating the alert dialog and displaying it
//                    android.app.AlertDialog alertDialog = builder.create();
//
//
//                    buttonOk.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//                            ConsumerMainActivity.logout();
//
//                        }
//                    });
//
//                    closeBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//
//                        }
//                    });
//
//                    buttonreset.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//                        }
//                    });
//                    alertDialog.setCanceledOnTouchOutside(false);
//                    alertDialog.show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(CorporateOrderDetailUser.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporateOrderDetailUser.this){    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                //params.put("content-type", "application/json");
                return params;
            }
        };


        ApplicationController.getInstance().addToRequestQueue(stringRequest, "scheduleList");
    }


    public class CustomAdapterRecycleDish extends RecyclerView.Adapter<  CustomAdapterRecycleDish.MyViewHolder> {

        private JSONArray dataSet;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView dish_id,dish_price,dish_multipleprice;
            ImageView img_id;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.dish_id = (TextView) itemView.findViewById(R.id.dish_id);
                this.dish_price = (TextView) itemView.findViewById(R.id.dish_price);
                this.dish_multipleprice = (TextView) itemView.findViewById(R.id.dish_multipleprice);
                //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
                this.img_id = (ImageView) itemView.findViewById(R.id.img_id);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                    }
                });
            }
        }

        public CustomAdapterRecycleDish(JSONArray data) {
            this.dataSet = data;
        }

        @Override
        public   MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                  int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_schedule_my_dish_item, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

              MyViewHolder myViewHolder = new   MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final   MyViewHolder holder, final int listPosition) {

            TextView dish_id = holder.dish_id;
            TextView dish_price = holder.dish_price;
            TextView dish_multipleprice = holder.dish_multipleprice;
            ImageView img_id = holder.img_id;

            try {

                //dataSet.getJSONObject(listPosition).getString("dishItemName")
                dish_id.setText(dataSet.getJSONObject(listPosition).getString("noOfUnits")+" X "+dataSet.getJSONObject(listPosition).getString("dishItemName"));
               // dish_price.setText("Rs. "+dataSet.getJSONObject(listPosition).getString("cost")+" /-");

                if(dataSet.getJSONObject(listPosition).getString("cost").equals("0"))
                {
                    //price_id.setText("Rs. "+" TBD ");
                    dish_price.setText("₹ "+" TBD ");
                }else
                {
                    dish_price.setText("₹ "+dataSet.getJSONObject(listPosition).getString("cost")+"/-");
                }

                double noOfUnits= Double.parseDouble(dataSet.getJSONObject(listPosition).getString("noOfUnits"));
                double cost= Double.parseDouble(dataSet.getJSONObject(listPosition).getString("cost"));
                double totalval=((noOfUnits)*(cost));

                //dish_multipleprice.setText(""+totalval);

                dish_multipleprice.setText("₹ "+totalval);



                Glide.with(  CorporateOrderDetailUser.this).load(dataSet.getJSONObject(listPosition).getString("dishImageUrl")).into(img_id);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //textViewVersion.setText(dataSet.get(listPosition).getVersion());
            //imageView.setImageResource(dataSet.get(listPosition).getImage());
        }

        @Override
        public int getItemCount() {
            return dataSet.length();
        }
    }




    class CustomAdapterRecycle extends RecyclerView.Adapter<  CustomAdapterRecycle.MyViewHolder> {

        private JSONArray dataSet;

        public  class MyViewHolder extends RecyclerView.ViewHolder {

            TextView month_id,day_id,year_id;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.month_id = (TextView) itemView.findViewById(R.id.month_id);
                this.day_id = (TextView) itemView.findViewById(R.id.day_id);
                this.year_id = (TextView) itemView.findViewById(R.id.year_id);
                //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
//                this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        if (getPosition()==0)
//                        {
//                            Toast.makeText(v.getContext(), " On CLick one", Toast.LENGTH_SHORT).show();
//
//                        } if (getPosition()==1)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Two", Toast.LENGTH_SHORT).show();
//
//                    } if (getPosition()==2)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Three", Toast.LENGTH_SHORT).show();
//
//                    } if (getPosition()==3)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Fore", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    }
//                });
            }
        }

        public CustomAdapterRecycle(JSONArray data) {
            this.dataSet = data;
        }

        @Override
        public   MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                              int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_schedule_my_item, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

              MyViewHolder myViewHolder = new   MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final   MyViewHolder holder, final int listPosition) {

            TextView month_id = holder.month_id;
            TextView day_id = holder.day_id;
            TextView year_id = holder.year_id;


            try {
                String[] separated = (dataSet.getString(listPosition).split("T"));

                String  string = dateFormat(separated[0]);


                String[] parts = string.split("/");
                String part1 = parts[0]; // 004
                String part2 = parts[1];
                String part3 = parts[2];
                String input_date=string;
                SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");

                Date dt1= null;
                try {
                    dt1 = format1.parse(input_date);
                    DateFormat format2=new SimpleDateFormat("MMM");
                    String finalDay=format2.format(dt1);
                    month_id.setText(finalDay);
                    day_id.setText(part1);
                    year_id.setText(part3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }






        }

        @Override
        public int getItemCount() {
            return dataSet.length();
        }}







    public void updateRequeststatus() throws JSONException {
        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");

        String url = APIBaseURL.BASEURLLINK_B2B_UpdateRequestStatus+requestId+"/"+companyId+"/"+sendstatus;
        //String url = APIBaseURL.BASEURLLINK_B2B_UpdateRequestStatus;
        ProgressDialog progressDialog = new ProgressDialog(CorporateOrderDetailUser.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject inputObject = new JSONObject();
        inputObject.put("requestId", requestId);
        inputObject.put("companyId", companyId);
        inputObject.put("requestStauts", sendstatus);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
                public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response));
                        if(jsonObject.getString("isSuccess").equals("true"))
                        {
                           // JSONArray datajsonarray = jsonObject.getJSONArray("data");

                            Toast.makeText(CorporateOrderDetailUser.this, jsonObject.getString("successMessage"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CorporateOrderDetailUser.this, CorporateMenuWithNavigatioinAdmin.class);
                            startActivity(intent);
                            finish();


                            //finish();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.dismiss();
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.statusCode == 404) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                            //use this json as you want
                           // Toast.makeText(CorporateOrderDetail.this, "Address Field should not contain #,Invalid address", Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                        }
                    }
                    if (error instanceof AuthFailureError) {
                        //TODO
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                    String token = sh.getString("token", "");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + token);
                   // params.put("content-type", "application/json");
                    return params;
                }
            };
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "edit_address_taq");



    }














    class CustomAdapter22 extends BaseAdapter {

        Context context;
        JSONArray items;
        String Status_current;

        public CustomAdapter22(Context context, JSONArray items,String Status_current) {
            this.context = context;
            this.items = items;
            this.Status_current = Status_current;
        }


        @Override
        public int getCount() {
            return items.length();
        }

        @Override
        public Object getItem(int i) {

            try {
                return items.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_schedule_item_new, null);

            ImageView dropdown_id = v.findViewById(R.id.dropdown_id);
            LinearLayout displaylist = v.findViewById(R.id.displaylist);
            TextView s_date_id = v.findViewById(R.id.s_date_id);
            TextView s_month_id = v.findViewById(R.id.s_month_id);
            TextView s_year_id = v.findViewById(R.id.s_year_id);
            TextView breakfast_id = v.findViewById(R.id.breakfast_id);
            TextView lunch_id = v.findViewById(R.id.lunch_id);
            TextView dinner_id = v.findViewById(R.id.dinner_id);
            ImageView edit_id = v.findViewById(R.id.edit_id);
            ImageView delete_id = v.findViewById(R.id.delete_id);
            TextView e_date_id = v.findViewById(R.id.e_date_id);
            TextView e_month_id = v.findViewById(R.id.e_month_id);
            TextView e_year_id = v.findViewById(R.id.e_year_id);
            RecyclerView dish_recycler_view = v.findViewById(R.id.dish_recycler_view);
            RecyclerView excludes_recycler_view = v.findViewById(R.id.excludes_recycler_view);
            LinearLayout exclude_linear_id = v.findViewById(R.id.exclude_linear_id);
            TextView total_id = v.findViewById(R.id.total_id);

//                if(Status_current.equalsIgnoreCase("Request"))
//                {
//                    edit_id.setVisibility(View.VISIBLE);
//                    delete_id.setVisibility(View.VISIBLE);
//                }else
//                {
//                    edit_id.setVisibility(View.GONE);
//                    delete_id.setVisibility(View.GONE);
//                }

            edit_id.setVisibility(View.GONE);
            delete_id.setVisibility(View.GONE);

            dropdown_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(displaylist.isShown())
                    {
                        displaylist.setVisibility(View.GONE);
                    }else
                    {
                        displaylist.setVisibility(View.VISIBLE);
                    }


                }
            });




            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(items.get(i)));
                String scheduleID=jsonObject.getString("scheduleID");
                String comapnyID=jsonObject.getString("companyID");
                JSONArray dishesItems_jsonarray = new JSONArray(jsonObject.getString("dishesItems"));
                CustomAdapterRecycleDish horizontalAdapter;
                horizontalAdapter=new  CustomAdapterRecycleDish(dishesItems_jsonarray);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager( CorporateOrderDetailUser.this, LinearLayoutManager.VERTICAL, false);
                dish_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                dish_recycler_view.setAdapter(horizontalAdapter);
                dish_recycler_view.setNestedScrollingEnabled(false);
                JSONArray exclusionDates_jsonarray = new JSONArray(jsonObject.getString("exclusionDates"));
                CustomAdapterRecycle horizontalAdapter2;
                horizontalAdapter2=new  CustomAdapterRecycle(exclusionDates_jsonarray);
                LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager( CorporateOrderDetailUser.this, LinearLayoutManager.HORIZONTAL, false);
                excludes_recycler_view.setLayoutManager(horizontalLayoutManagaer2);
                excludes_recycler_view.setAdapter(horizontalAdapter2);
                excludes_recycler_view.setNestedScrollingEnabled(false);
                String[] separated = (jsonObject.getString("startDate").split("T"));
                String string = dateFormat(separated[0]);
                String[] parts = string.split("/");
                String part1 = parts[0]; // 004
                String part2 = parts[1];
                String part3 = parts[2];
                String input_date=string;
                SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");

                Date dt1= null;
                try {
                    dt1 = format1.parse(input_date);
                    DateFormat format2=new SimpleDateFormat("MMM");
                    String finalDay=format2.format(dt1);
                    s_month_id.setText(finalDay);
                    s_date_id.setText(part1);
                    s_year_id.setText(part3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String[] separated2 = (jsonObject.getString("endDate").split("T"));

                String string1 = dateFormat(separated2[0]);
                String[] parts1 = string1.split("/");
                String part11 = parts1[0]; // 004
                String part21 = parts1[1];
                String part31= parts1[2];
                String input_date1=string1;
                SimpleDateFormat format11=new SimpleDateFormat("dd/MM/yyyy");
                Date dt11= null;
                try {
                    dt11 = format11.parse(input_date1);
                    DateFormat format2=new SimpleDateFormat("MMM");
                    String finalDay=format2.format(dt11);
                    e_month_id.setText(finalDay);
                    e_date_id.setText(part11);
                    e_year_id.setText(part31);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int totalmeal=0;
                if(jsonObject.getBoolean("isBreakfast"))
                {
                    breakfast_id.setVisibility(View.VISIBLE);
                    totalmeal++;
                }

                if(jsonObject.getBoolean("isLunch"))
                {
                    lunch_id.setVisibility(View.VISIBLE);
                    totalmeal++;
                }
                if(jsonObject.getBoolean("isDinner"))
                {
                    dinner_id.setVisibility(View.VISIBLE);
                    totalmeal++;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate start = LocalDate.parse(string,formatter);
                LocalDate end = LocalDate.parse(string1,formatter);
                System.out.println(ChronoUnit.DAYS.between(start, end));
                double dishtotal=0.0;
                for(int t=0;t<dishesItems_jsonarray.length();t++)
                {
                    double noOfUnits= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("noOfUnits"));
                    double cost= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("cost"));
                    double totalval=((noOfUnits)*(cost));
                    Log.i(TAG, "noOfUnits ( * ) cost" + noOfUnits +" * "+cost+" = "+totalval);
                    dishtotal+=totalval;
                    Log.i(TAG, "dishtotal" + dishtotal);
                }
                double selectmeal=(dishtotal * totalmeal);
                Log.i(TAG, "dishTotal ( * ) totalmeal" + selectmeal);
                long numberOfDays=(((ChronoUnit.DAYS.between(start, end)))-(exclusionDates_jsonarray.length()))+1;
                Log.i(TAG, "numberOfDays : start date & end date "+""+ChronoUnit.DAYS.between(start, end)+ " - "+"exclusionDates length : "+exclusionDates_jsonarray.length()+" = "+numberOfDays);


                double alltotal1=0.0;
                JSONArray dishesItems_jsonarray2 = new JSONArray(jsonObject.getString("dishesItems"));
                boolean isWekely=Globaluse.CheckWeeeklyUsingJson(dishesItems_jsonarray2);
                if(isWekely)
                {
                    // Toast.makeText(CorporateAfterScheduleApi.this, "Only Weekly Selection", Toast.LENGTH_SHORT).show();
                    alltotal1=(selectmeal * 1);
                }
                else
                {
                    //Toast.makeText(CorporateAfterScheduleApi.this, "Day selection", Toast.LENGTH_SHORT).show();
                    alltotal1=(selectmeal * numberOfDays);
                }
               // double alltotal1=(selectmeal * numberOfDays);


                Log.i(TAG, "all total" + alltotal1);
                total_id.setText(""+alltotal1);
                // s_date_id.setText(scheduleID);
                //sub_totalvalue1+=alltotal1;

//                dishPriceId.setText(""+sub_totalvalue1);
//                TotalId.setText(""+sub_totalvalue1);

            } catch (JSONException e) {
                e.printStackTrace();
            }



            return v;
        }
    }

}