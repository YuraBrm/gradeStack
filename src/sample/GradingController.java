package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GradingController implements Initializable {

    @FXML public Button studentsbtn,tasksbtn, settingsbtn, grade, back;
    @FXML public GridPane grid;
    @FXML public VBox tabBox;
    @FXML public VBox gradeBox;
    @FXML public Label title;
    @FXML public TableColumn name, exp, exp1, exp2, exp3, exp4, exp5, exp6, exp7, exp8, exp9, exp10;
    @FXML public TableView table;
    ObservableList row = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb) {
        tabBox = new VBox();
        grid.add(tabBox, 0, 0);
        studentsbtn.setId("sBtn");
        tasksbtn.setId("tBtn");
        settingsbtn.setId("setBtn");
        studentsbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        studentsbtn.prefHeightProperty().bind(studentsbtn.widthProperty());
        tasksbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        tasksbtn.prefHeightProperty().bind(tasksbtn.widthProperty());
        settingsbtn.prefWidthProperty().bind(tabBox.widthProperty().multiply(1));
        settingsbtn.prefHeightProperty().bind(settingsbtn.widthProperty());
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

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);

        name.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        exp.prefWidthProperty().bind(table.widthProperty().multiply(0.8));
        exp1.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp2.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp3.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp4.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp5.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp6.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp7.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp8.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp9.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        exp10.prefWidthProperty().bind(exp.widthProperty().multiply(0.1));
        name.setCellValueFactory(new PropertyValueFactory<studentRow, String>("name"));
        exp1.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp1"));
        exp2.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp2"));
        exp3.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp3"));
        exp4.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp4"));
        exp5.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp5"));
        exp6.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp6"));
        exp7.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp7"));
        exp8.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp8"));
        exp9.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp9"));
        exp10.setCellValueFactory(new PropertyValueFactory<studentRow, String>("exp10"));

        table.setItems(row);
        exp1.setCellFactory(TextFieldTableCell.forTableColumn());

        grade.setOnAction((event) -> { //grading dialogue box
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Grade Student");
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            Button nextStudent = new Button("Next");
            Button previousStudent = new Button("Previous");
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 10, 10, 10));

            ComboBox students = new ComboBox();
            for(int i = 0; i <= Main.getNumOfStudents()-1; i++) {
                students.getItems().add(Main.getStudentNames(i));
            }

            gridPane.add(students, 0, 0);
            gridPane.add(nextStudent, 0, 1);
            gridPane.add(previousStudent, 1, 1);

            TextField[] exp = new TextField[Main.getNumOfExpectations(title.getText())];
            for(int i = 0; i < exp.length; i++){
                exp[i] = new TextField();
                exp[i].setPromptText(Main.getExpectationNames(title.getText(), i));
                exp[i].setPrefWidth(50);
                gridPane.add(exp[i], i+1, 0);
            }

            nextStudent.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e) {
                    if(!students.getSelectionModel().isEmpty()){
                        for(int i = 0; i < Main.getNumOfExpectations(title.getText()); i++){
                            Main.setGrade(title.getText(), students.getSelectionModel().getSelectedItem().toString(), exp[i].getPromptText(), exp[i].getText());
                        }
                        for(int i = 0; i < exp.length; i++){ //clear all text boxes
                            exp[i].clear();
                        }
                        students.getSelectionModel().selectNext();
                    }
                }
            });
            previousStudent.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e) {
                    if(!students.getSelectionModel().isEmpty()){
                        for(int i = 0; i < Main.getNumOfExpectations(title.getText()); i++){
                            Main.setGrade(title.getText(), students.getSelectionModel().getSelectedItem().toString(), exp[i].getPromptText(), exp[i].getText());
                        }
                        students.getSelectionModel().selectPrevious();
                    }
                }
            });

            dialog.getDialogPane().setContent(gridPane);
            dialog.showAndWait();

        });
        back.setOnAction((event) -> {
            Stage stage = (Stage) grid.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tasks.fxml"));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            root.getStylesheets().add(getClass().getResource("theme.css").toString());
            stage.setScene(grid.getScene() == null ? new Scene(root, stage.getMinWidth(), stage.getMinHeight()) : new Scene(root, grid.getScene().getWidth(), grid.getScene().getHeight()));
            stage.show();
        });
    }

    void initData(String name) {
        title.setText(name);
    }

    void populateTable() {
        int numofexp = Main.getNumOfExpectations(title.getText());
        if(numofexp > 0){ exp1.setText(Main.getExpectationNames(title.getText(),0));}
        if(numofexp > 1){ exp2.setText(Main.getExpectationNames(title.getText(),1));}
        if(numofexp > 2){ exp3.setText(Main.getExpectationNames(title.getText(),2));}
        if(numofexp > 3){ exp4.setText(Main.getExpectationNames(title.getText(),3));}
        if(numofexp > 4){ exp5.setText(Main.getExpectationNames(title.getText(),4));}
        if(numofexp > 5){ exp6.setText(Main.getExpectationNames(title.getText(),5));}
        if(numofexp > 6){ exp7.setText(Main.getExpectationNames(title.getText(),6));}
        if(numofexp > 7){ exp8.setText(Main.getExpectationNames(title.getText(),7));}
        if(numofexp > 8){ exp9.setText(Main.getExpectationNames(title.getText(),8));}
        if(numofexp > 9){ exp10.setText(Main.getExpectationNames(title.getText(),9));}
        String[] exp = new String[Main.getNumOfExpectations(title.getText())];
        for(int i = 0; i < exp.length; i++){
            exp[i] = Main.getExpectationNames(title.getText(), i);
        }
        for(int i = 0; i < Main.getNumOfStudents(); i++) { //cycle through all students to add multiple rows
            row.add(new studentRow(Main.getFormattedStudentRow(i, exp, title.getText())));
        }
    }

    public class studentRow {
        final SimpleStringProperty name, exp1, exp2, exp3, exp4, exp5, exp6, exp7, exp8, exp9, exp10;
        studentRow(String[] newRow) {
            this.name = new SimpleStringProperty(newRow[0]);
            this.exp1 = new SimpleStringProperty(newRow[1]);
            this.exp2 = new SimpleStringProperty(newRow[2]);
            this.exp3 = new SimpleStringProperty(newRow[3]);
            this.exp4 = new SimpleStringProperty(newRow[4]);
            this.exp5 = new SimpleStringProperty(newRow[5]);
            this.exp6 = new SimpleStringProperty(newRow[6]);
            this.exp7 = new SimpleStringProperty(newRow[7]);
            this.exp8 = new SimpleStringProperty(newRow[8]);
            this.exp9 = new SimpleStringProperty(newRow[9]);
            this.exp10 = new SimpleStringProperty(newRow[10]);
        }
        public SimpleStringProperty nameProperty() { return name; }
        public SimpleStringProperty exp1Property() { return exp1; }
        public SimpleStringProperty exp2Property() { return exp2; }
        public SimpleStringProperty exp3Property() { return exp3; }
        public SimpleStringProperty exp4Property() { return exp4; }
        public SimpleStringProperty exp5Property() { return exp5; }
        public SimpleStringProperty exp6Property() { return exp6; }
        public SimpleStringProperty exp7Property() { return exp7; }
        public SimpleStringProperty exp8Property() { return exp8; }
        public SimpleStringProperty exp9Property() { return exp9; }
        public SimpleStringProperty exp10Property() { return exp10; }
    }
}
