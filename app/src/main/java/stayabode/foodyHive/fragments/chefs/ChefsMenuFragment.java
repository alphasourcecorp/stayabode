package stayabode.foodyHive.fragments.chefs;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.HomeAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefDrawer;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class ChefsMenuFragment extends Fragment {

    //RecyclerView recyclerView;

    public static Typeface fontBold;
    public static Typeface fontRegular;
    public static Typeface raleWayFontBold;
    public static Typeface ralewayFontRegular;
    TextView menuTitle;
    TextView noItemHeader;
    TextView subHeader;
    TextView endOfList;
    Button button;
    static LinearLayout buttonAddLayout;
    static LinearLayout addBtn;
    static Button addbutton;
    public static RelativeLayout menuLayout;
    public static LinearLayout noMenuLayout;
    public static NestedScrollView scrollView;
    public static RecyclerView recyclerView;
    public static List<Object> menuObjectList = new ArrayList<>();
    public static ShimmerFrameLayout shimmerFrameLayout;
    public static ChefsMenuFragment chefsMenuFragment;
    static ProgressBar progressBar;


    public static int index = 0;
    static int size = 20;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chef_items_menu, container, false);
        chefsMenuFragment = this;
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        raleWayFontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");
        ralewayFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
        cheftoolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze);

        ChefsMainActivity.chefnavigation.setVisibility(View.VISIBLE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chefDrawer.openDrawer(Gravity.LEFT);
            }
        });

        menuTitle = rootView.findViewById(R.id.menuTitle);
        noItemHeader = rootView.findViewById(R.id.noItemHeader);
        subHeader = rootView.findViewById(R.id.subHeader);
        menuLayout = rootView.findViewById(R.id.menuLayout);
        scrollView = rootView.findViewById(R.id.scrollView);
        noMenuLayout = rootView.findViewById(R.id.noMenuLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerLayout);
        button = rootView.findViewById(R.id.buttonAddItem);
        buttonAddLayout = rootView.findViewById(R.id.buttonAddLayout);
        addBtn = rootView.findViewById(R.id.addBtn);
        addbutton = rootView.findViewById(R.id.addbutton);
        progressBar = rootView.findViewById(R.id.progressBar);
        endOfList = rootView.findViewById(R.id.endOfList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        menuTitle.setText("My Menu");
        menuTitle.setTypeface(fontBold);
        noItemHeader.setText("You have no menu items currently!");
        noItemHeader.setTypeface(fontRegular);
        subHeader.setText("please add someitems to your menu");
        subHeader.setTypeface(fontRegular);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddChefMenuFragment fragment = new AddChefMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putString("From", "Add");
                fragment.setArguments(bundle);
                FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                ft.commit();
            }
        });

        buttonAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddChefMenuFragment fragment = new AddChefMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putString("From", "Add");
                fragment.setArguments(bundle);
                FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                ft.commit();
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddChefMenuFragment fragment = new AddChefMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putString("From", "Add");
                fragment.setArguments(bundle);
                FragmentManager fm = ChefsMainActivity.cheffragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                ft.commit();
            }
        });
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);


        index=0;
        getAllChefMenu();
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    index++;
                    getAllChefMenu();
                }
            }
        });
        return rootView;
    }



    /**
     Get All Chefs Menu from this API(POST)
     **/
    public static void getAllChefMenu() {
        String url = APIBaseURL.chefsGETMenusByFilter;
        JSONObject inputObject = new JSONObject();
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        try {
            inputObject.put("chefid", SaveSharedPreference.getLoggedInWorkFlowID(chefsMenuFragment.getContext()));
            inputObject.put("id", 0);
            inputObject.put("search", "");
            inputObject.put("pageindex", index);
            inputObject.put("pagesize", size);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (index > 0) {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            menuObjectList.clear();
            recyclerView.setVisibility(View.GONE);
            addBtn.setVisibility(View.GONE);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                addBtn.setVisibility(View.VISIBLE);

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray dishesArray = dataObject.getJSONArray("dishes");

                        if(index==0){
                            menuObjectList=new ArrayList<>();
                        }
                        if (dishesArray.length() > 0) {
                            for (int i = 0; i < dishesArray.length(); i++) {
                                JSONObject dishesObject = dishesArray.getJSONObject(i);
                                FoodItem foodItem = new FoodItem();
                                foodItem.setFoodId(dishesObject.optString("dishId"));
                                foodItem.setChefId(dishesObject.optString("chefId"));
                                foodItem.setAvailableStatus("On");
                                foodItem.setFoodName(dishesObject.optString("name"));
                                foodItem.setRatingAverage(Float.parseFloat(dishesObject.optString("ratingAverage")));
                                if (dishesObject.getJSONArray("dishImagePath").length() != 0) {
                                    foodItem.setFoodImage(dishesObject.getJSONArray("dishImagePath").get(0).toString());
                                }
                                foodItem.setTime(dishesObject.optString("deliveryTime") + " - " + dishesObject.optString("deliveryExpectedTime") + " mins");
                                foodItem.setRatingCount(dishesObject.optString("ratingsCount"));
                                foodItem.setReviewsCount(dishesObject.optString("reviewCount"));
                                foodItem.setPrice("₹" + dishesObject.optString("price"));
                                foodItem.setDiscountedPrice(dishesObject.optString("discountedPrice"));
                                foodItem.setActive(dishesObject.optBoolean("isActive"));
                                foodItem.setApproved(dishesObject.optBoolean("isApproved"));
                                foodItem.setAutoAcceptOrder(dishesObject.optBoolean("isActive"));
                                foodItem.setShortDescription(dishesObject.optString("shortDescription"));
                                try {
                                    if (Float.parseFloat(dishesObject.optString("discountedPercent")) > 0) {
                                        foodItem.setDiscountedPercentage(dishesObject.optString("discountedPercent") + "%\nOFF");
                                    } else foodItem.setDiscountedPercentage("0");
                                }
                                catch (Exception e)
                                {
                                    foodItem.setDiscountedPercentage("0");
                                    e.printStackTrace();
                                }

                                menuObjectList.add(foodItem);

                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            try {
                                Toast.makeText(chefsMenuFragment.getContext(), "No more Items!", Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        try {

                            recyclerView.setAdapter(new HomeAdapter(chefsMenuFragment.getContext(), menuObjectList, fontBold, fontRegular, raleWayFontBold, ralewayFontRegular));
                            if (recyclerView.getAdapter().getItemCount() == 0) {
                                noMenuLayout.setVisibility(View.VISIBLE);
                                scrollView.setVisibility(View.GONE);
                                menuLayout.setVisibility(View.GONE);
                            } else {
                                noMenuLayout.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                                menuLayout.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        noMenuLayout.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                        menuLayout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                noMenuLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                menuLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity) chefsMenuFragment.getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(chefsMenuFragment.getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(chefsMenuFragment.getContext());

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(chefsMenuFragment.getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(chefsMenuFragment.getContext()));
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "chef_menu_taq");


    }
}
