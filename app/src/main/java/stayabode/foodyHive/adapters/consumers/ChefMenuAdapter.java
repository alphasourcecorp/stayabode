package stayabode.foodyHive.adapters.consumers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
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


public class ChefMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<FoodItem> foodItemList = new ArrayList<>();
    Typeface poppinsSemiBold;
    Typeface poppinsLight;
    Typeface RobotoRegular;
    Typeface RobotoBold;

    public ChefMenuAdapter(Context context, List<FoodItem> foodItemList, Typeface poppinsSemiBold, Typeface poppinsLight, Typeface RobotoBold, Typeface RobotoRegular) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.poppinsSemiBold = poppinsSemiBold;
        this.poppinsLight = poppinsLight;
        this.RobotoRegular = RobotoRegular;
        this.RobotoBold = RobotoBold;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_saved_list_item, parent, false);
        return new ChefMenuItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChefMenuItemsViewHolder chefMenuItemsViewHolder = (ChefMenuItemsViewHolder) holder;
        chefMenuItemsViewHolder.title.setTypeface(poppinsSemiBold);
        chefMenuItemsViewHolder.ratingCount.setTypeface(poppinsLight);
        chefMenuItemsViewHolder.price.setTypeface(RobotoRegular);
        chefMenuItemsViewHolder.availableText.setTypeface(poppinsLight);
        chefMenuItemsViewHolder.timePreparation.setTypeface(poppinsLight);

        chefMenuItemsViewHolder.chefImage.setVisibility(View.GONE);
        chefMenuItemsViewHolder.saveForLaterIcon.setVisibility(View.VISIBLE);
        chefMenuItemsViewHolder.optionLayout.setVisibility(View.GONE);
        chefMenuItemsViewHolder.addCart.setText("Add");
        chefMenuItemsViewHolder.addCart.setTypeface(poppinsSemiBold);
        Glide.with(context).load(foodItemList.get(position).getFoodImage()).placeholder(R.drawable.foodi_logo_left_image).into(chefMenuItemsViewHolder.imageView);
        chefMenuItemsViewHolder.title.setText(foodItemList.get(position).getFoodName());
        chefMenuItemsViewHolder.timePreparation.setText(foodItemList.get(position).getTime());
        chefMenuItemsViewHolder.price.setText("\u20B9"+foodItemList.get(position).getPrice());
        chefMenuItemsViewHolder.price.setBackground(context.getResources().getDrawable(R.drawable.strike_through));
        chefMenuItemsViewHolder.savePrice.setText(foodItemList.get(position).getSavedPrice());
        chefMenuItemsViewHolder.discountPrice.setText("\u20B9"+foodItemList.get(position).getDiscountedPrice());
        chefMenuItemsViewHolder.availableText.setText(foodItemList.get(position).getAvailableQuantity());
        chefMenuItemsViewHolder.ratingCount.setText(foodItemList.get(position).getItemRatingAverage());


        Resources r = context.getResources();
        int mBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
        int mLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, r.getDisplayMetrics());
        int mRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, r.getDisplayMetrics());
        int mTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, r.getDisplayMetrics());

        CardView.LayoutParams params = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(mLeft, mTop, mRight, mBottom);
        chefMenuItemsViewHolder.cardView.setLayoutParams(params);

        chefMenuItemsViewHolder.saveScreenLayout.setVisibility(View.GONE);
        chefMenuItemsViewHolder.chefScreenLayout.setVisibility(View.VISIBLE);

        if (String.valueOf(foodItemList.get(position).getDiscountedPercentage()).equalsIgnoreCase("0%\noff")) {
            chefMenuItemsViewHolder.discountPercent.setVisibility(View.GONE);
            chefMenuItemsViewHolder.price.setVisibility(View.GONE);
        }
        else{
            chefMenuItemsViewHolder.discountPercent.setText(String.valueOf(foodItemList.get(position).getDiscountedPercentage()));
            chefMenuItemsViewHolder.discountPercent.setVisibility(View.VISIBLE);
            chefMenuItemsViewHolder.price.setVisibility(View.VISIBLE);
        }
        try {
            chefMenuItemsViewHolder.ratingBar.setRating(foodItemList.get(position).getRatingAverage());

        } catch (Exception e) {

            e.printStackTrace();
        }

        if(foodItemList.get(position).getAvailQty()!=0)
        {
            chefMenuItemsViewHolder.notAvailable.setVisibility(View.GONE);
            chefMenuItemsViewHolder.cardView.setAlpha((float) 1);
        }
        else
        {
            chefMenuItemsViewHolder.cardView.setAlpha((float) 0.3);
            chefMenuItemsViewHolder.notAvailable.setVisibility(View.VISIBLE);
            if(foodItemList.get(position).getAvailQty() == 0)
            {
                chefMenuItemsViewHolder.notAvailabelText.setText("SOLD OUT");
                chefMenuItemsViewHolder.notAvailabelText.setBackgroundColor(context.getResources().getColor(R.color.colorNotificationBG));
            }

        }


        chefMenuItemsViewHolder.popLayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionItem addItem = new ActionItem(1, "Add", context.getResources().getDrawable(R.drawable.quickaction_arrow_down));

                final QuickAction mQuickAction = new QuickAction(context);

                mQuickAction.addActionItem(addItem,foodItemList.get(position).getProteintCount(),foodItemList.get(position).getCarbsCount(),foodItemList.get(position).getFibreCount(),foodItemList.get(position).getFatCount(),foodItemList.get(position).getGramsCount(),foodItemList.get(position).getCaloriesCount());

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

        chefMenuItemsViewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(chefMenuItemsViewHolder.itemCount.getText().toString());
                cartCount++;

                if(cartCount > foodItemList.get(position).getAvailQty())
                {
                    Toast.makeText(context, "Only quantity of "+foodItemList.get(position).getAvailQty()+" available", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    chefMenuItemsViewHolder.itemCount.setText(String.valueOf(cartCount));

                    try {
                        updateCartCount(foodItemList.get(position).getFoodId(),Integer.parseInt("1"),chefMenuItemsViewHolder.itemCount,"increase",foodItemList.get(position).getFoodName(),foodItemList.get(position).getFoodImage(),foodItemList.get(position).getChefId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        chefMenuItemsViewHolder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getLoggedInUserRole(context).equals(""))
                {
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
                            CookedChefItemDetailActivity.startAuth();

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
                }
                else
                {
                                    List<Chef> arrayItems;
                SharedPreferences mySPrefs = SaveSharedPreference.getPreferences(context);
                String serializedObject = mySPrefs.getString(PreferencesUtility.LOGGED_IN_USER_CARTS_LIST, null);
                if (serializedObject != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Chef>>(){}.getType();
                    arrayItems = gson.fromJson(serializedObject, type);
                    if(arrayItems.size() !=0)
                    {
                        for (int i=0;i < arrayItems.size();i++)
                        {

                            if(!arrayItems.get(i).getId().equals(foodItemList.get(position).getChefId()))
                            {

                                if(SaveSharedPreference.checKPopUpAlreadySelected(context) && SaveSharedPreference.checKPopUpSelectedText(context).equals("add"))
                                {
                                    if(foodItemList.get(position).getAvailQty() > 0)
                                    {
                                        try {
                                            addToCart(foodItemList.get(position).getFoodId(),foodItemList.get(position).getFoodName(),foodItemList.get(position).getFoodImage(),foodItemList.get(position).getChefId(),Integer.parseInt("1"),chefMenuItemsViewHolder.addBtnLayout,chefMenuItemsViewHolder.qtyLayout);
                                        }
                                        catch (JSONException ex)
                                        {
                                            ex.printStackTrace();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
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
                                            if(foodItemList.get(position).getAvailQty() > 0)
                                            {
                                                SaveSharedPreference.alreadySavedPopUp(context,true,"add");
                                                try {
                                                    addToCart(foodItemList.get(position).getFoodId(),foodItemList.get(position).getFoodName(),foodItemList.get(position).getFoodImage(),foodItemList.get(position).getChefId(),Integer.parseInt("1"),chefMenuItemsViewHolder.addBtnLayout,chefMenuItemsViewHolder.qtyLayout);
                                                }
                                                catch (JSONException ex)
                                                {
                                                    ex.printStackTrace();
                                                }
                                            }
                                            else
                                            {
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
                                            if(foodItemList.get(position).getAvailQty() > 0)
                                            {
                                                emptyPreviousCart();
                                                SaveSharedPreference.alreadySavedPopUp(context,true,"reset");

                                                try {
                                                    addToCart(foodItemList.get(position).getFoodId(),foodItemList.get(position).getFoodName(),foodItemList.get(position).getFoodImage(),foodItemList.get(position).getChefId(),Integer.parseInt("1"),chefMenuItemsViewHolder.addBtnLayout,chefMenuItemsViewHolder.qtyLayout);
                                                }
                                                catch (JSONException ex)
                                                {
                                                    ex.printStackTrace();
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    alertDialog.show();
                                }


                            }
                            else
                            {
                                if(foodItemList.get(position).getAvailQty() > 0)
                                {
                                    try {
                                        addToCart(foodItemList.get(position).getFoodId(),foodItemList.get(position).getFoodName(),foodItemList.get(position).getFoodImage(),foodItemList.get(position).getChefId(),Integer.parseInt("1"),chefMenuItemsViewHolder.addBtnLayout,chefMenuItemsViewHolder.qtyLayout);
                                    }
                                    catch (JSONException ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }


                    }
                    else
                    {
                        if(foodItemList.get(position).getAvailQty() > 0)
                        {
                            try {
                                addToCart(foodItemList.get(position).getFoodId(),foodItemList.get(position).getFoodName(),foodItemList.get(position).getFoodImage(),foodItemList.get(position).getChefId(),Integer.parseInt("1"),chefMenuItemsViewHolder.addBtnLayout,chefMenuItemsViewHolder.qtyLayout);
                            }
                            catch (JSONException ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                        else
                        {
                            Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    if(foodItemList.get(position).getAvailQty() > 0)
                    {
                        try {
                            addToCart(foodItemList.get(position).getFoodId(),foodItemList.get(position).getFoodName(),foodItemList.get(position).getFoodImage(),foodItemList.get(position).getChefId(),Integer.parseInt("1"),chefMenuItemsViewHolder.addBtnLayout,chefMenuItemsViewHolder.qtyLayout);
                        }
                        catch (JSONException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Item has Sold out", Toast.LENGTH_SHORT).show();
                    }
                }
                }



            }
        });



        chefMenuItemsViewHolder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartCount = Integer.parseInt(chefMenuItemsViewHolder.itemCount.getText().toString());
                if(cartCount>1){
                    cartCount--;
                    chefMenuItemsViewHolder.itemCount.setText(String.valueOf(cartCount));
                }
                else
                {
                    removeCart(foodItemList.get(position).getFoodId(),chefMenuItemsViewHolder.addBtnLayout,chefMenuItemsViewHolder.qtyLayout);
                }


                try {
                    updateCartCount(foodItemList.get(position).getFoodId(),Integer.parseInt("1"),chefMenuItemsViewHolder.itemCount,"decrease",foodItemList.get(position).getFoodName(),foodItemList.get(position).getFoodImage(),foodItemList.get(position).getChefId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        if(foodItemList.get(position).getFavourite())
        {
            chefMenuItemsViewHolder.saveForLaterIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_select));
        }
        else
        {
            chefMenuItemsViewHolder.saveForLaterIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_unselect));

        }

        if(foodItemList.get(position).getCartAddedQuantity() == 0)
        {
            chefMenuItemsViewHolder.addBtnLayout.setVisibility(View.VISIBLE);
            chefMenuItemsViewHolder.qtyLayout.setVisibility(View.GONE);
        }
        else
        {
            chefMenuItemsViewHolder.addBtnLayout.setVisibility(View.GONE);
            chefMenuItemsViewHolder.qtyLayout.setVisibility(View.VISIBLE);
            chefMenuItemsViewHolder.itemCount.setText(String.valueOf(foodItemList.get(position).getCartAddedQuantity()));
        }

        chefMenuItemsViewHolder.saveForLaterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveSharedPreference.getLoggedInUserRole(context).equals(""))
                {
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
                }
                else
                {
                    if (foodItemList.get(position).getFavourite()){
                        foodItemList.get(position).setFavourite(false);
                        chefMenuItemsViewHolder.saveForLaterIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_unselect));
                        removeFavourite(foodItemList.get(position).getFoodId());
                    }else{
                        foodItemList.get(position).setFavourite(true);
                        chefMenuItemsViewHolder.saveForLaterIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_select));
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

    public class ChefMenuItemsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView ratingCount;
        TextView price;
        TextView availableText;
        TextView timePreparation;
        ImageView chefImage;
        ImageView popLayImage;
        ImageView saveForLaterIcon;
        LinearLayout optionLayout;
        Button addCart;
        CardView cardView;
        ImageView imageView;
        LinearLayout qtyLayout;
        LinearLayout saveScreenLayout;
        LinearLayout chefScreenLayout;
        TextView decrease;
        TextView itemCount;
        TextView increase;
        LinearLayout addBtnLayout;
        TextView savePrice;
        TextView discountPrice;
        TextView discountPercent;
        RatingBar ratingBar;
        LinearLayout notAvailable;
        TextView notAvailabelText;

        public ChefMenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemName);
            ratingCount = itemView.findViewById(R.id.ratingCount);
            price = itemView.findViewById(R.id.price);
            availableText = itemView.findViewById(R.id.availableText);
            timePreparation = itemView.findViewById(R.id.timePreparation);
            chefImage = itemView.findViewById(R.id.chefImage);
            popLayImage = itemView.findViewById(R.id.popLayImageChef);
            saveForLaterIcon = itemView.findViewById(R.id.saveForLaterIconChef);
            optionLayout = itemView.findViewById(R.id.optionLayout);
            addCart = itemView.findViewById(R.id.addCartChef);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            qtyLayout = itemView.findViewById(R.id.qtyLayoutChef);
            decrease = itemView.findViewById(R.id.decreaseChef);
            itemCount = itemView.findViewById(R.id.itemCountChef);
            increase = itemView.findViewById(R.id.increaseChef);
            addBtnLayout = itemView.findViewById(R.id.addBtnLayoutChef);
            savePrice = itemView.findViewById(R.id.savePrice);
            discountPrice = itemView.findViewById(R.id.discountPrice);
            chefScreenLayout = itemView.findViewById(R.id.chefScreenLayout);
            saveScreenLayout = itemView.findViewById(R.id.saveScreenLayout);
            discountPercent = itemView.findViewById(R.id.discountPercent);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            notAvailable = itemView.findViewById(R.id.notAvailable);
            notAvailabelText = itemView.findViewById(R.id.notAvailabelText);

        }
    }


    /**
     Add item to cart (POST method)
     **/
    public void addToCart(String dishID,String dishName,String dishImage,String chefID,int price,LinearLayout addBtn,LinearLayout qtyLayout) throws JSONException {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        String url = APIBaseURL.addToCart;

        JSONObject inputObject = new JSONObject();
        inputObject.put("dishId",dishID);
        inputObject.put("userId",SaveSharedPreference.getLoggedInUserEmail(context));
        inputObject.put("quantity",1);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                Boolean isSuccess = response.optBoolean("isSuccess");
                try {
                    if(isSuccess)
                    {
                        addBtn.setVisibility(View.GONE);
                        qtyLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Successfully added to cart", Toast.LENGTH_SHORT).show();
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(context));
                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest,"add_to_cart_taq");
    }




    /**
     delete item from the cart (DELETE method)
     **/
    public void removeCart(String cartID,LinearLayout addBtn,LinearLayout qtyLayout)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        String url = APIBaseURL.removeCart + SaveSharedPreference.getLoggedInUserEmail(context)+"/"+cartID;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        },context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"delete_item");
    }


    /**
     add an item to favourite list(POST)
     **/
    public void addFavourite(String dishID)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        String url = APIBaseURL.addFavourites +"true/"+dishID+"/"+SaveSharedPreference.getLoggedInUserEmail(context);


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


    /**
     remove the favourite item from the list(POST)
     **/
    public void removeFavourite(String dishID)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        String url = APIBaseURL.addFavourites +"false/"+dishID+"/"+SaveSharedPreference.getLoggedInUserEmail(context);


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


    /**
     update the item count number (PUT)
     **/
    public void updateCartCount(String dishID,int price,TextView quantityText,String check,String dishName,String dishImage,String chefID) throws JSONException {
        String url = APIBaseURL.updateCart;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        JSONObject inputObject = new JSONObject();
        inputObject.put("dishId",dishID);
        inputObject.put("userId",SaveSharedPreference.getLoggedInUserEmail(context));
        inputObject.put("quantity",Integer.parseInt(quantityText.getText().toString()));


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Boolean isSuccess = response.optBoolean("isSuccess");
                try {
                    if(isSuccess)
                    {
                        if(check.equals("increase"))
                        {

                        }
                        else
                        {
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
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest,"add_to_cart_taq");
    }


    /**
     To Remove All Items from Cart(DELETE)
     **/
    public void emptyPreviousCart()
    {
        String url = APIBaseURL.removeAllCarts +SaveSharedPreference.getLoggedInUserCartID(context);

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
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"taq");
    }
}
