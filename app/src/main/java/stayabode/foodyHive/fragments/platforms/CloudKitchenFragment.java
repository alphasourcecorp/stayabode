package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.CloudKitchen;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CloudKitchenFragment extends Fragment {

    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();
    List<CloudKitchen> cloudKitchenList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView toolbar_title;
    TextView search;
    TextView pagetitle;
    TextView back;
    ImageView searchIcon;
    LinearLayout searchLayout;

    AllListAdapter allListAdapter;
    // ProgressBar progressBar;
    CardView cardView;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout noInternetLayout;
    Button retryBtn;
    TextView text;
    ImageView noInternetImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cloud_kitchen, container, false);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");
//        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black);
//        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        MainActivity.toolbar.setNavigationIcon(null);
        MainActivity.customIcon.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);
        MainActivity.toolbar_save.setText("< Back");
        MainActivity.toolbar_save.setTypeface(fontBold);
        MainActivity.customIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.active = this;
        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("Cloud Kitchen Status");
        pagetitle.setTypeface(fontBold);

        searchIcon = rootView.findViewById(R.id.searchIcon);
        searchLayout = rootView.findViewById(R.id.searchLayout);
        search = rootView.findViewById(R.id.search);
        text = rootView.findViewById(R.id.text);
        noInternetImage = rootView.findViewById(R.id.noInternetImage);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerLayout);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        noInternetLayout.setVisibility(View.GONE);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cardView = rootView.findViewById(R.id.cardView);
//        progressBar.setVisibility(View.VISIBLE);
//        cardView.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });

        //getCloudKitchenStatus();

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.VISIBLE);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        cloudKitchens();

        return rootView;
    }

    void filter(String text) {
        List<Object> temp = new ArrayList<>();
        String searchText=text.toLowerCase();
        for (CloudKitchen cloudKitchen : cloudKitchenList) {
            String kitchenNames = cloudKitchen.getName().toLowerCase();
            if (kitchenNames.contains(searchText)) {
                temp.add(cloudKitchen);
            }
        }
        allListAdapter.updateList(temp);
    }

    public void getCloudKitchenStatus() {
        objectList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CloudKitchen cloudKitchen = new CloudKitchen();
            cloudKitchen.setFranchiseName("Franchise Name");
            cloudKitchen.setName("Cloud Kitchen");
            cloudKitchen.setLocation("location");
            objectList.add(cloudKitchen);
        }


        recyclerView.setAdapter(new AllListAdapter(getActivity(), objectList, fontBold, fontRegular));
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
//        BottomNavigationView navBar = getActivity().findViewById(R.id.navigation);
//        navBar.setVisibility(View.GONE);
//        navigation.setVisibility(View.GONE);
    }

    public void cloudKitchens() {

        String url = "";

        if(MainActivity.Role.equals("Franchisee"))
        {
            url = APIBaseURL.franchiseeGetCloudKitchenByFranchiseID + "5ece8c07a18aea3628105f88";
        }
        else
        {
            url = APIBaseURL.cloudKitchenURl;
        }

        //progressBar.setVisibility(View.VISIBLE);
        //cardView.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                progressBar.setVisibility(View.GONE);
//                cardView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    objectList = new ArrayList<>();
                    cloudKitchenList=new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        CloudKitchen cloudKitchen = new CloudKitchen();
                        cloudKitchen.setFranchiseName(dataObject.optString("franchise"));
                        cloudKitchen.setName(dataObject.optString("name"));
                        JSONObject locationObject = new JSONObject();
                        if(dataObject.has("location"))
                        {
                            locationObject = dataObject.getJSONObject("location");

                        }
                        cloudKitchen.setLocation(locationObject.optString("name"));
                        cloudKitchen.setId(dataObject.optString("id"));
                        cloudKitchen.setAddressLine1(dataObject.optString("addressLine1"));
                        cloudKitchen.setAddressLine2(dataObject.optString("addressLine2"));
                        cloudKitchen.setPinCode(dataObject.optString("pinCode"));
                        cloudKitchen.setImage(dataObject.optString("uploadLogo"));
//                        JSONObject stateObject = new JSONObject();
//                        if(dataObject.has("state"))
//                        {
//                            stateObject = dataObject.getJSONObject("state");
//                        }
//                        JSONObject countryObject = new JSONObject();
//                        if(dataObject.has("country"))
//                        {
//                            countryObject = dataObject.getJSONObject("country");
//                        }


                        cloudKitchen.setState(dataObject.optString("state"));
                        cloudKitchen.setCountry(dataObject.optString("country"));
                        JSONObject franchiseObject = new JSONObject();
                        if (dataObject.has("franchise")) {
                            franchiseObject = dataObject.getJSONObject("franchise");
                            cloudKitchen.setFranchiseName(franchiseObject.optString("franchiseName"));

                        } else {
                            cloudKitchen.setFranchiseName("");
                        }
                        cloudKitchen.setContact(dataObject.optString("contactNumber"));
                        cloudKitchen.setEmail(dataObject.optString("email"));
                        cloudKitchen.setGstNumber(dataObject.optString("gstNumber"));
                        cloudKitchen.setPanNumber(dataObject.optString("panNumber"));
                        cloudKitchen.setStatus(dataObject.optBoolean("status"));
                        cloudKitchen.setStartDate(dataObject.optString("startDate"));
                        cloudKitchen.setBankName(dataObject.optString("bankName"));
                        cloudKitchen.setBankBranchName(dataObject.optString("branch"));
                        cloudKitchen.setAccountNumber(dataObject.optString("accountNumber"));
                        cloudKitchen.setIfscCode(dataObject.optString("ifscCode"));
                        cloudKitchen.setCertificates(dataObject.optString("certificates"));
                        cloudKitchen.setFssaiNumber(dataObject.optString("fssaiNumber"));
                        cloudKitchen.setNoOfChefs(dataObject.optString("numberOfChefs"));
                        objectList.add(cloudKitchen);
                        cloudKitchenList.add(cloudKitchen);

                    }
                    allListAdapter=new AllListAdapter(getContext(),objectList,fontBold,fontRegular);
                    recyclerView.setAdapter(allListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                noInternetLayout.setVisibility(View.VISIBLE);
                retryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noInternetLayout.setVisibility(View.GONE);
                        cloudKitchens();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error", "TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error", "AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("No Cloud Kitchens!");
//                    retryBtn.setVisibility(View.GONE);
//                    Log.d("Error", "ServerError");
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error", "Network Error");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error", "ParseError");
//                }


            }
        },getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "cloud_kitchen_taq");

    }

    public void onBackPressed() {
        FragmentManager fm = MainActivity.fragmentManager;
        fm.popBackStack();
    }

}
