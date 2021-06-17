package stayabode.foodyHive.corporatemenurange;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.ApplicationController;

public class ColiveAndStayabodeHomeActivity extends AppCompatActivity {

    private boolean exit = false;
    Button home_id,about_id,login_id,whatsapp_id;
    Boolean homeclick=false,aboutclick=false,loginclick=false;
    FrameLayout homeFramelaout,AboutusFramelaout,loginFramelaout;
    EditText company_code_id,password_id;
    private ProgressDialog pDialog;
    Button submit_login;
    TextView register_id;
    TextView faq_id,support_id;

   // TextView whatsapp_id;

    ImageView youtube_image1,play_youtube1,youtube_image2,play_youtube2,youtube_image3,play_youtube3,youtube_image4,play_youtube4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_for_colive_and_stayabode);



        company_code_id=findViewById(R.id.company_code_id);
        register_id=findViewById(R.id.register_id);

        youtube_image1=findViewById(R.id.youtube_image1);
        play_youtube1=findViewById(R.id.play_youtube1);

        youtube_image2=findViewById(R.id.youtube_image2);
        play_youtube2=findViewById(R.id.youtube_image2);

        youtube_image3=findViewById(R.id.youtube_image3);
        play_youtube3=findViewById(R.id.play_youtube3);

        youtube_image4=findViewById(R.id.youtube_image4);
        play_youtube4=findViewById(R.id.play_youtube4);

        faq_id=findViewById(R.id.faq_id);
        support_id=findViewById(R.id.support_id);

        faq_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ColiveAndStayabodeHomeActivity.this, Faq.class);
                startActivity(intent);

            }
        });

        support_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ColiveAndStayabodeHomeActivity.this, Support.class);
                startActivity(intent);

            }
        });

        play_youtube1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse( "https://www.youtube.com/watch?v=NqRta8HY-cw" );
                uri = Uri.parse( "vnd.youtube:" + uri.getQueryParameter( "v" ) );
                startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
            }
        });
        Glide.with(ColiveAndStayabodeHomeActivity.this).load("https://img.youtube.com/vi/NqRta8HY-cw/2.jpg").into(youtube_image1);

        play_youtube2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse( "https://www.youtube.com/watch?v=lQoAeqheftE" );
                uri = Uri.parse( "vnd.youtube:" + uri.getQueryParameter( "v" ) );
                startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
            }
        });
        Glide.with(ColiveAndStayabodeHomeActivity.this).load("http://img.youtube.com/vi/lQoAeqheftE/0.jpg").into(youtube_image2);

        play_youtube3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse( "https://www.youtube.com/watch?v=Qa6HumVxwdA" );
                uri = Uri.parse( "vnd.youtube:" + uri.getQueryParameter( "v" ) );
                startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
            }
        });
        Glide.with(ColiveAndStayabodeHomeActivity.this).load("http://img.youtube.com/vi/Qa6HumVxwdA/0.jpg").into(youtube_image3);



        play_youtube4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse( "https://www.youtube.com/watch?v=EDDglc6L05E" );
                uri = Uri.parse( "vnd.youtube:" + uri.getQueryParameter( "v" ) );
                startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
            }
        });
        Glide.with(ColiveAndStayabodeHomeActivity.this).load("http://img.youtube.com/vi/EDDglc6L05E/0.jpg").into(youtube_image4);

        password_id=findViewById(R.id.password_id);
        login_id=findViewById(R.id.login_id);
        submit_login=findViewById(R.id.submit_login);

        home_id=findViewById(R.id.home_id);
        about_id=findViewById(R.id.about_id);
        login_id=findViewById(R.id.login_id);
        whatsapp_id=findViewById(R.id.whatsapp_id);

        homeFramelaout=findViewById(R.id.homeFramelaout);
        homeFramelaout=findViewById(R.id.homeFramelaout);
        AboutusFramelaout=findViewById(R.id.AboutusFramelaout);
        loginFramelaout=findViewById(R.id.loginFramelaout);


        home_id.setTextColor(getResources().getColor(R.color.colorWhite));

        home_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(homeclick)
