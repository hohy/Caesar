package caesar.interpreter.buildin;

import caesar.ast.MethodParam;
import caesar.interpreter.CaesarInterpreter;
import caesar.interpreter.InterpreterClass;
import caesar.interpreter.InterpreterOperation;

import java.lang.invoke.MethodHandle;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.12.11
 * Time: 17:04
 */
public class StringClass extends InterpreterClass {

    public static final Logger logger = Logger.getLogger(IntegerClass.class.getName());

    public StringClass() {
        super("String", -1);

        final List<MethodParam> stringParam = new LinkedList<MethodParam>();
        stringParam.add(new MethodParam("x", "String"));
        stringParam.add(new MethodParam("y", "String"));
        
        final List<MethodParam> intParam = new LinkedList<MethodParam>();
        intParam.add(new MethodParam("x", "Integer"));
        intParam.add(new MethodParam("y", "String"));

        final List<MethodParam> realParam = new LinkedList<MethodParam>();
        realParam.add(new MethodParam("x", "Real"));
        realParam.add(new MethodParam("y", "String"));

        addOperation("equals", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "equals from StringClass is called.");
                String opa = interpreter.getStack().popString();
                String opb = interpreter.getStack().popString();
                boolean result = opa.equals(opb);
                interpreter.getStack().pushBoolean(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return stringParam;
            }

            @Override
            public String getReturnType() {
                return "Boolean";
            }
        });

        addOperation("addString", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "addString from StringClass is called.");
                String opa = interpreter.getStack().popString();
                String opb = interpreter.getStack().popString();
                String result = opa + opb;
                interpreter.getStack().pushString(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return stringParam;
            }

            @Override
            public String getReturnType() {
                return "String";
            }
        });

        addOperation("addInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "addString from StrinClass is called.");
                Integer opb = interpreter.getStack().popInteger();
                String opa = interpreter.getStack().popString();
                String result = opa + opb;
                interpreter.getStack().pushString(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParam;
            }

            @Override
            public String getReturnType() {
                return "String";
            }
        });


        addOperation("addReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "addReal from StringClass is called.");
                Double opb = interpreter.getStack().popDouble();
                String opa = interpreter.getStack().popString();
                String result = opa + opb;
                interpreter.getStack().pushString(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParam;
            }

            @Override
            public String getReturnType() {
                return "String";
            }
        });
    }
}
