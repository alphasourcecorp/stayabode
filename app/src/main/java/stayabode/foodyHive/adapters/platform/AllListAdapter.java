package stayabode.foodyHive.adapters.platform;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;

import stayabode.foodyHive.activities.consumers.LocationSelectionActivity;
import stayabode.foodyHive.activities.platform.MainActivity;

import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.fragments.platforms.BulkOrderDetailFragment;
import stayabode.foodyHive.fragments.platforms.ChefDetailFragment;
import stayabode.foodyHive.fragments.platforms.CloudKitchenDetailFragment;
import stayabode.foodyHive.fragments.platforms.ConsumerDetailFragment;
import stayabode.foodyHive.fragments.platforms.DeliveryPartnerDetailFragment;
import stayabode.foodyHive.fragments.platforms.FranchiseDetailFragment;
import stayabode.foodyHive.fragments.platforms.PaymentGatewayDetailFragment;
import stayabode.foodyHive.models.Chef;
import stayabode.foodyHive.models.CloudKitchen;
import stayabode.foodyHive.models.Consumer;
import stayabode.foodyHive.models.DeliveryPartner;
import stayabode.foodyHive.models.Events;
import stayabode.foodyHive.models.Franchise;
import stayabode.foodyHive.models.FranchiseeByLocation;
import stayabode.foodyHive.models.FranchiseeByRevenue;
import stayabode.foodyHive.models.NearbyCloudKitchen;
import stayabode.foodyHive.models.NotificationsLists;
import stayabode.foodyHive.models.OrderByWeek;
import stayabode.foodyHive.models.Orders;
import stayabode.foodyHive.models.PaymentGateway;
import stayabode.foodyHive.models.PromoCodes;
import stayabode.foodyHive.models.Revenues;
import stayabode.foodyHive.models.Reviews;
import stayabode.foodyHive.models.Role;
import stayabode.foodyHive.models.SearchLocation;
import stayabode.foodyHive.models.User;
import stayabode.foodyHive.models.UserContact;
import stayabode.foodyHive.models.UserEmail;
import stayabode.foodyHive.utils.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

