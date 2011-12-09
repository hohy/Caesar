package caesar.ast;

/**
 *
 * @author hohy
 */
public class MethodDefinitionTree extends CommandTree {

    IdentifierTree returnType;
    IdentifierTree name;
    CommandListTree commands;

    public MethodDefinitionTree(IdentifierTree returnType, IdentifierTree name, CommandListTree commands) {
        this.returnType = returnType;
        this.name = name;
        this.commands = commands;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public CommandListTree getCommands() {
        return commands;
    }

    public IdentifierTree getName() {
        return name;
    }

    public IdentifierTree getReturnType() {
        return returnType;
    }

}