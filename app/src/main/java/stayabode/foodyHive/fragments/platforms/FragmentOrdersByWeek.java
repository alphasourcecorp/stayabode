package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.OrderByWeek;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentOrdersByWeek  extends Fragment {

    TextView pagetitle;
    TextView back;
    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView subHeader;
    TextView weekHeader;
    TextView bulkHeader;
    TextView reatailHeader;
    TextView subscriptionHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders_by_week,container,false);
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
        MainActivity.toolbar_save.setTypeface(fontBold);
        MainActivity.toolbar_save.setText("< Back");
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
        subHeader = rootView.findViewById(R.id.subHeader);
        weekHeader = rootView.findViewById(R.id.weekHeader);
        bulkHeader = rootView.findViewById(R.id.bulkHeader);
        reatailHeader = rootView.findViewById(R.id.reatailHeader);
        subscriptionHeader=rootView.findViewById(R.id.subscriptionHeader);

        pagetitle.setText("Orders");
        pagetitle.setTypeface(fontBold);
        subHeader.setTypeface(fontBold);
        weekHeader.setTypeface(fontBold);
        bulkHeader.setTypeface(fontBold);
        subscriptionHeader.setTypeface(fontBold);
        reatailHeader.setTypeface(fontBold);

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
        getOrdersRevenueByWeek();
        return rootView;
    }

//    public void getOrdersByWeek() {
//        for (int i = 0; i < 7; i++) {
//            OrderByWeek order = new OrderByWeek();
//
//            order.setBulkOrderCount("4");
//            order.setSubscription("120");
//            order.setRetail("5744");
//            objectList.add(order);
//        }
//        recyclerView.setAdapter(new AllListAdapter(getActivity(), objectList, fontBold, fontRegular));
//    }


    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }


    public void getOrdersRevenueByWeek()
    {
        String url = APIBaseURL.franchiseeDashboardOrdersRevenue;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    JSONObject weekDaysFirstObjectSplit = dataArray.getJSONObject(0);
                    JSONObject weekDaysSecondObjectSplit = dataArray.getJSONObject(1);
                    JSONObject weekDaysthirddObjectSplit = dataArray.getJSONObject(2);

                    JSONArray weekDaysFirstSplitArray = weekDaysFirstObjectSplit.getJSONArray("days");
                    JSONArray weekDayssecondSplitArray = weekDaysSecondObjectSplit.getJSONArray("days");
                    JSONArray weekDaysthirdSplitArray = weekDaysthirddObjectSplit.getJSONArray("days");
                    bulkHeader.setText(dataArray.getJSONObject(0).optString("title"));
                    subscriptionHeader.setText(dataArray.getJSONObject(1).optString("title"));
                    reatailHeader.setText(dataArray.getJSONObject(2).optString("title"));
                    for (int i=0;i < weekDaysFirstSplitArray.length() && i < weekDayssecondSplitArray.length() && i < weekDaysthirdSplitArray.length(); i++)
                    {
                        JSONObject weekDaysfirstObject = weekDaysFirstSplitArray.getJSONObject(i);
                        JSONObject weekDayssecondObject = weekDayssecondSplitArray.getJSONObject(i);
                        JSONObject weekDaysthirdObject = weekDaysthirdSplitArray.getJSONObject(i);
                        OrderByWeek order = new OrderByWeek();
                        order.setDay(weekDaysfirstObject.optString("Name"));
                        order.setBulkOrderCount(weekDaysfirstObject.optString("Amount"));
                        order.setSubscription(weekDayssecondObject.optString("Amount"));
                        order.setRetail(weekDaysthirdObject.optString("Amount"));
                        objectList.add(order);
                    }
                    recyclerView.setAdapter(new AllListAdapter(getActivity(), objectList, fontBold, fontRegular));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"orders_week_taq");
    }
}
