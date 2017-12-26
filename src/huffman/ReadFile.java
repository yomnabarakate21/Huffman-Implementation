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

public class ReadFile {

    static int[] frequencies = new int[128];

    public static void readFileAndPrintCounts(String crunchifyFile) throws FileNotFoundException {

        int crunchifyTotalWords = 0;
        int crunchifyTotalLines = 0;
        int crunchifyTotalCharacters = 0;

        String crunchifyLine;

        // Read file contents
        // The try-with-resources statement is a try statement that declares one or more resources. A resource is an
        // object that must be closed after the program is finished with it. The try-with-resources statement ensures
        // that each resource is closed at the end of the statement.
        try (BufferedReader crunchifyBuffer = new BufferedReader(new FileReader(crunchifyFile))) {
            crunchifyLog("========== File Content ==========");

            // read each line one by one
            while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
                //crunchifyLog(crunchifyLine);
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

        crunchifyLog("* Total Characters: " + crunchifyTotalCharacters);
        crunchifyLog("* Total Words: " + crunchifyTotalWords);
        crunchifyLog("* Toal Lines: " + crunchifyTotalLines);
    }

    private static void crunchifyLog(String string) {
        System.out.println(string);
    }

//    public static void main(String[] args) {
//
//        for (int i = 0; i < frequencies.length; i++) {
//            frequencixes[i] = 0;
//        }
//
//        try {
//            String crunchifyFile = "input2.txt";
//            readFileAndPrintCounts(crunchifyFile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < frequencies.length; i++) {
//            System.out.println((char)i +"     " +frequencies[i]);
//        }
//
//    }
}
