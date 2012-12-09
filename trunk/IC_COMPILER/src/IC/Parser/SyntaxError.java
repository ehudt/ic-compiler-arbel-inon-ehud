package IC.Parser;

import com.sun.java_cup.internal.runtime.Symbol;

public class SyntaxError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int line;
	private String token;
	
	public SyntaxError(int line, String message){
		super(line + ": Syntax error: " + message);
		this.line = line;
		this.token = token;
	}
	
	public SyntaxError(Token token){
		super(token.getLine() + ": Syntax error in token: " + Token.getTokenName(token.getId()) + 
				(token.value != "" ? " (" + token.value + ")" : ""));
	}

	
}
