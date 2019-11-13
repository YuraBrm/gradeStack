package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public void initialize(URL url, ResourceBundle rb) {
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
            StudentsController controller = fxmlLoader.<StudentsController>getController();
            stage.setScene(grid.getScene() == null ? new Scene(root, stage.getMinWidth(), stage.getMinHeight()) : new Scene(root, grid.getScene().getWidth(), grid.getScene().getHeight()));
            stage.show();
        });
    }

    void initData(String name) {
        studentNameTitle.setText(name);
    }
}