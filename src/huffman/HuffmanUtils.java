/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;


public class HuffmanUtils {
    
    
    
     static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
     static String[] codes= new String[128];
     
     
     //encode text function
     static  String encodeText(String text) {

        String encoded = "";
         StringBuilder encoded_builder = new StringBuilder();
        for (int i = 0; i < text.length() - 1; i++) {
            encoded_builder.append(codes[(int) text.charAt(i)]);
           
        }
        encoded = encoded_builder.toString();
       
       return encoded;
    }
   
    
    public static void encodeFile(String crunchifyFile) throws FileNotFoundException {

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
                    String code = encodeText(s);
                    System.out.println(code);
                }

                

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    
    
    
    
    
    
    
    
    
    private static void crunchifyLog(String string) {
        System.out.println(string);
    }
    //initialize each character freq to zero
    static void init_freq()
    {
         for (int i = 0; i <  ReadFile.frequencies.length; i++) {
            ReadFile.frequencies[i] = 0;
        }

    }
    //print the freq of each character.
    static void print_freq()
    {
        for (int i = 0; i < ReadFile.frequencies.length; i++) {
            System.out.println((char) i + "     " +  ReadFile.frequencies[i]);
        }
    }
    //get the frequency of each letter in the text inserted and add it in the priority queue.
    static void calc_frequencies_percnt(PriorityQueue<Node> vector, int size ) {

        for (int i = 0; i < ReadFile.frequencies.length; i++) {
            if (ReadFile.frequencies[i] != 0) {
                vector.add(new Node(ReadFile.frequencies[i] / ((size ) * 1.0), ((char) (i)) + ""));

                System.out.println("'" + ((char) (i)) + "' : " + ReadFile.frequencies[i] / ((size) * 1.0));

            }
        }

    }
    static void buildTree(PriorityQueue<Node> vector) {
        
      while (vector.size() > 1) {
          vector.add(new Node(vector.poll(), vector.poll()));
      }
    }
    
    
    //create code for each node, left is add zero right is add 1.
    static void createCode(Node node, String s) {
        if (node != null) {
            if (node.right != null) {
                createCode(node.right, s + "1");
            }

            if (node.left != null) {
                createCode(node.left, s + "0");
            }

            if (node.left == null && node.right == null) {
                //codes.put(node.character.charAt(0), s);
                codes[(int) node.character.charAt(0)] = s;
            }

        }
    }
    
    
     static void printCodes() {
        int i;
        System.out.println("Codes for each letter: ");
        // codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
        for (i = 0; i < 128; i++) {
            System.out.println("'" + (char) i + "' : " + codes[i]);
        }
    }
    
}
