package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CaesarBCInterpreter;
import caesar.bcinterpreter.CObject;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 14:43
 */
public class PushConstant {
    public static final byte code = 0x02;

    public static void execute(CaesarBCInterpreter interpreter) {
        // read address of constant
        int address = interpreter.readIntInstructionParam();

        CObject object = interpreter.getConstants().readObject(address);
        interpreter.getStack().pushObject(object);
    }
}
