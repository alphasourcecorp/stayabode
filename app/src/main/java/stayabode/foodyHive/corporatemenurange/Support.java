package stayabode.foodyHive.corporatemenurange;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.utils.ApplicationController;

public class Support extends AppCompatActivity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ImageView mapid;
    EditText name_id,email_id,message_id,phone_id;
    Button submit_id;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.support);

        mapid=findViewById(R.id.mapid);
        name_id=findViewById(R.id.name_id);
        email_id=findViewById(R.id.email_id);
        message_id=findViewById(R.id.message_id);
        phone_id=findViewById(R.id.phone_id);


        submit_id=findViewById(R.id.submit_id);

        submit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name_str=name_id.getText().toString().trim();
                String email_str=email_id.getText().toString().trim();
                String message_str=message_id.getText().toString().trim();
                String phone_str=phone_id.getText().toString().trim();

                if(validate(name_str,email_str,message_str))
                {
                    if(phone_str.equals(" ") ||phone_str.equals(null) || phone_str.equals(""))
                    {
                        pDialog = ProgressDialog.show(Support.this, "", "Please wait...", true,false);

                        JSONObject inputObject = new JSONObject();
                        JSONArray dishItems_array = new JSONArray();
                        JSONArray delivery_schedule_array = new JSONArray();
                        try {

                            inputObject.put("name",name_str);
                            inputObject.put("email",email_str);
                            inputObject.put("userMessage",message_str);
                            inputObject.put("phoneNumber",phone_str);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.dismiss();
                        String URL = APIBaseURL.BASEURLLINK_B2B_Dropmessage;
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, inputObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pDialog.dismiss();
                                // Toast.makeText(CorporatebussinessInformation.this, response.optString("errorMessage"), Toast.LENGTH_SHORT).show();

                                try {
                                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                                    if(jsonObject.getString("isSuccess").equals("true"))
                                    {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("successMessage"), Toast.LENGTH_LONG).show();
                                        name_id.setText(" ");
                                        email_id.setText(" ");
                                        message_id.setText(" ");
                                        phone_id.setText(" ");


                                    }else
                                    {

                                        final Dialog dialog = new Dialog(Support.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.corporate_register_fail);

                                        Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                                        TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                                        TextView textView16 = (TextView) dialog.findViewById(R.id.textView16);
                                        textView16.setText("Stayabode");
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
                                    final Dialog dialog = new Dialog(Support.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.corporate_fail);

                                    Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                                    TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                                    TextView textView16 = (TextView) dialog.findViewById(R.id.textView16);
                                    textView16.setText("Stayabode");
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



                    }else
                    {


                        if (!isValidMobile(phone_str)) {
                            phone_id.setError("Enter a valid Mobile Number");
                        } else {
                            phone_id.setError(null);

                            pDialog = ProgressDialog.show(Support.this, "", "Please wait...", true,false);

                            JSONObject inputObject = new JSONObject();
                            JSONArray dishItems_array = new JSONArray();
                            JSONArray delivery_schedule_array = new JSONArray();
                            try {

                                inputObject.put("name",name_str);
                                inputObject.put("email",email_str);
                                inputObject.put("userMessage",message_str);
                                inputObject.put("phoneNumber",phone_str);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pDialog.dismiss();
                            String URL = APIBaseURL.BASEURLLINK_B2B_Dropmessage;
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, inputObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    pDialog.dismiss();
                                    // Toast.makeText(CorporatebussinessInformation.this, response.optString("errorMessage"), Toast.LENGTH_SHORT).show();

                                    try {
                                        JSONObject jsonObject = new JSONObject(String.valueOf(response));

                                        if(jsonObject.getString("isSuccess").equals("true"))
                                        {
                                            Toast.makeText(getApplicationContext(), jsonObject.getString("successMessage"), Toast.LENGTH_LONG).show();
                                            name_id.setText(" ");
                                            email_id.setText(" ");
                                            message_id.setText(" ");
                                            phone_id.setText(" ");



                                        }else
                                        {

                                            final Dialog dialog = new Dialog(Support.this);
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.corporate_register_fail);

                                            Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                                            TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                                            TextView textView16 = (TextView) dialog.findViewById(R.id.textView16);
                                            textView16.setText("Stayabode");
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
                                        final Dialog dialog = new Dialog(Support.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.corporate_fail);

                                        Button subscribe_id = (Button) dialog.findViewById(R.id.subscribe_id);
                                        TextView errormessage_id = (TextView) dialog.findViewById(R.id.errormessage);
                                        TextView textView16 = (TextView) dialog.findViewById(R.id.textView16);
                                        textView16.setText("Stayabode");
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







                }

            }
        });



        mapid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapView("13.017767" , "77.652891" , "Alphasource Technologies Pvt. Ltd.");
            }
        });

    }

    private void openMapView(String latitude , String longitude , String locationName){
        Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude+"?q="+locationName);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }



    private boolean validate(String name,String email,  String message) {
        if ( name.equals("") ||!isValidEmail(email)  ||message.equals("")) {

            if (name.equals(null) || name.equals("")) {
                name_id.setError("Enter a name");
            } else {
                name_id.setError(null);
            }
            if (!isValidEmail(email)) {
                email_id.setError("Enter valid email");

            } else {
                email_id.setError(null);
            }

            if (message.equals(null) || message.equals("")) {
                message_id.setError("Enter a message");
            } else {
                message_id.setError(null);
            }


            return false;
        } else
        {
            return true;
        }

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() >= 10;
        }
        return false;
    }

}
