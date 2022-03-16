package sample.Controllers;

import com.github.daytron.simpledialogfx.data.DialogResponse;
import com.github.daytron.simpledialogfx.data.HeaderColorStyle;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import com.jfoenix.controls.JFXCheckBox;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;


import sample.Database.MemoryHandler;
import sample.Database.NitStandardParam;
import sample.Database.StandardDB;
import sample.EditCell;
import sample.Filter.FilterSupport;
import sample.Main;
import sample.TestPojos.ParamObject;
import sample.TestPojos.PathRecorder;
import sample.TestPojos.TableModel;
import sample.Tree.StandardParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class StandardSelectController implements Initializable {



 static public boolean StandardSelected = false;

    static public PathRecorder rec = new PathRecorder();



    ToggleGroup stdGroup = new ToggleGroup();
    ToggleGroup generalGroup = new ToggleGroup();
    ToggleGroup SpecificGroup = new ToggleGroup();
    ToggleGroup ClassGroup = new ToggleGroup();

    ToggleGroup ShapeGroup = new ToggleGroup();
    ToggleGroup SizeGroup = new ToggleGroup();





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
    TableColumn<String,String> SelectedClm;

    @FXML
    TableView SelectedTbl;

    @FXML
    TableColumn<TableModel,String> SizeClm;

    @FXML
    TableColumn<TableModel,String> ShapeClm;


    @FXML
    TableColumn<TableModel,String> SpecificClm;

    @FXML
    TableColumn<TableModel,String> ClassClm;

    @FXML
    TableView<TableModel> StandardTbl;

    @FXML
    TableView<TableModel> ShapeTbl;


    @FXML
    TableView<TableModel> SizeTbl;


    @FXML
    TableView<TableModel> SpecificTbl;


    @FXML
    TableView<TableModel> ClassTbl;


    @FXML
    TableColumn<TableModel,String> StandardClm;

    @FXML
    TableView<TableModel> GeneralTbl;


    @FXML
    TableColumn<TableModel,String> GeneralClm;


    ObservableList<String> SelectedList = FXCollections.observableArrayList();

    ArrayList<String> Standards = new ArrayList<>();

    ArrayList<String> Materials = new ArrayList<>();

    String OneMaterial;

    String OneStandard;

    String OneGeneralMaterial;


    public static ArrayList<ParamObject> deList = new ArrayList<>();

    public  ArrayList<ArrayList<ParamObject>> AllList = new ArrayList<>();
@FXML
    public JFXCheckBox OrderBox;

    boolean Order = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


//        LoggedName.setText(LoginController.LogName);


        OrderBox.setOnAction(event -> {


                    if (OrderBox.isSelected()) {

                        Order = false;

                    }
                });


        SelectedClm.setCellFactory(TextFieldTableCell.forTableColumn());

        SelectedClm.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));

        SelectedTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");


      //  setKeyHeaderEvents();

        StandardTbl.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
        SpecificTbl.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
        GeneralTbl.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
        ClassTbl.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
        SizeTbl.setStyle("-fx-font-weight: bold;-fx-font-size: 14");
        ShapeTbl.setStyle("-fx-font-weight: bold;-fx-font-size: 14");


        StandardClm.impl_setReorderable(false);
        GeneralClm.impl_setReorderable(false);
        SpecificClm.impl_setReorderable(false);
        ClassClm.impl_setReorderable(false);
        SizeClm.impl_setReorderable(false);
        ShapeClm.impl_setReorderable(false);
        ClassSelClm.impl_setReorderable(false);
        StandardSelClm.impl_setReorderable(false);
        GeneralSelClm.impl_setReorderable(false);
        ShapeSelClm.impl_setReorderable(false);
        SizeSelClm.impl_setReorderable(false);
        SpecificSelClm.impl_setReorderable(false);

        setClmUp(StandardClm);
        setClmUp(GeneralClm);
        setClmUp(SpecificClm);
        setClmUp(ClassClm);
        setClmUp(SizeClm);
        setClmUp(ShapeClm);

        setTableUp(StandardTbl);
        setTableUp(GeneralTbl);
        setTableUp(SpecificTbl);
        setTableUp(ClassTbl);
        setTableUp(ShapeTbl);
        setTableUp(SizeTbl);


        PopulateTable(StandardTbl,StandardClm,stdGroup,0);




    }




    public void MakeCarbonFirst(ArrayList<ArrayList<ParamObject>> M){

        for(ArrayList<ParamObject> P:M){

            for(ParamObject J:new ArrayList<>(P)){

                  if(J.getParamName().equals("C")){

                      ParamObject newP = new ParamObject();

                      newP.setTestName(J.getTestName());
                      newP.setParamName(J.getParamName());
                      newP.setMin(J.getMin());
                      newP.setMax(J.getMax());

                      P.remove(J);

                      P.add(0,newP);

                  }



            }



        }


        for(ArrayList<ParamObject> P:M){

            for(ParamObject J:new ArrayList<>(P)){

                if(J.getParamName().equals("Each")){

                    ParamObject newP = new ParamObject();

                    newP.setTestName(J.getTestName());
                    newP.setParamName(J.getParamName());
                    newP.setMin(J.getMin());
                    newP.setMax(J.getMax());

                    P.remove(J);

                    P.add(newP);

                }



            }



        }

        for(ArrayList<ParamObject> P:M){

            for(ParamObject J:new ArrayList<>(P)){

                if(J.getParamName().equals("Total")){

                    ParamObject newP = new ParamObject();

                    newP.setTestName(J.getTestName());
                    newP.setParamName(J.getParamName());
                    newP.setMin(J.getMin());
                    newP.setMax(J.getMax());

                    P.remove(J);

                    P.add(newP);

                }



            }



        }

        for(ArrayList<ParamObject> P:M){

            for(ParamObject J:new ArrayList<>(P)){

                if(J.getParamName().equals("Others")){


                    if(!J.getMin().equals("-")||!J.getMax().equals("-")){

                        ParamObject newP = new ParamObject();

                        newP.setTestName(J.getTestName());
                        newP.setParamName(J.getParamName());
                        newP.setMin(J.getMin());
                        newP.setMax(J.getMax());


                        P.remove(J);

                        P.add(newP);

                    } else {

                        P.remove(J);

                    }



                }



            }



        }






    }


    public ArrayList<ArrayList<ParamObject>> OrganizeLists(ArrayList<ArrayList<ParamObject>> List){

        ArrayList<ArrayList<ParamObject>> OrderedList = new ArrayList<>();
         ArrayList<String> newMaterials = new ArrayList<>();
        ArrayList<String> newStandards = new ArrayList<>();




        int Max = 0;

        if(!List.isEmpty()){

            Max = List.get(0).size();

        }

        for(ArrayList<ParamObject> L:List){

            if(L.size()>Max){

                int n = List.indexOf(L);

                newMaterials.add(0,Materials.get(List.indexOf(L)));
                newStandards.add(0,Standards.get(List.indexOf(L)));

                OrderedList.add(0,L);
                Max = L.size();


            } else {
             if(List.size()>0)
                OrderedList.add(L);

                int n = List.indexOf(L);

                newMaterials.add(Materials.get(List.indexOf(L)));
                newStandards.add(Standards.get(List.indexOf(L)));
            }




        }


        Materials = newMaterials;
        Standards = newStandards;

       return OrderedList;




    }

