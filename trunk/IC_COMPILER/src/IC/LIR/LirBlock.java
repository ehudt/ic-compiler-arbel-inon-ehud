package IC.LIR;

/**
 * A class representing a block of LIR code corresponding to 
 * a subtree of the AST. Each code block contains, apart from the 
 * actual LIR instructions, the target register of the calculation
 * and the type of value.
 * @author ehud
 *
 */
public class LirBlock {
	private StringBuilder lirCode;
	private Integer targetRegister;
	private LirValueType valueType = LirValueType.DONT_CARE;
	
	public LirBlock(StringBuilder lirCode, Integer targetReg){
		this.targetRegister = targetReg;
		this.lirCode = lirCode;
	}
	
	public LirBlock(StringBuilder lirCode, Integer targetReg, LirValueType valueType) {
		this(lirCode, targetReg);
		this.setValueType(valueType);
	}
	
	public StringBuilder getLirCode(){
		return lirCode;
	}
	
	public Integer getTargetRegister(){
		return targetRegister;
	}

	public LirValueType getValueType() {
		return valueType;
	}

	public void setValueType(LirValueType valueType) {
		this.valueType = valueType;
	}
	
	/*private OptLirBlockFlag lirBlockFlag = OptLirBlockFlag.DEFAULT;
	
	public LirBlock(StringBuilder lirCode, Integer targetReg,OptLirBlockFlag olb){
		this(lirCode, targetReg);
		this.lirBlockFlag=olb;
	}
	
	public LirBlock(StringBuilder lirCode, Integer targetReg, LirValueType valueType,OptLirBlockFlag olb) {
		this(lirCode, targetReg, valueType);
		this.lirBlockFlag=olb;
	}
	
	public OptLirBlockFlag getLirBlockFlag()
	{
		return this.lirBlockFlag;
	}
	
	public void setLirBlockFlag(OptLirBlockFlag olb)
	{
		this.lirBlockFlag=olb;
	}*/

}
