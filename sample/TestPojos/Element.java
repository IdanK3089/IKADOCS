package sample.TestPojos;

import java.util.ArrayList;

public class Element {

    String element;
    String result;
    ArrayList<String> minStandard;
    ArrayList<String> maxStandard;

    public Element(String element, String result, ArrayList<String> minStandard, ArrayList<String> maxStandard) {
        this.element = element;
        this.result = result;
        this.minStandard = minStandard;
        this.maxStandard = maxStandard;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<String> getMinStandard() {
        return minStandard;
    }

    public void setMinStandard(ArrayList<String> minStandard) {
        this.minStandard = minStandard;
    }

    public ArrayList<String> getMaxStandard() {
        return maxStandard;
    }

    public void setMaxStandard(ArrayList<String> maxStandard) {
        this.maxStandard = maxStandard;
    }
}
