package IC.Parser;

public class LexicalError extends Exception
{
    /**
	 * 
	 */
	private int line;
	private String value;
	
	public LexicalError(String message) {
		this("",-1,message);
	}

	public LexicalError(String value, int line, String message) {
		super(message);
		this.line = line;
		this.value = value;		
    }
	
	public int getLine(){
		return line;
	}
	
	public String getValue(){
		return value;
	}
	
	private static final long serialVersionUID = 1L;
}