@FXML
 public void SelectStd() throws FileNotFoundException {


        if(stdGroup.getSelectedToggle()!=null&&generalGroup.getSelectedToggle()!=null
    &&SpecificGroup.getSelectedToggle()!=null&&ShapeGroup.getSelectedToggle()!=null&&SizeGroup.getSelectedToggle()!=null
        &&ClassGroup.getSelectedToggle()!=null){

            deList.clear();
            OneMaterial = OneMaterial + " " + OneGeneralMaterial.toLowerCase();

           if(Materials.size()!=0)
            Materials.add(Materials.size(),OneMaterial);
           else
               Materials.add(OneMaterial);

            if(Standards.size()!=0)
            Standards.add(Standards.size(),OneStandard);
              else
                Standards.add(OneStandard);


            if(SelectedList.size()==0)
                SelectedList.add(Standards.get(Standards.size()-1) +"+" + Materials.get(Materials.size()-1));
            else
                SelectedList.add(SelectedList.size()-1,Standards.get(Standards.size()-1) +"+" + Materials.get(Materials.size()-1));


            //TODO here will be the code transferring the data from the database to the
          //TODO data from the database to the CentralSelection

            int ID = rec.getSizeID();






            try{


                ArrayList<NitStandardParam> T = Main.LoadedToMemory;

                ArrayList<NitStandardParam> TestList = new ArrayList<>();

                for(NitStandardParam P:T){

                    if(P.getParent_id()==ID){

                        TestList.add(P);

                        if(TestList.size()==6)
                            break;

                    }


                }

                int BendID = 0;
                int TensileID = 0 ;
                int HardnessID = 0;
                int MicroID = 0 ;
                int ChemicalID = 0;
                int ImpactID = 0;

                ArrayList<NitStandardParam> TensileParams = new ArrayList<>();
                ArrayList<NitStandardParam> BendParams = new ArrayList<>();
                ArrayList<NitStandardParam> ImpactParams = new ArrayList<>();
                ArrayList<NitStandardParam> ChemicalParams = new ArrayList<>();
                ArrayList<NitStandardParam> HardnessParams = new ArrayList<>();
                ArrayList<NitStandardParam> MicroParams = new ArrayList<>();




                for(NitStandardParam P:TestList){


                    if(P.getName().contains("Bend")){

                        BendID = P.getId();

                    }else if(P.getName().contains("Chemical")){

                        ChemicalID = P.getId();


                    }else if(P.getName().contains("Micro-Hardness")){
                        MicroID = P.getId();


                    }else if(P.getName().contains("Hardness")){

                        HardnessID = P.getId();

                    }else if(P.getName().contains("Impact")){

                        ImpactID = P.getId();


                    }else if(P.getName().contains("Tensile")){

                        TensileID = P.getId();


                    }



                }


                for(NitStandardParam P:T){

                    if(P.getParent_id()==BendID){

                        BendParams.add(P);

                    }else if(P.getParent_id()==TensileID){

                        TensileParams.add(P);


                    }else if(P.getParent_id()==ImpactID){

                        ImpactParams.add(P);

                    }else if(P.getParent_id()==MicroID){

                        MicroParams.add(P);


                    }else if(P.getParent_id()==HardnessID){

                        HardnessParams.add(P);


                    }else if(P.getParent_id()== ChemicalID){

                        ChemicalParams.add(P);


                    }




                }





                OptimizedDBUse(TensileParams,T,"Tensile");
                OptimizedDBUse(BendParams,T,"Bend");
                OptimizedDBUse(ImpactParams,T,"Impact");
                OptimizedDBUse(ChemicalParams,T,"Chemical");
                OptimizedDBUse(HardnessParams,T,"Hardness");
                OptimizedDBUse(MicroParams,T,"Micro-Hardness");


                Main.selection.setStandardData(deList);
                ArrayList<ParamObject> p = new ArrayList<>();

                for(ParamObject e:deList){

                    p.add(e);
                }


                if(Order==true){

                    if(ChemicalParametersSimillar(AllList,p)){




                    }



                    AllList.add(p);

                    MakeCarbonFirst(AllList);
                    AllList = OrganizeLists(AllList);


                    SelectedTbl.getItems().clear();

                    for(String d:SelectedList){

                        SelectedTbl.getItems().add(d);
                    }


                    Main.selection.setStandards(new ArrayList<>(Standards));
                    Main.selection.setMaterials(Materials);


                    Main.selection.setAllStandardsList(AllList);


                    StandardSelected = true;

                    if(Main.Hebrew==true){ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                            DialogType.INFORMATION,
                            "התקן נבחר",
                            "התקן נבחר בהצלחה");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);


                        SizeGroup.getSelectedToggle().setSelected(false);
                        ShapeGroup.getSelectedToggle().setSelected(false);
                        stdGroup.getSelectedToggle().setSelected(false);
                        generalGroup.getSelectedToggle().setSelected(false);
                        SpecificGroup.getSelectedToggle().setSelected(false);
                        ClassGroup.getSelectedToggle().setSelected(false);
                        GeneralTbl.getItems().clear();
                        SpecificTbl.getItems().clear();
                        ClassTbl.getItems().clear();
                        ShapeTbl.getItems().clear();
                        SizeTbl.getItems().clear();

                        dialog.showAndWait();}else{ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                            DialogType.INFORMATION,
                            "The standard was selected",
                            "The standard was selected");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                        dialog.showAndWait();

                        SizeGroup.getSelectedToggle().setSelected(false);
                        ShapeGroup.getSelectedToggle().setSelected(false);
                        stdGroup.getSelectedToggle().setSelected(false);
                        generalGroup.getSelectedToggle().setSelected(false);
                        SpecificGroup.getSelectedToggle().setSelected(false);
                        ClassGroup.getSelectedToggle().setSelected(false);

                        GeneralTbl.getItems().clear();
                        SpecificTbl.getItems().clear();
                        ClassTbl.getItems().clear();
                        ShapeTbl.getItems().clear();
                        SizeTbl.getItems().clear();

                    }






                }else{

                    if(CheckSTDS(AllList,p)==true){
                        AllList.add(p);
                        MakeCarbonFirst(AllList);
                        Main.selection.setAllStandardsList(AllList);


                        StandardSelected = true;


                        SelectedTbl.getItems().clear();

                        for(String d:SelectedList){

                            SelectedTbl.getItems().add(d);
                        }

                        if(Main.Hebrew==true){ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                                DialogType.INFORMATION,
                                "התקן נבחר",
                                "התקן נבחר בהצלחה");
                            dialog.setFontSize(20);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                            SizeGroup.getSelectedToggle().setSelected(false);
                            ShapeGroup.getSelectedToggle().setSelected(false);
                            stdGroup.getSelectedToggle().setSelected(false);
                            generalGroup.getSelectedToggle().setSelected(false);
                            SpecificGroup.getSelectedToggle().setSelected(false);
                            ClassGroup.getSelectedToggle().setSelected(false);
                            GeneralTbl.getItems().clear();
                            SpecificTbl.getItems().clear();
                            ClassTbl.getItems().clear();
                            ShapeTbl.getItems().clear();
                            SizeTbl.getItems().clear();

                            dialog.showAndWait();}else{ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                                DialogType.INFORMATION,
                                "The standard was selected",
                                "The standard was selected");
                            dialog.setFontSize(20);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                            dialog.showAndWait();

                            SizeGroup.getSelectedToggle().setSelected(false);
                            ShapeGroup.getSelectedToggle().setSelected(false);
                            stdGroup.getSelectedToggle().setSelected(false);
                            generalGroup.getSelectedToggle().setSelected(false);
                            SpecificGroup.getSelectedToggle().setSelected(false);
                            ClassGroup.getSelectedToggle().setSelected(false);

                            GeneralTbl.getItems().clear();
                            SpecificTbl.getItems().clear();
                            ClassTbl.getItems().clear();
                            ShapeTbl.getItems().clear();
                            SizeTbl.getItems().clear();

                        }


                    } else{

                        Dialog dialog;

                        if(Main.Hebrew==true){

                            dialog = new Dialog(
                                    DialogType.CONFIRMATION,
                                    "הוספת תקן",
                                    "התקן הנוסף מכיל ערכים רבים מהתקן/תקנים שכבר נבחרו, מצב זה יכול לגרום לחוסר בנתונים בבניית הדוח",
                                    "בחר YES להמשיך(הערה: מומלץ לבחור את התקן עם כמות הפרמטרים הגדולה תחילה)");
                            dialog.setFontSize(20);
                            dialog.showAndWait();}

                        else{

                            dialog = new Dialog(
                                    DialogType.CONFIRMATION,
                                    "Add Standard",
                                    "The standard you want to add has more parameters than previous selected standards",
                                    "Press YES to add this standard(It is advised to add the standard with more parameters first)");
                            dialog.setFontSize(20);
                            dialog.showAndWait();}



                        if (dialog.getResponse() == DialogResponse.YES) {}

                        AllList.add(p);
                        MakeCarbonFirst(AllList);
                        Main.selection.setAllStandardsList(AllList);


                        StandardSelected = true;





                        SelectedTbl.getItems().clear();

                        for(String d:SelectedList){

                            SelectedTbl.getItems().add(d);
                        }

                        if(Main.Hebrew==true){  dialog = new Dialog(
                                DialogType.INFORMATION,
                                "התקן נבחר",
                                "התקן נבחר בהצלחה");
                            dialog.setFontSize(20);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                            SizeGroup.getSelectedToggle().setSelected(false);
                            ShapeGroup.getSelectedToggle().setSelected(false);
                            stdGroup.getSelectedToggle().setSelected(false);
                            generalGroup.getSelectedToggle().setSelected(false);
                            SpecificGroup.getSelectedToggle().setSelected(false);
                            ClassGroup.getSelectedToggle().setSelected(false);
                            GeneralTbl.getItems().clear();
                            SpecificTbl.getItems().clear();
                            ClassTbl.getItems().clear();
                            ShapeTbl.getItems().clear();
                            SizeTbl.getItems().clear();

                            dialog.showAndWait();}else{

                            dialog = new Dialog(
                                    DialogType.INFORMATION,
                                    "The standard was selected",
                                    "The standard was selected");
                            dialog.setFontSize(20);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                            dialog.showAndWait();

                            SizeGroup.getSelectedToggle().setSelected(false);
                            ShapeGroup.getSelectedToggle().setSelected(false);
                            stdGroup.getSelectedToggle().setSelected(false);
                            generalGroup.getSelectedToggle().setSelected(false);
                            SpecificGroup.getSelectedToggle().setSelected(false);
                            ClassGroup.getSelectedToggle().setSelected(false);

                            GeneralTbl.getItems().clear();
                            SpecificTbl.getItems().clear();
                            ClassTbl.getItems().clear();
                            ShapeTbl.getItems().clear();
                            SizeTbl.getItems().clear();

                        }

                    }
                }





            }catch (Exception e){


                if(Main.Hebrew==true){


                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "מסד הנתונים בשימוש במחשב אחר, אנא נסה שוב בעוד 10 שניות",
                            "אנא נסה שוב בעוד 10 שניות");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();




                } else{

                    Dialog dialog = new Dialog(
                            DialogType.ERROR,
                            "The Database is in use, Try again in 10 seconds",
                            "Try again in 10 seconds");
                    dialog.setFontSize(22);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();

                }






                File file = new File(Paths.get("").toAbsolutePath()+"\\Log.txt");
                PrintStream ps = new PrintStream(file);
                e.printStackTrace(ps);
                ps.close();




            }










     /*


            ArrayList<StandardParam> list;

            try{

                ArrayList<StandardParam> Tests = db.GetListByID(ID);

                for(StandardParam sd:Tests){



                    list =  db.GetListByID(sd.getId());


                    for(StandardParam l:list){


                        if(db.haveChildren(l.getId())) {

                            TableModel M1 = db.getNeededList(l.getId()).get(0);
                            TableModel M2 = db.getNeededList(M1.getId()).get(0);


                              //Here i build the new param and get it into the list

                            if (!M1.getPropertyName().equals("-") ||
                                    !M2.getPropertyName().equals("-")) {
                                ParamObject param = new ParamObject();

                                param.setTestName(sd.getName());

                                param.setParamName(l.getName());

                                param.setMin(M1.getPropertyName());
                                param.setMax(M2.getPropertyName());

                                deList.add(param);

                            }

                        }

                    }







                }












            }catch (Exception e){







            }



    */







        }else{

            if(Main.Hebrew==true){  com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "בכדי לבחור תקן חייבת להעשות בחירה בכל הטבלאות",
                    "אנא בחר ערכים בכל הטבלאות");
                dialog.setFontSize(20);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{  com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "All parameters have to be selected!",
                    "All parameters have to be selected!");
                dialog.setFontSize(20);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}



        }








    }

