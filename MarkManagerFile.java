/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import  java.nio.file.Path;
import  java.nio.file.Paths;
import java.nio.file.Files;

/**
 *
 * @author earl
 */
public class MarkManagerFile {
   
    /**
     *
     * This program either creates a new file or opens an existing one
     *
     * @param fileName the file to be created or opened
     */
   public static void createNewFile (String fileName) {
       File studentList = new File (fileName);
       
       //If a file with fileName as its name doesn't already exists, it is created.
       //If it does, it is simply opened.
       if (studentList.exists() == false) {
           IO.createOutputFile(fileName);
   } else {
         IO.createOutputFile(fileName, true);
       }
   
}
   /**
     *
     * This program adds a grade to a file
     *
     * @param newGrade the grade to be added
     * @param fileName the file which the grade is added to
     */
public static void addGrade(Grade newGrade, String fileName) {
    File gradeList = new File (fileName);
    if (gradeList.exists() == false) {
    IO.createOutputFile(fileName);
    }
    else {
       IO.createOutputFile(fileName,true);
    }
 
            IO.println(newGrade.toString());
     IO.closeOutputFile();
     
}

public static void addGrade(Grade newGrade, Student gradedStudent, String fileName) {
    File gradeList = new File (fileName);
    if (gradeList.exists() == false) {
    IO.createOutputFile(fileName);
    }
    else {
       IO.createOutputFile(fileName,true);
    }
 
            IO.println(gradedStudent.toString() + ": " + newGrade.toString());
     IO.closeOutputFile();
     
}

