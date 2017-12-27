package huffman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author yomnabarakat
 */
public class Main {

    

//    public static String adjustSize(String pre, String encodedLineGiven) {
//        int count = encodedLineGiven.length() + pre.length();
//        System.out.println("THis is the zft el count "+count );
//        String encodedLine = pre + encodedLineGiven;
//        String newEncodedLine = "";
//        String extra = "";                
//        if (count >= 8) {
//            if (count % 8 == 0) {
//                new_encoded_lines.add(encodedLine);
//                return extra;
//            } else {
//                newEncodedLine = encodedLine.substring(0, 8);
//                extra = encodedLine.substring(8, encodedLine.length());
//                new_encoded_lines.add(newEncodedLine);
//                return extra;
//            }
//        }
//
//        extra = encodedLine;
//        return extra;
//
//    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.println("1) compress file");
        System.out.println("2) decompress file");
        System.out.println("3) compress folder");
        Scanner scanner = new Scanner(System.in);
        
        int n = scanner.nextInt();
        String filename;
        if (n == 1) {
            System.out.println("Enter file name to be compressed.");

            filename = scanner.next();
            ReadFile.Pass1(filename);
            HuffmanUtils.calc_frequencies_percnt(HuffmanUtils.nodes, ReadFile.totalsize);
            HuffmanUtils.buildTree(HuffmanUtils.nodes);
            HuffmanUtils.createCode(HuffmanUtils.nodes.peek(), "");
            HuffmanUtils.printCodes();
            HuffmanUtils.encodeFile(filename);
                     for (int i = 0; i < HuffmanUtils.encoded_lines.size(); i++) {
            System.out.println(HuffmanUtils.encoded_lines.get(i));
         }
            WriteBinaryUtils.writeFile("new.bin");

        } else if (n == 2) {
            System.out.println("Enter file name to be decompressed.");
            filename = scanner.next();
            HuffmanUtils.readEncodedFile(filename);
            System.out.println("Enter file name to be saved in.");
            filename = scanner.next();
            HuffmanUtils.writeDecoded(filename);
        } else if (n == 3) {
        }
    }
}
//        ArrayList<String> encoded_lines = new ArrayList<String>();
//        encoded_lines.add("11001100");
//        encoded_lines.add("1100110011");
//        encoded_lines.add("11");
//        encoded_lines.add("00");
//        String previous="";
//        for (int i = 0; i < encoded_lines.size(); i++) {
//            String s = encoded_lines.get(i);
//            previous = adjustSize(previous,s);             
//            System.out.println("xxxx                              "+ previous);
//        }
//        new_encoded_lines.add(previous);
//         for (int i = 0; i < new_encoded_lines.size(); i++) {
//            System.out.println(new_encoded_lines.get(i));
//         }
//    }

