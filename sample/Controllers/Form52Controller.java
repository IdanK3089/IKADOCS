package sample.Controllers;

import com.github.daytron.simpledialogfx.data.DialogResponse;
import com.github.daytron.simpledialogfx.data.HeaderColorStyle;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;
import sample.Database.HeaderDB;
import sample.Database.StateDB;
import sample.EditCell;
import sample.Filter.FilterSupport;
import sample.Main;
import sample.OtherPojos.CSVRow;
import sample.OtherPojos.HeaderObj;
import sample.OtherPojos.StateObj;
import sample.TestPojos.ParamObject;


import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;

public class Form52Controller implements Initializable {

    private final static int rowsPerPage = 500;


    public boolean NoStateDB = false;


@FXML
JFXCheckBox ImpactCB;

    @FXML
    JFXCheckBox BendCB;

    @FXML
    JFXCheckBox HardnessCB;

    @FXML
    JFXCheckBox MicroCB;

    @FXML
    JFXCheckBox MetaCB;

    @FXML
    JFXCheckBox ChemCB;

    @FXML
    JFXCheckBox TensileCB;

    @FXML
    JFXRadioButton EngRadio;
    @FXML
    JFXRadioButton HebRadio;

    ToggleGroup lgGroup = new ToggleGroup();

    public File file;

boolean AlreadyLoaded = false;

    @FXML
    ChoiceBox<String> BendDCB;

    @FXML
    ChoiceBox<String> JobCB;

    @FXML
    ChoiceBox<String> AutoCB;



    @FXML
    JFXCheckBox Abox;

@FXML TableColumn StateSelClm;

@FXML JFXCheckBox UltraCleanBox;

@FXML JFXCheckBox AlcoCleanBox;

    @FXML JFXCheckBox MultiSample;


    @FXML JFXCheckBox ReturnBox;

    @FXML JFXCheckBox MarkBox;


    @FXML
    JFXTextField BendThickness;

    @FXML
    JFXTextField TensileSpecimenTxt;

    @FXML JFXTextField CustomThicknessTxt;

    @FXML JFXTextField ChemicalSpecimenTxt;

    @FXML JFXTextField HardnessSpecimenTxt;

    @FXML JFXTextField MicroHSpecimenTxt;

    @FXML JFXTextField ImpactSpecimenTxt;

    @FXML JFXTextField BendSpecimenTxt;


    @FXML JFXTextField FormNameTxt;

    @FXML JFXTextField HeaderTxt;

    @FXML ChoiceBox<String> TensileTypeCB;

    @FXML ChoiceBox<String> TensileUnitCB;

    @FXML ChoiceBox<String> TensileThicknessCB;


    @FXML ChoiceBox<String> TensileStdCB;

    @FXML ChoiceBox<String> TensileDirectionCB;

    @FXML ChoiceBox<String> ChemicalTypeCB;

    @FXML ChoiceBox<String> ChemicalPlaceCB;

    @FXML ChoiceBox<String> MetaTypeCB;

    @FXML ChoiceBox<String> MetaStdCB;

    @FXML ChoiceBox<String> MetaModelCB;

    @FXML ChoiceBox<String> PolishCB;

    @FXML ChoiceBox<String> HardMethodCB;

    @FXML ChoiceBox<String> MeteDirectionCB;

    @FXML ChoiceBox<String> HardLoadCB;

    @FXML ChoiceBox<String> MicroHMethodCB;

    @FXML ChoiceBox<String> MicroHLoadCB;

    @FXML ChoiceBox<String> MicroHTypeCB;

    @FXML ChoiceBox<String> ImpactStdCB;

    @FXML ChoiceBox<String> ImpactSizeCB;

    @FXML ChoiceBox<String> ImpactPlaceCB;

    @FXML ChoiceBox<String> ImpactTempCB;

    @FXML ChoiceBox<String> BendTypeCB;

    @FXML ChoiceBox<String> BendAngleCB;

    @FXML ChoiceBox<String> BendStdCB;

    @FXML TableColumn StateClm;

    Variables variables;


    @FXML
    JFXCheckBox RemCoatBox;

    @FXML
    JFXCheckBox AppendixBox;

@FXML
Pagination StatePager;

    @FXML
    TableColumn HeaderSelClm;

    @FXML
    TableColumn<HeaderObj,String> HeaderClm;

    @FXML TableView HeaderTbl;

    @FXML TableView StateTbl;


    public HeaderObj currentHeader;

    @FXML
    TableView<CSVRow> DetailTbl;

    @FXML
    JFXDatePicker Form52Dater;

    @FXML
            TableColumn DetailSelClm;


    ToggleGroup DetailGroup = new ToggleGroup();

     ToggleGroup HeaderGroup = new ToggleGroup();

    CSVRow DetailTblObj;

    ToggleGroup StateGroup = new ToggleGroup();

    private CSVFormat csvFormat;
    private Integer numbeColumns = 0;
    private boolean saved = true;

    File Chosenfile;

    File GeneratedFile = null;

    String laungage;

    File Form52File;

    ObservableList<CSVRow> m;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        DetailTbl.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if( event.getCode() == KeyCode.ENTER) {
                    DetailTblObj = null;

//                  event.consume(); // don't consume the event or else the values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( DetailTbl.getEditingCell() == null) {
                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = DetailTbl.getFocusModel().getFocusedCell();
                        DetailTbl.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());
                        DetailTblObj = null;
                    }
                }

            }
        });



        StateSelClm.impl_setReorderable(false);
        StateClm.impl_setReorderable(false);

        HeaderSelClm.impl_setReorderable(false);
        HeaderClm.impl_setReorderable(false);


        DetailSelClm.impl_setReorderable(false);

        EngRadio.setToggleGroup(lgGroup);
        HebRadio.setToggleGroup(lgGroup);

        EngRadio.setUserData("English");
        HebRadio.setUserData("Hebrew");

        lgGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (lgGroup.getSelectedToggle() != null) {


                    laungage =  lgGroup.getSelectedToggle().getUserData().toString();

                }

            }
        });


        HeaderDB Hdb = new HeaderDB();

        Hdb.CreateDb();

        StateDB db = new StateDB();

        db.CreateDb();

        setKeyEvents();

        FilterSupport.addFilter(StateClm);

        setKeyHeaderEvents();
        setKeyEvents();

        StatePager.setPageCount(db.queryForStates().size() / rowsPerPage + 1);
        StatePager.setPageFactory(this::createPage);


        StateClm.setCellValueFactory(
                new PropertyValueFactory<StateObj,String>("stateName")
        );
        StateClm.setCellFactory(TextFieldTableCell.forTableColumn());


        PopulateTable(StateTbl,StateSelClm,StateGroup,0);


        TensileSpecimenTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        CustomThicknessTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        ChemicalSpecimenTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        HardnessSpecimenTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        MicroHSpecimenTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        ImpactSpecimenTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        BendSpecimenTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        FormNameTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        HeaderTxt.setFont(Font.font("ariel", FontWeight.BOLD, 18));


        DetailTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        StateTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        HeaderTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");

        JobCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");
        AutoCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");

        JobCB.getItems().add("בדיקת ח\"ג");
        JobCB.getItems().add("בדיקה מטלורגית");
        JobCB.getItems().add("חקר כשל");

        AutoCB.getItems().add("ISRAC");
        AutoCB.getItems().add("NADCAP");
        AutoCB.getItems().add("לא");


        TensileTypeCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");

        TensileTypeCB.getItems().add("4D");
        TensileTypeCB.getItems().add("5D");
        TensileTypeCB.getItems().add("מלבני");
        TensileTypeCB.getItems().add("צינור");
        TensileTypeCB.getItems().add("בורג");
        TensileTypeCB.getItems().add("חוט");
        TensileTypeCB.getItems().add("ריתוך");
        TensileTypeCB.getItems().add("יציקה אפורה");
        TensileTypeCB.getItems().add("אחר");


        TensileThicknessCB.getItems().add("2.5 מ\"מ");
        TensileThicknessCB.getItems().add("4 מ\"מ");
        TensileThicknessCB.getItems().add("6 מ\"מ");
        TensileThicknessCB.getItems().add("9 מ\"מ");
        TensileThicknessCB.getItems().add("12.5 מ\"מ");
        TensileThicknessCB.getItems().add("19 מ\"מ - ריתוך");
        TensileThicknessCB.getItems().add("20 מ\"מ");
        TensileThicknessCB.getItems().add("25 מ\"מ - ריתוך");


        TensileThicknessCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        TensileStdCB.getItems().add("ASTM E 8");
        TensileStdCB.getItems().add("מוכן");
        TensileStdCB.getItems().add("אחר");

        TensileStdCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        TensileDirectionCB.getItems().add("דגם אורכי");
        TensileDirectionCB.getItems().add("דגם רוחבי");
        TensileDirectionCB.getItems().add("מהמרכז");
        TensileDirectionCB.getItems().add("מחצי המרחק בין המרכז לפני השטח");

        TensileDirectionCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ChemicalTypeCB.getItems().add("רגילה");
        ChemicalTypeCB.getItems().add("בהתכה");

        ChemicalTypeCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ChemicalPlaceCB.getItems().add("לב החלק");
        ChemicalPlaceCB.getItems().add("פני השטח");

        ChemicalPlaceCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MetaTypeCB.getItems().add("גודל גרעין");
        MetaTypeCB.getItems().add("עובי ציפוי/שכבה");
        MetaTypeCB.getItems().add("אינקלוזיות");
        MetaTypeCB.getItems().add("פחמון/ הפחתת פחמן");
        MetaTypeCB.getItems().add("מיקרו-מבנה");
        MetaTypeCB.getItems().add("מאקרו");
        MetaTypeCB.getItems().add("עומק חדירת קורוזיה ");
        MetaTypeCB.getItems().add("אחר");


        MetaTypeCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MetaStdCB.getItems().add("ASTM E 112");
        MetaStdCB.getItems().add("ASTM B 487");
        MetaStdCB.getItems().add("ASTM E 45");
        MetaStdCB.getItems().add("ASTM E 340");

        MetaStdCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MeteDirectionCB.getItems().add("דגם אורכי");
        MeteDirectionCB.getItems().add("דגם רוחבי");

        MeteDirectionCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MetaModelCB.getItems().add("דגם חם - רגיל");
        MetaModelCB.getItems().add("דגם חם – שחור");
        MetaModelCB.getItems().add("דגם חם - שקוף");
        MetaModelCB.getItems().add("דגם קר");

        MetaModelCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        HardMethodCB.getItems().add("HRC");
        HardMethodCB.getItems().add("HRB");
        HardMethodCB.getItems().add("HRA");

        HardMethodCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MicroHLoadCB.getItems().add("100g");
        MicroHLoadCB.getItems().add("200g");
        MicroHLoadCB.getItems().add("500g");
        MicroHLoadCB.getItems().add("2kg");

        MicroHLoadCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MicroHMethodCB.getItems().add("HK");
        MicroHMethodCB.getItems().add("HV");


        MicroHMethodCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        MicroHTypeCB.getItems().add("לב");
        MicroHTypeCB.getItems().add("מפל");


        MicroHTypeCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ImpactStdCB.getItems().add("ASTM E 23");
        ImpactStdCB.getItems().add("אחר");

        ImpactStdCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ImpactSizeCB.getItems().add("10x10x55");
        ImpactSizeCB.getItems().add("7.5x10x55");
        ImpactSizeCB.getItems().add("5x10x55");
        ImpactSizeCB.getItems().add("2.5x10x55");
        ImpactSizeCB.getItems().add("אחר");

        ImpactSizeCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ImpactPlaceCB.getItems().add("מתכת בסיס");
        ImpactPlaceCB.getItems().add("רתך");
        ImpactPlaceCB.getItems().add("HAZ");
        ImpactPlaceCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        ImpactTempCB.getItems().add("טמפרטורת החדר");
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


        BendTypeCB.getItems().add("פנים");
        BendTypeCB.getItems().add("שורש");
        BendTypeCB.getItems().add("צד");
        BendTypeCB.getItems().add("אחר");
        BendTypeCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");


        BendAngleCB.getItems().add("90°");
        BendAngleCB.getItems().add("180°");
        BendAngleCB.getItems().add("אחר");
        BendAngleCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");

        BendStdCB.getItems().add("ASME IX");
        BendStdCB.getItems().add("EN 910");
        BendStdCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");




        TensileUnitCB.getItems().add("MPa");
        TensileUnitCB.getItems().add("KSI");
        TensileUnitCB.getItems().add("Kg/mm^2");
        TensileUnitCB.setStyle("-fx-font-family: ariel;-fx-font-weight: bold");

        Form52Dater.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 16px");

        InstlizeHeaders();
        FilterSupport.addFilter(HeaderClm);

        DetailTbl.setEditable(true);

        csvFormat = CSVFormat.DEFAULT.withIgnoreEmptyLines(false);


        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setAutoHide(true);
        MenuItem inserirLinha;
        if(Main.Hebrew==true){

            inserirLinha = new MenuItem("הוסף שורה");

        }else{inserirLinha = new MenuItem("Add Row");}


        inserirLinha.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
               if(DetailGroup.getSelectedToggle()!=null){

                   DetailGroup.getSelectedToggle().setSelected(false);
                   // if(m!=null)
                  //  m.clear();
                   DetailTblObj = null;

                  // if(DetailTblObj!=null)
                 //      DetailTblObj.getColumns().clear();
               }

                addNewRow();

            }
        });

        contextMenu.getItems().add(inserirLinha);
        MenuItem removerLinha;
        if(Main.Hebrew==true){

            removerLinha = new MenuItem("מחק שורה");

        }else{

            removerLinha = new MenuItem("Delete Row");

        }        removerLinha.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(DetailGroup.getSelectedToggle()!=null){

                    DetailGroup.getSelectedToggle().setSelected(false);


                    if(DetailTblObj!=null)
                        DetailTblObj.getColumns().clear();
                }

                deleteRow();

            }
        });
        contextMenu.getItems().add(removerLinha);

        contextMenu.getItems().add(new SeparatorMenuItem());



        MenuItem removerColuna ;
        if(Main.Hebrew==true){removerColuna = new MenuItem("מחק עמודה");}else{removerColuna = new MenuItem("Delete Column");}


        removerColuna.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(DetailGroup.getSelectedToggle()!=null){

                    DetailGroup.getSelectedToggle().setSelected(false);

                    if(DetailTblObj!=null)
                        DetailTblObj.getColumns().remove(DetailTblObj.getColumns().size()-1);
                }

                if(DetailTbl.getColumns().size()==2){
                    deleteRow();
                    deleteColumn();
                }else{

                    deleteColumn();

                }

            }
        });
        contextMenu.getItems().add(removerColuna);

        DetailTbl.setContextMenu(contextMenu);


    }
