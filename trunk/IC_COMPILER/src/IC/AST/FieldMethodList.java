package IC.AST;

import java.util.LinkedList;
import java.util.List;

public class FieldMethodList extends ASTNode {

	private List<Method> methods;
	private List<Field> fields;
	
	protected FieldMethodList(int line) {
		super(line);
		methods = new LinkedList<Method>();
		fields = new LinkedList<Field>();
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	
	public void add(Method m){
		methods.add(m);
	}
	
	public void add(Field f){
		fields.add(f);
	}
	
	public List<Method> getMethodList(){
		return methods;
	}
	
	public List<Field> getFieldList(){
		return fields;
	}

}
