package IC.LIR;

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
import IC.AST.Visitor;
import IC.AST.While;
import IC.SymbolTable.BlockSymbolTable;
import IC.SymbolTable.ClassSymbolTable;
import IC.SymbolTable.GlobalSymbolTable;
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.SymbolTable;

/**
 * 
 * Since the default value for regWeight is 0, 
 * we did not implement the visitor methods for expressions that needs
 * 0 registers
 * 
 * @author arbel
 *
 */

public class SethiUllmanWeightVisitor implements Visitor {

	@Override
	public Object visit(Program program) {
		for(ICClass icClass : program.getClasses()){
			icClass.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(ICClass icClass) {
		for(Field field : icClass.getFields()){
			field.accept(this);
		}
		for(Method method : icClass.getMethods()){
			method.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(VirtualMethod method) {
		for(Statement stmt : method.getStatements()){
			stmt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(StaticMethod method) {
		for(Statement stmt : method.getStatements()){
			stmt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(LibraryMethod method) {
		for(Statement stmt : method.getStatements()){
			stmt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(Formal formal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(PrimitiveType type) {
		type.setOptimizable(true);
		return null;
	}

	@Override
	public Object visit(UserType type) {
		// TODO
		return null;
	}

	@Override
	public Object visit(Assignment assignment) {
		assignment.getAssignment().accept(this);
		assignment.getVariable().accept(this);
		return null;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		callStatement.getCall().accept(this);
		return null;
	}

	@Override
	public Object visit(Return returnStatement) {
		if (returnStatement.getValue() == null) return null;
		returnStatement.getValue().accept(this);
		return null;
	}

	@Override
	public Object visit(If ifStatement) {
		ifStatement.getCondition().accept(this);
		ifStatement.getOperation().accept(this);
		if(ifStatement.hasElse()){
			ifStatement.getElseOperation().accept(this);
		}
		return null;
	}
	
	@Override
	public Object visit(While whileStatement) {
		whileStatement.getCondition().accept(this);
		whileStatement.getOperation().accept(this);
		return null;
	}

	@Override
	public Object visit(Break breakStatement) {
		// TODO
		return null;
	}

	@Override
	public Object visit(Continue continueStatement) {
		// TODO
		return null;
	}

	@Override
	public Object visit(StatementsBlock statementsBlock) {
		for(Statement stmt : statementsBlock.getStatements()){
			stmt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		localVariable.getType().accept(this);
		if (localVariable.hasInitValue()){
			localVariable.getInitValue().accept(this);
			localVariable.setRegWeight(localVariable.getInitValue().getRegWeight());
			localVariable.setOptimizable(localVariable.getInitValue().isOptimizable());
			}
		
		else{
			localVariable.setRegWeight(1);
			localVariable.setOptimizable(true);
			
		}
		if(localVariable.getInitValue() == null) return null;
		localVariable.getInitValue().accept(this);
		return null;
	}

	@Override
	public Object visit(VariableLocation location) {
		if(location.getLocation() == null) return null;
		location.getLocation().accept(this);
		if (location.isExternal()){
			location.setOptimizable(location.getLocation().isOptimizable());
			location.setRegWeight(location.getLocation().getRegWeight());
			}

		else{
			location.setOptimizable(true);
			location.setRegWeight(1);
			}
		
		return null;
	}

	@Override
	public Object visit(ArrayLocation location) {
		location.getArray().accept(this);
		int arrayReg = location.getArray().getRegWeight();
		boolean arrOpt = location.getArray().isOptimizable();
		
		location.getIndex().accept(this);
		int indexReg = location.getIndex().getRegWeight();
		boolean indOpt = location.getIndex().isOptimizable();
		
		location.setOptimizable(indOpt&&arrOpt);
		location.setRegWeight(sethiHelper(arrayReg, indexReg));
		
		return null;
	}

	@Override
	public Object visit(StaticCall call) {
		/**
		 * Method is never optimizable since it can change the state
		 */
		call.setOptimizable(false);
		for(Expression arg : call.getArguments()){
			arg.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(VirtualCall call) {
		/**
		 * Method is never optimizable since it can change the state
		 */
		call.setOptimizable(false);
		if(call.getLocation() != null){
			call.getLocation().accept(this);	
		}
		
		for(Expression arg : call.getArguments()){
			arg.accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(This thisExpression) {
		thisExpression.setRegWeight(1);
		thisExpression.setOptimizable(true);
		return null;
	}

	@Override
	public Object visit(NewClass newClass) {
		newClass.setOptimizable(true);
		newClass.setRegWeight(1);
		return null;
	}

	@Override
	public Object visit(NewArray newArray) {
		newArray.getSize().accept(this);
		newArray.setRegWeight(newArray.getSize().getRegWeight());
		newArray.setOptimizable(newArray.getSize().isOptimizable());
		return null;
	}

	@Override
	public Object visit(Length length) {
		length.getArray().accept(this);
		length.setRegWeight(length.getArray().getRegWeight());
		length.setOptimizable(length.getArray().isOptimizable());
		return null;
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
		binaryOp.setOptimizable(binaryOp.getFirstOperand().isOptimizable()&&binaryOp.getSecondOperand().isOptimizable());
		int firstReg = binaryOp.getFirstOperand().getRegWeight();
		int secondReg = binaryOp.getSecondOperand().getRegWeight();
		binaryOp.setRegWeight(sethiHelper(firstReg, secondReg));
		return null;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
		binaryOp.setOptimizable(binaryOp.getFirstOperand().isOptimizable()&&binaryOp.getSecondOperand().isOptimizable());
		int firstReg = binaryOp.getFirstOperand().getRegWeight();
		int secondReg = binaryOp.getSecondOperand().getRegWeight();
		binaryOp.setRegWeight(sethiHelper(firstReg, secondReg));
		return null;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		unaryOp.getOperand().accept(this);
		unaryOp.setOptimizable(unaryOp.isOptimizable());
		unaryOp.setRegWeight(unaryOp.getOperand().getRegWeight());
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		unaryOp.getOperand().accept(this);
		unaryOp.setOptimizable(unaryOp.isOptimizable());
		unaryOp.setRegWeight(unaryOp.getOperand().getRegWeight());
		return null;
	}

	@Override
	public Object visit(Literal literal) {
		literal.setOptimizable(true);
		return null;
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		expressionBlock.getExpression().accept(this);
		expressionBlock.setRegWeight(expressionBlock.getExpression().getRegWeight());
		expressionBlock.setOptimizable(expressionBlock.getExpression().isOptimizable());
		return null;
	}

	@Override
	public Object visit(FieldMethodList fieldMethodList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(EmptyStatement emptyStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ErrorMethod errorMethod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ErrorClass errorClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Method method) {
		for(Statement stmt : method.getStatements()){
			stmt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(GlobalSymbolTable table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ClassSymbolTable table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(BlockSymbolTable table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(MethodSymbolTable table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(SymbolTable symbolTable) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Calculates the register weight for the expression tree 
	 * 
	 **/
	public int sethiHelper(int right, int left){
		if (right==left){
			return right+1;
		}
		else return Math.max(right, left);
	}
	
}
