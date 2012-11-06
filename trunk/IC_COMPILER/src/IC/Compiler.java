package IC;

import java.io.FileReader;

//import com.sun.java_cup.internal.runtime.Symbol;
import IC.Parser.Lexer;
import IC.Parser.LexicalError;

public class Compiler
{
    public static void main(String[] args) throws Exception
    {
    	IC.Parser.Token currToken;
    	try {
    		FileReader txtFile = new FileReader(args[0]);
    		Lexer scanner = new Lexer(txtFile);
    	
    		do{
    			currToken = scanner.next_token();
    			System.out.print(currToken.getLine()+": "+ currToken.getName());
    			if (currToken.getValue() != "")
    				System.out.println("(" + currToken.getValue() + ")");
    			else 
    				System.out.println("");
    		}
    		while (currToken.getId() != IC.Parser.sym.EOF);
    		System.out.println(currToken.getLine() +": "+ currToken.getName());
    	}
    	
    	catch (LexicalError e) {
			System.out.println("ERROR" + e.toString());
		}
    	
    	catch (Exception e) {
			throw e;
		}
    	
    }
}
