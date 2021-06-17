package stayabode.foodyHive.activities.consumers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 Address Activity Created On ...
 **/

public class Address extends AppCompatActivity implements  OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
         GoogleApiClient.OnConnectionFailedListener,
         LocationListener {

    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface poppinsMedium;
    Typeface poppinsLight;

    EditText landLineET;
    EditText mobileNumberET;
    EditText cityET;
    EditText addressET;
    EditText stateET;
    EditText nameET;
    EditText areaET;
    EditText countryET;
    EditText pinCodeET;
    EditText emailET;

    Button saveBtn;
    FrameLayout framelayout_id;

     private static String TAG = "MapsActivity";
     private GoogleMap mMap;
     private Marker mCurrLocationMarker;
     EditText address_to_latlog_edittext;
     Button address_to_latlog;
     TextView address_to_latlog_textview;
     TextView dragable_lat_lon_txt;
     Button getcurrent_location_id,continune_address;
     Button draw_rout_multi;
     TextView mapresul;



     public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
     GoogleApiClient mGoogleApiClient;
     Location mLastLocation;
     //Marker mCurrLocationMarker;
     LocationRequest mLocationRequest;
     //private GoogleMap mMap;

     boolean currentLocation_button_is_clickable=false;
     private FusedLocationProviderClient mFusedLocationProviderClient;

     String global_lat="";
     String global_long="";
     String areaET_compare="";
     String addressET_compare="";
     String pinCodeET_compare="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_address);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Address");



        landLineET = findViewById(R.id.landLineET);
        mobileNumberET = findViewById(R.id.mobileNumberET);
        cityET = findViewById(R.id.cityET);
        addressET = findViewById(R.id.addressET);
        stateET = findViewById(R.id.stateET);
        nameET = findViewById(R.id.nameET);
        areaET = findViewById(R.id.areaET);
        countryET = findViewById(R.id.countryET);
        pinCodeET = findViewById(R.id.pinCodeET);
        saveBtn = findViewById(R.id.saveBtn);
        getcurrent_location_id = findViewById(R.id.getcurrent_location_id);
        emailET = findViewById(R.id.emailET);

        saveBtn.setTypeface(poppinsBold);
        areaET.setTypeface(poppinsMedium);
        nameET.setTypeface(poppinsMedium);
        addressET.setTypeface(poppinsMedium);
        pinCodeET.setTypeface(poppinsMedium);
        cityET.setTypeface(poppinsMedium);
        mobileNumberET.setTypeface(poppinsMedium);
        landLineET.setTypeface(poppinsMedium);


        saveBtn.setTypeface(poppinsBold);

        if (getIntent().getStringExtra("AddorEdit").equals("Edit")) {
            areaET.setText(getIntent().getStringExtra("AddressLine1"));
            mobileNumberET.setText(getIntent().getStringExtra("ContactNumber"));
            addressET.setText(getIntent().getStringExtra("AddressLine2"));
            nameET.setText(getIntent().getStringExtra("Name"));
            emailET.setText(getIntent().getStringExtra("emailId"));
            pinCodeET.setText(getIntent().getStringExtra("pinCode"));
            landLineET.setText(getIntent().getStringExtra("landmark"));


            global_lat=getIntent().getStringExtra("latitude");
            global_long=getIntent().getStringExtra("longtitude");

             areaET_compare=(getIntent().getStringExtra("AddressLine1")).trim();
             addressET_compare=(getIntent().getStringExtra("AddressLine2")).trim();
             pinCodeET_compare=(getIntent().getStringExtra("pinCode")).trim();


            saveBtn.setText("Update Address");
        } else {
            emailET.setText(getIntent().getStringExtra("emailId"));
            saveBtn.setText("Save");

        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("AddorEdit").equals("Add")) {
                    try {
                        addAddress();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        updateAddress();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Address.this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        getcurrent_location_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                }else
                {
                    getDeviceLocation();
                }

                currentLocation_button_is_clickable=true;


            }
        });




        framelayout_id=findViewById(R.id.framelayout_id);
        address_to_latlog=findViewById(R.id.address_to_latlog);
        address_to_latlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = nameET.getText().toString().trim();
                String area = areaET.getText().toString().trim();
                String address = addressET.getText().toString().trim();
                String pincode = pinCodeET.getText().toString().trim();
                String mobilenumber = mobileNumberET.getText().toString().trim();
                if (validate(name, area, address, pincode, mobilenumber)) {

                    framelayout_id.setVisibility(View.VISIBLE);

                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                    }
                    String full_address=areaET.getText().toString()+" "+addressET.getText().toString()+" "+cityET.getText().toString()+" "+stateET.getText().toString()+" "+countryET.getText().toString()+" "+pinCodeET.getText().toString();


                    LatLng latlng_from_address = null;
                    if(areaET_compare.equals(area)&&addressET_compare.equals(address)&&pinCodeET_compare.equals(pincode))
                    {

                        latlng_from_address = new LatLng(Double.parseDouble(global_lat), Double.parseDouble(global_long));
                      //  Toast.makeText(getApplicationContext(),"Address old To Lat to Lon"+global_lat+" "+global_long,Toast.LENGTH_SHORT).show();



                    }else
                    {
                         latlng_from_address= getLocationFromAddress(Address.this,full_address);
                      //  Toast.makeText(getApplicationContext(),"Address new To Lat to Lon"+global_lat+" "+global_long,Toast.LENGTH_SHORT).show();


                    }


                    mCurrLocationMarker = mMap.addMarker(new MarkerOptions().position(latlng_from_address).title("Your Location").draggable(true));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng_from_address));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18));



