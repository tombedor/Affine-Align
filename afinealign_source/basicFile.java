/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afinealign;

/**
 *
 * @author tom
 */

import java.io.IOException;
import java.io.*;
import java.util.Scanner;

public class basicFile {
    public String target;
    public int length;
    public String directory;
    public String fileName;
    public basicFile(String path) throws FileNotFoundException{
        target = path;
        int count = 0;
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(target)));
            s.useDelimiter("\n");
            while (s.hasNext()){
                count++;
                s.next();
              //  System.out.println(s.next());
                //System.out.println(count);
            }
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        length = count; 
    }
    public basicFile(String dir, String filen) throws FileNotFoundException{
        target = dir+filen;
        fileName = filen;
        directory = dir;
        
        int count = 0;
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(target)));
            s.useDelimiter("\n");
            while (s.hasNext()){
                count++;
                s.next();
              //  System.out.println(s.next());
                //System.out.println(count);
            }
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        length = count; 
    }

    
    String[] linesToArray() throws FileNotFoundException{
        //splits input into an array, each line as an array entry
        String[] larray = new String[length];
        int i = 0;
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(target)));
            s.useDelimiter("\n");
            while (s.hasNext()){
                //System.out.println(s.next());
                larray[i] = s.next();
                //System.out.println(larray[i]);
                i++;
               // s.next();
                
                //System.out.println(count);
            }
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        return larray;
    }
    
    int getFileLength(){
        return length;}
}



    /*throws FileNotFoundException {
        int count = 0;
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(target)));
            
            while (s.hasNext()){
                count++;
                s.next();
              //  System.out.println(s.next());
                //System.out.println(count);
            }
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        return count; 
        }
        
        */
        
        
        
   
    
    
    
    

