package IC.AST;

import IC.LIR.LirBlock;
import IC.LIR.PropagatingVisitor;

/**
 * Array length expression AST node.
 * 
 * @author Tovi Almozlino
 */
public class Length extends Expression {

	private Expression array;

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
	 * Constructs a new array length expression node.
	 * 
	 * @param array
	 *            Expression representing an array.
	 */
	public Length(Expression array) {
		super(array.getLine());
		this.array = array;
	}

	public Expression getArray() {
		return array;
	}

}
