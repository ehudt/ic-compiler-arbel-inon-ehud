package IC.Parser;
/**
 * class sym contains an integer ID constant 
 * for every token in IC programming language.
 */
public class sym {
    public static final int EOF = 0;
	public static final int ASSIGN = 1;
	public static final int BOOLEAN = 2;
	public static final int BREAK = 3;
	public static final int CLASS = 4;
	public static final int CLASS_ID = 5;
	public static final int COMMA = 6;
	public static final int CONTINUE = 7;
	public static final int DIVIDE = 8;
	public static final int DOT = 9;
	public static final int ELSE = 10;
	public static final int EQUAL = 11;
	public static final int EXTENDS = 12;
	public static final int FALSE = 13;
	public static final int GT = 14;
	public static final int GTE = 15;
	public static final int ID = 16;
	public static final int IF = 17;
	public static final int INT = 18;
	public static final int INTEGER = 19;
	public static final int LAND = 20;
	public static final int LB = 21;
	public static final int LCBR = 22;
	public static final int LENGTH = 23;
	public static final int LNEG = 24;
	public static final int LOR = 25;
	public static final int LP = 26;
	public static final int LT = 27;
	public static final int LTE = 28;
	public static final int MINUS = 29;
	public static final int MOD = 30;
	public static final int MULTIPLY = 31;
	public static final int NEQUAL = 32;
	public static final int NEW = 33;
	public static final int NULL = 34;
	public static final int PLUS = 35;
	public static final int QUOTE = 36;
	public static final int RB = 37;
	public static final int RCBR = 38;
	public static final int RETURN = 39;
	public static final int RP = 40;
	public static final int SEMI = 41;
	public static final int STATIC = 42;
	public static final int STRING = 43;
	public static final int THIS = 44;
	public static final int TRUE = 45;
	public static final int VOID = 46;
	public static final int WHILE = 47;
	
	/** 
	 * Get a string representation of a token's name by its id 
	 * @param id    The token's id
	 */
	public static String getNameById(int id){
    	switch(id){
			case IC.Parser.sym.EOF:
				return "EOF"; 
			case IC.Parser.sym.ASSIGN:
				return "ASSIGN"; 
			case IC.Parser.sym.BOOLEAN:
				return "BOOLEAN"; 
			case IC.Parser.sym.BREAK:
				return "BREAK"; 
			case IC.Parser.sym.CLASS:
				return "CLASS"; 
			case IC.Parser.sym.CLASS_ID:
				return "CLASS_ID"; 
			case IC.Parser.sym.COMMA:
				return "COMMA"; 
			case IC.Parser.sym.CONTINUE:
				return "CONTINUE"; 
			case IC.Parser.sym.DIVIDE:
				return "DIVIDE"; 
			case IC.Parser.sym.DOT:
				return "DOT"; 
			case IC.Parser.sym.ELSE:
				return "ELSE"; 
			case IC.Parser.sym.EQUAL:
				return "EQUAL"; 
			case IC.Parser.sym.EXTENDS:
				return "EXTENDS"; 
			case IC.Parser.sym.FALSE:
				return "FALSE"; 
			case IC.Parser.sym.GT:
				return "GT"; 
			case IC.Parser.sym.GTE:
				return "GTE"; 
			case IC.Parser.sym.ID:
				return "ID"; 
			case IC.Parser.sym.IF:
				return "IF"; 
			case IC.Parser.sym.INT:
				return "INT"; 
			case IC.Parser.sym.INTEGER:
				return "INTEGER"; 
			case IC.Parser.sym.LAND:
				return "LAND"; 
			case IC.Parser.sym.LB:
				return "LB"; 
			case IC.Parser.sym.LCBR:
				return "LCBR"; 
			case IC.Parser.sym.LENGTH:
				return "LENGTH"; 
			case IC.Parser.sym.LNEG:
				return "LNEG"; 
			case IC.Parser.sym.LOR:
				return "LOR"; 
			case IC.Parser.sym.LP:
				return "LP"; 
			case IC.Parser.sym.LT:
				return "LT"; 
			case IC.Parser.sym.LTE:
				return "LTE"; 
			case IC.Parser.sym.MINUS:
				return "MINUS"; 
			case IC.Parser.sym.MOD:
				return "MOD"; 
			case IC.Parser.sym.MULTIPLY:
				return "MULTIPLY"; 
			case IC.Parser.sym.NEQUAL:
				return "NEQUAL"; 
			case IC.Parser.sym.NEW:
				return "NEW"; 
			case IC.Parser.sym.NULL:
				return "NULL"; 
			case IC.Parser.sym.PLUS:
				return "PLUS"; 
			case IC.Parser.sym.QUOTE:
				return "QUOTE"; 
			case IC.Parser.sym.RB:
				return "RB"; 
			case IC.Parser.sym.RCBR:
				return "RCBR"; 
			case IC.Parser.sym.RETURN:
				return "RETURN"; 
			case IC.Parser.sym.RP:
				return "RP"; 
			case IC.Parser.sym.SEMI:
				return "SEMI"; 
			case IC.Parser.sym.STATIC:
				return "STATIC"; 
			case IC.Parser.sym.STRING:
				return "STRING"; 
			case IC.Parser.sym.THIS:
				return "THIS"; 
			case IC.Parser.sym.TRUE:
				return "TRUE"; 
			case IC.Parser.sym.VOID:
				return "VOID"; 
			case IC.Parser.sym.WHILE:
				return "WHILE"; 
    		default:
				return "";	
    	}
	}
}
