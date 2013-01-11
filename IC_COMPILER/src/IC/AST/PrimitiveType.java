package IC.AST;

import IC.DataTypes;

/**
 * Primitive data type AST node.
 * 
 * @author Tovi Almozlino
 */
public class PrimitiveType extends Type {

	private DataTypes type;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new primitive data type node.
	 * 
	 * @param line
	 *            Line number of type declaration.
	 * @param type
	 *            Specific primitive data type.
	 */
	public PrimitiveType(int line, DataTypes type) {
		super(line);
		this.type = type;
	}

	public String getName() {
		return type.getDescription();
	}
	
	/**
	 * This method allows to clone an object with another primitive type but with the requested dimesnion
	 */
	public Type clone(int newDimension){
		Type cloned = new PrimitiveType(getLine(), type);
		for(int i = 0; i < newDimension; i++){
			cloned.incrementDimension();
		}
		return cloned;
	}
	
	/**
	 * A method to check if a primitive type is subType of another Type
	 * i.e if the checked type is null it is subtype of any userType or type with dimension greater than 0
	 * Otherwise check if the references match. It is possible, since all types are taken from the Type Table. 
	 */
	@Override
	public boolean subTypeOf(Type otherType) {
		if (this.type == DataTypes.NULL && (otherType instanceof UserType || otherType.getDimension() > 0)) {
			return true;
		}
		
		return this == otherType;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof PrimitiveType) && 
				this.type == ((PrimitiveType)other).type &&
				this.dimension == ((PrimitiveType)other).dimension;
	}
}