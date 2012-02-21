package caesar.bcinterpreter;

import caesar.bccompiler.CompilerEnvironment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:30
 */
public abstract class CMethod {

    public static final int PRINT_METHOD_CODE = 1;
    public static final int INIT_METHOD_CODE = 2;

    private CClass returnType;
    private String name;
    private int code;
    private CompilerEnvironment methodEnvironment;
    private List<String> params = new LinkedList<String>();

    protected CMethod(Integer code) {
        this.code = code;
    }

    public abstract void execute(CaesarBCInterpreter interpreter);

    public int getCode() {
        return code;
    }

    public CompilerEnvironment getMethodEnvironment() {
        return methodEnvironment;
    }

    public void setMethodEnvironment(CompilerEnvironment methodEnvironment) {
        this.methodEnvironment = methodEnvironment;
    }

    public List<String> getParams() {
        return params;
    }
    
    public void addParam(String name) {
        params.add(name);
    }
}
