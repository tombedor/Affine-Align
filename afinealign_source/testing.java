/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afinealign;

/**
 *
 * @author thomasbedor
 */
public class testing {
    public testing(){};
    void stringTest(){
        String a = "fugglesticks";
        System.out.println(a);
    }
    void printArray(String[] input){
        int len = input.length;
        for (int x = 0; x<len; x++){
            System.out.println(input[x]);
        }
    }
     void printArray(int[] input){
        int len = input.length;
        for (int x = 0; x<len; x++){
            System.out.println(input[x]);
        }
    }
    void printArray(String[][] input){
        int len = input.length; 
        for (int x=0; x<len; x++){
            for (int y=0; y<len; y++)
                System.out.println(input[x][y]);
        }
    }
    void printArray(int[][] input){
        int len = input.length; 
        for (int x=0; x<len; x++){
            for (int y=0; y<len; y++)
                System.out.println(input[x][y]);
        }
    }
    void printArray(double[][] input){
        int len = input.length; 
        for (int x=0; x<len; x++){
            for (int y=0; y<len; y++)
                System.out.println(input[x][y]);
        }
    }
    void splitterTest(){
    String a = "a_jpg";
    String str = a.split("\\_")[0];
    System.out.println(str);
    
    }
    int a = 5;
    int[] filled = new int[a+1];
    
    


    double fact(int n){
        filled[0]=1;
        filled[1]=1;
        int ans =0;

        if (filled[n]!=0){
            return filled[n];
        }
        else {
            int ind = n-1;
            ans = (int) (fact(ind)*n);
            filled[n]=ans;
            return ans;
        }        
    }
}
    
    
    

