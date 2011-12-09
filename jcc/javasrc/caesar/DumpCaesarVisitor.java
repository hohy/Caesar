package caesar;

import caesar.ast.AssignVariableTree;
import caesar.ast.BinaryTree;
import caesar.ast.ClassDefinitionTree;
import caesar.ast.ClassIdentifierTree;
import caesar.ast.ClassMethodIdentifierTree;
import caesar.ast.CommandListTree;
import caesar.ast.CommandTree;
import caesar.ast.CreateVariableTree;
import caesar.ast.FieldIdentifierTree;
import caesar.ast.IdentifierTree;
import caesar.ast.IfTree;
import caesar.ast.LiteralTree;
import caesar.ast.MethodDefinitionTree;
import caesar.ast.MethodIdentifierTree;
import caesar.ast.PrintlnTree;
import caesar.ast.ProgramTree;
import caesar.ast.TreeVisitor;


public class DumpCaesarVisitor implements TreeVisitor {

    private int stackDepth = 0;
    private String getSpaces() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stackDepth; i++) {            
            sb.append(' ');
        }
        return sb.toString();                
    }
    

    @Override
    public void visit(BinaryTree t) {
        System.out.println(getSpaces() + t.getOperator()  + " (BinaryTree)");
        stackDepth++;
        t.getLeftOperand().accept(this);
        t.getRightOperand().accept(this);
        stackDepth--;
    }

//    @Override
//    public void visit(IdentifierTree t) {
//        System.out.println(getSpaces() + t.getName() + " (IdentifierTree) - " + t.getType());
//        stackDepth++;
////        if(t.getExp() != null) t.getExp().accept(this);
//        stackDepth--;        
//    }

    @Override
    public void visit(LiteralTree t) {
        System.out.println(getSpaces() + t.getType() + " (" + t.getValue() + ")");
    }

    @Override
    public void visit(PrintlnTree t) {
        System.out.println(getSpaces() + "Println");
        stackDepth++;
        t.getExp().accept(this);
        stackDepth--;
    }

    @Override
    public void visit(CreateVariableTree t) {
        System.out.println(getSpaces() + "Create");
        stackDepth++;
        t.getIdentifier().accept(this);
        if(t.getExp() != null) t.getExp().accept(this);
        stackDepth--;
    }    
    
    @Override
    public void visit(AssignVariableTree t) {
        System.out.println(getSpaces() + "Assign");
        stackDepth++;
        t.getIdentifier().accept(this);
        t.getExp().accept(this);
        stackDepth--;
    }

    @Override
    public void visit(CommandListTree t) {
        System.out.println(getSpaces() + "commands: ");
        stackDepth++;
        for (CommandTree cmd : t.getCommands()) {
            cmd.accept(this);
        }
        stackDepth--;
    }

    @Override
    public void visit(ProgramTree t) {
        System.out.println(getSpaces() + "Program " + t.getIdentifier().getName());
        stackDepth++;
        t.getCommands().accept(this);
        stackDepth--;
    }


    @Override
    public void visit(IfTree t) {
        System.out.println(getSpaces() + "If");
        stackDepth++;
        t.getCondition().accept(this);
        t.getCommands().accept(this);
        if(t.getElseCommands() != null) t.getElseCommands().accept(this);
        stackDepth--;
    }

    @Override
    public void visit(ClassDefinitionTree t) {
        System.out.println(getSpaces() + "Class declaration " + t.getIdentifierTree().getName());
        stackDepth++;
        System.out.println(getSpaces() + "vars: ");
        stackDepth++;
        for (CreateVariableTree vartree : t.getVars()) {
            vartree.getIdentifier().accept(this);
            if(vartree.getExp() != null) vartree.getExp().accept(this);
        }
        stackDepth--;        
        stackDepth--;
    }

    @Override
    public void visit(ClassIdentifierTree t) {
        System.out.println(getSpaces() + "Class identifier " + t.getName() + "." + t.getFldName());
    }

    @Override
    public void visitMethodIdentifier(MethodIdentifierTree t) {
        System.out.println(getSpaces() + "Method call " + t.getMethodName());
    }

    @Override
    public void visit(ClassMethodIdentifierTree t) {
        System.out.println(getSpaces() + "Class method call " + t.getName() + "." + t.getMethodName());
    }

    @Override
    public void visit(FieldIdentifierTree t) {
        System.out.println(getSpaces() + "Field identifier " + t.getName());
    }

    @Override
    public void visit(MethodDefinitionTree t) {
        System.out.println(getSpaces() + "Method " + t.getName());
        stackDepth++;
        System.out.println("Return Type:");
        t.getReturnType().accept(this);
        System.out.println("Body:");
        t.getCommands().accept(this);
    }

    
    
}