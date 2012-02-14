package caesar.bccompiler;

import caesar.ast.*;
import caesar.bcinterpreter.ByteConvertor;
import caesar.bcinterpreter.CBCFile;
import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.buildin.IntegerClass;
import caesar.bcinterpreter.buildin.StringClass;
import caesar.bcinterpreter.instructions.Print;
import caesar.bcinterpreter.instructions.PushConstant;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:46
 */
public class CaesarBCCompiler implements TreeVisitor {

    CBCFile outputFile;
    List<Byte> constants;
    List<Byte> bytecode;
    List<CClass> classList;
    
    public void writeOutput(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path + ".cbc");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(outputFile);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    @Override
    public void visit(ProgramTree t) {
        constants = new LinkedList<Byte>();
        bytecode = new LinkedList<Byte>();
        classList = new LinkedList<CClass>();

        t.getIdentifier().accept(this);
        t.getCommands().accept(this);
        
        byte[] byteArray = new byte[bytecode.size()];
        for(int i = 0; i < byteArray.length; i++) {
            byteArray[i] = bytecode.get(i);
        }
        outputFile = new CBCFile();
        outputFile.setBytecode(byteArray);

        byteArray = new byte[constants.size()];
        for(int i = 0; i < byteArray.length; i++) {
            byteArray[i] = constants.get(i);
        }

        outputFile.setConstants(byteArray);
        outputFile.setClassList(classList);

    }
    
    @Override
    public void visit(BinaryTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Registers new constant and pushes it to stack
     * @param t
     */
    @Override
    public void visit(LiteralTree t) {
        int address;
        CObject obj = null;
        // store constant
        if(t.getType().equals("Integer")) {
            byte[] data = ByteConvertor.toByta((Integer) t.getValue());
            obj = new CObject(IntegerClass.code, data);
        } else if(t.getType().equals("Real")) {
            // TODO: implement Real class
        } else if(t.getType().equals("String")) {
            byte[] data = ByteConvertor.toByta((String) t.getValue());
            obj = new CObject(StringClass.code, data);
        }
        address = addNewConstant(obj);
        // add push instr.
        bytecode.add(PushConstant.code);
        // add constant address
        addIntToByteList(address, bytecode);
    }

    /**
     * Pushes print instruction
     * @param t
     */
    @Override
    public void visit(PrintlnTree t) {
        t.getExp().accept(this);
        bytecode.add(Print.code);
    }

    @Override
    public void visit(AssignVariableTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(CreateVariableTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(CommandListTree t) {
        for(CommandTree command : t.getCommands()) {
            command.accept(this);
        }
    }

    @Override
    public void visit(IfTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ClassDefinitionTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ClassIdentifierTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visitMethodIdentifier(MethodIdentifierTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ClassMethodIdentifierTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(FieldIdentifierTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(MethodDefinitionTree aThis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(MethodCallTree methodCallTree) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ReturnTree returnTree) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(WhileTree whileTree) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public int addNewConstant(CObject object) {
        int address = constants.size();
        for(byte b : object.getData()) constants.add(b);
        return address;
    }
    
    public static void addIntToByteList(int c, List<Byte> list) {
        byte[] bytes = ByteConvertor.toByta(c);
        for(byte b : bytes) list.add(b);
    }
    
}
