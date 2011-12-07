package caesar;
public class DumpCaesarVisitor implements CaesarVisitor {

    private int stackDepth = 0;
    private String getSpaces() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stackDepth; i++) {            
            sb.append(' ');
        }
        return sb.toString();                
    }
    
    
    @Override
    public Object visit(SimpleNode node, Object data) {
        System.out.println(getSpaces() + node +
                       ": acceptor not unimplemented in subclass?");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTStart node, Object data) {
        System.out.println("Program");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        System.out.println("End of program.");
        return data;
    }

    @Override
    public Object visit(ASTCommandList node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTCommand node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTCreateVariableCmd node, Object data) {
        System.out.println(getSpaces() + "var ");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTAssignVariableCmd node, Object data) {
        System.out.println(getSpaces() + "Assing");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTIfCmd node, Object data) {
        System.out.println(getSpaces() + "If");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTElsePart node, Object data) {
        System.out.println(getSpaces() + "else");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTCondition node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTRelationalOperator node, Object data) {
        System.out.println(getSpaces() + node.getOperator());
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTWhileCmd node, Object data) {
        System.out.println(getSpaces() + "While");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTPrintlnCmd node, Object data) {
        System.out.println(getSpaces() + "Println");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTExpression node, Object data) {
        if(!node.getOperator().equals(Operator.NULL))
            System.out.println(getSpaces() + node.getOperator() + " (E)");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;                        
    }

    @Override
    public Object visit(ASTExpressionX node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTTerm node, Object data) {
        if(!node.getOperator().equals(Operator.NULL))
            System.out.println(getSpaces() + node.getOperator() + " (T)");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTTermX node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFactor node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTFactorRest node, Object data) {
        data = node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        System.out.println(getSpaces() + "'" + node.getName() + "'");
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;        
        return data;
    }

    @Override
    public Object visit(ASTIntConst node, Object data) {
        System.out.println(getSpaces() + node.getValue());
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

    @Override
    public Object visit(ASTRealConst node, Object data) {
        System.out.println(getSpaces() + node.getValue());
        stackDepth++;
        data = node.childrenAccept(this, data);
        stackDepth--;
        return data;
    }

}