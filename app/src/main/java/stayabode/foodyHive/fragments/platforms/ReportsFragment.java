package stayabode.foodyHive.fragments.platforms;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;

public class ReportsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reports,container,false);
        MainActivity.toolbar.setNavigationIcon(R.drawable.ic_dehaze_black);
        MainActivity.customIcon.setVisibility(View.GONE);
        MainActivity.rightMenu.setVisibility(View.GONE);
        MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.active = this;
        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.navigation.setVisibility(View.VISIBLE);
        MainActivity.mainBottomLayout.setVisibility(View.VISIBLE);
        MainActivity.rightMenu.setVisibility(View.GONE);
//        BottomNavigationView navBar = getActivity().findViewById(R.id.navigation);
//        navBar.setVisibility(View.VISIBLE);
//        navigation.setVisibility(View.VISIBLE);
    }
}
