package IC.LIR;

import java.util.List;

import IC.BinaryOps;
import IC.SemanticError;
import IC.AST.ArrayLocation;
import IC.AST.Assignment;
import IC.AST.Break;
import IC.AST.CallStatement;
import IC.AST.Continue;
import IC.AST.EmptyStatement;
import IC.AST.ErrorClass;
import IC.AST.ErrorMethod;
import IC.AST.Expression;
import IC.AST.ExpressionBlock;
import IC.AST.Field;
import IC.AST.FieldMethodList;
import IC.AST.Formal;
import IC.AST.ICClass;
import IC.AST.If;
import IC.AST.Length;
import IC.AST.LibraryMethod;
import IC.AST.Literal;
import IC.AST.LocalVariable;
import IC.AST.LogicalBinaryOp;
import IC.AST.LogicalUnaryOp;
import IC.AST.MathBinaryOp;
import IC.AST.MathUnaryOp;
import IC.AST.Method;
import IC.AST.NewArray;
import IC.AST.NewClass;
import IC.AST.PrimitiveType;
import IC.AST.Program;
import IC.AST.Return;
import IC.AST.Statement;
import IC.AST.StatementsBlock;
import IC.AST.StaticCall;
import IC.AST.StaticMethod;
import IC.AST.This;
import IC.AST.UserType;
import IC.AST.VariableLocation;
import IC.AST.VirtualCall;
import IC.AST.VirtualMethod;
import IC.AST.While;
import IC.SymbolTable.BlockSymbolTable;
import IC.SymbolTable.ClassSymbolTable;
import IC.SymbolTable.GlobalSymbolTable;
import IC.SymbolTable.Kind;
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.Symbol;
import IC.SymbolTable.SymbolTable;
import IC.Types.TypeTable;

public class OptimizedTranslateVisitor extends TranslateVisitor {

