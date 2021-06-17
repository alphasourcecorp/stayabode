package stayabode.foodyHive.activities.consumers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AboutUsAndGetHelpWebViewActivity extends AppCompatActivity {

    public static WebView webView;

    public static String email = "";
    public static String userName = "";
    public static String dob = "";
    public static String createdDate = "";

    public static AboutUsAndGetHelpWebViewActivity aboutUsAndGetHelpWebViewActivity;
    public static String jsonResult = "";
    Toolbar toolbar;


    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarColor));
        }
        setContentView(R.layout.web_view_authentication);
        aboutUsAndGetHelpWebViewActivity = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        webView = findViewById(R.id.webView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        new WebView(getApplicationContext()).clearCache(true);
        if(getIntent().getStringExtra("From").equals("GetHelp"))
        {

            toolbar.setTitle("Get Help");
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.loadUrl("https://www.foodyhive.com/mobile-help");//production
            //webView.loadUrl("http://www.alphasource-team.xyz/mobile-help");//test
            webView.clearView();
            webView.measure(100, 100);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
        }
        else if(getIntent().getStringExtra("From").equals("About"))
        {
            toolbar.setTitle("About");
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.loadUrl("https://www.foodyhive.com/mobile-about-us");//production
            //webView.loadUrl("http://www.alphasource-team.xyz/mobile-about-us");//test
            webView.clearView();
            webView.measure(100, 100);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
        }
        else
        {
            toolbar.setTitle("Login");
            //webView.loadUrl("https://foodyhiveprod.b2clogin.com/foodyhiveprod.onmicrosoft.com/oauth2/v2.0/authorize?p=b2c_1a_signuporsigninwithphone&response_type=token%20id_token&client_id=29c00184-68ea-4144-9df9-29aa4e43614d&state=bUpLSkxwSElQQ2FNV3BwNDBjUUxjMzFOYlNKYWhhaUZERkFqUF9BTXRRS243&redirect_uri=https%3A%2F%2Fwww.foodyhive.com%2Findex.html&scope=openid%20profile%20https%3A%2F%2Ffoodyhiveprod.onmicrosoft.com%2Fapi%2Fuser_impersonation&nonce=bUpLSkxwSElQQ2FNV3BwNDBjUUxjMzFOYlNKYWhhaUZERkFqUF9BTXRRS243");
            webView.loadUrl("https://foodyhiveb2c.b2clogin.com//tfp/foodyhiveb2c.onmicrosoft.com/B2C_1_foodyhive_susi/oauth2/v2.0/authorize?client_id=493008f4-a5d7-446b-8580-f40af1823a28&nonce=anyRandomValue&redirect_uri=https://jwt.ms&scope=https://foodyhiveb2c.onmicrosoft.com/api/read&response_type=code");
        }


//        webView.loadUrl("https://foodyhiveb2c.b2clogin.com/foodyhiveb2c.onmicrosoft.com/oauth2/v2.0/authorize?p=B2C_1_foodyhive_susi&client_id=493008f4-a5d7-446b-8580-f40af1823a28&nonce=defaultNonce&redirect_uri=com.onmicrosoft.foodyhiveb2c.homefood%3A%2F%2Foauth%2Fredirect&scope=openid%20profile%20https://foodyhiveb2c.onmicrosoft.com/foodyhiveapi/user_impersonation&response_type=id_token&token_type=Bearer&prompt=login");
//        webView.loadUrl("https://foodyhiveb2c.b2clogin.com/foodyhiveb2c.onmicrosoft.com/oauth2/v2.0/authorize?p=B2C_1_foodyhive_susi&client_id=493008f4-a5d7-446b-8580-f40af1823a28&nonce=defaultNonce&redirect_uri=com.onmicrosoft.foodyhiveb2c.homefood%3A%2F%2Foauth%2Fredirect&scope=openid%20profile%20https%3A%2F%2Ffoodyhiveb2c.onmicrosoft.com%2Ffoodyhiveapi%2Fuser_impersonation&response_type=id_token&prompt=login");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("Current URl",url );
                progressDialog.dismiss();
                if(url.contains("://oauth/redirect#id_token="))
                {

                    String onlyAccessToken = url.substring(url.indexOf("=") + 1);
                    Log.d("GetAccessToken",onlyAccessToken + "  Received");
                    webView.setVisibility(View.GONE);
                    SaveSharedPreference.SaveAzureAdToken(getApplicationContext(), onlyAccessToken);
                    try {
                        getDecodedJwt(onlyAccessToken);
                        decoded(onlyAccessToken);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.d("WebViewStarted", "your current url when webpage loading.." + url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d("WebViewLoadingRe" , url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("WebViewLoading" , url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    public String getDecodedJwt(String jwt) throws JSONException {
        String result = "";

        String[] parts = jwt.split("[.]");
        try
        {
            int index = 1;
            for(String part: parts)
            {
                if (index >= 2)
                    break;

                index++;
                byte[] partAsBytes = part.getBytes("UTF-8");
                String decodedPart = new String(java.util.Base64.getUrlDecoder().decode(partAsBytes), "UTF-8");

                result += decodedPart;
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("Couldnt decode jwt", e);
        }


        Log.d("Result",result);
//        {
//            "typ": "JWT",
//                "alg": "RS256",
//                "kid": "X5eXk4xyojNFum1kl2Ytv8dlNP4-c57dO6QGTVBwaNk"
//        }
//        {
//            "exp": 1592992363,
//                    "nbf": 1592988763,
//                    "ver": "1.0",
//                    "iss": "https://foodyhiveb2c.b2clogin.com/23ba4ef9-ad6b-434c-b520-e9e8f7b62ae6/v2.0/",
//                    "sub": "96709662-3f30-4284-90f7-623a63bf5c92",
//                    "aud": "493008f4-a5d7-446b-8580-f40af1823a28",
//                    "nonce": "defaultNonce",
//                    "iat": 1592988763,
//                    "auth_time": 1592988763,
//                    "oid": "96709662-3f30-4284-90f7-623a63bf5c92",
//                    "name": "Vignesh",
//                    "emails": ["vignesh.r@kalpitatechnologies.com"],
//            "tfp": "B2C_1_foodyhive_susi"
//        }
        jsonResult = result;
        JSONObject obj = new JSONObject(result);
        Log.d("ResultInJSONObject",obj + "");
//        JSONArray jsonArray = new JSONArray(result);
//        Log.d("ResultInJSONArray",jsonArray + "");
        return result;
    }

    public static void decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));

            // Body

//            {
//                "exp": 1592996525,
//                    "nbf": 1592992925,
//                    "ver": "1.0",
//                    "iss": "https://foodyhiveb2c.b2clogin.com/23ba4ef9-ad6b-434c-b520-e9e8f7b62ae6/v2.0/",
//                    "sub": "96709662-3f30-4284-90f7-623a63bf5c92",
//                    "aud": "493008f4-a5d7-446b-8580-f40af1823a28",
//                    "nonce": "defaultNonce",
//                    "iat": 1592992925,
//                    "auth_time": 1592992925,
//                    "oid": "96709662-3f30-4284-90f7-623a63bf5c92",
//                    "name": "Vignesh",
//                    "emails": ["vignesh.r@kalpitatechnologies.com"],
//                "tfp": "B2C_1_foodyhive_susi"
//            }

            // Header
//            {
//                "typ": "JWT",
//                    "alg": "RS256",
//                    "kid": "X5eXk4xyojNFum1kl2Ytv8dlNP4-c57dO6QGTVBwaNk"
//            }

            JSONObject jsonObject = new JSONObject(getJson(split[1]));
            JSONArray emails = jsonObject.getJSONArray("emails");
            email = emails.get(0).toString();
            userName = jsonObject.optString("name");

            postUserInfo(email,userName);
//            checkUserExistsOrNot();
        } catch (UnsupportedEncodingException e) {
            //Error
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }


    public static void postUserInfo(String email,String userName) throws JSONException {
        String url = APIBaseURL.LoginUserInfoPOSTURL;


//        {
//            "emailId": ["karthik8888@yopmail.com"],
//            "name": "Passs",
//                "location": "",
//                "dob": "2020-06-24T07:30:10.823Z",
//                "addressLine1": "",
//                "addressLine2": "",
//                "pinCode": "",
//                "state": "",
//                "country": "",
//                "phoneNo": [""],
//            "profilePic": "",
//                "roleId": "",
//                "createdDate": "2020-06-24T07:30:10.823Z",
//                "status": "",
//                "isDeleted": false,
//                "healthInfo": {
//            "height": "",
//                    "weight": ""
//        }
//        }

        JSONObject inputObject = new JSONObject();
        JSONArray emailArray = new JSONArray();
        JSONArray phoneNoArray = new JSONArray();
        emailArray.put(email);
        inputObject.put("emailId",emailArray);
        inputObject.put("dob","1993-03-24T07:30:10.823Z");
        inputObject.put("name",userName);
        inputObject.put("createdDate","2020-06-24T07:30:10.823Z");
        phoneNoArray.put("");
        inputObject.put("phoneNo",phoneNoArray);
        inputObject.put("location","");
        inputObject.put("addressLine1","");
        inputObject.put("addressLine2","");
        inputObject.put("pinCode","");
        inputObject.put("state","");
        inputObject.put("country","");
        inputObject.put("profilePic","");
        inputObject.put("roleId","");
        inputObject.put("status","");
        inputObject.put("isDeleted",false);
        JSONObject healthInfoObject = new JSONObject();
        healthInfoObject.put("height","");
        healthInfoObject.put("weight","");
        inputObject.put("healthInfo",healthInfoObject);



        Log.d("ResponseInput",inputObject + " ");


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseUser","Success" + response);

                Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Success In Insert User" + response, Toast.LENGTH_SHORT).show();

                getUserRole();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseVolleyError(error);
                Log.d("ResponseUser","Error In Posting User" + error.getMessage());
                Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();

            }
        }, aboutUsAndGetHelpWebViewActivity) ;
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"userPostTaq");
    }

    public static void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            JSONArray errors = data.getJSONArray("errors");
            JSONObject jsonMessage = errors.getJSONObject(0);
            String message = jsonMessage.getString("message");
            Toast.makeText(aboutUsAndGetHelpWebViewActivity, message + " Nor Properly Error Displayed", Toast.LENGTH_LONG).show();
            Log.d("InputPostUserError","Error" + message);
        } catch (JSONException e) {
        } catch (UnsupportedEncodingException errorr) {
        }
    }



    public static void getUserRole()
    {

        String url = APIBaseURL.getUserRoleInfo + email;
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ResponseRole","Success" + response);
//                {
//                    "isSuccess": true,
//                        "errorMessage": "",
//                        "data": [
//                    {
//                        "userDetails": {
//                        "id": "5ecc923deeda422a2c7de3ff",
//                                "emailId": [
//                        "vignesh.r@kalpitatechnologies.com"
//                ],
//                        "name": "Vignesh",
//                                "location": "",
//                                "lat": null,
//                                "lng": null,
//                                "dob": "2020-05-26T03:51:18.115Z",
//                                "addressLine1": "",
//                                "addressLine2": "",
//                                "pinCode": "",
//                                "state": "",
//                                "country": "",
//                                "phoneNo": [
//                        ""
//                ],
//                        "profilePic": "",
//                                "roleId": "104",
//                                "roleName": "Consumer",
//                                "createdDate": "2020-05-26T03:51:18.115Z",
//                                "status": "",
//                                "isDeleted": false,
//                                "healthInfo": {
//                            "height": "",
//                                    "weight": ""
//                        }
//                    },
//                        "lstPermision": [],
//                        "workFlowId": ""
//                    }
//    ],
//                    "count": 0
//                }

//                {
//                    "isSuccess": true,
//                        "errorMessage": "",
//                        "data": [],
//                    "count": 0
//                }

                Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Success Role " + response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("data") )
                    {
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        if(dataArray.length() !=0)
                        {

                            JSONObject jsonObject1 = dataArray.getJSONObject(0);
                            JSONObject userDetailsObject = new JSONObject();
                            if(jsonObject.has("userDetails"))
                            {
                                userDetailsObject = jsonObject1.getJSONObject("userDetails");
                            }

                            JSONArray emailIdArray = userDetailsObject.getJSONArray("emailId");
                            webView.setVisibility(View.GONE);
//                            SaveSharedPreference.setLoggedIn(webViewActivity, true,userDetailsObject.optString("roleName"),userDetailsObject.optString("roleId"),userDetailsObject.optString("id"),userDetailsObject.optString("name"),emailIdArray.get(0).toString());
//                            Intent intent = new Intent(webViewActivity,LoginActivity.class);
//                            webViewActivity.startActivity(intent);
//                            webViewActivity.finish();
                            if(SaveSharedPreference.getLoggedInUserRole(aboutUsAndGetHelpWebViewActivity).equals("Admin"))
                            {
                                Intent verifOTPIntent = new Intent(aboutUsAndGetHelpWebViewActivity, MainActivity.class);
                                verifOTPIntent.putExtra("Role","Admin");
                                aboutUsAndGetHelpWebViewActivity.startActivity(verifOTPIntent);
                                aboutUsAndGetHelpWebViewActivity.finish();
                            }
                            else if(SaveSharedPreference.getLoggedInUserRole(aboutUsAndGetHelpWebViewActivity).equals("Chef"))
                            {
                                Intent verifOTPIntent = new Intent(aboutUsAndGetHelpWebViewActivity, ChefsMainActivity.class);
                                verifOTPIntent.putExtra("Role","Chef");
                                aboutUsAndGetHelpWebViewActivity.startActivity(verifOTPIntent);
                                aboutUsAndGetHelpWebViewActivity.finish();
                            }
                            else if(SaveSharedPreference.getLoggedInUserRole(aboutUsAndGetHelpWebViewActivity).equals("Franchisee"))
                            {
                                Intent verifOTPIntent = new Intent(aboutUsAndGetHelpWebViewActivity,MainActivity.class);
                                verifOTPIntent.putExtra("Role","Franchisee");
                                aboutUsAndGetHelpWebViewActivity.startActivity(verifOTPIntent);
                                aboutUsAndGetHelpWebViewActivity.finish();
                            }
                            else if(SaveSharedPreference.getLoggedInUserRole(aboutUsAndGetHelpWebViewActivity).equals("Consumer"))
                            {
                                Intent intent = new Intent(aboutUsAndGetHelpWebViewActivity, LocationSelectionActivity.class);
                                aboutUsAndGetHelpWebViewActivity.startActivity(intent);
                                aboutUsAndGetHelpWebViewActivity.finish();
                            }
                        }
                        else
                        {
                            Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                parseVolleyError(error);
                Log.d("ResponseRole","Error" + error.getMessage());
                Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Error Role There" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }, aboutUsAndGetHelpWebViewActivity) ;
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"userPostTaq");
    }

    public static void checkUserExistsOrNot()
    {
        String url = APIBaseURL.checkUserExistsOrNot + email;



            CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("ResponseCheckUser","Success" + response);
//                {
//                    "isSuccess": true,
//                        "errorMessage": "",
//                        "data": [
//                    {
//                        "userDetails": {
//                        "id": "5ecc923deeda422a2c7de3ff",
//                                "emailId": [
//                        "vignesh.r@kalpitatechnologies.com"
//                ],
//                        "name": "Vignesh",
//                                "location": "",
//                                "lat": null,
//                                "lng": null,
//                                "dob": "2020-05-26T03:51:18.115Z",
//                                "addressLine1": "",
//                                "addressLine2": "",
//                                "pinCode": "",
//                                "state": "",
//                                "country": "",
//                                "phoneNo": [
//                        ""
//                ],
//                        "profilePic": "",
//                                "roleId": "104",
//                                "roleName": "Consumer",
//                                "createdDate": "2020-05-26T03:51:18.115Z",
//                                "status": "",
//                                "isDeleted": false,
//                                "healthInfo": {
//                            "height": "",
//                                    "weight": ""
//                        }
//                    },
//                        "lstPermision": [],
//                        "workFlowId": ""
//                    }
//    ],
//                    "count": 0
//                }

//                {
//                    "isSuccess": true,
//                        "errorMessage": "",
//                        "data": [],
//                    "count": 0
//                }

                    Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Success User There " + response, Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.has("data") )
                        {
                            JSONArray dataArray = jsonObject.getJSONArray("data");

                            if(dataArray.length() !=0)
                            {

                                JSONObject jsonObject1 = dataArray.getJSONObject(0);
                                JSONObject userDetailsObject = new JSONObject();
                                if(jsonObject.has("userDetails"))
                                {
                                    userDetailsObject = jsonObject1.getJSONObject("userDetails");
                                }

                                JSONArray emailIdArray = userDetailsObject.getJSONArray("emailId");
//                                SaveSharedPreference.setLoggedIn(webViewActivity, true,userDetailsObject.optString("roleName"),userDetailsObject.optString("roleId"),userDetailsObject.optString("id"),userDetailsObject.optString("name"),emailIdArray.get(0).toString());
//                                Intent intent = new Intent(webViewActivity,LoginActivity.class);
//                                webViewActivity.startActivity(intent);
//                                webViewActivity.finish();
                                getUserRole();
                            }
                            else
                            {
                                postUserInfo(email,userName);
                            }

                        }
                        else
                        {
                            Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    parseVolleyError(error);
                    Log.d("ResponseCheckUser","Error" + error.getMessage());
                    Toast.makeText(aboutUsAndGetHelpWebViewActivity, "Error User there" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }, aboutUsAndGetHelpWebViewActivity) ;
            ApplicationController.getInstance().addToRequestQueue(stringRequest,"userPostTaq");
        }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

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

}
