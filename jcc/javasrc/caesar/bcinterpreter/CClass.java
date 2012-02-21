package caesar.bcinterpreter;

import com.sun.org.apache.bcel.internal.classfile.ConstantFieldref;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:27
 */
public abstract class CClass implements Serializable {

    public static final int HEADER_SIZE = 8;
    
    protected String name;
    protected CClass extending;
    protected Map<Integer, CMethod> mtab;
    protected List<CField> fields;

    protected CaesarBCInterpreter interpreter;

    public CClass(CaesarBCInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public abstract int getObjectSize();
    public abstract int getCode();

    public CMethod getMethod(int code) {
        return mtab.get(code);
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFieldPos(String identifier) {
        int i = 0;
        for(CField fld : fields) {
            if(fld.getName().equals(identifier)) return i;
            i++;
        }
        return -1;
    }

    public CClass getFieldType(String varName) {
        for(CField fld : fields) {
            if(fld.getName().equals(varName)) return fld.getType();
        }
        return null;
    }
}
