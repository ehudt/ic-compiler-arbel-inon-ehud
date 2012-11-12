package IC.Parser;


/** 
 * Report a lexical error during lexical analysis of a source file.
 * The exception includes the value of the erroneous token (if applicable), 
 * the line in which the token appeared and a specific error message.
 */
public class LexicalError extends Exception
{
	/* line number where the error occurred, or 0 if not applicable */
	private int line;
	/* the string that caused the lexical error. Can have length of 1 or more. */
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
	 * @param value     the string that that caused an error
	 * @param line      Line # where the error occurred
	 * @param message   Error message string
	 */
	public LexicalError(String value, int line, String message) {
		super(message);
		this.line = line;
		this.value = value;		
    }
	
	/** 
	 * Get the line number where the lexical analyzer encountered an error.
	 * @return	line in the input where the error occurred 
	 */
	public int getLine(){
		return line;
	}
	
	/** 
	 * Get the string that caused the lexical error
	 * @return	string of erroneous token 
	 */
	public String getValue(){
		return value;
	}
	
	private static final long serialVersionUID = 1L;
}

