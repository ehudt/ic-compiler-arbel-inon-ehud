package IC.LIR;

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

public class TranslateVisitor implements PropagatingVisitor<LirBlock, Integer>{

	@Override
	public LirBlock visit(Program program, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ICClass icClass, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Field field, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(VirtualMethod method, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(StaticMethod method, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(LibraryMethod method, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Formal formal, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(PrimitiveType type, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(UserType type, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Assignment assignment, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(CallStatement callStatement, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Return returnStatement, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(If ifStatement, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(While whileStatement, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Break breakStatement, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Continue continueStatement, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(StatementsBlock statementsBlock, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(LocalVariable localVariable, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(VariableLocation location, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ArrayLocation location, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(StaticCall call, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(VirtualCall call, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(This thisExpression, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(NewClass newClass, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(NewArray newArray, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Length length, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(MathBinaryOp binaryOp, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(LogicalBinaryOp binaryOp, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(MathUnaryOp unaryOp, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(LogicalUnaryOp unaryOp, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Literal literal, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ExpressionBlock expressionBlock, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(FieldMethodList fieldMethodList, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(EmptyStatement emptyStatement, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ErrorMethod errorMethod, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ErrorClass errorClass, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Method method, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(GlobalSymbolTable table, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ClassSymbolTable table, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(BlockSymbolTable table, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(MethodSymbolTable table, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(SymbolTable symbolTable, Integer context) {
		// TODO Auto-generated method stub
		return null;
	}

}
