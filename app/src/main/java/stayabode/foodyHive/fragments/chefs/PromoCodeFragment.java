package stayabode.foodyHive.fragments.chefs;

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
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.HomeAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.PromoCodes;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class PromoCodeFragment extends Fragment {

    RecyclerView recyclerView;
    Typeface fontBold;
    Typeface fontRegular;
    Typeface raleWayFontBold;
    Typeface ralewayFontRegular;

    TextView title;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promocodes,container,false);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        raleWayFontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");
        ralewayFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
        cheftoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        title=view.findViewById(R.id.title);
        title.setTypeface(fontBold);
        ChefsMainActivity.searchLayout.setVisibility(View.GONE);
        ChefsMainActivity.chefnavigation.setVisibility(View.GONE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.GONE);
        getPromoCodes();
        return view;
    }

    /**
     Promo Code Static Data
     **/
    public void getPromoCodes()
    {
        List<Object> promoCodesList = new ArrayList<>();

        for (int i=0;i<3;i++)
        {
            PromoCodes promoCodes = new PromoCodes();
            promoCodes.setDiscount("15");
            promoCodes.setPromoCodeText("SDSFFS");
            promoCodes.setStartDate("12 Jul 2020");
            promoCodes.setEndDate("13 Jul 2020");
            promoCodesList.add(promoCodes);
        }

        recyclerView.setAdapter(new HomeAdapter(getContext(),promoCodesList,fontBold,fontRegular,raleWayFontBold,ralewayFontRegular));


    }
    /**
     Custom Method to go back to previous fragments
     **/
    public void onBackPressed()
    {
        FragmentManager fm = cheffragmentManager;
        fm.popBackStack();
    }
    /**
     PromoCodes with Dynamic Data(GET)
     **/
    public void getChefsPromoCodes()
    {
        String url = APIBaseURL.chefsGETPromoCodes+ SaveSharedPreference.getLoggedInWorkFlowID(getContext());

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = new JSONArray();
                    if(jsonObject.has("data"))
                    {
                        dataArray = jsonObject.getJSONArray("data");
                    }
                    if(dataArray.length()!=0)
                    {

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
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"chefs_promo_taq");
    }
}
