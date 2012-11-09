package IC;

import java.io.FileReader;

//import com.sun.java_cup.internal.runtime.Symbol;
import IC.Parser.Lexer;
import IC.Parser.LexicalError;

/**
 * Class Compiler is the class that goes over the text file, and run the lexer on it.
 * It gets one argumant: The text file path.
 */

public class Compiler
{
    public static void main(String[] args) throws Exception
    {
    	/** 
    	 * Define a variable that will hold the current token 
    	 */
    	
    	IC.Parser.Token currToken;
    	try {
    		/**
    		 *  Create the FileReader 
    		 */
    		FileReader txtFile = new FileReader(args[0]);
    		
    		/** Create the Scanner */
    		Lexer scanner = new Lexer(txtFile);
    	
    		/** Run the NextToken function on the text file untill reaching the EOF Token. 
    		 * Print for each token the line it's written at, the Token name and if there's a value,
    		 * the value
    		 */
    		
    		do{
    			currToken = scanner.next_token();
    			System.out.print(currToken.getLine()+": "+ currToken.getName());
    			if (currToken.getValue() != "")
    				System.out.println("(" + currToken.getValue() + ")");
    			
    			else{ 
    				System.out.print("");
    				
    				/** 
    				 * only print a new line if it's not the last Token
    				 */
    				if (currToken.getId() != IC.Parser.sym.EOF){
    					System.out.println("");
    				}
    			}
    		}
    		while (currToken.getId() != IC.Parser.sym.EOF);
    	}
    	
    	
    	/**
    	 * Catch lexical Errors and print the line and the value of the token
    	 */
    	catch (LexicalError e) {
			System.out.println(e.getLine() + ": Lexical error: illegal token '" + e.getValue() + "'");
		}
    	
    	//TODO define what to do here
    	catch (Exception e) {
			throw e;
		}
    	
    }
}
