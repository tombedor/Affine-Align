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
public class Sequence {
    String[] charSequence;
    int[] intSequence;
    int length;
    String fileName;
    public Sequence(basicFile input) throws FileNotFoundException{
        fileName = input.fileName;
        Ops op = new Ops();
        String[] preSplit = input.linesToArray();
        int chars = 0;
        for (int row = 1; row < preSplit.length; row++){
          //  System.out.println(preSplit[row]);
            chars += op.lettersInString(preSplit[row]);
        }
        length = chars;
        charSequence = new String[length];
        int charIndex = 0;
        for (int row = 1; row < preSplit.length; row++){
            String[] line = op.stringToLettersArray(preSplit[row]);
            for (int lineIndex = 0; lineIndex < line.length; lineIndex++){
                charSequence[charIndex] = line[lineIndex].toUpperCase();
                if (line[lineIndex].equals(".")){
                    charSequence[charIndex] = "*";
                }
                if ((line[lineIndex].equals("J"))|(line[lineIndex].equals("O"))){
                    charSequence[charIndex] = "L";
                }
                charIndex++;
            }
        }
        intSequence = new int[length];
        for (int seqIndex = 0; seqIndex < length; seqIndex++){
            String pos = charSequence[seqIndex];
            for (int letIndex = 0; letIndex < ScoringMatrix.letters.length; letIndex++){
                if (ScoringMatrix.letters[letIndex].equals(pos)){
                    intSequence[seqIndex] = letIndex;
                }
            }
                        
        }
    }
      public Sequence(basicFile input, int numb) throws FileNotFoundException{
        Ops op = new Ops();
        String[] preSplit = input.linesToArray();
        int chars = 0;
        int stopsFound = 0;
        for (int row = 1; row < preSplit.length; row++){
          //  System.out.println(preSplit[row]);
            String[] lets = op.stringToLettersArray(preSplit[row]);
            if ((lets[0]).equals(">")){
                stopsFound++;
            }
            if (stopsFound == 1 && !(lets[0]).equals(">")){
            chars += op.lettersInString(preSplit[row]);
            }
        }
        length = chars;
        charSequence = new String[length];
        int charIndex = 0;
        int seqNumber=0;
        for (int row = 0; row < preSplit.length; row++){
            String[] line = op.stringToLettersArray(preSplit[row]);
            if ((line[0]).equals(">")){
                seqNumber++;
            }
            if (seqNumber == numb && !(line[0]).equals(">")){
            for (int lineIndex = 0; lineIndex < line.length; lineIndex++){
                charSequence[charIndex] = line[lineIndex].toUpperCase();
                if (line[lineIndex].equals(".")){
                    charSequence[charIndex] = "*";
                }
                if ((line[lineIndex].equals("J"))|(line[lineIndex].equals("O"))){
                    charSequence[charIndex] = "L";
                }
                charIndex++;
            }
            }
            
        }
        intSequence = new int[length];
        //testing t = new testing();
        //t.printArray(charSequence);
        for (int seqIndex = 0; seqIndex < length; seqIndex++){
            
            String pos = charSequence[seqIndex];
            //System.out.println("for seqIndex "+seqIndex+"character = "+pos);
           // intSequence[seqIndex]= 7777;
            for (int letIndex = 0; letIndex < ScoringMatrix.letters.length; letIndex++){
                //System.out.println("is "+ScoringMatrix.letters[letIndex]+"?");
                
                if (ScoringMatrix.letters[letIndex].equals(pos)){
                    intSequence[seqIndex] = letIndex;
                }
            }
                        
        }
        
    }
  
  
}