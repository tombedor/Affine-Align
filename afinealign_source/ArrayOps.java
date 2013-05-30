/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afinealign;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author thomasbedor
 */
public class ArrayOps {
    public ArrayOps(){};
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
        ArrayOps op = new ArrayOps();
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
