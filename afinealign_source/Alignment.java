/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afinealign;

import javax.print.PrintException;
import java.io.*;

/**
 *
 * @author thomasbedor
 */
public class Alignment {
    Sequence X;
    Sequence Y;
    double I;
    double E;
    double[][] sMatrix;
    double[][] wMatrix;
    double[][] vMatrix;
    double score;
    int[][] dirMatrix; //encodes -1 for (i-1,j), 0 for (i-1,j-1), 1 for (i,j-1)
    ScoringMatrix scoreMatrix;
    String nameX;
    String nameY;
    
    public Alignment(Sequence s1, Sequence s2, ScoringMatrix mat, double gapOpen, double gapExtend,String name1,String name2){
        scoreMatrix = mat;
        X = s1;
        Y = s2;
        nameX = name1;
        nameY = name2;
        //nameX = X.fileName.split("\\.")[0];
        //nameY = Y.fileName.split("\\.")[1];
        int n = X.length+1;
        int m = Y.length+1;
        //System.out.println("m="+m+"n="+n);
        I = gapOpen;
        E = gapExtend;
        sMatrix = new double[n][m];
        dirMatrix = new int[n][m];
        vMatrix = new double[n][m];
        wMatrix = new double[n][m];
        
        
        
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                dirMatrix[i][j]= 5;
                vMatrix[i][j]= Double.NEGATIVE_INFINITY;
                wMatrix[i][j]= Double.NEGATIVE_INFINITY;
                sMatrix[i][j]= Double.NEGATIVE_INFINITY;
            }
        }
        
        
        //base cases
        sMatrix[0][0] = 0;
        sMatrix[1][0] = -gapOpen;
        
        for (int i=1;i<n;i++){
            dirMatrix[i][0]= -1;
        }
        sMatrix[0][1] = -gapOpen;
        for (int j=1; j<m; j++){
        dirMatrix[0][j] = 1;
        }
        for (int i=2; i < n; i++){
            sMatrix[i][0] = (sMatrix[i-1][0]-gapExtend);
        }
        for (int j=2; j < m; j++){
            sMatrix[0][j] = (sMatrix[0][j-1]-gapExtend);
        }
        for (int i=0;i<n;i++){
            wMatrix[i][1] = sMatrix[i][0]-I;
        }
        for (int j=0;j<m;j++){
            vMatrix[1][j] = sMatrix[0][j]-I;
        }
                
        
        
        
    }
    
    //recursive functions
   double S(int i,int j) throws PrintException{
       //System.out.println("CALL:S for i="+i+" j="+j+"\n");
    
       
       //If spot is filled:
       //System.out.println("sMatrix["+i+"]["+j+"]="+sMatrix[i][j]);
       if (sMatrix[i][j] != Double.NEGATIVE_INFINITY){
           //System.out.println("S dp triggered for i="+i+" j="+j);
           return sMatrix[i][j];
       }
       
       else{
           int x = X.intSequence[i-1];
           int y = Y.intSequence[j-1];
           double s = scoreMatrix.probMatrix[x][y];
           //System.out.println("s= "+s);
           //System.out.println("s for "+i+", "+j+"=");
           double sValue = S(i-1,j-1)+s;
           //System.out.println(sValue);
           //System.out.println("w for "+i+", "+j+"=");
           double wValue = W(i,j);
           //System.out.println(wValue);
           //System.out.println("v for "+i+", "+j+"=");
           double vValue = V(i,j);
           //System.out.println(vValue);
            if (sValue >= wValue & sValue >=vValue){
               sMatrix[i][j] = sValue;
               //System.out.println("Scs1");
               dirMatrix[i][j] = 0;
           }
           else if (vValue >= wValue & vValue > sValue){
               sMatrix[i][j] = vValue;
               dirMatrix[i][j] = -1;
             //  System.out.println("Scs2");
           }
           else if (wValue > sValue & wValue > vValue){
               sMatrix[i][j] = wValue;
               dirMatrix[i][j] = 1;
               //System.out.println("Scs3");
           }
           else {throw new PrintException("S dp matrix fillin failed");} 
        //   System.out.println("sMatrix["+i+"]["+j+"]= "+sMatrix[i][j]);
      }
       return sMatrix[i][j];
   }
   double V(int i,int j) throws PrintException{
       //System.out.println("CALL:V for i="+i+" j="+j+"\n");
       //System.out.println("vMatrix["+i+"]["+j+"]="+vMatrix[i][j]);
       
       //If spot is filled:
       if (vMatrix[i][j] != Double.NEGATIVE_INFINITY){
           //System.out.println("V dp triggered for i="+i+" j="+j);
           return vMatrix[i][j];
       }
       else{
           //System.out.println("v for "+(i-1)+", "+j+"=");
           double vValue = V(i-1,j)-E;
           //System.out.println(vValue);
           //System.out.println("s for "+(i-1)+", "+j+"=");
           double sValue = S(i-1,j)-I;
           //System.out.println(sValue);
           
     
           if (vValue >= sValue){
               vMatrix[i][j] = vValue;
            //   System.out.println("Vcs1");
           }

           else if (sValue >= vValue){
               vMatrix[i][j] =sValue;
               //System.out.println("Vcs2");
           }
           else {throw new PrintException("V dp matrix fillin failed");} 
    }
       return vMatrix[i][j];
        
   }
   double W(int i, int j) throws PrintException{
       //System.out.println("CALL:W for i="+i+" j="+j+"\n");
       //System.out.println("wMatrix["+i+"]["+j+"]="+wMatrix[i][j]);
     
            //If spot is filled:
       if (wMatrix[i][j] != Double.NEGATIVE_INFINITY){
           //System.out.println("W dp triggered for i="+i+" j="+j);
           return wMatrix[i][j];
       }
       else{
           //System.out.println("w for "+(i)+", "+(j-1)+"=");
           double wValue = W(i,j-1)-E;
           //System.out.println(wValue);
           //System.out.println("s for "+(i)+", "+(j-1)+"=");
           double sValue = S(i,j-1)-I;
           //System.out.println(sValue);
     
           if (wValue >= sValue){
               wMatrix[i][j] = wValue;
             //  System.out.println("Wcs1");
           }

           else if (sValue > wValue){
               wMatrix[i][j] = sValue;
               //System.out.println("Wcs2");
        }
           else {throw new PrintException("W dp matrix fillin failed");} 
        }
       return wMatrix[i][j];
   }
   double getScore() throws PrintException{
       score = (S(X.length,Y.length));
       //System.out.println(sMatrix[X.length][Y.length]);
       return score;
   }
   
   String traceBack(){
   //reads in sequences in reverse, then prints output
       
       int i = X.length;
       int j = Y.length;
       String seqX = "";
       String seqY = "";    
       if (vMatrix[X.length][Y.length] == Double.NEGATIVE_INFINITY){
           return "call V(i,j) before trying traceback";
       }
       else{       
           while (i >0 | j>0){
               //System.out.print("dirMatrix["+i+"]["+j+"]= "+dirMatrix[i][j]);
               if (dirMatrix[i][j] == 0){
                   seqX = X.charSequence[i-1] + seqX; //character sequence starts at 0, alignment matrix starts at 1
                   seqY = Y.charSequence[j-1] +seqY;
                   i--;
                   j--;
               }
               if (dirMatrix[i][j] == -1){//space in Y
                   seqY = "." + seqY;
                   seqX = X.charSequence[i-1] + seqX;
                   i--;
               }
               if (dirMatrix[i][j] == 1){//space in X
                   seqX = "." + seqX;
                   seqY = Y.charSequence[j-1] + seqY;
                   j--;
               }
           }


       }
       return (">"+nameX+"\n"+seqX+"\n>"+nameY+"\n"+seqY);
   }
   void writeTraceback(String fileName) throws IOException{
       //old method (with directory as input)
       //String fileName = nameX+"_"+nameY;
       //FileWriter fstream = new FileWriter(dir+"/"+fileName); 
       FileWriter fstream = new FileWriter(fileName); 
        try (BufferedWriter out = new BufferedWriter(fstream)) {
            out.write(traceBack());
        }

   
   
   
   }
   
     
        
        
        

        
      
    
    
}
