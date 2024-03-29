package IC.AST;

import java.util.List;

import IC.LIR.LirBlock;
import IC.LIR.PropagatingVisitor;

/**
 * Virtual method call AST node.
 * 
 * @author Tovi Almozlino
 */
public class VirtualCall extends Call {

	private Expression location = null;

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
	 * Constructs a new virtual method call node.
	 * 
	 * @param line
	 *            Line number of call.
	 * @param name
	 *            Name of method.
	 * @param arguments
	 *            List of all method arguments.
	 */
	public VirtualCall(int line, String name, List<Expression> arguments) {
		super(line, name, arguments);
	}

	/**
	 * Constructs a new virtual method call node, for an external location.
	 * 
	 * @param line
	 *            Line number of call.
	 * @param location
	 *            Location of method.
	 * @param name
	 *            Name of method.
	 * @param arguments
	 *            List of all method arguments.
	 */
	public VirtualCall(int line, Expression location, String name,
			List<Expression> arguments) {
		this(line, name, arguments);
		this.location = location;
	}

	public boolean isExternal() {
		return (location != null);
	}

	public Expression getLocation() {
		return location;
	}

}
