package src.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.w3c.dom.*;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Main extends Application {
    static Parent StageRoot;
    static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage; //workaround to get instantiate a stage that was already defined
        StageRoot = FXMLLoader.load(getClass().getResource("students.fxml")); //get FXML
        StageRoot.getStylesheets().add(getClass().getResource("theme.css").toString()); //get CSS
        primaryStage.setTitle("gradeStack Beta");
        primaryStage.setScene(new Scene(StageRoot, 1100, 700)); //default width and height
        primaryStage.show();
    }

    /* Java DOM objects creation*/
    DocumentBuilderFactory dbfactory;
    DocumentBuilder dbbuilder;
    static Document doc;
    static Element root;
    static Element classroom;
    static Element students;
    static Element student;
    static Element tasks;
    static Element expectations;

    public static void main(String[] args) throws IOException, TransformerException {
        Main main = new Main();
        main.createXML();
        launch(args);
    }

    public void createXML(){
        try{
            dbfactory = DocumentBuilderFactory.newInstance();
            dbbuilder = dbfactory.newDocumentBuilder();
            doc = dbbuilder.newDocument();
            root = doc.createElement("classrooms");

            //XML element setup

            doc.appendChild(root);
            root.setAttribute("id", "0");
            classroom = doc.createElement("classroom");
            root.appendChild(classroom);
            students = doc.createElement("students");
            classroom.appendChild(students);
            tasks = doc.createElement("tasks");
            classroom.appendChild(tasks);
            expectations = doc.createElement("expectations");
            classroom.appendChild(expectations);

            //placeholders
            /*newStudent("Test", 0);
            newStudent("Test2", 1);
            newStudent("Test3", 2);
            newStudent("Test4", 2);
            Main.newStudent("JFX Test", 4);
            Main.newStudent("CSS Test", 5);
            Main.newStudent("listview scroll", 5);
            newTask("Megatest", "A1", new String[] {"T1", "T2", "T3"});*/

            /*for(int i=0; i < expectations.getChildNodes().getLength(); i++){
                System.out.println(expectations.getChildNodes().getLength());
            }*/

            if(expectations.getChildNodes().getLength() != 0){
                for(int i=0; i < expectations.getChildNodes().getLength(); i++){
                    System.out.println(expectations.getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadFile(File f) throws IOException, SAXException {
        doc = null;
        DOMParser parser = new DOMParser();
        InputStream inputStream= new FileInputStream(f);
        Reader reader = new InputStreamReader(inputStream,"UTF-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");
        parser.parse(is);
        doc = parser.getDocument();

        root = (Element) doc.getChildNodes();
        classroom = (Element) root.getChildNodes();
        students = (Element) students.getChildNodes();
    }

    public static void saveToFile(File f) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //Result output = new StreamResult(new File("src\\sample\\res\\classrooms.xml"));
        Result output = new StreamResult(f);
        Source input = new DOMSource(doc);

        transformer.transform(input, output);
        transformer.transform(input, new StreamResult(System.out));
    }

    public static void newStudent(String n, int sID){ //n - name, sID - student ID
        Element student = doc.createElement("student");
        student.setAttribute("id", Integer.toString(sID));
        students.appendChild(student);
        Element name = doc.createElement("name");
        name.setTextContent(n);
        student.appendChild(name);
        Element grades = doc.createElement("grades");
        student.appendChild(grades);
        /*Element grade = doc.createElement("grade");
        grade.setAttribute("exp", "T1");
        grade.setAttribute("id", "A1");
        grade.setTextContent("6");
        grades.appendChild(grade);
        Element grade2 = doc.createElement("grade");
        grade2.setAttribute("exp", "T2");
        grade2.setAttribute("id", "A1");
        grade2.setTextContent("13");
        grades.appendChild(grade2);
        Element grade3 = doc.createElement("grade");
        grade3.setAttribute("exp", "T3");
        grade3.setAttribute("id", "A1");
        grade3.setTextContent("15");
        grades.appendChild(grade3);*/
    }

    public static void newTask(String n, String c, String[] exp){ //n - plain text name, c - code, exp[] - expectations
        Element task = doc.createElement("task");
        task.setAttribute("id", c);
        tasks.appendChild(task);
        Element name = doc.createElement("name");
        name.setTextContent(n);
        task.appendChild(name);
        for(int i=0; i < exp.length; i++){
            Element expectation = doc.createElement("expectation");
            expectation.setTextContent(exp[i]);
            task.appendChild(expectation);
        }
    }

    public static String getStudentNames(int i){ //i is the ID of the student whose name is needed to be looked up
        return students.getChildNodes().item(i).getFirstChild().getTextContent();
    }

    public static String[] getTaskNames(int i){ //i is the ID of the task which name/code is needed to be looked up
        return new String[]{tasks.getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue(),
                             tasks.getChildNodes().item(i).getFirstChild().getFirstChild().getTextContent()};
    }

    public static String getExpectationNames(String s, int e){ //s - name of task, i - id of expectation to look up
        int num = 0;
        String expName = "ERR";
        for(Node child = tasks.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        String taskID = s.substring(s.indexOf("[")+1,s.indexOf("]"));
        for(int i = 0; i < num; i++) {
            if(tasks.getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue().equals(taskID)){
                expName = tasks.getChildNodes().item(i).getChildNodes().item(e+1).getTextContent();
            }
        }
        return expName;
    }

    public static int getNumOfStudents(){
        int num = 0;
        for(Node child = students.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        return num;
    }

    public static int getNumOfExpectations(String s){
        int num = 0;
        for(Node child = tasks.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        int numofexp = 0;
        String taskID = s.substring(s.indexOf("[")+1,s.indexOf("]"));
        for(int i = 0; i < num; i++) {
            if(tasks.getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue().equals(taskID)){
                for(Node tasksChild = tasks.getChildNodes().item(i).getFirstChild(); tasksChild != null; tasksChild = tasksChild.getNextSibling()){
                    numofexp++;
                }
            }
        }
        return numofexp - 1;
    }

    public static int getNumofGrades(String s){ //s - name of student whose grades need to be looked up
        int num = 0;
        int numofgrades = 0;
        for(Node child = students.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        for(int i = 0; i < num; i++){
            if(students.getChildNodes().item(i).getFirstChild().getTextContent() == s){
                for(Node child = students.getChildNodes().item(i).getChildNodes().item(1).getFirstChild(); child != null; child = child.getNextSibling()) {
                    numofgrades++;
                }
            }
        }
        return numofgrades;
    }

    public static int getNumOfTasks(){
        int num = 0;
        for(Node child = tasks.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        return num;
    }

    public static String[] getFormattedGradeEntry(String s, int gID){ //returns a string array that can be inserted into the grade table. S is student name, i is the grade pointer
        int num = 0; //number of all students
        String exp = "ERR";
        String id = "";
        int value = 0;
        String[] array = new String[19];
        for(Node child = students.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        for(int i = 0; i < num; i++){
            if(students.getChildNodes().item(i).getFirstChild().getTextContent() == s){
                exp = students.getChildNodes().item(i).getChildNodes().item(1).getChildNodes().item(gID).getAttributes().getNamedItem("exp").getNodeValue();
                id = students.getChildNodes().item(i).getChildNodes().item(1).getChildNodes().item(gID).getAttributes().getNamedItem("id").getNodeValue();
                value = Integer.parseInt(students.getChildNodes().item(i).getFirstChild().getNextSibling().getChildNodes().item(gID).getTextContent());
            }
        }

        array[0] = exp;

        for(int i = 1; i < 19; i++){
            if(i == value){
                array[i] = id;
            }else{
                array[i] = "";
            }
        }

        return array;

        /*
        0 - EXP
        1 - inc
        2 - r-
        3 - r
        4 - r+
        5 - 1-
        6 - 1
        7 - 1+
        8 - 2-
        9 - 2
        10 - 2+
        11 - 3-
        12 - 3
        13 - 3+
        14 - 4-
        15 - 4-/4
        16 - 4
        17 - 4/4+
        18 - 4+
         */
    }
   
    public static String getStudentExpectation (String s, int gID) {
         int num = 0; //number of all students
         String expect = "";
             String[] array = new String[19];
        for(Node child = students.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        for(int i = 0; i < num; i++){
            if(students.getChildNodes().item(i).getFirstChild().getTextContent() == s){
               expect = students.getFirstChild().getLastChild().getFirstChild().getAttributes().getNamedItem("exp").getNodeValue();
               
            }

       
    }
         return expect;
    }
   
     public static String getStudentGrade (String s, int gID) {
         int num = 0; //number of all students
         String id = "";
             String[] array = new String[19];
        for(Node child = students.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        for(int i = 0; i < num; i++){
            if(students.getChildNodes().item(i).getFirstChild().getTextContent() == s){
              id = students.getChildNodes().item(i).getChildNodes().item(1).getChildNodes().item(gID).getAttributes().getNamedItem("id").getNodeValue();
               
            }

       
    }
         return id;
    }

    public static String[] getFormattedStudentRow(int id, String[] exp, String s){ //id is the student number, exp is the existing expectations, s is the name of the task
        String taskID = s.substring(s.indexOf("[")+1,s.indexOf("]")); //getting the expectation ID from a format of [X1] Name
        String [] row = {"", "", "", "", "", "", "", "", "", "", ""}; //11 is always the number of total rows
        int arrayPointer = 1;
        int numofgrades = getNumofGrades(getStudentNames(id)); //number of all grade elements that a student has

        row[0] = getStudentNames(id); //first row is always the student name

        for(int i = 0; i < numofgrades; i++) {
            if(students.getChildNodes().item(id).getLastChild().getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue().equals(taskID)){
                row[arrayPointer] = students.getChildNodes().item(id).getLastChild().getChildNodes().item(i).getTextContent(); //if calling task has the same code as given grade, log it
                arrayPointer++;
            }else{
                //row[arrayPointer] = ""; //if not, leave the cell empty
                //arrayPointer++;
            }
        }
        return row;
    }

    public static void setGrade(String task, String student, String exp, String grade){ //task is the task, student is student's name, grade is grade value
        int pointer = 0; //tracks how many expectations out of the required number (numofexp) are filled in
        boolean isGradeSet = false; //reports whether the required grade object was found and set
        String taskID = task.substring(task.indexOf("[")+1,task.indexOf("]"));
        for(Node child = students.getFirstChild(); child != null; child = child.getNextSibling()) {
            if(child.getFirstChild().getTextContent().equals(student)){
                if(child.getFirstChild().getNextSibling().getFirstChild() == null){ //special case when student doesn't have any grades
                    Element newGrade = doc.createElement("grade");
                    newGrade.setTextContent(grade);
                    newGrade.setAttribute("id", taskID);
                    newGrade.setAttribute("exp", exp);
                    child.getFirstChild().getNextSibling().appendChild(newGrade);
                }
                for(Node expchild = child.getFirstChild().getNextSibling().getFirstChild(); expchild != null; expchild = expchild.getNextSibling()){
                    if(expchild.getAttributes().getNamedItem("id").getNodeValue().equals(taskID)
                            && expchild.getAttributes().getNamedItem("exp").getNodeValue().equals(exp)){
                        expchild.setTextContent(grade);
                        break;
                    }else if(expchild.getNextSibling() == null){ //only attempt to create new grade if you've cycled through all of them beforehand
                        Element newGrade = doc.createElement("grade");
                        newGrade.setTextContent(grade);
                        newGrade.setAttribute("id", taskID);
                        newGrade.setAttribute("exp", exp);
                        child.getFirstChild().getNextSibling().appendChild(newGrade);
                        break;
                    }
                }
            }
        }
    }
    /**
     *
     * This program turns a grade into its numerical equivalent for usage in the getGradeMean method.
     *
     * @param originalGrade grade to convert
     */
public static int translateGrade(String originalGrade) {

    //gradeNumber is an integer which is turned into the numerical equivalent of the grade.
    int gradeNumber = 0;

    //Every possible grade is accounted for in this switch statement; in each case, an integer is set for the grade.
    switch (originalGrade) {
        case "INC":
            gradeNumber = 1;
            break;
        case "R-":
            gradeNumber = 2;
            break;
        case "R-/R":
            gradeNumber = 3;
            break;
        case "R":
            gradeNumber = 4;
            break;
        case "R/R+":
            gradeNumber = 5;
            break;
        case "R+":
            gradeNumber = 6;
            break;
        case "R+/1-":
            gradeNumber = 7;
            break;
        case "1-":
            gradeNumber = 8;
            break;
        case "1-/1":
            gradeNumber = 9;
            break;
        case "1":
            gradeNumber = 10;
            break;
        case "1/1+":
            gradeNumber = 11;
            break;
        case "1+":
            gradeNumber = 12;
            break;
        case "1+/2-":
            gradeNumber = 13;
            break;
        case "2-":
            gradeNumber = 14;
            break;
        case "2-/2":
            gradeNumber = 15;
            break;
        case "2":
            gradeNumber = 16;
            break;
        case "2/2+":
            gradeNumber = 17;
            break;
        case "2+":
            gradeNumber = 18;
            break;
        case "2+/3-":
            gradeNumber = 19;
            break;
        case "3-":
            gradeNumber = 20;
            break;
        case "3-/3":
            gradeNumber = 21;
            break;
        case "3":
            gradeNumber = 22;
            break;
        case "3/3+":
            gradeNumber = 23;
            break;
        case "3+":
            gradeNumber = 24;
            break;
        case "3+/4-":
            gradeNumber = 25;
            break;
        case "4-":
            gradeNumber = 26;
            break;
        case "4-/4":
            gradeNumber = 27;
            break;
        case "4":
            gradeNumber = 28;
            break;
        case "4/4+":
            gradeNumber = 29;
            break;
        case "4+":
            gradeNumber = 30;
            break;

    }
    return gradeNumber;
}

public static String translateNumber (int gradeNumber) {
    String realGrade = "";
    switch (gradeNumber) {
        case 1:
            realGrade = "INC";
            break;
        case 2:
            realGrade = "R-";
            break;
        case 3:
            realGrade = "R-/R";
            break;
        case 4:
            realGrade = "R";
            break;
        case 5:
            realGrade = "R/R+";
            break;
        case 6:
            realGrade = "R+";
            break;
        case 7:
            realGrade = "R+/1-";
            break;
        case 8:
            realGrade = "1-";
            break;
        case 9:
            realGrade = "1-/1";
            break;
        case 10:
            realGrade = "1";
            break;
        case 11:
            realGrade = "1/1+";
            break;
        case 12:
            realGrade = "1+";
            break;
        case 13:
            realGrade = "1+/2-";
            break;
        case 14:
            realGrade = "2-";
            break;
        case 15:
            realGrade = "2-/2";
            break;
        case 16:
            realGrade = "2";
            break;
        case 17:
            realGrade = "2/2+";
            break;
        case 18:
            realGrade = "2+";
            break;
        case 19:
            realGrade = "2+/3-";
            break;
        case 20:
            realGrade = "3-";
            break;
        case 21:
            realGrade = "3-/3";
            break;
        case 22:
            realGrade = "3";
            break;
        case 23:
            realGrade = "3/3+";
            break;
        case 24:
            realGrade = "3+";
            break;
        case 25:
            realGrade = "3+/4-";
            break;
        case 26:
            realGrade = "4-";
            break;
        case 27:
            realGrade = "4-/4";
            break;
        case 28:
            realGrade = "4";
            break;
        case 29:
            realGrade = "4/4+";
            break;
        case 30:
            realGrade = "4+";
            break;
    }
    return realGrade;
}

/**
     *
     * This program takes a list of grades and finds the mean of the list
     *
     * @param gradeValueList grade list to convert into integers and the
     * @return finalGrade the mean of the grade
     */
public static String getGradeMean (String [] gradeValueList) {
        double gradeMean = 0;
        int[] gradeNumberList = new int[gradeValueList.length];
        for (int convertGrades = 0; convertGrades < gradeValueList.length; convertGrades++) {

            gradeNumberList[convertGrades] = translateGrade(gradeValueList[convertGrades]);
        }
        for (int climbGradeList = 0; climbGradeList < gradeNumberList.length; climbGradeList++) {
            gradeMean = gradeMean + gradeNumberList[climbGradeList];
        }
        gradeMean = gradeMean / gradeNumberList.length;

        int roundedGradeMean = (int) Math.round(gradeMean);

        String finalGrade = translateNumber(roundedGradeMean);
        return finalGrade;
    }

}