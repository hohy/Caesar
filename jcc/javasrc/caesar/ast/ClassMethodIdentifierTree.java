/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

/**
 *
 * @author hohy
 */
public class ClassMethodIdentifierTree extends IdentifierTree {
    private String methodName;

    public ClassMethodIdentifierTree( String name, String methodName) {
        super(name);
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    
}
