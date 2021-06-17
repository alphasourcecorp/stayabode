package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.applandeo.materialcalendarview.CalendarView;
import stayabode.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.models.Orders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BulkOrderFragment extends Fragment {

    RecyclerView recyclerView;
    TextView header;
    Typeface fontBold;
    Typeface fontRegular;
    List<Object> objectList = new ArrayList<>();
    Toolbar toolbar;
    TextView toolbar_title;
    CalendarView calendarView;
    TextView pagetitle;
    TextView back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_bulk_order,container,false);
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
        pagetitle.setText("Bulk Order Status");
        pagetitle.setTypeface(fontBold);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        calendarView = rootView.findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 3, 19);

        try {
            calendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();


      //  calendarView.setMinimumDate(min);
        //calendarView.setMaximumDate(max);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        header = rootView.findViewById(R.id.header);
        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });
        header.setTypeface(fontBold);

        getOrders();
        return rootView;
    }

    public void getOrders()
    {
        for (int i=0;i < 10;i++)
        {
            Orders orders = new Orders();
            orders.setCount("11");
            orders.setName("Event Title");
            orders.setDescription("Lorem Ipsum dolor sit amet,\\nconsectetur adipiscing elit. Happy");
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
