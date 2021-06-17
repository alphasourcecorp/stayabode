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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.FulllistItemdisplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class CorporatebussinessInformation extends AppCompatActivity {

 Button order_subscription_id;
 EditText company_name_id,name_id,email_id,phone_id,address_id,city_id,postal_id;
 TextView date_id,hour_id,month_id,sub_total_id,total_id;
 ListView navLV;
    double full_totalvalue=0;
    String send_todayAsString="",send_tomorrowAsString="";
    TextView call_id;
    private ProgressDialog pDialog;
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
        total_id=findViewById(R.id.total_id);

        call_id=findViewById(R.id.call_id);
        call_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91 6366225334"));
                startActivity(intent);
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

        company_name_id.setText(companyname_share);
        name_id.setText(name_share);
        email_id.setText(email_share);
        phone_id.setText(phone_share);
        address_id.setText(fulladdress_share);
        city_id.setText(city_share);
        postal_id.setText(postalcode_share);


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






                if (validate(company_name_str, name_str, email_str,phone_str,address_str,city_str,postal_str)) {
//
                    pDialog = ProgressDialog.show(CorporatebussinessInformation.this, "", "Please wait...", true,false);



                    JSONObject inputObject = new JSONObject();
                    JSONArray dishItems_array = new JSONArray();
                    JSONArray delivery_schedule_array = new JSONArray();

                    try {
//                        inputObject.put("partitionKey","");
//                        inputObject.put("rowKey","");
//                        inputObject.put("timestamp","");
//                        inputObject.put("eTag","");
//                        inputObject.put("requestId","");
                        inputObject.put("companyName",company_name_str);
                        inputObject.put("subscriberName",name_str);
                        inputObject.put("emailId",email_str);
                       // inputObject.put("emailId","");
                        inputObject.put("phoneNumber",phone_str);


                        JSONObject deliveryAddressObject = new JSONObject();
                        deliveryAddressObject.put("addressLine1",address_str);
                        deliveryAddressObject.put("addressLine2","");
                        deliveryAddressObject.put("city",city_str);
                        deliveryAddressObject.put("state","Karnataka");
                        deliveryAddressObject.put("country","India");

//                        int postal_str_int;
//                        try {s
//                            postal_str_int = Integer.parseInt(postal_str);
//                        }
//                        catch (NumberFormatException e)
//                        {
//                            postal_str_int = 0;
//                        }

                        deliveryAddressObject.put("postalCode",postal_str);//////
                        inputObject.put("deliveryAddress",deliveryAddressObject);

                        inputObject.put("subscriptionDate",send_todayAsString);


                        for (int k=0;k<Globaluse.corporatedishlistnew.size();k++)
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(Globaluse.corporatedishlistnew.get(k));
                                JSONObject dishDetail = new JSONObject();
                                try {
                                    dishDetail.put("dishItemName", jsonObject.getString("dishItemName"));
                                    dishDetail.put("description", jsonObject.getString("description"));

                                    Double saleAmount_double;
                                    try {
                                        saleAmount_double = Double.parseDouble(jsonObject.getString("cost"));
                                    }
                                    catch (NumberFormatException e)
                                    {
                                        saleAmount_double = 0.0;
                                    }

                                    dishDetail.put("cost", saleAmount_double);/////////

                                    int noOfUnits_int;
                                    try {
                                        noOfUnits_int = Integer.parseInt(jsonObject.getString("noOfUnits"));
                                    }
                                    catch (NumberFormatException e)
                                    {
                                        noOfUnits_int = 0;
                                    }

                                    dishDetail.put("noOfUnits", noOfUnits_int);//////
                                    dishDetail.put("taxValue", 0);
                                    dishDetail.put("packingCharges", 0);
                                    dishDetail.put("deliveryCharges", 0);

                                    dishItems_array.put(dishDetail);
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }catch (JSONException err){
                                Log.d("Error", err.toString());
                            }
                        }

                        inputObject.put("dishItems",dishItems_array);
                        inputObject.put("plan",Globaluse.only_Monthly_week);//monty

//                        for (int k=1;k==1;k++)
//                        {
                                JSONObject deliverySchedule = new JSONObject();
                                try {

                                    if(Globaluse.today_tomo.equals("Tomorrow"))
                                    {

                                        deliverySchedule.put("date", send_tomorrowAsString);
                                    }else
                                    {

                                        deliverySchedule.put("date", send_todayAsString);
                                    }


                                    deliverySchedule.put("timeRange", Globaluse.deliver_time);
                                    deliverySchedule.put("pointOfContact", name_str);

                                    delivery_schedule_array.put(deliverySchedule);
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                      //  }

                        inputObject.put("deliverySchedule",delivery_schedule_array);

                        inputObject.put("totalOrderValue",full_totalvalue);/////
                        inputObject.put("status","Request");




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                       // String url = APIBaseURL.activeOrDeactiveStatus;
                    String URL = "https://foodyhivesubscriptionrequest.azurewebsites.net/api/SubscriptionRequest/create";


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
                                        final Dialog dialog = new Dialog(CorporatebussinessInformation.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.corporate_sucess);

                                        Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);

                                        subscribe_id.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.cancel();
                                                finishAffinity();
                                                Intent intent = new Intent(CorporatebussinessInformation.this, CorporateMenuWithNavigatioin.class);
                                                startActivity(intent);

                                            }
                                        });
                                        dialog.show();

                                    }else
                                    {
                                       // pDialog.dismiss();
                                        corporateshared("yes");
                                        final Dialog dialog = new Dialog(CorporatebussinessInformation.this);
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
                                final Dialog dialog = new Dialog(CorporatebussinessInformation.this);
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
                        }) ;
                        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);












