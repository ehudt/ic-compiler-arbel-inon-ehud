package IC.AST;

import java.util.List;

import IC.SymbolTable.BlockSymbolTable;
import IC.SymbolTable.ClassSymbol;
import IC.SymbolTable.ClassSymbolTable;
import IC.SymbolTable.FieldSymbol;
import IC.SymbolTable.GlobalSymbolTable;
import IC.SymbolTable.MethodSymbol;
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.SymbolTable;
import IC.SymbolTable.VarSymbol;

/**
 * Pretty printing visitor - travels along the AST and prints info about each
 * node, in an easy-to-comprehend format.
 * 
 * @author Tovi Almozlino
 */
public class PrettyPrinter implements Visitor {

	private int depth = 0; // depth of indentation

	private String ICFilePath;

	/**
	 * Constructs a new pretty printer visitor.
	 * 
	 * @param ICFilePath
	 *            The path + name of the IC file being compiled.
	 */
	public PrettyPrinter(String ICFilePath) {
		this.ICFilePath = ICFilePath;
	}

	private void indent(StringBuffer output, ASTNode node) {
		output.append("\n");
		for (int i = 0; i < depth; ++i)
			output.append(" ");
		if (node != null)
			output.append(node.getLine() + ": ");
	}

	private void indent(StringBuffer output) {
		indent(output, null);
	}

	public Object visit(Program program) {
		StringBuffer output = new StringBuffer();

		indent(output);
		output.append("Abstract Syntax Tree: " + ICFilePath + "\n");
		for (ICClass icClass : program.getClasses())
			output.append(icClass.accept(this));
		return output.toString();
	}

