package stayabode.foodyHive.adapters.consumers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.Address;
import stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.activities.consumers.ConsumerMyBasketActivity;
import stayabode.foodyHive.activities.consumers.CookedChefItemDetailActivity;
import stayabode.foodyHive.activities.consumers.ItemDetailsActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.models.ChefMenu;
import stayabode.foodyHive.models.Consumer;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.ItemAddOns;
import stayabode.foodyHive.models.NotificationsLists;
import stayabode.foodyHive.models.PromoCodes;
import stayabode.foodyHive.models.Reviews;
import stayabode.foodyHive.utils.ActionItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.QuickAction;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity.checkAddressRadius;
import static stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity.getAvailableDeliveryAddresses;
import static stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity.getCartCheckOutItems;
import static stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity.recyclerViewFoodItem;
import static stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity.selectedAddress;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.getSideMenuCounts;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.getCartLists;

public class ConsumerHomeAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> objectList = new ArrayList<>();
    Context context;
    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface poppinsMedium;
    Typeface poppinsLight;
    Typeface robotoFontBold;
    Typeface robotoFontRegular;

    private static int COUNTDOWN_RUNNING_TIME = 200;
    Animation expand;
    Animation collapse;
    int addressSelectedPosition = -1;
    String selectedAddressID = "";

    int from = 1;
    JSONArray chef_id_array = new JSONArray();
    RequestQueue requestQueue;


    public ConsumerHomeAdapters(Context context, List<Object> objectList, Typeface robotoFontBold, Typeface robotoFontRegular, Typeface poppinsSemiBold, Typeface poppinsBold, Typeface poppinsLight, Typeface poppinsMedium,int from) {
        this.context = context;
        this.objectList = objectList;
        this.poppinsBold = poppinsBold;
        this.poppinsLight = poppinsLight;
        this.poppinsMedium = poppinsMedium;
        this.poppinsSemiBold = poppinsSemiBold;
        this.robotoFontBold = robotoFontBold;
        this.robotoFontRegular = robotoFontRegular;
        this.from = from;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(viewType, parent, false);

        expand = AnimationUtils.loadAnimation(context, R.anim.expand);
        collapse = AnimationUtils.loadAnimation(context, R.anim.collapse);

        if (viewType == R.layout.consumer_review_list_item) {
            return new ConsumerReviewItemsViewHolder(view);
        } else if (viewType == R.layout.consumer_notification_list_item) {
            return new ConsumerNotificationItemsViewHolder(view);
        } else if (viewType == R.layout.consumer_saved_list_item) {
            return new ConsumerSavedItemsViewHolder(view);
        }  else if(viewType == R.layout.chef_seperate_ordered_items) {
            return new ChefsOrdersItemsViewHolder(view);
        }
        else if (viewType == R.layout.consumer_deliver_address_list_item) {
            return new ConsumerAddressItemViewHolder(view);
        } else if (viewType == R.layout.consumer_your_orders_list_item) {
            return new OrderItemViewHolder(view);
        } else if (viewType == R.layout.consumer_referral_code_list_item) {
            return new ReferralCodesItemsWalletsViewHolder(view);
        } else {
            return new ReferralCodesItemsWalletsViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (objectList.get(position) instanceof Reviews) {
            ConsumerReviewItemsViewHolder consumerReviewItemsViewHolder = (ConsumerReviewItemsViewHolder) holder;
            Reviews reviews = (Reviews) objectList.get(position);
            consumerReviewItemsViewHolder.description.setTypeface(poppinsLight);
            consumerReviewItemsViewHolder.consumerName.setTypeface(poppinsMedium);
            consumerReviewItemsViewHolder.date.setTypeface(poppinsLight);
            consumerReviewItemsViewHolder.ratingCount.setTypeface(poppinsMedium);
            consumerReviewItemsViewHolder.description.setText(reviews.getReviewsDescription());
            consumerReviewItemsViewHolder.consumerName.setText(reviews.getUserName());
            consumerReviewItemsViewHolder.ratingCount.setText(String.valueOf(reviews.getRatingCount()));
            Glide.with(context).load(reviews.getImage()).placeholder(R.drawable.foodi_logo_left_image).into(consumerReviewItemsViewHolder.userProfileImage);
            String date = reviews.getDate();
            String formattedDate = dateFormat(date);
            consumerReviewItemsViewHolder.date.setText("(" + formattedDate + ")");
        } else if (objectList.get(position) instanceof NotificationsLists) {
            ConsumerNotificationItemsViewHolder consumerNotificationItemsViewHolder = (ConsumerNotificationItemsViewHolder) holder;
            consumerNotificationItemsViewHolder.description.setTypeface(poppinsLight);
            consumerNotificationItemsViewHolder.title.setTypeface(poppinsBold);
            consumerNotificationItemsViewHolder.feedback.setTypeface(poppinsLight);
        } else if (objectList.get(position) instanceof FoodItem) {
            ConsumerSavedItemsViewHolder consumerSavedItemsViewHolder = (ConsumerSavedItemsViewHolder) holder;
            FoodItem foodItem = (FoodItem) objectList.get(position);
            consumerSavedItemsViewHolder.title.setTypeface(poppinsSemiBold);
            consumerSavedItemsViewHolder.ratingCount.setTypeface(poppinsLight);
            consumerSavedItemsViewHolder.price.setTypeface(robotoFontRegular);
            consumerSavedItemsViewHolder.availableText.setTypeface(poppinsLight);
            consumerSavedItemsViewHolder.timePreparation.setTypeface(poppinsLight);
            consumerSavedItemsViewHolder.addItem.setTypeface(poppinsSemiBold);

            if(foodItem.getAvailable() && foodItem.getAvailQty()!=0)
            {
                consumerSavedItemsViewHolder.notAvailable.setVisibility(View.GONE);
                consumerSavedItemsViewHolder.cardView.setAlpha((float) 1);
                consumerSavedItemsViewHolder.itemView.setClickable(true);
                consumerSavedItemsViewHolder.cardView.setClickable(true);
            }
            else
            {
                consumerSavedItemsViewHolder.cardView.setAlpha((float) 0.3);
                consumerSavedItemsViewHolder.notAvailable.setVisibility(View.VISIBLE);
                consumerSavedItemsViewHolder.itemView.setClickable(false);
                consumerSavedItemsViewHolder.cardView.setClickable(false);
                if(!foodItem.getAvailable())
                {
                    consumerSavedItemsViewHolder.notAvailabelText.setText("NOT AVAILABLE");
                    consumerSavedItemsViewHolder.notAvailabelText.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                }
                else if(foodItem.getAvailQty() == 0)
                {
                    consumerSavedItemsViewHolder.notAvailabelText.setText("SOLD OUT");
                    consumerSavedItemsViewHolder.notAvailabelText.setBackgroundColor(context.getResources().getColor(R.color.colorNotificationBG));
                }

            }
            Glide.with(context).load(foodItem.getFoodImage()).placeholder(R.drawable.foodi_logo_left_image).into(consumerSavedItemsViewHolder.imageView);
            Glide.with(context).load(foodItem.getChefImage()).placeholder(R.drawable.foodi_logo_left_image).into(consumerSavedItemsViewHolder.chefImage);

            consumerSavedItemsViewHolder.title.setText(foodItem.getFoodName());
            consumerSavedItemsViewHolder.timePreparation.setText(foodItem.getTime());
            consumerSavedItemsViewHolder.availableText.setText(foodItem.getAvailableQuantity());
            consumerSavedItemsViewHolder.ratingCount.setText(String.valueOf(foodItem.getItemRatingAverage()));
            consumerSavedItemsViewHolder.price.setText("\u20B9" + String.valueOf(foodItem.getPrice()));
            consumerSavedItemsViewHolder.price.setBackground(context.getResources().getDrawable(R.drawable.strike_through));
            consumerSavedItemsViewHolder.savePrice.setText(String.valueOf(foodItem.getSavedPrice()));
            consumerSavedItemsViewHolder.discountPrice.setText("\u20B9"+String.valueOf(foodItem.getDiscountedPrice()));

            Log.d("CartSavedQuantity", foodItem.getCartAddedQuantity() + " Saved");
            if (foodItem.getCartAddedQuantity() == 0) {
                consumerSavedItemsViewHolder.addBtnLayout.setVisibility(View.VISIBLE);
                consumerSavedItemsViewHolder.qtyLayout.setVisibility(View.GONE);
            } else {
                consumerSavedItemsViewHolder.addBtnLayout.setVisibility(View.GONE);
                consumerSavedItemsViewHolder.qtyLayout.setVisibility(View.VISIBLE);
                consumerSavedItemsViewHolder.itemCount.setText(String.valueOf(foodItem.getCartAddedQuantity()));
            }
            try {
                consumerSavedItemsViewHolder.ratingBar.setRating(foodItem.getRatingAverage());
                Log.v("avgRating", String.valueOf(foodItem.getRatingAverage()));
            } catch (Exception e) {
                Log.v("avgRating", String.valueOf(foodItem.getRatingAverage()));
                e.printStackTrace();
            }

            if (String.valueOf(foodItem.getDiscountedPercentage()).equalsIgnoreCase("0%\noff")) {
                consumerSavedItemsViewHolder.discountPercent.setVisibility(View.GONE);
                consumerSavedItemsViewHolder.price.setVisibility(View.GONE);
            }
            else{
                consumerSavedItemsViewHolder.discountPercent.setText(String.valueOf(foodItem.getDiscountedPercentage()));
                consumerSavedItemsViewHolder.discountPercent.setVisibility(View.VISIBLE);
                consumerSavedItemsViewHolder.price.setVisibility(View.VISIBLE);
                Log.v("discountPercentage", String.valueOf(foodItem.getDiscountedPercentage()));
            }
            consumerSavedItemsViewHolder.chefImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CookedChefItemDetailActivity.class);
                    intent.putExtra("chefId", foodItem.getChefId());
                    intent.putExtra("dishID", foodItem.getFoodId());
                    intent.putExtra("chefName", foodItem.getChefName());
                    intent.putExtra("chefImage", foodItem.getChefImage());
                    intent.putExtra("chefRatingAverage", foodItem.getChefratingAverage());
                    intent.putExtra("chefRatingCount", foodItem.getChefratingCount());
                    context.startActivity(intent);
                }
            });

            consumerSavedItemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(foodItem.getAvailable())
                    {
                        Intent intent = new Intent(context, ItemDetailsActivity.class);
                        intent.putExtra("Qty", 1);
                        intent.putExtra("Id", foodItem.getFoodId());
                        intent.putExtra("availableCount", foodItem.getAvailableQuantity());
                        intent.putExtra("chefImage", foodItem.getChefImage());
                        intent.putExtra("chefName", foodItem.getChefName());
                        intent.putExtra("chefId", foodItem.getChefId());
                        context.startActivity(intent);
                    }
                }

            });
            consumerSavedItemsViewHolder.addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(foodItem.getAvailable() && foodItem.getAvailQty()!=0)
                    {
                        try {
                            addToCart(foodItem.getFoodId(), foodItem.getFoodName(), foodItem.getFoodImage(), foodItem.getChefId(), Integer.parseInt("1"), consumerSavedItemsViewHolder.addBtnLayout, consumerSavedItemsViewHolder.qtyLayout);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Currently Item is not available", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            consumerSavedItemsViewHolder.increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cartCount = Integer.parseInt(consumerSavedItemsViewHolder.itemCount.getText().toString());
                    cartCount++;

                    if(cartCount > foodItem.getAvailQty())
                    {
                        Toast.makeText(context, "Only quantity of "+foodItem.getAvailQty()+" available", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        consumerSavedItemsViewHolder.itemCount.setText(String.valueOf(cartCount));
                        try {
                            updateCartCount(foodItem.getFoodId(), Integer.parseInt("1"), consumerSavedItemsViewHolder.itemCount, "increase", foodItem.getFoodName(), foodItem.getFoodImage(), foodItem.getChefId());
                            consumerSavedItemsViewHolder.itemCount.setText(String.valueOf(cartCount));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });

            consumerSavedItemsViewHolder.decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cartCount = Integer.parseInt(consumerSavedItemsViewHolder.itemCount.getText().toString());
                    if (cartCount > 1) {
                        cartCount--;
                        consumerSavedItemsViewHolder.itemCount.setText(String.valueOf(cartCount));
                    } else {


                        removeCart(foodItem.getFoodId(),consumerSavedItemsViewHolder.addBtnLayout,consumerSavedItemsViewHolder.qtyLayout);
                    }


                    try {
                        updateCartCount(foodItem.getFoodId(), Integer.parseInt("1"), consumerSavedItemsViewHolder.itemCount, "decrease", foodItem.getFoodName(), foodItem.getFoodImage(), foodItem.getChefId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            consumerSavedItemsViewHolder.popLayImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionItem addItem = new ActionItem(1, "Add", context.getResources().getDrawable(R.drawable.quickaction_arrow_down));

                    final QuickAction mQuickAction = new QuickAction(context);

                    mQuickAction.addActionItem(addItem, foodItem.getProteintCount(), foodItem.getCarbsCount(), foodItem.getFibreCount(), foodItem.getFatCount(), foodItem.getGramsCount(), foodItem.getCaloriesCount());

                    mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
                        @Override
                        public void onItemClick(QuickAction quickAction, int pos, int actionId) {
                            ActionItem actionItem = quickAction.getActionItem(pos);
                        }
                    });

                    mQuickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                        }
                    });

                    mQuickAction.show(v);

                }
            });
            consumerSavedItemsViewHolder.deleteFav.setVisibility(View.VISIBLE);
            consumerSavedItemsViewHolder.deleteFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeFavourite(foodItem.getFoodId(),consumerSavedItemsViewHolder.getAdapterPosition());

                }
            });

        }
        else if(objectList.get(position) instanceof Chef)
        {
            ChefsOrdersItemsViewHolder chefsOrdersItemsViewHolder = (ChefsOrdersItemsViewHolder)holder;
            Chef chef = (Chef)objectList.get(position);
            chefsOrdersItemsViewHolder.chefName.setText("Chef : "+chef.getName());
            chefsOrdersItemsViewHolder.mealCost.setText(String.format("%.2f",Double.valueOf(chef.getSumOfSaleAmount())));
            chefsOrdersItemsViewHolder.taxCost.setText(String.format("%.2f",Double.valueOf(chef.getSumTaxAmount())));
            chefsOrdersItemsViewHolder.packageCost.setText(String.format("%.2f",Double.valueOf(chef.getPackagingCharges())));
            chefsOrdersItemsViewHolder.deliveryCost.setText(String.format("%.2f",Double.valueOf(chef.getDeliveryCharges())));
            chefsOrdersItemsViewHolder.totalCostinPop.setText(String.format("%.2f",Double.valueOf(chef.getTotal())));
            chefsOrdersItemsViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if(context instanceof ConsumerMyBasketActivity)
            {
                chefsOrdersItemsViewHolder.viewMore.setVisibility(View.GONE);
            }
            else
            {
                chefsOrdersItemsViewHolder.viewMore.setVisibility(View.VISIBLE);
            }

//            if(position==0)
//            {
//                chefsOrdersItemsViewHolder.expandableLayout.startAnimation(collapse);
//                chefsOrdersItemsViewHolder.viewMore.setBackgroundResource(R.drawable.arrow_down_icon);
//                CountDownTimer countDownTimer = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 1) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        chefsOrdersItemsViewHolder.expandableLayout.setVisibility(View.GONE);
//                    }
//                };
//                countDownTimer.start();
//            }


            chefsOrdersItemsViewHolder.viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (chefsOrdersItemsViewHolder.expandableLayout.isShown()) {
                        chefsOrdersItemsViewHolder.expandableLayout.startAnimation(collapse);
                        chefsOrdersItemsViewHolder.viewMore.setBackgroundResource(R.drawable.arrow_down_icon);
                        CountDownTimer countDownTimer = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 1) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                chefsOrdersItemsViewHolder.expandableLayout.setVisibility(View.GONE);
                            }
                        };
                        countDownTimer.start();
                    } else {
                        chefsOrdersItemsViewHolder.viewMore.setBackgroundResource(R.drawable.arrow_up_icon);
                        chefsOrdersItemsViewHolder.expandableLayout.startAnimation(expand);
                        chefsOrdersItemsViewHolder.expandableLayout.setVisibility(View.VISIBLE);

                    }
                }
            });
            chefsOrdersItemsViewHolder.recyclerView.setAdapter(new CartItemsAdapter(chef.getFoodItemList(),context,robotoFontBold,robotoFontRegular,poppinsSemiBold,poppinsBold,poppinsLight,poppinsMedium,chefsOrdersItemsViewHolder.recyclerView,chefsOrdersItemsViewHolder.totalCostinPop,chefsOrdersItemsViewHolder.mealCost,chefsOrdersItemsViewHolder.taxCost,chefsOrdersItemsViewHolder.deliveryCost,chefsOrdersItemsViewHolder.packageCost,position,chef));
        }
        else if (objectList.get(position) instanceof Consumer) {
            ConsumerAddressItemViewHolder consumerAddressItemViewHolder = (ConsumerAddressItemViewHolder) holder;
            Consumer consumer = (Consumer) objectList.get(position);
            consumerAddressItemViewHolder.userName.setTypeface(poppinsBold);
            consumerAddressItemViewHolder.addressLine2Value.setTypeface(poppinsLight);
            consumerAddressItemViewHolder.addressLine1Value.setTypeface(poppinsLight);
            consumerAddressItemViewHolder.mobileNumber.setTypeface(poppinsLight);
            consumerAddressItemViewHolder.addAddressButton.setTypeface(poppinsBold);
            consumerAddressItemViewHolder.editAddressButton.setTypeface(poppinsBold);

            consumerAddressItemViewHolder.userName.setText(consumer.getName());
            consumerAddressItemViewHolder.mobileNumber.setText(consumer.getContactNumber());
            consumerAddressItemViewHolder.addressLine1Value.setText(consumer.getAddressLineOne());
            consumerAddressItemViewHolder.addressLine2Value.setText(consumer.getAddressLineTwo());



            consumerAddressItemViewHolder.deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        deleteAddress(consumer.getId(),position);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            consumerAddressItemViewHolder.addAddressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Address.class);
                    intent.putExtra("AddorEdit", "Add");
                    intent.putExtra("emailId",consumer.getEmailId());
                    ((ConsumerCheckOutActivity) context).startActivityForResult(intent, 101);
                }
            });

            consumerAddressItemViewHolder.editAddressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Address.class);
                    intent.putExtra("AddorEdit", "Edit");
                    intent.putExtra("AddressLine1", consumer.getAddressLineOne());
                    intent.putExtra("AddressLine2", consumer.getAddressLineTwo());
                    intent.putExtra("Name", consumer.getName());
                    intent.putExtra("ContactNumber", consumer.getContactNumber());
                    intent.putExtra("Id", consumer.getId());
                    intent.putExtra("emailId",consumer.getEmailId());
                    intent.putExtra("pinCode",consumer.getPinCode());
                    intent.putExtra("landmark",consumer.getLandMark());
                    intent.putExtra("landmark",consumer.getLandMark());
                    intent.putExtra("latitude",consumer.getLatitude());
                    intent.putExtra("longtitude",consumer.getLongtitude());
                    ((ConsumerCheckOutActivity) context).startActivityForResult(intent, 102);
                }
            });
            if (position == addressSelectedPosition) {

                if(!consumerAddressItemViewHolder.radioButton.isChecked())
                {

                    consumerAddressItemViewHolder.radioButton.setChecked(false);

                }
                else
                {

                    consumerAddressItemViewHolder.radioButton.setChecked(true);
                    selectedAddressID = consumer.getId();
                    selectedAddress = consumer.getAddressId();

                    ConsumerCheckOutActivity.selectedAddressLineOne  = consumer.getAddressLineOne();
                    ConsumerCheckOutActivity.selectedAddressLineTwo = consumer.getAddressLineTwo();
                    ConsumerCheckOutActivity.selectedUserName = consumer.getName();
                    ConsumerCheckOutActivity.selectedUserPhoneNumber = consumer.getContactNumber();
                    ConsumerCheckOutActivity.selectedAddressID = consumer.getAddressId();
                    ConsumerCheckOutActivity.selectedEmailID = consumer.getEmailId();
                    ConsumerCheckOutActivity.selectedPinCode = consumer.getPinCode();
                    ConsumerCheckOutActivity.selectedLandMark = consumer.getLandMark();
                    ConsumerCheckOutActivity.selectedLandMark = consumer.getLandMark();
                    ConsumerCheckOutActivity.selectedLandMark = consumer.getLandMark();

                    ConsumerCheckOutActivity.selectedlatitude = consumer.getLatitude();
                    ConsumerCheckOutActivity.selectedlongtitude = consumer.getLongtitude();

                }

            } else {

                if(consumer.getDeliveryHere())
                {

                    consumerAddressItemViewHolder.radioButton.setChecked(true);
                    selectedAddressID = consumer.getId();
                    selectedAddress = consumer.getAddressId();

                    ConsumerCheckOutActivity.selectedAddressLineOne  = consumer.getAddressLineOne();
                    ConsumerCheckOutActivity.selectedAddressLineTwo = consumer.getAddressLineTwo();
                    ConsumerCheckOutActivity.selectedUserName = consumer.getName();
                    ConsumerCheckOutActivity.selectedUserPhoneNumber = consumer.getContactNumber();
                    ConsumerCheckOutActivity.selectedAddressID = consumer.getAddressId();
                    ConsumerCheckOutActivity.selectedEmailID = consumer.getEmailId();
                    ConsumerCheckOutActivity.selectedPinCode = consumer.getPinCode();
                    ConsumerCheckOutActivity.selectedLandMark = consumer.getLandMark();
                    ConsumerCheckOutActivity.selectedlatitude = consumer.getLatitude();
                    ConsumerCheckOutActivity.selectedlongtitude = consumer.getLongtitude();

                    consumerAddressItemViewHolder.radioButton.setChecked(true);

                    getDeliveryCharges(selectedAddressID,1,from);




                }
                else
                {

                    consumerAddressItemViewHolder.radioButton.setChecked(false);

                }
            }
            consumerAddressItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    consumerAddressItemViewHolder.radioButton.performClick();
                }
            });

            consumerAddressItemViewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    selectedAddress = consumer.getAddressId();
                    consumerAddressItemViewHolder.radioButton.setChecked(false);
                    getDeliveryCharges(consumer.getId(),2,1);

                }
            });
        }
        else if (objectList.get(position) instanceof ChefMenu) {
            OrderItemViewHolder orderItemViewHolder = (OrderItemViewHolder) holder;
            orderItemViewHolder.foodName.setTypeface(poppinsSemiBold);
            orderItemViewHolder.chefName.setTypeface(poppinsSemiBold);
            orderItemViewHolder.deliveryDate.setTypeface(robotoFontRegular);
            orderItemViewHolder.chefName.setTypeface(robotoFontRegular);
            orderItemViewHolder.viewStatus.setTypeface(poppinsLight);
            orderItemViewHolder.orderInfo.setTypeface(poppinsLight);
        }
        else if(objectList.get(position) instanceof PromoCodes)
        {
            ReferralCodesItemsWalletsViewHolder referralCodesItemsWalletsViewHolder = (ReferralCodesItemsWalletsViewHolder)holder;
            PromoCodes promoCodes = (PromoCodes)objectList.get(position);
            referralCodesItemsWalletsViewHolder.date.setText(dateFormat(promoCodes.getStartDate()));
            referralCodesItemsWalletsViewHolder.pointsDate.setText(dateFormatforReferralCreated(promoCodes.getStartDate()));
            referralCodesItemsWalletsViewHolder.pointsValue.setText(promoCodes.getDiscount()+" points");
            referralCodesItemsWalletsViewHolder.referredNumber.setText(promoCodes.getPromoCodeText());
            if(promoCodes.getExpired()){
                referralCodesItemsWalletsViewHolder.availableStatusText.setText("Expired");
                referralCodesItemsWalletsViewHolder.availableStatusText.setTextColor(context.getResources().getColor(R.color.colorNotificationBG));
            }else{
                referralCodesItemsWalletsViewHolder.availableStatusText.setText("Available");
                referralCodesItemsWalletsViewHolder.availableStatusText.setTextColor(context.getResources().getColor(R.color.colorGreen));
            }
            if(promoCodes.getUsed()){
                referralCodesItemsWalletsViewHolder.usedStatusText.setText("Used");
                referralCodesItemsWalletsViewHolder.usedStatusText.setTextColor(context.getResources().getColor(R.color.colorNotificationBG));
            }else{
                referralCodesItemsWalletsViewHolder.usedStatusText.setText("Not Used");
                referralCodesItemsWalletsViewHolder.usedStatusText.setTextColor(context.getResources().getColor(R.color.colorGreen));
            }
            if(referralCodesItemsWalletsViewHolder.usedStatusText.getText().toString().equalsIgnoreCase("used")){
                referralCodesItemsWalletsViewHolder.availableStatusText.setText("Consumed");
                referralCodesItemsWalletsViewHolder.availableStatusText.setTextColor(context.getResources().getColor(R.color.colorNotificationBG));
            }
        }
    }

    /**
     delete consumer deliver address from the list
     **/
    private void deleteAddress(String deliverAddressId,int position) throws JSONException {
        String url = APIBaseURL.deleteConsumerDeliveryAddress + deliverAddressId+"/"+ SaveSharedPreference.getLoggedInUserEmail(context);
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Delivery Address deleted", Toast.LENGTH_SHORT).show();
                objectList.remove(position);
                notifyDataSetChanged();
                notifyItemRemoved(position);
                if (objectList.size() == 0) {
                    ((ConsumerCheckOutActivity) context).finish();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("UpdateResponse", error.getMessage() + " Error Occured");
                Toast.makeText(context, "Delivery Address not deleted", Toast.LENGTH_SHORT).show();
                objectList.remove(position);
                notifyDataSetChanged();
//or use this for better perfomance.
                notifyItemRemoved(position);
                if (objectList.size() == 0) {
                    ((ConsumerCheckOutActivity) context).finish();
                }

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "delete_address_taq");
    }


    /**
     format date in specific format
     **/
    private String dateFormat(String date) {
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("d MMM yyyy").format(newDate);
        return formattedDate;
    }

    /**
     format date in specific format
     **/
    private String dateFormatforReferralCreated(String date) {
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(newDate);
        return formattedDate;
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof Reviews) {
            return R.layout.consumer_review_list_item;
        } else if (objectList.get(position) instanceof NotificationsLists) {
            return R.layout.consumer_notification_list_item;
        } else if (objectList.get(position) instanceof FoodItem) {
            return R.layout.consumer_saved_list_item;
        } else if(objectList.get(position) instanceof Chef)
        {
            return R.layout.chef_seperate_ordered_items;
        }
        else if (objectList.get(position) instanceof Consumer) {
            return R.layout.consumer_deliver_address_list_item;
        } else if (objectList.get(position) instanceof ChefMenu) {
            return R.layout.consumer_your_orders_list_item;
        } else if (objectList.get(position) instanceof PromoCodes) {
            return R.layout.consumer_referral_code_list_item;
        } else {

        }
        return super.getItemViewType(position);
    }



    public class ConsumerReviewItemsViewHolder extends RecyclerView.ViewHolder {

        TextView consumerName;
        TextView date;
        TextView ratingCount;
        TextView description;
        TextView header;
        ImageView userProfileImage;

        public ConsumerReviewItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            consumerName = itemView.findViewById(R.id.consumerName);
            date = itemView.findViewById(R.id.date);
            ratingCount = itemView.findViewById(R.id.ratingCount);
            description = itemView.findViewById(R.id.reviewDescription);
            header = itemView.findViewById(R.id.header);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
        }
    }

    public class ConsumerNotificationItemsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView feedback;
        ImageView image;
        CardView cardView;

        public ConsumerNotificationItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            feedback = itemView.findViewById(R.id.feedback);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public class ConsumerSavedItemsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView ratingCount;
        TextView price;
        TextView availableText;
        TextView timePreparation;
        ImageView chefImage;
        ImageView imageView;
        ImageView popLayImage;
        Button addItem;
        LinearLayout qtyLayout;
        TextView decrease;
        TextView itemCount;
        TextView increase;
        LinearLayout addBtnLayout;
        TextView savePrice;
        TextView discountPrice;
        TextView discountPercent;
        ImageView deleteFav;
        RatingBar ratingBar;
        CardView cardView;
        LinearLayout notAvailable;
        TextView notAvailabelText;

        public ConsumerSavedItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemName);
            ratingCount = itemView.findViewById(R.id.ratingCount);
            price = itemView.findViewById(R.id.price);
            availableText = itemView.findViewById(R.id.availableText);
            timePreparation = itemView.findViewById(R.id.timePreparation);
            chefImage = itemView.findViewById(R.id.chefImage);
            popLayImage = itemView.findViewById(R.id.popLayImage);
            addItem = itemView.findViewById(R.id.addCart);
            imageView = itemView.findViewById(R.id.imageView);
            qtyLayout = itemView.findViewById(R.id.qtyLayout);
            decrease = itemView.findViewById(R.id.decrease);
            itemCount = itemView.findViewById(R.id.itemCount);
            increase = itemView.findViewById(R.id.increase);
            addBtnLayout = itemView.findViewById(R.id.addBtnLayout);
            savePrice = itemView.findViewById(R.id.savePrice);
            discountPrice = itemView.findViewById(R.id.discountPrice);
            deleteFav=itemView.findViewById(R.id.deleteIcon);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            discountPercent=itemView.findViewById(R.id.discountPercent);
            cardView=itemView.findViewById(R.id.cardView);
            notAvailable=itemView.findViewById(R.id.notAvailable);
            notAvailabelText=itemView.findViewById(R.id.notAvailabelText);

        }
    }

    public class ChefsOrdersItemsViewHolder extends RecyclerView.ViewHolder
    {
            TextView chefName;
            TextView mealCost;
            TextView taxCost;
            TextView deliveryCost;
            TextView totalCostinPop;
            TextView packageCost;
            RecyclerView recyclerView;
            LinearLayout expandableLayout;
            ImageButton viewMore;


        public ChefsOrdersItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            chefName = itemView.findViewById(R.id.chefName);
            mealCost = itemView.findViewById(R.id.mealCost);
            taxCost = itemView.findViewById(R.id.taxCost);
            deliveryCost = itemView.findViewById(R.id.deliveryCost);
            packageCost = itemView.findViewById(R.id.packageCost);
            totalCostinPop = itemView.findViewById(R.id.totalCostinPop);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            viewMore = itemView.findViewById(R.id.viewMore);
        }
    }
    public class ConsumerMyBasketItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView price;
        TextView itemCount;
        TextView availableText;
        TextView timePreparation;
        TextView saveForLater;
        TextView remove;
        ImageView deleteIcon;
        TextView selectPreference;
        TextView subTotalbreakUpHeader;
        TextView mealPrice;
        TextView mealCost;
        TextView totalTax;
        TextView taxCost;
        TextView deliveryCharge;
        TextView deliveryCost;
        TextView subTotal;
        TextView totalCost;
        ImageView dropdown;
        LinearLayout expandableLayout;
        LinearLayout dropdownLayout;
        CardView cardView;
        ImageView imageView;
        RecyclerView recyclerView;
        TextView decrease;
        TextView increase;


        public ConsumerMyBasketItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            itemCount = itemView.findViewById(R.id.itemCount);
            availableText = itemView.findViewById(R.id.availableText);
            timePreparation = itemView.findViewById(R.id.timePreparation);
            saveForLater = itemView.findViewById(R.id.saveForLater);
            remove = itemView.findViewById(R.id.remove);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            selectPreference = itemView.findViewById(R.id.selectPreference);
            subTotalbreakUpHeader = itemView.findViewById(R.id.subTotalbreakUpHeader);
            mealPrice = itemView.findViewById(R.id.mealPrice);
            mealCost = itemView.findViewById(R.id.mealCost);
            totalTax = itemView.findViewById(R.id.totalTax);
            taxCost = itemView.findViewById(R.id.taxCost);
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

        }
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

        }
    }

    public class ConsumerAddressItemViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView addressLine1Value;
        TextView addressLine2Value;
        TextView mobileNumber;
        Button editAddressButton;
        Button addAddressButton;
        RadioButton radioButton;
        ImageView deleteIcon;

        public ConsumerAddressItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            addressLine1Value = itemView.findViewById(R.id.addressLine1Value);
            addressLine2Value = itemView.findViewById(R.id.addressLine2Value);
            mobileNumber = itemView.findViewById(R.id.mobileNumber);
            editAddressButton = itemView.findViewById(R.id.editAddressButton);
            addAddressButton = itemView.findViewById(R.id.addAddressButton);
            radioButton = itemView.findViewById(R.id.radioButton);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView deliveryDate;
        TextView chefName;
        TextView viewStatus;
        TextView orderInfo;
        TextView writeReview;
        TextView orderAgain;

        LinearLayout otherOrdersLayout;
        LinearLayout openOrderLayout;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            deliveryDate = itemView.findViewById(R.id.deliveryDate);
            chefName = itemView.findViewById(R.id.chefName);
            viewStatus = itemView.findViewById(R.id.viewStatus);
            orderInfo = itemView.findViewById(R.id.orderInfo);
            writeReview = itemView.findViewById(R.id.writeReview);
            orderAgain = itemView.findViewById(R.id.orderAgain);
            otherOrdersLayout = itemView.findViewById(R.id.otherOrdersLayout);
            openOrderLayout = itemView.findViewById(R.id.openOrderLayout);
        }
    }

    public class ReferralCodesItemsWalletsViewHolder extends RecyclerView.ViewHolder
    {
        TextView referredNumber;
        TextView date;
        TextView pointsValue;
        TextView pointsDate;
        CardView cardReferralRootLayout;
        TextView usedStatusText;
        TextView availableStatusText;
        public ReferralCodesItemsWalletsViewHolder(@NonNull View itemView) {
            super(itemView);
            referredNumber = itemView.findViewById(R.id.referredNumber);
            date = itemView.findViewById(R.id.date);
            pointsValue = itemView.findViewById(R.id.pointsValue);
            pointsDate = itemView.findViewById(R.id.pointsDate);
            cardReferralRootLayout = itemView.findViewById(R.id.cardReferralRootLayout);
            usedStatusText = itemView.findViewById(R.id.usedStatusText);
            availableStatusText = itemView.findViewById(R.id.availableStatusText);
        }
    }




    /**
     remove item to the cart (DELETE)
     **/
    public void removeCart(String cartID,LinearLayout addBtn,LinearLayout qtyLayout)
    {
        String url = APIBaseURL.removeCart +SaveSharedPreference.getLoggedInUserEmail(context)+"/"+ cartID;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if(isSuccess)
                    {
                        addBtn.setVisibility(View.VISIBLE);
                        qtyLayout.setVisibility(View.GONE);
                        Toast.makeText(context, "Item Removed from Cart", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getCartLists();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);

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
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"delete_item");
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    /**
     add item to the cart (POST)
     **/
    public void addToCart(String dishID, String dishName, String dishImage, String chefID, int price, LinearLayout addBtn, LinearLayout qtyLayout) throws JSONException {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        String url = APIBaseURL.addToCart;

        JSONObject inputObject = new JSONObject();
        inputObject.put("dishId", dishID);
        inputObject.put("userId", SaveSharedPreference.getLoggedInUserEmail(context));
        inputObject.put("quantity", 1);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Boolean isSuccess = response.optBoolean("isSuccess");
                try {
                    if (isSuccess) {
                        addBtn.setVisibility(View.GONE);
                        qtyLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Successfully added to cart", Toast.LENGTH_SHORT).show();
                        getCartLists();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("CartResponse", "Error " + error.getMessage());
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);

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
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(context));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "add_to_cart_taq");
    }


    /**
     update cart count number (PUT)
     **/
    public void updateCartCount(String dishID, int price, TextView quantityText, String check, String dishName, String dishImage, String chefID) throws JSONException {
        String url = APIBaseURL.updateCart;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);



        JSONObject inputObject = new JSONObject();
        inputObject.put("dishId", dishID);
        inputObject.put("userId", SaveSharedPreference.getLoggedInUserEmail(context));
        inputObject.put("quantity", Integer.parseInt(quantityText.getText().toString()));



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();



                Boolean isSuccess = response.optBoolean("isSuccess");
                try {

                    if (isSuccess) {
                        if (check.equals("increase")) {

                        } else {

                        }

                    }
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
                    ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);

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
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(context));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest, "add_to_cart_taq");
    }




    /**
     get delivery charges for address chosen(GET)
     **/
    public void getDeliveryCharges(String selectedAddressID,int from,int adapterfrom)
    {
        String url = APIBaseURL.getDeliveryCharges+SaveSharedPreference.getLoggedInUserEmail(context)+"/"+selectedAddressID;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Delivery Charges..");
        if(adapterfrom == 1)
        {
            progressDialog.show();
        }


        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("deliveryAddress",response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if(isSuccess)
                    {
                        checkAddressRadius = true;
                        JSONObject dataArray = jsonObject.getJSONObject("data");
                       Globaluse.orderInvoiceNo_str= dataArray.optString("orderInvoiceNo");//sat

                        JSONArray cartDetailsArray = dataArray.getJSONArray("cartDetails");
                        JSONArray totalMenuItemsArray = new JSONArray();
                        double sum = 0;
                        double mealPriceSum = 0;
                        ConsumerCheckOutActivity.objectsList.clear();

                         chef_id_array = new JSONArray();


                        for (int i=0;i < cartDetailsArray.length();i++)
                        {
                            JSONObject menuObject = cartDetailsArray.getJSONObject(i);
                            Chef chef = new Chef();
                            chef.setId(menuObject.optString("chefId"));
                            chef.setName(menuObject.optString("chefName"));





                            JSONArray menuDetailsArray = new JSONArray();

                            if(menuObject.has("menuDetails"))
                            {
                                menuDetailsArray = menuObject.getJSONArray("menuDetails");
                                totalMenuItemsArray = menuObject.getJSONArray("menuDetails");
                            }

                            JSONObject footerObject = new JSONObject();

                            if(menuObject.has("footer"))
                            {
                                footerObject = menuObject.getJSONObject("footer");
                            }
                            chef.setSumOfSaleAmount(footerObject.optString("sumOfSaleAmount"));
                            chef.setSumTaxAmount(footerObject.optString("sumTaxAmount"));
                            chef.setDeliveryCharges(footerObject.optString("deliveryCharges"));
                            chef.setPackagingCharges(footerObject.optString("packagingCharges"));
                            chef.setTotal(footerObject.optString("total"));
                            List<FoodItem> foodItemList = new ArrayList<>();
                            for (int j=0;j < menuDetailsArray.length();j++)
                            {
                                JSONObject menuItemsObject = menuDetailsArray.getJSONObject(j);

                                FoodItem foodItem = new FoodItem();
                                if(menuItemsObject.getJSONArray("dishImage").length()!=0)
                                {
                                    foodItem.setFoodImage(menuItemsObject.getJSONArray("dishImage").get(0).toString());
                                }

                                foodItem.setFoodName(menuItemsObject.optString("dishName"));
                                foodItem.setShortDescription(menuItemsObject.optString("shortDescription"));
                                foodItem.setAvailableQuantity("Available("+menuItemsObject.optString("availableCount")+")");
                                foodItem.setAvailQty(menuItemsObject.optInt("availableCount"));
                                foodItem.setTime(menuItemsObject.optString("preparationTime"));
                                foodItem.setCartId(menuItemsObject.optString("id"));
                                foodItem.setFoodId(menuItemsObject.optString("dishId"));
                                foodItem.setCartQuantity(menuItemsObject.optInt("quantity"));
                                foodItem.setMealPrice(menuItemsObject.optString("mealPrice"));
                                foodItem.setPrice(menuItemsObject.optString("mealPrice"));
                                foodItem.setSubTotal(menuItemsObject.optInt("total"));
                                foodItem.setAvailable(menuItemsObject.optBoolean("isAvailable"));


                                JSONObject quickInfoObject = new JSONObject();

                                if(menuObject.has("quickInfo"))
                                {
                                    quickInfoObject = menuItemsObject.getJSONObject("quickInfo");


                                    JSONObject nutritionObject = new JSONObject();

                                    if(quickInfoObject.has("nutrition"))
                                    {
                                        nutritionObject = quickInfoObject.getJSONObject("nutrition");
                                    }

                                    foodItem.setProteintCount(nutritionObject.optInt("protein"));
                                    foodItem.setFatCount(nutritionObject.optInt("fat"));
                                    foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                                    foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                                }
                                JSONObject chefQuickInfoObject = new JSONObject();

                                if(menuObject.has("chefQuickInfo"))
                                {
                                    chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                                }

                                foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                                foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                                foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                                foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                                foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                                foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                                foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));

                                List<ItemAddOns> itemAddOns= new ArrayList<>();
                                ItemAddOns addOns=new ItemAddOns();
                                addOns.setName("coconut");
                                Double subTotal = menuObject.optDouble("total");
                                sum = sum + subTotal;

                                Double mealPriceTotal = menuObject.optDouble("mealPrice");
                                mealPriceSum = mealPriceSum + mealPriceTotal;
                                foodItemList.add(foodItem);
                                chef.setFoodItemList(foodItemList);

                            }
                            ConsumerCheckOutActivity.objectsList.add(chef);

                            chef_id_array.put(chef.getId());

                        }
                        try {
                            JSONObject orderReportSummaryObject = new JSONObject();

                            if(dataArray.has("orderReportSummary"))
                            {
                                orderReportSummaryObject = dataArray.getJSONObject("orderReportSummary");
                            }

                            ConsumerCheckOutActivity.subTotal.setText("Sub Total ("+jsonObject.optString("count")+" Items)");
                            ConsumerCheckOutActivity.price.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                            ConsumerCheckOutActivity.totalCostinPop.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                            ConsumerCheckOutActivity.taxCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("totalTax"))));
                            ConsumerCheckOutActivity.packagingCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("totalPackingCharges"))));
