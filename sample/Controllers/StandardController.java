package sample.Controllers;

import com.github.daytron.simpledialogfx.data.DialogResponse;
import com.github.daytron.simpledialogfx.data.HeaderColorStyle;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.io.FileUtils;
import sample.Database.MemoryHandler;
import sample.Database.MyUser;
import sample.Database.NitStandardParam;
import sample.Filter.FilterSupport;
import sample.Main;
import sample.OtherPojos.MinMaxCellObject;
import sample.OtherPojos.MinMaxTableObj;
import sample.TestPojos.*;

import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Paths;

import java.util.*;

public class StandardController implements Initializable {




    ArrayList<String> Tests = new ArrayList<>();
    ArrayList<String> TensileParams = new ArrayList<>();
    ArrayList<String> ChemicalParams = new ArrayList<>();
    ArrayList<String> HardnessParams = new ArrayList<>();
    ArrayList<String> MicroHardnessParams = new ArrayList<>();
    ArrayList<String> BendParams = new ArrayList<>();
    ArrayList<String> ImpactParams = new ArrayList<>();


    static public PathRecorder rec = new PathRecorder();

    ToggleGroup stdGroup = new ToggleGroup();
    ToggleGroup generalGroup = new ToggleGroup();
    ToggleGroup SpecificGroup = new ToggleGroup();
    ToggleGroup ClassGroup = new ToggleGroup();

    ToggleGroup ShapeGroup = new ToggleGroup();
    ToggleGroup SizeGroup = new ToggleGroup();
    ToggleGroup TestTypeGroup = new ToggleGroup();
    ToggleGroup TestParameterGroup = new ToggleGroup();

    private String lastKey = null;


    @FXML
    TableColumn StandardSelClm;

    @FXML
    TableColumn GeneralSelClm;

    @FXML
    TableColumn SpecificSelClm;

    @FXML
    TableColumn ClassSelClm;

    @FXML
    TableColumn SizeSelClm;

    @FXML
    TableColumn ShapeSelClm;

    @FXML
    TableColumn TypeSelClm;



    @FXML
    TableColumn<TableModel, String> SizeClm;

    @FXML
    TableColumn<TableModel, String> ShapeClm;

    @FXML
    TableColumn<TableModel, String> TestTypeClm;


    @FXML
    TableColumn<TableModel, String> SpecificClm;

    @FXML
    TableColumn<TableModel, String> ClassClm;

    @FXML
    TableView<TableModel> StdTbl;

    @FXML
    TableView<TableModel> ShapeTbl;


    @FXML
    TableView<TableModel> SizeTbl;


    @FXML
    TableView<TableModel> SpecificTbl;


    @FXML
    TableView<TableModel> ClassTbl;

    @FXML
    TableView<TableModel> TestTbl;


    @FXML
    TableColumn<TableModel, String> StdClm;

    @FXML
    TableView<TableModel> GeneralTbl;



    @FXML
    TableColumn<TableModel, String> GeneralClm;

    @FXML
    TextField StandardText;

    @FXML
    TextField GeneralText;


    @FXML
    TextField SpecificText;


    @FXML
    TextField ClassText;


    @FXML
    TextField SizeText;


    @FXML
    TextField ShapeText;



    Stack<TableModel> deletedLines = new Stack<>();


    @FXML
    TableView<MinMaxTableObj> MinMaxTable;

    @FXML
    TableColumn<MinMaxTableObj,String> ParamNameColumn;

    @FXML
    TableColumn<MinMaxTableObj,String> MinColumn;

    @FXML
    TableColumn<MinMaxTableObj,String> MaxColumn;

    @FXML
    TableColumn<MinMaxTableObj,String> UncertainityColumn;

    @FXML
    TableColumn<MinMaxTableObj,String> TolarenceMinColumn;

    @FXML
    TableColumn<MinMaxTableObj,String> TolarenceMaxColumn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initiateArrays();


        StandardSelClm.impl_setReorderable(false);
        TypeSelClm.impl_setReorderable(false);
        ShapeSelClm.impl_setReorderable(false);
        SpecificSelClm.impl_setReorderable(false);
        SizeSelClm.impl_setReorderable(false);
        GeneralSelClm.impl_setReorderable(false);
        ClassSelClm.impl_setReorderable(false);


        // LoggedName.setText(LoginController.LogName);

        StdTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        SpecificTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        GeneralTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        ClassTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        TestTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        SizeTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        ShapeTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");

        ShapeText.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        ClassText.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        StandardText.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        GeneralText.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        SpecificText.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        SizeText.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        ShapeText.setFont(Font.font("ariel", FontWeight.BOLD, 18));




        setClmUp(StdClm);
        setClmUp(GeneralClm);
        setClmUp(SpecificClm);
        setClmUp(ClassClm);
        setClmUp(SizeClm);
        setClmUp(ShapeClm);
        setClmUp(TestTypeClm);

        setTableUp(StdTbl);
        setTableUp(GeneralTbl);
        setTableUp(SpecificTbl);
        setTableUp(ClassTbl);
        setTableUp(ShapeTbl);
        setTableUp(SizeTbl);
        setTableUp(TestTbl);

       TestTbl.setEditable(false);


        MinMaxTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        MinMaxTable.setPlaceholder(new Label(""));



        ParamNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getParameterName().getText().getValue()));

        MinColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMin().getText().getValue()));


        MaxColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMax().getText().getValue()));


        UncertainityColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUncertainty().getText().getValue()));


        TolarenceMinColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getToleranceMin().getText().getValue()));

        TolarenceMaxColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getToleranceMax().getText().getValue()));

        SetParamColumnsUp(MinColumn);
        SetParamColumnsUp(MaxColumn);
        SetParamColumnsUp(UncertainityColumn);
        SetParamColumnsUp(TolarenceMinColumn);
        SetParamColumnsUp(TolarenceMaxColumn);



        MinColumn.setEditable(true);
        MaxColumn.setEditable(true);
        UncertainityColumn.setEditable(true);
        TolarenceMinColumn.setEditable(true);
        TolarenceMaxColumn.setEditable(true);
        MinMaxTable.setEditable(true);


        //ToDo add the all the columns

        PopulateTable(StdTbl,StdClm,stdGroup,0);

        // PopulateTable(GeneralTbl,GeneralClm,generalGroup);


    }


    public void initiateArrays(){


        TensileParams.add("UTS(MPa)");
        TensileParams.add("UTS(KSI)");
        TensileParams.add("Yield Point(MPa)");
        TensileParams.add("Yield Point(KSI)");

        TensileParams.add("% Elongation");
        TensileParams.add("% Area Reduction");
        TensileParams.add("Max Load(N)");

        BendParams.add("Bend Factor");


        ChemicalParams.add("C");
        ChemicalParams.add("Si");
        ChemicalParams.add("Mn");
        ChemicalParams.add("P");
        ChemicalParams.add("S");
        ChemicalParams.add("Cr");
        ChemicalParams.add("Ni");
        ChemicalParams.add("Mo");
        ChemicalParams.add("Al");
        ChemicalParams.add("Cu");
        ChemicalParams.add("Co");
        ChemicalParams.add("Ti");
        ChemicalParams.add("Nb");
        ChemicalParams.add("V");
        ChemicalParams.add("W");
        ChemicalParams.add("Pb");
        ChemicalParams.add("Mg");
        ChemicalParams.add("B");
        ChemicalParams.add("Be");
        ChemicalParams.add("Sn");
        ChemicalParams.add("Zn");
        ChemicalParams.add("Se");
        ChemicalParams.add("As");
        ChemicalParams.add("Bi");
        ChemicalParams.add("Ta");
        ChemicalParams.add("Zr");
        ChemicalParams.add("N");
        ChemicalParams.add("Sb");
        ChemicalParams.add("Cd");
        ChemicalParams.add("Fe");
        ChemicalParams.add("Sr");
        ChemicalParams.add("Ca");
        ChemicalParams.add("Y");

        ChemicalParams.add("Ag");
        ChemicalParams.add("Each");
        ChemicalParams.add("Total");
        ChemicalParams.add("Others");


        // ChemicalParams.add("Custom");

        MicroHardnessParams.add("HV 1 kgf");
        MicroHardnessParams.add("HV 2 kgf");
        MicroHardnessParams.add("HV 3 kgf");
        MicroHardnessParams.add("HV 5 kgf");
        MicroHardnessParams.add("HV 10 kgf");
        MicroHardnessParams.add("HV 20 kgf");
        MicroHardnessParams.add("HV 30 kgf");
        MicroHardnessParams.add("HV 50 kgf");

        MicroHardnessParams.add("HV 10 gf");
        MicroHardnessParams.add("HV 25 gf");
        MicroHardnessParams.add("HV 50 gf");
        MicroHardnessParams.add("HV 100 gf");
        MicroHardnessParams.add("HV 200 gf");
        MicroHardnessParams.add("HV 300 gf");
        MicroHardnessParams.add("HV 500 gf");

        MicroHardnessParams.add("HK 10 gf");
        MicroHardnessParams.add("HK 25 gf");
        MicroHardnessParams.add("HK 50 gf");
        MicroHardnessParams.add("HK 100 gf");
        MicroHardnessParams.add("HK 200 gf");
        MicroHardnessParams.add("HK 300 gf");
        MicroHardnessParams.add("HK 500 gf");
        MicroHardnessParams.add("HK 1 kgf");
        MicroHardnessParams.add("HK 2 kgf");
      //  MicroHardnessParams.add("Custom");




        HardnessParams.add("HRB 100 kgf, 1/16' BALL");
        HardnessParams.add("HRC 150 kgf, Diamond Cone");
        HardnessParams.add("HRA 60 kgf, Diamond Cone");
        HardnessParams.add("HRD 100 kgf, Diamond Cone");
        HardnessParams.add("HRE 60 kgf, 1/8' BALL");
        HardnessParams.add("HRF 60 kgf, 1/16' BALL");
        HardnessParams.add("HRG 150 kgf, 1/16' BALL");
        HardnessParams.add("HRH 60 kgf, 1/8' BALL");
        HardnessParams.add("HRK 150 kgf, 1/8' BALL");
        HardnessParams.add("HR 15N 15 kgf, Diamond Cone");
        HardnessParams.add("HR 30N 30 kgf, Diamond Cone");
        HardnessParams.add("HR 45N 45 kgf, Diamond Cone");
        HardnessParams.add("HR 15T 15 kgf, 1/16' BALL");
        HardnessParams.add("HR 30T 30 kgf, 1/16' BALL");
        HardnessParams.add("HR 45T 45 kgf, 1/16' BALL");
        HardnessParams.add("HBW 3000 kgf");
        HardnessParams.add("HB");




        ImpactParams.add("Room Temperature");
        ImpactParams.add("0°");
        ImpactParams.add("-5°");
        ImpactParams.add("-10°");
        ImpactParams.add("-15°");
        ImpactParams.add("-20°");
        ImpactParams.add("-25°");
        ImpactParams.add("-30°");
        ImpactParams.add("-35°");
        ImpactParams.add("-40°");
        ImpactParams.add("-45°");
        ImpactParams.add("-50°");
        ImpactParams.add("-55°");
        ImpactParams.add("-60°");
      //  ImpactParams.add("Custom");





    }


public void deleteParam(TableView tbl){



      if(!tbl.getId().equals("MinTbl")&&!tbl.getId().equals("MaxTbl")&&!tbl.getId().equals("TestTbl")&&!tbl.getId().equals("ParamTbl")
      &&!tbl.getId().equals("MinToleranceTbl")&&!tbl.getId().equals("MaxToleranceTbl")&&!tbl.getId().equals("UncertaintyTbl")){

          tbl.setOnKeyReleased((
                  KeyEvent t)-> {
              KeyCode key=t.getCode();
              if (key==KeyCode.DELETE){
                  TableModel param =  (TableModel)tbl.getSelectionModel().selectedItemProperty().get();



if(Main.Hebrew==true){
    Dialog dialog1;
    dialog1 = new Dialog(
            DialogType.CONFIRMATION,
            "למחוק פרמטר?",
            "האם תרצה למחוק פרמטר זה?",
            "בחר YES כדי למחוק את הפרמטר");

    dialog1.setFontSize(22);
    dialog1.showAndWait();

    if (dialog1.getResponse() == DialogResponse.YES) {


        Main.dbDriver.recursiveDelete(param.getId());
        Main.dbDriver.deleteItself(param.getId());

     //   Main.db.

        ClearTable(GeneralTbl);
        ClearTable(SpecificTbl);
        ClearTable(ClassTbl);
        ClearTable(ShapeTbl);
        ClearTable(SizeTbl);
        ClearTable(TestTbl);


        Main.LoadedToMemory = Main.dbDriver.getAll();

        PopulateTable(StdTbl,StdClm,stdGroup,0);





    }


}else{

    Dialog dialog1;
    dialog1 = new Dialog(
            DialogType.CONFIRMATION,
            "Delete Parameter",
            "Delete this parameter",
            "Press YES to delete the parameter");

    dialog1.setFontSize(22);
    dialog1.showAndWait();

    if (dialog1.getResponse() == DialogResponse.YES) {

        Main.dbDriver.recursiveDelete(param.getId());
        Main.dbDriver.deleteItself(param.getId());


        ClearTable(GeneralTbl);
        ClearTable(SpecificTbl);
        ClearTable(ClassTbl);
        ClearTable(ShapeTbl);
        ClearTable(SizeTbl);
        ClearTable(TestTbl);

        Main.LoadedToMemory = Main.dbDriver.getAll();


        PopulateTable(StdTbl,StdClm,stdGroup,0);


    }




}





              } else {
                  // ... user chose CANCEL or closed the dialog
              }
          });



      }


    }







    @FXML
    public void RestoreDB() throws IOException {
        Dialog dialog1;

        if(Main.Hebrew==true){ dialog1 = new Dialog(
                DialogType.CONFIRMATION,
                "לשחזר מסד נתונים",
                "האם תרצה לשחזר את מסד הנתונים",
                "בחר YES כדי לחזר את מסד הנתונים");

            dialog1.setFontSize(22);
            dialog1.showAndWait();}else{ dialog1 = new Dialog(
                DialogType.CONFIRMATION,
                "Restore Database",
                "Do you want to Restore database?",
                "Press YES to restore database");

            dialog1.setFontSize(22);
            dialog1.showAndWait();}



        if (dialog1.getResponse() == DialogResponse.YES) {

            File dest = new File(Paths.get("").toAbsolutePath().toString() + "\\MapStandardDB");
            File source = new File(Paths.get("").toAbsolutePath().toString() + "\\" + "IKADOCS_BACKUP" + "\\MapStandardDB" );


            FileUtils.copyFile(source, dest);

        }





    }


    @FXML
    public void Backup() throws IOException {

        Dialog dialog1;

        if(Main.Hebrew==true){  dialog1 = new Dialog(
                DialogType.CONFIRMATION,
                "לגבות מסד נתונים",
                "לגבות את מסד הנתונים?",
                "בחר YES כדי לגבות את מסד הנתונים");

            dialog1.setFontSize(22);
            dialog1.showAndWait();}else{  dialog1 = new Dialog(
                DialogType.CONFIRMATION,
                "Backup Database",
                "Backup Database?",
                "Press YES to backup database");

            dialog1.setFontSize(22);
            dialog1.showAndWait();}



        if (dialog1.getResponse() == DialogResponse.YES) {

            Stage stage = new Stage();
            ProgressBar progressBar = new ProgressBar();

            FlowPane root = new FlowPane();

            root.setHgap(50);
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(progressBar);

            Scene scene = new Scene(root, 50, 50);

            stage.setTitle("BackUp progress");

            stage.setScene(scene);
            stage.show();




            Task task = new Task<Void>() {
                @Override public Void call() throws IOException {



                    int i = 0;

                    File source = new File(Paths.get("").toAbsolutePath().toString() + "\\MapStandardDB");
                    File dest = new File(Paths.get("").toAbsolutePath().toString() + "\\" + "IKADOCS_BACKUP" + "\\MapStandardDB");

                    FileUtils.copyFile(source, dest);


                    updateProgress(100, 100);





                    return null;
                }
            };


            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();




        }



    }

