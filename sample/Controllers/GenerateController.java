package sample.Controllers;

import com.github.daytron.simpledialogfx.data.DialogResponse;
import com.github.daytron.simpledialogfx.data.HeaderColorStyle;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;


import com.github.mayconmfl.log4jconfigurator.interfaces.Log4jConfigurator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;


import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.html.AbstractHtmlExporter;
import org.docx4j.convert.out.html.HtmlExporterNG2;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.ImageType;
import pl.jsolve.templ4docx.variable.ImageVariable;
import pl.jsolve.templ4docx.variable.Variables;
import sample.Database.ReportDB;
import sample.Filter.FilterSupport;
import sample.Main;
import sample.StartGenerator;
import sample.TestPojos.SignatureTableModel;
import sample.TestPojos.TableModel;


import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


public class GenerateController implements Initializable {


    public static boolean NoDB = false;

    ToggleGroup ReportGroup = new ToggleGroup();

    ReportDB db = new ReportDB();

    public static String report;

    public String ReportName;

    public String ReportPath;


    private final static int rowsPerPage = 500;


    StartGenerator generator = new StartGenerator();

@FXML
    WebView DocView;



    Alert alert;

    @FXML
    Pagination GeneratePager;


    @FXML
    TableView<SignatureTableModel> ReportTbl;

    @FXML
    TableColumn<SignatureTableModel,String> ReportClm;

    @FXML
    TableColumn ReportSelClm;

    InputStream in = null;


    static Variables variables = new Variables();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        ReportClm.impl_setReorderable(false);
        ReportSelClm.impl_setReorderable(false);


        setKeyEvents();
        db.CreateDb();
        setClmUp(ReportClm);

        ReportTbl.setStyle("-fx-font-family: ariel;-fx-font-weight: bold;-fx-font-size: 14");

        PopulateTable(ReportTbl,ReportSelClm,ReportGroup,6);

        FilterSupport.addFilter(ReportClm);