public boolean CheckSTDS(ArrayList<ArrayList<ParamObject>> List,ArrayList<ParamObject> newList){



        if(List.size()>0){

            for(ArrayList<ParamObject> J:List){


                if(J.size()<newList.size()){

                    return false;


                }

            }




        }


       return true;

}


@FXML
public void UnselectStd(){

        SelectedTbl.getItems().clear();
        SelectedList.clear();
        Standards.clear();
        Materials.clear();

        AllList.clear();
        deList.clear();
        Main.selection.getStandardData().clear();
    StandardSelected = false;

    if(Main.Hebrew==true){ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
            DialogType.INFORMATION,
            "בחירת התקן בוטלה!",
            "בחירת התקן בוטלה!");
        dialog.setFontSize(20);
        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

        dialog.showAndWait();}else{ com.github.daytron.simpledialogfx.dialog.Dialog dialog = new Dialog(
            DialogType.INFORMATION,
            "Standard was unselected!",
            "Standard was unselected!");
        dialog.setFontSize(20);
        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

        dialog.showAndWait();}



}



    public void setKeyHeaderEvents(){

        SelectedTbl.setOnKeyReleased((KeyEvent t)-> {
            KeyCode key=t.getCode();
            if (key==KeyCode.DELETE){
                String header =  (String)SelectedTbl.getSelectionModel().selectedItemProperty().get();

                Dialog dialog1;

                if(Main.Hebrew==true){dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "ביטול תקן",
                        "האם ברצונך לבטל השוואה לתקן זה?",
                        "בחר YES כדי לבטל השוואה לתקן");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}else{dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "Cancel Standard",
                        "Are you sure you want Cancel this Standard?",
                        "Press YES to Cancel Standard");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}

                if (dialog1.getResponse() == DialogResponse.YES) {

                     SelectedList.remove(header);

                     SelectedTbl.getItems().clear();

                     for(String n:SelectedList){

                         SelectedTbl.getItems().add(n);
                     }

                     int index = SelectedTbl.getSelectionModel().getSelectedIndex();

                     AllList.remove(SelectedTbl.getSelectionModel().getSelectedIndex());

                }



            } else {
                // ... user chose CANCEL or closed the dialog
            }
        });



    }



    public void setTableUp(TableView table){


        alwaysShowVerticalScroll(table);


        table.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if( event.getCode() == KeyCode.ENTER) {
//                  event.consume(); // don't consume the event or else the values won't be updated;
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( table.getEditingCell() == null) {
                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                        TablePosition focusedCellPosition = table.getFocusModel().getFocusedCell();
                        table.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });

        table.setEditable(false);
        table.getSelectionModel().setCellSelectionEnabled(true);


    }

    public void setClmUp(TableColumn clm){



       clm.setCellValueFactory(
               new PropertyValueFactory<TableModel,String>("PropertyName")
       );



        clm.setCellFactory(tc -> {

            TableCell<TableModel, String> cell =new TableCell<TableModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item);
                }
            };

            // maybe choose a more suitable name here...
            cell.getStyleClass().add("first-name-col");
            return cell ;
        });

        FilterSupport.addFilter(clm);

        clm.setCellFactory(column -> EditCell.createStringEditCell());


        clm.setCellFactory(tc -> {
            TableCell<TableModel, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            if(Main.Hebrew==true)
                text.setTextAlignment(TextAlignment.RIGHT);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(clm.widthProperty().subtract(10));
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });




    }


    public ObservableList<TableModel> StandardToList(List<TableModel> WantToShowList){

        ObservableList<TableModel> list = FXCollections.observableArrayList();

        //ToDo
        for(TableModel nd : new LinkedList<>(WantToShowList))
            list.add(nd);

        return list;
    }






    private void addRadioButtonToPrepTable(ToggleGroup Group, TableColumn clm) {

        Callback<TableColumn<TableModel, String>, TableCell<TableModel, String>> cellFactory = new Callback<TableColumn<TableModel, String>, TableCell<TableModel, String>>() {

            public TableCell<TableModel, String> call(final TableColumn<TableModel, String> param) {
                final TableCell<TableModel, String> cell = new TableCell<TableModel, String>() {

                    private RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(Group);

                        btn.setOnAction((ActionEvent event) -> {

                            String text = clm.getText();

                            TableModel data = getTableView().getItems().get(getIndex());


                            switch (text) {

                                case ("Standard"): {



                                    OneStandard = data.getPropertyName();

                                    rec.setStandardID(data.getId());

                                    PopulateTable(GeneralTbl, GeneralClm, generalGroup, rec.getStandardID());

                                    ClearTable(SpecificTbl);
                                    ClearTable(ClassTbl);
                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);


                                }
                                break;
                                case ("General Material"): {

                                    rec.setGeneralID(data.getId());
                                    OneGeneralMaterial = data.getPropertyName();
                                    PopulateTable(SpecificTbl, SpecificClm, SpecificGroup, rec.getGeneralID());

                                    ClearTable(ClassTbl);
                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);

                                }
                                break;
                                case ("Specific Material"): {

                                    rec.setSpecificID(data.getId());
                                    OneMaterial = data.getPropertyName();

                                    PopulateTable(ClassTbl, ClassClm, ClassGroup, rec.getSpecificID());

                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);


                                }
                                break;
                                case ("Class Property\\General Info"): {
                                    rec.setClassPropertyID(data.getId());
                                    PopulateTable(ShapeTbl, ShapeClm, ShapeGroup, rec.getClassPropertyID());
                                    ClearTable(SizeTbl);

                                }
                                break;
                                case ("Shape"): {
                                    rec.setShapeID(data.getId());

                                    PopulateTable(SizeTbl, SizeClm, SizeGroup, rec.getShapeID());

                                }
                                break;
                                case ("Size"): {
                                    rec.setSizeID(data.getId());
                                }
                                break;
                                case ("תקן"): {

                                //    Main.selection.setStandardName(data.getPropertyName());
                                    OneStandard = data.getPropertyName();
                                    rec.setStandardID(data.getId());


                                    PopulateTable(GeneralTbl, GeneralClm, generalGroup, rec.getStandardID());

                                    ClearTable(SpecificTbl);
                                    ClearTable(ClassTbl);
                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);


                                }
                                break;
                                case ("חומר כללי"): {

                                    rec.setGeneralID(data.getId());
                                    OneGeneralMaterial = data.getPropertyName();
                                    PopulateTable(SpecificTbl, SpecificClm, SpecificGroup, rec.getGeneralID());

                                    ClearTable(ClassTbl);
                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);

                                }
                                break;
                                case ("חומר ספציפי"): {

                                    rec.setSpecificID(data.getId());
                                    OneMaterial = data.getPropertyName();

                                    PopulateTable(ClassTbl, ClassClm, ClassGroup, rec.getSpecificID());

                                    ClearTable(ShapeTbl);
                                    ClearTable(SizeTbl);


                                }
                                break;
                                case ("חוזק בורג/מידע כללי"): {
                                    rec.setClassPropertyID(data.getId());
                                    PopulateTable(ShapeTbl, ShapeClm, ShapeGroup, rec.getClassPropertyID());
                                    ClearTable(SizeTbl);

                                }
                                break;
                                case ("צורה"): {
                                    rec.setShapeID(data.getId());

                                    PopulateTable(SizeTbl, SizeClm, SizeGroup, rec.getShapeID());

                                }
                                break;
                                case ("מידות"): {
                                    rec.setSizeID(data.getId());
                                }
                                break;

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

            case ("תקן"): {

                StandardSelClm.setCellFactory(cellFactory);


            }
            break;
            case ("חומר כללי"): {

                GeneralSelClm.setCellFactory(cellFactory);


            }
            break;

            case("חומר ספציפי"):{

                SpecificSelClm.setCellFactory(cellFactory);


            }break;
            case("מידות"):{

                SizeSelClm.setCellFactory(cellFactory);

            }break;
            case("חוזק בורג/מידע כללי"):{

                ClassSelClm.setCellFactory(cellFactory);

            }break;
            case("צורה"):{
                ShapeSelClm.setCellFactory(cellFactory);


            }break;

            case ("Standard"): {

                StandardSelClm.setCellFactory(cellFactory);


            }
            break;
            case ("General Material"): {

                GeneralSelClm.setCellFactory(cellFactory);


            }
            break;

            case("Specific Material"):{

                SpecificSelClm.setCellFactory(cellFactory);


            }break;
            case("Size"):{

                SizeSelClm.setCellFactory(cellFactory);

            }break;
            case("Class Property\\General Info"):{

                ClassSelClm.setCellFactory(cellFactory);

            }break;
            case("Shape"):{
                ShapeSelClm.setCellFactory(cellFactory);


            }break;

        }

    }

    public void ClearTable(TableView table){


        FilterSupport.getItems(table).clear();

    }

    public void PopulateTable(TableView table,TableColumn clm,ToggleGroup g,int NeededParetID) {


        FilterSupport.getItems(table).clear();


        LinkedList<TableModel> Linked = new LinkedList<>(MemoryHandler.getNeededList(NeededParetID));

        ObservableList<TableModel> m = StandardToList(Linked);

        for (TableModel sd : new LinkedList<>(m)) {
            addRadioButtonToPrepTable(g,clm);
            FilterSupport.getItems(table).add(sd);
                 }





    }
