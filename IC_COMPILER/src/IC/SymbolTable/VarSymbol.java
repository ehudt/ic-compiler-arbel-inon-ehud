package IC.SymbolTable;

import IC.AST.Formal;
import IC.AST.LocalVariable;
import IC.AST.Type;
import IC.Types.TypeTable;

public class VarSymbol extends Symbol {

	Type type;
	boolean isParam;
	int initStep = Integer.MAX_VALUE;
	
	public int getInitStep() {
		return initStep;
	}
	
	public void setInitStep(int step) {
		initStep = step;
	}
	
	//constructor
	
	public VarSymbol(LocalVariable lv)
	{
		super(lv.getName(),Kind.VARIABLE, lv.getLine());
		this.isParam=false;
		this.type = TypeTable.getType(lv.getType());
	}
	
	public VarSymbol(Formal f)
	{
		super(f.getName(),Kind.VARIABLE, f.getLine());
		this.isParam=true;
		this.type = TypeTable.getType(f.getType());
	}
	//getters
	
	public boolean isParam()
	{
		return this.isParam;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append(isParam() ? "Parameter: " : "Local variable: ");
		str.append(getType());
		str.append(" ");
		str.append(getID());
		return str.toString();
	}
	
	public boolean equals(Object other) {
		/*if(!(other instanceof VarSymbol)) return false;
		VarSymbol otherSymbol = (VarSymbol)other;
		boolean retVal = this.id.equals(otherSymbol.id) &&
				this.kind == otherSymbol.kind &&
				this.line == otherSymbol.line &&
				this.type.equals(otherSymbol.type) &&
				this.isParam == otherSymbol.isParam;
		return retVal;*/
		return this == other;
	}
}
