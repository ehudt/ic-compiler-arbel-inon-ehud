package IC.AST;

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

}
