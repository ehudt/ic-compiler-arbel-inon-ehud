package IC.LIR;

public class OptimizedLirBlock extends LirBlock {
	
	public OptimizedLirBlock(StringBuilder lirCode, Integer targetReg){
		super(lirCode, targetReg);
	}
	
	public OptimizedLirBlock(StringBuilder lirCode, Integer targetReg, LirValueType valueType) {
		super(lirCode, targetReg, valueType);
	}

}
