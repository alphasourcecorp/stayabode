package stayabode.foodyHive.corporatemenurange;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.FulllistItemdisplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class CorporatebussinessInformationSubmit extends AppCompatActivity {

 Button order_subscription_id;
 EditText company_name_id,name_id,email_id,phone_id,address_id,city_id,postal_id,addressline1,addressline2,state_id,country_id,promocode_id;
 TextView date_id,hour_id,month_id,sub_total_id,total_id,sub_total_id3;
 ListView navLV;
    double full_totalvalue=0;
    String send_todayAsString="",send_tomorrowAsString="";
    TextView call_id;
    private ProgressDialog pDialog;
    RecyclerView excludes_recycler_view;

    double sub_totalvalue=0;
    double all_totalvalue=0;
    CheckBox applypromocode_chk;
    JSONArray sI_jsonarray = new JSONArray();
    ImageView back_id;
    EditText notes;


    Spinner stateSpinner,countrySpinner;
    String state_str="",country_str="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_business_information);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
         excludes_recycler_view = findViewById(R.id.excludes_recycler_view);
        GetscheduleCount();
        company_name_id=findViewById(R.id.company_name_id);
        name_id=findViewById(R.id.name_id);
        email_id=findViewById(R.id.email_id);
        phone_id=findViewById(R.id.phone_id);
        address_id=findViewById(R.id.address_id);
        city_id=findViewById(R.id.city_id);
        postal_id=findViewById(R.id.postal_id);

        date_id=findViewById(R.id.date_id);
        hour_id=findViewById(R.id.hour_id);
        month_id=findViewById(R.id.month_id);
        sub_total_id=findViewById(R.id.sub_total_id);
        sub_total_id3=findViewById(R.id.sub_total_id3);
        total_id=findViewById(R.id.total_id);
        notes=findViewById(R.id.notes);

        applypromocode_chk=(CheckBox)findViewById(R.id.applypromocode_chk);
        promocode_id=(EditText)findViewById(R.id.promocode_id);
        back_id=(ImageView) findViewById(R.id.back_id);
        back_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addressline1=findViewById(R.id.addressline1);
        addressline2=findViewById(R.id.addressline2);
        state_id=findViewById(R.id.state_id);
        country_id=findViewById(R.id.country_id);


        call_id=findViewById(R.id.call_id);
        call_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91 6366225334"));
                startActivity(intent);
            }
        });












         stateSpinner = findViewById(R.id.stateSpinner);
        ArrayList<String> spinnerList = new ArrayList<>();
        //spinnerList.clear();
        //spinnerList.add("Select State");


        countrySpinner = findViewById(R.id.countrySpinner);
        ArrayList<String> spinnerList2 = new ArrayList<>();
        //spinnerList.clear();
        spinnerList2.add("India");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CorporatebussinessInformationSubmit.this, R.layout.support_simple_spinner_dropdown_item, spinnerList2);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        countrySpinner.setAdapter(arrayAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = countrySpinner.getSelectedItemPosition();

                country_str = spinnerList2.get(pos);
                country_id.setText(country_str);
                //  prepareViewager(viewPager,arrayList);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        spinnerList.add("Andhra Pradesh" );
        spinnerList.add("Arunachal Pradesh");
        spinnerList.add("Assam");
        spinnerList.add("Bihar");
        spinnerList.add("Chhattisgarh");
        spinnerList.add("Goa");
        spinnerList.add("Gujarat");
        spinnerList.add("Haryana");
        spinnerList.add("Himachal Pradesh");
        spinnerList.add("Jharkhand");
        spinnerList.add("Karnataka");
        spinnerList.add("Kerala");
        spinnerList.add("Madhya Pradesh");
        spinnerList.add("Maharashtra");
        spinnerList.add("Manipur");
        spinnerList.add("Meghalaya");
        spinnerList.add("Mizoram");
        spinnerList.add("Nagaland");
        spinnerList.add("Odisha");
        spinnerList.add("Punjab");
        spinnerList.add("Rajasthan");
        spinnerList.add("Sikkim");
        spinnerList.add("Tamil Nadu");
        spinnerList.add("Telangana");
        spinnerList.add("Tripura");
        spinnerList.add("Uttarakhand");
        spinnerList.add("Uttar Pradesh");
        spinnerList.add("West Bengal");
        spinnerList.add("Andaman and Nicobar Islands");
        spinnerList.add("Chandigarh");
        spinnerList.add("Dadra and Nagar Haveli and Daman & Diu");
        spinnerList.add("The Government of NCT of Delhi");
        spinnerList.add("Jammu & Kashmir");
        spinnerList.add("Ladakh");
        spinnerList.add("Lakshadweep");
        spinnerList.add("Puducherry");


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(CorporatebussinessInformationSubmit.this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        stateSpinner.setAdapter(arrayAdapter2);
        //stateSpinner.setSelection(10);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = stateSpinner.getSelectedItemPosition();


                state_str = spinnerList.get(pos);
                state_id.setText(state_str);

                //  prepareViewager(viewPager,arrayList);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });











        SharedPreferences sh = getSharedPreferences("corporateShare", MODE_APPEND);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String addcart_share = sh.getString("addcart", "");
        String companyname_share = sh.getString("companyname", "");
        String name_share = sh.getString("name", "");
        String email_share = sh.getString("email", "");
        String phone_share = sh.getString("phone", "");
        String fulladdress_share = sh.getString("fulladdress", "");
        String city_share = sh.getString("city", "");
        String postalcode_share = sh.getString("postalcode", "");




        SharedPreferences sharedPreferences = getSharedPreferences("corporateLogin",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        String companyId = sharedPreferences.getString("companyId", "");
        String companyName = sharedPreferences.getString("companyName", "");
        String subscriberName = sharedPreferences.getString("subscriberName", "");
        String emailId = sharedPreferences.getString("emailId", "");
        String phoneNumber = sharedPreferences.getString("phoneNumber", "");
        String addressLine1 = sharedPreferences.getString("addressLine1", "");
        String addressLine2 = sharedPreferences.getString("addressLine2", "");
        String city = sharedPreferences.getString("city", "");
        String state = sharedPreferences.getString("state", "");
        String country = sharedPreferences.getString("country", "");
        String postalCode = sharedPreferences.getString("postalCode", "");
        String token = sharedPreferences.getString("token", "");

        addressline1.setText(addressLine1);
        addressline2.setText(addressLine2);
        city_id.setText(city);
        postal_id.setText(postalCode);

        for(int i=0;i<spinnerList.size();i++)
        {
            if(spinnerList.get(i).equals(state))
            {
                stateSpinner.setSelection(i);
                state_str = spinnerList.get(i);
            }
        }
        for(int i=0;i<spinnerList2.size();i++)
        {
            if(spinnerList2.get(i).equals(country))
            {
                countrySpinner.setSelection(i);
                country_str = spinnerList2.get(i);
            }
        }


        state_id.setText(state_str);
        country_id.setText(country_str);


//        company_name_id.setText(companyname_share);
//        name_id.setText(name_share);
//        email_id.setText(email_share);
//        phone_id.setText(phone_share);
//        address_id.setText(fulladdress_share);
//        address_id.setText(fulladdress_share);
//
//
//        city_id.setText(city_share);
//        postal_id.setText(postalcode_share);


        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        send_todayAsString = df.format(today);
        send_tomorrowAsString = df.format(tomorrow);

        String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);



        if(Globaluse.today_tomo.equals("Tomorrow"))
        {
            date_id.setText(Globaluse.today_tomo+" "+tomorrowAsString);
        }else
        {
            date_id.setText(Globaluse.today_tomo+" "+todayAsString);
        }

        hour_id.setText(Globaluse.deliver_time);
        month_id.setText(Globaluse.only_Monthly_week);




        applypromocode_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                           if(isChecked)
                                                           {
                                                               promocode_id.setVisibility(View.VISIBLE);


                                                           }
                                                           else
                                                           {
                                                               promocode_id.setVisibility(View.GONE);


                                                           }

                                                       }
                                                   }
        );




        order_subscription_id=findViewById(R.id.order_subscription_id);
        order_subscription_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String company_name_str=company_name_id.getText().toString().trim();
                String name_str=name_id.getText().toString().trim();
                String email_str=email_id.getText().toString().trim();
                String phone_str=phone_id.getText().toString().trim();
                String address_str=address_id.getText().toString().trim();
                String city_str=city_id.getText().toString().trim();
                String postal_str=postal_id.getText().toString().trim();



                String addressline1_str=addressline1.getText().toString().trim();
                String addressline2_str=addressline2.getText().toString().trim();
                String city_id_str=city_id.getText().toString().trim();
                String postal_id_str=postal_id.getText().toString().trim();
                String state_id_str=state_id.getText().toString().trim();
                String country_id_str=country_id.getText().toString().trim();
                String promocode_id_str=promocode_id.getText().toString().trim();

                if (validatenew(addressline1_str, addressline2_str, city_id_str,postal_id_str,state_id_str,country_id_str)) {
                    pDialog = ProgressDialog.show(CorporatebussinessInformationSubmit.this, "", "Please wait...", true,false);
                    JSONObject inputObject = new JSONObject();
                    JSONArray dA_jsonarray = new JSONArray();


                    try {
                        inputObject.put("companyId",companyId);
                        inputObject.put("companyName",companyName);
                        inputObject.put("subscriberName",subscriberName);
                        inputObject.put("emailId",emailId);
                        inputObject.put("phoneNumber",phoneNumber);


                        inputObject.put("notes",notes.getText().toString());

                        JSONObject dA_jsonobj = new JSONObject();
                        dA_jsonobj.put("addressLine1",addressline1_str);
                        dA_jsonobj.put("addressLine2",addressline2_str);
                        dA_jsonobj.put("city",city_id_str);
                        dA_jsonobj.put("state",state_id_str);
                        dA_jsonobj.put("country",country_id_str);
                        dA_jsonobj.put("postalCode",postal_id_str);
                        dA_jsonarray.put(dA_jsonobj);


                        inputObject.put("deliveryAddresses",dA_jsonarray);

                        Calendar calendar = Calendar.getInstance();
                        Date today = calendar.getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                        df.setTimeZone(TimeZone.getTimeZone("UTC"));
                        send_todayAsString = df.format(today);

                        inputObject.put("subscriptionDate",send_todayAsString);
                        inputObject.put("schedullingInfo",sI_jsonarray);
                        inputObject.put("promoCode",promocode_id_str);
                        inputObject.put("totalOrderValue",sub_totalvalue);
                        inputObject.put("status","Request");



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();

                    String URL = APIBaseURL.BASEURLLINK_B2B_SB_REQUEST;
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, inputObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pDialog.dismiss();
                               // Toast.makeText(CorporatebussinessInformation.this, response.optString("errorMessage"), Toast.LENGTH_SHORT).show();

                                try {
                                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                                    // jsonObject.getString("isSuccess");

                                    if(jsonObject.getString("isSuccess").equals("true"))
                                    {
                                       // pDialog.dismiss();
                                        corporateshared("no");
                                        final Dialog dialog = new Dialog(CorporatebussinessInformationSubmit.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.corporate_sucess);

                                        Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);

                                        subscribe_id.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.cancel();
                                                finishAffinity();
                                                Intent intent = new Intent(CorporatebussinessInformationSubmit.this, CorporateMenuWithNavigatioin.class);
                                                startActivity(intent);

                                            }
                                        });
                                        dialog.show();

                                    }else
                                    {
                                       // pDialog.dismiss();
                                        corporateshared("yes");
                                        final Dialog dialog = new Dialog(CorporatebussinessInformationSubmit.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.corporate_fail);

                                        Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                                        TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                                        errormessage_id.setText(jsonObject.getString("errorMessage"));

                                        subscribe_id.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.cancel();

                                            }
                                        });
                                        dialog.show();

                                        TextView call_id = (TextView) dialog.findViewById(R.id.call_id);
                                        call_id.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.cancel();
                                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                                intent.setData(Uri.parse("tel:+91 6366225334"));
                                                startActivity(intent);
                                            }
                                        });
                                    }



                                } catch (JSONException e) {
                                    pDialog.dismiss();
                                    e.printStackTrace();
                                }





                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();


                                corporateshared("yes");
                                final Dialog dialog = new Dialog(CorporatebussinessInformationSubmit.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.corporate_fail);

                                Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                                TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                                errormessage_id.setText("sorry something went wrong..");

                                subscribe_id.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                dialog.show();

                                TextView call_id = (TextView) dialog.findViewById(R.id.call_id);
                                call_id.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:+91 6366225334"));
                                        startActivity(intent);
                                    }
                                });