//                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                        @Override
//                        public void onMapClick(LatLng latLng) {
//
//                            if (mCurrLocationMarker != null) {
//                                mCurrLocationMarker.remove();
//                            }
//                            mCurrLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//
//                        }});

                    mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker arg0) {
                            // TODO Auto-generated method stub
                            Log.d("System out", "onMarkerDragStart..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
                        }

                        @SuppressWarnings("unchecked")
                        @Override
                        public void onMarkerDragEnd(Marker arg0) {
                            // TODO Auto-generated method stub
                            Log.d("System out", "onMarkerDragEnd..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);

                           // latlng_from_address = new LatLng(Double.parseDouble(global_lat), Double.parseDouble(global_long));
                            global_lat= String.valueOf(arg0.getPosition().latitude);
                            global_long= String.valueOf(arg0.getPosition().longitude);
                            LatLng latLng_move = new LatLng(Double.parseDouble(global_lat), Double.parseDouble(global_long));


                            if (mCurrLocationMarker != null) {
                                mCurrLocationMarker.remove();
                            }
                            mCurrLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng_move).title("Your Location").draggable(true));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng_move));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

                           // Toast.makeText(getApplicationContext(),"Address Drag  Lat to Lon"+global_lat+" "+global_long,Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onMarkerDrag(Marker arg0) {
                            // TODO Auto-generated method stub
                            Log.i("System out", "onMarkerDrag...");
                        }
                    });

                }



                //address_to_latlog_textview.setText("   "+latlng_from_address);
            }
        });

    }


    /**
     To Back Press from the Top Toolbar back Arrow
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                onBackPressed();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     To add Address for Consumer(POST)
     **/
    public void addAddress() throws JSONException {
        String url = APIBaseURL.addDeliveryAddress;

        String name = nameET.getText().toString().trim();
        String area = areaET.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        String pincode = pinCodeET.getText().toString().trim();
        String mobilenumber = mobileNumberET.getText().toString().trim();
        if (validate(name, area, address, pincode, mobilenumber)) {
            JSONObject inputObject = new JSONObject();
            inputObject.put("consumerEmailId", SaveSharedPreference.getLoggedInUserEmail(Address.this));
            inputObject.put("name", nameET.getText().toString());
            inputObject.put("streetAddress1", areaET.getText().toString());
            inputObject.put("streetAddress2", addressET.getText().toString());
            inputObject.put("city", cityET.getText().toString());
            inputObject.put("state", stateET.getText().toString());
            inputObject.put("country", countryET.getText().toString());
            inputObject.put("pincode", pinCodeET.getText().toString());
            inputObject.put("mobileNumber", mobileNumberET.getText().toString());
            inputObject.put("latitude", global_lat);
            inputObject.put("longtitude", global_long);
            inputObject.put("landmark", landLineET.getText().toString());

          //  Toast.makeText(getApplicationContext(),"Add Address "+global_lat+" "+global_long,Toast.LENGTH_SHORT).show();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(Address.this, "Delivery Address Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("MESSAGE", "Added");
                    setResult(101, intent);
                    finish();
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
                            Toast.makeText(Address.this, "Address Field should not contain #,Invalid address", Toast.LENGTH_SHORT).show();
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
                        ViewGroup viewGroup = Address.this.findViewById(android.R.id.content);

                        //then we will inflate the custom alert dialog xml that we created
                        View dialogView = LayoutInflater.from(Address.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                        Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                        ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                        //Now we need an AlertDialog.Builder object
                        AlertDialog.Builder builder = new AlertDialog.Builder(Address.this);

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
                        Toast.makeText(Address.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(Address.this));
                    return headers;
                }
            };
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "add_address_taq");
        }


    }


    /**
     To update the Address for Consumer(UPDATE)
     **/
    public void updateAddress() throws JSONException {
        String url = APIBaseURL.updateAddress + getIntent().getStringExtra("Id");

        String name = nameET.getText().toString().trim();


        String area = areaET.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        String pincode = pinCodeET.getText().toString().trim();


        String mobilenumber = mobileNumberET.getText().toString().trim();
        if (validate(name, area, address, pincode, mobilenumber)) {
            JSONObject inputObject = new JSONObject();
            inputObject.put("consumerEmailId", SaveSharedPreference.getLoggedInUserEmail(Address.this));
            inputObject.put("name", nameET.getText().toString());
            inputObject.put("streetAddress1", areaET.getText().toString());
            inputObject.put("streetAddress2", addressET.getText().toString());
            inputObject.put("city", cityET.getText().toString());
            inputObject.put("state", stateET.getText().toString());
            inputObject.put("country", countryET.getText().toString());
            inputObject.put("pincode", pinCodeET.getText().toString());
            inputObject.put("mobileNumber", mobileNumberET.getText().toString());
            inputObject.put("latitude", global_lat);
            inputObject.put("longtitude", global_long);
            inputObject.put("landmark", landLineET.getText().toString());

           // Toast.makeText(getApplicationContext(),"Update Address "+global_lat+" "+global_long,Toast.LENGTH_SHORT).show();


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, url, inputObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(Address.this, "Delivery Address Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("MESSAGE", "Edited");
                    setResult(102, intent);
                    finish();
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
                            Toast.makeText(Address.this, "Address Field should not contain #,Invalid address", Toast.LENGTH_SHORT).show();
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
                        ViewGroup viewGroup = Address.this.findViewById(android.R.id.content);

                        //then we will inflate the custom alert dialog xml that we created
                        View dialogView = LayoutInflater.from(Address.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                        Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                        ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                        //Now we need an AlertDialog.Builder object
                        AlertDialog.Builder builder = new AlertDialog.Builder(Address.this);

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
                        Toast.makeText(Address.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(Address.this));
                    return headers;
                }
            };
            ApplicationController.getInstance().addToRequestQueue(stringRequest, "edit_address_taq");
        }


    }





    /**
     To Check Valid Mobile Number or Not
     **/
    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }


    /**
     Validate All Edit Text Fields
     **/
    private boolean validate(String name, String area, String address, String pincode, String phone) {
        if (name.equals(null) || name.equals("") || !isValidMobile(phone) || area.equals(null) || area.equals("") || address.equals(null) || address.equals("") || pincode.equals(null) || pincode.equals("")) {

            if (name.equals(null) || name.equals("")) {
                nameET.setError("Enter a valid Name");
            } else {
                nameET.setError(null);
            }
            if (!isValidMobile(phone)) {
                mobileNumberET.setError("Enter a valid Mobile Number");
            } else {
                mobileNumberET.setError(null);
            }
            if (area.equals(null) || area.equals("")) {
                areaET.setError("Address cannot be empty");
            } else {
                areaET.setError(null);
            }
            if (address.equals(null) || address.equals("")) {
                addressET.setError("Address cannot be empty");
            } else {
                addressET.setError(null);
            }
            if (pincode.equals(null) || pincode.equals("")) {
                pinCodeET.setError("Pincode cannot be empty");
            } else {
                pinCodeET.setError(null);
            }
            return false;
        } else
            return true;
    }










     public LatLng getLocationFromAddress(Context context, String strAddress) {

         Geocoder coder = new Geocoder(context);
         List<android.location.Address> address;
         LatLng p1 = null;

         try {
             // May throw an IOException
             address = coder.getFromLocationName(strAddress, 1);
             if (address == null) {
                 return null;
             }

             android.location.Address location = address.get(0);
             p1 = new LatLng(location.getLatitude(), location.getLongitude() );

             global_lat= String.valueOf(location.getLatitude());
             global_long=String.valueOf(location.getLongitude());

         } catch (IOException ex) {

             ex.printStackTrace();
         }

         return p1;
     }







     private void buildAlertMessageNoGps() {
         final AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                 .setCancelable(false)
                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     public void onClick(final DialogInterface dialog, final int id) {
                         startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                     }
                 })
                 .setNegativeButton("No", new DialogInterface.OnClickListener() {
                     public void onClick(final DialogInterface dialog, final int id) {
                         dialog.cancel();
                     }
                 });
         final AlertDialog alert = builder.create();
         alert.show();
     }

     @Override
     public void onMapReady(GoogleMap googleMap) {
         mMap = googleMap;
         mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
         mMap.getUiSettings().setZoomControlsEnabled(true);
         mMap.getUiSettings().setZoomGesturesEnabled(true);
         mMap.getUiSettings().setCompassEnabled(true);
         //Initialize Google Play Services
         if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             if (ContextCompat.checkSelfPermission(this,
                     Manifest.permission.ACCESS_FINE_LOCATION)
                     == PackageManager.PERMISSION_GRANTED) {
                 buildGoogleApiClient();
                 mMap.setMyLocationEnabled(true);
             }
         } else {
             buildGoogleApiClient();
             mMap.setMyLocationEnabled(true);
         }