    /**
     *
     * This program allows a new student to be added to a class of students
     *
     * @param newStudent student to add
     * @param fileName name of file to add the student to
     */
public static void addStudent (Student newStudent, String fileName) {
File studentList = new File (fileName);
createNewFile(fileName);
 IO.println(newStudent.toString());
 IO.closeOutputFile();
 
}

/**
     *
     * This program adds a task to the task file
     *
     * @param newTask task to add
     * @param fileName name of file to add the task to
     */
public static void addTask (String newTask, String fileName) {
    File taskList = new File(fileName);
    createNewFile(fileName);
    IO.println(newTask);
    IO.closeOutputFile();
}

public static void addExpectation(String newExpectation, String fileName) {
   File expectationList = new File (fileName);
   createNewFile(fileName);
   IO.println(newExpectation);
   IO.closeOutputFile();
}

/**
     *
     * This program allows a student to be deleted from the class
     *
     * @param leavingStudent student to delete
     * @param fileName name of file to delete and then recreate
     */
public static void deleteStudent(Student leavingStudent, String fileName) {
try {
   
  //The file is opened, and all of the students sans the one to be deleted
  //are counted in the countStudents int.
   IO.openInputFile (fileName);
  BufferedReader findStudent = new BufferedReader(new FileReader(fileName));
  String listStudents = findStudent.readLine();
  int countStudents = 0;
  if (!listStudents.equals(leavingStudent.toString ())) {
  countStudents = 1;
  while (listStudents != null) {
         if (!listStudents.equals(leavingStudent.toString())) {
    countStudents++;
   }
   listStudents = findStudent.readLine();
  }
  }
  else {
      listStudents = findStudent.readLine();
        while (listStudents != null) {
         if (!listStudents.equals(leavingStudent.toString())) {
    countStudents++;
         }
          listStudents = findStudent.readLine();
  }
       
  }
 
  //An array as long as countStudents is created, and all of the students
  //sans the one for deletion are added.
  String [] updatedStudentList = new String [countStudents];
  findStudent = new BufferedReader(new FileReader(fileName));
 listStudents = findStudent.readLine();
 countStudents = 0;
 while (listStudents != null) {
   if (!listStudents.equals(leavingStudent.toString())) {
  updatedStudentList[countStudents] = listStudents;
   }
  listStudents = findStudent.readLine();
  if (!listStudents.equals(leavingStudent.toString())) {
    countStudents++;
   }  
}
 
 //A path is created to fileName, and the file is deleted and then recreated
Path originalFile = Paths.get(fileName);
 Files.delete(originalFile);
IO.createOutputFile(fileName);


//The students from the array are added to the file, and then the file is closed.
int addStudents = 0;
while (addStudents < updatedStudentList.length) {
   IO.println(updatedStudentList[addStudents]);
}
IO.closeOutputFile();

} catch (Exception e) {
   }
}

public static String getLastName(String fullStudent) {
    int studentCount = 0;
    String lastName = "";
   while (fullStudent.charAt(studentCount) != ',') {
    studentCount++;
    if (fullStudent.charAt(studentCount+1) == ',') {
        lastName = fullStudent.substring(0, studentCount+1);
    }
}
   return lastName;
}

/**
     *
     * This program finds a student's first name.
     *
     * @param fullStudent Student
     */
public static String getFirstName(String fullStudent) {
    int commaPosition = 0;
    String firstName = "";
   
    //This while loop finds where the comma in the student's full, printed name is
    while (fullStudent.charAt(commaPosition) != ',') {
     commaPosition++;
    }
   
   
    firstName = fullStudent.substring(commaPosition+2);
    return firstName;
}


/**
     *
     * This program checks to see if all of the symbols in at least part of a grade are valid
     *
     * @param grade grade to check
     * @param firstPosition position to start from
     */
public static int checkGrade (String grade, int firstPosition) {
 boolean isGrade = false;
 if (grade.equals("INC") ||grade.equals("R") || grade.equals("1") || grade.equals("2") || grade.equals("3") || grade.equals("4")) {
   isGrade = true;
 }  
 else  if (grade.substring(firstPosition,firstPosition+1).equals("R") || grade.substring(firstPosition,firstPosition+1).equals("1") || grade.substring(firstPosition,firstPosition+1).equals("2") || grade.substring(firstPosition,firstPosition+1).equals("3") || grade.substring(firstPosition,firstPosition+1).equals("4")) {
       if (grade.substring(firstPosition+1).equals("-") || grade.substring(firstPosition+1).equals("+")) {
       isGrade = true;
       }
   }
 if (isGrade == true)
     return 0;
 return 1;
}

public static int checkSides(String firstHalf, String secondHalf) {
    boolean firstIsSmaller = false;
   

     if (firstHalf.substring(0,1).equals("R") && secondHalf.substring(0,1).equals("R")) {
         if (firstHalf.substring(1).equals("-") && secondHalf.substring(1).equals(""))  {
        firstIsSmaller = true;
     }
         
       else if (firstHalf.substring(1).equals("") && secondHalf.substring(1).equals("+"))  {
         firstIsSmaller = true;
       }
    }
     else if (firstHalf.substring(0,1).equals("R") && secondHalf.substring(0,1).equals("1") && firstHalf.length() == 2 && secondHalf.length() == 2) {
       if (firstHalf.substring(1,2).equals("+") && secondHalf.substring(1,2).equals("-")) {
          firstIsSmaller = true;
       }
     }
   

    else if (Integer.parseInt(firstHalf.substring(0,1)) == Integer.parseInt(secondHalf.substring(0,1)))  {
     if (firstHalf.substring(1).equals("-") && secondHalf.substring(1).equals(""))  {
        firstIsSmaller = true;
     }
       else if (firstHalf.substring(1).equals("") && secondHalf.substring(1).equals("+"))  {
         firstIsSmaller = true;
       }
 }
 

     
  else if (Integer.parseInt(firstHalf.substring(0,1)) == Integer.parseInt(secondHalf.substring(0,1))-1)  {
if (firstHalf.substring(1).equals("+") && secondHalf.substring(1).equals("-")) {
    firstIsSmaller = true;
}
  }
 
   if (firstIsSmaller == true)
    return 0;  
 return 1;
}


/**
     *
     * This program  checks to see if a grade is valid
     *
     * @param grade grade to check
     */
public static int checkGrade2 (String grade) {
   
  //If the grade is valid, isGrade will become true.
boolean isGrade = false;
   
   
    if (grade.equals("INC")) {
       isGrade = true;
   }
   
    //If the grade has a length of 1 or 2, the program uses the earlier checkGrade method to see if the grade is valid or not.
    else if(grade.length() == 1 || grade.length() == 2) {
      int validityCheck = checkGrade(grade, 0);
      if (validityCheck == 0) {
        isGrade = true;
      }
    }
   

    else if (grade.length() == 3 || grade.length() == 4 || grade.length() == 5 || grade.length() == 6) {

  //checkForSlash is the integer that indicates where the slash that separates an "in-between" grade (3/3+, 4-/4, etc).    
        int checkForSlash = 0;
while (checkForSlash < grade.length()) {
    if (grade.charAt(checkForSlash) == '/') {
        break;
    }
    checkForSlash++;
}

//if the slash is in a legitimate place, the program checks to see if the grade on either side is legitimate, and then uses the checkSides method to see if the entire grade is legitimate.
if (checkForSlash != 0 && checkForSlash < 4) {
 int firstSide = checkGrade(grade.substring(0, checkForSlash), 0);
 int secondSide = checkGrade(grade.substring(checkForSlash+1), 0);
 if (firstSide == 0 && secondSide == 0) {
  int sideComparison = checkSides(grade.substring(0, checkForSlash), grade.substring(checkForSlash+1));
  if (sideComparison == 0) {
      isGrade = true;
  }
}
}
    }
 
  if (isGrade == true)  
      return 0;
  return 1;
}

//public static String translateGrades(int numberGrade) {
//    String actualGrade = "";
//    
//    if (numberGrade)
//    return actualGrade;
//}

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












	
	
	

	
	
	







	
		
