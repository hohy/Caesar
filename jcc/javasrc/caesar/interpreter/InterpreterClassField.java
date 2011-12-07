package caesar.interpreter;

import caesar.ast.ExpressionTree;

/**
 * Represent field in class
 * @author hohy
 */
public class InterpreterClassField {
    private String name;
    private InterpreterClass type;
    private ExpressionTree initTree;
    private int offset;

    public InterpreterClassField(String name, InterpreterClass type, ExpressionTree initTree, int offset) {
        this.name = name;
        this.type = type;
        this.initTree = initTree;
        this.offset = offset;
    }

    public ExpressionTree getInitTree() {
        return initTree;
    }

    public String getName() {
        return name;
    }

    public InterpreterClass getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }        
}
