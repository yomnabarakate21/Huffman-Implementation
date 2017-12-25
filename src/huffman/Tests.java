/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author yomnabarakat
 */
public class Tests implements java.io.Serializable {

    static String testFile(String filename) throws IOException {

        String text = Huffman.parseFile(filename);
        for (int i = 0; i < Huffman.frequencies.length; i++) {

            System.out.println("This is the frequecny of  " + ((char) (i)) + "  is   " + Huffman.frequencies[i]);
        }
        return text;
    }

    static void calc_percentage_test(String text) {

        Huffman.calc_frequencies_percnt(Huffman.nodes, text);
    }

    public void convertBinary(int num) {
        int binary[] = new int[40];
        int index = 0;
        while (num > 0) {
            binary[index++] = num % 2;
            num = num / 2;
        }
        for (int i = index - 1; i >= 0; i--) {
            System.out.print(binary[i]);
        }
    }

//    public static void main(String[] args) throws IOException {
//
//        System.out.println("1) compress file");
//        System.out.println("2) decompress file");
//        System.out.println("3) compress folder");
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//       String filename ;
//        if(n==1)
//        {
//             System.out.println("Enter file name to be compressed.");
//             filename = scanner.next();
//             Huffman.applyHuffman(testFile(filename));
//             Huffman.writeEncodedFile(Huffman.encoded);
//             
//               
//        }
//        
//        else if(n==2)
//        {
//            System.out.println("Enter file name to be decompressed.");
//            filename= scanner.next();
//            Huffman.readEncodedFile(filename);
//        }
//        
//        else if(n==3 )
//        {}


//        String temp_s = "";
//        String s = Integer.toString(123);
//        if (s.length() < 4) {
//            for (int i = 0; i < s.length(); i++) {
//                if (s.charAt(i) == '2') {
//                    temp_s = "00000010";
//                } else if (s.charAt(i) == '3') {
//                    temp_s = "00000011";
//                } else if (s.charAt(i) == '4') {
//                    temp_s = "00000100";
//                } else if (s.charAt(i) == '5') {
//                    temp_s = "00000101";
//                } else if (s.charAt(i) == '6') {
//                    temp_s = "00000110";
//                } else if (s.charAt(i) == '7') {
//                    temp_s = "00000111";
//                } else if (s.charAt(i) == '8') {
//                    temp_s = "00001000";
//                } else if (s.charAt(i) == '9') {
//                    temp_s = "00001001";
//                } else if (s.charAt(i) == '1') {
//                    temp_s = "00000001";
//                }
//                  int temp_int = Integer.parseUnsignedInt(temp_s, 2);
//           byte temp_byte = (byte) temp_int;
//           System.out.println(temp_byte);
//            }
//         
        }

//        System.out.println(file);
//        System.out.println("the length of file is   " + file.length());
//        calc_percentage_test(file);
//        System.out.println("the length of file is ");
//         the new line is a char of ascii 10
//        System.out.println(((char) 10));
//        System.out.println("hiiiiiiiiii");
//        
//        Huffman.applyHuffman(file);
//Huffman.writeEncodedFile(Huffman.encoded);
// 
//        String encoded = "111100001111000011";
//        String temp_string = "";
//        int temp_int;
//        byte temp_byte;
//        for (int i = 0; i < encoded.length(); i += 8) {
//            if (i + 8 < encoded.length()) {
//
//                temp_string = encoded.substring(i, i + 8);
//                System.out.println("STRING IS :"+temp_string);
//
//            } else {
//                temp_string = encoded.substring(i);
//                 while(temp_string.length()<8)
//                      temp_string = "0" + temp_string;
//                  System.out.println("STRING IS IN ELSE :"+temp_string);
//            }
//
//            temp_int = Integer.parseUnsignedInt(temp_string, 2);
//            temp_byte = (byte) temp_int;
//            System.out.println(temp_byte);
  //  }

