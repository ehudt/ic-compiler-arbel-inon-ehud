package IC.Parser;

import com.sun.java_cup.internal.runtime.Symbol;

public class SyntaxError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int line;
	private String token;
	
	public SyntaxError(int line, String token){
		super("SyntaxError in line:" + line + " in Token: " + token);
		this.line = line;
		this.token = token;
	}
	
	public SyntaxError(Token token){
		super("Syntax error in line " + token.getLine() + " in token: " + Token.getTokenName(token.getId()) + 
				(token.value != "" ? " (" + token.value + ")" : ""));
	}

	
}
