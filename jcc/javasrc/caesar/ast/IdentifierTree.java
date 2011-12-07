/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

/**
 *
 * @author hohy
 */
public abstract class IdentifierTree extends ExpressionTree {
    private String name;
    String type;

    public IdentifierTree(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }    
}
