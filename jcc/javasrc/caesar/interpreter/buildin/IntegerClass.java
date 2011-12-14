package caesar.interpreter.buildin;

import caesar.ast.MethodParam;
import caesar.interpreter.CaesarInterpreter;
import caesar.interpreter.Heap;
import caesar.interpreter.InterpreterClass;
import caesar.interpreter.InterpreterOperation;

import java.util.LinkedList;
import java.util.List;
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

        final List<MethodParam> intParams = new LinkedList<MethodParam>();
        intParams.add(new MethodParam("x", "Integer"));
        intParams.add(new MethodParam("y", "Integer"));
        
        final List<MethodParam> realParams = new LinkedList<MethodParam>();
        realParams.add(new MethodParam("x", "Integer"));
        realParams.add(new MethodParam("y", "Real"));

        addOperation("equals", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "equals from IntegerClass is called.");
                int opa = interpreter.getStack().popInteger();
                int opb = interpreter.getStack().popInteger();
                boolean result = opa == opb;
                interpreter.getStack().pushBoolean(result);
            }

            @Override
            public List<MethodParam> getParams() {
                List<MethodParam> params = new LinkedList<MethodParam>();
                params.add(new MethodParam("x", "Integer"));
                return params;
            }

            @Override
            public String getReturnType() {
                return "Boolean";
            }
        });

        addOperation("lt", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "lt from IntegerClass is called.");
                int opb = interpreter.getStack().popInteger();
                int opa = interpreter.getStack().popInteger();
                boolean result = opa < opb;
                interpreter.getStack().pushBoolean(result);
            }

            @Override
            public List<MethodParam> getParams() {
                List<MethodParam> params = new LinkedList<MethodParam>();
                params.add(new MethodParam("x", "Integer"));
                return params;
            }

            @Override
            public String getReturnType() {
                return "Boolean";
            }
        });

        addOperation("addInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "addInteger from IntegerClass is called.");
                int opb = interpreter.getStack().popInteger();
                int opa = interpreter.getStack().popInteger();
                int result = opa + opb;
                interpreter.getStack().pushInteger(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParams;
            }

            @Override
            public String getReturnType() {
                return "Integer";
            }
        });

        addOperation("subtractInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "subtractInteger from IntegerClass is called.");
                int opb = interpreter.getStack().popInteger();
                int opa = interpreter.getStack().popInteger();
                int result = opa - opb;
                interpreter.getStack().pushInteger(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParams;
            }

            @Override
            public String getReturnType() {
                return "Integer";
            }
        });

        addOperation("multiplyInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "multiplyInteger from IntegerClass is called.");
                int opb = interpreter.getStack().popInteger();
                int opa = interpreter.getStack().popInteger();
                int result = opa * opb;
                interpreter.getStack().pushInteger(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParams;
            }

            @Override
            public String getReturnType() {
                return "Integer";
            }
        });

        addOperation("divideInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "divideInteger from IntegerClass is called.");
                int opb = interpreter.getStack().popInteger();
                int opa = interpreter.getStack().popInteger();
                double result = opa / opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParams;
            }

            @Override
            public String getReturnType() {
                return "Real";
            }
        });

        addOperation("addReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "addReal from IntegerClass is called.");
                double opb = interpreter.getStack().popDouble();
                int opa = interpreter.getStack().popInteger();
                double result = opa + opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return "Real";
            }
        });

        addOperation("subtractReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "subtractReal from IntegerClass is called.");
                double opb = interpreter.getStack().popDouble();
                int opa = interpreter.getStack().popInteger();
                double result = opa - opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return "Real";
            }
        });

        addOperation("multiplyReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "multiplyReal from IntegerClass is called.");
                double opb = interpreter.getStack().popDouble();
                int opa = interpreter.getStack().popInteger();
                double result = opa * opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return "Real";
            }
        });

        addOperation("divideReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "divideReal from IntegerClass is called.");
                double opb = interpreter.getStack().popDouble();
                int opa = interpreter.getStack().popInteger();
                double result = opa / opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return "Real";
            }
        });
        
        addOperation("addString", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.fine("addString from IntegerClass is called");
                String opb = interpreter.getStack().popString();
                int opa = interpreter.getStack().popInteger();
                String result = opa + opb;
                interpreter.getStack().pushString(result);
            }

            @Override
            public List<MethodParam> getParams() {
                LinkedList<MethodParam> stringParams = new LinkedList<MethodParam>();
                stringParams.add(new MethodParam("x", "Integer"));
                stringParams.add(new MethodParam("y", "String"));
                return stringParams;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getReturnType() {
                return "String";
            }
        });
    }

}
