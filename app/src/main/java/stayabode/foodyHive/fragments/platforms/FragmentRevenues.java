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
import stayabode.foodyHive.models.Revenues;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentRevenues extends Fragment {

    TextView pagetitle;
    TextView back;
    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView dateHeader;
    TextView yearHeader;
    TextView secondYearHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_revenues,container,false);
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
        dateHeader = rootView.findViewById(R.id.dateHeader);
        yearHeader = rootView.findViewById(R.id.yearHeader);
        secondYearHeader = rootView.findViewById(R.id.secondYearHeader);
        pagetitle.setText("Revenues");
        pagetitle.setTypeface(fontBold);
        dateHeader.setTypeface(fontBold);
        yearHeader.setTypeface(fontBold);
        secondYearHeader.setTypeface(fontBold);

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
        getRevenues();
        return rootView;
    }

    public void getRevenues()
    {
        String url = APIBaseURL.revenueByYear;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");

//                    for (int i=0;i < dataArray.length();i++)
//                    {
                       // JSONObject dataObject = dataArray.getJSONObject(i);
//                        Iterator iterator = dataObject.keys();
//                        while(iterator.hasNext()){
//                            String key = (String)iterator.next();
//                            JSONObject issue = dataObject.getJSONObject(key);

                            //  get id from  issue
                           // String _pubKey = issue.optString("Name");
//                            Log.d("PubKey",key);

                        JSONArray  monthArray = dataArray.getJSONObject(0).getJSONArray("month");
                        JSONArray secondMonthArray = dataArray.getJSONObject(1).getJSONArray("month");

                        yearHeader.setText(dataArray.getJSONObject(0).optString("year"));
                        secondYearHeader.setText(dataArray.getJSONObject(1).optString("year"));

                        for (int j=0;j<monthArray.length() && j<secondMonthArray.length();j++)
                        {
                            JSONObject monthObject = new JSONObject();
                            JSONObject secondMonthObject = new JSONObject();
                            Revenues revenues = new Revenues();


                            monthObject = monthArray.getJSONObject(j);
                            revenues.setMonthName(monthObject.optString("Name"));
                            revenues.setYearAmount(monthObject.optString("Amount"));
                            revenues.setNineteenYear(monthObject.optString("year"));



                            secondMonthObject = secondMonthArray.getJSONObject(j);
                            revenues.setTwentyYearAmount(secondMonthObject.optString("Amount"));
                            revenues.setTwentyYear(secondMonthObject.optString("year"));


                            objectList.add(revenues);
                        }


                        recyclerView.setAdapter(new AllListAdapter(getContext(),objectList,fontBold,fontRegular));


//                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"revenue_taq");
    }
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
