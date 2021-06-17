package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.adapters.platform.DeliveryPartnerFranchiseAvailableAdapters;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.DeliveryPartner;
import stayabode.foodyHive.models.UserContact;
import stayabode.foodyHive.models.UserEmail;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CloudKitchenDetailFragment extends Fragment {

    ImageView profileImage;
    TextView cloudKitchenName;
    TextView city;
    TextView status;
    TextView quickStatsHeader;
    TextView chefsCount;
    TextView chefsHeader;
    TextView cloudKitchenCount;
    TextView cloudKitchenHeader;
    TextView cloudKitchenDetailheader;
    TextView locationHeader;
    TextView locationValue;
    TextView addressHeader;
    TextView addressLine1Value;
    TextView addressLine2Value;
    TextView addressLine3Value;
    TextView pincodeHeader;
    TextView pinCodeValue;
    TextView stateHeader;
    TextView stateValue;
    TextView countryHeader;
    TextView countryValue;
    TextView phoneHeader;
    //TextView phoneValue;
    TextView emailHeader;
    //TextView emailValue;
    TextView pricingModelHeader;
    TextView memberShipStatusValue;
    TextView amountValue;
    TextView availableDeliveryHeader;
    TextView transactionGSTValue;
    RecyclerView recyclerView;
    TextView text;
    ImageView noInternetImage;

    Typeface fontBold;
    Typeface fontRegular;
    List<DeliveryPartner> deliveryPartnerList = new ArrayList<>();
    List<Object> contactList=new ArrayList<>();
    List<Object> emailList = new ArrayList<>();

    TextView pagetitle;
    TextView back;
    NestedScrollView bodyLayout;

    ProgressBar progressBar;
    CardView availabledeliverycardView;
    LinearLayout noInternetLayout;
    Button retryBtn;

    RecyclerView recyclerViewPhone;
    RecyclerView recyclerViewEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cloud_kitchen_profile_details,container,false);

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
        fontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Regular.ttf");
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.active = this;
        pagetitle = rootView.findViewById(R.id.pagetitle);
        availabledeliverycardView = rootView.findViewById(R.id.availabledeliverycardView);
        pagetitle.setText("Cloud Kitchen Profile");
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

        recyclerViewEmail=rootView.findViewById(R.id.recyclerViewEmail);
        recyclerViewEmail.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPhone=rootView.findViewById(R.id.recyclerViewPhone);
        recyclerViewPhone.setLayoutManager(new LinearLayoutManager(getContext()));

        retryBtn = rootView.findViewById(R.id.retryBtn);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        noInternetLayout.setVisibility(View.GONE);
        text=rootView.findViewById(R.id.text);
        noInternetImage=rootView.findViewById(R.id.noInternetImage);
        profileImage = rootView.findViewById(R.id.profileImage);
        cloudKitchenName = rootView.findViewById(R.id.cloudKitchenName);
        city = rootView.findViewById(R.id.city);
        status = rootView.findViewById(R.id.status);
        quickStatsHeader = rootView.findViewById(R.id.quickStatsHeader);
        chefsCount = rootView.findViewById(R.id.chefsCount);
        chefsHeader = rootView.findViewById(R.id.chefsHeader);
        cloudKitchenCount = rootView.findViewById(R.id.cloudKitchenCount);
        cloudKitchenHeader = rootView.findViewById(R.id.cloudKitchenHeader);
        cloudKitchenDetailheader = rootView.findViewById(R.id.cloudKitchenDetailheader);
        locationHeader = rootView.findViewById(R.id.locationHeader);
        locationValue = rootView.findViewById(R.id.locationValue);
        addressHeader = rootView.findViewById(R.id.addressHeader);
        addressLine1Value = rootView.findViewById(R.id.addressLine1Value);
        addressLine2Value = rootView.findViewById(R.id.addressLine2Value);
        addressLine3Value = rootView.findViewById(R.id.addressLine3Value);
        pincodeHeader = rootView.findViewById(R.id.pincodeHeader);
        pinCodeValue = rootView.findViewById(R.id.pinCodeValue);
        stateHeader = rootView.findViewById(R.id.stateHeader);
        stateValue = rootView.findViewById(R.id.stateValue);
        countryHeader = rootView.findViewById(R.id.countryHeader);
        countryValue = rootView.findViewById(R.id.countryValue);
        phoneHeader = rootView.findViewById(R.id.phoneHeader);
        //phoneValue = rootView.findViewById(R.id.phoneValue);
        emailHeader = rootView.findViewById(R.id.emailHeader);
        //emailValue = rootView.findViewById(R.id.emailValue);
        pricingModelHeader = rootView.findViewById(R.id.pricingModelHeader);
        memberShipStatusValue = rootView.findViewById(R.id.memberShipStatusValue);
        amountValue = rootView.findViewById(R.id.amountValue);
        transactionGSTValue = rootView.findViewById(R.id.transactionGSTValue);
        transactionGSTValue.setText("< 50,000 Transactions\n25%\n18 % GST Extra");
        availableDeliveryHeader = rootView.findViewById(R.id.availableDeliveryHeader);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        bodyLayout =rootView. findViewById(R.id.bodyLayout);
        progressBar =rootView. findViewById(R.id.progressBar);
        bodyLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        cloudKitchenDetailheader.setTypeface(fontBold);
        cloudKitchenName.setTypeface(fontBold);
        city.setTypeface(fontRegular);
        status.setTypeface(fontRegular);
        quickStatsHeader.setTypeface(fontBold);
        chefsCount.setTypeface(fontBold);
        chefsHeader.setTypeface(fontRegular);
        cloudKitchenCount.setTypeface(fontBold);
        cloudKitchenHeader.setTypeface(fontRegular);
        locationHeader.setTypeface(fontBold);
        addressHeader.setTypeface(fontBold);
        pincodeHeader.setTypeface(fontBold);
        stateHeader.setTypeface(fontBold);
        countryHeader.setTypeface(fontBold);
        phoneHeader.setTypeface(fontBold);
        emailHeader.setTypeface(fontBold);
        locationValue.setTypeface(fontRegular);
        addressLine1Value.setTypeface(fontRegular);
        addressLine2Value.setTypeface(fontRegular);
        addressLine3Value.setTypeface(fontRegular);
        pinCodeValue.setTypeface(fontRegular);
        stateValue.setTypeface(fontRegular);
        countryValue.setTypeface(fontRegular);
        //phoneValue.setTypeface(fontRegular);
        //emailValue.setTypeface(fontRegular);
        pricingModelHeader.setTypeface(fontBold);
        memberShipStatusValue.setTypeface(fontRegular);
        amountValue.setTypeface(fontBold);
        transactionGSTValue.setTypeface(fontRegular);
        availableDeliveryHeader.setTypeface(fontBold);



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getCloudKitchenProfiles();
        return rootView;
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    public void getCloudKitchenProfiles()
    {
        bodyLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
        //recyclerView.setVisibility(View.GONE);
        String url = APIBaseURL.cloudKitchenProfileID+getArguments().getString("Id");

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                bodyLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject cloudKitchenObject = dataObject.getJSONObject("cloudKitchen");

                    cloudKitchenName.setText(cloudKitchenObject.optString("name"));
                    Glide.with(getContext()).load(cloudKitchenObject.optString("uploadLogo").replaceAll(" ","%20")).placeholder(R.drawable.foodi_logo_left_image).into(profileImage);

                    JSONObject locationObject = new JSONObject();
                    if(cloudKitchenObject.has("location"))
                    {
                        locationObject = cloudKitchenObject.getJSONObject("location");

                    }
                    city.setText(locationObject.optString("name"));
                    locationValue.setText(locationObject.optString("name"));
                    addressLine1Value.setText(cloudKitchenObject.optString("addressLine1"));
                    addressLine2Value.setText(cloudKitchenObject.optString("addressLine2"));
                    addressLine3Value.setText(cloudKitchenObject.optString("addressLine2"));
                    pinCodeValue.setText(cloudKitchenObject.optString("pinCode"));
                    stateValue.setText(cloudKitchenObject.optString("state"));
                    countryValue.setText(cloudKitchenObject.optString("country"));
                    //phoneValue.setText(cloudKitchenObject.optString("contact"));
                    //emailValue.setText(cloudKitchenObject.optString("email"));
                    chefsCount.setText(cloudKitchenObject.optString("numberOfChefs"));

                    if(cloudKitchenObject.optString("isActive").equalsIgnoreCase("Active"))
                    {
                        status.setText("Active");
                        status.setBackground(getResources().getDrawable(R.drawable.rounded_corner_border));
                    }
                    else
                    {
                        status.setText("Inactive");
                        status.setBackground(getResources().getDrawable(R.drawable.rounded_corner_border_inactive));
                    }

                    JSONArray deliveryPartnersArray = dataObject.getJSONArray("deliveryPartner");

                    deliveryPartnerList = new ArrayList<>();

                    for (int i=0;i < deliveryPartnersArray.length();i++)
                    {
                        JSONObject deliveryPartnerObject = deliveryPartnersArray.getJSONObject(i);
                        DeliveryPartner deliveryPartner = new DeliveryPartner();
                        deliveryPartner.setDistanceTime(deliveryPartnerObject.optString("deliveryPartnerResponseTime") +" Sec");
                        deliveryPartner.setName(deliveryPartnerObject.optString("deliveryPartnerName"));
                        deliveryPartner.setTotalPersons(deliveryPartnerObject.optString("deliveryPartnerNumberOfDeliveryPersons")+" Persons");
                        deliveryPartner.setUnitKG(deliveryPartnerObject.optString("deliveryPartnerMaxWeight")+" Kg");
                        deliveryPartner.setUploadLogo(deliveryPartnerObject.optString("deliveryPartnerUploadLogo"));
                        deliveryPartnerList.add(deliveryPartner);
                    }

//                    JSONObject pricingModelObject = cloudKitchenObject.getJSONObject("choosePricingModel");
//
//                    amountValue.setText("\u20B9 "+pricingModelObject.optString("amount"));
//                    memberShipStatusValue.setText(pricingModelObject.optString("pricing"));
//                    transactionGSTValue.setText(pricingModelObject.optString("transactions")+" Transactions\n"+pricingModelObject.optString("transactionPercentage")+"%\n"+pricingModelObject.optString("gstExtra")+" % GST Extra");
//

                    recyclerView.setAdapter(new DeliveryPartnerFranchiseAvailableAdapters(getContext(),deliveryPartnerList,fontBold,fontRegular));
                    if(recyclerView.getAdapter().getItemCount() == 0)
                    {
                        availabledeliverycardView.setVisibility(View.GONE);
                    }
                    else
                    {
                        availabledeliverycardView.setVisibility(View.VISIBLE);
                    }

                    JSONArray contactArray = cloudKitchenObject.getJSONArray("contact");
                    contactList = new ArrayList<>();

                    for (int i = 0; i < contactArray.length(); i++) {
                        UserContact userContact = new UserContact();
                        userContact.setContactNumber(String.valueOf(contactArray.getString(i)));
                        contactList.add(userContact);
                    }
                    recyclerViewPhone.setAdapter(new AllListAdapter(getContext(), contactList, fontBold, fontRegular));

                    JSONArray emailArray = cloudKitchenObject.getJSONArray("email");
                    emailList = new ArrayList<>();

                    for (int i = 0; i < emailArray.length(); i++) {
                        UserEmail userEmail = new UserEmail();
                        userEmail.setEmail(String.valueOf(emailArray.getString(i)));
                        emailList.add(userEmail);
                    }
                    recyclerViewEmail.setAdapter(new AllListAdapter(getContext(), emailList, fontBold, fontRegular));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bodyLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                noInternetLayout.setVisibility(View.VISIBLE);
                retryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noInternetLayout.setVisibility(View.GONE);
                        getCloudKitchenProfiles();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("Cloud Kitchen details not available!");
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
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"detail_taq_cloud_kitchen");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
    }
}