//                                if (error instanceof AuthFailureError) {
//
//                            }

                            }
                        }){    //this is the part, that adds the header to the request
                            @Override
                            public Map<String, String> getHeaders() {
                                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                                String token = sh.getString("token", "");
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Authorization", "Bearer " + token);
                                //params.put("content-type", "application/json");
                                return params;
                            }
                        } ;
                        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);



                }


            }
        });



        for (int k=0;k<Globaluse.corporatedishlistnew.size();k++)
        {

            try {
                JSONObject jsonObject = new JSONObject(Globaluse.corporatedishlistnew.get(k));
                double noofUnits= Integer.parseInt(jsonObject.getString("noOfUnits"));
                double cost= Integer.parseInt(jsonObject.getString("cost"));
                double totalvalue=(cost * noofUnits);
                full_totalvalue=full_totalvalue+totalvalue;
            }catch (JSONException err){
                Log.d("Error", err.toString());
            }

        }


//        sub_total_id.setText(""+full_totalvalue);
//        total_id.setText(""+full_totalvalue);


        navLV = findViewById(R.id.navLV);
       CustomAdapter adapter = new CustomAdapter(this,  Globaluse.corporatedishlistnew);
        navLV.setAdapter(adapter);
        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);
    }




    class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> items;

        public CustomAdapter(Context context, ArrayList<String> items) {
            this.context = context;
            this.items = items;
        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_expand_dish_list_row_, null);

            ImageView deleterecord = v.findViewById(R.id.deleterecord);
            TextView dish_id = v.findViewById(R.id.dish_id);
            TextView dish_multipleprice = v.findViewById(R.id.dish_multipleprice);






            //expListView.setSelection(1);
            //notifyDataSetChanged();
            try {
                JSONObject jsonObject = new JSONObject(items.get(i));

                dish_id.setText(jsonObject.getString("noOfUnits")+"X "+jsonObject.getString("dishItemName"));

                double noofUnits= Integer.parseInt(jsonObject.getString("noOfUnits"));
                double cost= Integer.parseInt(jsonObject.getString("cost"));

                double totalvalue=(cost * noofUnits);

                dish_multipleprice.setText(jsonObject.getString("noOfUnits")+"X "+jsonObject.getString("dishItemName"));
               // dish_multipleprice.setText("Rs. "+totalvalue);

                if(totalvalue==0.0)
                {
                    dish_multipleprice.setText("Rs. "+" TBD ");
                }else
                {
                    dish_multipleprice.setText("Rs. "+totalvalue);
                }


            }catch (JSONException err){
                Log.d("Error", err.toString());
            }


            deleterecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    JSONObject jsonObject_old = null;
                    try {
                        jsonObject_old = new JSONObject(items.get(i));
                        String dishItemName=jsonObject_old.getString("dishItemName");
                        String cost=jsonObject_old.getString("cost");


                        Globaluse.corporatedishlistnew=new  ArrayList<String>();
                        for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
                        {
                            for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
                            {
                                final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(childText);

                                    if(jsonObject.getString("dishName").equals(dishItemName))
                                    {
                                        //if(jsonObject.getString("saleAmount").equals(dishItemName))
                                        //{
                                        try {
                                            jsonObject.put("buttondisplay", false);
                                            jsonObject.put("quantity", 0);
                                            updatejson(c,t,String.valueOf(jsonObject));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        //}

                                    }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }







                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            });

            //text.setText(items.get(i));
            //event handling code can be done here
            return v;
        }
    }






    public void updatejson(int groupPosition, int childPosititon,String updatejsondat) {

        corporateshared("yes");

        List<String> dishlistdata = new ArrayList<String>();
        for (int m = 0; m < Globaluse._listDataChild.get(Globaluse._listDataHeader.get(groupPosition)).size(); m++) {

            if(childPosititon==m)
            {
                dishlistdata.add(updatejsondat);
            }else
            {
                dishlistdata.add(String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(groupPosition)).get(m)));
            }

        }
        Globaluse._listDataChild.put(Globaluse._listDataHeader.get(groupPosition),dishlistdata);

