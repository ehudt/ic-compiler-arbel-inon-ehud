package IC.AST;

import IC.LIR.LirBlock;
import IC.LIR.PropagatingVisitor;

/**
 * Return statement AST node.
 * 
 * @author Tovi Almozlino
 */
public class Return extends Statement {

	private Expression value = null;

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
	 * Constructs a new return statement node, with no return value.
	 * 
	 * @param line
	 *            Line number of return statement.
	 */
	public Return(int line) {
		super(line);
	}

	/**
	 * Constructs a new return statement node.
	 * 
	 * @param line
	 *            Line number of return statement.
	 * @param value
	 *            Return value.
	 */
	public Return(int line, Expression value) {
		this(line);
		this.value = value;
	}

	public boolean hasValue() {
		return (value != null);
	}

	public Expression getValue() {
		return value;
	}

}
