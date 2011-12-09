package caesar.interpreter;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author hohy
 */
public class InterpreterEnvironment {
    private Map<String, InterpreterObject> vars;
    private InterpreterEnvironment upperEnv;

    public InterpreterEnvironment(InterpreterEnvironment upperEnv) {
        this.vars = new TreeMap<String, InterpreterObject>();
        this.upperEnv = upperEnv;
    }
    
    public InterpreterObject searchEnv(String name) {
        if(vars.containsKey(name)) {
            return vars.get(name);
        } else if(upperEnv != null) return upperEnv.searchEnv(name);
        return null;
    }

    void add(String varName, InterpreterObject obj) {        
        vars.put(varName, obj);
    }
}
