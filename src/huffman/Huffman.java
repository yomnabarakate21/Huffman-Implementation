/*The code works as follows:
1)Create the node class.
2)Reads the text from a file and stores it in a string, count the number of repetition of each character and stores it in frequency array.
3)each character has a node and is inserted in the priorit queque according to the frequecnies, least repeated on top.
4)Build the tree of huffman
5)creates a Treemap ( Tree data structure cool gedan :D )of character and the corresponding code.
6)encode the text!
#To do work:
1) decode
3) work on folders
 */
package huffman;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineListener;
import java.io.*;
import java.util.*;

/**
 *
 * @author yomnabarakat
 */
public class Huffman implements Serializable{

    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static TreeMap<Character, String> codes = new TreeMap<>();
    static int[] frequencies = new int[128];
      ArrayList<String> encodedList = new ArrayList<String>();
     static ArrayList<String> LineList = new ArrayList<String>();
    static  String encoded = "";
    static String decoded = "";
    static StringBuilder encoded_builder = new StringBuilder();
    static StringBuilder decoded_builder = new StringBuilder();
    static byte[] encoded_bytes;
    static Node n = new Node(1234,"ppp");
    
//    public static void main(String[] args) {
//
//    }

    static void init_freq_array() {
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = 0;
        }
    }

    //the whole file is put into one String and then iterate over this string and count the number each letter was repeated 
    //and store it in the frequencies array
    static String parseFile(String fileName) throws IOException {

        BufferedReader input = null;
        // Store the contents of the file in a string
         StringBuilder contents = new StringBuilder();
         //System.out.println(fileName.substring(fileName.length()-3,fileName.length() )+"      ppppppppppppppp");
        if(fileName.substring(fileName.length()-3,fileName.length() ).equals("txt")) {
       
        try {
            FileReader fr = new FileReader(fileName);
            input = new BufferedReader(fr);

            String line = "";
            while ((line = input.readLine()) != null) {
             ///   LineList.add(line);
               contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        }
        
        else{
            try {
                InputStream is = new FileInputStream(fileName);
                byte buffer;
                while((buffer=(byte) is.read())!=-1)
                {
                    
                    contents.append((char)buffer);
                    
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String s = contents.toString();
        

        int letter;
        init_freq_array();

        for (int position = 0; position < s.length(); position++) {

            letter = s.charAt(position);
            frequencies[letter] = frequencies[letter] + 1;

        }
       return s;
    } 
    
    static void applyHuffman(String text) {

        encoded = "";
        calc_frequencies_percnt(nodes, text);
        buildTree(nodes);
        createCode(nodes.peek(), "");
        printCodes();
        System.out.println("Encoded Text!");
        encodeText(text);
        System.out.println("Decoded Text!");
        decodeText();
    }

    static void writeEncodedFile(String encoded) {

       byte temp_byte;
        String temp_string;
        int size=(int)Math.ceil(encoded.length()/8);
        
        try {

            
            OutputStream outputstream = new FileOutputStream("encoded.bin");
             ObjectOutputStream objectoutputstream=new ObjectOutputStream(outputstream);
            byte x = (byte) (size/Math.pow(256,3));
            
             x = (byte) (size/Math.pow(256,2)); 
             x = (byte) (size/Math.pow(256,1));
              x = (byte) size;
            
        outputstream.write(size/(256*3));
              outputstream.write(size/(256*2));
              outputstream.write(size/(256));
            outputstream.write(size);
            
           
            
    
            for (int i = 0; i < encoded.length(); i += 8) {
                if (i + 8 < encoded.length()) {
                    
                    temp_string = encoded.substring(i, i + 7);
                 
                
                } else {
                    temp_string = encoded.substring(i);
                 //   while(temp_string.length()<8)
                   //     temp_string = "0" + temp_string;
                    
                    
                }
                temp_byte = Byte.parseByte(temp_string,2);
                outputstream.write(temp_byte);
                 
               
         }
            
            Iterator iterator = nodes.iterator();
                 //hena el mafrood beygeeb awel 7aga fel queue ye3melaha serialization we ba3dein delete we ye7otaha fel file
                 //el moshkela eno lama yeegy yo7otaha beysheel elly ablahaa
          while(iterator.hasNext())
          {
          objectoutputstream.writeObject(iterator.next());
          } 
               objectoutputstream.close();
            outputstream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println(ex);
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void encodeText(String text) {
        encoded = "";
        for (int i = 0; i < text.length(); i++) {
            encoded_builder.append(codes.get(text.charAt(i)));
            // encoded += " ";
        }
        encoded = encoded_builder.toString();
        System.out.println("Encoded Text: " + encoded);
    }

    
    static void readEncodedFile()
    {
        InputStream is = new InputStream("encoded.txt");
     
        int byte_counter  = 0 ;
        
                byte buffer;
                int encoded_size = 0  ;
                
                while((buffer=(byte) is.read())!=-1)
                {
                    
                    //contents.append((char)buffer);
                    if(byte_counter<4) 
                        encoded_size += (int) Math.pow( buffer, 256*(3-byte_counter) );
                    
                    
                    
                }
        
        
        
        
    }
    
    
    private static void decodeText() {
decoded="";
        //left is add zero right is add 1.
        Node n = nodes.peek();
        String temp = "";
        for (int i = 0; i < encoded.length(); i++) {
            if (n.isLeaf()) {
                decoded_builder.append(n.character);

                n = nodes.peek();

            }

            if (Character.toString(encoded.charAt(i)).equals("0")) {

                n = n.left;
            } else if (Character.toString(encoded.charAt(i)).equals("1")) {

                n = n.right;
            }

        }
        decoded = decoded_builder.toString();
        System.out.println("Decoded Text: " + decoded);
        System.out.println("Decoded Text: ");
    }

    private static void buildTree(PriorityQueue<Node> vector) {
        while (vector.size() > 1) {
            vector.add(new Node(vector.poll(), vector.poll()));
        }
    }
    //print the code for each letter in the text.

    private static void printCodes() {
        System.out.println("Codes for each letter: ");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }

    //get the frequency of each letter in the text inserted and add it in the priority queue.
    static void calc_frequencies_percnt(PriorityQueue<Node> vector, String paragraph) {

        for (int i = 0; i < frequencies.length; i++) {

            vector.add(new Node(frequencies[i] / ((paragraph.length() - 1) * 1.0), ((char) (i)) + ""));

            System.out.println("'" + ((char) (i)) + "' : " + frequencies[i] / ((paragraph.length() - 1) * 1.0));

        }

    }

    //create code for each node, left is add zero right is add 1.
    private static void createCode(Node node, String s) {
        if (node != null) {
            if (node.right != null) {
                createCode(node.right, s + "1");
            }

            if (node.left != null) {
                createCode(node.left, s + "0");
            }

            if (node.left == null && node.right == null) {
                codes.put(node.character.charAt(0), s);
            }
        }
    }
}