package caesar.bccompiler;

import caesar.ast.*;
import caesar.bcinterpreter.*;
import caesar.bcinterpreter.buildin.*;
import caesar.bcinterpreter.instructions.*;
import caesar.bcinterpreter.instructions.Set;
import com.sun.org.apache.xpath.internal.operations.NotEquals;
import sun.security.util.Cache;

import java.io.*;
import java.util.*;

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
    Map<String, CClass> classNameMap;
    Map<String, CMethod> methodMap;            
    CompilerEnvironment currentEnvironment;
    int vars = 0;
    int methodCounter = 100;

    UserDefinedClass currentClassDefinition;
    MethodsClass methods;

    public CBCFile getOutputFile() {
        return outputFile;
    }

    public void writeOutput(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path + ".cbc");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(outputFile);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use FileClass | Settings | FileClass Templates.
        }
    }
    
    @Override
    public void visit(ProgramTree t) {
        constants = new LinkedList<Byte>();
        bytecode = new LinkedList<Byte>();
        classList = new LinkedList<CClass>();
        currentEnvironment = new CompilerEnvironment(null);
        classNameMap = new HashMap<String, CClass>();
        methodMap = new HashMap<String, CMethod>();
        addToClassMap(new IntegerClass(null));
        addToClassMap(new StringClass(null));
        addToClassMap(new DemoClass(null));
        addToClassMap(new ArrayClass(null));
        addToClassMap(new FileClass(null));
        methods = new MethodsClass(null);
        classList.add(methods);
        addToClassMap(methods);
        vars = 0;

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

    public void addToClassMap(CClass cls) {
        classNameMap.put(cls.getName(), cls);
        for(CMethod method : cls.getMethods()) {
            methodMap.put(cls.getName()+"."+method.getName(), method);
        }
    }

    @Override
    public void visit(BinaryTree bt) {
        Operator op = bt.getOperator();
        bt.getRightOperand().accept(this);
        bt.getLeftOperand().accept(this);
        switch (op) {
            case PLUS: bytecode.add(Add.code); break;
            case MINUS: bytecode.add(Sub.code); break;
            case MULTIPLY: bytecode.add(Mul.code); break;
            case DIVIDE: bytecode.add(Div.code); break;
            case EQ: bytecode.add(Equal.code); break;
            case NE: bytecode.add(NotEqual.code); break;
            case GT: bytecode.add(Greater.code); break;
            case LT: bytecode.add(Lower.code); break;
            case GE: bytecode.add(GreaterEqual.code); break;
            case LE: bytecode.add(LowerEqual.code); break;
        }
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
    public void visit(AssignVariableTree t) {
        t.getExp().accept(this);
        String varName = t.getIdentifier().getName();
        int id = currentEnvironment.get(varName).getId();
        if(t.getIdentifier() instanceof FieldIdentifierTree) {
            bytecode.add(Set.code);
            addIntToByteList(id, bytecode);
        } else {
            ClassIdentifierTree cit = (ClassIdentifierTree) t.getIdentifier();
            CClass currentType = currentEnvironment.get(varName).getType();
            
            if(cit.getIndetifiers().size() > 1) { // a.b.c.d
                bytecode.add(Load.code);            // Load a
                addIntToByteList(id, bytecode);                
                for(int i = 0; i < cit.getIndetifiers().size() - 2; i++) { // Load b
                    varName = cit.getIndetifiers().get(i);
                    bytecode.add(LoadField.code);
                    addIntToByteList(currentType.getFieldPos(varName),bytecode);
                    currentType = currentType.getFieldType(varName);
                }

                // c
                varName = cit.getIndetifiers().get(cit.getIndetifiers().size()-2);
                bytecode.add(PointField.code);
                addIntToByteList(currentType.getFieldPos(varName),bytecode);
                currentType = currentType.getFieldType(varName);
                //d
                varName = cit.getIndetifiers().get(cit.getIndetifiers().size()-1);
                bytecode.add(SetField.code);
                addIntToByteList(currentType.getFieldPos(varName),bytecode);
            } else { // a.b
                bytecode.add(Point.code);
                addIntToByteList(id, bytecode);
                varName = cit.getIndetifiers().get(0);
                bytecode.add(SetField.code);
                addIntToByteList(currentType.getFieldPos(varName),bytecode);
            }

        }
    }

    @Override
    public void visit(CreateVariableTree t) {
        CClass type;
        if(t.getExp() != null) {
            t.getExp().accept(this);
            type = classNameMap.get(t.getIdentifier().getType());
            if(type.getCode()==MethodsClass.code) {
                if(t.getExp() instanceof MethodIdentifierTree)
                    type = methodMap.get(((MethodIdentifierTree)t.getExp()).getName()).getReturnType();
                else if(t.getExp() instanceof ClassMethodIdentifierTree) {
                    ClassMethodIdentifierTree cmit = (ClassMethodIdentifierTree)t.getExp();
                    ObjectInfo obj = currentEnvironment.get(cmit.getName());
                    type = methodMap.get(obj.getType().getName() + "." + cmit.getMethodName()).getReturnType();
                } else if(t.getExp() instanceof FieldIdentifierTree)
                    type = currentEnvironment.get(((FieldIdentifierTree)t.getExp()).getName()).getType();
            }
        } else {
            type = classNameMap.get(t.getIdentifier().getType());
        }
        bytecode.add(New.code);
        int varId = vars++;
        ObjectInfo i = new ObjectInfo(varId,type);
        currentEnvironment.add(t.getIdentifier().getName(), i);
        addIntToByteList(type.getCode(), bytecode);
        addIntToByteList(varId, bytecode);
    }

    @Override
    public void visit(CommandListTree t) {
        for(CommandTree command : t.getCommands()) {
            command.accept(this);
        }
    }

    @Override
    public void visit(IfTree it) {
        it.getCondition().accept(this);
        bytecode.add(JumpIfTrue.code);
        int jumpAddress = bytecode.size();
        addIntToByteList(jumpAddress, bytecode);
        currentEnvironment = new CompilerEnvironment(currentEnvironment);
        // compile else part
        if(it.getElseCommands() != null) it.getElseCommands().accept(this);
        currentEnvironment = currentEnvironment.getSuperEnvironment();
        bytecode.add(Jump.code);
        int elseEndAddress = bytecode.size();
        addIntToByteList(elseEndAddress, bytecode);
        setIntInBytecode(jumpAddress, bytecode.size());
        currentEnvironment = new CompilerEnvironment(currentEnvironment);
        // compile else part
        it.getCommands().accept(this);
        currentEnvironment = currentEnvironment.getSuperEnvironment();
        setIntInBytecode(elseEndAddress,  bytecode.size());
    }

    @Override
    public void visit(ClassDefinitionTree t) {
        currentClassDefinition = new UserDefinedClass(null);
        currentClassDefinition.setCode(classList.size()+100);
        currentClassDefinition.setName(t.getIdentifierTree().getName());

        classList.add(currentClassDefinition);
        classNameMap.put(currentClassDefinition.getName(), currentClassDefinition);
        currentEnvironment = new CompilerEnvironment(currentEnvironment);
        
        List<CreateVariableTree> initVars = new LinkedList<CreateVariableTree>();
        
        for(CreateVariableTree var : t.getVars()) {
            String varName = var.getIdentifier().getName();
            CClass varType = classNameMap.get(var.getIdentifier().getType());
            currentEnvironment.add(varName,new ObjectInfo(currentClassDefinition.getFields().size(), varType));
            currentClassDefinition.addField(varName, varType);

        }
        
        CMethod init = new UserDefinedMethod(bytecode.size(), "init", CMethod.INIT_METHOD_CODE);
        bytecode.add(Jump.code);
        int jumpAddress = bytecode.size();
        addIntToByteList(jumpAddress, bytecode);

        int i = 0;
        for(CField fld : currentClassDefinition.getFields()) {
            bytecode.add(New.code);
            addIntToByteList(fld.getType().getCode(), bytecode);
            addIntToByteList(i++, bytecode);
        }
        bytecode.add(Return.code);
        int endaddr = bytecode.size();
        byte[] end = ByteConvertor.toByta(endaddr);
        for(int j = 0; j<end.length; j++) {
            bytecode.set(jumpAddress+j, end[j]);
        }

        currentClassDefinition.addMethod(CMethod.INIT_METHOD_CODE,init);
        
        for(MethodDefinitionTree mth : t.getMethods()) {
            mth.accept(this);
        }
        currentEnvironment = currentEnvironment.getSuperEnvironment();
    }


    @Override
    public void visit(MethodDefinitionTree t) {
        bytecode.add(Jump.code);
        int jumpAddress = bytecode.size();
        addIntToByteList(jumpAddress, bytecode);
        UserDefinedMethod method = new UserDefinedMethod(bytecode.size(),t.getName().getName(), methodCounter++);

        method.setMethodEnvironment(new CompilerEnvironment(currentEnvironment));
        for(MethodParam p : t.getParams()) {
            method.getMethodEnvironment().add(p.getName(), new ObjectInfo(vars++, classNameMap.get(p.getClassName())));
            method.addParam(p.getName());
        }

        if(!t.getReturnType().getName().equals("void")) method.setReturnType(classNameMap.get(t.getReturnType().getName()));

        methods.addMethod(method.getCode(), method);
        methodMap.put(t.getName().getName(), method);
        currentEnvironment = method.getMethodEnvironment();
        t.getCommands().accept(this);
        currentEnvironment = currentEnvironment.getSuperEnvironment();
        bytecode.add(Return.code);
        int endaddr = bytecode.size();
        byte[] end = ByteConvertor.toByta(endaddr);
        for(int i = 0; i<end.length; i++) {
            bytecode.set(jumpAddress+i, end[i]);
        }
    }

    @Override
    public void visit(ClassMethodIdentifierTree t) {
        CMethod method;
        int classCode;
        bytecode.add(NewEnv.code);
        if(t.getName() == null) {
            method = methodMap.get(t.getMethodName());
            classCode = methods.getCode();
        } else {
            ObjectInfo obj = currentEnvironment.get(t.getName());
            classCode = obj.getType().getCode();
            method = methodMap.get(obj.getType().getName() + "." + t.getMethodName());

            int address = addNewConstant(IntegerClass.createObject(obj.getId()));
            // add push instr.
            bytecode.add(PushConstant.code);
            // add constant address
            addIntToByteList(address, bytecode);
        }

        int cnt = 0;
        for(ExpressionTree et : t.getParams()) {
            et.accept(this);
            if(method.getMethodEnvironment()!=null) {
                bytecode.add(New.code);
                addIntToByteList(classNameMap.get(et.getType()).getCode(), bytecode);
                addIntToByteList(method.getMethodEnvironment().get(method.getParams().get(cnt++)).getId(), bytecode);
            }
        }
        bytecode.add(Call.code);
        addIntToByteList(classCode,bytecode);
        addIntToByteList(method.getCode(),bytecode);        
    }

    @Override
    public void visit(ClassIdentifierTree t) {
        ObjectInfo objectInfo = currentEnvironment.get(t.getName());

        bytecode.add(Load.code);
        addIntToByteList(objectInfo.getId(),bytecode);
        CClass objectType = objectInfo.getType();
        for(String identifier : t.getIndetifiers()) {
            int pos = objectType.getFieldPos(identifier);
            bytecode.add(LoadField.code);
            addIntToByteList(pos, bytecode);
            objectType = objectType.getFieldType(identifier);
        }


    }

    @Override
    public void visitMethodIdentifier(MethodIdentifierTree methodTree) {
        CMethod method = methodMap.get(methodTree.getName());
        int cnt = 0;

        bytecode.add(NewEnv.code);

        for(ExpressionTree et : methodTree.getParams()) {
            et.accept(this);
            if(method.getMethodEnvironment()!=null) {
                bytecode.add(New.code);
                addIntToByteList(classNameMap.get(et.getType()).getCode(), bytecode);
                addIntToByteList(method.getMethodEnvironment().get(method.getParams().get(cnt++)).getId(), bytecode);
            }
        }
/*        bytecode.add(NewEnv.code);
        for(ExpressionTree et : methodTree.getParams()) {
            et.accept(this);
            bytecode.add(New.code);
            int typeCode;
            if(et.getType() != null) typeCode= classNameMap.get(et.getType()).getCode();
            else if(et instanceof FieldIdentifierTree) typeCode = currentEnvironment.get(((FieldIdentifierTree)et).getName()).getType().getCode();
            else typeCode = 0;
            addIntToByteList(typeCode, bytecode);
            addIntToByteList(method.getMethodEnvironment().get(method.getParams().get(cnt++)).getId(), bytecode);
        }*/
        bytecode.add(Call.code);
        addIntToByteList(methods.getCode(),bytecode);
        addIntToByteList(method.getCode(),bytecode);
    }

    @Override
    public void visit(FieldIdentifierTree t) {
        ObjectInfo objectInfo = currentEnvironment.get(t.getName());
        if(objectInfo != null) {
            bytecode.add(Load.code);
            addIntToByteList(objectInfo.getId(),bytecode);
        }
    }


    @Override
    public void visit(MethodCallTree methodCallTree) {
        CMethod method;
        int classCode;
        bytecode.add(NewEnv.code);
        if(methodCallTree.getObjName() == null) {
            method = methodMap.get(methodCallTree.getMethodName());
            classCode = methods.getCode();
        } else {
            ObjectInfo obj = currentEnvironment.get(methodCallTree.getObjName());
            classCode = obj.getType().getCode();
            method = methodMap.get(obj.getType().getName() + "." + methodCallTree.getMethodName());
            int address = addNewConstant(IntegerClass.createObject(obj.getId()));
            // add push instr.
            bytecode.add(PushConstant.code);
            // add constant address
            addIntToByteList(address, bytecode);
        }

        if(method == null) {
            System.err.println("Nenalezena metoda: " + methodCallTree.getMethodName());
            System.exit(1);
        }

        int cnt = 0;

        for(ExpressionTree et : methodCallTree.getParamsExpressions()) {
            et.accept(this);
            if(method.getMethodEnvironment()!=null) {
                bytecode.add(New.code);
                addIntToByteList(classNameMap.get(et.getType()).getCode(), bytecode);
                addIntToByteList(method.getMethodEnvironment().get(method.getParams().get(cnt++)).getId(), bytecode);
            }
        }
        bytecode.add(Call.code);
        addIntToByteList(classCode,bytecode);
        addIntToByteList(method.getCode(),bytecode);

    }

    @Override
    public void visit(ReturnTree returnTree) {
        if(returnTree.getExpression() == null) bytecode.add(Return.code);
        else {
            returnTree.getExpression().accept(this);
            bytecode.add(ReturnTop.code);
        }

    }

    @Override
    public void visit(WhileTree wt) {
        int conditionPointer = bytecode.size();
        wt.getCondition().accept(this);
        bytecode.add(JumpIfFls.code);
        int endAddress = bytecode.size();
        addIntToByteList(endAddress, bytecode);
        wt.getCommands().accept(this);
        bytecode.add(Jump.code);
        addIntToByteList(conditionPointer,bytecode);
        setIntInBytecode(endAddress, bytecode.size());
    }

    public void setIntInBytecode(int address, int value) {
        byte[] end = ByteConvertor.toByta(value);
        for(int j = 0; j<end.length; j++) {
            bytecode.set(address+j, end[j]);
        }
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
