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
public class Tests {

    static String testFile() {
        String filename = "input.txt";
        String text = Huffman.parseFile(filename);
        for (int i = 0; i < Huffman.frequencies.length; i++) {
            if(i<=32)
            System.out.println("This is the frequecny of  " + ((char) (i + 96)) + "  is   " + Huffman.frequencies[i]);
            else
              System.out.println("This is the frequecny of  " + ((char) (i)) + "  is   " + Huffman.frequencies[i]);  
        }
        return text;
    }

    static void calc_percentage_test(String text) {

        Huffman.calc_frequencies_percnt(Huffman.nodes, text);
    }

    public static void main(String[] args) {
        String file = testFile();
        System.out.println(file);
        System.out.println("the length of file is   " + file.length());
        calc_percentage_test(file);
        System.out.println("the length of file is ");
        // the new line is a char of ascii 10
        System.out.println(((char) 10));
        System.out.println("hiiiiiiiiii");
        
        Huffman.applyHuffman(file);

    }

}
