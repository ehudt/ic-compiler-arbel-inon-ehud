package IC.Semantic;


import IC.BinaryOps;
import IC.LiteralTypes;
import IC.SemanticError;
import IC.AST.ArrayLocation;
import IC.AST.Assignment;
import IC.AST.BinaryOp;
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
import IC.SymbolTable.GlobalSymbolTable;
import IC.SymbolTable.Kind;
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.Symbol;
import IC.SymbolTable.SymbolTable;
import IC.Types.TypeTable;

public class TypeCheckVisitor implements Visitor {
//TODO delete this
	public void faPr (String x){
		System.out.println(x);
	}
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(UserType type) {
		// TODO
		return null;
	}

	@Override
	public Object visit(Assignment assignment) {
		
	/*	//type check for assignment
		Type variableType = (Type) assignment.getVariable().accept(this);
		Type assignmentType = (Type) assignment.getAssignment().accept(this);
		
		if (assignmentType == null) 
			return null;
		
		if (!(assignmentType.subTypeOf(variableType))){
			typeError(assignment.getLine(), "Type error: " + assignmentType);
			
		}*/
		
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
	//check that index is of type int
	public Object visit(ArrayLocation location) {
		Type arrayType = (Type) location.getArray().accept(this);
		Type indexType = (Type) location.getIndex().accept(this);
		
		if (indexType != TypeTable.getType("int")){
			typeError(location.getLine(), "Array index must be of type int");
		}
		
		return arrayType;
		
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
		// TODO
		return null;
	}

	@Override
	public Object visit(NewClass newClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//Typecheck for newArray
	public Object visit(NewArray newArray) {
		Type sizeType = (Type) newArray.getSize().accept(this);
		Type arrayType = (Type) newArray.getType().accept(this);
		
		if (!sizeType.subTypeOf(TypeTable.getType("int")))
			typeError(newArray.getLine(), "Array size must be of type int");
		
		
		return arrayType;
	}

	@Override
	public Object visit(Length length) {
		Type arrType = (Type) length.getArray().accept(this);
		if( arrType.getDimension() < 1 ){
			typeError(length.getLine(), "Not of array type");
		}
		
		return TypeTable.getType("int");
	}

	@Override
	public Object visit(MathBinaryOp binaryOp) {
		Type op1Type = (Type) binaryOp.getFirstOperand().accept(this);
		Type op2Type = (Type) binaryOp.getSecondOperand().accept(this);
	
		if ((op1Type == null) || (op2Type == null)) return null;
		
		if (op1Type != op2Type)
			typeError(binaryOp.getLine(), "Illegal "+binaryOp.getOperator().getOperatorString() + " operation. Both operands types should be the same");
		
		if(binaryOp.getOperator() != BinaryOps.PLUS){
			if (!op1Type.subTypeOf(TypeTable.getType("int"))){
				typeError(binaryOp.getLine(), binaryOp.getOperator().getOperatorString() + " operations are only for two operands of type int");
			}
		}else{
		
		if (!(op1Type.subTypeOf(TypeTable.getType("int")))&&!(op1Type.subTypeOf(TypeTable.getType("string"))))
			typeError(binaryOp.getLine(), binaryOp.getOperator().getOperatorString() + " operation is only for int or string operands");
		}
		
		return TypeTable.getType(op1Type);
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		Type op1Type = (Type) binaryOp.getFirstOperand().accept(this);
		Type op2Type = (Type) binaryOp.getSecondOperand().accept(this);
		BinaryOps op = binaryOp.getOperator();
	
		//TODO how to handle null?
		
		// Check for the || and && operators that both operands are boolean
		if ((op == BinaryOps.LAND)|| (op == BinaryOps.LOR))
			if ((!op1Type.subTypeOf(TypeTable.getType("boolean")))||(!op2Type.subTypeOf(TypeTable.getType("boolean")))){
				typeError(binaryOp.getLine(), "Logical "+ op.getOperatorString() + " on non-boolean types");
			}

		//Check if one operand type is subtype of the other
		if (!(op1Type.subTypeOf(op2Type))&&!(op2Type.subTypeOf(op1Type))){
			if (op == BinaryOps.EQUAL || op == BinaryOps.NEQUAL){
				typeError(binaryOp.getLine(), "Comparing forign types with the operator: " + op.getOperatorString());
			}
			
			else {
				typeError(binaryOp.getLine(), "Comparing non int values with " + op.getOperatorString());
			}
			
		}
	
		return TypeTable.getType("boolean");
	}

	@Override
	public Object visit(MathUnaryOp unaryOp) {
		Type opType = (Type) unaryOp.getOperand().accept(this);
		if (opType != TypeTable.getType("int"))
				typeError(unaryOp.getLine(), "Unary "+ unaryOp.getOperator().getOperatorString() + " with a non-int operand");
		
		return TypeTable.getType("int");
	}

	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		Type opType = (Type) unaryOp.getOperand().accept(this);
		if (opType != TypeTable.getType("boolean"))
				typeError(unaryOp.getLine(), unaryOp.getOperator().getOperatorString() + " with a non-boolean operand");
		
		unaryOp.getOperand().accept(this);
		return TypeTable.getType("boolean");
	}

	@Override
	public Object visit(Literal literal) {
		LiteralTypes type = literal.getType(); 
		switch (type){
			case STRING: return TypeTable.getType("string");
			case INTEGER: return TypeTable.getType("int");
			case TRUE: return TypeTable.getType("boolean");
         	case FALSE: return TypeTable.getType("boolean");
         	case NULL: return TypeTable.getType("null");
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
	
	private void typeError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);
	}

}