//                {
//                    homeclick=false;
//                    final int sdk = android.os.Build.VERSION.SDK_INT;
//                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                        home_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                        about_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                        login_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                    } else {
//                        home_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                        about_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                        login_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                    }
//
//                    home_id.setTextColor(getResources().getColor(R.color.colorGreyDark));
//                }else
//                {
//                    homeclick=true;
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        home_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                        about_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                        login_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    } else {
                        home_id.setBackground(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                        about_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                        login_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    }

                home_id.setTextColor(getResources().getColor(R.color.colorWhite));
                about_id.setTextColor(getResources().getColor(R.color.colorGreyDark));
                login_id.setTextColor(getResources().getColor(R.color.colorGreyDark));

                homeFramelaout.setVisibility(View.VISIBLE);
                AboutusFramelaout.setVisibility(View.GONE);
                loginFramelaout.setVisibility(View.GONE);

              //  }

            }
        });

        register_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ColiveAndStayabodeHomeActivity.this, CorporateRegisterActivity.class);
                startActivity(intent);
            }
        });




        about_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(homeclick)
//                {
//                    aboutclick=false;
//                    final int sdk = android.os.Build.VERSION.SDK_INT;
//                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                        home_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                        about_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                        login_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                    } else {
//                        home_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                        about_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                        login_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
//                    }
//
//                    about_id.setTextColor(getResources().getColor(R.color.colorGreyDark));
//                }else
//                {
//                    aboutclick=true;
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        home_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                        about_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                        login_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    } else {
                        home_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                        about_id.setBackground(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                        login_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    }

                home_id.setTextColor(getResources().getColor(R.color.colorGreyDark));
                about_id.setTextColor(getResources().getColor(R.color.colorWhite));
                login_id.setTextColor(getResources().getColor(R.color.colorGreyDark));

                homeFramelaout.setVisibility(View.GONE);
                AboutusFramelaout.setVisibility(View.VISIBLE);
                loginFramelaout.setVisibility(View.GONE);

                }

           // }
        });


        login_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    home_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    about_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    login_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                } else {
                    home_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    about_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    login_id.setBackground(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                }

                home_id.setTextColor(getResources().getColor(R.color.colorGreyDark));
                about_id.setTextColor(getResources().getColor(R.color.colorGreyDark));
                login_id.setTextColor(getResources().getColor(R.color.colorWhite));

                homeFramelaout.setVisibility(View.GONE);
                AboutusFramelaout.setVisibility(View.GONE);
                loginFramelaout.setVisibility(View.VISIBLE);









            }
        });





        whatsapp_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = "+91 6366225334"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = ColiveAndStayabodeHomeActivity.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(ColiveAndStayabodeHomeActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });



        submit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String company_code_str=company_code_id.getText().toString().trim();
                String password_str=password_id.getText().toString().trim();

                if (validate(company_code_str, password_str)) {

                    pDialog = ProgressDialog.show(ColiveAndStayabodeHomeActivity.this, "", "Please wait...", true,false);
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
                                    JSONObject jsoncAddressObject= new JSONObject(jsondataObject.getString("cAddress"));
                                    SharedPreferences sharedPreferences = getSharedPreferences("corporateLogin",MODE_PRIVATE);


                                    if(jsondataObject.getBoolean("isPasswordExpired"))
                                    {
                                        final Dialog dialog = new Dialog(ColiveAndStayabodeHomeActivity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.change_pwd);

                                        ImageView close_id = (ImageView) dialog.findViewById(R.id.close_id);
                                        EditText password_id2 = (EditText) dialog.findViewById(R.id.password_id);
                                        EditText cpassword_id2 = (EditText) dialog.findViewById(R.id.cpassword_id);
                                        Button chengepwd_id = (Button) dialog.findViewById(R.id.chengepwd_id);
                                       String password_str2=password_id2.getText().toString();
                                       String cpassword_str2=cpassword_id2.getText().toString();


                                        chengepwd_id.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                boolean allFieldFill=true;
                                                if (password_id2.getText().toString().equals(null) || password_id2.getText().toString().equals("")) {
                                                    password_id2.setError("Enter a New Password");
                                                    allFieldFill=false;
                                                } else {
                                                    password_id2.setError(null);

                                                }

                                                if (cpassword_id2.getText().toString().equals(null) || cpassword_id2.getText().toString().equals("")) {
                                                    cpassword_id2.setError("Enter a Confirm Password");
                                                    allFieldFill=false;
                                                } else {
                                                    if(password_id2.getText().toString().equals(cpassword_id2.getText().toString()))
                                                    {
                                                        cpassword_id2.setError(null);
                                                        allFieldFill=true;
                                                    }else
                                                    {
                                                        cpassword_id2.setError("Password mismatch");
                                                        allFieldFill=false;
                                                    }

                                                }



                                                try {
                                                    String   companyCode = jsondataObject.getString("phoneNumber");
                                                    String   currentPassword = jsondataObject.getString("password");
                                                    String   newPassword = cpassword_id2.getText().toString().trim();
                                                    if (allFieldFill)
                                                    {
                                                        dialog.cancel();
                                                        UpdatePwd(companyCode,currentPassword,newPassword);
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }





                                            }
                                        });




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

                                        if(jsondataObject.getBoolean("isAdmin"))
                                        {
                                            Intent intent = new Intent(ColiveAndStayabodeHomeActivity.this, CorporateMenuWithNavigatioinAdmin.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        }else
                                        {
                                            Intent intent = new Intent(ColiveAndStayabodeHomeActivity.this, CorporateMenuWithNavigatioin.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        }
                                    }



                                }else
                                {

                                    final Dialog dialog = new Dialog(ColiveAndStayabodeHomeActivity.this);
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


                                final Dialog dialog = new Dialog(ColiveAndStayabodeHomeActivity.this);
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
                    }) ;
                    ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);




                }









