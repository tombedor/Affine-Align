/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afinealign;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.print.PrintException;



/**
 *
 * @author thomasbedor
 */
/*
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
 * 
 */

public class AfineAlign {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, PrintException, IOException  {
        System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        
        
        //MAIN LOOP
        
        String masterDir = "./";
        basicFile tdlis = new basicFile("train_aln/aln-list");
        basicFile blosSour = new basicFile("BLOSUM62");
        ToDoList doList = new ToDoList(tdlis);
        
        ScoringMatrix blosum;
        blosum = new ScoringMatrix(blosSour);
        int[] zeroSValues = {-1,0,1};
        int[] gapOValues = {3,5,7,9,11};
        double[] gapEValues = {.5,1.0,1.5,2.0};
        
        ScoringMatrix prob = new ScoringMatrix(doList,"train_aln/refaln/");
        
        
        for (int zsI =0; zsI < zeroSValues.length; zsI++){
            for (int goI = 0; goI < gapOValues.length; goI++){
                for (int geI = 0; geI < gapEValues.length; geI++){
                    int zS = zeroSValues[zsI];
                    int gO = gapOValues[goI];
                    double gE = gapEValues[geI];
                    
                    //Make directory for alignment files
                    String dir = (masterDir+"output/BLOSUM_zS_"+zS+"__gO_"+gO+"__gE_"+gE);
                    File d = new File(dir);
                    d.mkdir();
                    
                    //Create scoring matrix
                    blosum = new ScoringMatrix(doList,"train_aln/refaln/");
                    blosum.zeroShift(zS);
                    System.out.println("Computing for folder"+dir);
                    for (int doIndex = 0; doIndex < doList.length; doIndex++){
                        //System.out.println(doList.fileNames[doIndex]);
                        
                        //Make sequences
                        String seqPath = doList.fileNames[doIndex];
                        String p1 = seqPath.split("\\_")[0];
                        String p2 = seqPath.split("\\_")[1];
                        String name1 = p1;
                        String name2 = p2;
                        p1 = masterDir+"/train_aln/fasta/"+p1+".fa";
                        p2 = masterDir+"/train_aln/fasta/"+p2+".fa";
                        basicFile bf1 = new basicFile(p1);
                        basicFile bf2 = new basicFile(p2);
                        Sequence seqA = new Sequence(bf1);
                        Sequence seqB = new Sequence(bf2);
                        
                        //Align
                        Alignment align = new Alignment(seqA,seqB,blosum,gO,gE,name1,name2);
                        align.getScore();
                        align.writeTraceback(dir+"/"+name1+"_"+name2+".fa");
                   }
                }
          }
        }
       // File f = new File("/usr/local/tmp/test");
       // f.mkdir();
        
        
        
        
        
        
        for (int zsI =0; zsI < zeroSValues.length; zsI++){
            for (int goI = 0; goI < gapOValues.length; goI++){
                for (int geI = 0; geI < gapEValues.length; geI++){
                    int zS = zeroSValues[zsI];
                    int gO = gapOValues[goI];
                    double gE = gapEValues[geI];
                    
                    //Make directory for alignment files
                    String dir = (masterDir+"outputP/BLOSUM_zS_"+zS+"__gO_"+gO+"__gE_"+gE);
                    File d = new File(dir);
                    d.mkdir();
                    
                    //Create scoring matrix
                    blosum = new ScoringMatrix(blosSour);
                    blosum.zeroShift(zS);
                    System.out.println("Computing for "+dir);
                    for (int doIndex = 0; doIndex < doList.length; doIndex++){
                        //System.out.println(doList.fileNames[doIndex]);
                        
                        //Make sequences
                        String seqPath = doList.fileNames[doIndex];
                        String p1 = seqPath.split("\\_")[0];
                        String p2 = seqPath.split("\\_")[1];
                        String name1 = p1;
                        String name2 = p2;
                        p1 = masterDir+"/train_aln/fasta/"+p1+".fa";
                        p2 = masterDir+"/train_aln/fasta/"+p2+".fa";
                        basicFile bf1 = new basicFile(p1);
                        basicFile bf2 = new basicFile(p2);
                        Sequence seqA = new Sequence(bf1);
                        Sequence seqB = new Sequence(bf2);
                        
                        //Align
                        Alignment align = new Alignment(seqA,seqB,blosum,gO,gE,name1,name2);
                        align.getScore();
                        align.writeTraceback(dir+"/"+name1+"_"+name2+".fa");
                   }
                }
          }
        }
        
        ///File P = new File("/usr/local/tmp/test");
       // P.mkdir();
       
    /*Empirically: Best settings:
     * For blosum:
     * zero shift = 1
     * gap open penalty = 3
     * gap extend penalty = 1.5
     * 
     * For probability matrix:
     * zero shift = 1
     * gap open penalty = 11
     * gap extend penalty = 2.0
      */ 
    basicFile tl = new basicFile("test_aln/aln-list");
    ToDoList testList = new ToDoList(tl);
    String dir = "testOut/";
    blosum = new ScoringMatrix(blosSour);
    blosum.zeroShift(1);
    
    for (int doIndex = 0; doIndex < testList.length; doIndex++){
        //System.out.println(doList.fileNames[doIndex]);
        System.out.println("Computing for folder"+dir);
        //Make sequences
        String seqPath = testList.fileNames[doIndex];
        String p1 = seqPath.split("\\_")[0];
        String p2 = seqPath.split("\\_")[1];
        String name1 = p1;
        String name2 = p2;
        p1 = masterDir+"/test_aln/fasta/"+p1+".fa";
        p2 = masterDir+"/test_aln/fasta/"+p2+".fa";
        basicFile bf1 = new basicFile(p1);
        basicFile bf2 = new basicFile(p2);
        Sequence seqA = new Sequence(bf1);
        Sequence seqB = new Sequence(bf2);

        //Align
        Alignment align = new Alignment(seqA,seqB,blosum,3,1.5,name1,name2);
        align.getScore();
        align.writeTraceback(dir+"/"+name1+"_"+name2+".fa");
    }
    dir = "testOutP";
    blosum = new ScoringMatrix(doList,"train_aln/refaln/");
    blosum.zeroShift(1);
    
    for (int doIndex = 0; doIndex < testList.length; doIndex++){
        //System.out.println(doList.fileNames[doIndex]);
        System.out.println("Computing for folder"+dir);
        //Make sequences
        String seqPath = testList.fileNames[doIndex];
        String p1 = seqPath.split("\\_")[0];
        String p2 = seqPath.split("\\_")[1];
        String name1 = p1;
        String name2 = p2;
        p1 = masterDir+"/test_aln/fasta/"+p1+".fa";
        p2 = masterDir+"/test_aln/fasta/"+p2+".fa";
        basicFile bf1 = new basicFile(p1);
        basicFile bf2 = new basicFile(p2);
        Sequence seqA = new Sequence(bf1);
        Sequence seqB = new Sequence(bf2);

        //Align
        Alignment align = new Alignment(seqA,seqB,blosum,11,2.0,name1,name2);
        align.getScore();
        align.writeTraceback(dir+"/"+name1+"_"+name2+".fa");
    }
     
    
    
    }
    
}

