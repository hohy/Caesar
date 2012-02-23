package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 22.2.12
 * Time: 22:18
 */
public class NotEqual {
    public static final byte code = 0x41;

    public static void execute(CaesarBCInterpreter interpreter) {
        CObject opA = interpreter.getStack().peekObject();
        interpreter.getCClass(opA.getTypeCode()).getMethod(CMethod.NE_METHOD_CODE).execute(interpreter);
    }
}
