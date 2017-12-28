/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.StringBinding;

public class HuffmanUtils {

    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static String[] codes = new String[128];
    static int encodedSize = 0;
    static public ArrayList<String> encoded_lines = new ArrayList<String>();
    static ArrayList<String> Y = new ArrayList<String>();//new array list for the characters encoded

    public static String adjustSize(String pre, String encodedLineGiven) {
        int count = encodedLineGiven.length() + pre.length();
        String encodedLine = pre + encodedLineGiven;
        String newEncodedLine = "";
        String extra = "";                //ALELTYYYYYYYYYYY EL ARKAAAAAAAAM !!!!!
        if (count >= 16384) {
            if (count == 16384) {
                HuffmanUtils.encoded_lines.add(encodedLine);
                extra = "";
                return extra;
            } else {
                newEncodedLine = encodedLine.substring(0, 16384);
                extra = encodedLine.substring(16384, encodedLine.length());
                HuffmanUtils.encoded_lines.add(newEncodedLine);
                return extra;
            }
        }

        extra = encodedLine;
        return extra;

    }

    public static int get_encoded_size(ArrayList encoded_lines) {
        int count = 0;
        for (int i = 0; i < encoded_lines.size(); i++) {
            count += encoded_lines.get(i).toString().length();
        }
        return count;
    }

