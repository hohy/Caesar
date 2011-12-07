
package caesar.ast;

/**
 *
 * @author hohy
 */
public abstract class Tree {
    /**
     * Implementace navrhoveho vzoru Visitor.
     */
    public abstract void accept(TreeVisitor visitor);    
}
