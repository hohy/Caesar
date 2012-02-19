package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Zjisti adresu promenne na heape a ulozi ji na zasobnik
 * User: Jan Hýbl
 * Date: 18.2.12
 * Time: 20:01
 */
public class Point {
    public static final byte code = 0x08;

    public static void execute(CaesarBCInterpreter interpreter) {
        int id = interpreter.readIntInstructionParam();
        int address = interpreter.getCurrentEnvironment().get(id);
        interpreter.getStack().pushInt(address);
    }
}
