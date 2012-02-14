package caesar.bccompiler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.2.12
 * Time: 11:01
 */
public class CompilerEnvironment {
    
    private CompilerEnvironment superEnvironment;
    private Map<String, ObjectInfo> map = new HashMap<String, ObjectInfo>();

    public CompilerEnvironment(CompilerEnvironment superEnvironment) {
        this.superEnvironment = superEnvironment;
    }
    
    public void add(String name, ObjectInfo info) {
        map.put(name, info);
    }
    
    public ObjectInfo get(String name) {
        if(map.containsKey(name)) {
            return map.get(name);
        } else if(superEnvironment != null) {
            return superEnvironment.get(name);
        }

        return null;
    }
}
