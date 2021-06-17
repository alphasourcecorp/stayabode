package stayabode.foodyHive.corporatemenurange;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.ApplicationController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CorporateRegisterActivity extends AppCompatActivity {


    Button register_id;
    EditText company_name_id,name_id,email_id,phone_id,address1_id,address2_id,city_id,postal_id;
    private ProgressDialog pDialog;
    com.google.android.material.textfield.TextInputEditText  password_id,cpassword_id;
    String access_str="",pwd="";
    Spinner stateSpinner,countrySpinner;
    String state_str="",country_str="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_register);


        company_name_id=findViewById(R.id.company_name_id);
        name_id=findViewById(R.id.name_id);
        email_id=findViewById(R.id.email_id);
        phone_id=findViewById(R.id.phone_id);
        address1_id=findViewById(R.id.address1_id);
        address2_id=findViewById(R.id.address2_id);
        city_id=findViewById(R.id.city_id);
        postal_id=findViewById(R.id.postal_id);

        password_id=findViewById(R.id.password_id);
        cpassword_id=findViewById(R.id.cpassword_id);

        stateSpinner = findViewById(R.id.stateSpinner);
        ArrayList<String> spinnerList = new ArrayList<>();
        //spinnerList.clear();
        //spinnerList.add("Select State");


        countrySpinner = findViewById(R.id.countrySpinner);
        ArrayList<String> spinnerList2 = new ArrayList<>();
        //spinnerList.clear();
        spinnerList2.add("India");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CorporateRegisterActivity.this, R.layout.support_simple_spinner_dropdown_item, spinnerList2);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        countrySpinner.setAdapter(arrayAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = countrySpinner.getSelectedItemPosition();

                country_str = spinnerList2.get(pos);

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


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(CorporateRegisterActivity.this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        stateSpinner.setAdapter(arrayAdapter2);
        stateSpinner.setSelection(10);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = stateSpinner.getSelectedItemPosition();


                state_str = spinnerList.get(pos);
                //  prepareViewager(viewPager,arrayList);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });





        register_id=findViewById(R.id.register_id);
        register_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");

                String company_name_str=company_name_id.getText().toString().trim();
                String name_str=name_id.getText().toString().trim();
                String email_str=email_id.getText().toString().trim();
                String phone_str=phone_id.getText().toString().trim();
                company_name_id.setText(phone_str);

                String address1_str=address1_id.getText().toString().trim();
                String address2_str=address2_id.getText().toString().trim();
                String city_str=city_id.getText().toString().trim();
                String postal_str=postal_id.getText().toString().trim();

                String password_str=password_id.getText().toString().trim();
                String cpassword_str=cpassword_id.getText().toString().trim();
                company_name_str=phone_str;
                boolean sta=validate(company_name_str, name_str, email_str,phone_str,address1_str,address2_str,city_str,postal_str,password_str,cpassword_str);

                if (validate(company_name_str, name_str, email_str,phone_str,address1_str,address2_str,city_str,postal_str,password_str,cpassword_str)) {

                    pDialog = ProgressDialog.show(CorporateRegisterActivity.this, "", "Please wait...", true,false);
                    JSONObject inputObject = new JSONObject();
                    JSONArray dishItems_array = new JSONArray();
                    JSONArray delivery_schedule_array = new JSONArray();
                    try {
                        inputObject.put("companyId"," ");
                        //inputObject.put("companyName",company_name_str);
                        inputObject.put("companyName",phone_str);
                        inputObject.put("subscriberName",name_str);
                        inputObject.put("emailId",email_str);
                        inputObject.put("phoneNumber",phone_str);
                        inputObject.put("password",password_str);
                        JSONObject deliveryAddressObject = new JSONObject();
                        deliveryAddressObject.put("addressLine1",address1_str);
                        deliveryAddressObject.put("addressLine2",address2_str);
                        deliveryAddressObject.put("city",city_str);
                        deliveryAddressObject.put("state",state_str);
                        deliveryAddressObject.put("country",country_str);
                        deliveryAddressObject.put("postalCode",postal_str);//////
                        inputObject.put("cAddress",deliveryAddressObject);
                        inputObject.put("isAdmin",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();
                    String URL = APIBaseURL.BASEURLLINK_B2B_AddCustomer;
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
                                    JSONObject jsondataObject = jsonObject.getJSONObject("data");
                                    //JSONObject jsondataObject = new JSONObject(String.valueOf(jsondataArrayObject.get(0)));
                                    access_str=jsondataObject.getString("phoneNumber");
                                    pwd=jsondataObject.getString("password");
                                    AgainLogin();

                                }else
                                {

                                    final Dialog dialog = new Dialog(CorporateRegisterActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.corporate_register_fail);

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

                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject data = new JSONObject(responseBody);
                                //JSONArray errors = data.getJSONArray("errors");
                                // JSONObject jsonMessage = errors.getJSONObject(0);
                                // String message = jsonMessage.getString("message");
                                //Toast.makeText(getApplicationContext(), data.get("errorMessage"), Toast.LENGTH_LONG).show();


                                final Dialog dialog = new Dialog(CorporateRegisterActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.corporate_fail);

                                Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                                TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                                errormessage_id.setText(data.getString("errorMessage"));

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



                            } catch (JSONException e) {
                            } catch (UnsupportedEncodingException errorr) {
                            }

                        }
                    }) ;
                    ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);




                }


            }
        });






    }




    private boolean validate(String company_name,String name,String email,  String phone,String address1,String address2,String city,String postal,String password_str,String cpassword_str) {
        if (company_name.equals("") || name.equals("") ||!isValidEmail(email) ||!isValidMobile(phone)  ||address1.equals("") ||address2.equals("")|| city.equals("")||!isValidPostal(postal)|| password_str.equals("") || cpassword_str.equals("")) {


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

            if (address1.equals(null) || address1.equals("")) {
                address1_id.setError("Enter a address");
            } else {
                address1_id.setError(null);
            }
            if (address2.equals(null) || address2.equals("")) {
                address2_id.setError("Enter a address");
            } else {
                address2_id.setError(null);
            }

            if (city.equals(null) || city.equals("")) {
                city_id.setError("Enter a city");
            } else {
                city_id.setError(null);
            }

            if (!isValidPostal(postal)) {
                postal_id.setError("Enter a postal code");
            } else {
                postal_id.setError(null);
            }



            if (password_str.equals(null) || password_str.equals("")) {
                password_id.setError("Enter a Password");
            } else {
                password_id.setError(null);
            }


            if (cpassword_str.equals(null) || cpassword_str.equals("")) {
                cpassword_id.setError("Enter a Password");
            } else {

                cpassword_id.setError(null);

            }

            return false;
        } else
        {
            if(password_str.equals(cpassword_str))
            {
                cpassword_id.setError(null);
                return true;
            }else
            {
                cpassword_id.setError("Password mismatch");
                cpassword_id.setText("");
                return false;
            }



        }

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



    public void  AgainLogin() {

        //String company_code_str=company_code_id.getText().toString().trim();
        ///String password_str=password_id.getText().toString().trim();


        pDialog = ProgressDialog.show(CorporateRegisterActivity.this, "", "Please wait...", true, false);
        JSONObject inputObject = new JSONObject();

        try {
            inputObject.put("companyCode", access_str);
            inputObject.put("password", pwd);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String URL = APIBaseURL.BASEURLLINK_B2B_LOGIN;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // Toast.makeText(CorporatebussinessInformation.this, response.optString("errorMessage"), Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    // jsonObject.getString("isSuccess");

                    if (jsonObject.getString("isSuccess").equals("true")) {
                        // pDialog.dismiss();
                        JSONArray jsondataArrayObject = jsonObject.getJSONArray("data");
                        JSONObject jsondataObject = new JSONObject(String.valueOf(jsondataArrayObject.get(0)));

                        JSONObject jsoncAddressObject = new JSONObject(jsondataObject.getString("cAddress"));

                        SharedPreferences sharedPreferences = getSharedPreferences("corporateLogin", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("isAlreadyLogin", "yes");
                        myEdit.putString("companyId", jsondataObject.getString("companyId"));
                        myEdit.putString("companyName", jsondataObject.getString("companyName"));
                        myEdit.putString("subscriberName", jsondataObject.getString("subscriberName"));
                        myEdit.putString("emailId", jsondataObject.getString("emailId"));
                        myEdit.putString("phoneNumber", jsondataObject.getString("phoneNumber"));
                        myEdit.putString("addressLine1", jsoncAddressObject.getString("addressLine1"));
                        myEdit.putString("addressLine2", jsoncAddressObject.getString("addressLine2"));
                        myEdit.putString("city", jsoncAddressObject.getString("city"));
                        myEdit.putString("state", jsoncAddressObject.getString("state"));
                        myEdit.putString("country", jsoncAddressObject.getString("country"));
                        myEdit.putString("postalCode", jsoncAddressObject.getString("postalCode"));
                        myEdit.putString("isAdmin", jsondataObject.getString("isAdmin"));
                        myEdit.putString("isAdmin", jsondataObject.getString("isAdmin"));
                        myEdit.putString("token", jsonObject.getString("token"));
                        myEdit.commit();


                        if (jsondataObject.getBoolean("isAdmin")) {
                            Intent intent = new Intent(CorporateRegisterActivity.this, CorporateMenuWithNavigatioinAdmin.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Intent intent = new Intent(CorporateRegisterActivity.this, CorporateMenuWithNavigatioin.class);
                            startActivity(intent);
                            finishAffinity();
                        }


                    } else {

                        final Dialog dialog = new Dialog(CorporateRegisterActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.corporate_register_fail);

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


                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);

                    final Dialog dialog = new Dialog(CorporateRegisterActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.corporate_fail);

                    Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                    TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                    errormessage_id.setText(data.getString("errorMessage"));

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


                } catch (JSONException e) {
                } catch (UnsupportedEncodingException errorr) {
                }


                //JSONObject jsonObject = new JSONObject(String.valueOf(response));


            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);


    }

}
