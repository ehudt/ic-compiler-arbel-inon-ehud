package IC.LIR;

public class LirBlock {
	private StringBuilder lirCode = new StringBuilder();
	private String targetRegister;
	private LirValueType valueType;
	
	public LirBlock(String expression, LirValueType valueType, Integer targetReg){
		this.lirCode.append(valueType.getMoveCommand());
		this.lirCode.append(expression);
		this.lirCode.append(",R");
		this.lirCode.append(targetReg);
		this.targetRegister = targetReg.toString();
	}
	
	
}
