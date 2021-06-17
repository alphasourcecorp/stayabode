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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.foodyHive.fragments.platforms.HomeFragment;
import stayabode.foodyHive.fragments.platforms.NotificationFragment;
import stayabode.foodyHive.models.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MainMenu> mainMenuList = new ArrayList<>();
    Context context;
    Typeface fontBold;
    Typeface fontRegular;
    int expandPosition = -1;

    public MainMenuAdapter(Context context,List<MainMenu> mainMenuList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.mainMenuList = mainMenuList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.side_bar_menu_custom_item_layout,parent,false);
        return new MainMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MainMenuItemViewHolder mainMenuItemViewHolder = (MainMenuItemViewHolder)holder;
        mainMenuItemViewHolder.name.setTypeface(fontRegular);
        mainMenuItemViewHolder.name.setText(mainMenuList.get(position).getName());

        mainMenuItemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mainMenuItemViewHolder.recyclerView.setAdapter(new SubMenuAdapter(context,mainMenuList.get(position).getSubMenuList(),fontBold,fontRegular));

        if(mainMenuList.get(position).getSubMenuList().size() == 0)
        {
            mainMenuItemViewHolder.arrowImage.setVisibility(View.GONE);
        }
        else
        {
            mainMenuItemViewHolder.arrowImage.setVisibility(View.VISIBLE);
        }

//        Typeface font = Typeface.createFromAsset(context.getAssets(), "FontAwesome.ttf");
//        mainMenuItemViewHolder.icon.setTypeface(font);

        //mainMenuItemViewHolder.icon.setText(String.valueOf("u"+mainMenuList.get(position).getImage()));
//        mainMenuItemViewHolder.icon.setText(mainMenuList.get(position).getImage());
        mainMenuItemViewHolder.icon.setText(new String(Character.toChars(Integer.parseInt(
                mainMenuList.get(position).getImage(), 16))));

        Log.d("Image",mainMenuList.get(position).getImage() + " Main Menu Image");

