package IC.Types;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import IC.DataTypes;
import IC.SemanticError;
import IC.AST.ICClass;
import IC.AST.Method;
import IC.AST.PrettyPrinter;
import IC.AST.PrimitiveType;
import IC.AST.Type;

public class TypeTable {
	
	private static Map<String, ICClass> UserTypes=new HashMap<String, ICClass>();
	private static Map<String,Type> primitiveTypes=new HashMap<String, Type>();
	private static Map<String,MethodType> methodTypes=new HashMap<String, MethodType>();
	private static Map<String, ArrayType> arrayTypes=new HashMap<String,ArrayType>();
	
	private static String filename = null;
	private static int counter= 0;
	
	public static void addPrimitiveTypes2Table(String fileName)
	{	
		primitiveTypes.put("int",new PrimitiveType(1,DataTypes.INT));
		primitiveTypes.put("boolean", new PrimitiveType(2,DataTypes.BOOLEAN));
		primitiveTypes.put("null", new PrimitiveType(3,DataTypes.NULL));
		primitiveTypes.put("string", new PrimitiveType(4,DataTypes.STRING));
		primitiveTypes.put("void", new PrimitiveType(5,DataTypes.VOID));
		TypeTable.counter = 6;
		TypeTable.filename = fileName;
	}
	
	public static void addUserType(ICClass c) throws SemanticError
	{
		//TODO: fix error messages
		if(UserTypes.containsKey(c.getName()))
		{
			throw new SemanticError("semantic error at line "+c.getLine()+": A class with the name - "+c.getName()+" already defined");
		}
		if(c.hasSuperClass())
		{
			if(!UserTypes.containsKey(c.getSuperClassName()))
			{
				throw new SemanticError("semantic error at line "+c.getLine()+": Super class ,"+c.getSuperClassName()+", for this class wasn't defined");
			}
		}
		c.setTypeTableID(TypeTable.counter);
		TypeTable.counter++;
		UserTypes.put(c.getName(), c);
	}
	
	public static ICClass getUserTypeByName(String className) throws SemanticError
	{
		ICClass cl;
		if ((cl= UserTypes.get(className))==null)
		{
			throw new SemanticError("semantic error: Requested Class("+className+") not Exists");
		}
		else
		{
			return cl;
		}
	}
	
	public static MethodType getType(Method m)
	{
		MethodType metType=new MethodType(m,TypeTable.counter);
		
		if(!methodTypes.containsKey(metType.toString()))
		{
			methodTypes.put(metType.toString(), metType);
			TypeTable.counter++;
			return metType;
		}
		else
		{
			return methodTypes.get(metType.toString());
		}
	}
	
	public static ArrayType getType(Type atArr)
	{
		ArrayType at = new ArrayType(atArr, atArr.getDimension(),TypeTable.counter);
		if(!arrayTypes.containsKey(at.getName()))
		{
			arrayTypes.put(at.getName(), at);
			TypeTable.counter++;
			return at;
		}
		else
		{
			return arrayTypes.get(at.getName());
		}
	}
	
	public static Type getType(String tName)
	{	
		if(!primitiveTypes.containsKey(tName))
		{
			return primitiveTypes.get(tName);
		}
		else
		{
			return null;
		}
	}
	
	/*
	public static boolean inTypeTable(String typeName)
	{
		boolean bRes=false;
		bRes=primitiveTypes.containsKey(typeName);
		bRes=UserTypes.containsKey(typeName);
		bRes=methodTypes.containsKey(typeName);
		return bRes;
	}
	*/
	public static String toTypeTableString()
	{
		StringBuilder str = new StringBuilder();
		
		str.append("Type Table: "+TypeTable.filename);
		str.append("\n");
		
		for(Type t : TypeTable.primitiveTypes.values())
		{
			str.append("\t");
			str.append(t.getLine()+": Primitive type: "+ t.getName());
			str.append("\n");
		}
		
		for(ICClass c : TypeTable.UserTypes.values())
		{
			str.append("\t");
			str.append(c.getTypeTableID()+": Class: "+ c.getName());
			if(c.hasSuperClass())
			{
				str.append(", Superclass ID: "+ TypeTable.UserTypes.get(c.getSuperClassName()).getTypeTableID());
			}
			str.append("\n");
		}
		
		for(ArrayType at : TypeTable.arrayTypes.values())
		{
			str.append("\t");
			str.append(at.getTypeTableID()+": Array type: "+ at.getName());
			str.append("\n");
		}
		
		for(MethodType mt : TypeTable.methodTypes.values())
		{
			str.append("\t");
			str.append(mt.getTypeTableID()+": Method type: "+ mt.toString());
			str.append("\n");
		}
		
		return str.toString();
	}
}