@FXML
public void Refresh() throws FileNotFoundException {


        try{

        SpecificTbl.getItems().clear();
        GeneralTbl.getItems().clear();
        SizeTbl.getItems().clear();
        ShapeTbl.getItems().clear();
        ClassTbl.getItems().clear();

        PopulateTable(StandardTbl,StandardSelClm,stdGroup,0);

    }catch(Exception e){

        File file = new File(Paths.get("").toAbsolutePath()+"\\Log.txt");
        PrintStream ps = new PrintStream(file);
            e.printStackTrace(ps);
        ps.close();

    }





}


public void OptimizedDBUse(ArrayList<NitStandardParam> TestArray,ArrayList<NitStandardParam> AllList,String TestName){

    for(NitStandardParam Tens:TestArray){

        for(NitStandardParam P:AllList){

            if(P.getParent_id()==Tens.getId()){


                for(NitStandardParam Q:AllList){

                    if(P.getId()==Q.getParent_id()){

                        if(!(P.getName().equals("-")&&Q.getName().equals("-"))){


                            ParamObject param = new ParamObject();

                            param.setTestName(TestName);
                            param.setParamName(Tens.getName());
                            param.setMax(Q.getName());
                            param.setMin(P.getName());


                            deList.add(param);

                            break;
                        }





                    }


                }




            }




        }

    }


}


public boolean ChemicalParametersSimillar(ArrayList<ArrayList<ParamObject>> List,ArrayList<ParamObject> NewSTD){


    ArrayList<ParamObject> LongestList;
    boolean AllIsFine = true;

if(List.size()>0){
    LongestList = new ArrayList<>(List.get(0));


    for(ArrayList<ParamObject> P:List){


        if(P.size()>LongestList.size()){

            LongestList = new ArrayList<>(P);
        }

    }



    for(ParamObject l:LongestList){
        for(ParamObject k:NewSTD){

            if(l.getParamName().contains("Chemical")){

                if(k.getParamName().contains("Chemical")){





                }



            }




        }



    }


}






    return true;



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



}
