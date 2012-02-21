package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 21.2.12
 * Time: 15:18
 */
public class Add {
    public static final byte code = 0x30;

    public static void execute(CaesarBCInterpreter interpreter) {
        CObject opA = interpreter.getStack().peekObject();
        interpreter.getCClass(opA.getTypeCode()).getMethod(CMethod.ADD_METHOD_CODE).execute(interpreter);
    }
}