        GeneratePager.setPageCount(db.queryForReports().size() / rowsPerPage + 1);
        GeneratePager.setPageFactory(this::createPage);

    }




    @FXML
    public void SignReport(){




        if(ReportPath!=null){

            if(LoginController.user.getSignaturePath()!=null){




                Docx docx = new Docx(ReportPath);

                if(LoginController.user.isApprover()){


                    String g = LoginController.user.getSignaturePath();

                    docx.setVariablePattern(new VariablePattern("#{", "}"));

                    variables.addImageVariable(new ImageVariable("#{AppSign}", LoginController.user.getSignaturePath(), 100, 60));
                    docx.fillTemplate(variables);

                    try{

                        docx.save(ReportPath);

                    }catch (Exception e){

                     if(Main.Hebrew==true){  Dialog dialog = new Dialog(
                             DialogType.ERROR,
                             "הדוח פתוח באחד המחשבים",
                             "הדוח פתוח באחד המחשבים");
                         dialog.setFontSize(20);
                         dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                         dialog.showAndWait();}else{  Dialog dialog = new Dialog(
                             DialogType.ERROR,
                             "Report is open on some computer",
                             "Report is open on some computer");
                         dialog.setFontSize(20);
                         dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                         dialog.showAndWait();}




                    }
                    if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                            DialogType.INFORMATION,
                            "הדוח חתום!",
                            "הדוח חתום!");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                        dialog.showAndWait();
                    }else{ Dialog dialog = new Dialog(
                            DialogType.INFORMATION,
                            "Report is Signed!",
                            "Report is Signed!");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                        dialog.showAndWait();
                    }



                }
                  else
                {


                    String g = LoginController.user.getSignaturePath();
                    docx.setVariablePattern(new VariablePattern("#{", "}"));

                        ImageVariable img = new ImageVariable("#{PrepSign}", LoginController.user.getSignaturePath(), ImageType.PNG, 100, 60);

                    variables.addImageVariable(img);

                    docx.fillTemplate(variables);
                    try{

                        docx.save(ReportPath);

                    }catch (Exception e){

                        if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                                DialogType.ERROR,
                                "הדוח פתוח באחד המחשבים",
                                "סגור את הדוח כדי לחתום");
                            dialog.setFontSize(20);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                                DialogType.ERROR,
                                "Report is open on some computer",
                                "Close report before signature");
                            dialog.setFontSize(20);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();}




                    }
                    if(Main.Hebrew==true){  Dialog dialog = new Dialog(
                            DialogType.INFORMATION,
                            "הדוח חתום!",
                            "הדוח חתום!");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                        dialog.showAndWait();}else{  Dialog dialog = new Dialog(
                            DialogType.INFORMATION,
                            "Report Signed!",
                            "Report Signed!");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_BLUE);

                        dialog.showAndWait();}






                }


            }else{

                if(Main.Hebrew==true){Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "אין חתימה",
                        "התחבר למשתמש עם חתימה");
                    dialog.setFontSize(20);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}else{Dialog dialog = new Dialog(
                        DialogType.ERROR,
                        "No Signature!",
                        "Login as User with Signature!");
                    dialog.setFontSize(20);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}




            }

        } else{

            if(Main.Hebrew==true){         Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "לא נבחר דוח",
                    "בחר דוח");
                dialog.setFontSize(20);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{         Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "No Report Selected!",
                    "Select Report");
                dialog.setFontSize(20);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}



        }




    }



    public ObservableList<SignatureTableModel> ReportToList(ArrayList<SignatureTableModel> List){

            ObservableList<SignatureTableModel> list = FXCollections.observableArrayList();

            //ToDo
            for(SignatureTableModel nd : List)
                list.add(nd);

            return list;


    }

    public void PopulateTable(TableView table,TableColumn clm,ToggleGroup g,int NeededParetID) {

        FilterSupport.getItems(table).clear();

        ObservableList<SignatureTableModel> m = ReportToList(db.queryForReports());

        sortListReverse(m);

        for (SignatureTableModel sd : m) {
            addRadioButtonToPrepTable(g,clm);

            FilterSupport.getItems(table).add(sd);

        }
    }

    private void sortListReverse(List<SignatureTableModel> list) {
        Collections.sort(list, new Comparator<SignatureTableModel>() {
            public int compare(SignatureTableModel ideaVal1, SignatureTableModel ideaVal2) {
                // avoiding NullPointerException in case name is null
                Long idea1 = new Long(ideaVal1.getId());
                Long idea2 = new Long(ideaVal2.getId());
                return idea2.compareTo(idea1);
            }
        });
    }




    private void addRadioButtonToPrepTable(ToggleGroup Group, TableColumn clm) {

        Callback<TableColumn<SignatureTableModel, String>, TableCell<SignatureTableModel, String>> cellFactory = new Callback<TableColumn<SignatureTableModel, String>, TableCell<SignatureTableModel, String>>() {

            public TableCell<SignatureTableModel, String> call(final TableColumn<SignatureTableModel, String> param) {
                final TableCell<SignatureTableModel, String> cell = new TableCell<SignatureTableModel, String>() {

                    private RadioButton btn = new RadioButton();

                    {

                        btn.setToggleGroup(Group);

                        btn.setOnAction((ActionEvent event) -> {




                            SignatureTableModel data = getTableView().getItems().get(getIndex());

                                ReportPath = data.getReportPath();
                                ReportName = data.getReportName();

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


                ReportSelClm.setCellFactory(cellFactory);


    }

    @FXML
    public void Generate() throws Exception {

        NoDB = false;

        Dialog dialog;
        Dialog dialog1;
        Dialog dialog2;

        report = Main.selection.ReportName;

        try{

            if(UserController.ReportLng!=null){


                if (Controller.reportFile != null) {
                    if (Main.selection.ReportName != null) {
                        if (!StandardSelectController.StandardSelected) {

                            if(Main.Hebrew==true){  dialog = new Dialog(
                                    DialogType.CONFIRMATION,
                                    "לא נבחר תקן",
                                    "האם להכין את הדוח ללא תקן?",
                                    "בחר YES כדי להכין את הדוח ללא תקן");
                                dialog.setFontSize(20);
                                dialog.showAndWait();}else{  dialog = new Dialog(
                                    DialogType.CONFIRMATION,
                                    "Generate Without Standard",
                                    "Generate the report without Standard?",
                                    "Press YES to Generate the report without Standard");
                                dialog.setFontSize(20);
                                dialog.showAndWait();}



                            if (dialog.getResponse() == DialogResponse.YES) {
                                // Rest of the code
                                if((Main.selection.getHeaderClms()==null)||(Main.selection.getHeaderClms().size()==0)){


                                    if(Main.Hebrew==true){    dialog1 = new Dialog(
                                            DialogType.CONFIRMATION,
                                            "לא נבחרו פרטים",
                                            "האם להכין את הדוח ללא פרטים?",
                                            "בחר YES כדי להכין את הדוח ללא פרטים");
                                        dialog1.setFontSize(20);
                                        dialog1.showAndWait();}else{    dialog1 = new Dialog(
                                            DialogType.CONFIRMATION,
                                            "Generate Without Headers",
                                            "Generate the report without Headers?",
                                            "Press YES to Generate the report without Headers");
                                        dialog1.setFontSize(20);
                                        dialog1.showAndWait();}



                                    if (dialog1.getResponse() == DialogResponse.YES) {



                                        if(Main.selection.getTensileUnit()==null){
                                            if(Main.Hebrew==true){ dialog2 = new Dialog(
                                                    DialogType.CONFIRMATION,
                                                    "לא נבחרו יחידות לבדיקת המתיחה",
                                                    "הכנת הדוח ללא בחירת יחידות לבדיקת המתיחה יכולה לגרום לדוח בו לא יופיעו תוצאות המתיחה",
                                                    "בחר YES כדי להכין דוח ללא יחידות לבדיקת המתיחה");
                                                dialog2.setFontSize(20);
                                                dialog2.showAndWait();

                                            }else{ dialog2 = new Dialog(
                                                    DialogType.CONFIRMATION,
                                                    "Generate Without Tensile Units",
                                                    "Generating report without Tensile test units might not show some data",
                                                    "Press YES to Generate the report without Tensile units");
                                                dialog2.setFontSize(20);
                                                dialog2.showAndWait();}



                                            if (dialog2.getResponse() == DialogResponse.YES) {

                                                GenerateCode();
                                                NoDB = false;

                                            }


                                        } else {

                                            GenerateCode();
                                            NoDB = false;

                                        }

                                    }


                                } else {

                                    if(Main.selection.getTensileUnit()==null) {
                                        if(Main.Hebrew==true){


                                            dialog2 = new Dialog(
                                                    DialogType.CONFIRMATION,
                                                    "לא נבחרו יחידות לבדיקת המתיחה",
                                                    "הכנת הדוח ללא בחירת יחידות לבדיקת המתיחה יכולה לגרום לדוח בו לא יופיעו תוצאות המתיחה",
                                                    "בחר YES כדי להכין דוח ללא יחידות לבדיקת המתיחה");
                                            dialog2.setFontSize(20);
                                            dialog2.showAndWait();

                                        }else{  dialog2 = new Dialog(
                                                DialogType.CONFIRMATION,
                                                "Generate Without Tensile Units",
                                                "Generating report without Tensile test units might not show some data",
                                                "Press YES to Generate the report without Tensile units");
                                            dialog2.setFontSize(20);
                                            dialog2.showAndWait();}



                                        if (dialog2.getResponse() == DialogResponse.YES) {
                                            GenerateCode();

                                        }
                                    }else {

                                        GenerateCode();
                                        NoDB = false;

                                    }

                                }


                            }


                        } else{


                            if((Main.selection.getHeaderClms()==null)||(Main.selection.getHeaderClms().size()==0)){


                                if(Main.Hebrew==true){

                                    dialog1 = new Dialog(
                                            DialogType.CONFIRMATION,
                                            "לא נבחרו פרטים",
                                            "האם להכין את הדוח ללא פרטים?",
                                            "בחר YES כדי להכין את הדוח ללא פרטים");
                                    dialog1.setFontSize(20);
                                    dialog1.showAndWait();

                                }else{ dialog1 = new Dialog(
                                        DialogType.CONFIRMATION,
                                        "Generate Without Headers",
                                        "Generate the report without Headers?",
                                        "Press YES to Generate the report without Headers");
                                    dialog1.setFontSize(20);
                                    dialog1.showAndWait();}





                                if (dialog1.getResponse() == DialogResponse.YES) {
                                    if(Main.selection.getTensileUnit()==null) {
                                        if(Main.Hebrew==true){   dialog2 = new Dialog(
                                                DialogType.CONFIRMATION,
                                                "לא נבחרו יחידות לבדיקת המתיחה",
                                                "הכנת הדוח ללא בחירת יחידות לבדיקת המתיחה יכולה לגרום לדוח בו לא יופיעו תוצאות המתיחה",
                                                "בחר YES כדי להכין דוח ללא יחידות לבדיקת המתיחה");
                                            dialog2.setFontSize(20);
                                            dialog2.showAndWait();}else{   dialog2 = new Dialog(
                                                DialogType.CONFIRMATION,
                                                "Generate Without Tensile Units",
                                                "Generating report without Tensile test units might not show some data",
                                                "Press YES to Generate the report without Tensile units");
                                            dialog2.setFontSize(20);
                                            dialog2.showAndWait();}



                                        if (dialog2.getResponse() == DialogResponse.YES) {
                                            GenerateCode();
                                            NoDB = false;


                                        }
                                    } else {

                                        GenerateCode();
                                        NoDB = false;

                                    }

                                }











                            } else {

                                if(Main.selection.getTensileUnit()==null) {
                                    if(Main.Hebrew==true){dialog2 = new Dialog(
                                            DialogType.CONFIRMATION,
                                            "לא נבחרו יחידות לבדיקת המתיחה",
                                            "הכנת הדוח ללא בחירת יחידות לבדיקת המתיחה יכולה לגרום לדוח בו לא יופיעו תוצאות המתיחה",
                                            "בחר YES כדי להכין דוח ללא יחידות לבדיקת המתיחה");
                                        dialog2.setFontSize(20);
                                        dialog2.showAndWait();}else{  dialog2 = new Dialog(
                                            DialogType.CONFIRMATION,
                                            "Generate Without Tensile Units",
                                            "Generating report without Tensile test units might not show some data",
                                            "Press YES to Generate the report without Tensile units");
                                        dialog2.setFontSize(20);
                                        dialog2.showAndWait();}



                                    if (dialog2.getResponse() == DialogResponse.YES) {
                                        GenerateCode();
                                        NoDB = false;

                                    } } else {

                                    GenerateCode();
                                    NoDB = false;


                                }







                            }





                        }


                    }else{
                        if(Main.Hebrew==true){dialog = new Dialog(
                                DialogType.ERROR,
                                "לא הוכנס שם דוח!",
                                "הכנס לדוח שם");
                            dialog.setFontSize(20);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();}else{dialog = new Dialog(
                                DialogType.ERROR,
                                "No Report Name!",
                                "Please Enter Report Name!");
                            dialog.setFontSize(20);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();}




                    }


                } else {

                    if(Main.Hebrew==true){dialog = new Dialog(
                            DialogType.ERROR,
                            "לא נבחרה תבנית!",
                            "אנא בחר תבנית");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                        dialog.showAndWait();
                    }else{dialog = new Dialog(
                            DialogType.ERROR,
                            "No Template!!!",
                            "Please Select Template!!!");
                        dialog.setFontSize(20);
                        dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                        dialog.showAndWait();
                    }


                }



            }else{

                if(Main.Hebrew==true){     dialog = new Dialog(
                        DialogType.ERROR,
                        "לא נבחרה שפה",
                        "אנא בחר שפה");
                    dialog.setFontSize(20);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}else{     dialog = new Dialog(
                        DialogType.ERROR,
                        "Please Select Report Language",
                        "Please Select Report Language");
                    dialog.setFontSize(20);
                    dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                    dialog.showAndWait();}




            }

        }catch (Exception E){

            File file = new File(Paths.get("").toAbsolutePath()+"\\Log.txt");
            PrintStream ps = new PrintStream(file);
            E.printStackTrace(ps);
            ps.close();

        }







    }




public void GenerateCode() throws Exception {

    generator.GenerateReport();


    SignatureTableModel model = new SignatureTableModel();


        model.setId(db.FindHigestID());
        model.setReportName(Main.selection.ReportName);
        model.setReportPath(Paths.get("").toAbsolutePath().toString() + "\\IKADOCS_Reports" +"\\" + Main.selection.ReportName + ".docx");
    if(NoDB==false)
        db.insertUser(model);
        in = new FileInputStream(Paths.get("").toAbsolutePath().toString() + "\\IKADOCS_Reports" +"\\" + Main.selection.ReportName + ".docx");

        XWPFDocument document = new XWPFDocument(in);




   //DocxConverter(Paths.get("").toAbsolutePath().toString() + "\\IKADOCS_Reports" +"\\" + Main.selection.ReportName + ".docx",Paths.get("").toAbsolutePath().toString() );




    DocumentConverter converter = new DocumentConverter();
    Result<String> result = converter.convertToHtml(new File(Paths.get("").toAbsolutePath().toString() + "\\IKADOCS_Reports" +"\\" + Main.selection.ReportName + ".docx"));
    String html = result.getValue(); // The generated HTML


    final WebEngine webEngine = DocView.getEngine();

    webEngine.loadContent(html);



        RefreashTbl();
    }




    public  void DocxConverter(String filePath, String destinationPath) throws Exception{



        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);



        System.out.println(filePath);

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(filePath));

        // XHTML export
        AbstractHtmlExporter exporter = new HtmlExporterNG2();
        AbstractHtmlExporter.HtmlSettings htmlSettings = new AbstractHtmlExporter.HtmlSettings();

        htmlSettings.setWmlPackage(wordMLPackage);

        htmlSettings.setImageDirPath(filePath);
        htmlSettings.setImageTargetUri(filePath);

        String htmlFilePath = Paths.get("").toAbsolutePath().toString() + "/DocxToXhtmlAndBack.html";
        OutputStream os = new java.io.FileOutputStream(htmlFilePath);

//		javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(os);
//		exporter.html(wordMLPackage, result, htmlSettings);
//		os.flush();
//		os.close();


        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_NONE);

        String stringFromFile = FileUtils.readFileToString(new File(htmlFilePath), "UTF-8");





        Document doc = Jsoup.parse(stringFromFile);
        //Elements element = doc.select("p");

       // element.forEach(e -> {
      //      if (e.hasAttr("style"))
      //          e.attr("style", "text-align:left");
      //  });


        final WebEngine webEngine = DocView.getEngine();

        webEngine.loadContent(doc.toString());


    }







          /*  convertor.anotherConvertor();

            pdf("Form02E18P");
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
@FXML
public void OpenReport()  {

    if(ReportPath!=null){

        if(Desktop.isDesktopSupported()){


        try {
            Desktop.getDesktop().open(new File(ReportPath));
        } catch (Exception  e) {
            if(Main.Hebrew==true){  Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "לא יכול למצוא קובץ",
                    "בדוק את שם הדוח");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}else{  Dialog dialog = new Dialog(
                    DialogType.ERROR,
                    "Could Not Find File!",
                    "Check Report File Name!");
                dialog.setFontSize(22);
                dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                dialog.showAndWait();}



        }

    }}else{

        if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                DialogType.ERROR,
                "לא נבחר דוח!",
                "אנא בחר דוח");
            dialog.setFontSize(22);
            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

            dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                DialogType.ERROR,
                "Select Report!",
                "Select Report!");
            dialog.setFontSize(22);
            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

            dialog.showAndWait();}






    }





}


    public void setClmUp(javafx.scene.control.TableColumn clm){

        clm.setCellValueFactory(
                new PropertyValueFactory<SignatureTableModel,String>("ReportName")
        );
        clm.setCellFactory(TextFieldTableCell.forTableColumn());

        clm.setCellFactory(tc -> {
            TableCell<SignatureTableModel, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(clm.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

    }
    @FXML
    public void RefreashTbl(){

    FilterSupport.getItems(ReportTbl).clear();


    PopulateTable(ReportTbl,ReportSelClm,ReportGroup,6);


    }

    private Node createPage(int pageIndex) {

        ReportDB db = new ReportDB();

        ArrayList<SignatureTableModel> g = db.queryForReports();

        int fromIndex = pageIndex * rowsPerPage;

        int toIndex = Math.min(fromIndex + rowsPerPage, g.size());
        ReportTbl.setItems(FXCollections.observableArrayList(g.subList(fromIndex, toIndex)));

        return new BorderPane(ReportTbl);
    }



    public void setKeyEvents(){



        ReportTbl.setOnKeyReleased((KeyEvent t)-> {
            KeyCode key=t.getCode();
            if (key==KeyCode.DELETE){
                SignatureTableModel report =  ReportTbl.getSelectionModel().selectedItemProperty().get();

                Dialog dialog1;

                if(Main.Hebrew==true){ dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "למחוק דוח מרישום",
                        "האם למחוק דוח זה מהרשימה?",
                        "בחר YES כדי למחוק דוח זה מהרשימה");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}else{ dialog1 = new Dialog(
                        DialogType.CONFIRMATION,
                        "Delete Report",
                        "Do you want to delete this Report?",
                        "Press YES to delete Report?");

                    dialog1.setFontSize(22);
                    dialog1.showAndWait();}



                if (dialog1.getResponse() == DialogResponse.YES) {


                    if(LoginController.AdminPass==true){

                        ReportDB db = new ReportDB();

                        db.DeleteUser(report.getId());
                        PopulateTable(ReportTbl,ReportSelClm,ReportGroup,6);

                    } else {

                        if(Main.Hebrew==true){ Dialog dialog = new Dialog(
                                DialogType.ERROR,
                                "התחבר כמשתמש ראשי כדי למחוק",
                                "אנא התחבר כמשתמש ראשי");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();}else{ Dialog dialog = new Dialog(
                                DialogType.ERROR,
                                "Login as ADMIN to delete report",
                                "Login as ADMIN to delete report");
                            dialog.setFontSize(22);
                            dialog.setHeaderColorStyle(HeaderColorStyle.OPAQUE_RED);

                            dialog.showAndWait();}






                    }



                }




            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }); }



}
