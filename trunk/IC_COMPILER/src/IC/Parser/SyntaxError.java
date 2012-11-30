package IC.Parser;

public class SyntaxError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int line;
	private String token;
	
	public SyntaxError(int line, String token){
		super();
		this.line = line;
		this.token = token;
	}
	
	public String toString(){
		return "SyntaxError in line: " + line + "in Token: " + token;
	}
	
}