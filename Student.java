/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markmanager;

/**
 *
 * @author earl
 */
public class Student {
    
    public String firstName;
    public String lastName;
    
    public Student () {
        
    }
    
    public Student (String fname, String lname) {
      firstName = fname;
      lastName = lname;
    }
    
    public String getName() {
        return firstName + lastName;
    }
    
    @Override
   public String toString () {
   return lastName + ", " + firstName;    
   }
    
}
