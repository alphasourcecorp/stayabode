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



import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.User;
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


public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView toolbar_title;
    TextView pagetitle;
    TextView back;
    CardView cardView;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout noInternetLayout;
    Button retryBtn;
    TextView text;
    ImageView noInternetImage;
    ImageView searchIcon;
    LinearLayout searchLayout;
    TextView search;
    AllListAdapter allListAdapter;
    List<User> userList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user, container, false);
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
        text = rootView.findViewById(R.id.text);
        noInternetImage = rootView.findViewById(R.id.noInternetImage);
        searchIcon = rootView.findViewById(R.id.searchIcon);
        searchLayout = rootView.findViewById(R.id.searchLayout);
        search = rootView.findViewById(R.id.search);
        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("Manage Users");
        pagetitle.setTypeface(fontBold);
        cardView = rootView.findViewById(R.id.cardView);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerLayout);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        noInternetLayout.setVisibility(View.GONE);
        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

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


        users();
        return rootView;
    }

    //dummy data
    public void getUsers() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserName("User Name");
            user.setCreatedDate("Created date - 12 June 2019");
            user.setActiveStatus("Active");
            objectList.add(user);
        }
        recyclerView.setAdapter(new AllListAdapter(getActivity(), objectList, fontBold, fontRegular));
    }


    void filter(String text) {
        List<Object> temp = new ArrayList<>();
        String searchText = text.toLowerCase();
        for (User user : userList) {
            String roleName = user.getUserName().toLowerCase();
            if (roleName.contains(searchText)) {
                temp.add(user);
            }
        }
        allListAdapter.updateList(temp);
    }


    //to-do
    public void users()
    {
        String url = APIBaseURL.usersInfo;
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if(isSuccess)
                    {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        objectList.clear();
                        userList = new ArrayList<>();
                        for (int i=0;i < dataArray.length();i++)
                        {
                            JSONObject userDataObject = dataArray.getJSONObject(i);
                            User user = new User();
                            user.setUserName(userDataObject.optString("name"));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            DateFormat createdDate = new SimpleDateFormat("dd MMMM yyyy");

                            try {
                                // Date createddate = null;
                                Date createddate = sdf.parse(userDataObject.optString("createdDate"));

                                user.setCreatedDate("Created date - "+createdDate.format(createddate));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            user.setActiveStatus(userDataObject.optString("status"));
                            user.setImage(userDataObject.optString("profilePic"));
                            objectList.add(user);
                            userList.add(user);
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
                        users();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error", "TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error", "AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("No Roles!");
//                    retryBtn.setVisibility(View.GONE);
//
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
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"user_taq");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
//        BottomNavigationView navBar = getActivity().findViewById(R.id.navigation);
//        navBar.setVisibility(View.GONE);
//        navigation.setVisibility(View.GONE);
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}