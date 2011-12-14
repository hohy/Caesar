package caesar.interpreter.buildin;

import caesar.ast.MethodParam;
import caesar.interpreter.CaesarInterpreter;
import caesar.interpreter.InterpreterClass;

import caesar.interpreter.InterpreterMethod;
import caesar.interpreter.InterpreterOperation;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.12.11
 * Time: 13:28
 */
public class RealClass extends InterpreterClass {

    public static final Logger logger = Logger.getLogger(RealClass.class.getName());

    public RealClass() {
        super("Real", 8);

        final List<MethodParam> intParams = new LinkedList<MethodParam>();
        intParams.add(new MethodParam("x", "Real"));
        intParams.add(new MethodParam("y", "Integer"));

        final List<MethodParam> realParams = new LinkedList<MethodParam>();
        realParams.add(new MethodParam("x", "Real"));
        realParams.add(new MethodParam("y", "Real"));

        addOperation("equals", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "equals from RealClass is called.");
                double opa = interpreter.getStack().popDouble();
                double opb = interpreter.getStack().popDouble();
                boolean result = opa == opb;
                interpreter.getStack().pushBoolean(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("lt", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "lt from RealClass is called.");
                double opb = interpreter.getStack().popDouble();
                double opa = interpreter.getStack().popDouble();
                boolean result = opa < opb;
                interpreter.getStack().pushBoolean(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("addInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "addInteger from RealClass is called.");
                int opb = interpreter.getStack().popInteger();
                double opa = interpreter.getStack().popDouble();
                double result = opa + opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("subtractInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "subtractInteger from RealClass is called.");
                int opb = interpreter.getStack().popInteger();
                double opa = interpreter.getStack().popDouble();
                double result = opa - opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("multiplyInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "multiplyInteger from RealClass is called.");
                int opb = interpreter.getStack().popInteger();
                double opa = interpreter.getStack().popDouble();
                double result = opa * opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("divideInteger", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "divideInteger from RealClass is called.");
                int opb = interpreter.getStack().popInteger();
                double opa = interpreter.getStack().popDouble();
                double result = opa / opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return intParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("addReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "addReal from RealClass is called.");
                double opb = interpreter.getStack().popDouble();
                double opa = interpreter.getStack().popDouble();
                double result = opa + opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("subtractReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "subtractReal from RealClass is called.");
                double opb = interpreter.getStack().popDouble();
                double opa = interpreter.getStack().popDouble();
                double result = opa - opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("multiplyReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "multiplyReal from RealClass is called.");
                double opb = interpreter.getStack().popDouble();
                double opa = interpreter.getStack().popDouble();
                double result = opa * opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });

        addOperation("divideReal", new InterpreterOperation() {
            @Override
            public void call(CaesarInterpreter interpreter) {
                logger.log(Level.FINE, "divideReal from RealClass is called.");
                double opb = interpreter.getStack().popDouble();
                double opa = interpreter.getStack().popDouble();
                double result = opa / opb;
                interpreter.getStack().pushDouble(result);
            }

            @Override
            public List<MethodParam> getParams() {
                return realParams;
            }

            @Override
            public String getReturnType() {
                return getName();
            }
        });
    }
}
