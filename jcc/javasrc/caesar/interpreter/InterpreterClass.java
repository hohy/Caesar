package caesar.interpreter;

import caesar.ast.ExpressionTree;
import caesar.ast.MethodParam;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hohy
 */
public class InterpreterClass {
    // class name
    private String name;
    
    // object size (on stack or in heap)
    private int size;
    
    // operation table
    private Map<String, InterpreterOperation> vtable;
    
    // class fields table
    private Map<String, InterpreterClassField> fieldsTable;

    public InterpreterClass(String name, int size) {
        this.name = name;
        this.size = size;
        vtable = new TreeMap<String, InterpreterOperation>();
        fieldsTable = new HashMap<String, InterpreterClassField>();
    }
    
    public void addOperation(String name, InterpreterOperation op) { 
        vtable.put(name, op);
    }
    
    public void addField(String name, InterpreterClass type, ExpressionTree initTree) {
        Logger.getLogger(InterpreterClass.class.getName()).log(Level.FINER, "Adding new {0} field {1} to class {2}. InitTree: {3}", new Object[]{type.getName(), name, getName(), initTree});
        fieldsTable.put(name, new InterpreterClassField(name, type, initTree, size));
        size += 4;
    }

    /**
     * Metoda slouzici ke spousteni operaci (volani metod)
     * @param interpreter   interpreter, ve kterem chceme operaci spustit
     * @param name          Jmeno operace, kterou spoustime
     * @param params        List parametru operace
     * @param environment   Environment ve kterem ma operace bezet
     */
    public void callOperation(CaesarInterpreter interpreter, String name, List<ExpressionTree> params, InterpreterEnvironment environment) {
        // najdeme se operaci v tabulce
        InterpreterOperation op = vtable.get(name);

//        if(op.getParams().size() != params.size()) {
//            Logger.getLogger(InterpreterClass.class.getName(),"Wrong number of method parameters of method " + name + " in class " + this.getName() + ". Expected " + op.getParams().size() + ", got " + params.size());
//            return;
//        }

//        // zpracovani parametru
//        int i = 0;
        for(ExpressionTree p : params) {
            p.accept(interpreter);
        }
//            try {
//                // najdeme si tridu parametru
//                InterpreterClass paramCls = interpreter.getInterpreterClass(p.getClassName());
//
//                // nechame vypocitat hodnotu vyrazu. Vysledek bude na zasobniku.
//                params.get(i).accept(interpreter);
//                i++;
//
//                // musime vysledek ze zasobniku vzit
//                byte[] data = interpreter.getStack().pop(paramCls.getObjectSize());
//
//                // ulozit jej do heapy
//                int pointer = interpreter.getHeap().store(data);
//
//                // a dat pointer do environmentu pro operaci.
//                InterpreterObject obj = new InterpreterObject(pointer, paramCls, interpreter.getCurrentEnv());
//                environment.add(p.getName(), obj);
//            } catch (Exception e) {
//                Logger.getLogger(InterpreterClass.class.getName(), "Unknown class " + p.getClassName());
//            }
//        }
        // nastavime interpreteru nove environment - predtim si zazalohujeme to stare.
        InterpreterEnvironment oldEnv = interpreter.getCurrentEnv();
        interpreter.setCurrentEnv(environment);
        // a konecne zacneme interpretovat operaci.
        if(op != null) op.call(interpreter);
        else {
            Logger.getLogger(InterpreterClass.class.getCanonicalName()).log(Level.SEVERE, "{0}: Calling unknown method {1}", new Object[]{this.getName(), name});
        }
        // nakonec nastavime zpet puvodni environment.
        interpreter.setCurrentEnv(oldEnv);
    }
    
    public void callOperation(CaesarInterpreter interpreter, String name, InterpreterEnvironment environment) {        
        List<ExpressionTree> params = new LinkedList<ExpressionTree>();
        callOperation(interpreter, name, params, environment);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InterpreterClass{" + "name=" + name + ", vtable=" + vtable.keySet() + '}';
    }

    public int getObjectSize() {
        return size;
    }

    public InterpreterClassField getField(String name) {
        return fieldsTable.get(name);
    }
    
    public Collection<InterpreterClassField> getFields() {
        return fieldsTable.values();
    }

}
