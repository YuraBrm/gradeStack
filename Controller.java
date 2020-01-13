package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML public ListView listView;

    public void initialize(URL url, ResourceBundle rb) {
        Main.newStudent("JFX Test", 4);
        Main.newStudent("CSS Test", 5);
        Main.newStudent("CSS Test", 5);
        for(int i = 0; i <= Main.getNumOfStudents()-1; i++) {
            listView.getItems().add(Main.getStudentNames(i));
        }
    }
}
