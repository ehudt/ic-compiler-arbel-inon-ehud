package IC.Types;

import IC.AST.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodType {
	String name;
	Type returnType;
	List<Type> paramTypes = new ArrayList<Type>();
	
	public MethodType(Method method){
		name = method.getName();
		
	}
}
