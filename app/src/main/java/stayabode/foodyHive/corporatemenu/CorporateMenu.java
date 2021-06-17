package stayabode.foodyHive.corporatemenu;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;

import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CorporateMenu extends AppCompatActivity {


    FrameLayout FrameLayout_id;
    ImageView menu_icon;
    boolean frame=true;
    LinearLayout menu_button;

    CorporateExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_corporate_menu);

        FrameLayout_id=findViewById(R.id.FrameLayout_id);
        FrameLayout_id.setVisibility(View.GONE);

        menu_icon=findViewById(R.id.menu_icon);
        menu_button=findViewById(R.id.menu_button);

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(frame)
                {
                    FrameLayout_id.animate()
                            .translationYBy(120)
                            .translationY(0)
                            .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
                    FrameLayout_id.setVisibility(View.VISIBLE);
                    frame=false;
                    menu_icon.setBackgroundResource(R.drawable.close_black);

                }else
                {
                    FrameLayout_id.setVisibility(View.GONE);
                    frame=true;
                    menu_icon.setBackgroundResource(R.drawable.ic_baseline_dehaze);
                }


            }
        });










        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getActivity(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getActivity(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getActivity(),
//                        listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });


        JSONObject jsonObject = null;
        try {

            String data="{\"isSuccess\":true,\"errorMessage\":\"\",\"data\":[{\"id\":\"0001\",\"catname\":\"BRIYANI\",\"catimg\":\"https://cimavistorage.blob.core.windows.net/assets/55954582/Dark Chocolate Ice cream 2.png\",\"menucount\":\"3\",\"menuDetails\":[{\"dishId\":\"5fb71837f5cab8535c7820e6\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/55954582/Dark Chocolate Ice cream 2.png\"],\"dishName\":\"CHETTINAD CHICKEN BIRYANI\",\"saleAmount\":550,\"quantity\":1,\"mealPrice\":550,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":210,\"preparationTime\":1440,\"autoAcceptOrder\":true,\"tax\":2.93,\"isAvailable\":true},{\"dishId\":\"5fb71837f5cab8535c7820d8\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/697413609/Mini Pies.png\"],\"dishName\":\"CHETTINAD MUTTON BIRYANI\",\"saleAmount\":56.92,\"quantity\":1,\"mealPrice\":45,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":150,\"preparationTime\":90,\"autoAcceptOrder\":true,\"tax\":4.4,\"isAvailable\":true},{\"dishId\":\"5fb71837f5cab8535c7820d8\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/697413609/Mini Pies.png\"],\"dishName\":\"CHETTINAD EGG BIRYANI\",\"saleAmount\":56.92,\"quantity\":1,\"mealPrice\":45,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":150,\"preparationTime\":90,\"autoAcceptOrder\":true,\"tax\":4.4,\"isAvailable\":true}]},{\"id\":\"0001\",\"catname\":\"NON-VEG CURRIES\",\"catimg\":\"https://cimavistorage.blob.core.windows.net/assets/55954582/Dark Chocolate Ice cream 2.png\",\"menucount\":\"3\",\"menuDetails\":[{\"dishId\":\"5fb71837f5cab8535c7820e6\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/55954582/Dark Chocolate Ice cream 2.png\"],\"dishName\":\"CHETTINAD CHICKEN BIRYANI\",\"saleAmount\":550,\"quantity\":1,\"mealPrice\":550,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":210,\"preparationTime\":1440,\"autoAcceptOrder\":true,\"tax\":2.93,\"isAvailable\":true},{\"dishId\":\"5fb71837f5cab8535c7820d8\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/697413609/Mini Pies.png\"],\"dishName\":\"CHETTINAD MUTTON BIRYANI\",\"saleAmount\":56.92,\"quantity\":1,\"mealPrice\":45,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":150,\"preparationTime\":90,\"autoAcceptOrder\":true,\"tax\":4.4,\"isAvailable\":true},{\"dishId\":\"5fb71837f5cab8535c7820d8\",\"dishImage\":[\"https://cimavistorage.blob.core.windows.net/assets/697413609/Mini Pies.png\"],\"dishName\":\"CHETTINAD EGG BIRYANI\",\"saleAmount\":56.92,\"quantity\":1,\"mealPrice\":45,\"discountedPercent\":0,\"availableCount\":1,\"energyCalories\":150,\"preparationTime\":90,\"autoAcceptOrder\":true,\"tax\":4.4,\"isAvailable\":true}]}]}";

            jsonObject = new JSONObject(data);

            JSONArray dataArray = jsonObject.getJSONArray("data");
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject fullOrderData = dataArray.getJSONObject(i);
                listDataHeader.add(fullOrderData.optString("catname"));

                JSONArray menuDetailsArray = fullOrderData.getJSONArray("menuDetails");
                List<String> dishlistdata = new ArrayList<String>();
                for (int m = 0; m < menuDetailsArray.length(); m++) {
                    JSONObject menuDetailsData = menuDetailsArray.getJSONObject(m);
                    JSONArray dishImageArray = menuDetailsData.getJSONArray("dishImage");
                    String dishname=menuDetailsData.optString("dishName");
                    dishlistdata.add(String.valueOf(dishname));
                }

                listDataChild.put(fullOrderData.optString("catname"), dishlistdata);

                listAdapter = new CorporateExpandableListAdapter(this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }







    }
}
