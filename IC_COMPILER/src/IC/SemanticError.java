package IC;

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
