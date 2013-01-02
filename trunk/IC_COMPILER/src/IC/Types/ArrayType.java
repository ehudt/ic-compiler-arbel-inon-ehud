package IC.Types;

import IC.AST.Type;

public class ArrayType{

	private Type varType;
	private String name;
	private int dimension;
	private int typeTableID;
	
	public ArrayType (Type vType,int dim,int id)
	{
		String brackets="";
		this.dimension=dim;
		for(int i=0;i<dim;i++)
		{
			brackets+="[]";
		}
		this.name = vType.getName()+brackets;		
		this.varType = vType;
		this.typeTableID = id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getDimensionO()
	{
		return dimension;
	}
	
	public Type getVarType()
	{
		return varType;
	}
	
	public int getTypeTableID()
	{
		return typeTableID;
	}
	/*
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("Array type: ");
		str.append(this.name);
		return str.toString();
	}
	*/
	
}
