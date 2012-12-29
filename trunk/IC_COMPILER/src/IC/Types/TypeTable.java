package IC.Types;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

import IC.DataTypes;
import IC.AST.ICClass;
import IC.AST.PrimitiveType;
import IC.AST.Type;

public class TypeTable {
	
	private Map<String, ICClass> classTypes;
	private Map<String,Type> primitiveTypes;
	
	public TypeTable()
	{
		classTypes=new HashMap<>();
		primitiveTypes=new HashMap<>();
		
		primitiveTypes.put("int",new PrimitiveType(0,DataTypes.INT));
		primitiveTypes.put("boolean", new PrimitiveType(0,DataTypes.BOOLEAN));
		primitiveTypes.put("string", new PrimitiveType(0,DataTypes.STRING));
		primitiveTypes.put("void", new PrimitiveType(0,DataTypes.VOID));
		
	}
	
	public void addClassType(ICClass c)
	{
		classTypes.put(c.getName(), c);
	}
	
	public boolean inTypeTable(String typeName)
	{
		boolean bRes=false;
		bRes=primitiveTypes.containsKey(typeName);
		bRes=primitiveTypes.containsKey(typeName);
		return bRes;
	}
	
}
