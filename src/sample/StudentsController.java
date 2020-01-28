package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    @FXML public ListView listView;
    @FXML public Button view, add, remove;
    @FXML public GridPane grid;
    @FXML public Button studentsbtn;
    @FXML public Button tasksbtn;
    @FXML public Button settingsbtn;
    @FXML public VBox tabBox;

    public void initialize(URL url, ResourceBundle rb) {
        studentsbtn.setId("sBtn");
        tasksbtn.setId("tBtn");
        settingsbtn.setId("setBtn");
        tabBox.setId("tabBox");
        studentsbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        studentsbtn.prefHeightProperty().bind(studentsbtn.widthProperty());
        tasksbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        tasksbtn.prefHeightProperty().bind(tasksbtn.widthProperty());
        settingsbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        settingsbtn.prefHeightProperty().bind(settingsbtn.widthProperty());

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
                controller.populateTable();
                stage.setScene(grid.getScene() == null
                        ? new Scene(root, stage.getMinWidth(), stage.getMinHeight())
                        : new Scene(root, grid.getScene().getWidth(), grid.getScene().getHeight()));
                stage.show();
            }
        });

        tasksbtn.setOnAction((event) -> {
            Stage stage = (Stage) grid.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tasks.fxml"));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            root.getStylesheets().add(getClass().getResource("theme.css").toString());
            stage.setScene(grid.getScene() == null
                    ? new Scene(root, stage.getMinWidth(), stage.getMinHeight())
                    : new Scene(root, grid.getScene().getWidth(), grid.getScene().getHeight()));
            stage.show();
        });

        settingsbtn.setOnAction((event) -> {
            event.consume();
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Settings");
            ButtonType acceptButtonType = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(acceptButtonType, ButtonType.CANCEL);
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 10, 10, 10));

            Button save = new Button();
            save.setText("Save");
            Button load = new Button();
            load.setText("Load");
            gridPane.add(save, 0, 0);
            gridPane.add(load, 1, 0);
            save.setOnAction((eventSave) -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Classroom File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("XML Classroom Files", "*.xml")
                );

                try {
                    Main.saveToFile(fileChooser.showSaveDialog(Main.stage));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            });
            load.setOnAction((eventLoad) -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Classroom File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("XML Classroom Files", "*.xml")
                );

                try {
                    Main.loadFile(fileChooser.showOpenDialog(Main.stage));
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            dialog.getDialogPane().setContent(gridPane);
            dialog.showAndWait();
            });

        add.setOnAction((event) -> {
            String entry;
            Dialog dialog = new Dialog<>();
            dialog.setTitle("New Student");
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 10, 10, 10));

            TextField name = new TextField();
            name.setPromptText("Name");

            gridPane.add(name, 0, 0);

            dialog.getDialogPane().setContent(gridPane);
            dialog.showAndWait();

            entry = name.getText();

            if(entry != null && entry.length() < 50 && Main.checkName(entry) == false) { //failsafes to check if name can be created
                Main.newStudent(entry, 0);
            }

            listView.getItems().clear(); //refreshing the listview
            for(int i = 0; i < Main.getNumOfStudents(); i++) {
                listView.getItems().add(Main.getStudentNames(i));
            }
        });

        remove.setOnAction((event) -> {
            if(listView.getSelectionModel().getSelectedItem() != null) {
                Main.removeStudent(listView.getSelectionModel().getSelectedItem().toString());
                listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
            }
        });
    }
}
