package stayabode.foodyHive.activities.consumers;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import  stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.SearchPlaceAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Search;
import stayabode.foodyHive.utils.ApplicationController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangeLocationActivity extends AppCompatActivity {

    public static AutoCompleteTextView changesearchText;
    List<Search> stringArrayList= new ArrayList<>();
    RelativeLayout places_list_rL;
    RecyclerView mAutoCompleteList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_location);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarColor));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Location Search");
        changesearchText = findViewById(R.id.searchText);
        mAutoCompleteList=(RecyclerView) findViewById(R.id.recyclerView);
        places_list_rL=(RelativeLayout) findViewById(R.id.places_list_rL);
        mAutoCompleteList.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteList.setVisibility(View.GONE);

        changesearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                stringArrayList = new ArrayList<>();
                if(changesearchText.getText().toString().trim().equals(""))
                {

                    places_list_rL.setVisibility(View.GONE);
                    mAutoCompleteList.setVisibility(View.GONE);


                }
                else {
                    places_list_rL.setVisibility(View.VISIBLE);
                    mAutoCompleteList.setVisibility(View.VISIBLE);

                }
                ApplicationController.getInstance().getRequestQueue().cancelAll("volleyPlaces");

                mAutoCompleteList.setAdapter(new SearchPlaceAdapter(stringArrayList, ChangeLocationActivity.this,"change"));


                /**
                 To Search Google Places Address For Service Availability(GET)
                 **/

                String url = APIBaseURL.getPlacesAddress+changesearchText.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray resultArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < resultArray.length(); i++) {
                                JSONObject resultObject = resultArray.getJSONObject(i);
                                String value=resultArray.optString(i);

                                Log.e("jsonSearch", i+"="+value);
                                Search search = new Search();
                                search.setAddress(resultObject.optString("description"));
                                search.setName(resultObject.optString("description"));
                                stringArrayList.add(search);
                            }

                            mAutoCompleteList.setAdapter(new SearchPlaceAdapter(stringArrayList,ChangeLocationActivity.this,"change"));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                ApplicationController.getInstance().addToRequestQueue(stringRequest,"volleyPlaces");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    /**
     To Back Press from the Top Toolbar back Arrow
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                    finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
