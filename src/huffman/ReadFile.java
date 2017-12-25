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

                // ignore multiple white spaces
                String[] myWords = crunchifyLine.replaceAll("\\s+", " ").split(" ");

                for (String s : myWords) {
                    for (int i = 0; i < s.length(); i++) {
                        frequencies[s.charAt(i)]++;
                    }
                    crunchifyTotalCharacters += s.length();
                }

                crunchifyTotalWords += myWords.length;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        crunchifyLog("\n========== Result ==========");
        totalsize= crunchifyTotalCharacters;
        crunchifyLog("* Total Characters: " + crunchifyTotalCharacters);
        crunchifyLog("* Total Words: " + crunchifyTotalWords);
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
