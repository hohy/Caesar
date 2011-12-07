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
public class CommandListTree extends Tree {

    List<CommandTree> commands;

    public CommandListTree(List<CommandTree> commands) {
        this.commands = commands;
    }    
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public List<CommandTree> getCommands() {
        return commands;
    }
    
}
