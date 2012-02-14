package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.2.12
 * Time: 16:53
 */
public class Load {
    public static final byte code = 0x04;

    public static void execute(CaesarBCInterpreter interpreter) {       
        int id = interpreter.readIntInstructionParam();
        int address = interpreter.getCurrentEnvironment().get(id);
        CObject obj = interpreter.getHeap().get(id);
        interpreter.getStack().pushObject(obj);
    }
}