public class AllListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    int selectedPosition = -1;

    String selectedLocationID = "";

    String selectedLocationName = "";

    public AllListAdapter(Context context, List<Object> objectList, Typeface fontBold, Typeface fontRegular) {
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

        if (viewType == R.layout.franchise_list_item) {
            return new FranchisesItemViewHolder(view);
        } else if (viewType == R.layout.cloud_kitchen_list_item) {
            return new CloudKitchenItemsViewHolder(view);
        } else if (viewType == R.layout.chef_list_item) {
            return new ChefsItemViewHolder(view);
        } else if (viewType == R.layout.consumers_list_item) {
            return new ConsumersItemViewHolder(view);
        } else if (viewType == R.layout.orders_list_item) {
            return new OrdersItemViewHolder(view);
        } else if (viewType == R.layout.delivery_partner_main_list_item) {
            return new DeliveryPartnerItemViewHolder(view);
        } else if (viewType == R.layout.payment_gateway_list_item) {
            return new PaymentGatewayItemsViewHolder(view);
        } else if (viewType == R.layout.events_list_item) {
            return new EventListItemViewHolder(view);
        } else if (viewType == R.layout.notification_list_item) {
            return new NotificationItemViewHolder(view);
        } else if (viewType == R.layout.promo_codes_list_item) {
            return new PromoCodesItemViewHolder(view);
        } else if (viewType == R.layout.reviews_list_item) {
            return new ReviewsItemViewHolder(view);
        } else if (viewType == R.layout.revenues_list_item) {
            return new RevenuesItemViewHolder(view);
        } else if (viewType == R.layout.franchisee_by_revenue_list_item) {
            return new ByRevenueItemViewHolder(view);
        } else if (viewType == R.layout.franchisee_by_location_list_item) {
            return new ByLocationItemViewHolder(view);
        } else if (viewType == R.layout.orders_by_week_list_item) {
            return new OrdersByWeekViewHolder(view);
        } else if (viewType == R.layout.users_list_item) {
            return new UsersItemViewHolder(view);
        } else if (viewType == R.layout.role_list_item) {
            return new RolesItemViewHolder(view);
        } else if (viewType == R.layout.home_nearby_cloud_kitchens_list_item) {
            return new NearByCloudKitchenItemViewHolder(view);
        } else if (viewType == R.layout.phone_list_item) {
            return new PhoneEmailItemViewHolder(view);
        }else if(viewType==R.layout.user_email_list_item){
            return new PhoneEmailItemViewHolder(view);
        }else if(viewType == R.layout.location_list_item)
        {
            return new LocationsItemViewHolder(view);
        }
        else {
            return new FranchisesItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (objectList.get(position) instanceof Franchise) {
            Franchise franchise = (Franchise) objectList.get(position);
            FranchisesItemViewHolder franchiseViewHolder = (FranchisesItemViewHolder) holder;
            franchiseViewHolder.franchiseName.setTypeface(fontBold);
            franchiseViewHolder.locationText.setTypeface(fontRegular);
            franchiseViewHolder.franchiseName.setText(franchise.getName());
            franchiseViewHolder.locationText.setText(franchise.getLocation());
            Glide.with(context).load(franchise.getImage()).placeholder(R.drawable.foodi_logo_left_image).into(franchiseViewHolder.imageView);
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                franchiseViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                franchiseViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }

            if (franchise.getStatus()) {
                franchiseViewHolder.statusImage.setImageResource(R.drawable.franchise_in_image);
            } else {
                franchiseViewHolder.statusImage.setImageResource(R.drawable.franchise_out_image);
            }


            franchiseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    navigation.setVisibility(View.GONE);
                    FranchiseDetailFragment fragment = new FranchiseDetailFragment();
                    Bundle intent = new Bundle();
                    intent.putString("Name", franchise.getName());
                    intent.putString("Id", franchise.getId());
                    intent.putString("Location", franchise.getLocation());
                    intent.putString("AddressLine1", franchise.getAddressLine1());
                    intent.putString("AddressLine2", franchise.getAddressLine2());
                    intent.putString("PinCode", franchise.getPinCode());
                    intent.putString("State", franchise.getState());
                    intent.putString("Country", franchise.getCountry());
                    intent.putString("StartDate", franchise.getStartDate());
                    intent.putString("Contact", franchise.getContact());
                    intent.putString("Email", franchise.getEmail());
                    intent.putString("UploadLogo", franchise.getUploadLogo());
                    intent.putString("GSTNumber", franchise.getGstNumber());
                    intent.putString("PANNumber", franchise.getPanNumber());
                    intent.putString("NoOfChefs", franchise.getNumberOfChefs());
                    intent.putString("NoOfCloudKitchen", franchise.getNumberOfCloudKitchens());
                    intent.putBoolean("Status", franchise.getStatus());
                    intent.putString("BankName", franchise.getBankName());
                    intent.putString("BankBranchName", franchise.getBankbranchName());
                    intent.putString("Account", franchise.getAccountNumber());
                    intent.putString("IFSCCode", franchise.getIfscCode());
                    intent.putString("Certificates", franchise.getCertificates());
                    intent.putString("FssaiNumber", franchise.getFssaiNumber());
                    intent.putString("pricingID", franchise.getPricingModelID());
                    intent.putString("pricingAmount", franchise.getPricingAmount());
                    intent.putString("pricingTransaction", franchise.getPricingTransactions());
                    intent.putString("pricingTransactionPercentage", franchise.getPricingTransactionPercentage());
                    intent.putString("pricingName", franchise.getPricing());
                    intent.putString("pricingGSTExtra", franchise.getPricinggstExtra());
                    fragment.setArguments(intent);
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();


//                    Intent intent = new Intent(context, FranchiseProfileDetailActivity.class);


//                    context.startActivity(intent);
                }
            });
        } else if (objectList.get(position) instanceof CloudKitchen) {
            CloudKitchenItemsViewHolder cloudKitchenItemViewHolder = (CloudKitchenItemsViewHolder) holder;
            CloudKitchen cloudKitchen = (CloudKitchen) objectList.get(position);
            cloudKitchenItemViewHolder.cloudKitchenName.setTypeface(fontBold);
            cloudKitchenItemViewHolder.locationText.setTypeface(fontRegular);
            cloudKitchenItemViewHolder.franchiseeName.setTypeface(fontRegular);
            cloudKitchenItemViewHolder.cloudKitchenName.setText(cloudKitchen.getName());
            cloudKitchenItemViewHolder.franchiseeName.setText(cloudKitchen.getFranchiseName());
            cloudKitchenItemViewHolder.locationText.setText(cloudKitchen.getLocation());
            Glide.with(context).load(cloudKitchen.getImage()).placeholder(R.drawable.foodi_logo_left_image).into(cloudKitchenItemViewHolder.imageView);
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                cloudKitchenItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                cloudKitchenItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }

            cloudKitchenItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, CloudKitchenProfileDetailActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
                    CloudKitchenDetailFragment fragment = new CloudKitchenDetailFragment();
                    Bundle intent = new Bundle();
                    intent.putString("Name", cloudKitchen.getName());
                    intent.putString("Id", cloudKitchen.getId());
                    intent.putString("Location", cloudKitchen.getLocation());
                    intent.putString("AddressLine1", cloudKitchen.getAddressLine1());
                    intent.putString("AddressLine2", cloudKitchen.getAddressLine2());
                    intent.putString("PinCode", cloudKitchen.getPinCode());
                    intent.putString("State", cloudKitchen.getState());
                    intent.putString("Country", cloudKitchen.getCountry());
                    intent.putString("StartDate", cloudKitchen.getStartDate());
                    intent.putString("Contact", cloudKitchen.getContact());
                    intent.putString("Email", cloudKitchen.getEmail());
                    //intent.putString("UploadLogo",cloudKitchen.getUploadLogo());
                    intent.putString("GSTNumber", cloudKitchen.getGstNumber());
                    intent.putString("PANNumber", cloudKitchen.getPanNumber());
                    intent.putString("NoOfChefs", cloudKitchen.getNoOfChefs());
                    // intent.putString("NoOfCloudKitchen",cloudKitchen.getNumberOfCloudKitchens());
                    intent.putBoolean("Status", cloudKitchen.getStatus());
                    intent.putString("BankName", cloudKitchen.getBankName());
                    intent.putString("BankBranchName", cloudKitchen.getBankBranchName());
                    intent.putString("Account", cloudKitchen.getAccountNumber());
                    intent.putString("IFSCCode", cloudKitchen.getIfscCode());
                    intent.putString("Certificates", cloudKitchen.getCertificates());
                    intent.putString("FssaiNumber", cloudKitchen.getFssaiNumber());
                    // intent.putString("PricingModel",cloudKitchen.getChoosePricingModel());
                    fragment.setArguments(intent);
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });
        } else if (objectList.get(position) instanceof NearbyCloudKitchen) {
            NearByCloudKitchenItemViewHolder nearByCloudKitchenItemViewHolder = (NearByCloudKitchenItemViewHolder) holder;
            nearByCloudKitchenItemViewHolder.locationHeader.setTypeface(fontRegular);
            nearByCloudKitchenItemViewHolder.cloudKitchenHeader.setTypeface(fontBold);
            NearbyCloudKitchen nearByCloudKitchenList = (NearbyCloudKitchen)objectList.get(position);
            nearByCloudKitchenItemViewHolder.cloudKitchenHeader.setText(nearByCloudKitchenList.getName());
            nearByCloudKitchenItemViewHolder.locationHeader.setText(nearByCloudKitchenList.getLocation());

            Log.d("CloudName",nearByCloudKitchenList.getName() + " Check");
            Log.d("CloudLocation",nearByCloudKitchenList.getLocation() + " Check");

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                nearByCloudKitchenItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                nearByCloudKitchenItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if (objectList.get(position) instanceof Chef) {
            ChefsItemViewHolder chefListItemViewHolder = (ChefsItemViewHolder) holder;
            Chef chef = (Chef) objectList.get(position);
            chefListItemViewHolder.locationText.setTypeface(fontRegular);
            chefListItemViewHolder.chefName.setTypeface(fontBold);
            chefListItemViewHolder.chefName.setText(chef.getName());
            chefListItemViewHolder.locationText.setText(chef.getLocation());

            Glide.with(context).load(chef.getImage()).placeholder(R.drawable.foodi_logo_left_image).into(chefListItemViewHolder.imageView);

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                chefListItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                chefListItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }

            if(chef.getStatus().equals("Blocked"))
            {
                chefListItemViewHolder.chefName.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            }
            else
            {
                chefListItemViewHolder.chefName.setTextColor(context.getResources().getColor(android.R.color.black));
            }
            chefListItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, ChefProfileDetailsActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
                    ChefDetailFragment fragment = new ChefDetailFragment();
                    Bundle intent = new Bundle();
                    intent.putString("Name", chef.getName());
                    intent.putString("Id", chef.getId());
                    intent.putString("Location", chef.getLocation());
                    intent.putString("AddressLine1", chef.getAddressLine1());
                    intent.putString("AddressLine2", chef.getAddressLine2());
                    intent.putString("PinCode", chef.getPinCode());
                    intent.putString("State", chef.getState());
                    intent.putString("Country", chef.getCountry());
                    intent.putString("StartDate", chef.getStartDate());
                    intent.putString("Contact", chef.getContact());
                    intent.putString("Email", chef.getEmail());
                   // intent.putString("UploadLogo",chef.getImage());
                    //intent.putString("UploadLogo",cloudKitchen.getUploadLogo());
                    intent.putString("GSTNumber", chef.getGstNumber());
                    intent.putString("PANNumber", chef.getPanNumber());
                    // intent.putString("NoOfChefs",chef.getNoOfChefs());
                    // intent.putString("NoOfCloudKitchen",cloudKitchen.getNumberOfCloudKitchens());
                    intent.putString("Status", chef.getStatus());
                    intent.putString("BankName", chef.getBankName());
                    intent.putString("BankBranchName", chef.getBankBranchName());
                    intent.putString("Account", chef.getAccountNumber());
                    intent.putString("IFSCCode", chef.getIfscCode());
                    intent.putString("Certificates", chef.getCertificates());
                    intent.putString("FssaiNumber", chef.getFssaiNumber());
                    // intent.putString("PricingModel",cloudKitchen.getChoosePricingModel());
                    fragment.setArguments(intent);
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });
        } else if (objectList.get(position) instanceof Consumer) {
            ConsumersItemViewHolder consumerItemViewHolder = (ConsumersItemViewHolder) holder;
            Consumer consumer = (Consumer) objectList.get(position);
            consumerItemViewHolder.consumerName.setTypeface(fontBold);
            consumerItemViewHolder.locationText.setTypeface(fontRegular);
            consumerItemViewHolder.subscriptionStatus.setTypeface(fontRegular);
            consumerItemViewHolder.consumerName.setText(consumer.getName());
            consumerItemViewHolder.locationText.setText(consumer.getLocation());

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                consumerItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                consumerItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
            consumerItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, ConsumerProfileDetailsActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
                    ConsumerDetailFragment fragment = new ConsumerDetailFragment();
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });
        } else if (objectList.get(position) instanceof User) {
            UsersItemViewHolder usersItemViewHolder = (UsersItemViewHolder) holder;
            User user = (User) objectList.get(position);
            usersItemViewHolder.userName.setTypeface(fontBold);
            usersItemViewHolder.createdDateText.setTypeface(fontRegular);
            usersItemViewHolder.activeStatus.setTypeface(fontRegular);
            usersItemViewHolder.userName.setText(user.getUserName());
            usersItemViewHolder.createdDateText.setText(user.getCreatedDate());
            usersItemViewHolder.activeStatus.setText(user.getActiveStatus());

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                usersItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                usersItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
            Glide.with(context).load(user.getImage()).placeholder(R.drawable.foodi_logo_left_image).into(usersItemViewHolder.imageView);
        } else if (objectList.get(position) instanceof UserContact) {
            PhoneEmailItemViewHolder phoneEmailItemViewHolder=(PhoneEmailItemViewHolder) holder;
            UserContact userContact =(UserContact) objectList.get(position);
            phoneEmailItemViewHolder.contactInfo.setTypeface(fontRegular);
            phoneEmailItemViewHolder.contactInfo.setText(userContact.getContactNumber());
            Log.d("ContactNumbers",userContact.getContactNumber() + " Numbers");
        }

        else if(objectList.get(position) instanceof UserEmail){
            PhoneEmailItemViewHolder phoneEmailItemViewHolder=(PhoneEmailItemViewHolder) holder;
            UserEmail userEmail=(UserEmail) objectList.get(position);
            phoneEmailItemViewHolder.contactInfo.setTypeface(fontRegular);
            phoneEmailItemViewHolder.contactInfo.setText(userEmail.getEmail());
            Log.d("EmailIds",userEmail.getEmail()+"Emails");
        }

        else if (objectList.get(position) instanceof Role) {
            RolesItemViewHolder rolesItemViewHolder = (RolesItemViewHolder) holder;
            Role role = (Role) objectList.get(position);
            rolesItemViewHolder.roleName.setTypeface(fontBold);
            rolesItemViewHolder.createdDateText.setTypeface(fontRegular);
            rolesItemViewHolder.activeStatus.setTypeface(fontRegular);
            rolesItemViewHolder.roleName.setText(role.getRoleName());
            rolesItemViewHolder.createdDateText.setText(role.getCreatedDate());
            rolesItemViewHolder.activeStatus.setText(role.getActiveStatus());

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                rolesItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                rolesItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if (objectList.get(position) instanceof DeliveryPartner) {
            DeliveryPartnerItemViewHolder deliveryPartnersItemViewHolder = (DeliveryPartnerItemViewHolder) holder;
            DeliveryPartner deliveryPartner = (DeliveryPartner) objectList.get(position);
            deliveryPartnersItemViewHolder.deliveryPartnerName.setTypeface(fontBold);
            deliveryPartnersItemViewHolder.coverageArea.setTypeface(fontRegular);
            deliveryPartnersItemViewHolder.responseTime.setTypeface(fontRegular);
            deliveryPartnersItemViewHolder.deliveryPartnerName.setText(deliveryPartner.getName());
            deliveryPartnersItemViewHolder.coverageArea.setText(deliveryPartner.getCoverageArea());
            deliveryPartnersItemViewHolder.responseTime.setText(deliveryPartner.getDistanceTime());
            Glide.with(context).load(deliveryPartner.getUploadLogo()).placeholder(R.drawable.foodi_logo_left_image).into(deliveryPartnersItemViewHolder.imageView);
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                deliveryPartnersItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                deliveryPartnersItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }


            if(deliveryPartner.getStatus().equals("Blocked"))
            {
                deliveryPartnersItemViewHolder.deliveryPartnerName.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            }
            else
            {
                deliveryPartnersItemViewHolder.deliveryPartnerName.setTextColor(context.getResources().getColor(android.R.color.black));
            }

            deliveryPartnersItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, DeliveryPartnerProfileDetailActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
                    DeliveryPartnerDetailFragment fragment = new DeliveryPartnerDetailFragment();
                    Bundle intent = new Bundle();
                    intent.putString("Id", deliveryPartner.getId());
                    intent.putString("uploadLogo",deliveryPartner.getUploadLogo());
                    // intent.putString("PricingModel",cloudKitchen.getChoosePricingModel());
                    fragment.setArguments(intent);
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });
        } else if (objectList.get(position) instanceof PaymentGateway) {
            PaymentGatewayItemsViewHolder paymentGatewayItemViewHolder = (PaymentGatewayItemsViewHolder) holder;
            PaymentGateway paymentGateway = (PaymentGateway) objectList.get(position);
            paymentGatewayItemViewHolder.franchiseDetails.setTypeface(fontRegular);
            paymentGatewayItemViewHolder.paymentGateway.setTypeface(fontBold);
            paymentGatewayItemViewHolder.transactionFee.setTypeface(fontRegular);

            paymentGatewayItemViewHolder.franchiseDetails.setText(paymentGateway.getFranchiseName());
            paymentGatewayItemViewHolder.paymentGateway.setText(paymentGateway.getTitle());
            paymentGatewayItemViewHolder.transactionFee.setText(paymentGateway.getTransaction());
            Glide.with(context).load(paymentGateway.getImage()).placeholder(R.drawable.foodi_logo_left_image).into(paymentGatewayItemViewHolder.imageView);
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                paymentGatewayItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                paymentGatewayItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }

            paymentGatewayItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, PaymentGatewayProfileDetailsActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
                    PaymentGatewayDetailFragment fragment = new PaymentGatewayDetailFragment();
                    Bundle intent = new Bundle();
                    intent.putString("Name", paymentGateway.getTitle());
                    intent.putString("Id", paymentGateway.getId());
                    intent.putString("uploadLogo",paymentGateway.getImage());
                    fragment.setArguments(intent);
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });
        } else if (objectList.get(position) instanceof Events) {
            EventListItemViewHolder eventListItemViewHolder = (EventListItemViewHolder) holder;
            eventListItemViewHolder.eventTitle.setTypeface(fontBold);
            eventListItemViewHolder.description.setTypeface(fontRegular);

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                eventListItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                eventListItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if (objectList.get(position) instanceof NotificationsLists) {

            NotificationItemViewHolder norificationViewHolder = (NotificationItemViewHolder) holder;
            NotificationsLists notification = (NotificationsLists) objectList.get(position);
            norificationViewHolder.description.setTypeface(fontRegular);
            norificationViewHolder.notificationTitle.setTypeface(fontBold);
            norificationViewHolder.description.setText(notification.getDescription());
            norificationViewHolder.notificationTitle.setText(notification.getTitle());
            Log.d("Notification", notification.getTitle());
            Log.d("Notification", notification.getTitle());

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                norificationViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                norificationViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }


            if(notification.getStatus().equals("Medium"))
            {
                norificationViewHolder.statusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.notification_priority_medium_image));
            }
            else if(notification.getStatus().equals("High Priority"))
            {
                norificationViewHolder.statusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.notification_priority_low_image));
            }
            else if(notification.getStatus().equals("Low priority"))
            {
                norificationViewHolder.statusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.notification_priority_high_image));
            }

        } else if (objectList.get(position) instanceof Orders) {
            OrdersItemViewHolder ordersItemViewHolder = (OrdersItemViewHolder) holder;
            ordersItemViewHolder.orderNo.setTypeface(fontRegular);
            ordersItemViewHolder.date.setTypeface(fontRegular);
            ordersItemViewHolder.franchiseeName.setTypeface(fontRegular);
            ordersItemViewHolder.orderQty.setTypeface(fontRegular);

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                ordersItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                ordersItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }

            ordersItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, BulkOrderProfileDetailActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
                    BulkOrderDetailFragment fragment = new BulkOrderDetailFragment();
                    FragmentManager fm = MainActivity.fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, fragment).addToBackStack(null);
                    //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
            });

        } else if (objectList.get(position) instanceof PromoCodes) {
            PromoCodesItemViewHolder promoCodesItemViewHolder = (PromoCodesItemViewHolder) holder;
            promoCodesItemViewHolder.promoCodes.setTypeface(fontRegular);
            promoCodesItemViewHolder.status.setTypeface(fontRegular);

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                promoCodesItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                promoCodesItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if (objectList.get(position) instanceof Reviews) {
            ReviewsItemViewHolder reviewsItemViewHolder = (ReviewsItemViewHolder) holder;
            reviewsItemViewHolder.description.setTypeface(fontRegular);

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                reviewsItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                reviewsItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if (objectList.get(position) instanceof Revenues) {
            RevenuesItemViewHolder revenuesItemViewHolder = (RevenuesItemViewHolder) holder;

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                revenuesItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                revenuesItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }

            Revenues revenues = (Revenues) objectList.get(position);
            revenuesItemViewHolder.monthText.setText(revenues.getMonthName());
            revenuesItemViewHolder.amountLastYear.setText(revenues.getYearAmount());
            revenuesItemViewHolder.amountPresentYear.setText(revenues.getTwentyYearAmount());
            revenuesItemViewHolder.monthText.setTypeface(fontRegular);
            revenuesItemViewHolder.amountLastYear.setTypeface(fontRegular);
            revenuesItemViewHolder.amountPresentYear.setTypeface(fontRegular);
        } else if (objectList.get(position) instanceof OrderByWeek) {
            OrdersByWeekViewHolder ordersByWeekViewHolder = (OrdersByWeekViewHolder) holder;
            OrderByWeek orderByWeek = (OrderByWeek) objectList.get(position);
            ordersByWeekViewHolder.weekText.setTypeface(fontRegular);
            ordersByWeekViewHolder.bulkText.setTypeface(fontRegular);
            ordersByWeekViewHolder.subscriptionsText.setTypeface(fontRegular);
            ordersByWeekViewHolder.retailText.setTypeface(fontRegular);

            ordersByWeekViewHolder.weekText.setText(orderByWeek.getDay());
            ordersByWeekViewHolder.bulkText.setText(orderByWeek.getBulkOrderCount());
            ordersByWeekViewHolder.subscriptionsText.setText(orderByWeek.getSubscription());
            ordersByWeekViewHolder.retailText.setText(orderByWeek.getRetail());
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                ordersByWeekViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                ordersByWeekViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if (objectList.get(position) instanceof FranchiseeByRevenue) {
            ByRevenueItemViewHolder byRevenueItemViewHolder = (ByRevenueItemViewHolder) holder;
            FranchiseeByRevenue franchiseeByRevenue = (FranchiseeByRevenue) objectList.get(position);
            byRevenueItemViewHolder.franchiseeName.setTypeface(fontRegular);
            byRevenueItemViewHolder.amount.setTypeface(fontRegular);
            byRevenueItemViewHolder.contribution.setTypeface(fontRegular);

            byRevenueItemViewHolder.contribution.setText(franchiseeByRevenue.getContribution());
            byRevenueItemViewHolder.franchiseeName.setText(franchiseeByRevenue.getName());
            byRevenueItemViewHolder.amount.setText(franchiseeByRevenue.getAmount());

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                byRevenueItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                byRevenueItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if (objectList.get(position) instanceof FranchiseeByLocation) {
            ByLocationItemViewHolder byLocationItemViewHolder = (ByLocationItemViewHolder) holder;
            FranchiseeByLocation franchiseeByLocation = (FranchiseeByLocation) objectList.get(position);
            byLocationItemViewHolder.franchiseeName.setTypeface(fontRegular);
            byLocationItemViewHolder.amount.setTypeface(fontRegular);
            byLocationItemViewHolder.contribution.setTypeface(fontRegular);

            byLocationItemViewHolder.contribution.setText(franchiseeByLocation.getContribution());
            byLocationItemViewHolder.franchiseeName.setText(franchiseeByLocation.getName());
            byLocationItemViewHolder.amount.setText(franchiseeByLocation.getAmount());

            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                byLocationItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#F2F4F3"));
            } else {
                byLocationItemViewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                // Set the background color for alternate row/item
            }
        } else if(objectList.get(position) instanceof SearchLocation)
        {
            LocationsItemViewHolder locationsItemViewHolder = (LocationsItemViewHolder)holder;
            SearchLocation searchLocation = (SearchLocation)objectList.get(position);
            locationsItemViewHolder.placeName.setTypeface(fontRegular);
            locationsItemViewHolder.placeName.setText(searchLocation.getName());

//            locationsItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Kammanahalli, Bengaluru, Karnataka, India
////                    latitude: 13.0159044,
////                            longitude: 77.6378619
//
//                    if(searchLocation.getId().equals(1))
//                    {
//                        SaveSharedPreference.saveLatLong(context,"13.0159044","77.6378619");
//                        SaveSharedPreference.saveAddress(context,"Kammanahalli, Bengaluru, Karnataka, India");
//                        Intent intent = new Intent(context, ConsumerMainActivity.class);
//                        ((LocationSelectionActivity)context).startActivity(intent);
//                        ((LocationSelectionActivity)context).finish();
//                    }
//                    else
//                    {
//                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//                        builder1.setMessage("Sorry! We currently do not service in this area");
//                        builder1.setCancelable(true);
//
//                        builder1.setPositiveButton(
//                                "Okay",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//
//                        AlertDialog alert11 = builder1.create();
//                        alert11.show();
//                    }
//
//
//                }
//            });

//           if(searchLocation.getId().equals(1)){
//               locationsItemViewHolder.radioButton.setEnabled(true);
//           }else
//               locationsItemViewHolder.radioButton.setEnabled(false);
//           if(searchLocation.getName().equals("Kammanahalli")){
//               locationsItemViewHolder.radioButton.setEnabled(true);
//               locationsItemViewHolder.radioButton.setChecked(true);
//           }else{
//               locationsItemViewHolder.radioButton.setEnabled(false);
//               locationsItemViewHolder.placeName.setTextColor(context.getResources().getColor(R.color.colorSkeleton));
//           }

//            if(position == 0)
//            {
//                selectedLocationName = searchLocation.getName();
//                selectedLocationID = searchLocation.getId();
//                locationsItemViewHolder.radioButton.setChecked(true);
//                searchText.setText(selectedLocationName + ",Bangalore,Karnataka,india");
//            }
//            else
//            {
//                selectedLocationID = "";
//                selectedLocationName = "";
//                locationsItemViewHolder.radioButton.setChecked(false);
//                searchText.setText("");
//            }

            if(position == selectedPosition)
            {
                selectedLocationName = searchLocation.getName();
                selectedLocationID = searchLocation.getId();
                locationsItemViewHolder.radioButton.setChecked(true);

            }
            else
            {
                selectedLocationID = "";
                selectedLocationName = "";
                locationsItemViewHolder.radioButton.setChecked(false);

            }

            locationsItemViewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // searchText.setText(searchLocation.getName());
                    selectedPosition = position;
                    notifyDataSetChanged();
                    if(searchLocation.getName().equals("Kammanahalli"))
                    {
                        SaveSharedPreference.saveLatLong(context,"13.0159044","77.6378619");
                        SaveSharedPreference.saveAddress(context,"Kammanahalli, Bengaluru, Karnataka, India");
                        Intent intent = new Intent(context, ConsumerMainActivity.class);
                        ((LocationSelectionActivity)context).startActivity(intent);
                        ((LocationSelectionActivity)context).finish();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setMessage("Sorry! We currently do not service in this area");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            });

            locationsItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedPosition = position;
//                    searchText.setText(searchLocation.getName()+ ",Bangalore,Karnataka,india");
                    notifyDataSetChanged();
                    if(searchLocation.getName().equals("Kammanahalli"))
                    {
                        SaveSharedPreference.saveLatLong(context,"13.0159044","77.6378619");
                        SaveSharedPreference.saveAddress(context,"Kammanahalli, Bengaluru, Karnataka, India");
                        Intent intent = new Intent(context, ConsumerMainActivity.class);
                        ((LocationSelectionActivity)context).startActivity(intent);
                        ((LocationSelectionActivity)context).finish();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setMessage("Sorry! We currently do not service in this area");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            });
//
//            locationsItemViewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    selectedPosition = position;
////                    searchText.setText(searchLocation.getName()+ ",Bangalore,Karnataka,india");
//                    notifyDataSetChanged();
//                }
//            });


        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof Franchise) {
            return R.layout.franchise_list_item;
        } else if (objectList.get(position) instanceof CloudKitchen) {
            return R.layout.cloud_kitchen_list_item;
        } else if (objectList.get(position) instanceof NearbyCloudKitchen) {
            return R.layout.home_nearby_cloud_kitchens_list_item;
        } else if (objectList.get(position) instanceof Chef) {
            return R.layout.chef_list_item;
        } else if (objectList.get(position) instanceof Consumer) {
            return R.layout.consumers_list_item;
        } else if (objectList.get(position) instanceof Orders) {
            return R.layout.orders_list_item;
        } else if (objectList.get(position) instanceof FranchiseeByRevenue) {
            return R.layout.franchisee_by_revenue_list_item;
        } else if (objectList.get(position) instanceof FranchiseeByLocation) {
            return R.layout.franchisee_by_location_list_item;
        }
        else if(objectList.get(position) instanceof UserContact){
            return R.layout.phone_list_item;
        }
        else if(objectList.get(position) instanceof UserEmail){
            return  R.layout.user_email_list_item;
        }

