package sample.Controllers;


import com.github.daytron.simpledialogfx.data.DialogResponse;
import com.github.daytron.simpledialogfx.data.HeaderColorStyle;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import sample.Database.HeaderDB;
import sample.Database.StateDB;
import sample.Database.UserDB;
import sample.EditCell;
import sample.Filter.FilterSupport;
import sample.Main;
import sample.OtherPojos.CSVRow;
import sample.OtherPojos.HeaderObj;
import sample.OtherPojos.StateObj;
import sample.OtherPojos.TableUser;


import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.util.Callback;
import sample.Database.MyUser;


public class UserController implements Initializable {



    UserDB db = new UserDB();

    final ToggleGroup ApproveGroup = new ToggleGroup();
    final ToggleGroup PrepareGroup = new ToggleGroup();


    private Integer numbeColumns = 0;

    @FXML TableColumn DetailSelClm;


    ArrayList<String> clms = new ArrayList<>();


    ToggleGroup DetailGroup = new ToggleGroup();

    ToggleGroup HeaderGroup = new ToggleGroup();

    CSVRow DetailTblObj;

    File Chosenfile;

    public File file;

    ArrayList<MyUser> users = new ArrayList<>();

    @FXML
    TextField ReportName;

    @FXML
    Spinner<String> ApproveSpn;
    @FXML
    TextField TitleText;

    @FXML
    TextField NameText;



    @FXML
    public TableColumn<MyUser,Void> SelectAppClm;

    @FXML
    public TableColumn<MyUser,Void> SelectPrepClm;



    @FXML
    public TableView<MyUser> ApproverTbl;

    @FXML
    public TableView<MyUser> PreparerTbl;

      @FXML
    TableColumn<MyUser,String> NameColumn;

    @FXML
    TableColumn<MyUser,String>  TitleColumn;



    @FXML
    TableColumn<MyUser,String>  AppNameClm;

    @FXML
    TableColumn<TableUser,String>  AppTitleClm;


   boolean AlreadyLoaded = false;

    @FXML TableColumn<String,String> HeaderClm;

    @FXML TableColumn HeaderSelClm;

    @FXML TableView HeaderTbl;

    @FXML TableView StateTbl;

    @FXML TableView<CSVRow> DetailTbl;

    @FXML TableColumn StateClm;

    @FXML TableColumn StateSelClm;


    public HeaderObj currentHeader;

    ToggleGroup langGroup =  new ToggleGroup();
@FXML
    JFXRadioButton HebRadio;

@FXML Pagination StatePager;

@FXML
JFXRadioButton EngRadio;

    ToggleGroup StateGroup = new ToggleGroup();

    private final static int rowsPerPage = 500;

