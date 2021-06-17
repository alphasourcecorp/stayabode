package stayabode.foodyHive.fragments.consumers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerActivityReferFriends;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.adapters.consumers.ConsumerHomeAdapters;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.PromoCodes;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ConsumerNotificationFragment extends Fragment {

    public static Typeface poppinsBold;
    public static Typeface poppinsMedium;
    public static Typeface poppinsLight;

    public static RecyclerView recyclerView;
    public static List<Object> objectList = new ArrayList<>();
    Toolbar toolbar;
    TextView referHeader;
    NestedScrollView scrollView;
    public static ProgressBar progressBar;

    public static int size = 20;
    public static int referralWalletindex = 0;

    public static String TAG = "ConsumerNotificationFragment";

    Button inviteButton;
    TextView inviteCode;
    TextView inviteText;
    public static TextView pointsEarnedValue;
    public static TextView pointsUsedValue;

    public static ConsumerNotificationFragment consumerNotificationFragment;
    Button apply_referal_id;
    PopupWindow mpopup;
    public static TextView yourscour;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consumer_notification, container, false);

        consumerNotificationFragment = this;
        poppinsMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");

        recyclerView = rootView.findViewById(R.id.recyclerView);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        inviteButton = (Button) rootView.findViewById(R.id.inviteButton);
        inviteCode = rootView.findViewById(R.id.inviteCode);
        inviteText = rootView.findViewById(R.id.inviteText);
        pointsEarnedValue = rootView.findViewById(R.id.pointsEarnedValue);
        pointsUsedValue = rootView.findViewById(R.id.pointsUsedValue);
        scrollView = rootView.findViewById(R.id.scrollView);
        progressBar = rootView.findViewById(R.id.progressBar);

        yourscour = rootView.findViewById(R.id.yourscour);



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        toolbar.setTitleTextAppearance(getContext(), R.style.ToolBarFont);
        progressBar.setVisibility(View.GONE);
        objectList.clear();
        apply_referal_id=rootView.findViewById(R.id.apply_referal_id);

        apply_referal_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popUpView = getLayoutInflater().inflate(R.layout.activity_refer_code_apply,
                        null); // inflating popup layout
                mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, true); // Creation of popup
                mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
                mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0); // Displaying popup

                EditText friendsReferalCode=popUpView.findViewById(R.id.friendsReferalCode);
                Button close_id=popUpView.findViewById(R.id.close_id);
                Button ok_id=popUpView.findViewById(R.id.ok_id);

                close_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mpopup.dismiss();
                        try {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                });

                ok_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String friendsReferalCode_str=friendsReferalCode.getText().toString().trim();
                        if(!(friendsReferalCode_str.isEmpty()))
                        {
                            applyReferralCode(friendsReferalCode_str);
                            mpopup.dismiss();
                            try {
                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                        }else
                        {
                            Toast.makeText(getActivity(), "Please enter your referral Code", Toast.LENGTH_SHORT).show();
                        }



                    }
                });


            }
        });


        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i = new Intent(getActivity(), ConsumerActivityReferFriends.class);
                startActivity(i);


                //inviteFriend();
            }
        });
        getReferralscore();
        showmyReferralCode();
       // getWalletHistory();


//        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                    referralWalletindex++;
//                    getWalletHistory();
//                }
//            }
//        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * To Show MY REFERRAL code for Consumer(GET)
     **/
    public void showmyReferralCode()
    {
 String url = APIBaseURL.getUserInfoForReferral + SaveSharedPreference.getLoggedInUserEmail(getActivity());
    //    String url = "https://cimaviapi.azurewebsites.net/api/userinfo/GetUserDetailsByEmail/sekhartsr@gmail.com";

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.optBoolean("isSuccess")) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        if(!(dataObject.optString("myReferralCode").equals("null")))
                        {
                           String myReferralCode = dataObject.optString("myReferralCode").trim();
                            inviteCode.setText("Your Referral Code : "+myReferralCode);
                        }
                        //  myReferralpoint = dataObject.optInt("myReferralpoint");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

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
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, getActivity());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");

    }