@FXML
public void ParamEdit(TableColumn.CellEditEvent<TableModel, String> event){

TableModel parameter = event.getRowValue();
String g = event.getNewValue();

    TableModel saleItem =  event.getTableView().getItems().get(event.getTablePosition().getRow());


    Main.dbDriver.EditParam(parameter.getId(),g);

    saleItem.setPropertyName(g);

}


    @FXML
    public void ParamMinMaxEdit(TableColumn.CellEditEvent<MinMaxTableObj, String> event){

        String g = event.getNewValue();

        MinMaxTableObj j = event.getRowValue();
          String columnID = event.getTableColumn().getId();

          switch (columnID){
              case("MinColumn"):{

                  Main.dbDriver.EditParam(j.getMin().getId(),g);
              }break;

              case("MaxColumn"):{
                  Main.dbDriver.EditParam(j.getMax().getId(),g);


              }break;


              case("TolarenceMaxColumn"):{

                  Main.dbDriver.EditParam(j.getToleranceMax().getId(),g);

              }break;

              case("TolarenceMinColumn"):{

                  Main.dbDriver.EditParam(j.getToleranceMin().getId(),g);

              }break;

              case("UncertainityColumn"):{

                  Main.dbDriver.EditParam(j.getUncertainty().getId(),g);

              }break;

          }

     //


    }




