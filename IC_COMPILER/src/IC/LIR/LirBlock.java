package IC.LIR;

public class LirBlock {
	private StringBuilder lirCode;
	private Integer targetRegister;
	private LirValueType valueType;
	
	public LirBlock(StringBuilder lirCode, Integer targetReg){
		this.targetRegister = targetReg;
		this.lirCode = lirCode;
	}
	
	
}
