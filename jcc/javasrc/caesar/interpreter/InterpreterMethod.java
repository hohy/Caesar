package caesar.interpreter;

import caesar.ast.MethodDefinitionTree;

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
        methodDefinition.getCommands().accept(interpreter);
    }
}
