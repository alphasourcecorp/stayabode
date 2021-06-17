package stayabode.foodyHive.fragments.platforms;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.platform.MainActivity;
import stayabode.github.mikephil.charting.charts.CombinedChart;
import stayabode.github.mikephil.charting.components.XAxis;
import stayabode.github.mikephil.charting.components.YAxis;
import stayabode.github.mikephil.charting.data.BarData;
import stayabode.github.mikephil.charting.data.BarDataSet;
import stayabode.github.mikephil.charting.data.BarEntry;
import stayabode.github.mikephil.charting.data.CombinedData;
import stayabode.github.mikephil.charting.data.Entry;
import stayabode.github.mikephil.charting.data.LineData;
import stayabode.github.mikephil.charting.data.LineDataSet;
import stayabode.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class MyProfileFragment extends Fragment {

    ImageView profileImage;
    TextView name;
    TextView city;
    TextView healthInfoheader;
    TextView height;
    TextView heightValue;
    TextView weight;
    TextView weightValue;
    TextView contactDetailheader;
    TextView locationHeader;
    TextView locationValue;
    TextView addressHeader;
    TextView addressLine1Value;
    TextView addressLine2Value;
    TextView addressLine3Value;
    TextView pincodeHeader;
    TextView pinCodeValue;
    TextView stateHeader;
    TextView stateValue;
    TextView countryHeader;
    TextView countryValue;
    TextView phoneHeader;
    TextView phoneValue;
    TextView emailHeader;
    TextView emailValue;
    Typeface fontBold;
    Typeface fontRegular;
    TextView toolbar_title;
    TextView pagetitle;
    private CombinedChart chart;

    private final int count = 4;
    protected final String[] nutritions = new String[] {
            "Protein", "Carbs", "Fiber", "Fat"
    };

    TextView back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_profile,container,false);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");
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
        pagetitle = rootView.findViewById(R.id.pagetitle);
        pagetitle.setText("My Profile");
        pagetitle.setTypeface(fontBold);
        MainActivity.navigation.setVisibility(View.GONE);
        MainActivity.mainBottomLayout.setVisibility(View.GONE);
        MainActivity.active = this;
        back = rootView.findViewById(R.id.back);
        back.setText("<Back");
        back.setTypeface(fontBold);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        });


        profileImage = rootView.findViewById(R.id.profileImage);
        name = rootView.findViewById(R.id.name);
        city = rootView.findViewById(R.id.city);
        healthInfoheader = rootView.findViewById(R.id.healthInfoheader);
        height = rootView.findViewById(R.id.height);
        heightValue = rootView.findViewById(R.id.heightValue);
        weight = rootView.findViewById(R.id.weight);
        weightValue = rootView.findViewById(R.id.weightValue);
        contactDetailheader = rootView.findViewById(R.id.contactDetailheader);
        locationHeader = rootView.findViewById(R.id.locationHeader);
        locationValue = rootView.findViewById(R.id.locationValue);
        addressHeader = rootView.findViewById(R.id.addressHeader);
        addressLine1Value = rootView.findViewById(R.id.addressLine1Value);
        addressLine2Value = rootView.findViewById(R.id.addressLine2Value);
        addressLine3Value = rootView.findViewById(R.id.addressLine3Value);
        pincodeHeader = rootView.findViewById(R.id.pincodeHeader);
        pinCodeValue = rootView.findViewById(R.id.pinCodeValue);
        stateHeader = rootView.findViewById(R.id.stateHeader);
        stateValue = rootView.findViewById(R.id.stateValue);
        countryHeader = rootView.findViewById(R.id.countryHeader);
        countryValue = rootView.findViewById(R.id.countryValue);
        phoneHeader = rootView.findViewById(R.id.phoneHeader);
        phoneValue = rootView.findViewById(R.id.phoneValue);
        emailHeader = rootView.findViewById(R.id.emailHeader);
        emailValue = rootView.findViewById(R.id.emailValue);

        locationHeader.setTypeface(fontBold);
        addressHeader.setTypeface(fontBold);
        pincodeHeader.setTypeface(fontBold);
        stateHeader.setTypeface(fontBold);
        countryHeader.setTypeface(fontBold);
        phoneHeader.setTypeface(fontBold);
        emailHeader.setTypeface(fontBold);
        locationValue.setTypeface(fontRegular);
        addressLine1Value.setTypeface(fontRegular);
        addressLine2Value.setTypeface(fontRegular);
        addressLine3Value.setTypeface(fontRegular);
        pinCodeValue.setTypeface(fontRegular);
        stateValue.setTypeface(fontRegular);
        countryValue.setTypeface(fontRegular);
        phoneValue.setTypeface(fontRegular);
        emailValue.setTypeface(fontRegular);
        contactDetailheader.setTypeface(fontBold);
        healthInfoheader.setTypeface(fontBold);
        height.setTypeface(fontBold);
        heightValue.setTypeface(fontRegular);
        weight.setTypeface(fontBold);
        weightValue.setTypeface(fontRegular);
        name.setTypeface(fontBold);
        city.setTypeface(fontRegular);


        chart = rootView.findViewById(R.id.combinedChart);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

//        Legend l = chart.getLegend();
//        l.setWordWrapEnabled(true);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);

//        YAxis rightAxis = chart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return nutritions[(int) value % nutritions.length];
            }
        });

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());
//        data.setData(generateBubbleData());
//        data.setData(generateScatterData());
//        data.setData(generateCandleData());
//        data.setValueTypeface(tfLight);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        chart.setData(data);
        chart.invalidate();

        return rootView;
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < count; index++)
            entries.add(new Entry(index + 0.5f, getRandom(15, 5)));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int index = 0; index < count; index++) {
            entries1.add(new BarEntry(0, getRandom(25, 25)));

            // stacked
            //  entries2.add(new BarEntry(0, new float[]{getRandom(13, 12), getRandom(13, 12)}));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColors(Color.rgb(61, 165, 255), Color.rgb(23, 197, 255));
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }

    protected float getRandom(float range, float start) {
        return (float) (Math.random() * range) + start;
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
