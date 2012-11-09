package IC;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import IC.Parser.Lexer;
import IC.Parser.LexicalError;

/**
 * The class that goes over the text file, 
 * and runs the lexer on it.
 */

public class Compiler
{
   /**
    * @param args should contain the path to the text file
    */
	public static void main(String[] args)
    {
		if(args.length != 1)
		{
			System.err.println("Usage: java IC.Compiler <input-filename>\n")
		}
    	// cuurToken will hold the current token from the scanner 
    	IC.Parser.Token currToken;
    	try {
    		FileReader txtFile = new FileReader(args[0]);
    		
    		Lexer scanner = new Lexer(txtFile);
    	
    		/* Run the NextToken function on the text file untill 
    		 * reaching the EOF Token. Print for each token the 
    		 * line it's written at, the Token name and its value
    		 * if there is one.
       		 */
    		
    		do{
    			currToken = scanner.next_token();
    			System.out.print(currToken.getLine()+": "+ currToken.getName());
    			if (!currToken.getValue().isEmpty())
    				System.out.println("(" + currToken.getValue() + ")");
    			else{ 
    				System.out.println("");
    			}
    		}
    		while (currToken.getId() != IC.Parser.sym.EOF);
    	}
    	//Catch lexical Errors and print the line and the value of the token
    	catch (LexicalError e) {
			System.out.println(e.message);
		}
    	catch (FileNotFoundException e) {
			System.err.println("Error: file not found " + args[0] + ". Check file path.");
		}
    	
    }
}
