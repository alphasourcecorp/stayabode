package stayabode.foodyHive.adapters.chefs;

import android.content.Context;
import android.graphics.Typeface;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.fragments.chefs.ChefHomeFragment;
import stayabode.foodyHive.fragments.chefs.ChefNotificationFragment;
import stayabode.foodyHive.fragments.chefs.ChefsMenuFragment;
import stayabode.foodyHive.fragments.chefs.ChefsOrderFragment;
import stayabode.foodyHive.fragments.chefs.EditProfileFragment;
import stayabode.foodyHive.models.MainMenu;


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


public class ChefLeftMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MainMenu> mainMenuList = new ArrayList<>();
    Context context;
    Typeface fontBold;
    Typeface fontRegular;
    int expandPosition = -1;


    public ChefLeftMenuAdapter(Context context,List<MainMenu> mainMenuList,Typeface fontBold,Typeface fontRegular,int expandPosition)
    {
        this.context = context;
        this.mainMenuList = mainMenuList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
        this.expandPosition = expandPosition;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chef_custom_main_menu,parent,false);
        return new ChefMenuItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChefMenuItemsViewHolder mainMenuItemViewHolder = (ChefMenuItemsViewHolder)holder;
        mainMenuItemViewHolder.name.setTypeface(fontRegular);
        mainMenuItemViewHolder.name.setText(mainMenuList.get(position).getName());

        mainMenuItemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mainMenuItemViewHolder.recyclerView.setAdapter(new ChefsLeftSideChildMenuAdapter(context,mainMenuList.get(position).getSubMenuList(),fontBold,fontRegular));

        if(mainMenuList.get(position).getSubMenuList().size() == 0)
        {
            mainMenuItemViewHolder.arrowImage.setVisibility(View.GONE);
        }
        else
        {
            mainMenuItemViewHolder.arrowImage.setVisibility(View.VISIBLE);
        }

        mainMenuItemViewHolder.icon.setText(new String(Character.toChars(Integer.parseInt(
                mainMenuList.get(position).getImage(), 16))));


        if (position == expandPosition) {
            mainMenuItemViewHolder.recyclerView.setVisibility(View.VISIBLE);
            mainMenuItemViewHolder.arrowImage.setText(R.string.beforeExpandIcon);
        } else {
            mainMenuItemViewHolder.recyclerView.setVisibility(View.GONE);
            mainMenuItemViewHolder.arrowImage.setText(R.string.afterExpandIcon);
        }

        if(position == 0)
        {
            mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
        else
        {
            mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
        final boolean isExpanded = position==expandPosition;

        if(position == expandPosition)
        {
            mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
        else
        {
            mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }

        if(mainMenuList.get(position).getId().equals("5ebaaa4812f1891a2429fc93"))
        {
            mainMenuItemViewHolder.notificationsbadge.setVisibility(View.VISIBLE);
        }
        else
        {
            mainMenuItemViewHolder.notificationsbadge.setVisibility(View.GONE);
        }

        mainMenuItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainMenuList.get(position).getSubMenuList().size() == 0)
                {
                    ChefsMainActivity.chefDrawer.closeDrawers();

                    // For My Profile Fragment from Navigation Side Menu

                    if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6680") || mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6604") || mainMenuList.get(position).getName().equals("My Profile"))
                    {
                        ChefsMainActivity.cheffragmentClass = EditProfileFragment.class;
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
                    // For Orders fragment from Navigation Side Menu
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6679") || mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6603")|| mainMenuList.get(position).getName().equals("Orders"))
                    {
                        ChefsMainActivity.cheffragmentClass= ChefsOrderFragment.class;


                        try {
                            chefMainfragment = (Fragment) ChefsMainActivity.cheffragmentClass.newInstance();
                            chefbackStateName = chefMainfragment.getClass().getName();

                            FragmentManager manager = cheffragmentManager;
                            boolean fragmentPopped = manager.popBackStackImmediate (chefbackStateName, 0);

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

                        chefnavigation.setSelectedItemId(R.id.navigation_orders);
                        dashboardView.setVisibility(View.INVISIBLE);
                        ordersView.setVisibility(View.VISIBLE);
                        franchiseView.setVisibility(View.INVISIBLE);
                        reportsView.setVisibility(View.INVISIBLE);
                    }
                    // For Notifications fragment from Navigation Side Menu
                    else if(mainMenuList.get(position).getId().equals("5ecce2ac1482c62b4c562db2") || mainMenuList.get(position).getId().equals("5ecce2ac1482c62b4c562d05") || mainMenuList.get(position).getName().equals("Notifications"))
                    {
                        ChefsMainActivity.cheffragmentClass = ChefNotificationFragment.class;

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
                        chefnavigation.setSelectedItemId(R.id.navigation_notification);
                        dashboardView.setVisibility(View.INVISIBLE);
                        ordersView.setVisibility(View.INVISIBLE);
                        franchiseView.setVisibility(View.INVISIBLE);
                        reportsView.setVisibility(View.VISIBLE);
                    }
                    // For Food Menu fragment from Navigation Side Menu
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6602") || mainMenuList.get(position).getName().equals("Menu"))
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
                    // For Dashboard fragment from Navigation Side Menu
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6677") || mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6600") || mainMenuList.get(position).getName().equals("Dashboard"))
                    {
                        ChefsMainActivity.cheffragmentClass = ChefHomeFragment.class;



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

                        chefnavigation.setSelectedItemId(R.id.navigation_my_dashboard);
                        dashboardView.setVisibility(View.VISIBLE);
                        ordersView.setVisibility(View.INVISIBLE);
                        franchiseView.setVisibility(View.INVISIBLE);
                        reportsView.setVisibility(View.INVISIBLE);
                    }
                    // For Logout from Navigation Side Menu
                    else if(mainMenuList.get(position).getId().equals("5ecce2ac1482c62b4c562db3") || mainMenuList.get(position).getId().equals("5ecce2ac1482c62b4c562d06") || mainMenuList.get(position).getName().equals("Logout"))
                    {
                        ChefsMainActivity.chefsMainActivity.logout();
                    }
                }
                else
                {
                    expandPosition = isExpanded ? -1:position;
                    // fancy animations can skip if like
                    TransitionManager.beginDelayedTransition( mainMenuItemViewHolder.recyclerView);
                    //This will call the onBindViewHolder for all the itemViews on Screen
                    notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mainMenuList.size();
    }

    public class ChefMenuItemsViewHolder extends RecyclerView.ViewHolder
    {

        TextView icon;
        TextView arrowImage;
        TextView name;
        TextView notificationsbadge;
        RecyclerView recyclerView;
        LinearLayout mainLayout;

        public ChefMenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            arrowImage = itemView.findViewById(R.id.arrowImage);
            icon = itemView.findViewById(R.id.icon);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            notificationsbadge = itemView.findViewById(R.id.notificationsbadge);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
