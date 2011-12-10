package caesar.interpreter;

import caesar.ast.ExpressionTree;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hohy
 */
public class InterpreterClass {
    // class name
    private String name;
    
    // object size (on stack or in heap)
    private int size;
    
    // operation table
    private Map<String, InterpreterOperation> vtable;
    
    // class fields table
    private Map<String, InterpreterClassField> fieldsTable;
    
    public InterpreterClass(String name, int size) {
        this.name = name;
        this.size = size;
        vtable = new TreeMap<String, InterpreterOperation>();
        fieldsTable = new HashMap<String, InterpreterClassField>();
    }
    
    public void addOperation(String name, InterpreterOperation op) { 
        vtable.put(name, op);
    }
    
    public void addField(String name, InterpreterClass type, ExpressionTree initTree) {
        Logger.getLogger(InterpreterClass.class.getName()).log(Level.FINER, "Adding new {0} field {1} to class {2}. InitTree: {3}", new Object[]{type.getName(), name, getName(), initTree});
        fieldsTable.put(name, new InterpreterClassField(name, type, initTree, size));
        size += 4;
    }

    public void callOperation(CaesarInterpreter interpreter, String name, InterpreterObject ... params) {
        InterpreterOperation op = vtable.get(name);

        for (int i = 0; i < params.length; i++) {                
            //InterpreterStack.getInstance().push(params[i]);
        }
        if(op != null) op.call(interpreter);
        else {
            Logger.getLogger(InterpreterClass.class.getCanonicalName()).log(Level.SEVERE, "{0}: Calling unknown method {1}", new Object[]{this.getName(), name});
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InterpreterClass{" + "name=" + name + ", vtable=" + vtable.keySet() + '}';
    }

    public int getObjectSize() {
        return size;
    }

    public InterpreterClassField getField(String name) {
        return fieldsTable.get(name);
    }
    
    public Collection<InterpreterClassField> getFields() {
        return fieldsTable.values();
    }
    
}
