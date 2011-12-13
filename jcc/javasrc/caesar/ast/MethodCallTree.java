package caesar.ast;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 10.12.11
 * Time: 13:19
 */
public class MethodCallTree extends CommandTree {
    String objName;
    String methodName;
    List<ExpressionTree> paramsExpressions;
    private String returnType;

    public MethodCallTree(ClassIdentifierTree ident, List<ExpressionTree> params) {
        this.objName = ident.getName();
        this.methodName = ident.getFldName();
        this.paramsExpressions = params;
    }

    public MethodCallTree(FieldIdentifierTree ident, List<ExpressionTree> params) {
        this.methodName = ident.getName();
        this.paramsExpressions = params;
    }

    public MethodCallTree(String objName, String methodName) {
        this.objName = objName;
        this.methodName = methodName;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public String getObjName() {
        return objName;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<ExpressionTree> getParamsExpressions() {
        return paramsExpressions;
    }

    @Override
    public String toString() {
        return objName + "." + methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
