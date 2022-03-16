package sample.Controllers;

import com.github.daytron.simpledialogfx.data.DialogResponse;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ImportController implements Initializable {


    @FXML
    JFXButton ChemicalBtn;

    @FXML
    JFXButton TensileBtn;


    @FXML
    JFXButton ImpactBtn;

    @FXML
    JFXButton HardnessBtn;

    @FXML
    JFXButton MicroHBtn;



       Stage stage = new Stage();

       public static ArrayList<File> ChemicalFiles = new ArrayList<>();
    public static ArrayList<File> TensileFiles = new ArrayList<>();
    public static ArrayList<File> ImpactFiles = new ArrayList<>();
    public static ArrayList<File> HardnessFile = new ArrayList<>();
    public static ArrayList<File> MicroHardnessFile = new ArrayList<>();

    ObservableList<String> list;
    @FXML
    TableView SampleTbl;

    @FXML
    TableColumn<String,String> SampleClm;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  LoggedName.setText(LoginController.LogName);

        SampleClm.impl_setReorderable(false);


        SampleClm.setCellFactory(TextFieldTableCell.forTableColumn());

        SampleClm.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));

        SampleTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");

    }


@FXML
   public void OpenHardness() throws IOException, InvalidFormatException {




    File reportFile = null;

    List<File> files = null;


    Dialog dialog1;


    Preferences userPrefs = Preferences.userNodeForPackage(Main.class);

    String lastVisitedDirectory=userPrefs.get("Hardness Path","");

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Hardness Data");

    if(!lastVisitedDirectory.equals(""))
        fileChooser.setInitialDirectory(new  File(lastVisitedDirectory));
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel","*.xlsx;*.xls"));


    try{

        files = fileChooser.showOpenMultipleDialog(stage);

    }catch (Exception e){

        String userDirectoryString = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new  File(userDirectoryString));
        files = fileChooser.showOpenMultipleDialog(stage);


    }




    if(HardnessFile.size()>0){

        if( CheckDuplicateFile(files.get(0))==true){

           if(Main.Hebrew==true){ dialog1 = new Dialog(
                   DialogType.CONFIRMATION,
                   "הקובץ כבר נבחר",
                   "האם תרצה לבחור קובץ זה בחירה כפולה?",
                   "בחר YES כדי לבחור את הקובץ בחירה כפולה");

               dialog1.setFontSize(20);
               dialog1.showAndWait();}else{ dialog1 = new Dialog(
                   DialogType.CONFIRMATION,
                   "This is a duplicate file",
                   "Are you sure you want to import duplicate file",
                   "Press Yes to import duplicate file");

               dialog1.setFontSize(20);
               dialog1.showAndWait();}



            if (dialog1.getResponse() == DialogResponse.YES) {


                try{

                    reportFile = files.get(0);
                }catch (Exception e){}

            }




        }else{
            try{

                reportFile = files.get(0);
            }catch (Exception e){}
        }

    }else {
        try{

            reportFile = files.get(0);
        }catch (Exception e){}


    }

    HardnessFile.clear();



    if(!lastVisitedDirectory.equals(System.getProperty("user.home"))){
        lastVisitedDirectory =userPrefs.get("Hardness Path","");
    }else
        lastVisitedDirectory=(files!=null && files.size()>=1)?files.get(0).getParent():System.getProperty("user.home");

    userPrefs.put("Hardness Path", lastVisitedDirectory);


    if(reportFile!=null)
    if(reportFile.exists()){

        HardnessFile.add(reportFile);
        list = FXCollections.observableArrayList();

        String G = reportFile.getName().replace(".xlsx","");

        list.add(G+" Hardness No." + HardnessFile.size());

        for(String s:list)
            SampleTbl.getItems().add(s);
    }




   }





@FXML
    public void OpenMicroHardness() throws IOException, InvalidFormatException {
    File reportFile = null;
    Dialog dialog1;
    List<File> files = null;
    Preferences userPrefs = Preferences.userRoot().node(this.getClass().getName());

    String lastVisitedDirectory=userPrefs.get("Micro-Hardness Path","");

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Micro-Hardness Data");

    if(!lastVisitedDirectory.equals(""))
        fileChooser.setInitialDirectory(new  File(lastVisitedDirectory));
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel","*.xlsx;*.xls"));

    try{

        files = fileChooser.showOpenMultipleDialog(stage);

    }catch (Exception e){

        String userDirectoryString = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new  File(userDirectoryString));
        files = fileChooser.showOpenMultipleDialog(stage);


    }
    if(MicroHardnessFile.size()>0){
try{   if( CheckDuplicateFile(files.get(0))==true){

    if(Main.Hebrew==true){ dialog1 = new Dialog(
            DialogType.CONFIRMATION,
            "הקובץ כבר נבחר",
            "האם תרצה לבחור קובץ זה בחירה כפולה?",
            "בחר YES כדי לבחור את הקובץ בחירה כפולה");

        dialog1.setFontSize(20);
        dialog1.showAndWait();}else{ dialog1 = new Dialog(
            DialogType.CONFIRMATION,
            "This is a duplicate file",
            "Are you sure you want to import duplicate file",
            "Press Yes to import duplicate file");

        dialog1.setFontSize(20);
        dialog1.showAndWait();}



    if (dialog1.getResponse() == DialogResponse.YES) {


        try{

            reportFile = files.get(0);
        }catch (Exception e){}

    }


}else{
    try{

        reportFile = files.get(0);
    }catch (Exception e){}
}}catch (Exception e){}


    } else{

        try{

            reportFile = files.get(0);
        }catch (Exception e){}

    }


    MicroHardnessFile.clear();


