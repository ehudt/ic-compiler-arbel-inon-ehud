package IC;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import IC.Parser.Lexer;
import IC.Parser.LexicalError;

/**
 * class Compiler opens a source file of IC language, scans the file
 * using the lexical analyzer and outputs the tokens in the file ordered by 
 * their appearance.
 * If the file contains invalid tokens, an error will be printed upon the 
 * first error and the processing will stop. 
 */
public class Compiler
{
   /**
    * @param args	path of the IC source file
    */
	public static void main(String[] args)
    {
		// check that there is only one argument, the path to an input file.
		if(args.length != 1)
		{
			System.out.println("Usage: java IC.Compiler <input-filename>\n");
		}
		
    	// currToken holds the current token from the scanner 
    	IC.Parser.Token currToken;
    	try {
    		// open file for scanning
    		FileReader txtFile = new FileReader(args[0]);
    		// initialize the scanner on the file
    		Lexer scanner = new Lexer(txtFile);
    	
    		/* Run the lexical analyzer on the input file and output
    		 * the tokens in the file sequentially. If EOF is reached,
    		 * the EOF token is printed and the program finishes.
    		 * If an error occurs, and error message is printed and the program
    		 * exits.
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
    	// Catch lexical Errors and print the line and the value of the token
    	catch (LexicalError e) {
			System.out.println(e.getMessage());
		}
    	// If the input file is not found, print an error to the user
    	catch (FileNotFoundException e) {
			System.out.println("Error: file not found " + args[0] + ". Check file path.");
		}
    	catch (IOException e) {
    		System.out.println("Error: I/O error during lexical analysis: " + e.getMessage());
    	}
    	
    }
}
