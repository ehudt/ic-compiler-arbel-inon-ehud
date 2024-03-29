package IC.AST;

import java.util.List;

import IC.SemanticError;
import IC.LIR.LirBlock;
import IC.LIR.PropagatingVisitor;
import IC.Types.TypeTable;

/**
 * Class declaration AST node.
 * 
 * @author Tovi Almozlino
 */
public class ICClass extends ASTNode {

	private String name;

	private String superClassName = null;

	private List<Field> fields;

	private List<Method> methods;
	
	private int typeTableID;
	
	public Object accept(Visitor visitor){
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
	 * Constructs a new class node.
	 * 
	 * @param line
	 *            Line number of class declaration.
	 * @param name
	 *            Class identifier name.
	 * @param fields
	 *            List of all fields in the class.
	 * @param methods
	 *            List of all methods in the class.
	 */
	public ICClass(int line, String name, List<Field> fields,
			List<Method> methods) {
		super(line);
		this.name = name;
		this.fields = fields;
		this.methods = methods;
	}

	/**
	 * Constructs a new class node, with a superclass.
	 * 
	 * @param line
	 *            Line number of class declaration.
	 * @param name
	 *            Class identifier name.
	 * @param superClassName
	 *            Superclass identifier name.
	 * @param fields
	 *            List of all fields in the class.
	 * @param methods
	 *            List of all methods in the class.
	 */
	public ICClass(int line, String name, String superClassName,
			List<Field> fields, List<Method> methods) {
		this(line, name, fields, methods);
		this.superClassName = superClassName;
	}

	public String getName() {
		return name;
	}

	public boolean hasSuperClass() {
		return (superClassName != null);
	}

	public String getSuperClassName() {
		return superClassName;
	}

	public List<Field> getFields() {
		return fields;
	}

	public List<Method> getMethods() {
		return methods;
	}
	
	//added for TypeTable
	public void setTypeTableID(int id)
	{
		typeTableID =id;
	}
	
	public int getTypeTableID()
	{
		return typeTableID;
	}
	
	public boolean subTypeOf(ICClass c)
	{
		if (this == c) // case A <= A
		{
			return true;
		}
		if(this.hasSuperClass())
		{
			try{// case A<=B B<=C -> A<=C
			return TypeTable.getUserTypeByName(this.superClassName).subTypeOf(c);
			}
			catch (SemanticError se)
			{
				return false;
			}
			
		}// ! A <= B
		else
		{
			return false;
		}
	}

}
