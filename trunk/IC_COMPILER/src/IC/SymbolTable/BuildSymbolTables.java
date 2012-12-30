package IC.SymbolTable;

import java.util.ArrayList;
import java.util.List;

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
import IC.AST.Visitor;
import IC.AST.While;

public class BuildSymbolTables implements Visitor {

	private void tableError(String message) {
		System.err.println(message);
		System.exit(0);
	}
	
	@Override
	public Object visit(Program program) {
		GlobalSymbolTable global = new GlobalSymbolTable(null);
		program.setEnclosingScope(global);
		for(ICClass classDecl : program.getClasses()){
			try{
				classDecl.setEnclosingScope(global);
				global.insert(classDecl);
				classDecl.accept(this);
			}
			catch (SemanticError semantic){
				tableError(classDecl.getLine() + ": " + semantic.getMessage());
			}
		}
		List<SymbolTable> toBeRemoved = new ArrayList<SymbolTable>();
		for(ICClass classDecl : program.getClasses()){
			if(classDecl.getSuperClassName() != null){
				SymbolTable parent = global.getSymbolTable(classDecl.getSuperClassName());
				SymbolTable child = global.getSymbolTable(classDecl.getName());
				if (parent != null && child != null){
					parent.insertChildSymbolTable(child);
					child.setParent(parent);
					toBeRemoved.add(child);
				}
			}
		}
		for(SymbolTable child : toBeRemoved){
			global.removeChildTable(child.getName());
		}
		return global;
	}

	@Override
	public Object visit(ICClass icClass){
		ClassSymbolTable symbols = new ClassSymbolTable(icClass.getEnclosingScope(), icClass.getName());
		for(Field field : icClass.getFields()){
			try{
				symbols.insert(field);
				field.setEnclosingScope(symbols);
			}
			catch (SemanticError semantic){
				tableError(field.getLine() + ": " + semantic.getMessage());
			}
		}
		
		for(Method method : icClass.getMethods()){
			try{
				symbols.insert(method);
				method.setEnclosingScope(symbols);
				((Method)method).accept(this);
			}
			catch(SemanticError semantic){
				tableError(method.getLine() + ": " + semantic.getMessage());
			}
		}
		icClass.getEnclosingScope().insertChildSymbolTable(symbols);
		return null;
	}

	@Override
	public Object visit(Field field) {
		field.getType().setEnclosingScope(field.getEnclosingScope());
		field.getType().accept(this);
		return null;
	}

	@Override
	public Object visit(Method method) {
	/*	MethodSymbolTable symbols = new MethodSymbolTable(method.getEnclosingScope(), method.getName(), !(method instanceof VirtualMethod));
		method.getEnclosingScope().insertChildSymbolTable(symbols);
		for(Formal formal : method.getFormals()){
			formal.setEnclosingScope(symbols);
			try{
				symbols.insert(formal);
			}
			catch(SemanticError semantic){
				tableError(formal.getLine() + ": " + semantic.getMessage());
			}
		}
		for(Statement stmt : method.getStatements()){
			stmt.setEnclosingScope(symbols);
			stmt.accept(this);
		}*/		
		return null;
	}
	
	private void visitMethod(Method method, boolean isStatic) {
		MethodSymbolTable symbols = new MethodSymbolTable(method.getEnclosingScope(), method.getName(), isStatic);
		method.getEnclosingScope().insertChildSymbolTable(symbols);
		for(Formal formal : method.getFormals()){
			try{
				symbols.insert(formal);
			}
			catch(SemanticError semantic){
				tableError(formal.getLine() + ": " + semantic.getMessage());
			}
			formal.setEnclosingScope(symbols);
			formal.accept(this);
		}
		for(Statement stmt : method.getStatements()){
			stmt.setEnclosingScope(symbols);
			stmt.accept(this);
		}		
	}
	
	@Override
	public Object visit(VirtualMethod method) {
		visitMethod(method, false);
		return null;
	}

	@Override
	public Object visit(StaticMethod method) {
		visitMethod(method, true);
		return null;
	}

	@Override
	public Object visit(LibraryMethod method) {
		visitMethod(method, true);
		return null;
	}

	@Override
	public Object visit(Formal formal) {
		formal.getType().setEnclosingScope(formal.getEnclosingScope());
		formal.getType().accept(this);
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
		assignment.getAssignment().setEnclosingScope(assignment.getEnclosingScope());
		assignment.getAssignment().accept(this);
		assignment.getVariable().setEnclosingScope(assignment.getEnclosingScope());
		assignment.getVariable().accept(this);
		return null;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		callStatement.getCall().setEnclosingScope(callStatement.getEnclosingScope());
		callStatement.getCall().accept(this);
		return null;
	}

