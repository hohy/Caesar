package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 14:16
 */
public class Print {

    public static final byte code = 0x01;

    public static void execute(CaesarBCInterpreter interpreter) {
        CObject object = interpreter.getStack().peekObject();
        CClass cls = interpreter.getCClass(object.getTypeCode());
        CMethod printMethod = cls.getMethod(CMethod.PRINT_METHOD_CODE);
        printMethod.execute(interpreter);
    }
}