//                            if(dataArray.optInt("totalPointused") == 0)
//                            {
//                                //ConsumerCheckOutActivity.referralCost.setText(" - "+String.format("%.2f",Double.valueOf("0")));
//                            }
//                            else
//                            {
                            try {
                                ConsumerCheckOutActivity.referralCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("referalAmount"))));
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

//                            }

                            ConsumerCheckOutActivity.mealCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("orderSubTotal"))));
                            ConsumerCheckOutActivity.deliveryCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("totalDeliveryCharges"))));


                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        try {
                            recyclerViewFoodItem.getAdapter().notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
//                            recyclerViewFoodItem.setAdapter(new ConsumerHomeAdapters(consumerCheckOutActivity, ConsumerCheckOutActivity.objectsList, robotoFontBold, robotoFontRegular, poppinsSemiBold, poppinsBold, poppinsLight, poppinsMedium));

                            e.printStackTrace();
                        }




                        try{
                            ConsumerCheckOutActivity.center_name_arraylist = new ArrayList<HashMap<String, String>>();
                            getchefDeatil();
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                else
                    {
                        checkAddressRadius = false;
                        if(jsonObject.optString("data").contains("The pickup and drop addresses are near by"))
                        {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.optString("data"));
                            Toast.makeText(context, jsonObject1.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.optString("data"));
                            Toast.makeText(context, jsonObject1.optString("message"), Toast.LENGTH_SHORT).show();
                        }

                        getCartCheckOutItems(2,1,true);



                    }

                try
                {
                    if(from == 2)
                    {
                        getAvailableDeliveryAddresses(2);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCartCheckOutItems(2,1,true);
                if(from == 2)
                {
                    getAvailableDeliveryAddresses(2);
                }

                progressDialog.dismiss();

                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);

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
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(Globaluse.offlinedeliver)
                    {

                        getCartListsinCheckoutPage();

                    }else
                    {
                        Toast.makeText(context, "Apologies, our services are limited to serviceable areas with in the city only", Toast.LENGTH_SHORT).show();

                    }



                }
            }
        },context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"get_delivery_charge_taq");
    }










    /**
     get delivery charges for address chosen(GET)
     **/
    public void getCartListsinCheckoutPage()
    {
        String url = APIBaseURL.getCartsList+SaveSharedPreference.getLoggedInUserEmail(context);
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Getting Delivery Charges..");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("deliveryAddress",response);
//                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if(isSuccess)
                    {
                        checkAddressRadius = true;
                        JSONObject dataArray = jsonObject.getJSONObject("data");
                        Globaluse.orderInvoiceNo_str= dataArray.optString("orderInvoiceNo");//sat

                        JSONArray cartDetailsArray = dataArray.getJSONArray("cartDetails");
                        JSONArray totalMenuItemsArray = new JSONArray();
                        double sum = 0;
                        double mealPriceSum = 0;
                        ConsumerCheckOutActivity.objectsList.clear();

                        chef_id_array = new JSONArray();


                        for (int i=0;i < cartDetailsArray.length();i++)
                        {
                            JSONObject menuObject = cartDetailsArray.getJSONObject(i);
                            Chef chef = new Chef();
                            chef.setId(menuObject.optString("chefId"));
                            chef.setName(menuObject.optString("chefName"));





                            JSONArray menuDetailsArray = new JSONArray();

                            if(menuObject.has("menuDetails"))
                            {
                                menuDetailsArray = menuObject.getJSONArray("menuDetails");
                                totalMenuItemsArray = menuObject.getJSONArray("menuDetails");
                            }

                            JSONObject footerObject = new JSONObject();

                            if(menuObject.has("footer"))
                            {
                                footerObject = menuObject.getJSONObject("footer");
                            }
                            chef.setSumOfSaleAmount(footerObject.optString("sumOfSaleAmount"));
                            chef.setSumTaxAmount(footerObject.optString("sumTaxAmount"));
                            chef.setDeliveryCharges(footerObject.optString("deliveryCharges"));
                            chef.setPackagingCharges(footerObject.optString("packagingCharges"));
                            chef.setTotal(footerObject.optString("total"));
                            List<FoodItem> foodItemList = new ArrayList<>();
                            for (int j=0;j < menuDetailsArray.length();j++)
                            {
                                JSONObject menuItemsObject = menuDetailsArray.getJSONObject(j);

                                FoodItem foodItem = new FoodItem();
                                if(menuItemsObject.getJSONArray("dishImage").length()!=0)
                                {
                                    foodItem.setFoodImage(menuItemsObject.getJSONArray("dishImage").get(0).toString());
                                }

                                foodItem.setFoodName(menuItemsObject.optString("dishName"));
                                foodItem.setShortDescription(menuItemsObject.optString("shortDescription"));
                                foodItem.setAvailableQuantity("Available("+menuItemsObject.optString("availableCount")+")");
                                foodItem.setAvailQty(menuItemsObject.optInt("availableCount"));
                                foodItem.setTime(menuItemsObject.optString("preparationTime"));
                                foodItem.setCartId(menuItemsObject.optString("id"));
                                foodItem.setFoodId(menuItemsObject.optString("dishId"));
                                foodItem.setCartQuantity(menuItemsObject.optInt("quantity"));
                                foodItem.setMealPrice(menuItemsObject.optString("mealPrice"));
                                foodItem.setPrice(menuItemsObject.optString("mealPrice"));
                                foodItem.setSubTotal(menuItemsObject.optInt("total"));
                                foodItem.setAvailable(menuItemsObject.optBoolean("isAvailable"));


                                JSONObject quickInfoObject = new JSONObject();

                                if(menuObject.has("quickInfo"))
                                {
                                    quickInfoObject = menuItemsObject.getJSONObject("quickInfo");


                                    JSONObject nutritionObject = new JSONObject();

                                    if(quickInfoObject.has("nutrition"))
                                    {
                                        nutritionObject = quickInfoObject.getJSONObject("nutrition");
                                    }

                                    foodItem.setProteintCount(nutritionObject.optInt("protein"));
                                    foodItem.setFatCount(nutritionObject.optInt("fat"));
                                    foodItem.setFibreCount(nutritionObject.optInt("fibre"));
                                    foodItem.setCarbsCount(nutritionObject.optInt("carbohydrates"));
                                }
                                JSONObject chefQuickInfoObject = new JSONObject();

                                if(menuObject.has("chefQuickInfo"))
                                {
                                    chefQuickInfoObject = menuObject.getJSONObject("chefQuickInfo");
                                }

                                foodItem.setChefId(chefQuickInfoObject.optString("chefId"));
                                foodItem.setChefImage(chefQuickInfoObject.optString("chefImagePath"));
                                foodItem.setChefName(chefQuickInfoObject.optString("chefName"));
                                foodItem.setChefprofession(chefQuickInfoObject.optString("profession"));
                                foodItem.setChefratingAverage(chefQuickInfoObject.optInt("ratingAverage"));
                                foodItem.setChefratingCount(chefQuickInfoObject.optInt("ratingsCount"));
                                foodItem.setChefsubscribersCount(chefQuickInfoObject.optInt("subscribersCount"));

                                List<ItemAddOns> itemAddOns= new ArrayList<>();
                                ItemAddOns addOns=new ItemAddOns();
                                addOns.setName("coconut");
                                Double subTotal = menuObject.optDouble("total");
                                sum = sum + subTotal;

                                Double mealPriceTotal = menuObject.optDouble("mealPrice");
                                mealPriceSum = mealPriceSum + mealPriceTotal;
                                foodItemList.add(foodItem);
                                chef.setFoodItemList(foodItemList);
                            }
                            ConsumerCheckOutActivity.objectsList.add(chef);
                            chef_id_array.put(chef.getId());
                        }
                        try {
                            JSONObject orderReportSummaryObject = new JSONObject();

                            if(dataArray.has("orderReportSummary"))
                            {
                                orderReportSummaryObject = dataArray.getJSONObject("orderReportSummary");
                            }
                            ConsumerCheckOutActivity.subTotal.setText("Sub Total ("+jsonObject.optString("count")+" Items)");
                            ConsumerCheckOutActivity.price.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                            ConsumerCheckOutActivity.totalCostinPop.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("grandTotal"))));
                            ConsumerCheckOutActivity.taxCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("totalTax"))));
                            ConsumerCheckOutActivity.packagingCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("totalPackingCharges"))));