@FXML
    public void AddNewHeader() throws FileNotFoundException {

if(LoginController.AdminPass==false){

    if(Main.Hebrew==true){
        Dialog dialog = new Dialog(
                DialogType.ERROR,
                "התחבר כמשתמש ראשי כדי להוסיף פרטים",
                "התחבר כמשתמש ראשי");
        dialog.setFontSize(22);
        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

        dialog.showAndWait();

    }else {



        Dialog dialog = new Dialog(
                DialogType.ERROR,
                "Login as ADMIN to add new header to database",
                "Login as ADMIN");
        dialog.setFontSize(22);
        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

        dialog.showAndWait();



    }




} else {



    try{

        //ToDo here add the code for adding new header to database

        String name = HeaderTxt.getText();

        if(!name.equals("")){


            HeaderDB db = new HeaderDB();

            HeaderObj h = new HeaderObj();

            h.setHeader(name);
            h.setId(db.FindHigestID());

            db.insertHeader(h);


            populateHeaderTbl();



            if(Main.Hebrew==true){

                Dialog dialog = new Dialog(
                        DialogType.INFORMATION,
                        "הפרטים נוספו",
                        "הפרטים נוספו");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                dialog.showAndWait();


            }else{
                Dialog dialog = new Dialog(
                        DialogType.INFORMATION,
                        "Header Added",
                        "Header Added");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                dialog.showAndWait();

            }



        }else{

            if(Main.Hebrew==true){

                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "לא ניתן לשמור",
                        "לא ניתן לשמור");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();

            }else{

                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "Please Add Header Name to Save",
                        "Add Header Name");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();

            }


        }


    }catch(Exception e){

        File file = new File(Paths.get("").toAbsolutePath()+"\\Log.txt");
        PrintStream ps = new PrintStream(file);
        e.printStackTrace(ps);
        ps.close();

    }







}



    }





    @FXML
    public void AddHeaderToTbl(){
          if(currentHeader!=null){

              int columnIndex = 0 ;

              for (CSVRow row : DetailTbl.getItems()) {
                  row.addColumn(DetailTbl.getColumns().size()-1);
              }
              numbeColumns=DetailTbl.getColumns().size();
              DetailTbl.getColumns().add(createColumn(numbeColumns - 1,currentHeader.getHeader()));
              DetailTbl.refresh();

          } else{
             if(Main.Hebrew==true){

                 Dialog dialog = new Dialog(
                         DialogType.ERROR,
                         "בחר פרטים מהטבלה",
                         "בחר פרטים");
                 dialog.setFontSize(22);
                 dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                 dialog.showAndWait();

             }else{

                 Dialog dialog = new Dialog(
                         DialogType.ERROR,
                         "Choose Header",
                         "Choose Header");
                 dialog.setFontSize(22);
                 dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                 dialog.showAndWait();
             }

          }






    }














    private void setTableEditable() {
        DetailTbl.setEditable(true);
        // allows the individual cells to be selected
        DetailTbl.getSelectionModel().cellSelectionEnabledProperty().set(true);
        // when character or numbers pressed it will start edit in editable
        // fields
        DetailTbl.setOnKeyPressed(event -> {

            if (event.getCode().isLetterKey() || event.getCode().isLetterKey()) {

                DetailTblObj = null;

            } else if (event.getCode() == KeyCode.RIGHT
                    || event.getCode() == KeyCode.TAB) {
                DetailTbl.getSelectionModel().selectNext();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                // work around due to
                // TableView.getSelectionModel().selectPrevious() due to a bug
                // stopping it from working on
                // the first column in the last row of the table
                event.consume();
            }
        });
    }

    public void InstlizeHeaders(){

        setTableEditable();


        HeaderClm.setCellFactory(TextFieldTableCell.forTableColumn());

        HeaderClm.setCellValueFactory(new PropertyValueFactory<>("Header"));
     //   headersList.add("Project No.:");

      //  headersList.add("Material:");

      //  headersList.add("Temper:");

       // headersList.add("Supplier:");

      //  headersList.add("ספק:");

      //  headersList.add("מס פרוייקט:");




        populateHeaderTbl();





    }


