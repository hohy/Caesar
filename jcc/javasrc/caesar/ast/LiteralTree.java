/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

/**
 *
 * @author hohy
 */
public class LiteralTree extends ExpressionTree {

    Object value;
    String type;
    String objType;

    public LiteralTree(Object value, String type) {
        this.value = value;
        this.type = type;
    }
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String getType() {
        return type;
    }

}
