package stayabode.foodyHive.adapters.intro;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.models.SliderUtils;
//import com.foodyHive.utils.CustomRequestIntro;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    public Context context;
    public LayoutInflater layoutInflater;
    public List<SliderUtils> sliderImg;
    public ImageLoader imageLoader;

    public ViewPagerAdapter(List sliderImg, Context context) {
        this.sliderImg = sliderImg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_intro_slider_item, null);

        SliderUtils utils = sliderImg.get(position);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);
        TextView subText = view.findViewById(R.id.textViewsubthird);


        Glide.with(context).load(utils.getSliderImageUrl()).into(imageView);

        subText.setText(utils.getSubtext());

        String colorText= "";

        if(utils.getMainText().equals("Health on your finger tips")){

            colorText= "<font color=\"#F7B917\"><bold>" + "Health on your " + "</bold></font>" + "finger tips" + "<font color=\"#FFFFFF\"><bold>";


        }
        else if(utils.getMainText().equals("Daily meals, sorted!"))
        {

            colorText= "<font color=\"#F7B917\"><bold>" + "Daily meals, " + "</bold></font>" + "sorted" + "<font color=\"#FFFFFF\"><bold>";
        }
        else if(utils.getMainText().equals("Catering for all occasions"))
        {

            colorText= "<font color=\"#F7B917\"><bold>" + "Catering for all " + "</bold></font>" + "occasions" + "<font color=\"#FFFFFF\"><bold>";
        }
        else if(utils.getMainText().equals("Live Stream your food preparation"))
        {

            colorText= "<font color=\"#F7B917\"><bold>" + "Live Stream " + "</bold></font>" + "your food preparation" + "<font color=\"#FFFFFF\"><bold>";
        }

        textView.setText(Html.fromHtml(colorText));
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
