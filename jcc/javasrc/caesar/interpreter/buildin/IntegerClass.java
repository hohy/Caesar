package caesar.interpreter.buildin;

import caesar.interpreter.CaesarInterpreter;
import caesar.interpreter.InterpreterClass;
import caesar.interpreter.InterpreterOperation;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 10.12.11
 * Time: 10:21
 */
public class IntegerClass extends InterpreterClass {
    public static final Logger logger = Logger.getLogger(IntegerClass.class.getName());
    public IntegerClass() {
        super("Integer", 4);

        addOperation("equals", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "equals from IntegerClass is called.");
                int opa = interpreter.getStack().popInteger();
                int opb = interpreter.getStack().popInteger();
                boolean result = opa == opb;
                interpreter.getStack().pushBoolean(result);
            }
        });

        addOperation("addInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "addInteger from IntegerClass is called.");
                int opa = interpreter.getStack().popInteger();
                int opb = interpreter.getStack().popInteger();
                int result = opa + opb;
                interpreter.getStack().pushInteger(result);
            }
        });
    }

}