    //encode text function
    public static void readEncodedFile(String inputfile) {  //leh>
        InputStream is = null;

        Byte[] temp_key_array = null;
        String[] temp_value_array = null;
        char temp_key = ' ';
        try {
            is = new FileInputStream(inputfile);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }

        int byte_counter = 0;
        boolean keyorvalue = true; //true for key
        int encoded_counter = 0;
        StringBuilder sb = new StringBuilder();
        int value = 0, key = 0;
        byte buffer;

        encodedSize = 0;
        init_freq();
        int digit = 0;

        try {
            while (true) {
                buffer = (byte) is.read();
                if (byte_counter < 4) {

                    encodedSize += Byte.toUnsignedInt(buffer) * (int) Math.pow(2, ((3 - byte_counter) * 8));

                    byte_counter++;

                } else if (byte_counter < 8) {

                    ReadFile.totalsize += Byte.toUnsignedInt(buffer) * (int) Math.pow(2, ((3 - (byte_counter - 4)) * 8));

                    byte_counter++;

                    //read encoded text
                } else if (encoded_counter < encodedSize) {

                    String temp = Integer.toBinaryString(Byte.toUnsignedInt(buffer));

                    while (temp.length() < 8 && (encoded_counter < encodedSize - 1 || encoded_counter < 16384)) {
                        temp = "0" + temp;
                    }
                    byte_counter++;

                    sb.append(temp);

                    if (sb.length() > 16383) {

                        encoded_lines.add(sb.toString());
                        sb = new StringBuilder();
                    }
                    encoded_counter++;

                } else if (buffer != 28) {
                    if (sb.length() > 0) {
                        encoded_lines.add(sb.toString());
                        sb = new StringBuilder();
                    }
                    if (keyorvalue) {
                        if (buffer == -1) {
                            break;
                        }
                        key = (byte) Byte.toUnsignedInt(buffer);
                        keyorvalue = !keyorvalue;
                        value = 0;
                        digit = 0;

                    } else {
                        value += ((byte) Byte.toUnsignedInt(buffer)) * Math.pow(10, digit);
                        digit++;

                    }
                } else if (buffer == 28) {
                    keyorvalue = !keyorvalue;
                    ReadFile.frequencies[key] = value;

                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }

        applyHuffman();

        // printFreq();
        try {
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }
        //     System.out.println("ORIGINAL SAIZE: " + ReadFile.totalsize);
        //   System.out.println("ENCODED SAIZE: " + encodedSize);

    }

    static String encodeText(String text) {

        String encoded = "";
        StringBuilder encoded_builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded_builder.append(codes[(int) text.charAt(i)]);

        }
        encoded = encoded_builder.toString();
        return encoded;
    }

    public static void encodeFile(String crunchifyFile) throws FileNotFoundException {

        int crunchifyTotalLines = 0;
        int crunchifyTotalCharacters = 0;
        StringBuilder cs = new StringBuilder();
        String previous = "";
        String crunchifyLine;
encoded_lines = new ArrayList<String>();
        try (BufferedReader crunchifyBuffer = new BufferedReader(new FileReader(crunchifyFile))) {
            //   crunchifyLog("========== File Content ==========");

            // read each line one by one
            while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
                cs = new StringBuilder(crunchifyLine);
                crunchifyTotalLines++;
                //if last line don't add a new line character.
                if (ReadFile.totalLine != crunchifyTotalLines) {

                    cs.append((char) 10);

                }

                String code = encodeText(cs.toString());
                previous = HuffmanUtils.adjustSize(previous, code);

            }

            if (previous.length() <= 16384) {

                HuffmanUtils.encoded_lines.add(previous);
            } else {
                boolean flag = true;
                while (flag) {
                    if (previous.length() <= 16384) {
                        flag = false;
                        HuffmanUtils.encoded_lines.add(previous);
                    } else {
                        String code = previous.substring(0, 16384);
                        HuffmanUtils.encoded_lines.add(code);
                        if (previous.length() > 16384) {
                            previous = previous.substring(16384, previous.length());
                        } else {
                            flag = false;
                        }
                    }

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
    static void init_freq() {
        for (int i = 0; i < ReadFile.frequencies.length; i++) {
            ReadFile.frequencies[i] = 0;
        }

    }

    //print the freq of each character.
    static void print_freq() {
        for (int i = 0; i < ReadFile.frequencies.length; i++) {
            System.out.println((char) i + "     " + ReadFile.frequencies[i]);
        }
    }

    //get the frequency of each letter in the text inserted and add it in the priority queue.
    static void calc_frequencies_percnt(PriorityQueue<Node> vector, int size) {

        for (int i = 0; i < ReadFile.frequencies.length; i++) {
            if (ReadFile.frequencies[i] != 0) {
                vector.add(new Node(ReadFile.frequencies[i] / ((size) * 1.0), ((char) (i)) + ""));

                //   System.out.println("'" + ((char) (i)) + "' : " + ReadFile.frequencies[i] / ((size) * 1.0));
            }
        }

    }

    static void writeDecoded(String filename) throws IOException {
        FileWriter fw = null;
        try {

            fw = new FileWriter(filename);
        } catch (IOException ex) {
            Logger.getLogger(HuffmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String s : Y) {
            fw.write(s);
        }
        fw.close();
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

    static void applyHuffman() {

        // init_codes_array();
        calc_frequencies_percnt(nodes, ReadFile.totalsize);
        buildTree(nodes);
        createCode(nodes.peek(), "");

        //   printCodes();
        // System.out.println("Decoded Text!");
        decode(encoded_lines);

    }

    static void printDecoded() {
        for (String s : encoded_lines) {
            System.out.print(s);
        }
    }

    static ArrayList decode(ArrayList x) {

        String line = "";
        StringBuilder temp = new StringBuilder();
        int size_of_string = 1000;
        Node n = nodes.peek();
        //loop on the arraylist
        for (int i = 0; i < x.size(); i++) {
            line = x.get(i).toString();

            for (int j = 0; j < line.length(); j++) {

                if (Character.toString(line.charAt(j)).equals("0")) {

                    n = n.left;
                } else if (Character.toString(line.charAt(j)).equals("1")) {

                    n = n.right;
                }
                if (n.isLeaf()) {

                    temp.append(n.character);

                    if (temp.length() >= size_of_string) {

                        Y.add(temp.toString());
                        temp = new StringBuilder();
                    }

                    n = nodes.peek();
                }
            }

        }
        Y.add(temp.toString());
        //     System.out.println("Size OF ARAY DECODED LIST IS: "+Y.size());
        //   printList(Y);

        return (Y);
    }

    static void printList(ArrayList x) {

        for (int i = 0; i < x.size(); i++) {
            // System.out.println( "hello");
            System.out.println(x.get(i).toString());

        }

    }
}
