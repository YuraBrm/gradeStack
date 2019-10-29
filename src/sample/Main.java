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
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException, TransformerException {
        launch(args);
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbbuilder;

        try{
            dbbuilder = dbfactory.newDocumentBuilder();
            Document doc = dbbuilder.newDocument();

            //XML element setup
            Element root = doc.createElement("classrooms");
            doc.appendChild(root);
            root.setAttribute("id", "0");
            Element classroom = doc.createElement("classroom");
            root.appendChild(classroom);
            Element students = doc.createElement("students");
            classroom.appendChild(students);
            Element tasks = doc.createElement("tasks");
            root.appendChild(tasks);
            Element expectations = doc.createElement("expectations");
            root.appendChild(expectations);

            newStudent(doc, students, "Test", 0);
            newStudent(doc, students, "Test2", 1);
            newStudent(doc, students, "Test3", 2);

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

    public static void newStudent(Document d, Element e, String n, int sID){ //d - XML document, e - classroom element, n - name, sID - student ID
        Element student = d.createElement("student");
        student.setAttribute("id", Integer.toString(sID));
        e.appendChild(student);
        Element name = d.createElement("name");
        name.setTextContent(n);
        student.appendChild(name);
        Element grades = d.createElement("grades");
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

    public static String returnAllStudents(Element e){
        return e.getElementsByTagName("name").item(1).getTextContent();
    }

    public int numOfStudents(Element e){
        return e.getChildNodes().getLength();
    }
}
