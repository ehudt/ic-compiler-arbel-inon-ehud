package IC.Parser;

import java_cup.runtime.Symbol;

public class Token extends Symbol {
	private int line;
	private String value;
	private String name;
	
	public Token(int id, int line){
		this(id, line, "");
	}
	
    public Token(int id, int line, String value) {
        super(id, null);
        this.line = line;
    	this.value = value;
    	switch(id){
    	
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
    	return this.name;
    }
}

