/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markmanager;

import java.io.File;
import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import static javafx.application.Application.launch;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.Arrays;

/**
 *
 * @author earl
 */
public class studentAndTaskLine extends Application {

    //grid is the area in which all of the functions of the GUI are added.
    GridPane grid;

    //studentButton and taskButton are used to switch between the student and
    //task parts of the GUI.  
    Button studentButton;
    Button taskButton;
    
    //addStudent, addGrade, addTask, and addExpectation 
    //are used to add students, grades, tasks, and expectations, respectively, 
    Button addStudent;
    Button addGrade;
    Button addTask;
    Button addExpectation;
//Button deleteStudent;
    
    //graphStudent is used to graph a student's grades.
    Button graphStudent;
    
    //backButton is used to go back from a screen.
    Button backButton;

//kidButtons is the list which stores all of the student buttons, and it is utilized
//when we need to delete all of them. taskButtons and expectationButtons are the equivalents for tasks and expectations, respectively.
//validGrades is a list used to create the vertical scale when students are graphed.
    Button[] kidButtons;
    Button[] taskButtons;
    Button[] expectationButtons;
    String[] validGrades;

//enterTask, enterExpectation, and enterGrade are the textfields used to enter tasks, expectations, and grades, respectively.
//enterStudentFirstName and enterStudentLastName are the textfields used to enter a student's first name and last name to go along with a grade.
    TextField enterTask;
    TextField enterExpectation;
    TextField enterGrade;
    TextField enterStudentFirstName;
    TextField enterStudentLastName;

//errorMessage is the message that pops up when the user enters an invalid grade.
//gradeLabels is the list that stores all of the grades for a student.
    Label errorMessage;
    Label[] gradeLabels;

//studentName2 is the name of a student that is clicked on to view their grades, while taskName2 is the name of a task that is clicked on to enter an expectation,
//and expectationName2 is the name of an expectation that is clicked on to enter a grade.
    String studentName2;
    String fullStudentName2;
    String taskName2;
    String expectationName2;

//numberOfTasks, numberOfExpectations, numberOfGrades, and numberOfStudents are the integers which represent the numbers of tasks, expectations, grades, and students, respectively.
    int numberOfTasks;
    int numberOfExpectations;
    int numberOfGrades;
    int numberOfStudents;

//studentLine, taskLine, expectationLine, and gradeLine are the integers used for the 
//y-coordinates of the student, task, expectation, and grade buttons.
    int studentLine;
    int taskLine;
    int expectationLine;
    int gradeLine;

    boolean isInStudentSection;

