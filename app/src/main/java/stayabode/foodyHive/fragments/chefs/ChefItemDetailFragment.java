package stayabode.foodyHive.fragments.chefs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import stayabode.foodyHive.BuildConfig;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.ReviewsAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Reviews;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.FileDownloader;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class ChefItemDetailFragment extends Fragment {

    ImageView itemImage;
    ImageView favouriteImg;
    TextView addfavouriteText;
    TextView itemName;
    TextView ratingCount;
    TextView reviewsCount;
    TextView time;
    TextView priceText;
    TextView caloriesgramText;
    TextView descriptionHeader;
    TextView descriptionValue;
    TextView receipeHeader;
    TextView downloadText;
    TextView nutritionHeader;
    TextView mealcontainsText;
    TextView kcalsText;
    TextView proteinHeader;
    TextView fatHeader;
    TextView carbsHeader;
    TextView fibreHeader;
    TextView protienValue;
    TextView fatValue;
    TextView carbsValue;
    TextView fibreValue;
    TextView discountPercent;
    Switch availableSwitch;
    Typeface fontNunitoLightFont;
    Typeface fontNunitoBold;
    Typeface fontNunitoRegular;
    Typeface raleWayFontBold;
    Typeface robotoFontRegular;
    Typeface ralewayFontRegular;
    Typeface ralewayFontRegularLight;

    ImageView reviewIcon;
    TextView reviewTitle;
    Button buttonDone;
    EditText commandsEditText;
    RatingBar ratingBarItem;
    RatingBar ratingBar;
    Button editItem;
    Button deleteItem;

    RecyclerView recyclerView;

    List<Object> objectList = new ArrayList<>();

    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    ProgressBar progressBar;
    NestedScrollView scrollrootView;

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu_item_detail,container,false);
        fontNunitoLightFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Light.ttf");
        fontNunitoBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontNunitoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");
        robotoFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        raleWayFontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");
        ralewayFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
        ralewayFontRegularLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Light.ttf");
        ChefsMainActivity.searchLayout.setVisibility(View.GONE);
        ChefsMainActivity.chefnavigation.setVisibility(View.GONE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.GONE);
        cheftoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        itemImage = rootView.findViewById(R.id.itemImage);

        itemName = rootView.findViewById(R.id.itemName);
        availableSwitch = rootView.findViewById(R.id.availableSwitch);
        ratingCount = rootView.findViewById(R.id.ratingCount);
        reviewsCount = rootView.findViewById(R.id.reviewsCount);
        time = rootView.findViewById(R.id.time);
        priceText = rootView.findViewById(R.id.priceText);
        caloriesgramText = rootView.findViewById(R.id.caloriesgramText);
        descriptionHeader = rootView.findViewById(R.id.descriptionHeader);
        descriptionValue = rootView.findViewById(R.id.descriptionValue);
        receipeHeader = rootView.findViewById(R.id.receipeHeader);
        downloadText = rootView.findViewById(R.id.downloadText);
        nutritionHeader = rootView.findViewById(R.id.nutritionHeader);
        mealcontainsText = rootView.findViewById(R.id.mealcontainsText);
        kcalsText = rootView.findViewById(R.id.kcalsText);
        proteinHeader = rootView.findViewById(R.id.proteinHeader);
        fatHeader = rootView.findViewById(R.id.fatHeader);
        carbsHeader = rootView.findViewById(R.id.carbsHeader);
        fibreHeader = rootView.findViewById(R.id.fibreHeader);
        protienValue = rootView.findViewById(R.id.protienValue);
        fatValue = rootView.findViewById(R.id.fatValue);
        carbsValue = rootView.findViewById(R.id.carbsValue);
        fibreValue = rootView.findViewById(R.id.fibreValue);
        reviewIcon= rootView.findViewById(R.id.reviewIcon);
        editItem = rootView.findViewById(R.id.editItem);
        deleteItem= rootView.findViewById(R.id.deleteItem);
        progressBar = rootView.findViewById(R.id.progressBar);
        scrollrootView = rootView.findViewById(R.id.scrollrootView);
        ratingBarItem = rootView.findViewById(R.id.ratingBarItem);
        discountPercent = rootView.findViewById(R.id.discountPercent);


        progressBar.setVisibility(View.VISIBLE);
        scrollrootView.setVisibility(View.GONE);

        itemName.setTypeface(raleWayFontBold);
        descriptionHeader.setTypeface(raleWayFontBold);
        receipeHeader.setTypeface(raleWayFontBold);
        nutritionHeader.setTypeface(raleWayFontBold);
        availableSwitch.setTypeface(robotoFontRegular);
        ratingCount.setTypeface(ralewayFontRegular);
        reviewsCount.setTypeface(ralewayFontRegular);
        time.setTypeface(ralewayFontRegular);
        priceText.setTypeface(raleWayFontBold);
        caloriesgramText.setTypeface(ralewayFontRegularLight);
        descriptionValue.setTypeface(ralewayFontRegular);
        downloadText.setTypeface(ralewayFontRegular);
        mealcontainsText.setTypeface(fontNunitoBold);
        kcalsText.setTypeface(fontNunitoBold);
        proteinHeader.setTypeface(fontNunitoRegular);
        fatHeader.setTypeface(fontNunitoRegular);
        carbsHeader.setTypeface(fontNunitoRegular);
        fibreHeader.setTypeface(fontNunitoRegular);
        protienValue.setTypeface(fontNunitoRegular);
        fatValue.setTypeface(fontNunitoRegular);
        carbsValue.setTypeface(fontNunitoRegular);
        fibreValue.setTypeface(fontNunitoRegular);
        editItem.setTypeface(robotoFontRegular);
        deleteItem.setTypeface(robotoFontRegular);

        reviewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(R.layout.fragment_review);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                reviewTitle = bottomSheetDialog.findViewById(R.id.reviewTitle);
                buttonDone = bottomSheetDialog.findViewById(R.id.buttonDone);
                commandsEditText = bottomSheetDialog.findViewById(R.id.dishName);
                ratingBar = bottomSheetDialog.findViewById(R.id.ratingBar);
                recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);

                reviewTitle.setTypeface(raleWayFontBold);
                buttonDone.setTypeface(robotoFontRegular);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                getChefsRaingsandReviews();
                bottomSheetDialog.show();

                buttonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            addRatings();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });
        request();

        getMenuDetailPage();


        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        getContext());

