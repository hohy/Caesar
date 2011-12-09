/* Generated By:JavaCC: Do not edit this line. Caesar.java */
package caesar;

import caesar.interpreter.CaesarInterpreter;
import java.util.logging.Level;
import java.util.logging.Logger;
import caesar.ast.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Caesar implements CaesarConstants {

    private static final Logger logger = Logger.getLogger(Caesar.class.getName());

    //private static Map<String, Type> symTab = new TreeMap<String, Type>();

    public static void main( String[] args ) {
      logger.setLevel(Level.ALL);
      logger.log(Level.INFO, "Welcome to Caesar programming laguage!");
      Caesar parser = new Caesar( System.in );
      try {
        ProgramTree t = parser.Start();
        //t.dump("");
        TreeVisitor v = new DumpCaesarVisitor();

        TreeVisitor interpreter = new CaesarInterpreter();
        t.accept(v);
        t.accept(interpreter);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }

  static final public ProgramTree Start() throws ParseException {
  IdentifierTree i;
  CommandListTree c;
  String n;
    jj_consume_token(PROGRAM);
    i = Identifier();
    jj_consume_token(COLON);
    c = CommandList();
    jj_consume_token(ENDPROGRAM);
    jj_consume_token(0);
    //i = new IdentifierTree(n);
    {if (true) return new ProgramTree(i, c);}
    throw new Error("Missing return statement in function");
  }

  static final public CommandListTree CommandList() throws ParseException {
  List<CommandTree> list = new ArrayList<CommandTree>();
  CommandListTree r;
  CommandTree c;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VAR:
      case IF:
      case WHILE:
      case PRINTLN:
      case CLASS:
      case IDENT:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      c = Command();
    list.add(c);
    }
  {if (true) return new CommandListTree(list);}
    throw new Error("Missing return statement in function");
  }

/*CommandListRest(List<CommandTree> list) : {
  CommandTree c;
} 
{
  c = Command;
  {list.add(c);}
  (CommandListRest(list))?
  {return new CommandListTree(list);}
}*/
  static final public CommandTree Command() throws ParseException {
  CommandTree c;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VAR:
      c = CreateVariableCmd();
                           {if (true) return c;}
      break;
    default:
      jj_la1[1] = jj_gen;
      if (jj_2_1(2)) {
        c = AssignVariableCmd();
                                          {if (true) return c;}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IF:
          c = IfCmd();
                {if (true) return c;}
          break;
        case WHILE:
          c = WhileCmd();
                   {if (true) return c;}
          break;
        case PRINTLN:
          c = PrintlnCmd();
                      {if (true) return c;}
          break;
        case CLASS:
          c = ClassDefinition();
                           {if (true) return c;}
          break;
        default:
          jj_la1[2] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public CreateVariableTree CreateVariableCmd() throws ParseException {
  String n;
  String c;
  ExpressionTree e;
  IdentifierTree i = null;
  Token t;
    jj_consume_token(VAR);
    i = Identifier();
    jj_consume_token(ASSIGN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
    case LBRC:
    case QUOTEDSTRING:
    case REAL_CONST:
    case INT_CONST:
      e = Expression();
    i.setType(e.getType());
    {if (true) return new CreateVariableTree(i, e);}
      break;
    case NEW:
      jj_consume_token(NEW);
      t = jj_consume_token(IDENT);
      jj_consume_token(LBRC);
      jj_consume_token(RBRC);
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    i.setType(t.image);
    {if (true) return new CreateVariableTree(i, null);}
    throw new Error("Missing return statement in function");
  }

  static final public CommandTree AssignVariableCmd() throws ParseException {
  String n;
  ExpressionTree e;
  IdentifierTree i;
    i = Identifier();
    jj_consume_token(ASSIGN);
    e = Expression();
    //i = new IdentifierTree(n);
    {if (true) return new AssignVariableTree(i, e);}
    throw new Error("Missing return statement in function");
  }

  static final public CommandTree IfCmd() throws ParseException {
  BinaryTree bt;
  CommandListTree c1;
  CommandListTree c2;
    jj_consume_token(IF);
    bt = Condition();
    jj_consume_token(COLON);
    c1 = CommandList();
    c2 = ElsePart();
   {if (true) return new IfTree(bt, c1, c2);}
    throw new Error("Missing return statement in function");
  }

  static final public CommandListTree ElsePart() throws ParseException {
  CommandListTree cmd = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ELSE:
      jj_consume_token(ELSE);
      cmd = CommandList();
      jj_consume_token(ENDIF);
      break;
    case ENDIF:
      jj_consume_token(ENDIF);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
   {if (true) return cmd;}
    throw new Error("Missing return statement in function");
  }

  static final public BinaryTree Condition() throws ParseException {
  BinaryTree bt;
  ExpressionTree e1;
  ExpressionTree e2;
  Operator op;
    e1 = Expression();
    op = RelationalOperator();
    e2 = Expression();
   {if (true) return new BinaryTree(op, e1, e2);}
    throw new Error("Missing return statement in function");
  }

  static final public Operator RelationalOperator() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EQ:
      jj_consume_token(EQ);
          {if (true) return Operator.EQ;}
      break;
    case NE:
      jj_consume_token(NE);
          {if (true) return Operator.NE;}
      break;
    case LT:
      jj_consume_token(LT);
          {if (true) return Operator.LT;}
      break;
    case GT:
      jj_consume_token(GT);
          {if (true) return Operator.GT;}
      break;
    case LE:
      jj_consume_token(LE);
          {if (true) return Operator.LE;}
      break;
    case GE:
      jj_consume_token(GE);
          {if (true) return Operator.GE;}
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public CommandTree WhileCmd() throws ParseException {
    jj_consume_token(WHILE);
    Condition();
    jj_consume_token(COLON);
    CommandList();
    jj_consume_token(ENDWHILE);
   {if (true) return null;}
    throw new Error("Missing return statement in function");
  }

  static final public PrintlnTree PrintlnCmd() throws ParseException {
  ExpressionTree e;
    jj_consume_token(PRINTLN);
    jj_consume_token(LBRC);
    e = Expression();
    jj_consume_token(RBRC);
   {if (true) return new PrintlnTree(e);}
    throw new Error("Missing return statement in function");
  }

  static final public ClassDefinitionTree ClassDefinition() throws ParseException {
  IdentifierTree className;
  List<CreateVariableTree> vars = new LinkedList<CreateVariableTree>();
  List<MethodDefinitionTree> methods = new LinkedList<MethodDefinitionTree>();
  CreateVariableTree var;
  MethodDefinitionTree mth;
    jj_consume_token(CLASS);
    className = Identifier();
    jj_consume_token(COLON);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VAR:
      case DEF:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VAR:
        var = CreateVariableCmd();
     vars.add(var);
        break;
      case DEF:
        mth = MethodDefinition();
     methods.add(mth);
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    jj_consume_token(ENDCLASS);
    {if (true) return new ClassDefinitionTree(className, vars, methods);}
    throw new Error("Missing return statement in function");
  }

  static final public MethodDefinitionTree MethodDefinition() throws ParseException {
    IdentifierTree returnType;
    IdentifierTree methodName;
    CommandListTree cmnds;
    jj_consume_token(DEF);
    returnType = Identifier();
    methodName = Identifier();
    jj_consume_token(COLON);
    cmnds = CommandList();
    jj_consume_token(ENDDEF);
   {if (true) return new MethodDefinitionTree(returnType, methodName, cmnds);}
    throw new Error("Missing return statement in function");
  }

/*
MethodCallTree MethodCall() : {
  String objName;
  IdentifierTree i;
  String methodName;
  IdentifierTree m;
}
{
  objName = Identifier() <DOT> methodName = Identifier() <LBRC> <RBRC>
  {
    i = new IdentifierTree(objName);
    m = new IdentifierTree(methodName);
    return new MethodCallTree(i, m);
  }
}
*/
  static final public ExpressionTree Expression() throws ParseException {
  ExpressionTree t;
  ExpressionTree r;
    t = Term();
    r = ExpressionX(t);
   {if (true) return r;}
    throw new Error("Missing return statement in function");
  }

  static final public ExpressionTree ExpressionX(ExpressionTree e1) throws ParseException {
  Operator op;
  ExpressionTree e2;
  BinaryTree t;
  ExpressionTree r;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
    case MINUS:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
     op = Operator.PLUS;
        e2 = Term();
     t = new BinaryTree(op, e1, e2);
        r = ExpressionX(t);
     {if (true) return r;}
        break;
      case MINUS:
        jj_consume_token(MINUS);
     op = Operator.MINUS;
        e2 = Term();
     t = new BinaryTree(op, e1, e2);
        r = ExpressionX(t);
     {if (true) return r;}
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[9] = jj_gen;
      ;
    }
   {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  static final public ExpressionTree Term() throws ParseException {
  ExpressionTree t;
  ExpressionTree r;
    t = Factor();
    r = TermX(t);
   {if (true) return r;}
    throw new Error("Missing return statement in function");
  }

  static final public ExpressionTree TermX(ExpressionTree e1) throws ParseException {
  Operator op;
  ExpressionTree e2;
  BinaryTree bt;
  ExpressionTree r;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MULTIPLY:
    case DIVIDE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
        jj_consume_token(MULTIPLY);
   op = Operator.MULTIPLY;
        e2 = Factor();
   bt = new BinaryTree(op, e1, e2);
        r = TermX(bt);
   {if (true) return r;}
        break;
      case DIVIDE:
        jj_consume_token(DIVIDE);
    op = Operator.DIVIDE;
        e2 = Factor();
    bt = new BinaryTree(op, e1, e2);
        r = TermX(bt);
    {if (true) return r;}
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
   {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  static final public ExpressionTree Factor() throws ParseException {
  ExpressionTree e;
  IdentifierTree i;
  String n;
  String m;
  LiteralTree l;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRC:
      jj_consume_token(LBRC);
      e = Expression();
     {if (true) return e;}
      jj_consume_token(RBRC);
      break;
    case IDENT:
      i = Identifier();
     {if (true) return i;}
      break;
    case INT_CONST:
      l = IntConst();
     {if (true) return l;}
      break;
    case REAL_CONST:
      l = RealConst();
     {if (true) return l;}
      break;
    case QUOTEDSTRING:
      l = StringConst();
     {if (true) return l;}
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public ExpressionTree FactorRest(String n) throws ParseException {
  ExpressionTree e = null;
  String m;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LABRC:
      jj_consume_token(LABRC);
      e = Expression();
      jj_consume_token(RABRC);
      {if (true) return new FieldIdentifierTree(n, e);}
      break;
    default:
      jj_la1[13] = jj_gen;
      ;
    }
     {if (true) return new FieldIdentifierTree(n);}
    throw new Error("Missing return statement in function");
  }

  static final public IdentifierTree Identifier() throws ParseException {
  Token t;
  Token tt = null;
  IdentifierTree it;
  MethodIdentifierTree mit;
    t = jj_consume_token(IDENT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRC:
    case DOT:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DOT:
        jj_consume_token(DOT);
        it = ClassIdentifier(t);
      {if (true) return it;}
        break;
      case LBRC:
        jj_consume_token(LBRC);
        jj_consume_token(RBRC);
      {if (true) return new MethodIdentifierTree(t.image);}
        break;
      default:
        jj_la1[14] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
    it = new FieldIdentifierTree(t.image);
    {if (true) return it;}
    throw new Error("Missing return statement in function");
  }

  static final public IdentifierTree ClassIdentifier(Token t) throws ParseException {
  Token tt;
  IdentifierTree it;
    tt = jj_consume_token(IDENT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRC:
      jj_consume_token(LBRC);
      jj_consume_token(RBRC);
      it = new ClassMethodIdentifierTree(t.image, tt.image);
      {if (true) return it;}
      break;
    default:
      jj_la1[16] = jj_gen;
      ;
    }
    it = new ClassIdentifierTree(t.image, tt.image);
    {if (true) return it;}
    throw new Error("Missing return statement in function");
  }

  static final public LiteralTree StringConst() throws ParseException {
  Token t;
    t = jj_consume_token(QUOTEDSTRING);
    String str = t.image.substring(1, t.image.length()-1);
    {if (true) return new LiteralTree(str, "String");}
    throw new Error("Missing return statement in function");
  }

  static final public LiteralTree RealConst() throws ParseException {
  Token t;
    t = jj_consume_token(REAL_CONST);
   {if (true) return new LiteralTree(Double.parseDouble(t.image), "Real");}
    throw new Error("Missing return statement in function");
  }

  static final public LiteralTree IntConst() throws ParseException {
  Token t;
    t = jj_consume_token(INT_CONST);
   {if (true) return new LiteralTree(Integer.parseInt(t.image), "Integer");}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_3R_7() {
    if (jj_scan_token(LBRC)) return true;
    return false;
  }

  static private boolean jj_3R_3() {
    if (jj_3R_4()) return true;
    if (jj_scan_token(ASSIGN)) return true;
    return false;
  }

  static private boolean jj_3R_6() {
    if (jj_scan_token(DOT)) return true;
    return false;
  }

  static private boolean jj_3R_5() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_6()) {
    jj_scanpos = xsp;
    if (jj_3R_7()) return true;
    }
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_3()) return true;
    return false;
  }

  static private boolean jj_3R_4() {
    if (jj_scan_token(IDENT)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_5()) jj_scanpos = xsp;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public CaesarTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[17];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40c128,0x8,0xc120,0x10480000,0xc0,0x0,0x20008,0x20008,0x3000000,0x3000000,0xc000000,0xc000000,0x10400000,0x40000000,0x10000000,0x10000000,0x10000000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x380,0x0,0x3f,0x0,0x0,0x0,0x0,0x0,0x0,0x380,0x0,0x40,0x40,0x0,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[1];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Caesar(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Caesar(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new CaesarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Caesar(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new CaesarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Caesar(CaesarTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(CaesarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[48];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 17; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 48; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

  }
