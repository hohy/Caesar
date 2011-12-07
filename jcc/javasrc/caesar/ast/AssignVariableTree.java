/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

/**
 *
 * @author hohy
 */
public class AssignVariableTree extends CommandTree {

    IdentifierTree identifier;
    ExpressionTree exp;

    public AssignVariableTree(IdentifierTree identifier, ExpressionTree exp) {
        this.identifier = identifier;
        this.exp = exp;
    }
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public ExpressionTree getExp() {
        return exp;
    }

    public IdentifierTree getIdentifier() {
        return identifier;
    }    
}
