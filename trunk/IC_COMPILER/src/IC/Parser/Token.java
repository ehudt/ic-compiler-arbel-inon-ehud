package IC.Parser;

import java_cup.runtime.Symbol;
import java.lang.reflect.*;

/**
 * class Token represents a token of the IC programming language.
 * Each token object contains its id, line number, name and value
 * if applicable.
 */
public class Token extends Symbol {
	
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
    public Token(int id, int line, Object value) {
        // Use the constructor of the superclass to hold the token ID.
    	super(id, line, 0, value);
    }
    
    /**
     * Get the current token's ID
     * @return	this token's ID
     */
    public int getId() {
    	return this.sym;
    }
    
    /**
     * Get the line in which this token appears.
     * @return	the line in which this token appears
     */
    public int getLine() {
    	return this.left;
    }
    /**
     * Get the token type's string representation
     * @return	the token type's string representation
     */
    static String getTokenName(int tokenId){
    	Field[] symbols = IC.Parser.sym.class.getDeclaredFields();

    	for(Field f : symbols){
    		try{
    			int fieldInt = f.getInt(null);
    			if(tokenId == fieldInt){
    				return f.getName();
    			}
    		}
    		catch (Exception e){
    			continue;
    		}
    	}
    	return null;
    }
}

