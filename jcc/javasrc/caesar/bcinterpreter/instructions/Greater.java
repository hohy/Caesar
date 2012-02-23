package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 22.2.12
 * Time: 22:24
 */
public class Greater {
    public static final byte code = 0x42;

    public static void execute(CaesarBCInterpreter interpreter) {
        CObject opA = interpreter.getStack().peekObject();
        interpreter.getCClass(opA.getTypeCode()).getMethod(CMethod.GT_METHOD_CODE).execute(interpreter);
    }
}
