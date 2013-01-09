package IC.Semantic;


import java.util.Iterator;

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
import IC.SymbolTable.MethodSymbol;
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.Symbol;
import IC.SymbolTable.SymbolTable;
import IC.SymbolTable.VarSymbol;
import IC.SymbolTable.FieldSymbol;
import IC.Types.MethodType;
import IC.Types.TypeTable;

public class TypeCheckVisitor implements Visitor {
	private boolean inLoopContext = false;
	private boolean inVirtualMethodContext = false;
		
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
		return TypeTable.getType(field.getType(), false);
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
		return TypeTable.getType(formal.getType(), false);
	}

	@Override
	public Object visit(PrimitiveType type) {
		return TypeTable.getType(type, false);
	}

	@Override
	public Object visit(UserType type) {
		try {
			TypeTable.getUserTypeByName(type.getName());				
		} catch (SemanticError semantic) {
			scopeError(semantic.line, semantic.getMessage());
		}
		return TypeTable.getType(type);
	}

	@Override
	public Object visit(Assignment assignment) {
		
	//type check for assignment
		Type variableType = (Type) assignment.getVariable().accept(this);
		if (variableType == null) 
			return null;
		
		Type assignmentType = (Type) assignment.getAssignment().accept(this);
		if (assignmentType == null) 
			return null;
		
		if (!(assignmentType.subTypeOf(variableType))){
			typeError(assignment.getLine(), "Type mismatch: " + assignmentType);
			
		}
		return null;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		callStatement.getCall().accept(this);
		return null;
	}

	@Override
	public Object visit(Return returnStatement) {	
		if (returnStatement.hasValue()){
			//	Type returnValueType = (Type) returnStatement.getValue().accept(this);
			//TODO
		}
		
		
		return null;
	}

	@Override
	public Object visit(If ifStatement) {
		Type conditionType = (Type) ifStatement.getCondition().accept(this);
			if (conditionType != TypeTable.getType("boolean")){
				typeError(ifStatement.getLine(), "If condition must be of type boolean");
			}
			
		ifStatement.getOperation().accept(this);
		
		
		if(ifStatement.hasElse()){
			ifStatement.getElseOperation().accept(this);
		}
		
		return null;
	}
	
	@Override
	public Object visit(While whileStatement) {
		Type conditionType = (Type)whileStatement.getCondition().accept(this);
		if (conditionType != TypeTable.getType("boolean")){
			typeError(whileStatement.getLine(), "While condition must be of type boolean");
		}
		
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

	@Override
	public Object visit(LocalVariable localVariable) {
		localVariable.getType().accept(this);
		if(localVariable.getInitValue() == null) return null;
		localVariable.getInitValue().accept(this);
		return null;
	}

	@Override
	public Object visit(VariableLocation location) {
		Type expressionType = null;
		if(location.getLocation() == null) {
			Symbol varSymbol = location.getEnclosingScope().lookup(location.getName());
			if (varSymbol == null) {
				scopeError(location.getLine(), "undeclared identifier: " + location.getName());
			}
			if (varSymbol.getKind() == Kind.VARIABLE) {
				if (varSymbol.getLine() > location.getLine()) {
					scopeError(location.getLine(), "variable " + location.getName() + " is used before declaration");
				}
				expressionType = TypeTable.getType(((VarSymbol)varSymbol).getType(), false);
			} else if (varSymbol.getKind() == Kind.FIELD) {
				expressionType = TypeTable.getType(((FieldSymbol)varSymbol).getType(), false);
			} else {
				typeError(location.getLine(), "illegal reference: " + location.getName());
			}			
		} else {
			Type classType = (Type)location.getLocation().accept(this);
			Symbol fieldSymbol = null;
			try {
				SymbolTable classSymbolTable = TypeTable.getUserTypeByName(classType.getName()).getEnclosingScope().getSymbolTable(classType.getName());
				fieldSymbol = classSymbolTable.lookup(location.getName());
			} catch (SemanticError semantic) {
				scopeError(location.getLine(), "no such class");
			}
			if (fieldSymbol == null) {
				scopeError(location.getLine(), "undeclared identifier: " + location.getName());
			}
			if (fieldSymbol.getKind() != Kind.FIELD) {
				typeError(location.getLine(), "illegal reference: " + location.getName());
			}
			expressionType = TypeTable.getType(((FieldSymbol)fieldSymbol).getType(), false);
		}
		return expressionType;
	}

	@Override
	//check that index is of type int
	public Object visit(ArrayLocation location) {
		Type arrayType = (Type) location.getArray().accept(this);
		Type indexType = (Type) location.getIndex().accept(this);
		
		if (indexType != TypeTable.getType("int")){
			typeError(location.getLine(), "Array index must be of type int");
		}
		
		if (arrayType.getDimension() < 1) {
			typeError(location.getLine(), arrayType + " is not an array type");
		}
		
	 
		// TODO don't return array type, but the type of an element in the array
		return arrayType;
		
	}

	@Override
	public Object visit(StaticCall call) {
		String className = call.getClassName();
		ICClass classInstance = null;
		try {
			classInstance = TypeTable.getUserTypeByName(className);
		} catch (SemanticError e) {
			scopeError(call.getLine(), className + ": no such class");
		}
		// query the class' scope for the static method
		SymbolTable classScope = classInstance.getEnclosingScope().getSymbolTable(className);
		String staticMethodName = call.getName();
		Symbol methodSymbol = classScope.staticLookup(staticMethodName);
		if(methodSymbol == null || methodSymbol.getKind() != Kind.METHOD ||
				!((MethodSymbol)methodSymbol).isStatic()){
			scopeError(call.getLine(), staticMethodName + ": no such static method in " + className);	
		}
		MethodType methodType = ((MethodSymbol)methodSymbol).getMetType();
		if (call.getArguments().size() != methodType.getParamTypes().size()) {
			typeError(call.getLine(), "invalid amount of arguments passed to " + staticMethodName);
		}
		Iterator<Type> methodParams = methodType.getParamTypes().iterator();
		for(Expression arg : call.getArguments()){
			Type paramType = TypeTable.getType(methodParams.next(), false);
			Type argType = (Type)arg.accept(this);
			if (argType.subTypeOf(paramType)) {
				typeError(call.getLine(), "argument and parameter type mismatch in call to " + staticMethodName +
						". Expected " + paramType + " and got " + argType);
			}
		}
		return TypeTable.getType(methodType.getReturnType(), false);
	}

	@Override
	public Object visit(VirtualCall call) {
		ICClass instanceClass = null;
		Type instanceType = null;
		SymbolTable classScope = null;
		String instanceClassName = null;
		if(call.isExternal()){
			Expression location = call.getLocation();
			instanceType = (Type)location.accept(this);
			instanceClassName = instanceType.getName();
			try {
				instanceClass = TypeTable.getUserTypeByName(instanceClassName);
			} catch (SemanticError e) {
				typeError(call.getLine(), e.getMessage());
			}
			classScope = instanceClass.getEnclosingScope().getSymbolTable(instanceClassName);
		} else {
			try {
				classScope = call.getEnclosingClassTable();
				instanceClass = TypeTable.getUserTypeByName(classScope.getName());
				instanceClassName = instanceClass.getName();
			} catch (SemanticError e) {
				typeError(call.getLine(), e.getMessage());
			}
		}
		String methodName = call.getName();
		Symbol methodSymbol = null;
		if (!call.isExternal()) {
			methodSymbol = classScope.staticLookup(methodName);
		}
		if (methodSymbol == null) {
			methodSymbol = classScope.lookup(methodName);
			if (methodSymbol != null && methodSymbol.getKind() == Kind.METHOD && ((MethodSymbol)methodSymbol).isStatic()) {
				methodSymbol = null;
			}
		}
		if(methodSymbol == null || methodSymbol.getKind() != Kind.METHOD){
			scopeError(call.getLine(), methodName + ": no such method in " + instanceClassName);	
		}
		MethodType methodType = ((MethodSymbol)methodSymbol).getMetType();
		if (call.getArguments().size() != methodType.getParamTypes().size()) {
			typeError(call.getLine(), "invalid amount of arguments passed to " + methodName);
		}
		Iterator<Type> methodParams = methodType.getParamTypes().iterator();
		for(Expression arg : call.getArguments()){
			Type paramType = TypeTable.getType(methodParams.next(), false);
			Type argType = (Type)arg.accept(this);
			if (!argType.subTypeOf(paramType)) {
				typeError(call.getLine(), "argument and parameter type mismatch in call to " + methodName +
						". Expected " + paramType + " and got " + argType);
			}
		}
		return TypeTable.getType(methodType.getReturnType(), false);
	}

	@Override
	public Object visit(This thisExpression) {
		return TypeTable.getType(new UserType(thisExpression.getLine(), thisExpression.getEnclosingClassTable().getName()));
	}

	@Override
	public Object visit(NewClass newClass) {
		String className = newClass.getName();
		Type classType = null;
		classType = TypeTable.getType(new UserType(newClass.getLine(), className));
		return classType;
	}

	@Override
	//Typecheck for newArray
	public Object visit(NewArray newArray) {
		Type sizeType = (Type) newArray.getSize().accept(this);
		// TODO must check that array size is positive
		Type arrayType = (Type) newArray.getType().accept(this);
		
		if (sizeType != TypeTable.getType("int"))
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
			typeError(binaryOp.getLine(), "Illegal "+binaryOp.getOperator().getOperatorString() + " operation. Both operands' types should be the same");
		
		if(binaryOp.getOperator() != BinaryOps.PLUS){
			if (op1Type != TypeTable.getType("int")){
				typeError(binaryOp.getLine(), binaryOp.getOperator().getOperatorString() + " operations are only for two operands of type int");
			}
		} else {
			if (op1Type != TypeTable.getType("int") && op1Type != TypeTable.getType("string"))
				typeError(binaryOp.getLine(), binaryOp.getOperator().getOperatorString() + " operation is only for int or string operands");
		}
		return TypeTable.getType(op1Type, false);
	}

	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		Type op1Type = (Type) binaryOp.getFirstOperand().accept(this);
		Type op2Type = (Type) binaryOp.getSecondOperand().accept(this);
		BinaryOps op = binaryOp.getOperator();
	
		//TODO how to handle null?
		
		// Check for the || and && operators that both operands are boolean
		if ((op == BinaryOps.LAND)|| (op == BinaryOps.LOR)) {
			if (op1Type != TypeTable.getType("boolean") || op2Type != TypeTable.getType("boolean")){
				typeError(binaryOp.getLine(), "Logical "+ op.getOperatorString() + " on non-boolean types");
			}
		}
		//Check if one operand type is subtype of the other
		if (op == BinaryOps.EQUAL || op == BinaryOps.NEQUAL){
			if (!(op1Type.subTypeOf(op2Type)) && !(op2Type.subTypeOf(op1Type))){
				typeError(binaryOp.getLine(), "Comparing foreign types with the operator: " + op.getOperatorString());
			}
		}
		//
		if (op == BinaryOps.LTE || op == BinaryOps.LT || op == BinaryOps.GTE || op == BinaryOps.GT) {
			if (op1Type != TypeTable.getType("int") || op2Type != TypeTable.getType("int")) {
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
	
	private void scopeError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);
	}

}
