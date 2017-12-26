/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

/**
 *
 * @author yomnabarakat
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class ReadFile {
    
    public static int[] frequencies = new int[128];
    static int totalsize;
    static int totalLine;
    
    
    
//pass 1 for counting the no of text and getting the freq.
    public static void readFileAndPrintCounts(String crunchifyFile) throws FileNotFoundException {

        int crunchifyTotalWords = 0;
        int crunchifyTotalLines = 0;
        int crunchifyTotalCharacters = 0;

        String crunchifyLine;

        try (BufferedReader crunchifyBuffer = new BufferedReader(new FileReader(crunchifyFile))) {
            crunchifyLog("========== File Content ==========");

            // read each line one by one
            while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
               
                crunchifyTotalLines++;
                crunchifyLine=crunchifyLine+(char)10;

                for (int i=0; i<  crunchifyLine.length(); i++) {
                    frequencies[crunchifyLine.charAt(i)]++;
                    crunchifyTotalCharacters ++;
                }

                

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        crunchifyLog("\n========== Result ==========");
        frequencies[10]--;
        totalsize= crunchifyTotalCharacters-1;
        totalLine=  crunchifyTotalLines;
        crunchifyLog("* Total Characters: " + crunchifyTotalCharacters);
        crunchifyLog("* Toal Lines: " + crunchifyTotalLines);
    }

    
    
    
    
    

    private static void crunchifyLog(String string) {
        System.out.println(string);
    }
    //initialize each character freq to zero
    static void init_freq()
    {
         for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = 0;
        }
    }


    //print the freq of each character.
    static void print_freq()
    {
        for (int i = 0; i < frequencies.length; i++) {
            System.out.println((char) i + "     " + frequencies[i]);
        }
    }

   public static void Pass1 (String filename){
         init_freq();

        try {
            String crunchifyFile = "input2.txt";
            readFileAndPrintCounts(crunchifyFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        print_freq();
        
    }
}
