package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class TasksController implements Initializable {

    @FXML public ListView listView;
    @FXML public Button grade, add, studentsbtn,tasksbtn, settingsbtn;
    @FXML public GridPane grid;
    @FXML public VBox tabBox;

    public void initialize(URL url, ResourceBundle rb) {
        studentsbtn.setId("sBtn");
        tasksbtn.setId("tBtn");
        settingsbtn.setId("setBtn");
        studentsbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        studentsbtn.prefHeightProperty().bind(studentsbtn.widthProperty());
        tasksbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        tasksbtn.prefHeightProperty().bind(tasksbtn.widthProperty());
        settingsbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        settingsbtn.prefHeightProperty().bind(settingsbtn.widthProperty());

        for(int i = 0; i <= Main.getNumOfTasks()-1; i++) {
            listView.getItems().add("[" + Main.getTaskNames(i)[0] + "] " + Main.getTaskNames(i)[1]);
        }

        add.setOnAction((event) -> {
            String[] entry = new String[3];
            String[] expectations;
            Dialog dialog = new Dialog<>();
            dialog.setTitle("New Task");
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 10, 10, 10));

            TextField id = new TextField();
            id.setPromptText("ID");
            TextField plainName = new TextField();
            plainName.setPromptText("Plain text name");
            TextField exp = new TextField();
            exp.setPromptText("Exp., separate with \",\"");

            gridPane.add(id, 0, 0);
            gridPane.add(plainName, 1, 0);
            gridPane.add(exp, 2, 0);

            dialog.getDialogPane().setContent(gridPane);
            dialog.showAndWait();

            entry[0] = id.getText();
            entry[1] = plainName.getText();
            entry[2] = exp.getText();
            expectations = entry[2].split("\\s*,\\s*");
            Main.newTask(entry[1], entry[0], expectations);

            listView.getItems().clear(); //refreshing the listview
            for(int i = 0; i <= Main.getNumOfTasks()-1; i++) {
                listView.getItems().add("[" + Main.getTaskNames(i)[0] + "] " + Main.getTaskNames(i)[1]);
            }
        });

        studentsbtn.setOnAction((event) -> {
            Stage stage = (Stage) grid.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("students.fxml"));
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
        grade.setOnAction((event) -> {
            if(!listView.getSelectionModel().isEmpty()) {
                Stage stage = (Stage) grid.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("grading.fxml"));
                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                root.getStylesheets().add(getClass().getResource("theme.css").toString());
                GradingController controller = fxmlLoader.getController();
                controller.initData(listView.getSelectionModel().getSelectedItem().toString());
                controller.populateTable();
                stage.setScene(grid.getScene() == null
                        ? new Scene(root, stage.getMinWidth(), stage.getMinHeight())
                        : new Scene(root, grid.getScene().getWidth(), grid.getScene().getHeight()));
                stage.show();
            }
        });
    }
}
