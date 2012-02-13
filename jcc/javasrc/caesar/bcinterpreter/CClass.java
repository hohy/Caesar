package caesar.bcinterpreter;

import com.sun.org.apache.bcel.internal.classfile.ConstantFieldref;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:27
 */
public abstract class CClass {

    public static final int HEADER_SIZE = 8;
    
    protected String name;
    protected CClass extending;
    protected Map<Integer, CMethod> mtab;
    protected List<CField> fields;

    public abstract int getObjectSize();
    public abstract int getCode();
}
