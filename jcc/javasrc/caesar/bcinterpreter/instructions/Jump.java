package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Zmeni PC na hodnotu parametru.
 * User: Jan Hýbl
 * Date: 19.2.12
 * Time: 14:15
 */
public class Jump {
    public static final byte code = 0x0A;

    public static void execute(CaesarBCInterpreter interpreter) {
        int address = interpreter.readIntInstructionParam();
        interpreter.setPc(address);
    }
}
