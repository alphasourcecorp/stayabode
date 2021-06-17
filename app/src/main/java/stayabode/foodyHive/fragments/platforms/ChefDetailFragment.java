package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.adapters.platform.FoodMenuItemsListAdapter;
import stayabode.foodyHive.models.FoodMenu;
import stayabode.foodyHive.models.PromoCodes;
import stayabode.foodyHive.models.Reviews;

import java.util.ArrayList;
import java.util.List;

public class ChefDetailFragment extends Fragment {

    ImageView profileImage;
    TextView chefName;
    TextView city;
    TextView status;
    TextView totalOrdersHeader;
    TextView subscriptionsHeader;
    TextView ordersCount;
    TextView subscriptionsCount;
    TextView menuheader;
    TextView itemHeader;
    TextView priceHeader;
    RecyclerView menuRecyclerView;
    TextView promoCodesheader;
    TextView promoCodeStatusHeader;
    TextView promoCodeTitle;
    TextView customerReviewheader;
    RecyclerView promoCodeRecyclerView;
    RecyclerView reviewsRecyclerView;
    Typeface fontBold;
    Typeface fontRegular;
    List<Object> reviewsList = new ArrayList<>();
    List<Object> objectList = new ArrayList<>();
    List<FoodMenu> foodMenuList = new ArrayList<>();
    TextView toolbar_title;
    TextView pagetitle;
    TextView back;
    NestedScrollView bodyLayout;

    ProgressBar progressBar;

    //ChefDetailFragment chefDetailFragment = new ChefDetailFragment();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_chef_profile_details,container,false);
        fontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Regular.ttf");
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
        pagetitle.setText("Chef Profile");
        pagetitle.setTypeface(fontBold);
        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });





        profileImage = rootView.findViewById(R.id.profileImage);
        chefName = rootView.findViewById(R.id.chefName);
        city = rootView.findViewById(R.id.city);
        status = rootView.findViewById(R.id.status);
        totalOrdersHeader = rootView.findViewById(R.id.totalOrdersHeader);
        subscriptionsHeader = rootView.findViewById(R.id.subscriptionsHeader);
        ordersCount = rootView.findViewById(R.id.ordersCount);
        subscriptionsCount = rootView.findViewById(R.id.subscriptionsCount);
        menuheader = rootView.findViewById(R.id.menuheader);
        itemHeader = rootView.findViewById(R.id.itemHeader);
        priceHeader = rootView.findViewById(R.id.priceHeader);
        menuRecyclerView = rootView.findViewById(R.id.menuRecyclerView);
        promoCodesheader = rootView.findViewById(R.id.promoCodesheader);
        promoCodeStatusHeader = rootView.findViewById(R.id.promoCodeStatusHeader);
        promoCodeTitle = rootView.findViewById(R.id.promoCodeTitle);
        promoCodeRecyclerView = rootView.findViewById(R.id.promoCodeRecyclerView);
        customerReviewheader = rootView.findViewById(R.id.customerReviewheader);
        reviewsRecyclerView = rootView.findViewById(R.id.reviewsRecyclerView);
        bodyLayout =rootView. findViewById(R.id.bodyLayout);
        progressBar =rootView. findViewById(R.id.progressBar);
//        bodyLayout.setVisibility(View.INVISIBLE);
//        progressBar.setVisibility(View.VISIBLE);


        chefName.setTypeface(fontBold);
        city.setTypeface(fontRegular);
        status.setTypeface(fontRegular);
        totalOrdersHeader.setTypeface(fontBold);
        ordersCount.setTypeface(fontBold);
        subscriptionsHeader.setTypeface(fontBold);
        subscriptionsCount.setTypeface(fontBold);
        menuheader.setTypeface(fontBold);
        itemHeader.setTypeface(fontBold);
        priceHeader.setTypeface(fontBold);
        promoCodesheader.setTypeface(fontBold);
        promoCodeTitle.setTypeface(fontBold);
        promoCodeStatusHeader.setTypeface(fontBold);
        customerReviewheader.setTypeface(fontBold);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        promoCodeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        chefName.setText(getArguments().getString("Name"));
        city.setText(getArguments().getString("Location"));

