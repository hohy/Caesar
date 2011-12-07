/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

/**
 *
 * @author hohy
 */
public class PrintlnTree extends CommandTree {

    ExpressionTree exp;

    public PrintlnTree(ExpressionTree exp) {
        this.exp = exp;
    }
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public ExpressionTree getExp() {
        return exp;
    }
    
}