//        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.addMarker(new MarkerOptions()
//                .position(origin)
//                .title("LinkedIn")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(dest));
//
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));


     }
     protected synchronized void buildGoogleApiClient() {
         mGoogleApiClient = new GoogleApiClient.Builder(this)
                 .addConnectionCallbacks(this)
                 .addOnConnectionFailedListener(this)
                 .addApi(LocationServices.API)
                 .build();
         mGoogleApiClient.connect();
     }

     @Override
     public void onConnected(Bundle bundle) {
         mLocationRequest = new LocationRequest();
         mLocationRequest.setInterval(1000);
         mLocationRequest.setFastestInterval(1000);
         mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
         if (ContextCompat.checkSelfPermission(this,
                 Manifest.permission.ACCESS_FINE_LOCATION)
                 == PackageManager.PERMISSION_GRANTED) {
             LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                     mLocationRequest, this);
         }
     }
     @Override
     public void onConnectionSuspended(int i) {
     }
     @Override
     public void onLocationChanged(Location location) {

         if(currentLocation_button_is_clickable) {

             // mLastLocation = location;
             if (mCurrLocationMarker != null) {
                 mCurrLocationMarker.remove();
             }
//Showing Current Location Marker on Map
             global_lat= String.valueOf(location.getLatitude());
             global_long= String.valueOf(location.getLongitude());

             LatLng latLng = new LatLng(Double.parseDouble(global_lat),Double.parseDouble( global_long));
             MarkerOptions markerOptions = new MarkerOptions();
             markerOptions.position(latLng);
             LocationManager locationManager = (LocationManager)
                     getSystemService(Context.LOCATION_SERVICE);
             String provider = locationManager.getBestProvider(new Criteria(), true);
             if (ActivityCompat.checkSelfPermission(this,
                     Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                     ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                             != PackageManager.PERMISSION_GRANTED) {
                 return;
             }
             Location locations = locationManager.getLastKnownLocation(provider);
             List<String> providerList = locationManager.getAllProviders();
             if (null != locations && null != providerList && providerList.size() > 0) {
                 double longitude = locations.getLongitude();
                 double latitude = locations.getLatitude();
                 Geocoder geocoder = new Geocoder(getApplicationContext(),
                         Locale.getDefault());
                 try {
                     List<android.location.Address> listAddresses = geocoder.getFromLocation(latitude,
                             longitude, 1);
                     if (null != listAddresses && listAddresses.size() > 0) {
                         String state = listAddresses.get(0).getAdminArea();
                         String country = listAddresses.get(0).getCountryName();
                         String subLocality = listAddresses.get(0).getSubLocality();
                         markerOptions.title("" + latLng + "," + subLocality + "," + state
                                 + "," + country);
                     }
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
             mCurrLocationMarker = mMap.addMarker(markerOptions);
             mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
             mMap.animateCamera(CameraUpdateFactory.zoomTo(18));


            // Toast.makeText(getApplicationContext(),"current location activate Drag  Lat to Lon",Toast.LENGTH_SHORT).show();
            // Toast.makeText(getApplicationContext(),"current location activate"+global_lat+" "+global_long,Toast.LENGTH_SHORT).show();


             //dragable_lat_lon_txt.setText(""+latLng);
//            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//
//                    if (mCurrLocationMarker != null) {
//                        mCurrLocationMarker.remove();
//                    }
//                    mCurrLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
//                    getcurrent_location_id.setText(""+latLng);
//                }});





             if (mGoogleApiClient != null) {
                 LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                         this);
             }
         }



     }
     @Override
     public void onConnectionFailed(ConnectionResult connectionResult) {
     }
     public boolean checkLocationPermission() {
         if (ContextCompat.checkSelfPermission(this,
                 Manifest.permission.ACCESS_FINE_LOCATION)
                 != PackageManager.PERMISSION_GRANTED) {

             if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                     Manifest.permission.ACCESS_FINE_LOCATION)) {
                 ActivityCompat.requestPermissions(this,
                         new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                         MY_PERMISSIONS_REQUEST_LOCATION);
             } else {
                 ActivityCompat.requestPermissions(this,
                         new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                         MY_PERMISSIONS_REQUEST_LOCATION);
             }
             return false;
         } else {
             return true;
         }
     }
     @Override
     public void onRequestPermissionsResult(int requestCode,
                                            String permissions[], int[] grantResults) {
         switch (requestCode) {
             case MY_PERMISSIONS_REQUEST_LOCATION: {
                 if (grantResults.length > 0
                         && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     if (ContextCompat.checkSelfPermission(this,
                             Manifest.permission.ACCESS_FINE_LOCATION)
                             == PackageManager.PERMISSION_GRANTED) {
                         if (mGoogleApiClient == null) {
                             buildGoogleApiClient();
                         }
                         mMap.setMyLocationEnabled(true);
                     }
                 } else {
                     Toast.makeText(this, "permission denied",
                             Toast.LENGTH_LONG).show();
                 }
                 return;
             }
         }
     }



     private void getDeviceLocation() {
         try {
             if (currentLocation_button_is_clickable) {
                 Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                 locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                     @Override
                     public void onComplete(@NonNull Task<Location> task) {
                         if (task.isSuccessful()) {
                             // Set the map's camera position to the current location of the device.
                             Location location = task.getResult();
                             global_lat= String.valueOf(location.getLatitude());
                             global_long= String.valueOf(location.getLongitude());

                             LatLng currentLatLng = new LatLng(Double.parseDouble(global_lat), Double.parseDouble(global_long));



                             if (mCurrLocationMarker != null) {
                                 mCurrLocationMarker.remove();
                             }
                             MarkerOptions markerOptions = new MarkerOptions();
                             markerOptions.position(currentLatLng);

                             markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                             mCurrLocationMarker = mMap.addMarker(markerOptions);
                             mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                             mMap.animateCamera(CameraUpdateFactory.zoomTo(18));


                            // Toast.makeText(getApplicationContext(),"current location after click "+global_lat+" "+global_long,Toast.LENGTH_SHORT).show();
                             //dragable_lat_lon_txt.setText(""+currentLatLng);


                         }
                     }
                 });
             }
         } catch (SecurityException e) {
             Log.e("Exception: %s", e.getMessage());
         }
     }





     @Override
     public void onBackPressed() {
         //Execute your code here
         if (framelayout_id.getVisibility() == View.VISIBLE) {
             // Its visible
             framelayout_id.setVisibility(View.GONE);
         } else {
             // Either gone or invisible
             finish();
         }

     }



 }
