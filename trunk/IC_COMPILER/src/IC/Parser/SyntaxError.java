package IC.Parser;

public class SyntaxError extends Exception {
	private int line;
	private String token;
	
	public SyntaxError(int line, String token){
		super();
		this.line = line;
		this.token = token;
	}
	
	public String toString(){
		return "SyntexError in line: " + line + "in Token: " + token;
	}
	
}
