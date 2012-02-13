package caesar.interpreter;

import caesar.ast.*;
import caesar.interpreter.buildin.IntegerClass;
import caesar.interpreter.buildin.RealClass;
import caesar.interpreter.buildin.StringClass;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interpreter of Caesar AST.
 * @author hohy
 */
public class CaesarInterpreter implements TreeVisitor {

    private static final Logger logger = Logger.getLogger(CaesarInterpreter.class.getName());
    
    private InterpreterEnvironment currentEnv;
    private Map<String, InterpreterClass> classTable;
    private String currentIdentifier;
    private InterpreterStack stack;
    private Heap heap;
    private Boolean returnFlag = false;
    

    /**
     * Interprete program. Init environment.
     */
    @Override
    public void visit(ProgramTree t) {       
        currentEnv = new InterpreterEnvironment(null);
        classTable = new TreeMap<String, InterpreterClass>();
        heap = Heap.getInstance(); 
        stack = InterpreterStack.getInstance();
        initClassTable();       
        t.getIdentifier().accept(this);
        t.getCommands().accept(this);
    }

    @Override
    public void visit(CommandListTree t) {
        for(CommandTree command : t.getCommands()) {
            if(returnFlag) break;
            command.accept(this);
            if(command instanceof ReturnTree) {
                setReturnFlag();
            }
        }
    }

