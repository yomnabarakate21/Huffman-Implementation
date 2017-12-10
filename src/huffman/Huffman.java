/*The code works as follows:
1)Create the node class.
2)Reads the text from a file and stores it in a string, count the number of repetition of each character and stores it in frequency array.
3)each character has a node and is inserted in the priorit queque according to the frequecnies, least repeated on top.
4)Build the tree of huffman
5)creates a Treemap ( Tree data structure cool gedan :D )of character and the corresponding code.
6)encode the text!

#To do work:
1) decode
2) this code handles only alphabetics, we need to make it handle numbers and all other symbols as well.
3) work on folders
*/
package huffman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author yomnabarakat
 */
public class Huffman {

    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static TreeMap<Character, String> codes = new TreeMap<>();
    static int[] frequencies = new int[128];
    static String encoded = "";

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
    static String parseFile(String fileName) {
        BufferedReader input = null;
        // Store the contents of the file in a string
        StringBuilder contents = new StringBuilder();
        try {
            FileReader fr = new FileReader(fileName);
            input = new BufferedReader(fr);

            String line = "";
            while ((line = input.readLine()) != null) {
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

        String s = contents.toString();

        int letter;
        init_freq_array();

        for (int position = 0; position < s.length(); position++) {
            // if the letter is in upper case convert it to lower case
            if (s.charAt(position) >= 65 && s.charAt(position) <= 90) {

                letter = s.charAt(position) + 32;
                frequencies[letter - 96] = frequencies[letter - 96] + 1;
            } // do not convert if already in lower case
            else if (s.charAt(position) >= 97 && s.charAt(position) <= 122) {

                letter = s.charAt(position);
                frequencies[letter - 96] = frequencies[letter - 96] + 1;
            }
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
    }

    private static void encodeText(String text) {
        encoded = "";
        for (int i = 0; i < text.length(); i++) {
            encoded += codes.get(text.charAt(i));
        }
        System.out.println("Encoded Text: " + encoded);
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
            if (frequencies[i] > 0) {
                vector.add(new Node(frequencies[i] / ((paragraph.length() - 1) * 1.0), ((char) (i + 96)) + ""));

                System.out.println("'" + ((char) (i + 96)) + "' : " + frequencies[i] / ((paragraph.length() - 1) * 1.0));

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
                codes.put(node.character.charAt(0), s);
            }
        }
    }
}
