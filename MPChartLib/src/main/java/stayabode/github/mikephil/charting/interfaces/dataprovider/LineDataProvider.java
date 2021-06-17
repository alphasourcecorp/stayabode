package stayabode.github.mikephil.charting.interfaces.dataprovider;

import stayabode.github.mikephil.charting.components.YAxis;
import stayabode.github.mikephil.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
