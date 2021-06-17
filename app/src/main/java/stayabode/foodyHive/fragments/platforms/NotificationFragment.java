package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
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
import stayabode.foodyHive.models.NotificationsLists;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;


    Typeface fontBold;
    Typeface fontRegular;
    List<Object> objectList = new ArrayList<>();
    TextView pagetitle;

    CardView cardView;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout noInternetLayout;
    Button retryBtn;

    TextView text;
    ImageView noInternetImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_dehaze_black);
        MainActivity.customIcon.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.active = this;
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");

        text=rootView.findViewById(R.id.text);
        noInternetImage=rootView.findViewById(R.id.noInternetImage);

        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setTypeface(fontBold);
        cardView = rootView.findViewById(R.id.cardView);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerLayout);
        noInternetLayout = rootView.findViewById(R.id.noInternetLayout);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        noInternetLayout.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        notifications();

        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);
//        BottomNavigationView navBar = getActivity().findViewById(R.id.navigation);
//        navBar.setVisibility(View.VISIBLE);
//        navigation.setVisibility(View.VISIBLE);
    }

    public void notifications()
    {
        String url = APIBaseURL.notifications;
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
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    objectList = new ArrayList<>();
                    objectList.clear();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject notificationObject = dataArray.getJSONObject(i);
                        NotificationsLists notification = new NotificationsLists();
                        notification.setTitle(notificationObject.optString("title"));
                        notification.setStatus(notificationObject.optString("flag"));
                        notification.setDescription(notificationObject.optString("descrpition"));
                        objectList.add(notification);
                    }
                    recyclerView.setAdapter(new AllListAdapter(getActivity(), objectList, fontBold, fontRegular));
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
                        notifications();
                    }
                });
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthError");
//                } else if (error instanceof ServerError) {
//                    noInternetImage.setVisibility(View.GONE);
//                    text.setText("No Notifications!");
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
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"user_taq");
    }
}
