package stayabode.foodyHive.fragments.intro;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import stayabode.foodyHive.R;

public class SliderItemFragment extends Fragment {

    private static final String ARG_POSITION = "slider-position";
    Typeface fontBold;
    Typeface fontsemiBold;
    Typeface fontRegular;
    // prepare all title ids arrays
    @StringRes
    private static final int[] PAGE_TITLES =
            new int[]{R.string.intro_first_main_text, R.string.intro_second_main_text, R.string.intro_third_main_text, R.string.intro_fourth_main_text};


    @StringRes
    private static final int[] PAGE_TITLES_SECOND =
            new int[]{R.string.intro_first_main_text_second, R.string.intro_second_main_text_second, R.string.intro_third_main_text_second, R.string.intro_fourth_main_text_second};


    @StringRes
    private static final int[] PAGE_TITLES_THIRD =
            new int[]{R.string.intro_first_main_text_third, R.string.intro_second_main_text_third, R.string.intro_third_main_text_third, R.string.intro_fourth_main_text_sthird};


    // prepare all subtitle ids arrays
    @StringRes
    private static final int[] PAGE_TEXT =
            new int[]{
                    R.string.intro_first_sub_text, R.string.intro_second_sub_text, R.string.intro_third_sub_text, R.string.intro_fourth_sub_text
            };
    // prepare all subtitle images arrays
    @StringRes
    private static final int[] PAGE_IMAGE =
            new int[]{
                    R.drawable.intro_screen_one, R.drawable.intro_screen_two, R.drawable.intro_screen_three, R.drawable.intro_screen_four
            };

    // prepare all background images arrays
    @StringRes
    private static final int[] BG_IMAGE = new int[]{
            R.drawable.ic_bg_blue, R.drawable.ic_bg_blue, R.drawable.ic_bg_blue,
            R.drawable.ic_bg_blue
    };

    private int position;

    public SliderItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     *
     * @return A new instance of fragment SliderItemFragment.
     */

    public static SliderItemFragment newInstance(int position) {
        SliderItemFragment fragment = new SliderItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }

        fontsemiBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        fontBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_slider_item, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // set page background
        try {
            view.setBackground(requireActivity().getDrawable(BG_IMAGE[position]));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    TextView title = view.findViewById(R.id.textView);
    TextView textViewsub = view.findViewById(R.id.textViewsub);
    TextView titleText = view.findViewById(R.id.textView2);
    ImageView imageView = view.findViewById(R.id.imageView);
    TextView textViewsubthird = view.findViewById(R.id.textViewsubthird);

    // set page title
    title.setText(PAGE_TITLES[position]);
    // set page sub title text
    titleText.setText(PAGE_TEXT[position]);
    textViewsub.setText(PAGE_TITLES_SECOND[position]);
    textViewsubthird.setText(PAGE_TITLES_THIRD[position]);
        textViewsub.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.VISIBLE);
    if(textViewsubthird.getText().toString().equals(""))
    {
      textViewsubthird.setVisibility(View.GONE);
    }
    else
    {
      textViewsubthird.setVisibility(View.VISIBLE);
    }
    title.setTypeface(fontBold);
    textViewsub.setTypeface(fontBold);
    textViewsubthird.setTypeface(fontBold);
    titleText.setTypeface(fontRegular);
    // set page image
    imageView.setImageResource(PAGE_IMAGE[position]);
    }
}
