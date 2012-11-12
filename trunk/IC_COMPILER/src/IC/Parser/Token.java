package IC.Parser;

import java_cup.runtime.Symbol;

/**
 * class Token represents a token of the IC programming language.
 * Each token object contains its id, line number, name and value
 * if applicable.
 */
public class Token extends Symbol {
	/* The line in which the token appears */
	private int line;
	/* A token's value, if applicable */
	private String value;
	/* The token's name. This is derived by the constructor from the token ID. */
	private String name;
	
	/** 
	 * Constructor for tokens without a value. This is used for any token whose value 
	 * is implied. For example, the token '+' always means PLUS.
	 * @param id	The token's ID, according to IC.Parser.sym
	 * @param line	The line in which the token appears
	 */
	public Token(int id, int line){
		this(id, line, "");
	}
	/** 
	 * Constructor for tokens with a value. This constructor is used when a token's value
	 * is variable. For example, a token ID can have a lot of different values, such as 'foo'. 
	 * @param id	The token's ID, according to IC.Parser.sym
	 * @param line	The line in which the token appears
	 * @param value	The token's string value.
	 */
    public Token(int id, int line, String value) {
        // Use the constructor of the superclass to hold the token ID.
    	super(id, null);
        
        // A line's number must be positive
		if(line <= 0){
			throw new IllegalArgumentException("Line number can't be negative");
		}
        this.line = line;
        
        // A token's value cannot be null. If there is no value, it is represented as "".
        if(value == null) {
        	throw new IllegalArgumentException("Token value cannot be NULL.");
        }
    	this.value = value;
    	
    	// Resolves the token's name, to be printed later on.
		this.name = IC.Parser.sym.getNameById(id);
		if(this.name.isEmpty()){
			throw new IllegalArgumentException("Token id is invalid");
		}
    }
    /**
     * Get the current token's ID
     * @return	this token's ID
     */
    public int getId() {
    	return this.sym;
    }
    
    /**
     * Get the current token's value (for tokens such as ID, Class ID, String, Integer). 
     * If the token has no value then the empty string, "", is returned.
     * @return	the specific value for a token
     */
    public String getValue() {
    	return this.value;
    }
    
    /**
     * Get the line in which this token appears.
     * @return	the line in which this token appears
     */
    public int getLine() {
    	return this.line;
    }
    
    /**
     * Get the token's string name. "
     * @return	this token's name in string representation
     */
    public String getName(){
    	return name;
    }
}

