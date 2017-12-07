/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    //static String text = "";
    static String encoded = "";

//    public static void main(String[] args) {
//
//    }
    static void init_freq_array() {
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = 0;
        }
    }

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

        // Scan each symbol to fill S and compute total_letters
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

     static void handleNewText(String text) {
        nodes.clear();
        codes.clear();
        encoded = "";
        calc_frequencies_percnt(nodes, true, text);
        buildTree(nodes);
        generateCodes(nodes.peek(), "");
        printCodes();
        System.out.println("-- Encoding in process --");
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

    private static void printCodes() {
        System.out.println("--- Printing Codes ---");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }

    static void calc_frequencies_percnt(PriorityQueue<Node> vector, boolean printIntervals, String paragraph) {

        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                vector.add(new Node(frequencies[i] / ((paragraph.length()-1) * 1.0), ((char) (i+96)) + ""));
                if (printIntervals) {
                    System.out.println("'" + ((char) (i + 96)) + "' : " + frequencies[i] / ((paragraph.length() - 1) * 1.0));
                }
            }
        }
    }

    private static void generateCodes(Node node, String s) {
        if (node != null) {
            if (node.right != null) {
                generateCodes(node.right, s + "1");
            }

            if (node.left != null) {
                generateCodes(node.left, s + "0");
            }

            if (node.left == null && node.right == null) {
                codes.put(node.character.charAt(0), s);
            }
        }
    }
}
