package sample.Controllers;

import com.github.daytron.simpledialogfx.data.DialogResponse;
import com.github.daytron.simpledialogfx.data.HeaderColorStyle;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Database.LoginDB;
import sample.Database.MapDBDriver;
import sample.Main;
import sample.TestPojos.LoginUser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    LoginDB db = new LoginDB();

    static final String AdminName = "IKA";
    static final String AdminPassWord = "2006";
    static LoginUser user = new LoginUser();

    static public String SignaturePath;

@FXML
    JFXTextField NameText;

@FXML
    JFXPasswordField PasswordText;

@FXML
    Label LoggedName;

@FXML
        JFXPasswordField AddPassText;

@FXML
        JFXTextField AddNameText;

@FXML
    JFXCheckBox ApproverBox;

Stage stage = new Stage();

static boolean AdminPass = false;

static boolean UserPass = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        db.CreateDb();

        if(LoggedName.getText()=="")
            LoggedName.setText("None");

        AddNameText.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        NameText.setFont(Font.font("ariel", FontWeight.BOLD, 18));
        PasswordText.setFont(Font.font("ariel", FontWeight.BOLD, 18));

    }



    @FXML
    public void Login() throws IOException {



        if(NameText.getText().equals(AdminName)&&PasswordText.getText().equals(AdminPassWord)){
            {    AdminPass = true;

                LoggedName.setText(NameText.getText());



                  if(Main.Hebrew==true){


                      Dialog dialog = new Dialog(
                          DialogType.INFORMATION,
                          "התחברת כמשתמש ראשי בהצלחה",
                          "התחברת בהצלחה");
                      dialog.setFontSize(20);
                      dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                      dialog.showAndWait();

                      Main.dbDriver.OpenDB();

                  }else{

                      Dialog dialog = new Dialog(
                          DialogType.INFORMATION,
                          "Login as ADMIN Successful",
                          "Login Successful");
                      dialog.setFontSize(20);
                      dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                      dialog.showAndWait();


            }




            }


        } else{

            int id =  db.getUserIdByNameAndPass(NameText.getText(),PasswordText.getText());

            if(id!=-1){

                  LoginUser u = db.LoadUser(PasswordText.getText(),NameText.getText());

                  user.setPassWord(u.getPassWord());
                  user.setId(u.getId());
                 user.setUserName(u.getUserName());
                 user.setApprover(u.isApprover());
                 user.setSignaturePath(u.getSignaturePath());

                UserPass = true;
                LoggedName.setText(NameText.getText());

                AdminPass = false;


               if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                       DialogType.INFORMATION,
                       "ההתחברות הצליחה",
                       "ההתחברות הצליחה");
                   dialog.setFontSize(20);
                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                   dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                       DialogType.INFORMATION,
                       "Login Successful",
                       "Login Successful");
                   dialog.setFontSize(20);
                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                   dialog.showAndWait();}




            } else {


               if(Main.Hebrew==true){    Dialog dialog = new Dialog(
                       DialogType.ERROR,
                       "ההתחברות נכשלה",
                       "ההתחברות נכשלה");
                   dialog.setFontSize(20);
                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                   dialog.showAndWait();
               }else{
                   Dialog dialog = new Dialog(
                           DialogType.ERROR,
                           "Login Fail",
                           "Login Fail");
                   dialog.setFontSize(20);
                   dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                   dialog.showAndWait();


               }


            }





        }














    }