    public static String ReportLng = null;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        ApproverTbl.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if( event.getCode() == KeyCode.ENTER) {
//                  event.consume(); // don't consume the event or else the values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( ApproverTbl.getEditingCell() == null) {
                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = ApproverTbl.getFocusModel().getFocusedCell();
                        ApproverTbl.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });


        PreparerTbl.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if( event.getCode() == KeyCode.ENTER) {
//                  event.consume(); // don't consume the event or else the values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( PreparerTbl.getEditingCell() == null) {
                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = PreparerTbl.getFocusModel().getFocusedCell();
                        PreparerTbl.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });




        DetailTbl.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if( event.getCode() == KeyCode.ENTER) {
//                  event.consume(); // don't consume the event or else the values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( DetailTbl.getEditingCell() == null) {
                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = DetailTbl.getFocusModel().getFocusedCell();
                        DetailTbl.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });







        HeaderDB Hdb = new HeaderDB();

        Hdb.CreateDb();

        HeaderClm.impl_setReorderable(false);
        HeaderSelClm.impl_setReorderable(false);


        HebRadio.setToggleGroup(langGroup);
        EngRadio.setToggleGroup(langGroup);

        StateSelClm.impl_setReorderable(false);
        StateClm.impl_setReorderable(false);
        DetailSelClm.impl_setReorderable(false);

        NameColumn.impl_setReorderable(false);
        TitleColumn.impl_setReorderable(false);
        SelectPrepClm.impl_setReorderable(false);

        SelectAppClm.impl_setReorderable(false);
        AppNameClm.impl_setReorderable(false);
        AppTitleClm.impl_setReorderable(false);



        EngRadio.setUserData("English");
        HebRadio.setUserData("Hebrew");

        FilterSupport.addFilter(StateClm);

        langGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (langGroup.getSelectedToggle() != null) {

                    ReportLng =  langGroup.getSelectedToggle().getUserData().toString();

                }

            }
        });


        InstlizeHeaders();
       // LoggedName.setText(LoginController.LogName);
        StateDB db = new StateDB();

        db.CreateDb();

        PopulateTableDetail(StateTbl,StateSelClm,StateGroup,0);


        StateTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        ApproverTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        PreparerTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        DetailTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");
        HeaderTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");



        StatePager.setPageCount(db.queryForStates().size() / rowsPerPage + 1);
        StatePager.setPageFactory(this::createPage);



        ReportName.setText(Main.selection.ReportName);

        ReportName.setFont(Font.font("ariel", FontWeight.BOLD, 18));

        NameText.setFont(Font.font("ariel", FontWeight.BOLD, 18));

        TitleText.setFont(Font.font("ariel", FontWeight.BOLD, 18));


        db.CreateDb();

        PreparerTbl.setEditable(true);
        PreparerTbl.getSelectionModel().setCellSelectionEnabled(true);

        ApproverTbl.setEditable(true);
        ApproverTbl.getSelectionModel().setCellSelectionEnabled(true);

        NameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        TitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));

        AppNameClm.setCellValueFactory(new PropertyValueFactory<>("Name"));
        AppTitleClm.setCellValueFactory(new PropertyValueFactory<>("Title"));

        AppNameClm.setCellFactory(TextFieldTableCell.forTableColumn());
        AppTitleClm.setCellFactory(TextFieldTableCell.forTableColumn());

        PreparerTbl.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ApproverTbl.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        FilterSupport.addFilter(NameColumn);

        FilterSupport.addFilter(TitleColumn);

        FilterSupport.addFilter(AppNameClm);

        FilterSupport.addFilter(AppTitleClm);


        FilterSupport.addFilter(HeaderClm);


        //   setTableListeners();
        setKeyEvents();

        StateClm.setCellValueFactory(
                new PropertyValueFactory<StateObj,String>("stateName")
        );
        StateClm.setCellFactory(TextFieldTableCell.forTableColumn());


        PopulateSpinner();

//ToDo need to insert update table here

        ClearTable();
        RefreshApp();



        FilterSupport.addFilter(HeaderClm);

        DetailTbl.setEditable(true);



        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setAutoHide(true);

        MenuItem inserirLinha;
         if(Main.Hebrew==true){inserirLinha = new MenuItem("הוסף שורה");}else{inserirLinha = new MenuItem("Add Row");}



        inserirLinha.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                if(DetailGroup.getSelectedToggle()!=null){

                    DetailGroup.getSelectedToggle().setSelected(false);
                    Main.selection.setReportHeader(null);
                   // currentHeader.setHeader(null);
                    Main.selection.setHeaderClms(null);



                    if(DetailTblObj!=null)
                        DetailTblObj.getColumns().clear();
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

          }


        removerLinha.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                if(DetailGroup.getSelectedToggle()!=null){

                    DetailGroup.getSelectedToggle().setSelected(false);
                    Main.selection.setReportHeader(null);
                   // currentHeader.setHeader(null);
                    Main.selection.setHeaderClms(null);

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
                    DetailTblObj.getColumns().clear();
                }

                if(DetailTbl.getColumns().size()==2){
                    deleteRow();
                    deleteColumn();
                    Main.selection.setHeaderClms(null);

                }else{

                    deleteColumn();

                }




            }
        });
        contextMenu.getItems().add(removerColuna);

        DetailTbl.setContextMenu(contextMenu);






    }
