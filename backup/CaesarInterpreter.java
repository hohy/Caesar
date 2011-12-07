
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interpreter of Caesar AST.
 * @author hohy
 */
public class CaesarInterpreter implements CaesarVisitor {

    private Map<String, InterpreterObject> globals;
    private Map<String, InterpreterClass> classTable;
    private String currentIdentifier;
    private Stack<InterpreterObject> stack;
    
    @Override
    public Object visit(SimpleNode node, Object data) {
        System.out.println(node + ": acceptor not unimplemented in subclass?");
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * Interprete program. Init enviroment.
     */
    @Override
    public Object visit(ASTStart node, Object data) {
        globals = new TreeMap<String, InterpreterObject>();
        classTable = new TreeMap<String, InterpreterClass>();
        initClassTable();
        stack = new Stack<InterpreterObject>();
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTCommandList node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTCommand node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * Creates new variable in actual enviroment
     */
    @Override
    public Object visit(ASTCreateVariableCmd node, Object data) {        
        data = node.childrenAccept(this, data);
        String varName = currentIdentifier;
        InterpreterObject value = stack.pop();
        globals.put(varName, value);
        return data;        
    }

    @Override
    public Object visit(ASTAssignVariableCmd node, Object data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(ASTIfCmd node, Object data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(ASTElsePart node, Object data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(ASTCondition node, Object data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(ASTRelationalOperator node, Object data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(ASTWhileCmd node, Object data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(ASTPrintlnCmd node, Object data) {
        data = node.childrenAccept(this, data);
        System.out.println(stack.pop());
        return data;
    }

    @Override
    public Object visit(ASTExpression node, Object data) {
        data = node.childrenAccept(this, data);
        switch(node.getOperator()) {
            case PLUS: plusOperation(); break;
            case MULTIPLY: throw new UnsupportedOperationException("Not supported yet.");
            case DIVIDE: throw new UnsupportedOperationException("Not supported yet.");
            case NULL:
            default:
                // do nothing
        }
        return data;
    }

    @Override
    public Object visit(ASTExpressionX node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    /**
     * Do operation (defined by operator) with numbers on stack.
     */
    @Override
    public Object visit(ASTTerm node, Object data) {
        data = node.childrenAccept(this, data);
        switch(node.getOperator()) {
            case PLUS: plusOperation();
            case MINUS: throw new UnsupportedOperationException("Not supported yet.");
            case NULL:
            default:
                // do nothing
        }
        return data;
    }

    @Override
    public Object visit(ASTTermX node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFactor node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFactorRest node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        data = node.childrenAccept(this, data);
        currentIdentifier = node.getName();
        if(globals.containsKey(currentIdentifier)) stack.push(globals.get(currentIdentifier));
        return data;
    }

    @Override
    public Object visit(ASTIntConst node, Object data) {
        data = node.childrenAccept(this, data);
        InterpreterClass icls;
        try {
            icls = getClass("Integer");
            InterpreterObject o = new InterpreterObject(node.getValue(),icls);
            Logger.getLogger(CaesarInterpreter.class.getName()).log(Level.FINE, "Created integer const: " + node.getValue());
            stack.push(o);            
        } catch (Exception ex) {
            Logger.getLogger(CaesarInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    @Override
    public Object visit(ASTRealConst node, Object data) {
        data = node.childrenAccept(this, data);
        InterpreterObject o = new InterpreterObject(node.getValue(), classTable.get("Real"));
        stack.push(o);
        return data;
    }

    // add build-in classes to classtable
    private void initClassTable() {
        // add iteger class
        InterpreterClass integer = new InterpreterClass("Integer");
        integer.addOperation("addInteger", new InterpreterOperation() {
            @Override
            public void call() {
                System.out.println("addInteger from IntegerClass is called.");
                int opa = (Integer) stack.pop().getValue();
                int opb = (Integer) stack.pop().getValue();
                InterpreterObject result = new InterpreterObject(opa + opb, classTable.get("Integer"));
                stack.push(result);
            }
        });
        integer.addOperation("addReal", new InterpreterOperation() {
            @Override
            public void call() {
                System.out.println("addInteger from IntegerClass is called.");
                int opa = (Integer) stack.pop().getValue();
                double opb = (Double) stack.pop().getValue();
                InterpreterObject result = new InterpreterObject(opa + opb, classTable.get("Real"));
                stack.push(result);
            }
        });       
        classTable.put(integer.getName(), integer);
        InterpreterClass real = new InterpreterClass("Real");
        real.addOperation("addInteger", new InterpreterOperation() {
            @Override
            public void call() {
                System.out.println("addInteger from IntegerClass is called.");
                double opa = (Double) stack.pop().getValue();
                double opb = (Integer) stack.pop().getValue();
                InterpreterObject result = new InterpreterObject(opa + opb, classTable.get("Real"));
                stack.push(result);
            }
        });        
        real.addOperation("addReal", new InterpreterOperation() {
            @Override
            public void call() {
                System.out.println("addInteger from IntegerClass is called.");
                double opa = (Double) stack.pop().getValue();
                double opb = (Double) stack.pop().getValue();
                InterpreterObject result = new InterpreterObject(opa + opb, classTable.get("Real"));
                stack.push(result);
            }
        });
        classTable.put(integer.getName(), real);
    }

    private InterpreterClass getClass(String name) throws Exception {
        InterpreterClass result = classTable.get(name);
        if(result != null) return result;
        throw new Exception("Unknown class " + name);
    }
    
    private void plusOperation() {
        InterpreterObject oba = stack.pop();
        InterpreterObject obb = stack.peek();
        stack.push(oba);
        oba.getType().callOperation("add" + obb.getType().getName());
    }
    
}
