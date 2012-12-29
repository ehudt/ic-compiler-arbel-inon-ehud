package IC.Types;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import IC.DataTypes;
import IC.SemanticError;
import IC.AST.ICClass;
import IC.AST.PrimitiveType;
import IC.AST.Type;

public class TypeTable {
	
	private static Map<String, ICClass> UserTypes=new HashMap<>();
	private static Map<String,Type> primitiveTypes=new HashMap<>();
	private static Map<String,MethodType> methodTypes=new HashMap<>();
	
	
	
	public static void addPrimitiveTypes2Table()
	{
		
		primitiveTypes.put("int",new PrimitiveType(0,DataTypes.INT));
		primitiveTypes.put("boolean", new PrimitiveType(0,DataTypes.BOOLEAN));
		primitiveTypes.put("string", new PrimitiveType(0,DataTypes.STRING));
		primitiveTypes.put("void", new PrimitiveType(0,DataTypes.VOID));
		
	}
	
	public static void addUserType(ICClass c) throws SemanticError
	{
		//TODO: fix error messages
		if(UserTypes.containsKey(c.getName()))
		{
			throw new SemanticError("A class with the same name already defined");
		}
		if(c.hasSuperClass())
		{
			if(!UserTypes.containsKey(c.getSuperClassName()))
			{
				throw new SemanticError("Super class for this class wasn't defined");
			}
		}
		UserTypes.put(c.getName(), c);
	}
	
	public static void addMethodType(IC.AST.Method m)
	{
		if(!methodTypes.containsKey(m.getName()))
		{
			methodTypes.put(m.getName(), new MethodType(m));
		}
	}
	
	public static boolean inTypeTable(String typeName)
	{
		boolean bRes=false;
		bRes=primitiveTypes.containsKey(typeName);
		bRes=UserTypes.containsKey(typeName);
		bRes=methodTypes.containsKey(typeName);
		return bRes;
	}
	
}
