package stayabode.foodyHive.adapters.consumers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.activities.consumers.CookedChefItemDetailActivity;
import stayabode.foodyHive.activities.consumers.ItemDetailsActivity;
import stayabode.foodyHive.activities.consumers.ViewAllSubItemsActivity;
import stayabode.foodyHive.constants.APIBaseURL;
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

import static stayabode.foodyHive.activities.consumers.ViewAllSubItemsActivity.getCartListsinViewAllPage;
import static stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments.getCartLists;


public class FoodsListVerticalAdapterAdminAccept extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<FoodItem> foodItemList = new ArrayList<>();
    Context context;
    Typeface poppinsSemiBold;
    Typeface RobotoRegular;
    Typeface RobotoBold;
    Typeface poppinsLight;

    public FoodsListVerticalAdapterAdminAccept(Context context, List<FoodItem> foodItemList, Typeface poppinsLight, Typeface poppinsSemiBold, Typeface RobotoRegular, Typeface RobotoBold) {
        this.foodItemList = foodItemList;
        this.context = context;
        this.poppinsLight = poppinsLight;
        this.poppinsSemiBold = poppinsSemiBold;
        this.RobotoBold = RobotoBold;
        this.RobotoRegular = RobotoRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.corporate_admin_accept_list_item, parent, false);
        return new FoodsItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodsItemsViewHolder foodsItemsViewHolder = (FoodsItemsViewHolder) holder;
        foodsItemsViewHolder.name.setTypeface(poppinsSemiBold);
        foodsItemsViewHolder.addCart.setTypeface(poppinsSemiBold);
        foodsItemsViewHolder.ratingCount.setTypeface(poppinsLight);
        foodsItemsViewHolder.availableText.setTypeface(poppinsLight);
        foodsItemsViewHolder.minsText.setTypeface(poppinsLight);
        foodsItemsViewHolder.oldPrice.setTypeface(RobotoRegular);
        foodsItemsViewHolder.savedPrice.setTypeface(RobotoRegular);
        foodsItemsViewHolder.newPrice.setTypeface(RobotoBold);



        Glide.with(context).load(foodItemList.get(position).getFoodImage()).placeholder(R.drawable.foodi_logo_left_image).into(foodsItemsViewHolder.image);
        Glide.with(context).load(foodItemList.get(position).getChefImage()).placeholder(R.drawable.foodi_logo_left_image).into(foodsItemsViewHolder.chefImage);

        foodsItemsViewHolder.name.setText(foodItemList.get(position).getFoodName());
        foodsItemsViewHolder.minsText.setText(foodItemList.get(position).getTime());
        foodsItemsViewHolder.availableText.setText(foodItemList.get(position).getAvailableQuantity());
        foodsItemsViewHolder.ratingCount.setText(String.valueOf(foodItemList.get(position).getItemRatingAverage()));
        foodsItemsViewHolder.oldPrice.setText("\u20B9" + String.valueOf(foodItemList.get(position).getPrice()));
        foodsItemsViewHolder.oldPrice.setBackground(context.getResources().getDrawable(R.drawable.strike_through));
        foodsItemsViewHolder.savedPrice.setText(String.valueOf(foodItemList.get(position).getSavedPrice()));
        foodsItemsViewHolder.newPrice.setText("\u20B9" + String.valueOf(foodItemList.get(position).getDiscountedPrice()));


        foodsItemsViewHolder.accept_reject_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.corporate_accept_reject_admin_popup);

                ImageView close_id = (ImageView) dialog.findViewById(R.id.close_id);



                close_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

