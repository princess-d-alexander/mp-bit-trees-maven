package edu.grinnell.csc207.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Trees intended to be used in storing mappings between fixed-length
 * sequences of bits and corresponding values.
 *
 * @author Princess Alexander
 */
public class BitTree {

  /**
  * The root node of the tree.
  */
  private Node root;

  /**
  * The length of bit sequences in this tree.
  */
  private int bitLength;

  /**
   * Constructor to initialize the tree with a given bit length.
   *
   * @param n the length of the bit sequences in the tree
   */
  public BitTree(int n) {
    this.bitLength = n;
    this.root = new Node(); // Initialize the root node
  } // End of BitTree constructor

  /**
   * Represents a node in the BitTree.
   * Can be an interior node (with children) or a leaf (with a value).
   */
  private class Node {
    /**
     * A map of child nodes, keyed by the bit value (0 or 1).
     */
    Map<Character, Node> children = new HashMap<>();

    /**
     * This holds the value for leaf nodes.
     */
    String value = null;
  } // End of Node class

  /**
   * Associates the given bit sequence with the specified value.
   *
   * @param bits the bit sequence
   * @param value the value to be associated with the bit sequence
   * @throws IndexOutOfBoundsException if the bit sequence is of incorrect 
   * length or contains invalid characters
   */
  public void set(String bits, String value) {
    if (bits.length() != bitLength) {
      throw new IndexOutOfBoundsException("Bit sequence must be of length " + bitLength);
    } // if

    // Traverse the tree and create nodes as necessary
    Node current = root;
    for (int i = 0; i < bits.length(); i++) {
      char bit = bits.charAt(i);
      if (bit != '0' && bit != '1') {
        throw new IndexOutOfBoundsException("Bit sequence contains invalid characters: only '0' and '1' are allowed");
      } // if
      current = current.children.computeIfAbsent(bit, k -> new Node());
    } // for

    // Set the value at the leaf node
    current.value = value;
  } // End of set method

  /**
   * Retrieves the value corresponding to the given bit sequence.
   *
   * @param bits the bit sequence
   * @return the associated value
   * @throws IndexOutOfBoundsException if the bit sequence is of incorrect 
   * length or no such path exists.
   */
  public String get(String bits) {
    if (bits.length() != bitLength) {
      throw new IndexOutOfBoundsException("Bit sequence must be of length " + bitLength);
    } // if

    // Traverse the tree following the bit sequence
    Node current = root;
    for (int i = 0; i < bits.length(); i++) {
      char bit = bits.charAt(i);
      if (bit != '0' && bit != '1' || !current.children.containsKey(bit)) {
        throw new IndexOutOfBoundsException("No such path exists in the tree for the bit sequence");
      } // if
      current = current.children.get(bit);
    } // for

    // Return the value at the leaf node
    if (current.value == null) {
      throw new IndexOutOfBoundsException("Bit sequence does not correspond to a value");
    } // if
    return current.value;
  } // End of get method]

  /**
   * Dumps the contents of the tree in CSV format.
   *
   * @param pen the output stream to which the tree is dumped
   */
  public void dump(PrintWriter pen) {
    dumpRecursive(root, "", pen);
  } // End of dump method

  // Helper method for recursive dumping
  private void dumpRecursive(Node node, String path, PrintWriter pen) {
    if (node.value != null) {
      pen.println(path + "," + node.value);
    } // if

    for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
      dumpRecursive(entry.getValue(), path + entry.getKey(), pen);
    } // for
  } // End of dumpRecursive method

  /**
   * Loads the contents of the tree from the specified input stream.
   *
   * @param source the input stream containing bit-value pairs
   */
  public void load(InputStream source) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(source))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
          set(parts[0], parts[1]);
        } else {
          throw new IllegalArgumentException("Each line must contain a bit sequence and a value, separated by a comma");
        } // else
      } // while
    } catch (Exception e) {
      throw new RuntimeException("Error loading data into BitTree", e);
    } // catch
  } // End of load method
} // End of BitTree class
