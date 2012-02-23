package caesar.bcinterpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.2.12
 * Time: 10:31
 */
public class Environment {
    
    private Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    
    private Environment superEnvironment;

    public Environment(Environment superEnvironment) {
        this.superEnvironment = superEnvironment;
    }
    
    public void add(int id, int address) {
        map.put(id, address);
    }
    
    public int get(int id) {
        if(map.containsKey(id)) {
            return map.get(id);
        } else if(superEnvironment != null) {
            return superEnvironment.get(id);
        }
        return -1;
    }

    public void set(int id, int address) {
        if(map.containsKey(id)) {
            map.put(id,address);
        } else if(superEnvironment != null) {
            superEnvironment.set(id, address);
        }
    }

    public Environment getSuperEnvironment() {
        return superEnvironment;
    }

    public void setSuperEnvironment(Environment superEnvironment) {
        this.superEnvironment = superEnvironment;
    }
}
