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
	
	public MethodType(Method method){
		name = method.getName();
		returnType = method.getType();
		for(Formal formal : method.getFormals()){
			paramTypes.add(formal.getType());
		}
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
}
