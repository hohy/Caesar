/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.ast;

/**
 *
 * @author hohy
 */
public class IfTree extends CommandTree {

    BinaryTree condition;
    CommandListTree commands;
    CommandListTree elseCommands;

    public IfTree(BinaryTree condition, CommandListTree commands, CommandListTree elseCommands) {
        this.condition = condition;
        this.commands = commands;
        this.elseCommands = elseCommands;
    }
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public CommandListTree getCommands() {
        return commands;
    }

    public BinaryTree getCondition() {
        return condition;
    }

    public CommandListTree getElseCommands() {
        return elseCommands;
    }
    
}
