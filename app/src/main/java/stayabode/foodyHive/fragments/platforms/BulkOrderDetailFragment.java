package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.BillingDetailsFoodMenuAdapter;
import stayabode.foodyHive.models.FoodMenu;

import java.util.ArrayList;
import java.util.List;

public class BulkOrderDetailFragment extends Fragment {

    TextView orderNumber;
    TextView status;
    TextView statusValue;
    TextView orderDate;
    TextView orderTime;
    TextView orderDateValue;
    TextView orderTimeValue;
    TextView kitchenName;
    TextView kitchenValue;
    TextView orderAmount;
    TextView orderAmountValue;
    TextView paymentMethod;
    TextView paymentMethodValue;
    TextView deliveryDetailsHeader;
    TextView name;
    TextView address;
    TextView mobileNumber;
    TextView deliveryDate;
    TextView deliveryDateValue;
    TextView deliveryTime;
    TextView deliveryTimeValue;
    TextView billingDetailsHeader;
    TextView foodItemHeader;
    TextView qtyHeader;
    TextView priceHeader;
    TextView subTotal;
    TextView subTotalValue;
    TextView tax;
    TextView taxValue;
    TextView deliveryFees;
    TextView deliveryFeesValue;
    RecyclerView recyclerView;
    TextView otherCahrges;
    TextView otherCahrgesValue;
    TextView total;
    TextView totalValue;
    List<FoodMenu> foodMenuList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView toolbar_title;
    TextView pagetitle;
    TextView back;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_bulk_order_details,container,false);

        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");
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
        MainActivity.active = this;
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        pagetitle = rootView.findViewById(R.id.pagetitle);
        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });
        pagetitle.setText("Bulk Order Status");
        orderNumber = rootView.findViewById(R.id.orderNumber);
        status = rootView.findViewById(R.id.status);
        statusValue = rootView.findViewById(R.id.statusValue);
        orderDate = rootView.findViewById(R.id.orderDate);
        orderDateValue = rootView.findViewById(R.id.orderDateValue);
        orderTimeValue = rootView.findViewById(R.id.orderTimeValue);
        orderTime = rootView.findViewById(R.id.orderTime);
        kitchenName = rootView.findViewById(R.id.kitchenName);
        kitchenValue = rootView.findViewById(R.id.kitchenValue);
        orderAmount = rootView.findViewById(R.id.orderAmount);
        orderAmountValue = rootView.findViewById(R.id.orderAmountValue);
        paymentMethod = rootView.findViewById(R.id.paymentMethod);
        paymentMethodValue = rootView.findViewById(R.id.paymentMethodValue);
        deliveryDetailsHeader = rootView.findViewById(R.id.deliveryDetailsHeader);
        name = rootView.findViewById(R.id.name);
        mobileNumber = rootView.findViewById(R.id.mobileNumber);
        address = rootView.findViewById(R.id.address);
        deliveryDate = rootView.findViewById(R.id.deliveryDate);
        deliveryDateValue = rootView.findViewById(R.id.deliveryDateValue);
        deliveryTime = rootView.findViewById(R.id.deliveryTime);
        deliveryTimeValue = rootView.findViewById(R.id.deliveryTimeValue);
        billingDetailsHeader = rootView.findViewById(R.id.billingDetailsHeader);
        foodItemHeader = rootView.findViewById(R.id.foodItemHeader);
        qtyHeader = rootView.findViewById(R.id.qtyHeader);
        priceHeader = rootView.findViewById(R.id.priceHeader);
        tax = rootView.findViewById(R.id.tax);
        taxValue = rootView.findViewById(R.id.taxValue);
        subTotal = rootView.findViewById(R.id.subTotal);
        subTotalValue = rootView.findViewById(R.id.subTotalValue);
        deliveryFees = rootView.findViewById(R.id.deliveryFees);
        deliveryFeesValue = rootView.findViewById(R.id.deliveryFeesValue);
        otherCahrges = rootView.findViewById(R.id.otherCahrges);
        otherCahrgesValue = rootView.findViewById(R.id.otherCahrgesValue);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        total = rootView.findViewById(R.id.total);
        totalValue = rootView.findViewById(R.id.totalValue);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderNumber.setTypeface(fontBold);
        status.setTypeface(fontBold);
        statusValue.setTypeface(fontRegular);
        orderDate.setTypeface(fontBold);
        orderDateValue.setTypeface(fontRegular);
        orderTime.setTypeface(fontBold);
        orderTimeValue.setTypeface(fontRegular);
        kitchenName.setTypeface(fontBold);
        kitchenValue.setTypeface(fontRegular);
        orderAmount.setTypeface(fontBold);
        orderAmountValue.setTypeface(fontRegular);
        paymentMethod.setTypeface(fontBold);
        paymentMethod.setTypeface(fontRegular);
        deliveryDetailsHeader.setTypeface(fontBold);
        deliveryDate.setTypeface(fontBold);
        deliveryDateValue.setTypeface(fontRegular);
        deliveryTime.setTypeface(fontBold);
        paymentMethod.setTypeface(fontBold);
        paymentMethodValue.setTypeface(fontRegular);
        deliveryTimeValue.setTypeface(fontRegular);
        name.setTypeface(fontRegular);
        address.setTypeface(fontRegular);
        mobileNumber.setTypeface(fontRegular);

        billingDetailsHeader.setTypeface(fontBold);
        tax.setTypeface(fontBold);
        subTotal.setTypeface(fontBold);
        subTotalValue.setTypeface(fontRegular);
        taxValue.setTypeface(fontRegular);
        deliveryFeesValue.setTypeface(fontRegular);
        otherCahrgesValue.setTypeface(fontRegular);
        foodItemHeader.setTypeface(fontBold);
        deliveryFees.setTypeface(fontBold);
        otherCahrges.setTypeface(fontBold);
        totalValue.setTypeface(fontBold);
        qtyHeader.setTypeface(fontBold);
        priceHeader.setTypeface(fontBold);
        total.setTypeface(fontBold);
        pagetitle.setTypeface(fontBold);

        getMenuItems();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
    }

    public void getMenuItems()
    {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setPrice("$ 300");
        foodMenu.setName("Food Item 1");
        foodMenu.setQty(1);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);
        foodMenuList.add(foodMenu);


        recyclerView.setAdapter(new BillingDetailsFoodMenuAdapter(getContext(),foodMenuList,fontBold,fontRegular));
    }

    public void onBackPressed()
    {
        //navigation.setVisibility(View.VISIBLE);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
