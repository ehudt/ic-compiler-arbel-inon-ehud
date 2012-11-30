package IC.Parser;

public class SyntaxError extends Exception {
	private int line;
	private String error;
	
	public SyntaxError(int line, String error){
		super();
		this.line = line;
		this.error = error;
	}
	
	public String toString(){
		return "Syntax Error in line: " + line + ": " + error;
	}
	
}
