package stayabode.foodyHive.activities.consumers;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import stayabode.foodyHive.R;

import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.AddReferrals;
import stayabode.foodyHive.models.ItemAddOns;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class ConsumerActivityReferFriends extends AppCompatActivity  {

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
    EditText nameId,emailId,phonenumberId;
    Button addFriends;
    List<AddReferrals> AddReferrals_var;
    JSONArray dataArray;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    Button submitButton;

    String email_str="",mobile_str="",name_str="",myReferralCode="";
    int myReferralpoint=50;
    TextView myReferralCode_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_friends);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
//        if (Build.VERSION.SDK_INT > 22) {
//            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
//                    /*zzzzzzREQUEST_PERMISSIONS},*/ 1);
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Refer Friends");
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
             //orderInvoiceNo = extras.getString("orderInvoiceNo");
            //The key argument here must match that used in the other activity
        }


        nameId=findViewById(R.id.nameId);
        emailId=findViewById(R.id.emailId);
        phonenumberId=findViewById(R.id.phonenumberId);
        addFriends=findViewById(R.id.addFriends);

        myReferralCode_id=findViewById(R.id.myReferralCode_id);

        AddReferrals_var = new ArrayList<>();


        getUserInfo();
        getAllreadyallocateData();



        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String email_str = SaveSharedPreference.getLoggedInUserEmail(getApplicationContext());
