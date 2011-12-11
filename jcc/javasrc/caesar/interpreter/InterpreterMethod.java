package caesar.interpreter;

import caesar.ast.MethodDefinitionTree;
import caesar.ast.MethodParam;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 10.12.11
 * Time: 10:01
 */
public class InterpreterMethod implements InterpreterOperation {
    private MethodDefinitionTree methodDefinition;

    public InterpreterMethod(MethodDefinitionTree methodDefinition) {
        this.methodDefinition = methodDefinition;
    }

    @Override
    public void call(CaesarInterpreter interpreter) {
        for(MethodParam p : methodDefinition.getParams()) {
            try {
                // najdeme si tridu parametru
                InterpreterClass paramCls = interpreter.getInterpreterClass(p.getClassName());

                // prectu ze stacku hodnotu parametru
                byte[] data = interpreter.getStack().pop(paramCls.getObjectSize());

                // ulozit jej do heapy
                int pointer = interpreter.getHeap().store(data);

                // a dat pointer do environmentu pro operaci.
                InterpreterObject obj = new InterpreterObject(pointer, paramCls, interpreter.getCurrentEnv());
                interpreter.getCurrentEnv().add(p.getName(), obj);
            } catch (Exception e) {
                Logger.getLogger(InterpreterClass.class.getName(), "Unknown class " + p.getClassName());
            }
        }
        methodDefinition.getCommands().accept(interpreter);
    }

    @Override
    public List<MethodParam> getParams() {
        return methodDefinition.getParams();
    }
}
