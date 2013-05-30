/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afinealign;

import java.io.FileNotFoundException;

/**
 *
 * @author thomasbedor
 * http://docs.oracle.com/javase/tutorial/essential/io/charstreams.html
 */
public class ToDoList {
  basicFile source;
  String[][] DoList;  
  int length;
  String[] scores;
  String[] fileNames;
  public ToDoList(basicFile input) throws FileNotFoundException{
      /*Splits textfile into an array of strings, then splits each string in
      array into two strings in seqList.
      */
      source = input;
      length = source.getFileLength();
      String[] preSplit = source.linesToArray();
      fileNames = preSplit;
      String[][] output = new String[length][2];
      for (int index=0; index < length; index++){
          String a = preSplit[index];
          String str0 = a.split("\\_")[0];
          String str1 = a.split("\\_")[1];
          output[index][0]=str0;
          output[index][1]=str1;
      }
      DoList = output;     
      
      //Print Scores.
      
  } 
  void printDoList(){
      for (int index = 0; index <length; index++){
          System.out.println(DoList[index][0]+", "+DoList[index][1]);
      }
  }

  
}
          
          
  
    

