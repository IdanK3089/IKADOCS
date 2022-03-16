package sample.Controllers;


import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Controller implements Initializable {


Stage stage = new Stage();


@FXML
AnchorPane ChoisePane;

@FXML
 AnchorPane GeneralPane;

    @FXML
     AnchorPane GeneratePane;

@FXML
    ImageView IkaImage;


    private Parent UserInfo ;
    private Parent ClientInfo;
    private Parent StdSelectInfo;
    private Parent StdEditInfo;
    private Parent ImportInfo;
    private Parent TestInfo;
    private Parent ConversionInfo;
    private Parent GenerateInfo;
    private Parent Form52Info;
    private Parent LoginInfo;
    private Parent TrackingInfo;

    private GenerateController generateController ;
    private UserController userController ;
    private ImportController importController ;
    private TestParamController testParamController;
    private StandardSelectController standardSelectController ;
    private StandardController standardController ;
    private ClientController clientController ;
    private LoginController loginController;
    private  Form52Controller form52Controller;
    private  TrackingController TrackingController;
    private ConvertController convertController;

    public static AnchorPane pane;

    public static String report;

 public static File reportFile;


    @Override
    public void initialize(URL location, ResourceBundle resources) {




        if(Main.Hebrew==true){




            FXMLLoader TrackingLoaderInfoLoader = new FXMLLoader(getClass().getResource("TrackPaneH.fxml"));
            try {
                TrackingInfo = TrackingLoaderInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TrackingController = TrackingLoaderInfoLoader.getController();




            FXMLLoader GenerateInfoLoader = new FXMLLoader(getClass().getResource("GenerateReportPaneH.fxml"));
            try {
                GenerateInfo = GenerateInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            generateController = GenerateInfoLoader.getController();

            FXMLLoader userInfoLoader = new FXMLLoader(getClass().getResource("ReportDetailsH.fxml"));
            try {
                UserInfo = userInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            userController = userInfoLoader.getController();



            FXMLLoader ImportInfoLoader = new FXMLLoader(getClass().getResource("ImportPaneH.fxml"));
            try {
                ImportInfo = ImportInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            importController = ImportInfoLoader.getController();


            FXMLLoader StandardEditInfoLoader = new FXMLLoader(getClass().getResource("EditStandardH.fxml"));
            try {
                StdEditInfo = StandardEditInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            standardController = StandardEditInfoLoader.getController();


            FXMLLoader StandardSelInfoLoader = new FXMLLoader(getClass().getResource("SelectStandardPaneH.fxml"));
            try {
                StdSelectInfo = StandardSelInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            standardSelectController = StandardSelInfoLoader.getController();


            FXMLLoader TestInfoLoader = new FXMLLoader(getClass().getResource("TestParamPaneH.fxml"));
            try {
                TestInfo = TestInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            testParamController = TestInfoLoader.getController();


            FXMLLoader ClientInfoLoader = new FXMLLoader(getClass().getResource("ClientSelectionH.fxml"));
            try {
                ClientInfo = ClientInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientController = ClientInfoLoader.getController();

            FXMLLoader LoginInfoLoader = new FXMLLoader(getClass().getResource("PasswordPaneH.fxml"));
            try {
                LoginInfo = LoginInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            loginController = LoginInfoLoader.getController();


            FXMLLoader Form52InfoLoader = new FXMLLoader(getClass().getResource("Form52PaneH.fxml"));
            try {
                Form52Info = Form52InfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            form52Controller = Form52InfoLoader.getController();

            FXMLLoader ConversionLoader = new FXMLLoader(getClass().getResource("ConvertPaneH.fxml"));
            try {
                ConversionInfo = ConversionLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            convertController = ConversionLoader.getController();



            File pathToFile = new File(Paths.get("").toAbsolutePath().toString()+"\\IKA.PNG");

            try {
                BufferedImage image = ImageIO.read(pathToFile);
                Image m = SwingFXUtils.toFXImage(image,null);
                IkaImage.setImage(m);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(Main.Hebrew==false) {


            FXMLLoader TrackingLoaderInfoLoader = new FXMLLoader(getClass().getResource("TrackPane.fxml"));
            try {
                TrackingInfo = TrackingLoaderInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TrackingController = TrackingLoaderInfoLoader.getController();




            FXMLLoader GenerateInfoLoader = new FXMLLoader(getClass().getResource("GenerateReportPane.fxml"));
            try {
                GenerateInfo = GenerateInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            generateController = GenerateInfoLoader.getController();

            FXMLLoader userInfoLoader = new FXMLLoader(getClass().getResource("ReportDetails.fxml"));
            try {
                UserInfo = userInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            userController = userInfoLoader.getController();



            FXMLLoader ImportInfoLoader = new FXMLLoader(getClass().getResource("ImportPane.fxml"));
            try {
                ImportInfo = ImportInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            importController = ImportInfoLoader.getController();


            FXMLLoader StandardEditInfoLoader = new FXMLLoader(getClass().getResource("EditStandard.fxml"));
            try {
                StdEditInfo = StandardEditInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            standardController = StandardEditInfoLoader.getController();

            FXMLLoader ConversionLoader = new FXMLLoader(getClass().getResource("ConvertPane.fxml"));
            try {
                ConversionInfo = ConversionLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            convertController = ConversionLoader.getController();


            FXMLLoader StandardSelInfoLoader = new FXMLLoader(getClass().getResource("SelectStandardPane.fxml"));
            try {
                StdSelectInfo = StandardSelInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            standardSelectController = StandardSelInfoLoader.getController();


            FXMLLoader TestInfoLoader = new FXMLLoader(getClass().getResource("TestParamPane.fxml"));
            try {
                TestInfo = TestInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            testParamController = TestInfoLoader.getController();


            FXMLLoader ClientInfoLoader = new FXMLLoader(getClass().getResource("ClientSelection.fxml"));
            try {
                ClientInfo = ClientInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientController = ClientInfoLoader.getController();

            FXMLLoader LoginInfoLoader = new FXMLLoader(getClass().getResource("PasswordPane.fxml"));
            try {
                LoginInfo = LoginInfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            loginController = LoginInfoLoader.getController();


            FXMLLoader Form52InfoLoader = new FXMLLoader(getClass().getResource("Form52Pane.fxml"));
            try {
                Form52Info = Form52InfoLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            form52Controller = Form52InfoLoader.getController();




            File pathToFile = new File(Paths.get("").toAbsolutePath().toString()+"\\IKA.PNG");

            try {
                BufferedImage image = ImageIO.read(pathToFile);
                Image m = SwingFXUtils.toFXImage(image,null);
                IkaImage.setImage(m);
            } catch (IOException e) {
                e.printStackTrace();
            }





        }





      //ToDo add Convert and Form 52

        ImageView app = (ImageView) (Node) GeneratePane.getChildren().get(0);


    }


    public void OpenFile(){


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Docx");
        fileChooser.setInitialDirectory(new File(Paths.get("").toAbsolutePath()+"\\Template"));



        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        if(files!=null && !files.isEmpty())
            reportFile = files.get(0);








    }



public void CloseApp(){

    Platform.exit();
    System.exit(0);



}
@FXML
public void ShowReportDetail() throws IOException {


      try{

          FadeTransition ft = new FadeTransition(Duration.millis(1000));
          ft.setNode(GeneratePane);
          ft.setFromValue(0);
          ft.setToValue(1);

          GeneratePane.getChildren().clear();

          GeneratePane.getChildren().add(UserInfo);
          ft.play();



      } catch(Exception e){


      }



}



    @FXML
    public void ShowForm52() throws IOException {



        try{

            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            ft.setNode(GeneratePane);
            ft.setFromValue(0);
            ft.setToValue(1);

            GeneratePane.getChildren().clear();

            GeneratePane.getChildren().add(Form52Info);
            ft.play();



        } catch(Exception e){


        }



    }

    @FXML
    public void ShowTracking() throws IOException {



        try{

            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            ft.setNode(GeneratePane);
            ft.setFromValue(0);
            ft.setToValue(1);

            GeneratePane.getChildren().clear();

            GeneratePane.getChildren().add(TrackingInfo);
            ft.play();



        } catch(Exception e){


        }



    }




    @FXML
    public void ShowGenerateReport() throws IOException {





        try{

            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            ft.setNode(GeneratePane);
            ft.setFromValue(0);
            ft.setToValue(1);


            GeneratePane.getChildren().clear();

            GeneratePane.getChildren().add(GenerateInfo);
            ft.play();



        } catch(Exception e){


        }



    }


    @FXML
    public void ShowAbout() throws IOException {


        if(Main.Hebrew==true){

            FXMLLoader fx = new FXMLLoader(getClass().getResource("AboutH.fxml"));


            pane = (AnchorPane)fx.load();


            try{

                FadeTransition ft = new FadeTransition(Duration.millis(1000));
                ft.setNode(GeneratePane);
                ft.setFromValue(0);
                ft.setToValue(1);


                //GeneratePane.getChildren().clear();

                GeneratePane.getChildren().add(pane);
                ft.play();



            } catch(Exception e){


            }

        } else {

            FXMLLoader fx = new FXMLLoader(getClass().getResource("About.fxml"));


            pane = (AnchorPane)fx.load();


            try{

                FadeTransition ft = new FadeTransition(Duration.millis(1000));
                ft.setNode(GeneratePane);
                ft.setFromValue(0);
                ft.setToValue(1);


                //GeneratePane.getChildren().clear();

                GeneratePane.getChildren().add(pane);
                ft.play();



            } catch(Exception e){


            }


        }





    }




    @FXML
    public void ShowConversion() throws IOException {




        if(Main.Hebrew==true){

            FXMLLoader fx = new FXMLLoader(getClass().getResource("ConvertPaneH.fxml"));


            pane = (AnchorPane)fx.load();


            try{

                FadeTransition ft = new FadeTransition(Duration.millis(1000));
                ft.setNode(GeneratePane);
                ft.setFromValue(0);
                ft.setToValue(1);


                //GeneratePane.getChildren().clear();

                GeneratePane.getChildren().add(ConversionInfo);
                ft.play();



            } catch(Exception e){


            }

        } else {

            FXMLLoader fx = new FXMLLoader(getClass().getResource("ConvertPane.fxml"));


            pane = (AnchorPane)fx.load();


            try{

                FadeTransition ft = new FadeTransition(Duration.millis(1000));
                ft.setNode(GeneratePane);
                ft.setFromValue(0);
                ft.setToValue(1);


                //GeneratePane.getChildren().clear();

                GeneratePane.getChildren().add(ConversionInfo);
                ft.play();



            } catch(Exception e){


            }


        }




    }

@FXML
public void ShowEdit() throws IOException {


    if(LoginController.AdminPass==false)
    {
          if(Main.Hebrew==true){

              FXMLLoader fx = new FXMLLoader(getClass().getResource("EmptyLoginH.fxml"));

              pane = (AnchorPane)fx.load();
              try{

                  FadeTransition ft = new FadeTransition(Duration.millis(1000));
                  ft.setNode(GeneratePane);
                  ft.setFromValue(0);
                  ft.setToValue(1);


                  GeneratePane.getChildren().clear();

                  GeneratePane.getChildren().add(pane);
                  ft.play();



              } catch(Exception e) {


              }
          } else {

              FXMLLoader fx = new FXMLLoader(getClass().getResource("EmptyLogin.fxml"));

              pane = (AnchorPane)fx.load();
              try{

                  FadeTransition ft = new FadeTransition(Duration.millis(1000));
                  ft.setNode(GeneratePane);
                  ft.setFromValue(0);
                  ft.setToValue(1);


                  GeneratePane.getChildren().clear();

                  GeneratePane.getChildren().add(pane);
                  ft.play();



              } catch(Exception e) {


              }
          }




    } else{





        try{

            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            ft.setNode(GeneratePane);
            ft.setFromValue(0);
            ft.setToValue(1);



            GeneratePane.getChildren().clear();

            GeneratePane.getChildren().add(StdEditInfo);
            ft.play();



        } catch(Exception e){


        }

    }




}

    @FXML
    public void ShowLogin() throws IOException {


        {

            try {

                FadeTransition ft = new FadeTransition(Duration.millis(1000));
                ft.setNode(GeneratePane);
                ft.setFromValue(0);
                ft.setToValue(1);


             //   GeneratePane.getChildren().clear();

                GeneratePane.getChildren().add(LoginInfo);
                ft.play();


            } catch (Exception e) {


            }
        }

    }




    @FXML
    public void ShowImportPane() throws IOException {





        try{

            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            ft.setNode(GeneratePane);
            ft.setFromValue(0);
            ft.setToValue(1);



            //GeneratePane.getChildren().clear();

            GeneratePane.getChildren().add(ImportInfo);
            ft.play();


        } catch(Exception e){


        }




    }
    @FXML
    public void ShowClient() throws IOException {



        try{

            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            ft.setNode(GeneratePane);
            ft.setFromValue(0);
            ft.setToValue(1);


            GeneratePane.getChildren().clear();

            GeneratePane.getChildren().add(ClientInfo);
            ft.play();


        } catch(Exception e){


        }




    }




    @FXML
    public void ShowTestPane() throws IOException {

        try{
            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            ft.setNode(GeneratePane);
            ft.setFromValue(0);
            ft.setToValue(1);


            GeneratePane.getChildren().clear();

            GeneratePane.getChildren().add(TestInfo);
            ft.play();
        } catch(Exception e){


        }




    }


    @FXML
    public void ShowSelectPane() throws IOException {




        try{


            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            ft.setNode(GeneratePane);
            ft.setFromValue(0);
            ft.setToValue(1);




            GeneratePane.getChildren().clear();

            GeneratePane.getChildren().add(StdSelectInfo);
            ft.play();


        } catch(Exception e){


        }





    }





}