public void populateHeaderTbl(){

    FilterSupport.getItems(HeaderTbl).clear();


    HeaderDB db = new HeaderDB();

    ArrayList<HeaderObj> listm = db.queryForHeaders();


   // new ArrayList<HeaderObj>(Arrays.asList(listm));




    ObservableList list = FXCollections.observableArrayList();

    list.addAll(listm);

    for(Object s : new LinkedList<>(list)){
        addRadioButtonToStringVoidTbl();
        FilterSupport.getItems(HeaderTbl).add(s);

    }



}



    private void addRadioButtonToDetailTblDynamic(TableColumn clm) {


        Callback<TableColumn<CSVRow, Void>, TableCell<CSVRow, Void>> cellFactory = new Callback<TableColumn<CSVRow, Void>, TableCell<CSVRow, Void>>() {

            public TableCell<CSVRow, Void> call(final TableColumn<CSVRow, Void> param) {
                final TableCell<CSVRow, Void> cell = new TableCell<CSVRow, Void>() {

                    private RadioButton btn = new RadioButton();

                    {





                        btn.setToggleGroup(DetailGroup);

                        btn.setOnAction((ActionEvent event) -> {
                            if(Main.Hebrew==true){
                                Dialog dialog = new Dialog(
                                        DialogType.CONFIRMATION,
                                        "להוסיף את שורת הפרטים לטופס 52",
                                        "האם להוסיף את שורת הפרטים לטופס 52?",
                                        "לחץ YES כדי להוסיף את שורת הפרטים");
                                dialog.setFontSize(22);
                                dialog.showAndWait();

                                if (dialog.getResponse() == DialogResponse.YES) {

                                    DetailTblObj = getTableView().getItems().get(getIndex());

                                } else{


                                    DetailGroup.getSelectedToggle().setSelected(false);


                                }


                            }else {

                                Dialog dialog = new Dialog(
                                        DialogType.CONFIRMATION,
                                        "Add header to Form 52",
                                        "Add this header to Form 52?",
                                        "Press YES to this header to Form 52");
                                dialog.setFontSize(22);
                                dialog.showAndWait();

                                if (dialog.getResponse() == DialogResponse.YES) {

                                    DetailTblObj = getTableView().getItems().get(getIndex());

                                } else{

                                    DetailGroup.getSelectedToggle().setSelected(false);

                                }
                            }



                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            btn.setSelected(false);
                            btn.setAlignment(Pos.CENTER);
                        }
                    }
                };
                return cell;
            }
        };
       clm.impl_setReorderable(false);
        clm.setCellFactory(cellFactory);


    }




    private void addRadioButtonToDetailTbl(TableColumn clm) {


        Callback<TableColumn<CSVRow, Void>, TableCell<CSVRow, Void>> cellFactory = new Callback<TableColumn<CSVRow, Void>, TableCell<CSVRow, Void>>() {

            public TableCell<CSVRow, Void> call(final TableColumn<CSVRow, Void> param) {
                final TableCell<CSVRow, Void> cell = new TableCell<CSVRow, Void>() {

                    private RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(DetailGroup);

                        btn.setOnAction((ActionEvent event) -> {
                          if(Main.Hebrew==true){

                              Dialog dialog = new Dialog(
                                      DialogType.CONFIRMATION,
                                      "להוסיף את שורת הפרטים לטופס 52",
                                      "האם להוסיף את שורת הפרטים לטופס 52?",
                                      "בחר YES כדי להכניס את שורת הפרטים לטופס 52");
                              dialog.setFontSize(22);
                              dialog.showAndWait();

                              if (dialog.getResponse() == DialogResponse.YES) {

                                  DetailTblObj = getTableView().getItems().get(getIndex());

                              } else{

                                  DetailGroup.getSelectedToggle().setSelected(false);

                              }
                          }else{

                              Dialog dialog = new Dialog(
                                      DialogType.CONFIRMATION,
                                      "Add header to Form 52",
                                      "Add this header to Form 52?",
                                      "Press YES to this header to Form 52");
                              dialog.setFontSize(22);
                              dialog.showAndWait();

                              if (dialog.getResponse() == DialogResponse.YES) {

                                  DetailTblObj = getTableView().getItems().get(getIndex());

                              } else{

                                  DetailGroup.getSelectedToggle().setSelected(false);


                              }
                          }





                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            btn.setSelected(false);
                            btn.setAlignment(Pos.CENTER);
                        }
                    }
                };
                return cell;
            }
        };
        clm.impl_setReorderable(false);

        clm.setCellFactory(cellFactory);


    }





    private void addRadioButtonToStringVoidTbl() {


        Callback<TableColumn<HeaderObj, Void>, TableCell<HeaderObj, Void>> cellFactory = new Callback<TableColumn<HeaderObj, Void>, TableCell<HeaderObj, Void>>() {

            public TableCell<HeaderObj, Void> call(final TableColumn<HeaderObj, Void> param) {
                final TableCell<HeaderObj, Void> cell = new TableCell<HeaderObj, Void>() {

                    private RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(HeaderGroup);

                        btn.setOnAction((ActionEvent event) -> {

                            currentHeader = getTableView().getItems().get(getIndex());


                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                         //   btn.setSelected(false);
                            btn.setSelected(false);
                           btn.setAlignment(Pos.CENTER);
                        }
                    }
                };
                return cell;
            }
        };

        HeaderSelClm.setCellFactory(cellFactory);


    }
    @FXML
    public void unSelect(){

        if(DetailTbl.getItems().size()!=0){

            if(DetailGroup.getSelectedToggle().isSelected()){

                DetailTblObj = null;
                DetailGroup.getSelectedToggle().setSelected(false);



            } else{



            }

        } else {

           if(Main.Hebrew==true){

               Dialog dialog = new Dialog(
                       DialogType.ERROR,
                       "אין שורות",
                       "אין שורות");
               dialog.setFontSize(22);
               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

               dialog.showAndWait();

           }else{

               Dialog dialog = new Dialog(
                       DialogType.ERROR,
                       "No Rows",
                       "No Rows");
               dialog.setFontSize(22);
               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

               dialog.showAndWait();
           }

        }



    }



    public void Save(File file) throws IOException {


//ToDO here i have to add the "column addition to the file




            int i;

            try (PrintWriter pw = new PrintWriter(file); CSVPrinter print = csvFormat.print(pw)) {

                for (int j = 1; j<DetailTbl.getColumns().size();j++){

                    print.print( DetailTbl.getColumns().get(j).getText());

                }
                print.println();

                for (CSVRow row : DetailTbl.getItems()) {
                    i=1;
                    if (row.isEmpty()) {
                        print.println();
                    } else {
                        for (SimpleStringProperty column : row.getColumns()) {
                            print.print( column.getValue());
                            i++;
                        }
                        print.println();

                    }

                }
                print.flush();


            } catch (Exception ex) {
                ex.printStackTrace();
                Alert d = new Alert(Alert.AlertType.ERROR);
                d.setHeaderText("Error Saving " + (file != null ? file.getName() : "."));
                d.setContentText(ex.getMessage());
                d.setTitle("Error");
                d.show();
            }










    }


    private void updateTable(ObservableList<CSVRow> rows) {
        DetailTbl.getColumns().clear();
        DetailTbl.getItems().clear();
        if(currentHeader!=null){

            DetailTbl.setItems(rows);
            DetailTbl.setEditable(true);
            DetailTbl.getSelectionModel().setCellSelectionEnabled(true);



        }

        DetailTbl.setItems(rows);
        DetailTbl.setEditable(true);
        DetailTbl.getSelectionModel().setCellSelectionEnabled(true);
    }




    private void readFile(File csvFile) throws IOException {


         if(csvFile.exists()){

             DetailTbl.getColumns().clear();
             ObservableList<CSVRow> rows = FXCollections.observableArrayList();

             CSVRow row = null;

             boolean firstRow = false;

             try (Reader in = new InputStreamReader(new FileInputStream(csvFile))) {
                 CSVParser parse = csvFormat.parse(in);
                 for (CSVRecord record : parse.getRecords()) {

                     if(firstRow){

                         row = new CSVRow();
                         for (int i = 0; i < record.size(); i++) {
                             row.getColumns().add(new SimpleStringProperty(record.get(i)));

                             addRadioButtonToDetailTbl(DetailSelClm);


                         }
                         rows.add(row);

                         //ToDO check how to load the thing
                         // addRadioButtonToDetailTblDynamic(DetailSelClm);
                     }

                     firstRow = true;


                 }

             } catch(Exception e){


         }

             updateTable(rows);

             if(AlreadyLoaded!=true){

                 AlreadyLoaded = true;

                 try (Reader in = new InputStreamReader(new FileInputStream(csvFile))) {
                     CSVParser parse = csvFormat.parse(in);

                     CSVRecord record = parse.getRecords().get(0);


                     TableColumn<CSVRow,Void> col = new TableColumn<>();
                     col.setResizable(false);
                     col.setMaxWidth(25);
                     addRadioButtonToDetailTblDynamic(col);
                     DetailTbl.getColumns().add(col);



                     for (int i = 0; i < record.size(); i++) {

                         String name = record.get(i);

                         numbeColumns++;
                         DetailTbl.getColumns().add(createColumn(numbeColumns - 1,name));
                         DetailTbl.refresh();



                     }



                 }


             }

         } else {

             if(Main.Hebrew==true){
                 Dialog dialog = new Dialog(
                         DialogType.ERROR,
                         "קובץ המצב לא נמצא",
                         "לא ניתן לאתר את הקובץ",
                         "לא נמצא הקובץ");
                 dialog.setFontSize(22);
                 dialog.showAndWait();


             }else{
                 Dialog dialog = new Dialog(
                         DialogType.ERROR,
                         "State File not found",
                         "State File cannot be found",
                         "No State File");
                 dialog.setFontSize(22);
                 dialog.showAndWait();


             }
         }












    }


@FXML
public void SignForm(){







}








    @FXML
    private void onSaveActionEvent(ActionEvent event) throws IOException {
        if (saved) {



            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            if(Main.Hebrew==true){

                alert.setTitle("שמירת מצב");
                alert.setHeaderText("האם לשמור מצב?");
                alert.setContentText("לחץ OK לשמירת המצב");
            }else{

                alert.setTitle("Save State");
                alert.setHeaderText("Sate the State?");
                alert.setContentText("Press OK to Save State");
            }


            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {


                if(FormNameTxt.getText().equals("")){

                    if(Main.Hebrew==true){

                        Dialog dialog = new Dialog(
                                DialogType.ERROR,
                                "מלא את שם הדוח כדי לשמור",
                                "מלא את שם הדוח");
                        dialog.setFontSize(22);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                        dialog.showAndWait();
                    }else{

                        Dialog dialog = new Dialog(
                                DialogType.ERROR,
                                "Fill the report Name to save state",
                                "Fill Report Name");
                        dialog.setFontSize(22);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                        dialog.showAndWait();
                    }




                } else {
               if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\Statefiles"+"\\" + FormNameTxt.getText())==true){

                   StateDB db = new StateDB();
                   StateObj state = new StateObj();

                   state.setId(db.FindHigestID());
                   state.setStateName(FormNameTxt.getText());
                   state.setStatePath(Paths.get("").toAbsolutePath().toString()+"\\Statefiles"+"\\" + FormNameTxt.getText());
                   if(NoStateDB==false)
                   db.insertState(state);

                   PopulateTable(StateTbl,StateSelClm,StateGroup,0);

                   file = new File(Paths.get("").toAbsolutePath().toString()+"\\Statefiles"+"\\" + FormNameTxt.getText());

                   Save(file);

               } else {

                   PopulateTable(StateTbl,StateSelClm,StateGroup,0);

                   file = new File(Paths.get("").toAbsolutePath().toString()+"\\Statefiles"+"\\" + FormNameTxt.getText());

                   Save(file);

               }



                }










            }





        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);

         if(Main.Hebrew==true){
             a.setHeaderText("האם אתה בטוח כי ברצונך לשמור על קובץ קודם?");


         }else{


             a.setHeaderText("Are you sure to overwrite the state?");

         }
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.YES) {
                Save(file);
            }
        }
    }




    public ObservableList<StateObj> ReportToList(ArrayList<StateObj> List){

        ObservableList<StateObj> list = FXCollections.observableArrayList();

        //ToDo
        for(StateObj nd : List)
            list.add(nd);

        return list;


    }

    public void PopulateTable(TableView table,TableColumn clm,ToggleGroup g,int NeededParetID) {

        StateDB db = new StateDB();

        FilterSupport.getItems(table).clear();

        ObservableList<StateObj> m = ReportToList(db.queryForStates());

        for (StateObj sd : m) {

            addRadioButtonToPrepTable(g,clm);

            FilterSupport.getItems(table).add(sd);

        }
    }

    private void addNewRow() {

        if(DetailTbl.getColumns().size()>1){

            DetailTbl.getItems().add(DetailTbl.getItems().size(), new CSVRow());
            DetailTbl.getSelectionModel().select(0);
            addRadioButtonToDetailTbl(DetailSelClm);

        }

    }

    private void deleteRow() {


       // int i = DetailTbl.getSelectionModel().getSelectedIndex();

      //  int j = DetailTbl.getItems().size();

        if(DetailTbl.getItems().size()>0)
        DetailTbl.getItems().remove(DetailTbl.getItems().size()-1);
    }



    @FXML
    private void onLoadActionEvent(ActionEvent event) throws IOException {


        if(DetailTbl.getItems().size()!=0) {

            if (Chosenfile != null){

                if(AlreadyLoaded==false){
                    DetailTbl.getColumns().remove(1,DetailTbl.getColumns().size());

                    DetailTbl.getItems().clear();
                  //  DetailTbl.getColumns().clear();


                    readFile(Chosenfile);
                }



            } else {


                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "Message for developer",
                        "Message for developer");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();


            }



        } else{
            if (Chosenfile != null)
                if(AlreadyLoaded==false){
                    DetailTbl.getItems().clear();
                    //DetailTbl.getColumns().clear();

                    DetailTbl.getColumns().remove(1,DetailTbl.getColumns().size());



                    readFile(Chosenfile);

                }



        }
    }


    private void deleteColumn() {


        if(DetailTbl.getColumns().size()>1){

          //  DetailTblObj.getColumns().remove(DetailTblObj.getColumns().size()-1);


            DetailTbl.getColumns().remove(DetailTbl.getColumns().size()-1);

                DetailTbl.refresh();

        }

    }





    private TableColumn<CSVRow, String> createColumn(int index,String currentHeader) {

        TableColumn<CSVRow, String> col = new TableColumn<>(currentHeader);

        col.setPrefWidth(130);
        col.impl_setReorderable(false);

        col.setSortable(false);
        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CSVRow, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CSVRow, String> param) {

               if(index>-1)
                adjustColumns(param.getValue().getColumns());
                return param.getValue().getColumns().get(index);
            }
        });
        col.setCellFactory(column -> EditCell.createStringEditCell());
        col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CSVRow, String>>() {



            @Override
            public void handle(TableColumn.CellEditEvent<CSVRow, String> event) {

                if(DetailGroup.getSelectedToggle()!=null){

                    DetailGroup.getSelectedToggle().setSelected(false);


                    if(DetailTblObj!=null)
                        DetailTblObj.getColumns().clear();
                }


           if(index>-1)
                adjustColumns(event.getRowValue().getColumns());
                event.getRowValue().getColumns().get(index).set(event.getNewValue());

            }
        });
        col.setEditable(true);
        return col;
    }


    private void adjustColumns(List<SimpleStringProperty> columns) {
        int dif = numbeColumns - columns.size();
        for (int i = 0; i < dif; i++) {
            columns.add(new SimpleStringProperty());
        }
    }


    public void setKeyHeaderEvents(){

        HeaderTbl.setOnKeyReleased((KeyEvent t)-> {
            KeyCode key=t.getCode();
            if (key==KeyCode.DELETE){
                HeaderObj header =  (HeaderObj)HeaderTbl.getSelectionModel().selectedItemProperty().get();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(Main.Hebrew==true){
                    alert.setTitle("מחיקת משתמש");
                    alert.setHeaderText("למחוק פרט זה?");
                    alert.setContentText("האם ברצונך למחוק פרט זה?");

                }else{

                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Delete Header Detail");
                    alert.setContentText("Do you want to delete this Header Detail?");
                }


                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK

                   HeaderDB db = new HeaderDB();

                   db.DeleteHeader(header.getId());

                    populateHeaderTbl();

                             }

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        });



    }



    private void addRadioButtonToPrepTable(ToggleGroup Group, TableColumn clm) {

        Callback<TableColumn<StateObj, String>, TableCell<StateObj, String>> cellFactory = new Callback<TableColumn<StateObj, String>, TableCell<StateObj, String>>() {

            public TableCell<StateObj, String> call(final TableColumn<StateObj, String> param) {
                final TableCell<StateObj, String> cell = new TableCell<StateObj, String>() {

                    private RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(Group);

                        btn.setOnAction((ActionEvent event) -> {

                            AlreadyLoaded = false;
                            numbeColumns = 0;
                            StateObj data = getTableView().getItems().get(getIndex());

                              Chosenfile = new File(data.getStatePath());


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


        StateSelClm.setCellFactory(cellFactory);


    }


    @FXML
    public void OpenReport() throws IOException {

        //ToDo chagne this code it is not right


        if(GeneratedFile!=null){ if(Desktop.isDesktopSupported()){

          File f = new File(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");

            Desktop.getDesktop().open(f);

        }}else{

         if(Main.Hebrew==true){
             Dialog dialog = new Dialog(
                     DialogType.ERROR,
                     "הכן טופס",
                     "הכן טופס תחילה!");
             dialog.setFontSize(20);
             dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

             dialog.showAndWait();



         }else{
             Dialog dialog = new Dialog(
                     DialogType.ERROR,
                     "Generate Form!",
                     "Please Generate a Form!");
             dialog.setFontSize(20);
             dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

             dialog.showAndWait();

         }


        }}

    private Node createPage(int pageIndex) {

        StateDB db = new StateDB();

        ArrayList<StateObj> g = db.queryForStates();

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, g.size());
        StateTbl.setItems(FXCollections.observableArrayList(g.subList(fromIndex, toIndex)));


        return new BorderPane(StateTbl);
    }


@FXML
    public void GenerateForm() throws IOException {

    variables = new Variables();

    Docx docx = null;

     boolean TensileUnitMust = false;

    for(ParamObject j: Main.selection.getStandardData()){

        if(j.getTestName().equals("Tensile")){

            TensileUnitMust = true;

        }


    }



if(Main.selection.getAllStandardsList().size()==0 || CheckTensileUnits(Main.selection.getAllStandardsList().get(0))== 0){




    if(Form52File==null){
        if(laungage!=null){
            if(laungage.equals("English"))
                docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52-Eng.docx");
            else if(laungage.equals("Hebrew"))
                docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52.docx");

            docx.setVariablePattern(new VariablePattern("#{", "}"));


            FillData();

            if(Main.selection.getStandardData().size()!=0)
                FillStandardData();




            docx.fillTemplate(variables);


            if(!FormNameTxt.getText().isEmpty()){


                try{

                    if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx")==true){
                        docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                     //   docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                        DeleteNotNeededTables(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                    }
                } catch (Exception e){

                   if(Main.Hebrew==true){

                       Dialog dialog = new Dialog(
                               DialogType.ERROR,
                               "הדוח פתוח באחד המחשבים",
                               "לא ניתן לשמור על דוח פתוח");
                       dialog.setFontSize(22);
                       dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                       dialog.showAndWait();

                   }else{


                       Dialog dialog = new Dialog(
                               DialogType.ERROR,
                               "Report is Opened",
                               "Cannot save on Opened Report ");
                       dialog.setFontSize(22);
                       dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                       dialog.showAndWait();

                   }


                }





                GeneratedFile = new File(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52-" + FormNameTxt.getText()+".docx");

                FormCleaner();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(Main.Hebrew==true){

                    alert.setTitle("הטופס מוכן!");
                    alert.setHeaderText("האם לפתוח את הטופס");
                    alert.setContentText("לחץ OK לפתיחת הטופס");
                }else{


                    alert.setTitle("The Form was Generated!");
                    alert.setHeaderText("You wish to open the Form?");
                    alert.setContentText("Press OK to open the Form");
                }



                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK

                    OpenReport();


                }







            }





            else{

                     if(Main.Hebrew==true){
                         Dialog dialog = new Dialog(
                                 DialogType.ERROR,
                                 "חסר שם לטופס!",
                                 "הוסף שם לטופס");
                         dialog.setFontSize(22);
                         dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                         dialog.showAndWait();

                     }else{
                         Dialog dialog = new Dialog(
                                 DialogType.ERROR,
                                 "No Form Name!",
                                 "Please add Form Name!");
                         dialog.setFontSize(22);
                         dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                         dialog.showAndWait();

                     }

            }


        } else {
             if(Main.Hebrew==true){

                 Dialog dialog = new Dialog(
                         DialogType.ERROR,
                         "לא נבחרה שפה!",
                         "אנא בחר שפה");
                 dialog.setFontSize(22);
                 dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                 dialog.showAndWait();

             }else{
                 Dialog dialog = new Dialog(
                         DialogType.ERROR,
                         "No Language!",
                         "Please Select Language!");
                 dialog.setFontSize(22);
                 dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                 dialog.showAndWait();
             }



        }

    } else {

        docx = new Docx(Form52File.getPath());
        docx.setVariablePattern(new VariablePattern("#{", "}"));


        FillData();

        if(Main.selection.getStandardData().size()!=0)
            FillStandardData();




        docx.fillTemplate(variables);


        if(!FormNameTxt.getText().isEmpty()){

            try{

                if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx")==true)
                    docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
              //  docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                DeleteNotNeededTables(GeneratedFile.getPath());
            } catch (Exception e){

                if(Main.Hebrew==true){
                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "הטופס פתוח",
                            "לא ניתן לשמור על טופס פתוח");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();

                }else{
                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "Report is Opened",
                            "Cannot save on Opened Report ");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();

                }


            }






            GeneratedFile = new File(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");


            FormCleaner();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            if(Main.Hebrew==true){

                alert.setTitle("הטופס מוכן!");
                alert.setHeaderText("האם לפתוח את הטופס?");
                alert.setContentText("לחץ OK כדי לפתוח את הטופס");

            }else{

                alert.setTitle("The Form was Generated!");
                alert.setHeaderText("You wish to open the Form?");
                alert.setContentText("Press OK to open the Form");
            }


            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK

                OpenReport();


            }


        }



        else{


           if(Main.Hebrew==true){

               Dialog dialog = new Dialog(
                       DialogType.ERROR,
                       "חסר שם טופס",
                       "אנא הוסף שם טופס!");
               dialog.setFontSize(22);
               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

               dialog.showAndWait();
           }else{

               Dialog dialog = new Dialog(
                       DialogType.ERROR,
                       "No Form Name!",
                       "Please add Form Name!");
               dialog.setFontSize(22);
               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

               dialog.showAndWait();

           }


        }


}




    } else {






    if(Main.selection.getAllStandardsList()!=null && Main.selection.getAllStandardsList().get(0)!=null &&  CheckTensileUnits(Main.selection.getAllStandardsList().get(0))== 1){

       if(TensileUnitCB.getValue()!=null){
           if(TensileUnitCB.getValue().equals("MPa")){
               if(Form52File==null){
                   if(laungage!=null){
                       if(laungage.equals("English"))
                           docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52-Eng.docx");
                       else if(laungage.equals("Hebrew"))
                           docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52.docx");

                       docx.setVariablePattern(new VariablePattern("#{", "}"));


                       FillData();

                       if(Main.selection.getStandardData().size()!=0)
                           FillStandardData();




                       docx.fillTemplate(variables);


                       if(!FormNameTxt.getText().isEmpty()){


                           try{

                               if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx")==true){
                                   docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                                   //   docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                                   DeleteNotNeededTables(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                               }
                           } catch (Exception e){

                               if(Main.Hebrew==true){

                                   Dialog dialog = new Dialog(
                                           DialogType.ERROR,
                                           "הדוח פתוח באחד המחשבים",
                                           "לא ניתן לשמור על דוח פתוח");
                                   dialog.setFontSize(22);
                                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                   dialog.showAndWait();

                               }else{


                                   Dialog dialog = new Dialog(
                                           DialogType.ERROR,
                                           "Report is Opened",
                                           "Cannot save on Opened Report ");
                                   dialog.setFontSize(22);
                                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                   dialog.showAndWait();

                               }


                           }





                           GeneratedFile = new File(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52-" + FormNameTxt.getText()+".docx");

                           FormCleaner();

                           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                           if(Main.Hebrew==true){

                               alert.setTitle("הטופס מוכן!");
                               alert.setHeaderText("האם לפתוח את הטופס");
                               alert.setContentText("לחץ OK לפתיחת הטופס");
                           }else{


                               alert.setTitle("The Form was Generated!");
                               alert.setHeaderText("You wish to open the Form?");
                               alert.setContentText("Press OK to open the Form");
                           }



                           Optional<ButtonType> result = alert.showAndWait();
                           if (result.get() == ButtonType.OK) {
                               // ... user chose OK

                               OpenReport();


                           }







                       }





                       else{

                           if(Main.Hebrew==true){
                               Dialog dialog = new Dialog(
                                       DialogType.ERROR,
                                       "חסר שם לטופס!",
                                       "הוסף שם לטופס");
                               dialog.setFontSize(22);
                               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                               dialog.showAndWait();

                           }else{
                               Dialog dialog = new Dialog(
                                       DialogType.ERROR,
                                       "No Form Name!",
                                       "Please add Form Name!");
                               dialog.setFontSize(22);
                               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                               dialog.showAndWait();

                           }

                       }


                   } else {
                       if(Main.Hebrew==true){

                           Dialog dialog = new Dialog(
                                   DialogType.ERROR,
                                   "לא נבחרה שפה!",
                                   "אנא בחר שפה");
                           dialog.setFontSize(22);
                           dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                           dialog.showAndWait();

                       }else{
                           Dialog dialog = new Dialog(
                                   DialogType.ERROR,
                                   "No Language!",
                                   "Please Select Language!");
                           dialog.setFontSize(22);
                           dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                           dialog.showAndWait();
                       }



                   }

               } else {

                   docx = new Docx(Form52File.getPath());
                   docx.setVariablePattern(new VariablePattern("#{", "}"));


                   FillData();

                   if(Main.selection.getStandardData().size()!=0)
                       FillStandardData();




                   docx.fillTemplate(variables);


                   if(!FormNameTxt.getText().isEmpty()){

                       try{

                           if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx")==true)
                               docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                           //  docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                           DeleteNotNeededTables(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                       } catch (Exception e){

                           if(Main.Hebrew==true){
                               Dialog dialog = new Dialog(
                                       DialogType.ERROR,
                                       "הטופס פתוח",
                                       "לא ניתן לשמור על טופס פתוח");
                               dialog.setFontSize(22);
                               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                               dialog.showAndWait();

                           }else{
                               Dialog dialog = new Dialog(
                                       DialogType.ERROR,
                                       "Report is Opened",
                                       "Cannot save on Opened Report ");
                               dialog.setFontSize(22);
                               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                               dialog.showAndWait();

                           }


                       }






                       GeneratedFile = new File(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");

                       FormCleaner();

                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                       if(Main.Hebrew==true){

                           alert.setTitle("הטופס מוכן!");
                           alert.setHeaderText("האם לפתוח את הטופס?");
                           alert.setContentText("לחץ OK כדי לפתוח את הטופס");

                       }else{

                           alert.setTitle("The Form was Generated!");
                           alert.setHeaderText("You wish to open the Form?");
                           alert.setContentText("Press OK to open the Form");
                       }


                       Optional<ButtonType> result = alert.showAndWait();
                       if (result.get() == ButtonType.OK) {
                           // ... user chose OK

                           OpenReport();


                       }


                   }



                   else{


                       if(Main.Hebrew==true){

                           Dialog dialog = new Dialog(
                                   DialogType.ERROR,
                                   "חסר שם טופס",
                                   "אנא הוסף שם טופס!");
                           dialog.setFontSize(22);
                           dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                           dialog.showAndWait();
                       }else{

                           Dialog dialog = new Dialog(
                                   DialogType.ERROR,
                                   "No Form Name!",
                                   "Please add Form Name!");
                           dialog.setFontSize(22);
                           dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                           dialog.showAndWait();

                       }


                   }


               }



           }else {
               if(Main.Hebrew==true){
                   Dialog dialog = new Dialog(
                           DialogType.ERROR,
                           "יחידות המתיחה בתקן הן MPa, חובה לבחור יחידות לבדיקת המתיחה",
                           "בבקשה בחר יחידות לבדיקת המתיחה");
                   dialog.setFontSize(22);
                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                   dialog.showAndWait();

               }else{

                   Dialog dialog = new Dialog(
                           DialogType.ERROR,
                           "Tensile Units in the standard are MPa, Units has to be selected!",
                           "Please selected Tensile Units!");
                   dialog.setFontSize(22);
                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                   dialog.showAndWait();
               }


           }



       }else{


           if(Main.Hebrew==true){
               Dialog dialog = new Dialog(
                       DialogType.ERROR,
                       "יחידות המתיחה בתקן הן MPa, חובה לבחור יחידות לבדיקת המתיחה",
                       "בבקשה בחר יחידות לבדיקת המתיחה");
               dialog.setFontSize(22);
               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

               dialog.showAndWait();

           }else{

               Dialog dialog = new Dialog(
                       DialogType.ERROR,
                       "Tensile Units in the standard are MPa, Units has to be selected!",
                       "Please selected Tensile Units!");
               dialog.setFontSize(22);
               dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

               dialog.showAndWait();


           }




       }








    } else  if(Main.selection.getAllStandardsList()!=null && Main.selection.getAllStandardsList().get(0)!=null &&  CheckTensileUnits(Main.selection.getAllStandardsList().get(0))== 2){
        if(TensileUnitCB.getValue()!=null){
            if(TensileUnitCB.getValue().contains("KSI")){

                if(Form52File==null){
                    if(laungage!=null){
                        if(laungage.equals("English"))
                            docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52-Eng.docx");
                        else if(laungage.equals("Hebrew"))
                            docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52.docx");

                        docx.setVariablePattern(new VariablePattern("#{", "}"));


                        FillData();

                        if(Main.selection.getStandardData().size()!=0)
                            FillStandardData();




                        docx.fillTemplate(variables);


                        if(!FormNameTxt.getText().isEmpty()){


                            try{

                                if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx")==true){
                                    docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                                    //   docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                                    DeleteNotNeededTables(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                                }
                            } catch (Exception e){

                                if(Main.Hebrew==true){

                                    Dialog dialog = new Dialog(
                                            DialogType.ERROR,
                                            "הדוח פתוח באחד המחשבים",
                                            "לא ניתן לשמור על דוח פתוח");
                                    dialog.setFontSize(22);
                                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                    dialog.showAndWait();

                                }else{


                                    Dialog dialog = new Dialog(
                                            DialogType.ERROR,
                                            "Report is Opened",
                                            "Cannot save on Opened Report ");
                                    dialog.setFontSize(22);
                                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                    dialog.showAndWait();

                                }


                            }





                            GeneratedFile = new File(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52-" + FormNameTxt.getText()+".docx");

                            FormCleaner();

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            if(Main.Hebrew==true){

                                alert.setTitle("הטופס מוכן!");
                                alert.setHeaderText("האם לפתוח את הטופס");
                                alert.setContentText("לחץ OK לפתיחת הטופס");
                            }else{


                                alert.setTitle("The Form was Generated!");
                                alert.setHeaderText("You wish to open the Form?");
                                alert.setContentText("Press OK to open the Form");
                            }



                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                // ... user chose OK

                                OpenReport();


                            }







                        }





                        else{

                            if(Main.Hebrew==true){
                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "חסר שם לטופס!",
                                        "הוסף שם לטופס");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();

                            }else{
                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "No Form Name!",
                                        "Please add Form Name!");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();

                            }

                        }


                    } else {
                        if(Main.Hebrew==true){

                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "לא נבחרה שפה!",
                                    "אנא בחר שפה");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();

                        }else{
                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "No Language!",
                                    "Please Select Language!");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();
                        }



                    }

                } else {

                    docx = new Docx(Form52File.getPath());
                    docx.setVariablePattern(new VariablePattern("#{", "}"));


                    FillData();

                    if(Main.selection.getStandardData().size()!=0)
                        FillStandardData();




                    docx.fillTemplate(variables);


                    if(!FormNameTxt.getText().isEmpty()){

                        try{

                            if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx")==true)
                                docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                            //  docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                            DeleteNotNeededTables(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");

                        } catch (Exception e){

                            if(Main.Hebrew==true){
                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "הטופס פתוח",
                                        "לא ניתן לשמור על טופס פתוח");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();

                            }else{
                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "Report is Opened",
                                        "Cannot save on Opened Report ");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();

                            }


                        }



                        GeneratedFile = new File(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");

                        FormCleaner();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        if(Main.Hebrew==true){

                            alert.setTitle("הטופס מוכן!");
                            alert.setHeaderText("האם לפתוח את הטופס?");
                            alert.setContentText("לחץ OK כדי לפתוח את הטופס");

                        }else{

                            alert.setTitle("The Form was Generated!");
                            alert.setHeaderText("You wish to open the Form?");
                            alert.setContentText("Press OK to open the Form");
                        }


                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            // ... user chose OK

                            OpenReport();


                        }


                    }



                    else{


                        if(Main.Hebrew==true){

                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "חסר שם טופס",
                                    "אנא הוסף שם טופס!");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();
                        }else{

                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "No Form Name!",
                                    "Please add Form Name!");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();

                        }


                    }


                }



            }else {
                if(Main.Hebrew==true){
                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "יחידות המתיחה בתקן הן KSI, חובה לבחור יחידות לבדיקת המתיחה",
                            "בבקשה בחר יחידות לבדיקת המתיחה");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();

                }else{

                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "Tensile Units in the standard are KSI, Units has to be selected!",
                            "Please selected Tensile Units!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();
                }


            }



        }else{

            if(Main.Hebrew==true){



                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "יחידות המתיחה בתקן הן KSI, חובה לבחור יחידות לבדיקת המתיחה",
                        "בבקשה בחר יחידות לבדיקת המתיחה");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();



            }else{

                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "Tensile Units in the standard are KSI, Units has to be selected!",
                        "Please selected Tensile Units!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();
            }




        }






    } else  if(Main.selection.getAllStandardsList()!=null && Main.selection.getAllStandardsList().get(0)!=null &&  CheckTensileUnits(Main.selection.getAllStandardsList().get(0))== 3){
        if(TensileUnitCB.getValue()!=null){

            if(TensileUnitCB.getValue().contains("KSI")||TensileUnitCB.getValue().contains("MPa")){

                if(Form52File==null){
                    if(laungage!=null){
                        if(laungage.equals("English"))
                            docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52-Eng.docx");
                        else if(laungage.equals("Hebrew"))
                            docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52.docx");

                        docx.setVariablePattern(new VariablePattern("#{", "}"));


                        FillData();

                        if(Main.selection.getStandardData().size()!=0)
                            FillStandardData();




                        docx.fillTemplate(variables);


                        if(!FormNameTxt.getText().isEmpty()){


                            try{

                                if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx")==true){
                                    docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                                    //   docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                                    DeleteNotNeededTables(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");

                                }
                            } catch (Exception e){

                                if(Main.Hebrew==true){

                                    Dialog dialog = new Dialog(
                                            DialogType.ERROR,
                                            "הדוח פתוח באחד המחשבים",
                                            "לא ניתן לשמור על דוח פתוח");
                                    dialog.setFontSize(22);
                                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                    dialog.showAndWait();

                                }else{


                                    Dialog dialog = new Dialog(
                                            DialogType.ERROR,
                                            "Report is Opened",
                                            "Cannot save on Opened Report ");
                                    dialog.setFontSize(22);
                                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                    dialog.showAndWait();

                                }


                            }





                            GeneratedFile = new File(Paths.get("").toAbsolutePath().toString()+"\\" + "Form52-" + FormNameTxt.getText()+".docx");

                            FormCleaner();

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            if(Main.Hebrew==true){

                                alert.setTitle("הטופס מוכן!");
                                alert.setHeaderText("האם לפתוח את הטופס");
                                alert.setContentText("לחץ OK לפתיחת הטופס");
                            }else{


                                alert.setTitle("The Form was Generated!");
                                alert.setHeaderText("You wish to open the Form?");
                                alert.setContentText("Press OK to open the Form");
                            }



                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                // ... user chose OK

                                OpenReport();


                            }







                        }





                        else{

                            if(Main.Hebrew==true){
                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "חסר שם לטופס!",
                                        "הוסף שם לטופס");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();

                            }else{
                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "No Form Name!",
                                        "Please add Form Name!");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();

                            }

                        }


                    } else {
                        if(Main.Hebrew==true){

                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "לא נבחרה שפה!",
                                    "אנא בחר שפה");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();

                        }else{
                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "No Language!",
                                    "Please Select Language!");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();
                        }



                    }

                } else {

                    docx = new Docx(Form52File.getPath());
                    docx.setVariablePattern(new VariablePattern("#{", "}"));


                    FillData();

                    if(Main.selection.getStandardData().size()!=0)
                        FillStandardData();




                    docx.fillTemplate(variables);


                    if(!FormNameTxt.getText().isEmpty()){

                        try{

                            if(OverwriteFile(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx")==true)
                                docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                            //  docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                            DeleteNotNeededTables(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");

                        } catch (Exception e){

                            if(Main.Hebrew==true){
                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "הטופס פתוח",
                                        "לא ניתן לשמור על טופס פתוח");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();

                            }else{
                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "Report is Opened",
                                        "Cannot save on Opened Report ");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();

                            }


                        }



                        GeneratedFile = new File(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");

                        FormCleaner();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        if(Main.Hebrew==true){

                            alert.setTitle("הטופס מוכן!");
                            alert.setHeaderText("האם לפתוח את הטופס?");
                            alert.setContentText("לחץ OK כדי לפתוח את הטופס");

                        }else{

                            alert.setTitle("The Form was Generated!");
                            alert.setHeaderText("You wish to open the Form?");
                            alert.setContentText("Press OK to open the Form");
                        }


                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            // ... user chose OK

                            OpenReport();


                        }


                    }



                    else{


                        if(Main.Hebrew==true){

                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "חסר שם טופס",
                                    "אנא הוסף שם טופס!");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();
                        }else{

                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "No Form Name!",
                                    "Please add Form Name!");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();

                        }


                    }


                }




            } else {

                if(Main.Hebrew==true){
                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "יחידות המתיחה בתקן KSI וMPa קיימות בתקן, חובה לבחור יחידות לבדיקת המתיחה",
                            "בבקשה בחר יחידות לבדיקת המתיחה");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();

                }else{

                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "Tensile Units of MPa and KSI exist in the standard, Units has to be selected!",
                            "Please selected Tensile Units!");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();
                }

            }





        }else{

            if(Main.Hebrew==true){
                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "יחידות המתיחה בתקן הן KSI ו-MPa , חובה לבחור יחידות לבדיקת המתיחה",
                        "בבקשה בחר יחידות לבדיקת המתיחה");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();

            }else{

                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "Tensile Units of MPa and KSI exist in the standard, Units has to be selected!",
                        "Please selected Tensile Units!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();


            }




        }







    }




}








    }

    public boolean OverwriteFile(String file){

        NoStateDB = false;

        File f = new File(file);

        if(f.exists()){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            if(Main.Hebrew==true){
                alert.setTitle("שמירה על קובץ קיים");
                alert.setHeaderText("טופס עם שם זהה כבר קיים");
                alert.setContentText("האם לשמור במקום הטופס הקיים?");

            }else{

                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("State already exist");
                alert.setContentText("Do you want to overwrite this State?");

            }


            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                NoStateDB = true;
                return true;

            } else {

                return false;
            }
        }

        return true;
    }