    /**
     * Prikaz pro vytvoreni nove promenne nebo instance nejake tridy.
     * @param t
     */
    @Override
    public void visit(CreateVariableTree t) {
        String varName = t.getIdentifier().getName();
        logger.log(Level.FINE, "Creating new variable {0} in enviroment.", varName);
        try {

            // environment pro nove vytvareny objekt
            InterpreterEnvironment objectEnv = new InterpreterEnvironment(currentEnv);
            InterpreterClass cls;

            boolean primitiveType = t.getExp() != null;

            if(primitiveType) { // vytvarime "primitivni" typ
                t.getExp().accept(this);
                cls = getClass(t.getExp());
            } else {    // vytvarime novy objekt
                // pro kazdy field z objektu musime najit misto na halde
                // Kazdy field bude take potreba inicializova a nahrat do nej 
                // inicializacni hodnotu. Ta by mela byt na zasobniku. Aby se na
                // zasobnik dostala, tak musim interpretovat ExpressionTree ktery
                // hodnotu vypocita.
                cls = getClass(t.getIdentifier());
                byte[] object = new byte[cls.getObjectSize()];
                int i = 0;
                for(InterpreterClassField field : cls.getFields()) {

                    if(field.getInitTree() != null) field.getInitTree().accept(this); // interpretut init tree pokud field neni objekt
                    else { // vytvor novou instanci tridy fieldu a pointer dej na stack (odtud se pak dal vezme a da se do noveho objektu)
                        // rekurzivne proto zavolame tuto metodu, akorat v environmentu noveho objektu.
                        IdentifierTree fit = new FieldIdentifierTree(field.getName());
                        fit.setType(field.getType().getName());
                        // je potreba, aby se objekt vytvoril v enviromentu noveho objektu.
                        InterpreterEnvironment old = currentEnv;
                        currentEnv = objectEnv;
                        CreateVariableTree nvt = new CreateVariableTree(fit, null);
                        nvt.accept(this);
                        InterpreterObject newObj = currentEnv.searchEnv(field.getName());
                        stack.pushInteger(newObj.getDataPointer());
                        currentEnv = old;
                    }

                    // vysledek interpretace vyzvednu ze zasobniku
                    byte[] data;
                    if(field.getType().getObjectSize() != -1) { // pokud vim velikost, tak je proste nactu
                        if(primitiveType) data = stack.pop(field.getType().getObjectSize());
                        else data = stack.pop(4);
                    } else { // u stringu velikost nevim... musim si ji nejdrive nacist
                        int lenght = stack.popInteger();
                        byte[] rawdata = stack.pop(lenght); // nactu data stringu
                        // k datum je potreba vratit velikost
                        byte[] ldata = ByteConvertor.toByta(lenght);
                        // vse ulozime jako data, ktera budou ulozena na heapu
                        data = new byte[lenght + 4];
                        System.arraycopy(ldata, 0, data, 0, 4);
                        System.arraycopy(rawdata, 0, data, 4, lenght);
                    }
                    // a dam ho do heapy
                    int pointer = heap.store(data);
                    logger.log(Level.FINEST, "Allocating field {0} of class {1} on pointer {2} for result of {3}", new Object[]{field.getName(), cls.getName(), pointer, field.getInitTree()});

                    // pointer z heapy si prekonvertuju
                    data = ByteConvertor.toByta(pointer);
                    int offset = field.getOffset();
                    i++;
                    // a dam na spravne misto do objektu.
                    System.arraycopy(data, 0, object, offset, data.length);

                    // a do environmentu daneho objektu
                    InterpreterObject fieldObject = new InterpreterObject(pointer, field.getType(), objectEnv);
                    objectEnv.add(field.getName(), fieldObject);
                }
                // vysledek dame na zasobnik
                stack.push(object);
            }            
            int varSize = cls.getObjectSize();
            logger.log(Level.FINEST, "Variable {0} has size {1}", new Object[]{varName, varSize});
            byte[] data;   // inicializacni data objektu. Ta budou ulozena na heape
            int pointer;   // ukazatel, kde na heape je objekt ulozen
            if(varSize != -1) {
                data = stack.pop(cls.getObjectSize());
                pointer = heap.store(data);
            } else {
                String str = stack.popString();
                byte[] strdata = ByteConvertor.toByta(str);
                byte[] length = ByteConvertor.toByta(strdata.length);
                data = new byte[strdata.length + 4];
                System.arraycopy(length, 0, data, 0, 4);
                System.arraycopy(strdata, 0, data, 4, strdata.length);                 
                pointer = heap.store(data);
            }
            objectEnv.add("this", new InterpreterObject(pointer, cls, null));
            currentEnv.add(varName, new InterpreterObject(pointer, cls, objectEnv));    // hotovy objekt si pridam do environmentu
            logger.log(Level.FINEST, "Created new variable {0} of class {1} ObjSize: {2} Pointer: {3}", new Object[]{varName, cls.getName(), cls.getObjectSize(), pointer});
        } catch (Exception ex) {
            Logger.getLogger(CaesarInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
    }

    /**
     * Prirazeni hodnoty do promenne
     */
    @Override
    public void visit(AssignVariableTree t) {                
        t.getExp().accept(this);    // interpretace vyrazu, ktery chceme vlozit do promenne
        String varName = t.getIdentifier().getName();
        InterpreterObject obj = currentEnv.searchEnv(varName);
        InterpreterClass cls = obj.getType();
        
        if(t.getIdentifier() instanceof FieldIdentifierTree) { // prirazujeme do lokalni promenne
            logger.log(Level.FINER, "Setting variable {0} in enviroment.", varName);                        
            if(cls.getObjectSize() != -1) { // pokud to neni string
                byte[] data = stack.pop(cls.getObjectSize());            
                int pointer = obj.getDataPointer();            
                heap.store(data, pointer);
            } else {                // string
                String str = stack.popString();
                byte[] strdata = ByteConvertor.toByta(str);
                byte[] length = ByteConvertor.toByta(strdata.length);
                byte[] data = new byte[strdata.length + 4];
                System.arraycopy(length, 0, data, 0, 4);
                System.arraycopy(strdata, 0, data, 4, strdata.length);                 
                int pointer = heap.store(data);
                obj.setDataPointer(pointer);
            }
        } else {    // prirazujeme do promenne v nejakem objektu
            ClassIdentifierTree tt = (ClassIdentifierTree) t.getIdentifier();
            logger.log(Level.FINER, "Setting field {0} in object {1}", new Object[]{tt.getFldName(), t.getIdentifier().getName()});
            // pozice pointeru na field v objektu
            int offset = cls.getField(tt.getFldName()).getOffset();
            // nacteme pointer na dany field
            byte[] data = heap.get(obj.getDataPointer() + offset, 4);
            int pointer = ByteConvertor.toInt(data);
            // yjistime si velikost objektu ve fieldu
            int objsize = cls.getField(tt.getFldName()).getType().getObjectSize();
            if(objsize != -1) { // string nebo neco jineho?
                data = stack.pop(cls.getField(tt.getFldName()).getType().getObjectSize());
                heap.store(data, pointer);
            } else {
                // nactem si string ze zasobniku
                String str = stack.popString();
                // vytvorim z nich pole bytu ulozitelne na heapu                
                byte[] strdata = ByteConvertor.toByta(str);
                byte[] length = ByteConvertor.toByta(strdata.length);
                data = new byte[strdata.length + 4];                
                System.arraycopy(length, 0, data, 0, 4);
                System.arraycopy(strdata, 0, data, 4, strdata.length);
                // necham heapu, at je nekam ulozi
                int newPointer = heap.store(data);
                logger.log(Level.FINEST, "String field {0}.{1} moved from {2} to {3}", new Object[]{t.getIdentifier().getName(), tt.getFldName(), pointer, newPointer});
                // upravim pointer v objektu
                data = ByteConvertor.toByta(newPointer);
                heap.store(data, obj.getDataPointer() + offset);
            }
            
        }
    }

    @Override
    public void visit(BinaryTree t) {
        Operator op = t.getOperator();
        t.getLeftOperand().accept(this);
        t.getRightOperand().accept(this);
        try {
            InterpreterClass leftOp = getClass(t.getLeftOperand());
            InterpreterClass rightOp = getClass(t.getRightOperand());        
            switch(op) {
                case PLUS: 
                    leftOp.callOperation(this, "add" + rightOp.getName(), currentEnv);
                    break;
                case MINUS: leftOp.callOperation(this, "subtract" + rightOp.getName(), currentEnv); break;
                case MULTIPLY: leftOp.callOperation(this, "multiply" + rightOp.getName(), currentEnv); break;
                case DIVIDE: leftOp.callOperation(this, "divide" + rightOp.getName(), currentEnv); break;
                case EQ:
                    leftOp.callOperation(this, "equals", currentEnv);
                    break;
                case LT:
                    leftOp.callOperation(this, "lt", currentEnv); break;
                    // Todo add other operators
                default: // do nothing
            }
        } catch (Exception ex) {
            Logger.getLogger(CaesarInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void visit(IfTree t) {
        t.getCondition().accept(this);
        boolean result = stack.popBoolean();
        if(result) {
            t.getCommands().accept(this);
        } else if(t.getElseCommands() != null) {
            t.getElseCommands().accept(this);
        }
    }

    @Override
    public void visit(WhileTree t) {
        boolean conditionResult = false;
        t.getCondition().accept(this);
        conditionResult = stack.popBoolean();
        while(conditionResult) {
            t.getCommands().accept(this);
            t.getCondition().accept(this);
            conditionResult = stack.popBoolean();
        }
    }

    @Override
    public void visit(FieldIdentifierTree t) {
        logger.log(Level.FINER, "Loading variable {0} from enviroment.", t.getName());
        InterpreterObject obj = currentEnv.searchEnv(t.getName());
        if(obj != null) {
            int fSize = obj.getType().getObjectSize();
            logger.log(Level.FINEST, "Variable: {0} type: {1} size: {2}", new Object[]{t.getName(), obj.getType().getName(), fSize});
            if(fSize != -1) {
                byte[] data = heap.get(obj.getDataPointer(), fSize);
                t.setType(obj.getType().getName());
                stack.push(data);
            } else {
                byte[] data = heap.get(obj.getDataPointer(), 4);
                int lenght = ByteConvertor.toInt(data);
                data = heap.get(obj.getDataPointer() + 4, lenght);
                String str = ByteConvertor.toString(data);
                stack.pushString(str);
                t.setType("String");
            }
        }        
    }

    @Override
    public void visit(ClassIdentifierTree t) {
        logger.log(Level.FINER, "Loading variable {0} from object {1}", new Object[]{t.getFldName(), t.getName()});
        InterpreterObject obj = currentEnv.searchEnv(t.getName());
        InterpreterClass cls = obj.getType();
        if(obj != null) {
            int offset = cls.getField(t.getFldName()).getOffset();
            byte[] data = heap.get(obj.getDataPointer() + offset, 4);
            int fieldPointer = ByteConvertor.toInt(data);
            InterpreterClass fieldCls =  cls.getField(t.getFldName()).getType();
            if(fieldCls.getObjectSize() != -1) {
                data = heap.get(fieldPointer,fieldCls.getObjectSize());
                stack.push(data);
                t.setType(fieldCls.getName());
            } else {
                data = heap.get(fieldPointer, 4);
                int lenght = ByteConvertor.toInt(data);
                data  = heap.get(fieldPointer + 4, lenght);
                String str = ByteConvertor.toString(data);
                stack.pushString(str);
                t.setType("String");
            }
        }
    }

    @Override
    public void visitMethodIdentifier(MethodIdentifierTree t) {        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ClassMethodIdentifierTree t) {
        ClassIdentifierTree it = new ClassIdentifierTree(t.getName(), t.getMethodName());
        MethodCallTree mct = new MethodCallTree(it, t.getParams());
        mct.accept(this);
        t.setType(mct.getReturnType());
    }
    
    @Override
    public void visit(LiteralTree t) {
        try {            
            logger.log(Level.FINER, "Pushed constant {0} {1}", new Object[]{t.getType(), t.getValue()});
            if(t.getType().equals("Integer")) {
                stack.pushInteger((Integer) t.getValue());
            } else if(t.getType().equals("Real")) {
                stack.pushDouble((Double) t.getValue());
            } else if(t.getType().equals("String")) {
                stack.pushString((String) t.getValue());
            }
        } catch (Exception ex) {
            Logger.getLogger(CaesarInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void visit(PrintlnTree t) {
        t.getExp().accept(this);
        String type = t.getExp().getType();
        if(type.equals("Integer")) {
            int ivalue = stack.popInteger();
            System.out.println(ivalue);
        } else if(type.equals("Real")) {
            double dvalue = stack.popDouble();
            System.out.println(dvalue);
        } else if(type.equals("String")) {
            String svalue = stack.popString();
            System.out.println(svalue);        
        }
    }

    @Override
    public void visit(ClassDefinitionTree t) {        
        InterpreterClass cls = new InterpreterClass(t.getIdentifierTree().getName(), 0);
        classTable.put(cls.getName(), cls);
        for(CreateVariableTree var : t.getVars()) {
            try {
                String varName = var.getIdentifier().getName();                
                InterpreterClass type = getInterpreterClass(var.getIdentifier().getType());
                logger.finer("Adding new " + type.getName() + " field " + varName + " to class " + cls.getName());
                cls.addField(varName, type, var.getExp());
            } catch (Exception ex) {
                Logger.getLogger(CaesarInterpreter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(MethodDefinitionTree mth : t.getMethods()) {
            InterpreterOperation operation = new InterpreterMethod(mth);
            cls.addOperation(mth.getName().getName(), operation);
        }
        logger.fine(cls.toString());
    }

    @Override
    public void visit(MethodDefinitionTree t) {
        try {
            InterpreterClass cls = getInterpreterClass(t.getClassName().getName());
            InterpreterOperation operation = new InterpreterMethod(t);
            cls.addOperation(t.getName().getName(), operation);
            logger.finer("Adding new method " + t.getName() + " to class " + cls.getName());
        } catch (Exception e) {
            logger.finest("Unknown class " + t.getClassName().getName());
        }
    }

    @Override
    public void visit(MethodCallTree t) {
        logger.finer("Calling method " + t.getMethodName() + " on object " + t.getObjName());
        InterpreterObject obj = currentEnv.searchEnv(t.getObjName());

        try {
            String returnType = getInterpreterClass(obj.getType().getName()).callOperation(this, t.getMethodName(), t.getParamsExpressions(), obj.getEnvironment());
            t.setReturnType(returnType);
            clearReturnFlag();
        } catch (Exception e) {
            logger.severe("Calling method " + t.getMethodName() + " on object " + t.getObjName());
        }
    }

    @Override
    public void visit(ReturnTree t) {
        logger.finest("Returning from method");
        // if method return result of some expression, interpret this expression.
        // Result should be on top of the stack.
        if(t.getExpression() != null) t.getExpression().accept(this);
    }
        
    // add build-in classes to classtable
    private void initClassTable() {
        
        // add iteger class
        InterpreterClass integer = new IntegerClass();
        
        classTable.put(integer.getName(), integer);
        
        InterpreterClass real = new RealClass();

        classTable.put(real.getName(), real);
        
        InterpreterClass string = new StringClass();
        
        classTable.put(string.getName(), string);
        
        for(InterpreterClass cls : classTable.values()) {
            logger.fine(cls.toString());
        }
    }

    public InterpreterClass getInterpreterClass(String name) throws Exception {
        InterpreterClass result = classTable.get(name);
        if(result != null) return result;
        throw new Exception("Unknown class " + name);
    }
    
    private InterpreterClass getClass(ExpressionTree t) throws Exception {
        InterpreterClass result;
        result = classTable.get(t.getType());      
        if(result != null) return result;
        throw new Exception("Unknown class " + t.getType());
    }

    public InterpreterStack getStack() {
        return stack;
    }

    public Heap getHeap() {
        return heap;
    }

    public InterpreterEnvironment getCurrentEnv() {
        return currentEnv;
    }

    public void setCurrentEnv(InterpreterEnvironment currentEnv) {
        this.currentEnv = currentEnv;
    }

    public Boolean getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag() {
        this.returnFlag = true;
    }

    public void clearReturnFlag() {
        this.returnFlag = false;
    }
}
