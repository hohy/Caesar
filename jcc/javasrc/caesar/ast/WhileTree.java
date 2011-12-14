package caesar.ast;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.12.11
 * Time: 22:24
 */
public class WhileTree extends CommandTree {

    private BinaryTree condition;
    private CommandListTree commands;

    public WhileTree(BinaryTree condition, CommandListTree commands) {
        this.condition = condition;
        this.commands = commands;
    }

    public BinaryTree getCondition() {
        return condition;
    }

    public CommandListTree getCommands() {
        return commands;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }
}
