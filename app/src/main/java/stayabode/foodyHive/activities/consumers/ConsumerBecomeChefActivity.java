package stayabode.foodyHive.activities.consumers;

import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ConsumerBecomeChefActivity extends AppCompatActivity {

    EditText nameText;
    EditText phoneNumber;
    EditText emailText;
    EditText descriptionText;

    Button joinWithUsButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_consumer_become_a_chef);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Become a Chef");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Become a Chef");

        nameText = findViewById(R.id.nameText);
        phoneNumber = findViewById(R.id.phoneNumber);
        emailText = findViewById(R.id.emailText);
        descriptionText = findViewById(R.id.descriptionText);

        nameText=findViewById(R.id.nameText);
        phoneNumber=findViewById(R.id.phoneNumber);
        emailText=findViewById(R.id.emailText);
        descriptionText=findViewById(R.id.descriptionText);
        joinWithUsButton = findViewById(R.id.joinWithUsButton);

        joinWithUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    addJoinChef();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     To Validate ALl Fields are Entered or Matches
     **/

    private boolean validate(String name, String phone, String email, String description) {
        if (name.equals(null) || name.equals("") || !isValidMobile(phone) || !isValidMail(email) || description.equals(null) || description.equals("")) {

            if (name.equals(null) || name.equals("")) {
                nameText.setError("Enter a valid Name");
            } else {
                nameText.setError(null);
            }
            if (!isValidMobile(phone)) {
                phoneNumber.setError("Enter a valid Mobile Number");
            } else {
                phoneNumber.setError(null);
            }
            if (!isValidMail(email)) {
                emailText.setError("Enter a valid Email ID");
            } else {
                emailText.setError(null);
            }
            if (description.equals(null) || description.equals("")) {
                descriptionText.setError("Add few lines about you");
            } else {
                descriptionText.setError(null);
            }
            return false;
        } else
            return true;
    }


    /**
     To Validate Email Fields
     **/
    private boolean isValidMail(String email) {
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(EMAIL_STRING).matcher(email).matches();
    }


    /**
     To Validate Mobile Fields
     **/
    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }

    /**
     Make the POST API call to Became an Chef
     **/
    private void addJoinChef() throws JSONException {
        String url = APIBaseURL.addJoinChefs;
        String name = nameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        String description = descriptionText.getText().toString().trim();
        if (validate(name, phone, email, description)) {
            JSONObject inputObject = new JSONObject();

            inputObject.put("name", name);
            inputObject.put("email", email);
            inputObject.put("phoneNo", phone);
            inputObject.put("message", description);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(ConsumerBecomeChefActivity.this, "Your Details Submitted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof AuthFailureError) {
                        //TODO
                        ViewGroup viewGroup = ConsumerBecomeChefActivity.this.findViewById(android.R.id.content);

                        //then we will inflate the custom alert dialog xml that we created
                        View dialogView = LayoutInflater.from(ConsumerBecomeChefActivity.this).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                        Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                        ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                        //Now we need an AlertDialog.Builder object
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerBecomeChefActivity.this);

                        //setting the view of the builder to our custom view that we already inflated
                        builder.setView(dialogView);

                        //finally creating the alert dialog and displaying it
                        AlertDialog alertDialog = builder.create();


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
                        Toast.makeText(ConsumerBecomeChefActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(ConsumerBecomeChefActivity.this));
                    return headers;
                }
            };
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "join_chef_tag");
        }
    }


    /**
     Toolbar Back Pres Overridden Method
     **/
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
