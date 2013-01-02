package IC.SymbolTable;

import IC.SemanticError;
import IC.AST.ICClass;
import IC.Types.TypeTable;

public class ClassSymbol extends Symbol {

	//private ICClass pclass;
	
	public ClassSymbol(ICClass cl) throws SemanticError
	{
		super(cl.getName(),Kind.CLASS, cl.getLine());
		TypeTable.addUserType(cl);
	}

	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("Class: ");
		str.append(getID());
		return str.toString();
	}
}