@FXML
public void UnselectTemplate(){

    Form52File = null;

}

@FXML
public void Refresh(){





    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    if(Main.Hebrew==true){

        alert.setTitle("לאפס נתונים");
        alert.setHeaderText("האם לאפס את הנתונים?");
        alert.setContentText("לחץ OK כדי לאפס את הנתונים");
    }else{


        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Clear all data?");
        alert.setContentText("Do you want to clear all data?");
    }


    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        // ... user chose OK

        Form52File = null;

        MultiSample.setSelected(false);
        if(lgGroup.getSelectedToggle()!=null)
        lgGroup.getSelectedToggle().setSelected(false);
        AutoCB.setValue(null);
        JobCB.setValue(null);
        ReturnBox.setSelected(false);
        Abox.setSelected(false);
        RemCoatBox.setSelected(false);
        AppendixBox.setSelected(false);
        UltraCleanBox.setSelected(false);
        AlcoCleanBox.setSelected(false);
        MarkBox.setSelected(false);

        HeaderTxt.setText("");
        CustomThicknessTxt.setText("");
        TensileSpecimenTxt.setText("");
        ChemicalSpecimenTxt.setText("");
        HardnessSpecimenTxt.setText("");
        MicroHSpecimenTxt.setText("");
        ImpactSpecimenTxt.setText("");
        BendThickness.setText("");
        BendSpecimenTxt.setText("");
        FormNameTxt.setText("");

        TensileCB.setSelected(false);
        ImpactCB.setSelected(false);
        HardnessCB.setSelected(false);
        MicroCB.setSelected(false);
        MetaCB.setSelected(false);
        ChemCB.setSelected(false);
        BendCB.setSelected(false);

        Form52Dater.setValue(null);

        TensileStdCB.setValue(null);

        TensileTypeCB.setValue(null);

        TensileUnitCB.setValue(null);

        TensileThicknessCB.setValue(null);

        TensileDirectionCB.setValue(null);

         TensileDirectionCB.setValue(null);

         ChemicalTypeCB.setValue(null);

        ChemicalPlaceCB.setValue(null);

     MetaTypeCB.setValue(null);

         MetaStdCB.setValue(null);

         MetaModelCB.setValue(null);

       PolishCB.setValue(null);

        HardMethodCB.setValue(null);

         MeteDirectionCB.setValue(null);

        HardLoadCB.setValue(null);

         MicroHMethodCB.setValue(null);

         MicroHLoadCB.setValue(null);

         MicroHTypeCB.setValue(null);

         ImpactStdCB.setValue(null);

       ImpactSizeCB.setValue(null);

         ImpactPlaceCB.setValue(null);

         ImpactTempCB.setValue(null);

         BendTypeCB.setValue(null);

         BendAngleCB.setValue(null);

         BendStdCB.setValue(null);


        PopulateTable(StateTbl,StateSelClm,StateGroup,0);

        FilterSupport.getItems(HeaderTbl).clear();
        InstlizeHeaders();
        DetailTbl.getItems().clear();
        DetailTbl.getColumns().remove(1,DetailTbl.getColumns().size());
        DetailTblObj = null;


    }






}

