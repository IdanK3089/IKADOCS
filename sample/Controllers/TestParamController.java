package sample.Controllers;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import sample.ExcelHandle.ExcelChemical;
import sample.Main;
import sample.TestPojos.ParamObject;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TestParamController implements Initializable {



    @FXML
    JFXCheckBox RoundAroundCB;

    @FXML
    JFXCheckBox CBox;

    @FXML
    JFXCheckBox SiBox;

    @FXML
    JFXCheckBox CoBox;

    @FXML
    JFXCheckBox SBox;

    @FXML
    JFXCheckBox PBox;

    @FXML
    JFXCheckBox WBox;

    @FXML
    JFXCheckBox BBox;

    @FXML
    JFXCheckBox SnBox;

    @FXML
    JFXCheckBox NBox;

    @FXML
    JFXCheckBox NiBox;

    @FXML
    JFXCheckBox CuBox;

    @FXML
    JFXCheckBox TiBox;

    @FXML
    JFXCheckBox AlBox;

    @FXML
    JFXCheckBox NbBox;

    @FXML
    JFXCheckBox MgBox;

    @FXML
    JFXCheckBox SeBox;

    @FXML
    JFXCheckBox PbBox;

    @FXML
    JFXCheckBox AsBox;

    @FXML
    JFXCheckBox SrBox;

    @FXML
    JFXCheckBox MnBox;

    @FXML
    JFXCheckBox MoBox;

    @FXML
    JFXCheckBox AgBox;

    @FXML
    JFXCheckBox ZnBox;

    @FXML
    JFXCheckBox VBox;

    @FXML
    JFXCheckBox TaBox;

    @FXML
    JFXCheckBox FeBox;

    @FXML
    JFXCheckBox BiBox;

    @FXML
    JFXCheckBox ZrBox;



    @FXML
    ChoiceBox<String> TensileSpecimenCB;

    @FXML
    ChoiceBox<String> TensileUnitCB;

    @FXML
    ChoiceBox<String> TensileThicknessCB;

    @FXML
    ChoiceBox<String> TensileStandardCB;

    @FXML
    ChoiceBox<String> TensileDirectionCB;

    @FXML
    ChoiceBox<String> HardnessMethodCB;

    @FXML
    ChoiceBox<String> HardnessLoadCB;

    @FXML
    ChoiceBox<String> HardnessTypeCB;

    @FXML
    ChoiceBox<String> HardnessLayoutCB;

    @FXML
    ChoiceBox<String> MicroMethodCB;

    @FXML
    ChoiceBox<String> MicroLoadCB;

    @FXML
    ChoiceBox<String> MicroTypeCB;

    @FXML
    ChoiceBox<String> MicroLayoutCB;

    @FXML
    ChoiceBox<String> MetaLocationCB;

    @FXML
    ChoiceBox<String> ImpactStandardCB;

    @FXML
    ChoiceBox<String> ImpactSizeCB;

    @FXML
    ChoiceBox<String> ImpactTestLocCB;

    @FXML
    ChoiceBox<String> ImpactLayoutCB;

    @FXML
    ChoiceBox<String> BendDiameterCB;

    @FXML
    ChoiceBox<String> BendDistanceCB;

    @FXML
    ChoiceBox<String> BendAngleCB;

    @FXML
    ChoiceBox<String> ImpactTempCB;

    @FXML
    ChoiceBox<String> MicroStdCB;


   public static ArrayList<ExcelChemical> additionalChemicals = new ArrayList<>();

    public static boolean RoundAround = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    //    LoggedName.setText(LoginController.LogName);


        MicroStdCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");
        MicroStdCB.getItems().add("ASTM E384");
        MicroStdCB.getItems().add("ASTM E92");


        BendDiameterCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");
        BendDiameterCB.getItems().add("10 mm");
        BendDiameterCB.getItems().add("30 mm");

        TensileSpecimenCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");

        TensileSpecimenCB.getItems().add("4D");
        TensileSpecimenCB.getItems().add("5D");
        TensileSpecimenCB.getItems().add("Rectangular");
        TensileSpecimenCB.getItems().add("Pipe");
        TensileSpecimenCB.getItems().add("Bolt");
        TensileSpecimenCB.getItems().add("Wire");


        TensileThicknessCB.getItems().add("2.5 mm");
        TensileThicknessCB.getItems().add("4 mm");
        TensileThicknessCB.getItems().add("6 mm");
        TensileThicknessCB.getItems().add("9 mm");
        TensileThicknessCB.getItems().add("12.5 mm");
        TensileThicknessCB.getItems().add("20 mm");


        TensileThicknessCB.setStyle("-fx-font-weight: bold");


        TensileStandardCB.getItems().add("ASME E 8");
        TensileStandardCB.getItems().add("Prepared");

        TensileStandardCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        TensileDirectionCB.getItems().add("Longitudinal");
        TensileDirectionCB.getItems().add("Transverse");
        TensileDirectionCB.getItems().add("Center");
        TensileDirectionCB.getItems().add("Half distance to surface");

        TensileDirectionCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");



        MetaLocationCB.getItems().add("Longitudinal");
        MetaLocationCB.getItems().add("Transverse");


        HardnessMethodCB.getItems().add("HRC");
        HardnessMethodCB.getItems().add("HRB");
        HardnessMethodCB.getItems().add("HRA");
        HardnessMethodCB.getItems().add("HB");

        HardnessMethodCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MicroLoadCB.getItems().add("100g");
        MicroLoadCB.getItems().add("200g");
        MicroLoadCB.getItems().add("500g");
        MicroLoadCB.getItems().add("2kg");

        MicroLoadCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MicroMethodCB.getItems().add("HK");
        MicroMethodCB.getItems().add("HV");


        MicroMethodCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MicroTypeCB.getItems().add("לב");
        MicroTypeCB.getItems().add("מפל");


        MicroTypeCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ImpactStandardCB.getItems().add("ASTM E 23");

        ImpactStandardCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ImpactSizeCB.getItems().add("10x10x55");
        ImpactSizeCB.getItems().add("7.5x10x55");
        ImpactSizeCB.getItems().add("5x10x55");
        ImpactSizeCB.getItems().add("2.5x10x55");


        ImpactSizeCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ImpactTestLocCB.getItems().add("Base Metal");
        ImpactTestLocCB.getItems().add("Weld Metal");
        ImpactTestLocCB.getItems().add("HAZ");
        ImpactTestLocCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ImpactTempCB.getItems().add("Room Temperature");
        ImpactTempCB.getItems().add("0°C");
        ImpactTempCB.getItems().add("-5°C");
        ImpactTempCB.getItems().add("-10°C");
        ImpactTempCB.getItems().add("-15°C");
        ImpactTempCB.getItems().add("-20°C");
        ImpactTempCB.getItems().add("-25°C");
        ImpactTempCB.getItems().add("-30°C");
        ImpactTempCB.getItems().add("-35°C");
        ImpactTempCB.getItems().add("-40°C");
        ImpactTempCB.getItems().add("-45°C");
        ImpactTempCB.getItems().add("-50°C");
        ImpactTempCB.getItems().add("-55°C");
        ImpactTempCB.getItems().add("-60°C");


        ImpactTempCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        BendAngleCB.getItems().add("90°");
        BendAngleCB.getItems().add("180°");
        BendAngleCB.getItems().add("אחר");
        BendAngleCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");



        TensileUnitCB.getItems().add("MPa");
        TensileUnitCB.getItems().add("KSI");
        TensileUnitCB.getItems().add("Kg/mm^2");
        TensileUnitCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");



        setSpecimen(TensileSpecimenCB);
        setUnit(TensileUnitCB);
        setTensileTickness(TensileThicknessCB);
        setTensileStd(TensileStandardCB);
        setTensileDirection(TensileDirectionCB);
        setHardnessMethod(HardnessMethodCB);
        setHardnessLoad(HardnessLoadCB);
        setHardnessType(HardnessTypeCB);
        setMicroMethod(MicroMethodCB);
        setMicroLoad(MicroLoadCB);
        setMicroType(MicroTypeCB);
        setImpactStandard(ImpactStandardCB);
        setImpactSize(ImpactSizeCB);
        setImpactTemp(ImpactTempCB);
        setImpactTestLoc(ImpactTestLocCB);
        setBendAngleCB(BendAngleCB);
        setBendDiameter(BendDiameterCB);
        setBendDistance(BendDistanceCB);
        setMetaLocation(MetaLocationCB);
        setMicroStd(MicroStdCB);



        addRemoveChemical(CBox,"C");
        addRemoveChemical(SiBox,"Si");
        addRemoveChemical(CoBox,"Co");
        addRemoveChemical(SBox,"S");
        addRemoveChemical(PBox,"P");
        addRemoveChemical(WBox,"W");
        addRemoveChemical(BBox,"B");
        addRemoveChemical(SnBox,"Sn");
        addRemoveChemical(NBox,"N");
        addRemoveChemical(NiBox,"Ni");
        addRemoveChemical(CuBox,"Cu");
        addRemoveChemical(TiBox,"Ti");
        addRemoveChemical(AlBox,"Al");
        addRemoveChemical(NbBox,"Nb");
        addRemoveChemical(MgBox,"Mg");
        addRemoveChemical(SeBox,"Se");
        addRemoveChemical(PbBox,"Pb");
        addRemoveChemical(AsBox,"As");
        addRemoveChemical(SrBox,"Sr");
        addRemoveChemical(MnBox,"Mn");
        addRemoveChemical(MoBox,"Mo");
        addRemoveChemical(AgBox,"Ag");
        addRemoveChemical(ZnBox,"Zn");
        addRemoveChemical(VBox,"V");
        addRemoveChemical(TaBox,"Ta");
        addRemoveChemical(FeBox,"Fe");
        addRemoveChemical(BiBox,"Bi");
        addRemoveChemical(ZrBox,"Zr");




    }