@FXML
public void AddGeneral(){

       if(stdGroup.getSelectedToggle()!=null)
       {



            if(!GeneralText.getText().equals("")){
                Main.dbDriver.addChildParam(rec.getStandardID(),GeneralText.getText());
                PopulateTable(GeneralTbl,GeneralClm,generalGroup,rec.getStandardID());

            }
else {
                if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "השדה ריק!",
                        "השדה ריק");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "The field is empty!",
                        "The field is empty!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}



            }



       } else{
           if(Main.Hebrew==true){Dialog dialog = new Dialog(
                   DialogType.ERROR,
                   "תקן לא נבחר",
                   "תקן לא נבחר");
               dialog.setFontSize(22);
               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

               dialog.showAndWait();}else{Dialog dialog = new Dialog(
                   DialogType.ERROR,
                   "Standard Not Selected!",
                   "Standard Not Selected!");
               dialog.setFontSize(22);
               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

               dialog.showAndWait();}




       }


}



    @FXML
    public void AddSpecific(){

        if(generalGroup.getSelectedToggle()!=null)
        {



            if(!SpecificText.getText().equals(""))
            {

                Main.dbDriver.addChildParam(rec.getGeneralID(),SpecificText.getText());

                PopulateTable(SpecificTbl,SpecificClm,SpecificGroup,rec.getGeneralID());}
            else {
                if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "השדה ריק!",
                        "השדה ריק!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "The field is empty!",
                        "The field is empty!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}





            }



        } else{
            if(Main.Hebrew==true){Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "חומר כללי לא נבחר!",
                    "חומר כללי לא נבחר!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "General Material Not Selected!",
                    "General Material Not Selected!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}





        }


    }


    @FXML
    public void AddClass(){

        if(SpecificGroup.getSelectedToggle()!=null)
        {



            if(!ClassText.getText().equals("")){

                Main.dbDriver.addChildParam(rec.getSpecificID(),ClassText.getText());

                PopulateTable(ClassTbl,ClassClm,ClassGroup,rec.getSpecificID());


            }
            else {
                if(Main.Hebrew==true){    Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "השדה ריק!",
                        "השדה ריק!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();
                }else{    Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "The field is empty!!!",
                        "The field is empty!!!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();
                }





            }



        } else{
            if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "חומר ספציפי לא נבחר!",
                    "חומר ספציפי לא נבחר!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();
            }else{ Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Specific Material Not Selected!",
                    "Specific Material Not Selected!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();
            }




        }


    }


    @FXML
    public void AddShape(){

        if(ClassGroup.getSelectedToggle()!=null)
        {



            if(!ShapeText.getText().equals("")) {

                Main.dbDriver.addChildParam(rec.getClassPropertyID(), ShapeText.getText());

                PopulateTable(ShapeTbl, ShapeClm, ShapeGroup, rec.getClassPropertyID());

            }
            else {
                if(Main.Hebrew==true){Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "השדה ריק!",
                        "השדה ריק!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}else{Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "The field is empty!!!",
                        "The field is empty!!!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}






            }



        } else{

            if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "חוזק הבורג לא נבחר!",
                    "חוזק הבורג לא נבחר!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Class Property Not Selected!",
                    "Class Property Not Selected!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}





        }


    }



    @FXML
    public void AddSize(){

        if(ShapeGroup.getSelectedToggle()!=null)
        {



            if(!SizeText.getText().equals("")){

                Main.dbDriver.addChildParam(rec.getShapeID(),SizeText.getText());

                PopulateTable(SizeTbl,SizeClm,SizeGroup,rec.getShapeID());


            }
            else {
                if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "השדה ריק!",
                        "השדה ריק!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "The field is empty!!!",
                        "The field is empty!!!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}






            }



        } else{
            if(Main.Hebrew==true){  Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "הצורה לא נבחרה!",
                    "הצורה לא נבחרה!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{  Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Shape Not Selected!",
                    "Shape Not Selected!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}





        }


    }






    @FXML
    public void insertStandardData(){


        FilterSupport.getItems(StdTbl).clear();
        String name = StandardText.getText();


        if(!StandardText.getText().equals("")) {

            Main.dbDriver.addChildParam(0, name);

        } else {

            if(Main.Hebrew==true){  Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "השדה ריק!",
                    "השדה ריק!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{  Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "The field is empty!!!",
                    "The field is empty!!!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}



        }

        PopulateTable(StdTbl,StdClm,stdGroup,0);



        //ToDo Add the DB addition code here






    }




    public void PopulateTable(TableView table,TableColumn clm,ToggleGroup g,int NeededParetID) {


        FilterSupport.getItems(table).clear();

        ObservableList<TableModel> m = StandardToList(MemoryHandler.getNeededList(NeededParetID));

           for (TableModel sd : m) {
          addRadioButtonToPrepTable(g,clm);


               FilterSupport.getItems(table).add(sd);


    }

        }



    public void PopulateParameterTable(TableView table,TableColumn ParamClm,
                                       TableColumn MinClm,TableColumn ManClm,TableColumn MinTolClm,
                                       TableColumn MaxTolClm,TableColumn UnClm,int NeededParetID) {


        FilterSupport.getItems(table).clear();

        ObservableList<MinMaxTableObj> T = FXCollections.observableArrayList();

        List<MinMaxCellObject> cells =  MemoryHandler.getNeededParamList(NeededParetID);


      for(int i=0;i<cells.size();i++){

          if(!MemoryHandler.haveChildren(cells.get(i).getId())){
              int Min =  Main.dbDriver.addChildParam(cells.get(i).getId(),"-");
              int  Max =  Main.dbDriver.addChildParam(Min,"-");
              int  TolaranceMin =  Main.dbDriver.addChildParam(Max,"-");
              int  TolaranceMax =  Main.dbDriver.addChildParam(TolaranceMin,"-");
              int  Uncertainity =  Main.dbDriver.addChildParam(TolaranceMax,"-");
          }

      }

       ArrayList<MinMaxTableObj> tableData = new ArrayList<>();

        for(MinMaxCellObject c:cells){


          MinMaxCellObject j1 =   MemoryHandler.findMinMax(c.getId());
          if(j1==null){
              Main.dbDriver.addChildParam(c.getId(),"-");
               j1 =   MemoryHandler.findMinMax(c.getId());
          }
            MinMaxCellObject j2 =   MemoryHandler.findMinMax(j1.getId());

            if(j2==null){
                Main.dbDriver.addChildParam(c.getId(),"-");
                j2 =   MemoryHandler.findMinMax(c.getId());
            }
            MinMaxCellObject j3 =   MemoryHandler.findMinMax(j2.getId());

            if(j3==null){
                Main.dbDriver.addChildParam(c.getId(),"-");
                j3 =   MemoryHandler.findMinMax(c.getId());
            }
            MinMaxCellObject j4 =   MemoryHandler.findMinMax(j3.getId());

            if(j4==null){
                Main.dbDriver.addChildParam(c.getId(),"-");
                j4 =   MemoryHandler.findMinMax(c.getId());
            }
            MinMaxCellObject j5 =   MemoryHandler.findMinMax(j4.getId());

            if(j5==null){
                Main.dbDriver.addChildParam(c.getId(),"-");
                j5 =   MemoryHandler.findMinMax(c.getId());
            }

              MinMaxTableObj T1 = new MinMaxTableObj(c,j1,j2,j3,j4,j5);
              tableData.add(T1);




        }



        for (MinMaxTableObj sd : tableData) {
            FilterSupport.getItems(table).add(sd);
            T.add(sd);
        }


        MinMaxTable.setItems(T);



    }



    public void AddParamsToTests(int id, ArrayList<String> TestType) {

        ArrayList<Integer> g = new ArrayList<>();

        for (String d : TestType) {

            g.add(Main.dbDriver.addChildParam(id, d));

        }

    }



public void CreateNeededTestParams(int parentID){

Tests.clear();

    Tests.add("Tensile");
    Tests.add("Bend");
    Tests.add("Hardness");
    Tests.add("Micro-Hardness");
    Tests.add("Impact");
    Tests.add("Chemical");

        for(String s: Tests)
        {
            Main.dbDriver.addChildParam(parentID, s);
        }






}

    private void addRadioButtonToPrepTable(ToggleGroup Group,TableColumn clm) {

        Callback<TableColumn<TableModel, String>, TableCell<TableModel, String>> cellFactory = new Callback<TableColumn<TableModel, String>, TableCell<TableModel, String>>() {

            public TableCell<TableModel, String> call(final TableColumn<TableModel, String> param) {
                final TableCell<TableModel, String> cell = new TableCell<TableModel, String>() {

                    private  RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(Group);

                        btn.setOnAction((ActionEvent event) -> {

                            String text = clm.getText();

                            TableModel data = getTableView().getItems().get(getIndex());

                            Dialog dialog1;
                            Dialog dialog;
                            Dialog dialog2;

                            switch(text){

                                case("Standard"):{


                                     rec.setStandardID(data.getId());
                                    PopulateTable(GeneralTbl,GeneralClm,generalGroup,rec.getStandardID());

                                    ClearTable(SpecificTbl);
                                    ClearTable(ClassTbl);
                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);
                                    ClearTable(TestTbl);
                                    ClearTable(MinMaxTable);




                                }break;
                                case("General Material"):{

                                    rec.setGeneralID(data.getId());

                                    PopulateTable(SpecificTbl,SpecificClm,SpecificGroup,rec.getGeneralID());

                                    ClearTable(ClassTbl);
                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);
                                    ClearTable(TestTbl);
                                    ClearTable(MinMaxTable);

                                } break;
                                case("Specific Material"):{

                                    rec.setSpecificID(data.getId());
                                    PopulateTable(ClassTbl,ClassClm,ClassGroup,rec.getSpecificID());

                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);
                                    ClearTable(TestTbl);
                                    ClearTable(MinMaxTable);


                                }break;
                                case("Class Property\\General Info"):{
                                    rec.setClassPropertyID(data.getId());
                                    PopulateTable(ShapeTbl,ShapeClm,ShapeGroup,rec.getClassPropertyID());
                                    ClearTable(SizeTbl);
                                    ClearTable(TestTbl);
                                    ClearTable(MinMaxTable);

                                }break;
                                case("Shape"):{
                                    rec.setShapeID(data.getId());

                                    PopulateTable(SizeTbl,SizeClm,SizeGroup,rec.getShapeID());
                                    ClearTable(TestTbl);
                                    ClearTable(MinMaxTable);

                                }break;
                                case("Size"):{
                                    rec.setSizeID(data.getId());

                                    if(!MemoryHandler.haveChildren(data.getId()))


                                    CreateNeededTestParams(rec.getSizeID());

                                    PopulateTable(TestTbl,TestTypeClm,TestTypeGroup,rec.getSizeID());

                                    ClearTable(MinMaxTable);


                                    //When i chose size, i need to create all the tests
                                    //related to this size and add to each test the fitting params
                                    //and to each param Min and Max

                                }break;
                                case("Test Type"):{


                                    //ToDo here i need to change to the table format i want, easier
                                    //Todo handling of the information insertion

                                    rec.setTestTypeID(data.getId());

                                    switch(data.getPropertyName()){

                                        case("Tensile"):{
                                            if(!MemoryHandler.haveChildren(data.getId())) {

                                                if(Main.Hebrew==true){ dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "להוסיף למסד הנתונים את ערכים לבדיקת המתיחה",
                                                        "האם אתה בטוח כי ברצונך להוסיף את הפרמטרים של בדיקת המתיחה?",
                                                        "הוסף פרמטרים רק במקרה הצורך כדי לשמור על יעילות מסד הנתונים");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}else{ dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "Opening Tensile parameters options",
                                                        "Are you sure you want to open Tensile parameters options?",
                                                        "Open test options only if needed to save database space");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}



                                                if (dialog1.getResponse() == DialogResponse.YES) {

                                                    if(Main.Hebrew==true){   dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "ההעתקת הפרמטרים של בדיקת המתיחה",
                                                            "האם להעתיק את ערכי הפרמטרים של בדיקת המתיחה מהערך הקודם?",
                                                            "בחר YES כדי להעתיק");

                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();}else{   dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "Copy Tensile standard parameters",
                                                            "Copy Results from previous entry?",
                                                            "Press YES to copy results from previous entry");

                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();}



                                                    if (dialog2.getResponse() == DialogResponse.YES) {
                                                        CopyParamList(data.getId(),"Tensile",TensileParams);
                                                    }else{
                                                        AddParamsToTests(data.getId(), TensileParams);
                                                    }


                                                }


                                            }


                                        }break;
                                        case("Bend"):{


                                            if(!MemoryHandler.haveChildren(data.getId())) {

                                                if(Main.Hebrew==true){    dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "הוספצ הפרמטרים של בדיקת הכפיפה למסד הנתונים",
                                                        "האם להוסיף למסד הנתונים ערכים לבדיקת הכפיפה?",
                                                        "הוסף ערכים למסד הנתונים רק אם הדבר דרוש כדי לשמור על יעילות מסד הנתונים");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}else{    dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "Opening Bend parameters options",
                                                        "Are you sure you want to open  Bend parameters options?",
                                                        "Open test options only if needed to save database space");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}



                                                if (dialog1.getResponse() == DialogResponse.YES) {

                                                    if(Main.Hebrew==true){ dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "ההעתקת הפרמטרים של בדיקת הכפיפה",
                                                            "האם להעתיק פרמטרים של בדיקת הכפיפה מהערך הקודם",
                                                            "בחר YES כדי להעתיק");

                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();}else{ dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "Copy Bend test results",
                                                            "Copy Results from previous entry?",
                                                            "Press YES to copy results from previous entry");

                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();}



                                                    if (dialog2.getResponse() == DialogResponse.YES) {


                                                        CopyParamList(data.getId(),"Bend",BendParams);




                                                    }else{

                                                        AddParamsToTests(data.getId(),BendParams);
                                                    }


                                                }

                                            }



                                        }break;
                                        case("Chemical"):{


                                            if(!MemoryHandler.haveChildren(data.getId())) {

                                                if(Main.Hebrew==true){ dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "הוספת הפרמטרים של הבדיקה למסד הנתונים",
                                                        "האם להוסיף את הפרמטרים של הבדיקה הכימית למסד הנתונים?",
                                                        "הוסף פרמטרים רק אם הדבר דרוש כדי לשמור על יעילות מסד הנתונים");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}else{ dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "Opening Chemical parameters options",
                                                        "Are you sure you want to open Chemical parameters options?",
                                                        "Open test options only if needed to save database space");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}



                                                if (dialog1.getResponse() == DialogResponse.YES) {

                                                    if(Main.Hebrew==true){   dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "ההעתקת הפרמטרים של הבדיקה הכימית?",
                                                            "להעתיק את הפרמטרים מהערך הקודם?",
                                                            "בחר YES כדי להעתיק את הפרמטרים מהערך הקודם");

                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();}else{   dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "Copy Chemical parameters",
                                                            "Copy Chemicals from previous entry?",
                                                            "Press YES to copy parameters from previous entry");

                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();}



                                                    if (dialog2.getResponse() == DialogResponse.YES) {


                                                        CopyParamList(data.getId(),"Chemical",ChemicalParams);




                                                    }else{

                                                        AddParamsToTests(data.getId(),ChemicalParams);
                                                    }


                                                }


                                            }



                                        }break;
                                        case("Micro-Hardness"):{


                                            if(!MemoryHandler.haveChildren(data.getId())) {

                                                if(Main.Hebrew==true){      dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "הוספת הפרמטרים של בדיקת המיקרו קושיות למסד הנתונים",
                                                        "האם להוסיף את הפרמטרים של בדיקת המיקרו קושיות למסד הנתונים?",
                                                        "הוסף פרמטרים רק כאשר דרוש בכדי לשמור על יעילות מסד הנתונים");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}else{      dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "Opening Micro-Hardness parameters options",
                                                        "Are you sure you want to open Micro-Hardness parameters options?",
                                                        "Open test options only if needed to save database space");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}



                                                if (dialog1.getResponse() == DialogResponse.YES) {

                                                    if(Main.Hebrew==true){   dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "ההעתקת הפרמטרים של בדיקת המיקרו קושיות",
                                                            "האם להעתיק את הפרמטרים של המיקרו קושיות מהערך הקודם?",
                                                            "בחר YES כדי להעתיק את הפרמטרים של בדיקת המיקרו קושיות");

                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();}else{   dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "Copy Micro-Hardness parameters",
                                                            "Copy Micro-Hardness from previous entry?",
                                                            "Press YES to copy parameters from previous entry");

                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();}



                                                    if (dialog2.getResponse() == DialogResponse.YES) {


                                                        CopyParamList(data.getId(),"Micro-Hardness",MicroHardnessParams);


                                                    }else{

                                                        AddParamsToTests(data.getId(),MicroHardnessParams);
                                                    }


                                                }


                                            }


                                        }break;
                                        case("Hardness"):{


                                            if(!MemoryHandler.haveChildren(data.getId())) {

                                                if(Main.Hebrew==true){dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "הוספת הפרמטרים של בדיקת הקושיות למסד הנתונים",
                                                        "האם להוסיף את הפרמטרים של בדיקת הקושיות למסד הנתונים?",
                                                        "הוסף פרמטרים רק כאשר דרוש כדי לשמור על יעילות מסד הנתונים");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}else{dialog1 = new Dialog(
                                                        DialogType.CONFIRMATION,
                                                        "Opening Hardness parameters options",
                                                        "Are you sure you want to open Hardness parameters options?",
                                                        "Open test options only if needed to save database space");

                                                    dialog1.setFontSize(20);
                                                    dialog1.showAndWait();}



                                                if (dialog1.getResponse() == DialogResponse.YES) {

                                                    if(Main.Hebrew==true){ dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "ההעתקת הפרמטרים של בדיקת הקושיות",
                                                            "האם להעתיק את הפרמטרים של בדיקת הקושיות מהערך הקודם?",
                                                            "בחר YES כדי להעתיק את הפרמטרים מהערך הקודם");

                                                    }else{ dialog2 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "Copy Hardness parameters",
                                                            "Copy Hardness from previous entry?",
                                                            "Press YES to copy parameters from previous entry");

                                                    }
                                                    dialog2.setFontSize(20);
                                                    dialog2.showAndWait();


                                                    if (dialog2.getResponse() == DialogResponse.YES) {


                                                        CopyParamList(data.getId(),"Hardness",HardnessParams);




                                                    }else{
                                                        AddParamsToTests(data.getId(),HardnessParams);
                                                    }


                                                }
                                            }


                                        }break;
                                        case("Impact"):{

                                                if(!MemoryHandler.haveChildren(data.getId())) {

                                                    if(Main.Hebrew==true){    dialog1 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "הוספת הפרמטרים של בדיקת הנגיפה למסד הנתונים",
                                                            "האם להוסיף את הפרמטרים של בדיקת הנגיפה למסד הנתונים?",
                                                            "הוסף פרמטרים רק כאשר דרוש לצורך שמירה על יעילות מס הנתונים");

                                                        dialog1.setFontSize(22);
                                                    }else{    dialog1 = new Dialog(
                                                            DialogType.CONFIRMATION,
                                                            "Opening Impact parameters options",
                                                            "Are you sure you want to open Impact parameters options?",
                                                            "Open test options only if needed to save database space");

                                                        dialog1.setFontSize(20);
                                                    }
                                                    dialog1.showAndWait();


                                                    if (dialog1.getResponse() == DialogResponse.YES) {

                                                        if(Main.Hebrew==true){  dialog2 = new Dialog(
                                                                DialogType.CONFIRMATION,
                                                                "ההעתקה של הפרמטרים לבדיקת הנגיפה",
                                                                "האם להעתיק את הפרמטרים של בדיקת הנגיפה מהערך הקודם?",
                                                                "בחר YES כדי להעתיק את הפרמטרים מהערך הקודם");

                                                        }else{  dialog2 = new Dialog(
                                                                DialogType.CONFIRMATION,
                                                                "Copy Impact parameters",
                                                                "Copy Impact from previous entry?",
                                                                "Press YES to copy parameters from previous entry");

                                                        }
                                                        dialog2.setFontSize(20);
                                                        dialog2.showAndWait();


                                                        if (dialog2.getResponse() == DialogResponse.YES) {


                                                            CopyParamList(data.getId(),"Impact",ImpactParams);




                                                        }else{

                                                            AddParamsToTests(data.getId(),ImpactParams);
                                                        }


                                                    }


                                        }



                                        }break;





                                    }


                                    PopulateParameterTable(MinMaxTable,ParamNameColumn,MinColumn,MaxColumn,TolarenceMinColumn,TolarenceMaxColumn,UncertainityColumn,rec.getTestTypeID());



                                }break;

                            }



                           // Main.selection.setStandardName(data.getPropertyName());


                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                             btn.setSelected(false);
                        }
                    }
                };
                return cell;
            }
        };





        switch(clm.getText()) {

            case ("Standard"):


            case ("תקן"): {

                StandardSelClm.setCellFactory(cellFactory);


            }
            break;
            case ("General Material"):
            case ("חומר כללי"): {

                GeneralSelClm.setCellFactory(cellFactory);


            }
            break;

            case("Specific Material"):

            case("חומר ספציפי"): {

                SpecificSelClm.setCellFactory(cellFactory);


            }break;
            case("Size"):
            case("מידות"): {

                SizeSelClm.setCellFactory(cellFactory);

            }break;
            case("Class Property\\General Info"):
            case("חוזק בורג"): {

                ClassSelClm.setCellFactory(cellFactory);

            }break;
            case("Shape"):
            case("צורה"): {
                ShapeSelClm.setCellFactory(cellFactory);


            }break;
            case("Test Type"):{

                TypeSelClm.setCellFactory(cellFactory);

            }break;



        }



    }



    public void ClearTable(TableView table){

        FilterSupport.getItems(table).clear();

    }




    public static void alwaysShowVerticalScroll(final TableView view) {
        new Thread(() -> {
            while (true) {
                Set<Node> nodes = view.lookupAll(".scroll-bar");
                if (nodes.isEmpty()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) {
                    }
                    continue;
                }
                Node node = view.lookup(".virtual-flow");
                if (node == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) {
                    }
                    continue;
                }
                final VirtualFlow flow = (VirtualFlow) node;
                for (Node n : nodes) {
                    if (n instanceof ScrollBar) {
                        final ScrollBar bar = (ScrollBar) n;
                        if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                            bar.visibleProperty().addListener(l -> {
                                if (bar.isVisible()) {
                                    return;
                                }
                                Field f = getVirtualFlowField("needLengthBar");
                                Method m = getVirtualFlowMethod("updateViewportDimensions");
                                try {
                                    f.setBoolean(flow, true);
                                    m.invoke(flow);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                bar.setVisible(true);
                            });
                            Platform.runLater(() -> {
                                bar.setVisible(true);
                            });
                            break;
                        }
                    }
                }
                break;
            }
        }).start();
    }

    private static Field getVirtualFlowField(String name) {
        Field field = null;
        try {
            field = VirtualFlow.class.getDeclaredField(name);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    private static Method getVirtualFlowMethod(String name) {
        Method m = null;
        try {
            m = VirtualFlow.class.getDeclaredMethod(name);
            m.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

public void setTableUp(TableView table){

        table.scrollToColumnIndex(0);

    ObservableList<TableModel> items = FXCollections.observableArrayList();

    alwaysShowVerticalScroll(table);




    table.setOnKeyReleased((KeyEvent t) -> {
        TablePosition tp;
        switch (t.getCode()) {
            //other code cut out here
            case Z:
                if (t.isControlDown()) {
                    if (!deletedLines.isEmpty()) {
                        items.add(deletedLines.pop());
                    }
                    break; //don't break for regular Z
                }
            default:
                if (t.getCode().isLetterKey() || t.getCode().isDigitKey()) {
                    lastKey = t.getText();
                    tp = table.getFocusModel().getFocusedCell();
                    table.edit(tp.getRow(), tp.getTableColumn());
                    lastKey = null;
                }
        }
    });


    table.setEditable(true);
    table.getSelectionModel().setCellSelectionEnabled(true);
    deleteParam(table);
}

public void setClmUp(TableColumn clm){

    clm.impl_setReorderable(false);


    clm.setCellValueFactory(
            new PropertyValueFactory<TableModel,String>("PropertyName")
    );
    clm.setCellFactory(TextFieldTableCell.forTableColumn());



/*
    clm.setCellFactory(tc-> {
        TableCell<TableModel, String> cell = new TableCell<>();
        Text text = new Text();
        cell.setGraphic(text);
        cell.setEditable(true);
        text.setStyle("-fx-fill: -fx-text-background-color;");
        text.setFontSmoothingType(FontSmoothingType.LCD);
        text.wrappingWidthProperty().bind(clm.widthProperty().subtract(5));
        text.textProperty().bind(Bindings.createStringBinding(() -> {
            if (cell.isEmpty()) {
                return null ;
            } else {
                return cell.getItem().toString();
            }
        }, cell.emptyProperty(), cell.itemProperty()));
        return cell;
    });
*/




    clm.setCellFactory(new Callback<TableColumn<TableModel, String>, TableCell<TableModel, String>>() {
        @Override
        public TableCell<TableModel, String> call(TableColumn<TableModel, String> arg0) {
            return new TableCell<TableModel, String>() {

                private TextField textField;

                @Override
                public void startEdit() {
                    if (!isEmpty()) {
                        super.startEdit();
                        createTextField();
                        setText(null);
                        setGraphic(textField);
                        Platform.runLater(() -> {//without this space erases text, f2 doesn't
                            textField.requestFocus();//also selects
                        });
                        if (lastKey != null) {
                            textField.setText(lastKey);
                            Platform.runLater(() -> {
                                textField.deselect();
                                textField.end();
                                textField.positionCaret(textField.getLength()+2);//works sometimes
                            });
                        }
                    }
                }


                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }

                private void createTextField() {
                    textField = new TextField(getString());

                    //doesn't work if clicking a different cell, only focusing out of table
                    textField.focusedProperty().addListener(
                            (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
                                if (!arg2) commitEdit(textField.getText());
                            });

                    textField.setOnKeyReleased((KeyEvent t) -> {
                        if (t.getCode() == KeyCode.ENTER) {
                            commitEdit(textField.getText());

                            this.getTableView().getSelectionModel().selectBelowCell();

                        }
                        if (t.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    });

                    textField.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent t) -> {
                        if (t.getCode() == KeyCode.DELETE) {
                            t.consume();//stop from deleting line in table keyevent
                        }
                    });
                }

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null) {
                        setText("");
                    } else {

                        Text text = new Text();
                        setGraphic(text);
                        setEditable(true);
                        if(Main.Hebrew==true)
                        text.setTextAlignment(TextAlignment.RIGHT);
                        text.setStyle("-fx-fill: -fx-text-background-color;");
                        text.setFontSmoothingType(FontSmoothingType.LCD);
                        text.wrappingWidthProperty().bind(clm.widthProperty().subtract(5));
                        text.textProperty().bind(Bindings.createStringBinding(() -> {
                            if (isEmpty()) {
                                return null ;
                            } else {
                              if(getItem()!=null)
                                return getItem().toString();
                              else return null;
                            }
                        }, emptyProperty(), itemProperty()));
                    }
                }

            };
        }
    });



    if(!clm.getId().equals("MinClm")&&!clm.getId().equals("MaxClm")&&!clm.getId().equals("MinToleranceClm")&&!clm.getId().equals("MaxToleranceClm")
            &&!clm.getId().equals("UncertainityClm"))
        FilterSupport.addFilter(clm);






}