@FXML
public void AddSignature(){

if(AdminPass==true){
    FileChooser chooser = new FileChooser();

    FileChooser.ExtensionFilter fileExtensions =
            new FileChooser.ExtensionFilter(
                    "Image", "*.jpg;*.jpeg;*.png;*.gif;*.bmp");
    chooser.setSelectedExtensionFilter(fileExtensions);

    chooser.getExtensionFilters().add(fileExtensions);

    File SignatureFile = chooser.showOpenDialog(stage);

    SignaturePath = SignatureFile.getPath();

}else{

if(Main.Hebrew==true){   Dialog dialog = new Dialog(
        DialogType.INFORMATION,
        "התחבר כמשתמש ראשי כדי לשנות פרטים של משתמש אחר",
        "התחבר כמשתמש ראשי");
    dialog.setFontSize(20);
    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

    dialog.showAndWait();}else{   Dialog dialog = new Dialog(
        DialogType.INFORMATION,
        "Login As Admin To Change details",
        "Login As Admin To Change details");
    dialog.setFontSize(20);
    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

    dialog.showAndWait();}



}



}

    @FXML
      public void DeleteUser(){






        if(AdminPass == true){

              int id = -1;
            Dialog dialog1;

            if(Main.Hebrew==true){ dialog1 = new Dialog(
                    DialogType.CONFIRMATION,
                    "מחיקת משתמש",
                    "האם למחוק משתמש זה?",
                    "בחר YES כדי למחוק את המשתמש");

                dialog1.setFontSize(20);
                dialog1.showAndWait();}else{ dialog1 = new Dialog(
                    DialogType.CONFIRMATION,
                    "Delete User",
                    "Delete this User?",
                    "Press YES to Delete User");

                dialog1.setFontSize(20);
                dialog1.showAndWait();}





            if (dialog1.getResponse() == DialogResponse.YES) {

                id = db.getUserIdByNameAndPass(AddNameText.getText(),AddPassText.getText());

                if(id!=-1){


                    db.DeleteUser(id);

                    if(Main.Hebrew==true){Dialog dialog = new Dialog(
                            DialogType.INFORMATION,
                            "המשתמש נמחק",
                            "מחיקת המשתמש הסתיימה");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                        dialog.showAndWait();}else{Dialog dialog = new Dialog(
                            DialogType.INFORMATION,
                            "The user was deleted",
                            "The user was deleted");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                        dialog.showAndWait();}





                }else{

                    if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "משתמש זה אינו קיים",
                            "המשתמש אינו קיים");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                        dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "This user does not exist",
                            "This user does not exist");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                        dialog.showAndWait();}




                }


            }







        } else {

            if(Main.Hebrew==true){  Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "התחבר כמשתמש ראשי כדי למחוק משתמשים",
                    "התחבר כמשתמש ראשי");
                dialog.setFontSize(20);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{  Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Login as Admin to Delete Users",
                    "Login as Admin to Delete Users");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}


        }



    }



    @FXML
    public void AddUser(){

      if(AdminPass == true) {


          LoginUser user = new LoginUser();

          user.setUserName(AddNameText.getText());

          user.setPassWord(AddPassText.getText());


          if(ApproverBox.isSelected())
          user.setApprover(true);
          else
              user.setApprover(false);

          if(AddPassText.getText()==""||AddNameText.getText()==""){

              if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                      DialogType.ERROR,
                      "שדות השם או הסיסמא ריקים",
                      "אנא מלא שם וסיסמא");
                  dialog.setFontSize(20);
                  dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                  dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                      DialogType.ERROR,
                      "Name or Password fields are empty",
                      "Name or Password fields are empty");
                  dialog.setFontSize(20);
                  dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                  dialog.showAndWait();}




          }else{

              int id;
              id = db.getUserIdByName(AddNameText.getText());

              if (id!=-1) {


                      db.EditUser(id, AddPassText.getText());

                  if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                          DialogType.INFORMATION,
                          "הסיסמא שונתה",
                          "השינוי בוצא בהצלחה");
                      dialog.setFontSize(20);
                      dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                      dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                          DialogType.INFORMATION,
                          "Password changed",
                          "Password changed");
                      dialog.setFontSize(20);
                      dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                      dialog.showAndWait();}



          } else{

                  if(SignaturePath == null){
                      Dialog dialog1;

                      if(Main.Hebrew==true){dialog1 = new Dialog(
                              DialogType.CONFIRMATION,
                              "להוסיף משתמש ללא חתימה",
                              "האם ליצור משתמש ללא חתימה?",
                              "בחר YES כדי להוסיף משתמש ללא חתימה");

                          dialog1.setFontSize(20);
                          dialog1.showAndWait();}else{dialog1 = new Dialog(
                              DialogType.CONFIRMATION,
                              "Add User Without Signature",
                              "Add User Without Signature?",
                              "Press YES to Add User Without Signature");

                          dialog1.setFontSize(20);
                          dialog1.showAndWait();}



                      if (dialog1.getResponse() == DialogResponse.YES) {


                          db.insertUser(user);
                      }



              } else{
                        user.setSignaturePath(SignaturePath);
                      db.insertUser(user);

                  }






          }
















          }
      } else{

          if(Main.Hebrew==true){  Dialog dialog = new Dialog(
                  DialogType.INFORMATION,
                  "התחבר כמשתמש ראשי כדי לשנות או ליצור משתמשים",
                  "התחבר כמשתמש ראשי");
              dialog.setFontSize(20);
              dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

              dialog.showAndWait();}else{  Dialog dialog = new Dialog(
                  DialogType.INFORMATION,
                  "Login as Admin to Add/Change Users",
                  "Login As Admin");
              dialog.setFontSize(20);
              dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

              dialog.showAndWait();}




      }




    }

    @FXML
    public void LogOut(){

        UserPass = false;
        AdminPass = false;
        LoggedName.setText("None");

        Main.dbDriver.CloseDB();
        Main.dbDriver.OpenReadOnlyDB();

    }

}