//                Intent intent = new Intent(CorporateLoginActivity.this, CorporateMenuWithNavigatioinAdmin.class);
//                startActivity(intent);
//                finish();

            }
        });




        if(Globaluse.frommenu.equals(" "))
        {

        }else
        {
            login_id.setVisibility(View.INVISIBLE);
            if(Globaluse.frommenu.equals("home"))
            {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    home_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                    about_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    login_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                } else {
                    home_id.setBackground(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                    about_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    login_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                }

                home_id.setTextColor(getResources().getColor(R.color.colorWhite));
                about_id.setTextColor(getResources().getColor(R.color.colorGreyDark));
                login_id.setTextColor(getResources().getColor(R.color.colorGreyDark));

                homeFramelaout.setVisibility(View.VISIBLE);
                AboutusFramelaout.setVisibility(View.GONE);
                loginFramelaout.setVisibility(View.GONE);
            }else
            {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    home_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    about_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                    login_id.setBackgroundDrawable(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                } else {
                    home_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                    about_id.setBackground(getResources().getDrawable(R.drawable.corporate_gradient_background_for_login));
                    login_id.setBackground(getResources().getDrawable(R.drawable.corporate_menu_bg_white));
                }

                home_id.setTextColor(getResources().getColor(R.color.colorGreyDark));
                about_id.setTextColor(getResources().getColor(R.color.colorWhite));
                login_id.setTextColor(getResources().getColor(R.color.colorGreyDark));

                homeFramelaout.setVisibility(View.GONE);
                AboutusFramelaout.setVisibility(View.VISIBLE);
                loginFramelaout.setVisibility(View.GONE);
            }

        }
    }



    public void onBackPressed() {
        if (exit)
            ColiveAndStayabodeHomeActivity.this.finish();
        else {
            if(Globaluse.frommenu.equals(" "))
            {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
            else
            {
                finish();
            }



        }

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








    public void  UpdatePwd(String companyCode,String currentPassword,String newPassword)
    {

        JSONObject inputObject = new JSONObject();
        try {
            inputObject.put("companyCode",companyCode);
            inputObject.put("oldPassword",currentPassword);
            inputObject.put("newPassword",newPassword);
            inputObject.put("confirmPassword",newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = APIBaseURL.BASEURLLINK_B2B_change_PWD;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                // Toast.makeText(CorporatebussinessInformation.this, response.optString("errorMessage"), Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if(jsonObject.getString("isSuccess").equals("true"))
                    {
                        Toast.makeText(ColiveAndStayabodeHomeActivity.this, jsonObject.getString("successMessage"), Toast.LENGTH_LONG).show();

                        //finish();
                    }else
                    {
                        final Dialog dialog = new Dialog(ColiveAndStayabodeHomeActivity.this);
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

                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    //JSONArray errors = data.getJSONArray("errors");
                    // JSONObject jsonMessage = errors.getJSONObject(0);
                    // String message = jsonMessage.getString("message");
                    //Toast.makeText(getApplicationContext(), data.get("errorMessage"), Toast.LENGTH_LONG).show();


                    final Dialog dialog = new Dialog(ColiveAndStayabodeHomeActivity.this);
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
