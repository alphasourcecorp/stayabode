package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import stayabode.foodyHive.models.DeliveryPartner;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeliveryPartnerFragment extends Fragment {

    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView toolbar_title;
    TextView pagetitle;
    TextView back;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout noInternetLayout;
    Button retryBtn;
    TextView text;
    ImageView noInternetImage;
    ImageView searchIcon;
    LinearLayout searchLayout;
    TextView search;
    AllListAdapter allListAdapter;
    List<DeliveryPartner> deliveryPartnerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_delivery_partners,container,false);
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
        pagetitle.setText("Delivery Partner Status");
        pagetitle.setTypeface(fontBold);
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

        searchIcon = rootView.findViewById(R.id.searchIcon);
        searchLayout = rootView.findViewById(R.id.searchLayout);
        search = rootView.findViewById(R.id.search);
        text=rootView.findViewById(R.id.text);
        noInternetImage=rootView.findViewById(R.id.noInternetImage);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerLayout);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        noInternetLayout.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        //getDeliveryPartners();
        deliveryPartners();

        return rootView;
    }

    void filter(String text) {
        List<Object> temp = new ArrayList<>();
        String searchText=text.toLowerCase();
        for (DeliveryPartner deliveryPartner : deliveryPartnerList) {
            String deliveryPartnerName=deliveryPartner.getName().toLowerCase();
            if (deliveryPartnerName.contains(searchText)) {
                temp.add(deliveryPartner);
            }
        }
        allListAdapter.updateList(temp);
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
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    public void deliveryPartners()
    {
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        String url = "";

        if(MainActivity.Role.equals("Franchisee"))
        {
            url = APIBaseURL.franchiseeGetDeliveryPartnerByFranchiseeID + "5ece8c07a18aea3628105f88";
        }
        else
        {
            url = APIBaseURL.deliveryPartnerURL;
        }



        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    objectList = new ArrayList<>();
                    deliveryPartnerList = new ArrayList<>();
                    for (int i=0;i < dataArray.length();i++)
                    {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        DeliveryPartner deliveryPartner = new DeliveryPartner();
                        deliveryPartner.setName(dataObject.optString("name"));
                        deliveryPartner.setId(dataObject.optString("id"));
                        deliveryPartner.setDistanceTime("Response Time - "+dataObject.optString("responseTime") + " Sec");


                        JSONArray coverageArray = dataObject.getJSONArray("coverageAreaSelectedAreas");
//                        List<String> list = new ArrayList<String>();
//                        String coverageAreaStringWithCommaSeperator = "";
//                        for(int j = 0; j < coverageArray.length(); j++){
//                            JSONObject jsonobject = coverageArray.getJSONObject(j);
//                            list.add(jsonobject.optString("Area"));
//                            coverageAreaStringWithCommaSeperator = jsonobject.optString("Area");
//
//                        }
                        StringBuffer result = new StringBuffer();
                        for (int j = 0; j < coverageArray.length(); j++) {
                            result.append( coverageArray.get(j) );
                        }
                        String mynewstring = result.toString();
                        deliveryPartner.setCoverageArea(TextUtils.join(",", Collections.singleton(result.toString())));
                        deliveryPartner.setAddressLine1(dataObject.optString("addressLine2"));
                        deliveryPartner.setAddressLine2(dataObject.optString("addressLine2"));
                        deliveryPartner.setMaxWeight(dataObject.optString("maxWeight"));
                        deliveryPartner.setLocation(dataObject.optString("location"));
                        deliveryPartner.setChargesPerDelivery(dataObject.optString("chargesPerDelivery"));
                        deliveryPartner.setOtherCharges(dataObject.optString("otherCharges"));
                        deliveryPartner.setTotalCharges(dataObject.optString("totalCharges"));
                        deliveryPartner.setContactNumber(dataObject.optString("contactNumber"));
                        deliveryPartner.setEmail(dataObject.optString("email"));
                        deliveryPartner.setPanNumber(dataObject.optString("panNumber"));
                        deliveryPartner.setGstNumber(dataObject.optString("gstNumber"));
                        deliveryPartner.setNoOfDeliveryPerson(dataObject.optString("numberOfDeliveryPerson"));
                        deliveryPartner.setUploadLogo(dataObject.optString("uploadLogo"));
                        deliveryPartner.setStatus(dataObject.optBoolean("isActive"));
                        deliveryPartner.setContract(dataObject.optString("contract"));
                        deliveryPartner.setHoursOfOperation(dataObject.optString("hoursOfOperation"));
                        deliveryPartner.setDaysofOperation(dataObject.optString("daysofOperation"));
                        deliveryPartner.setStartDate(dataObject.optString("startDate"));
                        deliveryPartner.setEndDate(dataObject.optString("endDate"));
                        deliveryPartner.setBankName(dataObject.optString("bankName"));
                        deliveryPartner.setBankAccount(dataObject.optString("bankAccount"));
                        deliveryPartner.setIfscCode(dataObject.optString("ifscCode"));
                        deliveryPartner.setBranch(dataObject.optString("branch"));
                        deliveryPartner.setTermsAndConditions(dataObject.optBoolean("termsAndCondition"));
                        deliveryPartner.setActive(dataObject.optBoolean("isActive"));
                        deliveryPartner.setPayPlatformFees(dataObject.optBoolean("payPlatformFees"));
                        deliveryPartner.setRaiseInvoice(dataObject.optBoolean("raiseInvoice"));
                        deliveryPartner.setDelete(dataObject.optBoolean("isDelete"));
                        objectList.add(deliveryPartner);
                        deliveryPartnerList.add(deliveryPartner);
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
                        deliveryPartners();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("No Delivery Partners!");
//                    retryBtn.setVisibility(View.GONE);
//                    Log.d("Error","ServerError");
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error","Network Error");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error","ParseError");
//                }
            }
        },getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"deliver_partner_taq");
    }
}
