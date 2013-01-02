package IC.Types;

import IC.AST.Formal;
import IC.AST.Method;
import IC.AST.Type;
import IC.AST.PrettyPrinter;

import java.util.ArrayList;
import java.util.List;

public class MethodType {
	String name;
	Type returnType;
	List<Type> paramTypes = new ArrayList<Type>();
	int typeTableID;
	
	public MethodType(Method method,int id){
		name = method.getName();
		returnType = method.getType();
		for(Formal formal : method.getFormals()){
			paramTypes.add(formal.getType());
		}
		typeTableID = id;
	}
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append(PrettyPrinter.printListNicely(paramTypes));
		str.append(" -> ");
		str.append(returnType);
		str.append("}");
		return str.toString();
	}
	
	public int getTypeTableID()
	{
		return typeTableID;
	}
	
	public Type getReturnType(){
		return returnType;
	}
	
	public List<Type> getParamTypes(){
		return paramTypes;
	}
	

	@Override 
	public boolean equals(Object o) {
		if (!(o instanceof MethodType))
				return false;
		
		MethodType m = (MethodType)o;
		if ((this.getParamTypes().toString().equals(m.getParamTypes())) && 
				this.getReturnType().toString().equals(m.getReturnType()))
		{
			return true;
		}
		return false;
	}
}
