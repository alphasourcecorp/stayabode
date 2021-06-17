/*
 * Copyright 2015 The AppAuth for Android Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stayabode.foodyHive.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.IntroScreenActivity;
import stayabode.foodyHive.activities.consumers.LocationSelectionActivity;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.android.material.snackbar.Snackbar;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceDiscovery;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import okio.Okio;


/**
 * Displays the authorized state of the user. This activity is provided with the outcome of the
 * authorization flow, which it uses to negotiate the final authorized state,
 * by performing an authorization code exchange if necessary. After this, the activity provides
 * additional post-authorization operations if available, such as fetching user info and refreshing
 * access tokens.
 */
public class TokenActivity extends AppCompatActivity {
    private static final String TAG = "TokenActivity";

    private static final String KEY_USER_INFO = "userInfo";

    private static TokenActivity tokenActivity ;

    public static String email = "";
    public static String userMobileNo = "";
    public static String userName = "";

    private AuthorizationService mAuthService;
    private AuthStateManager mStateManager;
    private final AtomicReference<JSONObject> mUserInfoJson = new AtomicReference<>();
    private ExecutorService mExecutor;
    private Configuration mConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStateManager = AuthStateManager.getInstance(this);
        mExecutor = Executors.newSingleThreadExecutor();
        mConfiguration = Configuration.getInstance(this);

        Configuration config = Configuration.getInstance(this);
        if (config.hasConfigurationChanged()) {
            Toast.makeText(
                    this,
                    "Configuration change detected",
                    Toast.LENGTH_SHORT)
                    .show();
            signOut();
            return;
        }

        mAuthService = new AuthorizationService(
                this,
                new AppAuthConfiguration.Builder()
                        .setConnectionBuilder(config.getConnectionBuilder())
                        .build());

//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView()
//                    .setSystemUiVisibility(
//                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_token);

        tokenActivity = this;
        getSupportActionBar().hide();

        displayLoading("Fetching User Details...");

