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

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;
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
public class Huffman implements Serializable {

    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    //static TreeMap<Character, String> codes = new TreeMap<>();
    static String[] codes = new String[128];
    static int[] frequencies = new int[128];
    ArrayList<String> encodedList = new ArrayList<String>();
    static ArrayList<String> LineList = new ArrayList<String>();
    static String encoded = "";
    static String decoded = "";
    static StringBuilder encoded_builder = new StringBuilder();
    static StringBuilder decoded_builder = new StringBuilder();
    static byte[] encoded_bytes;
    static int original_size;
    static Node n = new Node(1234, "ppp");

    static void init_freq_array() {
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = 0;
        }
        frequencies[10] = -1;
    }

    static void init_codes_array() {
        for (int i = 0; i < codes.length; i++) {
            codes[i] = "a";
        }
    }

    //the whole file is put into one String and then iterate over this string and count the number each letter was repeated 
    //and store it in the frequencies array
    static String parseFile(String fileName) throws IOException {

        BufferedReader input = null;
        // Store the contents of the file in a string
        StringBuilder contents = new StringBuilder();
        //System.out.println(fileName.substring(fileName.length()-3,fileName.length() )+"      ppppppppppppppp");
        if (fileName.substring(fileName.length() - 3, fileName.length()).equals("txt")) {

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
        } else {
            try {
                InputStream is = new FileInputStream(fileName);
                byte buffer;
                while ((buffer = (byte) is.read()) != -1) {

                    contents.append((char) buffer);

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
        init_codes_array();
        calc_frequencies_percnt(nodes, text);
        buildTree(nodes);
        createCode(nodes.peek(), "");

        printCodes();
        System.out.println("Encoded Text!");
        encodeText(text);
        System.out.println("Decoded Text!");
        decodeText();

    }

    static void applyHuffman() {

        init_codes_array();
        calc_frequencies_percnt(nodes, original_size);
        buildTree(nodes);
        createCode(nodes.peek(), "");

        printCodes();

        System.out.println("Decoded Text!");
        decodeText();

    }

    static void writeEncodedFile(String encoded) {

        byte temp_byte;
        int key = 0;
        String temp_string;
        int temp_int;
        int size = (int) Math.ceil(encoded.length()/8.0);

        //Start writing the encoded text in a binary file
        try {

            System.out.println("SIZE OF THE ENCODED  " + size);
            OutputStream out = new FileOutputStream("encoded.bin");

            byte x = (byte) (size / Math.pow(2, 24));
            
            out.write(x);
            x = (byte) (size / Math.pow(2, 16));
            out.write(x);
            x = (byte) (size / Math.pow(2, 8));
            out.write(x);
            x = (byte) size;
            out.write(x);

            System.out.println("SIZE OF THE ORIGINAL " + original_size);
            x = (byte) (original_size / Math.pow(2, 24));
            out.write(x);
            x = (byte) (original_size / Math.pow(2, 16));
            out.write(x);
            x = (byte) (original_size / Math.pow(2, 8));
            out.write(x);
            x = (byte) original_size;
            out.write(x);

            for (int i = 0; i < encoded.length(); i += 8) {
                if (i + 8 < encoded.length()) {

                    temp_string = encoded.substring(i, i + 8);

                } else {
                    temp_string = encoded.substring(i);
                    while (temp_string.length() < 8) {
                        temp_string = "0" + temp_string;
                    }

                }

                temp_int = Integer.parseInt(temp_string, 2);

                temp_byte = (byte) temp_int;
                System.out.println(temp_byte);
                out.write(temp_byte);

            }

            //hena beye3mel file seperator 3alashan yebda2 el array
            // out.write((char) 28);
            //Start writing the freq of each character in the freq array
            for (int j = 0; j < 128; j++) {
                if (frequencies[j] != 0) {
                    String temp_s = "";
                    String s = Integer.toString(frequencies[j]);
                    //write the letter
                    out.write((char) j);
                    if (s.length() < 4) {
                        for (int i = s.length()-1 ; i >=0 ; i--) {
                            if (s.charAt(i) == '2') {
                                temp_s = "00000010";
                            } else if (s.charAt(i) == '3') {
                                temp_s = "00000011";
                            } else if (s.charAt(i) == '4') {
                                temp_s = "00000100";
                            } else if (s.charAt(i) == '5') {
                                temp_s = "00000101";
                            } else if (s.charAt(i) == '6') {
                                temp_s = "00000110";
                            } else if (s.charAt(i) == '7') {
                                temp_s = "00000111";
                            } else if (s.charAt(i) == '8') {
                                temp_s = "00001000";
                            } else if (s.charAt(i) == '9') {
                                temp_s = "00001001";
                            } else if (s.charAt(i) == '1') {
                                temp_s = "00000001";
                            }
else if (s.charAt(i) == '0') {
                                temp_s = "00000000";
                            }
                            temp_int = Integer.parseUnsignedInt(temp_s, 2);
                            temp_byte = (byte) temp_int;
                            out.write(temp_byte);

                        }

                    }
                    out.write((char) 28);

                }

            }

            //close the stream.
            out.close();
        } //end of the try block
        catch (IOException ex) {
            ex.printStackTrace();
            System.err.println(ex);
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }// end of catch block
    }//end of function

    private static void encodeText(String text) {

        encoded = "";
        for (int i = 0; i < text.length() - 1; i++) {
            encoded_builder.append(codes[(int) text.charAt(i)]);
            // encoded += " ";
        }
        encoded = encoded_builder.toString();
        System.out.println("Encoded Text: " + encoded);
        original_size = text.length();
    }

    public static void readEncodedFile(String inputfile) {
        InputStream is = null;
        byte temp_key_byte = 0;
        //ArrayList<String> temp_value = new ArrayList<Byte>();
        StringBuilder temp_value_builder = new StringBuilder();
        Byte[] temp_key_array = null;
        String[] temp_value_array = null;
        char temp_key = ' ';
        try {
            is = new FileInputStream(inputfile);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean textorcode = true;
        int null_counter = 0;
        int byte_counter = 0;
        boolean keyorvalue = true; //true for key
        int encoded_counter = 0;
        StringBuilder sb = new StringBuilder();
        int value = 0, key = 0;
        byte buffer;
        int encoded_size = 0;

      
        init_freq_array();
        frequencies[10] = 0;
        int digit = 0;
       

        try {
            while (true) {
                buffer = (byte) is.read();
                if (byte_counter < 4) {

                    encoded_size += Byte.toUnsignedInt(buffer) * (int) Math.pow(2, ((3 - byte_counter) * 8));

                    byte_counter++;

                   // if (byte_counter == 4) {
                     //   encoded_size = (int) Math.ceil(encoded_siz);
                   // }
                } else if (byte_counter < 8) {
                    
                    original_size += Byte.toUnsignedInt(buffer) * (int) Math.pow(2, ((3 - (byte_counter - 4)) * 8));

                    byte_counter++;

                   // if (byte_counter == 8) {
                     //   original_size = (int) Math.ceil(original_size);
                   // }
                } else if (encoded_counter < encoded_size) {

                    String temp = Integer.toBinaryString(buffer & 0xFF);

                    while (temp.length() < 8 && byte_counter - 8 < encoded_size-1) {
                        temp = "0" + temp;
                    }
                    byte_counter++;

                    sb.append(temp);

                    encoded_counter++;

                } else {

                    //  textorcode = false;
                    if (buffer != 28) {
                        if (keyorvalue) {
                            if(buffer==-1) break;
                            key = (byte)Byte.toUnsignedInt(buffer);
                            keyorvalue = !keyorvalue;
                            value = 0;
                            digit = 0;

                        } else {
                            value += ((byte)Byte.toUnsignedInt(buffer)) * Math.pow(10, digit);
                            digit++;

                        }
                    } else {
                        keyorvalue = !keyorvalue;
                        frequencies[key] = value;
                        
                    }
                    /*  if((int)buffer!=28)
                        
                        {
                            
                            if(keyorvalue){
                            temp_key= (char)buffer;
                            keyorvalue=!keyorvalue;
                            null_counter++;
                            }
                            else{
                                temp_value_builder.append((char)buffer);
                                
                                null_counter++;
                            }
                                
                        }
                        
                        else
                        {
                               
                             if((!keyorvalue)&&null_counter>1)
                             {
                                 //  temp_value_array = temp_value.toArray(new Byte[temp_value.size()]);
                       
                           
                            codes.put(temp_key , temp_value_builder.toString());
                            temp_value_builder=new StringBuilder();
                            keyorvalue=!keyorvalue;
                           
                             }
                            
                        }*/

                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }

        encoded = sb.toString();
        applyHuffman();
printFreq();
        try {
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("ORIGINAL SAIZE: " + original_size);
        System.out.println("ENCODED SAIZE: " + encoded_size);

    }

    private static void decodeText() {
        decoded = "";
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
        System.out.println(" ");

    }
private static void printFreq()
{
    for(int i = 0 ; i<128;i++)
    {
        System.out.print((char)i);
        System.out.print("   ");
        System.out.println(frequencies[i]);
    }
}
    private static void buildTree(PriorityQueue<Node> vector) {
        while (vector.size() > 1) {
            vector.add(new Node(vector.poll(), vector.poll()));
        }
    }
    //print the code for each letter in the text.

    private static void printCodes() {
        int i;
        System.out.println("Codes for each letter: ");
        // codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
        for (i = 0; i < 128; i++) {
            System.out.println("'" + (char) i + "' : " + codes[i]);
        }
    }

    //get the frequency of each letter in the text inserted and add it in the priority queue.
    static void calc_frequencies_percnt(PriorityQueue<Node> vector, String paragraph) {

        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                vector.add(new Node(frequencies[i] / ((paragraph.length() - 1) * 1.0), ((char) (i)) + ""));

                System.out.println("'" + ((char) (i)) + "' : " + frequencies[i] / ((paragraph.length() - 1) * 1.0));

            }
        }

    }

    static void calc_frequencies_percnt(PriorityQueue<Node> vector, int size) {


        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                vector.add(new Node(frequencies[i] / ((size - 1) * 1.0), ((char) (i)) + ""));

                System.out.println("'" + ((char) (i)) + "' : " + frequencies[i] / ((size - 1) * 1.0));

            }
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
                //codes.put(node.character.charAt(0), s);
                codes[(int) node.character.charAt(0)] = s;
            }

        }
    }
}
