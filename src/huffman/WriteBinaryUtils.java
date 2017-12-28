/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WriteBinaryUtils {

    //write the size of each if the encoded and the original text in the header of the binary file.
    public static void writeFreq(String filename) throws FileNotFoundException {

        int temp_int;
        int temp_byte;

        try {
            OutputStream out = new FileOutputStream(filename, true);

            for (int j = 0; j < 128; j++) {
                if (ReadFile.frequencies[j] != 0) {
                    String temp_s = "";
                    String s = Integer.toString(ReadFile.frequencies[j]);
                    //write the letter
                    out.write((char) j);
                   
                        for (int i = s.length() - 1; i >= 0; i--) {
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
                            } else if (s.charAt(i) == '0') {
                                temp_s = "00000000";
                            }

                            temp_int = Integer.parseUnsignedInt(temp_s, 2);
                            temp_byte = (byte) temp_int;
                            out.write(temp_byte);

                        }

                    
                         out.write((char) 28);
                }
          

            }
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println(ex);
            Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void writeEncoded(String filename) throws FileNotFoundException, IOException {

        String temp_string = "";
        int temp_int;
        byte temp_byte;
        OutputStream out = new FileOutputStream(filename, true);

        for (int j = 0; j < HuffmanUtils.encoded_lines.size(); j++) {

            String encoded = HuffmanUtils.encoded_lines.get(j);
            for (int i = 0; i < encoded.length(); i += 8) {
                if (i + 8 < encoded.length()) {

                    temp_string = encoded.substring(i, i + 8);

                } else {
                    temp_string = encoded.substring(i);
                    while (temp_string.length() < 8 ) {
                        temp_string = "0" + temp_string;
                    }

                }

                temp_int = Integer.parseInt(temp_string, 2);

                temp_byte = (byte) temp_int;
             
                out.write(temp_byte);

            }

        }
        out.close();
    }

    static void writeHeader(String filename) {

        
        int size = (int) Math.ceil(HuffmanUtils.get_encoded_size(HuffmanUtils.encoded_lines)/8.0);
             // int size = (1024*(HuffmanUtils.encoded_lines.size()-1))+HuffmanUtils.encoded_lines.get(HuffmanUtils.encoded_lines.size()-1).length();
       
         //size =(int) Math.ceil(size/8);
            OutputStream out;
            
        try {

           // System.out.println("SIZE OF THE ENCODED  " + size);
             out= new FileOutputStream(filename,Main.flag);
            byte x = (byte) (size / Math.pow(2, 24));
            out.write(x);
            x = (byte) (size / Math.pow(2, 16));
            out.write(x);
            x = (byte) (size / Math.pow(2, 8));
            out.write(x);
            x = (byte) size;
            out.write(x);

          //  System.out.println("SIZE OF THE ORIGINAL " + ReadFile.totalsize);
          if(Main.flag==false){
            x = (byte) (ReadFile.totalsize / Math.pow(2, 24));
            out.write(x);
            x = (byte) (ReadFile.totalsize / Math.pow(2, 16));
            out.write(x);
            x = (byte) (ReadFile.totalsize / Math.pow(2, 8));
            out.write(x);
            x = (byte) ReadFile.totalsize;
            out.write(x);
            out.close();}
        } //end of the try block
        catch (IOException ex) {
            ex.printStackTrace();
            System.err.println(ex);

       Logger.getLogger(Huffman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeFile(String filename) throws IOException {
        writeHeader(filename);
        writeEncoded(filename);
        writeFreq(filename);
    }

    public static void writeFolder(String filename) throws IOException
    {
        writeHeader(filename);
        writeEncoded(filename);
    }
    
    public static void writeFolderFreq(String filename) throws IOException{
        writeFreq(filename);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
