package stayabode.foodyHive.activities.consumers;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;

import stayabode.foodyHive.adapters.consumers.ParentOrderDetailAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.ChildItemOrderDetail;
import stayabode.foodyHive.models.ItemAddOns;
import stayabode.foodyHive.models.ParentItemOrderDetail;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ConusumerOrderDetail extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumerorderdetail);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        if (Build.VERSION.SDK_INT > 22) {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                    /*zzzzzzREQUEST_PERMISSIONS},*/ 1);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Detail");
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        RobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        RobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        cartIcon = findViewById(R.id.cartIcon);
        cartTotalCountText = findViewById(R.id.cartTotalCountText);
        cartTotalCountText.setVisibility(View.GONE);
        cartIcon.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             orderInvoiceNo = extras.getString("orderInvoiceNo");
            //The key argument here must match that used in the other activity
        }


         ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);
        // Initialise the Linear layout manager
         layoutManager = new LinearLayoutManager( ConusumerOrderDetail.this);
        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()



        // Set the layout manager
        // and adapter for items
        // of the parent recyclerview


        invoiceId = (TextView) findViewById(R.id.invoiceId);
        orderdate = (TextView) findViewById(R.id.orderdate);
        totalQuantityId = (TextView) findViewById(R.id.totalQuantityId);
        paymentId = (TextView) findViewById(R.id.paymentId);
        dishPriceId = (TextView) findViewById(R.id.dishPriceId);
        totalTaxId = (TextView) findViewById(R.id.totalTaxId);
        deliveryChargeId = (TextView) findViewById(R.id.deliveryChargeId);
        packagingChargeId = (TextView) findViewById(R.id.packagingChargeId);
        TotalId = (TextView) findViewById(R.id.TotalId);


        getUserOrders();



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


                                        dishPriceId.setText("" + orderSubTotal_number2);
                                        totalTaxId.setText("" + totalTax_number2);
                                        deliveryChargeId.setText("" + totalDeliveryCharges_number2);
                                        packagingChargeId.setText("" + totalPackingCharges_number2);
                                        TotalId.setText("" + grandTotal_number2);

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
                    ViewGroup viewGroup = ConusumerOrderDetail.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());

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


    private String dateFormat(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        String formattedDate = null;
        try {
            serverDate = df.parse(date);
            //SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy");

            outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = outputFormat.format(serverDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

}