        if (savedInstanceState != null) {
            try {
                mUserInfoJson.set(new JSONObject(savedInstanceState.getString(KEY_USER_INFO)));
            } catch (JSONException ex) {
                Log.e(TAG, "Failed to parse saved user info JSON, discarding", ex);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }

        if (mStateManager.getCurrent().isAuthorized()) {
            displayAuthorized();
            return;
        }

// the stored AuthState is incomplete, so check if we are currently receiving the result of
// the authorization flow from the browser.
        AuthorizationResponse response = AuthorizationResponse.fromIntent(getIntent());
        AuthorizationException ex = AuthorizationException.fromIntent(getIntent());

        if (response != null || ex != null) {
            mStateManager.updateAfterAuthorization(response, ex);
        }

        if (response != null && response.authorizationCode != null) {
// authorization code exchange is required
            mStateManager.updateAfterAuthorization(response, ex);
            exchangeAuthorizationCode(response);
        } else if (ex != null) {
            displayNotAuthorized("Authorization flow failed: " + ex.getMessage());
        } else {
            displayNotAuthorized("No authorization state retained - reauthorization required");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
// user info is retained to survive activity restarts, such as when rotating the
// device or switching apps. This isn't essential, but it helps provide a less
// jarring UX when these events occur - data does not just disappear from the view.
        if (mUserInfoJson.get() != null) {
            state.putString(KEY_USER_INFO, mUserInfoJson.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuthService.dispose();
        mExecutor.shutdownNow();
    }

    @MainThread
    private void displayNotAuthorized(String explanation) {
        findViewById(R.id.not_authorized).setVisibility(View.VISIBLE);
        findViewById(R.id.authorized).setVisibility(View.GONE);
        findViewById(R.id.loading_container).setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.explanation)).setText(explanation);
        findViewById(R.id.reauth).setOnClickListener((View view) -> signOut());
    }

    @MainThread
    private void displayLoading(String message) {
        findViewById(R.id.loading_container).setVisibility(View.VISIBLE);
        findViewById(R.id.authorized).setVisibility(View.GONE);
        findViewById(R.id.not_authorized).setVisibility(View.GONE);

        ((TextView) findViewById(R.id.loading_description)).setText(message);
    }

    @MainThread
    private void displayAuthorized() {
        findViewById(R.id.authorized).setVisibility(View.GONE);
        findViewById(R.id.not_authorized).setVisibility(View.GONE);
        findViewById(R.id.loading_container).setVisibility(View.VISIBLE);

        AuthState state = mStateManager.getCurrent();

        TextView refreshTokenInfoView = (TextView) findViewById(R.id.refresh_token_info);
        refreshTokenInfoView.setText((state.getRefreshToken() == null)
                ? R.string.no_refresh_token_returned
                : R.string.refresh_token_returned);

        if(state.getAccessToken() == null)
        {

        }
        else
        {
            SaveSharedPreference.SaveAzureAdToken(getApplicationContext(), state.getAccessToken());
            try {
                decoded(state.getAccessToken());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TextView idTokenInfoView = (TextView) findViewById(R.id.id_token_info);
        idTokenInfoView.setText((state.getIdToken()) == null
                ? R.string.no_id_token_returned
                : R.string.id_token_returned);

        TextView accessTokenInfoView = (TextView) findViewById(R.id.access_token_info);
        if (state.getAccessToken() == null) {
            accessTokenInfoView.setText(R.string.no_access_token_returned);
        } else {
            Long expiresAt = state.getAccessTokenExpirationTime();
            if (expiresAt == null) {
                accessTokenInfoView.setText("No Access Token Expired");
            } else if (expiresAt < System.currentTimeMillis()) {
                accessTokenInfoView.setText("Access Token Expired");
            } else {
                String template = getResources().getString(R.string.access_token_expires_at);
                accessTokenInfoView.setText(String.format(template,
                        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ZZ").print(expiresAt)));
            }
        }

        Button refreshTokenButton = (Button) findViewById(R.id.refresh_token);
        refreshTokenButton.setVisibility(state.getRefreshToken() != null
                ? View.GONE
                : View.GONE);
        refreshTokenButton.setOnClickListener((View view) -> refreshAccessToken());

        Button viewProfileButton = (Button) findViewById(R.id.view_profile);

        AuthorizationServiceDiscovery discoveryDoc =
                state.getAuthorizationServiceConfiguration().discoveryDoc;
        if ((discoveryDoc == null || discoveryDoc.getUserinfoEndpoint() == null)
                && mConfiguration.getUserInfoEndpointUri() == null) {
            viewProfileButton.setVisibility(View.GONE);
        } else {
            viewProfileButton.setVisibility(View.GONE);
            viewProfileButton.setOnClickListener((View view) -> fetchUserInfo());
        }

        ((Button) findViewById(R.id.sign_out)).setOnClickListener((View view) -> signOut());

        View userInfoCard = findViewById(R.id.userinfo_card);
        JSONObject userInfo = mUserInfoJson.get();
        if (userInfo == null) {
            userInfoCard.setVisibility(View.INVISIBLE);
        } else {
            try {
                String name = "???";
                if (userInfo.has("name")) {
                    name = userInfo.getString("name");
                }
                ((TextView) findViewById(R.id.userinfo_name)).setText(name);

                if (userInfo.has("picture")) {
//    GlideApp.with(TokenActivity.this)
//    .load(Uri.parse(userInfo.getString("picture")))
//    .fitCenter()
//    .into((ImageView) findViewById(R.id.userinfo_profile));
                }

                ((TextView) findViewById(R.id.userinfo_json)).setText(mUserInfoJson.toString());
                userInfoCard.setVisibility(View.VISIBLE);
            } catch (JSONException ex) {
                Log.e(TAG, "Failed to read userinfo JSON", ex);
            }
        }
    }

    @MainThread
    private void refreshAccessToken() {
        displayLoading("Refreshing access token");
        performTokenRequest(
                mStateManager.getCurrent().createTokenRefreshRequest(),
                this::handleAccessTokenResponse);
    }

    @MainThread
    private void exchangeAuthorizationCode(AuthorizationResponse authorizationResponse) {
        displayLoading("Fetching User..");
        performTokenRequest(
                authorizationResponse.createTokenExchangeRequest(),
                this::handleCodeExchangeResponse);
    }

    @MainThread
    private void performTokenRequest(
            TokenRequest request,
            AuthorizationService.TokenResponseCallback callback) {
        ClientAuthentication clientAuthentication;
        try {
            clientAuthentication = mStateManager.getCurrent().getClientAuthentication();
        } catch (ClientAuthentication.UnsupportedAuthenticationMethod ex) {
            Log.d(TAG, "Token request cannot be made, client authentication for the token "
                    + "endpoint could not be constructed (%s)", ex);
            displayNotAuthorized("Client authentication method is unsupported");
            return;
        }

        mAuthService.performTokenRequest(
                request,
                clientAuthentication,
                callback);
    }

    @WorkerThread
    private void handleAccessTokenResponse(
            @Nullable TokenResponse tokenResponse,
            @Nullable AuthorizationException authException) {
        mStateManager.updateAfterTokenResponse(tokenResponse, authException);
        runOnUiThread(this::displayAuthorized);
    }

    @WorkerThread
    private void handleCodeExchangeResponse(
            @Nullable TokenResponse tokenResponse,
            @Nullable AuthorizationException authException) {

        mStateManager.updateAfterTokenResponse(tokenResponse, authException);
        if (!mStateManager.getCurrent().isAuthorized()) {
            final String message = "Authorization Code exchange failed"
                    + ((authException != null) ? authException.error : "");

// WrongThread inference is incorrect for lambdas
//noinspection WrongThread
            runOnUiThread(() -> displayNotAuthorized(message));
        } else {
            runOnUiThread(this::displayAuthorized);
        }
    }

    /**
     * Demonstrates the use of {@link AuthState#performActionWithFreshTokens} to retrieve
     * user info from the IDP's user info endpoint. This callback will negotiate a new access
     * token / id token for use in a follow-up action, or provide an error if this fails.
     */
    @MainThread
    private void fetchUserInfo() {
        displayLoading("Fetching User..");
        mStateManager.getCurrent().performActionWithFreshTokens(mAuthService, this::fetchUserInfo);
    }

    @MainThread
    private void fetchUserInfo(String accessToken, String idToken, AuthorizationException ex) {
        if (ex != null) {
            Log.e(TAG, "Token refresh failed when fetching user info");
            mUserInfoJson.set(null);
            runOnUiThread(this::displayAuthorized);
            return;
        }

        AuthorizationServiceDiscovery discovery =
                mStateManager.getCurrent()
                        .getAuthorizationServiceConfiguration()
                        .discoveryDoc;

        URL userInfoEndpoint;
        try {
            userInfoEndpoint =
                    mConfiguration.getUserInfoEndpointUri() != null
                            ? new URL(mConfiguration.getUserInfoEndpointUri().toString())
                            : new URL(discovery.getUserinfoEndpoint().toString());
        } catch (MalformedURLException urlEx) {
            Log.e(TAG, "Failed to construct user info endpoint URL", urlEx);
            mUserInfoJson.set(null);
            runOnUiThread(this::displayAuthorized);
            return;
        }

        mExecutor.submit(() -> {
            try {
                HttpURLConnection conn =
                        (HttpURLConnection) userInfoEndpoint.openConnection();
                conn.setRequestProperty("Authorization", "Bearer " + accessToken);
                conn.setInstanceFollowRedirects(false);
                String response = Okio.buffer(Okio.source(conn.getInputStream()))
                        .readString(Charset.forName("UTF-8"));
                mUserInfoJson.set(new JSONObject(response));
            } catch (IOException ioEx) {
                Log.e(TAG, "Network error when querying userinfo endpoint", ioEx);
                showSnackbar("Fetching user info failed");
            } catch (JSONException jsonEx) {
                Log.e(TAG, "Failed to parse userinfo response");
                showSnackbar("Failed to parse user info");
            }

            runOnUiThread(this::displayAuthorized);
        });
    }

    @MainThread
    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.coordinator),
                message,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    @MainThread
    private void signOut() {
// discard the authorization and token state, but retain the configuration and
// dynamic client registration (if applicable), to save from retrieving them again.
        AuthState currentState = mStateManager.getCurrent();
        AuthState clearedState =
                new AuthState(currentState.getAuthorizationServiceConfiguration());
        if (currentState.getLastRegistrationResponse() != null) {
            clearedState.update(currentState.getLastRegistrationResponse());
        }
        mStateManager.replace(clearedState);

        Intent mainIntent = new Intent(this, IntroScreenActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
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
            try {
                JSONArray emails = new JSONArray();
                if(jsonObject.has("emails"))
                {
                    emails = jsonObject.getJSONArray("emails");
                }

                if(emails.length()!=0)
                {
                    email = emails.get(0).toString();
                }
                else
                {
                    email = jsonObject.optString("email");
                    userMobileNo = jsonObject.optString("signInNames.phoneNumber");
                }
            }
            catch (Exception e)
            {
                email = jsonObject.optString("email");
                userMobileNo = jsonObject.optString("signInNames.phoneNumber");
                e.printStackTrace();
            }


            userName = jsonObject.optString("name");
            postUserInfo(email,userName,userMobileNo);

//            getUserRole();

//            checkUserExistsOrNot();
        } catch (UnsupportedEncodingException e) {
            //Error
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }



    public static void postUserInfo(String email,String userName,String userMobileNo) throws JSONException {
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







        Calendar now = Calendar.getInstance();
        System.out.println("Current date : " + (now.get(Calendar.MONTH) + 1) + "-"
                + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));

        now = Calendar.getInstance();
        now.add(Calendar.YEAR, -20);
        System.out.println("date before 20 years : " + (now.get(Calendar.MONTH) + 1) + "-"
                + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        String formattedDate = df.format(Calendar.getInstance().getTime());

        getCalculatedDate(formattedDate,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",-20);


        String mydate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(new Date());//sat

        JSONObject inputObject = new JSONObject();
        JSONArray emailArray = new JSONArray();
        emailArray.put(email);
        inputObject.put("emailId",emailArray);
        inputObject.put("name",userName);
        inputObject.put("location","");
        inputObject.put("lat","");
        inputObject.put("lng","");
        inputObject.put("dob",mydate);
        inputObject.put("addressLine1","");
        inputObject.put("addressLine2","");
        inputObject.put("pinCode","");
        inputObject.put("state","");
        inputObject.put("country","");
        JSONArray phoneNoArray = new JSONArray();

        phoneNoArray.put(userMobileNo.replace("+91", "").trim());//sat
        //phoneNoArray.put(userMobileNo);
        inputObject.put("phoneNo",phoneNoArray);
        inputObject.put("profilePic","");
        inputObject.put("roleId","");
        inputObject.put("createdDate",mydate);
        inputObject.put("status","");
        inputObject.put("referredCode","q124fy66jby9ok");
        inputObject.put("isDeleted",false);
        JSONObject healthInfoObject = new JSONObject();
        healthInfoObject.put("height","");
        healthInfoObject.put("weight","");
        inputObject.put("healthInfo",healthInfoObject);



        Log.d("ResponseInput",inputObject + " ");


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ResponseUser","Success" + response);

               // Toast.makeText(tokenActivity, "Success In Insert User" + response, Toast.LENGTH_SHORT).show();
//                {
//                    "isSuccess": true,
//                        "errorMessage": "",
//                        "data": [],
//                    "count": 0
//                }

//                "myReferralCode": "KARANIAPASAHAPAN",
//                        "referredCode": "IDGF234232",
                try {

                        if(response.has("data") )
                        {
                            JSONArray dataArray = response.getJSONArray("data");

                            if(dataArray.length() !=0)
                            {

                                JSONObject jsonObject1 = dataArray.getJSONObject(0);
                                JSONObject userDetailsObject = new JSONObject();
                                //JSONArray phoneNoarray = jsonObject1.getJSONArray("phoneNo");



                                if(jsonObject1.has("userDetails"))
                                {
                                    userDetailsObject = jsonObject1.getJSONObject("userDetails");
                                }

                                JSONArray emailIdArray = userDetailsObject.getJSONArray("emailId");
                                JSONArray phoneNoArray = userDetailsObject.getJSONArray("phoneNo");

                                SaveSharedPreference.setLoggedIn(tokenActivity, true,userDetailsObject.optString("roleName"),userDetailsObject.optString("roleId"),userDetailsObject.optString("id"),userDetailsObject.optString("name"),emailIdArray.get(0).toString(),jsonObject1.optString("workFlowId"),userDetailsObject.optString("profilePic"),userDetailsObject.optString("myReferralCode"),userDetailsObject.optString("referredCode"),false,phoneNoArray.get(0).toString());

//                                Intent intent = new Intent(TrackOrderActivity.this, ConsumerMainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                tokenActivity.startActivity(intent);
//                                tokenActivity.finish();

                              /*  if(SaveSharedPreference.getLoggedInUserRole(tokenActivity).equals("Admin"))
                                {
                                    Intent verifOTPIntent = new Intent(tokenActivity, MainActivity.class);
                                    verifOTPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    verifOTPIntent.putExtra("Role","Admin");
                                    tokenActivity.startActivity(verifOTPIntent);
                                    tokenActivity.finish();
                                }
                                else if(SaveSharedPreference.getLoggedInUserRole(tokenActivity).equals("Chef"))
                                {
                                    Intent verifOTPIntent = new Intent(tokenActivity, ChefsMainActivity.class);
                                    verifOTPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    verifOTPIntent.putExtra("Role","Chef");
                                    tokenActivity.startActivity(verifOTPIntent);
                                    tokenActivity.finish();
                                }
                                else if(SaveSharedPreference.getLoggedInUserRole(tokenActivity).equals("Franchisee"))
                                {
                                    Intent verifOTPIntent = new Intent(tokenActivity,MainActivity.class);
                                    verifOTPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    verifOTPIntent.putExtra("Role","Franchisee");
                                    tokenActivity.startActivity(verifOTPIntent);
                                    tokenActivity.finish();
                                }
                                else if(SaveSharedPreference.getLoggedInUserRole(tokenActivity).equals("Consumer"))
                                {

                                    if(SaveSharedPreference.getLoggedInUserAddress(tokenActivity).equals(""))
                                    {
                                        Intent intent = new Intent(tokenActivity, LocationSelectionActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        tokenActivity.startActivity(intent);
                                        tokenActivity.finish();
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(tokenActivity, ConsumerMainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        tokenActivity.startActivity(intent);
                                        tokenActivity.finish();
                                    }

                                }*/



                                if(SaveSharedPreference.getLoggedInUserRole(tokenActivity).equals("Chef"))
                                {

                                    Intent verifOTPIntent = new Intent(tokenActivity, ChefsMainActivity.class);
                                    verifOTPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    verifOTPIntent.putExtra("Role","Chef");
                                    tokenActivity.startActivity(verifOTPIntent);
                                    tokenActivity.finish();

                                }else
                                {
                                    if(SaveSharedPreference.getLoggedInUserAddress(tokenActivity).equals(""))
                                    {
                                        Intent intent = new Intent(tokenActivity, LocationSelectionActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        tokenActivity.startActivity(intent);
                                        tokenActivity.finish();
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(tokenActivity, ConsumerMainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        tokenActivity.startActivity(intent);
                                        tokenActivity.finish();
                                    }

                                }












                            }
                            else
                            {
//                            postUserInfo(email,userName);
                                // Toast.makeText(tokenActivity, "This user is not registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(tokenActivity, "Unable to Login", Toast.LENGTH_SHORT).show();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ResponseUser","Error In Posting User" + error.getMessage());
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Log.d("Error","TimeoutError");
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    Log.d("Error","AuthError");
//                } else if (error instanceof ServerError) {
//
//                    Log.d("Error","ServerError");
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    Log.d("Error","Network Error");
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    Log.d("Error","ParseError");
//                }
                //Toast.makeText(tokenActivity, "Oops something went wrong Please try again later", Toast.LENGTH_SHORT).show();
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = tokenActivity.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(tokenActivity).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(tokenActivity);

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            tokenActivity.signOut();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(tokenActivity, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();

            headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(tokenActivity));
            Log.d("AccessToken", SaveSharedPreference.getAzureToken(tokenActivity) + " Volley Header");
            return headers;
        }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"userPostTaq");
    }

    public static String getCalculatedDate(String date, String dateFormat, int year) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.YEAR, year);
        try {
            Log.d("Date",s.format(new Date(s.parse(date).getTime())) + "Valid");
            return s.format(new Date(s.parse(date).getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            Log.d("Date", " Not Valid");
            Log.e("TAG", "Error in Parsing Date : " + e.getMessage());
        }
        return null;
    }
}