public void SetParamColumnsUp(TableColumn clm){

    clm.impl_setReorderable(false);




    clm.setCellFactory(new Callback<TableColumn<MinMaxCellObject, String>, TableCell<MinMaxCellObject, String>>() {
        @Override
        public TableCell<MinMaxCellObject, String> call(TableColumn<MinMaxCellObject, String> arg0) {


            return new TableCell<MinMaxCellObject, String>() {

                TextField textField = new TextField();
                @Override
                public void startEdit() {
                    if (!isEmpty()) {
                        super.startEdit();
                        createTextField();
                        setText(null);
                        setGraphic(textField);

                        Platform.runLater(() -> {//without this space erases text, f2 doesn't
                            textField.requestFocus();//also selects
                        });
                        if (lastKey != null) {
                            textField.setText(lastKey);
                            Platform.runLater(() -> {
                                textField.deselect();
                                textField.end();
                                textField.positionCaret(textField.getLength()+2);//works sometimes
                            });
                        }
                    }
                }



                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }

                private void createTextField() {
                    textField = new TextField(getString());

                    //doesn't work if clicking a different cell, only focusing out of table
                    textField.focusedProperty().addListener(
                            (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
                                if (!arg2) commitEdit(textField.getText());
                            });

                    textField.setOnKeyReleased((KeyEvent t) -> {
                        if (t.getCode() == KeyCode.ENTER) {
                            commitEdit(textField.getText());

                            this.getTableView().getSelectionModel().selectBelowCell();

                        }
                        if (t.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    });

                    textField.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent t) -> {
                        if (t.getCode() == KeyCode.DELETE) {
                            t.consume();//stop from deleting line in table keyevent
                        }
                    });
                }

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null) {
                        setText("");
                    } else {
                        Text text = new Text(item);
                        setGraphic(text);
                        setEditable(true);
                        text.setTextAlignment(TextAlignment.CENTER);
                        text.setStyle("-fx-fill: -fx-text-background-color;");
                        text.setFontSmoothingType(FontSmoothingType.LCD);
                        text.wrappingWidthProperty().bind(clm.widthProperty().subtract(5));
                        text.textProperty().bind(Bindings.createStringBinding(() -> {
                            if (isEmpty()) {
                                return null ;
                            } else {
                                if(getItem()!=null)
                                    return getItem().toString();
                                else return null;
                            }
                        }, emptyProperty(), itemProperty()));
                    }
                }

            };
        }
    });




}


    @FXML
    void edit_colnom(TableColumn.CellEditEvent<MinMaxTableObj, String> event)  {

        MinMaxTableObj u= event.getRowValue();

        MinMaxTableObj saleItem =  event.getTableView().getItems().get(event.getTablePosition().getRow());

        String n = event.getNewValue();

        TableColumn t = event.getTableColumn();
        String clmName = t.getId();

        if(clmName.equals("MinColumn")){
            Main.dbDriver.EditParam(u.getMin().getId(),n);
            saleItem.setMin(new MinMaxCellObject(new SimpleStringProperty(n),u.getMin().getParentId(),u.getMin().getId()));

        } else if(clmName.equals("MaxColumn")){
            Main.dbDriver.EditParam(u.getMax().getId(),n);
            saleItem.setMax(new MinMaxCellObject(new SimpleStringProperty(n),u.getMax().getParentId(),u.getMax().getId()));


        }else if(clmName.equals("TolarenceMaxColumn")){
            Main.dbDriver.EditParam(u.getToleranceMax().getId(),n);
            saleItem.setToleranceMax(new MinMaxCellObject(new SimpleStringProperty(n),u.getToleranceMax().getParentId(),u.getToleranceMax().getId()));

        }else if(clmName.equals("TolarenceMinColumn")){
            Main.dbDriver.EditParam(u.getToleranceMin().getId(),n);
            saleItem.setToleranceMin(new MinMaxCellObject(new SimpleStringProperty(n),u.getToleranceMin().getParentId(),u.getToleranceMin().getId()));


        }else if(clmName.equals("UncertainityColumn")){
            Main.dbDriver.EditParam(u.getUncertainty().getId(),n);
            saleItem.setUncertainty(new MinMaxCellObject(new SimpleStringProperty(n),u.getUncertainty().getParentId(),u.getUncertainty().getId()));


        }

        event.getTableView().refresh();



    }

