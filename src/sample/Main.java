package sample;

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
        primaryStage.setTitle("gradeStack 2020-01-20");
        primaryStage.setScene(new Scene(StageRoot, 1000, 650)); //default width and height
        primaryStage.show();
    }

    /* Java DOM objects creation*/
    static DocumentBuilderFactory dbfactory;
    static DocumentBuilder dbbuilder;
    static Document doc;
    static Element root;
    static Element classroom;
    static Element students;
    static Element tasks;
    static Element expectations;

    public static void main(String[] args) {
        Main main = new Main();
        main.createXML();
        launch(args);
    }

    public static void createXML(){
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

        }catch(Exception e){ e.printStackTrace(); }
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

        root = doc.getElementById("classrooms");
    }

    public static void saveToFile(File f) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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
    }

    public static String[] getFormattedStudentRow(int id, String[] exp, String s){ //id is the student number, exp is the existing expectations, s is the name of the task
        String taskID = s.substring(s.indexOf("[")+1,s.indexOf("]")); //getting the expectation ID from a format of [X1] Name
        String [] row = {"", "", "", "", "", "", "", "", "", "", ""}; //11 is always the number of total rows
        int arrayPointer = 1;
        int numofgrades = getNumofGrades(getStudentNames(id)); //number of all grade elements that a student has

        row[0] = getStudentNames(id); //first row is always the student name
        String test = "A1";

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
        String taskID = task.substring(task.indexOf("[")+1,task.indexOf("]"));
        for(Node child = students.getFirstChild(); child != null; child = child.getNextSibling()) {
            if(child.getFirstChild().getTextContent().equals(student)){
                if(child.getFirstChild().getNextSibling().getFirstChild() == null){ //special case when student doesn't have any grades
                    Element newGrade = doc.createElement("grade");
                    newGrade.setTextContent(Integer.toString(inputToGrade(grade)));
                    newGrade.setAttribute("id", taskID);
                    newGrade.setAttribute("exp", exp);
                    child.getFirstChild().getNextSibling().appendChild(newGrade);
                }
                for(Node expchild = child.getFirstChild().getNextSibling().getFirstChild(); expchild != null; expchild = expchild.getNextSibling()){
                    if(expchild.getAttributes().getNamedItem("id").getNodeValue().equals(taskID)
                            && expchild.getAttributes().getNamedItem("exp").getNodeValue().equals(exp)){
                        expchild.setTextContent(Integer.toString(inputToGrade(grade)));
                        break;
                    }else if(expchild.getNextSibling() == null){ //only attempt to create new grade if you've cycled through all of them beforehand
                        Element newGrade = doc.createElement("grade");
                        newGrade.setTextContent(Integer.toString(inputToGrade(grade)));
                        newGrade.setAttribute("id", taskID);
                        newGrade.setAttribute("exp", exp);
                        child.getFirstChild().getNextSibling().appendChild(newGrade);
                        break;
                    }
                }
            }
        }
    }

    public static int inputToGrade(String s){
        int realGrade = 1;

        switch(s){
            case "inc": case "INC": case "i": case "I": realGrade = 1; break;
            case "R-": case "r-": realGrade = 2; break;
            case "R": case "r": realGrade = 3; break;
            case "R+": case "r+": realGrade = 4; break;
            case "1-": realGrade = 5; break;
            case "1": realGrade = 6; break;
            case "1+": realGrade = 7; break;
            case "2-": realGrade = 8; break;
            case "2": realGrade = 9; break;
            case "2+": realGrade = 10; break;
            case "3-": realGrade = 11; break;
            case "3": realGrade = 12; break;
            case "3+": realGrade = 13; break;
            case "4-": realGrade = 14; break;
            case "4-/4": case "4/4-": case "4-/": realGrade = 15; break;
            case "4": realGrade = 16; break;
            case "4/4+": case "4+/4": case "4+/": realGrade = 17; break;
            case "4+": realGrade = 18; break;
        }
        return realGrade;
    }

    public static Boolean checkName(String s){ //checks if the name of a new student doesn't conflict with past names
        if(students == null){
            return true;
        }

        for(Node n = students.getFirstChild(); n != null; n = n.getNextSibling()){
            if(n.getFirstChild().getTextContent() == s){return true;}
        }
        return false;
    }

    public static void removeStudent(String s){
        for(Node n = students.getFirstChild(); n != null; n = n.getNextSibling()){
            if(n.getFirstChild().getTextContent() == s){students.removeChild(n);}
        }
    }
}