	public Object visit(ICClass icClass) {
		StringBuffer output = new StringBuffer();
		
		indent(output, icClass);
		output.append("Declaration of class: " + icClass.getName());
		if (icClass.hasSuperClass())
			output.append(", subclass of " + icClass.getSuperClassName());
		depth += 2;
		for (Field field : icClass.getFields())
			output.append(field.accept(this));
		for (Method method : icClass.getMethods())
			output.append(method.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(PrimitiveType type) {
		StringBuffer output = new StringBuffer();

		indent(output, type);
		output.append("Primitive data type: ");
		if (type.getDimension() > 0)
			output.append(type.getDimension() + "-dimensional array of ");
		output.append(type.getName());
		return output.toString();
	}

	public Object visit(UserType type) {
		StringBuffer output = new StringBuffer();

		indent(output, type);
		output.append("User-defined data type: ");
		if (type.getDimension() > 0)
			output.append(type.getDimension() + "-dimensional array of ");
		output.append(type.getName());
		return output.toString();
	}

	public Object visit(Field field) {
		StringBuffer output = new StringBuffer();

		indent(output, field);
		output.append("Declaration of field: " + field.getName());
		++depth;
		output.append(field.getType().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(LibraryMethod method) {
		StringBuffer output = new StringBuffer();

		indent(output, method);
		output.append("Declaration of library method: " + method.getName());
		depth += 2;
		output.append(method.getType().accept(this));
		for (Formal formal : method.getFormals())
			output.append(formal.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(Formal formal) {
		StringBuffer output = new StringBuffer();

		indent(output, formal);
		output.append("Parameter: " + formal.getName());
		++depth;
		output.append(formal.getType().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(VirtualMethod method) {
		StringBuffer output = new StringBuffer();

		indent(output, method);
		output.append("Declaration of virtual method: " + method.getName());
		depth += 2;
		output.append(method.getType().accept(this));
		for (Formal formal : method.getFormals())
			output.append(formal.accept(this));
		for (Statement statement : method.getStatements())
			output.append(statement.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(StaticMethod method) {
		StringBuffer output = new StringBuffer();

		indent(output, method);
		output.append("Declaration of static method: " + method.getName());
		depth += 2;
		output.append(method.getType().accept(this));
		for (Formal formal : method.getFormals())
			output.append(formal.accept(this));
		for (Statement statement : method.getStatements())
			output.append(statement.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(Assignment assignment) {
		StringBuffer output = new StringBuffer();

		indent(output, assignment);
		output.append("Assignment statement");
		depth += 2;
		output.append(assignment.getVariable().accept(this));
		output.append(assignment.getAssignment().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(CallStatement callStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, callStatement);
		output.append("Method call statement");
		++depth;
		output.append(callStatement.getCall().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(Return returnStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, returnStatement);
		output.append("Return statement");
		if (returnStatement.hasValue())
			output.append(", with return value");
		if (returnStatement.hasValue()) {
			++depth;
			output.append(returnStatement.getValue().accept(this));
			--depth;
		}
		return output.toString();
	}

	public Object visit(If ifStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, ifStatement);
		output.append("If statement");
		if (ifStatement.hasElse())
			output.append(", with Else operation");
		depth += 2;
		output.append(ifStatement.getCondition().accept(this));
		output.append(ifStatement.getOperation().accept(this));
		if (ifStatement.hasElse())
			output.append(ifStatement.getElseOperation().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(While whileStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, whileStatement);
		output.append("While statement");
		depth += 2;
		output.append(whileStatement.getCondition().accept(this));
		output.append(whileStatement.getOperation().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(Break breakStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, breakStatement);
		output.append("Break statement");
		return output.toString();
	}

	public Object visit(Continue continueStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, continueStatement);
		output.append("Continue statement");
		return output.toString();
	}

	public Object visit(StatementsBlock statementsBlock) {
		StringBuffer output = new StringBuffer();

		indent(output, statementsBlock);
		output.append("Block of statements");
		depth += 2;
		for (Statement statement : statementsBlock.getStatements())
			output.append(statement.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(LocalVariable localVariable) {
		StringBuffer output = new StringBuffer();

		indent(output, localVariable);
		output.append("Declaration of local variable: "
				+ localVariable.getName());
		if (localVariable.hasInitValue()) {
			output.append(", with initial value");
			++depth;
		}
		++depth;
		output.append(localVariable.getType().accept(this));
		if (localVariable.hasInitValue()) {
			output.append(localVariable.getInitValue().accept(this));
			--depth;
		}
		--depth;
		return output.toString();
	}

	public Object visit(VariableLocation location) {
		StringBuffer output = new StringBuffer();

		indent(output, location);
		output.append("Reference to variable: " + location.getName());
		if (location.isExternal())
			output.append(", in external scope");
		if (location.isExternal()) {
			++depth;
			output.append(location.getLocation().accept(this));
			--depth;
		}
		return output.toString();
	}

	public Object visit(ArrayLocation location) {
		StringBuffer output = new StringBuffer();

		indent(output, location);
		output.append("Reference to array");
		depth += 2;
		output.append(location.getArray().accept(this));
		output.append(location.getIndex().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(StaticCall call) {
		StringBuffer output = new StringBuffer();

		indent(output, call);
		output.append("Call to static method: " + call.getName()
				+ ", in class " + call.getClassName());
		depth += 2;
		for (Expression argument : call.getArguments())
			output.append(argument.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(VirtualCall call) {
		StringBuffer output = new StringBuffer();

		indent(output, call);
		output.append("Call to virtual method: " + call.getName());
		if (call.isExternal())
			output.append(", in external scope");
		depth += 2;
		if (call.isExternal())
			output.append(call.getLocation().accept(this));
		for (Expression argument : call.getArguments())
			output.append(argument.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(This thisExpression) {
		StringBuffer output = new StringBuffer();

		indent(output, thisExpression);
		output.append("Reference to 'this' instance");
		return output.toString();
	}

	public Object visit(NewClass newClass) {
		StringBuffer output = new StringBuffer();

		indent(output, newClass);
		output.append("Instantiation of class: " + newClass.getName());
		return output.toString();
	}

	public Object visit(NewArray newArray) {
		StringBuffer output = new StringBuffer();

		indent(output, newArray);
		output.append("Array allocation");
		depth += 2;
		output.append(newArray.getType().accept(this));
		output.append(newArray.getSize().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(Length length) {
		StringBuffer output = new StringBuffer();

		indent(output, length);
		output.append("Reference to array length");
		++depth;
		output.append(length.getArray().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(MathBinaryOp binaryOp) {
		StringBuffer output = new StringBuffer();

		indent(output, binaryOp);
		output.append("Mathematical binary operation: "
				+ binaryOp.getOperator().getDescription());
		depth += 2;
		output.append(binaryOp.getFirstOperand().accept(this));
		output.append(binaryOp.getSecondOperand().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(LogicalBinaryOp binaryOp) {
		StringBuffer output = new StringBuffer();

		indent(output, binaryOp);
		output.append("Logical binary operation: "
				+ binaryOp.getOperator().getDescription());
		depth += 2;
		output.append(binaryOp.getFirstOperand().accept(this));
		output.append(binaryOp.getSecondOperand().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(MathUnaryOp unaryOp) {
		StringBuffer output = new StringBuffer();

		indent(output, unaryOp);
		output.append("Mathematical unary operation: "
				+ unaryOp.getOperator().getDescription());
		++depth;
		output.append(unaryOp.getOperand().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(LogicalUnaryOp unaryOp) {
		StringBuffer output = new StringBuffer();

		indent(output, unaryOp);
		output.append("Logical unary operation: "
				+ unaryOp.getOperator().getDescription());
		++depth;
		output.append(unaryOp.getOperand().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(Literal literal) {
		StringBuffer output = new StringBuffer();

		indent(output, literal);
		output.append(literal.getType().getDescription() + ": "
				+ literal.getType().toFormattedString(literal.getValue()));
		return output.toString();
	}

	public Object visit(ExpressionBlock expressionBlock) {
		StringBuffer output = new StringBuffer();

		indent(output, expressionBlock);
		output.append("Parenthesized expression");
		++depth;
		output.append(expressionBlock.getExpression().accept(this));
		--depth;
		return output.toString();
	}

	@Override
	public Object visit(FieldMethodList fieldMethodList) {
		StringBuffer output = new StringBuffer();
		
		indent(output, fieldMethodList);
		output.append("Field method list");
		depth += 2;
		for (Field field : fieldMethodList.getFieldList())
			output.append(field.accept(this));
		for (Method method : fieldMethodList.getMethodList())
			output.append(method.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(EmptyStatement statement){
		StringBuffer output = new StringBuffer();

		indent(output, statement);
		output.append("Syntax error in statement");
		
		return output.toString();
	}
	
	@Override
	public Object visit(ErrorMethod errorMethod) {
		StringBuffer output = new StringBuffer();

		indent(output, errorMethod);
		output.append("Syntax error in method declaration");
		
		return output.toString();
	}

	@Override
	public Object visit(ErrorClass errorClass) {
		StringBuffer output = new StringBuffer();

		indent(output, errorClass);
		output.append("Syntax error in class declaration");
		
		return output.toString();
	}

	@Override
	public Object visit(Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(GlobalSymbolTable table) {
		depth = 0;
		StringBuffer str = new StringBuffer();
		str.append("Global Symbol Table: ");
		str.append(ICFilePath);
		depth += 4;
		for(ClassSymbol symbol : table.getClassSymbols()){
			indent(str);
			str.append(symbol.toString());
		}
		depth -= 4;
		indent(str);
		str.append(printChildrenTables(table));
		return str.toString();
	}

	@Override
	public Object visit(ClassSymbolTable table) {
		depth = 0;
		StringBuffer str = new StringBuffer();
		str.append("Class Symbol Table: ");
		str.append(table.getName());
		depth += 4;
		for(FieldSymbol symbol : table.getFieldSymbols()){
			indent(str);
			str.append(symbol.toString());
		}
		for(MethodSymbol symbol : table.getMethodSymbols()){
			indent(str);
			str.append(symbol.toString());
		}
		depth -= 4;
		indent(str);
		str.append(printChildrenTables(table));
		return str.toString();
	}

	@Override
	public Object visit(BlockSymbolTable table) {
		depth = 0;
		StringBuffer str = new StringBuffer();
		str.append("Statement Block Symbol Table ");
		str.append("( located in ");
		str.append(table.getParent().toString());
		str.append(" )");
		depth += 4;
		for(VarSymbol symbol : table.getLocalSymbols()){
			indent(str);
			str.append(symbol.toString());
		}
		depth -= 4;
		indent(str);
		str.append(printChildrenTables(table));
		return str.toString();
	}

	@Override
	public Object visit(MethodSymbolTable table) {
		depth = 0;
		StringBuffer str = new StringBuffer();
		str.append("Method Symbol Table: ");
		str.append(table.getName());
		depth += 4;
		for(VarSymbol param : table.getParameterSymbols()){
			indent(str);
			str.append(param.toString());
		}
		for(VarSymbol local : table.getLocalSymbols()){
			indent(str);
			str.append(local.toString());
		}
		depth -= 4;
		indent(str);
		str.append(printChildrenTables(table));
		return str.toString();
	}

	@Override
	public Object visit(SymbolTable symbolTable) {
		//return symbolTable.accept(this);
		return null;
	}
	
	private Object printChildrenTables(SymbolTable table) {
		StringBuffer str = new StringBuffer();
		if(table.getSymbolTables().size() == 0)
			return "";
		str.append("Children tables: ");
		str.append(printListNicely(table.getSymbolTables()));
		indent(str);
		for(SymbolTable child : table.getSymbolTables()){
			indent(str);
			str.append(child.accept(this));
		}
		return str.toString();
	}
	
	public static String printListNicely(List<? extends Object> list){
		StringBuffer str = new StringBuffer();
		String delim="";
		for(Object item : list){
			str.append(delim);
			str.append(item);
			delim = ", ";
		}
		return str.toString();
	}

}