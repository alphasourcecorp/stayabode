package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
import stayabode.foodyHive.models.Franchise;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FranchiseFragment extends Fragment {

    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();
    List<Franchise> franchiseList = new ArrayList<>();

    Typeface fontBold;
    Typeface fontRegular;
    TextView pagetitle;
    // ProgressBar progressBar;
    CardView cardView;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout noInternetLayout;
    Button retryBtn;
    TextView text;
    EditText search;
    ImageView noInternetImage;
    AllListAdapter allListAdapter;
    ImageView searchIcon;
    LinearLayout searchLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_franchise, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_dehaze_black);
        MainActivity.customIcon.setVisibility(View.GONE);
        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);
        MainActivity.active = this;
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");
        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("Franchisee Status");
        pagetitle.setTypeface(fontBold);

        text = rootView.findViewById(R.id.text);
        noInternetImage = rootView.findViewById(R.id.noInternetImage);

        //progressBar = rootView.findViewById(R.id.progressBar);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerLayout);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        search = rootView.findViewById(R.id.search);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        noInternetLayout.setVisibility(View.GONE);
        cardView = rootView.findViewById(R.id.cardView);
        searchLayout = rootView.findViewById(R.id.searchLayout);
        searchIcon = rootView.findViewById(R.id.searchIcon);
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        // progressBar.setVisibility(View.VISIBLE);
        // cardView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getFranchises();

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

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }

    void filter(String text) {
        List<Object> temp = new ArrayList();
        String searchText=text.toLowerCase();
        for (Franchise franchise : franchiseList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            String franchiseName=franchise.getName().toLowerCase();
            if (franchiseName.contains(searchText)){
                temp.add(franchise);
            }
        }
        //update recyclerview
        allListAdapter.updateList(temp);
    }

    public void getFranchises() {

        String url = "";
        if(MainActivity.Role.equals("Franchisee"))
        {
             url = APIBaseURL.franchiseesGetFranchiseByFranchiseeID + "5ece8c07a18aea3628105f88";
        }
        else
        {
             url = APIBaseURL.franchisesURL;
        }

        //progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.GONE);
        // cardView.setVisibility(View.GONE);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                // cardView.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optBoolean("isSuccess")) {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        objectList = new ArrayList<>();
                        franchiseList = new ArrayList<>();
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            Franchise franchise = new Franchise();
                            franchise.setName(dataObject.optString("name"));
                            franchise.setLocation(dataObject.optString("location"));
                            franchise.setId(dataObject.optString("id"));
                            franchise.setAddressLine1(dataObject.optString("addressLine1"));
                            franchise.setAddressLine2(dataObject.optString("addressLine2"));
                            franchise.setPinCode(dataObject.optString("pinCode"));

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

                            franchise.setState(dataObject.optString("state"));
                            franchise.setCountry(dataObject.optString("country"));
                            franchise.setStartDate(dataObject.optString("startDate"));
                            franchise.setContact(dataObject.optString("contactNumber"));
                            franchise.setEmail(dataObject.optString("email"));
                            franchise.setImage(dataObject.optString("uploadLogo"));
                            franchise.setGstNumber(dataObject.optString("gstNumber"));
                            franchise.setPanNumber(dataObject.optString("panNumber"));
                            franchise.setNumberOfChefs(dataObject.optString("numberOfChefs"));
                            franchise.setNumberOfCloudKitchens(dataObject.optString("numberOfCloudKitchens"));
                            franchise.setStatus(dataObject.optBoolean("isActive"));
                            franchise.setBankName(dataObject.optString("bankName"));
                            franchise.setBankbranchName(dataObject.optString("branch"));
                            franchise.setBankbranchName(dataObject.optString("branch"));
                            franchise.setAccountNumber(dataObject.optString("accountNumber"));
                            franchise.setIfscCode(dataObject.optString("ifscCode"));
                            franchise.setFssaiNumber(dataObject.optString("fssaiNumber"));
//                        JSONObject choosePricingModelObject = new JSONObject();
//                        if(dataObject.has("choosePricingModel"))
//                        {
//                            choosePricingModelObject = dataObject.getJSONObject("choosePricingModel");
//                        }


                            franchise.setPricing(dataObject.optString("choosePricingModel"));
//                        franchise.setPricingModelID(choosePricingModelObject.optString("id"));
//                        franchise.setPricingAmount(choosePricingModelObject.optString("amount"));
//                        franchise.setPricingTransactions(choosePricingModelObject.optString("transactions"));
//                        franchise.setPricingTransactionPercentage(choosePricingModelObject.optString("transactionPercentage"));
//                        franchise.setPricinggstExtra(choosePricingModelObject.optString("gstExtra"));

                            objectList.add(franchise);
                            franchiseList.add(franchise);
                        }
                        allListAdapter = new AllListAdapter(getActivity(), objectList, fontBold, fontRegular);
                        recyclerView.setAdapter(allListAdapter);
                    }


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
                        getFranchises();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error", "TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error", "AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("No Franchisees!");
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
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "franchises_taq");
    }

    @Override
    public void onResume() {
        super.onResume();
//        BottomNavigationView navBar = getActivity().findViewById(R.id.navigation);
//        navBar.setVisibility(View.VISIBLE);
//        navigation.setVisibility(View.VISIBLE);
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);
    }
}
