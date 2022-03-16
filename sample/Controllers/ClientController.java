package sample.Controllers;

import com.github.daytron.simpledialogfx.data.HeaderColorStyle;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import sample.Database.ClientContactDB;
import sample.Database.ContactDB;
import sample.Filter.FilterSupport;
import sample.Main;
import sample.TestPojos.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

ToggleGroup ClientGroup = new ToggleGroup();

ToggleGroup ContactGroup = new ToggleGroup();


@FXML
    JFXTextField CompanyText;

@FXML
    JFXTextField AdressText;

@FXML JFXTextField EmailText;

@FXML JFXTextField NameText;

@FXML JFXTextField PhoneText;


@FXML
    JFXButton ClientAddBtn;

@FXML
JFXButton ContactAddBtn;

@FXML
TableView ClientTbl;

    @FXML
    TableView ContactTbl;

    @FXML
    TableColumn CompanyClm;


    @FXML
    TableColumn AdressClm;

    @FXML
    TableColumn NameClm;

    @FXML
    TableColumn EmailClm;

    @FXML
    TableColumn PhoneClm;

    @FXML
    TableColumn ClientSelClm;

    @FXML
    TableColumn ContactSelClm;
    


  int ParentID;


ClientContactDB db = new ClientContactDB();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        ClientTbl.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if( event.getCode() == KeyCode.ENTER) {
//                  event.consume(); // don't consume the event or else the values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( ClientTbl.getEditingCell() == null) {
                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = ClientTbl.getFocusModel().getFocusedCell();
                        ClientTbl.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });


        ContactTbl.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if( event.getCode() == KeyCode.ENTER) {
//                  event.consume(); // don't consume the event or else the values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( ContactTbl.getEditingCell() == null) {
                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = ContactTbl.getFocusModel().getFocusedCell();
                        ContactTbl.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });


        db.CreateDb();

        ContactSelClm.impl_setReorderable(false);
        ClientSelClm.impl_setReorderable(false);
        NameClm.impl_setReorderable(false);
        CompanyClm.impl_setReorderable(false);
        AdressClm.impl_setReorderable(false);
        EmailClm.impl_setReorderable(false);
        PhoneClm.impl_setReorderable(false);


        //LoggedName.setText(LoginController.LogName);

        deleteContactParam();
        deleteClientParam();


        CompanyText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        AdressText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        NameText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        EmailText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        PhoneText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));


        ClientTbl.setEditable(false);
        AdressClm.setEditable(false);
        NameClm.setEditable(false);
        EmailClm.setEditable(false);
        CompanyClm.setEditable(false);
        PhoneClm.setEditable(false);

        ContactTbl.setEditable(false);


        ClientTbl.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
        ContactTbl.setStyle("-fx-font-weight: bold;-fx-font-size: 14");


        ClientTbl.getSelectionModel().setCellSelectionEnabled(true);







        ContactTbl.getSelectionModel().setCellSelectionEnabled(true);

        setTableUp(ClientTbl);

        setTableUp(ContactTbl);

        FilterSupport.addFilter(AdressClm);
        FilterSupport.addFilter(CompanyClm);
        FilterSupport.addFilter(NameClm);
        FilterSupport.addFilter(EmailClm);
        FilterSupport.addFilter(PhoneClm);




        AdressClm.setCellValueFactory(
                new PropertyValueFactory<ClientTableModel,String>("Adress")
        );
        AdressClm.setCellFactory(TextFieldTableCell.forTableColumn());


        CompanyClm.setCellValueFactory(
                new PropertyValueFactory<ClientTableModel,String>("CompanyName")
        );
        CompanyClm.setCellFactory(TextFieldTableCell.forTableColumn());


        NameClm.setCellValueFactory(
                new PropertyValueFactory<ContactTableModel,String>("Name")
        );
        NameClm.setCellFactory(TextFieldTableCell.forTableColumn());


        EmailClm.setCellValueFactory(
                new PropertyValueFactory<ContactTableModel,String>("Email")
        );
        EmailClm.setCellFactory(TextFieldTableCell.forTableColumn());

        PhoneClm.setCellValueFactory(
                new PropertyValueFactory<ContactTableModel,String>("Phone")
        );
        PhoneClm.setCellFactory(TextFieldTableCell.forTableColumn());





      //ToDo initialize Tables

        PopulateClientTable();

    }





