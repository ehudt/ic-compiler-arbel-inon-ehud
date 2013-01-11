package IC.Semantic;



/**
 *	This class is in charge with structure checks.
 *	This visitor recursively go over the program and see that the structure
 *	is correct, according to the IC spec
 * @author arbel
 *
 */

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
import IC.AST.Type;
import IC.AST.UserType;
import IC.AST.VariableLocation;
import IC.AST.VirtualCall;
import IC.AST.VirtualMethod;
import IC.AST.Visitor;
import IC.AST.While;
import IC.SymbolTable.BlockSymbolTable;
import IC.SymbolTable.ClassSymbolTable;
import IC.SymbolTable.FieldSymbol;
import IC.SymbolTable.GlobalSymbolTable;
import IC.SymbolTable.Kind;
import IC.SymbolTable.MethodSymbol;
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.Symbol;
import IC.SymbolTable.SymbolTable;
import IC.Types.TypeTable;
import IC.Types.MethodType;

public class StructureChecksVisitor implements Visitor {
	boolean hasMain = false;
	private boolean inLoopContext = false;
	private boolean inVirtualMethodContext = false;

	//return true iff this is a correct main method with the correct signature
	private boolean isMain(MethodSymbol ms, Method m){
		
		//check the method name
		
		if (!m.getName().equals("main")) 
			return false; 
		
		if (hasMain){
			structureError(m.getLine(), "only a single main method is allowed");
		}
		
		MethodType mt = ms.getMetType();
		if (mt.getReturnType().getName() != TypeTable.getType("void").getName()){
			
			structureError(m.getLine(),"invalid return type for method main. should be void");
		}
		
		if (m.getFormals().size() != 1){
			structureError(m.getLine(),"invalid parameter signature for method main. should have a single parameter of type string[]");
		}
		
		Type ty = TypeTable.getType(ms.getMetType().getParamTypes().get(0), false);
		if ((!ty.getName().equals("string"))||
				!(ty.getDimension() == 1))
		{
			structureError(m.getLine(),"invalid argument type for method main. should be string[]");

		}
		return true;
	}
	
	
	private void structureError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);
	}
	
	@Override
	public Object visit(Program program) {
		for(ICClass icClass : program.getClasses()){
			icClass.accept(this);
		}
		inLoopContext = false;
		return null;
	}

	@Override
	public Object visit(ICClass icClass) {
		for(Field field : icClass.getFields()){
			field.accept(this);
		}
		for(Method method : icClass.getMethods()){
			MethodSymbol methodSymbol = (MethodSymbol) method.getEnclosingScope().lookup(method.getName());
			if (isMain(methodSymbol, method))
					hasMain = true;
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
		inVirtualMethodContext = true;
		for(Statement stmt : method.getStatements()){
			stmt.accept(this);
		}
		inVirtualMethodContext = false;
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
		if(!inLoopContext){
			inLoopContext = true;
			whileStatement.getOperation().accept(this);
			inLoopContext = false;
		} else {
			whileStatement.getOperation().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(Break breakStatement) {
		if(!inLoopContext){
			structureError(breakStatement.getLine(), "break statement must be inside a loop context");
		}
		return null;
	}

	@Override
	public Object visit(Continue continueStatement) {
		if(!inLoopContext){
			structureError(continueStatement.getLine(), "continue statement must be inside a loop context");
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
		localVariable.getType().accept(this);
		if(localVariable.getInitValue() == null) return null;
		localVariable.getInitValue().accept(this);
		return null;
	}

	@Override
	public Object visit(VariableLocation location) {
		if(location.getLocation() == null) return null;
		location.getLocation().accept(this);
		return null;
	}

	@Override
	public Object visit(ArrayLocation location) {
		location.getArray().accept(this);
		location.getIndex().accept(this);
		return null;
	}

	@Override
	public Object visit(StaticCall call) {
		for(Expression arg : call.getArguments()){
			arg.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(VirtualCall call) {
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
		if(!inVirtualMethodContext){
			structureError(thisExpression.getLine(), "'this' can only be used inside an instance method context");
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
		newArray.getSize().accept(this);
		newArray.getType().accept(this);
		return null;
	}

	@Override
	public Object visit(Length length) {
		length.getArray().accept(this);
		return null;
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		Expression expr = unaryOp.getOperand();
		if(expr instanceof Literal) {
			Literal literal = (Literal) expr;
			if(literal.getType() == LiteralTypes.INTEGER){
				try{
					long value = Long.parseLong((String)literal.getValue());
					if(value > 2147483648L){ // check negative integer range
						structureError(unaryOp.getLine(), "integer literal out of range");
					}
				}
				catch (NumberFormatException numberFormatErr){
					structureError(unaryOp.getLine(), numberFormatErr.getMessage());
				}
			}
		}
		else
		{
			expr.accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		unaryOp.getOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(Literal literal) {
		if(literal.getType() == LiteralTypes.INTEGER){
			try {
				Integer.parseInt((String)literal.getValue());
			}
			catch (NumberFormatException numberFormatErr){
				structureError(literal.getLine(), "integer literal out of range");
			}
		}
		return null;
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		expressionBlock.getExpression().accept(this);
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
		for(SymbolTable child : table.getSymbolTables()){
			child.accept(this);
		}
		return null;
	}


	@Override
	public Object visit(ClassSymbolTable table) {
		
		
		//Check that fields are not getting overridden
		for (FieldSymbol field : table.getFieldSymbols()){
			Symbol parentField = table.getParent().lookup(field.getID());
			if (!(parentField == null)){
				structureError(field.getLine(), "illegal field name. field "+ field.getID() + " is used in a superclass");
			}
		}

		//Check that medhods are not getting overridden incorrectly 
		for (MethodSymbol method : table.getMethodSymbols()){
			Symbol parentField = table.getParent().lookup(method.getID());
			
			if (!(parentField == null)){
				if (!parentField.getKind().equals(Kind.METHOD)){
					structureError(method.getLine(), "illegal method name. name "+ method.getID() + " is used in a superclass");
				}
				
				MethodType methodType = method.getMetType();
				MethodSymbol parentMethod = (MethodSymbol)parentField;
				MethodType parentType = parentMethod.getMetType();		
				
				if (method.isStatic() && !parentMethod.isStatic()){
					structureError(method.getLine(), "Can't override "+ method.getID() + " with a static method");
				}
				
				if (!method.isStatic() && parentMethod.isStatic()){
					structureError(method.getLine(), "Can't override "+ method.getID() + " with an instance method");
				}
				
				if (!methodType.toString().equals(parentType.toString())){
					structureError(method.getLine(), "illegal method name or signature. "+ method.getID() + " is used in a superclass with a different signature");
				}
				
			}
		}
	
		
		
		for(SymbolTable child : table.getSymbolTables()){
		child.accept(this);		}
	return null;
	}
		
	@Override
	public Object visit(BlockSymbolTable table) {
		for(SymbolTable child : table.getSymbolTables()){
			child.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(MethodSymbolTable table) {
		for(SymbolTable child : table.getSymbolTables()){
			child.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(SymbolTable symbolTable) {
		for(SymbolTable child : symbolTable.getSymbolTables()){
			child.accept(this);
		}
		return null;
	}

}
