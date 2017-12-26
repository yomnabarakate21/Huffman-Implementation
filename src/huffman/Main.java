package huffman;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author yomnabarakat
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        ReadFile.Pass1("input2.txt");
        HuffmanUtils.calc_frequencies_percnt(HuffmanUtils.nodes, ReadFile.totalsize);
        HuffmanUtils.buildTree(HuffmanUtils.nodes);
        HuffmanUtils.createCode(HuffmanUtils.nodes.peek(), "");
        HuffmanUtils.printCodes();
        HuffmanUtils.encodeFile("input2.txt");
        WriteBinaryUtils.writeFile("new.bin");
       
    }
}
