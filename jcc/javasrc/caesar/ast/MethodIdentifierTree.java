/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

/**
 *
 * @author hohy
 */
public class MethodIdentifierTree extends IdentifierTree {

    public MethodIdentifierTree(String name) {
        super(name);
        System.out.println("New method identifier tree: " + name);
    }

    @Override
    public void accept(TreeVisitor visitor) {
        System.out.println("Visit method identifier tree");
        visitor.visitMethodIdentifier(this);
    }

    public String getMethodName() {
        return super.getName();
    }

    
    
}
