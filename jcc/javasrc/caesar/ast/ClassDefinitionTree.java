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
public class ClassDefinitionTree extends CommandTree {

    private IdentifierTree identifierTree;
    private List<CreateVariableTree> vars;
    private List<MethodDefinitionTree> methods;

    public ClassDefinitionTree(IdentifierTree identifierTree, List<CreateVariableTree> vars, List<MethodDefinitionTree> methods) {
        this.identifierTree = identifierTree;
        this.vars = vars;
        this.methods = methods;
    }
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public IdentifierTree getIdentifierTree() {
        return identifierTree;
    }

    public List<CreateVariableTree> getVars() {
        return vars;
    }

    public List<MethodDefinitionTree> getMethods() {
        return methods;
    }
}
