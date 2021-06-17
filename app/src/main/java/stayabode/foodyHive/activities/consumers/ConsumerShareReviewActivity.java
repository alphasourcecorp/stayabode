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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsumerShareReviewActivity extends AppCompatActivity {

    Typeface poppinsMedium;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface poppinsLight;
    Typeface RobotoBold;

    TextView foodName;
   // TextView publicNameText;
    EditText name;
    //TextView commentsText;

    EditText comments;
    Button submitButton;
    RatingBar ratingBar;
    ImageView foodImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_share_review);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Share your Review");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        RobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");


        foodName=findViewById(R.id.foodName);
        ratingBar=findViewById(R.id.ratingBar);
     //   publicNameText=findViewById(R.id.publicNameText);
        name=findViewById(R.id.name);
        //commentsText=findViewById(R.id.commentsText);
        comments=findViewById(R.id.comments);
        submitButton=findViewById(R.id.submitButton);
        foodImage=findViewById(R.id.foodImage);

        foodName.setTypeface(poppinsSemiBold);
       // publicNameText.setTypeface(poppinsLight);
        name.setTypeface(poppinsLight);
       // commentsText.setTypeface(poppinsLight);
        comments.setTypeface(poppinsLight);

        submitButton.setTypeface(poppinsBold);
        name.setText(ConsumerMainActivity.userName);
        foodName.setText(getIntent().getStringExtra("ItemName"));
        Glide.with(this).load(getIntent().getStringExtra("ItemImage")).placeholder(R.drawable.foodi_logo_left_image).into(foodImage);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateRating();
            }
        });

    }


    /**
     validate the text fields and rating bar
     **/
    private void ValidateRating() {
        if(name.getText().toString().trim().equals("")){
            Toast.makeText(ConsumerShareReviewActivity.this,"Name cannot be empty!",Toast.LENGTH_SHORT).show();
        }else if( comments.getText().toString().trim().equals("") ){
            Toast.makeText(ConsumerShareReviewActivity.this,"Comments cannot be empty!",Toast.LENGTH_SHORT).show();
        }else if(ratingBar.getRating()==0.0f){
            Toast.makeText(ConsumerShareReviewActivity.this,"Rating cannot be zero!",Toast.LENGTH_SHORT).show();
        }else {
            try {
                addRatings();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
     To Add the Ratings make an POST Call API(POST)
     **/
    public void addRatings() throws JSONException {
        String url = APIBaseURL.addRating;

        JSONObject inputObject = new JSONObject();
        inputObject.put("consumerComments",comments.getText().toString());
        inputObject.put("consumerImage", SaveSharedPreference.getUserFilePath(ConsumerShareReviewActivity.this));
        inputObject.put("chefId",getIntent().getStringExtra("ChefId"));
        inputObject.put("consumerName",name.getText().toString());
        inputObject.put("consumerRating",ratingBar.getRating());
        inputObject.put("dishId",getIntent().getStringExtra("ItemId"));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerShareReviewActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.consumer_checkout_dialogue, null);
                TextView header = dialogLayout.findViewById(R.id.header);
                TextView subHeader = dialogLayout.findViewById(R.id.subHeader);
                Button trackOrderButton = dialogLayout.findViewById(R.id.trackOrderButton);
                Button exploreFoodButton = dialogLayout.findViewById(R.id.exploreFoodButton);
                header.setText("Your review is submitted successfully Thank you.");
                header.setTypeface(RobotoBold);
                subHeader.setVisibility(View.GONE);
                trackOrderButton.setVisibility(View.GONE);
                exploreFoodButton.setTypeface(poppinsBold);
                builder.setView(dialogLayout);
                AlertDialog alertDialog = builder.create();
                exploreFoodButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        finish();
                    }
                });
                alertDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ConsumerShareReviewActivity.this.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(ConsumerShareReviewActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConsumerShareReviewActivity.this);

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
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(ConsumerShareReviewActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(ConsumerShareReviewActivity.this));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest,"add_rating_taq");
    }
}