if(!lastVisitedDirectory.equals(System.getProperty("user.home"))){
    lastVisitedDirectory =userPrefs.get("Hardness Path","");
}else
    lastVisitedDirectory=(files!=null && files.size()>=1)?files.get(0).getParent():System.getProperty("user.home");

    userPrefs.put("Hardness Path", lastVisitedDirectory);



    if(reportFile!=null)
    if(reportFile.exists()){

        MicroHardnessFile.add(reportFile);
        list = FXCollections.observableArrayList();

        String G = reportFile.getName().replace(".xlsx","");

        list.add(G+" Micro Hardness No." + MicroHardnessFile.size());

        for(String s:list)
            SampleTbl.getItems().add(s);
    }





    }



@FXML
    public void OpenTensile() throws IOException, InvalidFormatException {
    File reportFile = null;
    List<File> files = null;
    Dialog dialog1;
    Preferences userPrefs = Preferences.userNodeForPackage(Main.class);

    String lastVisitedDirectory=userPrefs.get("Tensile Path","");

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Tensile Data");

    if(!lastVisitedDirectory.equals(""))
        fileChooser.setInitialDirectory(new  File(lastVisitedDirectory));
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV","*.csv;"));

    try{

        files = fileChooser.showOpenMultipleDialog(stage);

    }catch (Exception e){

        String userDirectoryString = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new  File(userDirectoryString));
        files = fileChooser.showOpenMultipleDialog(stage);


    }
       if(TensileFiles.size()>0){
           if( CheckDuplicateFile(files.get(0))==true){

               if(Main.Hebrew==true){dialog1 = new Dialog(
                       DialogType.CONFIRMATION,
                       "הקובץ כבר נבחר",
                       "האם תרצה לבחור קובץ זה בחירה כפולה?",
                       "בחר YES כדי לבחור את הקובץ בחירה כפולה");

                   dialog1.setFontSize(20);
                   dialog1.showAndWait();}else{ dialog1 = new Dialog(
                       DialogType.CONFIRMATION,
                       "This is a duplicate file",
                       "Are you sure you want to import duplicate file",
                       "Press Yes to import duplicate file");

                   dialog1.setFontSize(20);
                   dialog1.showAndWait();}



               if (dialog1.getResponse() == DialogResponse.YES) {


                   try{

                       reportFile = files.get(0);
                   }catch (Exception e){}

               }

           }else{
               try{

                   reportFile = files.get(0);
               }catch (Exception e){}

           }



       } else {
           try{

               reportFile = files.get(0);
           }catch (Exception e){}


       }





    if(!lastVisitedDirectory.equals(System.getProperty("user.home"))){
        lastVisitedDirectory =userPrefs.get("Tensile Path","");
    }else
        lastVisitedDirectory=(files!=null && files.size()>=1)?files.get(0).getParent():System.getProperty("user.home");

    userPrefs.put("Tensile Path", lastVisitedDirectory);



     if(reportFile!=null)
    if(reportFile.exists()){

        TensileFiles.add(reportFile);
            list = FXCollections.observableArrayList();

          String G = reportFile.getName().replace(".csv","");

        list.add(G+" Tensile No." + TensileFiles.size());

        for(String s:list)
            SampleTbl.getItems().add(s);
    }




    }

public boolean CheckDuplicateFile(File f){

        boolean isDuplicate = false;

        for(File existingFile:ChemicalFiles){

            if(existingFile.getPath().equals(f.getPath())){


                isDuplicate = true;

            }


        }


    for(File existingFile:TensileFiles){

        if(existingFile.getPath().equals(f.getPath())){

            isDuplicate = true;



        }


    }

    for(File existingFile:ImpactFiles){

        if(existingFile.getPath().equals(f.getPath())){

            isDuplicate = true;



        }


    }

    for(File existingFile:HardnessFile){

        if(existingFile.getPath().equals(f.getPath())){


            isDuplicate = true;


        }


    }

    for(File existingFile:MicroHardnessFile){

        if(existingFile.getPath().equals(f.getPath())){

            isDuplicate = true;



        }


    }



return isDuplicate;

}