	@Override
	public LirBlock visit(MathBinaryOp binaryOp, Integer targetReg) {
		// TODO
		String opString = "";
		StringBuilder lirCode = new StringBuilder("");
		
		Expression firstOperandNode = binaryOp.getFirstOperand();
		Expression secondOperandNode = binaryOp.getSecondOperand();
		boolean reversedOrder = false;
		
		if (binaryOp.isOptimizable()) {
			int firstWeight = firstOperandNode.getRegWeight(), secondWeight = secondOperandNode.getRegWeight();
			if (secondWeight > firstWeight) {
				reversedOrder = true;
				Expression tmpExpr = firstOperandNode;
				firstOperandNode = secondOperandNode;
				secondOperandNode = tmpExpr;
			}
		}
		Integer firstTargetReg=targetReg;
		Integer secondTargetReg=targetReg+1;
		
		LirBlock leftOperand = firstOperandNode.accept(this,firstTargetReg);
		lirCode.append(leftOperand.getLirCode());
		
		LirBlock rightOperand = secondOperandNode.accept(this,secondTargetReg);
		lirCode.append(rightOperand.getLirCode());
		
		if (reversedOrder) {
			Integer tmp = firstTargetReg;
			firstTargetReg = secondTargetReg;
			secondTargetReg = tmp;
		}
		
		switch(binaryOp.getOperator())
		{
			case PLUS:
				IC.AST.Type operandType=(IC.AST.Type)firstOperandNode.accept(typeVisitor);
				if(operandType==TypeTable.getType("int"))
				{
					//Addition of 2 integers
					opString="Add R"+secondTargetReg+",R"+firstTargetReg;
				}
				else
				{
					//Concatenation of 2 strings
					opString="Library __stringCat(R"+firstTargetReg+",R"+secondTargetReg+"),R"+firstTargetReg;
				}
				break;
			case MINUS:
				opString="Sub R"+secondTargetReg+",R"+firstTargetReg;
				break;
			case MULTIPLY:
				opString="Mul R"+secondTargetReg+",R"+firstTargetReg;
				break;
			case DIVIDE:
				//static call to checkZero on runtime
				opString+="StaticCall __checkZero(b=R"+secondTargetReg+"),Rdummy\n";
				opString+="Div R"+secondTargetReg+",R"+firstTargetReg;
				break;
			case MOD:
				//static call to checkZero on runtime
				opString+="StaticCall __checkZero(b=R"+secondTargetReg+"),Rdummy\n";
				opString+="Mod R"+secondTargetReg+",R"+firstTargetReg;
				break;
		}
		lirCode.append(opString);
		lirCode.append("\n");
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(LogicalBinaryOp binaryOp, Integer targetReg) {
		// TODO
		String jumpString = "";
		StringBuilder lirCode = new StringBuilder("");
		int lblnum=getNextLabelNum();
		
		Expression firstOperandNode = binaryOp.getFirstOperand();
		Expression secondOperandNode = binaryOp.getSecondOperand();
		boolean reversedOrder = false;
		
		if (binaryOp.isOptimizable()) {
			int firstWeight = firstOperandNode.getRegWeight(), secondWeight = secondOperandNode.getRegWeight();
			if (secondWeight > firstWeight) {
				reversedOrder = true;
				Expression tmpExpr = firstOperandNode;
				firstOperandNode = secondOperandNode;
				secondOperandNode = tmpExpr;
			}
		}
		
		Integer firstTargetReg=targetReg;
		Integer secondTargetReg=targetReg+1;
		
		LirBlock leftOperand=firstOperandNode.accept(this,firstTargetReg);
		lirCode.append(leftOperand.getLirCode());		
		
		if(binaryOp.getOperator()==BinaryOps.LOR || binaryOp.getOperator()==BinaryOps.LAND)
		{
			String compareTo;
			String boolOp;
			if(binaryOp.getOperator()==BinaryOps.LOR)
			{
				compareTo="1";
				boolOp="Or";
			}
			else
			{
				compareTo="0";
				boolOp="And";
			}
			lirCode.append("Compare "+compareTo+",R"+firstTargetReg+"\n");
			lirCode.append("JumpTrue _endlbl"+lblnum+"\n");
			
			LirBlock rightOperand=secondOperandNode.accept(this,secondTargetReg);
			lirCode.append(rightOperand.getLirCode());
			//lirCode.append("\n");
			
			if (reversedOrder) {
				Integer tmp = firstTargetReg;
				firstTargetReg = secondTargetReg;
				secondTargetReg = tmp;
			}
			
			lirCode.append(boolOp+" R"+secondTargetReg+",R"+firstTargetReg+"\n");
			
		}
		else
		{
			LirBlock rightOperand=secondOperandNode.accept(this,secondTargetReg);
			lirCode.append(rightOperand.getLirCode());
			//lirCode.append("\n");
			
			if (reversedOrder) {
				Integer tmp = firstTargetReg;
				firstTargetReg = secondTargetReg;
				secondTargetReg = tmp;
			}
			
			lirCode.append("Compare R"+secondTargetReg+",R"+firstTargetReg);
			lirCode.append("\n");
			
			switch (binaryOp.getOperator()) {
			case GT:
				jumpString+="JumpG";
				break;
			case GTE:
				jumpString+="JumpGE";
				break;
			case LT:
				jumpString+="JumpL";
				break;
			case LTE:
				jumpString+="JumpLE";
				break;
			case EQUAL:
				jumpString+="JumpTrue";
				break;
			case NEQUAL:
				jumpString+="JumpFalse";
				break;
			}
			jumpString+=" _truelbl"+lblnum+"\n";
			
			lirCode.append(jumpString);
			lirCode.append("Move 0,R"+firstTargetReg);
			lirCode.append("\n");
			lirCode.append("Jump _endlbl"+lblnum+"\n");
			lirCode.append("_truelbl"+lblnum+":\n");
			lirCode.append("Move 1,R"+firstTargetReg);
			lirCode.append("\n");
		}
		
		lirCode.append("_endlbl"+lblnum+":\n");
		return new LirBlock(lirCode, targetReg);
	}
}
