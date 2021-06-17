package stayabode.foodyHive.activities.consumers;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.ConsumerTabAdapter;
import stayabode.foodyHive.authentication.AuthStateManager;
import stayabode.foodyHive.authentication.BrowserSelectionAdapter;
import stayabode.foodyHive.authentication.Configuration;
import stayabode.foodyHive.authentication.TokenActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.fragments.consumers.ConsumerChefdetailFragments;
import stayabode.foodyHive.models.AboutInfoChef;
import stayabode.foodyHive.models.Category;
import stayabode.foodyHive.models.ChefDetailHeader;
import stayabode.foodyHive.models.Reviews;
import stayabode.foodyHive.models.ReviewsHeader;
import stayabode.foodyHive.utils.ApplicationController;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class CookedChefItemDetailActivity extends AppCompatActivity {


    public Boolean enableScroll = true;

    public  static int selectedposition = -1;

    TextView chefName;
    TextView foodType;
    TextView availableText;
    TextView minsText;
    TextView ratingCount;
    TextView reviewText;

    Typeface poppinsMedium;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface poppinsLight;
    Typeface robotoMedium;
    Typeface RobotoRegular;
    Typeface RobotoBold;
    ImageView iv_class_image;
    LinearLayout rootLayout;
    ProgressBar progressBar;
    TabLayout tabLayout;
    ViewPager viewPager;

    AboutInfoChef aboutInfoChef = new AboutInfoChef();
    ReviewsHeader reviewsheader = new ReviewsHeader();

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

    @NonNull
    private BrowserMatcher mBrowserMatcher = AnyBrowserMatcher.INSTANCE;

    public static CookedChefItemDetailActivity cookedChefItemDetailActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExecutor = Executors.newSingleThreadExecutor();
        mAuthStateManager = AuthStateManager.getInstance(this);
        mConfiguration = Configuration.getInstance(this);
        setContentView(R.layout.activity_cooked_chef_detail_page);

        cookedChefItemDetailActivity = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        robotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        RobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        RobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_for_consmer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        chefName = findViewById(R.id.chefName);
        foodType = findViewById(R.id.foodType);
        availableText = findViewById(R.id.availableText);
        minsText = findViewById(R.id.minsText);
        iv_class_image = findViewById(R.id.iv_class_image);
        ratingCount = findViewById(R.id.ratingCount);
        reviewText = findViewById(R.id.reviewText);
        rootLayout = findViewById(R.id.rootLayout);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);

        chefName.setTypeface(poppinsSemiBold);
        foodType.setTypeface(poppinsLight);
        ratingCount.setTypeface(poppinsLight);
        availableText.setTypeface(poppinsLight);
        minsText.setTypeface(poppinsLight);
        reviewText.setTypeface(poppinsMedium);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add("About");
        arrayList.add("Reviews");
        arrayList.add("Menu");
        arrayList.add("Popular");
        prepareViewager(viewPager,arrayList);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);


        getChefDetailPage();


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
    }

    //adding the tabs for viewpager
    private void prepareViewager(ViewPager viewPager, ArrayList<String> arrayList) {
        ConsumerTabAdapter consumerTabAdapter=new ConsumerTabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ConsumerChefdetailFragments consumerChefdetailFragments =new ConsumerChefdetailFragments();
        for(int i=0;i<arrayList.size();i++){
            Bundle bundle=new Bundle();
            bundle.putString("title",arrayList.get(i));
            consumerChefdetailFragments.setArguments(bundle);
            consumerTabAdapter.createFragment(consumerChefdetailFragments,arrayList.get(i));
            consumerChefdetailFragments=new ConsumerChefdetailFragments();
        }
        viewPager.setAdapter(consumerTabAdapter);
    }

    // To Back Press from the Top Toolbar back Arrow
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


    /**
     To GET the Chefs Detail make an GET  Call API(GET)
     **/
    public void getChefDetailPage()
    {
        String url = APIBaseURL.getCookedChefProfile+""+getIntent().getStringExtra("chefId")+"&1&1";
        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                List<Category> categoryList = new ArrayList<>();

                Category localCategory = new Category();
                localCategory.setName("Vegetarian");
                localCategory.setIdforDish(1);

                Category localCategory1 = new Category();
                localCategory1.setName("Non-vegetarian");
                localCategory1.setIdforDish(2);

                Category localCategory2 = new Category();
                localCategory2.setName("Eggetarian");
                localCategory2.setIdforDish(3);

                Category localCategory3 = new Category();
                localCategory3.setName("Vegan");
                localCategory3.setIdforDish(4);

                categoryList.add(localCategory);
                categoryList.add(localCategory1);
                categoryList.add(localCategory2);
                categoryList.add(localCategory3);


                List<ChefDetailHeader> chefDetailHeaderList = new ArrayList<>();

                ChefDetailHeader chefDetailHeader = new ChefDetailHeader();
                chefDetailHeader.setTitle("About");

                ChefDetailHeader chefDetailHeader1 = new ChefDetailHeader();
                chefDetailHeader1.setTitle("Reviews");


                ChefDetailHeader chefDetailHeader2 = new ChefDetailHeader();
                chefDetailHeader2.setTitle("Menu");

                ChefDetailHeader chefDetailHeader3 = new ChefDetailHeader();
                chefDetailHeader3.setTitle("Popular");


                chefDetailHeaderList.add(chefDetailHeader);
                chefDetailHeaderList.add(chefDetailHeader1);
                chefDetailHeaderList.add(chefDetailHeader2);
                chefDetailHeaderList.add(chefDetailHeader3);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray consumerReviewsArray = new JSONArray();
                    chefName.setText(dataObject.optString("chefName"));
                    reviewText.setText(dataObject.optString("chefRatingStatus"));
                    ratingCount.setText(dataObject.optString("chefAverageRating") +"("+dataObject.optString("chefRatingsCount")+")");
                    minsText.setText(dataObject.optString("chefAveragePreparationTime") + " - " + dataObject.optString("chefAverageDeliveryTime"));
                    availableText.setText("Recipes("+dataObject.optString("chefTotalRecepies")+")");

                    try {
                        Glide.with(CookedChefItemDetailActivity.this).load(dataObject.optString("chefImage")).placeholder(R.drawable.foodi_logo_left_image).into(iv_class_image);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    if(dataObject.has("consumerReviews"))
                    {
                        consumerReviewsArray = dataObject.getJSONArray("consumerReviews");
                    }

                    JSONArray typeofDishesArray = new JSONArray();
                    if(dataObject.has("typeofDishes"))
                    {
                        typeofDishesArray = dataObject.getJSONArray("typeofDishes");
                    }

                    String finalResultNamesForDishTypes = "";




                    if(typeofDishesArray.length()  == 0)
                    {
                        foodType.setText("");
                    }
                    else
                    {
                        ArrayList<String> stringList = new ArrayList<>();
                        String results = "";
                        for (int i=0;i < typeofDishesArray.length();i++) {

                            Object getValues = typeofDishesArray.get(i);



                            for (int j=0;j < categoryList.size();j++)
                            {

                                if(getValues.equals(categoryList.get(j).getIdforDish()))
                                {
                                    finalResultNamesForDishTypes = categoryList.get(j).getName();
                                    stringList.add(finalResultNamesForDishTypes);

                                    StringBuilder builder = new StringBuilder();
                                    for(String s : stringList) {
                                        builder.append(s);
                                    }
                                    results = builder.toString();

                                }
                            }

                        }
                        foodType.setText(String.join("/", stringList));
                    }



                    aboutInfoChef.setDescription(dataObject.optString("aboutYou"));
                    aboutInfoChef.setTitle("About "+dataObject.optString("chefName"));



                    List<Reviews> reviewsList = new ArrayList<>();
                    for (int i=0;i < consumerReviewsArray.length();i++)
                    {
                        JSONObject consumerReviewsObject = consumerReviewsArray.getJSONObject(i);
                        Reviews reviews = new Reviews();
                        reviews.setDate(consumerReviewsObject.optString("createdDate"));
                        reviews.setUserName(consumerReviewsObject.optString("consumerName"));
                        reviews.setImage(consumerReviewsObject.optString("consumerImage"));
                        reviews.setReviewsDescription(consumerReviewsObject.optString("consumerComments"));
                        reviews.setRatingCount(consumerReviewsObject.optString("consumerRating"));
                        reviewsList.add(reviews);
                    }
                    reviewsheader.setReviewsList(reviewsList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                rootLayout.setVisibility(View.GONE);
                Toast.makeText(CookedChefItemDetailActivity.this, "Oops something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"chef_detail_taq");
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
        mExecutor.submit(cookedChefItemDetailActivity::doAuth);
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


        String clientIdStr;
        if (state.getLastRegistrationResponse() != null) {
            clientIdStr = "Dynamic client ID: \n";
        } else {
            clientIdStr = "Static client ID: \n";
        }
        clientIdStr += mClientId;
        ((TextView)findViewById(R.id.client_id)).setText(clientIdStr);
    }

    private void displayAuthCancelled() {
        try {
            Snackbar.make(findViewById(R.id.coordinator),
                    "Authorization canceled",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
        catch (Exception e)
        {
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
        return ((EditText)findViewById(R.id.login_hint_value))
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
}
