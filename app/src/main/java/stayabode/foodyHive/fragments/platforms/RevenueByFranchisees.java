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
import stayabode.foodyHive.models.FranchiseeByLocation;
import stayabode.foodyHive.models.FranchiseeByRevenue;

import java.util.ArrayList;
import java.util.List;

public class RevenueByFranchisees extends Fragment {
    RecyclerView revenueRecyclerView;
    RecyclerView locationRecyclerView;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView pagetitle;
    TextView back;
    TextView locationHeader;
    TextView revenueHeader;
    TextView franchiseeNameHeader;
    TextView amountHeader;
    TextView contributionHeader;
    TextView franchiseeNameHeaderRevenue;
    TextView amountHeaderRevenue;
    TextView contributionHeaderRevenue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.revenue_by_franchisees, container, false);
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
        locationHeader = rootView.findViewById(R.id.locationHeader);
        revenueHeader = rootView.findViewById(R.id.revenueHeader);
        franchiseeNameHeader = rootView.findViewById(R.id.franchiseeNameHeader);
        amountHeader = rootView.findViewById(R.id.amountHeader);
        contributionHeader = rootView.findViewById(R.id.contributionHeader);
        franchiseeNameHeaderRevenue = rootView.findViewById(R.id.franchiseeNameHeaderRevenue);
        amountHeaderRevenue = rootView.findViewById(R.id.amountHeaderRevenue);
        contributionHeaderRevenue = rootView.findViewById(R.id.contributionHeaderRevenue);

        pagetitle.setText("Revenue by Franchisees");
        pagetitle.setTypeface(fontBold);

        locationHeader.setText("By Location");
        locationHeader.setTypeface(fontBold);

        revenueHeader.setText("By Revenue");
        revenueHeader.setTypeface(fontBold);

        franchiseeNameHeader.setText("Franchisee");
        franchiseeNameHeader.setTypeface(fontRegular);

        amountHeader.setText("Amount");
        amountHeader.setTypeface(fontRegular);

        contributionHeader.setText("Contribution");
        contributionHeader.setTypeface(fontRegular);

        franchiseeNameHeaderRevenue.setText("Franchisee");
        franchiseeNameHeaderRevenue.setTypeface(fontRegular);

        amountHeaderRevenue.setText("Amount");
        amountHeaderRevenue.setTypeface(fontRegular);

        contributionHeaderRevenue.setText("Contribution");
        contributionHeaderRevenue.setTypeface(fontRegular);

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
        revenueRecyclerView = rootView.findViewById(R.id.revenueRecyclerView);
        revenueRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        locationRecyclerView = rootView.findViewById(R.id.locationRecyclerView);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getByLocation();
        getByRevenue();
        //users();
        return rootView;
    }


    public void getByLocation() {
        objectList.clear();
        for (int i = 1; i <= 5; i++) {
            FranchiseeByLocation franchiseeByLocation = new FranchiseeByLocation();
            franchiseeByLocation.setName("Franchisee " + i);
            franchiseeByLocation.setAmount("₹12212");
            franchiseeByLocation.setContribution("25%");
            objectList.add(franchiseeByLocation);
        }
        locationRecyclerView.setAdapter(new AllListAdapter(getActivity(), objectList, fontBold, fontRegular));
    }


    public void getByRevenue() {
        objectList.clear();
        for (int i = 1; i <= 5; i++) {
            FranchiseeByRevenue franchiseeByRevenue = new FranchiseeByRevenue();
            franchiseeByRevenue.setName("Franchisee " + i);
            franchiseeByRevenue.setAmount("₹12212");
            franchiseeByRevenue.setContribution("25%");
            objectList.add(franchiseeByRevenue);
        }
        revenueRecyclerView.setAdapter(new AllListAdapter(getActivity(), objectList, fontBold, fontRegular));

    }


    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
