package IC.LIR;

public enum LirValueType {
    LITERAL,
    REGISTER,
    ARRAY_LOCATION,
    FIELD;

	public String getMoveCommand() {
		switch(this){
			case LITERAL:
				return "MOVE ";
				
		
		}
		return null;
	}
}

