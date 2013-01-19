package IC.AST;

import java.util.ArrayList;
import IC.DataTypes;
import IC.LIR.LirBlock;
import IC.LIR.PropagatingVisitor;
/**
 * a class for Erroneous methods. Used for error recovery
 * @author ehud
 *
 */
public class ErrorMethod extends Method {
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	
	/**
	 * Implementation of propagating visitor
	 * @param visitor
	 * @param targetReg
	 * @return
	 */
	public LirBlock accept(PropagatingVisitor<LirBlock, Integer> visitor, Integer targetReg){
		return visitor.visit(this, targetReg);
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
