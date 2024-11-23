import java.util.*;
import java.io.*;

public class HuffmanCoding {

   public static void main(String[] args) {
      // Create an instance of HuffmanCoding
      HuffmanCoding hc = new HuffmanCoding();

      // Example string to test the functionality
      String testString = "huffman coding example";

      // Step 1: Get character frequencies
      Hashtable<Character, Integer> charFreq = hc.getChararacterFrequency(testString);
      System.out.println("Character Frequencies: " + charFreq);

      // Step 2: Build Huffman Tree
      HuffmanTreeNode huffmanTree = hc.buildHuffmanTree(charFreq);
      System.out.println("Huffman Tree built successfully.");

      // Step 3: Build Code Table
      Hashtable<Character, String> codeTable = hc.buildCodeTable(huffmanTree);
      System.out.println("Code Table: " + codeTable);

      // Step 4: Encode the string
      String encodedString = hc.encoding(testString, codeTable);
      System.out.println("Encoded String: " + encodedString);

      // Step 5: Decode the string (optional)
      String decodedString = hc.decode(encodedString, huffmanTree);
      System.out.println("Decoded String: " + decodedString);

      // Optional: Test with a file
      try {
         File testFile = new File("test.txt");
         if (testFile.createNewFile()) {
            try (FileWriter writer = new FileWriter(testFile)) {
               writer.write(testString);
            }
            Hashtable<Character, Integer> fileCharFreq = hc.getChararacterFrequency(testFile);
            System.out.println("Character Frequencies from File: " + fileCharFreq);
         }
      } catch (IOException e) {
         System.err.println("Error while creating or writing to test.txt: " + e.getMessage());
      }
   }

   public Hashtable<Character, Integer> getChararacterFrequency(String aStr) {
      Hashtable<Character, Integer> charFreq = new Hashtable<>();
      for (char c : aStr.toCharArray()) {
         charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
      }
      return charFreq;
   }

   public Hashtable<Character, Integer> getChararacterFrequency(File aFile) {
      Hashtable<Character, Integer> charFreq = new Hashtable<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(aFile))) {
         String line;
         while ((line = reader.readLine()) != null) {
            for (char c : line.toCharArray()) {
               charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return charFreq;
   }

   public HuffmanTreeNode buildHuffmanTree(Hashtable<Character, Integer> ht) {
      PriorityQueue<HuffmanTreeNode> pq = new PriorityQueue<>();
      for (Map.Entry<Character, Integer> entry : ht.entrySet()) {
         pq.add(new HuffmanTreeNode(entry.getKey(), entry.getValue()));
      }

      while (pq.size() > 1) {
         HuffmanTreeNode left = pq.poll();
         HuffmanTreeNode right = pq.poll();
         HuffmanTreeNode parent = new HuffmanTreeNode(left.getFrequency() + right.getFrequency());
         parent.setLeft(left);
         parent.setRight(right);
         pq.add(parent);
      }

      return pq.poll();
   }

   public Hashtable<Character, String> buildCodeTable(HuffmanTreeNode huffmanTree) {
      Hashtable<Character, String> codeTable = new Hashtable<>();
      buildCodeTableRecursive(huffmanTree, "", codeTable);
      return codeTable;
   }

   private void buildCodeTableRecursive(HuffmanTreeNode node, String path, Hashtable<Character, String> codeTable) {
      if (node == null) return;

      if (node.getKey() != '\u0000') {
         codeTable.put(node.getKey(), path);
      }

      buildCodeTableRecursive(node.getLeft(), path + "0", codeTable);
      buildCodeTableRecursive(node.getRight(), path + "1", codeTable);
   }

   public String encoding(String aStr, Hashtable<Character, String> codeTable) {
      StringBuilder encodedStr = new StringBuilder();
      for (char c : aStr.toCharArray()) {
         encodedStr.append(codeTable.get(c));
      }
      return encodedStr.toString();
   }

   public String decode(String binaryStr, HuffmanTreeNode huffmanTree) {
      StringBuilder decodedStr = new StringBuilder();
      HuffmanTreeNode currentNode = huffmanTree;

      for (char bit : binaryStr.toCharArray()) {
         currentNode = (bit == '0') ? currentNode.getLeft() : currentNode.getRight();

         if (currentNode.getKey() != '\u0000') {
            decodedStr.append(currentNode.getKey());
            currentNode = huffmanTree;
         }
      }

      return decodedStr.toString();
   }
}