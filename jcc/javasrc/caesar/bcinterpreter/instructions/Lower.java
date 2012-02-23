package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 22.2.12
 * Time: 22:25
 */
public class Lower {
    public static final byte code = 0x43;

    public static void execute(CaesarBCInterpreter interpreter) {
        CObject opA = interpreter.getStack().peekObject();
        interpreter.getCClass(opA.getTypeCode()).getMethod(CMethod.LT_METHOD_CODE).execute(interpreter);
    }
}
