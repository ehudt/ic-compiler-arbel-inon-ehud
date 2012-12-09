package IC;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		boolean printAst = false, useLib = false;
		String 	srcPath, libPath = "";

		// validate the number of arguments
		if(args.length < 1 || args.length > 3)
			usage();
		
		// process command line arguments: paths and flags 
		srcPath = args[0];
		for( int i = 1; i < args.length; i++ ){
			String arg = args[i];
			if(arg.startsWith("-L")){
				if (useLib){
					System.out.println("Error: only 1 library file supported per compiler execution.");
					usage();
				}
				else if(arg.length() == 2){
					System.out.println("Error: library flag must include path to file.");
					usage();
				} 
				else { // arg.length() > 2
					libPath = arg.substring(2);
					useLib = true;
				}
			}
			else if(arg.equals("-print-ast")){
				if(printAst){
					System.out.println("Error: duplicate flag: " + arg);
					usage();
				} else printAst = true;
			} 
			else {
				System.out.println("Error: invalid argument: " + arg);
				usage();
			}
		}

		// start processing the source file(s)
    	try {
    		// parse library file
    		if(useLib){
    			System.out.println("Processing library file: " + libPath);
    			FileReader libFile = new FileReader(libPath);
        		Lexer libScanner = new Lexer(libFile);
        		
        		LibraryParser libParser = new LibraryParser(libScanner);
        		Symbol libParseSymbol = new Symbol(1);
        		// parse and generate AST
        		libParseSymbol = libParser.parse();//.parse();
        		ICClass libraryRoot = (ICClass)libParseSymbol.value;
        		
        		List<ICClass> dummyList = new ArrayList<ICClass>();
        		dummyList.add(libraryRoot);
        		Program libraryProgram = new Program(dummyList);
        		
        		if(printAst){
	        		PrettyPrinter libPrinter = new PrettyPrinter(libPath);
	    			System.out.println(libraryProgram.accept(libPrinter));
	    			System.out.println();
        		}
    		}
    		
    		// parse IC source file
    		System.out.println("Processing source file: " + libPath);
    		FileReader txtFile = new FileReader(srcPath);
    		Lexer scanner = new Lexer(txtFile);    		
    		Parser parser = new Parser(scanner);
    		Symbol parseSymbol = new Symbol(1);
    		// parse and generate AST
    		parseSymbol = parser.parse();
    		Program programRoot = (Program)parseSymbol.value;

    		// print the generated AST
    		if(printAst){
    			PrettyPrinter printer = new PrettyPrinter(srcPath);
    			System.out.println(programRoot.accept(printer));
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
			System.out.println("Error: " + e.getMessage() + ". Check file path.");
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
