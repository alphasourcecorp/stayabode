package stayabode.github.mikephil.charting.interfaces.dataprovider;

import stayabode.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import stayabode.github.mikephil.charting.utils.Transformer;
import stayabode.github.mikephil.charting.components.YAxis;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