//        else if(objectList.get(position) instanceof Subscriptions)
//        {
//            return R.layout.orders_list_item;
//        }

        else if (objectList.get(position) instanceof User) {
            return R.layout.users_list_item;
        } else if (objectList.get(position) instanceof Role) {
            return R.layout.role_list_item;
        } else if (objectList.get(position) instanceof DeliveryPartner) {
            return R.layout.delivery_partner_main_list_item;
        } else if (objectList.get(position) instanceof PaymentGateway) {
            return R.layout.payment_gateway_list_item;
        } else if (objectList.get(position) instanceof Events) {
            return R.layout.events_list_item;
        } else if (objectList.get(position) instanceof NotificationsLists) {
            return R.layout.notification_list_item;
        } else if (objectList.get(position) instanceof PromoCodes) {
            return R.layout.promo_codes_list_item;
        } else if (objectList.get(position) instanceof Reviews) {
            return R.layout.reviews_list_item;
        } else if (objectList.get(position) instanceof Revenues) {
            return R.layout.revenues_list_item;
        } else if (objectList.get(position) instanceof OrderByWeek) {
            return R.layout.orders_by_week_list_item;
        } else if(objectList.get(position) instanceof SearchLocation)
        {
            return R.layout.location_list_item;
        }
        else {

        }
        return super.getItemViewType(position);

    }

    public class ChefsItemViewHolder extends RecyclerView.ViewHolder {
        TextView chefName;
        TextView locationText;
        RatingBar ratingBar;
        ImageView imageView;

        public ChefsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            chefName = itemView.findViewById(R.id.chefName);
            locationText = itemView.findViewById(R.id.locationText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public class NearByCloudKitchenItemViewHolder extends RecyclerView.ViewHolder {

        TextView cloudKitchenHeader;
        TextView locationHeader;

        public NearByCloudKitchenItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cloudKitchenHeader = itemView.findViewById(R.id.cloudKitchenHeader);
            locationHeader = itemView.findViewById(R.id.locationHeader);
        }
    }

    public class CloudKitchenItemsViewHolder extends RecyclerView.ViewHolder {

        TextView cloudKitchenName;
        TextView locationText;
        TextView franchiseeName;
        ImageView imageView;

        public CloudKitchenItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            cloudKitchenName = itemView.findViewById(R.id.cloudKitchenName);
            locationText = itemView.findViewById(R.id.locationText);
            franchiseeName = itemView.findViewById(R.id.franchiseeName);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public class ConsumersItemViewHolder extends RecyclerView.ViewHolder {

        TextView consumerName;
        TextView locationText;
        TextView subscriptionStatus;

        public ConsumersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            consumerName = itemView.findViewById(R.id.consumerName);
            locationText = itemView.findViewById(R.id.locationText);
            subscriptionStatus = itemView.findViewById(R.id.subscriptionStatus);
        }
    }


    public class UsersItemViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView createdDateText;
        TextView activeStatus;
        ImageView imageView;

        public UsersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            createdDateText = itemView.findViewById(R.id.createdDateText);
            activeStatus = itemView.findViewById(R.id.activeStatus);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public class RolesItemViewHolder extends RecyclerView.ViewHolder {

        TextView roleName;
        TextView createdDateText;
        TextView activeStatus;

        public RolesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            roleName = itemView.findViewById(R.id.roleName);
            createdDateText = itemView.findViewById(R.id.createdDateText);
            activeStatus = itemView.findViewById(R.id.activeStatus);
        }
    }

    public class PaymentGatewayItemsViewHolder extends RecyclerView.ViewHolder {
        TextView paymentGateway;
        TextView transactionFee;
        TextView franchiseDetails;
        ImageView imageView;

        public PaymentGatewayItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentGateway = itemView.findViewById(R.id.paymentGateway);
            transactionFee = itemView.findViewById(R.id.transactionFee);
            franchiseDetails = itemView.findViewById(R.id.franchiseDetails);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public class OrdersItemViewHolder extends RecyclerView.ViewHolder {
        TextView orderNo;
        TextView franchiseeName;
        TextView orderQty;
        TextView date;

        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNo = itemView.findViewById(R.id.orderNo);
            franchiseeName = itemView.findViewById(R.id.franchiseeName);
            orderQty = itemView.findViewById(R.id.orderQty);
            date = itemView.findViewById(R.id.date);
        }
    }

    public class BulkOrderItemsViewHolder extends RecyclerView.ViewHolder {

        public BulkOrderItemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FranchisesItemViewHolder extends RecyclerView.ViewHolder {
        TextView franchiseName;
        TextView locationText;
        ImageView statusImage;
        ImageView imageView;


        public FranchisesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            franchiseName = itemView.findViewById(R.id.franchiseName);
            locationText = itemView.findViewById(R.id.locationText);
            statusImage = itemView.findViewById(R.id.statusImage);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public class DeliveryPartnerItemViewHolder extends RecyclerView.ViewHolder {
        TextView deliveryPartnerName;
        TextView responseTime;
        TextView coverageArea;
        ImageView imageView;

        public DeliveryPartnerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            deliveryPartnerName = itemView.findViewById(R.id.deliveryPartnerName);
            responseTime = itemView.findViewById(R.id.responseTime);
            coverageArea = itemView.findViewById(R.id.coverageArea);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public class NotificationItemViewHolder extends RecyclerView.ViewHolder {
        TextView notificationTitle;
        TextView description;
        ImageView statusImage;

        public NotificationItemViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            description = itemView.findViewById(R.id.description);
            statusImage = itemView.findViewById(R.id.statusImage);
        }
    }

    public class EventListItemViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView description;

        public EventListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            description = itemView.findViewById(R.id.description);
        }
    }

    public class PromoCodesItemViewHolder extends RecyclerView.ViewHolder {
        TextView promoCodes;
        TextView status;

        public PromoCodesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            promoCodes = itemView.findViewById(R.id.promoCodes);
            status = itemView.findViewById(R.id.status);
        }
    }

    public class ReviewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView description;

        public ReviewsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
        }
    }

    public class RevenuesItemViewHolder extends RecyclerView.ViewHolder {
        TextView monthText;
        TextView amountLastYear;
        TextView amountPresentYear;

        public RevenuesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            monthText = itemView.findViewById(R.id.monthText);
            amountLastYear = itemView.findViewById(R.id.amountLastYear);
            amountPresentYear = itemView.findViewById(R.id.amountPresentYear);
        }
    }

    public class OrdersByWeekViewHolder extends RecyclerView.ViewHolder {

        TextView weekText;
        TextView bulkText;
        TextView subscriptionsText;
        TextView retailText;

        public OrdersByWeekViewHolder(@NonNull View itemView) {
            super(itemView);
            weekText = itemView.findViewById(R.id.weekText);
            bulkText = itemView.findViewById(R.id.bulkText);
            subscriptionsText = itemView.findViewById(R.id.subscriptionsText);
            retailText = itemView.findViewById(R.id.retailText);
        }
    }

    public class ByRevenueItemViewHolder extends RecyclerView.ViewHolder {

        TextView franchiseeName;
        TextView amount;
        TextView contribution;

        public ByRevenueItemViewHolder(@NonNull View itemView) {
            super(itemView);
            franchiseeName = itemView.findViewById(R.id.franchiseeName);
            amount = itemView.findViewById(R.id.amount);
            contribution = itemView.findViewById(R.id.contribution);
        }
    }

    public class ByLocationItemViewHolder extends RecyclerView.ViewHolder {

        TextView franchiseeName;
        TextView amount;
        TextView contribution;

        public ByLocationItemViewHolder(@NonNull View itemView) {
            super(itemView);
            franchiseeName = itemView.findViewById(R.id.franchiseeName);
            amount = itemView.findViewById(R.id.amount);
            contribution = itemView.findViewById(R.id.contribution);
        }
    }

    public class PhoneEmailItemViewHolder extends RecyclerView.ViewHolder {
        TextView contactInfo;

        public PhoneEmailItemViewHolder(@NonNull View itemView) {
            super(itemView);
            contactInfo = itemView.findViewById(R.id.contactInfo);
        }
    }


    public class LocationsItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView placeName;
        RadioButton radioButton;

        public LocationsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }

    public void updateList(List<Object> list){
        objectList = list;
        notifyDataSetChanged();
    }
}
