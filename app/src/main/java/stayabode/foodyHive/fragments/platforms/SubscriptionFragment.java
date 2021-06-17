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
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.models.Orders;

import java.util.ArrayList;
import java.util.List;

import noman.weekcalendar.WeekCalendar;

public class SubscriptionFragment  extends Fragment {

    TextView corporateHeader;
    TextView corporateCount;
    TextView individualHeader;
    TextView individualCount;
    RecyclerView recyclerView;
    WeekCalendar weekCalendar;
    Typeface fontBold;
    Typeface fontRegular;
    List<Object> objectList = new ArrayList<>();
    TextView toolbar_title;
    TextView pagetitle;
    TextView back;
    TextView weekTextHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_subscription_status,container,false);
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
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.active = this;
        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("Subscription Status");
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


        corporateHeader = rootView.findViewById(R.id.corporateHeader);
        corporateCount = rootView.findViewById(R.id.corporateCount);
        individualHeader = rootView.findViewById(R.id.individualHeader);
        individualCount = rootView.findViewById(R.id.individualCount);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        weekCalendar = rootView.findViewById(R.id.weekCalendar);
        weekTextHeader = rootView.findViewById(R.id.weekTextHeader);

        corporateCount.setTypeface(fontBold);
        corporateHeader.setTypeface(fontBold);
        individualCount.setTypeface(fontBold);
        individualHeader.setTypeface(fontBold);
        weekTextHeader.setTypeface(fontBold);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getSubscriptionStatus();

        return rootView;
    }

    public void getSubscriptionStatus()
    {
        for (int i=0;i<10;i++)
        {
            Orders orders = new Orders();
            orders.setDescription("description");
            orders.setName("Order");
            orders.setCount("1");
            objectList.add(orders);
        }

        recyclerView.setAdapter(new AllListAdapter(getActivity(),objectList,fontBold,fontRegular));

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
//        BottomNavigationView navBar = getActivity().findViewById(R.id.navigation);
//        navBar.setVisibility(View.GONE);
//        navigation.setVisibility(View.GONE);
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}
