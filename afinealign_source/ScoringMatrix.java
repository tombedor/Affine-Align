/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afinealign;

import java.io.FileNotFoundException;
import javax.print.PrintException;

/**
 *
 * @author thomasbedor
 */
public class ScoringMatrix {
    int[][] intMatrix = new int[24][24];
    static String[][] charMatrix = new String[24][24];
    static String[] letters = new String[24];
    int[][] probMatrix = new int[24][24];
    double[][] probDMatrix = new double[24][24];
    int[][] countMatrix = new int[24][24];//count incidences of character alignments
    public ScoringMatrix(basicFile input) throws FileNotFoundException{
        Ops op = new Ops();
        String[] prePrune = input.linesToArray();
        int count = 0;
        for (int index=0; index<prePrune.length; index++){//Determine how big array will be
            String line = prePrune[index];
            String first = op.readFirstWord(line);
            if (!"#".equals(first)){
                count++;
            }
        }
        String[] raw = new String[count]; // new matrix to prune away header.
        int rawi = 0;
        for (int index=0; index<prePrune.length; index++){
            String[] line = op.stringToArray(prePrune[index]);
            String first = line[0];
            String second = line[1];
            if (!"#".equals(first) && !"R".equals(second)){
                raw[rawi] = prePrune[index];
                rawi++;
            }
        }
        for (int indexRow=0; indexRow < 24; indexRow++){
            String[] line = op.stringToArray(raw[indexRow]);
            letters[indexRow] = line[0];
            for (int indexCol = 0; indexCol <24; indexCol++){
                intMatrix[indexRow][indexCol] = Integer.valueOf(line[indexCol+1]); 
            }
        }
        for (int indexRow = 0; indexRow<24; indexRow++){
            for (int indexCol = 0; indexCol <24; indexCol++){
                charMatrix[indexRow][indexCol] = letters[indexRow]+letters[indexCol];
            }
        }
        probMatrix = intMatrix;
    }
    public ScoringMatrix(ToDoList inList, String dir) throws FileNotFoundException, PrintException{
        //dir contains sequence files.
        for (int x=0;x<24;x++){
            for (int y=0;y<24;y++){
                charMatrix[x][y] = letters[x]+letters[y];
            }
        }
        for (int doIndex = 0; doIndex < inList.length; doIndex++){
            basicFile bas = new basicFile(dir+"/"+inList.fileNames[doIndex]+".fa");
            //System.out.println(dir+"/"+inList.fileNames[doIndex]+".fa");
            Sequence seqA = new Sequence(bas, 1);
            Sequence seqB = new Sequence(bas, 2);
            if (seqA.length != seqB.length){
                throw new PrintException("Aligned sequences are not the same length");
            }
           // System.out.println("Computing sequence pairs"+doIndex+"/"+inList.length+":\n"+inList.fileNames[doIndex]);
            for (int seqIndex=0; seqIndex < seqA.length; seqIndex++){
                int x = seqA.intSequence[seqIndex];
                int y = seqB.intSequence[seqIndex];
                countMatrix[x][y]++;
            }
            
        }
        
        
        //since previous for loop only uypdates one of the two entries, add (i,j) to (j,i) and vice versa
        for (int i = 0; i<24; i++){
            for (int j = 0; j<24; j++){
                if (i != j){// diagonals do not need to be updated.
                    int ij = countMatrix[i][j];
                    int ji = countMatrix[j][i];
                    countMatrix[i][j] += ji;
                    countMatrix[j][i] += ij;
                }
            }
            
        }
        //pseudocount:
        for (int i=0; i<24; i++){
            for (int j=0; j<24; j++){
                countMatrix[i][j]+=1;
            }
        }
        
        int sum;
        sum = 0;
        for (int i=0; i<23; i++){
            for (int j=0; j<23; j++){
                if (i>=j){
                    sum+= countMatrix[i][j];
                }
            }
        }
        double dsum;
        double prior = (double)(23*23);
        dsum = (double)sum;
        for (int i=0; i<23; i++){
            for (int j=0; j<23; j++){
                if (i>=j){
                    double c = (double)countMatrix[i][j];
                    Double pDoub = (Math.log(c/prior));
                    
                    int pInt = pDoub.intValue();
                    probMatrix[i][j] = pInt;
                    probMatrix[j][i] = pInt;
                    probDMatrix[i][j] = pDoub;
                    probDMatrix[j][i] = pDoub;
                    
                }
            }
        }
        
      //  testing t = new testing();
       // t.printArray(probMatrix);
    }/*
        String[] raw = new String[count]; // new matrix to prune away header.
        int rawi = 0;
        for (int index=0; index<prePrune.length; index++){
            String[] line = op.stringToArray(prePrune[index]);
            String first = line[0];
            String second = line[1];
            if (!"#".equals(first) && !"R".equals(second)){
                raw[rawi] = prePrune[index];
                rawi++;
            }
        }
        for (int indexRow=0; indexRow < 24; indexRow++){
            String[] line = op.stringToArray(raw[indexRow]);
            letters[indexRow] = line[0];
            for (int indexCol = 0; indexCol <24; indexCol++){
                intMatrix[indexRow][indexCol] = Integer.valueOf(line[indexCol+1]); 
            }
        }
        for (int indexRow = 0; indexRow<24; indexRow++){
            for (int indexCol = 0; indexCol <24; indexCol++){
                charMatrix[indexRow][indexCol] = letters[indexRow]+letters[indexCol];
            }
        }
    }
    */ 
    public int Score(Sequence seq1, Sequence seq2){
        int score = 0;
        for (int index = 0; index<seq1.intSequence.length; index++){
            int x = seq1.intSequence[index];
            int y = seq2.intSequence[index];
            score += intMatrix[x][y];
        }
        return score;
    }

    public double ScoreD(int[] seq1, int[] seq2){
    int score = 0;
        for (int index = 0; index<seq1.length; index++){
            int x = seq1[index];
            int y = seq2[index];
            score += probMatrix[x][y];
        }
        return score; 
    }
    public void zeroShift(int shift){
        for (int x = 0; x<24; x++){
            for (int y = 0; y<24; y++){
                intMatrix[x][y]+=shift;
            }
        }
        
    
    
    }
    
}
