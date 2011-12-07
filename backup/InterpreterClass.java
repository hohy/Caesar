
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author hohy
 */
public class InterpreterClass {
    // class name
    private String name;
    
    // operation table
    private Map<String, InterpreterOperation> vtable;

    public InterpreterClass(String name) {
        this.name = name;
        vtable = new TreeMap<String, InterpreterOperation>();
    }
    
    public void addOperation(String name, InterpreterOperation op) { 
        vtable.put(name, op);
    }
    
    public void callOperation(String name) {
        InterpreterOperation op = vtable.get(name);
        op.call();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}
