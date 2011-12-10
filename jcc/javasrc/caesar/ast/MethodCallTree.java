package caesar.ast;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 10.12.11
 * Time: 13:19
 */
public class MethodCallTree extends CommandTree {
    String objName;
    String methodName;

    public MethodCallTree(ClassIdentifierTree ident) {
        this.objName = ident.getName();
        this.methodName = ident.getFldName();
    }

    public MethodCallTree(FieldIdentifierTree ident) {
        this.methodName = ident.getName();
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

    @Override
    public String toString() {
        return objName + "." + methodName;
    }
}
