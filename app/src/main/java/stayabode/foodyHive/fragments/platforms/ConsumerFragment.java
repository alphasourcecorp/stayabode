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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.adapters.platform.AllListAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.Consumer;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import java.util.ArrayList;
import java.util.List;

public class ConsumerFragment extends Fragment {
    RecyclerView recyclerView;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;
    TextView toolbar_title;
    TextView pagetitle;
    TextView back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_consumer,container,false);
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
        pagetitle.setText("Consumer Status");
        pagetitle.setTypeface(fontBold);

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

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getConsumers();
        return rootView;
    }

    public void getConsumers()
    {
        for(int i=0; i < 10;i++)
        {
            Consumer consumer = new Consumer();
            consumer.setName("Consumer name");
            consumer.setLocation("location");
            consumer.setSubscriptionStatus("Subscribed");
            objectList.add(consumer);
        }



        recyclerView.setAdapter(new AllListAdapter(getActivity(),objectList,fontBold,fontRegular));

    }

    public void consumers()
    {
        String url = APIBaseURL.consumersURL;


        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest,"consumer_taq");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.GONE);
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
