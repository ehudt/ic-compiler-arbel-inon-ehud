
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Sun Dec 09 17:32:57 IST 2012
//----------------------------------------------------

package IC.Parser;

import IC.AST.*;
import java_cup.runtime.*;
import java.util.List;
import java.util.LinkedList;
import IC.DataTypes;
import IC.Parser.Lexer;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Sun Dec 09 17:32:57 IST 2012
  */
public @SuppressWarnings(value={"all"}) class LibraryParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public LibraryParser() {super();}

  /** Constructor which sets the default scanner. */
  public LibraryParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public LibraryParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\017\000\002\002\007\000\002\002\004\000\002\004" +
    "\004\000\002\004\002\000\002\003\011\000\002\003\011" +
    "\000\002\006\005\000\002\006\003\000\002\006\002\000" +
    "\002\005\004\000\002\007\003\000\002\007\003\000\002" +
    "\007\003\000\002\007\003\000\002\007\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\041\000\004\007\004\001\002\000\004\061\007\001" +
    "\002\000\004\002\006\001\002\000\004\002\000\001\002" +
    "\000\004\026\010\001\002\000\006\045\ufffe\051\ufffe\001" +
    "\002\000\006\045\013\051\012\001\002\000\014\005\020" +
    "\023\022\052\015\056\021\061\017\001\002\000\004\002" +
    "\001\001\002\000\006\045\uffff\051\uffff\001\002\000\006" +
    "\025\ufff5\062\ufff5\001\002\000\006\025\031\062\037\001" +
    "\002\000\006\025\ufff4\062\ufff4\001\002\000\006\025\ufff6" +
    "\062\ufff6\001\002\000\004\062\023\001\002\000\006\025" +
    "\ufff7\062\ufff7\001\002\000\004\032\024\001\002\000\016" +
    "\005\020\010\ufff9\023\022\047\ufff9\052\015\061\017\001" +
    "\002\000\006\010\033\047\034\001\002\000\006\010\ufffa" +
    "\047\ufffa\001\002\000\006\025\031\062\030\001\002\000" +
    "\006\010\ufff8\047\ufff8\001\002\000\004\044\032\001\002" +
    "\000\006\025\ufff3\062\ufff3\001\002\000\012\005\020\023" +
    "\022\052\015\061\017\001\002\000\004\050\035\001\002" +
    "\000\006\045\ufffc\051\ufffc\001\002\000\006\010\ufffb\047" +
    "\ufffb\001\002\000\004\032\040\001\002\000\016\005\020" +
    "\010\ufff9\023\022\047\ufff9\052\015\061\017\001\002\000" +
    "\006\010\033\047\042\001\002\000\004\050\043\001\002" +
    "\000\006\045\ufffd\051\ufffd\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\041\000\004\002\004\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\004\010\001\001\000\004\003\013\001\001\000\004\007" +
    "\015\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\010\005\025\006\024\007\026\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\006\005\035\007" +
    "\026\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\010\005\025\006\040\007" +
    "\026\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$LibraryParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$LibraryParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$LibraryParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



	
	private Lexer lexer;

	public LibraryParser(Lexer lexer) {
		super(lexer);
		this.lexer = lexer;
	}
	
	public int getLine() {
		return lexer.getCurrentLine();
	}

	
	// override method: syntax_error
	public void unrecovered_syntax_error(Symbol curr_tok) throws SyntaxError{
		throw new SyntaxError((Token) curr_tok);
	}
	
	// override method to avoid duplicate errors
	public void syntax_error(Symbol curr_tok){}
	

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$LibraryParser$actions {
  private final LibraryParser parser;

  /** Constructor */
  CUP$LibraryParser$actions(LibraryParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$LibraryParser$do_action(
    int                        CUP$LibraryParser$act_num,
    java_cup.runtime.lr_parser CUP$LibraryParser$parser,
    java.util.Stack            CUP$LibraryParser$stack,
    int                        CUP$LibraryParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$LibraryParser$result;

      /* select the action based on the action number */
      switch (CUP$LibraryParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // type ::= type LB RB 
            {
              Type RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).right;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).value;
		
		 		t.incrementDimension();
				RESULT=t;
		 
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("type",5, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // type ::= CLASS_ID 
            {
              Type RESULT =null;
		int c_nameleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).left;
		int c_nameright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).right;
		String c_name = (String)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.peek()).value;
		
				RESULT=new UserType(c_nameleft, c_name);
		 
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("type",5, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // type ::= STRING 
            {
              Type RESULT =null;
		int strleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).left;
		int strright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).right;
		Object str = (Object)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.peek()).value;
		
				RESULT=new PrimitiveType(strleft,DataTypes.STRING); 
		 
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("type",5, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // type ::= BOOLEAN 
            {
              Type RESULT =null;
		int boolleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).left;
		int boolright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).right;
		Object bool = (Object)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.peek()).value;
		
				RESULT=new PrimitiveType(boolleft,DataTypes.BOOLEAN);
		 
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("type",5, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // type ::= INT 
            {
              Type RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).right;
		Object i = (Object)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.peek()).value;
		
				RESULT=new PrimitiveType(ileft,DataTypes.INT);
		 
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("type",5, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // formal ::= type ID 
            {
              Formal RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).right;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).value;
		int idleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).left;
		int idright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).right;
		String id = (String)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.peek()).value;
		 RESULT = new Formal(t, id); 
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("formal",3, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // formal_list ::= 
            {
              List<Formal> RESULT =null;
		
			   RESULT = new LinkedList<Formal>();
			   
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("formal_list",4, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // formal_list ::= formal 
            {
              List<Formal> RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).right;
		Formal f = (Formal)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.peek()).value;
		
			   List<Formal> fl=new LinkedList<Formal>();
			   fl.add(f);
			   RESULT = fl;
			   
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("formal_list",4, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // formal_list ::= formal_list COMMA formal 
            {
              List<Formal> RESULT =null;
		int flleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).left;
		int flright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).right;
		List<Formal> fl = (List<Formal>)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).right;
		Formal f = (Formal)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.peek()).value;
		
			   	fl.add(f);
			   	RESULT = fl;
			   
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("formal_list",4, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // libmethod ::= STATIC VOID ID LP formal_list RP SEMI 
            {
              LibraryMethod RESULT =null;
		int stleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-6)).left;
		int stright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-6)).right;
		Object st = (Object)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-6)).value;
		int idleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-4)).left;
		int idright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-4)).right;
		String id = (String)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-4)).value;
		int flleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).left;
		int flright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).right;
		List<Formal> fl = (List<Formal>)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).value;
		 RESULT = new LibraryMethod(new PrimitiveType(stleft,DataTypes.VOID), id, fl); 
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("libmethod",1, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-6)), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // libmethod ::= STATIC type ID LP formal_list RP SEMI 
            {
              LibraryMethod RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-5)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-5)).right;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-5)).value;
		int idleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-4)).left;
		int idright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-4)).right;
		String id = (String)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-4)).value;
		int flleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).left;
		int flright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).right;
		List<Formal> fl = (List<Formal>)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-2)).value;
		 RESULT = new LibraryMethod(t, id, fl); 
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("libmethod",1, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-6)), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // libmethod_list ::= 
            {
              List<Method> RESULT =null;
		
			List<Method> lmList = new LinkedList<Method>();
			RESULT = lmList;
		
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("libmethod_list",2, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // libmethod_list ::= libmethod_list libmethod 
            {
              List<Method> RESULT =null;
		int lmListleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).left;
		int lmListright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).right;
		List<Method> lmList = (List<Method>)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).value;
		int lmleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).left;
		int lmright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()).right;
		LibraryMethod lm = (LibraryMethod)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.peek()).value;
			
			lmList.add(lm);
			RESULT = lmList;
		
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("libmethod_list",2, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= libic EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).right;
		ICClass start_val = (ICClass)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).value;
		RESULT = start_val;
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$LibraryParser$parser.done_parsing();
          return CUP$LibraryParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // libic ::= CLASS CLASS_ID LCBR libmethod_list RCBR 
            {
              ICClass RESULT =null;
		int libnameleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-3)).left;
		int libnameright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-3)).right;
		String libname = (String)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-3)).value;
		int lmListleft = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).left;
		int lmListright = ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).right;
		List<Method> lmList = (List<Method>)((java_cup.runtime.Symbol) CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-1)).value;
		 if (libname.equals("Library")){
			RESULT = new ICClass(libnameleft, libname, new LinkedList<Field>(), lmList);
		}else{
			throw new SyntaxError(libnameleft, "Syntax error: library class name must be Library");
			}
			
              CUP$LibraryParser$result = parser.getSymbolFactory().newSymbol("libic",0, ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.elementAt(CUP$LibraryParser$top-4)), ((java_cup.runtime.Symbol)CUP$LibraryParser$stack.peek()), RESULT);
            }
          return CUP$LibraryParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

