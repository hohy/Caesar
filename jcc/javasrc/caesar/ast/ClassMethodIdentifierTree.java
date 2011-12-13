/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

import java.util.List;

/**
 *
 * @author hohy
 */
public class ClassMethodIdentifierTree extends IdentifierTree {
    private String methodName;
    private List<ExpressionTree> params;

    public ClassMethodIdentifierTree(String name, String methodName, List<ExpressionTree> params) {
        super(name);
        this.methodName = methodName;
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }


    public List<ExpressionTree> getParams() {
        return params;
    }
}
