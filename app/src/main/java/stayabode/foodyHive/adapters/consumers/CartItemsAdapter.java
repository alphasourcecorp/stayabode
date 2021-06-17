package stayabode.foodyHive.adapters.consumers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;

import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity.getCartCheckOutItemsTotalAmount;
import static stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity.stateReferralAppliedorNot;
import static stayabode.foodyHive.activities.consumers.ConsumerMyBasketActivity.getCartListsinCheckoutPageForTotal;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.getCartLists;

public class CartItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<FoodItem> foodItemList = new ArrayList<>();
    Context context;
    private static int COUNTDOWN_RUNNING_TIME = 200;
    Animation expand;
    Animation collapse;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface poppinsMedium;
    Typeface poppinsLight;
    Typeface robotoFontBold;
    Typeface robotoFontRegular;
    RecyclerView recyclerView;
    TextView subTotalTextView;
    TextView mealPriceTextView;
    TextView taxTextTextView;
    TextView deliveryChargeTextView;
    TextView packagingTextView;
    int chefposition;
    Chef chefLayout;

    public CartItemsAdapter(List<FoodItem> foodItemList, Context context, Typeface robotoFontBold, Typeface robotoFontRegular, Typeface poppinsSemiBold, Typeface poppinsBold, Typeface poppinsLight, Typeface poppinsMedium, RecyclerView recyclerView, TextView subTotalTextView, TextView mealPriceTextView, TextView taxTextTextView, TextView deliveryChargeTextView, TextView packagingChangeTextView, int position, Chef chef) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.poppinsBold = poppinsBold;
        this.poppinsLight = poppinsLight;
        this.poppinsMedium = poppinsMedium;
        this.poppinsSemiBold = poppinsSemiBold;
        this.robotoFontBold = robotoFontBold;
        this.robotoFontRegular = robotoFontRegular;
        this.recyclerView = recyclerView;
        this.subTotalTextView = subTotalTextView;
        this.mealPriceTextView = mealPriceTextView;
        this.taxTextTextView = taxTextTextView;
        this.deliveryChargeTextView = deliveryChargeTextView;
        this.packagingTextView = packagingChangeTextView;
        this.chefposition = position;
        this.chefLayout = chef;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_checkout_food_list_item, parent, false);
        return new ConsumerCheckOutItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConsumerCheckOutItemsViewHolder consumerCheckOutItemsViewHolder = (ConsumerCheckOutItemsViewHolder) holder;
        FoodItem checkOutItem = foodItemList.get(position);
        consumerCheckOutItemsViewHolder.itemName.setTypeface(poppinsSemiBold);
        consumerCheckOutItemsViewHolder.price.setTypeface(robotoFontRegular);
        consumerCheckOutItemsViewHolder.itemCount.setTypeface(poppinsSemiBold);
        consumerCheckOutItemsViewHolder.selectPreference.setTypeface(poppinsLight);
        consumerCheckOutItemsViewHolder.availableText.setTypeface(poppinsLight);
        consumerCheckOutItemsViewHolder.mealPrice.setTypeface(poppinsMedium);
        consumerCheckOutItemsViewHolder.mealCost.setTypeface(poppinsMedium);
        consumerCheckOutItemsViewHolder.totalTax.setTypeface(poppinsMedium);
        consumerCheckOutItemsViewHolder.taxCost.setTypeface(poppinsMedium);
        consumerCheckOutItemsViewHolder.deliveryCharge.setTypeface(poppinsMedium);
        consumerCheckOutItemsViewHolder.deliveryCost.setTypeface(poppinsMedium);
        consumerCheckOutItemsViewHolder.subTotal.setTypeface(poppinsBold);
        consumerCheckOutItemsViewHolder.totalCost.setTypeface(poppinsBold);
        consumerCheckOutItemsViewHolder.timePreparation.setText(String.valueOf(Integer.parseInt(checkOutItem.getTime()) + 30 + " - " + String.valueOf(Integer.parseInt(checkOutItem.getTime()) + 30) + " mins"));

        if (foodItemList.get(position).getAvailable() && foodItemList.get(position).getAvailQty() != 0) {
            consumerCheckOutItemsViewHolder.soldOutLayout.setVisibility(View.GONE);
            consumerCheckOutItemsViewHolder.rootLayout.setAlpha((float) 1);
            consumerCheckOutItemsViewHolder.itemView.setClickable(true);
            consumerCheckOutItemsViewHolder.cardView.setClickable(true);
            consumerCheckOutItemsViewHolder.decrease.setEnabled(true);
            consumerCheckOutItemsViewHolder.increase.setEnabled(true);
        } else {
            consumerCheckOutItemsViewHolder.rootLayout.setAlpha((float) 0.1);
            consumerCheckOutItemsViewHolder.soldOutLayout.setVisibility(View.VISIBLE);
            consumerCheckOutItemsViewHolder.itemView.setClickable(false);
            consumerCheckOutItemsViewHolder.cardView.setClickable(false);
            consumerCheckOutItemsViewHolder.decrease.setEnabled(false);
            consumerCheckOutItemsViewHolder.increase.setEnabled(false);

            if (!foodItemList.get(position).getAvailable()) {
                consumerCheckOutItemsViewHolder.notAvailabelText.setText("NOT AVAILABLE");
                consumerCheckOutItemsViewHolder.notAvailabelText.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            } else if (foodItemList.get(position).getAvailQty() == 0) {
                consumerCheckOutItemsViewHolder.notAvailabelText.setText("SOLD OUT");
                consumerCheckOutItemsViewHolder.notAvailabelText.setBackgroundColor(context.getResources().getColor(R.color.colorNotificationBG));
            }

        }
        try {
            Glide.with(context).load(checkOutItem.getFoodImage()).placeholder(R.drawable.foodi_logo_left_image).into(consumerCheckOutItemsViewHolder.imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }


        consumerCheckOutItemsViewHolder.mealCost.setText(String.format("%.2f", Double.valueOf(checkOutItem.getMealPrice())));
        consumerCheckOutItemsViewHolder.totalCost.setText(String.format("%.2f", Double.valueOf(checkOutItem.getSubTotal())));
        consumerCheckOutItemsViewHolder.itemName.setText(checkOutItem.getFoodName());
        consumerCheckOutItemsViewHolder.itemCount.setText(String.valueOf(checkOutItem.getCartQuantity()));
        consumerCheckOutItemsViewHolder.availableText.setText(String.valueOf(checkOutItem.getAvailableQuantity()));

        consumerCheckOutItemsViewHolder.price.setText("Price - " + String.format("%.2f", Double.valueOf(checkOutItem.getMealPrice())));

        consumerCheckOutItemsViewHolder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCart(checkOutItem.getFoodId(), position);
            }
        });

        consumerCheckOutItemsViewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(consumerCheckOutItemsViewHolder.itemCount.getText().toString());
                cartCount++;

                if (cartCount > checkOutItem.getAvailQty()) {
                    Toast.makeText(context, "Only quantity of " + checkOutItem.getAvailQty() + " available", Toast.LENGTH_SHORT).show();
                } else {
                    consumerCheckOutItemsViewHolder.itemCount.setText(String.valueOf(cartCount));
                    try {
                        updateCartCount(checkOutItem.getFoodId(), 1, consumerCheckOutItemsViewHolder.itemCount, "decrease", checkOutItem.getFoodName(), checkOutItem.getFoodImage(), checkOutItem.getChefId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        consumerCheckOutItemsViewHolder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(consumerCheckOutItemsViewHolder.itemCount.getText().toString());
                if (cartCount > 1) {
                    cartCount--;
                    consumerCheckOutItemsViewHolder.itemCount.setText(String.valueOf(cartCount));
                } else {
                    removeCart(checkOutItem.getFoodId(), position);
                }

                try {
                    updateCartCount(checkOutItem.getFoodId(), 1, consumerCheckOutItemsViewHolder.itemCount, "decrease", checkOutItem.getFoodName(), checkOutItem.getFoodImage(), checkOutItem.getChefId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        consumerCheckOutItemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        consumerCheckOutItemsViewHolder.dropdownLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (consumerCheckOutItemsViewHolder.expandableLayout.isShown()) {
                    consumerCheckOutItemsViewHolder.expandableLayout.startAnimation(collapse);
                    consumerCheckOutItemsViewHolder.dropdown.setBackgroundResource(R.drawable.consumer_arrow_down);

                    CountDownTimer countDownTimer = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 1) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            consumerCheckOutItemsViewHolder.expandableLayout.setVisibility(View.GONE);
                        }
                    };
                    countDownTimer.start();
                } else {
                    consumerCheckOutItemsViewHolder.dropdown.setBackgroundResource(R.drawable.consumer_arrow_up);

                    consumerCheckOutItemsViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    consumerCheckOutItemsViewHolder.expandableLayout.startAnimation(expand);
                    consumerCheckOutItemsViewHolder.expandableLayout.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public class ConsumerCheckOutItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView price;
        TextView itemCount;
        TextView selectPreference;
        TextView mealPrice;
        TextView mealCost;
        TextView totalTax;
        TextView taxCost;
        TextView deliveryCharge;
        TextView deliveryCost;
        TextView subTotal;
        TextView totalCost;
        TextView availableText;
        ImageView deleteIcon;
        ImageView dropdown;
        LinearLayout expandableLayout;
        CardView cardView;
        ImageView imageView;
        RecyclerView recyclerView;
        LinearLayout dropdownLayout;
        TextView decrease;
        TextView increase;
        LinearLayout soldOutLayout;
        LinearLayout rootLayout;
        TextView notAvailabelText;
        TextView timePreparation;


        public ConsumerCheckOutItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            itemCount = itemView.findViewById(R.id.itemCount);
            selectPreference = itemView.findViewById(R.id.selectPreference);
            mealPrice = itemView.findViewById(R.id.mealPrice);
            mealCost = itemView.findViewById(R.id.mealCost);
            totalTax = itemView.findViewById(R.id.totalTax);
            taxCost = itemView.findViewById(R.id.taxCost);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            deliveryCharge = itemView.findViewById(R.id.deliveryCharge);
            deliveryCost = itemView.findViewById(R.id.deliveryCost);
            subTotal = itemView.findViewById(R.id.subTotal);
            totalCost = itemView.findViewById(R.id.totalCost);
            dropdown = itemView.findViewById(R.id.dropdown);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            cardView = itemView.findViewById(R.id.cardView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            dropdownLayout = itemView.findViewById(R.id.dropdownLayout);
            imageView = itemView.findViewById(R.id.imageView);
            decrease = itemView.findViewById(R.id.decrease);
            increase = itemView.findViewById(R.id.increase);
            availableText = itemView.findViewById(R.id.availableText);
            soldOutLayout = itemView.findViewById(R.id.soldOutLayout);
            notAvailabelText = itemView.findViewById(R.id.notAvailabelText);
            timePreparation = itemView.findViewById(R.id.timePreparation);
            rootLayout = itemView.findViewById(R.id.rootLayout);

        }
    }

    /**
     * Delete food item from the cart (DELETE method)
     **/

    public void removeCart(String cartID, int position) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "" ;

        try {
            url = APIBaseURL.removeCart + SaveSharedPreference.getLoggedInUserEmail(context) + "/" + cartID+"?Referal="+stateReferralAppliedorNot;
        }
        catch (Exception e)
        {
            url = APIBaseURL.removeCart + SaveSharedPreference.getLoggedInUserEmail(context) + "/" + cartID;
            e.printStackTrace();
        }

        Log.d("ReferralDelete",url);
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        Toast.makeText(context, "Item Removed from Cart", Toast.LENGTH_SHORT).show();
                        foodItemList.remove(position);
                        notifyDataSetChanged();
                        notifyItemRemoved(position);

                        try {
                            getCartListsinCheckoutPageForTotal(2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {
                            getCartCheckOutItemsTotalAmount(subTotalTextView, mealPriceTextView, taxTextTextView, deliveryChargeTextView, packagingTextView, 2,stateReferralAppliedorNot);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity) context).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "delete_item");
    }


    /**
     * To update the cart count number (PUT method)
     **/
    public void updateCartCount(String dishID, int price, TextView quantityText, String check, String dishName, String dishImage, String chefID) throws JSONException {
        String url = "" ;

        try {
           url =  APIBaseURL.updateCart+"?Referal="+stateReferralAppliedorNot;
        }
        catch (Exception e)
        {
            url =  APIBaseURL.updateCart;
            e.printStackTrace();
        }
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        JSONObject inputObject = new JSONObject();
        inputObject.put("dishId", dishID);
        inputObject.put("userId", SaveSharedPreference.getLoggedInUserEmail(context));
        inputObject.put("quantity", Integer.parseInt(quantityText.getText().toString()));

        Log.d("ReferralUpdate",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();


                Boolean isSuccess = response.optBoolean("isSuccess");
                try {
                    getCartLists();
                    if (isSuccess) {
                        if (check.equals("increase")) {

                        } else {
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    getCartListsinCheckoutPageForTotal(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    getCartCheckOutItemsTotalAmount(subTotalTextView, mealPriceTextView, taxTextTextView, deliveryChargeTextView, packagingTextView, 1,stateReferralAppliedorNot);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity) context).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

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
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(context));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "add_to_cart_taq");
    }

}
