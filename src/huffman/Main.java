package huffman;

import static huffman.Tests.testFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author yomnabarakat
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        
        
   
       
       
       
          System.out.println("1) compress file");
        System.out.println("2) decompress file");
        System.out.println("3) compress folder");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
       String filename ;
        if(n==1)
        {
            System.out.println("Enter file name to be compressed.");
         
             
             filename = scanner.next();
              ReadFile.Pass1(filename);
            HuffmanUtils.calc_frequencies_percnt(HuffmanUtils.nodes, ReadFile.totalsize);
        HuffmanUtils.buildTree(HuffmanUtils.nodes);
        HuffmanUtils.createCode(HuffmanUtils.nodes.peek(), "");
        HuffmanUtils.printCodes();
        HuffmanUtils.encodeFile(filename);
        WriteBinaryUtils.writeFile("new.bin");
        
             
               
        }
        
        else if(n==2)
        {
            System.out.println("Enter file name to be decompressed.");
            filename= scanner.next();
                HuffmanUtils.readEncodedFile(filename);
        }
        
       else if(n==3 )
        {}
       
       
       
    }
}
