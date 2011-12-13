package caesar.interpreter;

import caesar.ast.MethodParam;

import java.util.List;

/**
 *
 * @author hohy
 */
public interface InterpreterOperation {
    public void call(CaesarInterpreter interpreter);
    public List<MethodParam> getParams();

    String getReturnType();
}
