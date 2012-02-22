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

    public static final int ADD_METHOD_CODE = 10;
    public static final int SUB_METHOD_CODE = 11;
    public static final int MUL_METHOD_CODE = 12;
    public static final int DIV_METHOD_CODE = 13;
    public static final int EQ_METHOD_CODE = 20;

    private CClass returnType;
    private String name;
    private int code;
    private CompilerEnvironment methodEnvironment;
    private List<String> params = new LinkedList<String>();

    protected CMethod(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    protected CMethod(Integer code, String name, List<String> params) {
        this.code = code;
        this.name = name;
        this.params = params;
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

    public CClass getReturnType() {
        return returnType;
    }

    public void setReturnType(CClass returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    };
}
