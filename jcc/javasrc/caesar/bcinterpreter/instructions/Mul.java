package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 21.2.12
 * Time: 20:51
 */
public class Mul {
    public static final byte code = 0x32;

    public static void execute(CaesarBCInterpreter interpreter) {
        CObject opA = interpreter.getStack().peekObject();
        interpreter.getCClass(opA.getTypeCode()).getMethod(CMethod.MUL_METHOD_CODE).execute(interpreter);
    }
}
