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
/**
 * Interface for traversing an AST with a context value attached to each call
 * @author ehud
 *
 * @param <ReturnType>
 * @param <ContextType>
 */
public interface PropagatingVisitor<ReturnType, ContextType> {
	public ReturnType visit(Program program, ContextType context);

	public ReturnType visit(ICClass icClass, ContextType context);

	public ReturnType visit(Field field, ContextType context);

	public ReturnType visit(VirtualMethod method, ContextType context);

	public ReturnType visit(StaticMethod method, ContextType context);

	public ReturnType visit(LibraryMethod method, ContextType context);

	public ReturnType visit(Formal formal, ContextType context);

	public ReturnType visit(PrimitiveType type, ContextType context);

	public ReturnType visit(UserType type, ContextType context);

	public ReturnType visit(Assignment assignment, ContextType context);

	public ReturnType visit(CallStatement callStatement, ContextType context);

	public ReturnType visit(Return returnStatement, ContextType context);

	public ReturnType visit(If ifStatement, ContextType context);

	public ReturnType visit(While whileStatement, ContextType context);

	public ReturnType visit(Break breakStatement, ContextType context);

	public ReturnType visit(Continue continueStatement, ContextType context);

	public ReturnType visit(StatementsBlock statementsBlock, ContextType context);

	public ReturnType visit(LocalVariable localVariable, ContextType context);

	public ReturnType visit(VariableLocation location, ContextType context);

	public ReturnType visit(ArrayLocation location, ContextType context);

	public ReturnType visit(StaticCall call, ContextType context);

	public ReturnType visit(VirtualCall call, ContextType context);

	public ReturnType visit(This thisExpression, ContextType context);

	public ReturnType visit(NewClass newClass, ContextType context);

	public ReturnType visit(NewArray newArray, ContextType context);

	public ReturnType visit(Length length, ContextType context);

	public ReturnType visit(MathBinaryOp binaryOp, ContextType context);

	public ReturnType visit(LogicalBinaryOp binaryOp, ContextType context);

	public ReturnType visit(MathUnaryOp unaryOp, ContextType context);
	
	public ReturnType visit(LogicalUnaryOp unaryOp, ContextType context);

	public ReturnType visit(Literal literal, ContextType context);

	public ReturnType visit(ExpressionBlock expressionBlock, ContextType context);

	public ReturnType visit(FieldMethodList fieldMethodList, ContextType context);

	public ReturnType visit(EmptyStatement emptyStatement, ContextType context);

	public ReturnType visit(ErrorMethod errorMethod, ContextType context);
	
	public ReturnType visit(ErrorClass errorClass, ContextType context);

	public ReturnType visit(Method method, ContextType context);
	
	public ReturnType visit(GlobalSymbolTable table, ContextType context);
	
	public ReturnType visit(ClassSymbolTable table, ContextType context);
	
	public ReturnType visit(BlockSymbolTable table, ContextType context);
	
	public ReturnType visit(MethodSymbolTable table, ContextType context);

	public ReturnType visit(SymbolTable symbolTable, ContextType context);
}

