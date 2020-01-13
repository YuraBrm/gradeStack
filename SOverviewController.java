package src.sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SOverviewController implements Initializable {
    @FXML public Label studentNameTitle;
    @FXML public TableColumn exp, inc, r, rM, rC, rP, one, oneM, oneC, oneP, two, twoM, twoC, twoP, three, threeM, threeC, threeP, four, fourM, fourMC, fourC, fourCP, fourP;
    //grades are stored in format of grade + leaning, where M is minus, C is core (neutral), P is plus
    @FXML public TableView table;
    @FXML public Button back;
    @FXML public GridPane grid;
    ObservableList row = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb) {

        table.setEditable(true);

        exp.prefWidthProperty().bind(table.widthProperty().multiply(0.1)); //setting all column widths to a proper scale, since FXML doesn't support percentage scaling
        inc.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
        r.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        one.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        two.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        three.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        four.prefWidthProperty().bind(table.widthProperty().multiply(0.23));
        rM.prefWidthProperty().bind(r.widthProperty().multiply(0.33));
        rC.prefWidthProperty().bind(r.widthProperty().multiply(0.34));
        rP.prefWidthProperty().bind(r.widthProperty().multiply(0.33));
        oneM.prefWidthProperty().bind(one.widthProperty().multiply(0.33));
        oneC.prefWidthProperty().bind(one.widthProperty().multiply(0.34));
        oneP.prefWidthProperty().bind(one.widthProperty().multiply(0.33));
        twoM.prefWidthProperty().bind(two.widthProperty().multiply(0.33));
        twoC.prefWidthProperty().bind(two.widthProperty().multiply(0.34));
        twoP.prefWidthProperty().bind(two.widthProperty().multiply(0.33));
        threeM.prefWidthProperty().bind(three.widthProperty().multiply(0.33));
        threeC.prefWidthProperty().bind(three.widthProperty().multiply(0.34));
        threeP.prefWidthProperty().bind(three.widthProperty().multiply(0.33));
        fourM.prefWidthProperty().bind(four.widthProperty().multiply(0.2));
        fourMC.prefWidthProperty().bind(four.widthProperty().multiply(0.2));
        fourC.prefWidthProperty().bind(four.widthProperty().multiply(0.2));
        fourCP.prefWidthProperty().bind(four.widthProperty().multiply(0.2));
        fourP.prefWidthProperty().bind(four.widthProperty().multiply(0.2));

        exp.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("exp"));
        inc.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("inc"));
        rM.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("rM"));
        rC.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("rC"));
        rP.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("rP"));
        oneM.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("oneM"));
        oneC.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("oneC"));
        oneP.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("oneP"));
        twoM.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("twoM"));
        twoC.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("twoC"));
        twoP.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("twoP"));
        threeM.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("threeM"));
        threeC.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("threeC"));
        threeP.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("threeP"));
        fourM.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("fourM"));
        fourMC.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("fourMC"));
        fourC.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("fourC"));
        fourCP.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("fourCP"));
        fourP.setCellValueFactory(new PropertyValueFactory<gradeRow, String>("fourP"));

        table.setItems(row);
        fourP.setCellFactory(TextFieldTableCell.forTableColumn());
        fourP.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent cellEditEvent) {
                System.out.println("AAA");
            }
        });

        back.setOnAction((event) -> {
            Stage stage = (Stage) grid.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("students.fxml"));
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
        studentNameTitle.setText(name);
    }

    void populateTable() {
        for(int i = 0; i < Main.getNumofGrades(studentNameTitle.getText()); i++){
            System.out.println("test");
            row.add(new gradeRow(Main.getFormattedGradeEntry(studentNameTitle.getText(), i)));
        }
    }

    public class gradeRow {
        public final SimpleStringProperty exp, inc, rM, rC, rP, oneM, oneC, oneP, twoM, twoC, twoP, threeM, threeC, threeP, fourM, fourMC, fourC, fourCP, fourP;

        public gradeRow(String[] newRow) {
            this.exp = new SimpleStringProperty(newRow[0]);
            this.inc = new SimpleStringProperty(newRow[1]);
            this.rM = new SimpleStringProperty(newRow[2]);
            this.rC = new SimpleStringProperty(newRow[3]);
            this.rP = new SimpleStringProperty(newRow[4]);
            this.oneM = new SimpleStringProperty(newRow[5]);
            this.oneC = new SimpleStringProperty(newRow[6]);
            this.oneP = new SimpleStringProperty(newRow[7]);
            this.twoM = new SimpleStringProperty(newRow[8]);
            this.twoC = new SimpleStringProperty(newRow[9]);
            this.twoP = new SimpleStringProperty(newRow[10]);
            this.threeM = new SimpleStringProperty(newRow[11]);
            this.threeC = new SimpleStringProperty(newRow[12]);
            this.threeP = new SimpleStringProperty(newRow[13]);
            this.fourM = new SimpleStringProperty(newRow[14]);
            this.fourMC = new SimpleStringProperty(newRow[15]);
            this.fourC = new SimpleStringProperty(newRow[16]);
            this.fourCP = new SimpleStringProperty(newRow[17]);
            this.fourP = new SimpleStringProperty(newRow[18]);
        }

        public SimpleStringProperty expProperty(){return exp;}
        public SimpleStringProperty incProperty(){return inc;}
        public SimpleStringProperty rMProperty(){return rM;}
        public SimpleStringProperty rCProperty(){return rC;}
        public SimpleStringProperty rPProperty(){return rP;}
        public SimpleStringProperty oneMProperty(){return oneM;}
        public SimpleStringProperty oneCProperty(){return oneC;}
        public SimpleStringProperty onePProperty(){return oneP;}
        public SimpleStringProperty twoMProperty(){return twoM;}
        public SimpleStringProperty twoCProperty(){return twoC;}
        public SimpleStringProperty twoPProperty(){return twoP;}
        public SimpleStringProperty threeMProperty(){return threeM;}
        public SimpleStringProperty threeCProperty(){return threeC;}
        public SimpleStringProperty threePProperty(){return threeP;}
        public SimpleStringProperty fourMProperty(){return fourM;}
        public SimpleStringProperty fourMCProperty(){return fourMC;}
        public SimpleStringProperty fourCProperty(){return fourC;}
        public SimpleStringProperty fourCPProperty(){return fourCP;}
        public SimpleStringProperty fourPProperty(){return fourP;}
    }
}