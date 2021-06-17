package stayabode.foodyHive.adapters.platform;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
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
import stayabode.foodyHive.activities.platform.MainActivity;

import stayabode.foodyHive.fragments.platforms.BulkOrderFragment;
import stayabode.foodyHive.fragments.platforms.ChefFragment;
import stayabode.foodyHive.fragments.platforms.CloudKitchenFragment;
import stayabode.foodyHive.fragments.platforms.ConsumerFragment;
import stayabode.foodyHive.fragments.platforms.DeliveryPartnerFragment;
import stayabode.foodyHive.fragments.platforms.FranchiseFragment;
import stayabode.foodyHive.fragments.platforms.PaymentGatewayFragment;
import stayabode.foodyHive.fragments.platforms.RevenueSharingFragment;
import stayabode.foodyHive.fragments.platforms.RoleFragment;
import stayabode.foodyHive.fragments.platforms.SubscriptionFragment;
import stayabode.foodyHive.fragments.platforms.UserFragment;
import stayabode.foodyHive.models.SubMenu;

import java.util.ArrayList;
import java.util.List;

//import static com.foodyhive.activities.MainActivity.bulkOrderFragment;
//import static com.foodyhive.activities.MainActivity.chefFragment;
//import static com.foodyhive.activities.MainActivity.cloudKitchenFragment;
//import static com.foodyhive.activities.MainActivity.consumerFragment;
//import static com.foodyhive.activities.MainActivity.deliveryPartnerFragment;

//import static com.foodyhive.activities.MainActivity.paymentGatewayFragment;
//import static com.foodyhive.activities.MainActivity.revenueSharingFragment;
//import static com.foodyhive.activities.MainActivity.subscriptionFragment;

public class SubMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SubMenu> subMenuList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;

    public SubMenuAdapter(Context context,List<SubMenu> subMenuList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.subMenuList = subMenuList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.side_bar_sub_menu_item_layout,parent,false);
        return new SubMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SubMenuItemViewHolder subMenuItemViewHolder = (SubMenuItemViewHolder)holder;
        subMenuItemViewHolder.name.setTypeface(fontRegular);
        subMenuItemViewHolder.name.setText(subMenuList.get(position).getName());

//        Typeface tf = Typeface.createFromAsset(context.getAssets(),
//                "FontAwesome.ttf");
//        subMenuItemViewHolder.icon.setTypeface(tf);


      //  subMenuItemViewHolder.icon.setText(String.valueOf("u"+subMenuList.get(position).getImage()));
