package caesar.bccompiler;

import caesar.bcinterpreter.CClass;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.2.12
 * Time: 16:16
 */
public class ObjectInfo {
    private int id;
    private CClass type;

    public ObjectInfo(int id, CClass type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public CClass getType() {
        return type;
    }
}
