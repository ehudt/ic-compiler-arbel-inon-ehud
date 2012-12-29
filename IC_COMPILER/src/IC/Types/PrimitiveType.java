package IC.Types;

import IC.DataTypes;

public class PrimitiveType extends Type {
	private DataTypes type;
	
	public PrimitiveType(IC.AST.Type type){
		super(type.getDescription());
		this.setType(type);
	}

	public DataTypes getType() {
		return type;
	}

	private void setType(DataTypes type) {
		this.type = type;
	}
}