    /**
     * @param args the command line arguments
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("");

        primaryStage.show();
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(100, 100, 100, 100));

        Text scenetitle = new Text("Students");
        grid.add(scenetitle, 0, 1);
        scenetitle.setVisible(false);

        enterStudentFirstName = new TextField();

        enterStudentLastName = new TextField();

        enterTask = new TextField();

        enterExpectation = new TextField();

        studentButton = new Button("Go to student section");
        grid.add(studentButton, 0, 0);

        taskButton = new Button("Go to task section");
        grid.add(taskButton, 2, 0);

        addStudent = new Button("Add a student");

        addTask = new Button("Add a task");

        addExpectation = new Button("Add an expectation");
        addExpectation.setVisible(false);

        graphStudent = new Button("Graph the grades");
        graphStudent.setVisible(false);

        addGrade = new Button("Add a grade");
        addGrade.setVisible(false);

        backButton = new Button("Back");
        grid.add(backButton, 5, 6);
        backButton.setVisible(false);

        errorMessage = new Label("ERROR: Invalid grade.");
        grid.add(errorMessage, 2, 6);
        errorMessage.setVisible(false);

        enterGrade = new TextField();

        studentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                grid.getChildren().removeAll(studentButton, taskButton);
                isInStudentSection = true;
                grid.add(enterStudentFirstName, 3, 3);
                enterStudentFirstName.setVisible(true);
                grid.add(enterStudentLastName, 4, 3);
                enterStudentLastName.setVisible(true);
                grid.add(addStudent, 5, 5);
                int deleteButtons = 0;
                if (taskButtons != null) {
                    while (deleteButtons < taskButtons.length) {
                        grid.getChildren().remove(taskButtons[deleteButtons]);
                        deleteButtons++;
                    }
                }
                backButton.setVisible(true);
                studentLine = 1;

//deleteStudent = new Button("Delete a student");
//grid.add(deleteStudent, 2, 4);
                MarkManagerFile.createNewFile("students.txt");
                IO.closeOutputFile();

//This try/catch method opens the newly created file, counts the number of students,
// creates a list as long as the number of students, creates buttons out of the students,
// and adds them to kidButtons.
                try {

                    //getStudents reads the file while studentName recieves the text from getStudents.
                    IO.openInputFile("students.txt");
                    BufferedReader getStudents = new BufferedReader(new FileReader("students.txt"));
                    numberOfStudents = 0;
                    String studentName = getStudents.readLine();

//This while loop has getStudents read the file in order for the program to count the number of students.
                    while (studentName != null) {
                        numberOfStudents++;
                        studentName = getStudents.readLine();
                    }

//kidButtons is created, and getStudents and studentName are reset to read the students again.
                    kidButtons = new Button[numberOfStudents];
                    getStudents = new BufferedReader(new FileReader("students.txt"));
                    studentName = getStudents.readLine();

                    String firstName = MarkManagerFile.getFirstName(studentName);
                    String lastName = MarkManagerFile.getLastName(studentName);

                    numberOfStudents = 0;

                    //In this while loop, each student's names are initialized onto a button called newKid, put into the list, set to respond to the switchToGrades final EventHandler,
                    //and added to the GUI.
                    while (studentName != null) {
                        studentLine += 2;
                        Button newKid = new Button(lastName + ", " + firstName);
                        kidButtons[numberOfStudents] = newKid;
                        kidButtons[numberOfStudents].setOnAction(switchToGrades);
                        grid.add(newKid, 0, studentLine);
                        numberOfStudents++;
                        studentName = getStudents.readLine();
                        firstName = MarkManagerFile.getFirstName(studentName);
                        lastName = MarkManagerFile.getLastName(studentName);

                    }
                } catch (Exception e) {
                }
                IO.closeOutputFile();
            }
        });

        addStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String studentFirstName = enterStudentFirstName.getText();

                String studentLastName = enterStudentLastName.getText();

//duplicateStudent is the boolean which checks to see if two students have the same
//first and last name, and enter is the student created from the inputted first
//name and last name.
//boolean duplicateStudent = false;      
                Student enter = new Student(studentFirstName, studentLastName);

//for (int checkDuplicates = 0; checkDuplicates < kidButtons.length; checkDuplicates++) {
//String buttonText = kidButtons[checkDuplicates].getText();
//    if (buttonText.equals(enter.toString())) {
//        System.out.println(buttonText);
//        duplicateStudent = true;
//    }
//}
                errorMessage.setVisible(false);
                MarkManagerFile.addStudent(enter, "students.txt");
                studentLine += 2;
                Button newKid = new Button(enter.toString());
                numberOfStudents++;

//kidButtons2 is a list created to store all of the old students. searchKidButtons is an integer 
//which goes down the list and adds all of the student buttons to kidButtons2.
                Button[] kidButtons2 = new Button[numberOfStudents];
                int searchKidButtons = 0;

//If there are no spaces in kidButtons, kidButtons is turned into another list with a length of 1, with newKid being placed at position 0.
                if (kidButtons == null) {
                    kidButtons = new Button[1];
                    kidButtons[0] = newKid;
                    grid.add(newKid, 0, studentLine);
                } 

//This method adds all but the new student to kidButtons2.
                else {
                    while (searchKidButtons < kidButtons.length) {
                        kidButtons2[searchKidButtons] = kidButtons[searchKidButtons];
                        searchKidButtons++;
                    }

//kidButtons is transformed into a new list with one extra space for the new student,
// all of the students from kidButtons2 are added to kidButtons, and the new student
//is added to the GUI.
                    kidButtons = new Button[numberOfStudents];
                    int searchKidButtons2 = 0;
                    while (searchKidButtons2 != kidButtons.length - 1) {
                        kidButtons[searchKidButtons2] = kidButtons2[searchKidButtons2];
                        searchKidButtons2++;
                    }
                    kidButtons[numberOfStudents - 1] = newKid;
                    kidButtons[numberOfStudents - 1].setOnAction(switchToGrades);
                    grid.add(newKid, 0, studentLine);

                }

//if duplicateStudent is true, the error message appears.
//else if (duplicateStudent == true) {
//    errorMessage.setText("ERROR: Invalid student.");
//    errorMessage.setVisible(true);
//}
                System.out.println(kidButtons[numberOfStudents - 1].getText());
            }
        });

        graphStudent.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {

                //grades represents the y-axis of the graph.
                String[] grades = {"INC", "R-", "R-/R", "R", "R/R+", "R+", "R+/1-", "1-", "1-/1", "1", "1/1+", "1+", "1+/2-", "2-", "2-/2", "2", "2/2+",
                    "2+", "2+/3-", "3-", "3-/3", "3", "3/3+", "3+", "3+/4-", "4-", "4-/4", "4", "4/4+", "4+"};

                //The stage for the graph is created, along with the number axis, and the labels are set.
                Stage graph = new Stage();
                final LineChart<String, String> lineChart;
                final CategoryAxis expectationAxis = new CategoryAxis();
                final CategoryAxis gradeAxis = new CategoryAxis();
                gradeAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(grades)));
                gradeAxis.setAutoRanging(false);
                gradeAxis.invalidateRange(Arrays.asList(grades));
                expectationAxis.setLabel("Tasks");
                gradeAxis.setLabel("Grades");

                //The graph is initialized, with the title being set and the series being created.
                lineChart = new LineChart<String, String>(expectationAxis, gradeAxis);
                String studentForGraphing = getFullStudentName2();
                lineChart.setTitle(studentForGraphing);
                XYChart.Series series = new XYChart.Series();

                IO.createOutputFile("tasks.txt", true);
                try {
                    
                    
                    BufferedReader taskReader = new BufferedReader(new FileReader("tasks.txt"));
                    String taskFileSearch = taskReader.readLine();
                    
                    while (taskFileSearch != null) {
                        
                        //Each task is read and their individual files are opened.
                        try {
                            IO.createOutputFile(taskFileSearch + ".txt", true);
                            BufferedReader expectationReader = new BufferedReader(new FileReader(taskFileSearch + ".txt"));
                            String expectationFileSearch = expectationReader.readLine();
                            
                            //Each expectation in each task file is read, with their files being opened as well.
                            while (expectationFileSearch != null) {
                                try {
                                    IO.createOutputFile(expectationFileSearch + ".txt", true);
                                    BufferedReader gradeReader = new BufferedReader(new FileReader(expectationFileSearch + ".txt"));
                                    String gradeFileSearch = gradeReader.readLine();

                                    int gradeValues = 0;
                                    
                            //Each grade in each expectation file is read
                                    while (gradeFileSearch != null) {

                                        if (gradeFileSearch.substring(0, studentForGraphing.length()).equals(studentForGraphing)) {
                                            gradeValues++;
                                        }
                                        gradeFileSearch = gradeReader.readLine();

                                    }
                                    String[] studentGradeList = new String[gradeValues];
                                    gradeValues = 0;
                                    gradeReader = new BufferedReader(new FileReader(expectationFileSearch + ".txt"));
                                    gradeFileSearch = gradeReader.readLine();
                                    while (gradeFileSearch != null) {
                                        if (gradeFileSearch.substring(0, studentForGraphing.length()).equals(studentForGraphing)) {
                                            String grade = gradeFileSearch.substring(studentForGraphing.length() + 2);
                                            System.out.println(grade);
                                            studentGradeList[gradeValues] = grade;
                                            gradeValues++;
                                            System.out.println(gradeValues);
                                        }
                                        gradeFileSearch = gradeReader.readLine();
                                    }
                                    String meanGrade = MarkManagerFile.getGradeMean(studentGradeList);
                                    System.out.println(meanGrade);
                                    series.getData().add(new XYChart.Data(expectationFileSearch, meanGrade));

                                    IO.closeOutputFile();
                                } catch (Exception e) {

                                }
                                expectationFileSearch = expectationReader.readLine();
                            }
                            IO.closeOutputFile();
                        } catch (Exception e) {

                        }
                        taskFileSearch = taskReader.readLine();
                    }
                } catch (Exception e) {

                }
                IO.closeOutputFile();
                Scene graphOfStudent = new Scene(lineChart, 800, 800);
                lineChart.getData().add(series);
                graph.setScene(graphOfStudent);
                graph.show();

            }
        });

        taskButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                grid.getChildren().removeAll(studentButton, taskButton);
                isInStudentSection = false;
                grid.getChildren().remove(enterStudentFirstName);
                grid.getChildren().remove(enterStudentLastName);
                grid.getChildren().remove(addStudent);
                backButton.setVisible(true);

                int deleteButtons = 0;

                if (kidButtons != null) {
                    while (deleteButtons < kidButtons.length) {
                        grid.getChildren().remove(kidButtons[deleteButtons]);
                        deleteButtons++;
                    }
                }
                taskLine = 1;

                MarkManagerFile.createNewFile("tasks.txt");
                IO.closeOutputFile();
                grid.add(addTask, 2, 3);
                addTask.setVisible(true);
                grid.add(enterTask, 3, 3);

                try {
                    IO.openInputFile("tasks.txt");

//Here, the file is read and the tasks are counted, with a list as long as the number of tasks created.
                    BufferedReader getTasks = new BufferedReader(new FileReader("tasks.txt"));
                    numberOfTasks = 0;
                    String taskName = getTasks.readLine();
                    while (taskName != null) {
                        numberOfTasks++;
                        taskName = getTasks.readLine();
                    }
                    taskButtons = new Button[numberOfTasks];

//The file is reread and a button is created out of each task, with each button being added to taskButtons before being added to the GUI.
                    getTasks = new BufferedReader(new FileReader("tasks.txt"));
                    taskName = getTasks.readLine();
                    numberOfTasks = 0;
                    while (taskName != null) {
                        taskLine += 2;
                        Button newTask = new Button(taskName);
                        taskButtons[numberOfTasks] = newTask;
                        taskButtons[numberOfTasks].setOnAction(switchToExpectations);
                        grid.add(newTask, 0, taskLine);
                        numberOfTasks++;
                        taskName = getTasks.readLine();
                    }
                } catch (Exception e) {
                }
                IO.closeOutputFile();

            }

        });

        addTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String taskName = enterTask.getText();
                MarkManagerFile.addTask(taskName, "tasks.txt");
                taskLine += 2;
                Button newTask = new Button(taskName);
                numberOfTasks++;

//taskButtons2 is a list created to store all of the old tasks. searchKidButtons is the integer designed to add
//the old tasks to taskButtons2 and then add them to the new taskButtons.
                Button[] taskButtons2 = new Button[numberOfTasks];
                int searchTaskButtons = 0;

//If there are no spaces in taskButtons, taskButtons is turned into a new list with a length of 1, with the new
//task being placed at the beginning of the list and added to the GUI.
                if (taskButtons == null) {
                    taskButtons = new Button[1];
                    taskButtons[0] = newTask;
                    grid.add(newTask, 0, taskLine);
                } 

//This method adds all but the new task to taskButtons2.
                else {
                    while (searchTaskButtons < taskButtons.length) {
                        taskButtons2[searchTaskButtons] = taskButtons[searchTaskButtons];
                        searchTaskButtons++;
                    }
                    
//taskButtons is transformed into a new list with one extra space for the new task,
// all of the tasks from taskButtons2 are added to taskButtons, and the new task
//is added to the GUI.
                    taskButtons = new Button[numberOfTasks];
                    int searchTaskButtons2 = 0;
                    while (searchTaskButtons2 != taskButtons.length - 1) {
                        taskButtons[searchTaskButtons2] = taskButtons2[searchTaskButtons2];
                        searchTaskButtons2++;
                    }
                    taskButtons[numberOfTasks - 1] = newTask;
                    newTask.setOnAction(switchToExpectations);
                    grid.add(newTask, 0, taskLine);
                }
            }
        });

        addExpectation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String expectationName = enterExpectation.getText();
                MarkManagerFile.addExpectation(expectationName, taskName2 + ".txt");
                expectationLine += 2;
                Button newExpectation = new Button(expectationName);
                numberOfExpectations++;

                //expectationButtons2 is a list created to store all of the old expectations. searchExpectationButtons is
                Button[] expectationButtons2 = new Button[numberOfExpectations];
                int searchExpectationButtons = 0;

                //If there are no spaces in expectationButtons, expectationButtons is turned into a new list
                //with a length of 1, with the new expectation being added to the only position.
                if (expectationButtons == null) {
                    expectationButtons = new Button[1];
                    expectationButtons[0] = newExpectation;
                    grid.add(newExpectation, 0, expectationLine);
                } 
//This method adds all but the new student to taskButtons2.
                else {
                    while (searchExpectationButtons < expectationButtons.length) {
                        expectationButtons2[searchExpectationButtons] = expectationButtons[searchExpectationButtons];
                        searchExpectationButtons++;
                    }
//taskButtons is transformed into a new list with one extra space for the new student,
// all of the students from taskButtons2 are added to taskButtons, and the new student
//is added to the GUI.
                    expectationButtons = new Button[numberOfExpectations];
                    int searchExpectationButtons2 = 0;
                    while (searchExpectationButtons2 < expectationButtons.length) {
                        expectationButtons[searchExpectationButtons2] = expectationButtons2[searchExpectationButtons2];
                        searchExpectationButtons2++;
                    }
                    expectationButtons[numberOfExpectations - 1] = newExpectation;
                    newExpectation.setOnAction(switchToGrades);
                    grid.add(newExpectation, 0, expectationLine);
                }
            }
        });

        addGrade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String newGrade = enterGrade.getText();
                if (MarkManagerFile.checkGrade2(newGrade) == 0) {
                    Grade justMarked = new Grade(newGrade);
                    String studentFirstName = enterStudentFirstName.getText();

                    String studentLastName = enterStudentLastName.getText();

                    Student enter = new Student(studentFirstName, studentLastName);

                    try {
                        IO.openInputFile("students.txt");
                        BufferedReader findStudent = new BufferedReader(new FileReader("students.txt"));
                        String fullStudentString = findStudent.readLine();
                        boolean studentExists = false;
                        while (fullStudentString != null) {
                            if (fullStudentString.equals(enter.toString())) {
                                studentExists = true;
                                break;
                            }
                            fullStudentString = findStudent.readLine();
                        }
                        if (studentExists == false) {
                            errorMessage.setVisible(true);
                            newGrade = enterGrade.getText();
                        } else {
                            errorMessage.setVisible(false);
                            MarkManagerFile.addGrade(justMarked, enter, expectationName2 + ".txt");
                            gradeLine += 2;
                            Label completeGradeAndStudent = new Label(enter.toString() + ": " + newGrade);
                            numberOfGrades++;

//gradeLabels2 is a list created to store all of the old expectations. searchGradeLabels is the integer designed to add
//the old tasks to gradeLabels2 and then add them to the new gradeLabels.
                            Label[] gradeLabels2 = new Label[numberOfGrades];
                            int searchGradeLabels = 0;

//If there are no spaces in gradeLabels, gradeLabels is turned into a new list with a length of 1, with the new
//task being placed at the beginning of the list and added to the GUI.
                            if (gradeLabels == null) {
                                gradeLabels = new Label[1];
                                gradeLabels[0] = completeGradeAndStudent;
                                grid.add(completeGradeAndStudent, 0, gradeLine);
                            } //This method adds all but the new grade to gradeLabels2.
                            else {
                                while (searchGradeLabels < gradeLabels.length) {
                                    gradeLabels2[searchGradeLabels] = gradeLabels[searchGradeLabels];
                                    searchGradeLabels++;
                                }
//gradeLabels is transformed into a new list with one extra space for the new student,
// all of the students from taskButtons2 are added to taskButtons, and the new student
//is added to the GUI.
                                gradeLabels = new Label[numberOfGrades];
                                int searchGradeLabels2 = 0;
                                while (searchGradeLabels2 != gradeLabels.length - 1) {
                                    gradeLabels[searchGradeLabels2] = gradeLabels2[searchGradeLabels2];
                                    searchGradeLabels2++;
                                }
                                gradeLabels[numberOfGrades - 1] = completeGradeAndStudent;
                                grid.add(completeGradeAndStudent, 0, gradeLine);
                            }
                            File individualGradeFile = new File(studentFirstName + ".txt");
                            int lengthOfButtons = 0;
                            int distinguishingNumber = 1;
                            String addOnNumber = Integer.toString(distinguishingNumber);
                            
                            //If a file with a student's name already exists, these two if statements go down kidButtons 
                           //using lengthOfButtons and find the student button with the student's exact name.
                            if (individualGradeFile.exists()) {
                                while (lengthOfButtons < kidButtons.length) {

                                    //If a file with the same first name, but not the same last name as the student is selected, then a number is added to the student's first name in order
                                    //to distinguish the file with the student's name; the number will increase if there are more students with the same first name.
                                    if (MarkManagerFile.getFirstName(kidButtons[lengthOfButtons].getText()).equals(studentName2) && !MarkManagerFile.getLastName(kidButtons[lengthOfButtons].getText()).equals(studentLastName)) {
                                        distinguishingNumber++;
                                        addOnNumber = Integer.toString(distinguishingNumber);
                                    } else if (MarkManagerFile.getFirstName(kidButtons[lengthOfButtons].getText()).equals(studentName2) && MarkManagerFile.getLastName(kidButtons[lengthOfButtons].getText()).equals(studentLastName)) {
                                        if (distinguishingNumber == 1) {
                                            break;
                                        } else {
                                            studentFirstName = studentFirstName + " " + addOnNumber;
                                            break;
                                        }
                                    }
                                    lengthOfButtons++;
                                }
                            }

                            //A file with the clicked student's name is created and closed, the addStudent button as well as the two textfields to enter a student's name are removed,
                            // the addGrade and enterGrade buttons are added into the GUI, and the backButton is made visible.
                            MarkManagerFile.createNewFile(studentFirstName + ".txt");
                            String studentGrade = taskName2 + ", " + expectationName2 + ": " + newGrade;
                            IO.println(studentGrade);
                            IO.closeOutputFile();
                        }
                    } catch (Exception e) {

                    }

                }
            }

        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int addButtons = 0;
                if (isInStudentSection == false) {
                    if (addGrade.isVisible() == true) {
                        expectationLine = 1;
                        while (addButtons < gradeLabels.length) {
                            grid.getChildren().remove(gradeLabels[addButtons]);
                            addButtons++;
                        }
                        gradeLine = 1;
                        addButtons = 0;
                        while (addButtons < expectationButtons.length) {
                            expectationLine += 2;
                            grid.add(expectationButtons[addButtons], 0, expectationLine);
                            addButtons++;

                        }

                        grid.getChildren().remove(enterStudentFirstName);
                        enterStudentFirstName.setVisible(false);
                        grid.getChildren().remove(enterStudentLastName);
                        enterStudentLastName.setVisible(false);
                        grid.getChildren().remove(addGrade);
                        addGrade.setVisible(false);
                        grid.getChildren().remove(enterGrade);
                        enterGrade.setVisible(false);
                        grid.add(addExpectation, 2, 3);
                        addExpectation.setVisible(true);
                        grid.add(enterExpectation, 3, 3);
                        enterExpectation.setVisible(true);

                    } else if (addExpectation.isVisible() == true) {
                        taskLine = 1;
                        while (addButtons < expectationButtons.length) {
                            grid.getChildren().remove(expectationButtons[addButtons]);
                            addButtons++;
                        }
                        expectationLine = 1;
                        addButtons = 0;
                        while (addButtons < taskButtons.length) {
                            taskLine += 2;
                            grid.add(taskButtons[addButtons], 0, taskLine);
                            addButtons++;
                        }
                        grid.getChildren().remove(addExpectation);
                        addExpectation.setVisible(false);
                        grid.getChildren().remove(enterExpectation);
                        enterExpectation.setVisible(false);
                        grid.add(addTask, 2, 3);
                        addTask.setVisible(true);
                        grid.add(enterTask, 3, 3);
                        enterTask.setVisible(true);

                    } else if (addTask.isVisible() == true) {
                        while (addButtons < taskButtons.length) {
                            grid.getChildren().remove(taskButtons[addButtons]);
                            addButtons++;
                        }
                        grid.getChildren().removeAll(enterStudentFirstName, enterStudentLastName, addExpectation, enterExpectation, addTask, enterTask, enterGrade);
                         grid.add(studentButton, 0, 0);
                         grid.add(taskButton, 2, 0);
                        backButton.setVisible(false);
                    }
                } else if (isInStudentSection == true) {
                    
                    //If the user is on the screen where graphStudent is visible, gradeLine and studentLine are both reset,
                    //with the grades being removed from the screen and the student buttons being added to the screen.
                    //The addStudent, enterStudentFirstName, and enterStudentLastName buttons are all added to the screen.
                    if (graphStudent.isVisible() == true) {
                        gradeLine = 1;
                        while (addButtons < gradeLabels.length) {
                            grid.getChildren().remove(gradeLabels[addButtons]);
                            addButtons++;
                        }
                        studentLine = 1;
                        addButtons = 0;
                        while (addButtons < kidButtons.length) {
                            studentLine += 2;
                            grid.add(kidButtons[addButtons], 0, studentLine);
                            addButtons++;
                        }
                        grid.getChildren().remove(graphStudent);
                        graphStudent.setVisible(false);
                        grid.add(addStudent, 5, 5);
                        addStudent.setVisible(true);
                        grid.add(enterStudentFirstName, 3, 3);
                        enterStudentFirstName.setVisible(true);
                        grid.add(enterStudentLastName, 4, 3);
                        enterStudentLastName.setVisible(true);
                        
                    } else if (addStudent.isVisible() == true) {
                        while (addButtons < kidButtons.length) {
                            grid.getChildren().remove(kidButtons[addButtons]);
                            addButtons++;
                        }
                        grid.getChildren().removeAll(enterStudentFirstName, enterStudentLastName, addStudent);
                         grid.add(studentButton, 0, 0);
                         grid.add(taskButton, 2, 0);
                        backButton.setVisible(false);
                    }
                }
            }

        });

        StackPane root = new StackPane();

        root.getChildren().add(grid);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public String getFullStudentName2() {
        return fullStudentName2;
    }

    //switchToExpectations is the ActionEvent which switches to a task's expectations when the task in question is clicked.
    final EventHandler<ActionEvent> switchToExpectations = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            numberOfExpectations = 0;

//taskButton is the button that was clicked on to initiate the ActionEvent.
            Button taskButton = (Button) event.getSource();

//Here, the program goes down taskButtons using the integer deleteButtons, deleting all of the tasks from the GUI.
            int deleteButtons = 0;
            while (deleteButtons < taskButtons.length) {
                grid.getChildren().remove(taskButtons[deleteButtons]);
                deleteButtons++;
            }

            taskName2 = taskButton.getText();
            MarkManagerFile.createNewFile(taskName2 + ".txt");
            IO.closeOutputFile();
            grid.getChildren().remove(addTask);
            addTask.setVisible(false);
            grid.getChildren().remove(enterTask);
            grid.add(addExpectation, 2, 3);
            addExpectation.setVisible(true);
            grid.add(enterExpectation, 3, 3);
            enterExpectation.setVisible(true);

            // This try/catch method opens the file, counts the number of expectations,
            // creates a list as long as the number of expectations, creates buttons out of the expectations,
            // and adds them to expectationButtons. backButton also becomes visible, while the addTask textfield becomes invisible.
            try {

                IO.openInputFile(taskName2 + ".txt");

                BufferedReader getExpectations = new BufferedReader(new FileReader(taskName2 + ".txt"));
                String currentExpectation = getExpectations.readLine();
                while (currentExpectation != null) {
                    numberOfExpectations++;
                    currentExpectation = getExpectations.readLine();
                }
                expectationButtons = new Button[numberOfExpectations];
                numberOfExpectations = 0;
                getExpectations = new BufferedReader(new FileReader(taskName2 + ".txt"));
                currentExpectation = getExpectations.readLine();
                while (currentExpectation != null) {
                    expectationLine += 2;
                    Button expectation = new Button(currentExpectation);
                    expectationButtons[numberOfExpectations] = expectation;
                    expectationButtons[numberOfExpectations].setOnAction(switchToGrades);
                    grid.add(expectation, 0, expectationLine);
                    currentExpectation = getExpectations.readLine();
                    numberOfExpectations++;
                    IO.closeOutputFile();
                }
                backButton.setVisible(true);
                addTask.setVisible(false);
            } catch (Exception e) {
            }

        }
    };
    
    final EventHandler<ActionEvent> switchToGrades = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            Button sourceButton = (Button) event.getSource();
            int checkLists = 0;
            boolean isInKidButtons = false;
            boolean isInExpectationButtons = false;
            
            //If kidButtons already has buttons in it, the list is checked to see if the clicked button is a student button.
            if (kidButtons != null) {
                while (checkLists < kidButtons.length) {
                    if (kidButtons[checkLists].getText().equals(sourceButton.getText())) {
                        isInKidButtons = true;
                    }
                    checkLists++;
                }
            }
            checkLists = 0;
            if (isInKidButtons == false) {
                while (checkLists < expectationButtons.length) {
                    if (expectationButtons[checkLists].getText().equals(sourceButton.getText())) {
                        isInExpectationButtons = true;
                    }
                    checkLists++;
                }
                if (isInExpectationButtons == true) {
                    int deleteButtons = 0;
                    while (deleteButtons < expectationButtons.length) {
                        grid.getChildren().remove(expectationButtons[deleteButtons]);
                        deleteButtons++;
                    }
                    expectationName2 = sourceButton.getText();
                    MarkManagerFile.createNewFile(expectationName2 + ".txt");
                    IO.closeOutputFile();
                    grid.getChildren().remove(addExpectation);
                    addExpectation.setVisible(true);
                    grid.getChildren().remove(enterExpectation);
                    grid.add(addGrade, 2, 2);
                    addGrade.setVisible(true);
                    grid.add(enterGrade, 3, 2);
                    enterGrade.setVisible(true);
                    grid.add(enterStudentFirstName, 3, 3);
                    enterStudentFirstName.setVisible(true);
                    grid.add(enterStudentLastName, 4, 3);
                    enterStudentLastName.setVisible(true);
                    try {
                        numberOfGrades = 0;
                        IO.openInputFile(expectationName2 + ".txt");
                        BufferedReader getGrades = new BufferedReader(new FileReader(expectationName2 + ".txt"));
                        String gradeAndStudent = getGrades.readLine();
                        while (gradeAndStudent != null) {
                            numberOfGrades++;
                            gradeAndStudent = getGrades.readLine();
                        }
                        gradeLabels = new Label[numberOfGrades];
                        numberOfGrades = 0;
                        getGrades = new BufferedReader(new FileReader(expectationName2 + ".txt"));
                        gradeAndStudent = getGrades.readLine();
                        while (gradeAndStudent != null) {
                            gradeLine += 2;
                            Label completeGradeAndStudent = new Label(gradeAndStudent);
                            gradeLabels[numberOfGrades] = completeGradeAndStudent;
                            grid.add(completeGradeAndStudent, 0, gradeLine);
                            gradeAndStudent = getGrades.readLine();
                            IO.closeOutputFile();
                            numberOfGrades++;
                        }
                    } catch (Exception e) {
                    }
                }
            } else if (isInKidButtons == true) {
                int deleteButtons = 0;
                while (deleteButtons < kidButtons.length) {
                    grid.getChildren().remove(kidButtons[deleteButtons]);
                    deleteButtons++;
                }
                studentName2 = MarkManagerFile.getFirstName(sourceButton.getText());
                fullStudentName2 = sourceButton.getText();
                String lastName = MarkManagerFile.getLastName(sourceButton.getText());
                int lengthOfButtons = 0;
                int distinguishingNumber = 1;
                String addOnNumber = Integer.toString(distinguishingNumber);
                File individualGradeFile = new File(studentName2 + ".txt");

//If a file with a student's name already exists, these two if statements go down kidButtons using lengthOfButtons and find the student button with the student's exact name.
                if (individualGradeFile.exists()) {
                    while (lengthOfButtons < kidButtons.length) {

                        //If a file with the same first name, but not the same last name as the student is selected, then a number is added to the student's first name in order
                        //to distinguish the file with the student's name; the number will increase if there are more students with the same first name.
                        if (MarkManagerFile.getFirstName(kidButtons[lengthOfButtons].getText()).equals(studentName2) && !MarkManagerFile.getLastName(kidButtons[lengthOfButtons].getText()).equals(lastName)) {
                            distinguishingNumber++;
                            addOnNumber = Integer.toString(distinguishingNumber);
                        } else if (MarkManagerFile.getFirstName(kidButtons[lengthOfButtons].getText()).equals(studentName2) && MarkManagerFile.getLastName(kidButtons[lengthOfButtons].getText()).equals(lastName)) {
                            if (distinguishingNumber == 1) {
                                break;
                            } else {
                                studentName2 = studentName2 + " " + addOnNumber;
                                break;
                            }
                        }
                        lengthOfButtons++;
                    }
                }

                //A file with the clicked student's name is created and closed, the addStudent button as well as the two textfields to enter a student's name are removed,
                // the addGrade and enterGrade buttons are added into the GUI, and the backButton is made visible.
                MarkManagerFile.createNewFile(studentName2 + ".txt");
                IO.closeOutputFile();
                grid.getChildren().remove(enterStudentFirstName);
                grid.getChildren().remove(enterStudentLastName);
                grid.getChildren().remove(addStudent);
                addStudent.setVisible(false);
                grid.add(graphStudent, 4, 4);
                graphStudent.setVisible(true);

                try {
                    IO.openInputFile(studentName2 + ".txt");
                    numberOfGrades = 0;

//getGrades reads the file while grade recieves the text from getGrades.
                    BufferedReader getGrades = new BufferedReader(new FileReader(studentName2 + ".txt"));
                    String grade = getGrades.readLine();

//This while loop has getGrades read the file in order for the program to count the number of grades.
                    while (grade != null) {
                        numberOfGrades++;
                        grade = getGrades.readLine();
                    }
                    gradeLabels = new Label[numberOfGrades];
                    numberOfGrades = 0;
                    getGrades = new BufferedReader(new FileReader(studentName2 + ".txt"));
                    grade = getGrades.readLine();
                    while (grade != null) {
                        gradeLine += 2;
                        Label completeGrade = new Label(grade);
                        gradeLabels[numberOfGrades] = completeGrade;
                        grid.add(completeGrade, 0, gradeLine);
                        grade = getGrades.readLine();
                        numberOfGrades++;
                    }
                    IO.closeOutputFile();
                } catch (Exception e) {
                }
            } else {

            }
        }
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}





