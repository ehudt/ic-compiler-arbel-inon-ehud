package IC.Parser;

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

	
}
