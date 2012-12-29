package IC.SymbolTable;

import IC.SemanticError;
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
				//global.insertChildSymbolTable((ClassSymbolTable)classDecl.accept(this));
				classDecl.accept(this);
			}
			catch (SemanticError semantic){
				//throw new SemanticError(classDecl.getLine() + ": " + semantic.getMessage());
				tableError(classDecl.getLine() + ": " + semantic.getMessage());
			}
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
				//throw new SemanticError(field.getLine() + ": " + semantic.getMessage());
				tableError(field.getLine() + ": " + semantic.getMessage());
			}
		}
		
		for(Method method : icClass.getMethods()){
			try{
				method.setEnclosingScope(symbols);
				symbols.insert(method);
				//symbols.insertChildSymbolTable((SymbolTable)((Method)
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
		MethodSymbolTable symbols = new MethodSymbolTable(method.getEnclosingScope(), method.getName(), !(method instanceof VirtualMethod));
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
			/*if(stmt instanceof LocalVariable){
				try{
					symbols.insert((LocalVariable) stmt);
				}
				catch(SemanticError semantic){
					tableError(stmt.getLine() + ": " + semantic.getMessage());
				}
			}*/
			/*SymbolTable child = (SymbolTable)stmt.accept(this);
			if(child != null){
				symbols.insertChildSymbolTable(child);
			}*/
		}		
		method.getEnclosingScope().insertChildSymbolTable(symbols);
		return null;
	}
	
	@Override
	public Object visit(VirtualMethod method) {
		return null;
	}

	@Override
	public Object visit(StaticMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LibraryMethod method) {
		// TODO Auto-generated method stub
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
		returnStatement.getValue().setEnclosingScope(returnStatement.getEnclosingScope());
		returnStatement.getValue().accept(this);
		return null;
	}

	@Override
	public Object visit(If ifStatement) {
		// TODO from here
		// after updating the chidren's enclosing scope, we can visit them
		// and they will add themselves and their symbol tables (if there are any) to the enclosing table 
		ifStatement.getCondition().setEnclosingScope(ifStatement.getEnclosingScope());
		ifStatement.getCondition().accept(this);
		ifStatement.getOperation().setEnclosingScope(ifStatement.getEnclosingScope());
		if(ifStatement.hasElse()){
			ifStatement.getElseOperation();
		}
		
		return null;
	}

	@Override
	public Object visit(While whileStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Break breakStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Continue continueStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(StatementsBlock statementsBlock) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Literal literal) {
		// TODO Auto-generated method stub
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


}
