package stayabode.foodyHive.adapters.consumers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.LocationSelectionActivity;
import stayabode.foodyHive.activities.consumers.ChangeLocationActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Search;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static stayabode.foodyHive.activities.consumers.LocationSelectionActivity.latitude;
import static stayabode.foodyHive.activities.consumers.LocationSelectionActivity.longitude;
import static stayabode.foodyHive.activities.consumers.LocationSelectionActivity.mAutoCompleteList;
import static stayabode.foodyHive.activities.consumers.LocationSelectionActivity.places_list_rL;
import static stayabode.foodyHive.activities.consumers.LocationSelectionActivity.searchText;

public class SearchPlaceAdapter extends RecyclerView.Adapter<SearchPlaceAdapter.MyViewHolder> {

    private List<Search> list;
    Context context;
    int lastPosition = -1;
    String from = "";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView place_name, place_detail;


        public MyViewHolder(View view) {
            super(view);
            place_name = (TextView) view.findViewById(R.id.place_name);
            place_detail = (TextView) view.findViewById(R.id.place_detail);
        }
    }


    public SearchPlaceAdapter(List<Search> list, Context context,String from) {
        this.list = list;
        this.context = context;
        this.from = from;
    }


    @Override
    public SearchPlaceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.autocomplete_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchPlaceAdapter.MyViewHolder holder, final int position) {
        holder.place_name.setText(list.get(position).getName());
        holder.place_detail.setText(list.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(from.equals("location"))
                {

                    getPlacesLatAndLongAPI(list.get(position).getName(),holder.itemView);


                    searchText.setText(list.get(position).getName());
                    places_list_rL.setVisibility(View.GONE);
                    mAutoCompleteList.setVisibility(View.GONE);

                    InputMethodManager imm = (InputMethodManager) holder.itemView.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.itemView.getWindowToken(), 0);

                    LocationSelectionActivity.hideKeyboard();
                }
                else
                {
                    getPlacesLatAndLongAPI(list.get(position).getName(),holder.itemView);


                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     Get Google Places Address From API(GET)
     **/
    public void getPlacesLatAndLongAPI(String name,View view)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Fetching Address");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = APIBaseURL.getLatLongAPIFromAddress+name.replaceAll(" ","");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");


                    if(from.equals("change"))
                    {
                        latitude = dataObject.optString("latitude");
                        longitude = dataObject.optString("longitude");

                        checkServiceAvailableorNotAvailable(name,latitude,longitude);
                    }
                    else
                    {
                        latitude = dataObject.optString("latitude");
                        longitude = dataObject.optString("longitude");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"get_lat_long_fromAddess");
    }


    //check if delivery service is available for the address(POST)
    public void checkServiceAvailableorNotAvailable(String name,String latitude,String longitude) throws JSONException {


        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Checking Services in this Location");
        progressDialog.show();


        String url  = APIBaseURL.checkBusinessServicesAvailability;

        JSONObject inputObject = new JSONObject();
        inputObject.put("latitude",Double.valueOf(latitude));
        inputObject.put("longitude",Double.valueOf(longitude));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Boolean isSuccess = response.optBoolean("isSuccess");
                if(isSuccess)
                {
                    if(response.optString("errorMessage").equals("Service Available"))
                    {
                        SaveSharedPreference.saveLatLong(context,latitude,longitude);
                        SaveSharedPreference.saveAddress(context,name);
                        Intent intent = new Intent();
                        intent.putExtra("MESSAGE", "Added");
                        ((ChangeLocationActivity) context).setResult(102, intent);
                        ((ChangeLocationActivity) context).finish();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setMessage("Sorry! We currently do not service in this area");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Sorry! We currently do not service in this area");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest,"location_lat_longitude_taq");

    }
}