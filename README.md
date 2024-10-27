# PA4_Pham
Huffman Project


13.8 PA4: Huffman Coding Project

Huffman Code Implementation

In computer science and information technology, Huffman coding is a method of lossless data compression that is
independent of the data type. The idea is to assign variable-length codes to input characters, lengths of the assigned
codes are based on the frequencies of corresponding characters. The most frequent character gets the smallest code and
the least frequent character gets the largest code.

The variable-length codes assigned to input characters are Prefix Codes, means the codes (bit sequences) are assigned
in such a way that the code assigned to one character is not the prefix of code assigned to any other character.
This is how Huffman Coding makes sure that there is no ambiguity when decoding the generated bitstream.

There are two major parts of Huffman Coding:

Build a Huffman Tree according to character frequency.
Traverse the Huffman Tree to assign Prefix Code for each character.
Step 1: build a Huffman Tree according to character frequency

You may use either sorted list or a priority queue (better performance) where the node with smallest frequency
is given highest priority:

Add all nodes to a priority queue (or a sorted list)
While there is more than one node in the queue (or sorted list): a). Remove the two nodes of
highest priority (smallest frequency) from the queue (or sorted list), b). then create a new internal node with these
two nodes as children and with frequency equal to the sum of the two nodes' frequency. c).
add the new node to the queue (or sorted list).
The remaining node is the root node and the Huffman tree is complete.
For example, given the following character frequency:
```
character              Frequency
    a                      15
    b                      5
    c                      50
    d                      2
```
```
A priority queue is constructed as follows:
           ______[a:5]_____
          /                \
      [b:9]                 [c:12]
     /    \                /
[e:16]     [d:13]     [f:45]
```
In the 1st iteration, remove two minimum frequency node (highest priority) from queue (‘a:5’ and ‘b:9’)
and form an internal node with frequency 14, then add the new node to the queue:

```
Queue after 1st iteration
            [c:12]_____
           /           \
      [d:13]            [f:45]
     /     \
[e:16]      [ :14]
```
```
The new internal node
     [ :14]
    /     \
[a:5]      [b:9]
```
In the 2nd iteration, remove [c:12] and [d:13] and create a new internal node and add to the queue:
```
Queue after 2nd  iteration
            [ :14]
           /     \
      [e:16]      [f:45]
     /
[ :25]
```
```
The new internal node
      [ :25]
     /     \
[c:12]      [d:13]
```
In the 3rd iteration, remove [ :14] and [e:16] and create a new internal node and add to the queue:
```
Queue after 3rd iteration
      [ :25]
     /     \
[f:45]      [ :30]
```
```
The new internal node
        [ :30]____
          /         \
     [ :14]           [e:16]
    /     \
[a:5]      [b:9]
```
In the 4th iteration, remove [ :25] and [:30] and create a new internal node and add to the queue:
```
Queue after 4th iteration
      [f:45]
     /
[ :55]
```
```
The new internal node
            ___________[ :55]_____
           /                      \
      [ :25]                       [ :30]____
     /     \                      /          \
[c:12]      [d:13]           [ :14]           [e:16]
                            /     \
                        [a:5]      [b:9]
```
In the 5th iteration, remove [ f:45] and [:55] and create a new internal node and add to the queue:
```
Queue after 5th iteration
[ :100]
```
```
The new internal node (Huffman Tree)

      _______________________[ :100]
     /                             \
[f:45]                   ___________[ :55]_____
                        /                      \
                   [ :25]                       [ :30]____
                  /     \                      /          \
             [c:12]      [d:13]           [ :14]           [e:16]
                                         /     \
                                     [a:5]      [b:9]
```
Step 2: traverse the Huffman Tree to assign Prefix Code for each character

Traverse the tree from root. While moving the left, write 0, while moving the right, write 1,
combining all bits when a leaf node is encounted.
```
      ____________0__________[ :100]
     /                             \1
[f:45]                   ____0______[ :55]_____
                        /                      \1
                   [ :25]                       [ :30]____
                 0/     \1                    0/          \1
             [c:12]      [d:13]           [ :14]           [e:16]
                                        0/     \1
                                     [a:5]      [b:9]
```
```
So the prefix codes are as follows:
character         prefix code
      f                   0
      c                  100
      d                  101
      a                  1100
      b                  1101
      e                  111
```
For this project, you are required to implement the encoding part of the Huffman code.
The decoding part will be optional and not for grade, but I strongly encourage you to try it yourself.

Create a program named HuffmanCoding.java and implement the following methods as specified.

public Hashtable getChararacterFrequency(String aStr), it counts the frequency of each character in aStr
and returns a Hashtable:use each character as key and frequency as its value.

public Hashtable getChararacterFrequency(File aFile), it counts the frequency of each character in the file and
returns a Hashtable:use each character as key and frequency as its value.

public HuffmanTreeNode buildHuffmanTree(Hashtable ht), it takes as a parameter the character frequency hashtable,
builds and returns HuffmanTree. For example, for the following character frequency:
```
character              Frequency
    a                       15
    b                       5
    c                       50
    d                       2
```
```
The Huffman tree is as follows:
                [ :72]__________
               /                \
          [ :22]____             [c:50]
         /          \
     [ :7]           [a:15]
    /    \
[d:2]     [b:5]
```
public Hashtable buildCodeTable(HuffmanTreeNode huffmanTree), it takes a Huffman tree as a parameter and returns
a hashtable: key: char, value: prefix code. In the above example, its corresponding prefix codes are as follows:
```
character             Frequency           prefix code
    a                   15                     01
    b                   5                      001
    c                   50                     1
    d                   2                      000
```
public String encoding(String aStr, Hashtable codeTable), it takes as parameters aStr to encode and codeTable
which is used to encode each character in aStr, then returns encoded binary String.
For example, with the above code table, “cab” will be encoded into “101001”

public String decode(String binaryStr, HuffmanTreeNode huffmanTree), it takes as parameters a
binary String and Huffman tree, and uses the Huffman tree to decode the binary string then returns the decoded String.
For example, use the Huffman tree in the above, calling decode(“101001”, huffmanTree) should return “cab”
