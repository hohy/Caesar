package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CaesarBCInterpreter;
import caesar.bcinterpreter.Environment;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 23.2.12
 * Time: 10:15
 */
public class NewEnv {
    public static final byte code = 0x0E;

    public static void execute(CaesarBCInterpreter interpreter) {
        interpreter.setCurrentEnvironment(new Environment(interpreter.getCurrentEnvironment()));
    }
}
