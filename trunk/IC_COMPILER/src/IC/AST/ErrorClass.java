package IC.AST;

import java.util.ArrayList;

public class ErrorClass extends ICClass {
	
	public Object visit(Visitor visitor){
		return visitor.visit(this);
	}

	public ErrorClass(int line) {
		super(line, "", new ArrayList<Field>(), new ArrayList<Method>());
	}

}
