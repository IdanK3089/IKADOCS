package sample.Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class ConvertController implements Initializable {

    @FXML
    JFXTextField CText;

    @FXML
    JFXTextField MoText;

    @FXML
    JFXTextField VText;

    @FXML
    JFXTextField CuText;

    @FXML
    JFXTextField CrText;

    @FXML
    JFXTextField MnText;

    @FXML
    JFXTextField NiText;

    @FXML
    ChoiceBox<String> CarbonSTDCB;


    String CarbonEqSTD = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CText.setFont(Font.font("System", FontWeight.BOLD, 18));
        MoText.setFont(Font.font("System", FontWeight.BOLD, 18));
        VText.setFont(Font.font("System", FontWeight.BOLD, 18));
        CuText.setFont(Font.font("System", FontWeight.BOLD, 18));
        CrText.setFont(Font.font("System", FontWeight.BOLD, 18));
        MnText.setFont(Font.font("System", FontWeight.BOLD, 18));
        NiText.setFont(Font.font("System", FontWeight.BOLD, 18));





    }


  @FXML
  public void CaluculateCarbonEql(){








  }





}
