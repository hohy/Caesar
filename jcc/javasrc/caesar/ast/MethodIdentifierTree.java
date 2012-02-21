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
public class MethodIdentifierTree extends IdentifierTree {

    private List<ExpressionTree> params;

    public MethodIdentifierTree(String name, List<ExpressionTree> params) {
        super(name);
        this.params = params;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visitMethodIdentifier(this);
    }

    public String getMethodName() {
        return super.getName();
    }

    public List<ExpressionTree> getParams() {
        return params;
    }
    
}
