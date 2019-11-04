package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); //get FXML
        root.getStylesheets().add(getClass().getResource("theme.css").toString()); //get CSS
        primaryStage.setTitle("gradeStack b1101"); //window title
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    DocumentBuilderFactory dbfactory;
    DocumentBuilder dbbuilder;

    static Document doc;
    static Element root;
    static Element classroom;
    static Element students;
    static Element student;
    static Element tasks;

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
            root.appendChild(tasks);
            Element expectations = doc.createElement("expectations");
            root.appendChild(expectations);

            newStudent("Test", 0);
            newStudent("Test2", 1);
            newStudent("Test3", 2);
            newStudent("Test4", 2);

            newExpectation(doc, expectations, "E1");
            newExpectation(doc, expectations, "A1");
            newExpectation(doc, expectations, "D1");

            newTask(doc, tasks, "Megatest", "T1", new String[] {"E1", "A1"}, expectations);

            for(int i=0; i < students.getChildNodes().getLength(); i++){
                System.out.println(i);
            }

            /*for(int i=0; i < expectations.getChildNodes().getLength(); i++){
                System.out.println(expectations.getChildNodes().getLength());
            }*/

            if(expectations.getChildNodes().getLength() != 0){
                for(int i=0; i < expectations.getChildNodes().getLength(); i++){
                    System.out.println(expectations.getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue());
                }
            }


            saveToFile(doc);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void saveToFile(Document d) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Result output = new StreamResult(new File("src\\sample\\res\\classrooms.xml"));
        Source input = new DOMSource(d);

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

    public static void newTask(Document d, Element e, String n, String c, String[] exp, Element e2){ //d - XML document, e - classroom element, n - name, c - code
        Element task = d.createElement("task");
        task.setAttribute("id", c);
        e.appendChild(task);
        Element name = d.createElement("name");
        name.setTextContent(n);
        task.appendChild(name);
        for(int i=0; i < exp.length; i++){
            for(int j=0; j < e2.getChildNodes().getLength(); j++){
                if(e2.getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue() == exp[i]);
            }
        }
    }

    public static void newExpectation(Document d, Element e, String c){ //d - XML document, e - classroom element, n - name, c - code
        Element expectation = d.createElement("expectation");
        expectation.setAttribute("id", c);
        e.appendChild(expectation);
    }

    public static String getStudentNames(int i){ //i is the ID of the student whose name is needed to be looked up
        return students.getChildNodes().item(i).getFirstChild().getTextContent();
    }

    public static int getNumOfStudents(){
        int num = 0;
        for(Node child = students.getFirstChild(); child != null; child = child.getNextSibling()) {
            num++;
        }
        return num;
    }

    public Element getNodeByName(Element e, String s){ //searches through an element's child nodes until it finds one whose name match s, returns it
        for(Node child = e.getFirstChild(); child != null; child = child.getNextSibling()) {
            if(child instanceof Element && e.equals(child.getNodeName())) return (Element) child;
        }
        return null;
    }

}