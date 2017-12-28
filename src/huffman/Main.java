package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yomnabarakat
 */
public class Main {

    public static boolean flag;
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

    public static void encodeFolder(String folder) throws IOException {
        File file = new File(folder);
        String[] fileList = file.list();
        int i = 0;
        for (String name : fileList) {
            if (i > 0) {
                flag = true;
            }
            ReadFile.Pass1(folder + "/" + name);
            i++;
        }
        i = 0;
        HuffmanUtils.calc_frequencies_percnt(HuffmanUtils.nodes, ReadFile.totalsize);
        HuffmanUtils.buildTree(HuffmanUtils.nodes);
        HuffmanUtils.createCode(HuffmanUtils.nodes.peek(), "");
        HuffmanUtils.printCodes();
flag = false;
        for (String name : fileList) {
            if (i > 0) {
                flag = true;
            }
HuffmanUtils.encodeFile(folder+"/"+name);
            WriteBinaryUtils.writeFolder(folder + ".bin");

            i++;
        }

        WriteBinaryUtils.writeFolderFreq(folder + ".bin");
        
        RandomAccessFile rw = new RandomAccessFile(folder + ".bin", "rw");
        rw.seek(0);
         byte x = (byte) (HuffmanUtils.total_encoded / Math.pow(2, 24));
            rw.write(x);
            x = (byte) (HuffmanUtils.total_encoded / Math.pow(2, 16));
            rw.write(x);
            x = (byte) (HuffmanUtils.total_encoded / Math.pow(2, 8));
            rw.write(x);
            x = (byte) HuffmanUtils.total_encoded;
            rw.write(x);
        
        rw.close();
    }

    public static void encode(String filename) throws IOException {
        ReadFile.Pass1(filename);
        HuffmanUtils.calc_frequencies_percnt(HuffmanUtils.nodes, ReadFile.totalsize);
        HuffmanUtils.buildTree(HuffmanUtils.nodes);
        HuffmanUtils.createCode(HuffmanUtils.nodes.peek(), "");
        HuffmanUtils.printCodes();
        try {
            HuffmanUtils.encodeFile(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        WriteBinaryUtils.writeFile("new.bin");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        flag = false;
        System.out.println("1) compress file");
        System.out.println("2) decompress file");
        System.out.println("3) compress folder");
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        String filename;
        if (n == 1) {
            System.out.println("Enter file name to be compressed.");

            filename = scanner.next();

            encode(filename);

        } else if (n == 2) {
            System.out.println("Enter file name to be decompressed.");
            filename = scanner.next();
            HuffmanUtils.readEncodedFile(filename);
            System.err.println(" ");
            System.out.println("Enter file name to be saved in.");
            filename = scanner.next();
            HuffmanUtils.writeDecoded(filename);
        } else if (n == 3) {
        
            System.out.println("Enter folder name to be compressed.");
            filename = scanner.next();
            encodeFolder(filename);

        }
        else if (n == 4) {
            // flag = true;
            System.out.println("Enter folder name to be decompressed.");
            filename = scanner.next();
             HuffmanUtils.readEncodedFile(filename);
               System.out.println("Enter containing folder name .");
            filename = scanner.next();
             HuffmanUtils.writeDecoded(filename, 1);
           

        }
        else System.out.println("error");
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

