package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.AboutUsAndGetHelpWebViewActivity;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.fragments.consumers.ConsumerHomeOnDemandFragments;
import stayabode.foodyHive.fragments.consumers.ConsumerSavedItemsFragment;
import stayabode.foodyHive.fragments.consumers.ConsumerYouOrdersFragment;
import stayabode.foodyHive.models.MainMenu;
import stayabode.foodyHive.utils.SaveSharedPreference;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.consumerMainActivity;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.consumerNotificationFragment;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.consumerOnDemandFragment;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.consumerSavedItemsFragment;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.consumerYouOrdersFragment;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.consumernavigation;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.favouritesCount;
import static stayabode.foodyHive.activities.consumers.ConsumerMainActivity.opendOrdersCount;

public class ConsumerSideMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MainMenu> mainMenuList = new ArrayList<>();
    Context context;
    Typeface fontBold;
    Typeface fontRegular;
    int expandPosition = -1;

    public ConsumerSideMenuAdapter(Context context,List<MainMenu> mainMenuList,Typeface fontBold,Typeface fontRegular)
    {
        this.context = context;
        this.mainMenuList = mainMenuList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumer_side_menu_item,parent,false);
        return new MainMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MainMenuItemViewHolder mainMenuItemViewHolder = (MainMenuItemViewHolder)holder;
        mainMenuItemViewHolder.name.setTypeface(fontRegular);
        mainMenuItemViewHolder.count.setTypeface(fontRegular);
        mainMenuItemViewHolder.name.setText(mainMenuList.get(position).getName());

        mainMenuItemViewHolder.icon.setText(new String(Character.toChars(Integer.parseInt(
                mainMenuList.get(position).getImage(), 16))));

        Log.d("Image",mainMenuList.get(position).getImage() + " Main Menu Image");


        if(mainMenuList.get(position).getName().equals("Home"))
        {
            // Home
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.customer_home_left_menu));
            mainMenuItemViewHolder.count.setText("");
        }
        else if(mainMenuList.get(position).getName().equals("Your Orders"))
        {
            //Your Orders
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.customer_orders_left_menu));
            mainMenuItemViewHolder.count.setText(opendOrdersCount);

        }
        else if(mainMenuList.get(position).getName().equals("Favourites")||mainMenuList.get(position).getName().equals("Favourite")||mainMenuList.get(position).getName().equals("Favorites")||mainMenuList.get(position).getName().equals("Favorite"))
        {
            //Saved
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.customer_favourite_left_menu));
            mainMenuItemViewHolder.count.setText(favouritesCount);
        }
        else if(mainMenuList.get(position).getName().equals("Subscriptions"))
        {
            //Subscriptions
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.customer_subscription_menu));
            mainMenuItemViewHolder.count.setText("");
        }
        else if(mainMenuList.get(position).getName().equals("Events"))
        {
            //Events
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.customer_events_menu));
            mainMenuItemViewHolder.count.setText("");
        }
        else if(mainMenuList.get(position).getName().equals("Notifications")||mainMenuList.get(position).getName().equalsIgnoreCase("refer a friend"))
        {
            // Notifications
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.refer_icon));
            mainMenuItemViewHolder.count.setText("");
        }
        else if(mainMenuList.get(position).getName().equals("Get Help"))
        {
            //Get Help
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.customer_get_help_menu));
            mainMenuItemViewHolder.count.setText("");
        }
        else if(mainMenuList.get(position).getName().equals("About"))
        {
            //About
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.customer_about_menu));
            mainMenuItemViewHolder.count.setText("");
        }
        else if(mainMenuList.get(position).getName().equals("Logout"))
        {
            // Logout
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.customer_logout_menu));
            mainMenuItemViewHolder.count.setText("");
            if(SaveSharedPreference.getLoggedInUserRole(context).equals(""))
            {
                mainMenuItemViewHolder.name.setText("Login");
            }
            else
            {
                mainMenuItemViewHolder.name.setText("Logout");
            }

        }else{
            mainMenuItemViewHolder.menuIcon.setImageDrawable(context.getDrawable(R.drawable.refer_icon));
            mainMenuItemViewHolder.count.setText("");
        }


        mainMenuItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainMenuList.get(position).getSubMenuList().size() == 0)
                {
                    ConsumerMainActivity.consumerDrawer.closeDrawers();

                    if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6677"))
                    {
                        try {
                            ConsumerHomeOnDemandFragments.updateTopOFFersItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            ConsumerHomeOnDemandFragments.updatePopularChoices();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            ConsumerHomeOnDemandFragments.updateRecentlyViewedItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        consumernavigation.setSelectedItemId(R.id.navigation_my_dashboard);
                        ConsumerMainActivity.consumerfragmentManager.beginTransaction().hide(ConsumerMainActivity.active).show(consumerOnDemandFragment).commit();
                        ConsumerMainActivity.active=consumerOnDemandFragment;
                    }
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6678"))
                    {
                        //Your Orders
                        consumernavigation.setSelectedItemId(R.id.navigation_orders);
                        try {
                            ConsumerYouOrdersFragment.recyclerViewInfo.getAdapter().notifyDataSetChanged();
                            ConsumerYouOrdersFragment.recyclerViewOrders.getAdapter().notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        ConsumerMainActivity.consumerfragmentManager.beginTransaction().hide(ConsumerMainActivity.active).show(consumerYouOrdersFragment).commit();
                        ConsumerMainActivity.active=consumerYouOrdersFragment;
                    }
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6679"))
                    {
                        //Saved
                        consumernavigation.setSelectedItemId(R.id.navigation_saved);
                        try {
                            ConsumerSavedItemsFragment.getSavedOrders();
                            ConsumerSavedItemsFragment.recyclerView.getAdapter().notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        ConsumerMainActivity.consumerfragmentManager.beginTransaction().hide(ConsumerMainActivity.active).show(consumerSavedItemsFragment).commit();
                        ConsumerMainActivity.active=consumerSavedItemsFragment;
                    }
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6680"))
                    {
                        //Subscriptions
                    }
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6681"))
                    {
                        //Events
                    }
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6682"))
                    {
                        // Notifications
                        consumernavigation.setSelectedItemId(R.id.navigation_notification);
                        try {
                            ConsumerSavedItemsFragment.getSavedOrders();
                            ConsumerSavedItemsFragment.recyclerView.getAdapter().notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        ConsumerMainActivity.consumerfragmentManager.beginTransaction().hide(ConsumerMainActivity.active).show(consumerNotificationFragment).commit();
                        ConsumerMainActivity.active=consumerNotificationFragment;
                    }
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6683"))
                    {
                        //Get Help

                        Intent intent = new Intent(context, AboutUsAndGetHelpWebViewActivity.class);
                        intent.putExtra("From","GetHelp");
                        context.startActivity(intent);

                    }
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6684"))
                    {
                        //About
                        Intent intent = new Intent(context, AboutUsAndGetHelpWebViewActivity.class);
                        intent.putExtra("From","About");
                        context.startActivity(intent);
                    }
                    else if(mainMenuList.get(position).getId().equals("5e82ea476c34d13b706b6685"))
                    {
                        // Logout

                        if(SaveSharedPreference.getLoggedInUserRole(context).equals(""))
                        {
                            consumerMainActivity.startAuth();
                        }
                        else
                        {
                            consumerMainActivity.logout();
                        }

                    }
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
        TextView name;
        TextView count;
        ImageView menuIcon;

        public MainMenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
            count = itemView.findViewById(R.id.count);
            menuIcon = itemView.findViewById(R.id.menuIcon);
        }
    }

}