//        Globaluse._listDataHeader=new ArrayList<String>();
//        Globaluse._listDataChild=new HashMap<String, List<String>>();
        Globaluse._listDataHeader=new ArrayList<String>(Globaluse._listDataHeader);
        Globaluse._listDataChild=new HashMap<String, List<String>>(Globaluse._listDataChild);


        Globaluse.corporatedishlistnew=new  ArrayList<String>();
        for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
        {
            for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
            {
                final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(childText);
                    if(jsonObject.getBoolean("buttondisplay"))
                    {
                        JSONObject dishDetail = new JSONObject();
                        try {
                            dishDetail.put("dishItemName", jsonObject.getString("dishName"));
                            dishDetail.put("description", jsonObject.getString("dishDescription"));
                            dishDetail.put("cost", jsonObject.getString("saleAmount"));
                            dishDetail.put("noOfUnits", jsonObject.getString("quantity"));
                            dishDetail.put("taxValue", 0);
                            dishDetail.put("packingCharges", 0);
                            dishDetail.put("deliveryCharges", 0);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Globaluse.corporatedishlistnew.add(String.valueOf(dishDetail));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);


    }








    private boolean validate(String company_name,String name,String email,  String phone,String address,String city,String postal) {
        if (company_name.equals("") || name.equals("") ||!isValidEmail(email) ||!isValidMobile(phone)  ||address.equals("") || city.equals("")||!isValidPostal(postal)) {

//            company_name_id=findViewById(R.id.company_name_id);
//            name_id=findViewById(R.id.name_id);
//            email_id=findViewById(R.id.email_id);
//            phone_id=findViewById(R.id.phone_id);
//            address_id=findViewById(R.id.address_id);
//            city_id=findViewById(R.id.city_id);
//            postal_id=findViewById(R.id.postal_id);
//

            if (company_name.equals(null) || company_name.equals("")) {
                company_name_id.setError("Enter a company_name");
            } else {
                company_name_id.setError(null);
            }
            if (name.equals(null) || name.equals("")) {
                name_id.setError("Enter a name");
            } else {
                name_id.setError(null);
            }


            if (!isValidEmail(email)) {
                email_id.setError("Enter valid email id");

            } else {
                email_id.setError(null);

            }


            if (!isValidMobile(phone)) {
                phone_id.setError("Enter a valid Mobile Number");
            } else {
                phone_id.setError(null);
            }

            if (name.equals(null) || name.equals("")) {
                address_id.setError("Enter a address");
            } else {
                address_id.setError(null);
            }

            if (name.equals(null) || name.equals("")) {
                city_id.setError("Enter a city");
            } else {
                city_id.setError(null);
            }

            if (!isValidPostal(postal)) {
                postal_id.setError("Enter a postal code");
            } else {
                postal_id.setError(null);
            }

            return false;
        } else
            return true;
    }

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() >= 10;
        }
        return false;
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isValidPostal(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 6;
        }
        return false;
    }






    private boolean validatenew(String addressline1t,String addressline2t,String city_str,String postal_str,String state_str,String country_str) {
        if (addressline1t.equals("") || addressline2t.equals("") ||city_str.equals("")||!isValidPostal(postal_str)||state_str.equals("")||country_str.equals("")) {


            if (addressline1t.equals(null) || addressline1t.equals("")) {
                addressline1.setError("Enter a address");
            } else {
                address_id.setError(null);
            }

            if (addressline2t.equals(null) || addressline2t.equals("")) {
                addressline2.setError("Enter a address");
            } else {
                addressline2.setError(null);
            }

            if (city_str.equals(null) || city_str.equals("")) {
                city_id.setError("Enter a city");
            } else {
                addressline2.setError(null);
            }

            if (!isValidPostal(postal_str)) {
                postal_id.setError("Enter a postal code");
            } else {
                postal_id.setError(null);
            }


            if (state_str.equals(null) || state_str.equals("")) {
                state_id.setError("Enter a state");
            } else {
                state_id.setError(null);
            }

            if (country_str.equals(null) || country_str.equals("")) {
                country_id.setError("Enter a country");
            } else {
                country_id.setError(null);
            }


            return false;
        } else
            return true;

    }





    public void corporateshared(String yes_or_no)
    {
// Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("corporateShare",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putString("addcart", yes_or_no);
        myEdit.putString("companyname", company_name_id.getText().toString());
        myEdit.putString("name", name_id.getText().toString());
        myEdit.putString("email", email_id.getText().toString());
        myEdit.putString("phone", phone_id.getText().toString());
        myEdit.putString("fulladdress", address_id.getText().toString());
        myEdit.putString("city", city_id.getText().toString());
        myEdit.putString("postalcode", postal_id.getText().toString());

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();
    }







    public void  GetscheduleCount()
    {
        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");
        String URL = APIBaseURL.BASEURLLINK_B2B_SCHEDULE_LIST+""+companyId;
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if(jsonObject.getString("isSuccess").equals("true"))
                    {
                        JSONArray datajsonarray = jsonObject.getJSONArray("data");
                        sI_jsonarray=jsonObject.getJSONArray("data");
                        //JSONArray exclusionDates_jsonarray = new JSONArray(jsonObject.getString("exclusionDates"));




                        for(int k=0;k<datajsonarray.length();k++)
                        {

                            try {
                                JSONObject jsonObject2 = new JSONObject(String.valueOf(datajsonarray.get(k)));
                                String scheduleID=jsonObject2.getString("scheduleID");
                                String comapnyID=jsonObject2.getString("companyID");
                                JSONArray dishesItems_jsonarray = new JSONArray(jsonObject2.getString("dishesItems"));

                                JSONArray exclusionDates_jsonarray = new JSONArray(jsonObject2.getString("exclusionDates"));

                                String[] separated = (jsonObject2.getString("startDate").split("T"));
                                String string = dateFormat2(separated[0]);
                                String[] parts = string.split("/");
                                String part1 = parts[0]; // 004
                                String part2 = parts[1];
                                String part3 = parts[2];
                                String input_date=string;
                                SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");

                                Date dt1= null;
                                try {
                                    dt1 = format1.parse(input_date);
                                    DateFormat format2=new SimpleDateFormat("MMM");
                                    String finalDay=format2.format(dt1);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String[] separated2 = (jsonObject2.getString("endDate").split("T"));

                                String string1 = dateFormat2(separated2[0]);
                                String[] parts1 = string1.split("/");
                                String part11 = parts1[0]; // 004
                                String part21 = parts1[1];
                                String part31= parts1[2];
                                String input_date1=string1;
                                SimpleDateFormat format11=new SimpleDateFormat("dd/MM/yyyy");
                                Date dt11= null;
                                try {
                                    dt11 = format11.parse(input_date1);
                                    DateFormat format2=new SimpleDateFormat("MMM");
                                    String finalDay=format2.format(dt11);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                int totalmeal=0;
                                if(jsonObject2.getBoolean("isBreakfast"))
                                {

                                    totalmeal++;
                                }

                                if(jsonObject2.getBoolean("isLunch"))
                                {

                                    totalmeal++;
                                }
                                if(jsonObject2.getBoolean("isDinner"))
                                {

                                    totalmeal++;
                                }
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDate start = LocalDate.parse(string,formatter);
                                LocalDate end = LocalDate.parse(string1,formatter);
                                System.out.println(ChronoUnit.DAYS.between(start, end));
                                double dishtotal=0.0;
                                for(int t=0;t<dishesItems_jsonarray.length();t++)
                                {
                                    double noOfUnits= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("noOfUnits"));
                                    double cost= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("cost"));
                                    double totalval=((noOfUnits)*(cost));
                                   // Log.i(TAG, "noOfUnits ( * ) cost" + noOfUnits +" * "+cost+" = "+totalval);
                                    dishtotal+=totalval;
                                    //Log.i(TAG, "dishtotal" + dishtotal);
                                }
                                double selectmeal=(dishtotal * totalmeal);
                                //Log.i(TAG, "dishTotal ( * ) totalmeal" + selectmeal);
                                long numberOfDays=(((ChronoUnit.DAYS.between(start, end)))-(exclusionDates_jsonarray.length()))+1;
                               // Log.i(TAG, "numberOfDays : start date & end date "+""+ChronoUnit.DAYS.between(start, end)+ " - "+"exclusionDates length : "+exclusionDates_jsonarray.length()+" = "+numberOfDays);

                                double alltotal1=0.0;

                                JSONArray dishesItems_jsonarray2 = new JSONArray(jsonObject2.getString("dishesItems"));
                                boolean isWekely=Globaluse.CheckWeeeklyUsingJson(dishesItems_jsonarray2);
                                if(isWekely)
                                {
                                    Toast.makeText(CorporatebussinessInformationSubmit.this, "Only Weekly Selection", Toast.LENGTH_SHORT).show();
                                    alltotal1=(selectmeal * 1);
                                }
                                else
                                {
                                    Toast.makeText(CorporatebussinessInformationSubmit.this, "Day selection", Toast.LENGTH_SHORT).show();
                                    alltotal1=(selectmeal * numberOfDays);
                                }


                                //double alltotal1=(selectmeal * numberOfDays);
                                //Log.i(TAG, "all total" + alltotal1);

                                // s_date_id.setText(scheduleID);
                                sub_totalvalue+=alltotal1;

                                sub_total_id3.setText("₹ "+sub_totalvalue);
                                total_id.setText("₹ "+sub_totalvalue);


//                                sub_total_id3.setText(""+sub_totalvalue);
//                                total_id.setText(""+sub_totalvalue);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }



                        CustomAdapterRecycle horizontalAdapter2;
                        horizontalAdapter2=new CustomAdapterRecycle(datajsonarray);
                        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(CorporatebussinessInformationSubmit.this, LinearLayoutManager.HORIZONTAL, false);
                        excludes_recycler_view.setLayoutManager(horizontalLayoutManagaer2);
                        excludes_recycler_view.setAdapter(horizontalAdapter2);
                        excludes_recycler_view.setNestedScrollingEnabled(false);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO

                } else if (error instanceof NetworkError) {
                    Toast.makeText(CorporatebussinessInformationSubmit.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporatebussinessInformationSubmit.this){    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                //params.put("content-type", "application/json");
                return params;
            }
        };


        ApplicationController.getInstance().addToRequestQueue(stringRequest, "scheduleList");
    }






    class CustomAdapterRecycle extends RecyclerView.Adapter< CustomAdapterRecycle.MyViewHolder> {

        private JSONArray dataSet;

        public  class MyViewHolder extends RecyclerView.ViewHolder {

            TextView s_id,total_id;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.s_id = (TextView) itemView.findViewById(R.id.s_id);
                this.total_id = (TextView) itemView.findViewById(R.id.total_id);

            }
        }

        public CustomAdapterRecycle(JSONArray data) {
            this.dataSet = data;
        }

        @Override
        public CustomAdapterRecycle.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_schedule_with_total_item, parent, false);

            CustomAdapterRecycle.MyViewHolder myViewHolder = new CustomAdapterRecycle.MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final  CustomAdapterRecycle.MyViewHolder holder, final int listPosition) {

            TextView s_id = holder.s_id;
            TextView total_id2 = holder.total_id;


            s_id.setText("S "+ (listPosition+1));


            try {

            String string = dateFormat((dataSet.getJSONObject(listPosition).getString("startDate")));
            String[] parts = string.split("/");
            String part1 = parts[0]; // 004
            String part2 = parts[1];
            String part3 = parts[2];
            String input_date=string;
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");

            Date dt1= null;
            try {
                dt1 = format1.parse(input_date);
                DateFormat format2=new SimpleDateFormat("MMM");
                String finalDay=format2.format(dt1);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String string1 = dateFormat((dataSet.getJSONObject(listPosition).getString("endDate")));
            String[] parts1 = string1.split("/");
            String part11 = parts1[0]; // 004
            String part21 = parts1[1];
            String part31= parts1[2];
            String input_date1=string1;
            SimpleDateFormat format11=new SimpleDateFormat("dd/MM/yyyy");
            Date dt11= null;
            try {
                dt11 = format11.parse(input_date1);
                DateFormat format2=new SimpleDateFormat("MMM");
                String finalDay=format2.format(dt11);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            int totalmeal=0;
            if(dataSet.getJSONObject(listPosition).getBoolean("isBreakfast"))
            {

                totalmeal++;
            }

            if(dataSet.getJSONObject(listPosition).getBoolean("isLunch"))
            {

                totalmeal++;
            }
            if(dataSet.getJSONObject(listPosition).getBoolean("isDinner"))
            {

                totalmeal++;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate start = LocalDate.parse(string,formatter);
            LocalDate end = LocalDate.parse(string1,formatter);
            System.out.println(ChronoUnit.DAYS.between(start, end));

            double dishtotal=0.0;
            JSONArray dishesItems_jsonarray = new JSONArray(dataSet.getJSONObject(listPosition).getString("dishesItems"));
            for(int t=0;t<dishesItems_jsonarray.length();t++)
            {
                double noOfUnits= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("noOfUnits"));
                double cost= Double.parseDouble(dishesItems_jsonarray.getJSONObject(t).getString("cost"));
                double totalval=((noOfUnits)*(cost));
                //Log.i(TAG, "noOfUnits ( * ) cost" + noOfUnits +" * "+cost+" = "+totalval);
                dishtotal+=totalval;
                //Log.i(TAG, "dishtotal" + dishtotal);
            }

            double selectmeal=(dishtotal * totalmeal);

            //Log.i(TAG, "dishTotal ( * ) totalmeal" + selectmeal);
                JSONArray exclusionDates_jsonarray = new JSONArray(dataSet.getJSONObject(listPosition).getString("exclusionDates"));
            //long numberOfDays=((ChronoUnit.DAYS.between(start, end)+1)-(exclusionDates_jsonarray.length()));
            long numberOfDays=((ChronoUnit.DAYS.between(start, end))-(exclusionDates_jsonarray.length()))+1;

            //Log.i(TAG, "numberOfDays : start date & end date "+""+ChronoUnit.DAYS.between(start, end)+ " - "+"exclusionDates length : "+exclusionDates_jsonarray.length()+" = "+numberOfDays);

                double alltotal=0.0;
                JSONArray dishesItems_jsonarray2 = new JSONArray(dataSet.getJSONObject(listPosition).getString("dishesItems"));
                boolean isWekely=Globaluse.CheckWeeeklyUsingJson(dishesItems_jsonarray2);
                if(isWekely)
                {
                    Toast.makeText(CorporatebussinessInformationSubmit.this, "Only Weekly Selection", Toast.LENGTH_SHORT).show();
                    alltotal=(selectmeal * 1);
                }
                else
                {
                    Toast.makeText(CorporatebussinessInformationSubmit.this, "Day selection", Toast.LENGTH_SHORT).show();
                    alltotal=(selectmeal * numberOfDays);
                }


            //double alltotal=(selectmeal * numberOfDays);

            //Log.i(TAG, "all total" + alltotal);
                total_id2.setText(""+alltotal);

//                 sub_totalvalue+=alltotal;
//                 all_totalvalue+=alltotal;
//
//
//                runOnUiThread(new Runnable(){
//                    @Override
//                    public void run(){
//                        sub_total_id3.setText(""+sub_totalvalue);
//                        total_id.setText(""+sub_totalvalue);
//                    }
//                });


            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return dataSet.length();
        }}










    private String dateFormat(String date) {
       // Log.i(TAG, "serverdate " + date);
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        String formattedDate = null;
        try {
            serverDate = df.parse(date);
            //SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            //outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = outputFormat.format(serverDate);
           // Log.i(TAG, "converdate" + formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }





    private String dateFormat2(String date) {
        // Log.i(TAG, "serverdate " + date);
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        String formattedDate = null;
        try {
            serverDate = df.parse(date);
            //SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            //outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = outputFormat.format(serverDate);
            // Log.i(TAG, "converdate" + formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }



}
