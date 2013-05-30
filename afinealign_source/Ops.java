/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afinealign;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.print.PrintException;

/**
 *
 * @author thomasbedor
 */
public class Ops {
    public Ops(){};
    int lettersInString(String input) throws FileNotFoundException{
        //splits input into an array, each line as an array entry
        //String[] larray = new String[length];
        int i = 0;
        Scanner s = null;
        try {
            s = new Scanner(input);
            s.useDelimiter("");
            while (s.hasNext()){
                //System.out.println(s.next());
                //System.out.println(larray[i]);
                i++;
                s.next();
               // s.next();
                
                //System.out.println(count);
            }
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        return i;
    }
    String[] stringToLettersArray(String input) throws FileNotFoundException{
        //splits input into an array, each line as an array entry
        //String[] larray = new String[length];
        Ops op = new Ops();
        int len = op.lettersInString(input);
        int i = 0;
        String[] output = new String[len];
        Scanner s = null;
        try {
            s = new Scanner(input);
            s.useDelimiter("");
            while (s.hasNext()){
                output[i] = s.next();
                i++;
            }
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        return output;
    }
    public String readFirstWord(String input) throws FileNotFoundException{
        String first;
        int i = 0;
        Scanner s = null;
        try {
            s = new Scanner(input);
            first = s.next();
            
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        return first;
    }
    public int wordsInString(String input) throws FileNotFoundException{
        int i = 0;
        Scanner s = null;
        try {
            s = new Scanner(input);
            while (s.hasNext()){
                i++;
                s.next();
            }
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        return i;
    }
    public String[] stringToArray(String input) throws FileNotFoundException{
        Ops op = new Ops();
        Scanner s = null;
        int size = op.wordsInString(input);
        String[] output = new String[size];
        int i = 0;
        try {
            s = new Scanner(input);
            while (s.hasNext()){
                output[i] = s.next();
                i++;
            }
        } finally { 
            if (s != null) {
                s.close();
            }
        }
        return output;
    }
}
