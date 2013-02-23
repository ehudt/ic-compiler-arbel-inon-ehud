package IC.LIR;

import IC.BinaryOps;
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
 * Calculate the weight of each AST node (expressions) according to 
 * Sethi-Ullman algorithm and determine whether it is optimizable, 
 * i.e. contains no expressions that may contain side-effects.
 * The default weight is 0 and the default isOptimise is false.
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

		return null;
	}

	@Override
	public Object visit(Formal formal) {
		return null;
	}

	@Override
	public Object visit(PrimitiveType type) {
		return null;
	}

	@Override
	public Object visit(UserType type) {
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
		return null;
	}

	@Override
	public Object visit(Continue continueStatement) {
		return null;
	}

	@Override
	public Object visit(StatementsBlock statementsBlock) {
		for(Statement stmt : statementsBlock.getStatements()){
			stmt.accept(this);
		}
		return null;
	}


	/**
	 * 
	 * recursively calculate the weight and optimization of a local variable node
	 * 
	 */
	@Override
	public Object visit(LocalVariable localVariable) {
		localVariable.getType().accept(this);
		if (localVariable.hasInitValue()){
			localVariable.getInitValue().accept(this);
			localVariable.setRegWeight(localVariable.getInitValue().getRegWeight());
			localVariable.setOptimizable(localVariable.getInitValue().isOptimizable());
			}

		return null;
	}


	/**
	 * 
	 * recursively calculate the weight and optimization of a variable Location node
	 * 
	 */
	@Override
	public Object visit(VariableLocation location) {
		if (location.isExternal()){
			location.getLocation().accept(this);
			location.setOptimizable(location.getLocation().isOptimizable());
			location.setRegWeight(location.getLocation().getRegWeight());
			}

		else{
			location.setOptimizable(true);
			location.setRegWeight(1);
			}
		
		return null;
	}


	/**
	 * 
	 * recursively calculate the weight and optimization of an array location node
	 * 
	 */
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
		 * 
		 * Method is never optimizable since it can change the state
		 * 
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
		 * 
		 * Method is never optimizable since it can change the state
		 * 
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


	/**
	 * 
	 * this expression has weight 1 and is always optimizable.
	 * 
	 */
	@Override
	public Object visit(This thisExpression) {
		thisExpression.setRegWeight(1);
		thisExpression.setOptimizable(true);
		return null;
	}

	/**
	 * 
	 * recursively calculate the weight and optimization of a new class node
	 * 
	 */
	@Override
	public Object visit(NewClass newClass) {
		newClass.setOptimizable(true);
		newClass.setRegWeight(1);
		return null;
	}

	/**
	 * 
	 * recursively calculate the weight and optimization of a new array node
	 * 
	 */
	@Override
	public Object visit(NewArray newArray) {
		newArray.getSize().accept(this);
		newArray.setRegWeight(newArray.getSize().getRegWeight());
		newArray.setOptimizable(newArray.getSize().isOptimizable());
		return null;
	}

	/**
	 * 
	 * recursively calculate the weight and optimization of an array length node
	 * 
	 */
	
	@Override
	public Object visit(Length length) {
		length.getArray().accept(this);
		length.setRegWeight(length.getArray().getRegWeight());
		length.setOptimizable(length.getArray().isOptimizable());
		return null;
	}

	/**
	 * 
	 * recursively calculate the weight and optimization of a math binary op
	 * 
	 */
	
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

	/**
	 * 
	 * recursively calculate the weight and optimization of a logical binary op.
	 * Notice that Or and And are not optimizable.
	 * 
	 */
	
	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
	
		if (binaryOp.getOperator().equals(BinaryOps.LOR) || binaryOp.getOperator().equals(BinaryOps.LAND)){
			binaryOp.setOptimizable(false);
		}
		else{
			binaryOp.setOptimizable(binaryOp.getFirstOperand().isOptimizable()&&binaryOp.getSecondOperand().isOptimizable());
		}
		
		int firstReg = binaryOp.getFirstOperand().getRegWeight();
		int secondReg = binaryOp.getSecondOperand().getRegWeight();
		binaryOp.setRegWeight(sethiHelper(firstReg, secondReg));
		return null;
		
	}

	/**
	 * 
	 * recursively calculate the weight and optimization of a math unary op
	 * 
	 */
	
	@Override
	public Object visit(MathUnaryOp unaryOp) {
		unaryOp.getOperand().accept(this);
		unaryOp.setOptimizable(unaryOp.isOptimizable());
		unaryOp.setRegWeight(unaryOp.getOperand().getRegWeight());
		return null;
	}

	/**
	 * 
	 * recursively calculate the weight and optimization of a logical unary op
	 * 
	 */
	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		unaryOp.getOperand().accept(this);
		unaryOp.setOptimizable(unaryOp.isOptimizable());
		unaryOp.setRegWeight(unaryOp.getOperand().getRegWeight());
		return null;
	}


	/**
	 * 
	 * A literal has weight 0 and is always optimizable.
	 * 
	 */
	@Override
	public Object visit(Literal literal) {
		literal.setOptimizable(true);
		return null;
	}

	/**
	 * recursively calculate the weight of an expression block
	 * 
	 */
	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		expressionBlock.getExpression().accept(this);
		expressionBlock.setRegWeight(expressionBlock.getExpression().getRegWeight());
		expressionBlock.setOptimizable(expressionBlock.getExpression().isOptimizable());
		return null;
	}

	@Override
	public Object visit(FieldMethodList fieldMethodList) {
		return null;
	}

	@Override
	public Object visit(EmptyStatement emptyStatement) {
		return null;
	}

	@Override
	public Object visit(ErrorMethod errorMethod) {
		return null;
	}

	@Override
	public Object visit(ErrorClass errorClass) {
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
		return null;
	}

	@Override
	public Object visit(ClassSymbolTable table) {
		return null;
	}

	@Override
	public Object visit(BlockSymbolTable table) {
		return null;
	}

	@Override
	public Object visit(MethodSymbolTable table) {
		return null;
	}

	@Override
	public Object visit(SymbolTable symbolTable) {
		return null;
	}

	/** 
	 * Calculates the register weight for the expression tree: if right!=left return max(right,left)
	 * else return right+1.
	 * 
	 **/
	public int sethiHelper(int right, int left){
		if (right==left){
			return right+1;
		}
		else return Math.max(right, left);
	}
	
}
