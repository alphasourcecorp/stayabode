package stayabode.foodyHive.adapters.chefs;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.fragments.chefs.ChefProfileFragment;
import stayabode.foodyHive.fragments.chefs.ChefsMenuFragment;
import stayabode.foodyHive.fragments.chefs.PromoCodeFragment;
import stayabode.foodyHive.models.SubMenu;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefMainfragment;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefbackStateName;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.chefnavigation;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.dashboardView;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.franchiseView;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.ordersView;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.reportsView;

public class ChefsLeftSideChildMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SubMenu> subMenuList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;

    public ChefsLeftSideChildMenuAdapter(Context context,List<SubMenu> subMenuList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.subMenuList = subMenuList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chef_child_menu_side,parent,false);
        return new SubMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SubMenuItemViewHolder subMenuItemViewHolder = (SubMenuItemViewHolder)holder;
        subMenuItemViewHolder.name.setTypeface(fontRegular);
        subMenuItemViewHolder.name.setText(subMenuList.get(position).getName());

        try
        {
            subMenuItemViewHolder.icon.setText(new String(Character.toChars(Integer.parseInt(
                    subMenuList.get(position).getImage(), 16))));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        subMenuItemViewHolder.subMenuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChefsMainActivity.chefDrawer.closeDrawers();
                if(subMenuList.get(position).getId().equals("9999"))
                {
                    ChefsMainActivity.cheffragmentClass = PromoCodeFragment.class;
                    try {
                        chefMainfragment = (Fragment) ChefsMainActivity.cheffragmentClass.newInstance();
                        chefbackStateName = chefMainfragment.getClass().getName();

                        FragmentManager manager = cheffragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate(chefbackStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = cheffragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if (currentFragment != null && !currentFragment.getClass().equals(chefMainfragment.getClass())) {
                                cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).addToBackStack(chefbackStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else  if(subMenuList.get(position).getId().equals("1003") || subMenuList.get(position).getId().equals("5e82ea476c34d13b706b6680"))
                {

                    ChefsMainActivity.cheffragmentClass = ChefsMenuFragment.class;
                    try {
                        chefMainfragment = (Fragment) ChefsMainActivity.cheffragmentClass.newInstance();
                        chefbackStateName = chefMainfragment.getClass().getName();

                        FragmentManager manager = cheffragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate(chefbackStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = cheffragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if (currentFragment != null && !currentFragment.getClass().equals(chefMainfragment.getClass())) {
                                cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).commit();
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    chefnavigation.setSelectedItemId(R.id.navigation_menu);
                    dashboardView.setVisibility(View.INVISIBLE);
                    ordersView.setVisibility(View.INVISIBLE);
                    franchiseView.setVisibility(View.VISIBLE);
                    reportsView.setVisibility(View.INVISIBLE);
                }
                // When Clicking Chef go to Chef fragment
                else if(subMenuList.get(position).getName().equalsIgnoreCase("Chef")){
                    ChefsMainActivity.cheffragmentClass = ChefProfileFragment.class;
                    try {
                        chefMainfragment = (Fragment) ChefsMainActivity.cheffragmentClass.newInstance();
                        chefbackStateName = chefMainfragment.getClass().getName();

                        FragmentManager manager = cheffragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate(chefbackStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = cheffragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if (currentFragment != null && !currentFragment.getClass().equals(chefMainfragment.getClass())) {
                                cheffragmentManager.beginTransaction().replace(R.id.content, chefMainfragment).addToBackStack(chefbackStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subMenuList.size();
    }

    public class SubMenuItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView icon;
        TextView name;
        LinearLayout subMenuLayout;


        public SubMenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
            subMenuLayout = itemView.findViewById(R.id.subMenuLayout);
        }
    }
}
