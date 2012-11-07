package IC.Parser;


/** 
 * Report a lexical error during lexical analysis of a source file.
 * The exception can inlude the value of the erroneous token, the
 * line in which it appeared and a custom error message.
 */
public class LexicalError extends Exception
{
	private int line;
	private String value;
	
  /**
   * Initialize a LexicalError object with only a custom message
   * @param message   Error message string
   */
	public LexicalError(String message) {
		this("",-1,message);
	}
  /**
   * Initialize a LexicalError object with a token value, a line number
   * and a custom message.
   * @param value     Value of token that caused an error
   * @param line      Line # where the error occured
   * @param message   Error message string
   */
	public LexicalError(String value, int line, String message) {
		super(message);
		this.line = line + 1;
		this.value = value;		
    }
	/** 
   * Get the line number where the lexical analyzer encountered an error 
   */
	public int getLine(){
		return line;
	}

	/** 
   * Get the token that caused the lexical error 
   */
	public String getValue(){
		return value;
	}
	
	private static final long serialVersionUID = 1L;
}

