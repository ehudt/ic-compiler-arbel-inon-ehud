package IC.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import IC.DataTypes;
import IC.SemanticError;
import IC.AST.ICClass;
import IC.AST.Method;
import IC.AST.PrimitiveType;
import IC.AST.Type;
import IC.AST.UserType;

public class TypeTable {
	
	private static Map<String, ICClass> 	userTypeClasses=new LinkedHashMap<String, ICClass>();
	private static Map<String, UserType>	userTypes = new LinkedHashMap<String, UserType>();
	private static Map<String, Type> 		primitiveTypes=new LinkedHashMap<String, Type>();
	private static Map<String, MethodType> 	methodTypes=new LinkedHashMap<String, MethodType>();
	private static Map<String, Type> 		arrayTypes=new LinkedHashMap<String,Type>();
	
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
		if(userTypeClasses.containsKey(c.getName()))
		{
			throw new SemanticError(c.getLine(), "A class with the name "+c.getName()+" already exists");
		}
		if(c.hasSuperClass())
		{
			if(!userTypeClasses.containsKey(c.getSuperClassName()))
			{
				throw new SemanticError(c.getLine(), "super class " + c.getSuperClassName() + " for this class wasn't defined");
			}
		}
		c.setTypeTableID(TypeTable.counter);
		TypeTable.counter++;
		userTypeClasses.put(c.getName(), c);
		userTypes.put(c.getName(), new UserType(c.getLine(), c.getName()));
	}
	
	public static ICClass getUserTypeByName(String className) throws SemanticError
	{
		ICClass cl;
		if ((cl= userTypeClasses.get(className))==null)
		{
			throw new SemanticError("requested class(" + className + ") does not exist");
		}
		else
		{
			return cl;
		}
	}
	
	public static MethodType getType(Method m) {
		return getType(m, true);
	}
	
	public static MethodType getType(Method m, boolean insert)
	{
		MethodType metType=new MethodType(m,TypeTable.counter);
		
		if(!methodTypes.containsKey(metType.toString()))
		{
			if (insert) {
				methodTypes.put(metType.toString(), metType);
				TypeTable.counter++;
				return metType;
			} else {
				typeError(m.getLine(), "no such type: " + m.getName());
				return null;
			}
		}
		else
		{
			return methodTypes.get(metType.toString());
		}
	}
	
	public static Type getType(Type atArr) {
		return getType(atArr, true);
	}
	
	public static Type getType(Type atArr, boolean insert)
	{
		if (atArr.getDimension()>0)
		{
			//String brackets="";
			String atName=atArr.getName();
			int dim=atArr.getDimension();
			for(int i=1;i<=dim;i++)
			{
				atName+="[]";
				if(!arrayTypes.containsKey(atName))
				{
					if (insert) {
						Type cloned = atArr.clone(i);
						cloned.setTypeTableID(counter);
						arrayTypes.put(atName, cloned);
						TypeTable.counter++;
					} else {
						typeError(atArr.getLine(), "no such type: " + atName);
						//return null;
					}
				}
			}
			return arrayTypes.get(atName);
		}
		else
		{
			if (primitiveTypes.containsKey(atArr.toString())) {
				return primitiveTypes.get(atArr.toString());
			} else if(userTypes.containsKey(atArr.toString())){
				return userTypes.get(atArr.toString());
			} else {
				return null;
			}
			
		}
	}
	
	private static void typeError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);		
	}

	public static Type getType(String tName)
	{	
		if (primitiveTypes.containsKey(tName)) {
			return primitiveTypes.get(tName);
		} else if (arrayTypes.containsKey(tName)) {
			return arrayTypes.get(tName);
		} else if (userTypes.containsKey(tName)) {
			return userTypes.get(tName);
		} else return null;
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
		
		List<ICClass> classlist=new ArrayList<ICClass>(TypeTable.userTypeClasses.values());
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
				str.append(", Superclass ID: "+ TypeTable.userTypeClasses.get(c.getSuperClassName()).getTypeTableID());
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
/*
	public static MethodType getMethodType(String currentMethodName) {
		return methodTypes.get(currentMethodName);
	}*/
}
