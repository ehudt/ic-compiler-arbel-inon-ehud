package IC.Semantic;

import java.util.Iterator;

import sun.org.mozilla.javascript.regexp.SubString;

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

	/**
	 * Checks that:
	 * The assignment type is subtype of the variable to be assigned in.
	 * Returns null, or an error of there is one. 
	 */
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
			typeError(assignment.getLine(), "Type mismatch: " + assignmentType + " cannot be assigned to " + variableType);
			
		}
		return null;
	}

	@Override
	public Object visit(CallStatement callStatement) {
		callStatement.getCall().accept(this);
		return null;
	}

	/**
	 * Checks that the return value code is a subtype of the method type
	 */
	@Override
	public Object visit(Return returnStatement) {
		Type returnValueType = null;
		if (returnStatement.hasValue()){
			returnValueType = (Type)returnStatement.getValue().accept(this);
		} else {
			returnValueType = TypeTable.getType("void");
		}
		String currentMethodName = returnStatement.getEnclosingScope().getCurrentMethodName();
		MethodSymbol currentMethodSymbol = (MethodSymbol)returnStatement.getEnclosingScope().lookup(currentMethodName);
		MethodType currentMethodType = currentMethodSymbol.getMetType();
		Type methodReturnType = TypeTable.getType(currentMethodType.getReturnType());
		if(!returnValueType.subTypeOf(methodReturnType)) {
			typeError(returnStatement.getLine(), "return value of method " + currentMethodName + " must be " + methodReturnType);
		}
		
		
		return null;
	}

	/**
	 * Checks types in an If/Else statement.
	 * Makes sure that the if condition is of type boolean.
	 * Returns null, or sends an error if there is one.
	 */
	@Override
	public Object visit(If ifStatement) {
		Type conditionType = (Type) ifStatement.getCondition().accept(this);
			if (conditionType != TypeTable.getType("boolean")){
				typeError(ifStatement.getLine(), "if condition expression must be of type boolean");
			}
			
		ifStatement.getOperation().accept(this);
		
		
		if(ifStatement.hasElse()){
			ifStatement.getElseOperation().accept(this);
		}
		
		return null;
	}
	
	/**
	 * Checks in a while statement that the condition is of type boolean.
	 * Retruns null or an Error.  
	 */
	@Override
	public Object visit(While whileStatement) {
		Type conditionType = (Type)whileStatement.getCondition().accept(this);
		if (conditionType != TypeTable.getType("boolean")){
			typeError(whileStatement.getLine(), "while condition expression must be of type boolean");
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

	/**
	 * checks that the variable type is a subtype of the local variables type
	 */
	@Override
	public Object visit(LocalVariable localVariable) {
		localVariable.getType().accept(this);
		Type varType = TypeTable.getType(localVariable.getType(), false);
		if(localVariable.getInitValue() != null) {
			Type initValueType = (Type)localVariable.getInitValue().accept(this);
			if (!initValueType.subTypeOf(varType)){
				typeError(localVariable.getLine(), "type mismatch: expected " + varType + " and got " + initValueType);
			}
		}
		
		return null;
	}

	/**
	 * Returns the location type, or error if found one.
	 * Implements the checks for the bonus: Checks if the variable was declared before 
	 * it is being used, and send an error otherwise.
	 */
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
				scopeError(location.getLine(), "no such class: " + classType.getName());
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

	/** 
	 * ArrayLocation: check that the index is of type int. Return element type, or send 
	 * an error if there is one.
	 */
	@Override
	//check that index is of type int
	public Object visit(ArrayLocation location) {
		Type arrayType = (Type) location.getArray().accept(this);
		Type indexType = (Type) location.getIndex().accept(this);
		
		if (indexType != TypeTable.getType("int")){
			typeError(location.getLine(), "array index must be of type int");
		}
		
		if (arrayType.getDimension() < 1) {
			typeError(location.getLine(), arrayType + " is not an array type");
		}
		
		String typeName = arrayType.toString();
		String elementType = typeName.substring(0, typeName.length()-2);

		return TypeTable.getType(elementType);
		
	}

	/**
	 * check types of arguments, check that the method is defined in the correct class
	 * Check that all arguments correspond to the method's arguments types
	 * send an error if there is one, return the method return value otherwise.
	 */
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
			if (!argType.subTypeOf(paramType)) {
				typeError(call.getLine(), "argument and parameter type mismatch in call to " + staticMethodName +
						". Expected " + paramType + " and got " + argType);
			}
		}
		return TypeTable.getType(methodType.getReturnType(), false);
	}
	
	 /**
	   * 
	   * Check that the method is defined in the relevant class
       * Check that all arguments correspond to the method's arguments types
       * Return the method return value, or error if found one.
       * 
       */
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

	/**
	 * Checks new class expression:
	 * Checks that the class type is legal and exists.
	 */
	@Override
	public Object visit(NewClass newClass) {
		String className = newClass.getName();
		Type classType = null;
		classType = TypeTable.getType(new UserType(newClass.getLine(), className));
		return classType;
	}

	/**
	 *Checks new array expression:
	 *Checks that element type is a legal type and that the size is of type int.
	 * Returns the array type.
	 */
	@Override
	public Object visit(NewArray newArray) {
		newArray.getType().accept(this);
		Type tmpType = TypeTable.getType(newArray.getType(), false);
		Type arrayType = TypeTable.getType(tmpType.clone(tmpType.getDimension() + 1));
		
		Type sizeType = (Type) newArray.getSize().accept(this);	
		if (sizeType != TypeTable.getType("int")) {
			typeError(newArray.getLine(), "array size must be of type int");
		}
		
		return arrayType;
	}

	/**
	 * 
	 * visitor for array.length
	 * Checks that it's an array type and returns int
	 * 
	 */
	@Override
	public Object visit(Length length) {
		Type arrType = (Type) length.getArray().accept(this);
		
		if( arrType.getDimension() < 1 ){
			typeError(length.getLine(), "length is applicable only for array types");
		}
		
		return TypeTable.getType("int");
	}

	/**
	 * 
	 * Checks for types in math binary operations.
	 * for + operation: gets either 2 ints or 2 strings. Return int or string respectably. 
	 * for {/,*,-} operations, gets 2 ints and return int. 
	 * Send an error otherwise
	 * 
	 */
	@Override
	public Object visit(MathBinaryOp binaryOp) {
		Type op1Type = (Type) binaryOp.getFirstOperand().accept(this);
		Type op2Type = (Type) binaryOp.getSecondOperand().accept(this);
	
		if ((op1Type == null) || (op2Type == null)) return null;
		
		if (op1Type != op2Type)
			typeError(binaryOp.getLine(), "Illegal " + binaryOp.getOperator().getOperatorString() + " operation. Both operands' types must be the same");
		
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

	/**
	 * 
	 * Checks for types in logical binary operations. Returns boolean.
	 * Different operands types for different operations:
	 * just booleans for &&, ||. 
	 * ints for: >,<,>=,<=
	 * subtypes for ==,!=
	 * Send an error otherwise
	 *  
	 */
	@Override
	public Object visit(LogicalBinaryOp binaryOp) {
		Type op1Type = (Type) binaryOp.getFirstOperand().accept(this);
		Type op2Type = (Type) binaryOp.getSecondOperand().accept(this);
		BinaryOps op = binaryOp.getOperator();
	
		
		// Check for the || and && operators that both operands are boolean
		if ((op == BinaryOps.LAND)|| (op == BinaryOps.LOR)) {
			if (op1Type != TypeTable.getType("boolean") || op2Type != TypeTable.getType("boolean")){
				typeError(binaryOp.getLine(), "Logical "+ op.getOperatorString() + " is applicable only for boolean operands");
			}
		}
		//Check if one operand type is subtype of the other
		if (op == BinaryOps.EQUAL || op == BinaryOps.NEQUAL){
			if (!(op1Type.subTypeOf(op2Type)) && !(op2Type.subTypeOf(op1Type))){
				typeError(binaryOp.getLine(), "comparison of foreign types with the operator: " + op.getOperatorString());
			}
		}
		//
		if (op == BinaryOps.LTE || op == BinaryOps.LT || op == BinaryOps.GTE || op == BinaryOps.GT) {
			if (op1Type != TypeTable.getType("int") || op2Type != TypeTable.getType("int")) {
				typeError(binaryOp.getLine(), "comparison of non int values with " + op.getOperatorString());
			}
		}
		return TypeTable.getType("boolean");
	}

	/**
	 * 
	 * Check the unary math operation: -
	 * operand should be of type int and returns int. Otherwise: Send a type error.
	 * 
	 */
	@Override	
	public Object visit(MathUnaryOp unaryOp) {
		Type opType = (Type) unaryOp.getOperand().accept(this);
		if (opType != TypeTable.getType("int"))
				typeError(unaryOp.getLine(), "Unary "+ unaryOp.getOperator().getOperatorString() + " with a non-int operand");
		return TypeTable.getType("int");
	}

	/**
	 * 
	 * Checks for the unary logical operation: !
	 * checks that unaryOp is of type boolean, and returns boolean. Otherwise send a type error. 
	 * 
	 */
	@Override
	public Object visit(LogicalUnaryOp unaryOp) {
		Type opType = (Type) unaryOp.getOperand().accept(this);
		if (opType != TypeTable.getType("boolean"))
				typeError(unaryOp.getLine(), unaryOp.getOperator().getOperatorString() + " with a non-boolean operand");
		return TypeTable.getType("boolean");
	}

	/**
	 *
	 * return the type of the literal
	 * 
	 */
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
	
	//prints a typeError and exit from the program
	private void typeError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);
	}
	
	//prints a scopeError and exit from the program 
	private void scopeError(int line, String message) {
		System.out.println("semantic error at line " + line + ": " + message);
		System.exit(0);
	}

}