@FXML
    public void RefreshApp(){
    FilterSupport.getItems(ApproverTbl).clear();

    FilterSupport.getItems(PreparerTbl).clear();
  PopulateTable();

}

    @FXML
    public void RefreshPrep(){
        FilterSupport.getItems(PreparerTbl).clear();

        FilterSupport.getItems(ApproverTbl).clear();
        PopulateTable();

    }

    public void PopulateTableDetail(TableView table,TableColumn clm,ToggleGroup g,int NeededParetID) {

        StateDB db = new StateDB();

        FilterSupport.getItems(table).clear();

        ObservableList<StateObj> m = ReportToList(db.queryForStates());

        for (StateObj sd : m) {

            addRadioButtonToPrepTable(g,clm);

            FilterSupport.getItems(table).add(sd);

        }
    }









    private void addRadioButtonToDetailTblDynamic(TableColumn clm) {


        Callback<TableColumn<CSVRow, Void>, TableCell<CSVRow, Void>> cellFactory = new Callback<TableColumn<CSVRow, Void>, TableCell<CSVRow, Void>>() {

            public TableCell<CSVRow, Void> call(final TableColumn<CSVRow, Void> param) {
                final TableCell<CSVRow, Void> cell = new TableCell<CSVRow, Void>() {

                    private RadioButton btn = new RadioButton();

                    {
                        Main.selection.setHeaderClms(null);

                        btn.setToggleGroup(DetailGroup);

                        btn.setOnAction((ActionEvent event) -> {

                            Dialog dialog;

                            if(Main.Hebrew==true){  dialog = new Dialog(
                                    DialogType.CONFIRMATION,
                                    "הוספת פרטים לדוח",
                                    "האם להוסיף את שורת הפרטים האלו לדוח?",
                                    "בחר YES כדי להוסיף את שורת הפרטים האלו לדוח");
                                dialog.setFontSize(22);
                                dialog.showAndWait();}else{  dialog = new Dialog(
                                    DialogType.CONFIRMATION,
                                    "Add header to Report",
                                    "Add this header to Report?",
                                    "Press YES to this header to Report");
                                dialog.setFontSize(22);
                                dialog.showAndWait();}



                            if (dialog.getResponse() == DialogResponse.YES) {

                                clms.clear();
                                Main.selection.setHeaderClms(null);

                                Main.selection.setReportHeader(getTableView().getItems().get(getIndex()));

                                for(TableColumn c :DetailTbl.getColumns())
                                {
                                    clms.add(c.getText());

                                }

                                Main.selection.setHeaderClms(clms);
                            } else{

                                DetailGroup.getSelectedToggle().setSelected(false);
                                Main.selection.setReportHeader(null);
                                Main.selection.setHeaderClms(null);
                                currentHeader.setHeader(null);
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

        clm.setCellFactory(cellFactory);


    }

    public void InstlizeHeaders(){

        setTableEditable();

        HeaderDB db = new HeaderDB();

        HeaderClm.setCellFactory(TextFieldTableCell.forTableColumn());

        HeaderClm.setCellValueFactory(new PropertyValueFactory<>("Header"));
        //   headersList.add("Project No.:");

        //  headersList.add("Material:");

        //  headersList.add("Temper:");

        // headersList.add("Supplier:");

        //  headersList.add("ספק:");

        //  headersList.add("מס פרוייקט:");



        ArrayList<HeaderObj> listm = db.queryForHeaders();




        ObservableList list = FXCollections.observableArrayList();

        list.addAll(listm);
        for(Object s : list){

            addRadioButtonToStringVoidTbl();
            FilterSupport.getItems(HeaderTbl).add(s);


        }






    }




    private void editFocusedCell() {


    }


    private void setTableEditable() {
        DetailTbl.setEditable(true);
        // allows the individual cells to be selected
        DetailTbl.getSelectionModel().cellSelectionEnabledProperty().set(true);
        // when character or numbers pressed it will start edit in editable
        // fields
        DetailTbl.setOnKeyPressed(event -> {
            if (event.getCode().isLetterKey() || event.getCode().isLetterKey()) {
                editFocusedCell();
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


    @FXML
    public void unSelect(){

        if(DetailTbl.getItems().size()!=0){

            if(DetailGroup.getSelectedToggle().isSelected()){

                DetailTblObj = null;
                DetailGroup.getSelectedToggle().setSelected(false);
                Main.selection.setHeaderClms(null);

                Main.selection.setReportHeader(null);
                currentHeader.setHeader(null);

                Main.selection.getHeaderClms().clear();

            } else{



            }

        } else {
            if(Main.Hebrew==true){  com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "אין שורות",
                    "אין שורות");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{  com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "No Rows",
                    "No Rows");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}


        }



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



    private void readFile(File csvFile) throws IOException {

        CSVFormat csvFormat;
        csvFormat = CSVFormat.DEFAULT.withIgnoreEmptyLines(false);

        if(csvFile.exists()){

          //  DetailTbl.getColumns().clear();


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

            } catch(Exception e ){


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

        }else{

            if(Main.Hebrew==true){   Dialog dialog1 = new Dialog(
                    DialogType.ERROR,
                    "קובץ המצב לא נמצא",
                    "לא ניתן לאתר את הקובץ",
                    "לא נמצא הקובץ");
                dialog1.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);
                dialog1.setFontSize(22);
                dialog1.showAndWait();}else{   Dialog dialog1 = new Dialog(
                    DialogType.ERROR,
                    "Cannot find this state file",
                    "Cannot find this state file",
                    "State load Error");
                dialog1.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);
                dialog1.setFontSize(22);
                dialog1.showAndWait();}



        }









    }

    public void setKeyHeaderEvents(){

        HeaderTbl.setOnKeyReleased((KeyEvent t)-> {
            KeyCode key=t.getCode();
            if (key==KeyCode.DELETE){
                HeaderObj header =  (HeaderObj)HeaderTbl.getSelectionModel().selectedItemProperty().get();

                Dialog dialog1;

                if(Main.Hebrew==true){dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "מחיקת פרט",
                        "האם ברצונך למחוק פרט זה?",
                        "בחר YES כדי למחוק את הפרט");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}else{dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "Delete Header",
                        "Are you sure you want to delete header?",
                        "Press YES to delete header");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}



                if (dialog1.getResponse() == DialogResponse.YES) {

                    HeaderDB db = new HeaderDB();

                    db.DeleteHeader(header.getId());

                }



            } else {
                // ... user chose CANCEL or closed the dialog
            }
        });



    }
    private void adjustColumns(List<SimpleStringProperty> columns) {
        int dif = numbeColumns - columns.size();
        for (int i = 0; i < dif; i++) {
            columns.add(new SimpleStringProperty());
        }
    }


    private TableColumn<CSVRow, String> createColumn(int index,String currentHeader) {
        TableColumn<CSVRow, String> col = new TableColumn<>(currentHeader);

        col.setPrefWidth(130);

        col.setSortable(false);
        col.impl_setReorderable(false);
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
                    Main.selection.setReportHeader(null);
                    Main.selection.setHeaderClms(null);


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
    private void deleteColumn() {


       // Main.selection.setReportHeader(null);
       if((Main.selection.getReportHeader()!=null)&&(Main.selection.getReportHeader().getColumns().size()>0))
        Main.selection.getReportHeader().getColumns().remove(Main.selection.getReportHeader().getColumns().size()-1);

        if(DetailTbl.getColumns().size()>1){

            Main.selection.setReportHeader(null);

            DetailTbl.getColumns().remove(DetailTbl.getColumns().size()-1);

            DetailTbl.refresh();

        }

    }

    @FXML
    private void onLoadActionEvent(ActionEvent event) throws IOException {




       if(DetailTbl.getItems().size()!=0) {

           if (Chosenfile != null){

               if(AlreadyLoaded==false){

                   DetailTbl.getColumns().remove(1,DetailTbl.getColumns().size());

                   DetailTbl.getItems().clear();
                   readFile(Chosenfile);
               }



           } else {
               com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
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
                   DetailTbl.getColumns().remove(1,DetailTbl.getColumns().size());

                   DetailTbl.getItems().clear();


                   readFile(Chosenfile);
               }




       }
       }










    private void deleteRow() {

        if(DetailTbl.getItems().size()>0)
            DetailTbl.getItems().remove(DetailTbl.getItems().size()-1);
    }


    public ObservableList<StateObj> ReportToList(ArrayList<StateObj> List){

        ObservableList<StateObj> list = FXCollections.observableArrayList();

        //ToDo
        for(StateObj nd : List)
            list.add(nd);

        return list;


    }

    private void addNewRow() {

        if(DetailTbl.getColumns().size()>1){

            DetailTbl.getItems().add(DetailTbl.getItems().size(), new CSVRow());
            DetailTbl.getSelectionModel().select(0);
            addRadioButtonToDetailTbl(DetailSelClm);

        }

    }

    private void updateTable(ObservableList<CSVRow> rows) {
        DetailTbl.getColumns().clear();
        if(currentHeader!=null){


            DetailTbl.setItems(rows);
            DetailTbl.setEditable(true);
            DetailTbl.getSelectionModel().setCellSelectionEnabled(true);


        } else {


            DetailTbl.setItems(rows);
            DetailTbl.setEditable(true);
            DetailTbl.getSelectionModel().setCellSelectionEnabled(true);

        }


    }

    private void addRadioButtonToDetailTbl(TableColumn clm) {



        Callback<TableColumn<CSVRow, Void>, TableCell<CSVRow, Void>> cellFactory = new Callback<TableColumn<CSVRow, Void>, TableCell<CSVRow, Void>>() {

            public TableCell<CSVRow, Void> call(final TableColumn<CSVRow, Void> param) {
                final TableCell<CSVRow, Void> cell = new TableCell<CSVRow, Void>() {

                    private RadioButton btn = new RadioButton();

                    {


                        Main.selection.setHeaderClms(null);

                        btn.setToggleGroup(DetailGroup);

                        btn.setOnAction((ActionEvent event) -> {

                            Dialog dialog2;

                            if(Main.Hebrew==true){dialog2 = new Dialog(
                                    DialogType.CONFIRMATION,
                                    "שימוש בשורת פרטים",
                                    "האם להשתמש בשורת פרטים אלו בדוח?",
                                    "בחר YES כדי להשתמש בשורת פרטים אלו בדוח");
                                dialog2.setFontSize(22);
                                dialog2.showAndWait();}else{dialog2 = new Dialog(
                                    DialogType.CONFIRMATION,
                                    "Use this header at the Report?",
                                    "Use this header at the Report?",
                                    "Press YES to use this header");
                                dialog2.setFontSize(22);
                                dialog2.showAndWait();}



                            if (dialog2.getResponse() == DialogResponse.YES) {

                                clms.clear();


                                Main.selection.setReportHeader(getTableView().getItems().get(getIndex()));

                                for(TableColumn c :DetailTbl.getColumns())
                                {
                                    clms.add(c.getText());

                                }

                                Main.selection.setHeaderClms(clms);

                            }else {


                                clms.clear();

                                DetailGroup.getSelectedToggle().setSelected(false);
                                Main.selection.setReportHeader(null);
                                currentHeader.setHeader(null);
                                Main.selection.setHeaderClms(null);


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

        clm.setCellFactory(cellFactory);


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

            if(Main.Hebrew==true){ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "בחר פרט להוספה מתוך הטבלה",
                    "בחר פרט להוספה מתוך הטבלה");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Choose Header",
                    "Choose Header");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}


        }






    }


















    public void setKeyEvents(){

        ApproverTbl.setOnKeyReleased((KeyEvent t)-> {
            KeyCode key=t.getCode();
            if (key==KeyCode.DELETE){
                MyUser user =  ApproverTbl.getSelectionModel().selectedItemProperty().get();
                Dialog dialog1;
                if(Main.Hebrew==true){ dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "מחיקת משתמש",
                        "האם ברצונך למחוק את המשתמש?",
                        "בחר YES כדי למחוק את המשתמש");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}else{ dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "Delete User",
                        "Are you sure you want to delete user?",
                        "Press YES to delete user");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}



                if (dialog1.getResponse() == DialogResponse.YES) {

                    //Add Admin Right

                    if(LoginController.AdminPass==true){

                        deleteUser(user.getID());

                    }else{

                        if(Main.Hebrew==true){ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                                DialogType.ERROR,
                                "התחבר כמשתמש ראשי כדי למחוק משתמש",
                                "התחבר כמשתמש ראשי כדי למחוק משתמש");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();}else{ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                                DialogType.ERROR,
                                "Login as to Delete",
                                "Login as Admin to Delete");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();}



                    }


                }


            } else {
                    // ... user chose CANCEL or closed the dialog
                }
        });


        PreparerTbl.setOnKeyReleased((KeyEvent t)-> {
            KeyCode key=t.getCode();

            if (key==KeyCode.DELETE){

                MyUser user =  PreparerTbl.getSelectionModel().selectedItemProperty().get();
                Dialog dialog1;
                if(Main.Hebrew==true){   dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "מחיקת המשתמש",
                        "האם ברצונך למחוק את המשתמש?",
                        "בחר YES כדי למחוק את המשתמש");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}else{   dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "Delete User",
                        "Are you sure you want to delete user?",
                        "Press YES to delete user");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}



                if (dialog1.getResponse() == DialogResponse.YES) {


                    //Add Admin Right
                   if(LoginController.AdminPass==true){
                       deleteUser(user.getID());

                   }else{

                       if(Main.Hebrew==true){ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                               DialogType.ERROR,
                               "התחבר כמשתמש ראשי כדי למחוק",
                               "התחבר כמשתמש ראשי כדי למחוק");
                           dialog.setFontSize(22);
                           dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                           dialog.showAndWait();}else{ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                               DialogType.ERROR,
                               "Login as to Delete",
                               "Login as Admin to Delete");
                           dialog.setFontSize(22);
                           dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                           dialog.showAndWait();}




                   }


                }



            }
        });

    }

    @FXML
    void edit_colnom(TableColumn.CellEditEvent<MyUser, String> event)  {
        MyUser u= event.getRowValue();
         String n = event.getNewValue();
               TableColumn t = event.getTableColumn();
               String clmName = t.getText();

               db.EditUser(u.getID(),n,clmName);



    }






public void setReportName(){

      Main.selection.ReportName = ReportName.getText();

}






    public void ClearTable(){

        users = null;

        FilterSupport.getItems(ApproverTbl).clear();

        FilterSupport.getItems(PreparerTbl).clear();

}




@FXML
public void DeleteAll(){

        db.deleteDB();

}






    public void PopulateTable(){


           users = db.queryForUsers();

           for (MyUser person : users) {


               if(person.GetApprover().equals("No")) {

                   addRadioButtonToPrepTable();
                   PreparerTbl.getItems().add(person);

               }
               else {
                   addRadioButtonToAppTable();
                   ApproverTbl.getItems().add(person);
               }
           }




    }


    private void addRadioButtonToStringVoidTbl() {


        Callback<TableColumn<HeaderObj, Void>, TableCell<HeaderObj, Void>> cellFactory = new Callback<TableColumn<HeaderObj, Void>, TableCell<HeaderObj, Void>>() {

            public TableCell<HeaderObj, Void> call(final TableColumn<HeaderObj, Void> param) {
                final TableCell<HeaderObj, Void> cell = new TableCell<HeaderObj, Void>() {

                    private  RadioButton btn = new RadioButton();

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
                            btn.setSelected(false);


                        }
                    }
                };
                return cell;
            }
        };

        HeaderSelClm.setCellFactory(cellFactory);


    }



    private void addRadioButtonToPrepTable() {


        Callback<TableColumn<MyUser, Void>, TableCell<MyUser, Void>> cellFactory = new Callback<TableColumn<MyUser, Void>, TableCell<MyUser, Void>>() {

            public TableCell<MyUser, Void> call(final TableColumn<MyUser, Void> param) {
                final TableCell<MyUser, Void> cell = new TableCell<MyUser, Void>() {

                    private  RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(PrepareGroup);

                        btn.setOnAction((ActionEvent event) -> {
                            MyUser data = getTableView().getItems().get(getIndex());

                            Main.selection.setPreparerTitle(data.getTitle());
                            Main.selection.setPreparerName(data.getName());



                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        SelectPrepClm.setCellFactory(cellFactory);


    }






    private void addRadioButtonToAppTable() {


        Callback<TableColumn<MyUser, Void>, TableCell<MyUser, Void>> cellFactory = new Callback<TableColumn<MyUser, Void>, TableCell<MyUser, Void>>() {

            public TableCell<MyUser, Void> call(final TableColumn<MyUser, Void> param) {
                final TableCell<MyUser, Void> cell = new TableCell<MyUser, Void>() {

                    private  RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(ApproveGroup);

                        btn.setOnAction((ActionEvent event) -> {
                            MyUser data = getTableView().getItems().get(getIndex());

                            Main.selection.setApproverTitle(data.getTitle());
                            Main.selection.setApproverName(data.getName());


                            System.out.println("selectedData: " + data);



                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        SelectAppClm.setCellFactory(cellFactory);


    }



public void AddRecord(){

    //ToDo here i will add record to the database and the table

if(LoginController.AdminPass==true){
    String name = NameText.getText();
    String title = TitleText.getText();
    String Approver = ApproveSpn.getValue();

    MyUser user = new MyUser(name,title,Approver);

    user.setID(db.FindHigestID());

    db.insertUser(user);

    //ToDo Add the DB addition code here

    ClearTable();

    PopulateTable();

    NameText.clear();
    TitleText.clear();

} else {

    if(Main.Hebrew==true){  com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
            DialogType.ERROR,
            "התחבר כמשתמש ראשי כדי למחוק",
            "התחבר כמשתמש ראשי כדי למחוק");
        dialog.setFontSize(22);
        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

        dialog.showAndWait();
    }else{  com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
            DialogType.ERROR,
            "Login as to Delete",
            "Login as Admin to Delete");
        dialog.setFontSize(22);
        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

        dialog.showAndWait();
    }





}






}




    public void deleteUser(int id){

     db.DeleteUser(id);

     ClearTable();
     PopulateTable();


    }


    public void PopulateSpinner(){


        ObservableList<String> Options = FXCollections.observableArrayList(//
                "No", "Yes");


        SpinnerValueFactory<String> valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(Options);

        ApproveSpn.setValueFactory(valueFactory);

    }


    @FXML
    public void EditPrepTbl(){


        if(LoginController.AdminPass==true){

            PreparerTbl.setEditable(true);
            NameColumn.setEditable(true);
            TitleColumn.setEditable(true);




            if(Main.Hebrew==true){ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.INFORMATION,
                    "הרשאות העריכה פתוחות",
                    "ניתן כעת לערוך את הטבלאות");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                dialog.showAndWait();}else{ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.INFORMATION,
                    "Edit option open",
                    "You can edit the Prepares table");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                dialog.showAndWait();}





        }else{

            if(Main.Hebrew==true){     Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "התחבר כמשתמש ראשי כדי לערוך",
                    "התחבר כמשתמש ראשי");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{     Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Login as ADMIN to Edit",
                    "Login as ADMIN to Edit");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}





        }

    }


    @FXML
    public void EditAppTbl(){

        if(LoginController.AdminPass==true){

            ApproverTbl.setEditable(true);
            AppNameClm.setEditable(true);
            AppTitleClm.setEditable(true);

            if(Main.Hebrew==true){   com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.INFORMATION,
                    "הרשאות העריכה פתוחות",
                    "ניתן כעת לערוך את הטבלאות");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                dialog.showAndWait();}else{   com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.INFORMATION,
                    "Edit option open",
                    "You can edit the Prepares table");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                dialog.showAndWait();}





        }else{

            if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "התחבר כמשתמש ראשי כדי לערוך",
                    "התחבר כמשתמש ראשי כדי לערוך");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Login as ADMIN to Edit",
                    "Login as ADMIN to Edit");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}





        }

    }

@FXML
public void Refresh(){

    PopulateTableDetail(StateTbl,StateSelClm,StateGroup,0);

   FilterSupport.getItems(HeaderTbl).clear();
    InstlizeHeaders();
    DetailTbl.getItems().clear();
    DetailTbl.getColumns().remove(1,DetailTbl.getColumns().size());

    DetailGroup.getSelectedToggle().setSelected(false);
    Main.selection.setReportHeader(null);
    // currentHeader.setHeader(null);
    Main.selection.setHeaderClms(null);

}

    private Node createPage(int pageIndex) {

        StateDB db = new StateDB();

        ArrayList<StateObj> g = db.queryForStates();

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, g.size());
        StateTbl.setItems(FXCollections.observableArrayList(g.subList(fromIndex, toIndex)));


        return new BorderPane(StateTbl);
    }



    }