@FXML
    public void OpenImpact() throws IOException, InvalidFormatException {


    List<File> files = null;
    File reportFile = null;
    Dialog dialog1;
    Preferences userPrefs = Preferences.userNodeForPackage(Main.class);

    String lastVisitedDirectory=userPrefs.get("Impact Path","");

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Impact Data");

    if(!lastVisitedDirectory.equals(""))
        fileChooser.setInitialDirectory(new  File(lastVisitedDirectory));
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel","*.xlsx;*.xls"));

    try{

        files = fileChooser.showOpenMultipleDialog(stage);

    }catch (Exception e){

        String userDirectoryString = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new  File(userDirectoryString));
        files = fileChooser.showOpenMultipleDialog(stage);


    }
    if(ImpactFiles.size()>0){

        if( CheckDuplicateFile(files.get(0))==true){

            if(Main.Hebrew==true){dialog1 = new Dialog(
                    DialogType.CONFIRMATION,
                    "הקובץ כבר נבחר",
                    "האם תרצה לבחור קובץ זה בחירה כפולה?",
                    "בחר YES כדי לבחור את הקובץ בחירה כפולה");

                dialog1.setFontSize(20);
                dialog1.showAndWait();}else{   dialog1 = new Dialog(
                    DialogType.CONFIRMATION,
                    "This is a duplicate file",
                    "Are you sure you want to import duplicate file",
                    "Press Yes to import duplicate file");

                dialog1.setFontSize(20);
                dialog1.showAndWait();}



            if (dialog1.getResponse() == DialogResponse.YES) {


                try{

                    reportFile = files.get(0);
                }catch (Exception e){}

            }


        }else{
            try{

                reportFile = files.get(0);
            }catch (Exception e){}

        }

    }else {
        try {

            reportFile = files.get(0);
        } catch (Exception e) {
        }
    }


ImpactFiles.clear();

    if(!lastVisitedDirectory.equals(System.getProperty("user.home"))){
        lastVisitedDirectory =userPrefs.get("Impact Path","");
    }else
        if(!lastVisitedDirectory.equals(System.getProperty("user.home")))
        lastVisitedDirectory=(files!=null && files.size()>=1)?files.get(0).getParent():System.getProperty("user.home");
        else
            lastVisitedDirectory = userPrefs.get("Impact Path","");
    userPrefs.put("Impact Path", lastVisitedDirectory);



    if(reportFile!=null)
    if(reportFile.exists()){

        ImpactFiles.add(reportFile);        list = FXCollections.observableArrayList();
        String G = reportFile.getName().replace(".xlsx","");

        list.add(G+" Impact Data No." + ImpactFiles.size());

        for(String s:list)
            SampleTbl.getItems().add(s);
    }





    }


@FXML
    public void OpenChemical() throws IOException, InvalidFormatException {
    Dialog dialog1;
       File reportFile = null;
    List<File> files = null;
    Preferences userPrefs = Preferences.userNodeForPackage(Main.class);

    String lastVisitedDirectory=userPrefs.get("Chemical Path","");

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Chemical Data");

    if(!lastVisitedDirectory.equals(""))
        fileChooser.setInitialDirectory(new  File(lastVisitedDirectory));
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel","*.xlsx;*.xls"));

    try{

        files = fileChooser.showOpenMultipleDialog(stage);

    }catch (Exception e){

        String userDirectoryString = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new  File(userDirectoryString));
        files = fileChooser.showOpenMultipleDialog(stage);


    }





    if(ChemicalFiles.size()>0){
 if(files!=null)
        if( CheckDuplicateFile(files.get(0))==true){

            if(Main.Hebrew==true){dialog1 = new Dialog(
                    DialogType.CONFIRMATION,
                    "הקובץ כבר נבחר",
                    "האם תרצה לבחור קובץ זה בחירה כפולה?",
                    "בחר YES כדי לבחור את הקובץ בחירה כפולה");

                dialog1.setFontSize(20);
                dialog1.showAndWait();}else{dialog1 = new Dialog(
                    DialogType.CONFIRMATION,
                    "This is a duplicate file",
                    "Are you sure you want to import duplicate file",
                    "Press Yes to import duplicate file");

                dialog1.setFontSize(20);
                dialog1.showAndWait();}



            if (dialog1.getResponse() == DialogResponse.YES) {


                try{

                    reportFile = files.get(0);
                }catch (Exception e){}

            }


        }else{
            try{

                reportFile = files.get(0);
            }catch (Exception e){}
        }

    }else{

        try{

            reportFile = files.get(0);
        }catch (Exception e){}


    }

    if(!lastVisitedDirectory.equals(System.getProperty("user.home"))){
        userPrefs.put("Chemical Path", System.getProperty("user.home"));
    }else
        lastVisitedDirectory = userPrefs.get("Chemical Path","");


    userPrefs.put("Chemical Path", lastVisitedDirectory);





    if(reportFile!=null)
         if(reportFile.exists()){

             ChemicalFiles.add(reportFile);
             list = FXCollections.observableArrayList();
             String G = reportFile.getName().replace(".xlsx","");
             list.add(G+" Chemical Data No." + ChemicalFiles.size());

             for(String s:list)
                 SampleTbl.getItems().add(s);
         }


    }






@FXML
public void ClearAll() {


SampleTbl.getItems().clear();

ChemicalFiles.clear();
    HardnessFile.clear();
    TensileFiles.clear();
    MicroHardnessFile.clear();
    ImpactFiles.clear();

}


}












