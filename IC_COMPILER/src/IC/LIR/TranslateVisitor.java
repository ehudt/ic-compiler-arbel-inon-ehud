package IC.LIR;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import IC.BinaryOps;
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
import IC.AST.While;
import IC.Semantic.TypeCheckVisitor;
import IC.SymbolTable.BlockSymbolTable;
import IC.SymbolTable.ClassSymbolTable;
import IC.SymbolTable.GlobalSymbolTable;
import IC.SymbolTable.Kind;
import IC.SymbolTable.MethodSymbolTable;
import IC.SymbolTable.Symbol;
import IC.SymbolTable.SymbolTable;
import IC.Types.TypeTable;
import IC.LIR.ClassLayout;

public class TranslateVisitor implements PropagatingVisitor<LirBlock, Integer>{
	
	private Map<String, String> stringLiterals = new LinkedHashMap<String, String>();
	private Map<String,ClassLayout> classLayouts = new LinkedHashMap<String, ClassLayout>();
	private int labelCount = 1;
	private int currentLoopLabel = -1;
	
	private int getNextLabelNum() {
		return labelCount++;
	}
	static final String runTimeErrorStrings = 
			"str_size_error: \"Runtime Error: Array allocation with negative array size!\"\n" +
			"str_null_ref_error: \"Runtime Error: Null pointer dereference!\"\n" +
			"str_div_zero: \"Runtime Error: Division by zero!\"\n" +
			"str_arr_access: \"Runtime Error: Array index out of bounds!\"\n";
	
	static final String runTimeChecks = "" +
			"__checkZero:\n"+
			"Move b,Rcheck1\n"+
			"Compare 0,Rcheck1\n"+
			"JumpTrue _err_zero\n"+
			"Return 9999\n"+
			"_err_zero:\n"+
			"Library __println(str_div_zero),Rdummy\n"+
			"Jump _runtime_error\n" +
			"\n" +
			"__checkArrayAccess:\n" +
			"Move a,Rcheck1\n" +
			"Move i,Rcheck2\n" +
			"Compare 0,Rcheck2\n" +
			"JumpL _err_access\n" +
			"ArrayLength Rcheck1,Rcheck1\n" +
			"Compare Rcheck1,Rcheck2\n" +
			"JumpGE _err_access\n" +
			"Return 9999\n" +
			"_err_access:\n" +
			"Library __println(str_arr_access),Rdummy\n" +
			"Jump _runtime_error\n" +
			"\n" +
			"__checkSize:\n" +
			"Move n,Rcheck1\n" +
			"Compare 0,Rcheck1\n" +
			"JumpLE _size_error\n" +
			"Return 9999\n" +
			"_size_error:\n" +
			"Library __println(str_size_error),Rdummy\n" +
			"Jump _runtime_error\n" +
			"\n" +
			"__checkNullRef:\n" +
			"Move o,Rcheck1\n" +
			"Compare 0,Rcheck1\n" +
			"JumpTrue _null_ref_error\n" +
			"Return 9999\n" +
			"_null_ref_error:\n" +
			"Library __println(str_null_ref_error),Rdummy\n" +
			"Jump _runtime_error\n\n";
	
	@Override
	public LirBlock visit(Program program, Integer targetReg) {
		StringBuilder programCode = new StringBuilder();
				
		
		/* visit the program */
		StringBuilder classesCode = new StringBuilder();
		for (ICClass classDecl : program.getClasses()) {
			if (classDecl.getName().equals("Library")) continue;
			LirBlock classLirBlock = classDecl.accept(this, targetReg);
			classesCode.append(classLirBlock.getLirCode());
		}
		
		/* generate the string literals' code */
		for (String label : stringLiterals.keySet()) {
			programCode.append(stringLiterals.get(label));
			programCode.append(": ");
			programCode.append(label);
			programCode.append("\n");
		}
		// put static strings into the program
		programCode.append(runTimeErrorStrings);
		programCode.append("\n");
		
		/* generate DVs' code */
		for (ICClass classDecl : program.getClasses()) {
			if (classDecl.getName().equals("Library")) continue;
			String dvStr = classLayouts.get(classDecl.getName()).getDispatchVector();
			programCode.append(dvStr);
			programCode.append("\n");
		}
		programCode.append("\n");
		
		programCode.append(runTimeChecks);
		
		programCode.append(classesCode);
		
		programCode.append("\n_runtime_error:\n");
		
		return new LirBlock(programCode, targetReg);
	}

