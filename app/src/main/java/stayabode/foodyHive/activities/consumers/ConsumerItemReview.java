package stayabode.foodyHive.activities.consumers;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.ConsumerHomeAdapters;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Reviews;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumerItemReview extends AppCompatActivity {

    Typeface poppinsBold;
    Typeface poppinsMedium;
    Typeface poppinsLight;

    TextView header;
    TextView subHeader;
    TextView foodQualityText;
    TextView ValueForMoneyText;
    TextView orderPackingText;
    TextView deliveryTimeText;

    NestedScrollView nestedScroll;


    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();
    LinearLayout rootLayout;
    ProgressBar progressBar;
    ProgressBar itemsLoader;
    private int index = 0;
    private int size = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_item_review);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        recyclerView = findViewById(R.id.recyclerView);

        header = findViewById(R.id.header);
        subHeader = findViewById(R.id.subHeader);
        foodQualityText = findViewById(R.id.foodQualityText);
        ValueForMoneyText = findViewById(R.id.ValueForMoneyText);
        orderPackingText = findViewById(R.id.orderPackingText);
        deliveryTimeText = findViewById(R.id.deliveryTimeText);
        nestedScroll = findViewById(R.id.nestedScroll);
        itemsLoader = findViewById(R.id.itemsLoader);

        header.setTypeface(poppinsBold);
        subHeader.setTypeface(poppinsLight);
        foodQualityText.setTypeface(poppinsMedium);
        ValueForMoneyText.setTypeface(poppinsMedium);
        orderPackingText.setTypeface(poppinsMedium);
        deliveryTimeText.setTypeface(poppinsMedium);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rootLayout = findViewById(R.id.rootLayout);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
        itemsLoader.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        header.setText(getIntent().getStringExtra("itemName") + ", Rating & Reviews");

        subHeader.setText("Based on "+getIntent().getStringExtra("ratingsCount")+" Reviews");

        getChefReviews();
        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    index++;
                    getChefReviews();
                }
            }
        });
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
     To Get the Chefs Reviews From API(GET)
     **/
    public void getChefReviews() {
        progressBar.setVisibility(View.VISIBLE);
        String url = APIBaseURL.getReviewsAndRatings + "" + getIntent().getStringExtra("chefId") + "&" + getIntent().getStringExtra("dishId") + "&" + index + "&" + size;
        if (index > 0) {
            itemsLoader.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                rootLayout.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray consumerReviewsArray = new JSONArray();

                    if (dataObject.has("consumerReviews")) {
                        consumerReviewsArray = dataObject.getJSONArray("consumerReviews");
                    }

                    if (consumerReviewsArray.length() > 0) {
                        for (int i = 0; i < consumerReviewsArray.length(); i++) {
                            JSONObject consumerReviewsObject = consumerReviewsArray.getJSONObject(i);
                            Reviews reviews = new Reviews();
                            reviews.setDate(consumerReviewsObject.optString("createdDate"));
                            reviews.setUserName(consumerReviewsObject.optString("consumerName"));
                            reviews.setReviewsDescription(consumerReviewsObject.optString("consumerComments"));
                            reviews.setImage(consumerReviewsObject.optString("consumerImage"));
                            reviews.setRatingCount(consumerReviewsObject.optString("consumerRating"));
                            objectList.add(reviews);
                        }
                        recyclerView.setAdapter(new ConsumerHomeAdapters(ConsumerItemReview.this, objectList, null, null, null, poppinsBold, poppinsLight, poppinsMedium,1));
                    } else {
                        subHeader.setVisibility(View.GONE);
                        itemsLoader.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                rootLayout.setVisibility(View.GONE);
                itemsLoader.setVisibility(View.GONE);
                subHeader.setVisibility(View.GONE);
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ConsumerItemReview.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerItemReview.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConsumerItemReview.this);

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ConsumerMainActivity.logout();

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(ConsumerItemReview.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(ConsumerItemReview.this, "Oops something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, ConsumerItemReview.this);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_review_taq");
    }

}
