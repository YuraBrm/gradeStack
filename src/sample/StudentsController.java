package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    @FXML public ListView listView;
    @FXML public Button view;
    @FXML public GridPane grid;

    public void initialize(URL url, ResourceBundle rb) {
        Main.newStudent("JFX Test", 4);
        Main.newStudent("CSS Test", 5);
        Main.newStudent("CSS Test", 5);
        for(int i = 0; i <= Main.getNumOfStudents()-1; i++) {
            listView.getItems().add(Main.getStudentNames(i));
        }

        view.setOnAction((event) -> {
            if(!listView.getSelectionModel().isEmpty()) {
                Stage stage = (Stage) grid.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("student_overview.fxml"));
                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                root.getStylesheets().add(getClass().getResource("theme.css").toString());
                SOverviewController controller = fxmlLoader.<SOverviewController>getController();
                controller.initData(listView.getSelectionModel().getSelectedItem().toString()); //sending the selected student name to the student overview controller
                stage.setScene(grid.getScene() == null
                        ? new Scene(root, stage.getMinWidth(), stage.getMinHeight())
                        : new Scene(root, grid.getScene().getWidth(), grid.getScene().getHeight()));
                stage.show();
            }
        });
    }
}