	@Override
	public LirBlock visit(ICClass icClass, Integer targetReg) {
		classLayouts.put(icClass.getName(), ClassLayout.NewClassLayout(icClass));
		
		StringBuilder classBody = new StringBuilder();
		for (Method method : icClass.getMethods()) {
			if (method.getName().equals("main")) {
				classBody.append("_ic_main:\n");
			} /*else*/ {
				classBody.append("_" + icClass.getName() + "_" + method.getName() + ":\n");
			}
			LirBlock methodCode = method.accept(this, targetReg);
			classBody.append(methodCode.getLirCode());
			if (method.getName().equals("main")) classBody.append("Library __exit(0),Rdummy\n");
		}
		return new LirBlock(classBody, targetReg);
	}

	@Override
	public LirBlock visit(Field field, Integer targetReg) {
		// this method shouldn't be reached
		return null;
	}

	@Override
	public LirBlock visit(VirtualMethod method, Integer targetReg) {
		StringBuilder methodBody = new StringBuilder();
		
		for (Statement statement : method.getStatements()) {
			LirBlock statementCode = statement.accept(this, targetReg);
			methodBody.append(statementCode.getLirCode());
		}
		
		return new LirBlock(methodBody, targetReg);
	}

	@Override
	public LirBlock visit(StaticMethod method, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		
		for (Statement s : method.getStatements()){
			lirCode.append(s.accept(this, targetReg).getLirCode());
		}
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(LibraryMethod method, Integer targetReg) {
		return new LirBlock(new StringBuilder(), targetReg);
	}

	@Override
	public LirBlock visit(Formal formal, Integer targetReg) {
		return new LirBlock(new StringBuilder(), targetReg);
	}

	@Override
	public LirBlock visit(PrimitiveType type, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(UserType type, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(Assignment assignment, Integer targetReg) {
		StringBuilder assignCode = new StringBuilder();		
		LirBlock exprBlock = assignment.getAssignment().accept(this, targetReg);
		assignCode.append(exprBlock.getLirCode());
		LirBlock varBlock = assignment.getVariable().accept(this, targetReg + 1);
		assignCode.append(varBlock.getLirCode());
		switch(varBlock.getValueType()) {
			case VARIABLE:
				VariableLocation varLocation = (VariableLocation)assignment.getVariable();
				assignCode.append("Move R" + targetReg + "," + 
									varLocation.getName() + varLocation.getEnclosingScope().getBlockDepth() +
									"\n");
				break;
			case FIELD:
				assignCode.append("MoveField R" + targetReg + "," +
									"R" + (targetReg+1) + ".R" + (targetReg+2) + "\n");
				break;
			case ARRAY_LOCATION:
				assignCode.append("MoveArray R" + targetReg + "," +
									"R" + (targetReg+1) + "[R" + (targetReg+2) + "]\n");
				break;			
		}
		
		return new LirBlock(assignCode, targetReg);
	}

	@Override
	public LirBlock visit(CallStatement callStatement, Integer targetReg) {
		return callStatement.getCall().accept(this, targetReg);
	}

	@Override
	public LirBlock visit(Return returnStatement, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		
		if(returnStatement.hasValue())
		{
			LirBlock ret= returnStatement.getValue().accept(this,targetReg);
			lirCode.append(ret.getLirCode());
			lirCode.append("\n");
			lirCode.append("Return R"+targetReg);
			lirCode.append("\n");
		}
		else
		{
			lirCode.append("Return 9999");
			lirCode.append("\n");
		}
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(If ifStatement, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		Integer labelNumber = getNextLabelNum();
		String falseLabel = "_false_label" + labelNumber.toString();
		String endLabel = "_end_label"+labelNumber.toString();
		
		LirBlock condition = ifStatement.getCondition().accept(this, targetReg);
		LirBlock trueStatement = ifStatement.getOperation().accept(this,targetReg);
		
		lirCode.append(condition.getLirCode());
		lirCode.append("Compare 0,R");
		lirCode.append(condition.getTargetRegister()+"\n");
		if (ifStatement.hasElse()){
			LirBlock elseStatement = ifStatement.getElseOperation().accept(this, targetReg);
			lirCode.append("JumpTrue "+falseLabel+"\n");
			lirCode.append(trueStatement.getLirCode());
			lirCode.append("Jump "+endLabel+"\n");
			lirCode.append(falseLabel+":\n");
			lirCode.append(elseStatement.getLirCode());
			lirCode.append(endLabel+":\n");
			}
		else{
			lirCode.append("JumpTrue "+endLabel+"\n");
			lirCode.append(trueStatement.getLirCode());
			lirCode.append(endLabel+":\n");
		}
			
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(While whileStatement, Integer targetReg) {
		StringBuilder whileCode = new StringBuilder();
		Integer labelNum = getNextLabelNum();
		whileCode.append("_test_label" + labelNum + ": \n");
		
		LirBlock conditionCode = whileStatement.getCondition().accept(this, targetReg);
		whileCode.append(conditionCode.getLirCode());
		whileCode.append("Compare 0,R" + conditionCode.getTargetRegister() + "\n");
		whileCode.append("JumpTrue _end_label" + labelNum + "\n");
		
		Integer prevLoopLabel = currentLoopLabel;
		currentLoopLabel = labelNum;
		LirBlock operationCode = whileStatement.getOperation().accept(this, targetReg);
		currentLoopLabel = prevLoopLabel;
		whileCode.append(operationCode.getLirCode());
		whileCode.append("Jump _test_label" + labelNum + "\n");
		whileCode.append("_end_label" + labelNum + ":");
		
		return new LirBlock(whileCode, targetReg);
	}

	@Override
	public LirBlock visit(Break breakStatement, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder("Jump _end_label" + currentLoopLabel+ "\n");
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(Continue continueStatement, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder("Jump _test_label" + currentLoopLabel+ "\n");
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(StatementsBlock statementsBlock, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		for (Statement s : statementsBlock.getStatements()){
			lirCode.append(s.accept(this,targetReg).getLirCode());
			lirCode.append("\n");
		}
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(LocalVariable localVariable, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		if(localVariable.hasInitValue())
		{
			LirBlock init_val = localVariable.getInitValue().accept(this,targetReg);
			lirCode.append(init_val.getLirCode());
			lirCode.append("Move R"+targetReg+","+localVariable.getName()+localVariable.getEnclosingScope().getBlockDepth() + "\n");
		}
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(VariableLocation location, Integer targetReg) {
		StringBuilder locationCode = new StringBuilder();
		if (location.isExternal()) {
			LirBlock locExpr = location.getLocation().accept(this, targetReg);
			locationCode.append(locExpr.getLirCode());
			locationCode.append("StaticCall __checkNullRef(a=R" + targetReg + "),Rdummy\n");
			
			UserType instanceType = (UserType)location.getLocation().accept(new TypeCheckVisitor());
			String className = instanceType.getName();
			Integer fieldOffset = classLayouts.get(className).getFieldOffset(location.getName());
			locationCode.append("Move " + fieldOffset + ",R" + (targetReg+1) + "\n");
			return new LirBlock(locationCode, targetReg, LirValueType.FIELD);
		} else {
			Symbol symbol = location.getEnclosingScope().lookup(location.getName());
			int blockDepth = location.getEnclosingScope().getBlockDepth();
			if (symbol.getKind() == Kind.FIELD) {
				locationCode.append("Move this,R" + targetReg + "\n");
				String className = location.getEnclosingClassTable().getName();
				Integer fieldOffset = classLayouts.get(className).getFieldOffset(location.getName());
				locationCode.append("Move " + fieldOffset + ",R" + (targetReg+1) + "\n");
				return new LirBlock(locationCode, targetReg, LirValueType.FIELD);
			} else {
				assert(symbol.getKind() == Kind.VARIABLE);
				locationCode.append("Move " + symbol.getID() + blockDepth + ",R" + targetReg + "\n");
				return new LirBlock(locationCode, targetReg, LirValueType.VARIABLE);
			}
		}
	}

	@Override
	public LirBlock visit(ArrayLocation location, Integer targetReg) {
			StringBuilder lirCode = new StringBuilder();
			
			LirBlock array = location.getArray().accept(this, targetReg);
			
			lirCode.append(array.getLirCode());
			lirCode.append("StaticCall __checkNullRef(a=R" + targetReg + "),Rdummy\n");
			
			LirBlock index = location.getIndex().accept(this, targetReg + 1);
			lirCode.append(index.getLirCode());
			lirCode.append("MoveArray R"+array.getTargetRegister()+"[R"+index.getTargetRegister()+"],R"+targetReg+"\n");
			lirCode.append("StaticCall __checkArrayAccess(a=R"+targetReg+",i=R"+ index.getTargetRegister() +"),Rdummy\n");
	
		return new LirBlock(lirCode, targetReg, LirValueType.ARRAY_LOCATION);
	}

	@Override
	public LirBlock visit(StaticCall call, Integer targetReg) {
		String className = call.getClassName();
		String methodName = call.getName();
		StringBuilder argCode = new StringBuilder();
		String methodLabel = null;
		boolean isLibrary = className.equals("Library");
		if (isLibrary) {
			methodLabel = "__" + methodName;
		} else {
			methodLabel = "_" + className + "_" + methodName;
		}
		StringBuilder callCode = new StringBuilder((isLibrary ? "Library " : "StaticCall ") + methodLabel + "(");
		List<Expression> arguments = call.getArguments();
		List<Formal> parameters = getMethodParameters(className, methodName);
		int argReg = targetReg + 1;
		for (int i = 0; i < arguments.size(); ++i) {
			Expression arg = arguments.get(i);
			Formal param = parameters.get(i);
			
			// insert argument code
			LirBlock argBlock = arg.accept(this, argReg + i);
			argCode.append(argBlock.getLirCode());
			
			// insert argument name and register into call code
			if (!isLibrary) {
				callCode.append(param.getName() + "1" + "=R" + (argReg + i));
			} else {
				callCode.append("R" + (argReg + i));
			}
			if (i < arguments.size() - 1) callCode.append(",");
		}
		callCode.append("),R" + targetReg + "\n");
		argCode.append(callCode);
		return new LirBlock(argCode, targetReg);
	}

	private List<Formal> getMethodParameters(String className, String methodName) {
		ICClass icClass = null;
		try {
			icClass = TypeTable.getUserTypeByName(className);
		} catch (SemanticError e) {
			// this shouldn't be reached
			e.printStackTrace();
		}
		for (Method method : icClass.getMethods()) {
			if (method.getName().equals(methodName)) {
				return method.getFormals();
			}
		}
		return getMethodParameters(icClass.getSuperClassName(), methodName);
	}

	@Override
	public LirBlock visit(VirtualCall call, Integer targetReg) {
		StringBuilder argCode = new StringBuilder();
		StringBuilder callCode = new StringBuilder("VirtualCall ");
		StringBuilder locationCode = new StringBuilder();
		Integer methodOffset = 0;
		String className = "";
		if (call.isExternal()) {
			LirBlock locationBlock = call.getLocation().accept(this, targetReg + 1);
			locationCode.append(locationBlock.getLirCode());
			className = ((UserType)call.getLocation().accept(new TypeCheckVisitor())).getName();
		} else {
			locationCode.append("Move this,R" + (targetReg + 1) + "\n");
			className = call.getEnclosingClassTable().getName();
		}
		argCode.append(locationCode);
		methodOffset = classLayouts.get(className).getMethodOffset(call.getName());
		callCode.append("R" + (targetReg + 1) + "." + methodOffset + "(");
		
		List<Expression> arguments = call.getArguments();
		List<Formal> parameters = getMethodParameters(className, call.getName());
		int argReg = targetReg + 2;
		for (int i = 0; i < arguments.size(); ++i) {
			Expression arg = arguments.get(i);
			Formal param = parameters.get(i);
			
			// insert argument code
			LirBlock argBlock = arg.accept(this, argReg + i);
			argCode.append(argBlock.getLirCode());
			
			// insert argument name and register into call code
			callCode.append(param.getName() + "1" + "=R" + (argReg + i));
			if (i < arguments.size() - 1) callCode.append(",");
		}
		callCode.append("),R" + targetReg + "\n");
		argCode.append(callCode);
		return new LirBlock(argCode, targetReg);
	}

	@Override
	public LirBlock visit(This thisExpression, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		lirCode.append("Move this,R"+targetReg+"\n");
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(NewClass newClass, Integer targetReg) {
		StringBuilder newCode = new StringBuilder();
		newCode.append("Library __allocateObject(" + 
						classLayouts.get(newClass.getName()).getObjectAllocSize() +
						"),R" + targetReg + "\n");
		newCode.append("MoveField _DV_" + newClass.getName() + ",R" +
						targetReg + ".0\n");
		return new LirBlock(newCode, targetReg);
	}

	@Override
	public LirBlock visit(NewArray newArray, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		LirBlock size = newArray.getSize().accept(this, targetReg);
		lirCode.append(size.getLirCode());
		lirCode.append("StaticCall __checkSize(n="+size.getTargetRegister()+"),Rdummy\n");
		lirCode.append("Library __allocateArray(R"+size.getTargetRegister()+"),R");
		lirCode.append(targetReg+"\n");
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(Length length, Integer targetReg) {
		//Expects to get the array in a register
		StringBuilder lirCode = new StringBuilder();
		LirBlock array = length.getArray().accept(this, targetReg);
		lirCode.append(array.getLirCode());
		lirCode.append("StaticCall __checkNullRef(a=R"+targetReg+"),Rdummy\n");
		lirCode.append("ArrayLength R"+ array.getTargetRegister()+",R"+
		targetReg +"\n");
		
		return new LirBlock(lirCode,targetReg);
	}

	@Override
	public LirBlock visit(MathBinaryOp binaryOp, Integer targetReg) {
		String opString = "";
		StringBuilder lirCode = new StringBuilder("");
		
		Integer firstTargetReg=targetReg;
		Integer secondTargetReg=targetReg+1;
		
		LirBlock leftOperand=binaryOp.getFirstOperand().accept(this,firstTargetReg);
		lirCode.append(leftOperand.getLirCode());
		lirCode.append("\n");
		
		LirBlock rightOperand=binaryOp.getSecondOperand().accept(this,secondTargetReg);
		lirCode.append(rightOperand.getLirCode());
		lirCode.append("\n");
		
		switch(binaryOp.getOperator())
		{
			case PLUS:
				IC.AST.Type operandType=(IC.AST.Type)binaryOp.getFirstOperand().accept(new IC.Semantic.TypeCheckVisitor());
				if(operandType==TypeTable.getType("int"))
				{
					//Addition of 2 integers
					opString="Add R"+secondTargetReg+",R"+firstTargetReg;
				}
				else
				{
					//Concatenation of 2 strings
					opString="Library __stringCat(R"+firstTargetReg+",R"+secondTargetReg+"),R"+firstTargetReg;
				}
				break;
			case MINUS:
				opString="Sub R"+secondTargetReg+",R"+firstTargetReg;
				break;
			case MULTIPLY:
				opString="Mul R"+secondTargetReg+",R"+firstTargetReg;
				break;
			case DIVIDE:
				//static call to checkZero on runtime
				opString+="StaticCall __checkZero(b=R"+secondTargetReg+"),Rdummy\n";
				opString+="Div R"+secondTargetReg+",R"+firstTargetReg;
				break;
			case MOD:
				//static call to checkZero on runtime
				opString+="StaticCall __checkZero(b=R"+secondTargetReg+"),Rdummy\n";
				opString="Mod R"+secondTargetReg+",R"+firstTargetReg;
				break;
		}
		lirCode.append(opString);
		lirCode.append("\n");
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(LogicalBinaryOp binaryOp, Integer targetReg) {
		
		String jumpString = "";
		StringBuilder lirCode = new StringBuilder("");
		int lblnum=getNextLabelNum();
		
		Integer firstTargetReg=targetReg;
		Integer secondTargetReg=targetReg+1;
		
		LirBlock leftOperand=binaryOp.getFirstOperand().accept(this,firstTargetReg);
		lirCode.append(leftOperand.getLirCode());
		lirCode.append("\n");
		
		LirBlock rightOperand=binaryOp.getSecondOperand().accept(this,secondTargetReg);
		lirCode.append(rightOperand.getLirCode());
		lirCode.append("\n");
		
		if(binaryOp.getOperator()==BinaryOps.LOR || binaryOp.getOperator()==BinaryOps.LAND)
		{
			String compareTo;
			String boolOp;
			if(binaryOp.getOperator()==BinaryOps.LOR)
			{
				compareTo="0";
				boolOp="Or";
			}
			else
			{
				compareTo="1";
				boolOp="And";
			}
			lirCode.append("Compare "+compareTo+",R"+firstTargetReg);
			lirCode.append("JumpTrue _endlbl"+lblnum);
			lirCode.append(boolOp+" R"+secondTargetReg+",R"+firstTargetReg);
			
		}
		else
		{
			lirCode.append("Compare R"+secondTargetReg+",R"+firstTargetReg);
			lirCode.append("\n");
			
			switch (binaryOp.getOperator()) {
			case GT:
				jumpString+="JumpG";
				break;
			case GTE:
				jumpString+="JumpGE";
				break;
			case LT:
				jumpString+="JumpL";
				break;
			case LTE:
				jumpString+="JumpLE";
				break;
			case EQUAL:
				jumpString+="JumpTrue";
				break;
			case NEQUAL:
				jumpString+="JumpFalse";
				break;
			}
			jumpString+=" _truelbl"+lblnum+"\n";
			
			lirCode.append(jumpString);
			lirCode.append("Move 0,R"+firstTargetReg);
			lirCode.append("\n");
			lirCode.append("Jump _endlbl"+lblnum+"\n");
			lirCode.append("_truelbl"+lblnum+":\n");
			lirCode.append("Move 1,R"+firstTargetReg);
			lirCode.append("\n");
		}
		
		lirCode.append("_endlbl"+lblnum+":\n");
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(MathUnaryOp unaryOp, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		LirBlock operand = unaryOp.getOperand().accept(this, targetReg);
		lirCode.append(operand.getLirCode());
		lirCode.append("Neg R");
		lirCode.append(operand.getTargetRegister().toString());
		lirCode.append("\n");
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(LogicalUnaryOp unaryOp, Integer targetReg) {
		StringBuilder lirCode = new StringBuilder();
		LirBlock operand = unaryOp.getOperand().accept(this, targetReg);
		lirCode.append(operand.getLirCode());
		lirCode.append("Neg R");
		lirCode.append(operand.getTargetRegister().toString());
		lirCode.append("\n");
		
		return new LirBlock(lirCode, targetReg);
		
	}

	@Override
	public LirBlock visit(Literal literal, Integer targetReg) {
		String literalString = "";
		StringBuilder lirCode = new StringBuilder("Move ");
		switch(literal.getType()){
			case STRING:
				String str = literal.getValue().toString();
				if(!stringLiterals.containsKey(str))
					stringLiterals.put(str, "str"+(stringLiterals.size()+1));
				literalString = stringLiterals.get(str);
				break;
				
			case INTEGER:
					literalString = literal.getValue().toString();
				break;
				
			case TRUE:
					literalString = "1";
				break;
				
			case FALSE:
					literalString = "0";
				break;
				
			case NULL:
					literalString = "0";
				break;
		}
		
		lirCode.append(literalString);
		lirCode.append(",R");
		lirCode.append(targetReg.toString());
		lirCode.append("\n");
		
		return new LirBlock(lirCode, targetReg);
	}

	@Override
	public LirBlock visit(ExpressionBlock expressionBlock, Integer targetReg) {
		return expressionBlock.getExpression().accept(this, targetReg);
	}

	@Override
	public LirBlock visit(FieldMethodList fieldMethodList, Integer targetReg) {
		// this method shouldn't be reached
		return null;
	}

	@Override
	public LirBlock visit(EmptyStatement emptyStatement, Integer targetReg) {
		// this method shouldn't be reached
		return null;
	}

	@Override
	public LirBlock visit(ErrorMethod errorMethod, Integer targetReg) {
		// this method shouldn't be reached
		return null;
	}

	@Override
	public LirBlock visit(ErrorClass errorClass, Integer targetReg) {
		// this method shouldn't be reached
		return null;
	}

	@Override
	public LirBlock visit(Method method, Integer targetReg) {
		// this method shouldn't be reached
		return null;
	}

	@Override
	public LirBlock visit(GlobalSymbolTable table, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(ClassSymbolTable table, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(BlockSymbolTable table, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(MethodSymbolTable table, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirBlock visit(SymbolTable symbolTable, Integer targetReg) {
		// TODO Auto-generated method stub
		return null;
	}

}