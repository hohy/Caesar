package caesar.bcinterpreter.buildin;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CaesarBCInterpreter;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 21.2.12
 * Time: 9:48
 */
public class MethodsClass extends CClass {

    public static final int code = 5;
    
    public MethodsClass(CaesarBCInterpreter interpreter) {
        super(interpreter);
        mtab = new HashMap<Integer, CMethod>();
    }

    @Override
    public int getObjectSize() {
        return 0;  //To change body of implemented methods use FileClass | Settings | FileClass Templates.
    }

    @Override
    public int getCode() {
        return code;
    }

    public void addMethod(int i, UserDefinedMethod method) {
        mtab.put(i, method);
    }
}
