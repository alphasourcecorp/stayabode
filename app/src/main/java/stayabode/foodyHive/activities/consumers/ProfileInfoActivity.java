package stayabode.foodyHive.activities.consumers;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.ConsumerPreferencesAdapter;
import stayabode.foodyHive.adapters.consumers.InfoListsAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.AccountInfoHeaders;
import stayabode.foodyHive.models.ChefDetailHeader;
import stayabode.foodyHive.models.ItemAddOns;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.FilePath;
import stayabode.foodyHive.utils.SaveSharedPreference;


import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileInfoActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_items);


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
        toolbar.setTitle("Settings");
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
        topRecyclerView = findViewById(R.id.recyclerView);
        bottomRecyclerView = findViewById(R.id.bottomRecyclerView);
        searchBar = findViewById(R.id.searchBar);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);
        confirmBtn = findViewById(R.id.confirmBtn);

        userProfileImage = findViewById(R.id.userProfileImage);
        userProfileImageSlected = findViewById(R.id.userProfileImageSlected);
        userProfileImageSlected.setVisibility(View.GONE);
        preferencesCheckRecyclerView = findViewById(R.id.preferencesCheckRecyclerView);
        preferencesCheckRecyclerView.setLayoutManager(new LinearLayoutManager(ProfileInfoActivity.this));


        ItemAddOns category = new ItemAddOns();
        category.setId("1");
        category.setIdForCheck(1);
        category.setName("Veg");
        category.setSelected(false);

        ItemAddOns category2 = new ItemAddOns();
        category2.setId("2");
        category2.setIdForCheck(2);
        category2.setName("NonVeg");
        category2.setSelected(false);


        ItemAddOns category3 = new ItemAddOns();
        category3.setId("3");
        category3.setIdForCheck(3);
        category3.setName("Eggterian");
        category3.setSelected(false);

        ItemAddOns category4 = new ItemAddOns();
        category4.setId("4");
        category4.setIdForCheck(4);
        category4.setName("Jain");
        category4.setSelected(false);


        categoryList.add(category);
        categoryList.add(category2);
        categoryList.add(category3);
        categoryList.add(category4);


        radioGroup = findViewById(R.id.radioGroup);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        searchBar.setVisibility(View.GONE);
        topRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ProfileInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProfileInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }

                if (ContextCompat.checkSelfPermission(ProfileInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProfileInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(ProfileInfoActivity.this,
                            WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(ProfileInfoActivity.this,
                            READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(ProfileInfoActivity.this,
                                new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }

            }
        });


        userProfileImageSlected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ProfileInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProfileInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }

                if (ContextCompat.checkSelfPermission(ProfileInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProfileInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(ProfileInfoActivity.this,
                            WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(ProfileInfoActivity.this,
                            READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(ProfileInfoActivity.this,
                                new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }

            }
        });

        getUserInfo();


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!male.isChecked() && !female.isChecked()) {
                    Toast.makeText(ProfileInfoActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                } else if (selectedFoodPreferencesList.size() == 0) {
                    Toast.makeText(ProfileInfoActivity.this, "Please select Food Preferences", Toast.LENGTH_SHORT).show();
                } else {
                    if (filetoUploadinServer != null) {
                        uploadImage(filePath);
                    } else uploadWithoutImage();
                }

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.male) {
                    selectedGenderTypes = "Male";
                } else {
                    selectedGenderTypes = "Female";
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.v("permission", requestCode + " request Code");
        if (requestCode == 1) {
            if (!(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            }
//            if (permissions.length > 0 && grantResults.length > 0) {
//
//                boolean flag = true;
//                for (int i = 0; i < grantResults.length; i++) {
//                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                        flag = false;
//                    }
//                }
//                if (flag) {
//                    Log.v("permission", requestCode + " request Code");
//                    getUserInfo();
//
//                } else {
//
//                }
//
//            } else {
//
//            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * Get logged in user information (GET method).
     **/
    public void getUserInfo() {
        String url = APIBaseURL.checkUserExistsOrNot + SaveSharedPreference.getLoggedInUserEmail(ProfileInfoActivity.this);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);

                List<ChefDetailHeader> chefDetailHeaderList = new ArrayList<>();

                ChefDetailHeader chefDetailHeader = new ChefDetailHeader();
                chefDetailHeader.setTitle("Account Info");

                chefDetailHeaderList.add(chefDetailHeader);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    List<Object> objectList = new ArrayList<>();

                    AccountInfoHeaders accountInfoHeaders = new AccountInfoHeaders();
                    accountInfoHeaders.setUserName(dataObject.optString("name"));
                    accountInfoHeaders.setUserEmail(dataObject.getJSONArray("emailId").getString(0));
                    accountInfoHeaders.setUserImage(dataObject.optString("profilePic"));




                    if (dataObject.getJSONArray("emailId").length() != 0) {
                        email.setText(dataObject.getJSONArray("emailId").getString(0).trim());
                    }

                    name.setText(dataObject.optString("name").trim());
                    if (dataObject.getJSONArray("phoneNo").length() != 0) {

                        phoneNumber.setText(dataObject.getJSONArray("phoneNo").getString(0).replace("+91", "").trim());

                    }


                    try {
                        Glide.with(ProfileInfoActivity.this).load(dataObject.optString("profilePic")).placeholder(R.drawable.customer_user_profile_left_menu).into(userProfileImage);

                        fileImageURLFromServer = dataObject.optString("profilePic");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (dataObject.optString("gender").equals("Male")) {
                        selectedGenderTypes = "Male";
                        male.setChecked(true);
                    } else if (dataObject.optString("gender").equals("Female")) {
                        selectedGenderTypes = "Female";
                        female.setChecked(true);
                    }


                    JSONArray foodPreferenceArray = new JSONArray();
                    if (dataObject.has("foodPreference")) {
                        foodPreferenceArray = dataObject.getJSONArray("foodPreference");
                    }


                    if (foodPreferenceArray.length() != 0) {
                        Boolean selected = false;
                        ArrayList<String> stringList = new ArrayList<>();
                        selectedPreviousesLists = new ArrayList<>();
                        for (int i = 0; i < foodPreferenceArray.length(); i++) {


                            getValues = foodPreferenceArray.get(i);

                            List<ItemAddOns> checkedCategoryLists = new ArrayList<>();
                            for (int j = 0; j < categoryList.size(); j++) {
                                ItemAddOns itemAddOns = new ItemAddOns();
                                itemAddOns.setIdForCheck(categoryList.get(j).getIdForCheck());
                                itemAddOns.setName(categoryList.get(j).getName());
                                if (getValues.equals(categoryList.get(j).getIdForCheck())) {
                                    String finalResultNamesForDishTypes = categoryList.get(j).getName();
                                    stringList.add(finalResultNamesForDishTypes);
                                    itemAddOns.setSelected(true);
                                    selected = true;
                                }


                                checkedCategoryLists.add(itemAddOns);
                            }

                            selectedPreviousesLists = checkedCategoryLists;


                        }
                        List<ItemAddOns> categoryListRecheck = new ArrayList<>();

                        for (int j = 0; j < categoryList.size() /*&& j < selectedPreviousesLists.size()*/; j++) {
                            ItemAddOns category5 = new ItemAddOns();
                            category5.setIdForCheck(categoryList.get(j).getIdForCheck());
                            category5.setId(categoryList.get(j).getId());
                            category5.setName(categoryList.get(j).getName());
                            if (stringList.contains(categoryList.get(j).getName())) {
                                category5.setSelected(true);

                            } else {
                                category5.setSelected(false);
                            }

                            selectedFoodPreferencesList.add(category5);
                            categoryListRecheck.add(category5);
                        }

                        preferencesCheckRecyclerView.setAdapter(new ConsumerPreferencesAdapter(ProfileInfoActivity.this, categoryListRecheck));


                    } else {
                        preferencesCheckRecyclerView.setAdapter(new ConsumerPreferencesAdapter(ProfileInfoActivity.this, categoryList));
                    }

                    objectList.add(accountInfoHeaders);

                    bottomRecyclerView.setAdapter(new InfoListsAdapter(ProfileInfoActivity.this, objectList, topRecyclerView, poppinsSemiBold, poppinsLight, poppinsMedium, poppinsBold, RobotoBold, RobotoRegular));
                    bottomRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!scrolled) {
                                enableScroll = false;
                                scrolled = true;
                                ((LinearLayoutManager) topRecyclerView.getLayoutManager()).scrollToPositionWithOffset(selectedposition, 0);
                                ((LinearLayoutManager) bottomRecyclerView.getLayoutManager()).scrollToPositionWithOffset(selectedposition, 0);
                            }
                        }
                    });


                    bottomRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (enableScroll) {

                                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                                if (scrolled) {
                                    selectedposition = linearLayoutManager.findFirstVisibleItemPosition();
                                }

                                ((LinearLayoutManager) topRecyclerView.getLayoutManager()).scrollToPositionWithOffset(selectedposition, 0);

                            } else {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enableScroll = true;
                                    }
                                }, 500);
                            }


                        }
                    });



                    send_location=dataObject.optString("location");
                    send_lat=dataObject.optString("lat");
                    send_lng=dataObject.optString("lng");
                    send_dob=dataObject.optString("dob");
                    send_addressLine1=dataObject.optString("addressLine1");
                    send_addressLine2=dataObject.optString("addressLine2");
                    send_pinCode=dataObject.optString("pinCode");
                    send_state=dataObject.optString("state");
                    send_country=dataObject.optString("country");
                    if (dataObject.getJSONArray("phoneNo").length() != 0) {
                         cloneJSONArray = new JSONArray(dataObject.getJSONArray("phoneNo").toString());
                    }
                    if (dataObject.getJSONArray("emailId").length() != 0) {
                        emailJSONArray = new JSONArray(dataObject.getJSONArray("emailId").toString());
                    }

                      send_profilePic=dataObject.optString("profilePic");
                      send_roleId=dataObject.optString("roleId");
                      send_roleName=dataObject.optString("roleName");
                      send_createdDate=dataObject.optString("createdDate");
                      send_status=dataObject.optString("status");
                   // boolean  send_isDeleted= Boolean.parseBoolean(dataObject.optString("isDeleted"));
                    //String  send_healthInfo= String.valueOf(dataObject.getJSONObject("healthInfo"));
                    if (dataObject.getJSONObject("healthInfo").length() != 0) {
                        //JSONObject cloneJSONObject = jsonObject.getJSONObject(send_healthInfo);
                         cloneJSONObject = new JSONObject(dataObject.getJSONObject("healthInfo").toString());
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
                    ViewGroup viewGroup = ProfileInfoActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ProfileInfoActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProfileInfoActivity.this);

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
                    Toast.makeText(ProfileInfoActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, ProfileInfoActivity.this);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();
                fileuploaded = new File(FilePath.getPath(this, imageUri));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                userProfileImage.setVisibility(View.GONE);
                userProfileImageSlected.setVisibility(View.VISIBLE);

                // BitmapFactory options to downsize the image
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                o.inSampleSize = 6;
                // factor of downsizing the image

                FileInputStream inputStream = null;

                inputStream = new FileInputStream(fileuploaded);
                //Bitmap selectedBitmap = null;
                BitmapFactory.decodeStream(inputStream, null, o);
                inputStream.close();

                // The new size we want to scale to
                final int REQUIRED_SIZE = 75;

                // Find the correct scale value. It should be the power of 2.
                int scale = 1;
                while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                        o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                    scale *= 2;
                }

                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                inputStream = new FileInputStream(fileuploaded);

                Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
                selectedBitmap=Bitmap.createScaledBitmap(selectedBitmap,250,250,true);
                try {
                    userProfileImageSlected.setImageBitmap(selectedBitmap);
                } catch (Exception e) {
                    userProfileImageSlected.setVisibility(View.GONE);
                    userProfileImage.setVisibility(View.VISIBLE);
                    Toast.makeText(ProfileInfoActivity.this, "Please choose a different image", Toast.LENGTH_SHORT).show();
                }
                inputStream.close();

                // here i override the original image file
                fileuploaded.createNewFile();
                if (ContextCompat.checkSelfPermission(
                        ProfileInfoActivity.this, WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    String extr = Environment.getExternalStorageDirectory().toString();
                    mFolder = new File(extr + "/FoodyHive");
                    if (!mFolder.exists()) {
                        mFolder.mkdir();
                    }
                    File f = new File(mFolder.getAbsolutePath(), System.currentTimeMillis() + "profile.png");
                    Log.v("extrPath", mFolder.getAbsolutePath());
                    strMyImagePath = f.getAbsolutePath();

                    FileOutputStream outputStream = new FileOutputStream(f);

                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);


                    filetoUploadinServer = f;


                }



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ProfileInfoActivity.this, "Please choose a different image", Toast.LENGTH_SHORT).show();
            }

        }
    }


    /**
     * multipart upload method with user information **with the image path**
     **/
    private void uploadImage(String filePath) {

        ProgressDialog progressDialog = new ProgressDialog(ProfileInfoActivity.this);
        progressDialog.setMessage("Updating your details, Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        JSONObject inputObject = new JSONObject();

        try {
//            JSONArray emailArray = new JSONArray();
//            emailArray.put(email.getText().toString().trim());
            try {
                inputObject.put("emailId", emailJSONArray);
                inputObject.put("name", name.getText().toString().trim());
                inputObject.put("location", send_location);
                inputObject.put("lat", send_lat);
                inputObject.put("lng", send_lng);
                inputObject.put("dob", send_dob);
                inputObject.put("addressLine1", send_addressLine1);
                inputObject.put("addressLine2", send_addressLine2);
                inputObject.put("pinCode", send_pinCode);
                inputObject.put("state", send_state);
                inputObject.put("country", send_country);
                inputObject.put("id", SaveSharedPreference.getLoggedInUserID(ProfileInfoActivity.this));
                inputObject.put("gender", selectedGenderTypes);
                JSONArray foodPreferencesArray = new JSONArray();
                for (int i = 0; i < selectedFoodPreferencesList.size(); i++) {
                    if (selectedFoodPreferencesList.get(i).getSelected()) {
                        foodPreferencesArray.put(selectedFoodPreferencesList.get(i).getIdForCheck());
                    }

                }
                inputObject.put("foodPreference", foodPreferencesArray);
                //JSONArray phoneNoArray = new JSONArray();
                //phoneNoArray.put(phoneNumber.getText().toString().trim());
                inputObject.put("phoneNo", cloneJSONArray);


                inputObject.put("roleId", send_roleId);
                inputObject.put("roleName", send_roleName);
                inputObject.put("createdDate", send_createdDate);
                inputObject.put("status", send_status);
                //inputObject.put("isDeleted", false);
//                JSONObject healthInfoObject = new JSONObject();
//                healthInfoObject.put("height", "");
//                healthInfoObject.put("weight", "");
                inputObject.put("healthInfo", cloneJSONObject);
                inputObject.put("profilePic", "");



            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(ProfileInfoActivity.this, uploadId, ROOT_URL)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(ProfileInfoActivity.this))
                    .setMethod("PUT")
                    .addParameter("request", inputObject.toString())
                    .addParameter("roleId", send_roleId)
                    .addParameter("userid", SaveSharedPreference.getLoggedInUserID(ProfileInfoActivity.this))
                    .addFileToUpload(filetoUploadinServer.getAbsolutePath(), "ProfilePic")

                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();


                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                            onBackPressed();
                        }


                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                        }
                    })
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
        }
    }


    /**
     * multipart upload method with user information **without the image path** passing the url for image path when it already exists.
     **/
    private void uploadWithoutImage() {

        ProgressDialog progressDialog = new ProgressDialog(ProfileInfoActivity.this);
        progressDialog.setMessage("Updating your details, Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        JSONObject inputObject = new JSONObject();

        try {
//            JSONArray emailArray = new JSONArray();
//            emailArray.put(email.getText().toString().trim());
            try {
                inputObject.put("emailId", emailJSONArray);
                inputObject.put("name", name.getText().toString().trim());
                inputObject.put("location", send_location);
                inputObject.put("lat", send_lat);
                inputObject.put("lng", send_location);
                inputObject.put("dob", send_dob);
                inputObject.put("addressLine1", send_addressLine1);
                inputObject.put("addressLine2", send_addressLine2);
                inputObject.put("pinCode", send_pinCode);
                inputObject.put("state", send_state);
                inputObject.put("country", send_country);
                inputObject.put("id", SaveSharedPreference.getLoggedInUserID(ProfileInfoActivity.this));
                inputObject.put("gender", selectedGenderTypes);
                JSONArray foodPreferencesArray = new JSONArray();
                for (int i = 0; i < selectedFoodPreferencesList.size(); i++) {
                    if (selectedFoodPreferencesList.get(i).getSelected()) {
                        foodPreferencesArray.put(selectedFoodPreferencesList.get(i).getIdForCheck());
                    }

                }
                inputObject.put("foodPreference", foodPreferencesArray);
//                JSONArray phoneNoArray = new JSONArray();
//                phoneNoArray.put(phoneNumber.getText().toString().trim());
                inputObject.put("phoneNo", cloneJSONArray);

                inputObject.put("roleId", send_roleId);
                inputObject.put("roleName", send_roleName);
                inputObject.put("createdDate", send_createdDate);
                inputObject.put("status", send_status);
                //inputObject.put("isDeleted", false);
//                JSONObject healthInfoObject = new JSONObject();
//                healthInfoObject.put("height", "");
//                healthInfoObject.put("weight", "");
                inputObject.put("healthInfo", cloneJSONObject);
                inputObject.put("profilePic", fileImageURLFromServer);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(ProfileInfoActivity.this, uploadId, ROOT_URL)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(ProfileInfoActivity.this))
                    .setMethod("PUT")
                    .addParameter("request", inputObject.toString())
                    .addParameter("roleId", send_roleId)
                    .addParameter("userid", SaveSharedPreference.getLoggedInUserID(ProfileInfoActivity.this))
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                            onBackPressed();
                        }


                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                        }
                    })
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
        }
    }


    /**
     * Display default gallery on the device
     **/
    private void showFileChooser() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        output = new File(dir, System.currentTimeMillis() / 1000 + "profile" + ".jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_IMAGE_REQUEST);
    }

    /**
     * get file path of choosen image.
     **/
    private String getFilePath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        String res = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return res;
    }

    /**
     * delete temp folder of image after compression
     **/
    public void deleteTempImageFolder() {
        try {
            if (mFolder.exists() && mFolder.isDirectory()) {
                File path = mFolder.getAbsoluteFile();
                String[] entries = path.list();
                for (String s : entries) {
                    File currentFile = new File(path.getPath(), s);
                    currentFile.delete();
                }
                mFolder.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}