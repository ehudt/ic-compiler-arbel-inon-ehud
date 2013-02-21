package IC.LIR;

import IC.AST.ExpressionBlock;
import IC.AST.Literal;

public class OptimizedTranslateVisitor extends TranslateVisitor {
	 
	
	@Override
	public LirBlock visit(Literal literal, Integer targetReg) {
		String literalString = "";
		switch(literal.getType()){
			case STRING:
				String str = literal.getValue().toString();
				if(!stringLiterals.containsKey(str))
					stringLiterals.put(str, "str"+(stringLiterals.size()+1));
				literalString = stringLiterals.get(str);
				break;
				
			case INTEGER:
					literalString = literal.getValue().toString();
				break;
				
			case TRUE:
					literalString = "1";
				break;
				
			case FALSE:
					literalString = "0";
				break;
				
			case NULL:
					literalString = "0";
				break;
		}
		return new OptimizedLirBlock(new StringBuilder(literalString), targetReg, OptLirBlockFlag.LITERAL);
	}
	
	@Override
	public OptimizedLirBlock visit(ExpressionBlock expressionBlock, Integer targetReg) {
		return expressionBlock.getExpression().accept(this, targetReg);
	}
}
