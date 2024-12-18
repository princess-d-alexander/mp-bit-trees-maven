package edu.grinnell.csc207.main;

import java.io.PrintWriter;

import edu.grinnell.csc207.util.BrailleAsciiTables;

/**
 *
 */
public class BrailleASCII {
  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   *
   * @author Princess Alexander
   * @author Samuel A. Rebelsky
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    // Test conversion from ASCII to Braille
    pen.println("ASCII -> Braille");
    pen.println("----------------");
    for (char letter = 'A'; letter <= 'Z'; ++letter) {
      try {
        pen.println(letter + " -> " + BrailleAsciiTables.toBraille(letter));
      } catch (Exception e) {
        pen.println("Could not convert " + letter + " to braille because "
                        + e.getMessage());
      } // try
    } // for

    // Test conversion from Braille to ASCII
    String[] brailleCharacters = new String[] {
        "100000", "110000", "100100", "100110", "100010", "110100", "110110",
        "110010", "010100", "010110", "101000", "111000", "101100", "101110",
        "101010", "111100", "111110", "111010", "011100", "011110", "101001",
        "111001", "101101", "101111", "101011", "010111", "000000"
    };

    pen.println();
    pen.println("Braille -> ASCII");
    pen.println("----------------");
    for (String bits : brailleCharacters) {
      try {
        pen.println(bits + " -> '" + BrailleAsciiTables.toAscii(bits) + "'");
      } catch (Exception e) {
        pen.println("Could not convert " + bits + " to ASCII because "
                    + e.getMessage());
      } // try
    } // for

    // Test conversion from Braille to Unicode
    pen.println();
    pen.println("Braille -> Unicode");
    pen.println("------------------");
    for (String bits : brailleCharacters) {
      try {
        pen.println(bits + " -> '" + BrailleAsciiTables.toUnicode(bits) + "'");
      } catch (Exception e) {
        pen.println("Could not convert " + bits + " to unicode because "
                    + e.getMessage());
      } // try
    } // for
    pen.close();
  } // main(String[]

} // class BrailleASCII

