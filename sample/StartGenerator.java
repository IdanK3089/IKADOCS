package sample;

import com.github.daytron.simpledialogfx.data.HeaderColorStyle;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;
import sample.Controllers.*;
import sample.ExcelHandle.CSVTensile;
import sample.ExcelHandle.ExcelChemical;
import sample.OtherPojos.CSVRow;
import sample.TestPojos.ParamObject;



import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class StartGenerator {

    //Static but referenced only in this class
    static ArrayList<Integer> FailedList = new ArrayList<>();

    public static ArrayList<ExcelChemical> chemicals = new ArrayList<>();

    static public ArrayList<ExcelChemical> roundedList = new ArrayList<>();

    static public ArrayList<ArrayList<CSVTensile>> TensileData = new ArrayList<>();

    static public String rootDir;

    static Variables variables;
    static Docx docx;


    private static void DataFiller(int elementNumber) {

    }


    public static int CountCSVClm(File file) throws IOException {

        int rowCounter = 0;

        CSVFormat csvFormat;
        csvFormat = CSVFormat.DEFAULT.withIgnoreEmptyLines(false);

        CSVRow row = null;


        try (Reader in = new InputStreamReader(new FileInputStream(file))) {
            CSVParser parse = csvFormat.parse(in);
            for (CSVRecord record : parse.getRecords()) {

                rowCounter++;


            }

        }

        return rowCounter;
    }

    public static void ReadTensileFile(File csvFile) throws IOException {

        CSVFormat csvFormat;
        csvFormat = CSVFormat.DEFAULT.withIgnoreEmptyLines(false);

        CSVRow row = null;

        ArrayList<String> HelpList = new ArrayList<>();
        ArrayList<ArrayList<String>> BigList = new ArrayList<>();

        int rowNumber = CountCSVClm(csvFile);


        try (Reader in = new InputStreamReader(new FileInputStream(csvFile))) {
            CSVParser parse = csvFormat.parse(in);
            for (CSVRecord record : parse.getRecords()) {
                HelpList.clear();

                row = new CSVRow();
                for (int i = 0; i < record.size(); i++) {

                    HelpList.add(record.get(i));

                }

                BigList.add(new ArrayList<>(HelpList));


            }


            BigList.remove(0);

            //ToDo fix an error here

            int Size = BigList.get(0).size();

            for (ArrayList<String> L : new ArrayList<ArrayList<String>>(BigList)) {

                if (L.size() != Size) {

                    BigList.remove(L);
                    rowNumber--;

                }


            }


            for (int i = 0; i < rowNumber - 3; i++) {

                ArrayList<CSVTensile> Temp = new ArrayList<>();

                for (int j = 0; j < BigList.get(0).size(); j++) {

                    CSVTensile t = new CSVTensile();

                    t.setParameterName(BigList.get(0).get(j));
                    t.setUnit(BigList.get(1).get(j));
                    t.setValue(BigList.get(i + 2).get(j));

                    Temp.add(t);
                }


                TensileData.add(Temp);


            }


        }


    }

    public static double round(double num, int multipleOf) {
        return Math.floor((num + (double) multipleOf / 2) / multipleOf) * multipleOf;
    }

    public static void RoundNADCAP() {

        if (!TensileData.isEmpty()) {

            for (ArrayList<CSVTensile> T : TensileData) {


                for (CSVTensile OneParam : T) {

                    if (OneParam.getUnit().contains("MPa")) {

                        if (Double.parseDouble(OneParam.getValue()) >= 1000) {


                            OneParam.setValue(String.valueOf(Math.round(round(Double.parseDouble(OneParam.getValue()), 10))));


                        } else if ((Double.parseDouble(OneParam.getValue()) >= 500) && (Double.parseDouble(OneParam.getValue()) < 1000)) {


                            OneParam.setValue(String.valueOf(Math.round(round(Double.parseDouble(OneParam.getValue()), 5))));


                        } else if ((Double.parseDouble(OneParam.getValue()) < 500)) {

                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(0, RoundingMode.HALF_EVEN);
                            double d = bd.doubleValue();
                            int b = (int) d;
                            String g = String.valueOf(b);

                            OneParam.setValue(g);

                        }


                    } else if (OneParam.getUnit().contains("ksi") || OneParam.getUnit().contains("KSI")) {

                        if (Double.parseDouble(OneParam.getValue()) >= 100) {

                            if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) > 5) {

                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(1, RoundingMode.HALF_UP).toString();


                                OneParam.setValue(bd);

                            } else if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) < 5) {
                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(1, RoundingMode.HALF_DOWN).toString();

                                OneParam.setValue(bd);


                            } else if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) == 5) {


                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(1, RoundingMode.HALF_EVEN).toString();
                                OneParam.setValue(bd);


                            }


                        } else if ((Double.parseDouble(OneParam.getValue()) >= 50) && (Double.parseDouble(OneParam.getValue()) < 100)) {
                            OneParam.setValue(String.valueOf(roundToHalf(Double.parseDouble(OneParam.getValue()))));


                        } else if ((Double.parseDouble(OneParam.getValue()) < 50)) {

                            if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) > 5) {

                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_UP).toString();

                                OneParam.setValue(bd);

                            } else if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) < 5) {
                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_DOWN).toString();
                                OneParam.setValue(bd);


                            } else if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) == 5) {


                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_EVEN).toString();

                                OneParam.setValue(bd);


                            }

                        }


                    } else if (OneParam.getParameterName().contains("area") || OneParam.getParameterName().contains("Area")) {
                        if (Double.parseDouble(OneParam.getValue()) >= 10) {

                            String g = String.valueOf(Math.round(Double.parseDouble(OneParam.getValue())));
                            OneParam.setValue(g);

                        } else if ((Double.parseDouble(OneParam.getValue()) >= 5) && (Double.parseDouble(OneParam.getValue()) < 10)) {

                            OneParam.setValue(String.valueOf(roundToHalf(Double.parseDouble(OneParam.getValue()))));


                        } else if ((Double.parseDouble(OneParam.getValue()) < 5)) {

                            if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) > 5) {

                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_UP).toString();
                                OneParam.setValue(bd);

                            } else if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) < 5) {
                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_DOWN).toString();

                                OneParam.setValue(bd);


                            } else if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) == 5) {
                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_EVEN).toString();

                                OneParam.setValue(bd);


                            }

                        }

                    } else if (OneParam.getParameterName().contains("Elongation") && OneParam.getParameterName().contains("%")) {

                        if (Double.parseDouble(OneParam.getValue()) >= 10) {

                            String g = String.valueOf(Math.round(Double.parseDouble(OneParam.getValue())));
                            OneParam.setValue(g);


                        } else if ((Double.parseDouble(OneParam.getValue()) >= 5) && (Double.parseDouble(OneParam.getValue()) < 10)) {
                            OneParam.setValue(String.valueOf(roundToHalf(Double.parseDouble(OneParam.getValue()))));

                        } else if ((Double.parseDouble(OneParam.getValue()) < 5)) {

                            if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) > 5) {

                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_UP).toString();

                                OneParam.setValue(bd);

                            } else if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) < 5) {
                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_DOWN).toString();

                                OneParam.setValue(bd);


                            } else if (Character.getNumericValue(OneParam.getValue().charAt(OneParam.getValue().length() - 1)) == 5) {
                                String bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(3, RoundingMode.HALF_EVEN).toString();


                                OneParam.setValue(bd);


                            }

                        }
                    }


                }


            }


        }


    }

    public static void RoundAroundSTD(ArrayList<ParamObject> TensileSTDList) {


        int decimalPlacesMin = 0;
        int decimalPlacesMax = 0;


        //First have to round around standard, this rounding just leaves the amount of
        //decimal places
        if (StandardSelectController.StandardSelected == true) {
            //Round According to Standard


            if (!TensileData.isEmpty()) {

                for (ArrayList<CSVTensile> T : TensileData) {


                    for (CSVTensile OneParam : T) {

                        //Running over all the numbers
                        if ((OneParam.getParameterName().contains("Maximum") || OneParam.getParameterName().contains("maximum")) && !OneParam.getParameterName().contains("Load")) {


                            for (ParamObject p : TensileSTDList) {


                                if (OneParam.getUnit().contains("MPa")) {

                                    if (p.getParamName().contains("UTS") && p.getParamName().contains("MPa")) {

                                        decimalPlacesMin = 0;
                                        decimalPlacesMax = 0;
                                        //Look at how many decimal places there are at the min and max of the STD, and to round according to that the data from the list
                                        String[] stdSplitMin = p.getMin().split("\\.");
                                        String[] stdSplitMax = p.getMax().split("\\.");

                                        if (Numeric(p.getMin())) {
                                            if (!p.getMin().equals("-"))
                                                if (stdSplitMin.length > 1) {
                                                    decimalPlacesMin = stdSplitMin[1].length();
                                                }

                                        }
                                        if (Numeric(p.getMax())) {
                                            if (!p.getMax().equals("-"))
                                                if (stdSplitMax.length > 1) {
                                                    decimalPlacesMax = stdSplitMax[1].length();
                                                }

                                        }
                                        if (decimalPlacesMax > decimalPlacesMin) {

                                            //Round around max

                                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMax, RoundingMode.HALF_EVEN);
                                            double d = bd.doubleValue();
                                            String g = String.valueOf(d);

                                            OneParam.setValue(g);

                                        } else if (decimalPlacesMax <= decimalPlacesMin) {

                                            if ((decimalPlacesMax == decimalPlacesMin) && (decimalPlacesMax == 0)) {


                                            } else {
                                                //Round around min
                                                BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMin, RoundingMode.HALF_EVEN);
                                                double d = bd.doubleValue();
                                                String g = String.valueOf(d);

                                                OneParam.setValue(g);

                                            }


                                        }


                                    }


                                } else if ((OneParam.getUnit().contains("KSI") || (OneParam.getUnit().contains("ksi")))) {
                                    if (p.getParamName().contains("UTS") && p.getParamName().contains("KSI")) {

                                        decimalPlacesMin = 0;
                                        decimalPlacesMax = 0;
                                        //Look at how many decimal places there are at the min and max of the STD, and to round according to that the data from the list
                                        String[] stdSplitMin = p.getMin().split("\\.");
                                        String[] stdSplitMax = p.getMax().split("\\.");

                                        if (Numeric(p.getMin())) {
                                            if (!p.getMin().equals("-"))
                                                if (stdSplitMin.length > 1) {
                                                    decimalPlacesMin = stdSplitMin[1].length();
                                                }

                                        }
                                        if (Numeric(p.getMax())) {
                                            if (!p.getMax().equals("-"))
                                                if (stdSplitMax.length > 1) {
                                                    decimalPlacesMax = stdSplitMax[1].length();
                                                }

                                        }


                                        if (decimalPlacesMax > decimalPlacesMin) {

                                            //Round around max

                                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMax, RoundingMode.HALF_EVEN);
                                            double d = bd.doubleValue();
                                            String g = String.valueOf(d);

                                            OneParam.setValue(g);

                                        } else if (decimalPlacesMax <= decimalPlacesMin) {
                                            //Round around min
                                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMin, RoundingMode.HALF_EVEN);
                                            double d = bd.doubleValue();
                                            String g = String.valueOf(d);

                                            OneParam.setValue(g);


                                        }

                                    }

                                }


                            }


                        } else if ((OneParam.getParameterName().contains("Elongation") && OneParam.getParameterName().contains("%"))) {
                            decimalPlacesMin = 0;
                            decimalPlacesMax = 0;
                            for (ParamObject p : TensileSTDList) {


                                if (p.getParamName().contains("Elongation") && p.getParamName().contains("%")) {

                                    //Look at how many decimal places there are at the min and max of the STD, and to round according to that the data from the list
                                    String[] stdSplitMin = p.getMin().split("\\.");
                                    String[] stdSplitMax = p.getMax().split("\\.");
                                    String[] resultSplit = OneParam.getValue().split("\\.");

                                    if (Numeric(p.getMin())) {
                                        if (!p.getMin().equals("-"))
                                            if (stdSplitMin.length > 1) {
                                                decimalPlacesMin = stdSplitMin[1].length();
                                            }

                                    }
                                    if (Numeric(p.getMax())) {
                                        if (!p.getMax().equals("-"))
                                            if (stdSplitMax.length > 1) {
                                                decimalPlacesMax = stdSplitMax[1].length();
                                            }

                                    }

                                    if (decimalPlacesMax > decimalPlacesMin) {

                                        //Round around max

                                        BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMax, RoundingMode.HALF_EVEN);
                                        double d = bd.doubleValue();
                                        String g = String.valueOf(d);

                                        OneParam.setValue(g);

                                    } else if (decimalPlacesMax <= decimalPlacesMin) {
                                        //Round around min

                                        if ((decimalPlacesMax == decimalPlacesMin) && (decimalPlacesMax == 0)) {
                                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMin, RoundingMode.HALF_EVEN);
                                            double d = bd.doubleValue();
                                            String g = String.valueOf(d);

                                            String[] std = g.split("\\.");

                                            OneParam.setValue(std[0]);

                                        } else {

                                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMin, RoundingMode.HALF_EVEN);
                                            double d = bd.doubleValue();
                                            String g = String.valueOf(d);
                                            OneParam.setValue(g);
                                        }

                                    }


                                }


                            }


                        } else if ((OneParam.getParameterName().contains("Area") || OneParam.getParameterName().contains("area"))) {
                            decimalPlacesMin = 0;
                            decimalPlacesMax = 0;
                            for (ParamObject p : TensileSTDList) {


                                if (p.getParamName().contains("Area")) {

                                    //Look at how many decimal places there are at the min and max of the STD, and to round according to that the data from the list
                                    String[] stdSplitMin = p.getMin().split("\\.");
                                    String[] stdSplitMax = p.getMax().split("\\.");

                                    if (Numeric(p.getMin())) {
                                        if (!p.getMin().equals("-"))
                                            if (stdSplitMin.length > 1) {
                                                decimalPlacesMin = stdSplitMin[1].length();
                                            }

                                    }
                                    if (Numeric(p.getMax())) {
                                        if (!p.getMax().equals("-"))
                                            if (stdSplitMax.length > 1) {
                                                decimalPlacesMax = stdSplitMax[1].length();
                                            }

                                    }

                                    if (decimalPlacesMax > decimalPlacesMin) {

                                        //Round around max

                                        BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMax, RoundingMode.HALF_EVEN);
                                        double d = bd.doubleValue();
                                        String g = String.valueOf(d);

                                        OneParam.setValue(g);

                                    } else if (decimalPlacesMax <= decimalPlacesMin) {
                                        //Round around min

                                        if ((decimalPlacesMax == decimalPlacesMin) && (decimalPlacesMax == 0)) {

                                            OneParam.setValue(String.valueOf(Math.round(Double.parseDouble(OneParam.getValue()))));

                                        } else {

                                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMin, RoundingMode.HALF_UP);
                                            double d = bd.doubleValue();
                                            String g = String.valueOf(d);
                                            OneParam.setValue(g);
                                        }


                                    }

                                }


                            }


                        } else if ((OneParam.getParameterName().contains("Yield") || OneParam.getParameterName().contains("yield"))) {

                            decimalPlacesMin = 0;
                            decimalPlacesMax = 0;
                            for (ParamObject p : TensileSTDList) {

                                if (OneParam.getUnit().contains("MPa")) {

                                    if (p.getParamName().contains("Yield") && p.getParamName().contains("MPa")) {

                                        //Look at how many decimal places there are at the min and max of the STD, and to round according to that the data from the list
                                        String[] stdSplitMin = p.getMin().split("\\.");
                                        String[] stdSplitMax = p.getMax().split("\\.");

                                        if (Numeric(p.getMin())) {
                                            if (!p.getMin().equals("-"))
                                                if (stdSplitMin.length > 1) {
                                                    decimalPlacesMin = stdSplitMin[1].length();
                                                }

                                        }
                                        if (Numeric(p.getMax())) {
                                            if (!p.getMax().equals("-"))
                                                if (stdSplitMax.length > 1) {
                                                    decimalPlacesMax = stdSplitMax[1].length();
                                                }

                                        }
                                        if (decimalPlacesMax > decimalPlacesMin) {

                                            //Round around max

                                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMax, RoundingMode.HALF_UP);
                                            double d = bd.doubleValue();
                                            String g = String.valueOf(d);

                                            OneParam.setValue(g);

                                        } else if (decimalPlacesMax <= decimalPlacesMin) {

                                            if ((decimalPlacesMax == decimalPlacesMin) && (decimalPlacesMax == 0)) {


                                            } else {
                                                //Round around min
                                                BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMin, RoundingMode.HALF_UP);
                                                double d = bd.doubleValue();
                                                String g = String.valueOf(d);

                                                OneParam.setValue(g);

                                            }


                                        }


                                    }


                                } else if ((OneParam.getUnit().contains("KSI") || (OneParam.getUnit().contains("ksi")))) {
                                    if (p.getParamName().contains("Yield") && p.getParamName().contains("KSI")) {
                                        decimalPlacesMin = 0;
                                        decimalPlacesMax = 0;
                                        //Look at how many decimal places there are at the min and max of the STD, and to round according to that the data from the list
                                        String[] stdSplitMin = p.getMin().split("\\.");
                                        String[] stdSplitMax = p.getMax().split("\\.");

                                        if (Numeric(p.getMin())) {
                                            if (!p.getMin().equals("-"))
                                                if (stdSplitMin.length > 1) {
                                                    decimalPlacesMin = stdSplitMin[1].length();
                                                }

                                        }
                                        if (Numeric(p.getMax())) {
                                            if (!p.getMax().equals("-"))
                                                if (stdSplitMax.length > 1) {
                                                    decimalPlacesMax = stdSplitMax[1].length();
                                                }

                                        }


                                        if (decimalPlacesMax > decimalPlacesMin) {

                                            //Round around max

                                            OneParam.setValue(String.valueOf(Math.round(Double.parseDouble(OneParam.getValue()))));

                                        } else if (decimalPlacesMax <= decimalPlacesMin) {
                                            //Round around min
                                            BigDecimal bd = new BigDecimal(Float.parseFloat(OneParam.getValue())).setScale(decimalPlacesMin, RoundingMode.HALF_EVEN);
                                            double d = bd.doubleValue();
                                            String g = String.valueOf(d);

                                            OneParam.setValue(g);


                                        }

                                    }

                                }


                            }

                        }

                        //ToDO later if needed!!!!!!!!!!!!!11


                    }

                }

            }
        }


    }

    public static void roundTensile() {

        ArrayList<ParamObject> TensileSTDList = new ArrayList<>();

        if (!Main.selection.getAllStandardsList().isEmpty()) {

            for (ParamObject p : Main.selection.getAllStandardsList().get(0)) {

                if (p.getTestName().equals("Tensile")) {

                    TensileSTDList.add(p);


                }


            }

        }


        //Round according to ASTM

        if (TestParamController.RoundAround == false) {

            RoundNADCAP();
            RoundAroundSTD(TensileSTDList);


        } else {
            RoundAroundSTD(TensileSTDList);
            RoundNADCAP();
        }


    }

    public static void PassChemicalTest(boolean passTest, int place, String Element, int StandardCounter) {

        if (Main.Hebrew == true) {

            Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "הבדיקה הכימית נכשלה",
                    "הבדיקה נכשלה בדגם " + place + " ובאלמנט/פרמטר " + Element + " בתקן מספר " + StandardCounter);
            dialog.setFontSize(22);
            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

            dialog.showAndWait();

        } else {

            Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "The Chemical Test Failed",
                    "The Chemical Test Failed at Sample " + place + " at the Element or Parameter " + Element + " in Standard No. " + StandardCounter);
            dialog.setFontSize(22);
            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

            dialog.showAndWait();

        }


    }

    public static void PassTensileTest(boolean passTest, String Where, int Sample) {


        if (Main.Hebrew == true) {
            Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "מבחן המתיחה נכשל",
                    "מבחן המתיחה נכשל בדגם " + Sample + " ובפרמטר " + Where);
            dialog.setFontSize(22);
            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

            dialog.showAndWait();
        } else {
            Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "The Tensile Test Failed",
                    "The Tensile Test Failed at Sample " + Sample + " at the parameter " + Where);
            dialog.setFontSize(22);
            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

            dialog.showAndWait();
        }

        if (!FailedList.contains(Sample)) {
            variables.addTextVariable(new TextVariable("#{PassFail-" + Sample + "}", "Fail"));
            FailedList.add(Sample);
        }


    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    public static void TensileDataFiller() throws IOException {

        FailedList.clear();


        String Ts = null;
        boolean passTest = true;
        int SampleCount = 1;

        double currentWidth = 0;
        double currentThickness = 0;
        double currentDiameter = 0;
        double currentLoad = 0;


        int UTSMPacounter = 1;
        int UTSKSIcounter = 1;
        int YPKSIcounter = 1;
        int YPMPAcounter = 1;
        int ELcounter = 1;
        int ThicknessCounter = 1;
        int WidthCounter = 1;
        int DiameterCounter = 1;
        int ARcounter = 1;
        int MaxLoadCounter = 1;
        int ModulusCounter = 1;

        DescriptiveStatistics stats = new DescriptiveStatistics();
        DescriptiveStatistics statsWidth = new DescriptiveStatistics();
        DescriptiveStatistics statsThickness = new DescriptiveStatistics();
        DescriptiveStatistics statsForce = new DescriptiveStatistics();
        DescriptiveStatistics statsDiameter = new DescriptiveStatistics();

        for (File f : ImportController.TensileFiles) {

            ReadTensileFile(f);

        }
        roundTensile();


        for (int i = 0; i < TensileData.size(); i++) {

            variables.addTextVariable(new TextVariable("#{SpT-" + (i + 1) + "}", TensileData.get(i).get(1).getValue()));

        }


        for (ArrayList<CSVTensile> T : TensileData) {

            for (CSVTensile TensileObject : T) {


                if (TensileObject.getParameterName().contains("Width")) {
                    variables.addTextVariable(new TextVariable("#{Width-" + WidthCounter + "}", TensileObject.getValue()));

                    currentWidth = Double.parseDouble(TensileObject.getValue());

                    WidthCounter++;

                }

                if (TensileObject.getParameterName().contains("Thickness")) {

                    variables.addTextVariable(new TextVariable("#{Thickness-" + ThicknessCounter + "}", TensileObject.getValue()));
                    ThicknessCounter++;
                    currentThickness = Double.parseDouble(TensileObject.getValue());

                }

                if (TensileObject.getParameterName().contains("Original") && TensileObject.getParameterName().contains("Diameter")) {
                    variables.addTextVariable(new TextVariable("#{Diameter-" + DiameterCounter + "}", TensileObject.getValue()));
                    DiameterCounter++;
                    currentDiameter = Double.parseDouble(TensileObject.getValue());

                }


                if (Main.selection.getTensileUnit() != null) {
                    if (Main.selection.getTensileUnit().equals("MPa")) {

                        if (TensileObject.getUnit().contains("MPa") && TensileObject.getParameterName().contains("Maximum") && !TensileObject.getParameterName().contains("Load")) {
                            variables.addTextVariable(new TextVariable("#{UTSMPaRs-" + UTSMPacounter + "}", TensileObject.getValue()));
                            if (Main.selection.getTensileUnit() != null)
                                if (Main.selection.getTensileUnit().equals("MPa")) {

                                    variables.addTextVariable(new TextVariable("#{UTSRs-" + UTSMPacounter + "}", TensileObject.getValue()));

                                }
                            for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList())
                                for (ParamObject j : M) {

                                    if (j.getParamName().contains("UTS") && j.getParamName().contains("MPa"))
                                        passTest = CheckResults(j.getMin(), j.getMax(), TensileObject.getValue());
                                    if (passTest == false)
                                        PassTensileTest(passTest, TensileObject.getParameterName(), SampleCount);
                                    passTest = true;

                                }
                            UTSMPacounter++;

                        } else if (TensileObject.getUnit().contains("MPa") && TensileObject.getParameterName().contains("Yield")) {
                            variables.addTextVariable(new TextVariable("#{YPMPaRs-" + YPMPAcounter + "}", TensileObject.getValue()));

                            if (Main.selection.getTensileUnit() != null)
                                if (Main.selection.getTensileUnit().equals("MPa")) {

                                    variables.addTextVariable(new TextVariable("#{YPRs-" + YPMPAcounter + "}", TensileObject.getValue()));


                                }

                            for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList())
                                for (ParamObject j : M) {
                                    if (j.getParamName().contains("Yield") && j.getParamName().contains("MPa"))
                                        passTest = CheckResults(j.getMin(), j.getMax(), TensileObject.getValue());
                                    if (passTest == false)
                                        PassTensileTest(passTest, TensileObject.getParameterName(), SampleCount);
                                    passTest = true;

                                }


                            YPMPAcounter++;
                        }


                    } else if (Main.selection.getTensileUnit().equals("KSI")) {


                        if (TensileObject.getUnit().contains("ksi") && TensileObject.getParameterName().contains("Maximum") && !TensileObject.getParameterName().contains("Load")) {

                            if (Main.selection.getTensileUnit() != null)
                                if (Main.selection.getTensileUnit().equals("KSI")) {

                                    variables.addTextVariable(new TextVariable("#{UTSRs-" + UTSKSIcounter + "}", TensileObject.getValue()));


                                }


                            for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList())
                                for (ParamObject j : M) {
                                    if (j.getParamName().contains("UTS") && j.getParamName().contains("KSI"))
                                        passTest = CheckResults(j.getMin(), j.getMax(), TensileObject.getValue());
                                    if (passTest == false)

                                        PassTensileTest(passTest, TensileObject.getParameterName(), SampleCount);
                                    passTest = true;

                                }


                            UTSKSIcounter++;


                        } else if (TensileObject.getUnit().contains("ksi") && TensileObject.getParameterName().contains("Yield")) {
                            variables.addTextVariable(new TextVariable("#{YPKSIRs-" + YPKSIcounter + "}", TensileObject.getValue()));

                            if (Main.selection.getTensileUnit() != null)
                                if (Main.selection.getTensileUnit().equals("KSI")) {

                                    variables.addTextVariable(new TextVariable("#{YPRs-" + YPKSIcounter + "}", TensileObject.getValue()));


                                }

                            for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList())
                                for (ParamObject j : M) {
                                    if (j.getParamName().contains("Yield") && j.getParamName().contains("KSI"))
                                        passTest = CheckResults(j.getMin(), j.getMax(), TensileObject.getValue());
                                    if (passTest == false)

                                        PassTensileTest(passTest, TensileObject.getParameterName(), SampleCount);
                                    passTest = true;

                                }


                            YPKSIcounter++;
                        }


                    } else if (Main.selection.getTensileUnit().equals("Kg\\mm^2")) {


                    }

                }

                if (TensileObject.getParameterName().contains("Elongation")) {
                    variables.addTextVariable(new TextVariable("#{ELRs-" + ELcounter + "}", TensileObject.getValue()));
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList())
                        for (ParamObject j : M) {
                            if (j.getParamName().contains("Elongation") && j.getParamName().contains("%"))
                                passTest = CheckResults(j.getMin(), j.getMax(), TensileObject.getValue());
                            if (passTest == false)

                                PassTensileTest(passTest, TensileObject.getParameterName(), SampleCount);
                            passTest = true;

                        }

                    ELcounter++;
                } else if (TensileObject.getParameterName().contains("Area") || TensileObject.getParameterName().contains("area")) {
                    variables.addTextVariable(new TextVariable("#{RdARs-" + ARcounter + "}", TensileObject.getValue()));
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList())
                        for (ParamObject j : M) {
                            if (j.getParamName().contains("Area") && j.getParamName().contains("%"))
                                passTest = CheckResults(j.getMin(), j.getMax(), TensileObject.getValue());
                            if (passTest == false)
                                PassTensileTest(passTest, TensileObject.getParameterName(), SampleCount);
                            passTest = true;

                        }

                    ARcounter++;
                } else if ((TensileObject.getParameterName().contains("Maximum") && TensileObject.getParameterName().contains("Load")) && TensileObject.getUnit().contains("kN")) {
                    variables.addTextVariable(new TextVariable("#{MLoadRs-" + MaxLoadCounter + "}", TensileObject.getValue()));
                    currentLoad = Double.parseDouble(TensileObject.getValue());
                    stats.addValue(Double.parseDouble(TensileObject.getValue()));
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList())
                        for (ParamObject j : M) {
                            if (j.getParamName().contains("Load"))
                                passTest = CheckResults(j.getMin(), j.getMax(), TensileObject.getValue());
                            if (passTest == false)
                                PassTensileTest(passTest, TensileObject.getParameterName(), SampleCount);
                            passTest = true;

                        }

                    MaxLoadCounter++;
                } else if (TensileObject.getParameterName().contains("Modulus")) {
                    variables.addTextVariable(new TextVariable("#{Modulus-" + ModulusCounter + "}", TensileObject.getValue()));
                    ModulusCounter++;
                }


            }


            variables.addTextVariable(new TextVariable("#{Force-" + SampleCount + "}", String.valueOf(Math.round(currentLoad * 1000 / (currentThickness * currentWidth)))));
            variables.addTextVariable(new TextVariable("#{Area-" + SampleCount + "}", String.valueOf(Math.round((currentThickness * currentWidth * 100.0) / 100.0))));


            statsForce.addValue(currentLoad * 1000 / (currentThickness * currentWidth));

            statsThickness.addValue(currentThickness);
            statsWidth.addValue(currentWidth);
            statsDiameter.addValue(currentDiameter);

            currentLoad = 0;
            currentThickness = 0;
            currentWidth = 0;
            currentDiameter = 0;
            SampleCount++;

        }


        double AverageLoad = stats.getSum() / (SampleCount - 1);
        double AverageWidth = statsWidth.getSum() / (SampleCount - 1);
        double AverageThickness = statsThickness.getSum() / (SampleCount - 1);
        double AverageDiameter = statsDiameter.getSum() / (SampleCount - 1);
        double AverageForce = statsForce.getSum() / (SampleCount - 1);


        variables.addTextVariable(new TextVariable("#{DiameterAverage}", String.valueOf(Math.round(AverageDiameter * 1000.0) / 1000.0)));
        variables.addTextVariable(new TextVariable("#{DiameterMin}", String.valueOf(statsDiameter.getMin())));
        variables.addTextVariable(new TextVariable("#{DiameterMax}", String.valueOf(statsDiameter.getMax())));
        variables.addTextVariable(new TextVariable("#{DiameterSD}", String.valueOf(Math.round(statsDiameter.getStandardDeviation() * 1000.0) / 1000.0)));


        variables.addTextVariable(new TextVariable("#{ForceAverage}", String.valueOf(Math.round(AverageForce * 1000.0) / 1000.0)));
        variables.addTextVariable(new TextVariable("#{ForceMin}", String.valueOf(Math.round(statsForce.getMin()))));
        variables.addTextVariable(new TextVariable("#{ForceMax}", String.valueOf(Math.round(statsForce.getMax()))));
        variables.addTextVariable(new TextVariable("#{ForceSD}", String.valueOf(Math.round(statsForce.getStandardDeviation() * 1000.0) / 1000.0)));

        variables.addTextVariable(new TextVariable("#{WidthAverage}", String.valueOf(Math.round(AverageWidth * 1000.0) / 1000.0)));
        variables.addTextVariable(new TextVariable("#{WidthMin}", String.valueOf(statsWidth.getMin())));
        variables.addTextVariable(new TextVariable("#{WidthMax}", String.valueOf(statsWidth.getMax())));
        variables.addTextVariable(new TextVariable("#{WidthSD}", String.valueOf(Math.round(statsWidth.getStandardDeviation() * 1000.0) / 1000.0)));

        variables.addTextVariable(new TextVariable("#{ThicknessAverage}", String.valueOf(Math.round(AverageThickness * 1000.0) / 1000.0)));
        variables.addTextVariable(new TextVariable("#{ThicknessMin}", String.valueOf(statsThickness.getMin())));
        variables.addTextVariable(new TextVariable("#{ThicknessMax}", String.valueOf(statsThickness.getMax())));
        variables.addTextVariable(new TextVariable("#{ThicknessSD}", String.valueOf(Math.round(statsThickness.getStandardDeviation() * 1000.0) / 1000.0)));


        variables.addTextVariable(new TextVariable("#{LoadAverage}", String.valueOf(Math.round(AverageLoad * 1000.0) / 1000.0)));
        variables.addTextVariable(new TextVariable("#{LoadMin}", String.valueOf(stats.getMin())));
        variables.addTextVariable(new TextVariable("#{LoadMax}", String.valueOf(stats.getMax())));
        variables.addTextVariable(new TextVariable("#{LoadSD}", String.valueOf(Math.round(stats.getStandardDeviation() * 1000.0) / 1000.0)));


        if (Main.selection.getTensileSpecimen() != null) {

            if (UserController.ReportLng.equals("English")) {
                if (Main.selection.getTensileSpecimen().equals("4D") || Main.selection.getTensileSpecimen().equals("5D"))
                    variables.addTextVariable(new TextVariable("#{SpN}", "round (" + Main.selection.getTensileSpecimen() + ")"));
                else
                    variables.addTextVariable(new TextVariable("#{SpN}", Main.selection.getTensileSpecimen().toLowerCase()));

            } else if (UserController.ReportLng.equals("Hebrew")) {


                if (Main.selection.getTensileSpecimen().equals("Rectangular"))
                    variables.addTextVariable(new TextVariable("#{SpN}", "מלבני, ברוחב"));
                else if (Main.selection.getTensileSpecimen().equals("Wire")) {
                    variables.addTextVariable(new TextVariable("#{SpN}", "חוט, בקוטר"));
                } else if (Main.selection.getTensileSpecimen().equals("Pipe")) {
                    variables.addTextVariable(new TextVariable("#{SpN}", "צינור, בקוטר"));
                } else if (Main.selection.getTensileSpecimen().equals("Bolt")) {
                    variables.addTextVariable(new TextVariable("#{SpN}", "בורג, בקוטר"));
                } else if (Main.selection.getTensileSpecimen().equals("4D")) {
                    variables.addTextVariable(new TextVariable("#{SpN}", "עגול, בקוטר"));


                } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                    variables.addTextVariable(new TextVariable("#{SpN}", "עגול, בקוטר"));


                }

            }


        }


        if (Main.selection.getTensileThickness() != null && (Main.selection.getTensileSpecimen() != null)) {

            if (UserController.ReportLng.contains("Hebrew")) {


                if ((Main.selection.getTensileSpecimen().equals("4D")) || (Main.selection.getTensileSpecimen().equals("5D"))) {

                    if (Main.selection.getTensileThickness().equals("2.5 mm"))
                        variables.addTextVariable(new TextVariable("#{GaT}", "2.5 "));
                    else if (Main.selection.getTensileThickness().equals("4 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "4 "));
                    } else if (Main.selection.getTensileThickness().equals("6 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "6 "));
                    } else if (Main.selection.getTensileThickness().equals("9 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", " 9 "));
                    } else if (Main.selection.getTensileThickness().equals("12.5 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "12.5 "));
                    } else if (Main.selection.getTensileThickness().equals("20 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "20 "));
                    }


                } else {

                    if (Main.selection.getTensileThickness().equals("2.5 mm"))
                        variables.addTextVariable(new TextVariable("#{GaT}", "2.5 "));
                    else if (Main.selection.getTensileThickness().equals("4 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "4 "));
                    } else if (Main.selection.getTensileThickness().equals("6 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "6 "));
                    } else if (Main.selection.getTensileThickness().equals("9 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "9 "));
                    } else if (Main.selection.getTensileThickness().equals("12.5 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "12.5 "));
                    } else if (Main.selection.getTensileThickness().equals("20 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "20 "));
                    }
                }


                if (Main.selection.getTensileThickness().equals("2.5 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(2.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(2.5 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(2.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    }
                } else if (Main.selection.getTensileThickness().equals("4 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(4 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(4 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(4 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    }
                }
                if (Main.selection.getTensileThickness().equals("6 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(6 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(6 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(6 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    }
                }
                if (Main.selection.getTensileThickness().equals("9 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(9 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(9 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(9 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    }
                }
                if (Main.selection.getTensileThickness().equals("12.5 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(12.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(12.5 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(12.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    }

                }

                if (Main.selection.getTensileThickness().equals("20 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(20 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(20 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(20 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts));

                    }


                }


                //here english
            } else {

                if ((Main.selection.getTensileSpecimen().equals("4D")) || (Main.selection.getTensileSpecimen().equals("5D"))) {

                    if (Main.selection.getTensileThickness().equals("2.5 mm"))
                        variables.addTextVariable(new TextVariable("#{GaT}", " ø 2.5 mm"));
                    else if (Main.selection.getTensileThickness().equals("4 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", " ø 4 mm"));
                    } else if (Main.selection.getTensileThickness().equals("6 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", " ø 6 mm"));
                    } else if (Main.selection.getTensileThickness().equals("9 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", " ø 9 mm"));
                    } else if (Main.selection.getTensileThickness().equals("12.5 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", " ø 12.5 mm"));
                    } else if (Main.selection.getTensileThickness().equals("20 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", " ø 20 mm"));
                    }


                } else {

                    if (Main.selection.getTensileThickness().equals("2.5 mm"))
                        variables.addTextVariable(new TextVariable("#{GaT}", "2.5 mm width"));
                    else if (Main.selection.getTensileThickness().equals("4 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "4 mm width"));
                    } else if (Main.selection.getTensileThickness().equals("6 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "6 mm width"));
                    } else if (Main.selection.getTensileThickness().equals("9 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "9 mm width"));
                    } else if (Main.selection.getTensileThickness().equals("12.5 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "12.5 mm width"));
                    } else if (Main.selection.getTensileThickness().equals("20 mm")) {
                        variables.addTextVariable(new TextVariable("#{GaT}", "20 mm width"));
                    }
                }


                if (Main.selection.getTensileThickness().equals("2.5 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(2.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(2.5 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(2.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    }
                } else if (Main.selection.getTensileThickness().equals("4 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(4 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(4 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(4 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    }
                }
                if (Main.selection.getTensileThickness().equals("6 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(6 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(6 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(6 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    }
                }
                if (Main.selection.getTensileThickness().equals("9 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(9 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(9 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(9 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    }
                }

                if (Main.selection.getTensileThickness().equals("12.5 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(12.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(12.5 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(12.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    }
                }

                if (Main.selection.getTensileThickness().equals("20 mm")) {
                    if (Main.selection.getTensileSpecimen().equals("4D")) {

                        Ts = String.valueOf(12.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("5D")) {

                        Ts = String.valueOf(12.5 * 5);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    } else if (Main.selection.getTensileSpecimen().equals("Rectangular")) {

                        Ts = String.valueOf(12.5 * 4);
                        variables.addTextVariable(new TextVariable("#{GaL}", Ts + " mm"));

                    }
                }


            }


        }


    }

    public static void FillHardness() {

        if (Main.selection.getHardnessMethod() != null) {
            variables.addTextVariable(new TextVariable("#{HardMethod}", Main.selection.getHardnessMethod()));
        }


    }

    public static void FillMicro() {

        if (Main.selection.getMicroMethod() != null) {
            variables.addTextVariable(new TextVariable("#{MicroMethod}", Main.selection.getHardnessMethod()));
        }

        if (Main.selection.getMicroLoad() != null) {
            variables.addTextVariable(new TextVariable("#{MicroLoad}", Main.selection.getMicroLoad()));
        }
        if (Main.selection.getMicroMethod() != null) {
            variables.addTextVariable(new TextVariable("#{MicroMethod}", Main.selection.getMicroMethod()));
        }

        if (Main.selection.getMicroStd() != null) {
            variables.addTextVariable(new TextVariable("#{MicroStd}", Main.selection.getMicroStd()));
        }
    }

    public static boolean CheckResults(String min, String max, String result) {


        if (result.contains("<") || result.contains(">")) {

            if (Numeric(min))
                return false;
        }


        if (!Numeric(result))
            return true;

        if (Numeric(min) && Numeric(max)) {

            if ((Double.parseDouble(result) > Double.parseDouble(max)) || (Double.parseDouble(result) < Double.parseDouble(min))) {

                return false;

            }
        }

        if (!Numeric(min) && Numeric(max)) {

            if ((Double.parseDouble(result) > Double.parseDouble(max))) {

                return false;

            }

        }


        if (Numeric(min) && !Numeric(max)) {

            if ((Double.parseDouble(result) < Double.parseDouble(min))) {

                return false;

            }

        }


        return true;
    }


    int CheckTensileUnits(ArrayList<ParamObject> T) {

        boolean hasKSI = false;

        boolean hasMPa = false;

        if (T.size() == 0 || T == null) {

            return 0;

        } else {

            for (ParamObject o : T) {

                if (o.getParamName().contains("MPa")) {


                    hasMPa = true;

                }

                if (o.getParamName().contains("KSI")) {


                    hasKSI = true;

                }


            }

            if (hasKSI == true && hasMPa == true) {

                return 3;
            } else if (hasKSI == true && hasMPa == false) {

                return 2;
            } else if (hasKSI == false && hasMPa == true) {

                return 1;
            } else if (hasKSI == false && hasMPa == false) {

                return 0;
            }


        }


        return 0;


    }


    private static void GeneralDlFiller() {


        Date date = Calendar.getInstance().getTime();

        variables.addTextVariable(new TextVariable("#{RpFull}", "askdjf;alskdjf;alskdfj"));


        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);

        variables.addTextVariable(new TextVariable("#{Dt}", today));

        variables.addTextVariable(new TextVariable("#{RpFull}", Main.selection.getReportName()));

        int Cnt = 1;

        if (StandardSelectController.StandardSelected) {

            for (String M : Main.selection.getStandards()) {


                variables.addTextVariable(new TextVariable("#{Std-" + Cnt + "}", M));

                Cnt++;
            }

            Cnt = 1;
            for (String M : Main.selection.getMaterials()) {

                variables.addTextVariable(new TextVariable("#{Mat-" + Cnt + "}", M));

                Cnt++;
            }

            Cnt = 1;

        }

        if (Main.selection.getReportName().contains("-")) {

            String[] RepNumber = Main.selection.getReportName().split("-");
            if (RepNumber.length >= 3) {

                String g = RepNumber[1] + "-" + RepNumber[2];
                for (int i = 3; i < RepNumber.length; i++) {
                    g = g + "-" + RepNumber[i];

                }


                variables.addTextVariable(new TextVariable("#{RpN}", g));

            } else {

                if (Main.Hebrew == true) {
                    com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "השם של הדוח איננו תקין",
                            "השם של הדוח איננו תקין");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();
                } else {

                    com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "The Report Name was not Valid",
                            "The Report Name was not Valid");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();
                }


            }

        }


        variables.addTextVariable(new TextVariable("#{Mt}", "S235JR"));

        variables.addTextVariable(new TextVariable("#{HtN}", "3456567"));

        variables.addTextVariable(new TextVariable("#{HtN}", "3456567"));

        variables.addTextVariable(new TextVariable("#{AdR}", Main.selection.getAdress()));

        variables.addTextVariable(new TextVariable("#{Sz}", "3.5 mm"));

        variables.addTextVariable(new TextVariable("#{PtD}", "Rectangular plate"));

        variables.addTextVariable(new TextVariable("#{CoN}", Main.selection.getCompanyName()));

        variables.addTextVariable(new TextVariable("#{Dm}", "3456567"));

        variables.addTextVariable(new TextVariable("#{PrN}", Main.selection.getPreparerName()));


        variables.addTextVariable(new TextVariable("#{ApN}", Main.selection.getApproverName()));

        variables.addTextVariable(new TextVariable("#{ApT}", Main.selection.getApproverTitle()));

        variables.addTextVariable(new TextVariable("#{PrT}", Main.selection.getPreparerTitle()));


        //variables.addTextVariable(new TextVariable("#{TUnit}", Main.selection.getUnit()));

        if (Main.selection.getTensileSpecimen() != null) {

            switch (Main.selection.getTensileSpecimen()) {

                case ("5D"): {

                    variables.addTextVariable(new TextVariable("#{SpN}", "round " + Main.selection.getTensileSpecimen()));

                }
                break;

                case ("4D"): {

                    variables.addTextVariable(new TextVariable("#{SpN}", "round " + Main.selection.getTensileSpecimen()));

                }
                break;

                case ("Rectangular"): {

                    variables.addTextVariable(new TextVariable("#{SpN}", Main.selection.getTensileSpecimen()));

                }
                break;

                case ("Wire"): {

                    variables.addTextVariable(new TextVariable("#{SpN}", Main.selection.getTensileSpecimen()));

                }
                break;

                case ("Bolt"): {

                    variables.addTextVariable(new TextVariable("#{SpN}", Main.selection.getTensileSpecimen()));

                }
                break;

                case ("Pipe"): {

                    variables.addTextVariable(new TextVariable("#{SpN}", Main.selection.getTensileSpecimen()));

                }
                break;


            }
            variables.addTextVariable(new TextVariable("#{SpN}", Main.selection.getTensileSpecimen()));

        } else {

            //  Alert alert = new Alert(Alert.AlertType.ERROR);
            // alert.setTitle("No Report Name!");
            // alert.setHeaderText("No Report Name!");
            // alert.setContentText("Please Enter Report Name!");

            // alert.showAndWait();


        }


    }

    public static void FillHeaderData() {

        int EraserCount = 0;

        CSVRow headers = Main.selection.getReportHeader();

        if ((Main.selection.getHeaderClms() != null) && (Main.selection.getHeaderClms().size() != 0)) {
            {

                for (int i = 1; i < Main.selection.getHeaderClms().size(); i++) {

                    variables.addTextVariable(new TextVariable("#{HeadPr-" + 0 + "-" + i + "}", Main.selection.getHeaderClms().get(i)));

                    EraserCount++;

                }


                if (headers != null) {

                    for (int i = 1; i <= headers.getColumns().size(); i++) {

                        variables.addTextVariable(new TextVariable("#{HeadDt-" + 0 + "-" + i + "}", headers.getColumns().get(i - 1).getValue()));

                    }

                }


                for (int i = EraserCount + 1; i < 20; i++) {
                    variables.addTextVariable(new TextVariable("#{HeadPr-" + 0 + "-" + i + "}", ""));
                    variables.addTextVariable(new TextVariable("#{HeadDt-" + 0 + "-" + i + "}", ""));


                }


            }


        }


    }

    public static ArrayList<ArrayList<ParamObject>> OrederTheLists(ArrayList<ArrayList<ParamObject>> OriginalList) {


        boolean NotFound = false;

        ArrayList<String> AllElemenstsList = new ArrayList<>();

        ArrayList<ArrayList<ParamObject>> AllOrderedLists = new ArrayList<>();


        // ArrayList<ParamObject> SameElementsList = new ArrayList<>();
        // ArrayList<ParamObject> DifferentElementsList = new ArrayList<>();


        //OrderedList.add(FirstList);

        if (OriginalList.size() != 0) {
            for (ArrayList<ParamObject> J : OriginalList) {


                for (ParamObject K : J) {

                    NotFound = true;

                    for (String M : new ArrayList<>(AllElemenstsList)) {

                        if (M.equals(K.getParamName())) {

                            AllElemenstsList.add(K.getParamName());

                            NotFound = false;

                        }

                    }


                    if (NotFound == true) {

                        AllElemenstsList.add(K.getParamName());
                    }

                }


            }

            //Delete Duplicates

            AllElemenstsList = new ArrayList<String>(new LinkedHashSet<String>(AllElemenstsList));


            for (ArrayList<ParamObject> J : OriginalList) {

                for (String M : AllElemenstsList) {

                    NotFound = true;

                    for (ParamObject K : J) {

                        if (M.equals(K.getParamName())) {

                            NotFound = false;

                        }

                    }


                    if (NotFound == true) {

                        ParamObject j = new ParamObject();
                        j.setTestName("Chemical");
                        j.setParamName(M);
                        j.setMin("-");
                        j.setMax("-");

                        J.add(j);
                    }

                }


            }


            ArrayList<ParamObject> FirstList = OriginalList.get(0);

            for (ArrayList<ParamObject> P : OriginalList) {

                ArrayList<ParamObject> OneOrderedList = new ArrayList<>();

                for (ParamObject J : FirstList) {

                    for (ParamObject M : P) {

                        if (J.getParamName().equals(M.getParamName())) {

                            OneOrderedList.add(M);
                        }


                    }


                }


                AllOrderedLists.add(new ArrayList<>(OneOrderedList));
                OneOrderedList.clear();

            }


        }



/*
    for(ArrayList<ParamObject> M:OriginalList){

        SameElementsList.clear();

        ArrayList<ParamObject> oneOrderedList = new ArrayList<>();


        for(ParamObject J:FirstList){

            for(ParamObject K:M){

                if(J.getParamName().equals(K.getParamName())){
                    SameElementsList.add(K);
                }

            }


        }

        DifferentElementsList.addAll(M);



        for(ParamObject J:FirstList){
        for(ParamObject K:new ArrayList<>(DifferentElementsList)){

            if(J.getParamName().equals(K.getParamName())){
                DifferentElementsList.remove(K);
            }


        }}





        for(ParamObject P:DifferentElementsList){

            oneOrderedList.add(P);

        }

        DifferentElementsList.clear();

        AllOrderedLists.add(new ArrayList<ParamObject>(oneOrderedList));
        oneOrderedList.clear();
    }
*/


        return AllOrderedLists;

    }


    private static void ChemicalElements(ArrayList<ParamObject> elements, int StandardCounter) throws IOException, InvalidFormatException {

        roundedList.clear();


        ExcelChemical RestChemical = new ExcelChemical();

        int counter = 1;
        int rowCounter = 0;
        int failedPlace = 1;
        boolean restElement = false;

        boolean passTest = true;


        if ((ImportController.ChemicalFiles != null) && (ImportController.ChemicalFiles.size() != 0)) {

            if (StandardSelectController.StandardSelected == true) {

                for (File f : ImportController.ChemicalFiles) {
                    counter = 1;
                    rowCounter++;
                    ReadChemical(f);


                    for (ExcelChemical ex : new ArrayList<>(roundedList)) {

                        if (ex.getChemicalName() == null && ex.getPercent() == null) {

                            roundedList.remove(ex);
                        }


                    }


                    for (ExcelChemical p : new ArrayList<ExcelChemical>(roundedList)) {

                        if (p.getPercent().equals("Rest")) {
                            RestChemical.setPercent("Rest");
                            RestChemical.setChemicalName(p.getChemicalName());
                            roundedList.remove(p);

                        }
                    }

/*
                for (ExcelChemical p : new ArrayList<ExcelChemical>(roundedList)) {

                    if (p.getChemicalName().contains("Total")) {
                      TotalChemical.setPercent(p.getPercent());
                      TotalChemical.setChemicalName("Total");
                        roundedList.remove(p);

                    }
                }

                for (ExcelChemical p : new ArrayList<ExcelChemical>(roundedList)) {

                    if (p.getChemicalName().contains("Each")) {
                        EachChemical.setPercent(p.getPercent());
                        EachChemical.setChemicalName("Each");
                        roundedList.remove(p);

                    }
                }

               */

                    for (ParamObject element : elements) {
                        restElement = false;
                        for (ExcelChemical ex : roundedList) {

                            //if found in the roundList then add the rounded number
                            if (ex.getChemicalName().equals(element.getParamName())) {


                                variables.addTextVariable(new TextVariable("#{" + "Rs" + rowCounter + "-" + counter + "}", ex.getPercent()));

                            }

                            //add the standard to the report
                            if (!element.getParamName().equals(RestChemical.getChemicalName())) {
                                if (!ex.getChemicalName().equals("-")) {

                                    variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", element.getParamName()));

                                    if (Numeric(element.getMin())) {

                                        if (Double.parseDouble(element.getMin()) == 0.0)
                                            variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", "-"));
                                        else
                                            variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", element.getMin()));
                                    } else
                                        variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", element.getMin()));


                                    if (Numeric(element.getMax())) {
                                        if (Double.parseDouble(element.getMax()) == 0.0)
                                            variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", "-"));
                                        else
                                            variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", element.getMax()));


                                    } else {

                                        variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", element.getMax()));

                                    }


                                }


                                restElement = false;
                            } else restElement = true;


                        }
                        if (restElement == false)
                            counter++;

                    }


                    //ToDo here to check if hebrew or English!!!!!!!!!!!!!1
                    if (UserController.ReportLng.equals("English")) {

                        variables.addTextVariable(new TextVariable("#{" + "Rs" + rowCounter + "-" + counter + "}", "Rest"));
                        variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", RestChemical.getChemicalName()));
                        variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", "Rest"));
                        variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", "Rest"));
                        counter++;

                    } else if (UserController.ReportLng.equals("Hebrew")) {

                        variables.addTextVariable(new TextVariable("#{" + "Rs" + rowCounter + "-" + counter + "}", "שאר"));
                        variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", RestChemical.getChemicalName()));
                        variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", "שאר"));
                        variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", "שאר"));
                        counter++;

                    }


                    if (!TestParamController.additionalChemicals.isEmpty()) {

                        for (ExcelChemical c : chemicals) {

                            for (ExcelChemical g : TestParamController.additionalChemicals) {


                                if (c.getChemicalName().equals(g.getChemicalName())) {


                                    g.setPercent(c.getPercent());


                                }


                            }


                        }


                    }


                    for (ExcelChemical e : TestParamController.additionalChemicals) {


                        if (e.getPercent() != null) {

                            variables.addTextVariable(new TextVariable("#{" + "Rs" + rowCounter + "-" + counter + "}", e.getPercent()));
                            variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", e.getChemicalName()));

                            counter++;

                        }


                    }


                    variables.addTextVariable(new TextVariable("#{" + "Rs" + rowCounter + "-" + counter + "}", "End"));
                    variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", "End"));
                    variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", "End"));
                    variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", "End"));


                    for (ParamObject element : elements) {


                        for (ExcelChemical ex : roundedList) {
                            passTest = true;
                            if (ex.getChemicalName().equals(element.getParamName()))
                                passTest = CheckResults(element.getMin(), element.getMax(), ex.getPercent());

                            if (passTest == false) {

                                PassChemicalTest(passTest, failedPlace, element.getParamName(), StandardCounter);

                            }


                        }


                    }

                    failedPlace++;

                }


            } else {
                //If no standard is selected
                rowCounter = 1;
                for (File f : ImportController.ChemicalFiles) {

                    ReadChemical(f);


                    if (!TestParamController.additionalChemicals.isEmpty()) {

                        for (ExcelChemical c : chemicals) {

                            for (ExcelChemical g : TestParamController.additionalChemicals) {


                                if (c.getChemicalName().equals(g.getChemicalName())) {


                                    g.setPercent(c.getPercent());


                                }


                            }


                        }


                    }


                    for (ExcelChemical e : TestParamController.additionalChemicals) {


                        if (e.getPercent() != null) {

                            variables.addTextVariable(new TextVariable("#{" + "Rs" + rowCounter + "-" + counter + "}", e.getPercent()));
                            variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", e.getChemicalName()));

                            counter++;

                        }


                    }

                    variables.addTextVariable(new TextVariable("#{" + "Rs" + rowCounter + "-" + counter + "}", "End"));
                    variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", "End"));
                    variables.addTextVariable(new TextVariable("#{" + "sdmin-" + rowCounter + "-" + counter + "}", "End"));
                    variables.addTextVariable(new TextVariable("#{" + "sdmax-" + rowCounter + "-" + counter + "}", "End"));

                    rowCounter++;

                }


                variables.addTextVariable(new TextVariable("#{" + "Rs" + rowCounter + "-" + counter + "}", "End"));
                variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", "End"));
                variables.addTextVariable(new TextVariable("#{" + "sdmin-" + rowCounter + "-" + counter + "}", "End"));
                variables.addTextVariable(new TextVariable("#{" + "sdmax-" + rowCounter + "-" + counter + "}", "End"));


            }

        } else {

            StandardCounter = 0;


            ArrayList<ParamObject> OnlyChemicals = new ArrayList<>();
            ParamObject RestChemicalM = new ParamObject();

            for (ArrayList<ParamObject> o : Main.selection.getAllStandardsList()) {

                counter = 1;

                StandardCounter++;

                OnlyChemicals.clear();

                for (ParamObject p : o) {

                    if (p.getTestName().equals("Chemical")) {
                        OnlyChemicals.add(p);
                    }


                }

                for (ParamObject p : new ArrayList<ParamObject>(OnlyChemicals)) {

                    if (p.getMin().equals("Rest") || p.getMax().equals("Rest")) {
                        RestChemicalM.setParamName(p.getParamName());
                        RestChemicalM.setTestName(p.getTestName());
                        RestChemicalM.setMax(p.getMax());
                        RestChemicalM.setMin(p.getMin());

                        OnlyChemicals.remove(p);

                    }


                }


                for (ParamObject element : OnlyChemicals) {


                    variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", element.getParamName()));


                    if (Numeric(element.getMin())) {


                        if (Double.parseDouble(element.getMin()) == 0.0)
                            variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", "-"));
                        else
                            variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", element.getMin()));
                    } else
                        variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", element.getMin()));


                    if (Numeric(element.getMax())) {
                        if (Double.parseDouble(element.getMax()) == 0.0)
                            variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", "-"));
                        else
                            variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", element.getMax()));


                    } else {

                        variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", element.getMax()));

                    }

                    counter++;

                }

                variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", RestChemicalM.getParamName()));
                variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", "Rest"));
                variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", "Rest"));
                counter++;


                variables.addTextVariable(new TextVariable("#{" + "el" + counter + "}", "End"));
                variables.addTextVariable(new TextVariable("#{" + "sdmin-" + StandardCounter + "-" + counter + "}", "End"));
                variables.addTextVariable(new TextVariable("#{" + "sdmax-" + StandardCounter + "-" + counter + "}", "End"));
                counter++;


            }


        }

    }

    public static ArrayList<ArrayList<ParamObject>> StandardDataFiller() {

        int STDCounter = 0;
        ArrayList<ParamObject> ChemicalArray = new ArrayList<>();

        ArrayList<ArrayList<ParamObject>> AllChemicalArray = new ArrayList<>();

        AllChemicalArray.clear();


        if (Main.selection.getTensileUnit() != null) {

            if (UserController.ReportLng.contains("Hebrew")) {

                if (Main.selection.getTensileUnit().equals("MPa")) {
                    variables.addTextVariable(new TextVariable("#{Unit}", "מג\"פ"));
                    STDCounter = 0;
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList()) {
                        STDCounter++;
                        for (ParamObject j : M) {

                            if (j.getTestName().equals("Tensile")) {

                                if (j.getParamName().equals("UTS(MPa)")) {

                                    variables.addTextVariable(new TextVariable("#{UTSMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{UTSMax-" + STDCounter + "}", j.getMax()));


                                }


                                if (j.getParamName().equals("Yield Point(MPa)")) {

                                    variables.addTextVariable(new TextVariable("#{YPMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{YPMax-" + STDCounter + "}", j.getMax()));


                                }
                            }
                        }

                    }


                } else if (Main.selection.getTensileUnit().equals("KSI")) {
                    variables.addTextVariable(new TextVariable("#{Unit}", "KSI"));
                    STDCounter = 0;
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList()) {
                        STDCounter++;
                        for (ParamObject j : M) {
                            if (j.getTestName().equals("Tensile")) {

                                if (j.getParamName().equals("UTS(KSI)")) {

                                    variables.addTextVariable(new TextVariable("#{UTSMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{UTSMax-" + STDCounter + "}", j.getMax()));


                                }


                                if (j.getParamName().equals("Yield Point(KSI)")) {

                                    variables.addTextVariable(new TextVariable("#{YPMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{YPMax-" + STDCounter + "}", j.getMax()));


                                }
                            }
                        }


                    }


                } else if (Main.selection.getTensileUnit().equals("Kg/mm^2")) {
                    variables.addTextVariable(new TextVariable("#{Unit}", "Kg/mm^2"));

                    STDCounter = 0;
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList()) {
                        STDCounter++;
                        for (ParamObject j : M) {


                            if (j.getTestName().equals("Tensile")) {


                                if (j.getParamName().equals("UTS(Kg/mm^2)")) {

                                    variables.addTextVariable(new TextVariable("#{UTSMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{UTSMax-" + STDCounter + "}", j.getMax()));


                                }


                                if (j.getParamName().equals("Yield Point(Kg/mm^2)")) {

                                    variables.addTextVariable(new TextVariable("#{YPMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{YPMax-" + STDCounter + "}", j.getMax()));


                                }
                            }
                        }

                    }


                }


            } else {

                if (Main.selection.getTensileUnit().equals("MPa")) {
                    variables.addTextVariable(new TextVariable("#{Unit}", "MPa"));
                    STDCounter = 0;
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList()) {
                        STDCounter++;
                        for (ParamObject j : M) {
                            if (j.getTestName().equals("Tensile")) {
                                if (j.getParamName().equals("UTS(MPa)")) {

                                    variables.addTextVariable(new TextVariable("#{UTSMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{UTSMax-" + STDCounter + "}", j.getMax()));


                                }


                                if (j.getParamName().equals("Yield Point(MPa)")) {

                                    variables.addTextVariable(new TextVariable("#{YPMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{YPMax-" + STDCounter + "}", j.getMax()));


                                }
                            }
                        }


                    }


                } else if (Main.selection.getTensileUnit().equals("KSI")) {
                    variables.addTextVariable(new TextVariable("#{Unit}", "KSI"));
                    STDCounter = 0;
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList()) {
                        STDCounter++;


                        for (ParamObject j : M) {


                            if (j.getTestName().equals("Tensile")) {

                                if (j.getParamName().equals("UTS(KSI)")) {

                                    variables.addTextVariable(new TextVariable("#{UTSMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{UTSMax-" + STDCounter + "}", j.getMax()));


                                }


                                if (j.getParamName().equals("Yield Point(KSI)")) {

                                    variables.addTextVariable(new TextVariable("#{YPMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{YPMax-" + STDCounter + "}", j.getMax()));


                                }
                            }
                        }


                    }


                } else if (Main.selection.getTensileUnit().equals("Kg/mm^2")) {
                    variables.addTextVariable(new TextVariable("#{Unit}", "Kg/mm^2"));

                    STDCounter = 0;
                    for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList()) {
                        STDCounter++;
                        for (ParamObject j : M) {

                            if (j.getTestName().equals("Tensile")) {

                                if (j.getParamName().equals("UTS(Kg/mm^2)")) {

                                    variables.addTextVariable(new TextVariable("#{UTSMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{UTSMax-" + STDCounter + "}", j.getMax()));


                                }


                                if (j.getParamName().equals("Yield Point(Kg/mm^2)")) {

                                    variables.addTextVariable(new TextVariable("#{YPMin-" + STDCounter + "}", j.getMin()));
                                    variables.addTextVariable(new TextVariable("#{YPMax-" + STDCounter + "}", j.getMax()));


                                }
                            }
                        }

                    }


                }

            }


        }
        STDCounter = 0;
        for (ArrayList<ParamObject> M : Main.selection.getAllStandardsList()) {

            STDCounter++;
            for (ParamObject j : M) {

                if (j.getParamName().equals("% Elongation")) {

                    variables.addTextVariable(new TextVariable("#{ELMin-" + STDCounter + "}", j.getMin()));
                    variables.addTextVariable(new TextVariable("#{ELMax-" + STDCounter + "}", j.getMax()));

                }

                if (j.getParamName().equals("% Area Reduction")) {

                    variables.addTextVariable(new TextVariable("#{RdAMin-" + STDCounter + "}", j.getMin()));
                    variables.addTextVariable(new TextVariable("#{RdAMax-" + STDCounter + "}", j.getMax()));

                }

                if (Main.selection.getHardnessMethod() != null) {

                    if (Main.selection.getHardnessLoad() != null) {

                        if (j.getParamName().contains("HRC")) {

                        } else if (j.getParamName().contains("HRB")) {


                        } else if (j.getParamName().contains("HRA")) {

                        }


                    } else {

                        if (j.getParamName().contains("HRC")) {
                            if (!j.getMin().equals("-"))
                                variables.addTextVariable(new TextVariable("#{HRMin-" + STDCounter + "}", j.getMin()));
                            if (!j.getMax().equals("-"))
                                variables.addTextVariable(new TextVariable("#{HRMax-" + STDCounter + "}", j.getMax()));

                        } else if (j.getParamName().contains("HRB")) {
                            if (!j.getMin().equals("-"))
                                variables.addTextVariable(new TextVariable("#{HRMin-" + STDCounter + "}", j.getMin()));
                            if (!j.getMax().equals("-"))
                                variables.addTextVariable(new TextVariable("#{HRMax-" + STDCounter + "}", j.getMax()));

                        } else if (j.getParamName().contains("HRA")) {
                            if (!j.getMin().equals("-"))
                                variables.addTextVariable(new TextVariable("#{HRMin-" + STDCounter + "}", j.getMin()));
                            if (!j.getMax().equals("-"))
                                variables.addTextVariable(new TextVariable("#{HRMax-" + STDCounter + "}", j.getMax()));

                        } else if (j.getParamName().contains("HB")) {
                            if (!j.getMin().equals("-"))
                                variables.addTextVariable(new TextVariable("#{HRMin-" + STDCounter + "}", j.getMin()));
                            if (!j.getMax().equals("-"))
                                variables.addTextVariable(new TextVariable("#{HRMax-" + STDCounter + "}", j.getMax()));

                        }


                    }


                }


                if (Main.selection.getMicroMethod() != null) {


                    if (Main.selection.getMicroLoad() != null) {

                        if (j.getTestName().contains("Micro-Hardness")) {


                            switch (Main.selection.getMicroLoad()) {

                                case ("100g"): {
                                    if ((j.getParamName().contains("HV")) && (j.getParamName().contains("100 gf"))) {

                                        if (!j.getMin().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMin-" + STDCounter + "}", j.getMin()));
                                        if (!j.getMax().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMax-" + STDCounter + "}", j.getMax()));


                                    }

                                }
                                break;
                                case ("200g"): {
                                    if ((j.getParamName().contains("HV")) && (j.getParamName().contains("200 gf"))) {

                                        if (!j.getMin().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMin-" + STDCounter + "}", j.getMin()));
                                        if (!j.getMax().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMax-" + STDCounter + "}", j.getMax()));


                                    }

                                }
                                break;
                                case ("500g"): {
                                    if ((j.getParamName().contains("HV")) && (j.getParamName().contains("500 gf"))) {
                                        if (!j.getMin().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMin-" + STDCounter + "}", j.getMin()));
                                        if (!j.getMax().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMax-" + STDCounter + "}", j.getMax()));


                                    }

                                }
                                break;
                                case ("2kg"): {
                                    if ((j.getParamName().contains("HV")) && (j.getParamName().contains("2 kgf"))) {
                                        if (!j.getMin().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMin-" + STDCounter + "}", j.getMin()));
                                        if (!j.getMax().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMax-" + STDCounter + "}", j.getMax()));


                                    }

                                }
                                break;


                            }


                        } else if (j.getParamName().contains("HK")) {


                            switch (Main.selection.getMicroLoad()) {

                                case ("100g"): {
                                    if ((j.getParamName().contains("HK")) && (j.getParamName().contains("100 gf"))) {

                                        if (!j.getMin().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMin-" + STDCounter + "}", j.getMin()));
                                        if (!j.getMax().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMax-" + STDCounter + "}", j.getMax()));


                                    }

                                }
                                break;
                                case ("200g"): {
                                    if ((j.getParamName().contains("HK")) && (j.getParamName().contains("200 gf"))) {


                                        if (!j.getMin().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMin-" + STDCounter + "}", j.getMin()));
                                        if (!j.getMax().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMax-" + STDCounter + "}", j.getMax()));


                                    }

                                }
                                break;
                                case ("500g"): {
                                    if ((j.getParamName().contains("HK")) && (j.getParamName().contains("500 gf"))) {

                                        if (!j.getMin().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMin-" + STDCounter + "}", j.getMin()));
                                        if (!j.getMax().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMax-" + STDCounter + "}", j.getMax()));


                                    }

                                }
                                break;
                                case ("2kg"): {
                                    if ((j.getParamName().contains("HK")) && (j.getParamName().contains("2 kgf"))) {

                                        if (!j.getMin().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMin-" + STDCounter + "}", j.getMin()));
                                        if (!j.getMax().equals("-"))
                                            variables.addTextVariable(new TextVariable("#{MicroHRMax-" + STDCounter + "}", j.getMax()));


                                    }

                                }
                                break;


                            }
                        }


                    }

                }

                if (Main.selection.getImpactTemp() != null) {

                    if (j.getTestName().contains("Impact")) {

                        switch (Main.selection.getImpactTemp()) {

                            case ("Room Temperature"): {
                                if ((j.getParamName().contains("Room Temperature"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;
                            case ("0°C"): {
                                if ((j.getParamName().contains("0°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;
                            case ("-5°C"): {
                                if ((j.getParamName().contains("-5°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;
                            case ("-10°C"): {
                                if ((j.getParamName().contains("-10°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;

                            case ("-15°C"): {
                                if ((j.getParamName().contains("-15°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;

                            case ("-20°C"): {
                                if ((j.getParamName().contains("-20°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;

                            case ("-25°C"): {
                                if ((j.getParamName().contains("-25°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;

                            case ("-30°C"): {
                                if ((j.getParamName().contains("-30°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;

                            case ("-35°C"): {
                                if ((j.getParamName().contains("-35°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;

                            case ("-40°C"): {
                                if ((j.getParamName().contains("-40°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;

                            case ("-45°C"): {
                                if ((j.getParamName().contains("-45°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;


                            case ("-50°C"): {
                                if ((j.getParamName().contains("-50°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;


                            case ("-55°C"): {
                                if ((j.getParamName().contains("-55°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;


                            case ("-60°C"): {
                                if ((j.getParamName().contains("-60°C"))) {

                                    if (!j.getMin().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMin-" + STDCounter + "}", j.getMin()));
                                    if (!j.getMax().equals("-"))
                                        variables.addTextVariable(new TextVariable("#{ImpactMax-" + STDCounter + "}", j.getMax()));


                                }

                            }
                            break;


                        }


                    }
                }


                if (j.getTestName().equals("Chemical")) {

                    ChemicalArray.add(j);
                }


            }


            //Should return two arrays with the chemical data only from both standards
            if (!ChemicalArray.isEmpty()) {
                ArrayList<ParamObject> NewList = new ArrayList<>();
                for (ParamObject e : ChemicalArray) {

                    NewList.add(e);
                }

                AllChemicalArray.add(NewList);
            }

            ChemicalArray.clear();

        }


        return AllChemicalArray;

    }


    private static void fillTemplate() throws Exception {


        variables = new Variables();

        int StandardCounter = 1;

        ArrayList<ArrayList<ParamObject>> g;

        rootDir = Paths.get("").toAbsolutePath().toString() + "\\IKADOCS_Reports" + "\\" + Main.selection.ReportName + ".docx";

        g = StandardDataFiller();
        GeneralDlFiller();
        FillHeaderData();

        if (!ImportController.ChemicalFiles.isEmpty()) {
            if (StandardSelectController.StandardSelected == true) {


                g = OrederTheLists(g);

                for (ArrayList<ParamObject> m : g) {

                    ChemicalElements(m, StandardCounter);
                    StandardCounter++;
                }

            }


        } else {

            ChemicalElements(new ArrayList<ParamObject>(), StandardCounter);
        }


        TensileDataFiller();
        FillHardness();
        FillMicro();
        docx.fillTemplate(variables);


        if (OverwriteFile(rootDir) == true) {
            try {
                docx.save(rootDir);
                ReportCleaner();
                ReadAndChangeFooter();
            } catch (Exception e) {

                if (Main.Hebrew == true) {
                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "הדוח פתוח באחד המחשבים",
                            "לא ניתן לשמור כאשר הדוח פתוח");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();
                } else {
                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "Report is Opened",
                            "Cannot save on Opened Report ");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();
                }


                GenerateController.NoDB = true;
            }

        } else {
            GenerateController.NoDB = true;
            //  docx.save(rootDir);
            //  ReportCleaner();

        }


        chemicals.clear();
        TensileData.clear();
    }

    public static boolean OverwriteFile(String file) {

        File f = new File(file);

        if (f.exists()) {


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            if (Main.Hebrew == true) {
                alert.setTitle("הודעת הזהרה");
                alert.setHeaderText("הדוח כבר קיים");
                alert.setContentText("האם לשמור על דוח קיים");
            } else {

                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Report already exist");
                alert.setContentText("Do you want to overwrite this report?");
            }


            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                GenerateController.NoDB = true;
                return true;

            } else return false;

        }

        return true;
    }

    public void GenerateReport() throws Exception {

        DataFiller(9);
        if (Controller.reportFile != null)
            docx = new Docx(Controller.reportFile.getPath());
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (Main.Hebrew == true) {
                alert.setTitle("שגיאה");
                alert.setHeaderText("לא נבחרה תבנית לדוח");
                alert.setContentText("לא נבחרה תבנית לדוח");
            } else {
                alert.setTitle("Error Dialog!");
                alert.setHeaderText("You did not open a template file!!!");
                alert.setContentText("You did not open a template file!!!");
            }


            alert.showAndWait();

        }


        docx.setVariablePattern(new VariablePattern("#{", "}"));

        StartGenerator.fillTemplate();


    }


    public static int CountClm(Sheet sheet, DataFormatter dataFormatter) {

        int i = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {

                String cellValue = dataFormatter.formatCellValue(cell);

                i++;

            }
        }
        return i;
    }


    public static boolean Numeric(String string) {

        boolean numeric = true;

        try {
            Double num = Double.parseDouble(string);

        } catch (NumberFormatException e) {
            numeric = false;
        }

        if (numeric)
            return true;
        else
            return false;
    }

    public static ExcelChemical Rounder(String ChemicalName, String result, String StdNumMin, String StdNumMax) {

        ExcelChemical chemical = new ExcelChemical();

        int decimalPlacesMin = 0;
        int decimalPlacesMax = 0;
        int resultDecimal = 0;

        if (result.contains(",")) {
            result.replace(",", ".");
        }


        if (!Numeric(StdNumMin) && !Numeric(StdNumMax)) {

            ExcelChemical ex = new ExcelChemical();
            ex.setChemicalName(ChemicalName);
            ex.setPercent(result);
            return ex;

        }


        String[] stdSplitMin = StdNumMin.split("\\.");
        String[] stdSplitMax = StdNumMax.split("\\.");

        // Before Decimal Count

        String[] resultSplit = result.split("\\.");

        // Before Decimal Count

        if (result.contains("<") || result.contains(">")) {

            result = result.replaceAll("\\s+", "");
            //  result.replaceAll(">","");
            //  result.replaceAll("<","");

            chemical.setChemicalName(ChemicalName);
            chemical.setPercent(result);
            return chemical;


        }

//ToDO add protection vs Integer Values

        if (Numeric(StdNumMin)) {
            if (!StdNumMin.equals("-"))
                if (stdSplitMin.length > 1) {
                    decimalPlacesMin = stdSplitMin[1].length();
                }

        }
        if (Numeric(StdNumMax)) {
            if (!StdNumMax.equals("-"))
                if (stdSplitMax.length > 1) {
                    decimalPlacesMax = stdSplitMax[1].length();
                }
        }

        if (Numeric(result)) {
            if (!result.equals("-"))
                if (resultSplit.length > 1) {
                    resultDecimal = resultSplit[1].length();
                }
        }

        if ((decimalPlacesMax > decimalPlacesMin) || !Numeric(StdNumMin)) {

            //Round around Max

            chemical.setChemicalName(ChemicalName);
            chemical.setPercent(ChemicalRoundedResult(StdNumMax, result));
            return chemical;

        } else {

            chemical.setChemicalName(ChemicalName);
            chemical.setPercent(ChemicalRoundedResult(StdNumMin, result));
            return chemical;


        }

    }


    public static boolean FindChemicalInArray(ExcelChemical result, ArrayList<ParamObject> stdArray) {

        boolean found = false;


        for (ParamObject p : stdArray) {

            if (result.getChemicalName().equals(p.getParamName())) {

                found = true;

            }

        }


        return found;
    }


    //Is rounding according to the standard
    public static void roundResults() {
        roundedList.clear();


        String maxEach = "0";
        Double sumTotal = 0.0;

        ExcelChemical RestChemical = new ExcelChemical();

        ArrayList<ParamObject> CleanList = new ArrayList<>();

        ArrayList<ParamObject> OnlyChemicals = new ArrayList<>();


        if (StandardSelectController.StandardSelected == true) {

            //Round Around the first standard on the list
            ArrayList<ParamObject> List = Main.selection.getAllStandardsList().get(0);

            int i = 0;

            //List = refers to the standard
            //chemicals = refers to the results


            for (ParamObject p : List) {

                if (p.getTestName().equals("Chemical")) {
                    OnlyChemicals.add(p);
                }


            }


            for (ExcelChemical p : new ArrayList<ExcelChemical>(chemicals)) {
                if (p.getPercent().equals("") && p.getChemicalName().equals("")) {

                    chemicals.remove(p);

                }
            }


            for (ParamObject p : new ArrayList<ParamObject>(OnlyChemicals)) {
                if (p.getMin().equals("Rest") && p.getMax().equals("Rest")) {


                    RestChemical.setChemicalName(p.getParamName());
                    RestChemical.setPercent("Rest");

                    OnlyChemicals.remove(p);


                }
            }

            for (ParamObject p : new ArrayList<ParamObject>(OnlyChemicals)) {
                if (p.getMin().equals("Rest") && p.getMax().equals("Rest")) {


                    RestChemical.setChemicalName(p.getParamName());
                    RestChemical.setPercent("Rest");

                    OnlyChemicals.remove(p);


                }
            }


            for (ParamObject p : OnlyChemicals) {

                if (!p.getParamName().equals("Others") && !p.getParamName().equals("Total") && !p.getParamName().equals("Each") && !p.getMin().equals("Rest") && !p.getMax().equals("Rest")) {
                    CleanList.add(p);

                }


            }


            //Calculate the Total
            for (ParamObject p : OnlyChemicals) {

                if (p.getParamName().equals("Total")) {


                    for (ExcelChemical chemical : chemicals) {


                        if ((FindChemicalInArray(chemical, CleanList) == false) && Numeric(chemical.getPercent())) {


                            if (!chemical.getChemicalName().equals(RestChemical.getChemicalName()))
                                sumTotal = sumTotal + Double.parseDouble(chemical.getPercent());

                        }

                    }


                }

            }
            //Find the Max Each
            for (ParamObject p : OnlyChemicals) {

                if (p.getParamName().equals("Each")) {


                    for (ExcelChemical chemical : chemicals) {


                        if (FindChemicalInArray(chemical, CleanList) == false) {

                            if (Numeric(chemical.getPercent())) {

                                if (Double.parseDouble(maxEach) < Double.parseDouble(chemical.getPercent())) {

                                    if (!chemical.getChemicalName().equals(RestChemical.getChemicalName()))
                                        maxEach = chemical.getPercent();


                                }


                            }

                        }


                    }


                }

            }


            //Add the Each to the roundlist if not 0
            for (ParamObject p : OnlyChemicals) {
                if (p.getParamName().equals("Each"))
                    if (!maxEach.equals("0")) {

                        roundedList.add(Rounder(p.getParamName(), maxEach, p.getMin(), p.getMax()));

                    }


                //Add the Total to the roundlist if not 0
                if (p.getParamName().equals("Total"))
                    if (sumTotal > 0) {

                        roundedList.add(Rounder(p.getParamName(), Double.toString(sumTotal), p.getMin(), p.getMax()));

                    }
            }


            //Takes care of the case where one of the standard parameters is not numeric(ex 5XC or "-")
            for (ParamObject p : new ArrayList<ParamObject>(OnlyChemicals)) {

                if (!Numeric(p.getMin()) || !Numeric(p.getMax())) {


                    //If the minimum of the STD is not numeric, round around maximum
                    if (!Numeric(p.getMin())) {

                        //ToDo round over the maximum and add to roundList
                        // run over the list of excel chemicals and if

                        for (ExcelChemical chemical : chemicals) {

                            if (p.getParamName().equals(chemical.getChemicalName())) {


                                roundedList.add(Rounder(p.getParamName(), chemical.getPercent(), "-", p.getMax()));
                                OnlyChemicals.remove(p);


                            }


                        }

                        //If the Maximum is not numeric, round around Minimum
                    } else if (!Numeric(p.getMax())) {

                        //ToDo round over the minimum add to roundList
                        for (ExcelChemical chemical : chemicals) {

                            if (p.getParamName().equals(chemical.getChemicalName())) {

                                roundedList.add(Rounder(p.getParamName(), chemical.getPercent(), "-", p.getMin()));
                                OnlyChemicals.remove(p);

                            }


                        }

                        //if both are not numeric, do not round at all
                    } else if (!Numeric(p.getMax()) && !Numeric(p.getMin())) {

                        //ToDo do not round at All add to roundList!!!

                        for (ExcelChemical chemical : chemicals) {

                            if (p.getParamName().equals(chemical.getChemicalName())) {


                                roundedList.add(chemical);
                                OnlyChemicals.remove(p);


                            }


                        }

                    }


                    //To round when both are numeric!!

                } else if (Numeric(p.getMin()) && Numeric(p.getMax())) {


                    for (ExcelChemical chemical : chemicals) {

                        if (p.getParamName().equals(chemical.getChemicalName())) {

                            roundedList.add(Rounder(p.getParamName(), chemical.getPercent(), p.getMin(), p.getMax()));

                            OnlyChemicals.remove(p);

                        }
                    }


                }


            }

            if (RestChemical.getChemicalName() != null && !RestChemical.getChemicalName().equals(""))
                roundedList.add(RestChemical);


        }


    }


    public static int FindRow(File f) throws IOException, InvalidFormatException {
        try {

            Workbook workbook = WorkbookFactory.create(f);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            int i = CountClm(sheet, dataFormatter);

            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int columnIndex = 0; columnIndex < i; columnIndex++) {

                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    Row row = CellUtil.getRow(rowIndex, sheet);
                    Cell cell = CellUtil.getCell(row, columnIndex);

                    String cellValue = dataFormatter.formatCellValue(cell);

                    if (cellValue.contains("Ø")) {

                        return rowIndex;

                    }


                }

            }

        } catch (Exception e) {

            if (Main.Hebrew == true) {
                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "האקסל של התוצאות הכימיה פתוח במחשב כלשהו",
                        "אנא סגור את קובץ תוצאות הכימיה כדי להכין את הדוח");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();

            } else {


                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "Result Excel is Opened",
                        "Cannot Work with opened Excel");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();
            }


        }


        return 0;

    }


    //Is reading the raw chemicals from excel file
    public static void ReadChemical(File f) throws IOException, InvalidFormatException {

        chemicals.clear();


        int ResultRow = FindRow(f);


        Workbook workbook = WorkbookFactory.create(f);

        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        //Here i need to count rows, and change the row indexes according to the count

        int i = CountClm(sheet, dataFormatter);

        for (int columnIndex = 0; columnIndex < i; columnIndex++) {

            ExcelChemical e = new ExcelChemical();

            for (int rowIndex = 0; rowIndex <= ResultRow; rowIndex++) {
                Row row = CellUtil.getRow(rowIndex, sheet);
                Cell cell = CellUtil.getCell(row, columnIndex);

                String cellValue = dataFormatter.formatCellValue(cell);

                if (rowIndex == 0)
                    e.setChemicalName(cellValue);

                if (rowIndex == ResultRow) {

                    e.setPercent(cellValue);
                }


            }
            chemicals.add(e);
        }


        if (!chemicals.isEmpty()) {

            chemicals.remove(0);
            chemicals.remove(0);
        }


        if (StandardSelectController.StandardSelected)
            roundResults();

    }


    public static void ReportCleaner() {

        Docx docxcleaner = new Docx(Paths.get("").toAbsolutePath().toString() + "\\IKADOCS_Reports" + "\\" + Main.selection.ReportName + ".docx");

        Variables vr = new Variables();


        docxcleaner.setVariablePattern(new VariablePattern("#{", "}"));


        vr.addTextVariable(new TextVariable("#{Dt}", ""));

        vr.addTextVariable(new TextVariable("#{RpN}", ""));

        //  vr.addTextVariable(new TextVariable("#{Mt}", ""));

        vr.addTextVariable(new TextVariable("#{HtN}", ""));

        vr.addTextVariable(new TextVariable("#{HtN}", ""));

        vr.addTextVariable(new TextVariable("#{AdR}", ""));

        vr.addTextVariable(new TextVariable("#{Sz}", ""));

        vr.addTextVariable(new TextVariable("#{PtD}", ""));

        vr.addTextVariable(new TextVariable("#{CoN}", ""));


        vr.addTextVariable(new TextVariable("#{Dm}", ""));


        vr.addTextVariable(new TextVariable("#{RpN}", ""));

        vr.addTextVariable(new TextVariable("#{RpFull}", ""));


        vr.addTextVariable(new TextVariable("#{PrN}", ""));


        vr.addTextVariable(new TextVariable("#{ApN}", ""));

        vr.addTextVariable(new TextVariable("#{ApT}", ""));

        vr.addTextVariable(new TextVariable("#{PrT}", ""));

        vr.addTextVariable(new TextVariable("#{Unit}", ""));

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {

                vr.addTextVariable(new TextVariable("#{SpT-" + i + "}", "-"));

                vr.addTextVariable(new TextVariable("#{Rs" + i + "-" + j + "}", "-"));

                vr.addTextVariable(new TextVariable("#{" + "el" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{" + "sdmin-" + j + "-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{" + "sdmax-" + j + "-" + i + "}", "-"));

                vr.addTextVariable(new TextVariable("#{HeadPr-" + +i + "-" + j + "}", ""));

                vr.addTextVariable(new TextVariable("#{HeadDt-" + i + "-" + j + "}", ""));


                vr.addTextVariable(new TextVariable("#{YPMPaRs-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{UTSMPaRs-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{ELRs-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{RdARs-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{YPKSIRs-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{UTSKSIRs-" + i + "}", "-"));

                vr.addTextVariable(new TextVariable("#{UTSRs-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{YPRs-" + i + "}", "-"));

                vr.addTextVariable(new TextVariable("#{UTSMin-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{UTSMax-" + i + "}", "-"));


                vr.addTextVariable(new TextVariable("#{YPMin-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{YPMax-" + i + "}", "-"));


                vr.addTextVariable(new TextVariable("#{ELMin-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{ELMax-" + i + "}", "-"));


                vr.addTextVariable(new TextVariable("#{RdAMin-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{RdAMax-" + i + "}", "-"));

                vr.addTextVariable(new TextVariable("#{HRMin-" + i + "}", "-"));
                vr.addTextVariable(new TextVariable("#{HRMax-" + i + "}", "-"));
            }


        }


        docx.fillTemplate(vr);
        docx.save(Paths.get("").toAbsolutePath().toString() + "\\IKADOCS_Reports" + "\\" + Main.selection.ReportName + ".docx");

    }


    public static void ReadAndChangeFooter() throws Exception {

        FileInputStream fis = new FileInputStream(rootDir);
        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(xdoc);

        if (UserController.ReportLng.contains("Hebrew")) {

            replaceTextInFooter(xdoc, "דו\"ח מס'", Main.selection.ReportName + " 'דו\"ח מס ");


            String[] RepNum = Main.selection.ReportName.split("-");


            if (RepNum.length >= 3) {

                if (RepNum.length > 1) {
                    replaceTextInFooter(xdoc, "מס' עבודה:", RepNum[1] + "-" + RepNum[2] + " :'מס עבודה ");
                }

            }


        } else {


            String[] RepNum = Main.selection.ReportName.split("-");


            replaceTextInFooter(xdoc, "Report:", "Report: " + Main.selection.ReportName);


            if (RepNum.length > 1) {
                replaceTextInFooter(xdoc, "Work No.", "Work No. " + RepNum[1] + "-" + RepNum[2]);
            }

        }


        xdoc.write(new FileOutputStream(rootDir));


    }

    private static void replaceTextInFooter(XWPFDocument doc, String findText, String replaceText) {
        for (XWPFFooter footer : doc.getFooterList()) {
            for (XWPFTable T : footer.getTables()) {
                for (XWPFTableRow run : T.getRows()) {
                    for (XWPFTableCell cell : run.getTableCells()) {

                        String text = cell.getText();
                        if (text.contains(findText)) {

                            cell.removeParagraph(0);

                            XWPFParagraph paragraph = cell.addParagraph();
                            paragraph.setAlignment(ParagraphAlignment.CENTER);
                            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);


                            setRun(paragraph.createRun(), "Ariel", 8, "000000", replaceText, false, false);

                        }

                    }


                }
            }
        }


    }


    private static void setRun(XWPFRun run, String fontFamily, int fontSize, String colorRGB, String text, boolean bold, boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text, 0);
        run.setBold(bold);
        if (addBreak) run.addBreak();
    }

    public static String ChemicalRoundedResult(String StandardNum, String Result) {

        int decimalStandard = 0;
        int resultDecimal = 0;
        String RoundedResult = null;
        //Entering this function i already assume both StandardNum and Result are numbers!

        if (Result.contains(",")) {
            Result.replace(",", ".");
        }


        String[] stdSplitNum = StandardNum.split("\\.");

        String[] resultSplit = Result.split("\\.");

        if (stdSplitNum.length > 1) {
            decimalStandard = stdSplitNum[1].length();
        }

        if (resultSplit.length > 1) {
            resultDecimal = resultSplit[1].length();
        }


        if (decimalStandard == resultDecimal) {
            RoundedResult = Result;
            return RoundedResult;
        }

        if (decimalStandard > resultDecimal) {


            for(int i= resultDecimal;i<decimalStandard;i++)
                Result = Result + "0";


            return Result;
        }


        if (decimalStandard < resultDecimal) {


            int counter =  decimalStandard + 1;
            int sumAfterTheFive = 0;


            if (Integer.valueOf(resultSplit[1].charAt(decimalStandard - 1)) == '0') {


                if(resultSplit[0].equals("0")){
                    double pow = Math.pow(10,-decimalStandard);
           //Here a problem with 0.40 standard and 0.603 result
                    if(pow>Double.valueOf(Result)){

                        String g = "<" + pow;
                        return g;

                    }else{

                        String bd = new BigDecimal(Double.valueOf(Result)).setScale(decimalStandard, RoundingMode.HALF_EVEN).toString();
                        return bd;

                    }


                } else {

                    String bd = new BigDecimal(Double.valueOf(Result)).setScale(decimalStandard, RoundingMode.HALF_EVEN).toString();
                    return bd;


                }
                //Need to find how many decimal places in standard, and lower than

            }


            if (Integer.valueOf(resultSplit[1].charAt(counter-1)) < '5') {
                //Round Down to the amount of digits at the standard and return
                String bd = new BigDecimal(Double.valueOf(Result)).setScale(decimalStandard, RoundingMode.HALF_EVEN).toString();
                return bd;

            } else if (Integer.valueOf(resultSplit[1].charAt(counter-1)) > '5') {
                //Round up to the amount of digits at the standard and return


                String bd = new BigDecimal(Double.valueOf(Result)).setScale(decimalStandard, RoundingMode.HALF_EVEN).toString();
                return bd;

            } else if (Integer.valueOf(resultSplit[1].charAt(counter-1)) == '5'){


                while (counter < resultDecimal) {


                   if(resultSplit[1].length()>counter)
                    sumAfterTheFive += Integer.valueOf(Character.toString(resultSplit[1].charAt(counter)));


                    counter++;

                }

            if(sumAfterTheFive>0){
                String bd = new BigDecimal(Double.valueOf(Result)).setScale(decimalStandard, RoundingMode.HALF_UP).toString();
                return bd;

            } else {

                //Here make real Half Even Rounding!!!
                if(Integer.valueOf(Character.toString(resultSplit[1].charAt(decimalStandard-1)))%2==0){

                    String g = Result.substring(0,Result.length()-1);
                    return g;

                } else {
                    String bd = new BigDecimal(Double.valueOf(Result)).setScale(decimalStandard, RoundingMode.HALF_UP).toString();
                    return bd;
                }




            }



            }

            return RoundedResult;

        }

        return RoundedResult;

    }
}