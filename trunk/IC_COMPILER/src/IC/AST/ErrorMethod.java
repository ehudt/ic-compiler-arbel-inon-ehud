package IC.AST;

import java.util.ArrayList;
import java.util.List;

import IC.DataTypes;

public class ErrorMethod extends Method {
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new error method node.
	 * @param line
	 *            line number.
	 */
	public ErrorMethod(int line) {
		super(new PrimitiveType(line,DataTypes.VOID), "Error", new ArrayList<Formal>(), new ArrayList<Statement>());
	}
}
