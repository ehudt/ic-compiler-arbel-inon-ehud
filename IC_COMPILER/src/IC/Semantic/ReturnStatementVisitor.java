package IC.Semantic;

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
import IC.AST.Type;
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
import IC.Types.TypeTable;

public class ReturnStatementVisitor implements Visitor {
	
	private void returnError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);
	}

	@Override
	public Object visit(Program program) {
		for(ICClass icClass : program.getClasses()){
			icClass.accept(this);
		}
		return true;
	}

	@Override
	public Object visit(ICClass icClass) {
		for(Method method : icClass.getMethods()){
			Type methodReturn = TypeTable.getType(TypeTable.getType(method).getReturnType());
			if(methodReturn == TypeTable.getType("void")) continue;
			boolean hasReturn = (Boolean)method.accept(this);
			if(!hasReturn) {
				returnError(method.getLine(), "method " + method.getName() + " is of non-void return type and must have a return statement in every execution path");
			}
		}
		return true;
	}

	@Override
	public Object visit(Field field) {
		return false;
	}

	@Override
	public Object visit(VirtualMethod method) {
		for(Statement stmt : method.getStatements()){
			if ((Boolean)stmt.accept(this)) return true;
		}
		return false;
	}

	@Override
	public Object visit(StaticMethod method) {
		for(Statement stmt : method.getStatements()){
			if ((Boolean)stmt.accept(this)) return true;
		}
		return false;
	}

	@Override
	public Object visit(LibraryMethod method) {
		for(Statement stmt : method.getStatements()){
			if ((Boolean)stmt.accept(this)) return true;
		}
		return false;
	}

	@Override
	public Object visit(Formal formal) {
		return false;
	}

	@Override
	public Object visit(PrimitiveType type) {
		return false;
	}

	@Override
	public Object visit(UserType type) {
		return false;
	}

	@Override
	public Object visit(Assignment assignment) {
		assignment.getAssignment().accept(this);
		assignment.getVariable().accept(this);
		return false;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		callStatement.getCall().accept(this);
		return false;
	}

	@Override
	public Object visit(Return returnStatement) {
		return true;
	}

	@Override
	public Object visit(If ifStatement) {
		if(ifStatement.hasElse()){
			return 	(Boolean)ifStatement.getOperation().accept(this) &&
					(Boolean)ifStatement.getElseOperation().accept(this);
		} else {
			return false;
		}
	}
	
	@Override
	public Object visit(While whileStatement) {
		return false;
	}

	@Override
	public Object visit(Break breakStatement) {
		return false;
	}

	@Override
	public Object visit(Continue continueStatement) {
		return false;
	}

	@Override
	public Object visit(StatementsBlock statementsBlock) {
		for(Statement stmt : statementsBlock.getStatements()){
			if ((Boolean)stmt.accept(this)) return true;
		}
		return false;
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		return false;
	}

	@Override
	public Object visit(VariableLocation location) {
		return false;
	}

	@Override
	public Object visit(ArrayLocation location) {
		return false;
	}

	@Override
	public Object visit(StaticCall call) {
		return false;
	}

	@Override
	public Object visit(VirtualCall call) {
		return false;
	}

	@Override
	public Object visit(This thisExpression) {
		return false;
	}

	@Override
	public Object visit(NewClass newClass) {
		return false;
	}

	@Override
	public Object visit(NewArray newArray) {
		return false;
	}

	@Override
	public Object visit(Length length) {
		return false;
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		return false;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		return false;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		return false;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		return false;
	}

	@Override
	public Object visit(Literal literal) {
		return false;
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		return false;
	}

	@Override
	public Object visit(FieldMethodList fieldMethodList) {
		return false;
	}

	@Override
	public Object visit(EmptyStatement emptyStatement) {
		return false;
	}

	@Override
	public Object visit(ErrorMethod errorMethod) {
		return false;
	}

	@Override
	public Object visit(ErrorClass errorClass) {
		return false;
	}

	@Override
	public Object visit(Method method) {
		for(Statement stmt : method.getStatements()){
			if ((Boolean)stmt.accept(this)) return true;
		}
		return false;
	}

	@Override
	public Object visit(GlobalSymbolTable table) {
		return false;
	}

	@Override
	public Object visit(ClassSymbolTable table) {
		return false;
	}

	@Override
	public Object visit(BlockSymbolTable table) {
		return false;
	}

	@Override
	public Object visit(MethodSymbolTable table) {
		return false;
	}

	@Override
	public Object visit(SymbolTable symbolTable) {
		return false;
	}


}