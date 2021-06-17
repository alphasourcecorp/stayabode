package stayabode.foodyHive.activities.consumers;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.OrderFoodsItemsListsAdapter;
import stayabode.foodyHive.models.FoodItem;

import java.util.List;

public class ConsumerOrderInfoActivity  extends AppCompatActivity {


    TextView foodName;
    TextView delivered;
    TextView invoice;
    TextView OrderTotal;
    TextView deliveredDate;
    TextView invoiceNumber;
    TextView orderTotalQuantity;
    TextView paymentMethod;
    TextView paymentMethodType;
    TextView deliverAddress;
    TextView address;
    TextView orderSummary;
    TextView orderQuantity;
    TextView mealPrice;
    TextView totalTax;
    TextView deliveryCharge;
    TextView subTotal;
    TextView mealCost;
    TextView taxCost;
    TextView deliveryCost;
    TextView subtotalCost;

    Typeface poppinsSemiBold;
    Typeface poppinsBold;
    Typeface poppinsMedium;
    Typeface poppinsLight;
    Typeface robotoFontBold;
    Typeface robotoFontRegular;
    ImageView foodImage;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_order_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Order Info");
        poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        poppinsBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Light.ttf");
        robotoFontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        robotoFontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        foodName = findViewById(R.id.foodName);
        foodImage = findViewById(R.id.foodImage);
        delivered = findViewById(R.id.delivered);
        invoice = findViewById(R.id.invoice);
        OrderTotal = findViewById(R.id.OrderTotal);
        deliveredDate = findViewById(R.id.deliveredDate);
        invoiceNumber = findViewById(R.id.invoiceNumber);
        orderTotalQuantity = findViewById(R.id.orderTotalQuantity);
        paymentMethod = findViewById(R.id.paymentMethod);
        paymentMethodType = findViewById(R.id.paymentMethodType);
        deliverAddress = findViewById(R.id.deliverAddress);
        address = findViewById(R.id.address);
        orderSummary = findViewById(R.id.orderSummary);
        orderQuantity = findViewById(R.id.orderQuantity);
        mealPrice = findViewById(R.id.mealPrice);
        totalTax = findViewById(R.id.totalTax);
        deliveryCharge = findViewById(R.id.deliveryCharge);
        subTotal =findViewById(R.id.subTotal);
        mealCost =findViewById(R.id.mealCost);
        taxCost = findViewById(R.id.taxCost);
        deliveryCost = findViewById(R.id.deliveryCost);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<FoodItem> myList = (List<FoodItem>) getIntent().getSerializableExtra("ItemsList");
        recyclerView.setAdapter(new OrderFoodsItemsListsAdapter(this,myList,"detail",null,null,null));
        subtotalCost = findViewById(R.id.subtotalCost);

        foodName.setTypeface(poppinsSemiBold);
        paymentMethod.setTypeface(poppinsSemiBold);
        deliverAddress.setTypeface(poppinsSemiBold);
        orderSummary.setTypeface(poppinsSemiBold);
        orderQuantity.setTypeface(poppinsMedium);
        subtotalCost.setTypeface(poppinsBold);
        mealCost.setTypeface(poppinsMedium);
        taxCost.setTypeface(poppinsMedium);
        deliveryCost.setTypeface(poppinsMedium);
        delivered.setTypeface(poppinsLight);
        invoice.setTypeface(poppinsLight);
        OrderTotal.setTypeface(poppinsLight);
        deliveredDate.setTypeface(poppinsLight);
        invoiceNumber.setTypeface(poppinsLight);
        orderTotalQuantity.setTypeface(poppinsLight);
        paymentMethodType.setTypeface(poppinsLight);
        address.setTypeface(poppinsLight);
        mealPrice.setTypeface(poppinsLight);
        totalTax.setTypeface(poppinsLight);
        deliveryCharge.setTypeface(poppinsLight);
        subTotal.setTypeface(poppinsLight);


        Glide.with(this).load(getIntent().getStringExtra("ItemImage")).placeholder(R.drawable.foodi_logo_left_image).into(foodImage);
        foodName.setText(getIntent().getStringExtra("ItemName"));
        invoiceNumber.setText(": "+getOrderNoWithDashes(getIntent().getStringExtra("OrderId"),"-",4));
        if(getIntent().getStringExtra("title").equals("Open"))
        {
            delivered.setText("Ordered Date");
        }
        else if(getIntent().getStringExtra("title").equals("Closed"))
        {
            delivered.setText("Delivered Date");
        }
        else if(getIntent().getStringExtra("title").equals("Cancelled"))
        {
            delivered.setText("Cancelled Date");
        }
        orderQuantity.setText(getIntent().getStringExtra("quantity"));
        try {
            mealCost.setText(String.format("%.2f",Double.valueOf(getIntent().getStringExtra("Itemamount"))));
            subtotalCost.setText(String.format("%.2f",Double.valueOf(getIntent().getStringExtra("Itemamount"))));
            orderTotalQuantity.setText(String.format("%.2f",Double.valueOf(getIntent().getStringExtra("Itemamount"))));
            paymentMethodType.setText(""+getIntent().getStringExtra("payment"));
            deliveredDate.setText(": "+getIntent().getStringExtra("dateOrdered"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    /**
     To Back Press from the Top Toolbar back Arrow
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     get order number with dashes for every 4 digits..
     **/

    public String getOrderNoWithDashes(String orderNo, String insert, int period) {
        StringBuilder builder = new StringBuilder(
                orderNo.length() + insert.length() * (orderNo.length() / period) + 1);

        int index = 0;
        String prefix = "";
        while (index < orderNo.length()) {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            prefix = insert;
            builder.append(orderNo.substring(index,
                    Math.min(index + period, orderNo.length())));
            index += period;
        }
        return builder.toString();
    }


}
