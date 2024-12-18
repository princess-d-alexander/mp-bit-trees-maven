package edu.grinnell.csc207.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for converting between Braille, ASCII, and Unicode.
 *
 * @author Princess Alexander
 * @author Samuel A. Rebelsky
 */
public class BrailleAsciiTables {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+
  /**
   * Conversion table for ASCII to Braille.
   * Each line maps an ASCII character to its Braille bit string.
   */
  // Conversion tables
  static final String a2b =
      "01000001,100000\n"
      + "01000010,110000\n"
      + "01000011,100100\n"
      + "01000100,100110\n"
      + "01000101,100010\n"
      + "01000110,110100\n"
      + "01000111,110110\n"
      + "01001000,110010\n"
      + "01001001,010100\n"
      + "01001010,010110\n"
      + "01001011,101000\n"
      + "01001100,111000\n"
      + "01001101,101100\n"
      + "01001110,101110\n"
      + "01001111,101010\n"
      + "01010000,111100\n"
      + "01010001,111110\n"
      + "01010010,111010\n"
      + "01010011,011100\n"
      + "01010100,011110\n"
      + "01010101,101001\n"
      + "01010110,111001\n"
      + "01010111,010111\n"
      + "01011000,101101\n"
      + "01011001,101111\n"
      + "01011010,101011\n"
      + "01100001,100000\n"
      + "01100010,110000\n"
      + "01100011,100100\n"
      + "01100100,100110\n"
      + "01100101,100010\n"
      + "01100110,110100\n"
      + "01100111,110110\n"
      + "01101000,110010\n"
      + "01101001,010100\n"
      + "01101010,010110\n"
      + "01101011,101000\n"
      + "01101100,111000\n"
      + "01101101,101100\n"
      + "01101110,101110\n"
      + "01101111,101010\n"
      + "01110000,111100\n"
      + "01110001,111110\n"
      + "01110010,111010\n"
      + "01110011,011100\n"
      + "01110100,011110\n"
      + "01110101,101001\n"
      + "01110110,111001\n"
      + "01110111,010111\n"
      + "01111000,101101\n"
      + "01111001,101111\n"
      + "01111010,101011\n"
      + "00100000,000000\n";
  /**
   * Conversion table for Braille to Asciicode.
   * Each line maps a Braille bit string to a Asciicode character.
   */
  static final String b2a =
      "100000,A\n"
      + "110000,B\n"
      + "100100,C\n"
      + "100110,D\n"
      + "100010,E\n"
      + "110100,F\n"
      + "110110,G\n"
      + "110010,H\n"
      + "010100,I\n"
      + "010110,J\n"
      + "101000,K\n"
      + "111000,L\n"
      + "101100,M\n"
      + "101110,N\n"
      + "101010,O\n"
      + "111100,P\n"
      + "111110,Q\n"
      + "111010,R\n"
      + "011100,S\n"
      + "011110,T\n"
      + "101001,U\n"
      + "111001,V\n"
      + "101101,X\n"
      + "101111,Y\n"
      + "101011,Z\n"
      + "010111,W\n"
      + "000000, \n";
  /**
   * Conversion table for Braille to Unicode.
   * Each line maps a Braille bit string to a Unicode character.
   */
  static final String b2u =
      "000000,2800\n"
      + "100000,2801\n"
      + "010000,2802\n"
      + "110000,2803\n"
      + "001000,2804\n"
      + "101000,2805\n"
      + "011000,2806\n"
      + "111000,2807\n"
      + "000100,2808\n"
      + "100100,2809\n"
      + "010100,280A\n"
      + "110100,280B\n"
      + "001100,280C\n"
      + "101100,280D\n"
      + "011100,280E\n"
      + "111100,280F\n"
      + "000010,2810\n"
      + "100010,2811\n"
      + "010010,2812\n"
      + "110010,2813\n"
      + "001010,2814\n"
      + "101010,2815\n"
      + "011010,2816\n"
      + "111010,2817\n"
      + "000110,2818\n"
      + "100110,2819\n"
      + "010110,281A\n"
      + "110110,281B\n"
      + "001110,281C\n"
      + "101110,281D\n"
      + "011110,281E\n"
      + "111110,281F\n";

  // +---------------+-----------------------------------------------
  // | Static fields |
  // +---------------+

  /**
   * Tree for braille-to-ASCII conversion.
   */
  static BitTree b2aTree = null;

  /**
   * Tree for braille-to-Unicode conversion.
   */
  static BitTree b2uTree = null;

  // +-----------------------+---------------------------------------
  // | Static helper methods |
  // +-----------------------+

  // +----------------+----------------------------------------------
  // | Static methods |
  // +----------------+

  /**
   * Converts an ASCII letter to its Braille representation.
   *
   * @param letter the ASCII letter to convert
   * @return the Braille representation of the letter
   */
  public static String toBraille(char letter) {
    // Find the Braille representation in the a2b table
    String letterBits = getFromA2BTable(letter);
    return letterBits != null ? letterBits : "";  // Return empty string if not found
  } // String toBraille

  /**
   * Converts a string of Braille bits into the corresponding ASCII characters.
   *
   * @param bits a string of Braille bits
   * @return the ASCII representation of the bits
   */
  public static String toAscii(String bits) {
    // Ensure the Braille-to-ASCII tree is loaded
    if (b2aTree == null) {
      b2aTree = new BitTree(6);
      InputStream b2aStream = new ByteArrayInputStream(b2a.getBytes());
      b2aTree.load(b2aStream);
      try {
        b2aStream.close();
      } catch (IOException e) {
      } // catch (Ignore)
    } // if
    return b2aTree.get(bits);  // Look up the ASCII representation
  } // String toAscii

  /**
   * Converts a string of Braille bits into the corresponding Unicode characters.
   *
   * @param bits a string of Braille bits
   * @return the Unicode representation of the bits
   */
  public static String toUnicode(String bits) {
    // Ensure the Braille-to-Unicode tree is loaded
    if (b2uTree == null) {
      b2uTree = new BitTree(6);
      InputStream b2uStream = new ByteArrayInputStream(b2u.getBytes());
      b2uTree.load(b2uStream);
      try {
        b2uStream.close();
      } catch (IOException e) {
      } // catch (ignore)
    } // if
    return b2uTree.get(bits);  // Look up the Unicode representation
  } // String to Unicode

  /**
   * Helper method to get the Braille bit string from the a2b table for a given letter.
   *
   * @param letter the ASCII character
   * @return the Braille bit string for the letter, or null if not found
   */
  private static String getFromA2BTable(char letter) {
    String[] mappings = a2b.split("\n");
    for (String mapping : mappings) {
      String[] pair = mapping.split(",");
      if (pair[1].charAt(0) == letter) {
        return pair[0];
      } // if
    } // for
    return null;
  } //String getFromA2BTable
} // BrailleAsciiTables
