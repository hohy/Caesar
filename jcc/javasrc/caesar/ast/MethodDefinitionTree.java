package caesar.ast;

import java.util.List;

/**
 *
 * @author hohy
 */
public class MethodDefinitionTree extends CommandTree {

    IdentifierTree className;
    IdentifierTree returnType;
    IdentifierTree name;
    CommandListTree commands;
    List<MethodParam> params;

    public MethodDefinitionTree(IdentifierTree className, IdentifierTree returnType, IdentifierTree name, List<MethodParam> params, CommandListTree commands) {
        this.className = className;
        this.returnType = returnType;
        this.name = name;
        this.params = params;
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

    public IdentifierTree getClassName() {
        return className;
    }

    public List<MethodParam> getParams() {
        return params;
    }
}