// Setting Dialog Title
                alertDialog2.setTitle("Confirmation");

// Setting Dialog Message
                alertDialog2.setMessage("Are you sure you want delete this Item?");



// Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                deleteItem();
                            }
                        });
// Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog

                                dialog.cancel();
                            }
                        });

// Showing Alert Dialog
                alertDialog2.show();

            }
        });

        editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddChefMenuFragment fragment = new AddChefMenuFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ID", getArguments().getString("ID"));
                bundle.putString("ChefId", getArguments().getString("ChefId"));
                bundle.putString("From", "Edit");
                Log.v("dishId",getArguments().getString("ID"));
                fragment.setArguments(bundle);
                FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                ft.commit();
            }
        });

        availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Log.d("Status", "True");
                    try {
                        setActiveorDeactiveStatus(getArguments().getString("ChefId"),getArguments().getString("ID"), true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Status", "False");
                    try {
                        setActiveorDeactiveStatus(getArguments().getString("ChefId"), getArguments().getString("ID"), false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return rootView;
    }

    public void onBackPressed()
    {
        FragmentManager fm = cheffragmentManager;
        fm.popBackStack();
    }


    private void setActiveorDeactiveStatus(String chefID, String dishId, boolean status) throws JSONException {
        String url = APIBaseURL.activeOrDeactiveStatus;

        JSONObject inputObject = new JSONObject();
        inputObject.put("chefId", chefID);
        inputObject.put("dishId", dishId);
        inputObject.put("forAllDishes", false);
        inputObject.put("status", status);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), response.optString("errorMessage"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()));

                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    /**
     To Get an Single dish Item Detail by dish Item by using this API (GET)
     **/
    public void getMenuDetailPage()
    {
        progressBar.setVisibility(View.VISIBLE);
        scrollrootView.setVisibility(View.GONE);

        String url = APIBaseURL.getDishById+getArguments().getString("ID");

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                scrollrootView.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    try {
                        Glide.with(getContext()).load(dataObject.getJSONArray("dishImagePath").optString(0)).placeholder(R.drawable.foodi_logo_left_image).into(itemImage);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    itemName.setText(dataObject.optString("name"));
                    priceText.setText("₹"+String.format("%.2f",Double.valueOf(dataObject.optString("discountedPrice"))));
                    if(dataObject.optBoolean("isActive"))
                    {
                        availableSwitch.setChecked(true);
                    }
                    else
                    {
                        availableSwitch.setChecked(false);
                    }

                    time.setText(dataObject.optString("preparationTime") + " mins");
                    ratingCount.setText(dataObject.optString("ratingsCount"));
                    caloriesgramText.setText(dataObject.optString("weight")+"g");
                    descriptionValue.setText(dataObject.optString("description"));
                    try {
                        ratingBarItem.setRating(Float.parseFloat(dataObject.optString("ratingAverage")));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    JSONObject nutritionObject = new JSONObject();
                    if (dataObject.has("nutritionPercentage")) {
                        nutritionObject = dataObject.getJSONObject("nutritionPercentage");
                    }

                    if (dataObject.has("nutrition")) {
                        nutritionObject = dataObject.getJSONObject("nutrition");
                    }
                    mealcontainsText.setText("This meal ("+dataObject.optString("weight")+"g) contains");
                    protienValue.setText(nutritionObject.optString("protein")+"%");
                    fatValue.setText(nutritionObject.optString("fat")+"%");
                    carbsValue.setText(nutritionObject.optString("carbohydrates")+"%");
                    fibreValue.setText(nutritionObject.optString("fibre")+"%");
                    kcalsText.setText(nutritionObject.optString("energyCalories") + " Kcals");
                    try {
                        if(Float.parseFloat(dataObject.optString("discountPercent"))>0){
                            discountPercent.setVisibility(View.VISIBLE);
                            discountPercent.setText(dataObject.optString("discountPercent")+"%\nOFF");
                        }else{
                            discountPercent.setVisibility(View.GONE);
                        }
                    }
                    catch (Exception e)
                    {
                        discountPercent.setVisibility(View.GONE);
                        e.printStackTrace();
                    }


                    downloadText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                download(dataObject.getJSONArray("recipeFilePath").optString(0));
                            } catch (JSONException e) {
                                Log.d("Exception",e.getMessage() + " Exception Thrown");
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                scrollrootView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No Records found", Toast.LENGTH_SHORT).show();
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

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
                    Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"menu_detail_taq");
    }
    /**
    Request Permission for download the PDF File from Url
     **/
    public void request() {

        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, 112);

    }
    /**
     Request Permission for download the PDF File from Url
     **/
    public void view(View view) {
        Log.v(TAG, "view() Method invoked ");

        if (!hasPermissions(getActivity(), PERMISSIONS)) {

            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");

            Toast t = Toast.makeText(getContext(), "You don't have read access !", Toast.LENGTH_LONG);
            t.show();

        } else {
            File d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);  // -> filename = maven.pdf
            File pdfFile = new File(d, "maven.pdf");

            Log.v(TAG, "view() Method pdfFile " + pdfFile.getAbsolutePath());

            Uri path = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".fileprovider", pdfFile);


            Log.v(TAG, "view() Method path " + path);

            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(pdfIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }
        }
        Log.v(TAG, "view() Method completed ");

    }

    /**
     To download the PDF File from Url
     **/
    public void download(String url) {
        Log.v(TAG, "download() Method invoked ");

        if (!hasPermissions(getContext(), PERMISSIONS)) {

            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");

            Toast t = Toast.makeText(getContext(), "You don't have write access !", Toast.LENGTH_LONG);
            t.show();

        } else {
            Log.v(TAG, "download() Method HAVE PERMISSIONS ");

            //new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
            new DownloadFile().execute(url, "receipe_file.pdf");

        }

        Log.v(TAG, "download() Method completed ");

    }

    /**
     Download pdf file from URL
     **/

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            Log.v("TAG", "doInBackground() Method invoked ");

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File pdfFile = new File(folder, fileName);
            Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsolutePath());
            Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsoluteFile());

            try {
                pdfFile.createNewFile();
                Log.v(TAG, "doInBackground() file created" + pdfFile);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground() error" + e.getMessage());
                Log.e(TAG, "doInBackground() error" + e.getStackTrace());


            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            Log.v(TAG, "doInBackground() file download completed");

            return null;
        }
    }


    /**
     Get the Chefs Ratings and Reviews from this API(GET)
     **/
    public void getChefsRaingsandReviews()
    {

        String url = APIBaseURL.getReviewsAndRatings+""+getArguments().getString("ChefId")+"&"+getArguments().getString("ID")+"&0&20";

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray consumerReviewsArray = new JSONArray();


                    if(dataObject.has("consumerReviews"))
                    {
                        consumerReviewsArray = dataObject.getJSONArray("consumerReviews");
                    }

                    List<Object> reviewsList = new ArrayList<>();
                    for (int i=0;i < consumerReviewsArray.length();i++)
                    {
                        JSONObject consumerReviewsObject = consumerReviewsArray.getJSONObject(i);
                        Reviews reviews = new Reviews();
                        reviews.setDate(consumerReviewsObject.optString("createdDate"));
                        reviews.setUserName(consumerReviewsObject.optString("consumerName"));
                        reviews.setImage(consumerReviewsObject.optString("consumerImage"));
                        reviews.setReviewsDescription(consumerReviewsObject.optString("consumerComments"));
                        reviews.setImage(consumerReviewsObject.optString("consumerImage"));
                        reviews.setRatingCount(consumerReviewsObject.optString("consumerRating"));
                        reviews.setImage(consumerReviewsObject.optString("consumerImage"));
                        reviewsList.add(reviews);
                    }
                    recyclerView.setAdapter(new ReviewsAdapter(getContext(), reviewsList, raleWayFontBold, ralewayFontRegular));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"chef_detail_taq");
    }


    /**
     To add an new Rating for Chefs Menu Items(POST)
     **/
    public void addRatings() throws JSONException {
        String url = APIBaseURL.addRating;

        JSONObject inputObject = new JSONObject();
        inputObject.put("consumerComments",commandsEditText.getText().toString());
        inputObject.put("chefId",getArguments().getString("ChefId"));
        inputObject.put("consumerName", SaveSharedPreference.getLoggedInUserName(getContext()));
        inputObject.put("consumerRating",ratingBar.getRating());
        inputObject.put("dishId",getArguments().getString("ID"));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Ratings added Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()));

                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest,"add_rating_taq");
    }

    /**
     To delete an dish Item by using this API (DELETE)
     **/
    public void deleteItem()
    {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url = APIBaseURL.deleteDish + getArguments().getString("ID");

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "This Item has deleted", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                } else if (error instanceof AuthFailureError) {
                    //TODO

                } else if (error instanceof ServerError) {


                }
                                else if (error instanceof NetworkError)
                                {
                                    Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                                }else if (error instanceof NetworkError) {
                    //TODO

                } else if (error instanceof ParseError) {
                    //TODO

                }
            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"remove_all_carts");
    }
}
