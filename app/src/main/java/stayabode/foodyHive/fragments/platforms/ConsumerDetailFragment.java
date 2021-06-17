package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.ConsumerFavouritesListAdapter;
import stayabode.foodyHive.models.ConsumerFavourites;

import java.util.ArrayList;
import java.util.List;

public class ConsumerDetailFragment extends Fragment {

    ImageView profileImage;
    TextView consumerName;
    TextView city;
    TextView totalOrdersHeader;
    TextView subscriptionsHeader;
    TextView ordersCount;
    TextView subscriptionsCount;
    TextView addressheader;
    TextView homeTitle;
    TextView workTitle;
    TextView workAddress;
    TextView homeAddress;
    TextView paymentChoiceheader;
    TextView paymentSavedCard;
    TextView paymentCardnumber;
    TextView paymentCardBrand;
    TextView bankDetailsheader;
    TextView savedbankCard;
    TextView bankcardNumber;
    TextView bankcardBrand;
    TextView favouritesheader;
    TextView kitchenName;
    TextView location;
    RecyclerView favouritesRecyclerView;
    List<ConsumerFavourites> consumerFavouritesList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    Typeface segeoAddressFontRegular;
    TextView pagetitle;
    TextView back;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_consumer_profile_details,container,false);
        fontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Regular.ttf");
//        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black);
//        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        MainActivity.toolbar.setNavigationIcon(null);
        MainActivity.customIcon.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);
        MainActivity.toolbar_save.setText("< Back");
        MainActivity.toolbar_save.setTypeface(fontBold);
        MainActivity.customIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.active = this;
        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("Consumer Profile");
        pagetitle.setTypeface(fontBold);

        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });

        segeoAddressFontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/SEGOEUI.TTF");

        profileImage = rootView.findViewById(R.id.profileImage);
        consumerName = rootView.findViewById(R.id.consumerName);
        city = rootView.findViewById(R.id.city);
        totalOrdersHeader = rootView.findViewById(R.id.totalOrdersHeader);
        subscriptionsHeader = rootView.findViewById(R.id.subscriptionsHeader);
        ordersCount = rootView.findViewById(R.id.ordersCount);
        subscriptionsCount = rootView.findViewById(R.id.subscriptionsCount);
        addressheader = rootView.findViewById(R.id.addressheader);
        homeTitle = rootView.findViewById(R.id.homeTitle);
        workAddress = rootView.findViewById(R.id.workAddress);
        homeAddress = rootView.findViewById(R.id.homeAddress);
        workTitle = rootView.findViewById(R.id.workTitle);
        paymentChoiceheader = rootView.findViewById(R.id.paymentChoiceheader);
        paymentSavedCard = rootView.findViewById(R.id.paymentSavedCard);
        paymentCardnumber =rootView. findViewById(R.id.paymentCardnumber);
        paymentCardBrand = rootView.findViewById(R.id.paymentCardBrand);
        bankDetailsheader = rootView.findViewById(R.id.bankDetailsheader);
        savedbankCard = rootView.findViewById(R.id.savedbankCard);
        bankcardNumber = rootView.findViewById(R.id.bankcardNumber);
        bankcardBrand = rootView.findViewById(R.id.bankcardBrand);
        favouritesheader = rootView.findViewById(R.id.favouritesheader);
        kitchenName = rootView.findViewById(R.id.kitchenName);
        location = rootView.findViewById(R.id.location);
        favouritesRecyclerView = rootView.findViewById(R.id.favouritesRecyclerView);
        favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        consumerName.setTypeface(fontBold);
        city.setTypeface(fontRegular);
        ordersCount.setTypeface(fontRegular);
        subscriptionsCount.setTypeface(fontBold);
        subscriptionsHeader.setTypeface(fontBold);
        totalOrdersHeader.setTypeface(fontBold);
        addressheader.setTypeface(fontBold);
        homeTitle.setTypeface(fontBold);
        workTitle.setTypeface(fontBold);
        homeAddress.setTypeface(fontRegular);
        workAddress.setTypeface(fontRegular);
        paymentChoiceheader.setTypeface(fontBold);
        paymentSavedCard.setTypeface(fontBold);
        bankcardBrand.setTypeface(fontBold);
        bankcardNumber.setTypeface(fontBold);
        savedbankCard.setTypeface(fontBold);
        favouritesheader.setTypeface(fontBold);
        bankDetailsheader.setTypeface(fontBold);
        kitchenName.setTypeface(fontBold);
        location.setTypeface(fontBold);
        workAddress.setTypeface(segeoAddressFontRegular);
        homeAddress.setTypeface(segeoAddressFontRegular);

        getFavourites();
        return rootView;
    }

    public void getFavourites()
    {
        ConsumerFavourites consumerFavourites = new ConsumerFavourites();
        consumerFavourites.setKitchenName("Rahul's Kitchen");
        consumerFavourites.setLocation("Location");
        consumerFavouritesList.add(consumerFavourites);
        consumerFavouritesList.add(consumerFavourites);
        consumerFavouritesList.add(consumerFavourites);
        consumerFavouritesList.add(consumerFavourites);
        consumerFavouritesList.add(consumerFavourites);
        consumerFavouritesList.add(consumerFavourites);
        consumerFavouritesList.add(consumerFavourites);

        favouritesRecyclerView.setAdapter(new ConsumerFavouritesListAdapter(getContext(),consumerFavouritesList,fontBold,fontRegular));

    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
    }
}
