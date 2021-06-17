package stayabode.foodyHive.fragments.platforms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;

public class EditProfileFragment extends Fragment {

    TextView back;
    TextView pagetitle;
    Typeface fontBold;
    Typeface fontRegular;

    EditText nameEditText;
    EditText locationEditText;
    EditText heightEditText;
    EditText weightEditText;
    EditText addressoneEditText;
    EditText addresstwoEditText;
    EditText addressthreeEditText;
    EditText pinCodeEditText;
    EditText stateEditText;
    EditText countryEditText;
    EditText phoneEditText;
    EditText emailEditText;


    TextView fullNameHeader;
    TextView locationHeader;
    TextView heightHeader;
    TextView weightHeader;
    TextView changeProfilePicHeader;
    TextView centimeter;
    TextView kilogram;
    TextView healthInfoHeader;
    TextView nameHeader;
    TextView contactHeader;
    TextView addressHeader;
    TextView addressHeader2;
    TextView addressHeader3;
    TextView pinCodeHeader;
    TextView stateHeader;
    TextView countryHeader;
    TextView phoneHeader;
    TextView emailHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_profile,container,false);
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
        pagetitle.setText("Edit Profile");
        back.setTypeface(fontRegular);
        pagetitle.setTypeface(fontBold);

        fullNameHeader = rootView.findViewById(R.id.fullNameHeader);
        locationHeader = rootView.findViewById(R.id.locationHeader);
        heightHeader = rootView.findViewById(R.id.heightHeader);
        weightHeader = rootView.findViewById(R.id.weightHeader);
        changeProfilePicHeader = rootView.findViewById(R.id.changeProfilePicHeader);
        centimeter = rootView.findViewById(R.id.centimeter);
        kilogram = rootView.findViewById(R.id.kilogram);
        healthInfoHeader = rootView.findViewById(R.id.healthInfoHeader);
        nameHeader = rootView.findViewById(R.id.nameHeader);
        contactHeader = rootView.findViewById(R.id.contactHeader);
        addressHeader = rootView.findViewById(R.id.addressHeader);
        addressHeader2 = rootView.findViewById(R.id.addressHeader2);
        addressHeader3 = rootView.findViewById(R.id.addressHeader3);
        pinCodeHeader = rootView.findViewById(R.id.pinCodeHeader);
        stateHeader = rootView.findViewById(R.id.stateHeader);
        countryHeader = rootView.findViewById(R.id.countryHeader);
        phoneHeader = rootView.findViewById(R.id.phoneHeader);
        emailHeader = rootView.findViewById(R.id.emailHeader);

        nameEditText = rootView.findViewById(R.id.nameEditText);
        locationEditText = rootView.findViewById(R.id.locationEditText);
        heightEditText = rootView.findViewById(R.id.heightEditText);
        weightEditText = rootView.findViewById(R.id.weightEditText);
        addressoneEditText = rootView.findViewById(R.id.addressoneEditText);
        addresstwoEditText = rootView.findViewById(R.id.addresstwoEditText);
        addressthreeEditText = rootView.findViewById(R.id.addressthreeEditText);
        pinCodeEditText = rootView.findViewById(R.id.pinCodeEditText);
        stateEditText = rootView.findViewById(R.id.stateEditText);
        countryEditText = rootView.findViewById(R.id.countryEditText);
        phoneEditText = rootView.findViewById(R.id.phoneEditText);
        emailEditText = rootView.findViewById(R.id.emailEditText);

        fullNameHeader.setTypeface(fontRegular);
        locationHeader.setTypeface(fontRegular);
        heightHeader.setTypeface(fontRegular);
        weightHeader.setTypeface(fontRegular);
        addressHeader.setTypeface(fontRegular);
        addressHeader2.setTypeface(fontRegular);
        addressHeader3.setTypeface(fontRegular);
        pinCodeHeader.setTypeface(fontRegular);
        stateHeader.setTypeface(fontRegular);
        countryHeader.setTypeface(fontRegular);
        phoneHeader.setTypeface(fontRegular);
        emailHeader.setTypeface(fontRegular);

        nameEditText.setTypeface(fontRegular);
        locationEditText.setTypeface(fontRegular);
        heightEditText.setTypeface(fontRegular);
        weightEditText.setTypeface(fontRegular);
        addressoneEditText.setTypeface(fontRegular);
        addresstwoEditText.setTypeface(fontRegular);
        addressthreeEditText.setTypeface(fontRegular);
        stateEditText.setTypeface(fontRegular);
        pinCodeEditText.setTypeface(fontRegular);
        countryEditText.setTypeface(fontRegular);
        phoneEditText.setTypeface(fontRegular);
        emailEditText.setTypeface(fontRegular);

        centimeter.setTypeface(fontRegular);
        kilogram.setTypeface(fontRegular);
        contactHeader.setTypeface(fontBold);
        nameHeader.setTypeface(fontBold);
        healthInfoHeader.setTypeface(fontBold);
        changeProfilePicHeader.setTypeface(fontBold);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