@FXML
    public void setRound(){

        if(RoundAroundCB.isSelected()){

            RoundAround = true;

        }



    }

    public void addRemoveChemical(JFXCheckBox btn,String chemicalName){

        ExcelChemical ch = new ExcelChemical();

        btn.setOnAction(event -> {


            if(btn.isSelected()){

                ch.setChemicalName(btn.getText());

                additionalChemicals.add(ch);

            } else {

                for(ExcelChemical e: new ArrayList<>(additionalChemicals)){


                    if(e.getChemicalName().equals(chemicalName)){

                        additionalChemicals.remove(e);

                    }



                }




            }




        });

    }





    public void setSpecimen(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setTensileSpecimen(btn.getValue().toString());

                });

    }

    public void setUnit(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setTensileUnit(btn.getValue().toString());

        });

    }

    public void setTensileTickness(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setTensileThickness(btn.getValue().toString());

        });

    }

    public void setTensileStd(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setTensileSTD(btn.getValue().toString());

        });

    }

    public void setTensileDirection(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setTensileDirection(btn.getValue().toString());

        });

    }

    public void setHardnessMethod(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setHardnessMethod(btn.getValue().toString());

        });

    }

    public void setHardnessLoad(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setHardnessLoad(btn.getValue().toString());

        });

    }

    public void setHardnessType(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setHardnessTestType(btn.getValue().toString());

        });

    }

    public void setMicroMethod(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setMicroMethod(btn.getValue().toString());

        });

    }

    public void setMicroLoad(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setMicroLoad(btn.getValue().toString());

        });

    }

    public void setMicroType(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setMicroTestType(btn.getValue().toString());

        });

    }



    public void setImpactStandard(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setImpactSTD(btn.getValue().toString());

        });

    }

    public void setImpactSize(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setImpactSize(btn.getValue().toString());
        });

    }


    public void setImpactTestLoc(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setImpactTestLoc(btn.getValue().toString());
        });

    }

    public void setImpactTemp(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setImpactTemp(btn.getValue().toString());
        });

    }

    public void setBendDiameter(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setBendDiameter(btn.getValue().toString());
        });

    }

    public void setBendDistance(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setBendDistance(btn.getValue().toString());
        });

    }

    public void setBendAngleCB(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setBendAngle(btn.getValue().toString());
        });

    }


    public void setMicroStd(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setMicroStd(btn.getValue().toString());
        });

    }

    public void setMetaLocation(ChoiceBox btn){

        btn.setOnAction(event -> {
            if(btn.getValue()!=null)
            Main.selection.setMetaLocaion(btn.getValue().toString());
        });

    }

    @FXML
    public void ClearAll(){


     TensileUnitCB.setValue(null);
        MicroStdCB.setValue(null);
        BendDiameterCB.setValue(null);
        TensileSpecimenCB.setValue(null);
        TensileThicknessCB.setValue(null);
        TensileStandardCB.setValue(null);
        TensileDirectionCB.setValue(null);
        MetaLocationCB.setValue(null);
        HardnessMethodCB.setValue(null);
        MicroLoadCB.setValue(null);
        MicroMethodCB.setValue(null);
        MicroTypeCB.setValue(null);
        ImpactStandardCB.setValue(null);
        ImpactSizeCB.setValue(null);
        ImpactTestLocCB.setValue(null);
        ImpactTempCB.setValue(null);
        ImpactTempCB.setValue(null);
        BendAngleCB.setValue(null);

     CBox.setSelected(false);
       SiBox.setSelected(false);
        CoBox.setSelected(false);
       SBox.setSelected(false);
       PBox.setSelected(false);
        WBox.setSelected(false);
        BBox.setSelected(false);
        SnBox.setSelected(false);
       NBox.setSelected(false);
        NiBox.setSelected(false);
       CuBox.setSelected(false);
        TiBox.setSelected(false);
      AlBox.setSelected(false);
        NbBox.setSelected(false);
        MgBox.setSelected(false);
       SeBox.setSelected(false);
        PbBox.setSelected(false);
       AsBox.setSelected(false);
        SrBox.setSelected(false);;
      MnBox.setSelected(false);
        MoBox.setSelected(false);
        AgBox.setSelected(false);
        ZnBox.setSelected(false);
       VBox.setSelected(false);
        TaBox.setSelected(false);
        FeBox.setSelected(false);
       BiBox.setSelected(false);
        ZrBox.setSelected(false);

    }




}
