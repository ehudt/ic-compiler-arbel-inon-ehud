package IC;

import java.io.FileReader;

import com.sun.java_cup.internal.runtime.Symbol;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import IC.Parser.Lexer;
import IC.Parser.LexicalError;

public class Compiler
{
    public static void main(String[] args) throws LexicalError
    {
    	IC.Parser.Token currToken;
    	try {
    		FileReader txtFile = new FileReader(args[0]);
    		Lexer scanner = new Lexer(txtFile);
    	
    		do{
    			currToken = scanner.next_token();
    			System.out.println(currToken.getId());
    			System.out.print(currToken.getLine()+": "+ currToken.getName());
    			if (currToken.getValue() != "")
    				System.out.println("(" + currToken.getValue() + ")");
    			else 
    				System.out.println("");
    		}
    		while (currToken.getId() != sym.EOF);
    		System.out.println(currToken.getLine() +": "+ currToken.getName());
    	}
    	
    	catch (Exception e) {
			System.out.println("ERROR" + e.toString());
		}
    	
    }
}
