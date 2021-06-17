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



import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.MappedFranchiseListAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Franchise;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentGatewayDetailFragment extends Fragment {

    ImageView profileImage;
    TextView paymentGatewayName;
    //TextView city;
    TextView status;
    TextView quickStatsHeader;

    TextView mappedFranchiseHeader;
    TextView transactionValue;
    TextView transactioneHeader;
    TextView creditDayValue;
    TextView creditDayHeader;
    TextView setupValue;
    TextView setupHeader;
    TextView paymentGatewayDetailHeader;
    TextView startDate;
    TextView startDateValue;
    RecyclerView recyclerView;

    List<Franchise> franchiseList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView pagetitle;
    TextView back;
    NestedScrollView bodyLayout;

    ProgressBar progressBar;
    CardView mappedcardView;

    LinearLayout noInternetLayout;
    Button retryBtn;
    TextView text;
    ImageView noInternetImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_payment_gateway_profile_detail,container,false);

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
        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        pagetitle.setText("Payment Gateway Detail");
        pagetitle.setTypeface(fontBold);

        back = rootView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });
        text=rootView.findViewById(R.id.text);
        noInternetImage=rootView.findViewById(R.id.noInternetImage);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        noInternetLayout.setVisibility(View.GONE);

        profileImage = rootView.findViewById(R.id.profileImage);
        paymentGatewayName = rootView.findViewById(R.id.paymentGatewayName);
        //city = rootView.findViewById(R.id.city);
        status = rootView.findViewById(R.id.status);
        quickStatsHeader = rootView.findViewById(R.id.quickStatsHeader);
        startDate = rootView.findViewById(R.id.startDate);
        startDateValue =rootView. findViewById(R.id.startDateValue);
        transactionValue = rootView.findViewById(R.id.transactionValue);
        paymentGatewayDetailHeader =rootView. findViewById(R.id.paymentGatewayDetailHeader);
        setupHeader = rootView.findViewById(R.id.setupHeader);
        setupValue = rootView.findViewById(R.id.setupValue);
        transactioneHeader =rootView. findViewById(R.id.transactioneHeader);
        creditDayValue = rootView.findViewById(R.id.creditDayValue);
        creditDayHeader = rootView.findViewById(R.id.creditDayHeader);

        mappedFranchiseHeader = rootView.findViewById(R.id.mappedFranchiseHeader);
        bodyLayout =rootView. findViewById(R.id.bodyLayout);
        progressBar =rootView. findViewById(R.id.progressBar);
        mappedcardView =rootView. findViewById(R.id.mappedcardView);
        bodyLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        paymentGatewayName.setTypeface(fontBold);
       // city.setTypeface(fontRegular);
        status.setTypeface(fontRegular);
        quickStatsHeader.setTypeface(fontBold);
        startDate.setTypeface(fontBold);
        startDateValue.setTypeface(fontRegular);
        setupHeader.setTypeface(fontRegular);
        setupValue.setTypeface(fontBold);
        creditDayHeader.setTypeface(fontRegular);
        creditDayValue.setTypeface(fontBold);
        transactioneHeader.setTypeface(fontRegular);
        transactionValue.setTypeface(fontBold);
        paymentGatewayDetailHeader.setTypeface(fontBold);

        mappedFranchiseHeader.setTypeface(fontBold);

        getPaymentGatewayDetail();
       // getMappedFranchises();
        return rootView;
    }

    public void getMappedFranchises()
    {
        Franchise franchise = new Franchise();
        franchise.setLocation("location");
        franchise.setName("Franchises Name");
        franchiseList.add(franchise);
        franchiseList.add(franchise);
        franchiseList.add(franchise);
        franchiseList.add(franchise);
        franchiseList.add(franchise);

        recyclerView.setAdapter(new MappedFranchiseListAdapter(getContext(),franchiseList,fontBold,fontRegular));

    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
    }

    public void getPaymentGatewayDetail()
    {
        bodyLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
        //recyclerView.setVisibility(View.GONE);

        String url = APIBaseURL.paymentGatewayProfileURL +  getArguments().getString("Id");

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                bodyLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    JSONObject dataObject = dataArray.getJSONObject(0);
                    paymentGatewayName.setText(dataObject.optString("name"));
                    Glide.with(getContext()).load(dataObject.optString("uploadLogo").replaceAll(" ","%20")).placeholder(R.drawable.foodi_logo_left_image).into(profileImage);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    DateFormat startDate = new SimpleDateFormat("dd MMM yyyy");
//                        DateFormat endDateFormat = new SimpleDateFormat("dd");



                    Date startdate = null;
                    try {
                        startdate = sdf.parse(dataObject.optString("startDate"));
                        startDateValue.setText(startDate.format(startdate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Date startdate1 = null;
                        try {
                            startdate1 = sdf1.parse(dataObject.optString("startDate"));
                            startDateValue.setText(startDate.format(startdate1));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }


                    transactionValue.setText(dataObject.optString("transactionFee")+"%");
                    setupValue.setText(dataObject.optString("setupfee"));
                    creditDayValue.setText(dataObject.optString("daysToCredit"));

                  //  startDateValue.setText(dataObject.optString("startDate"));

                    JSONArray franchiseeArray = new JSONArray();
                    if(dataObject.has("franchisee"))
                    {
                        franchiseeArray = dataObject.getJSONArray("franchisee");
                    }
                    franchiseList.clear();
                    for (int i=0;i < franchiseeArray.length();i++)
                    {
                        JSONObject franchiseeObject = franchiseeArray.getJSONObject(i);
                        Franchise franchise = new Franchise();
                        franchise.setLocation(franchiseeObject.optString("franchiseLocation"));
                        franchise.setName(franchiseeObject.optString("franchiseName"));
                        franchise.setImage(franchiseeObject.optString("franchiseLogo"));
                        franchiseList.add(franchise);
                    }
                    recyclerView.setAdapter(new MappedFranchiseListAdapter(getContext(),franchiseList,fontBold,fontRegular));
                    if(recyclerView.getAdapter().getItemCount()== 0)
                    {
                        mappedcardView.setVisibility(View.GONE);
                    }
                    else
                    {
                        mappedcardView.setVisibility(View.VISIBLE);
                    }

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
                        getPaymentGatewayDetail();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("Payment gateway details not available!");
//                    retryBtn.setVisibility(View.GONE);
//
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
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"payment_profile_taq");
    }
}
