package IC.Semantic;

import IC.LiteralTypes;
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

public class StructureChecksVisitor implements Visitor {

	private void structureError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);
	}
	
	private class Context{
		public boolean inLoopContext = false;
		public boolean inVirtualMethodContext = false;
	}
	
	private Context context = new Context();
	
	@Override
	public Object visit(Program program) {
		for(ICClass icClass : program.getClasses()){
			icClass.accept(this);
		}
		context.inLoopContext = false;
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
		context.inVirtualMethodContext = true;
		for(Statement stmt : method.getStatements()){
			stmt.accept(this);
		}
		context.inVirtualMethodContext = false;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(UserType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Assignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Return returnStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(If ifStatement) {
		ifStatement.getOperation().accept(this);
		if(ifStatement.hasElse()){
			ifStatement.getElseOperation().accept(this);
		}
		return null;
	}
	
	@Override
	public Object visit(While whileStatement) {
		if(!context.inLoopContext){
			context.inLoopContext = true;
			whileStatement.getOperation().accept(this);
			context.inLoopContext = false;
		} else {
			whileStatement.getOperation().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(Break breakStatement) {
		if(!context.inLoopContext){
			structureError(breakStatement.getLine(), "break statement must be inside a loop context");
		}
		return null;
	}

	@Override
	public Object visit(Continue continueStatement) {
		if(!context.inLoopContext){
			structureError(continueStatement.getLine(), "break statement must be inside a loop context");
		}
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(VariableLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ArrayLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(StaticCall call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(VirtualCall call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(This thisExpression) {
		if(!context.inVirtualMethodContext){
			structureError(thisExpression.getLine(), "'this'can only be used inside an instance method context");
		}
		return null;
	}

	@Override
	public Object visit(NewClass newClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(NewArray newArray) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Length length) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		Expression expr = unaryOp.getOperand();
		if(expr instanceof Literal){
			
		}
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Literal literal) {
		if(literal.getType() == LiteralTypes.INTEGER){
			try {
				Integer.parseInt((String)literal.getValue());
			}
			catch (NumberFormatException numberFormatErr){
				structureError(literal.getLine(), numberFormatErr.getMessage());
			}
		}
		return null;
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		// TODO Auto-generated method stub
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

}