//        subMenuItemViewHolder.icon.setText(subMenuList.get(position).getImage());\
        try
        {
            subMenuItemViewHolder.icon.setText(new String(Character.toChars(Integer.parseInt(
                    subMenuList.get(position).getImage(), 16))));

            Log.d("Image",subMenuList.get(position).getImage() + " Sub Menu Image");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        subMenuItemViewHolder.subMenuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.closeDrawers();
                if(subMenuList.get(position).getId().equals("1001"))
                {
//                    Intent intent = new Intent(context, CloudKitchenActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
//                    fragmentManager.beginTransaction().hide(active).show(cloudKitchenFragment).commit();
//                    active = cloudKitchenFragment;
//                    CloudKitchenFragment fragment = new CloudKitchenFragment();
//                    FragmentManager fm = MainActivity.fragmentManager;
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content, fragment).addToBackStack(null);
//                    ft.commit();
                    MainActivity.fragmentClass= CloudKitchenFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else if(subMenuList.get(position).getId().equals("1000"))
                {
//                    Intent intent = new Intent(context, CloudKitchenActivity.class);
//                    context.startActivity(intent);
                    MainActivity.navigation.setSelectedItemId(R.id.navigation_franchise);
                    //toolbar_title.setText("Franchisee Status");
//                    if(nowActiveFragment == homeFragment || nowActiveFragment == ordersFragment || nowActiveFragment== franchiseFragment || nowActiveFragment == reportsFragment || nowActiveFragment == notificationFragment)
//                    {
//                        fragmentManager.beginTransaction().hide(active).show(franchiseFragment).commit();
//                        active = franchiseFragment;
//                    }
//                    else
//                    {
//                    for (Fragment fragment : MainActivity.fragmentManager.getFragments()) {
//                        MainActivity.fragmentManager.beginTransaction().remove(fragment).commit();
//                    }
//                        FranchiseFragment fragment = new FranchiseFragment();
//                        FragmentManager fm = MainActivity.fragmentManager;
//                        FragmentTransaction ft = fm.beginTransaction();
//                        ft.replace(R.id.content, fragment).addToBackStack(null);
//                        ft.commit();
//                    }
                    MainActivity.fragmentClass= FranchiseFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else if(subMenuList.get(position).getId().equals("1002"))
                {
//                    Intent intent = new Intent(context, ChefListActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
//                    fragmentManager.beginTransaction().hide(active).show(chefFragment).commit();
//                    active = chefFragment;
//                    ChefFragment fragment = new ChefFragment();
//                    FragmentManager fm = MainActivity.fragmentManager;
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content, fragment).addToBackStack(null);
//                    ft.commit();
                    MainActivity.fragmentClass= ChefFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(subMenuList.get(position).getId().equals("1003"))
                {
//                    Intent intent = new Intent(context, ConsumerActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
//                    fragmentManager.beginTransaction().hide(active).show(consumerFragment).commit();
//                    active = consumerFragment;
//                    ConsumerFragment fragment = new ConsumerFragment();
//                    FragmentManager fm = MainActivity.fragmentManager;
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content, fragment).addToBackStack(null);
//                    ft.commit();
                    MainActivity.fragmentClass= ConsumerFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(subMenuList.get(position).getId().equals("2000"))
                {
//                    Intent intent = new Intent(context, DeliveryPartnerListActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
//                    fragmentManager.beginTransaction().hide(active).show(deliveryPartnerFragment).commit();
//                    active = deliveryPartnerFragment;
//                    DeliveryPartnerFragment fragment = new DeliveryPartnerFragment();
//                    FragmentManager fm = MainActivity.fragmentManager;
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content, fragment).addToBackStack(null);
//                    ft.commit();
                    MainActivity.fragmentClass= DeliveryPartnerFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(subMenuList.get(position).getId().equals("1004"))
                {
//                    Intent intent = new Intent(context, BulkOrderActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
//                    fragmentManager.beginTransaction().hide(active).show(bulkOrderFragment).commit();
//                    active = bulkOrderFragment;
//                    BulkOrderFragment fragment = new BulkOrderFragment();
//                    FragmentManager fm = MainActivity.fragmentManager;
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content, fragment).addToBackStack(null);
//                    ft.commit();
                    MainActivity.fragmentClass= BulkOrderFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(subMenuList.get(position).getId().equals("1005"))
                {
//                    Intent intent = new Intent(context, SubscriptionStatusActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
//                    fragmentManager.beginTransaction().hide(active).show(subscriptionFragment).commit();
//                    active = subscriptionFragment;
//                    SubscriptionFragment fragment = new SubscriptionFragment();
//                    FragmentManager fm = MainActivity.fragmentManager;
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content, fragment).addToBackStack(null);
//                    ft.commit();
                    MainActivity.fragmentClass= SubscriptionFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                else if(subMenuList.get(position).getId().equals("1006"))
                {
//
                    MainActivity.fragmentClass= UserFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                else if(subMenuList.get(position).getId().equals("1007"))
                {
//
                    MainActivity.fragmentClass= RoleFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                else if(subMenuList.get(position).getId().equals("2001"))
                {
//                    Intent intent = new Intent(context, PaymentGatewayListActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
//                    fragmentManager.beginTransaction().hide(active).show(paymentGatewayFragment).commit();
//                    active = paymentGatewayFragment;
//                    PaymentGatewayFragment fragment = new PaymentGatewayFragment();
//                    FragmentManager fm = MainActivity.fragmentManager;
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content, fragment).addToBackStack(null);
//                    ft.commit();
                    MainActivity.fragmentClass= PaymentGatewayFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(subMenuList.get(position).getId().equals("4001"))
                {
//                    Intent intent = new Intent(context, RevenueSharingActivity.class);
//                    context.startActivity(intent);
//                    navigation.setVisibility(View.GONE);
//                    fragmentManager.beginTransaction().hide(active).show(revenueSharingFragment).commit();
//                    active = revenueSharingFragment;
//                    RevenueSharingFragment fragment = new RevenueSharingFragment();
//                    FragmentManager fm = MainActivity.fragmentManager;
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.content, fragment).addToBackStack(null);
//                    ft.commit();
                    MainActivity.fragmentClass= RevenueSharingFragment.class;
                    try {
                        MainActivity.fragment  = (Fragment) MainActivity.fragmentClass.newInstance();
                        MainActivity.backStateName = MainActivity.fragment.getClass().getName();

                        FragmentManager manager = MainActivity.fragmentManager;
                        boolean fragmentPopped = manager.popBackStackImmediate (MainActivity.backStateName, 0);

                        if (!fragmentPopped) {

                            Fragment currentFragment = MainActivity.fragmentManager.findFragmentById(R.id.content);
                            // Return if the class are the same
                            if(currentFragment!=null&&!currentFragment.getClass().equals(MainActivity.fragment.getClass())) {
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content, MainActivity.fragment).addToBackStack(MainActivity.backStateName).commit();
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