//                suubmit_id.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.cancel();
//                        Globaluse.schedule_data_array.add(schedule_data);
//                        runOnUiThread(new Runnable(){
//                            @Override
//                            public void run(){
//                                // change UI elements here
//                                cartTotalCountText.setText(""+Globaluse.schedule_data_array.size());
//                            }
//                        });
//
//                        // Toast.makeText(CorporateSchedule.this, "Please select  date", Toast.LENGTH_SHORT).show();
//
//                    }
//                });

                dialog.show();
            }
        });


        if (String.valueOf(foodItemList.get(position).getDiscountedPercentage()).equalsIgnoreCase("0%\noff")) {
            foodsItemsViewHolder.discountPercent.setVisibility(View.GONE);
            foodsItemsViewHolder.oldPrice.setVisibility(View.GONE);
        }
        else{
            foodsItemsViewHolder.discountPercent.setText(String.valueOf(foodItemList.get(position).getDiscountedPercentage()));
            foodsItemsViewHolder.discountPercent.setVisibility(View.VISIBLE);
            foodsItemsViewHolder.oldPrice.setVisibility(View.VISIBLE);

        }

            try {
            foodsItemsViewHolder.ratingBar.setRating(foodItemList.get(position).getRatingAverage());

        } catch (Exception e) {

            e.printStackTrace();
        }


            if(foodItemList.get(position).getAvailQty() == 0 /*|| !foodItemList.get(position).getAvailable()*/)
            {
                if(!foodItemList.get(position).getAvailable())
                {
                    foodsItemsViewHolder.notAvailabelText.setText("NOT AVAILABLE");
                    foodsItemsViewHolder.notAvailabelText.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                }
                else if(foodItemList.get(position).getAvailQty() == 0)
                {
                    foodsItemsViewHolder.notAvailabelText.setText("SOLD OUT");
                    foodsItemsViewHolder.notAvailabelText.setBackgroundColor(context.getResources().getColor(R.color.colorNotificationBG));
                }
                foodsItemsViewHolder.soldOutLayout.setVisibility(View.VISIBLE);
                foodsItemsViewHolder.cardLayout.setAlpha((float) 0.3);
            }
            else
            {
                foodsItemsViewHolder.soldOutLayout.setVisibility(View.GONE);
                foodsItemsViewHolder.cardLayout.setAlpha((float) 1);
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

                mQuickAction.addActionItem(addItem, foodItemList.get(position).getProteintCount(), foodItemList.get(position).getCarbsCount(), foodItemList.get(position).getFibreCount(), foodItemList.get(position).getFatCount(), foodItemList.get(position).getGramsCount(), foodItemList.get(position).getCaloriesCount());

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
                            ViewAllSubItemsActivity.startAuth();

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




                                if (!arrayItems.get(i).getId().equals(foodItemList.get(position).getChefId())) {

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
                        }

                    }
                }


            }
        });


        foodsItemsViewHolder.chefImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CookedChefItemDetailActivity.class);
                intent.putExtra("chefId", foodItemList.get(position).getChefId());
                intent.putExtra("dishID", foodItemList.get(position).getFoodId());
                intent.putExtra("chefName", foodItemList.get(position).getChefName());
                intent.putExtra("chefImage", foodItemList.get(position).getChefImage());
                intent.putExtra("chefRatingAverage", foodItemList.get(position).getChefratingAverage());
                intent.putExtra("chefRatingCount", foodItemList.get(position).getChefratingCount());
                context.startActivity(intent);
            }
        });

        foodsItemsViewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(foodsItemsViewHolder.itemCount.getText().toString());
                cartCount++;

                if (cartCount > foodItemList.get(position).getAvailQty()) {
                    Toast.makeText(context, "Only quantity of "+foodItemList.get(position).getAvailQty()+" available", Toast.LENGTH_SHORT).show();
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

        if (foodItemList.get(position).getFavourite()) {
            foodsItemsViewHolder.addFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_select));
        } else {
            foodsItemsViewHolder.addFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_unselect));
        }

        if (foodItemList.get(position).

                getCartAddedQuantity() == 0) {
            foodsItemsViewHolder.addBtnLayout.setVisibility(View.VISIBLE);
            foodsItemsViewHolder.qtyLayout.setVisibility(View.GONE);
        } else {
            foodsItemsViewHolder.addBtnLayout.setVisibility(View.GONE);
            foodsItemsViewHolder.qtyLayout.setVisibility(View.VISIBLE);
            foodsItemsViewHolder.itemCount.setText(String.valueOf(foodItemList.get(position).getCartAddedQuantity()));
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
                    if (foodItemList.get(position).getFavourite()) {
                        foodItemList.get(position).setFavourite(false);
                        foodsItemsViewHolder.addFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_unselect));

                        removeFavourite(foodItemList.get(position).getFoodId());
                    } else {
                        foodItemList.get(position).setFavourite(true);
                        foodsItemsViewHolder.addFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_select));
                        addFavourite(foodItemList.get(position).getFoodId());
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

        ImageView image;
        TextView name;
        TextView ratingCount;
        TextView oldPrice;
        TextView savedPrice;
        TextView newPrice;
        TextView availableText;
        TextView minsText;
        Button addCart;
        LinearLayout qtyLayout;
        TextView decrease;
        TextView itemCount;
        TextView increase;
        ImageView popLayImage;
        ImageView chefImage;
        ImageView addFavourite;
        TextView discountPercent;
        LinearLayout addBtnLayout;
        RatingBar ratingBar;
        CardView cardLayout;
        LinearLayout soldOutLayout;
        TextView notAvailabelText;
        Button accept_reject_id;

        public FoodsItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            oldPrice = itemView.findViewById(R.id.oldPrice);
            savedPrice = itemView.findViewById(R.id.savedPrice);
            ratingCount = itemView.findViewById(R.id.ratingCount);
            newPrice = itemView.findViewById(R.id.newPrice);
            availableText = itemView.findViewById(R.id.availableText);
            minsText = itemView.findViewById(R.id.minsText);
            addCart = itemView.findViewById(R.id.addItemBtn);
            qtyLayout = itemView.findViewById(R.id.qtyLayout);
            decrease = itemView.findViewById(R.id.decrease);
            itemCount = itemView.findViewById(R.id.itemCount);
            increase = itemView.findViewById(R.id.increase);
            popLayImage = itemView.findViewById(R.id.popLayImage);
            chefImage = itemView.findViewById(R.id.chefImage);
            addFavourite = itemView.findViewById(R.id.addFavourite);
            addBtnLayout = itemView.findViewById(R.id.addBtnLayout);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            discountPercent = itemView.findViewById(R.id.discountPercent);
            cardLayout = itemView.findViewById(R.id.cardLayout);
            soldOutLayout = itemView.findViewById(R.id.soldOutLayout);
            notAvailabelText = itemView.findViewById(R.id.notAvailabelText);
            accept_reject_id = itemView.findViewById(R.id.accept_reject_id);
        }

    }


    /**
     add item to the cart(POST)
     **/
    public void addToCart(String dishID, String dishName, String dishImage, String chefID, int price, LinearLayout addBtn, LinearLayout qtyLayout) throws JSONException {


        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

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

                        try {
                            getCartListsinViewAllPage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getCartLists();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

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
     update cart count number(PUT)
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


                getCartLists();
                Boolean isSuccess = response.optBoolean("isSuccess");


                try {
                    getCartListsinViewAllPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }

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


                        try {
                            getCartListsinViewAllPage();
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
            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "delete_item");
    }


    /**
     add an item to favourite list(POST)
     **/
    public void addFavourite(String dishID) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        String url = APIBaseURL.addFavourites + "true/" + dishID + "/" + SaveSharedPreference.getLoggedInUserEmail(context);


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(context, "Item Added to Favourite", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "post_fav_taq");


    }


    /**
     remove an item to favourite list(POST)
     **/
    public void removeFavourite(String dishID) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        String url = APIBaseURL.addFavourites + "false/" + dishID + "/" + SaveSharedPreference.getLoggedInUserEmail(context);


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Toast.makeText(context, "Item Removed from Favourite", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

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

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "taq");
    }
}
