package stayabode.foodyHive.activities.consumers;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.SearchPlaceAdapter;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Search;
import stayabode.foodyHive.models.SearchLocation;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocationSelectionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerViewplace;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    Typeface poppinsLight;
    Typeface poppinsBold;
    Typeface RobotoLight;
    Typeface RobotoBold;


    TextView logoLeftText;
    TextView logoRightText;
    TextView logoBottomText;
    TextView mainText;
    TextView mainSubText;
    TextView subOneText;
    TextView subText;
    TextView letsgotext;
    TextView howworksText;
    TextView pointOneText;
    TextView pointTwoText;
    TextView pointThreeText;
    TextView pointFourText;
    LinearLayout letsGoLayout;
    public static AutoCompleteTextView searchText;

    private static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;
    public static String latitude, longitude;
    private String address1, address2, city, state, country, county, PIN;
    ImageView getLocation;
    public static LinearLayout places_list_rL;
    public static RecyclerView mAutoCompleteList;
    List<Search> stringArrayList= new ArrayList<>();

    Location location;

    public static LocationSelectionActivity locationSelectionActivity;
    Boolean isCheckAddress = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // making activity full screen
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_location_selection);
        locationSelectionActivity = this;
        // hide action bar you can use NoAction theme as well
        getSupportActionBar().hide();


        if (ContextCompat.checkSelfPermission(LocationSelectionActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(LocationSelectionActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(LocationSelectionActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                String location_context = Context.LOCATION_SERVICE;
                locationManager = (LocationManager) getSystemService(location_context);
                List<String> providers = locationManager.getProviders(true);
                for (String provider : providers) {
                    locationManager.requestLocationUpdates(provider, 1000, 0,
                            new LocationListener() {

                                public void onLocationChanged(Location location) {}

                                public void onProviderDisabled(String provider) {}

                                public void onProviderEnabled(String provider) {}

                                public void onStatusChanged(String provider, int status,
                                                            Bundle extras) {}
                            });
                    location = locationManager.getLastKnownLocation(provider);
                }

                getLocationAddress();

            }else{

                ActivityCompat.requestPermissions(LocationSelectionActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                String location_context = Context.LOCATION_SERVICE;
                locationManager = (LocationManager) getSystemService(location_context);
                List<String> providers = locationManager.getProviders(true);
                for (String provider : providers) {
                    locationManager.requestLocationUpdates(provider, 1000, 0,
                            new LocationListener() {

                                public void onLocationChanged(Location location) {}

                                public void onProviderDisabled(String provider) {}

                                public void onProviderEnabled(String provider) {}

                                public void onStatusChanged(String provider, int status,
                                                            Bundle extras) {}
                            });
                    location = locationManager.getLastKnownLocation(provider);
                }

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)&&!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    OnGPS();
                }

            }
        }
        else
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER )&&!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                OnGPS();
            }
        }
        // bind views
        fontBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        RobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        RobotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        recyclerView = findViewById(R.id.recyclerView);
        logoLeftText = findViewById(R.id.logoLeftText);
        logoRightText = findViewById(R.id.logoRightText);
        logoBottomText = findViewById(R.id.logoBottomText);
        mainText = findViewById(R.id.mainText);
        mainSubText = findViewById(R.id.mainSubText);
        subText = findViewById(R.id.subText);
        subOneText = findViewById(R.id.subOneText);
        searchText = findViewById(R.id.searchText);
        letsgotext = findViewById(R.id.letsgotext);
        howworksText = findViewById(R.id.howworksText);
        pointOneText = findViewById(R.id.pointOneText);
        pointTwoText = findViewById(R.id.pointTwoText);
        pointThreeText = findViewById(R.id.pointThreeText);
        pointFourText = findViewById(R.id.pointFourText);
        letsGoLayout = findViewById(R.id.letsGoLayout);
        getLocation = findViewById(R.id.getLocation);

        pointOneText.setTypeface(fontRegular);
        pointTwoText.setTypeface(fontRegular);
        pointThreeText.setTypeface(fontRegular);
        pointFourText.setTypeface(fontRegular);
        logoLeftText.setTypeface(poppinsLight);
        logoRightText.setTypeface(poppinsLight);
        logoBottomText.setTypeface(poppinsLight);
        mainText.setTypeface(RobotoLight);
        mainSubText.setTypeface(RobotoBold);
        subText.setTypeface(fontRegular);
        subOneText.setTypeface(fontRegular);
        searchText.setTypeface(fontRegular);
        letsgotext.setTypeface(poppinsBold);
        howworksText.setTypeface(poppinsBold);

        mAutoCompleteList=(RecyclerView) findViewById(R.id.recyclerViewplace);
        places_list_rL=(LinearLayout) findViewById(R.id.places_list_rL);
        mAutoCompleteList.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteList.setVisibility(View.GONE);



        letsGoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkServiceAvailability();
                if(searchText.getText().toString().equals(""))
                {
                    Toast.makeText(LocationSelectionActivity.this, "Please Choose or Enter your Location", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        checkServiceAvailableorNotAvailable();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        // make status bar transparent
        changeStatusBarColor();




        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getLocationAddress();

            }
        });


        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                stringArrayList = new ArrayList<>();
                if(searchText.getText().toString().trim().equals(""))
                {
                    places_list_rL.setVisibility(View.GONE);
                    mAutoCompleteList.setVisibility(View.GONE);


                }
                else {
                    places_list_rL.setVisibility(View.VISIBLE);
                    mAutoCompleteList.setVisibility(View.VISIBLE);

                }

                /**
                 * To get the place names suggestions on user search basis
                 */

                ApplicationController.getInstance().getRequestQueue().cancelAll("volleyPlaces");

                mAutoCompleteList.setAdapter(new SearchPlaceAdapter(stringArrayList,LocationSelectionActivity.this,"location"));

                String url = APIBaseURL.getPlacesAddress+searchText.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                                JSONArray resultArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject resultObject = resultArray.getJSONObject(i);
                                    String value=resultArray.optString(i);

                                    Log.e("jsonSearch", i+"="+value);
                                    Search search = new Search();
                                    search.setAddress(resultObject.optString("description"));
                                    search.setName(resultObject.optString("description"));
                                    stringArrayList.add(search);
                                }

                                mAutoCompleteList.setAdapter(new SearchPlaceAdapter(stringArrayList,LocationSelectionActivity.this,"location"));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                ApplicationController.getInstance().addToRequestQueue(stringRequest,"volleyPlaces");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getLocations();

    }

    /**
     * Turn On GPS and go to settings
     */
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),121);
                dialog.dismiss();
                getLocationAddress();


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * Get User Current Location latitude and longitude
     */
    public void checkAddress()
    {




        if (location != null) {

            double lat = location.getLatitude();
            double longi = location.getLongitude();
            latitude = String.valueOf(lat);
            longitude = String.valueOf(longi);


            init();
            retrieveData(Double.valueOf(latitude),Double.valueOf(longitude));
            try {
                getAddress(Double.valueOf(latitude),Double.valueOf(longitude));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    /**
     * Get User Current Location latitude and longitude
     */


    private void getLocationAddress() {

        String location_context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 1000, 0,
                    new LocationListener() {

                        public void onLocationChanged(Location location) {}

                        public void onProviderDisabled(String provider) {}

                        public void onProviderEnabled(String provider) {}

                        public void onStatusChanged(String provider, int status,
                                                    Bundle extras) {}
                    });
            location = locationManager.getLastKnownLocation(provider);
        }

        if (ActivityCompat.checkSelfPermission(
                LocationSelectionActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                LocationSelectionActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            isCheckAddress = true;
            checkAddress();
            if(isCheckAddress)
            {
                checkAddress();
            }

        }
    }


    /**
     * Get Some Static Location which service is available in Bangalore
     */

    public void getLocations()
    {
        objectList = new ArrayList<>();

        SearchLocation searchLocation = new SearchLocation();
        searchLocation.setId("1");
        searchLocation.setName("Kammanahalli");
        searchLocation.setLatitude(13.0159);
        searchLocation.setLongitude(77.6379);

        SearchLocation searchLocation1 = new SearchLocation();
        searchLocation1.setId("2");
        searchLocation1.setName("Indira Nagar");
        searchLocation1.setLatitude(12.9784);
        searchLocation1.setLongitude(77.6408);


        SearchLocation searchLocation2 = new SearchLocation();
        searchLocation2.setId("3");
        searchLocation2.setName("HSR layout");
        searchLocation2.setLatitude(12.9121);
        searchLocation2.setLongitude(77.6446);


        SearchLocation searchLocation3 = new SearchLocation();
        searchLocation2.setId("4");
        searchLocation3.setName("Whitefield");
        searchLocation3.setLatitude(12.9698);
        searchLocation3.setLongitude(77.7500);
        objectList.add(searchLocation);
        objectList.add(searchLocation1);
        objectList.add(searchLocation2);
        objectList.add(searchLocation3);



        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new AllListAdapter(this,objectList,fontBold,fontRegular));

    }


    /**
     * Change status Bar color programmatically using this method
     */

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * init address methods
     */
    private void init() {
        address1 = "";
        address2 = "";
        city = "";
        state = "";
        country = "";
        county = "";
        PIN = "";
    }

    /**
     * Fetch data address by street,lat etc...
     */
    private void retrieveData(double latitude, double longitude) {
        try {
            String responseFromHttpUrl = getResponseFromHttpUrl(buildUrl(latitude, longitude));
            JSONObject jsonResponse = new JSONObject(responseFromHttpUrl);
            String status = jsonResponse.getString("status");

            if (status.equalsIgnoreCase("OK")) {
                JSONArray results = jsonResponse.getJSONArray("results");
                JSONObject zero = results.getJSONObject(0);
                JSONArray addressComponents = zero.getJSONArray("address_components");


                for (int i = 0; i < addressComponents.length(); i++) {
                    JSONObject zero2 = addressComponents.getJSONObject(i);
                    String longName = zero2.getString("long_name");
                    JSONArray types = zero2.getJSONArray("types");
                    String type = types.getString(0);


                    if (!TextUtils.isEmpty(longName)) {
                        if (type.equalsIgnoreCase("street_number")) {
                            address1 = longName + " ";
                        } else if (type.equalsIgnoreCase("route")) {
                            address1 = address1 + longName;
                        } else if (type.equalsIgnoreCase("sublocality")) {
                            address2 = longName;
                        } else if (type.equalsIgnoreCase("locality")) {
                            // address2 = address2 + longName + ", ";
                            city = longName;
                        } else if (type.equalsIgnoreCase("administrative_area_level_2")) {
                            county = longName;
                        } else if (type.equalsIgnoreCase("administrative_area_level_1")) {
                            state = longName;
                        } else if (type.equalsIgnoreCase("country")) {
                            country = longName;
                        } else if (type.equalsIgnoreCase("postal_code")) {
                            PIN = longName;
                        }
                    }
                }
            }
            else
            {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Google map place url
     */
    private URL buildUrl(double latitude, double longitude) {
        Uri uri = Uri.parse("https://maps.googleapis.com/maps/api/geocode/json").buildUpon()
                .appendQueryParameter("latlng", latitude + "," + longitude)
                .appendQueryParameter("key","AIzaSyBsug3VqBpKa4Xvo6zDaxcfzwGj0nU3B8E")
                .build();

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Get places response url
     */
    private String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Get Address for User Current Location (POST)
     */
    public void getAddress(Double latitude,Double longitude) throws JSONException {

        String url = APIBaseURL.getUsersLocationAddress;
        JSONObject inputObject = new JSONObject();
        inputObject.put("latitude",latitude);
        inputObject.put("longitude",longitude);

        ProgressDialog progressDialog = new ProgressDialog(LocationSelectionActivity.this);
        progressDialog.setMessage("Getting Current Location");
        progressDialog.show();



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                searchText.setText(response.optString("data"));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest,"get_address_taq");
    }

    /**
     * To hide keyboard using this method
     */
    public  static void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) locationSelectionActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = locationSelectionActivity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(locationSelectionActivity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                    getLocationAddress();

    }



    /**
     * Check the Business Service is Available in this location or not (POST)
     */
    public void checkServiceAvailableorNotAvailable() throws JSONException {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking Services in this Location");
        progressDialog.show();

        String url  = APIBaseURL.checkBusinessServicesAvailability;

        JSONObject inputObject = new JSONObject();
        inputObject.put("latitude",Double.valueOf(latitude));
        inputObject.put("longitude",Double.valueOf(longitude));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Boolean isSuccess = response.optBoolean("isSuccess");
                if(isSuccess)
                {
                    if(response.optString("errorMessage").equals("Service Available"))
                    {
                        SaveSharedPreference.saveLatLong(LocationSelectionActivity.this,latitude,longitude);
                        SaveSharedPreference.saveAddress(LocationSelectionActivity.this,searchText.getText().toString());
                        Intent intent = new Intent(LocationSelectionActivity.this, ConsumerMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(LocationSelectionActivity.this);
                        builder1.setMessage("Sorry! We currently do not service in this area");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(LocationSelectionActivity.this);
                builder1.setMessage("Sorry! We currently do not service in this area");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest,"location_lat_longitude_taq");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case REQUEST_LOCATION: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(LocationSelectionActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        String location_context = Context.LOCATION_SERVICE;
                        locationManager = (LocationManager) getSystemService(location_context);
                        List<String> providers = locationManager.getProviders(true);
                        for (String provider : providers) {
                            locationManager.requestLocationUpdates(provider, 1000, 0,
                                    new LocationListener() {

                                        public void onLocationChanged(Location location) {
                                        }

                                        public void onProviderDisabled(String provider) {
                                        }

                                        public void onProviderEnabled(String provider) {
                                        }

                                        public void onStatusChanged(String provider, int status,
                                                                    Bundle extras) {
                                        }
                                    });
                            location = locationManager.getLastKnownLocation(provider);
                        }
                    }
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }else{

                }
                return;
            }
        }
    }
}