int CheckTensileUnits(ArrayList<ParamObject> T){

        boolean hasKSI = false;

        boolean hasMPa = false;

        if(T.size()==0 || T == null){

            return 0;

        } else{

            for(ParamObject o:T){

                if(o.getParamName().contains("MPa")){


                    hasMPa = true;

                }

                if(o.getParamName().contains("KSI")){


                    hasKSI = true;

                }


            }

            if(hasKSI == true && hasMPa == true){

                return 3;
            } else if(hasKSI == true && hasMPa==false){

                return 2;
            } else if(hasKSI == false && hasMPa == true){

                return 1;
            } else if(hasKSI == false && hasMPa == false){

                return 0;
            }


        }



       return 0 ;


}


public void FillStandardData(){


int i=1;
int STDcounter = 0;
ParamObject RestObject = new ParamObject();

    ArrayList<ParamObject> ChemicalsOnly = new ArrayList<>();


boolean AlreadyDidRest = false;
    boolean AlreadyDidEnd = false;


    for(ArrayList<ParamObject> p: Main.selection.getAllStandardsList()){
        STDcounter++;
        AlreadyDidRest = false;
        AlreadyDidEnd = false;
        ChemicalsOnly.clear();

        ArrayList<ParamObject> copyList = new ArrayList<>(p);

        for(ParamObject j: new ArrayList<ParamObject>(copyList)){

            if(j.getMax().contains("Rest")&&j.getMin().contains("Rest")){

                RestObject = j;

                copyList.remove(j);


            }


        }

        for(ParamObject j: new ArrayList<ParamObject>(copyList)){

            if(j.getTestName().contains("Chemical")){

                ChemicalsOnly.add(j);
                copyList.remove(j);

            }


        }



        for(ParamObject j: ChemicalsOnly){

            if(j.getTestName().equals("Chemical"))
            {
                variables.addTextVariable(new TextVariable("#{el"+i+"}", j.getParamName()));
                variables.addTextVariable(new TextVariable("#{" + "sdmin-" + STDcounter +"-"+ i + "}", j.getMin()));
                variables.addTextVariable(new TextVariable("#{" + "sdmax-" + STDcounter +"-"+ i + "}", j.getMax()));

                i++;
            }


        }

            if(RestObject!=null)
            {
                if(AlreadyDidRest==false){

                        variables.addTextVariable(new TextVariable("#{el"+i+"}", RestObject.getParamName()));

                        variables.addTextVariable(new TextVariable("#{sdmin-"+STDcounter+"-"+i+"}", "Rest"));
                        variables.addTextVariable(new TextVariable("#{sdmax-"+STDcounter+"-"+i+"}","Rest"));

                    i++;
                        AlreadyDidRest =true;





            }

            if(AlreadyDidRest==true){


                variables.addTextVariable(new TextVariable("#{el"+i+"}", "End"));

                variables.addTextVariable(new TextVariable("#{sdmin-"+STDcounter+"-"+ i+"}", "End"));
                variables.addTextVariable(new TextVariable("#{sdmax"+STDcounter + "-" + i+ "}","End"));
                i++;
            }


        }







            if (StandardSelectController.StandardSelected) {


                for (String M : Main.selection.getStandards()) {
                    variables.addTextVariable(new TextVariable("#{Std-" + STDcounter + "}", M));
                }


                for (String M : Main.selection.getMaterials()) {

                    variables.addTextVariable(new TextVariable("#{Mat-" + STDcounter + "}", M));


                }


            }


        for(ParamObject j: p) {

            if (j.getTestName().equals("Tensile")) {


                if (TensileUnitCB.getValue().equals("MPa")) {

                    if (j.getParamName().equals("UTS(MPa)")) {
                          if(!j.getMax().equals("-"))
                        variables.addTextVariable(new TextVariable("#{UTSMax-" + STDcounter + "}", j.getMax() + " Max"));
                          else
                              variables.addTextVariable(new TextVariable("#{UTSMax-" + STDcounter + "}", j.getMax()));

                        if(!j.getMin().equals("-"))
                            variables.addTextVariable(new TextVariable("#{UTSMin-" + STDcounter + "}", j.getMin() + " Min"));
                        else
                            variables.addTextVariable(new TextVariable("#{UTSMin-" + STDcounter + "}", j.getMin()));



                    }

                    if (j.getParamName().equals("Yield Point(MPa)")) {

                        if(!j.getMax().equals("-"))
                            variables.addTextVariable(new TextVariable("#{YPMax-" + STDcounter + "}", j.getMax() + " Max"));
                        else
                            variables.addTextVariable(new TextVariable("#{YPMax-" + STDcounter + "}", j.getMax()));

                        if(!j.getMin().equals("-"))
                            variables.addTextVariable(new TextVariable("#{YPMin-" + STDcounter + "}", j.getMin() + " Min"));
                        else
                            variables.addTextVariable(new TextVariable("#{YPMin-" + STDcounter + "}", j.getMin()));





                    }


                } else if (TensileUnitCB.getValue().equals("KSI")) {

                    if (j.getParamName().equals("UTS(KSI)")) {


                        if(!j.getMax().equals("-"))
                            variables.addTextVariable(new TextVariable("#{UTSMax-" + STDcounter + "}", j.getMax() + " Max"));
                        else
                            variables.addTextVariable(new TextVariable("#{UTSMax-" + STDcounter + "}", j.getMax()));

                        if(!j.getMin().equals("-"))
                            variables.addTextVariable(new TextVariable("#{UTSMin-" + STDcounter + "}", j.getMin() + " Min"));
                        else
                            variables.addTextVariable(new TextVariable("#{UTSMin-" + STDcounter + "}", j.getMin()));






                    }

                    if (j.getParamName().equals("Yield Point(KSI)")) {


                        if(!j.getMax().equals("-"))
                            variables.addTextVariable(new TextVariable("#{YPMax-" + STDcounter + "}", j.getMax() + " Max"));
                        else
                            variables.addTextVariable(new TextVariable("#{YPMax-" + STDcounter + "}", j.getMax()));

                        if(!j.getMin().equals("-"))
                            variables.addTextVariable(new TextVariable("#{YPMin-" + STDcounter + "}", j.getMin() + " Min"));
                        else
                            variables.addTextVariable(new TextVariable("#{YPMin-" + STDcounter + "}", j.getMin()));






                    }


                } else if (TensileUnitCB.getValue().equals("Kg/mm^2")) {

                    if (j.getParamName().equals("UTS(Kg/mm^2)")) {



                        variables.addTextVariable(new TextVariable("#{UTSMax}", j.getMax()));
                        variables.addTextVariable(new TextVariable("#{UTSMin}", j.getMin()));


                    }

                    if (j.getParamName().equals("Yield Point(Kg/mm^2)")) {

                        variables.addTextVariable(new TextVariable("#{YPMax-" + STDcounter + "}", j.getMax()));
                        variables.addTextVariable(new TextVariable("#{YPMax-" + STDcounter + "}", j.getMin()));


                    }


                }


                if (j.getParamName().equals("% Elongation")) {


                    if(!j.getMax().equals("-"))
                        variables.addTextVariable(new TextVariable("#{ELMax-" + STDcounter + "}", j.getMax() + " Max"));
                    else
                        variables.addTextVariable(new TextVariable("#{ELMax-" + STDcounter + "}", j.getMax()));

                    if(!j.getMin().equals("-"))
                        variables.addTextVariable(new TextVariable("#{ELMin-" + STDcounter + "}", j.getMin() + " Min"));
                    else
                        variables.addTextVariable(new TextVariable("#{ELMin-" + STDcounter + "}", j.getMin()));




                }

                if (j.getParamName().equals("% Area Reduction")) {



                    if(!j.getMax().equals("-"))
                        variables.addTextVariable(new TextVariable("#{AreaMax-" + STDcounter + "}", j.getMin() + " Max"));
                    else
                        variables.addTextVariable(new TextVariable("#{AreaMax-" + STDcounter + "}", j.getMin() ));

                    if(!j.getMin().equals("-"))
                        variables.addTextVariable(new TextVariable("#{AreaMin-" + STDcounter + "}", j.getMin() + " Min"));
                    else
                        variables.addTextVariable(new TextVariable("#{AreaMin-" + STDcounter + "}", j.getMin()));





                }


                if (j.getParamName().equals("UTS(MPa)")) {

                    if(!j.getMax().equals("-"))
                        variables.addTextVariable(new TextVariable("#{UTSMPAMax-" + STDcounter + "}", j.getMax() + " Max"));
                    else
                        variables.addTextVariable(new TextVariable("#{UTSMPAMax-" + STDcounter + "}", j.getMax() ));

                    if(!j.getMin().equals("-"))
                        variables.addTextVariable(new TextVariable("#{UTSMPAMin-" + STDcounter + "}", j.getMin() + " Min"));
                    else
                        variables.addTextVariable(new TextVariable("#{UTSMPAMin-" + STDcounter + "}", j.getMin() ));






                }


                if (j.getParamName().equals("Yield Point(MPa)")) {


                    if(!j.getMax().equals("-"))
                        variables.addTextVariable(new TextVariable("#{YPMPAMax-" + STDcounter + "}", j.getMax() + " Max"));
                    else
                        variables.addTextVariable(new TextVariable("#{YPMPAMax-" + STDcounter + "}", j.getMax()));

                    if(!j.getMin().equals("-"))
                        variables.addTextVariable(new TextVariable("#{YPMPAMin-" + STDcounter + "}", j.getMin() + " Min"));
                    else
                        variables.addTextVariable(new TextVariable("#{YPMPAMin-" + STDcounter + "}", j.getMin()));





                }

                if (j.getParamName().equals("UTS(KSI)")) {

                    if(!j.getMax().equals("-"))
                        variables.addTextVariable(new TextVariable("#{UTSKSIMax-" + STDcounter + "}", j.getMax() + " Max"));
                    else
                        variables.addTextVariable(new TextVariable("#{UTSKSIMax-" + STDcounter + "}", j.getMax() ));

                    if(!j.getMin().equals("-"))
                        variables.addTextVariable(new TextVariable("#{UTSKSIMin-" + STDcounter + "}", j.getMin() + " Min"));
                    else
                        variables.addTextVariable(new TextVariable("#{UTSKSIMin-" + STDcounter + "}", j.getMin() ));






                }

                if (j.getParamName().equals("Yield Point(KSI)")) {

                    if(!j.getMax().equals("-"))
                        variables.addTextVariable(new TextVariable("#{YPKSIMax-" + STDcounter + "}", j.getMax() + " Max"));
                    else
                        variables.addTextVariable(new TextVariable("#{YPKSIMax-" + STDcounter + "}", j.getMax()));

                    if(!j.getMin().equals("-"))
                        variables.addTextVariable(new TextVariable("#{YPKSIMin-" + STDcounter + "}", j.getMin() + " Min"));
                    else
                        variables.addTextVariable(new TextVariable("#{YPKSIMin-" + STDcounter + "}", j.getMin()));







                }


            }


        }


}






}


  public void FillData(){


      Date date = Calendar.getInstance().getTime();

      // Display a date in day, month, year format
      DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      String today = formatter.format(date);


      variables.addTextVariable(new TextVariable("#{Dt}", today));

      variables.addTextVariable(new TextVariable("#{FormN}", FormNameTxt.getText()));


       if(Main.selection.getStandardName()!=null)
      variables.addTextVariable(new TextVariable("#{Std}", Main.selection.getStandardName()));
         else {

           variables.addTextVariable(new TextVariable("#{Std}", ""));

       }
      if(Main.selection.getCompanyName()!=null)
          variables.addTextVariable(new TextVariable("#{ClientN}", Main.selection.getCompanyName()));
      else {

          variables.addTextVariable(new TextVariable("#{ClientN}", ""));

      }

      if(Main.selection.getContactName()!=null)
          variables.addTextVariable(new TextVariable("#{ContN}", Main.selection.getContactName()));
      else {

          variables.addTextVariable(new TextVariable("#{ContN}", ""));

      }



      FillTensile();
      FIllChemical();
      FIllHardness();
      FIllMicroH();
      FIllMetallography();
      FillImpact();
      FillBend();



       int Clms =  DetailTbl.getColumns().size();

       int Rows =  DetailTbl.getItems().size();


       m = DetailTbl.getItems();


                //Here is how i get to the Cell Value
                 //  m.get(0).getColumns().get(0).getValue();


      //Filling the headers

      if(DetailTblObj!=null){



                      for(int i=0;i<Clms;i++){


                          if(!DetailTbl.getColumns().get(i).getText().equals("")){



                                if(DetailTblObj!=null)
                                variables.addTextVariable(new TextVariable("#{HeadPr-"+ 0 + "-" + i +"}", DetailTbl.getColumns().get(i).getText()));

                                variables.addTextVariable(new TextVariable("#{HeadDt-" + 0 + "-" + i +"}", DetailTblObj.getColumns().get(i-1).getValue()));







                          }







          }





      }else{


          if(MultiSample.isSelected()){

              for(int j=0;j<Rows;j++){

                  for(int i=0;i<Clms;i++){


                      if(!DetailTbl.getColumns().get(i).getText().equals("")){



                          variables.addTextVariable(new TextVariable("#{HeadPr-"+ j + "-" + i +"}", DetailTbl.getColumns().get(i).getText()));

                          variables.addTextVariable(new TextVariable("#{HeadDt-" + j + "-" + i +"}", m.get(j).getColumns().get(i-1).getValue()));



                      }

                  }





              }
          }




      }






      LocalDate oDate = Form52Dater.getValue();

       if(oDate!=null){

           DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

           String szDate = oDate.format(formatter1);


           variables.addTextVariable(new TextVariable("#{DelDate}", szDate));
       } else
           variables.addTextVariable(new TextVariable("#{DelDate}", ""));





      if(ReturnBox.isSelected()){

          variables.addTextVariable(new TextVariable("#{ReturnSp}", "להחזיר שאריות ללקוח!"));

      }else{

          variables.addTextVariable(new TextVariable("#{ReturnSp}", ""));

      }


      if(AutoCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{Auto}", ""));


      }else{

          variables.addTextVariable(new TextVariable("#{Auto}", AutoCB.getValue()));


      }


      if(JobCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{Job}", ""));


      }else{

          variables.addTextVariable(new TextVariable("#{Job}", JobCB.getValue()));


      }


  }

    public void FillBend(){



        if(BendTypeCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{BendType}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{BendType}", BendTypeCB.getValue()));


        }
        if(BendAngleCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{BendAngle}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{BendAngle}", BendAngleCB.getValue()));


        }

        if(BendStdCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{BendStd}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{BendStd}", BendStdCB.getValue()));
        }


      if(!BendThickness.getText().equals("")){

          if(Numeric(BendThickness.getText())){

              double BendDist;
              double BendDiam;

              BendDiam = Double.parseDouble(BendThickness.getText()) * 4;
              BendDist = Math.round(Double.parseDouble(BendThickness.getText()) * 6 +3.2);

              variables.addTextVariable(new TextVariable("#{BendDist}", String.valueOf(BendDist)));

              variables.addTextVariable(new TextVariable("#{BendDiam}", String.valueOf(BendDiam)));

          }


      }

        variables.addTextVariable(new TextVariable("#{BendAmount}", BendSpecimenTxt.getText()));



    }

    public  boolean Numeric(String string){

        boolean numeric=true;

        try {
            Double num = Double.parseDouble(string);

        } catch (NumberFormatException e) {
            numeric = false;
        }

        if(numeric)
            return true;
        else
            return false;
    }

    public void FillImpact(){


        if(ImpactStdCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{ImpactStd}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{ImpactStd}", ImpactStdCB.getValue()));


        }
        if(ImpactSizeCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{ImpactSize}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{ImpactSize}", ImpactSizeCB.getValue()));


        }
        if(ImpactPlaceCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{ImpactLoc}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{ImpactLoc}", ImpactPlaceCB.getValue()));
        }

        if(ImpactTempCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{ImpactTemp}", ""));


        }else{

            String temp = ImpactTempCB.getValue().replace("-","");
            temp = temp + "-";

            variables.addTextVariable(new TextVariable("#{ImpactTemp}", temp));
        }


        variables.addTextVariable(new TextVariable("#{ImpactAmount}", ImpactSpecimenTxt.getText()));


    }

    public void FIllMetallography(){

        if(MetaTypeCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{MetaType}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{MetaType}", MetaTypeCB.getValue()));


        }
        if(MetaStdCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{MetaStd}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{MetaStd}", MetaStdCB.getValue()));


        }
        if(MeteDirectionCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{MetaDirec}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{MetaDirec}", MeteDirectionCB.getValue()));
        }

        if(MetaModelCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{MetaModel}", ""));

        }else{

            variables.addTextVariable(new TextVariable("#{MetaModel}", MetaModelCB.getValue()));


        }
        if(PolishCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{Polish}", ""));

        }else{

            variables.addTextVariable(new TextVariable("#{Polish}", PolishCB.getValue()));

        }


        if(UltraCleanBox.isSelected()){

            variables.addTextVariable(new TextVariable("#{UltaClean}", "לנקות באמבט אולטרסוני!"));

        }else{

            variables.addTextVariable(new TextVariable("#{UltaClean}", ""));

        }

        if(MarkBox.isSelected()){

            variables.addTextVariable(new TextVariable("#{Mark}", "לסמן פני שטח!"));

        }else{

            variables.addTextVariable(new TextVariable("#{Mark}", ""));

        }


        if(AlcoCleanBox.isSelected()){

            variables.addTextVariable(new TextVariable("#{AlcoClean}", "לנקות באלכוהול!"));

        }else{

            variables.addTextVariable(new TextVariable("#{AlcoClean}", ""));

        }






    }



    public void FIllMicroH(){


        if(MicroHMethodCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{MicroHMetod}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{MicroHMetod}", MicroHMethodCB.getValue()));

        }

        if(MicroHLoadCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{MicroHLoad}", ""));

        }else{

            variables.addTextVariable(new TextVariable("#{MicroHLoad}", MicroHLoadCB.getValue()));


        }


        if(MicroHTypeCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{MicroHType}", ""));

        }else{

            variables.addTextVariable(new TextVariable("#{MicroHType}", MicroHTypeCB.getValue()));


        }

        variables.addTextVariable(new TextVariable("#{MicroAmount}", MicroHSpecimenTxt.getText()));





    }




    public void FIllHardness(){

        if(HardMethodCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{HardMetod}", ""));


        }else{

            variables.addTextVariable(new TextVariable("#{HardMetod}", HardMethodCB.getValue()));

        }

        if(HardLoadCB.getValue()== null){

            variables.addTextVariable(new TextVariable("#{HardLoad}", ""));

        }else{

            variables.addTextVariable(new TextVariable("#{HardLoad}", HardLoadCB.getValue()));


        }

        variables.addTextVariable(new TextVariable("#{HardAmount}", HardnessSpecimenTxt.getText()));


    }


  public void FIllChemical(){

      if(ChemicalTypeCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{ChemType}", ""));


      }else{

          variables.addTextVariable(new TextVariable("#{ChemType}", ChemicalTypeCB.getValue()));


      }


      if(ChemicalPlaceCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{ChemLoc}", ""));


      }else{

          variables.addTextVariable(new TextVariable("#{ChemLoc}", ChemicalPlaceCB.getValue()));


      }


      if(RemCoatBox.isSelected()){

          variables.addTextVariable(new TextVariable("#{Coat}", "להוריד ציפוי!"));

      }else{

          variables.addTextVariable(new TextVariable("#{Coat}", ""));

      }

      if(AppendixBox.isSelected()){

          variables.addTextVariable(new TextVariable("#{App}", "פירוט בנספחים"));

      }else{

          variables.addTextVariable(new TextVariable("#{App}", ""));

      }


      variables.addTextVariable(new TextVariable("#{ChemAmount}", ChemicalSpecimenTxt.getText()));




  }



  public void FillTensile(){

      if(TensileTypeCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{TenType}", ""));


      }else{

          variables.addTextVariable(new TextVariable("#{TenType}", TensileTypeCB.getValue()));


      }
      if(TensileUnitCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{TenUnit}", ""));


      }else{

          variables.addTextVariable(new TextVariable("#{TenUnit}", TensileUnitCB.getValue()));


      }
      if(TensileThicknessCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{TenThickness}", ""));


      }else{

          variables.addTextVariable(new TextVariable("#{TenThickness}", TensileThicknessCB.getValue()));
      }

      if(TensileStdCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{TenStd}", ""));

      }else{

          variables.addTextVariable(new TextVariable("#{TenStd}", TensileStdCB.getValue()));


      }
      if(TensileDirectionCB.getValue()== null){

          variables.addTextVariable(new TextVariable("#{TenDirect}", ""));

      }else{

          variables.addTextVariable(new TextVariable("#{TenDirect}", TensileDirectionCB.getValue()));

      }


      if(Abox.isSelected()){

          variables.addTextVariable(new TextVariable("#{AeroSel}", "לתעופה!"));

      }else{

          variables.addTextVariable(new TextVariable("#{AeroSel}", "לא לתעופה!"));

      }


      variables.addTextVariable(new TextVariable("#{TenAmount}", TensileSpecimenTxt.getText()));


  }


    public void setKeyEvents(){

        StateDB db = new StateDB();
        StateTbl.setOnKeyReleased((KeyEvent t)-> {
            KeyCode key=t.getCode();
            if (key==KeyCode.DELETE){
                StateObj user = (StateObj) StateTbl.getSelectionModel().selectedItemProperty().get();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(Main.Hebrew==true){

                    alert.setTitle("למחוק מצב");
                    alert.setHeaderText("האם למחוק את המצב?");
                    alert.setContentText("לחץ OK כדי למחוק את המצב");
                }else{


                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Delete State");
                    alert.setContentText("Do you want to delete this State?");
                }


                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK



                   db.DeleteState(user.getId());
                   PopulateTable(StateTbl,StateSelClm,StateGroup,0);

                    File file = new File(user.getStatePath());

                    if(file.delete())
                    {

                        if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                                DialogType.INFORMATION,
                                "המצב נמחק",
                                "המצב נמחק");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                            dialog.showAndWait();}else{

                            Dialog dialog = new Dialog(
                                    DialogType.INFORMATION,
                                    "Stage was deleted",
                                    "Stage was deleted");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                            dialog.showAndWait();

                        }


                    }
                    else {

                        if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                                DialogType.INFORMATION,
                                "המצב נמחק",
                                "המצב נמחק");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                            dialog.showAndWait();}else{

                            Dialog dialog = new Dialog(
                                    DialogType.INFORMATION,
                                    "State was deleted",
                                    "State was deleted");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                            dialog.showAndWait();

                        }
                    }



                }

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }); }



        public void FormCleaner(){


            Docx docx = new Docx(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");

            docx.setVariablePattern(new VariablePattern("#{", "}"));
            Variables CleanVb = new Variables();
            int StandardCounter = 0;


           for(ArrayList<ParamObject> p : Main.selection.getAllStandardsList()){

               StandardCounter ++;

               CleanVb.addTextVariable(new TextVariable("#{UTSMax-" + StandardCounter +"}", ""));
               CleanVb.addTextVariable(new TextVariable("#{UTSMin-" + StandardCounter +"}", ""));


               CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}", ""));
               CleanVb.addTextVariable(new TextVariable("#{YPMin-" + StandardCounter +"}", ""));




               CleanVb.addTextVariable(new TextVariable("#{UTSMax-" + StandardCounter +"}", ""));
               CleanVb.addTextVariable(new TextVariable("#{UTSMin-" + StandardCounter +"}", ""));



               CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}",""));
               CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}", ""));







               CleanVb.addTextVariable(new TextVariable("#{UTSMax-" + StandardCounter +"}", ""));
               CleanVb.addTextVariable(new TextVariable("#{UTSMin-" + StandardCounter +"}", ""));




               CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}", ""));
               CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}", ""));





               CleanVb.addTextVariable(new TextVariable("#{ELMin-" + StandardCounter +"}",""));


               CleanVb.addTextVariable(new TextVariable("#{AreaMin-" + StandardCounter +"}", ""));



           }

            if(Main.selection.getAllStandardsList().isEmpty()){

                StandardCounter ++;

                CleanVb.addTextVariable(new TextVariable("#{UTSMax-" + StandardCounter +"}", ""));
                CleanVb.addTextVariable(new TextVariable("#{UTSMin-" + StandardCounter +"}", ""));


                CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}", ""));
                CleanVb.addTextVariable(new TextVariable("#{YPMin-" + StandardCounter +"}", ""));




                CleanVb.addTextVariable(new TextVariable("#{UTSMax-" + StandardCounter +"}", ""));
                CleanVb.addTextVariable(new TextVariable("#{UTSMin-" + StandardCounter +"}", ""));



                CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}",""));
                CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}", ""));







                CleanVb.addTextVariable(new TextVariable("#{UTSMax-" + StandardCounter +"}", ""));
                CleanVb.addTextVariable(new TextVariable("#{UTSMin-" + StandardCounter +"}", ""));




                CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}", ""));
                CleanVb.addTextVariable(new TextVariable("#{YPMax-" + StandardCounter +"}", ""));





                CleanVb.addTextVariable(new TextVariable("#{ELMin-" + StandardCounter +"}",""));


                CleanVb.addTextVariable(new TextVariable("#{AreaMin-" + StandardCounter +"}", ""));






            }

            for(int j=0;j<40;j++){

                for(int i=0;i<40;i++){


                    CleanVb.addTextVariable(new TextVariable("#{HeadPr-"+ j + "-" + i +"}", ""));

                    CleanVb.addTextVariable(new TextVariable("#{HeadDt-" + j + "-" + i +"}", ""));


                    CleanVb.addTextVariable(new TextVariable("#{el-"+StandardCounter + "-" + i+"}",""));
                    CleanVb.addTextVariable(new TextVariable("#{sdmin-"+StandardCounter + "-" + i+"}", ""));
                    CleanVb.addTextVariable(new TextVariable("#{sdmax-"+StandardCounter + "-" + i+"}", ""));


                }





            }

            docx.fillTemplate(CleanVb);


            try{


                    docx.save(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");
                    DeleteNotNeededTables(Paths.get("").toAbsolutePath().toString()+"\\IKADOCS_Form52" +"\\" + "Form52-"+FormNameTxt.getText()+".docx");


            } catch (Exception e){

               if(Main.Hebrew==true){

                   Dialog dialog = new Dialog(
                           DialogType.ERROR,
                           "הטופס פתוח",
                           "לא ניתן לשמור על טופס פתוח");
                   dialog.setFontSize(22);
                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                   dialog.showAndWait();

               }else{

                   Dialog dialog = new Dialog(
                           DialogType.ERROR,
                           "The Form is Opened",
                           "Cannot save on Opened Report ");
                   dialog.setFontSize(22);
                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                   dialog.showAndWait();


               }


            }





        }



        @FXML
        public void OpenForm52Template(){

        Stage stage = new Stage();

        //    Preferences userPrefs = Preferences.userNodeForPackage(Main.class);

      //      String lastVisitedDirectory=userPrefs.get("Form52 Path","");

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Docx");
            fileChooser.setInitialDirectory(new File(Paths.get("").toAbsolutePath()+"\\Form52 Template"));



            List<File> files = fileChooser.showOpenMultipleDialog(stage);
            if(files!=null && !files.isEmpty())
            Form52File = files.get(0);



        }

    private static int getBodyElementOfTable( XWPFDocument document, int tableNumberInDocument ) {
        List<XWPFTable> tables = document.getTables();
        XWPFTable theTable = tables.get( tableNumberInDocument );

        return document.getPosOfTable( theTable );
    }

        public void DeleteNotNeededTables(String file) throws IOException {

        int tableIndexCounter = 0;
        boolean flag;
            int bodyElement=-1;
            FileInputStream in = new FileInputStream( new File( file ) );
            XWPFDocument document = new XWPFDocument( in );

            List<XWPFTable> tables = document.getTables();

            if(!TensileCB.isSelected()){
                deleteTable(document,tables,"מתיחה");
            }

            if(!ChemCB.isSelected()){

                deleteTable(document,tables,"כימי");
                deleteTable(document,tables,"Element");
            }


            if(!ImpactCB.isSelected()){

                deleteTable(document,tables,"נגיפה");
            }


            if(!MetaCB.isSelected()){
                deleteTable(document,tables,"מטלוגרפיה");
            }



            if(!BendCB.isSelected()){
                deleteTable(document,tables,"כפיפה");
            }

            if(!HardnessCB.isSelected()){
                deleteTable(document,tables,"קושיות");
            }

            if(!MicroCB.isSelected()){
                deleteTable(document,tables,"עומס");}

             saveDoc( document,file);

        }

    private static void saveDoc( XWPFDocument document, String filename ) {
        try {
            FileOutputStream out = new FileOutputStream( new File( filename ) );
            document.write( out );
            out.close();
        } catch ( FileNotFoundException e ) {
            System.out.println( e.getMessage() );
        } catch ( IOException e ) {
            System.out.println( "IOException while saving to " + filename + ":\n" + e.getMessage() );
        }
    }
    private static void deleteTable( XWPFDocument document,List<XWPFTable> tables, String Word ) {

        int bodyElement=-1;
        int tableIndex = 0;

        outerloop:
        for ( XWPFTable table : new ArrayList<XWPFTable>(tables) ) {
            bodyElement=-1;
            for (int rowIndex = 0; rowIndex < table.getNumberOfRows(); rowIndex++) {

                XWPFTableRow row = table.getRow(rowIndex);
                for (int colIndex = 0; colIndex < row.getTableCells().size()-1; colIndex++) {
                    if(table.getRow(rowIndex).getCell(colIndex).getText().contains(Word)){

                       if(tableIndex!=1)
                        break outerloop;

                    }
                }

            }
             tableIndex++;
        }

        deleteOneTable(document,tableIndex);
    }


    private static void deleteOneTable( XWPFDocument document, int tableIndex ) {
        try {
            int bodyElement = getBodyElementOfTable( document, tableIndex );
            System.out.println( "deleting table with bodyElement #" + bodyElement );
            document.removeBodyElement( bodyElement );
        } catch ( Exception e ) {
            System.out.println( "There is no table #" + tableIndex + " in the document." );
        }
    }


}












