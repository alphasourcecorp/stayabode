package stayabode.foodyHive.activities.consumers;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.AnyThread;
import androidx.annotation.ColorRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.ConsumerTabAdapter;
import stayabode.foodyHive.authentication.AuthStateManager;
import stayabode.foodyHive.authentication.BrowserSelectionAdapter;
import stayabode.foodyHive.authentication.Configuration;
import stayabode.foodyHive.authentication.TokenActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.fragments.consumers.ConsumerAllItemsFragment;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.RegistrationRequest;
import net.openid.appauth.RegistrationResponse;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.browser.AnyBrowserMatcher;
import net.openid.appauth.browser.BrowserMatcher;
import net.openid.appauth.browser.ExactBrowserMatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class ViewAllSubItemsActivity extends AppCompatActivity {

    TabLayout tabLayoutViewAll;
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView cartIcon;
    public static TextView cartTotalCountText;

    private static final String TAG = "IntroActivity";

    private static final String EXTRA_FAILED = "failed";
    private static final int RC_AUTH = 100;

    private AuthorizationService mAuthService;
    private AuthStateManager mAuthStateManager;
    private Configuration mConfiguration;

    private final AtomicReference<String> mClientId = new AtomicReference<>();
    private final AtomicReference<AuthorizationRequest> mAuthRequest = new AtomicReference<>();
    private final AtomicReference<CustomTabsIntent> mAuthIntent = new AtomicReference<>();
    private CountDownLatch mAuthIntentLatch = new CountDownLatch(1);
    private static ExecutorService mExecutor;

    private static boolean mUsePendingIntents;
    LinearLayout startDateIcon,date_layout;
    EditText startDateEditText;
    DatePickerDialog picker;

    @NonNull
    private BrowserMatcher mBrowserMatcher = AnyBrowserMatcher.INSTANCE;

    public static ViewAllSubItemsActivity viewAllSubItemsListsActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExecutor = Executors.newSingleThreadExecutor();
        mAuthStateManager = AuthStateManager.getInstance(this);
        mConfiguration = Configuration.getInstance(this);
        setContentView(R.layout.fragment_consumer_your_orders);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        viewAllSubItemsListsActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Dish Categories");

        cartIcon = findViewById(R.id.cartIcon);
        cartTotalCountText = findViewById(R.id.cartTotalCountText);
        cartTotalCountText.setVisibility(View.VISIBLE);
        cartIcon.setVisibility(View.VISIBLE);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllSubItemsActivity.this, ConsumerMyBasketActivity.class);
                startActivity(intent);
            }
        });

        tabLayoutViewAll=findViewById(R.id.tabLayoutViewAll);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        tabLayout.setVisibility(View.GONE);
        tabLayoutViewAll.setVisibility(View.VISIBLE);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add("Breakfast");
        arrayList.add("Lunch");
        arrayList.add("Snacks");
        arrayList.add("Dinner");
        arrayList.add("Other");
        prepareViewager(viewPager,arrayList);
        tabLayoutViewAll.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);

        configureBrowserSelector();
        if (mConfiguration.hasConfigurationChanged()) {
            // discard any existing authorization state due to the change of configuration
            Log.i(TAG, "Configuration change detected, discarding old state");
            mAuthStateManager.replace(new AuthState());
            mConfiguration.acceptConfiguration();
        }

        if (getIntent().getBooleanExtra(EXTRA_FAILED, false)) {
            displayAuthCancelled();
        }


        displayLoading("Initializing");
        mExecutor.submit(this::initializeAppAuth);


        date_layout = findViewById(R.id.date_layout);
        date_layout.setVisibility(View.GONE);
        startDateEditText = findViewById(R.id.startDateEditText);
        startDateIcon = findViewById(R.id.startDateIcon);
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getApplicationContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //  startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    Date date = format.parse(dateFormat);
                                    startDateEditText.setText(format.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //isValidateDate();
                            }
                        }, year, month, day);

                picker.getDatePicker().setMinDate(System.currentTimeMillis());
                cldr.add(Calendar.YEAR, 1);
                picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();
            }
        });

        startDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateEditText.performClick();
            }
        });
    }

    //add viewAllTabs to the viewpager
    private void prepareViewager(ViewPager viewPager, ArrayList<String> arrayList) {
        ConsumerTabAdapter consumerTabAdapter=new ConsumerTabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ConsumerAllItemsFragment consumerAllItemsFragment =new ConsumerAllItemsFragment();
        for(int i=0;i<arrayList.size();i++){
            Bundle bundle=new Bundle();
            bundle.putString("title",arrayList.get(i));
            consumerAllItemsFragment.setArguments(bundle);
            consumerTabAdapter.createFragment(consumerAllItemsFragment,arrayList.get(i));
            consumerAllItemsFragment=new ConsumerAllItemsFragment();
        }
        viewPager.setAdapter(consumerTabAdapter);
    }

    // Back to previous screen
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
    protected void onStart() {
        super.onStart();
        if (mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mExecutor.shutdownNow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAuthService != null) {
            mAuthService.dispose();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartListsinViewAllPage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayAuthOptions();
        if (resultCode == RESULT_CANCELED) {
            displayAuthCancelled();
        } else {
            Intent intent = new Intent(this, TokenActivity.class);
            intent.putExtras(data.getExtras());
            startActivity(intent);
            finish();
        }
    }

    @MainThread
    public static void startAuth() {

        // WrongThread inference is incorrect for lambdas
        // noinspection WrongThread
        mExecutor.submit(viewAllSubItemsListsActivity::doAuth);
    }

    /**
     * Initializes the authorization service configuration if necessary, either from the local
     * static values or by retrieving an OpenID discovery document.
     */
    @WorkerThread
    private void initializeAppAuth() {
        Log.i(TAG, "Initializing AppAuth");
        recreateAuthorizationService();

        if (mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration() != null) {
            // configuration is already created, skip to client initialization
            Log.i(TAG, "auth config already established");
            initializeClient();
            return;
        }

        // if we are not using discovery, build the authorization service configuration directly
        // from the static configuration values.
        if (mConfiguration.getDiscoveryUri() == null) {
            Log.i(TAG, "Creating auth config from res/raw/auth_config.json");
            AuthorizationServiceConfiguration config = new AuthorizationServiceConfiguration(
                    mConfiguration.getAuthEndpointUri(),
                    mConfiguration.getTokenEndpointUri(),
                    mConfiguration.getRegistrationEndpointUri());

            mAuthStateManager.replace(new AuthState(config));
            initializeClient();
            return;
        }

        // WrongThread inference is incorrect for lambdas
        // noinspection WrongThread
        runOnUiThread(() -> displayLoading("Retrieving discovery document"));
        Log.i(TAG, "Retrieving OpenID discovery doc");
        AuthorizationServiceConfiguration.fetchFromUrl(
                mConfiguration.getDiscoveryUri(),
                this::handleConfigurationRetrievalResult,
                mConfiguration.getConnectionBuilder());
    }

    @MainThread
    private void handleConfigurationRetrievalResult(
            AuthorizationServiceConfiguration config,
            AuthorizationException ex) {
        if (config == null) {
            Log.i(TAG, "Failed to retrieve discovery document", ex);
            displayError("Failed to retrieve discovery document: " + ex.getMessage(), true);
            return;
        }

        Log.i(TAG, "Discovery document retrieved");
        mAuthStateManager.replace(new AuthState(config));
        mExecutor.submit(this::initializeClient);
    }

    /**
     * Initiates a dynamic registration request if a client ID is not provided by the static
     * configuration.
     */
    @WorkerThread
    private void initializeClient() {
        if (mConfiguration.getClientId() != null) {
            Log.i(TAG, "Using static client ID: " + mConfiguration.getClientId());
            // use a statically configured client ID
            mClientId.set(mConfiguration.getClientId());
            runOnUiThread(this::initializeAuthRequest);
            return;
        }

        RegistrationResponse lastResponse =
                mAuthStateManager.getCurrent().getLastRegistrationResponse();
        if (lastResponse != null) {
            Log.i(TAG, "Using dynamic client ID: " + lastResponse.clientId);
            // already dynamically registered a client ID
            mClientId.set(lastResponse.clientId);
            runOnUiThread(this::initializeAuthRequest);
            return;
        }

        // WrongThread inference is incorrect for lambdas
        // noinspection WrongThread
        runOnUiThread(() -> displayLoading("Dynamically registering client"));
        Log.i(TAG, "Dynamically registering client");

        RegistrationRequest registrationRequest = new RegistrationRequest.Builder(
                mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration(),
                Collections.singletonList(mConfiguration.getRedirectUri()))
                .setTokenEndpointAuthenticationMethod(ClientSecretBasic.NAME)
                .build();

        mAuthService.performRegistrationRequest(
                registrationRequest,
                this::handleRegistrationResponse);
    }

    @MainThread
    private void handleRegistrationResponse(
            RegistrationResponse response,
            AuthorizationException ex) {
        mAuthStateManager.updateAfterRegistration(response, ex);
        if (response == null) {
            Log.i(TAG, "Failed to dynamically register client", ex);
            displayErrorLater("Failed to register client: " + ex.getMessage(), true);
            return;
        }

        Log.i(TAG, "Dynamically registered client: " + response.clientId);
        mClientId.set(response.clientId);
        initializeAuthRequest();
    }

    /**
     * Enumerates the browsers installed on the device and populates a spinner, allowing the
     * demo user to easily test the authorization flow against different browser and custom
     * tab configurations.
     */
    @MainThread
    private void configureBrowserSelector() {


        Spinner spinner = (Spinner) findViewById(R.id.browser_selector);
        final BrowserSelectionAdapter adapter = new BrowserSelectionAdapter(this);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BrowserSelectionAdapter.BrowserInfo info = adapter.getItem(position);
                if (info == null) {
                    mBrowserMatcher = AnyBrowserMatcher.INSTANCE;
                    return;
                } else {
                    mBrowserMatcher = new ExactBrowserMatcher(info.mDescriptor);
                }

                recreateAuthorizationService();
                createAuthRequest(getLoginHint());
                warmUpBrowser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBrowserMatcher = AnyBrowserMatcher.INSTANCE;
            }
        });
    }

    /**
     * Performs the authorization request, using the browser selected in the spinner,
     * and a user-provided `login_hint` if available.
     */
    @WorkerThread
    private void doAuth() {
        try {
            mAuthIntentLatch.await();
        } catch (InterruptedException ex) {
            Log.w(TAG, "Interrupted while waiting for auth intent");
        }

        if (mUsePendingIntents) {
            Intent completionIntent = new Intent(this, TokenActivity.class);
            Intent cancelIntent = new Intent(this, TokenActivity.class);
            cancelIntent.putExtra(EXTRA_FAILED, true);
            cancelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            mAuthService.performAuthorizationRequest(
                    mAuthRequest.get(),
                    PendingIntent.getActivity(this, 0, completionIntent, 0),
                    PendingIntent.getActivity(this, 0, cancelIntent, 0),
                    mAuthIntent.get());
        } else {
            Intent intent = mAuthService.getAuthorizationRequestIntent(
                    mAuthRequest.get(),
                    mAuthIntent.get());
            startActivityForResult(intent, RC_AUTH);
        }
    }

    private void recreateAuthorizationService() {
        if (mAuthService != null) {
            Log.i(TAG, "Discarding existing AuthService instance");
            mAuthService.dispose();
        }
        mAuthService = createAuthorizationService();
        mAuthRequest.set(null);
        mAuthIntent.set(null);
    }

    private AuthorizationService createAuthorizationService() {
        Log.i(TAG, "Creating authorization service");
        AppAuthConfiguration.Builder builder = new AppAuthConfiguration.Builder();
        builder.setBrowserMatcher(mBrowserMatcher);
        builder.setConnectionBuilder(mConfiguration.getConnectionBuilder());

        return new AuthorizationService(this, builder.build());
    }

    @MainThread
    private static void displayLoading(String loadingMessage) {

    }

    @MainThread
    private void displayError(String error, boolean recoverable) {

    }

    // WrongThread inference is incorrect in this case
    @SuppressWarnings("WrongThread")
    @AnyThread
    private void displayErrorLater(final String error, final boolean recoverable) {
        runOnUiThread(() -> displayError(error, recoverable));
    }

    @MainThread
    private void initializeAuthRequest() {
        createAuthRequest(getLoginHint());
        warmUpBrowser();
        displayAuthOptions();
    }

    @MainThread
    private void displayAuthOptions() {
        findViewById(R.id.auth_container).setVisibility(View.GONE);
        findViewById(R.id.loading_container).setVisibility(View.GONE);
        findViewById(R.id.error_container).setVisibility(View.GONE);

        AuthState state = mAuthStateManager.getCurrent();
        AuthorizationServiceConfiguration config = state.getAuthorizationServiceConfiguration();

        String authEndpointStr;
        if (config.discoveryDoc != null) {
            authEndpointStr = "Discovered auth endpoint: \n";
        } else {
            authEndpointStr = "Static auth endpoint: \n";
        }
        authEndpointStr += config.authorizationEndpoint;
//        ((TextView)findViewById(R.id.auth_endpoint)).setText(authEndpointStr);

        String clientIdStr;
        if (state.getLastRegistrationResponse() != null) {
            clientIdStr = "Dynamic client ID: \n";
        } else {
            clientIdStr = "Static client ID: \n";
        }
        clientIdStr += mClientId;
        ((TextView) findViewById(R.id.client_id)).setText(clientIdStr);
    }

    private void displayAuthCancelled() {
        try {
            Snackbar.make(findViewById(R.id.coordinator),
                    "Authorization canceled",
                    Snackbar.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void warmUpBrowser() {
        mAuthIntentLatch = new CountDownLatch(1);
        mExecutor.execute(() -> {
            Log.i(TAG, "Warming up browser instance for auth request");
            CustomTabsIntent.Builder intentBuilder =
                    mAuthService.createCustomTabsIntentBuilder(mAuthRequest.get().toUri());
            intentBuilder.setToolbarColor(getColorCompat(R.color.colorPrimary));
            mAuthIntent.set(intentBuilder.build());
            mAuthIntentLatch.countDown();
        });
    }

    private void createAuthRequest(@Nullable String loginHint) {
        Log.i(TAG, "Creating auth request for login hint: " + loginHint);
        AuthorizationRequest.Builder authRequestBuilder = new AuthorizationRequest.Builder(
                mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration(),
                mClientId.get(),
                ResponseTypeValues.CODE,
                mConfiguration.getRedirectUri())
                .setPrompt("login")
                .setScope(mConfiguration.getScope());

        if (!TextUtils.isEmpty(loginHint)) {
            authRequestBuilder.setLoginHint(loginHint);
        }

        mAuthRequest.set(authRequestBuilder.build());
    }

    private String getLoginHint() {
        return ((EditText) findViewById(R.id.login_hint_value))
                .getText()
                .toString()
                .trim();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressWarnings("deprecation")
    private int getColorCompat(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getColor(color);
        } else {
            return getResources().getColor(color);
        }
    }


    /**
     GET Cart Counts in Cart Icon which placed in Toolbar right (GET)
     **/
    public static void getCartListsinViewAllPage() {
        String url = APIBaseURL.getCartsList + SaveSharedPreference.getLoggedInUserEmail(viewAllSubItemsListsActivity);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataArray = jsonObject.getJSONObject("data");
                    SaveSharedPreference.saveCartID(viewAllSubItemsListsActivity, dataArray.optString("id"));
                    JSONArray cartDetailsArray = new JSONArray();
                    JSONArray menuDetailsArray = new JSONArray();
                    if (dataArray.has("cartDetails")) {
                        cartDetailsArray = dataArray.getJSONArray("cartDetails");
                    }
                    List<Chef> chefList = new ArrayList<>();
                    for (int i = 0; i < cartDetailsArray.length(); i++) {
                        JSONObject menuObject = cartDetailsArray.getJSONObject(i);
                        Chef chef = new Chef();
                        chef.setId(menuObject.optString("chefId"));
                        chef.setName(menuObject.optString("chefName"));

                        chefList.add(chef);

                        SaveSharedPreference.setList(chefList, viewAllSubItemsListsActivity);
                    }


                    if (jsonObject.optInt("count") == 0) {

                    } else {

                    }
                    double sum = 0;
                    for (int i = 0; i < cartDetailsArray.length(); i++) {
                        JSONObject dataObject = cartDetailsArray.getJSONObject(i);
                        JSONObject footerTotalObject = new JSONObject();
                        if (dataObject.has("footer")) {
                            footerTotalObject = dataObject.getJSONObject("footer");
                        }

                        if (dataObject.has("menuDetails")) {
                            menuDetailsArray = dataObject.getJSONArray("menuDetails");
                        }
                        // Double subTotal = dataObject.optDouble("total");
                        sum = footerTotalObject.optDouble("total");
                    }
                    try {
                        cartTotalCountText.setText(String.valueOf(jsonObject.optString("count")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cartTotalCountText.setText(String.valueOf(0));
                SaveSharedPreference.saveCartID(viewAllSubItemsListsActivity,"");

                try {
                    List<Chef> chefList = new ArrayList<>();
                    SaveSharedPreference.setList(chefList,viewAllSubItemsListsActivity);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        },viewAllSubItemsListsActivity);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "cart_list_taq");
    }

}
