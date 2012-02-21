package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 20.2.12
 * Time: 18:43
 */
public class Init {
    public static final byte code = 0x0C;

    public static void execute(CaesarBCInterpreter interpreter) {
        int objId = interpreter.readIntInstructionParam();
        CObject obj = interpreter.getHeap().get(objId);
        interpreter.getCClass(obj.getTypeCode()).getMethod(CMethod.INIT_METHOD_CODE).execute(interpreter);
        //interpreter.setPc(address);
    }
}
