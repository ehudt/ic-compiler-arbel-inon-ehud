package IC.AST;

import IC.LIR.LirBlock;
import IC.LIR.PropagatingVisitor;

/**
 * If statement AST node.
 * 
 * @author Tovi Almozlino
 */
public class If extends Statement {

	private Expression condition;

	private Statement operation;

	private Statement elseOperation = null;

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
	 * Constructs an If statement node.
	 * 
	 * @param condition
	 *            Condition of the If statement.
	 * @param operation
	 *            Operation to perform if condition is true.
	 * @param elseOperation
	 *            Operation to perform if condition is false.
	 */
	public If(Expression condition, Statement operation, Statement elseOperation) {
		this(condition, operation);
		this.elseOperation = elseOperation;
	}

	/**
	 * Constructs an If statement node, without an Else operation.
	 * 
	 * @param condition
	 *            Condition of the If statement.
	 * @param operation
	 *            Operation to perform if condition is true.
	 */
	public If(Expression condition, Statement operation) {
		super(condition.getLine());
		this.condition = condition;
		this.operation = operation;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getOperation() {
		return operation;
	}

	public boolean hasElse() {
		return (elseOperation != null);
	}

	public Statement getElseOperation() {
		return elseOperation;
	}

}
