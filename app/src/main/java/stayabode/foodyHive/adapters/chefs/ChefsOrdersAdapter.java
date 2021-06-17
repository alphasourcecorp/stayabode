package stayabode.foodyHive.adapters.chefs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.CurrentOrders;
import stayabode.foodyHive.models.FoodItem;
import stayabode.foodyHive.models.NotificationsLists;
import stayabode.foodyHive.models.OrderByMonth;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.models.UpcomingOrders;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static stayabode.foodyHive.fragments.chefs.ChefsOrderFragment.getCountsForTransites;
import static stayabode.foodyHive.fragments.chefs.ChefsOrderFragment.getOpenedOrders;
import static stayabode.foodyHive.fragments.chefs.ChefsOrderFragment.getOpenedOrdersForCount;



public class ChefsOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;


    CountDownTimer cdt;
    CountDownTimer preparationcdt;

    public ChefsOrdersAdapter(Context context, List<Object> objectList, Typeface fontBold, Typeface fontRegular) {
        this.context = context;
        this.objectList = objectList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(viewType, parent, false);

        if (viewType == R.layout.upcoming_order_list_item) {
            return new UpcomingOrdersItemViewHolder(view);
        } else if (viewType == R.layout.current_order_list_item) {
            return new CurrentOrdersItemViewHolder(view);
        } else if (viewType == R.layout.chef_cancelled_order_list_item) {
            return new CompletedorCancelledOrdersItemViewHolder(view);
        } else if (viewType == R.layout.chef_notification_list_item) {
            return new ChefNotificationItemViewHolder(view);
        } else if (viewType == R.layout.chef_bulk_order_list_item) {
            return new ChefBulkOrderItemViewHolder(view);
        } else {
            return new CompletedorCancelledOrdersItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (objectList.get(position) instanceof UpcomingOrders) {
            UpcomingOrdersItemViewHolder upcomingOrdersItemViewHolder = (UpcomingOrdersItemViewHolder) holder;
            UpcomingOrders upcomingOrders = (UpcomingOrders) objectList.get(position);
            upcomingOrdersItemViewHolder.orderID.setTypeface(fontBold);
            upcomingOrdersItemViewHolder.acceptText.setTypeface(fontRegular);
            upcomingOrdersItemViewHolder.totalAmount.setTypeface(fontBold);
            upcomingOrdersItemViewHolder.totalAmountValue.setTypeface(fontBold);
            upcomingOrdersItemViewHolder.acceptOrderBtn.setTypeface(fontRegular);
            upcomingOrdersItemViewHolder.rejectOrderBtn.setTypeface(fontRegular);

            upcomingOrdersItemViewHolder.orderID.setText("Order No " + upcomingOrders.getOrderNo());
            upcomingOrdersItemViewHolder.totalAmountValue.setText(String.valueOf(upcomingOrders.getTotalAmount()));
            upcomingOrdersItemViewHolder.acceptText.setText(orderdateFormat(upcomingOrders.getCreateddate()));
            upcomingOrdersItemViewHolder.itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            List<Object> objectList = new ArrayList<>();
            for (int i = 0; i < upcomingOrders.getFoodItemList().size(); i++) {
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodName(upcomingOrders.getFoodItemList().get(i).getFoodName());
                foodItem.setCartAddedQuantity(upcomingOrders.getFoodItemList().get(i).getCartAddedQuantity());
                objectList.add(foodItem);
            }

            upcomingOrdersItemViewHolder.itemsRecyclerView.setAdapter(new MenuCategoryAdapter(context, objectList, fontBold, fontRegular, "Orders"));

        } else if (objectList.get(position) instanceof CurrentOrders) {
            CurrentOrdersItemViewHolder currentOrdersItemViewHolder = (CurrentOrdersItemViewHolder) holder;
            CurrentOrders currentOrders = (CurrentOrders) objectList.get(position);
            Dialog extraTimeDialoge = new Dialog(context);
            currentOrdersItemViewHolder.setIsRecyclable(false);
            currentOrdersItemViewHolder.orderID.setTypeface(fontBold);
            currentOrdersItemViewHolder.orderDateTime.setTypeface(fontRegular);
            currentOrdersItemViewHolder.timerText.setTypeface(fontRegular);
            currentOrdersItemViewHolder.timeValue.setTypeface(fontRegular);
            currentOrdersItemViewHolder.totalAmountValue.setTypeface(fontRegular);
            currentOrdersItemViewHolder.needMoreTime.setTypeface(fontRegular);
            currentOrdersItemViewHolder.extratimeText.setTypeface(fontRegular);
            currentOrdersItemViewHolder.totalAmountValue.setTypeface(fontBold);
            currentOrdersItemViewHolder.totalAmount.setTypeface(fontBold);
            currentOrdersItemViewHolder.cancelOrderBtn.setTypeface(fontBold);
            currentOrdersItemViewHolder.readyBtn.setTypeface(fontBold);


            /**
             To Show the Button Text as Start Cooking or Ready For Delivery
             **/
            if (currentOrders.getOrderStatusEnum() == 2) {
                currentOrdersItemViewHolder.readyBtn.setText("Start Cooking");
                currentOrdersItemViewHolder.timerProgressBar.setMax((int) currentOrders.getStartCookingTimeDifference());

                Animation startAnimation = AnimationUtils.loadAnimation(context, R.anim.blinking_animation);
                currentOrdersItemViewHolder.readyBtn.startAnimation(startAnimation);

                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long tempMili = currentOrders.getCookingTime() * 60000;

                long elapsedHours = tempMili / hoursInMilli;
                tempMili = tempMili % hoursInMilli;

                long elapsedMinutes = tempMili / minutesInMilli;
                tempMili = tempMili % minutesInMilli;

                long elapsedSeconds = tempMili / secondsInMilli;

                String yy = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
                currentOrdersItemViewHolder.extratimeText.setText(yy);
            } else {
                currentOrdersItemViewHolder.readyBtn.setText("Ready For Delivery");
                currentOrdersItemViewHolder.timerProgressBar.setMax((int) currentOrders.getMaxDifferences());

                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long tempMili = currentOrders.getMaxDifferences();

                long elapsedHours = tempMili / hoursInMilli;
                tempMili = tempMili % hoursInMilli;

                long elapsedMinutes = tempMili / minutesInMilli;
                tempMili = tempMili % minutesInMilli;

                long elapsedSeconds = tempMili / secondsInMilli;

                String yy = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
                currentOrdersItemViewHolder.extratimeText.setText(yy);
            }

            currentOrdersItemViewHolder.orderID.setText("Order No " + currentOrders.getOrderNo());
            currentOrdersItemViewHolder.totalAmountValue.setText(String.valueOf(currentOrders.getTotalAmount()));
            currentOrdersItemViewHolder.orderDateTime.setText(orderdateFormat(currentOrders.getCreateddate()));

            /**
             On Click Funtion for Preparation Time Need More Time
             **/

            currentOrdersItemViewHolder.needMoreTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    extraTimeDialoge.setContentView(R.layout.chef_extra_time_popup);
                    Button fivebutton = extraTimeDialoge.findViewById(R.id.fiveMinsButton);
                    Button tenbutton = extraTimeDialoge.findViewById(R.id.tenMinsButton);
                    Button fifteenbutton = extraTimeDialoge.findViewById(R.id.fifteenMinsButton);
                    fivebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateNeedMorePreparationTime(currentOrders.getOrderNoWithoutDash(), 5);
                            extraTimeDialoge.dismiss();
                        }
                    });
                    tenbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateNeedMorePreparationTime(currentOrders.getOrderNoWithoutDash(), 10);
                            extraTimeDialoge.dismiss();
                        }
                    });
                    fifteenbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateNeedMorePreparationTime(currentOrders.getOrderNoWithoutDash(), 15);
                            extraTimeDialoge.dismiss();
                        }
                    });
                    extraTimeDialoge.setCanceledOnTouchOutside(true);
                    extraTimeDialoge.show();
                }
            });

            currentOrdersItemViewHolder.itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            List<Object> objectList = new ArrayList<>();
            for (int i = 0; i < currentOrders.getFoodItemList().size(); i++) {
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodName(currentOrders.getFoodItemList().get(i).getFoodName());
                foodItem.setCartAddedQuantity(currentOrders.getFoodItemList().get(i).getCartAddedQuantity());
                objectList.add(foodItem);
            }


            currentOrdersItemViewHolder.itemsRecyclerView.setAdapter(new MenuCategoryAdapter(context, objectList, fontBold, fontRegular, "Orders"));

            /**
             To Check the Orders is in Before start Cooking or after start Cooking
             **/

            if (currentOrders.getOrderStatusEnum() == 2) {

                if (currentOrders.getEllapsedDifferences() > currentOrders.getStartCookingTimeDifference()) {
                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long tempMili = currentOrders.getCookingTime() * 60000;

                    long elapsedHours = tempMili / hoursInMilli;
                    tempMili = tempMili % hoursInMilli;

                    long elapsedMinutes = tempMili / minutesInMilli;
                    tempMili = tempMili % minutesInMilli;

                    long elapsedSeconds = tempMili / secondsInMilli;

                    String yy = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
                    currentOrdersItemViewHolder.timeValue.setText("00:00");
                    currentOrdersItemViewHolder.extratimeText.setText(yy);
                    currentOrdersItemViewHolder.timerProgressBar.setMax((int) currentOrders.getStartCookingTimeDifference());
                    currentOrdersItemViewHolder.timerProgressBar.setProgress((int) currentOrders.getEllapsedDifferences());
                    currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorNotificationBG)));
                }
                else if(currentOrders.getEllapsedDifferences()>0) {

                        cdt =new CountDownTimer(currentOrders.getStartCookingTimeDifference(),1000) {
                            @Override
                            public void onTick(long l) {
                                String addedDateTime = currentOrders.getCreateddate();
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date date = null;
                                try {
                                    date = sdf.parse(addedDateTime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                long timeDiff = System.currentTimeMillis() - date.getTime();
                                long timeDiffBeforeCooking = currentOrders.getStartCookingTimeDifference()-timeDiff;
                                String ss = String.format("%02d:%02d:%02d",
                                        TimeUnit.MILLISECONDS.toHours(timeDiffBeforeCooking),
                                        TimeUnit.MILLISECONDS.toMinutes(timeDiffBeforeCooking) -
                                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiffBeforeCooking)), // The change is in this line
                                        TimeUnit.MILLISECONDS.toSeconds(timeDiffBeforeCooking) -
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiffBeforeCooking)));
                                currentOrdersItemViewHolder.timeValue.setText(ss);
                                currentOrdersItemViewHolder.timerProgressBar.setMax((int) TimeUnit.MILLISECONDS.toSeconds(currentOrders.getStartCookingTimeDifference()));
                                currentOrdersItemViewHolder.timerProgressBar.setProgress((int) TimeUnit.MILLISECONDS.toSeconds(timeDiff));

                                if (currentOrdersItemViewHolder.timerProgressBar.getProgress() > TimeUnit.MILLISECONDS.toSeconds(currentOrders.getStartCookingTimeDifference()) / 2 && currentOrdersItemViewHolder.timerProgressBar.getProgress() <= TimeUnit.MILLISECONDS.toSeconds(currentOrders.getStartCookingTimeDifference()) * (0.8)) {
                                    currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                } else if (currentOrdersItemViewHolder.timerProgressBar.getProgress() > TimeUnit.MILLISECONDS.toSeconds(currentOrders.getStartCookingTimeDifference()) * (0.8)) {
                                    currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorNotificationBG)));
                                    if (currentOrdersItemViewHolder.timerProgressBar.getProgress() == (int) TimeUnit.MILLISECONDS.toSeconds(currentOrders.getStartCookingTimeDifference())) {
                                        try {
                                            cdt.onFinish();
                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }

                                    }
                                } else {
                                    currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorSideBarTop)));
                                }
                            }

                            @Override
                            public void onFinish() {
                                long secondsInMilli = 1000;
                                long minutesInMilli = secondsInMilli * 60;
                                long hoursInMilli = minutesInMilli * 60;
                                long tempMili = currentOrders.getCookingTime() * 60000;

                                long elapsedHours = tempMili / hoursInMilli;
                                tempMili = tempMili % hoursInMilli;

                                long elapsedMinutes = tempMili / minutesInMilli;
                                tempMili = tempMili % minutesInMilli;

                                long elapsedSeconds = tempMili / secondsInMilli;

                                String yy = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
                                currentOrdersItemViewHolder.timeValue.setText("00:00");
                                currentOrdersItemViewHolder.extratimeText.setText(yy);
                                currentOrdersItemViewHolder.timerProgressBar.setMax(100);
                                currentOrdersItemViewHolder.timerProgressBar.setProgress(100);

                                removeAt();
                                currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorNotificationBG)));

                            }
                        };
                    cdt.start();
                }

            } else {
                if (currentOrders.getEllapsedDifferences() > currentOrders.getMaxDifferences()) {

                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long tempMili = currentOrders.getPreparationTime() * 60000;

                    long elapsedHours = tempMili / hoursInMilli;
                    tempMili = tempMili % hoursInMilli;

                    long elapsedMinutes = tempMili / minutesInMilli;
                    tempMili = tempMili % minutesInMilli;

                    long elapsedSeconds = tempMili / secondsInMilli;

                    String yy = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
                    currentOrdersItemViewHolder.timeValue.setText("00:00:00");
                    currentOrdersItemViewHolder.extratimeText.setText(yy);
                    currentOrdersItemViewHolder.timerProgressBar.setMax((int) currentOrders.getMaxDifferences());
                    currentOrdersItemViewHolder.timerProgressBar.setProgress((int) currentOrders.getEllapsedDifferences());
                    currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorNotificationBG)));
                }
                else {


                    preparationcdt = new CountDownTimer(currentOrders.getMaxDifferences(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                                String addedDateTime = currentOrders.getCreateddate();
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date date = null;
                                try {
                                    date = sdf.parse(addedDateTime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                long timeDiff = System.currentTimeMillis() - date.getTime();
                                long timerCountDown =currentOrders.getMaxDifferences() -timeDiff;
                                String ss = String.format("%02d:%02d:%02d",
                                        TimeUnit.MILLISECONDS.toHours(timerCountDown),
                                        TimeUnit.MILLISECONDS.toMinutes(timerCountDown) -
                                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timerCountDown)), // The change is in this line
                                        TimeUnit.MILLISECONDS.toSeconds(timerCountDown) -
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timerCountDown)));
                                currentOrdersItemViewHolder.timeValue.setText(ss);
                                currentOrdersItemViewHolder.timerProgressBar.setMax((int) TimeUnit.MILLISECONDS.toSeconds(currentOrders.getMaxDifferences()));
                                currentOrdersItemViewHolder.timerProgressBar.setProgress((int) TimeUnit.MILLISECONDS.toSeconds(timeDiff));

                                if (currentOrdersItemViewHolder.timerProgressBar.getProgress() > TimeUnit.MILLISECONDS.toSeconds(currentOrders.getMaxDifferences()) / 2 && currentOrdersItemViewHolder.timerProgressBar.getProgress() <= TimeUnit.MILLISECONDS.toSeconds(currentOrders.getMaxDifferences()) * (0.8)) {
                                    currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                } else if (currentOrdersItemViewHolder.timerProgressBar.getProgress() > TimeUnit.MILLISECONDS.toSeconds(currentOrders.getMaxDifferences()) * (0.8)) {
                                    currentOrdersItemViewHolder.needMoreTime.setVisibility(View.VISIBLE);
                                    currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorNotificationBG)));
                                    if (currentOrdersItemViewHolder.timerProgressBar.getProgress() == TimeUnit.MILLISECONDS.toSeconds(currentOrders.getMaxDifferences())) {
                                        preparationcdt.onFinish();
                                    }
                                } else {
                                    currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorSideBarTop)));
                                }


                        }

                        @Override
                        public void onFinish() {
                            long secondsInMilli = 1000;
                            long minutesInMilli = secondsInMilli * 60;
                            long hoursInMilli = minutesInMilli * 60;
                            long tempMili = currentOrders.getPreparationTime() * 60000;

                            long elapsedHours = tempMili / hoursInMilli;
                            tempMili = tempMili % hoursInMilli;

                            long elapsedMinutes = tempMili / minutesInMilli;
                            tempMili = tempMili % minutesInMilli;

                            long elapsedSeconds = tempMili / secondsInMilli;

                            String yy = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
                            currentOrdersItemViewHolder.timeValue.setText("00:00:00");
                            currentOrdersItemViewHolder.extratimeText.setText(yy);
                            currentOrdersItemViewHolder.timerProgressBar.setMax(100);
                            currentOrdersItemViewHolder.timerProgressBar.setProgress(100);
                            currentOrdersItemViewHolder.timerProgressBar.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorNotificationBG)));
                        }
                    };
                    preparationcdt.start();
                }


            }


            currentOrdersItemViewHolder.cancelOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateCancelOrderorReadyDelivery(currentOrders.getOrderNoWithoutDash(), 100, currentOrdersItemViewHolder.getAdapterPosition());
                }
            });

            currentOrdersItemViewHolder.readyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentOrdersItemViewHolder.readyBtn.getText().toString().equals("Start Cooking")) {
                        readyForDelivery(currentOrders.getOrderNoWithoutDash(), 3, currentOrdersItemViewHolder.getAdapterPosition());
                    } else {
                        readyForDelivery(currentOrders.getOrderNoWithoutDash(), 4, currentOrdersItemViewHolder.getAdapterPosition());
                    }

                }
            });
        } else if (objectList.get(position) instanceof NotificationsLists) {
            ChefNotificationItemViewHolder chefNotificationItemViewHolder = (ChefNotificationItemViewHolder) holder;
            NotificationsLists notificationsLists = (NotificationsLists)objectList.get(position);
            chefNotificationItemViewHolder.notificationText.setTypeface(fontBold);
            chefNotificationItemViewHolder.notificationSubText.setTypeface(fontRegular);
            chefNotificationItemViewHolder.notificationText.setText(notificationsLists.getTitle());
            chefNotificationItemViewHolder.notificationSubText.setText(notificationsLists.getDescription());
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                chefNotificationItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                chefNotificationItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if (objectList.get(position) instanceof Orders) {
            CompletedorCancelledOrdersItemViewHolder completedOrdersItemViewHolder = (CompletedorCancelledOrdersItemViewHolder) holder;
            Orders orders = (Orders) objectList.get(position);

            completedOrdersItemViewHolder.orderID.setText("Order No " + orders.getOrderNo());
            completedOrdersItemViewHolder.dateHeader.setText(orderdateFormat(orders.getCreateddate()));
            completedOrdersItemViewHolder.costHeader.setText(String.valueOf(orders.getTotalAmount()));


            List<Object> objectList = new ArrayList<>();
            for (int i = 0; i < orders.getFoodItemList().size(); i++) {
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodName(orders.getFoodItemList().get(i).getFoodName());
                foodItem.setCartAddedQuantity(orders.getFoodItemList().get(i).getCartAddedQuantity());
                objectList.add(foodItem);
            }
            completedOrdersItemViewHolder.itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            completedOrdersItemViewHolder.itemsRecyclerView.setAdapter(new MenuCategoryAdapter(context, objectList, fontBold, fontRegular, "Orders"));
        } else if (objectList.get(position) instanceof OrderByMonth) {
            ChefBulkOrderItemViewHolder chefBulkOrderItemViewHolder = (ChefBulkOrderItemViewHolder) holder;
            OrderByMonth orderByMonth = (OrderByMonth) objectList.get(position);
            chefBulkOrderItemViewHolder.dateText.setTypeface(fontRegular);
            chefBulkOrderItemViewHolder.orderNumber.setTypeface(fontRegular);
            chefBulkOrderItemViewHolder.quantity.setTypeface(fontRegular);

            chefBulkOrderItemViewHolder.dateText.setText(dateFormat(orderByMonth.getDate()));
            chefBulkOrderItemViewHolder.orderNumber.setText(orderByMonth.getOrderNumber());
            chefBulkOrderItemViewHolder.quantity.setText(orderByMonth.getQuantity());
        } else {

        }
    }


    /**
     To Move orders accoring to the  Dunzo Status after timer ends
     **/
    private void removeAt() {
        objectList=new ArrayList<>();
        getOpenedOrders(2);
        getCountsForTransites();
        getOpenedOrdersForCount();
    }


    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof UpcomingOrders) {
            return R.layout.upcoming_order_list_item;
        } else if (objectList.get(position) instanceof CurrentOrders) {
            return R.layout.current_order_list_item;
        } else if (objectList.get(position) instanceof Orders) {
            return R.layout.chef_cancelled_order_list_item;
        } else if (objectList.get(position) instanceof NotificationsLists) {
            return R.layout.chef_notification_list_item;
        } else if (objectList.get(position) instanceof OrderByMonth) {
            return R.layout.chef_bulk_order_list_item;
        } else {

        }
        return super.getItemViewType(position);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ChefNotificationItemViewHolder extends RecyclerView.ViewHolder {

        ImageView notificationImage;
        TextView notificationText;
        TextView notificationSubText;

        public ChefNotificationItemViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationImage = itemView.findViewById(R.id.notificationImage);
            notificationText = itemView.findViewById(R.id.notificationText);
            notificationSubText = itemView.findViewById(R.id.notificationSubText);
        }
    }

    public class UpcomingOrdersItemViewHolder extends RecyclerView.ViewHolder {
        TextView orderID;
        TextView acceptText;
        TextView totalAmount;
        RecyclerView itemsRecyclerView;
        LinearLayout amountLayout;
        TextView totalAmountValue;
        Button rejectOrderBtn;
        Button acceptOrderBtn;

        public UpcomingOrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderID);
            acceptText = itemView.findViewById(R.id.acceptText);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            totalAmountValue = itemView.findViewById(R.id.totalAmountValue);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
            rejectOrderBtn = itemView.findViewById(R.id.rejectOrderBtn);
            acceptOrderBtn = itemView.findViewById(R.id.acceptOrderBtn);
            amountLayout = itemView.findViewById(R.id.amountLayout);
        }
    }

    public class CurrentOrdersItemViewHolder extends RecyclerView.ViewHolder {

        TextView orderID;
        TextView orderDateTime;
        TextView timerText;
        TextView timeValue;
        TextView totalAmount;
        TextView needMoreTime;
        TextView extratimeText;
        TextView totalAmountValue;
        RecyclerView itemsRecyclerView;
        ProgressBar timerProgressBar;
        Button cancelOrderBtn;
        Button readyBtn;

        public CurrentOrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderID);
            orderDateTime = itemView.findViewById(R.id.orderDateTime);
            timerText = itemView.findViewById(R.id.timerText);
            timeValue = itemView.findViewById(R.id.timeValue);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
            timerProgressBar = itemView.findViewById(R.id.timerProgressBar);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            totalAmountValue = itemView.findViewById(R.id.totalAmountValue);
            cancelOrderBtn = itemView.findViewById(R.id.cancelOrderBtn);
            readyBtn = itemView.findViewById(R.id.readyBtn);
            needMoreTime = itemView.findViewById(R.id.needMoreTime);
            extratimeText = itemView.findViewById(R.id.extratimeText);
        }
    }

    public class CompletedorCancelledOrdersItemViewHolder extends RecyclerView.ViewHolder {
        TextView orderID;
        TextView costHeader;
        TextView dateHeader;
        TextView mealHeader;
        RecyclerView itemsRecyclerView;

        public CompletedorCancelledOrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderID);
            costHeader = itemView.findViewById(R.id.costHeader);
            dateHeader = itemView.findViewById(R.id.dateHeader);
            mealHeader = itemView.findViewById(R.id.mealHeader);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
        }
    }

    public class ChefBulkOrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;
        TextView quantity;
        TextView orderNumber;

        public ChefBulkOrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            quantity = itemView.findViewById(R.id.orderQty);
            orderNumber = itemView.findViewById(R.id.orderNumber);
        }
    }
    /**
     To get Data for Notification fragment
     **/
    public List<Object> getData() {
        return objectList;
    }
    /**
     To remove the Items frm Notification fragment
     **/
    public void removeItem(int position) {
        objectList.remove(position);
        notifyItemRemoved(position);
    }
    /**
     To Convert the Date Time Format from UTC to Default
     **/
    private String dateFormat(String date) {
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("dd").format(newDate);
        return formattedDate;
    }
    /**
     To Convert the Date Time Format from UTC to Default
     **/
    private String orderdateFormat(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date serverDate = null;
        try {
            serverDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM hh:mm a");

        outputFormat.setTimeZone(TimeZone.getDefault());

        String formattedDate = "";
        try {
            formattedDate = outputFormat.format(serverDate);
        }
        catch (Exception e)
        {
            formattedDate = "";
            e.printStackTrace();
        }


        return formattedDate;
    }

    /**
     To Cancel the Order or To Make In-transit (or) Ready For Delivery using this API (PUT)
     **/
    public void updateCancelOrderorReadyDelivery(String orderId, int orderStatus, int position) {
        String url = APIBaseURL.updateOrderStatus + "" + orderId + "&" + orderStatus;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    Toast.makeText(context, "Order moved to cancelled", Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);
                    try {
                        objectList=new ArrayList<>();
                        getOpenedOrders(2);
                        getCountsForTransites();
                        getOpenedOrdersForCount();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                try {
                    objectList=new ArrayList<>();
                    getOpenedOrders(2);
                    getCountsForTransites();
                    getOpenedOrdersForCount();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_order_taq");
    }

    /**
     To  Make Ready For Delivery using this API (POST)
     **/
    public void readyForDelivery(String orderId, int orderStatus, int position) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();


        String url = APIBaseURL.readyForDelivery + "" + orderId + "/" + orderStatus;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    if (orderStatus == 3) {

                        Toast.makeText(context, "Cooking Timer Started", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(context, "Order moved to delivery in-Transit", Toast.LENGTH_SHORT).show();
                    }

                    getOpenedOrders(2);
                    getCountsForTransites();
                    getOpenedOrdersForCount();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                try {
                    if (orderStatus == 3) {

                        Toast.makeText(context, "Cooking Timer Started", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Order moved to delivery in-Transit", Toast.LENGTH_SHORT).show();
                    }

                    objectList=new ArrayList<>();
                    getOpenedOrders(2);
                    getCountsForTransites();
                    getOpenedOrdersForCount();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_order_taq_delivery");
    }
    /**
     To  Make the Order Preparation Need More Time using this API (POST)
     **/
    public void updateNeedMorePreparationTime(String orderID, int selectedTime) {
        String url = APIBaseURL.updatePreparationTime + orderID + "&" + selectedTime;
        ;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                objectList=new ArrayList<>();
                getOpenedOrders(2);
                getCountsForTransites();
                getOpenedOrdersForCount();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, context);
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "update_time_taq");
    }

    /**
     While Searching the text on Notification Page get only that searched results
     **/
    public void updateList(List<Object> list){
        objectList = list;
        notifyDataSetChanged();
    }
}
