package stayabode.foodyHive.corporatemenurange;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class CorporateLoginActivity extends AppCompatActivity {


Button login_id;
TextView signup_id;
EditText company_code_id,password_id;
private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_order_login);
        Globaluse.fromscheduleEdit=" ";
        login_id=findViewById(R.id.login_id);
        signup_id=findViewById(R.id.signup_id);

        company_code_id=findViewById(R.id.company_code_id);
        password_id=findViewById(R.id.password_id);

//        company_code_id.setText("KISO84999");//user
//        password_id.setText("Kira4cf0b3");


        company_code_id.setText("ALPH80675");//user
        password_id.setText("sath64fb59");
      //  password_id.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

//        company_code_id.setText("VITE88653");//admin
//        password_id.setText("vinafcdcfb");

        login_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String company_code_str=company_code_id.getText().toString().trim();
                String password_str=password_id.getText().toString().trim();

                if (validate(company_code_str, password_str)) {

                    pDialog = ProgressDialog.show(CorporateLoginActivity.this, "", "Please wait...", true,false);
                    JSONObject inputObject = new JSONObject();
//                    JSONArray dishItems_array = new JSONArray();
//                    JSONArray delivery_schedule_array = new JSONArray();
                    try {
                        inputObject.put("companyCode",company_code_str);
                        inputObject.put("password",password_str);

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

                                if(jsonObject.getString("isSuccess").equals("true"))
                                {
                                    // pDialog.dismiss();
                                    JSONArray jsondataArrayObject = jsonObject.getJSONArray("data");
                                    JSONObject jsondataObject = new JSONObject(String.valueOf(jsondataArrayObject.get(0)));
//                                    jsondataObject.getString("companyName");
//                                    jsondataObject.getString("subscriberName");
//                                    jsondataObject.getString("emailId");
//                                    jsondataObject.getString("phoneNumber");
//                                    jsondataObject.getString("isAdmin");
                                    JSONObject jsoncAddressObject= new JSONObject(jsondataObject.getString("cAddress"));
//                                    jsoncAddressObject.getString("addressLine1");
//                                    jsoncAddressObject.getString("addressLine2");
//                                    jsoncAddressObject.getString("city");
//                                    jsoncAddressObject.getString("state");
//                                    jsoncAddressObject.getString("country");
//                                    jsoncAddressObject.getString("postalCode");
                                        SharedPreferences sharedPreferences = getSharedPreferences("corporateLogin",MODE_PRIVATE);
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



//                                    final Dialog dialog = new Dialog(CorporateLoginActivity.this);
//                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                    dialog.setCancelable(false);
//                                    dialog.setContentView(R.layout.corporate_register_sucess);
//
//                                    Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
//                                    TextView register_txt_id = (TextView) dialog.findViewById(R.id.register_txt_id);
//                                    register_txt_id.setText(jsonObject.getString("successMessage"));
//
//                                    subscribe_id.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            dialog.cancel();
//                                            finishAffinity();
//                                            Intent intent = new Intent(CorporateLoginActivity.this, CorporateMenuWithNavigatioinAdmin.class);
//                                            startActivity(intent);
//
//                                        }
//                                    });
//                                    dialog.show();


//                                    Intent intent = new Intent(CorporateLoginActivity.this, CorporateMenuWithNavigatioinAdmin.class);
//                                    startActivity(intent);
//                                    finish();




if(jsondataObject.getBoolean("isPasswordExpired"))
{
    final Dialog dialog = new Dialog(CorporateLoginActivity.this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.change_pwd);

    ImageView close_id = (ImageView) dialog.findViewById(R.id.close_id);



    close_id.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();
        }
    });
    dialog.show();

}
else
{
    if(jsondataObject.getBoolean("isAdmin"))
    {
        Intent intent = new Intent(CorporateLoginActivity.this, CorporateMenuWithNavigatioinAdmin.class);
        startActivity(intent);
        finishAffinity();
    }else
    {
        Intent intent = new Intent(CorporateLoginActivity.this, CorporateMenuWithNavigatioin.class);
        startActivity(intent);
        finishAffinity();
    }
}







                                }else
                                {

                                    final Dialog dialog = new Dialog(CorporateLoginActivity.this);
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

                            //JSONObject jsonObject = new JSONObject(String.valueOf(response));

                            final Dialog dialog = new Dialog(CorporateLoginActivity.this);
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

                        }
                    }) ;
                    ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);




                }









//                Intent intent = new Intent(CorporateLoginActivity.this, CorporateMenuWithNavigatioinAdmin.class);
//                startActivity(intent);
//                finish();

            }
        });

        signup_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CorporateLoginActivity.this, CorporateRegisterActivity.class);
                startActivity(intent);



            }
        });

    }




    private boolean validate(String company_code_str,String password_str) {
        if (company_code_str.equals("") || password_str.equals("") ) {


            if (company_code_str.equals(null) || company_code_str.equals("")) {
                company_code_id.setError("Enter a Access Code");
            } else {
                company_code_id.setError(null);
            }
            if (password_str.equals(null) || password_str.equals("")) {
                password_id.setError("Enter a Passwrord");
            } else {
                password_id.setError(null);
            }


            return false;
        } else
            return true;
    }

}