//        locationValue.setText(getArguments().getString("Location"));
//        addressLine1Value.setText(getArguments().getString("AddressLine1"));
//        addressLine2Value.setText(getArguments().getString("AddressLine2"));
//        addressLine3Value.setText(getArguments().getString("State"));
//        pinCodeValue.setText(getArguments().getString("PinCode"));
//        stateValue.setText(getArguments().getString("State"));
//        countryValue.setText(getArguments().getString("Country"));
//        phoneValue.setText(getArguments().getString("Contact"));
//        emailValue.setText(getArguments().getString("Email"));


        getMenuItems();
        getPromoCodes();
        getReviews();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
    }

    public void getReviews()
    {
        for (int i=0;i < 10;i++)
        {
            Reviews reviews = new Reviews();
            reviews.setReviewsDescription("Lorem Ipsum dolor sit amet,\\nconsectetur adipiscing elit. Sed in \\ntellus eget sem pretium lacnia");
            reviewsList.add(reviews);
        }

        reviewsRecyclerView.setAdapter(new AllListAdapter(getContext(),reviewsList,fontBold,fontRegular));
    }

    public void getPromoCodes()
    {
        for (int i=0;i<10;i++)
        {
            PromoCodes promoCodes = new PromoCodes();
            promoCodes.setPromoCodeText("ASDRDR21");
            promoCodes.setStatus("Active");
            objectList.add(promoCodes);
        }

        promoCodeRecyclerView.setAdapter(new AllListAdapter(getContext(),objectList,fontBold,fontRegular));
    }

    public void getMenuItems()
    {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setName("Food Item 1");
        foodMenu.setPrice("$ 50");
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);


        menuRecyclerView.setAdapter(new FoodMenuItemsListAdapter(getContext(),foodMenuList,fontBold,fontRegular));

    }

    public void onBackPressed()
    {
        FragmentManager fm = MainActivity.fragmentManager;
        fm.popBackStack();
    }

//    public void getCloudKitchenProfiles()
//    {
//        bodyLayout.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
//        String url = APIBaseURL.cloudKitchenProfileID+getArguments().getString("Id");
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                bodyLayout.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject dataObject = jsonObject.getJSONObject("data");
//                    JSONObject cloudKitchenObject = dataObject.getJSONObject("cloudKitchen");
//
//                    cloudKitchenName.setText(cloudKitchenObject.optString("name"));
//                    city.setText(cloudKitchenObject.optString("location"));
//
//                    locationValue.setText(cloudKitchenObject.optString("location"));
//                    addressLine1Value.setText(cloudKitchenObject.optString("addressLine1"));
//                    addressLine2Value.setText(cloudKitchenObject.optString("addressLine2"));
//                    addressLine3Value.setText(cloudKitchenObject.optString("addressLine2"));
//                    pinCodeValue.setText(cloudKitchenObject.optString("pinCode"));
//                    stateValue.setText(cloudKitchenObject.optString("state"));
//                    countryValue.setText(cloudKitchenObject.optString("country"));
//                    phoneValue.setText(cloudKitchenObject.optString("contact"));
//                    emailValue.setText(cloudKitchenObject.optString("email"));
//                    chefsCount.setText(cloudKitchenObject.optString("numberOfChefs"));
//
//                    if(cloudKitchenObject.optBoolean("isActive"))
//                    {
//                        status.setText("Active");
//                    }
//                    else
//                    {
//                        status.setText("Inactive");
//                    }
//
//                    JSONArray deliveryPartnersArray = dataObject.getJSONArray("deliveryPartner");
//
//                    deliveryPartnerList = new ArrayList<>();
//
//                    for (int i=0;i < deliveryPartnersArray.length();i++)
//                    {
//                        JSONObject deliveryPartnerObject = deliveryPartnersArray.getJSONObject(i);
//                        DeliveryPartner deliveryPartner = new DeliveryPartner();
//                        deliveryPartner.setDistanceTime(/*deliveryPartnerObject.optString("responseTime")*/ "3"+" Sec");
//                        deliveryPartner.setName(deliveryPartnerObject.optString("name"));
//                        deliveryPartner.setTotalPersons(deliveryPartnerObject.optString("numberOfDeliveryPerson")+" Persons");
//                        deliveryPartner.setUnitKG(deliveryPartnerObject.optString("maxWeight")+" Kg");
//                        deliveryPartnerList.add(deliveryPartner);
//                    }
//
////                    JSONObject pricingModelObject = cloudKitchenObject.getJSONObject("choosePricingModel");
////
////                    amountValue.setText("\u20B9 "+pricingModelObject.optString("amount"));
////                    memberShipStatusValue.setText(pricingModelObject.optString("pricing"));
////                    transactionGSTValue.setText(prici
////                    ngModelObject.optString("transactions")+" Transactions\n"+pricingModelObject.optString("transactionPercentage")+"%\n"+pricingModelObject.optString("gstExtra")+" % GST Extra");
////
//
//                    recyclerView.setAdapter(new DeliveryPartnerFranchiseAvailableAdapters(getContext(),deliveryPartnerList,fontBold,fontRegular));
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        ApplicationController.getInstance().addToRequestQueue(stringRequest,"detail_taq_cloud_kitchen");
//    }
}
