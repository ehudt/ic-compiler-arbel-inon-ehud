package IC.LIR;

public class OptimizedLirBlock extends LirBlock {
	
	private OptLirBlockFlag lirBlockFlag;
	
	public OptimizedLirBlock(StringBuilder lirCode, Integer targetReg,OptLirBlockFlag olb){
		super(lirCode, targetReg);
		this.lirBlockFlag=olb;
	}
	
	public OptimizedLirBlock(StringBuilder lirCode, Integer targetReg, LirValueType valueType,OptLirBlockFlag olb) {
		super(lirCode, targetReg, valueType);
		this.lirBlockFlag=olb;
	}
	
	public OptLirBlockFlag getLirBlockFlag()
	{
		return this.lirBlockFlag;
	}
	
	public void setLirBlockFlag(OptLirBlockFlag olb)
	{
		this.lirBlockFlag=olb;
	}

}
