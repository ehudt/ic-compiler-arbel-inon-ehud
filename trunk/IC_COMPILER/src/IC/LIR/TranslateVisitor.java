package IC.LIR;

import java.util.HashMap;
import java.util.Map;

import IC.AST.ArrayLocation;
import IC.AST.Assignment;
import IC.AST.Break;
import IC.AST.CallStatement;
import IC.AST.Continue;
import IC.AST.EmptyStatement;
import IC.AST.ErrorClass;
import IC.AST.ErrorMethod;
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
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.SymbolTable;
import IC.LIR.ClassLayout;

public class TranslateVisitor implements PropagatingVisitor<LirBlock, Integer>{
	
	private Map<String, String> stringLiterals = new LinkedHashMap<String, String>();
	private Map<String,ClassLayout> classLayouts = new LinkedHashMap<String, ClassLayout>();
	private int labelCount = 1;
	
	@Override
	public LirBlock visit(Program program, Integer targetReg) {
		StringBuilder programCode = new StringBuilder();
		
		
		/* visit the program */
		StringBuilder classesCode = new StringBuilder();
		for (ICClass classDecl : program.getClasses()) {
			classesCode.append(classDecl.accept(this, targetReg));
		}
		
		/* generate the string literals' and DVs' code */
		
		
		return new LirBlock(programCode, targetReg);
	}

	@Override
	public LirBlock visit(ICClass icClass, Integer targetReg) {
		classLayouts.put(icClass.getName(), ClassLayout.NewClassLayout(icClass));
		return null;
	}

	@Override
	public LirBlock visit(Field field, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(VirtualMethod method, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(StaticMethod method, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(LibraryMethod method, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Formal formal, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(PrimitiveType type, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(UserType type, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Assignment assignment, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(CallStatement callStatement, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Return returnStatement, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(If ifStatement, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(While whileStatement, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Break breakStatement, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Continue continueStatement, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(StatementsBlock statementsBlock, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(LocalVariable localVariable, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(VariableLocation location, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ArrayLocation location, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(StaticCall call, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(VirtualCall call, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(This thisExpression, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(NewClass newClass, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(NewArray newArray, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Length length, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(MathBinaryOp binaryOp, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(LogicalBinaryOp binaryOp, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(MathUnaryOp unaryOp, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		LirBlock operand = unaryOp.getOperand().accept(this, targetReg);
		lirCode.append(operand.getLirCode());
		lirCode.append("Neg R");
		lirCode.append(operand.getTargetRegister().toString());
		lirCode.append("\n");
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(LogicalUnaryOp unaryOp, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		LirBlock operand = unaryOp.getOperand().accept(this, targetReg);
		lirCode.append(operand.getLirCode());
		lirCode.append("Neg R");
		lirCode.append(operand.getTargetRegister().toString());
		lirCode.append("\n");
		
		return new LirBlock(lirCode, targetReg);
		
	}

	@Override
	public LirBlock visit(Literal literal, Integer targetReg) {
		String literalString = "";
		StringBuilder lirCode = new StringBuilder("Move ");
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
		
		lirCode.append(literalString);
		lirCode.append(",R");
		lirCode.append(targetReg.toString());
		lirCode.append("\n");
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(ExpressionBlock expressionBlock, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(FieldMethodList fieldMethodList, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(EmptyStatement emptyStatement, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ErrorMethod errorMethod, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ErrorClass errorClass, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Method method, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(GlobalSymbolTable table, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ClassSymbolTable table, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(BlockSymbolTable table, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(MethodSymbolTable table, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(SymbolTable symbolTable, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

}
