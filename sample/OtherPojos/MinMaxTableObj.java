package sample.OtherPojos;

public class MinMaxTableObj {

    public MinMaxTableObj(MinMaxCellObject parameterName, MinMaxCellObject min, MinMaxCellObject max, MinMaxCellObject toleranceMin, MinMaxCellObject toleranceMax, MinMaxCellObject uncertainty) {
        ParameterName = parameterName;
        Min = min;
        Max = max;
        ToleranceMin = toleranceMin;
        ToleranceMax = toleranceMax;
        Uncertainty = uncertainty;
    }

    public MinMaxCellObject getParameterName() {
        return ParameterName;
    }

    public void setParameterName(MinMaxCellObject parameterName) {
        ParameterName = parameterName;
    }

    public MinMaxCellObject getMin() {
        return Min;
    }

    public void setMin(MinMaxCellObject min) {
        Min = min;
    }

    public MinMaxCellObject getMax() {
        return Max;
    }

    public void setMax(MinMaxCellObject max) {
        Max = max;
    }

    public MinMaxCellObject getToleranceMin() {
        return ToleranceMin;
    }

    public void setToleranceMin(MinMaxCellObject toleranceMin) {
        ToleranceMin = toleranceMin;
    }

    public MinMaxCellObject getToleranceMax() {
        return ToleranceMax;
    }

    public void setToleranceMax(MinMaxCellObject toleranceMax) {
        ToleranceMax = toleranceMax;
    }

    public MinMaxCellObject getUncertainty() {
        return Uncertainty;
    }

    public void setUncertainty(MinMaxCellObject uncertainty) {
        Uncertainty = uncertainty;
    }

    private MinMaxCellObject ParameterName;

    private MinMaxCellObject  Min;

    private MinMaxCellObject Max;

    private MinMaxCellObject ToleranceMin;

    private MinMaxCellObject ToleranceMax;

    private MinMaxCellObject Uncertainty;


}
