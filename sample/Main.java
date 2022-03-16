package sample;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerArrayTuple;
import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.tools.ZooConfig;
import sample.Database.MapDBDriver;
import sample.Database.NitStandardParam;
import sample.TestPojos.CentralSelection;

import java.awt.*;
import java.nio.file.Paths;
import java.util.*;

public class Main extends Application {


     public static double px;
     public static boolean Hebrew;
     public static Stage parentWindow;
     public static  Scene s;

   public static  ArrayList<NitStandardParam> LoadedToMemory = new ArrayList<>();


    public static CentralSelection selection = new CentralSelection();


    public static MapDBDriver dbDriver = new MapDBDriver();


    @Override
    public void start(Stage primaryStage) throws Exception{

        ButtonType foo = new ButtonType("English", ButtonBar.ButtonData.OK_DONE);
        ButtonType bar = new ButtonType("עברית", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Select User Interface Language",
                foo,
                bar);


        alert.setHeaderText("Select User Interface Language");
        alert.setContentText("בחר שפה לממשק משתמש");


        alert.setTitle("בחירת שפה לממשק");





        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(bar) == foo) {
            Hebrew = false;
            parentWindow = primaryStage;

            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

            Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
            double width = resolution.getWidth();
            double height = resolution.getHeight();
            double w = width/1920;  //your window width
            double h = height/1040;  //your window hight
            Scale scale = new Scale(w, h, 0, 0);
            root.getTransforms().add(scale);


            s = new Scene(root, 1920,1040);


            parentWindow.setScene(s);
            parentWindow.setResizable(false);
            parentWindow.show();


        }else if(result.orElse(bar) == bar){

            Hebrew = true;
            parentWindow = primaryStage;


            Parent root = FXMLLoader.load(getClass().getResource("sampleH.fxml"));

            Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
            double width = resolution.getWidth();
            double height = resolution.getHeight();
            double w = width/1920;  //your window width
            double h = height/1040;  //your window hight
            Scale scale = new Scale(w, h, 0, 0);
            root.getTransforms().add(scale);


            s = new Scene(root, 1920,1040);

            parentWindow.setScene(s);
            parentWindow.setResizable(false);
            parentWindow.show();
        }

        primaryStage.setOnCloseRequest(e -> handleExit());


    }

    private void handleExit(){

        Platform.exit();
        System.exit(0);

    }

    public static void main(String[] args)  {



        dbDriver.OpenReadOnlyDB();
        LoadedToMemory = dbDriver.getAll();
       //Testing();
    //   TransferToMapDB();
     //  OpenAndFind();
        DiskAccessOneFile.allowReadConcurrency(true);
        ZooConfig.setFilePageSize(512);
        launch(args);
    }




    public static void TransferToMapDB(){

        DB db1 = DBMaker.fileDB(Paths.get("").toAbsolutePath().toString()+"\\MapStandardDB").transactionEnable().make();


        BTreeMap<Integer,Object[]> map = db1.treeMap("Standards").valueSerializer(new SerializerArrayTuple(Serializer.INTEGER, Serializer.STRING))
                .keySerializer(Serializer.INTEGER).create();


        for(NitStandardParam d: Main.LoadedToMemory){

            map.put(d.getId(),new Object[]{d.getParent_id(),d.getName()});

        }

        db1.commit();


    }




    }
