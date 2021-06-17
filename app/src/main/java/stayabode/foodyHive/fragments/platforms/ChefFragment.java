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
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChefFragment extends Fragment {

    RecyclerView recyclerView;
    Typeface fontBold;
    Typeface fontRegular;
    List<Object> objectList = new ArrayList<>();
    TextView toolbar_title;
    TextView back;
    TextView pagetitle;
    //    ProgressBar progressBar;
    CardView cardView;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout noInternetLayout;
    Button retryBtn;
    TextView text;
    ImageView noInternetImage;
    TextView chefName;
    ImageView searchIcon;
    LinearLayout searchLayout;
    TextView search;
    AllListAdapter allListAdapter;
    List<Chef> chefList = new ArrayList<>();
    //ChefFragment chefFragment = new ChefFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_chef, container, false);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");
        MainActivity.rightMenu.setVisibility(View.GONE);
//        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black);
//        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("Chef Status");
        pagetitle.setTypeface(fontBold);
        MainActivity.active = this;
        //progressBar = rootView.findViewById(R.id.progressBar);

        searchIcon = rootView.findViewById(R.id.searchIcon);
        searchLayout = rootView.findViewById(R.id.searchLayout);
        search = rootView.findViewById(R.id.search);
        cardView = rootView.findViewById(R.id.cardView);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerLayout);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        text = rootView.findViewById(R.id.text);
        noInternetImage = rootView.findViewById(R.id.noInternetImage);
        noInternetLayout.setVisibility(View.GONE);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        // progressBar.setVisibility(View.VISIBLE);
        //cardView.setVisibility(View.GONE);
        back = rootView.findViewById(R.id.back);
        if (MainActivity.Role.equals("Franchisee")) {
            back.setVisibility(View.GONE);
            MainActivity.toolbar.setNavigationIcon(R.drawable.ic_dehaze_black);
            MainActivity.customIcon.setVisibility(View.GONE);
            MainActivity.rightMenu.setVisibility(View.GONE);
            MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.drawer.openDrawer(Gravity.LEFT);
                }
            });
        } else {
            back.setVisibility(View.GONE);
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
        }
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

        chefs();

        return rootView;
    }

    void filter(String text) {
        List<Object> temp = new ArrayList<>();
        String searchText=text.toLowerCase();
        for (Chef chef : chefList) {
            String chefName = chef.getName().toLowerCase();
            if (chefName.contains(searchText)) {
                temp.add(chef);
            }
        }
        allListAdapter.updateList(temp);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.Role.equals("Franchisee")) {
            MainActivity.navigation.setVisibility(View.VISIBLE);
            MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
            MainActivity.rightMenu.setVisibility(View.GONE);
        } else {
            MainActivity.navigation.setVisibility(View.GONE);
            MainActivity.mainBottomLayout.setVisibility(View.GONE);
            MainActivity.rightMenu.setVisibility(View.GONE);
        }

    }

    public void chefs() {
        String url = APIBaseURL.chefsURL;
//        progressBar.setVisibility(View.VISIBLE);
//        cardView.setVisibility(View.GONE);
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
                    if (jsonObject.optBoolean("isSuccess")) {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        objectList = new ArrayList<>();
                        chefList = new ArrayList<>();
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            Chef chef = new Chef();
                            chef.setLocation(dataObject.optString("location"));
                            chef.setName(dataObject.optString("name"));
                          /*  String isActive = dataObject.optString("isActive");
                          if(isActive.contains("Blocked")){
                                chefName.setTextColor(R.color.colorNotificationBG);
                          }*/
                            chef.setId(dataObject.optString("id"));
                            chef.setAddressLine1(dataObject.optString("addressLine1"));
                            chef.setAddressLine2(dataObject.optString("addressLine2"));
                            chef.setPinCode(dataObject.optString("pinCode"));
                            chef.setStatus(dataObject.optString("isActive"));
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
                            chef.setRatingCount(dataObject.optInt("rating"));
                            chef.setState(dataObject.optString("state"));
                            chef.setCountry(dataObject.optString("country"));
                            chef.setFranchiseName(dataObject.optString("franchise"));
                            chef.setContact(dataObject.optString("contactNumber"));
                            chef.setEmail(dataObject.optString("email"));
                            chef.setGstNumber(dataObject.optString("gstNumber"));
                            chef.setPanNumber(dataObject.optString("panNumber"));
                            chef.setStatus(dataObject.optString("isActive"));
                            chef.setStartDate(dataObject.optString("startDate"));
                            chef.setBankName(dataObject.optString("bankName"));
                            chef.setBankBranchName(dataObject.optString("branch"));
                            chef.setAccountNumber(dataObject.optString("accountNumber"));
                            chef.setIfscCode(dataObject.optString("ifscCode"));
                            chef.setCertificates(dataObject.optString("certificates"));
                            chef.setFssaiNumber(dataObject.optString("fssaiNumber"));
                            chef.setImage(dataObject.optString("uploadLogo"));
                            objectList.add(chef);
                            chefList.add(chef);

                        }
                        allListAdapter = new AllListAdapter(getContext(), objectList, fontBold, fontRegular);
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
                        chefs();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error", "TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error", "AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("No Chefs!");
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
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chefs_taq");

    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
