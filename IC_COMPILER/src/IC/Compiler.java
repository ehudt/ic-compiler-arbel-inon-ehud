package IC;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java_cup.runtime.Symbol;

import IC.AST.*;
import IC.Parser.*;

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
		boolean printAST = false, useLib = false;
		String 	srcPath, libPath = "";
		// check that there is only one argument, the path to an input file.
		if(args.length < 1 || args.length > 3)
		{
			usage();
			return;
		}
		
		srcPath = args[0];
		
		// Read and parse the library file
		if(args.length >= 2){
			if(args[1].startsWith("-L") && args[1].length() > 2){
				libPath = args[1].substring(2);
				useLib = true;
				if(args.length == 3){
					if(args[2].equals("-print-ast")) 
						printAST = true; 
					else usage();
				}
			} 
			else if(args[1].equals("-print-ast") && args.length == 2) {
				printAST = true;
			} 
			else usage();
		}

		// start processing the source file(s)
    	try {
    		// parse IC source file
    		FileReader txtFile = new FileReader(srcPath);
    		Lexer scanner = new Lexer(txtFile);    		
    		Parser parser = new Parser(scanner);
    		Symbol parseSymbol = new Symbol(1);
    		// parse and generate AST
    		parseSymbol = parser.parse();
    		Program programRoot = (Program)parseSymbol.value;

    		// print the generated AST
    		if(printAST){
    			PrettyPrinter printer = new PrettyPrinter(srcPath);
    			System.out.println(programRoot.accept(printer));
    		}
    		
    		// parse library file
    		if(useLib){
    			FileReader libFile = new FileReader(libPath);
        		Lexer libScanner = new Lexer(libFile);    		
        		LibraryParser libParser = new LibraryParser(libScanner);
        		Symbol libParseSymbol = new Symbol(1);
        		// parse and generate AST
        		libParseSymbol = libParser.parse();
        		ICClass libraryRoot = (ICClass)parseSymbol.value;
        		
        		// TODO for debugging only (remove before handing in)
        		PrettyPrinter libPrinter = new PrettyPrinter(libPath);
    			System.out.println(libraryRoot.accept(libPrinter));
    		}
    		
    	}
    	// Catch lexical Errors and print the line and the value of the token
    	catch (LexicalError e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
    	// Handle syntax errors
    	catch (SyntaxError e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
    	// If the input file is not found, print an error to the user
    	catch (FileNotFoundException e) {
			System.out.println("Error: file not found " + args[0] + ". Check file path.");
			System.exit(-1);
		}
    	// Catch other I/O errors
    	catch (IOException e) {
    		System.out.println("Error: I/O error: " + e.getMessage());
    		System.exit(-1);
    	}
    	catch (Exception e) {
    		System.out.println("Error: " + e.getMessage());
    		e.printStackTrace();
    		System.exit(-1);
    	}
    	
    }
	
	private static void usage(){
		System.out.println("Usage: java IC.Compiler <input-filename> [ -L<library-path> ] [ -print-ast ]\n");
		System.exit(0);
	}
}