public ObservableList<TableModel> StandardToList(List<TableModel> WantToShowList){

    ObservableList<TableModel> list = FXCollections.observableArrayList();

    //ToDo
      for(TableModel nd : new ArrayList<>(WantToShowList))
          list.add(nd);

    return list;
}


public void CopyParamList(int ID,String testType,ArrayList<String> TestParams){


        //Get the highest id of the chemical params
        ArrayList<NitStandardParam> CopyParams = new ArrayList<>();

        int maxID = -1;

        int parentID = MemoryHandler.FindParentId(ID);

        int GrandParentID = MemoryHandler.FindParentId(parentID);


ArrayList<Integer> n = MemoryHandler.findChildrenID(GrandParentID);

    n.remove(Collections.max(n));

    if(!n.isEmpty()){
         maxID = Collections.max(n);
    } else{

        switch(testType){

            case("Chemical"):{

                AddParamsToTests(ID,ChemicalParams);
                 return;
            }
            case("Tensile"):{
                AddParamsToTests(ID,TensileParams);
                return;

            }
            case("Impact"):{
                AddParamsToTests(ID,ImpactParams);
                return;

            }
            case("Hardness"):{
                AddParamsToTests(ID,HardnessParams);

                return;

            }
            case("Micro-Hardness"):{
                AddParamsToTests(ID,MicroHardnessParams);

                return;

            }
            case("Bend"):{
                AddParamsToTests(ID,BendParams);

                return;

            }



        }

    }


     ArrayList<NitStandardParam> ListOfTest = MemoryHandler.GetListByID(maxID);


     switch(testType){

         case("Chemical"):{

             ArrayList<Integer> g = new ArrayList<>();
            //adding the testParams to the list

             for (String d : TestParams) {
                 g.add(Main.dbDriver.addChildParam(ID, d));
             }

             //I need the ID of the test in the new list

             ArrayList<NitStandardParam> NewTestList = MemoryHandler.GetListByID(ID);



             //Get the list of parameters for the list
             for(NitStandardParam p:ListOfTest){

                 if(p.getName().equals("Chemical")){

                     CopyParams = MemoryHandler.GetListByID(p.getId());

                 }

             }

        //Here the copying really happens
             if(!CopyParams.isEmpty()){

                 for(NitStandardParam p:CopyParams){

                     if(MemoryHandler.haveChildren(p.getId())){



                         for(NitStandardParam m:NewTestList){

                             if(p.getName().equals(m.getName())){


                                 //This one is right
                                 ArrayList<NitStandardParam> p1 = MemoryHandler.GetListByID(p.getId());

                                 ArrayList<NitStandardParam> p3 =  MemoryHandler.GetListByID(p1.get(0).getId());

                                 ArrayList<NitStandardParam> p5 =  MemoryHandler.GetListByID(p3.get(0).getId());

                                 ArrayList<NitStandardParam> p7 =  MemoryHandler.GetListByID(p5.get(0).getId());

                                 ArrayList<NitStandardParam> p9 =  MemoryHandler.GetListByID(p7.get(0).getId());

                                 //adding the minimum

                                 int Min =  Main.dbDriver.addChildParam(m.getId(),p1.get(0).getName());
                                 //adding the maximum
                               int Max =  Main.dbDriver.addChildParam(Min,p3.get(0).getName());

                               int MinTolerance = Main.dbDriver.addChildParam(Max,p5.get(0).getName());

                                 int MaxTolerance = Main.dbDriver.addChildParam(MinTolerance,p7.get(0).getName());

                                 Main.dbDriver.addChildParam(MaxTolerance,p9.get(0).getName());

                             }




                         }







                     }


                 }


             } else {

                 AddParamsToTests(ID,ChemicalParams);

             }




         }break;

         case("Bend"):{
             ArrayList<Integer> g = new ArrayList<>();
             //adding the testParams to the list

             for (String d : TestParams) {

                 g.add(Main.dbDriver.addChildParam(ID, d));

             }

             //I need the ID of the test in the new list

             ArrayList<NitStandardParam> NewTestList = MemoryHandler.GetListByID(ID);



             //Get the list of parameters for the list
             for(NitStandardParam p:ListOfTest){

                 if(p.getName().equals("Bend")){

                     CopyParams = MemoryHandler.GetListByID(p.getId());

                 }

             }

             //Here the copying really happens
             if(!CopyParams.isEmpty()){

                 for(NitStandardParam p:CopyParams){

                     if(MemoryHandler.haveChildren(p.getId())){



                         for(NitStandardParam m:NewTestList){

                             if(p.getName().equals(m.getName())){


                                 //This one is right
                                 ArrayList<NitStandardParam> p1 = MemoryHandler.GetListByID(p.getId());

                                 ArrayList<NitStandardParam> p3 =  MemoryHandler.GetListByID(p1.get(0).getId());

                                 ArrayList<NitStandardParam> p5 =  MemoryHandler.GetListByID(p3.get(0).getId());

                                 ArrayList<NitStandardParam> p7 =  MemoryHandler.GetListByID(p5.get(0).getId());

                                 ArrayList<NitStandardParam> p9 =  MemoryHandler.GetListByID(p7.get(0).getId());

                                 //adding the minimum

                                 int Min =  Main.dbDriver.addChildParam(m.getId(),p1.get(0).getName());
                                 //adding the maximum
                                 int Max =  Main.dbDriver.addChildParam(Min,p3.get(0).getName());

                                 int MinTolerance = Main.dbDriver.addChildParam(Max,p5.get(0).getName());

                                 int MaxTolerance = Main.dbDriver.addChildParam(MinTolerance,p7.get(0).getName());

                                 Main.dbDriver.addChildParam(MaxTolerance,p9.get(0).getName());

                             }




                         }







                     }


                 }


             } else {

                 AddParamsToTests(ID,BendParams);

             }

         }break;

         case("Tensile"):{
             ArrayList<Integer> g = new ArrayList<>();
             //adding the testParams to the list

             for (String d : TestParams) {

                 g.add(Main.dbDriver.addChildParam(ID, d));

             }


             //I need the ID of the test in the new list

             ArrayList<NitStandardParam> NewTestList = MemoryHandler.GetListByID(ID);



             //Get the list of parameters for the list
             for(NitStandardParam p:ListOfTest){

                 if(p.getName().equals("Tensile")){

                     CopyParams = MemoryHandler.GetListByID(p.getId());

                 }

             }

             //Here the copying really happens
             if(!CopyParams.isEmpty()){

                 for(NitStandardParam p:CopyParams){

                     if(MemoryHandler.haveChildren(p.getId())){



                         for(NitStandardParam m:NewTestList){

                             if(p.getName().equals(m.getName())){


                                 //This one is right
                                 ArrayList<NitStandardParam> p1 = MemoryHandler.GetListByID(p.getId());

                                 ArrayList<NitStandardParam> p3 =  MemoryHandler.GetListByID(p1.get(0).getId());

                                 ArrayList<NitStandardParam> p5 =  MemoryHandler.GetListByID(p3.get(0).getId());

                                 ArrayList<NitStandardParam> p7 =  MemoryHandler.GetListByID(p5.get(0).getId());

                                 ArrayList<NitStandardParam> p9 =  MemoryHandler.GetListByID(p7.get(0).getId());

                                 //adding the minimum

                                 int Min =  Main.dbDriver.addChildParam(m.getId(),p1.get(0).getName());
                                 //adding the maximum
                                 int Max =  Main.dbDriver.addChildParam(Min,p3.get(0).getName());

                                 int MinTolerance = Main.dbDriver.addChildParam(Max,p5.get(0).getName());

                                 int MaxTolerance = Main.dbDriver.addChildParam(MinTolerance,p7.get(0).getName());

                                 Main.dbDriver.addChildParam(MaxTolerance,p9.get(0).getName());

                             }




                         }







                     }


                 }


             } else {

                 AddParamsToTests(ID,TensileParams);

             }

         }break;

         case("Hardness"):{
             ArrayList<Integer> g = new ArrayList<>();
             //adding the testParams to the list

             for (String d : TestParams) {
                 g.add(Main.dbDriver.addChildParam(ID, d));
             }


             //I need the ID of the test in the new list

             ArrayList<NitStandardParam> NewTestList = MemoryHandler.GetListByID(ID);



             //Get the list of parameters for the list
             for(NitStandardParam p:ListOfTest){

                 if(p.getName().equals("Hardness")){

                     CopyParams = MemoryHandler.GetListByID(p.getId());

                 }

             }

             //Here the copying really happens
             if(!CopyParams.isEmpty()){

                 for(NitStandardParam p:CopyParams){

                     if(MemoryHandler.haveChildren(p.getId())){



                         for(NitStandardParam m:NewTestList){

                             if(p.getName().equals(m.getName())){


                                 //This one is right
                                 ArrayList<NitStandardParam> p1 = MemoryHandler.GetListByID(p.getId());

                                 ArrayList<NitStandardParam> p3 =  MemoryHandler.GetListByID(p1.get(0).getId());

                                 ArrayList<NitStandardParam> p5 =  MemoryHandler.GetListByID(p3.get(0).getId());

                                 ArrayList<NitStandardParam> p7 = MemoryHandler.GetListByID(p5.get(0).getId());

                                 ArrayList<NitStandardParam> p9 =  MemoryHandler.GetListByID(p7.get(0).getId());

                                 //adding the minimum

                                 int Min =  Main.dbDriver.addChildParam(m.getId(),p1.get(0).getName());
                                 //adding the maximum
                                 int Max =  Main.dbDriver.addChildParam(Min,p3.get(0).getName());

                                 int MinTolerance = Main.dbDriver.addChildParam(Max,p5.get(0).getName());

                                 int MaxTolerance = Main.dbDriver.addChildParam(MinTolerance,p7.get(0).getName());

                                 Main.dbDriver.addChildParam(MaxTolerance,p9.get(0).getName());

                             }




                         }







                     }


                 }


             } else {

                 AddParamsToTests(ID,HardnessParams);

             }

         }break;

         case("Impact"):{
             ArrayList<Integer> g = new ArrayList<>();
             //adding the testParams to the list

             for (String d : TestParams) {

                 g.add(Main.dbDriver.addChildParam(ID, d));

             }


             //I need the ID of the test in the new list

             ArrayList<NitStandardParam> NewTestList = MemoryHandler.GetListByID(ID);



             //Get the list of parameters for the list
             for(NitStandardParam p:ListOfTest){

                 if(p.getName().equals("Impact")){

                     CopyParams = MemoryHandler.GetListByID(p.getId());

                 }

             }

             //Here the copying really happens
             if(!CopyParams.isEmpty()){

                 for(NitStandardParam p:CopyParams){

                     if(MemoryHandler.haveChildren(p.getId())){



                         for(NitStandardParam m:NewTestList){

                             if(p.getName().equals(m.getName())){


                                 //This one is right
                                 ArrayList<NitStandardParam> p1 = MemoryHandler.GetListByID(p.getId());

                                 ArrayList<NitStandardParam> p3 =  MemoryHandler.GetListByID(p1.get(0).getId());

                                 ArrayList<NitStandardParam> p5 = MemoryHandler.GetListByID(p3.get(0).getId());

                                 ArrayList<NitStandardParam> p7 = MemoryHandler.GetListByID(p5.get(0).getId());

                                 ArrayList<NitStandardParam> p9 =  MemoryHandler.GetListByID(p7.get(0).getId());

                                 //adding the minimum

                                 int Min =  Main.dbDriver.addChildParam(m.getId(),p1.get(0).getName());
                                 //adding the maximum
                                 int Max =  Main.dbDriver.addChildParam(Min,p3.get(0).getName());

                                 int MinTolerance = Main.dbDriver.addChildParam(Max,p5.get(0).getName());

                                 int MaxTolerance = Main.dbDriver.addChildParam(MinTolerance,p7.get(0).getName());

                                 Main.dbDriver.addChildParam(MaxTolerance,p9.get(0).getName());

                             }




                         }







                     }


                 }


             } else {

                 AddParamsToTests(ID,ImpactParams);

             }


         }break;

         case("Micro-Hardness"):

             ArrayList<Integer> g = new ArrayList<>();

             //adding the testParams to the list

             for (String d : TestParams) {

                 g.add(Main.dbDriver.addChildParam(ID, d));

             }


             //I need the ID of the test in the new list

             ArrayList<NitStandardParam> NewTestList = MemoryHandler.GetListByID(ID);



             //Get the list of parameters for the list
             for(NitStandardParam p:ListOfTest){

                 if(p.getName().equals("Micro-Hardness")){

                     CopyParams = MemoryHandler.GetListByID(p.getId());

                 }

             }

             //Here the copying really happens
             if(!CopyParams.isEmpty()){

                 for(NitStandardParam p:CopyParams){

                     if(MemoryHandler.haveChildren(p.getId())){



                         for(NitStandardParam m:NewTestList){

                             if(p.getName().equals(m.getName())){


                                 //This one is right
                                 ArrayList<NitStandardParam> p1 = MemoryHandler.GetListByID(p.getId());

                                 ArrayList<NitStandardParam> p3 =  MemoryHandler.GetListByID(p1.get(0).getId());

                                 ArrayList<NitStandardParam> p5 =  MemoryHandler.GetListByID(p3.get(0).getId());

                                 ArrayList<NitStandardParam> p7 =  MemoryHandler.GetListByID(p5.get(0).getId());

                                 ArrayList<NitStandardParam> p9 =  MemoryHandler.GetListByID(p7.get(0).getId());

                                 //adding the minimum

                                 int Min =  Main.dbDriver.addChildParam(m.getId(),p1.get(0).getName());
                                 //adding the maximum
                                 int Max =  Main.dbDriver.addChildParam(Min,p3.get(0).getName());

                                 int MinTolerance = Main.dbDriver.addChildParam(Max,p5.get(0).getName());

                                 int MaxTolerance = Main.dbDriver.addChildParam(MinTolerance,p7.get(0).getName());

                                 Main.dbDriver.addChildParam(MaxTolerance,p9.get(0).getName());

                             }




                         }







                     }


                 }


             } else {

                 AddParamsToTests(ID,MicroHardnessParams);

             }



                 break;

     }




}



  /*
          else {

              ArrayList<Integer> m ;
              ArrayList<Integer> g ;
              ArrayList<Integer> f ;
              ArrayList<Integer> h ;
              ArrayList<Integer> l ;

              m = MemoryHandler.findChildrenID(NeededParetID);
              g = MemoryHandler.findChildrenID(m.get(0));
              f = MemoryHandler.findChildrenID(g.get(0));
              h = MemoryHandler.findChildrenID(f.get(0));
              l = MemoryHandler.findChildrenID(h.get(0));

          } */



}
