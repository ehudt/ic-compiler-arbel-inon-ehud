package IC.AST;

import java.util.LinkedList;
import java.util.List;

import IC.LIR.LirBlock;
import IC.LIR.PropagatingVisitor;

/**
 * Class FieldMethodList reperesents a class' body, consisting of method declarations
 * and field declarations.
 */
public class FieldMethodList extends ASTNode {

	/**
	 * List of the class' methods
	 */
	private List<Method> methods;
	/**
	 * List of the class' fields.
	 */
	private List<Field> fields;
	
	/**
	 * class constructor.
	 * @param line	The line in which the field-method list begins
	 */
	public FieldMethodList(int line) {
		super(line);
		methods = new LinkedList<Method>();
		fields = new LinkedList<Field>();
	}

	/**
	 * Visitor pattern implementation
	 */
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
	
	/**
	 * add a method to the list
	 * @param m	A method declaration to be added to the list
	 */
	public void add(Method m){
		methods.add(m);
	}
	
	/**
	 * add a field to the list
	 * @param f	A field decalaration to be added to the list
	 */
	public void add(Field f){
		fields.add(f);
	}
	
	/**
	 * A getter for the object's methods
	 * @return 	a list of all methods
	 */
	public List<Method> getMethodList(){
		return methods;
	}
	
	/**
	 * A getter for the object's fields
	 * @return	a list of all the fields decalared in the object
	 */
	public List<Field> getFieldList(){
		return fields;
	}

}