//        if (position == expandPosition) {
//            mainMenuItemViewHolder.recyclerView.setVisibility(View.VISIBLE);
//        } else {
//            mainMenuItemViewHolder.recyclerView.setVisibility(View.GONE);
//        }

        if (position == expandPosition) {
            mainMenuItemViewHolder.recyclerView.setVisibility(View.VISIBLE);
        } else {
            mainMenuItemViewHolder.recyclerView.setVisibility(View.GONE);
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
            mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }


        if(position == expandPosition)
        {
            mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
        else
        {
            mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorWhite));
            mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }

        if(mainMenuList.get(position).getId().equals("5ebaaa4812f1891a2429fc93"))
        {
            mainMenuItemViewHolder.notificationsbadge.setVisibility(View.VISIBLE);
        }
        else
        {
            mainMenuItemViewHolder.notificationsbadge.setVisibility(View.GONE);
        }

        mainMenuItemViewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainMenuList.get(position).getSubMenuList().size() == 0)
                {
                    MainActivity.drawer.closeDrawers();

                    if(mainMenuList.get(position).getId().equals("5ebaaa4812f1891a2429fc93"))
                    {
//                        navigation.setVisibility(View.VISIBLE);
                        MainActivity.navigation.setSelectedItemId(R.id.navigation_notification);

//                        if(nowActiveFragment == homeFragment || nowActiveFragment == ordersFragment || nowActiveFragment== franchiseFragment || nowActiveFragment == reportsFragment || nowActiveFragment == notificationFragment)
//                        {
//                            fragmentManager.beginTransaction().hide(active).show(franchiseFragment).commit();
//                            active = franchiseFragment;
//                        }
//                        else
//                        {
//                        for (Fragment fragment : MainActivity.fragmentManager.getFragments()) {
//                            MainActivity.fragmentManager.beginTransaction().remove(fragment).commit();
//                        }
//                            NotificationFragment fragment = new NotificationFragment();
//                            FragmentManager fm = MainActivity.fragmentManager;
//                            FragmentTransaction ft = fm.beginTransaction();
//                            ft.replace(R.id.content, fragment).addToBackStack(null);
//                            MainActivity.nowActiveFragment = fragment;
//                            ft.commit();

                        MainActivity.fragmentClass= NotificationFragment.class;
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

//                        }

                    }
                    if(mainMenuList.get(position).getId().equals("5ebaa7e245fa320568eaea9e"))
                    {
//                        navigation.setVisibility(View.VISIBLE);
                        MainActivity.navigation.setSelectedItemId(R.id.navigation_my_dashboard);
//                        if(nowActiveFragment == homeFragment || nowActiveFragment == ordersFragment || nowActiveFragment== franchiseFragment || nowActiveFragment == reportsFragment || nowActiveFragment == notificationFragment)
//                        {
//                            fragmentManager.beginTransaction().hide(active).show(franchiseFragment).commit();
//                            active = franchiseFragment;
//                        }
//                        else
//                        {
//                        for (Fragment fragment : MainActivity.fragmentManager.getFragments()) {
//                            MainActivity.fragmentManager.beginTransaction().remove(fragment).commit();
//                        }

//                            HomeFragment fragment = new HomeFragment();
//                            FragmentManager fm = MainActivity.fragmentManager;
//                            FragmentTransaction ft = fm.beginTransaction();
//                            ft.replace(R.id.content, fragment).addToBackStack(null);
//                            MainActivity.nowActiveFragment = fragment;
//                            ft.commit();
                        MainActivity.fragmentClass= HomeFragment.class;
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

//                        }

                    }

                }
                else
                {
                    expandPosition = position;
                    notifyDataSetChanged();

////                    MainMenuItemViewHolder holder = (MainMenuItemViewHolder) v.getTag();
////
////
////                    // Check for an expanded view, collapse if you find one
////                    if (expandPosition >= 0) {
////                        int prev = expandPosition;
////                        notifyItemChanged(prev);
////                    }
////                    // Set the current position to "expanded"
////                    expandPosition = mainMenuItemViewHolder.getAdapterPosition();
////                    notifyItemChanged(expandPosition);
////
////                    TransitionManager.beginDelayedTransition(mainMenuItemViewHolder.recyclerView);
//                    boolean shouldExpand = ((MainMenuItemViewHolder) holder).recyclerView.getVisibility() == View.GONE;
//
//                    ChangeBounds transition = new ChangeBounds();
//                    transition.setDuration(125);
//
//                    if (shouldExpand) {
//                        ((MainMenuItemViewHolder) holder).recyclerView.setVisibility(View.VISIBLE);
//                        ((MainMenuItemViewHolder) holder).arrowImage.setText(R.string.beforeExpandIcon);
//                        mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
//                        mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBlack));
//                        mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorBlack));
//                        mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorBlack));
//                    } else {
//                        ((MainMenuItemViewHolder) holder).recyclerView.setVisibility(View.GONE);
//                        ((MainMenuItemViewHolder) holder).arrowImage.setText(R.string.afterExpandIcon);
//                        mainMenuItemViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
//                        mainMenuItemViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
//                        mainMenuItemViewHolder.arrowImage.setTextColor(context.getResources().getColor(R.color.colorWhite));
//                        mainMenuItemViewHolder.icon.setTextColor(context.getResources().getColor(R.color.colorWhite));
//                    }
//
//                    TransitionManager.beginDelayedTransition(((MainMenuItemViewHolder) holder).recyclerView, transition);
//                    ((MainMenuItemViewHolder) holder).itemView.setActivated(shouldExpand);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mainMenuList.size();
    }

    public class MainMenuItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView icon;
        TextView arrowImage;
        TextView name;
        TextView notificationsbadge;
        RecyclerView recyclerView;
        LinearLayout mainLayout;

        public MainMenuItemViewHolder(@NonNull View itemView) {
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