//                String mobile_str = SaveSharedPreference.getLoggedInUserPhone(getApplicationContext());
//                String name_str = SaveSharedPreference.getLoggedInUserName(getApplicationContext());


                String to_email_str = emailId.getText().toString();
                String to_name_str = nameId.getText().toString();
                String to_mobile_str = phonenumberId.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());


                if (validate(to_email_str, to_name_str, to_mobile_str)) {

                boolean isavilble = false;
                boolean isavilble_list = false;


                if(dataArray!=null)
                {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject fullreferrdData = null;
                        try {
                            fullreferrdData = dataArray.getJSONObject(i);
                            //Toast.makeText(getApplicationContext(), "ReferredToEmailId : "+fullreferrdData.optString("referredToEmailId"), Toast.LENGTH_SHORT).show();

                            if ((fullreferrdData.optString("referredToEmailId").equals(to_email_str.trim()))) {
                                //Toast.makeText(getApplicationContext(), "Sorry This email id alredy Used", Toast.LENGTH_SHORT).show();
                                isavilble = true;
                                break;
                            } else {
//                            AddReferrals_var.add(new AddReferrals(email_str,mobile_str,name_str,to_email_str,to_mobile_str,to_name_str,"REF123456",currentDateandTime,50));
//                            adapter.notifyDataSetChanged();
                                isavilble = false;
                            }
//                        if ( ArrayUtils.contains(new JSONObject[]{fullreferrdData}, to_email_str.trim() ) ) {
//                            // Do some stuff.
//                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else
                {
                    isavilble = false;
                }




                for (int i = 0; i < AddReferrals_var.size(); i++) {

                    if (AddReferrals_var.get(i).getReferredToEmailId().equals(to_email_str.trim())) {
                        isavilble_list = true;
                        break;
                    } else {
                        isavilble_list = false;
                    }


                }


                if (isavilble_list) {
                    Toast.makeText(getApplicationContext(), "Email alredy in List", Toast.LENGTH_SHORT).show();
                } else {
                    if (isavilble) {
                        Toast.makeText(getApplicationContext(), "Sorry This email id alredy Used", Toast.LENGTH_SHORT).show();
                    } else {
                        AddReferrals_var.add(new AddReferrals(email_str, mobile_str, name_str, to_email_str, to_mobile_str, to_name_str, myReferralCode, currentDateandTime, myReferralpoint));
                        adapter.notifyDataSetChanged();
                        // getUserOrders();
                    }
                }


                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }


            }

            }
        });


        // set up the RecyclerView
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, AddReferrals_var);
        recyclerView.setAdapter(adapter);

        submitButton=findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    submitReferalls();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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




    public void getAllreadyallocateData() {
        String url = APIBaseURL.getReferrals+ SaveSharedPreference.getLoggedInUserEmail(getApplicationContext());
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("allreadyallocate", response);
                List <String> stringList = new ArrayList<String>();
                try {
                    //JSONObject  jsonObject = new JSONObject(response);

                     dataArray = new JSONArray(response);
                    //JSONArray dataArray = jsonObject.getJSONArray(String.valueOf(jsonObject));

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject fullreferrdData = dataArray.getJSONObject(i);

                        //Toast.makeText(getApplicationContext(), "ReferredToEmailId : "+fullreferrdData.optString("referredToEmailId"), Toast.LENGTH_SHORT).show();


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
                    ViewGroup viewGroup = ConsumerActivityReferFriends.this.findViewById(android.R.id.content);

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
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaqs");
    }






    public void submitReferalls() throws JSONException {

        String url = APIBaseURL.addReferrals;

            ArrayList  referListsArray = new ArrayList<>();
            for(int i=0;i<AddReferrals_var.size();i++)
            {
                JSONObject inputObject = new JSONObject();
                inputObject.put("ReferredByEmailId", AddReferrals_var.get(i).getReferredByEmailId());
                inputObject.put("ReferredByPhoneNumber", AddReferrals_var.get(i).getReferredByPhoneNumber());
                inputObject.put("ReferredByName", AddReferrals_var.get(i).getReferredByName());
                inputObject.put("ReferredToEmailId", AddReferrals_var.get(i).getReferredToEmailId());
                inputObject.put("ReferredToPhoneNumber", AddReferrals_var.get(i).getReferredToPhoneNumber());
                inputObject.put("ReferredToName", AddReferrals_var.get(i).getReferredToName());
                inputObject.put("ReferralCode", AddReferrals_var.get(i).getReferralCode());
                inputObject.put("ReferralDate", AddReferrals_var.get(i).getReferralDate());
                inputObject.put("ReferralAmount", AddReferrals_var.get(i).getReferralAmount());

                referListsArray.add(inputObject);
            }

        JSONArray mJSONArray = new JSONArray(referListsArray);


        //  Toast.makeText(getApplicationContext(),"Add Address "+global_lat+" "+global_long,Toast.LENGTH_SHORT).show();

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, mJSONArray, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        //JSONObject  jsonObject = new JSONObject(response);
                       // JSONArray  dataArray2 = new JSONArray(response);
                        //JSONArray dataArray = jsonObject.getJSONArray(String.valueOf(jsonObject));
                        boolean operationStatus=false;
                        String errorMessage_str="";

                        if(!(response==null))
                        {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject fullreferrdData = response.getJSONObject(i);

                                // Toast.makeText(getApplicationContext(), "ReferredToEmailId : "+fullreferrdData.optString("referredToEmailId"), Toast.LENGTH_SHORT).show();

                                if(fullreferrdData.optString("operationStatus").equals("false"))
                                {
                                    operationStatus=false;

                                    errorMessage_str=fullreferrdData.optString("errorMessage");
                                    break;
                                }else
                                {
                                    operationStatus=true;
                                }


                            }

                            if(operationStatus)
                            {
                                Toast.makeText(getApplicationContext(), "Successfully referred Your Friends", Toast.LENGTH_LONG).show();
                                finish();
                                //recreate();
                            }else
                            {
                                Toast.makeText(getApplicationContext(), errorMessage_str, Toast.LENGTH_SHORT).show();
                                // getUserOrders();
                            }
                        }






                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(Address.this, "Delivery Address Added Successfully", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent();
//                    intent.putExtra("MESSAGE", "Added");
//                    setResult(101, intent);
//                    finish();

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.statusCode == 404) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                            //use this json as you want
                            Toast.makeText(ConsumerActivityReferFriends.this, "Address Field should not contain #,Invalid address", Toast.LENGTH_SHORT).show();
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
                        ViewGroup viewGroup = ConsumerActivityReferFriends.this.findViewById(android.R.id.content);

                        //then we will inflate the custom alert dialog xml that we created
                        View dialogView = LayoutInflater.from(ConsumerActivityReferFriends.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                        Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                        ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                        //Now we need an AlertDialog.Builder object
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerActivityReferFriends.this);

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
                    }
                    else if (error instanceof NetworkError)
                    {
                        Toast.makeText(ConsumerActivityReferFriends.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(ConsumerActivityReferFriends.this));
                    return headers;
                }
            };
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "add_address_taq");



    }


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





    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private List<AddReferrals> mData;
        private LayoutInflater mInflater;


        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<AddReferrals> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.activity_refer_friends_view_item, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //String animal = mData.get(position).getReferredToName();
            holder.name_id.setText(mData.get(position).getReferredToName());
            holder.email_id.setText(mData.get(position).getReferredToEmailId());
            holder.mobile_id.setText(mData.get(position).getReferredToPhoneNumber());

            holder.delete_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddReferrals_var.remove(position);
                    recyclerView.removeViewAt(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, AddReferrals_var.size());

                }
            });
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView name_id,email_id,mobile_id;
            ImageView delete_id;

            ViewHolder(View itemView) {
                super(itemView);
                name_id = itemView.findViewById(R.id.name_id);
                email_id = itemView.findViewById(R.id.email_id);
                mobile_id = itemView.findViewById(R.id.mobile_id);
                delete_id = itemView.findViewById(R.id.delete_id);



            }


        }





    }




    private boolean validate(String email, String name, String mobile) {
        if (name.equals(null) || name.equals("") || !isValidMobile(mobile)  ||  !isValidEmail(email)) {


            nameId=findViewById(R.id.nameId);
            emailId=findViewById(R.id.emailId);
            phonenumberId=findViewById(R.id.phonenumberId);

            if (name.equals(null) || name.equals("")) {
                nameId.setError("Enter a valid Name");
            } else {
                nameId.setError(null);
            }

            if (!isValidMobile(mobile)) {
                phonenumberId.setError("Enter a valid Mobile Number");
            } else {
                phonenumberId.setError(null);
            }

            if (!isValidEmail(email)) {
                emailId.setError("Enter valid email id");

            } else {
                emailId.setError(null);

            }

            return false;
        } else
            return true;
    }

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }






    public void getUserInfo() {

        String url = APIBaseURL.getUserInfoForReferral + SaveSharedPreference.getLoggedInUserEmail(ConsumerActivityReferFriends.this);
       // String url = "https://cimaviapi.azurewebsites.net/api/userinfo/GetUserDetailsByEmail/sekhartsr@gmail.com";

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
               // getAllreadyallocateData();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.optBoolean("isSuccess")) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        if (dataObject.getJSONArray("emailId").length() != 0) {
                            email_str = dataObject.getJSONArray("emailId").getString(0).trim();
                        }
                        if (dataObject.getJSONArray("phoneNo").length() != 0) {
                            mobile_str = dataObject.getJSONArray("phoneNo").getString(0).trim();
                        }
                        name_str = dataObject.optString("name").trim();

                        if(!(dataObject.optString("myReferralCode").equals("null")))
                        {
                            myReferralCode = dataObject.optString("myReferralCode").trim();
                            myReferralCode_id.setText(myReferralCode);
                        }

                      //  myReferralpoint = dataObject.optInt("myReferralpoint");

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
                    ViewGroup viewGroup = ConsumerActivityReferFriends.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerActivityReferFriends.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConsumerActivityReferFriends.this);

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
                    Toast.makeText(ConsumerActivityReferFriends.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, ConsumerActivityReferFriends.this);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");
    }


}