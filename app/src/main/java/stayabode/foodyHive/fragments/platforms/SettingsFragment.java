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
import androidx.fragment.app.FragmentTransaction;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;

public class SettingsFragment extends Fragment {


    TextView back;
    TextView pagetitle;
    TextView editProfile;
    TextView changeLanguage;
    TextView changeCurrency;
    Typeface fontBold;
    Typeface fontRegular;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings,container,false);
        fontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Nunito-Regular.ttf");
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
        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("Settings");
        back.setTypeface(fontRegular);
        pagetitle.setTypeface(fontBold);
        editProfile = rootView.findViewById(R.id.editProfile);
        changeLanguage = rootView.findViewById(R.id.changeLanguage);
        changeCurrency = rootView.findViewById(R.id.changeCurrency);
        editProfile.setTypeface(fontRegular);
        changeLanguage.setTypeface(fontRegular);
        changeCurrency.setTypeface(fontRegular);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditProfileFragment fragment = new EditProfileFragment();
                FragmentManager fm = MainActivity.fragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
               // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });


        changeCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentChangeCurrency fragment = new FragmentChangeCurrency();
                FragmentManager fm = MainActivity.fragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
               // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentChangeLanguage fragment = new FragmentChangeLanguage();
                FragmentManager fm = MainActivity.fragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, fragment).addToBackStack(null);
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return rootView;
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
//        navigation.setVisibility(View.VISIBLE);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
