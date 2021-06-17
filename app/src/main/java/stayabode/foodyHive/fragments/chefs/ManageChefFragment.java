package stayabode.foodyHive.fragments.chefs;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.HomeAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Chef;
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

public class ManageChefFragment extends Fragment {

    Typeface fontBold;
    Typeface fontRegular;
    Typeface raleWayFontBold;
    Typeface ralewayFontRegular;

    TextView header;
    RecyclerView recyclerView;

    List<Object> objectList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chefs_menu_items, container, false);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        raleWayFontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");
        ralewayFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");

        header = rootView.findViewById(R.id.header);
        recyclerView = rootView.findViewById(R.id.recyclerViewChef);
        recyclerView.setVisibility(View.VISIBLE);
        ChefsMainActivity.searchLayout.setVisibility(View.GONE);
        ChefsMainActivity.chefnavigation.setVisibility(View.GONE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.GONE);
        cheftoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        header.setText("Manage Chef");
        header.setTextSize(22);
        header.setTypeface(fontBold);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getChefDeatils();

        return rootView;
    }
    /**
     Custom Method to go back to previous fragment
     **/
    public void onBackPressed()
    {
        FragmentManager fm = cheffragmentManager;
        fm.popBackStack();
    }

    /**
     Get Chef Full Profile(GET)
     **/
    private void getChefDeatils() {
        String url = APIBaseURL.chefsURL+ "/"+ SaveSharedPreference.getLoggedInWorkFlowID(getContext());

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    objectList.clear();
                    for (int i=0;i < dataArray.length();i++)
                    {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        Chef chef=new Chef();
                        chef.setName(dataObject.optString("name"));
                        chef.setImage(dataObject.optString("uploadLogo"));
                        JSONObject locationObject = new JSONObject();
                        if(dataObject.has("location"))
                        {
                            locationObject = dataObject.getJSONObject("location");
                        }
                        chef.setLocation(locationObject.optString("name"));
                        chef.setRatingAverage(Float.parseFloat(dataObject.optString("rating")));
                        objectList.add(chef);
                    }

                    recyclerView.setAdapter(new HomeAdapter(getContext(),objectList,fontBold,fontRegular,raleWayFontBold,ralewayFontRegular));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"manage_chef");
    }
}
