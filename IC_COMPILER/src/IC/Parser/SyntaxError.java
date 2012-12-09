package IC.Parser;

public class SyntaxError extends Exception {
	/**
	 * Report a syntactic error during syntactic analysis of a source file.	 
	 * */
	
	private static final long serialVersionUID = 1L;
	//private int line;
	//private String token;
	
	/**
	 * Initialize a SyntaxError object with the line number and a custom message
	 * @param line 		Line # where the error occurred
	 * @param message   Error message string
	 **/
	
	public SyntaxError(int line, String message){
		super(line + ": Syntax error: " + message);
		//this.line = line;
		//this.token = token;
	}
	
	/**
	 * Initialize a SyntaxError object with the line number, the token name and the
	 * token value (if token has value)
	 * @param token		The token that caused the error
	 **/
	public SyntaxError(Token token){
		super(token.getLine() + ": Syntax error in token: " + Token.getTokenName(token.getId()) + 
				(token.value != "" ? " (" + token.value + ")" : ""));
	}
}
