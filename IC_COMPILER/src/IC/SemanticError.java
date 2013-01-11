package IC;

/** 
 * Report a semantic error during semantic analysis of a source file. 
 * The error includes the line in which the token appeared and a specific error message.
 */
public class SemanticError extends Exception {

	public int line;
	
	public SemanticError(String message){
		super(message);
	}
	
	public SemanticError(int line, String message){
		super(message);
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
