package IC.Semantic;

import java.util.HashSet;
import java.util.Set;

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
import IC.SymbolTable.Kind;
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.Symbol;
import IC.SymbolTable.SymbolTable;
import IC.SymbolTable.VarSymbol;

 
/**
 * This visitor is in charge of checking bonus 1: a local variable is used only after it has 
 * been initialized * 
 * @author arbel
 *
 */


public class VariableInitializeVisitor implements Visitor {
	// records the current step in the iteration
	private int step = 0;
	private boolean assignmentLValueContext = false;
	private MethodSymbolTable currentMethodScope;
	
	private void initError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);
	}

	@Override
	public Object visit(Program program) {
		for(ICClass icClass : program.getClasses()){
			step++;
			icClass.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(ICClass icClass) {
		if(icClass.getName().equals("Library")) return null;
		for(Method method : icClass.getMethods()){
			step++;
			ClassSymbolTable classScope = (ClassSymbolTable)method.getEnclosingScope();
			currentMethodScope = (MethodSymbolTable)classScope.getSymbolTable(method.getName());
			method.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(Field field) {
		step++;
		return null;
	}

	@Override
	public Object visit(VirtualMethod method) {
		for(Statement stmt : method.getStatements()){
			step++;
			stmt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(StaticMethod method) {
		for(Statement stmt : method.getStatements()){
			step++;
			stmt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(LibraryMethod method) {
		step++;
		return null;
	}

	@Override
	public Object visit(Formal formal) {
		step++;
		return null;
	}

	@Override
	public Object visit(PrimitiveType type) {
		step++;
		return null;
	}

	@Override
	public Object visit(UserType type) {
		step++;
		return null;
	}

	@Override
	public Object visit(Assignment assignment) {
		step++;
		assignment.getAssignment().accept(this);
		step++;
		assignmentLValueContext = true;
		assignment.getVariable().accept(this);
		assignmentLValueContext = false;
		step++;
		return null;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		step++;
		callStatement.getCall().accept(this);
		return null;
	}

	@Override
	public Object visit(Return returnStatement) {
		step++;
		if(returnStatement.hasValue()) {
			returnStatement.getValue().accept(this);
		}
		return null;
	}
	
	private Set<VarSymbol> getInitiatedSince(int step) {
		return getInitiatedSince(step, currentMethodScope);
	}
	
	// a function that returns all the symbols that were initiated
	// since the given step of the visitor
	private Set<VarSymbol> getInitiatedSince(int step, BlockSymbolTable symbolTable) {
		Set<VarSymbol> returnSet = new HashSet<VarSymbol>();
		for(VarSymbol symbol : symbolTable.getLocalSymbols()) {
			if(symbol.isParam()) continue;
			if(symbol.getInitStep() != Integer.MAX_VALUE && symbol.getInitStep() > step) {
				returnSet.add(symbol);
			}
		}
		for(SymbolTable table : symbolTable.getSymbolTables()) {
			BlockSymbolTable childScope = (BlockSymbolTable)table;
			returnSet.addAll(getInitiatedSince(step, childScope));
		}
		return returnSet;
	}

	/**
	 * To find out if a variable was initialized in both if and else blocks, we find out which was initialized
	 * in each block using getInitiatedSince and comparing the 2 sets. Only variables that appear in both
	 * are considered initialized 
	 */
	@Override
	public Object visit(If ifStatement) {
		step++;
		ifStatement.getCondition().accept(this);
		step++;
		int operationInitialStep = step;
		step++;
		ifStatement.getOperation().accept(this);
		// list all variables that were initiated during the if operation
		Set<VarSymbol> initiatedSymbols = getInitiatedSince(operationInitialStep);
		for (VarSymbol local : initiatedSymbols) {
			local.setInitStep(Integer.MAX_VALUE);
		}
		
		if(ifStatement.hasElse()){
			step++;
			int elseInitialStep = step;
			step++;
			ifStatement.getElseOperation().accept(this);
			
			for (VarSymbol local : getInitiatedSince(elseInitialStep)) {
				if(!initiatedSymbols.contains(local)) {
					local.setInitStep(Integer.MAX_VALUE);
				}
			}
		}
		step++;
		return null;
	}
	
	/**
	 * A variable can be initialized inside a loop context, but the initialization must be discarded because 
	 * the loop of the body is not guaranteed to be executed. When the while ends, we check which variables were
	 * initialized inside it and un-initialize them.
	 */
	@Override
	public Object visit(While whileStatement) {
		step++;
		whileStatement.getCondition().accept(this);
		int initialStep = step;
		step++;
		whileStatement.getOperation().accept(this);
		for (VarSymbol local : getInitiatedSince(initialStep)) {
			local.setInitStep(Integer.MAX_VALUE);
		}
		step++;
		return null;
	}

	@Override
	public Object visit(Break breakStatement) {
		step++;
		return null;
	}

	@Override
	public Object visit(Continue continueStatement) {
		step++;
		return null;
	}

	@Override
	public Object visit(StatementsBlock statementsBlock) {
		step++;
		for(Statement stmt : statementsBlock.getStatements()){
			step++;
			stmt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		step++;
		localVariable.getType().accept(this);
		if(localVariable.hasInitValue()) {
			step++;
			localVariable.getInitValue().accept(this);
			step++;
			VarSymbol varSymbol = (VarSymbol)localVariable.getEnclosingScope().lookup(localVariable.getName());
			varSymbol.setInitStep(step);
			step++;
		}
		return null;
	}

	@Override
	public Object visit(VariableLocation location) {
		if(location.getLocation() != null) {
			step++;
			location.getLocation().accept(this);
			return null;
		}
		step++;
		Symbol symbol = location.getEnclosingScope().lookup(location.getName());
		if(symbol.getKind() != Kind.VARIABLE || ((VarSymbol)symbol).isParam()) return null;
		VarSymbol varSymbol = (VarSymbol)symbol;
		
		if(assignmentLValueContext) {
			varSymbol.setInitStep(Math.min(step, varSymbol.getInitStep()));
		} else {
			if(varSymbol.getInitStep() > step) {
				initError(location.getLine(), "illegal variable reference: variable " + location.getName() + " may have not been initialized");
			}
		}
		return null;
	}

	@Override
	public Object visit(ArrayLocation location) {
		boolean isLValue = assignmentLValueContext;
		assignmentLValueContext = false;
		step++;
		location.getIndex().accept(this);
		step++;
		location.getArray().accept(this);
		assignmentLValueContext = isLValue;
		return null;
	}

	@Override
	public Object visit(StaticCall call) {
		for(Expression arg : call.getArguments()){
			step++;
			arg.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(VirtualCall call) {
		if(call.getLocation() != null){
			step++;
			call.getLocation().accept(this);
		}
		for(Expression arg : call.getArguments()){
			step++;
			arg.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(This thisExpression) {
		step++;
		return null;
	}

	@Override
	public Object visit(NewClass newClass) {
		step++;
		return null;
	}

	@Override
	public Object visit(NewArray newArray) {
		step++;
		newArray.getSize().accept(this);
		return null;
	}

	@Override
	public Object visit(Length length) {
		step++;
		length.getArray().accept(this);
		return null;
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		step++;
		binaryOp.getFirstOperand().accept(this);
		step++;
		binaryOp.getSecondOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		step++;
		binaryOp.getFirstOperand().accept(this);
		step++;
		binaryOp.getSecondOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		step++;
		unaryOp.getOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		step++;
		unaryOp.getOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(Literal literal) {
		step++;
		return null;
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		step++;
		expressionBlock.getExpression().accept(this);
		return null;
	}

	@Override
	public Object visit(FieldMethodList fieldMethodList) {
		step++;
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
			step++;
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


}