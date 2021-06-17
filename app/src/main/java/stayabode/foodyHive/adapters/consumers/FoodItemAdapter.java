package stayabode.foodyHive.adapters.consumers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import stayabode.foodyHive.activities.consumers.CookedChefItemDetailActivity;
import stayabode.foodyHive.activities.consumers.ItemDetailsActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.utils.ActionItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.QuickAction;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stayabode.foodyHive.utils.PreferencesUtility;

import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.getSideMenuCounts;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.categoryList;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.getCartLists;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.getHomePageFilteredAPI;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.maxPriceSelectedFilter;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.minPriceSelectedFilter;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.updateOtherThanTopOFfers;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.updatePopularChoices;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.updateRecentlyViewedItems;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.updateTopOFFersItems;


public class FoodItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<FoodItem> foodItemList = new ArrayList<>();
    Typeface poppinsSemiBold;
    Typeface poppinsLight;
    Typeface RobotoRegular;
    Typeface RobotoBold;
    String title;


    public FoodItemAdapter(Context context, List<FoodItem> foodItemList, Typeface poppinsSemiBold, Typeface poppinsLight, Typeface RobotoBold, Typeface RobotoRegular, String title) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.poppinsSemiBold = poppinsSemiBold;
        this.poppinsLight = poppinsLight;
        this.RobotoRegular = RobotoRegular;
        this.RobotoBold = RobotoBold;
        this.title = title;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_top_offers_list_item, parent, false);
        return new FoodsItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FoodsItemsViewHolder foodsItemsViewHolder = (FoodsItemsViewHolder) holder;
        FoodItem foodItem = foodItemList.get(position);
        foodsItemsViewHolder.oldPrice.setTypeface(RobotoRegular);
        foodsItemsViewHolder.savePriceDiscount.setTypeface(RobotoRegular);
        foodsItemsViewHolder.price.setTypeface(RobotoBold);
        foodsItemsViewHolder.itemName.setTypeface(poppinsSemiBold);
        foodsItemsViewHolder.ratingCount.setTypeface(poppinsLight);
        foodsItemsViewHolder.timePreparation.setTypeface(poppinsLight);
        foodsItemsViewHolder.availableText.setTypeface(poppinsLight);
        foodsItemsViewHolder.addCart.setTypeface(poppinsSemiBold);
        Glide.with(context).load(foodItemList.get(position).getFoodImage()).placeholder(R.drawable.foodi_logo_left_image).into(foodsItemsViewHolder.imageView);
        Glide.with(context).load(foodItemList.get(position).getChefImage()).placeholder(R.drawable.foodi_logo_left_image).into(foodsItemsViewHolder.chefImage);

        foodsItemsViewHolder.itemName.setText(foodItemList.get(position).getFoodName());
        foodsItemsViewHolder.timePreparation.setText(foodItemList.get(position).getTime());
        foodsItemsViewHolder.availableText.setText(foodItemList.get(position).getAvailableQuantity());
        foodsItemsViewHolder.ratingCount.setText(String.valueOf(foodItemList.get(position).getItemRatingAverage()));
        foodsItemsViewHolder.oldPrice.setText("\u20B9" + String.valueOf(foodItemList.get(position).getPrice()));
        foodsItemsViewHolder.oldPrice.setPaintFlags(foodsItemsViewHolder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        foodsItemsViewHolder.savePriceDiscount.setText(String.valueOf(foodItemList.get(position).getSavedPrice()));
        foodsItemsViewHolder.price.setText(" \u20B9" + String.valueOf(foodItemList.get(position).getDiscountedPrice()));
        foodsItemsViewHolder.ratingBar.setRating(foodItemList.get(position).getRatingAverage());
        if (String.valueOf(foodItemList.get(position).getDiscountedPercentage()).equalsIgnoreCase("0%\noff")) {
            foodsItemsViewHolder.discountPercent.setVisibility(View.GONE);
            foodsItemsViewHolder.oldPrice.setVisibility(View.GONE);
        } else {
            foodsItemsViewHolder.discountPercent.setText(String.valueOf(foodItemList.get(position).getDiscountedPercentage()));
            foodsItemsViewHolder.discountPercent.setVisibility(View.VISIBLE);
            foodsItemsViewHolder.oldPrice.setVisibility(View.VISIBLE);

        }

        foodsItemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra("Qty", foodsItemsViewHolder.itemCount.getText().toString());
                intent.putExtra("Id", foodItemList.get(position).getFoodId());
                intent.putExtra("availableCount", foodItemList.get(position).getAvailableQuantity());
                intent.putExtra("chefImage", foodItemList.get(position).getChefImage());
                intent.putExtra("chefName", foodItemList.get(position).getChefName());
                intent.putExtra("chefId", foodItemList.get(position).getChefId());
                context.startActivity(intent);
            }
        });


        foodsItemsViewHolder.popLayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActionItem addItem = new ActionItem(1, "Add", context.getResources().getDrawable(R.drawable.quickaction_arrow_down));

                final QuickAction mQuickAction = new QuickAction(context);

                mQuickAction.addActionItem(addItem, foodItemList.get(position).getProteintCount(), foodItemList.get(position).getCarbsCount(), foodItemList.get(position).getFibreCount(), foodItemList.get(position).getFatCount(), foodItem.getGramsCount(), foodItem.getCaloriesCount());

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

        foodsItemsViewHolder.infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActionItem addItem = new ActionItem(2, "Info", context.getResources().getDrawable(R.drawable.quickaction_arrow_down));

                final QuickAction mQuickAction = new QuickAction(context);

                mQuickAction.addActionItem(addItem, foodItemList.get(position).getShortDescription());

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

        if(foodItemList.get(position).getAvailQty() == 0 /*||  !foodItemList.get(position).getAvailable()*/)
        {
            foodsItemsViewHolder.soldOutLayout.setVisibility(View.VISIBLE);
            foodsItemsViewHolder.cardLayout.setAlpha((float) 0.2);
        }
        else
        {
            foodsItemsViewHolder.soldOutLayout.setVisibility(View.GONE);
            foodsItemsViewHolder.cardLayout.setAlpha((float) 1);
        }

        foodsItemsViewHolder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SaveSharedPreference.getLoggedInUserRole(context).equals("")) {
                    ViewGroup viewGroup = v.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.sign_in_dialog, viewGroup, false);

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
                            ConsumerMainActivity.startAuth();

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
                    alertDialog.show();
                } else {
                    List<Chef> arrayItems;
                    SharedPreferences mySPrefs = SaveSharedPreference.getPreferences(context);
                    String serializedObject = mySPrefs.getString(PreferencesUtility.LOGGED_IN_USER_CARTS_LIST, null);

                    if (serializedObject != null) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Chef>>() {
                        }.getType();
                        arrayItems = gson.fromJson(serializedObject, type);

                        if (arrayItems.size() != 0) {
                            for (int i = 0; i < arrayItems.size(); i++) {


                                if (!arrayItems.get(i).getId().equals(foodItem.getChefId())) {

                                    if (SaveSharedPreference.checKPopUpAlreadySelected(context) && SaveSharedPreference.checKPopUpSelectedText(context).equals("add")) {
                                        if (foodItemList.get(position).getAvailQty() > 0) {
                                            try {
                                                addToCart(foodItemList.get(position).getFoodId(), foodItemList.get(position).getFoodName(), foodItemList.get(position).getFoodImage(), foodItemList.get(position).getChefId(), Integer.parseInt("1"), foodsItemsViewHolder.addBtnLayout, foodsItemsViewHolder.qtyLayout);
                                            } catch (JSONException ex) {
                                                ex.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
                                        ViewGroup viewGroup = v.findViewById(android.R.id.content);

                                        //then we will inflate the custom alert dialog xml that we created
                                        View dialogView = LayoutInflater.from(context).inflate(R.layout.additional_delivery_popup, viewGroup, false);

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

                                                if (foodItemList.get(position).getAvailQty() > 0) {
                                                    SaveSharedPreference.alreadySavedPopUp(context, true, "add");
                                                    try {
                                                        addToCart(foodItemList.get(position).getFoodId(), foodItemList.get(position).getFoodName(), foodItemList.get(position).getFoodImage(), foodItemList.get(position).getChefId(), Integer.parseInt("1"), foodsItemsViewHolder.addBtnLayout, foodsItemsViewHolder.qtyLayout);
                                                    } catch (JSONException ex) {
                                                        ex.printStackTrace();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                                                }

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
                                                if (foodItemList.get(position).getAvailQty() > 0) {
                                                    emptyPreviousCart();
                                                    SaveSharedPreference.alreadySavedPopUp(context, true, "reset");
                                                    try {
                                                        addToCart(foodItemList.get(position).getFoodId(), foodItemList.get(position).getFoodName(), foodItemList.get(position).getFoodImage(), foodItemList.get(position).getChefId(), Integer.parseInt("1"), foodsItemsViewHolder.addBtnLayout, foodsItemsViewHolder.qtyLayout);
                                                    } catch (JSONException ex) {
                                                        ex.printStackTrace();
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                        alertDialog.show();
                                    }


                                } else {
                                    if (foodItemList.get(position).getAvailQty() > 0) {
                                        try {
                                            addToCart(foodItemList.get(position).getFoodId(), foodItemList.get(position).getFoodName(), foodItemList.get(position).getFoodImage(), foodItemList.get(position).getChefId(), Integer.parseInt("1"), foodsItemsViewHolder.addBtnLayout, foodsItemsViewHolder.qtyLayout);
                                        } catch (JSONException ex) {
                                            ex.printStackTrace();
                                        }
                                    } else {
                                        Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }


                        } else {
                            if (foodItemList.get(position).getAvailQty() > 0) {

                                try {
                                    addToCart(foodItemList.get(position).getFoodId(), foodItemList.get(position).getFoodName(), foodItemList.get(position).getFoodImage(), foodItemList.get(position).getChefId(), Integer.parseInt("1"), foodsItemsViewHolder.addBtnLayout, foodsItemsViewHolder.qtyLayout);
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        if (foodItemList.get(position).getAvailQty() > 0) {

                            try {
                                addToCart(foodItemList.get(position).getFoodId(), foodItemList.get(position).getFoodName(), foodItemList.get(position).getFoodImage(), foodItemList.get(position).getChefId(), Integer.parseInt("1"), foodsItemsViewHolder.addBtnLayout, foodsItemsViewHolder.qtyLayout);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }
        });


        foodsItemsViewHolder.chefImage.setOnClickListener(new View.OnClickListener() {
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

        foodsItemsViewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(foodsItemsViewHolder.itemCount.getText().toString());
                cartCount++;

                if (cartCount > foodItemList.get(position).getAvailQty()) {
                    Toast.makeText(context, "Only quantity of " + foodItemList.get(position).getAvailQty() + " available", Toast.LENGTH_SHORT).show();
                } else {
                    foodsItemsViewHolder.itemCount.setText(String.valueOf(cartCount));
                    try {
                        updateCartCount(foodItemList.get(position).getFoodId(), Integer.parseInt("1"), foodsItemsViewHolder.itemCount, "increase", foodItemList.get(position).getFoodName(), foodItemList.get(position).getFoodImage(), foodItemList.get(position).getChefId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        foodsItemsViewHolder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(foodsItemsViewHolder.itemCount.getText().toString());
                if (cartCount > 1) {
                    cartCount--;
                    foodsItemsViewHolder.itemCount.setText(String.valueOf(cartCount));
                } else {
                    removeCart(foodItemList.get(position).getFoodId(), foodsItemsViewHolder.addBtnLayout, foodsItemsViewHolder.qtyLayout);
                }


                try {
                    updateCartCount(foodItemList.get(position).getFoodId(), Integer.parseInt("1"), foodsItemsViewHolder.itemCount, "decrease", foodItemList.get(position).getFoodName(), foodItemList.get(position).getFoodImage(), foodItemList.get(position).getChefId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        if (foodItem.getFavourite()) {
            foodsItemsViewHolder.addFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_select));
        } else {
            foodsItemsViewHolder.addFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_unselect));
        }


        if (foodItemList.get(position).getCartAddedQuantity() == 0) {


            foodsItemsViewHolder.addBtnLayout.setVisibility(View.VISIBLE);
            foodsItemsViewHolder.qtyLayout.setVisibility(View.GONE);
            foodsItemsViewHolder.itemCount.setText(String.valueOf(foodItem.getCartAddedQuantity() + 1));
        } else {


            foodsItemsViewHolder.addBtnLayout.setVisibility(View.GONE);
            foodsItemsViewHolder.qtyLayout.setVisibility(View.VISIBLE);
            foodsItemsViewHolder.itemCount.setText(String.valueOf(foodItem.getCartAddedQuantity()));
        }


        try {

            if (foodItem.getTypeOfDish().equalsIgnoreCase("VEG")) {

                foodsItemsViewHolder.foodTypeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_veg));
            } else if (foodItem.getTypeOfDish().equalsIgnoreCase("NON-VEG")) {

                foodsItemsViewHolder.foodTypeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_non_veg));
            } else if (foodItem.getTypeOfDish().equalsIgnoreCase("EGGETARIAN")) {

                foodsItemsViewHolder.foodTypeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_egg));
            } else if (foodItem.getTypeOfDish().equalsIgnoreCase("VEGAN")) {

                foodsItemsViewHolder.foodTypeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vegan));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        foodsItemsViewHolder.addFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SaveSharedPreference.getLoggedInUserRole(context).equals("")) {
                    ViewGroup viewGroup = v.findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.sign_in_dialog, viewGroup, false);

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
                            ConsumerMainActivity.startAuth();

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
                    alertDialog.show();
                } else {
                    if (foodItem.getFavourite()) {
                        foodItem.setFavourite(false);
                        foodsItemsViewHolder.addFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_unselect));
                        removeFavourite(foodItem.getFoodId());
                    } else {
                        foodItem.setFavourite(true);
                        foodsItemsViewHolder.addFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_select));
                        addFavourite(foodItem.getFoodId());
                    }
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public class FoodsItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView foodTypeImage;
        ImageView popLayImage;
        ImageView infoImage;
        ImageView chefImage;
        TextView oldPrice;
        TextView savePriceDiscount;
        TextView price;
        TextView itemName;
        TextView availableText;
        TextView ratingCount;
        TextView timePreparation;
        Button addCart;
        LinearLayout qtyLayout;
        LinearLayout addBtnLayout;
        TextView itemCount;
        TextView decrease;
        TextView increase;
        TextView discountPercent;
        ImageView addFavourite;
        LinearLayout soldOutLayout;
        CardView cardLayout;
        RatingBar ratingBar;

        public FoodsItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            foodTypeImage = itemView.findViewById(R.id.foodTypeImage);
            chefImage = itemView.findViewById(R.id.chefImage);
            popLayImage = itemView.findViewById(R.id.popLayImage);
            infoImage = itemView.findViewById(R.id.infoImage);
            oldPrice = itemView.findViewById(R.id.oldPrice);
            savePriceDiscount = itemView.findViewById(R.id.savePriceDiscount);
            price = itemView.findViewById(R.id.price);
            itemName = itemView.findViewById(R.id.itemName);
            ratingCount = itemView.findViewById(R.id.ratingCount);
            availableText = itemView.findViewById(R.id.availableText);
            timePreparation = itemView.findViewById(R.id.timePreparation);
            addCart = itemView.findViewById(R.id.addCart);
            qtyLayout = itemView.findViewById(R.id.qtyLayout);
            addBtnLayout = itemView.findViewById(R.id.addBtnLayout);
            itemCount = itemView.findViewById(R.id.itemCount);
            decrease = itemView.findViewById(R.id.decrease);
            increase = itemView.findViewById(R.id.increase);
            addFavourite = itemView.findViewById(R.id.addFavourite);
            discountPercent = itemView.findViewById(R.id.discountPercent);
            soldOutLayout = itemView.findViewById(R.id.soldOutLayout);
            cardLayout = itemView.findViewById(R.id.cardLayout);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }

    /**
     Add an Item to Cart(POST)
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                getCartLists();

                try {

                    if(categoryList.size()!=0 || minPriceSelectedFilter != 0 && maxPriceSelectedFilter != 0)
                    {
                        getHomePageFilteredAPI(2);
                    }
                    else
                    {
                        if(ConsumerHomeOnDemandFragments.header.getText().toString().equals("Top Offers")){
                            updateTopOFFersItems();
                        }
                        else
                        {
                            updateOtherThanTopOFfers(ConsumerHomeOnDemandFragments.getSelectedCategoryID,ConsumerHomeOnDemandFragments.header.getText().toString());
                        }

                    }
                    updatePopularChoices();
                    updateRecentlyViewedItems();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                //do stuff with the body...

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
     Update an Item to Cart(PUT)
     **/
    public void updateCartCount(String dishID, int price, TextView quantityText, String check, String dishName, String dishImage, String chefID) throws JSONException {
        String url = APIBaseURL.updateCart;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        JSONObject inputObject = new JSONObject();
        inputObject.put("dishId", dishID);
        inputObject.put("userId", SaveSharedPreference.getLoggedInUserEmail(context));
        inputObject.put("quantity", Integer.parseInt(quantityText.getText().toString()));



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();


                getCartLists();
                Boolean isSuccess = response.optBoolean("isSuccess");
                try {
                    if (isSuccess) {
                        if (check.equals("increase")) {
                        } else {

                        }

                        try {
                            if(categoryList.size()!=0 || minPriceSelectedFilter != 0 && maxPriceSelectedFilter != 0)
                            {
                                getHomePageFilteredAPI(2);
                            }
                            else
                            {
                                if(ConsumerHomeOnDemandFragments.header.getText().toString().equals("Top Offers")){
                                    updateTopOFFersItems();
                                }
                                else
                                {
                                    updateOtherThanTopOFfers(ConsumerHomeOnDemandFragments.getSelectedCategoryID,ConsumerHomeOnDemandFragments.header.getText().toString());
                                }
                            }
                            updatePopularChoices();
                            updateRecentlyViewedItems();

                        } catch (JSONException e) {
                            e.printStackTrace();
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
     remove item from the cart(DELETE)
     **/
    public void removeCart(String cartID, LinearLayout addBtn, LinearLayout qtyLayout) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url = APIBaseURL.removeCart + SaveSharedPreference.getLoggedInUserEmail(context) + "/" + cartID;



        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean isSuccess = jsonObject.optBoolean("isSuccess");
                    if (isSuccess) {
                        addBtn.setVisibility(View.VISIBLE);
                        qtyLayout.setVisibility(View.GONE);
                        Toast.makeText(context, "Item Removed from Cart", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getCartLists();

                try {
                    if(categoryList.size()!=0 || minPriceSelectedFilter != 0 && maxPriceSelectedFilter != 0)
                    {
                        getHomePageFilteredAPI(2);
                    }
                    else
                    {
                        if(ConsumerHomeOnDemandFragments.header.getText().toString().equals("Top Offers")){
                            updateTopOFFersItems();
                        }
                        else
                        {
                            updateOtherThanTopOFfers(ConsumerHomeOnDemandFragments.getSelectedCategoryID,ConsumerHomeOnDemandFragments.header.getText().toString());
                        }
                    }
                    updatePopularChoices();
                    updateRecentlyViewedItems();

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
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "delete_item");
    }


    /**
     add an item to the favourite list(POST)
     **/
    public void addFavourite(String dishID) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url = APIBaseURL.addFavourites + "true/" + dishID + "/" + SaveSharedPreference.getLoggedInUserEmail(context);


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Toast.makeText(context, "Item Added to Favourite", Toast.LENGTH_SHORT).show();
                getSideMenuCounts();


                try {
                    if(categoryList.size()!=0 || minPriceSelectedFilter != 0 && maxPriceSelectedFilter != 0)
                    {
                        getHomePageFilteredAPI(2);
                    }
                    else
                    {
                        if(ConsumerHomeOnDemandFragments.header.getText().toString().equals("Top Offers")){
                            updateTopOFFersItems();
                        }
                        else
                        {
                            updateOtherThanTopOFfers(ConsumerHomeOnDemandFragments.getSelectedCategoryID,ConsumerHomeOnDemandFragments.header.getText().toString());
                        }
                    }
                    updatePopularChoices();
                    updateRecentlyViewedItems();

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
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "post_fav_taq");


    }


    /**
     remove an item from the favourite list(POST)
     **/
    public void removeFavourite(String dishID) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url = APIBaseURL.addFavourites + "false/" + dishID + "/" + SaveSharedPreference.getLoggedInUserEmail(context);


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Toast.makeText(context, "Item Removed from Favourite", Toast.LENGTH_SHORT).show();
                getSideMenuCounts();


                try {
                    if(categoryList.size()!=0 || minPriceSelectedFilter != 0 && maxPriceSelectedFilter != 0)
                    {
                        getHomePageFilteredAPI(2);
                    }
                    else
                    {
                        if(ConsumerHomeOnDemandFragments.header.getText().toString().equals("Top Offers")){
                            updateTopOFFersItems();
                        }
                        else
                        {
                            updateOtherThanTopOFfers(ConsumerHomeOnDemandFragments.getSelectedCategoryID,ConsumerHomeOnDemandFragments.header.getText().toString());
                        }
                    }

                    updatePopularChoices();
                    updateRecentlyViewedItems();

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
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "post_fav_taq");


    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    /**
     To Remove All Items from Cart(DELETE)
     **/
    public void emptyPreviousCart() {
        String url = APIBaseURL.removeAllCarts + SaveSharedPreference.getLoggedInUserCartID(context);

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }

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
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "taq");
    }
}
