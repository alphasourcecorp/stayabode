package stayabode.foodyHive.fragments.chefs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.ChefsOrdersAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.NotificationsLists;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefDrawer;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class ChefNotificationFragment extends Fragment {

    RecyclerView recyclerView;
    Typeface fontBold;
    Typeface fontRegular;
    TextView clearText;
    NestedScrollView scrollView;
    ProgressBar progressBar;

    List<Object> notificationList=new ArrayList<>();
    List<NotificationsLists> searchnotificationList=new ArrayList<>();
    ChefsOrdersAdapter chefsOrdersAdapter;

    int index = 0;
    int size = 20;

    EditText search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chef_notification, container, false);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        recyclerView = view.findViewById(R.id.recyclerView);
        clearText = view.findViewById(R.id.clearText);
        scrollView=view.findViewById(R.id.scrollView);
        progressBar=view.findViewById(R.id.progressBar);
        search=view.findViewById(R.id.search);

        clearText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        ChefsMainActivity.chefnavigation.setVisibility(View.VISIBLE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        cheftoolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chefDrawer.openDrawer(Gravity.LEFT);
            }
        });


      /*  SwipeHelper swipeHelper = new SwipeHelper(getContext()) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(getContext(),
                        "Delete", R.drawable.delete_icon, Color.parseColor("#C6C6C6"),
                        new SwipeHelper.UnderlayButtonClickListener() {

                            @Override
                            public void onClick(int pos) {
                                Object item = chefsOrdersAdapter.getData().get(pos);
//                                chefsOrdersAdapter.removeItem(pos);
                            }
                        }
                ));
            }
        };
        swipeHelper.attachToRecyclerView(recyclerView);
*/

        getChefNotifications();
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    index++;
                    getChefNotifications();
                }
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

        return view;
    }




    public void onBackPressed() {
        FragmentManager fm = cheffragmentManager;
        fm.popBackStack();
    }

    void filter(String text) {
        List<Object> temp = new ArrayList<>();
        String searchText=text.toLowerCase();
        for (NotificationsLists notificationsLists : searchnotificationList) {
            String chefName = notificationsLists.getTitle().toLowerCase();
            if (chefName.contains(searchText)) {
                temp.add(notificationsLists);
            }
        }
        chefsOrdersAdapter.updateList(temp);
    }

    public void getChefNotifications()
    {
        String url = APIBaseURL.getChefsNotifications+ SaveSharedPreference.getLoggedInWorkFlowID(getContext())+"&pageno="+index+"&pagesize="+size;

        clearText.setVisibility(View.GONE);

        if(index>0){
            progressBar.setVisibility(View.VISIBLE);
        }

        CustomVolleyRequest customVolleyRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(jsonObject.has("data"))
                {
                    try {
                        JSONObject dataJsonObject=jsonObject.getJSONObject("data");
                        JSONArray dataArray = dataJsonObject.getJSONArray("notificationlist");

                        if(dataArray.length()>0) {
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataObject = dataArray.getJSONObject(i);
                                NotificationsLists notificationsLists = new NotificationsLists();
                                notificationsLists.setTitle(dataObject.getString("title"));
                                notificationsLists.setDescription(dataObject.getString("descrpition"));
                                notificationsLists.setImage("1");
                                notificationList.add(notificationsLists);
                                searchnotificationList.add(notificationsLists);
                            }
                            chefsOrdersAdapter = new ChefsOrdersAdapter(getContext(), notificationList, fontBold, fontRegular);
                            recyclerView.setAdapter(chefsOrdersAdapter);
                            if (recyclerView.getAdapter().getItemCount() == 0) {
                                clearText.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                clearText.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }else{
                            try {
                                Toast.makeText(getContext(),"No notifications",Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    clearText.setVisibility(View.VISIBLE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                clearText.setVisibility(View.VISIBLE);

                //do stuff with the body...
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                } else if (error instanceof AuthFailureError) {
                    //TODO

                } else if (error instanceof ServerError) {
                    //TODO

                } else if (error instanceof NetworkError) {
                    //TODO

                } else if (error instanceof ParseError) {
                    //TODO

                }
            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(customVolleyRequest);
    }
}
