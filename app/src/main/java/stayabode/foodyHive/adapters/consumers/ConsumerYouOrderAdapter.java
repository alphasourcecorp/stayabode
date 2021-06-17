package stayabode.foodyHive.adapters.consumers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import stayabode.foodyHive.activities.consumers.ConsumerMyBasketActivity;
import stayabode.foodyHive.activities.consumers.ConsumerOrderInfoActivity;
import stayabode.foodyHive.activities.consumers.ConsumerShareReviewActivity;
import stayabode.foodyHive.activities.consumers.TrackOrderActivity;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ConsumerYouOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Orders> objectList = new ArrayList<>();
    Context context;
    Typeface poppinsSemibold;
    Typeface poppinsLight;
    Typeface poppinsBold;
    Typeface poppinsMedium;
    Typeface robotoRegular;
    Typeface robotoBold;
    String title;

    public ConsumerYouOrderAdapter(Context context, List<Orders> objectList, Typeface poppinsSemibold, Typeface poppinsLight, Typeface poppinsMedium, Typeface poppinsBold, Typeface robotoBold, Typeface robotoRegular, String title) {
        this.context = context;
        this.objectList = objectList;
        this.poppinsLight = poppinsLight;
        this.poppinsMedium = poppinsMedium;
        this.poppinsSemibold = poppinsSemibold;
        this.poppinsBold = poppinsBold;
        this.robotoBold = robotoBold;
        this.robotoRegular = robotoRegular;
        this.title = title;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_your_orders_list_item, parent, false);
        return new YourOrderViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        YourOrderViewHolder yourOrderViewHolder = (YourOrderViewHolder) holder;
        yourOrderViewHolder.chefName.setTypeface(poppinsSemibold);
        yourOrderViewHolder.deliveryDate.setTypeface(robotoRegular);
        yourOrderViewHolder.chefName.setTypeface(robotoRegular);
        yourOrderViewHolder.openOrderLayout.setVisibility(View.GONE);
        yourOrderViewHolder.otherOrdersLayout.setVisibility(View.VISIBLE);
        yourOrderViewHolder.orderInfoText.setTypeface(poppinsLight);
        yourOrderViewHolder.writeReview.setTypeface(poppinsLight);
        yourOrderViewHolder.orderInfo.setTypeface(poppinsLight);
        yourOrderViewHolder.orderAgain.setTypeface(poppinsLight);
        yourOrderViewHolder.chefName.setText(objectList.get(position).getChefName());

        String orderDate = objectList.get(position).getOrderDate();
        yourOrderViewHolder.deliveryDate.setText(dateFormat(orderDate));

        Glide.with(context).load(objectList.get(position).getChefImage()).placeholder(R.drawable.foodi_logo_left_image).into(yourOrderViewHolder.chefImage);
        String chefImage = objectList.get(position).getChefImage();

        yourOrderViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        yourOrderViewHolder.recyclerView.setAdapter(new OrderFoodsItemsListsAdapter(context, objectList.get(position).getSingleFoodItemList(), "orders", yourOrderViewHolder.deliveryDate.getText().toString(), yourOrderViewHolder.chefName.getText().toString(), chefImage));

        yourOrderViewHolder.orderInfoPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConsumerOrderInfoActivity.class);
                intent.putExtra("OrderId", objectList.get(position).getOrderID());
                intent.putExtra("title", title);
                intent.putExtra("Itemamount", objectList.get(position).getAmount());
                intent.putExtra("ItemsList", (Serializable) objectList.get(position).getFoodItemList());
                intent.putExtra("quantity", "Order Quantity : " + objectList.get(position).getQuantity());
                intent.putExtra("payment", objectList.get(position).getPaymentmethod());
                intent.putExtra("dateOrdered", yourOrderViewHolder.deliveryDate.getText().toString());
                context.startActivity(intent);
            }
        });
        yourOrderViewHolder.orderInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourOrderViewHolder.orderInfoPic.performClick();
            }
        });
        yourOrderViewHolder.writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConsumerShareReviewActivity.class);
                intent.putExtra("ItemId", objectList.get(position).getFoodItemList().get(0).getFoodId());
                String ss = objectList.get(position).getFoodItemList().get(0).getFoodId();
                intent.putExtra("ItemName", objectList.get(position).getFoodItemList().get(0).getFoodName());
                intent.putExtra("ItemImage", objectList.get(position).getFoodItemList().get(0).getFoodImage());
                intent.putExtra("Itemamount", objectList.get(position).getAmount());
                intent.putExtra("ChefId", objectList.get(position).getChefId());
                context.startActivity(intent);
            }
        });
        yourOrderViewHolder.reviewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourOrderViewHolder.writeReview.performClick();
            }
        });
        if (title.equals("Open")) {
            yourOrderViewHolder.openOrderLayout.setVisibility(View.VISIBLE);
            yourOrderViewHolder.otherOrdersLayout.setVisibility(View.GONE);
        } else {
            yourOrderViewHolder.openOrderLayout.setVisibility(View.GONE);
            yourOrderViewHolder.otherOrdersLayout.setVisibility(View.VISIBLE);
            yourOrderViewHolder.orderAgainLayout.setVisibility(View.GONE);
            String dishId = objectList.get(position).getFoodItemList().get(0).getFoodId();
            yourOrderViewHolder.orderAgainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        orderAgain(dishId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        yourOrderViewHolder.viewStatusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TrackOrderActivity.class);
                intent.putExtra("OrderID", objectList.get(position).getOrderID());
                intent.putExtra("Status", objectList.get(position).getOrderStatus());
                intent.putExtra("From", "Order");
                intent.putExtra("dateOrdered", yourOrderViewHolder.deliveryDate.getText().toString());
                intent.putExtra("quantity", "Order Quantity : " + objectList.get(position).getQuantity());
                context.startActivity(intent);

            }
        });


        yourOrderViewHolder.orderInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ConsumerOrderInfoActivity.class);
                intent.putExtra("OrderId", objectList.get(position).getOrderID());
                intent.putExtra("title", title);
                intent.putExtra("Itemamount", objectList.get(position).getAmount());
                intent.putExtra("ItemsList", (Serializable) objectList.get(position).getFoodItemList());
                intent.putExtra("quantity", "Order Quantity : " + objectList.get(position).getQuantity());
                intent.putExtra("payment", objectList.get(position).getPaymentmethod());
                intent.putExtra("dateOrdered", yourOrderViewHolder.deliveryDate.getText().toString());
                context.startActivity(intent);
            }
        });
    }


    /**
     order again the same item from order list (POST)
     **/
    private void orderAgain(String dishID) throws JSONException {
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
                String errorMessage = response.optString("errorMessage");
                try {
                    if (isSuccess) {
                        Toast.makeText(context, "Successfully added to cart", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ConsumerMyBasketActivity.class);
                        context.startActivity(intent);
                    } else if(errorMessage.equalsIgnoreCase("Dish is Already Exits"))
                        Toast.makeText(context, "Already added to cart", Toast.LENGTH_SHORT).show();
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
     format the date in specific format
     **/
    private String dateFormat(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        String formattedDate=null;
        try {
            serverDate = df.parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy, hh:mm a");

            outputFormat.setTimeZone(TimeZone.getDefault());

            formattedDate = outputFormat.format(serverDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public class YourOrderViewHolder extends RecyclerView.ViewHolder {

        TextView deliveryDate;
        TextView chefName;
        TextView viewStatus;
        TextView orderInfo;
        TextView writeReview;
        TextView orderInfoText;
        TextView orderAgain;
        TextView name;
        ImageView orderInfoPic;
        ImageView chefImage;
        ImageView reviewIcon;
        RecyclerView recyclerView;
        LinearLayout otherOrdersLayout;
        LinearLayout openOrderLayout;
        LinearLayout viewStatusLayout;
        LinearLayout orderInfoLayout;
        LinearLayout orderAgainLayout;

        public YourOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            deliveryDate = itemView.findViewById(R.id.deliveryDate);
            chefName = itemView.findViewById(R.id.chefName);
            viewStatus = itemView.findViewById(R.id.viewStatus);
            orderInfo = itemView.findViewById(R.id.orderInfo);
            writeReview = itemView.findViewById(R.id.writeReview);
            orderInfoText = itemView.findViewById(R.id.orderInfoText);
            otherOrdersLayout = itemView.findViewById(R.id.otherOrdersLayout);
            openOrderLayout = itemView.findViewById(R.id.openOrderLayout);
            orderAgain = itemView.findViewById(R.id.orderAgain);
            orderInfoPic = itemView.findViewById(R.id.orderInfoPic);
            chefImage = itemView.findViewById(R.id.chefImage);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            name = itemView.findViewById(R.id.name);
            viewStatusLayout = itemView.findViewById(R.id.viewStatusLayout);
            orderInfoLayout = itemView.findViewById(R.id.orderInfoLayout);
            orderAgainLayout = itemView.findViewById(R.id.orderAgainLayout);
            reviewIcon = itemView.findViewById(R.id.reviewIcon);
        }
    }


}
