package IC.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.lang.model.type.NullType;

import IC.DataTypes;
import IC.SemanticError;
import IC.AST.ICClass;
import IC.AST.Method;
//import IC.AST.PrettyPrinter;
import IC.AST.PrimitiveType;
import IC.AST.Type;

public class TypeTable {
	
	private static Map<String, ICClass> UserTypes=new HashMap<String, ICClass>();
	private static Map<String,Type> primitiveTypes=new HashMap<String, Type>();
	private static Map<String,MethodType> methodTypes=new HashMap<String, MethodType>();
	private static Map<String, Type> arrayTypes=new HashMap<String,Type>();
	
	private static String filename = null;
	private static int counter= 1;
	
	static
	{	
		Type intType=new PrimitiveType(0,DataTypes.INT);
		intType.setTypeTableID(counter++);
		primitiveTypes.put("int",intType);
		Type boolType=new PrimitiveType(0,DataTypes.BOOLEAN);
		boolType.setTypeTableID(counter++);
		primitiveTypes.put("boolean", boolType);
		Type nulllType=new PrimitiveType(0,DataTypes.NULL);
		nulllType.setTypeTableID(counter++);
		primitiveTypes.put("null", nulllType);
		Type stringType=new PrimitiveType(0,DataTypes.STRING);
		stringType.setTypeTableID(counter++);
		primitiveTypes.put("string", stringType);
		Type voidType=new PrimitiveType(0,DataTypes.VOID);
		voidType.setTypeTableID(counter++);
		primitiveTypes.put("void", voidType);
	}
	
	public static void setFileName(String fileName){
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
	
	public static Type getType(Type atArr)
	{
		if (atArr.getDimension()>0)
		{
			String brackets="";
			String atName=atArr.getName();
			for(int i=0;i<atArr.getDimension();i++)
			{
				brackets+="[]";
			}
			atName+=brackets;
			if(!arrayTypes.containsKey(atName))
			{
				atArr.setTypeTableID(counter);
				arrayTypes.put(atName, atArr);
				TypeTable.counter++;
				return atArr;
			}
			else
			{
				return arrayTypes.get(atName);
			}
		}
		else
		{
			return primitiveTypes.get(atArr.getName());
		}
	}
	
	public static Type getType(String tName)
	{	
		return primitiveTypes.get(tName);
	}
	
	public static String toTypeTableString()
	{
		StringBuilder str = new StringBuilder();
		
		str.append("Type Table: "+TypeTable.filename);
		str.append("\n");
		
		List<Type> primitiveTypeslist=new ArrayList<Type>(TypeTable.primitiveTypes.values());
		Collections.sort(primitiveTypeslist,
				new Comparator<Type>(){
	        	public int compare(Type t1, Type t2) {
	        		return t1.getTypeTableID() - t2.getTypeTableID();
	        	}});
		
		for(Type t : primitiveTypeslist)
		{
			str.append("\t");
			str.append(t.getTypeTableID()+": Primitive type: "+ t.getName());
			str.append("\n");
		}
		
		List<ICClass> classlist=new ArrayList<ICClass>(TypeTable.UserTypes.values());
		Collections.sort(classlist,
				new Comparator<ICClass>(){
	        	public int compare(ICClass c1, ICClass c2) {
	        		return c1.getTypeTableID() - c2.getTypeTableID();
	        	}});
		
		for(ICClass c : classlist)
		{
			str.append("\t");
			str.append(c.getTypeTableID()+": Class: "+ c.getName());
			if(c.hasSuperClass())
			{
				str.append(", Superclass ID: "+ TypeTable.UserTypes.get(c.getSuperClassName()).getTypeTableID());
			}
			str.append("\n");
		}
		
		List<Type> arrayTypelist=new ArrayList<Type>(TypeTable.arrayTypes.values());
		Collections.sort(arrayTypelist,
				new Comparator<Type>(){
	        	public int compare(Type a1, Type a2) {
	        		return a1.getTypeTableID() - a2.getTypeTableID();
	        	}});
		
		
		for(Type at : arrayTypelist)
		{
			String brackets="";
			for(int i=0; i<at.getDimension();i++)
			{
				brackets+="[]";
			}
			str.append("\t");
			str.append(at.getTypeTableID()+": Array type: "+ at.getName()+brackets);
			str.append("\n");
		}
		
		List<MethodType> methodTypelist=new ArrayList<MethodType>(TypeTable.methodTypes.values());
		Collections.sort(methodTypelist,
				new Comparator<MethodType>(){
	        	public int compare(MethodType m1, MethodType m2) {
	        		return m1.getTypeTableID() - m2.getTypeTableID();
	        	}});
		
		for(MethodType mt : TypeTable.methodTypes.values())
		{
			str.append("\t");
			str.append(mt.getTypeTableID()+": Method type: "+ mt.toString());
			str.append("\n");
		}
		
		return str.toString();
	}
}
