package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.models.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    RecyclerView recyclerView;


    Typeface fontBold;
    Typeface fontRegular;
    List<Object> objectList = new ArrayList<>();

    TextView pagetitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders,container,false);
        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_dehaze_black);
        MainActivity.customIcon.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerView);
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.active = this;
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");

        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setTypeface(fontBold);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getOrders();
        return  rootView;
    }

    public void getOrders()
    {
        for (int i=0;i<10;i++)
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
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);
//        BottomNavigationView navBar = getActivity().findViewById(R.id.navigation);
//        navBar.setVisibility(View.VISIBLE);
//        navigation.setVisibility(View.VISIBLE);
    }
}
