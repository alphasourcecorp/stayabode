package stayabode.github.mikephil.charting.interfaces.dataprovider;

import stayabode.github.mikephil.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
