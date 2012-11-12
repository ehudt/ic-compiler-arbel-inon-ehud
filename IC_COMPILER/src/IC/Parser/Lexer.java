/* The following code was generated by JFlex 1.4.3 on 11/12/12 4:28 PM */

package IC.Parser;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 11/12/12 4:28 PM from the specification file
 * <tt>IC.lex</tt>
 */
public class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\1\1\52\2\0\1\1\22\0\1\10\1\56\1\12\2\7"+
    "\1\63\1\57\1\7\1\41\1\42\1\14\1\61\1\47\1\62\1\50"+
    "\1\15\1\5\11\6\1\7\1\51\1\55\1\53\1\54\2\7\32\3"+
    "\1\45\1\11\1\46\1\7\1\2\1\7\1\20\1\31\1\16\1\25"+
    "\1\22\1\35\1\33\1\37\1\26\1\4\1\40\1\17\1\4\1\13"+
    "\1\30\2\4\1\32\1\21\1\24\1\34\1\27\1\36\1\23\2\4"+
    "\1\43\1\60\1\44\1\7\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\2\5\1\1\1\4"+
    "\1\6\1\7\13\4\1\10\1\11\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\2\1\1\25\1\26\1\27\1\30\2\0\1\31\2\4"+
    "\1\0\1\2\11\4\1\32\6\4\1\33\1\34\1\35"+
    "\1\36\1\37\1\40\1\41\1\4\1\0\11\4\1\42"+
    "\6\4\1\43\1\0\5\4\1\44\1\4\1\45\1\46"+
    "\1\47\5\4\1\2\1\50\6\4\1\51\1\4\1\52"+
    "\1\53\1\4\1\54\1\55\1\56\2\4\1\57\1\4"+
    "\1\60\1\61\1\62";

  private static int [] zzUnpackAction() {
    int [] result = new int[129];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\64\0\64\0\150\0\234\0\320\0\u0104\0\u0138"+
    "\0\u016c\0\64\0\u01a0\0\u01d4\0\u0208\0\u023c\0\u0270\0\u02a4"+
    "\0\u02d8\0\u030c\0\u0340\0\u0374\0\u03a8\0\u03dc\0\64\0\64"+
    "\0\64\0\64\0\64\0\64\0\64\0\64\0\64\0\u0410"+
    "\0\u0444\0\u0478\0\u04ac\0\u04e0\0\u0514\0\64\0\64\0\64"+
    "\0\320\0\u0138\0\u0548\0\64\0\u057c\0\u05b0\0\u05e4\0\u0618"+
    "\0\u064c\0\u0680\0\u06b4\0\u06e8\0\u071c\0\u0750\0\u0784\0\u07b8"+
    "\0\u07ec\0\234\0\u0820\0\u0854\0\u0888\0\u08bc\0\u08f0\0\u0924"+
    "\0\64\0\64\0\64\0\64\0\64\0\64\0\234\0\u0958"+
    "\0\u098c\0\u09c0\0\u09f4\0\u0a28\0\u0a5c\0\u0a90\0\u0ac4\0\u0af8"+
    "\0\u0b2c\0\u0b60\0\234\0\u0b94\0\u0bc8\0\u0bfc\0\u0c30\0\u0c64"+
    "\0\u0c98\0\234\0\u0ccc\0\u0d00\0\u0d34\0\u0d68\0\u0d9c\0\u0dd0"+
    "\0\234\0\u0e04\0\234\0\234\0\234\0\u0e38\0\u0e6c\0\u0ea0"+
    "\0\u0ed4\0\u0f08\0\u05e4\0\234\0\u0f3c\0\u0f70\0\u0fa4\0\u0fd8"+
    "\0\u100c\0\u1040\0\234\0\u1074\0\234\0\234\0\u10a8\0\234"+
    "\0\234\0\234\0\u10dc\0\u1110\0\234\0\u1144\0\234\0\234"+
    "\0\234";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[129];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\2\1\4\1\5\1\6\1\7\1\2"+
    "\1\3\1\2\1\10\1\11\1\12\1\13\1\14\1\15"+
    "\1\5\1\16\1\17\1\5\1\20\1\5\1\21\1\22"+
    "\1\5\1\23\1\24\2\5\1\25\1\26\2\5\1\27"+
    "\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37"+
    "\1\3\1\40\1\41\1\42\1\43\1\44\1\45\1\46"+
    "\1\47\1\50\66\0\5\4\4\0\1\4\2\0\23\4"+
    "\25\0\5\5\4\0\1\5\2\0\23\5\30\0\2\51"+
    "\62\0\2\7\57\0\7\52\1\53\1\54\37\52\1\0"+
    "\11\52\2\0\5\5\4\0\1\5\2\0\4\5\1\55"+
    "\11\5\1\56\4\5\37\0\1\57\1\60\50\0\5\5"+
    "\4\0\1\5\2\0\1\5\1\61\10\5\1\62\10\5"+
    "\25\0\5\5\4\0\1\5\2\0\4\5\1\63\16\5"+
    "\25\0\5\5\4\0\1\5\2\0\6\5\1\64\14\5"+
    "\25\0\5\5\4\0\1\5\2\0\1\5\1\65\3\5"+
    "\1\66\15\5\25\0\5\5\4\0\1\5\2\0\14\5"+
    "\1\67\4\5\1\70\1\5\25\0\5\5\4\0\1\71"+
    "\2\0\17\5\1\72\3\5\25\0\5\5\4\0\1\5"+
    "\2\0\12\5\1\73\10\5\25\0\5\5\4\0\1\5"+
    "\2\0\12\5\1\74\1\5\1\75\6\5\25\0\5\5"+
    "\4\0\1\5\2\0\4\5\1\76\16\5\25\0\5\5"+
    "\4\0\1\5\2\0\2\5\1\77\20\5\25\0\5\5"+
    "\4\0\1\5\2\0\21\5\1\100\1\5\76\0\1\101"+
    "\63\0\1\102\63\0\1\103\63\0\1\104\67\0\1\105"+
    "\64\0\1\106\14\0\3\52\10\0\1\52\41\0\5\5"+
    "\4\0\1\5\2\0\20\5\1\107\2\5\25\0\5\5"+
    "\4\0\1\5\2\0\1\5\1\110\21\5\23\0\14\57"+
    "\1\111\47\57\52\60\1\0\11\60\2\0\5\5\4\0"+
    "\1\5\2\0\2\5\1\112\20\5\25\0\5\5\4\0"+
    "\1\113\2\0\23\5\25\0\5\5\4\0\1\114\2\0"+
    "\23\5\25\0\5\5\4\0\1\5\2\0\2\5\1\115"+
    "\11\5\1\116\6\5\25\0\5\5\4\0\1\5\2\0"+
    "\3\5\1\117\17\5\25\0\5\5\4\0\1\5\2\0"+
    "\6\5\1\120\14\5\25\0\5\5\4\0\1\5\2\0"+
    "\16\5\1\121\4\5\25\0\5\5\4\0\1\5\2\0"+
    "\10\5\1\122\12\5\25\0\5\5\4\0\1\5\2\0"+
    "\6\5\1\123\14\5\25\0\5\5\4\0\1\5\2\0"+
    "\10\5\1\124\12\5\25\0\5\5\4\0\1\5\2\0"+
    "\12\5\1\125\10\5\25\0\5\5\4\0\1\5\2\0"+
    "\4\5\1\126\16\5\25\0\5\5\4\0\1\5\2\0"+
    "\6\5\1\127\14\5\25\0\5\5\4\0\1\5\2\0"+
    "\1\5\1\130\21\5\25\0\5\5\4\0\1\5\2\0"+
    "\10\5\1\131\12\5\25\0\5\5\4\0\1\5\2\0"+
    "\1\5\1\132\21\5\23\0\14\57\1\133\1\3\46\57"+
    "\2\0\5\5\4\0\1\5\2\0\3\5\1\134\17\5"+
    "\25\0\5\5\4\0\1\5\2\0\6\5\1\135\14\5"+
    "\25\0\5\5\4\0\1\5\2\0\15\5\1\136\5\5"+
    "\25\0\5\5\4\0\1\5\2\0\6\5\1\137\14\5"+
    "\25\0\5\5\4\0\1\5\2\0\10\5\1\140\12\5"+
    "\25\0\5\5\4\0\1\5\2\0\4\5\1\141\16\5"+
    "\25\0\5\5\4\0\1\5\2\0\4\5\1\142\16\5"+
    "\25\0\5\5\4\0\1\5\2\0\4\5\1\143\16\5"+
    "\25\0\5\5\4\0\1\5\2\0\3\5\1\144\17\5"+
    "\25\0\5\5\4\0\1\5\2\0\7\5\1\145\13\5"+
    "\25\0\5\5\4\0\1\5\2\0\1\5\1\146\21\5"+
    "\25\0\5\5\4\0\1\5\2\0\2\5\1\147\20\5"+
    "\25\0\5\5\4\0\1\5\2\0\16\5\1\150\4\5"+
    "\25\0\5\5\4\0\1\5\2\0\3\5\1\151\17\5"+
    "\25\0\5\5\4\0\1\5\2\0\1\5\1\152\21\5"+
    "\23\0\14\57\1\111\1\153\46\57\2\0\5\5\4\0"+
    "\1\5\2\0\3\5\1\154\17\5\25\0\5\5\4\0"+
    "\1\5\2\0\10\5\1\155\12\5\25\0\5\5\4\0"+
    "\1\5\2\0\6\5\1\156\14\5\25\0\5\5\4\0"+
    "\1\5\2\0\10\5\1\157\12\5\25\0\5\5\4\0"+
    "\1\160\2\0\23\5\25\0\5\5\4\0\1\161\2\0"+
    "\23\5\25\0\5\5\4\0\1\5\2\0\4\5\1\162"+
    "\16\5\25\0\5\5\4\0\1\5\2\0\22\5\1\163"+
    "\25\0\5\5\4\0\1\5\2\0\14\5\1\164\6\5"+
    "\25\0\5\5\4\0\1\5\2\0\4\5\1\165\16\5"+
    "\25\0\5\5\4\0\1\5\2\0\4\5\1\166\16\5"+
    "\25\0\5\5\4\0\1\167\2\0\23\5\25\0\5\5"+
    "\4\0\1\5\2\0\21\5\1\170\1\5\25\0\5\5"+
    "\4\0\1\5\2\0\1\171\22\5\25\0\5\5\4\0"+
    "\1\5\2\0\15\5\1\172\5\5\25\0\5\5\4\0"+
    "\1\5\2\0\7\5\1\173\13\5\25\0\5\5\4\0"+
    "\1\5\2\0\2\5\1\174\20\5\25\0\5\5\4\0"+
    "\1\175\2\0\23\5\25\0\5\5\4\0\1\5\2\0"+
    "\16\5\1\176\4\5\25\0\5\5\4\0\1\5\2\0"+
    "\3\5\1\177\17\5\25\0\5\5\4\0\1\200\2\0"+
    "\23\5\25\0\5\5\4\0\1\5\2\0\4\5\1\201"+
    "\16\5\23\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4472];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\2\11\6\1\1\11\14\1\11\11\6\1\3\11"+
    "\1\1\2\0\1\11\2\1\1\0\21\1\6\11\2\1"+
    "\1\0\21\1\1\0\46\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[129];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 130) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) throws LexicalError {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new LexicalError(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  throws LexicalError {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Token next_token() throws java.io.IOException, LexicalError {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          zzR = false;
          break;
        case '\r':
          yyline++;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
          }
          break;
        default:
          zzR = false;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { throw new LexicalError(yytext(), (yyline + 1), (yyline + 1) + ": Lexical error: illegal character '" + yytext() + "'");
          }
        case 51: break;
        case 12: 
          { return new Token(sym.LB,(yyline + 1));
          }
        case 52: break;
        case 3: 
          { return new Token(sym.CLASS_ID,(yyline + 1),yytext());
          }
        case 53: break;
        case 30: 
          { return new Token(sym.NEQUAL,(yyline + 1));
          }
        case 54: break;
        case 48: 
          { return new Token(sym.EXTENDS,(yyline + 1));
          }
        case 55: break;
        case 36: 
          { return new Token(sym.ELSE,(yyline + 1));
          }
        case 56: break;
        case 23: 
          { return new Token(sym.MOD,(yyline + 1));
          }
        case 57: break;
        case 32: 
          { return new Token(sym.LOR,(yyline + 1));
          }
        case 58: break;
        case 45: 
          { return new Token(sym.STATIC,(yyline + 1));
          }
        case 59: break;
        case 9: 
          { return new Token(sym.RP,(yyline + 1));
          }
        case 60: break;
        case 16: 
          { return new Token(sym.SEMI,(yyline + 1));
          }
        case 61: break;
        case 44: 
          { return new Token(sym.LENGTH,(yyline + 1));
          }
        case 62: break;
        case 10: 
          { return new Token(sym.LCBR,(yyline + 1));
          }
        case 63: break;
        case 14: 
          { return new Token(sym.COMMA,(yyline + 1));
          }
        case 64: break;
        case 47: 
          { return new Token(sym.RETURN,(yyline + 1));
          }
        case 65: break;
        case 43: 
          { return new Token(sym.WHILE,(yyline + 1));
          }
        case 66: break;
        case 40: 
          { return new Token(sym.CLASS,(yyline + 1));
          }
        case 67: break;
        case 19: 
          { return new Token(sym.LT,(yyline + 1));
          }
        case 68: break;
        case 39: 
          { return new Token(sym.VOID,(yyline + 1));
          }
        case 69: break;
        case 7: 
          { return new Token(sym.DIVIDE,(yyline + 1));
          }
        case 70: break;
        case 28: 
          { return new Token(sym.GTE,(yyline + 1));
          }
        case 71: break;
        case 18: 
          { return new Token(sym.GT,(yyline + 1));
          }
        case 72: break;
        case 31: 
          { return new Token(sym.LAND,(yyline + 1));
          }
        case 73: break;
        case 35: 
          { return new Token(sym.NULL,(yyline + 1));
          }
        case 74: break;
        case 49: 
          { return new Token(sym.BOOLEAN,(yyline + 1));
          }
        case 75: break;
        case 21: 
          { return new Token(sym.PLUS,(yyline + 1));
          }
        case 76: break;
        case 8: 
          { return new Token(sym.LP,(yyline + 1));
          }
        case 77: break;
        case 15: 
          { return new Token(sym.DOT,(yyline + 1));
          }
        case 78: break;
        case 26: 
          { return new Token(sym.IF,(yyline + 1));
          }
        case 79: break;
        case 38: 
          { return new Token(sym.THIS,(yyline + 1));
          }
        case 80: break;
        case 25: 
          { return new Token(sym.QUOTE,(yyline + 1),yytext());
          }
        case 81: break;
        case 29: 
          { return new Token(sym.LTE,(yyline + 1));
          }
        case 82: break;
        case 50: 
          { return new Token(sym.CONTINUE,(yyline + 1));
          }
        case 83: break;
        case 22: 
          { return new Token(sym.MINUS,(yyline + 1));
          }
        case 84: break;
        case 11: 
          { return new Token(sym.RCBR,(yyline + 1));
          }
        case 85: break;
        case 41: 
          { return new Token(sym.BREAK,(yyline + 1));
          }
        case 86: break;
        case 13: 
          { return new Token(sym.RB,(yyline + 1));
          }
        case 87: break;
        case 4: 
          { return new Token(sym.ID,(yyline + 1),yytext());
          }
        case 88: break;
        case 27: 
          { return new Token(sym.EQUAL,(yyline + 1));
          }
        case 89: break;
        case 20: 
          { return new Token(sym.LNEG,(yyline + 1));
          }
        case 90: break;
        case 17: 
          { return new Token(sym.ASSIGN,(yyline + 1));
          }
        case 91: break;
        case 46: 
          { return new Token(sym.STRING,(yyline + 1));
          }
        case 92: break;
        case 34: 
          { return new Token(sym.INT,(yyline + 1));
          }
        case 93: break;
        case 33: 
          { return new Token(sym.NEW,(yyline + 1));
          }
        case 94: break;
        case 37: 
          { return new Token(sym.TRUE,(yyline + 1));
          }
        case 95: break;
        case 24: 
          { throw new LexicalError(yytext(), (yyline + 1), (yyline + 1) + ": Lexical error: illegal integer format in token '" + yytext() + "'");
          }
        case 96: break;
        case 6: 
          { return new Token(sym.MULTIPLY,(yyline + 1));
          }
        case 97: break;
        case 42: 
          { return new Token(sym.FALSE,(yyline + 1));
          }
        case 98: break;
        case 5: 
          { return new Token(sym.INTEGER,(yyline + 1),yytext());
          }
        case 99: break;
        case 2: 
          { 
          }
        case 100: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              {     return new Token(sym.EOF, (yyline + 1));
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
