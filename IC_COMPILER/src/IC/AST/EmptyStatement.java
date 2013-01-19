package IC.AST;

import IC.LIR.LirBlock;
import IC.LIR.PropagatingVisitor;

/**
 * A class for empty statements. Used for error recovery
 * @author ehud
 *
 */
public class EmptyStatement extends Statement {

	public EmptyStatement(int line){
		super(line);
	}
	@Override
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

}