	@Override
	public Object visit(Return returnStatement) {
		if (returnStatement.getValue() == null) return null;
		returnStatement.getValue().setEnclosingScope(returnStatement.getEnclosingScope());
		returnStatement.getValue().accept(this);
		return null;
	}

	@Override
	public Object visit(If ifStatement) {
		ifStatement.getCondition().setEnclosingScope(ifStatement.getEnclosingScope());
		ifStatement.getCondition().accept(this);
		ifStatement.getOperation().setEnclosingScope(ifStatement.getEnclosingScope());
		ifStatement.getOperation().accept(this);
		if(ifStatement.hasElse()){
			ifStatement.getElseOperation().setEnclosingScope(ifStatement.getEnclosingScope());
			ifStatement.getElseOperation().accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(While whileStatement) {
		whileStatement.getCondition().setEnclosingScope(whileStatement.getEnclosingScope());
		whileStatement.getCondition().accept(this);
		whileStatement.getOperation().setEnclosingScope(whileStatement.getEnclosingScope());
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
		BlockSymbolTable symbols = new BlockSymbolTable(statementsBlock.getEnclosingScope());
		for(Statement stmt : statementsBlock.getStatements()){
			stmt.setEnclosingScope(symbols);
			stmt.accept(this);
		}
		statementsBlock.getEnclosingScope().insertChildSymbolTable(symbols);
		return null;
	}

	@Override
	public Object visit(LocalVariable localVariable) {
		try{
			((BlockSymbolTable)localVariable.getEnclosingScope()).insert(localVariable);
		}
		catch(SemanticError semantic){
			tableError(localVariable.getLine() + ": " + semantic.getMessage());
		}
		localVariable.getType().setEnclosingScope(localVariable.getEnclosingScope());
		localVariable.getType().accept(this);
		if(localVariable.getInitValue() != null){
			localVariable.getInitValue().setEnclosingScope(localVariable.getEnclosingScope());
			localVariable.getInitValue().accept(this);
		}
		return null;
	}

	@Override
	public Object visit(VariableLocation location) {
		if (location.getLocation() == null) return null;
		location.getLocation().setEnclosingScope(location.getEnclosingScope());
		location.getLocation().accept(this);
		return null;
	}

	@Override
	public Object visit(ArrayLocation location) {
		location.getArray().setEnclosingScope(location.getEnclosingScope());
		location.getArray().accept(this);
		location.getIndex().setEnclosingScope(location.getEnclosingScope());
		location.getIndex().accept(this);
		return null;
	}

	@Override
	public Object visit(StaticCall call) {
		for(Expression expr : call.getArguments()){
			expr.setEnclosingScope(call.getEnclosingScope());
			expr.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(VirtualCall call) {
		if(call.getLocation() != null){
			call.getLocation().setEnclosingScope(call.getEnclosingScope());
		}
		for(Expression expr : call.getArguments()){
			expr.setEnclosingScope(call.getEnclosingScope());
			expr.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(This thisExpression) {
		return null;
	}

	@Override
	public Object visit(NewClass newClass) {
		return null;
	}

	@Override
	public Object visit(NewArray newArray) {
		newArray.getSize().setEnclosingScope(newArray.getEnclosingScope());
		newArray.getSize().accept(this);
		newArray.getType().setEnclosingScope(newArray.getEnclosingScope());
		newArray.getType().accept(this);
		return null;
	}

	@Override
	public Object visit(Length length) {
		length.getArray().setEnclosingScope(length.getEnclosingScope());
		length.getArray().accept(this);
		return null;
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		binaryOp.getFirstOperand().setEnclosingScope(binaryOp.getEnclosingScope());
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().setEnclosingScope(binaryOp.getEnclosingScope());
		binaryOp.getSecondOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		binaryOp.getFirstOperand().setEnclosingScope(binaryOp.getEnclosingScope());
		binaryOp.getFirstOperand().accept(this);
		binaryOp.getSecondOperand().setEnclosingScope(binaryOp.getEnclosingScope());
		binaryOp.getSecondOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		unaryOp.getOperand().setEnclosingScope(unaryOp.getEnclosingScope());
		unaryOp.getOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		unaryOp.getOperand().setEnclosingScope(unaryOp.getEnclosingScope());
		unaryOp.getOperand().accept(this);
		return null;
	}

	@Override
	public Object visit(Literal literal) {
		return null;
	}

	@Override
	public Object visit(ExpressionBlock expressionBlock) {
		expressionBlock.getExpression().setEnclosingScope(expressionBlock.getEnclosingScope());
		expressionBlock.getExpression().accept(this);
		return null;
	}

	@Override
	public Object visit(FieldMethodList fieldMethodList) {
		// TODO Delete before handing in!!!
		System.err.println("Visited FieldMethodList in build symbol table. shouldn't happen.");
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