//    public void showmyReferralCode()
//    {
//
//
//        String url = APIBaseURL.checkUserExistsOrNot + SaveSharedPreference.getLoggedInUserEmail(getContext());
//
//
//        CustomVolleyRequest customVolleyRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                scrollView.setVisibility(View.VISIBLE);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    JSONObject dataObject = new JSONObject();
//                    if (jsonObject.has("data")) {
//                        dataObject = jsonObject.getJSONObject("data");
//                    }
//                    inviteCode.setText(dataObject.optString("myReferralCode"));
//                    inviteText.setText("Join me on FoodyHive, online food platform, to order authentic home cooked food from expert home chefs around you. Enter my code "+dataObject.optString("myReferralCode")+" to earn back 50 points on your first purchase!");
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                scrollView.setVisibility(View.GONE);
//            }
//        }, getContext());
//
//        ApplicationController.getInstance().addToRequestQueue(customVolleyRequest, "get_referral_history_taq");
//    }








    /**
     * To GET REFERRAL Wallet History for Consumer(GET)
     **/
    public static void getWalletHistory() {

        String url = APIBaseURL.getWalletHistory + SaveSharedPreference.getLoggedInUserEmail(consumerNotificationFragment.getContext()) +"?pageno="+referralWalletindex+"&pagesize="+size;
        Log.v("referalUrl",url);

        if (referralWalletindex > 0) {
            progressBar.setVisibility(View.VISIBLE);
        }
        CustomVolleyRequest customVolleyRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("walletResponse",response);
                progressBar.setVisibility(View.VISIBLE);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject dataObject = new JSONObject();
                    if (jsonObject.has("data")) {
                        dataObject = jsonObject.getJSONObject("data");
                    }


                    JSONArray pointsArray = new JSONArray();
                    if (dataObject.has("points")) {
                        pointsArray = dataObject.getJSONArray("points");
                    }
                    pointsUsedValue.setText(dataObject.optString("totalAvailablePoints"));
                    pointsEarnedValue.setText(dataObject.optString("totalPointsEarned"));
                    if (referralWalletindex == 0) {
                        objectList.clear();
                    }
                    //totalAvailablePoints
                    if (pointsArray.length() > 0) {
                        for (int i = 0; i < pointsArray.length(); i++) {
                            JSONObject pointsObject = pointsArray.getJSONObject(i);
                            PromoCodes promoCodes = new PromoCodes();
                            promoCodes.setPromoCodeText(pointsObject.optString("referalinformation"));
                            promoCodes.setStartDate(pointsObject.optString("validty"));
                            promoCodes.setDiscount(pointsObject.optString("score"));
                            promoCodes.setExpired(pointsObject.optBoolean("isExpired"));
                            promoCodes.setUsed(pointsObject.optBoolean("isUsed"));
                            objectList.add(promoCodes);

                        }
                        recyclerView.setAdapter(new ConsumerHomeAdapters(consumerNotificationFragment.getContext(), objectList, null, null, null, poppinsBold, poppinsLight, poppinsMedium,1));
                    }else{
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pointsUsedValue.setText("0");
                pointsEarnedValue.setText("0");
                progressBar.setVisibility(View.GONE);

                objectList.clear();
                recyclerView.setAdapter(new ConsumerHomeAdapters(consumerNotificationFragment.getContext(), objectList, null, null, null, poppinsBold, poppinsLight, poppinsMedium,1));
            }
        }, consumerNotificationFragment.getContext());

        ApplicationController.getInstance().addToRequestQueue(customVolleyRequest, "get_referral_history_taq");
    }








    public  static void  getReferralscore()
    {
 String url = APIBaseURL.getReferralpoints + SaveSharedPreference.getLoggedInUserEmail(consumerNotificationFragment.getContext());
        //String url = "https://cimaviapi.azurewebsites.net/api/userinfo/GetUserDetailsByEmail/sekhartsr@gmail.com";

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                yourscour.setText("Your score : "+response+" Points");
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //yourscour.setText("Your score : "+response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO


                }
            }
        }, consumerNotificationFragment.getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");

    }








    public void applyReferralCode(String applyreferral_str) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();


        String url = APIBaseURL.applyReferral +""+ SaveSharedPreference.getLoggedInUserEmail(consumerNotificationFragment.getContext())+"/"+ applyreferral_str;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.optBoolean("isSuccess")) {

                        Toast.makeText(getActivity(), "Successfully apply referral code..! ", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), jsonObject.optString("errorMessage"), Toast.LENGTH_LONG).show();
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }, getActivity());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_order_taq_delivery");
    }






    /**
     * INVITE FRIEND & EARN for Consumer(GET)
     **/
    public void inviteFriend() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        String url = APIBaseURL.inviteFriend;

        CustomVolleyRequest customVolleyRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject dataObject = new JSONObject();
                    if (jsonObject.has("data")) {
                        dataObject = jsonObject.getJSONObject("data");
                    }

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);

                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Join me on FoodyHive, online food platform, to order authentic home cooked food from expert home chefs around you. Enter my code " + inviteCode.getText().toString() + " to earn back 50 points on your first purchase!\nDownload Foodyhive Now:\n" + dataObject.optString("description") + "\n" + "https://play.google.com/store/apps/details?id=com.foodyHive");
                    startActivity(Intent.createChooser(intent, "Share"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Join me on FoodyHive, online food platform, to order authentic home cooked food from expert home chefs around you. Enter my code " + inviteCode.getText().toString() + " to earn back 50 points on your first purchase!\nDownload Foodyhive Now:\n" + "https://play.google.com/store/apps/details?id=com.foodyHive");
                startActivity(Intent.createChooser(intent, "Share"));
            }
        }, getContext());

        ApplicationController.getInstance().addToRequestQueue(customVolleyRequest, "get_referral_history_taq");
    }

}