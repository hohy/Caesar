package caesar.interpreter;
/**
 *
 * @author hohy
 */
public class InterpreterObject {
    private int dataPointer;
    private InterpreterClass type;
    private InterpreterEnvironment environment;

    public InterpreterObject(int dataPointer, InterpreterClass type, InterpreterEnvironment objectEnv) {
        this.dataPointer = dataPointer;
        this.type = type;
        this.environment = objectEnv;
    }
    
    public InterpreterClass getType() {
        return type;
    }

    public void setType(InterpreterClass type) {
        this.type = type;
    }

    public int getDataPointer() {
        return dataPointer;
    }

    public void setDataPointer(int dataPointer) {
        this.dataPointer = dataPointer;
    }

    public InterpreterEnvironment getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        return type.getName() + 
                "("+ dataPointer + ")";
    }

}