//                            if(dataArray.optInt("totalPointused") == 0)
//                            {
//                                //ConsumerCheckOutActivity.referralCost.setText(" - "+String.format("%.2f",Double.valueOf("0")));
//                            }
//                            else
//                            {
                            try {
                                ConsumerCheckOutActivity.referralCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("referalAmount"))));
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
//                            }
                            ConsumerCheckOutActivity.mealCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("orderSubTotal"))));
                            ConsumerCheckOutActivity.deliveryCost.setText(String.format("%.2f",Double.valueOf(orderReportSummaryObject.optString("totalDeliveryCharges"))));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        try {
                            recyclerViewFoodItem.getAdapter().notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
//                            recyclerViewFoodItem.setAdapter(new ConsumerHomeAdapters(consumerCheckOutActivity, ConsumerCheckOutActivity.objectsList, robotoFontBold, robotoFontRegular, poppinsSemiBold, poppinsBold, poppinsLight, poppinsMedium));
                            e.printStackTrace();
                        }
                        try{
                            ConsumerCheckOutActivity.center_name_arraylist = new ArrayList<HashMap<String, String>>();
                            getchefDeatil();
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                    else
                    {
                        checkAddressRadius = false;
                        if(jsonObject.optString("data").contains("The pickup and drop addresses are near by"))
                        {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.optString("data"));
                            Toast.makeText(context, jsonObject1.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.optString("data"));
                            Toast.makeText(context, jsonObject1.optString("message"), Toast.LENGTH_SHORT).show();
                        }

                        getCartCheckOutItems(2,1,true);
                    }

                    try
                    {
                        if(from == 2)
                        {
                            getAvailableDeliveryAddresses(2);
                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCartCheckOutItems(2,1,true);
                if(from == 2)
                {
                    getAvailableDeliveryAddresses(2);
                }
//                progressDialog.dismiss();
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);
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
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
                else{

                        Toast.makeText(context, "Apologies, our services are limited to serviceable areas with in the city only", Toast.LENGTH_SHORT).show();
                }
            }
        },context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"get_delivery_charge_taq");
    }





















    /**
     delete favourite item from the favourite list(POST)
     **/
    public void removeFavourite(String dishID,int position)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url = APIBaseURL.addFavourites +"false/"+dishID+"/"+SaveSharedPreference.getLoggedInUserEmail(context);
        //Log.d("objectPosition",objectList.get(position)+"");


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("FavouriteResponse","Removed Fav"+response);
                try {
                    objectList.remove(position);
                    notifyItemRemoved(position);
                    getSideMenuCounts();
                    Toast.makeText(context, "Item Removed from Favourite", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    notifyItemRemoved(position);
                    getSideMenuCounts();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("FavouriteResponse","Added Fav"+error.getMessage());
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);

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
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(context, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"post_fav_taq");


    }



    public void getchefDeatil() throws JSONException {
        String url = APIBaseURL.chefLocation;

        JSONObject inputObject = new JSONObject();
        inputObject.put("chefIds", chef_id_array);

        CustomJsonRequest jsonObjectRequest = new CustomJsonRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response) {




                for(int i=0;i<response.length();i++){

                    try {
                        JSONObject   jresponse = response.getJSONObject(i);
                        String chefId = jresponse.getString("chefId");
                        String chefName = jresponse.getString("chefName");
                        JSONObject   jresponse_lat_long =jresponse.getJSONObject("chefLocation");
                        String lat = jresponse_lat_long.getString("lat");
                        String lon = jresponse_lat_long.getString("lon");
                        Log.d(ApplicationController.TAG,"chefId"+chefId);
                        Log.d(ApplicationController.TAG, "chefName"+chefName);
                        Log.d(ApplicationController.TAG, "lat"+lat);
                        Log.d(ApplicationController.TAG, "lon"+lon);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("chefId",chefId);
                        map.put("chef_name",chefName);
                        map.put("chef_latitude",lat);
                        map.put("chef_longtitude",lon);
                        map.put("consumer_name",ConsumerCheckOutActivity.selectedUserName);
                        map.put("consumer_latitude",ConsumerCheckOutActivity.selectedlatitude);
                        map.put("consumer_longtitude",ConsumerCheckOutActivity.selectedlongtitude);

                        ConsumerCheckOutActivity.center_name_arraylist.add(map);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Log.d(ApplicationController.TAG, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(ApplicationController.TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(context));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);


    }



    public class CustomJsonRequest<T> extends JsonRequest<JSONArray> {

        private JSONObject mRequestObject;
        private Response.Listener<JSONArray> mResponseListener;

        public CustomJsonRequest(int method, String url, JSONObject requestObject, Response.Listener<JSONArray> responseListener,  Response.ErrorListener errorListener) {
            super(method, url, (requestObject == null) ? null : requestObject.toString(), responseListener, errorListener);
            mRequestObject = requestObject;
            mResponseListener = responseListener;
        }

        @Override
        protected void deliverResponse(JSONArray response) {
            mResponseListener.onResponse(response);
        }

        @Override
        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            try {
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                try {
                    return Response.success(new JSONArray(json),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }
    }



    }














//    public class MyJsonArrayRequest extends JsonRequest<JSONArray> {
//
//
//        public MyJsonArrayRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
//            super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
//        }
//
//        @Override
//        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
//            try {
//                String jsonString = new String(response.data,
//                        HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
//                return Response.success(new JSONArray(jsonString),
//                        HttpHeaderParser.parseCacheHeaders(response));
//            } catch (UnsupportedEncodingException e) {
//                return Response.error(new ParseError(e));
//            } catch (JSONException je) {
//                return Response.error(new ParseError(je));
//            }
//        }
//    }
//
//
//}
