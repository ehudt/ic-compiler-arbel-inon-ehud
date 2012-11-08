package IC.Parser;

import java_cup.runtime.Symbol;
/**
 * class Token represents a token of the IC programming language.
 * Each token object contains its id, line number, name and value
 * if applicable.
 */
public class Token extends Symbol {
	private int line;
	/** Value field for specific tokens (e.g. ID) */
	private String value;
	/** 
	 * The token's name. This is derived by the constructor
	 * from the token ID.
	 */
	private String name;
	/** Constructor for tokens without a value */
	public Token(int id, int line){
		this(id, line, "");
	}
	/** Constructor for tokens with value */
    public Token(int id, int line, String value) {
        super(id, null);

		if(line < 0){
			throw new IllegalArgumentException("Line number can't be negative");
		}
        this.line = line + 1;

    	this.value = value;

		this.name = getNameById(id);
		if(this.name.isEmpty()){
			throw new IllegalArgumentException("Token id is invalid");
		}
    }
    
    public int getId() {
    	return this.sym;
    }
    public String getValue() {
    	return this.value;
    }
    public int getLine() {
    	return this.line;
    }
    
    public String getName(){
    	return name;
    }
	/** 
	 * Get a string representation of a token's name by its id 
	 * @param id    The token's id (from IC.Parser.sym)
	 */
	private String getNameById(int id){
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

