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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HuffmanUtils {

    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static String[] codes = new String[128];
    static int encodedSize = 0;
    static public ArrayList<String> encoded_lines = new ArrayList<String>();

    // else if (count >= 16) {
//            if (count % 16 == 0) {
//                 HuffmanUtils.encoded_lines.add(encodedLine);
//            } else {
//                newEncodedLine = encodedLine.substring(0, 16);
//                extra = encodedLine.substring(16, encodedLine.length());
//                 HuffmanUtils.encoded_lines.add(newEncodedLine);
//                 return extra;
//            }
//                }
//        else if (count >= 16) {
//            if (count % 16 == 0) {
//                 HuffmanUtils.encoded_lines.add(encodedLine);
//            } else {
//                newEncodedLine = encodedLine.substring(0, 16);
//                extra = encodedLine.substring(16, encodedLine.length());
//                 HuffmanUtils.encoded_lines.add(newEncodedLine);
//                 return extra;
//            }
//        } else if (count >= 8) {
//            if (count % 8 == 0) {
//                 HuffmanUtils.encoded_lines.add(encodedLine);
//            } else {
//                newEncodedLine = encodedLine.substring(0, 8);
//                extra = encodedLine.substring(8, encodedLine.length());
//                  HuffmanUtils.encoded_lines.add(newEncodedLine);
//                return extra;
//            }
//        }
    //adjust size 
    public static String adjustSize(String pre, String encodedLineGiven) {
        int count = encodedLineGiven.length() + pre.length();
        String encodedLine = pre + encodedLineGiven;
        String newEncodedLine = "";
        String extra = "";                //ALELTYYYYYYYYYYY EL ARKAAAAAAAAM !!!!!
        if (count >= 8) {
            if (count == 8) {
                HuffmanUtils.encoded_lines.add(encodedLine);
                extra = "";
                return extra;
            } else {
                newEncodedLine = encodedLine.substring(0, 8);
                extra = encodedLine.substring(8, encodedLine.length());
                HuffmanUtils.encoded_lines.add(newEncodedLine);
                return extra;
            }
        }

        extra = encodedLine;
        return extra;

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

                    while (temp.length() < 8 && (encoded_counter < encodedSize - 1 || encoded_counter < 128)) {
                        temp = "0" + temp;
                    }
                    byte_counter++;

                    sb.append(temp);

                    if (sb.length() > 127) {

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
                } else {
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
        System.out.println("ORIGINAL SAIZE: " + ReadFile.totalsize);
        System.out.println("ENCODED SAIZE: " + encodedSize);

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
        String previous = "";
        String crunchifyLine;

        try (BufferedReader crunchifyBuffer = new BufferedReader(new FileReader(crunchifyFile))) {
            crunchifyLog("========== File Content ==========");

            // read each line one by one
            while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {

                crunchifyTotalLines++;
                //if last line don't add a new line character.
                if (ReadFile.totalLine != crunchifyTotalLines) {
                    crunchifyLine = crunchifyLine + (char) 10;
                }
                String code = encodeText(crunchifyLine);
                previous = HuffmanUtils.adjustSize(previous, code);

            }

            if (previous.length() <= 8) {

                HuffmanUtils.encoded_lines.add(previous);
            } else {
                boolean flag = true;
                while (flag) {
                    if (previous.length() <= 8) {
                        flag = false;
                        HuffmanUtils.encoded_lines.add(previous);
                    } else {
                        String code = previous.substring(0, 8);
                        HuffmanUtils.encoded_lines.add(code);
                        if (previous.length() > 8) {
                            previous = previous.substring(8, previous.length());
                        } else {
                            flag = false;
                        }
                    }
                    
                }
            }
        }catch (IOException e) {
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

    static void applyHuffman() {

        // init_codes_array();
        calc_frequencies_percnt(nodes, ReadFile.totalsize);
        buildTree(nodes);
        createCode(nodes.peek(), "");

        printCodes();

        System.out.println("Decoded Text!");
        // decodeText();

    }

}