//                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                        //String URL = "https://foodyhivesubscription.azurewebsites.net/api/SubscriptionRequest/create";
//                        String URL = "https://foodyhivesubscriptionrequest.azurewebsites.net/api/SubscriptionRequest/create";
////                        JSONObject jsonBody = new JSONObject();
////                        jsonBody.put("firstkey", "firstvalue");
////                        jsonBody.put("secondkey", "secondobject");
//                        final String mRequestBody = inputObject.toString();
//
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//
//
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//
//                                    // jsonObject.getString("isSuccess");
//
//                                     if(jsonObject.getString("isSuccess").equals("true"))
//                                     {
//                                         pDialog.dismiss();
//                                         corporateshared("no");
//                                         final Dialog dialog = new Dialog(CorporatebussinessInformation.this);
//                                         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                         dialog.setCancelable(false);
//                                         dialog.setContentView(R.layout.corporate_sucess);
//
//                                         Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
//
//                                         subscribe_id.setOnClickListener(new View.OnClickListener() {
//                                             @Override
//                                             public void onClick(View v) {
//                                                 dialog.cancel();
//                                                 finishAffinity();
//                                                 Intent intent = new Intent(CorporatebussinessInformation.this, CorporateMenuWithNavigatioin.class);
//                                                 startActivity(intent);
//
//                                             }
//                                         });
//                                         dialog.show();
//
//                                     }else
//                                     {
//                                         pDialog.dismiss();
//                                         corporateshared("yes");
//                                         final Dialog dialog = new Dialog(CorporatebussinessInformation.this);
//                                         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                         dialog.setCancelable(false);
//                                         dialog.setContentView(R.layout.corporate_fail);
//
//                                         Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
//                                         TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
//                                         errormessage_id.setText(jsonObject.getString("errorMessage"));
//
//                                         subscribe_id.setOnClickListener(new View.OnClickListener() {
//                                             @Override
//                                             public void onClick(View v) {
//                                                 dialog.cancel();
//
//                                             }
//                                         });
//                                         dialog.show();
//
//                                         TextView call_id = (TextView) dialog.findViewById(R.id.call_id);
//                                         call_id.setOnClickListener(new View.OnClickListener() {
//                                             @Override
//                                             public void onClick(View v) {
//                                                 dialog.cancel();
//                                                 Intent intent = new Intent(Intent.ACTION_DIAL);
//                                                 intent.setData(Uri.parse("tel:+91 6366225334"));
//                                                 startActivity(intent);
//                                             }
//                                         });
//                                     }
//
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//
//                                Log.i("LOG_VOLLEY", response);
//
////                       // boolean responsebo= Boolean.parseBoolean(response);
////                                if(response.equals("200"))
////                                {
//////                                    Toast.makeText(CorporatebussinessInformation.this, "Order Received!\n" +
//////                                            "Thank you!! Your order will be processed soon.", Toast.LENGTH_SHORT).show();
////
////                                    pDialog.dismiss();
////                                    corporateshared("no");
////
//////                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CorporatebussinessInformation.this);
//////                                    builder1.setMessage("Order Received!\n" +
//////                                            "Thank you!! Your order will be processed soon.");
//////                                    builder1.setCancelable(true);
//////
//////                                    builder1.setPositiveButton(
//////                                            "Yes",
//////                                            new DialogInterface.OnClickListener() {
//////                                                public void onClick(DialogInterface dialog, int id) {
//////                                                    dialog.cancel();
//////
//////                                                    Intent intent = new Intent(CorporatebussinessInformation.this, CorporateMenuWithNavigatioin.class);
//////                                                    startActivity(intent);
//////                                                    finish();
//////                                                }
//////                                            });
//////
//////                                    builder1.setNegativeButton(
//////                                            "No",
//////                                            new DialogInterface.OnClickListener() {
//////                                                public void onClick(DialogInterface dialog, int id) {
//////                                                    dialog.cancel();
//////                                                }
//////                                            });
//////
//////                                    AlertDialog alert11 = builder1.create();
//////                                    alert11.show();
//////
//////
//////
//////
//////
////
////
////
////
////
////
////
////
////                                    final Dialog dialog = new Dialog(CorporatebussinessInformation.this);
////                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                                    dialog.setCancelable(false);
////                                    dialog.setContentView(R.layout.corporate_sucess);
////
////                                    Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
////
////                                    subscribe_id.setOnClickListener(new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View v) {
////                                            dialog.cancel();
////                                            finishAffinity();
////                                            Intent intent = new Intent(CorporatebussinessInformation.this, CorporateMenuWithNavigatioin.class);
////                                            startActivity(intent);
////
////                                        }
////                                    });
////                                    dialog.show();
////
////
////
////
////                                }
////                               else
////                                {
////                                   // Toast.makeText(CorporatebussinessInformation.this, "Something Wrong", Toast.LENGTH_SHORT).show();
////
////                                    pDialog.dismiss();
////                                    corporateshared("yes");
//////                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CorporatebussinessInformation.this);
//////                                    builder1.setMessage("Fail");
//////                                    builder1.setCancelable(true);
//////
//////                                    builder1.setPositiveButton(
//////                                            "Yes",
//////                                            new DialogInterface.OnClickListener() {
//////                                                public void onClick(DialogInterface dialog, int id) {
//////                                                    dialog.cancel();
//////                                                }
//////                                            });
//////
//////                                    builder1.setNegativeButton(
//////                                            "No",
//////                                            new DialogInterface.OnClickListener() {
//////                                                public void onClick(DialogInterface dialog, int id) {
//////                                                    dialog.cancel();
//////                                                }
//////                                            });
//////
//////                                    AlertDialog alert11 = builder1.create();
//////                                    alert11.show();
////
////
////                                    final Dialog dialog = new Dialog(CorporatebussinessInformation.this);
////                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                                    dialog.setCancelable(false);
////                                    dialog.setContentView(R.layout.corporate_fail);
////
////                                    Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
////
////
////                                    subscribe_id.setOnClickListener(new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View v) {
////                                            dialog.cancel();
////
////                                        }
////                                    });
////                                    dialog.show();
////
////                                    TextView call_id = (TextView) dialog.findViewById(R.id.call_id);
////                                    call_id.setOnClickListener(new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View v) {
////                                            dialog.cancel();
////                                            Intent intent = new Intent(Intent.ACTION_DIAL);
////                                            intent.setData(Uri.parse("tel:+91 6366225334"));
////                                            startActivity(intent);
////                                        }
////                                    });
////
////
////
////                                }
//
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("LOG_VOLLEY", error.toString());
//                                pDialog.dismiss();
//                            }
//                        }) {
//                            @Override
//                            public String getBodyContentType() {
//                                return "application/json; charset=utf-8";
//                            }
//
//                            @Override
//                            public byte[] getBody() throws AuthFailureError {
//                                try {
//                                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                                } catch (UnsupportedEncodingException uee) {
//                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                                    return null;
//                                }
//                            }
//
//                            @Override
//                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                                String responseString = "";
//                                if (response != null) {
//
//                                    responseString = String.valueOf(response.statusCode);
//
//                                }
//                                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                            }
//                        };
//
//                        requestQueue.add(stringRequest);










                    //pDialog.dismiss();

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


        sub_total_id.setText(""+full_totalvalue);
        total_id.setText(""+full_totalvalue);


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






}
