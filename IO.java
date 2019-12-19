


 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markmanager;

import java.io.*;

public class IO {
    
private static PrintWriter fileOut;


/**
* Creates a new file (fileName) in the current
* folder and places a reference to it in fileOut
* @param fileName Represents the name of the file
*/
public static void createOutputFile(String fileName)
{
createOutputFile(fileName, false);

// try
// {
// fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
// }
// catch(IOException e)
// {
// System.out.println("*** Cannot create file: " + fileName + " ***");
// }
}

/**
* Creates a new file (fileName) in the current
* folder and places a reference to it in fileOut
* @param fileName Represents the name of the file
* @param append   True if you want to add to the existing information,
*   false if you want to re-write the entire file
*/
public static void createOutputFile(String fileName, boolean append)
{
try
{
fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, append)));
}
catch(IOException e)
{
System.out.println("*** Cannot create file: " + fileName + " ***");
}
}

/**
* Text is added to the current file
* @param text The characters that will be added to the file
*/
public static void print(String text)
{
fileOut.print(text);
}


/**
* Text is added to the current file and a new line
* is inserted at the end of the characters
* @param text The characters that will be added to the file
*/
public static void println(String text)
{
fileOut.println(text);
}


/**
* Close the file that is currently being written to
* NOTE: This method MUST be called when you are finished
* writing to a file in order to have your changes saved
*/
public static void closeOutputFile()
{
fileOut.close();
}





/* VARIABLE AND METHODS NEEDED FOR READING FROM A FILE */

private static BufferedReader fileIn;


/**
* Opens a file called fileName (that must be
* stored in the current folder) and places a
* reference to it in fileIn
* @param fileName The name of a file that already exists
*/
public static void openInputFile(String fileName)
{
try
{
fileIn = new BufferedReader(new FileReader(fileName));
}
catch(FileNotFoundException e)
{
System.out.println("***Cannot open " + fileName + "***");
}
}


/**
* Read the next line from the file and return it
*/
public static String readLine()
{
try
{
return fileIn.readLine();
}
catch(IOException e){}

return null;
}


/**
* Close the file that is currently being read from
*/
public static void closeInputFile() // throws IOException
{
try
{
fileIn.close();
}
catch(IOException e){}
}

} // end class