@FXML
    public void EditTbls(){






    if(LoginController.AdminPass==true){

            ClientTbl.setEditable(true);
            AdressClm.setEditable(true);
            NameClm.setEditable(true);
            EmailClm.setEditable(true);
            CompanyClm.setEditable(true);
            PhoneClm.setEditable(true);

            ContactTbl.setEditable(true);


            if(Main.Hebrew==true){

                com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                        DialogType.INFORMATION,
                        "אפשרויות העריכה פתוחות",
                        "ניתן לערוך טבלאות");
                dialog.setFontSize(20);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                dialog.showAndWait();

            }else{ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.INFORMATION,
                    "Edit option open",
                    "You can edit the tables");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                dialog.showAndWait();
            }



    }else{

            if(Main.Hebrew==true){
                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "התחבר כמשתמש ראשי כדי לערוך",
                        "התחבר כמשתמש ראשי");
                dialog.setFontSize(20);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();



            }else {

                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "Login as ADMIN to Edit",
                        "Login as ADMIN to Edit");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();


            }




        }



    }




    public void PopulateContactTable(int parentID){

        ArrayList<ContactObject> contacts = new ArrayList<>();
        FilterSupport.getItems(ContactTbl).clear();

        for(ContactObject obj :db.GetListByID(parentID))
        {

            contacts.add(obj);

        }


        for (ContactObject person : contacts) {


            addRadioButtonToContactTable();


            FilterSupport.getItems(ContactTbl).add(person);

        }




    }



    public void PopulateClientTable(){

        ArrayList<ClientObject> clients = new ArrayList<>();


        FilterSupport.getItems(ClientTbl).clear();

        for(ClientObject obj :db.queryForUsers())
        {

            clients.add(obj);

        }


        for (ClientObject person : clients) {


            addRadioButtonToClientTable();


            FilterSupport.getItems(ClientTbl).add(person);

        }




    }


    private void addRadioButtonToClientTable() {


        Callback<TableColumn<ClientObject, Void>, TableCell<ClientObject, Void>> cellFactory = new Callback<TableColumn<ClientObject, Void>, TableCell<ClientObject, Void>>() {

            public TableCell<ClientObject, Void> call(final TableColumn<ClientObject, Void> param) {
                final TableCell<ClientObject, Void> cell = new TableCell<ClientObject, Void>() {

                    private  RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(ClientGroup);

                        btn.setOnAction((ActionEvent event) -> {
                            ClientObject data = getTableView().getItems().get(getIndex());
                             ParentID = data.getId();
                            Main.selection.setCompanyName(data.getCompanyName());
                            Main.selection.setAdress(data.getAdress());

                           PopulateContactTable(ParentID);

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

        ClientSelClm.setCellFactory(cellFactory);


    }

    private void addRadioButtonToContactTable() {


        Callback<TableColumn<ContactObject, Void>, TableCell<ContactObject, Void>> cellFactory = new Callback<TableColumn<ContactObject, Void>, TableCell<ContactObject, Void>>() {

            public TableCell<ContactObject, Void> call(final TableColumn<ContactObject, Void> param) {
                final TableCell<ContactObject, Void> cell = new TableCell<ContactObject, Void>() {

                    private  RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(ContactGroup);

                        btn.setOnAction((ActionEvent event) -> {
                            ContactObject data = getTableView().getItems().get(getIndex());
                            ParentID = data.getId();
                            Main.selection.setContactName(data.getName());
                            Main.selection.setEmail(data.getEmail());
                            Main.selection.setPhone(data.getPhone());



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

        ContactSelClm.setCellFactory(cellFactory);


    }


    @FXML
    public void AddClient(){



        if(LoginController.AdminPass==true){

            String ComapnyName = CompanyText.getText();
            String Adress = AdressText.getText();

            ClientObject client = new ClientObject();

            client.setAdress(Adress);
            client.setCompanyName(ComapnyName);
            client.setId(db.FindHigestID());

            ContactObject obj = new ContactObject();

            db.insertClientContact(client);

            //ToDo Add the DB addition code here

            ClearTable();

            PopulateClientTable();

            AdressText.clear();
            CompanyText.clear();


        } else{
          if(Main.Hebrew==true){

              Dialog dialog = new Dialog(
                      DialogType.ERROR,
                      "התחבר כמשתמש ראשי כדי להוסיף לקוחות",
                      "התחבר כמשתמש ראשי");
              dialog.setFontSize(20);
              dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

              dialog.showAndWait();

          }else{
              Dialog dialog = new Dialog(
                      DialogType.ERROR,
                      "Login as ADMIN to add Clients",
                      "Login as ADMIN to add Clients");
              dialog.setFontSize(22);
              dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

              dialog.showAndWait();


          }




        }











    }

    @FXML
    public void RefreshClient(){

        ClearTable();

        PopulateClientTable();
    }



    public void ClearTable(){

        FilterSupport.getItems(ClientTbl).clear();
        FilterSupport.getItems(ContactTbl).clear();
    }

    @FXML
    void edit_Client(TableColumn.CellEditEvent<ClientObject, String> event)  {

       if(LoginController.AdminPass==true){
           ClientObject u= event.getRowValue();
           String n = event.getNewValue();
           TableColumn t = event.getTableColumn();
           String clmName = t.getText();

           db.EditUser(u.getId(),n,clmName);



       } else{
            if(Main.Hebrew==true){

                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "התחבר כמשתמש ראשי כדי לערוך לקוחות",
                        "התחבר כמשתמש ראשי");
                dialog.setFontSize(20);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();

            }else{

                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "Login as ADMIN to Edit Clients",
                        "Login as ADMIN to Edit Clients");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();

            }


       }





    }

@FXML
    void edit_Contact(TableColumn.CellEditEvent<ContactObject, String> event)  {

    if(LoginController.AdminPass==true){
        ContactDB db1 = new ContactDB();

        ContactObject u= event.getRowValue();
        String n = event.getNewValue();
        TableColumn t = event.getTableColumn();
        String clmName = t.getText();

        db1.EditUser(u.getId(),n,clmName);


    } else{

        if(Main.Hebrew==true){

            Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "התחבר כמשתמש ראשי כדי לערוך אנשי קשר",
                    "התחבר כמשתמש ראשי");
            dialog.setFontSize(20);
            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

            dialog.showAndWait();

        }else {

            Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Login as ADMIN to Edit Contact",
                    "Login as ADMIN to Edit Contact");
            dialog.setFontSize(22);
            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

            dialog.showAndWait();

        }


    }



    }


    @FXML
    public void AddContact(){

        if(LoginController.AdminPass==true){


            ContactDB db1 = new ContactDB();
            if(ClientGroup.getSelectedToggle()!=null){

                ContactObject contact = new ContactObject();

                contact.setId(db1.FindHigestID());
                contact.setParent_id(ParentID);
                contact.setName(NameText.getText());
                contact.setPhone(PhoneText.getText());
                contact.setEmail(EmailText.getText());

                ClientObject j = new ClientObject();

                db1.insertClientContact(contact);


                ContactTbl.getItems().clear();
                PopulateContactTable(ParentID);

            } else {

                  if(Main.Hebrew==true){
                      Dialog dialog = new Dialog(
                              DialogType.ERROR,
                              "חובה לבחור לקוח",
                              "בחר לקוח כדי להוסיף איש קשר");
                      dialog.setFontSize(22);
                      dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                      dialog.showAndWait();


                  }else{

                      Dialog dialog = new Dialog(
                              DialogType.ERROR,
                              "Client has to be selected",
                              "Select a Client to add a contact");
                      dialog.setFontSize(22);
                      dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                      dialog.showAndWait();

                  }


            }


        } else{

            if(Main.Hebrew==true){


                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "התחבר כמשתמש ראשי כדי להוסיף אנשי קשר",
                        "התחבר כמשתמש ראשי");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();
            }else{

                Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "Login as ADMIN to add Contact",
                        "Login as ADMIN to add Contact");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();

            }



        }






    }


    public void setTableUp(TableView table){


        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);


    }





    public void deleteClientParam(){


            ClientTbl.setOnKeyReleased((
                    KeyEvent t)-> {
                KeyCode key=t.getCode();
                if (key==KeyCode.DELETE){
                    ClientObject param =  (ClientObject)ClientTbl.getSelectionModel().selectedItemProperty().get();


                    if(Main.Hebrew==true){

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("מחיקת משתמש");
                        alert.setHeaderText("למחוק משתמש?");
                        alert.setContentText("לחץ OK כדי למחוק את המשתמש");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){

                            if(LoginController.AdminPass==true){
                                db.DeleteUser(param.getId());



                                PopulateClientTable();



                            } else{

                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "התחבר כמשתמש ראשי כדי למחוק לקוח",
                                        "התחבר כמשתמש ראשי");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();


                            }





                            //TODO refresh after the deletion(refresh) tables
                        }

                    }else {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Delete User");
                        alert.setContentText("Do you want to delete this user?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){

                            if(LoginController.AdminPass==true){
                                db.DeleteUser(param.getId());



                                PopulateClientTable();



                            } else{

                                Dialog dialog = new Dialog(
                                        DialogType.ERROR,
                                        "Login as ADMIN to Delete Clients",
                                        "Login as ADMIN to Delete Clients");
                                dialog.setFontSize(22);
                                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                                dialog.showAndWait();


                            }





                            //TODO refresh after the deletion(refresh) tables
                        }


                    }


                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            });










    }



    public void deleteContactParam(){

        ContactTbl.setOnKeyReleased((
                KeyEvent t)-> {
            KeyCode key=t.getCode();
            if (key==KeyCode.DELETE){
                ContactObject param =  (ContactObject)ContactTbl.getSelectionModel().selectedItemProperty().get();


                if(Main.Hebrew==true){

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("מחיקת משתמש");
                    alert.setHeaderText("למחוק משתמש?");
                    alert.setContentText("לחץ OK כדי למחוק את המשתמש");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){

                        if(LoginController.AdminPass==true){

                            ContactDB db1 = new ContactDB();

                            db1.DeleteUser(param.getId());



                            PopulateContactTable(param.getParent_id());



                        } else{

                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "התחבר כמשתמש ראשי כדי למחוק לקוח",
                                    "התחבר כמשתמש ראשי");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();


                        }





                        //TODO refresh after the deletion(refresh) tables
                    }

                }else{

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Delete User");
                    alert.setContentText("Do you want to delete this user?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){


                        if(LoginController.AdminPass==true){

                            ContactDB db1 = new ContactDB();
                            db1.DeleteUser(param.getId());
                            PopulateContactTable(ParentID);


                        } else{

                            Dialog dialog = new Dialog(
                                    DialogType.ERROR,
                                    "Login as ADMIN to Delete Clients",
                                    "Login as ADMIN to Delete Clients");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();


                        }







                        //TODO refresh after the deletion(refresh) tables
                    }

                }




            } else {
                // ... user chose CANCEL or closed the dialog
            }
        });

    }


}
