package IC;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java_cup.runtime.Symbol;

import IC.AST.*;
import IC.LIR.LirBlock;
import IC.LIR.OptimizedTranslateVisitor;
import IC.LIR.SethiUllmanWeightVisitor;
import IC.LIR.TranslateVisitor;
import IC.Parser.*;
import IC.Semantic.ReturnStatementVisitor;
import IC.Semantic.StructureChecksVisitor;
import IC.Semantic.TypeCheckVisitor;
import IC.Semantic.VariableInitializeVisitor;
import IC.SymbolTable.BuildSymbolTables;
import IC.SymbolTable.GlobalSymbolTable;
import IC.Types.TypeTable;

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
    * @param args		First argument must be the source file path
    * 					optional arguments: -L<library-path> 	Path to IC library file (.sig)
    * 										-print-ast 			Print the AST generated by the parser
    * 										-dump-symtab		Print the symbol table and type table generated
    * 															by the compiler
    */
	public static void main(String[] args)
    {
		boolean printAst = false, useExternalLib = false, printSymTab = false, printLir = false;
		String 	srcPath, libPath = "libic.sig", currentFile = "";

		// validate the number of arguments
		if(args.length < 1)
			usage();
		
		// process command line arguments: paths and flags 
		srcPath = args[0];
		for( int i = 1; i < args.length; i++ ){
			String arg = args[i];
			if(arg.startsWith("-L")){
				if (useExternalLib){
					System.out.println("Error: only 1 library file supported per compiler execution.");
					usage();
				}
				else if(arg.length() == 2){
					System.out.println("Error: library flag must include path to file.");
					usage();
				} 
				else { // arg.length() > 2
					libPath = arg.substring(2);
					useExternalLib = true;
				}
			}
			else if(arg.equals("-print-ast")){
				if(printAst){
					System.out.println("Error: duplicate flag: " + arg);
					usage();
				} else printAst = true;
			}
			else if(arg.equals("-dump-symtab")){
				if(printSymTab)	{
					System.out.println("Error: duplicate flag: " + arg);
					usage();
				} else printSymTab = true;
			}
			else if(arg.equals("-print-lir")) {
				if(printLir) {
					System.out.println("Error: duplicate flag: " + arg);
					usage();
				} else printLir = true;
			}
			else {
				System.out.println("Error: invalid argument: " + arg);
				usage();
			}
		}

		// start processing the source file(s)
    	try {
    		// parse library file
			currentFile = libPath;
			FileReader libFile = new FileReader(libPath);
    		Lexer libScanner = new Lexer(libFile);
    		
    		LibraryParser libParser = new LibraryParser(libScanner);
    		Symbol libParseSymbol = new Symbol(1);
    		// parse and generate AST
    		libParseSymbol = libParser.parse();
    		if(useExternalLib){
    			System.out.println("Parsed " + libPath + " successfully!");
    		}
    		ICClass libraryRoot = (ICClass)libParseSymbol.value;
        		
    		// parse IC source file
    		currentFile = srcPath;
    		FileReader txtFile = new FileReader(srcPath);
    		Lexer scanner = new Lexer(txtFile);    		
    		Parser parser = new Parser(scanner);
    		Symbol parseSymbol = new Symbol(1);
    		// parse and generate AST
    		parseSymbol = parser.parse();
    		System.out.println("Parsed " + srcPath + " successfully!");
    		System.out.println();
    		Program programRoot = (Program)parseSymbol.value;
    		
    		// add the library AST as a node to the program AST
    		programRoot.getClasses().add(0, libraryRoot);
    		// print the generated AST
    		if(printAst){
    			PrettyPrinter printer = new PrettyPrinter(srcPath);
    			System.out.println(programRoot.accept(printer));
    			System.out.println();
    		}
    		
    		// build symbol table
    		BuildSymbolTables symTableBuilder = new BuildSymbolTables();
    		GlobalSymbolTable globalSymbolTable = (GlobalSymbolTable)programRoot.accept(symTableBuilder);
    		// perform semantic checks
    		StructureChecksVisitor checkStructure = new StructureChecksVisitor();
    		programRoot.accept(checkStructure);
    		globalSymbolTable.accept(checkStructure);
    		TypeCheckVisitor typeChecker = new TypeCheckVisitor();
    		programRoot.accept(typeChecker);
    		ReturnStatementVisitor returnChecker = new ReturnStatementVisitor();
    		programRoot.accept(returnChecker);
    		//VariableInitializeVisitor varInitCheck = new VariableInitializeVisitor();
    		//programRoot.accept(varInitCheck);
    		
    		if(printSymTab){
    			PrettyPrinter symTabPrint = new PrettyPrinter(srcPath);
    			System.out.println(globalSymbolTable.accept(symTabPrint));
    			TypeTable.setFileName(srcPath);
    			System.out.println(TypeTable.toTypeTableString());
    		}
    		
    		SethiUllmanWeightVisitor regWeightCalculate = new SethiUllmanWeightVisitor();
    		programRoot.accept(regWeightCalculate);
    		
    		TranslateVisitor makeLir = new OptimizedTranslateVisitor();
    		LirBlock programLir = programRoot.accept(makeLir, 1);
    		if (printLir) {
    			String lirPath = srcPath.substring(0, srcPath.length() - 2) + "lir";
    			FileWriter writeLir = new FileWriter(lirPath);
    			writeLir.write(programLir.getLirCode().toString());
    			writeLir.close();
    		}
    	}
    	// Catch lexical Errors and print the line and the value of the token
    	catch (LexicalError e) {
			System.out.println(e.getMessage());
			System.out.println("Lexical error in file: " + currentFile);
			System.exit(0);
		}
    	// Handle syntax errors
    	catch (SyntaxError e) {
			System.out.println(e.getMessage());
			System.out.println("Syntax error(s) in file: " + currentFile);
			System.exit(0);
		}
    	// If the input file is not found, print an error to the user
    	catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage() + ". Check file path.");
			System.exit(0);
		}
    	// Catch other I/O errors
    	catch (IOException e) {
    		System.out.println("Error: I/O error: " + e.getMessage());
    		System.exit(0);
    	}
    	catch (Exception e) {
    		System.out.println("Error: "+ e.getClass() + " " + e.getMessage());
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
	
	private static void usage(){
		System.out.println("Usage: java IC.Compiler <input-filename> [ -L<library-path> ] [ -print-ast ] [ -dump-symtab ] [ -print-lir ]\n");
		System.exit(0);
	}
}
