package src.sample;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.Arrays;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 *
 * @author earl
 */
public class GraphController {
    final static String performance = "Performance:";

 
   CategoryAxis expectationAxis;
 

    CategoryAxis gradeAxis;
    @FXML public Label studentNameTitle;

 //   @FXML
    LineChart<String,String> bc;

    public void initialize(){
        expectationAxis.setLabel("Tasks");  
        expectationAxis.setTickLabelRotation(90);
       
        
                String[] grades = {"INC", "R-", "R", "R+",  "1-",  "1",  "1+",  "2-",  "2", //grades represents the y-axis of the graph.
                    "2+",  "3-",  "3",  "3+",  "4-", "4-/4", "4", "4/4+", "4+"};
                gradeAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(grades)));
                gradeAxis.setAutoRanging(false);
                gradeAxis.setLabel("Grades");
       
       


               }



  //     task = Main.getStudentTask()
       
         void initData(String name) {
        studentNameTitle.setText(name);
    }  

void fillGraph () {

      
        XYChart.Series series1 = new XYChart.Series();  //series1 stores all of the data for the graph.

            String expect = ""; //expect is the expectation of each individual grade.
            String gradeMean = ""; //gradeMean is the mean of each grade 
           
           
           for(int i = 0; i < Main.getNumofGrades(studentNameTitle.getText()); i++){
               for(int i2 = 1; i2 < 19; i2++){
                if (!Main.getStudentExpectation(studentNameTitle.getText(), i2).equals("") && !Main.getStudentGrade(studentNameTitle.getText(), i2).equals("")) {
               expect = Main.getStudentExpectation(studentNameTitle.getText(), i2);
               int gradeListLength = 0;
               for (int gradeScan = 1; gradeScan < 19; gradeScan++) { //This for loop cycles through every single possible grade, seeing if the student has that grade.
                   if (Main.getStudentExpectation(studentNameTitle.getText(), gradeScan).equals(expect) && !Main.getStudentGrade(studentNameTitle.getText(), gradeScan).equals("")) {
                       gradeListLength++;
                   }
               }
               String [] gradeArray = new String [gradeListLength];
               for (int findGrades = 1; findGrades < 19; findGrades++) {  //The program cycles through the grades again, finds the ones with the same expectation, and adds them to the list.
                    if (Main.getStudentExpectation(studentNameTitle.getText(), findGrades).equals(expect) && !Main.getStudentGrade(studentNameTitle.getText(), findGrades).equals("")) {
                       gradeArray[findGrades] = Main.getStudentGrade(studentNameTitle.getText(), findGrades);
                   }
               }
                gradeMean = Main.getGradeMean(gradeArray);
                  series1.getData().add(new XYChart.Data(expect, gradeMean));  
                }
     }

}
                   bc.getData().add(series1);
}
           
       // series1.getData().add(new XYChart.Data(80, performance));

   
    }
