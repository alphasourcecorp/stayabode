package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RevenueSharingFragment extends Fragment {

    TextView platform;
    TextView platformValue;
    TextView franchise;
    TextView franchiseValue;
    TextView delivery;
    TextView deliveryValue;
    TextView paymentGateway;
    TextView paymentGatewayValue;
    TextView chef;
    TextView chefValue;
    TextView taxgst;
    TextView taxgstValue;
    TextView total;
    TextView totalValue;
    Spinner pricingType;
    TextView selectPricingHeader;

    Typeface fontBold;
    Typeface fontRegular;
    TextView toolbar_title;
    TextView pagetitle;
    TextView back;
    String[] searchNames;
    String[] searchIds;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_revenue_sharing,container,false);
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
        pagetitle.setText("Revenue Sharing");
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


        selectPricingHeader = rootView.findViewById(R.id.selectPricingHeader);
        pricingType = rootView.findViewById(R.id.pricingType);
        platform = rootView.findViewById(R.id.platform);
        platformValue = rootView.findViewById(R.id.platformValue);
        franchise = rootView.findViewById(R.id.franchise);
        franchiseValue = rootView.findViewById(R.id.franchiseValue);
        delivery = rootView.findViewById(R.id.delivery);
        deliveryValue = rootView.findViewById(R.id.deliveryValue);
        paymentGateway = rootView.findViewById(R.id.paymentGateway);
        paymentGatewayValue = rootView.findViewById(R.id.paymentGatewayValue);
        chef = rootView.findViewById(R.id.chef);
        chefValue = rootView.findViewById(R.id.chefValue);
        taxgst = rootView.findViewById(R.id.taxgst);
        taxgstValue = rootView.findViewById(R.id.taxgstValue);
        total =rootView. findViewById(R.id.total);
        totalValue = rootView.findViewById(R.id.totalValue);

//        pricingType.setTypeface(fontRegular);
        platform.setTypeface(fontRegular);
        platformValue.setTypeface(fontRegular);
        franchise.setTypeface(fontRegular);
        franchiseValue.setTypeface(fontRegular);
        delivery.setTypeface(fontRegular);
        deliveryValue.setTypeface(fontRegular);
        paymentGateway.setTypeface(fontRegular);
        paymentGatewayValue.setTypeface(fontRegular);
        chef.setTypeface(fontRegular);
        chefValue.setTypeface(fontRegular);
        taxgst.setTypeface(fontRegular);
        taxgstValue.setTypeface(fontRegular);
        total.setTypeface(fontBold);
        totalValue.setTypeface(fontBold);
        selectPricingHeader.setTypeface(fontBold);

        return rootView;
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

    public void getRevenueSharings()
    {
        String url = APIBaseURL.revenueSharing;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    searchNames = new String[dataArray.length() + 1];
                    searchIds = new String[dataArray.length() + 1];
                    searchIds[0] = "0000000000000";
                    searchNames[0] = "Select";

                    for (int i = 1; i <= dataArray.length(); i++) {
                        JSONObject officer = dataArray.getJSONObject(i - 1);
                        String officerType_Id = officer.optString("id");
                        String officerType_name = officer.optString("name");
                        searchIds[i] = officerType_Id;
                        searchNames[i] = officerType_name;
                    }

                    try {
                        ArrayAdapter<String> officerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, searchNames);
                        pricingType.setAdapter(officerAdapter);
                        pricingType.setFocusable(false);
                        pricingType.setClickable(false);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"revenue_sharing_taq");
    }

}
