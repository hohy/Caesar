package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 17.2.12
 * Time: 16:15
 */
public class Set {
    public static final byte code = 0x06;

    public static void execute(CaesarBCInterpreter interpreter) {
        int id = interpreter.readIntInstructionParam();
        CObject obj = interpreter.getStack().popObject();
        interpreter.getHeap().set(id, obj);
    }
}
