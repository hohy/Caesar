/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

/**
 *
 * @author hohy
 */
public class ProgramTree extends Tree {
    IdentifierTree identifier;
    CommandListTree commands;

    public ProgramTree(IdentifierTree identifier, CommandListTree commands) {
        this.identifier = identifier;
        this.commands = commands;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public CommandListTree getCommands() {
        return commands;
    }

    public IdentifierTree getIdentifier() {
        return identifier;
    }
    
}
